package zxn.test;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import zxn.domain.Category;
@Service
public class TestDB {
	@Resource
	private SessionFactory factory;
	@org.junit.Test
	public void Test(){
Session session = factory.getCurrentSession();
		
		session.save(new Category());
	}
}
