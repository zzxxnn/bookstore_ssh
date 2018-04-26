package zxn.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Orders implements Serializable {
	private String id;
	private String ordernum;
	private int num;
	private float price;
	
	private int state;
	private Set<OrdersItem> ordersItem = new HashSet<OrdersItem>();
	
	public Set<OrdersItem> getOrdersItem() {
		return ordersItem;
	}
	public void setOrdersItem(Set<OrdersItem> ordersItem) {
		this.ordersItem = ordersItem;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	private List<OrdersItem> items = new ArrayList<OrdersItem>();
	
	public List<OrdersItem> getItems() {
		return items;
	}
	public void setItems(List<OrdersItem> items) {
		this.items = items;
	}
	private User user;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
