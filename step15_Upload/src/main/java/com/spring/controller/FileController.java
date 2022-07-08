package com.spring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.FileDTO;
import com.spring.entity.FileEntity;
import com.spring.repository.FileRepository;
import com.spring.service.FileServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileController {

	private final FileServiceImpl fileService;
	
	private final FileRepository fileRepository;

	@PostMapping("/file-save-test")
	public void saveFileTest(@RequestParam("file") MultipartFile multiFile) {
		try {
			// 파일 저장 시 이름 구분
			String originalFileName = multiFile.getOriginalFilename();
			String fileName = UUID.randomUUID().toString() + "-" + originalFileName;

			// 파일 저장시 경로 설정
			System.out.println(System.getProperty("user.dir"));
			String filePath = System.getProperty("user.dir") + "\\files";

			// 저장폴더가 존재 하지 않을 경우 -> 반드시 생성해줘야함
			if (!new File(filePath).exists()) {
				new File(filePath).mkdir();
			} else {
				String finalFilePath = filePath + "\\" + fileName;
				System.out.println(finalFilePath);
				multiFile.transferTo(new File(finalFilePath));
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/file-save")
	public ResponseEntity saveFile(@RequestParam("file") MultipartFile multiFile) {
		try {
			// 파일 저장 시 이름 구분
			String originalFileName = multiFile.getOriginalFilename();
			String fileName = UUID.randomUUID().toString() + "-" + originalFileName;

			// 파일 저장시 경로 설정
			System.out.println(System.getProperty("user.dir"));
			String filePath = System.getProperty("user.dir") + "\\files";

			// 저장폴더가 존재 하지 않을 경우 -> 반드시 생성해줘야함
			if (!new File(filePath).exists()) {
				new File(filePath).mkdir();
			} 
			FileDTO fileDTO = FileDTO.builder()
					.originalFileName(originalFileName)
					.fileName(fileName)
					.filePath(filePath)
					.build();
					
				String finalFilePath = filePath + "\\" + fileName;
				System.out.println(finalFilePath);
				multiFile.transferTo(new File(finalFilePath));
				Long fileId = fileService.saveFile(fileDTO);
			
//			List<FileDTO> fileList = fileService.getFiles();
//			System.out.println(fileList);
			
			/*
			 * boardDTO.setFileDTO(fileDTO);
			 * boardDTO.setFileId(fileId);
			 * boardServiceImpl.saveBoard(boardDTO);
			 */
				return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:3000")).body(fileId);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/file-list") 
	public List<FileEntity> showFileList() {
	  return fileRepository.findAll();
	}
	
	@GetMapping("/file-download/{id}")
	public void downloadFile(@PathVariable Long id, HttpServletResponse response) throws FileNotFoundException{
		FileEntity file = fileRepository.findById(id).get();
		
		FileInputStream fis = new FileInputStream(file.getFilePath());
		try {
			OutputStream os = response.getOutputStream();
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while((readCount = fis.read(buffer)) != -1) {
				os.write(buffer, 0, readCount);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
