package com.pk.hackerearth.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 *  This is an Entity class which holds below information about all the trades
 *  1. ExecutionID of the trade Execution with BUY and SELL order
 *  2. Sell ID
 *  3. Buy ID
 *  4. Quantity of stocks traded
 *  5. Deal Price
 *  6. Time of trade Execution
 * 
 * @author pk0044
 * 
 * @since August 9th, 2020
 *
 */

@Entity
@Table
public class ExecutedStockOrders {
	
	@Id
	@GeneratedValue
	int execution_id;
	int sell_id;
	int buy_id;
	int quantity;
	int price;
	Date time;
	
	public ExecutedStockOrders(int sell_id, int buy_id, int quantity, int price, Date time) {
		this.sell_id = sell_id;
		this.buy_id = buy_id;
		this.quantity = quantity;
		this.price = price;
		this.time = time;
	}
	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public ExecutedStockOrders() {
		
	}

	public int getExecution_id() {
		return execution_id;
	}
	public void setExecution_id(int execution_id) {
		this.execution_id = execution_id;
	}
	public int getSell_id() {
		return sell_id;
	}
	public void setSell_id(int sell_id) {
		this.sell_id = sell_id;
	}
	public int getBuy_id() {
		return buy_id;
	}
	public void setBuy_id(int buy_id) {
		this.buy_id = buy_id;
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
