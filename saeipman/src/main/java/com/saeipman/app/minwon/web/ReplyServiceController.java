package com.saeipman.app.minwon.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.saeipman.app.minwon.service.ReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class ReplyServiceController {
	
	private final ReplyService replyService;
	
	//댓글 리스트 출력
	@GetMapping( "/comment-list/{postNo}")
	@ResponseBody
	public Map<String, Object> commentList(@RequestParam("postNo") int postNo){
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
}
