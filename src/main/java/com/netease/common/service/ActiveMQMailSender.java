package com.netease.common.service;

import com.netease.roommates.vo.MailVO;

public interface ActiveMQMailSender {
	public void send(MailVO mail);
}
