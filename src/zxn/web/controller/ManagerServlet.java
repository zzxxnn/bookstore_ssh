package zxn.web.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import zxn.domain.Book;
import zxn.domain.Category;
import zxn.domain.Orders;
import zxn.service.BusinessService;
import zxn.service.impl.BusinessServiceImpl;
import zxn.util.Page;
import zxn.util.WebUtil;

public class ManagerServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opretion = request.getParameter("operation");
		if ("addCategory".equals(opretion)) {
			addCategory(request, response);
		}
		if ("showAllCategory".equals(opretion)) {
			showAllCategory(request, response);
		}
		if ("showAllCategoryUI".equals(opretion)) {
			showAllCategoryUI(request, response);
		}
		if ("addBook".equals(opretion)) {

			try {
				addBook(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if ("showAllBook".equals(opretion)) {
			showAllBook(request, response);
		}
		if ("showAllOrders0".equals(opretion)) {
			showAllOrders0(request, response);
		}
		if ("showAllOrders1".equals(opretion)) {
			showAllOrders1(request, response);
		}
		if ("showOrderDetail".equals(opretion)) {
			showOrderDetail(request, response);
		}
		if ("sureOrders".equals(opretion)) {
			sureOrders(request, response);
		}

	}

	private void sureOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("ordersId");
		s.sureOrders(id);
		request.setAttribute("message", "�����ɹ�");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	private void showOrderDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("ordersId");
		Orders o = s.findOrdersById(id);
		request.setAttribute("o", o);
		request.getRequestDispatcher("/manager/showOrdersDetail.jsp").forward(
				request, response);
	}

	private void showAllOrders1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Orders> list = s.findOrdersByState(1);
		request.setAttribute("os", list);
		request.getRequestDispatcher("/manager/showOrders.jsp").forward(
				request, response);
	}

	private void showAllOrders0(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Orders> list = s.findOrdersByState(0);
		request.setAttribute("os", list);
		request.getRequestDispatcher("/manager/showOrders.jsp").forward(
				request, response);
	}

	private void showAllBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pagenum = request.getParameter("pagenum");
		Page page = s.findPageRecords(pagenum);
		page.setUrl("/servlet/ManagerServlet?operation=showAllBook");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manager/listBook.jsp").forward(request,
				response);

	}

	private void addBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			IllegalAccessException, InvocationTargetException {
		String resultpath = "";
		String storpath = getServletContext().getRealPath("/files");
		try {
			Book book = new Book();

			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			// ��������
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					// ��װ���ݵ�JavaBean��
					String fieldName = item.getFieldName();// �ֶ�������javabean��������������ͼƬ
					String fieldValue = item.getString(request
							.getCharacterEncoding());
					BeanUtils.setProperty(book, fieldName, fieldValue);// ����ͼƬ·�����������ݶ�����
				} else {
					// �����ļ��ϴ�
					InputStream in = item.getInputStream();
					String fileName = item.getName();// c:\dsf\a.jpg
					fileName = UUID.randomUUID()
							+ fileName
									.substring(fileName.lastIndexOf("\\") + 1);// a.jpg
					// ���ô�ȡ��ͼƬ�ļ���
					book.setImage(fileName);
					OutputStream out = new FileOutputStream(storpath + "\\"
							+ fileName);
					byte b[] = new byte[1024];
					int len = -1;
					while ((len = in.read(b)) != -1) {
						out.write(b, 0, len);
					}
					out.close();
					in.close();
					item.delete();// ɾ����ʱ�ļ�
				}
			}
			s.addBook(book);
			// ��ѯ����
			List<Category> cs = s.findAllCategory();
			request.setAttribute("cs", cs);
			resultpath = "/manager/addBook.jsp";
			request.setAttribute("message",
					"<script type='text/javascript'>alert('��ӳɹ�')</script>");
		} catch (FileUploadException e) {
			e.printStackTrace();
			resultpath = "/message.jsp";
			request.setAttribute("message", "������æ");
		}
		request.getRequestDispatcher(resultpath).forward(request, response);
	}

	private void showAllCategoryUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAllCategory();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manager/addBook.jsp").forward(request,
				response);
	}

	private void showAllCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAllCategory();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manager/listCategory.jsp").forward(
				request, response);
	}

	private void addCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Category c = WebUtil.fillBean(request, Category.class);
		s.addCategory(c);
		request.setAttribute("message",
				"<script type='text/javascript'>alert('��ӳɹ�')</script>");
		request.getRequestDispatcher("/manager/addCategory.jsp").forward(
				request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
