package zxn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import zxn.dao.OrdersDao;
import zxn.domain.Book;
import zxn.domain.Orders;
import zxn.domain.OrdersItem;
import zxn.domain.User;
import zxn.exception.DaoException;
import zxn.util.DBCPUtil;
@Repository
public class OrdersDaoImpl extends DaoSupportImpl<Orders> implements OrdersDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	@Override
	public void addOrders(Orders orders, User user) {
		try {
			qr.update("insert into orders (id,ordernum,num,price,user_id,state) values(?,?,?,?,?,?)",orders.getId(),orders.getOrdernum(),orders.getNum(),orders.getPrice(),user.getId(),0);
			List<OrdersItem> items = orders.getItems();
			if (items != null&&items.size()>0) {
				/**
				 * 
				 */
				String sql = "insert into ordersitem (id,num,price,orders_id,book_id) values(?,?,?,?,?)";
				/**
				 * 
				 */
				Object pps[][] = new Object[items.size()][];
				for (int i = 0; i < items.size(); i++) {
					OrdersItem item = items.get(i);
					pps[i] = new Object[]{item.getId(),item.getNum(),item.getPrice(),orders.getId(),item.getBook().getId()};
					
				}
				qr.batch(sql, pps);
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	@Override
	public List<Orders> findOrdersByUsersId(String id) {
		try {
			return qr.query("select * from orders where user_id=? order by ordernum desc", new BeanListHandler<Orders>(Orders.class),id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	@Override
	public Orders findOrdersById(String id) {
		try {
			Orders orders = qr.query("select * from orders where id=?", new BeanHandler<Orders>(Orders.class),id);
			
			if (orders!=null) {
				User user = qr.query("select * from user where id=(select user_id from orders where id=?)",new BeanHandler<User>(User.class),id);
				orders.setUser(user);
				List<OrdersItem> items = qr.query("select * from ordersitem where orders_id=?", new BeanListHandler<OrdersItem>(OrdersItem.class),orders.getId());
				if (items!=null&&items.size()>0) {
					for (OrdersItem item:items) {
						Book book = qr.query("select * from book where id=(select book_id from ordersitem where id=?)", new BeanHandler<Book>(Book.class),item.getId());
						item.setBook(book);
					}
				}
				orders.setItems(items);
			}
			return orders;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	@Override
	public List<Orders> findOrdersByState(int i) {
		try {
			
			List<Orders> list = qr.query("select * from orders where state=? order by ordernum desc", new BeanListHandler<Orders>(Orders.class),i);
			if (list != null&&list.size()>0) {
				for (Orders orders : list) {
					User user = qr.query("select * from user where id=(select user_id from orders where id=?)", new BeanHandler<User>(User.class),orders.getId());
					orders.setUser(user);
				}
			}
			return list;
		
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	public void sureOrders(String id) {
		Orders orders = super.getById(id);
		orders.setState(1);
		super.update(orders);
	}
}
