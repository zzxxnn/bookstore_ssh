package zxn.dao;

import zxn.domain.User;

public interface UserDao {

	public void addUser(User user);

	public User findUser(String username, String password);

}
