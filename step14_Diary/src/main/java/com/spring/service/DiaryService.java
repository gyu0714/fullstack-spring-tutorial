package com.spring.service;

import java.util.List;

import com.spring.dto.DiaryDTO;

public interface DiaryService {
	
	public void insertDiary(DiaryDTO diary);
	
	public void insertDiaryBatch(List<DiaryDTO> diaryDTOList);
}
