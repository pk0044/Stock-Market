package com.pk.hackerearth.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pk.hackerearth.entity.Stock;

/**
 * 
 * @author pk0044
 *
 */
public interface StockRepository extends JpaRepository<Stock, Integer>{
	
	@Query(value = "select * from stock S where S.stock_name = :stock_name and S.stock_type = 'SELL' and S.price <= :price and S.time <= :time order by S.price ASC, S.time ASC", nativeQuery = true)
	public List<Stock> selectMatchedSellOrders(@Param("stock_name") String stock_name, @Param("price") int price, @Param("time") Date time);
	
}
