package com.pk.hackerearth.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pk.hackerearth.entity.Stock;
import com.pk.hackerearth.repository.StockRepository;

/**
 *  This is a service class to perform all CRUD operation in stock table.
 *  
 * @author pk0044
 * 
 * @since August 9th 2020
 *
 */
@Service
public class StockService {

	@Autowired
	private StockRepository stockRepo;
	
	
	/**
	 * 
	 * @param stock
	 * @return
	 */
	public Stock saveStock(Stock stock) {
		return stockRepo.save(stock);
	}
	
	/**
	 * 
	 * @param stocks
	 * @return
	 */
	public List<Stock> saveStocks(List<Stock> stocks){
		return stockRepo.saveAll(stocks);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Stock> getStocks(){
		return stockRepo.findAll();
	}
	
	/**
	 * 
	 * @param stockID
	 * @return
	 */
	public Optional<Stock> getStock(int stockID) {
		return stockRepo.findById(stockID);
	}
	
	/**
	 * 
	 * @param stockID
	 * @return
	 */
	public String deleteStock(int stockID) throws EmptyResultDataAccessException{
		stockRepo.deleteById(stockID);
		return "Stock Deleted!!";
	}
	
	/**
	 * 
	 * @param stock
	 * @return
	 */
	public Stock updateStock(Stock stock) {
		Stock oldStock = stockRepo.findById(stock.getOrderID()).orElse(null);
		stock.setOrderID(oldStock.getOrderID());
		return stockRepo.save(stock);
	}
	
	/**
	 * 
	 * @param stockName
	 * @param price
	 * @param time
	 * @return
	 */
	public List<Stock> selectMatchedSellOrders(String stockName, int price, Date time) throws SQLException{
		return stockRepo.selectMatchedSellOrders(stockName, price, time);
	}
}
