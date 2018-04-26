package zxn.dao;

import java.util.List;

import zxn.domain.Category;

public interface CategoryDao {
	void addCategory(Category c);
	List<Category> findAll();
	Category findCategoryById(String category);
}
