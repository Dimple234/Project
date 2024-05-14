package com.cloth.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OrderAdmin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer Cid;
	private String paymentMethod;

	private String orderDateTime;
	private String name;
	private String productname;
	private String status;
	private Integer noOfOrder;
	private Integer billAmount;

	public OrderAdmin(Integer id, Integer cid, String paymentMethod, String orderDateTime, String name,
			String productname, String status, Integer noOfOrder, Integer billAmount) {
		super();
		this.id = id;
		Cid = cid;
		this.paymentMethod = paymentMethod;
		this.orderDateTime = orderDateTime;
		this.name = name;
		this.productname = productname;
		this.status = status;
		this.noOfOrder = noOfOrder;
		this.billAmount = billAmount;
	}


	public OrderAdmin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCid() {
		return Cid;
	}


	public void setCid(Integer cid) {
		Cid = cid;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public String getOrderDateTime() {
		return orderDateTime;
	}


	public void setOrderDateTime(String orderDateTime) {
		this.orderDateTime = orderDateTime;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getProductname() {
		return productname;
	}


	public void setProductname(String productname) {
		this.productname = productname;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getNoOfOrder() {
		return noOfOrder;
	}


	public void setNoOfOrder(Integer noOfOrder) {
		this.noOfOrder = noOfOrder;
	}


	public Integer getBillAmount() {
		return billAmount;
	}


	public void setBillAmount(Integer billAmount) {
		this.billAmount = billAmount;
	}


	@Override
	public String toString() {
		return "OrderAdmin [id=" + id + ", Cid=" + Cid + ", paymentMethod=" + paymentMethod + ", orderDateTime="
				+ orderDateTime + ", name=" + name + ", productname=" + productname + ", status=" + status
				+ ", noOfOrder=" + noOfOrder + ", billAmount=" + billAmount + "]";
	}


	
}
