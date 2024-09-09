package com.saeipman.app.upload.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.saeipman.app.file.service.FileService;
import com.saeipman.app.file.service.FileVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUtility {
	
	private FileService fileService;
	public FileUtility(FileService fileService) {
		this.fileService = fileService;
	}
	@Value("${file.upload.path}")
	private String uploadPath;
	@Value("${naver.service.url}")
	@Setter
	private String folder; 
	
	public String upload(MultipartFile[] files) {
		List<String> imgList = new ArrayList<>();
		FileVO fileVO = new FileVO();
		
		log.info(uploadPath);

		//폴더 경로
		String folderPath = makeFolder(this.folder);
		//group_id
		String group = fileService.fileGroupId(fileVO);
		fileVO.setGroupId(group);
		
		for (MultipartFile file : files) {
				log.info(file.getContentType());
				log.info(file.getOriginalFilename());
				log.info(String.valueOf(file.getSize()));
				if(file.getOriginalFilename() == "") {
					return "-1";
				}
				
				String fileName = file.getOriginalFilename();
				String uuid = UUID.randomUUID().toString();
				String uploadFolder = folderPath + "/" + uuid + "_" + fileName;
				String saveName = uploadPath + uploadFolder; // separator = 자바가 인식하는 경로
				
				fileVO.setFileName(uuid+"_"+fileName);
				fileVO.setFileSize(String.valueOf(file.getSize()));
				fileVO.setFilePath(uploadPath+folderPath);
				fileVO.setTableName(folderPath);
				int index = file.getOriginalFilename().indexOf(".");
				if(index > 1) {
					String fileType = file.getOriginalFilename().substring(index + 1);
					fileVO.setFileType(fileType);
				}
				
				log.debug("saveName : " + saveName);
				
				Path savePath = Paths.get(saveName);
				
				try {
					file.transferTo(savePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				fileService.fileInsert(fileVO);
				imgList.add(setImgPath(uploadFolder));
			} // for END
		return group;
	}
	public String makeFolder(String folder) {
		String folderPath = folder;
		File uploadPathFoler = new File(uploadPath, folderPath);
		
		if(uploadPathFoler.exists() == false) {
			uploadPathFoler.mkdirs();
		}
		return folderPath;
	}
	
	public String setImgPath(String uploadFileName) {
		return uploadFileName.replace(File.separator, "/");
	}
}
