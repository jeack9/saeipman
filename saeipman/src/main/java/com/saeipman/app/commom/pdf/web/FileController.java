package com.saeipman.app.commom.pdf.web;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {
	 private final String fileBasePath = "C:/springupload/계약서";

	    @GetMapping("/contract/pdf")
	    public ResponseEntity<Resource> viewPdf(@RequestParam(name = "fileName") String fileName) {
	        try {
	            Path filePath = Paths.get(fileBasePath + fileName).toAbsolutePath().normalize();
	            Resource resource = new UrlResource(filePath.toUri());

	            if (resource.exists()) {
	                return ResponseEntity.ok()
	                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
	                        .body(resource);
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (MalformedURLException ex) {
	            return ResponseEntity.badRequest().build();
	        }
	    }
}
