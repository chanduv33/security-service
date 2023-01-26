package com.storesmanagementsystem.springsecuritymicro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.storesmanagementsystem.springsecuritymicro.dao.UserDAO;
import com.storesmanagementsystem.springsecuritymicro.dto.CartInfoBean;
import com.storesmanagementsystem.springsecuritymicro.dto.OrderDetails;
import com.storesmanagementsystem.springsecuritymicro.dto.UserInfoBean;
import com.storesmanagementsystem.springsecuritymicro.security.UserDetailsImpl;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserDAO dao;

	@Override
	public boolean register(UserInfoBean user) {
		return dao.register(user);
	}

	/*
	 * @Override public UserInfoBean login(UserInfoBean user) { return
	 * dao.login(user); }
	 */

	@Override
	public boolean update(UserInfoBean bean) {
		return dao.update(bean);
	}

	@Override
	public List<OrderDetails> getOrders(int userId) {
		return dao.getOrders(userId);
	}

	@Override
	public boolean setDeliveredDate(int orderId, String date) {
		return dao.setDeliveredDate(orderId, date);
	}

	@Override
	public boolean addToCart(CartInfoBean cartInfoBean, String userId) {

		return dao.addToCart(cartInfoBean, userId);
	}

	@Override
	public List<CartInfoBean> getCartItems(int userId) {
		return dao.getCartItems(userId);
	}

	@Override
	public boolean removeCartItem(int itemId) {
		return dao.removeCartItem(itemId);
	}

	@Override
	public boolean changeStatus(OrderDetails order) {
		return dao.changeStatus(order);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetailsImpl udi = new UserDetailsImpl();
		UserInfoBean user = dao.getUser(username);
		if (user != null) {
			udi.setBean(user);
			return new User(udi.getUsername(), udi.getPassword(), udi.getAuthorities());
		} else {
			throw new UsernameNotFoundException("User Not Found");
		}
	}

	@Override
	public UserInfoBean getUserByEmail(String email) {
		UserInfoBean user = dao.getUser(email);
		if (user != null)
			return user;
		else {
			throw new UsernameNotFoundException("User Not Found");
		}
	}
}
