package com.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.DiaryDTO;
import com.spring.service.DiaryServiceImpl;


@RestController
// 생성자 주입
//@RequiredArgsConstructor // fianl이 붙거나 @NotNull이 붙은 필드(멤버변수)의 생성자를 자동 생성하는 어노테이션
public class DiaryRestController {
	
//	필드 주입
	@Autowired
	DiaryServiceImpl diaryService;
	
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
}
