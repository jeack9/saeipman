package com.saeipman.app.admin.Service;

import java.util.Date;
import java.util.List;

import com.saeipman.app.file.service.FileVO;

import lombok.Data;

@Data
public class NoticeVO {
	private Integer postNo;
    private String title;
    private String content;
    private Date regDate;
    private Date modDate;
    private String groupId;   // 파일 첨부를 위한 그룹 ID
    private int views;
    private String writer;
    private String modWriter;
    private List<FileVO> files;
}
