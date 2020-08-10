package com.pk.hackerearth.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pk.hackerearth.entity.ExecutedStockOrders;
import com.pk.hackerearth.entity.Stock;
import com.pk.hackerearth.service.ProcessStocksService;
import com.pk.hackerearth.service.StockService;

/**
 * 
 *  This Controller class receives client request for below operations and respond with applicable result
 *  1. Add stock
 *  2. Delete Stock
 *  3. Modify Stock
 *  4. Get a Stock by ID
 *  5. Get lists of All Stocks
 *  
 * 
 * 
 * @author pk0044
 * @since August 9th, 2020
 *
 */
@RestController
public class StockController {

	@Autowired
	StockService stockService;
	
	@Autowired
	ProcessStocksService proceStocksServiceObj;
	
	
	/**
	 * 
	 * Add Stocks (SELL or BUY)
	 * 
	 * if stockType is SELL it simply adds the Stock in the stock table
	 * if stockType is BUY it match the give stock with All SELL stocks which are eligible to make a trade with this BUY order
	 * and execute the given BUY order and stores and returns the list of executed Trades.
	 * 
	 * @param reqBody
	 * @return List of Trades done by BUY order
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/addStock", method = RequestMethod.POST)
	public Map<String, Object> addStock(@RequestBody String reqBody) throws JSONException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<ExecutedStockOrders> executedStocks = new ArrayList<ExecutedStockOrders>();
		JSONObject jsonObj = new JSONObject(reqBody);
		String stockName = (String) jsonObj.get("stockName");
		String stockType = (String) jsonObj.get("stockType");
		int quantity = jsonObj.getInt("quantity");
		int price = jsonObj.getInt("price");
		String date = jsonObj.getString("date");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date time = null;
		try {
			time = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Stock stock = new Stock(stockName, stockType, Integer.valueOf(quantity), Integer.valueOf(price), time);
		Stock addedStock = stockService.saveStock(stock);
		System.out.println("stock :: " + stock.toString());
		if(stock.getStockType().equals("BUY")) {
			try {
				executedStocks = proceStocksServiceObj.processSellOrder(addedStock.getOrderID(), stockName, price, quantity, time);
				resMap.put("ExecutedSellOrders", executedStocks);
				resMap.put("RESULT", "OK");
			} catch (SQLException e) {
				resMap.put("RESULT", "NOK");
				e.printStackTrace();
			}
		}
		
		return resMap;
	}
	
	
	/**
	 * 
	 * Returns a stock with an ID passed in the parameter
	 * 
	 * @param reqBody
	 * @return Stock with a given ID
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/getStockByID", method = RequestMethod.GET)
	public Optional<Stock> getStockByID(@RequestBody String reqBody) throws JSONException {
		JSONObject jsonObj = new JSONObject(reqBody);
		int stockID = jsonObj.getInt("stockID");
		return stockService.getStock(stockID);
	}
	
	/**
	 * Returns the list of all stocks (SELL and BUY) present in the stock table
	 * 
	 * @return Map containing list of stocks
	 */
	@RequestMapping(value = "/getAllStocks", method = RequestMethod.GET)
	public Map<String, Object> getAllStocks(){
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<Stock> stockList = stockService.getStocks();
		resMap.put("stockList", stockList);
		resMap.put("RESULT", "OK");
		return resMap;
	}
	
	/**
	 *  Returns the list of Executed Stocks from the executed_stock_orders table
	 *  
	 * @return List of Executes Stocks
	 */
	@RequestMapping(value = "/getExecutedStocks", method = RequestMethod.GET)
	public Map<String, Object> getExecutedStocks(){
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<ExecutedStockOrders> execOrder = proceStocksServiceObj.getAllExecutedOrders();
		resMap.put("execOrders", execOrder);
		resMap.put("RESULT", "OK");
		return resMap;
	}
	
	/**
	 * Returns the list of SELL orders which are fulfilling the criteria for given BUY order in param 
	 * 
	 * @param reqBody
	 * @return List of SELL orders
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/getMatchedSellOrders", method = RequestMethod.GET)
	public Map<String, Object> getMachedSellOrders(@RequestBody String reqBody) throws JSONException{
		Map<String, Object> resMap = new HashMap<String, Object>();
		JSONObject jsonObj = new JSONObject(reqBody);
		String stockName = (String) jsonObj.get("stockName");
		int price = jsonObj.getInt("price");
		String date = jsonObj.getString("date");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date time = null;
		try {
			time = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Stock> matchedStocks = null;
		try {
			matchedStocks = stockService.selectMatchedSellOrders(stockName, price, time);
		} catch (SQLException e) {
			resMap.put("RESULT", "NOK");
			e.printStackTrace();
		}
		resMap.put("matchedStocks", matchedStocks);
		return resMap;
	}
	
	
	/**
	 * 
	 * updates a given Stock in the table stock 
	 * 
	 * @param reqBody
	 * @return updated Stock
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/updateStock", method = RequestMethod.PUT)
	public Stock updateStock(@RequestBody String reqBody) throws JSONException {
		JSONObject jsonObj = new JSONObject(reqBody);
		int stockID = jsonObj.getInt("stockID");
		String stockName = (String) jsonObj.get("stockName");
		String stockType = (String) jsonObj.get("stockType");
		int quantity = jsonObj.getInt("quantity");
		int price = jsonObj.getInt("price");
		String date = jsonObj.getString("date");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date time = null;
		try {
			time = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Stock stock = new Stock(stockID, stockName, stockType, Integer.valueOf(quantity), Integer.valueOf(price), time);
		return stockService.updateStock(stock);
	}
	
	/**
	 *  
	 *  Deletes a Stock form the stock table
	 *  
	 * @param reqBody
	 * @return status of delete operation
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/deleteStock", method = RequestMethod.DELETE)
	public Map<String, Object> deleteStock(@RequestBody String reqBody) throws JSONException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		JSONObject jsonObj = new JSONObject(reqBody);
		int stockID = jsonObj.getInt("stockID");
		
		try {
			String res = stockService.deleteStock(stockID);
			resMap.put("RESULT", "OK");
		}catch(EmptyResultDataAccessException e) {
			resMap.put("RESULT", "NOK");
			resMap.put("errorMessage", "Seletced item is not present.");
			e.printStackTrace();
		}
		return resMap;
	}
	
}
