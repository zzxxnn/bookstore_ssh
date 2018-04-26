package zxn.dao;

import java.util.List;

import zxn.domain.Orders;
import zxn.domain.User;

public interface OrdersDao {

	void addOrders(Orders orders, User user);

	List<Orders> findOrdersByUsersId(String id);

	Orders findOrdersById(String id);

	List<Orders> findOrdersByState(int i);

	void sureOrders(String id);
	
}
