package zxn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import zxn.dao.CategoryDao;
import zxn.domain.Book;
import zxn.domain.Category;
import zxn.exception.DaoException;
import zxn.util.DBCPUtil;
@Repository
public class CategoryDaoImpl extends DaoSupportImpl<Category> implements CategoryDao{
//	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	@Override
	public void addCategory(Category c) {
		super.save(c);
	}

	@Override
	public List<Category> findAll() {
		return super.findAll();
	}

	@Override
	public Category findCategoryById(String category) {
		return super.getById(category);
	}

}
