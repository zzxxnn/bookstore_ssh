package zxn.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import zxn.domain.Book;
import zxn.domain.Cart;
import zxn.domain.CartItem;
import zxn.domain.Category;
import zxn.domain.Orders;
import zxn.domain.OrdersItem;
import zxn.domain.User;
import zxn.service.BusinessService;
import zxn.service.impl.BusinessServiceImpl;
import zxn.util.HQTT;
import zxn.util.Page;
import zxn.util.SureImage;
import zxn.util.WebUtil;

public class ClientServlet extends HttpServlet {
	BusinessService s = new BusinessServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String operation = request.getParameter("operation");
		if ("showIndexCategory".equals(operation)) {
			showIndexCategory(request, response);
		}
		if ("showCategoryBooks".equals(operation)) {
			showCategoryBooks(request, response);
		}
		if ("buyBook".equals(operation)) {
			BuyBook(request, response);
		}
		if ("regist".equals(operation)) {
			regist(request, response);
		}
		if ("login".equals(operation)) {
			login(request, response);
		}
		if ("zx".equals(operation)) {
			zx(request, response);
		}
		if ("genOrders".equals(operation)) {
			genOrders(request, response);
		}
		if ("showUsersOrders".equals(operation)) {
			showUsersOrders(request, response);
		}
		if ("showOrdersDetail".equals(operation)) {
			showOrdersDetail(request, response);
		}
		if ("getimage".equals(operation)) {
			getimage(request, response);
		}

	}

	private void getimage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SureImage image = new SureImage();
		String sb = image.creatImage();

		// 把验证码放到HttpSession中
		request.getSession().setAttribute("code", sb);
		// 输出到浏览器的页面上:ImageIO
		ImageIO.write(image.getImage(), "jpg", response.getOutputStream());
	}

	private void showOrdersDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("ordersId");
		Orders o = s.findOrdersById(id);
		request.setAttribute("o", o);
		request.getRequestDispatcher("/client/showOrdersDetail.jsp").forward(
				request, response);
	}

	private void showUsersOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"请先登录！2秒后自动转向主页。<meta http-equiv='Refresh' content='2';URL="
							+ request.getContextPath() + "/client/login.jsp>");
			request.getRequestDispatcher("/client/message.jsp").forward(
					request, response);
			return;
		}
		List<Orders> list = s.findOrdersByUsersId(user.getId());
		request.setAttribute("os", list);
		request.getRequestDispatcher("/client/listOrders.jsp").forward(request,
				response);

	}

	private void genOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession();
		User user = (User) httpSession.getAttribute("user");
		if (user == null) {
			request.setAttribute(
					"message",
					"请先登录！2秒后自动转向主页。<meta http-equiv='Refresh' content='0';URL=${pageContext.request.contextPath}/client/login.jsp>");
			request.getRequestDispatcher("/client/message.jsp").forward(
					request, response);
			return;
		}
		Cart cart = (Cart) httpSession.getAttribute("cart");
		Orders orders = new Orders();
		orders.setNum(cart.getNum());
		orders.setPrice(cart.getPrice());
		List<OrdersItem> items = new ArrayList<OrdersItem>();
		for (Map.Entry<String, CartItem> item : cart.getItems().entrySet()) {
			CartItem cartItem = item.getValue();
			OrdersItem item2 = new OrdersItem();
			item2.setNum(cartItem.getNum());
			item2.setPrice(cartItem.getPrice());
			item2.setBook(cartItem.getBook());
			items.add(item2);
		}
		orders.setItems(items);
		s.addOrders(orders, user);
		request.setAttribute("message", "生成订单成功");
		request.getRequestDispatcher("/client/message.jsp").forward(request,
				response);
	}

	private void zx(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().setAttribute("user", null);
		request.setAttribute("message", "注销成功");
		request.getRequestDispatcher("/client/message.jsp").forward(request,
				response);

	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		String sessionCode = (String) request.getSession().getAttribute("code");
		// 判断验证码
		if (!code.equalsIgnoreCase(sessionCode)) {
			request.setAttribute("message", "登录失败,验证码错误");
			request.getRequestDispatcher("/client/message.jsp").forward(
					request, response);
			return;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = s.login(username, password);
		if (null == user) {
			request.setAttribute("message", "登录失败，账号或密码错误");
			request.getRequestDispatcher("/client/message.jsp").forward(
					request, response);
		} else {
			request.getSession().setAttribute("user", user);
			response.sendRedirect(request.getContextPath());
		}

	}

	/**
	 * 用了抛异常就不用了
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @throws ServletException
	 * @throws IOException
	 */

	private void sureCode(HttpServletRequest request,
			HttpServletResponse response, String code) throws ServletException,
			IOException {
		System.out.println(code);
		String sessionCode = (String) request.getSession().getAttribute("code");
		// 判断验证码
		if (!code.equalsIgnoreCase(sessionCode)) {
			request.setAttribute("message", "登录失败,验证码错误");
			request.getRequestDispatcher("/client/message.jsp").forward(
					request, response);
			return;
		}
	}

	private void regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// sureCode(request, response, request.getParameter("code"));
		String code = request.getParameter("code");
		String sessionCode = (String) request.getSession().getAttribute("code");
		// 判断验证码
		if (!code.equalsIgnoreCase(sessionCode)) {
			request.setAttribute("message", "注册失败,验证码错误");
			request.getRequestDispatcher(
					request.getContextPath() + "/client/message.jsp").forward(
					request, response);
			return;
		}
		User user = WebUtil.fillBean(request, User.class);
		s.regist(user);
		request.setAttribute("message", "注册成功！");
		request.getRequestDispatcher("/client/message.jsp").forward(request,
				response);
		return;
	}

	private void BuyBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(bookId);
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		cart.addBook(book);
		request.setAttribute("message", "购买成功");
		request.getRequestDispatcher("/client/message.jsp").forward(request,
				response);
	}

	private void showCategoryBooks(final HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pagenum = request.getParameter("pagenum");
		String categoryId = request.getParameter("categoryId");
		Page page = s.findPageRecords(pagenum, categoryId);
		page.setUrl("/servlet/ClientServlet?operation=showCategoryBooks&categoryId="
				+ categoryId);
		request.setAttribute("page", page);
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				request.setAttribute("hehe", new HQTT().getWeather());
//			}
//		}).start();
		setWeather(request,response);
		request.getRequestDispatcher("/client/welcome.jsp").forward(request,
				response);
	}

	private void showIndexCategory(final HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAllCategory();
		request.getSession().setAttribute("cs", cs);
		String pagenum = request.getParameter("pagenum");
		Page page = s.findPageRecords(pagenum);
		page.setUrl("/servlet/ClientServlet?operation=showIndexCategory");
		request.setAttribute("page", page);
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				request.setAttribute("hehe", new HQTT().getWeather());
//			}
//		}).start();
		
		setWeather(request,response);

		request.getRequestDispatcher("/client/welcome.jsp").forward(request,
				response);
	}
	
	

	private void setWeather(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String a = (String) session.getAttribute("hehe");
		if ( a == null ) {
			session.setAttribute("hehe", new HQTT().getWeather());
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 
		 */
		doGet(request, response);
		/**
		 * 
		 */
	}

}
