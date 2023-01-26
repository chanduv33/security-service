package com.storesmanagementsystem.springsecuritymicro.dao;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.storesmanagementsystem.springsecuritymicro.dto.CartInfoBean;
import com.storesmanagementsystem.springsecuritymicro.dto.DealerProductInfoBean;
import com.storesmanagementsystem.springsecuritymicro.dto.OrderDetails;
import com.storesmanagementsystem.springsecuritymicro.dto.ProductInfoBean;
import com.storesmanagementsystem.springsecuritymicro.dto.UserInfoBean;
import com.storesmanagementsystem.springsecuritymicro.exceptions.EmailAlreadyExistsException;
import com.storesmanagementsystem.springsecuritymicro.exceptions.MobileNumberAlreadyExistsException;

import lombok.extern.java.Log;

@Repository
@Log
public class UserDAOImpl implements UserDAO {
	// public static UserInfoBean user = new UserInfoBean();;
	@PersistenceUnit
	private EntityManagerFactory fact;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public boolean register(UserInfoBean user) {
		EntityManager mgr = fact.createEntityManager();
		EntityTransaction tx = mgr.getTransaction();
		tx.begin();
		String jpql = "select u from UserInfoBean u";
		TypedQuery<UserInfoBean> query = mgr.createQuery(jpql, UserInfoBean.class);
		List<UserInfoBean> list = query.getResultList();
		if (!list.isEmpty()) {
			for (UserInfoBean userInfoBean : list) {
				if (null != user.getUsername() && user.getUsername().equalsIgnoreCase(userInfoBean.getUsername())) {
					throw new EmailAlreadyExistsException();
				}
				if (user.getMobileNumber() != 0 && user.getMobileNumber() == userInfoBean.getMobileNumber()) {
					throw new MobileNumberAlreadyExistsException();
				}
			}
		}
		user.setPassword(encoder.encode(user.getPassword()));
		mgr.persist(user);
		tx.commit();
		return true;
	}

	@Override
	public boolean update(UserInfoBean bean) {
		EntityManager mgr = fact.createEntityManager();
		EntityTransaction tx = mgr.getTransaction();
		try {
			tx.begin();
			UserInfoBean updateBean = mgr.find(UserInfoBean.class, bean.getUserId());
			updateBean.setPassword(bean.getPassword());
			mgr.persist(updateBean);
			tx.commit();
			return true;
		} catch (Exception e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				log.info(ele.toString());
				return false;
			}
		}
		return false;

	}

	@Override
	public List<OrderDetails> getOrders(int userId) {
		EntityManager mgr = fact.createEntityManager();
		@SuppressWarnings("unchecked")
		TypedQuery<OrderDetails> query = (TypedQuery<OrderDetails>) mgr
				.createNativeQuery("select * from orders_info  where userId=" + userId, OrderDetails.class);
		List<OrderDetails> list = query.getResultList();
		return list;
	}

	@Override
	public UserInfoBean getUser(String username) {
		EntityManager mgr = fact.createEntityManager();
		try {
			String jpql = "select a from UserInfoBean a where a.username=:username";
			TypedQuery<UserInfoBean> query = mgr.createQuery(jpql, UserInfoBean.class);
			query.setParameter("username", username);
			UserInfoBean bean = query.getSingleResult();
			if (bean.getUsername().equals(username)) {
				// user.setRole(bean.getRole());
				// user.setUserId(bean.getUserId());
				return bean;
			} else {
				// user = null;
				return null;
			}
		} catch (Exception e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				log.info(ele.toString());
				return null;
			}
		}
		return null;
	}

	@Override
	public boolean setDeliveredDate(int orderId, String date) {
		EntityManager mgr = fact.createEntityManager();
		EntityTransaction tx = mgr.getTransaction();
		try {
			tx.begin();
			OrderDetails bean = mgr.find(OrderDetails.class, orderId);
			LocalDate deliveredDate = LocalDate.parse(date);
			bean.setDeliveredOn(deliveredDate);
			Period p1 = Period.between(bean.getDateOfOrder(), bean.getDateOfDelivery());
			if (deliveredDate.isBefore(bean.getDateOfOrder())) {
				return false;
			} else {
				Period p2 = Period.between(deliveredDate, bean.getDateOfDelivery());
				if (p2.getDays() <= p1.getDays()) {
					bean.setStatus("Delivered");
					mgr.persist(bean);
					tx.commit();
					return true;
				} else {
					bean.setStatus("Order Delivered Lately");
					mgr.persist(bean);
					tx.commit();
					return true;
				}
			}
		} catch (Exception e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				log.info(ele.toString());
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean addToCart(CartInfoBean cartInfoBean, String userId) {
		EntityManager mgr = fact.createEntityManager();
		EntityTransaction tx = mgr.getTransaction();
		try {
			tx.begin();
			UserInfoBean user = mgr.find(UserInfoBean.class, Integer.parseInt(userId));
//			Iterator<CartInfoBean> itr = bean.getItems().iterator();
//			if (itr.hasNext()) {
//				CartInfoBean item = itr.next();
			CartInfoBean item = cartInfoBean;
			CartInfoBean cartItem = new CartInfoBean();
			if (user.getRole().equals("ROLE_DEALER")) {
				ProductInfoBean product = mgr.find(ProductInfoBean.class, item.getItemProductId());
				cartItem.setItemProductId(product.getProductId());
				cartItem.setRole(user.getRole());
				cartItem.setQuantity(item.getQuantity());
				cartItem.setUser(user);
			} else if (user.getRole().equals("ROLE_CUSTOMER")) {
				DealerProductInfoBean product = mgr.find(DealerProductInfoBean.class, item.getItemProductId());
				cartItem.setItemProductId(product.getDealersProductId());
				cartItem.setRole(user.getRole());
				cartItem.setQuantity(item.getQuantity());
				cartItem.setUser(user);
			}
			user.getItems().add(cartItem);
			mgr.persist(user);
			tx.commit();
			return true;

		} catch (

		Exception e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				log.info(ele.toString());
				return false;
			}
		}
		return false;
	}

	@Override
	public List<CartInfoBean> getCartItems(int userId) {
		EntityManager mgr = fact.createEntityManager();
		@SuppressWarnings("unchecked")
		TypedQuery<CartInfoBean> query = (TypedQuery<CartInfoBean>) mgr
				.createNativeQuery("select * from cart_info  where userId=" + userId, CartInfoBean.class);
		List<CartInfoBean> list = query.getResultList();
		for (CartInfoBean item : list) {
			if (item.getRole().equals("ROLE_DEALER")) {
				ProductInfoBean product = mgr.find(ProductInfoBean.class, item.getItemProductId());
				item.setProduct(product);
			} else if (item.getRole().equals("ROLE_CUSTOMER")) {
				DealerProductInfoBean product = mgr.find(DealerProductInfoBean.class, item.getItemProductId());
				item.setDealerProd(product);
			}
		}
		return list;
	}

	@Override
	public boolean removeCartItem(int itemId) {
		EntityManager mgr = fact.createEntityManager();
		EntityTransaction tx = mgr.getTransaction();
		try {
			tx.begin();
			CartInfoBean bean = mgr.find(CartInfoBean.class, itemId);
			mgr.remove(bean);
			tx.commit();
			return true;
		} catch (Exception e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				log.info(ele.toString());
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean changeStatus(OrderDetails order) {
		EntityManager mgr = fact.createEntityManager();
		EntityTransaction tx = mgr.getTransaction();
		try {
			tx.begin();
			OrderDetails bean = mgr.find(OrderDetails.class, order.getOrderId());
			bean.setStatus(order.getStatus());
			if (order.getStatus().equals("delivered")) {
				bean.setDeliveredOn(LocalDate.now());
			}
			mgr.persist(bean);
			tx.commit();
			return true;
		} catch (Exception e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				log.info(ele.toString());
				return false;
			}
		}
		return false;
	}

}
