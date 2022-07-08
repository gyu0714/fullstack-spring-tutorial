package com.spring.service;

import java.util.List;

import com.spring.dto.FileDTO;

public interface FileService {
	
	public Long saveFile(FileDTO fileDTO);
	
	public List<FileDTO> getFiles();
	
}
