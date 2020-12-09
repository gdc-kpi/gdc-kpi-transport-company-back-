package com.ip737.transportcompany.transportcompany.configs.scheduler;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.dao.OrderDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import static javax.management.timer.Timer.ONE_DAY;
import static javax.management.timer.Timer.ONE_SECOND;

@Slf4j
@Component
@EnableScheduling
public class OrdersScheduler {
    private OrderDao orderDao;

    @Autowired
    public OrdersScheduler(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Scheduled(fixedDelay = ONE_SECOND * 300)
    public void deleteUnacceptedUsers() {
        orderDao.reassignUnconfirmedOrders();
        log.info(Constants.SCHEDULER_INFO);
    }

}
