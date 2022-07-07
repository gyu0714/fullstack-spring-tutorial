package com.spring.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PageRequestDTO {
	// 화면에서 요청받은 page, size parameter 정보를 얻는 객체
	// --> JPA에서 사용하는 Pageable 타입의 객체를 생성하기 위해
	
	private int size;	
	private int page;

	public PageRequestDTO() {
		this.page = 1;
		this.size = 10;
	}
	
	public Pageable getPageable(Sort sort) {
		return PageRequest.of(page - 1, size, sort);
	}
}
