package com.goodkredit.myapplication.responses;

import com.goodkredit.myapplication.adapter.NotificationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban on 08/03/2018.
 */

public class GetNotifications {

    private List<NotificationItem> notificationList = new ArrayList<>();

    public List<NotificationItem> getNotificationList() {
        return notificationList;
    }
}
