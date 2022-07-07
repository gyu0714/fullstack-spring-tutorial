package com.spring.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.spring.dto.DiaryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Diary implements Persistable<Long> {

	@Id
	private Long no;
	
	private String title;
	
	private String content;
	
	@CreatedDate
	private LocalDateTime writtenTime;

	@Override
	public Long getId() {
		return no;
	}

	@Override
	public boolean isNew() {
		return writtenTime == null;
	}

	public DiaryDTO toDTO(Diary diaryEntity) {
		DiaryDTO diaryDTO = DiaryDTO.builder()
							.no(diaryEntity.getNo())
							.title(diaryEntity.getTitle())
							.content(diaryEntity.getTitle())
							.writtenTime(diaryEntity.getWrittenTime() )
							.build();
		return diaryDTO;
	}
}
