package com.spring.dto;

import javax.persistence.Id;

import com.spring.entity.Diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiaryDTO {

	@Id
	private Long no;
	
	private String title;
	
	private String content;
	
	public Diary diaryToEntity(DiaryDTO diaryDTO) {
		Diary diaryEntity = Diary.builder()
							.no(diaryDTO.getNo())
							.title(diaryDTO.getTitle())
							.content(diaryDTO.getContent())
							.build();
		
		return diaryEntity;
	}
	
}
