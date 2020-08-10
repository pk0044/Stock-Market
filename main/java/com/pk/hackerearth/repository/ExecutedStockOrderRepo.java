package com.pk.hackerearth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pk.hackerearth.entity.ExecutedStockOrders;
import com.pk.hackerearth.entity.Stock;

public interface ExecutedStockOrderRepo extends JpaRepository<ExecutedStockOrders, Integer>{

}
