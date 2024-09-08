package com.saeipman.app.minwon.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.saeipman.app.minwon.service.ReplyService;
import com.saeipman.app.minwon.service.ReplyVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class ReplyServiceController {
	
	private final ReplyService replyService;
	
	
	//댓글 리스트 출력(댓글 계층이 0)
	@GetMapping( "/comment-list/{postNo}")
	@ResponseBody
	public Map<String, Object> commentList(@RequestParam("postNo") Long postNo){
		
	    Map<String, Object> map = new HashMap<String, Object>();
	      
	    try {
	        //댓글 리스트 가져오기
	        List<ReplyVO> comments = replyService.listCmt(postNo);
	         
	        //가져온 리스트 데이터 Map에 담기
	        map.put("status", "success");
	        map.put("comments", comments);
	    } catch(Exception e) {
	    	
	        // 예외 발생 시 오류 메시지
	        map.put("status", "error");
	        map.put("message", "오류가 발생했습니다.");
	    }
	        return map;
	}
	   
    // 특정 부모 댓글의 대댓글(댓글 계층이 1인 댓글) 가져오기
    @GetMapping("/reply-list/{parentCmtNo}")
    public Map<String, Object> getReplyList(@PathVariable("parentCmtNo") Long parentCmtNo) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 부모 댓글에 대한 대댓글 리스트 가져오기
            List<ReplyVO> replies = replyService.getReplyList(parentCmtNo);

            // Map에 결과 저장
            map.put("status", "success");
            map.put("replies", replies);
        } catch (Exception e) {
            // 예외 발생 시 오류 메시지 저장
        	map.put("status", "error");
        	map.put("message", "오류가 발생했습니다.");
        }

        // Map 반환
        return map;
    }
    
    // 댓글 등록 (POST 요청 처리)
    @PostMapping("/comments/new")
    public ResponseEntity<String> addComment(@RequestBody ReplyVO reply) {
        try {
            // 서비스 레이어를 통해 댓글을 저장
            replyService.insertCmt(reply); 
            
            // 성공적으로 댓글이 저장되면 "success" 메시지 반환
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            // 예외 발생 시 "error" 메시지 반환
            return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
        }
    }
    
    // 댓글 삭제 (DELETE 요청 처리)
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId) {
        try {
            // 서비스 레이어를 통해 댓글 삭제
            replyService.deleteCmt(commentId);

            // 성공 시 "success" 메시지 반환
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            // 예외 발생 시 "error" 메시지 반환
            return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
        }
    }
}
