package com.netease.roommates.vo;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	// 1 represents invitation, 2 represents reply of invitation,
	// 3 represents house message, 4 represents personal letter.
	private int type;
	private int receiver;
	private String typeName;
	private String description;
	private String content;
	private long timestamp;
	
	public Message() {
		
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message [type=" + type + ", receiver=" + receiver + ", typeName=" + typeName + ", description="
				+ description + ", content=" + content + ", timestamp=" + timestamp + "]";
	}
}
