package com.intheeast.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity<ID> implements Persistable<ID> {

    @Transient // 이 어노테이션이 적용된 필드는 테이블의 컬럼으로 만들어지지 않음
    private boolean isNew = true;  

    @Override
    public boolean isNew() {
        return isNew;  
    }

    @PrePersist  
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
	
	protected String createdBy;
	
	@CreationTimestamp
	protected LocalDateTime creationDate;
	
	protected String lastModifiedBy;
	
	@UpdateTimestamp
	protected LocalDateTime lastModifiedDate;

}