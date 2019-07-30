package spring.user.dao;

import spring.user.domain.User;

import java.util.List;

public interface UserDao{

	public void add(User user);
	public User get(String id);
	public List<User> getAll();
	public int getCount();
	public void deleteAll();
	public void update(User user1);

}