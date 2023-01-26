package com.storesmanagementsystem.springsecuritymicro.dao;

import java.util.List;

import com.storesmanagementsystem.springsecuritymicro.dto.CartInfoBean;
import com.storesmanagementsystem.springsecuritymicro.dto.OrderDetails;
import com.storesmanagementsystem.springsecuritymicro.dto.UserInfoBean;

public interface UserDAO {

	public boolean register(UserInfoBean admin);

	// public UserInfoBean login(UserInfoBean admin);
	public boolean update(UserInfoBean bean);

	public List<OrderDetails> getOrders(int userId);

	public UserInfoBean getUser(String username);

	public boolean setDeliveredDate(int orderId, String date);

	public boolean addToCart(CartInfoBean cartInfoBean, String userId);

	public List<CartInfoBean> getCartItems(int userId);

	public boolean removeCartItem(int itemId);

	public boolean changeStatus(OrderDetails order);
}
