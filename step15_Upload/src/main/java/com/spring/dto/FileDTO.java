package com.spring.dto;


import com.spring.entity.FileEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {
	private Long id;
	private String originalFileName;
	private String fileName;
	private String filePath;
	
	public FileEntity toEntity(FileDTO fileDTO) {
		FileEntity fileEntity = FileEntity.builder()
				.id(fileDTO.getId())
				.fileName(fileDTO.getFileName())
				.originalFileName(fileDTO.getOriginalFileName())
				.fileName(fileDTO.getFileName())
				.filePath(fileDTO.getFilePath())
				.build();
		
		return fileEntity;
	}
}
