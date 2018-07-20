package com.vasyl.testtaskpecode.models;

import java.util.ArrayList;
import java.util.List;

public class FragmentData {
    private int id;

    List<Integer> notificationIdList;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getNotificationIdList() {
        if (notificationIdList == null)
            notificationIdList = new ArrayList<>();
        return notificationIdList;
    }

    public void setNotificationIdList(List<Integer> notificationIdList) {
        this.notificationIdList = notificationIdList;
    }
}
