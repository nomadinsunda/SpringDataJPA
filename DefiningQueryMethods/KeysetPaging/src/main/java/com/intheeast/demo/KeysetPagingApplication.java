package com.intheeast.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.intheeast.demo.domain.Product;
import com.intheeast.demo.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class KeysetPagingApplication {
	
	@Bean
    CommandLineRunner initData(ProductRepository repository) {
        return args -> {
            // 기존 데이터 삭제 (선택 사항)
            repository.deleteAll();

            // 1000개의 상품 생성
            for (int i = 1; i <= 10; i++) {
                Product product = new Product();
                product.setName("상품 " + i);
                // 가격을 1000원 단위로 다르게 설정 (중복 가격도 테스트되도록 i / 10 활용)
                product.setPrice(1000 * (i / 10 + 1)); 
                repository.save(product);
            }
            
            log.info(">>> 100개의 테스트 데이터 생성 완료");
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(KeysetPagingApplication.class, args);
	}

}
