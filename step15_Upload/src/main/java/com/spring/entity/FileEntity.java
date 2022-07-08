package com.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spring.dto.FileDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "files")
public class FileEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "original_file_name", nullable = true)
	private String originalFileName;
	
	@Column(name = "file_name", nullable = true)
	private String fileName;
	
	@Column(name = "file_path", nullable = true)
	private String filePath;
	
	public FileDTO toDTO(FileEntity fileEntity) {
		FileDTO fileDTO = FileDTO.builder()
				.id(fileEntity.getId())
				.fileName(fileEntity.getFileName())
				.originalFileName(fileEntity.getOriginalFileName())
				.fileName(fileEntity.getFileName())
				.filePath(fileEntity.getFilePath())
				.build();
		
		return fileDTO;
	}
}
