package com.pk.hackerearth.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * This is an Entity Class which holds below information about all the Stock orders
 * 1. Order ID
 * 2. Time at which the stock is Registered
 * 3. Name of the Stock
 * 4. Type of the Stock BUY or SELL
 * 5. Price of the Stock
 * 6. Quantity of Stock
 * 
 * @author pk0044
 * 
 * @since August 9th, 2020
 *
 */
@Entity
@Table
public class Stock {
	
	@Id
	@GeneratedValue
	int orderID;
	
	Date time;
	String stockName;
	String stockType;
	int quantity;
	int price;
	
	
	public Stock(String stockName, String stockType, int quantity, int price, Date time) {
		this.stockName = stockName;
		this.stockType = stockType;
		this.quantity = quantity;
		this.price = price;
		this.time = time;
	}
	
	public Stock(int stockID, String stockName, String stockType, int quantity, int price, Date time) {
		this.orderID = stockID;
		this.stockName = stockName;
		this.stockType = stockType;
		this.quantity = quantity;
		this.price = price;
		this.time = time;
	}
	
	public Stock() {
		
	}
	

	public int getOrderID() {
		return orderID;
	}
	
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getStockType() {
		return stockType;
	}
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

}
