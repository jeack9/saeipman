package com.saeipman.app.upload.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUtility {
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	public String upload(MultipartFile[] files) {
		List<String> imgList = new ArrayList<>();

		log.info(uploadPath);

		for (MultipartFile file : files) {
			log.info(file.getContentType());
			log.info(file.getOriginalFilename());
			log.info(String.valueOf(file.getSize()));

			String fileName = file.getOriginalFilename();
			String saveName = uploadPath + File.separator + fileName; // separator = 자바가 인식하는 경로

			log.debug("saveName : " + saveName);

			Path savePath = Paths.get(saveName);

			try {
				file.transferTo(savePath);
				imgList.add(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return String.join(":", imgList);
	}
	
}
