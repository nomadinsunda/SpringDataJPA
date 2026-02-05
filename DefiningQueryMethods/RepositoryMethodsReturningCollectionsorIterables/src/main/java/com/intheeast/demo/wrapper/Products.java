package com.intheeast.demo.wrapper;


import com.intheeast.demo.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;

import java.math.BigDecimal;
import java.util.Iterator;

@RequiredArgsConstructor(staticName = "of")
public class Products implements Streamable<Product> {

    private final Streamable<Product> streamable;

    // 총 가격 계산 메서드
    public BigDecimal getTotalPrice() {
    	
        return streamable.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    @Override
    public Iterator<Product> iterator() {
        return streamable.iterator();
    }
}
