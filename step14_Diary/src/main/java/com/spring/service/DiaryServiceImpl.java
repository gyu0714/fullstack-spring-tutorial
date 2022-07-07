package com.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.dto.DiaryDTO;
import com.spring.dto.PageRequestDTO;
import com.spring.dto.PageResultDTO;
import com.spring.entity.Diary;
import com.spring.repository.DiaryRepository;

@Service
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	DiaryRepository diaryRepository;
	
	@Transactional
	@Override
	public void insertDiary(DiaryDTO diaryDTO) {
		Diary diaryEntity = diaryDTO.diaryToEntity(diaryDTO);
		
		diaryRepository.save(diaryEntity);
	}

	@Transactional
	@Override
	public void insertDiaryBatch(List<DiaryDTO> diaryDTOList) {
		
//		List<Diary> diaryEntityList = new ArrayList();
		// version 1
//		for(DiaryDTO diaryDTO : diaryDTOList) {
//			diaryEntityList.add(diaryDTO.diaryToEntity(diaryDTO));
//		}
		
		// version 2
//		diaryDTOList.forEach(diaryDTO -> diaryEntityList.add(diaryDTO.diaryToEntity(diaryDTO)));
		
		// version 3
		List<Diary> diaryEntityList = diaryDTOList.stream()
									.map(diaryDTO -> diaryDTO.diaryToEntity(diaryDTO))
									.collect(Collectors.toList());
		
		
		diaryRepository.saveAll(diaryEntityList);
		
	}

	@Override
	public PageResultDTO<DiaryDTO, Diary> getList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = pageRequestDTO.getPageable(Sort.by("no").descending());
		
		Page<Diary> result = diaryRepository.findAll(pageable);
		
		// Entity -> DTO
		Function<Diary, DiaryDTO> function = (diaryEntity -> diaryEntity.toDTO(diaryEntity));
		
		return new PageResultDTO<DiaryDTO, Diary>(result, function);
	}

	
	

}
