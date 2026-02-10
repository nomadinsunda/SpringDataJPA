package com.intheeast.demo.repository;

import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.repository.CrudRepository;

import com.intheeast.demo.domain.Product;

import org.springframework.data.domain.Sort;

public interface ProductRepository extends CrudRepository<Product, Long> {
	
	/**
     * 가격 내림차순(Price DESC), ID 내림차순(ID DESC)으로 정렬하여
     * 한 번에 20개씩 데이터를 가져오는 Window 메서드.
     */
    Window<Product> findFirst20ByOrderByPriceDescIdDesc(ScrollPosition position);
    
    // findFirst(Size) 방식으로 limit를 걸고, ScrollPosition으로 위치를 잡습니다.
    Window<Product> findFirst20ByOrderByPriceDescIdDesc(KeysetScrollPosition position);
}