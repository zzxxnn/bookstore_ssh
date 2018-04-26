package zxn.web.funtions;

import zxn.domain.Category;
import zxn.service.BusinessService;
import zxn.service.impl.BusinessServiceImpl;

public class MyFuntion {
	
	public static String getCategoryName(String category){
		BusinessService s = new BusinessServiceImpl();
		Category c = s.findCategoryById(category);
		if (c!=null) {
			return c.getName();
		}
		return "";
	}
}
