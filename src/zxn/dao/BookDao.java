package zxn.dao;

import java.io.Serializable;
import java.util.List;

import zxn.domain.Book;

public interface BookDao extends Serializable {

	void addBook(Book book);
	int getTotalRecord();
	List<Book> findPageBooks(int startIndex,int pagesize);
	int getTotalRecord(String categoryId);
	List findPageBooks(int startindex, int pagesize, String categoryId);
	Book findBookById(String bookId);
}
