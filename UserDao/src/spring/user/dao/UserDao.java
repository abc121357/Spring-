package spring.user.dao;

import springbook.user.domain.User;

import java.util.List;

public interface UserDao{

	public void add(User user);
	public User get(String id);
	public List<User> getAll();
	public int getCount();
	public void deleteAll();

}