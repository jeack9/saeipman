package com.saeipman.app.file.service;

import lombok.Data;

@Data
public class FileVO {
	private String fileId;
	private String filePath;
	private String fileName;
	private String fileType;
	private String tableName;
	private String fileSize;
	private String groupId;
}
