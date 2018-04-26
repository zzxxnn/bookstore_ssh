package zxn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zxn.dao.BookDao;
import zxn.dao.CategoryDao;
import zxn.dao.OrdersDao;
import zxn.dao.UserDao;
import zxn.dao.impl.BookDaoImpl;
import zxn.dao.impl.CategoryDaoImpl;
import zxn.dao.impl.OrdersDaoImpl;
import zxn.dao.impl.UserDaoImpl;
import zxn.domain.Book;
import zxn.domain.Category;
import zxn.domain.Orders;
import zxn.domain.OrdersItem;
import zxn.domain.User;
import zxn.service.BusinessService;
import zxn.util.IdGenerator;
import zxn.util.Page;
@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {
	@Resource
	private CategoryDao cdao;
	@Resource
	private BookDao bookdao; 
	@Resource
	private UserDao userdao;
	@Resource
	private OrdersDao ODao;
	@Override
	public void addCategory(Category c) {
		c.setId(IdGenerator.genPrimaryKey());
		cdao.addCategory(c);
	}

	@Override
	public List<Category> findAllCategory() {
		return cdao.findAll();
	}

	@Override
	public void addBook(Book book) {
		book.setId(IdGenerator.genPrimaryKey());
		bookdao.addBook(book);
	}

	@Override
	public Page findPageRecords(String hehe) {
		int num = 1;
		if (hehe != null &&!"".equals(hehe.trim())) {
			num = Integer.parseInt(hehe);
		}
		int totalrecords = bookdao.getTotalRecord();
		Page page = new Page(num,totalrecords);
		List records = bookdao.findPageBooks(page.getStartindex(), page.getPagesize());
		
		page.setRecords(records);
		return page;
	}

	@Override
	public Category findCategoryById(String category) {
		
		return cdao.findCategoryById(category);
	}

	@Override
	public Page findPageRecords(String hehe, String categoryId) {
		int num = 1;
		if (hehe != null && !"".equals(hehe.trim())) {
			num = Integer.parseInt(hehe);
		}
		int totalrecords = bookdao.getTotalRecord(categoryId);
		Page page = new Page(num,totalrecords);
		List records = bookdao.findPageBooks(page.getStartindex(), page.getPagesize(),categoryId);
		
		page.setRecords(records);
		return page;
	}

	@Override
	public Book findBookById(String bookId) {
		return bookdao.findBookById(bookId);
	}

	@Override
	public void regist(User user) {
		user.setId(IdGenerator.genPrimaryKey());
		userdao.addUser(user);
	}

	@Override
	public User login(String username, String password) {
		return userdao.findUser(username,password);
	}

	public void addOrders(Orders orders, User user) {
		orders.setId(IdGenerator.genPrimaryKey());
		orders.setOrdernum(IdGenerator.genOrdersNum());
		//给购物项添加id
		List<OrdersItem> items = orders.getItems();
		for(OrdersItem item:items)
			item.setId(IdGenerator.genPrimaryKey());
		ODao.addOrders(orders,user);
	}

	@Override
	public List<Orders> findOrdersByUsersId(String id) {
		return ODao.findOrdersByUsersId(id);
	}

	@Override
	public Orders findOrdersById(String id) {
		return ODao.findOrdersById(id);
	}

	@Override
	public List<Orders> findOrdersByState(int i) {
		return ODao.findOrdersByState(i);
	}

	@Override
	public void sureOrders(String id) {
		ODao.sureOrders(id);
	}


}
