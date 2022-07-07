package com.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.DiaryDTO;
import com.spring.dto.PageRequestDTO;
import com.spring.dto.PageResultDTO;
import com.spring.entity.Diary;
import com.spring.repository.DiaryRepository;
import com.spring.service.DiaryServiceImpl;


@RestController
// 생성자 주입
//@RequiredArgsConstructor // fianl이 붙거나 @NotNull이 붙은 필드(멤버변수)의 생성자를 자동 생성하는 어노테이션
public class DiaryRestController {
	
//	필드 주입
	@Autowired
	DiaryServiceImpl diaryService;
	
	@Autowired
	DiaryRepository diaryRepository;
	
	@PostMapping("/diary")
	public void insertDiary(@RequestBody DiaryDTO diaryDTO) {
		diaryService.insertDiary(diaryDTO);
	}
	
	@PostMapping("/diary-batch")
	public void insertDiaryBatch() {
		List<DiaryDTO> diaryDTOList = new ArrayList<>();
		IntStream.rangeClosed(1, 200).forEach(i -> {
			
			DiaryDTO diaryDTO = DiaryDTO.builder()
								.no(Long.valueOf(i))
								.title("Title : " + i)
								.content("Content : " + i)
								.build();
			diaryDTOList.add(diaryDTO);
//			diaryService.insertDiary(diaryDTO);
		});
		diaryService.insertDiaryBatch(diaryDTOList);
	}
	
	// 페이지 처리
	// Pagable 인터페이스 -> 구현체 PageRequest
	// Pageable pageable = new PageRequest(); X
	// 내부 static of 메소드를 사용 가능
	// of(int page, int size) : 페이지 번호(0부터 시작), 개수
	// of(int page, int size, Sort sort) : 페이지 번호, 개수, 정렬
	
	@GetMapping("/pageable")
	public void pageDefault() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Diary> result = diaryRepository.findAll(pageable);
		System.out.println(result);
		System.out.println("=====pageable result=====");
		// 총 페이지 수
		System.out.println(result.getTotalPages());
		// 전체 갯수
		System.out.println(result.getTotalElements());
		// 현재 페이지 번호: 0부터 시작
		System.out.println(result.getNumber());
		// 페이지당 데이터 개수
		System.out.println(result.getSize());
		// 다음페이지 여부
		System.out.println(result.hasNext());
		// 이전페이지 여부
		System.out.println(result.hasPrevious());
		// 모든 데이터
		System.out.println(result.getContent());
		
		
		// 정렬
		Sort sort1 = Sort.by("no").descending();
		Pageable pageable2 = PageRequest.of(0, 10, sort1);
		Page<Diary> result2 = diaryRepository.findAll(pageable2);
		System.out.println("=====pageable result Sort=====");
		result2.forEach(diary -> {
			System.out.println(diary);
		});

		System.out.println("=====pageable + QueryMethod=====");
		// QueryMethod + Pageable
		Pageable pageable3 = PageRequest.of(0, 10, Sort.by("no").descending());
		Page<Diary> result3 = diaryRepository.findByNoBetween(10L, 50L, pageable3);
		
		result3.get().forEach(diary -> System.out.println(diary));
		
		// 고려 사항
		// Entity -> DTO 전환
		// DTO -> Pagable
		// 페이지 번호
		// 페이지 요청 처리 DTO 생성
	
		System.out.println("=====Page Request DTO====");
		PageRequestDTO pageReuqestDTO = PageRequestDTO.builder()
										.page(1)
										.size(10)
										.build();
		
		PageResultDTO<DiaryDTO, Diary> pageResultDTO = diaryService.getList(pageReuqestDTO);
	
		System.out.println(pageResultDTO.isPrev());
		System.out.println(pageResultDTO.isNext());
		System.out.println(pageResultDTO.getTotalPage());
		
		System.out.println("===PageRequestDTO 객체값 출력(1번 페이지에 있는 내용만)===");
		pageResultDTO.getDtoList().forEach(resultDTO -> System.out.println(resultDTO));
	}
	
	@GetMapping("/diary")
	public void getDiary(PageRequestDTO pageReuqestDTO) {
		System.out.println(pageReuqestDTO);
		
		
		PageResultDTO<DiaryDTO, Diary> pageResultDTO = diaryService.getList(pageReuqestDTO);
		System.out.println(pageResultDTO.getPageList());
		System.out.println(pageResultDTO.getDtoList());
		System.out.println(pageResultDTO.getTotalPage());
		System.out.println(pageResultDTO.isNext());
		System.out.println(pageResultDTO.isPrev());
		System.out.println(pageResultDTO.getStart());
		System.out.println(pageResultDTO.getEnd());
	}
	
	
		

}
