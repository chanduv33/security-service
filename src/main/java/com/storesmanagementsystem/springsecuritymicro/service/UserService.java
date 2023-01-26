package com.storesmanagementsystem.springsecuritymicro.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.storesmanagementsystem.springsecuritymicro.dto.CartInfoBean;
import com.storesmanagementsystem.springsecuritymicro.dto.OrderDetails;
import com.storesmanagementsystem.springsecuritymicro.dto.UserInfoBean;

public interface UserService extends UserDetailsService {
	public boolean register(UserInfoBean admin);

	// public UserInfoBean login(UserInfoBean admin);
	public boolean update(UserInfoBean bean);

	public List<OrderDetails> getOrders(int userId);

	public boolean setDeliveredDate(int orderId, String date);

	public boolean addToCart(CartInfoBean cartInfoBean, String userId);

	public List<CartInfoBean> getCartItems(int userId);

	public boolean removeCartItem(int itemId);

	public boolean changeStatus(OrderDetails order);
	
	public UserInfoBean getUserByEmail(String email);
}
