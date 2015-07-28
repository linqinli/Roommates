package com.netease.roommates.po;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BatchPhotoModel {

	private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public BatchPhotoModel(List<MultipartFile> files) {
		super();
		this.files = files;
	}

	public BatchPhotoModel() {
		super();
	}
}
