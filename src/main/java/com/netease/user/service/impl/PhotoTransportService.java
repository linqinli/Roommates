package com.netease.user.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.netease.user.service.FileService;

@Service
public class PhotoTransportService implements FileService {

	@Override
	public void fildUpload(String name, byte[] photo) throws IOException {
		BufferedOutputStream bos = null;
		try {
			System.out.println(name);
			bos = new BufferedOutputStream(new FileOutputStream(name));
			bos.write(photo);
			bos.flush();
		} finally {
			if (bos != null) {
				bos.close();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> fileDownload(int userId, String fileName) throws IOException {
	        File file=new File(fileName);
	        HttpHeaders headers = new HttpHeaders();    
	        headers.setContentDispositionFormData("attachment", file.getName());   
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
	                                          headers, HttpStatus.CREATED);   
	}

}
