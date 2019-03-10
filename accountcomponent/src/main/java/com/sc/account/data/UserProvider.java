package com.sc.account.data;

import com.sc.service.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
public class UserProvider {

    private static final String[] NAMES = {"James", "Kobe Bryant", "Michael Jordan", "梅西", "欧文", "哈登", "Lady Gaga"};
    private static final ArrayList<User> USERS;
    private static final Map<String, User> USER_MAP;

    static {
        final int len = NAMES.length;
        USERS = new ArrayList<>(len);
        USER_MAP = new HashMap<>();
        for (int i = 0; i < len; i++) {
            User user = new User();
            user.id = (10000 + i) + "";
            user.name = NAMES[i];
            USER_MAP.put(user.id, user);
            USERS.add(user);
        }
    }

    public static List<User> getUsers(final int count) {
        final int len = USERS.size();
        ArrayList<User> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            final int index = new Random().nextInt(len);
            list.add(USERS.get(index));
        }
        return list;
    }

    public static User getUser(String id) {
        return USER_MAP.get(id);
    }
}
