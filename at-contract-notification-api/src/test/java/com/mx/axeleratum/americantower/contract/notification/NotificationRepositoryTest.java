package com.mx.axeleratum.americantower.contract.notification;

import com.mx.axeleratum.americantower.contract.core.model.Notification;
import com.mx.axeleratum.americantower.contract.core.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class NotificationRepositoryTest {
    @Autowired
    NotificationRepository notificationRepository;




    @Test
    public void testDeleteAll() {
        notificationRepository.deleteAll();
        log.info("notificacion deleted: ");
    }


    @Test
    public void testFindNotificationsReceivedByUserName() {
        @SuppressWarnings("unused")
		List<Notification> notificationList = notificationRepository.findByReceiverUsernameAndStatusOrderByCreatedDateDesc("userRevisor", Notification.STATUS_CREATED);
        log.info("notificacion leida: ");
    }


}
