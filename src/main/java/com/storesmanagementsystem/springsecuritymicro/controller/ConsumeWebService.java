package com.storesmanagementsystem.springsecuritymicro.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.storesmanagementsystem.springsecuritymicro.dto.DealerProductInfoBean;
import com.storesmanagementsystem.springsecuritymicro.dto.ProductInfoBean;
import com.storesmanagementsystem.springsecuritymicro.dto.ResponseClass;
import com.storesmanagementsystem.springsecuritymicro.dto.UserInfoBean;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "Access-Control-Allow-Origin")
public class ConsumeWebService {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/template/updateMan", method = RequestMethod.POST)
	public ResponseClass updateManufacturer(@RequestBody UserInfoBean bean) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<UserInfoBean> entity = new HttpEntity<UserInfoBean>(bean, headers);
		return restTemplate.exchange("http://localhost:8081/updateMan", HttpMethod.POST, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/getAllMans", method = RequestMethod.GET)
	public ResponseClass getAllManufacturer() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseClass> entity = new HttpEntity<ResponseClass>(headers);
		return restTemplate.exchange("http://localhost:8081/getAllMans", HttpMethod.GET, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/deleteMan", method = RequestMethod.GET)
	public ResponseClass removeManufacturer(@RequestParam("userId") int userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseClass> entity = new HttpEntity<ResponseClass>(headers);
		return restTemplate.exchange("http://localhost:8081/deleteMan?userId=" + userId, HttpMethod.GET, entity,
				ResponseClass.class).getBody();
	}

	@RequestMapping(value = "/template/buyProduct", method = RequestMethod.POST)
	public ResponseClass buyProduct(@RequestBody UserInfoBean bean) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<UserInfoBean> entity = new HttpEntity<UserInfoBean>(bean, headers);
		return restTemplate.exchange("http://localhost:8084/buyProduct", HttpMethod.POST, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/getOrder", method = RequestMethod.GET)
	public ResponseClass getOrder(@RequestParam("orderId") int orderId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseClass> entity = new HttpEntity<ResponseClass>(headers);
		return restTemplate.exchange("http://localhost:8084/getOrder?orderId=" + orderId, HttpMethod.GET, entity,
				ResponseClass.class).getBody();
	}

	@RequestMapping(value = "/template/getDealerProds", method = RequestMethod.GET)
	public ResponseClass getManufacturersProducts() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseClass> entity = new HttpEntity<ResponseClass>(headers);
		return restTemplate
				.exchange("http://localhost:8084/getDealerProds", HttpMethod.GET, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/dealerOrder", method = RequestMethod.POST)
	public ResponseClass placeOrder(@RequestBody UserInfoBean bean) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<UserInfoBean> entity = new HttpEntity<UserInfoBean>(bean, headers);
		return restTemplate.exchange("http://localhost:8083/dealerOrder", HttpMethod.POST, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/setSellingPrice", method = RequestMethod.POST)
	public ResponseClass setSellingPrice(@RequestBody DealerProductInfoBean bean) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<DealerProductInfoBean> entity = new HttpEntity<DealerProductInfoBean>(bean, headers);
		return restTemplate
				.exchange("http://localhost:8083/setSellingPrice", HttpMethod.POST, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/getProds", method = RequestMethod.GET)
	public ResponseClass getAllProducts(@RequestParam("userId") int userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseClass> entity = new HttpEntity<ResponseClass>(headers);
		return restTemplate.exchange("http://localhost:8083/getProds?userId=" + userId, HttpMethod.GET, entity,
				ResponseClass.class).getBody();
	}

	@RequestMapping(value = "/template/getProd", method = RequestMethod.GET)
	public ResponseClass getProduct(@RequestParam("dealersProductId") int dealersProductId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseClass> entity = new HttpEntity<ResponseClass>(headers);
		return restTemplate.exchange("http://localhost:8083/getProd?dealersProductId=" + dealersProductId,
				HttpMethod.GET, entity, ResponseClass.class).getBody();
	}

	@RequestMapping(value = "/template/updateDealerProduct", method = RequestMethod.POST)
	public ResponseClass updateDealerProduct(@RequestBody DealerProductInfoBean bean) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<DealerProductInfoBean> entity = new HttpEntity<DealerProductInfoBean>(bean, headers);
		return restTemplate
				.exchange("http://localhost:8083/updateDealerProduct", HttpMethod.POST, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/setMin", method = RequestMethod.POST)
	public ResponseClass setMinimumQuantity(@RequestBody DealerProductInfoBean bean) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<DealerProductInfoBean> entity = new HttpEntity<DealerProductInfoBean>(bean, headers);
		return restTemplate.exchange("http://localhost:8083/setMin", HttpMethod.POST, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/getMansProds", method = RequestMethod.GET)
	public ResponseClass getMansProducts() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseClass> entity = new HttpEntity<ResponseClass>(headers);
		return restTemplate.exchange("http://localhost:8083/getMansProds", HttpMethod.GET, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/addprod", method = RequestMethod.POST)
	public ResponseClass addProduct(@RequestBody UserInfoBean bean) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<UserInfoBean> entity = new HttpEntity<UserInfoBean>(bean, headers);
		return restTemplate.exchange("http://localhost:8082/addprod", HttpMethod.POST, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/updateCost", method = RequestMethod.PUT)
	public ResponseClass updateProductCost(@RequestBody ProductInfoBean bean) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ProductInfoBean> entity = new HttpEntity<ProductInfoBean>(bean, headers);
		return restTemplate.exchange("http://localhost:8082/updateCost", HttpMethod.PUT, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/getAllProducts", method = RequestMethod.GET)
	public ResponseClass getAllMyProducts(@RequestParam("userId") int userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseClass> entity = new HttpEntity<ResponseClass>(headers);
		return restTemplate.exchange("http://localhost:8082/getAllProducts?userId=" + userId, HttpMethod.GET, entity,
				ResponseClass.class).getBody();
	}

	@RequestMapping(value = "/template/updateProd", method = RequestMethod.PUT)
	public ResponseClass updateProduct(@RequestBody ProductInfoBean bean) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ProductInfoBean> entity = new HttpEntity<ProductInfoBean>(bean, headers);
		return restTemplate.exchange("http://localhost:8082/updateProd", HttpMethod.PUT, entity, ResponseClass.class)
				.getBody();
	}

	@RequestMapping(value = "/template/getPayments", method = RequestMethod.GET)
	public ResponseClass getPayments(@RequestParam("userId") int userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseClass> entity = new HttpEntity<ResponseClass>(headers);
		return restTemplate.exchange("http://localhost:8082/getPayments?userId=" + userId, HttpMethod.GET, entity,
				ResponseClass.class).getBody();
	}

	@RequestMapping(value = "/template/removeProd", method = RequestMethod.GET)
	public ResponseClass removeManufacturerProduct(@RequestParam("productId") int productId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseClass> entity = new HttpEntity<ResponseClass>(headers);
		return restTemplate.exchange("http://localhost:8082/removeProd?productId=" + productId, HttpMethod.GET, entity,
				ResponseClass.class).getBody();
	}

}
