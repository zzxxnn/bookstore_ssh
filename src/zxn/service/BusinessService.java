package zxn.service;

import java.util.List;

import zxn.domain.Book;
import zxn.domain.Cart;
import zxn.domain.Category;
import zxn.domain.Orders;
import zxn.domain.User;
import zxn.util.Page;

public interface BusinessService {
	void addCategory(Category c);
	List<Category> findAllCategory();
	void addBook(Book book);
	/**
	 * 后台查询图书使用
	 * @param hehe
	 * @return
	 */
	Page findPageRecords(String hehe);
	Category findCategoryById(String category);
	Page findPageRecords(String pagenum, String categoryId);
	Book findBookById(String bookId);
	void regist(User user);
	User login(String username, String password);
	void addOrders(Orders orders, User user);
	List<Orders> findOrdersByUsersId(String id);
	Orders findOrdersById(String id);
	List<Orders> findOrdersByState(int i);
	void sureOrders(String id);
}
