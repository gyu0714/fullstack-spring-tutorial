package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.entity.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
	
}
