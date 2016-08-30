package co.openu.hibernate.model;

import java.util.List;

import  co.openu.hibernate.model.User;

public interface UserInterface {

	public void addUser(User u);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserById(int id);
	public void removeUser(int id);
	
}


