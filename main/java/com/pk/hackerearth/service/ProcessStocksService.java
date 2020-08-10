package com.pk.hackerearth.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.hackerearth.entity.ExecutedStockOrders;
import com.pk.hackerearth.entity.Stock;
import com.pk.hackerearth.repository.ExecutedStockOrderRepo;

/**
 * 
 * This is a service class which process BUY order with the SELL orders
 * 
 * @author pk0044
 *
 * @since August 9th, 2020
 *
 */
@Service
public class ProcessStocksService {

	@Autowired
	StockService stockService;
	
	@Autowired
	ExecutedStockOrderRepo execOrderRepoObj;
	
	public ProcessStocksService() {

	}

	/**
	 * 
	 * @param buy_id
	 * @param stockName
	 * @param price
	 * @param quantity
	 * @param time
	 * @return
	 * @throws SQLException 
	 */
	public List<ExecutedStockOrders> processSellOrder(int buy_id, String stockName, int price, int quantity, Date time) throws SQLException {
		List<ExecutedStockOrders> executedOrders = new ArrayList<ExecutedStockOrders>();
		List<Stock> matchedStocks = stockService.selectMatchedSellOrders(stockName, price, time);
		
		if(matchedStocks == null || matchedStocks.isEmpty()) {
			System.out.println("No matched Sell orders found!!");
			return null;
		}

		int availableQuantity = quantity;
		int execQuantity = 0;
		
		for(Stock stock : matchedStocks) {			
			if(availableQuantity > 0) {
				int sell_id = stock.getOrderID();
				if(availableQuantity > stock.getQuantity()) {
					execQuantity = stock.getQuantity();
					availableQuantity = availableQuantity - stock.getQuantity();
					stockService.deleteStock(stock.getOrderID());
				}else {
					execQuantity = availableQuantity;
					stock.setQuantity(stock.getQuantity() - availableQuantity);
					stockService.updateStock(stock);
					availableQuantity = 0;
				}
				ExecutedStockOrders execObj = new ExecutedStockOrders(sell_id, buy_id, execQuantity, stock.getPrice(), time);
				executedOrders.add(execObj);
			}else {
				break;
			}
		}
		execOrderRepoObj.saveAll(executedOrders);
		return executedOrders;
	}
	
	/**
	 * 
	 * @param execOrders
	 * @return
	 */
	public ExecutedStockOrders addExecutedStockOrders(ExecutedStockOrders execOrders) {
		return execOrderRepoObj.save(execOrders);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ExecutedStockOrders> getAllExecutedOrders() {
		return execOrderRepoObj.findAll();
	}
}
