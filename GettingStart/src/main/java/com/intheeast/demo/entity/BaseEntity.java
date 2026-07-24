package com.intheeast.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass //....
//@Entity
public class BaseEntity { // 엔티티 클래스가 아니기 때문에 관계형 데이터베이스의 테이블로 생성/매핑되지 않음
	
	// nullable is true
	@Column(
			length = 20,
			columnDefinition = "varchar(20) default 'UnKnown'"
		)
	protected String createdBy; // 누구에 의해서 생성되었는지가 필요할 때...
	
	@CreationTimestamp
	protected LocalDateTime creationDate;
	
	// nullable is true
	@Column(
			length = 20,
			columnDefinition = "varchar(20) default 'UnKnown'"
		)
	protected String lastModifiedBy;  // 누구에 의해서 수정되었는가?
	
	@UpdateTimestamp
	protected LocalDateTime lastModifiedDate;

}