package zxn.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Category implements Serializable{
	private String id;
	private String name;
	private String description;
	private Set<Book> books = new HashSet<Book>();
	
	public Set<Book> getBooks() {
		return books;
	}
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
