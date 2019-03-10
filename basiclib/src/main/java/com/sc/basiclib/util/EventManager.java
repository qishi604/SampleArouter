package com.sc.basiclib.util;

import org.greenrobot.eventbus.EventBus;

/**
 *
 * Event bus 包装类
 *
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
public final class EventManager {

    private EventManager() {
        throw new UnsupportedOperationException("Can not instance");
    }

    public static void register(Object subscriber) {
        getDefault().register(subscriber);
    }

    public static void unRegister(Object subscriber) {
        getDefault().unregister(subscriber);
    }

    public static void post(Object event) {
        getDefault().post(event);
    }

    public static EventBus getDefault() {
        return EventBus.getDefault();
    }
}
