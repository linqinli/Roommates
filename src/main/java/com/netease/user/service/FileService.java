package com.netease.user.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

public interface FileService {
	public void fildUpload(String name, byte[] file) throws IOException;
	
	public ResponseEntity<byte[]> fileDownload(int userId, String fileName) throws IOException;
}
