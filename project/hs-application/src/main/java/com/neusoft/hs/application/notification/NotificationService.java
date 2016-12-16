package com.neusoft.hs.application.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.notification.domain.Notification;
import com.neusoft.hs.application.notification.domain.NotificationRepo;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class NotificationService {

	@Autowired
	private NotificationRepo notificationRepo;

	public void notify(NotificationVO notificationVO) {

		List<Notification> notifications = new ArrayList<Notification>();
		Notification notification;

		for (String receiver : notificationVO.getReceivers()) {
			notification = new Notification();

			notification.setCreateDate(DateUtil.getSysDate());
			notification.setMessage(notificationVO.getMessage());
			notification.setSender(notificationVO.getSourceId());
			notification.setState(Notification.unRead);
			notification.setReceiver(receiver);

			notifications.add(notification);
		}

		notificationRepo.save(notifications);

	}
}
