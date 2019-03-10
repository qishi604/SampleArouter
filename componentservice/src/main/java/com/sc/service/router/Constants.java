package com.sc.service.router;

/**
 * 路由常量
 *
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
public final class Constants {

    /**
     * 用户模块路径
     */
     public static final class USER {
        /**
         * 前缀，标识跟用户相关
         */
        public static final String PREFIX = "/user";
        /**
         * 登录
         */
        public static final String LOGIN = "/user/login";
        /**
         * 用户列表
         */
        public static final String LIST = "/user/list";
        /**
         * 用户详情
         */
        public static final String DETAIL = "/user/detail";
        /**
         * 选择用户
         */
        public static final String SELECT = "/user/select";
        /**
         * 返回一个用户列表 fragment
         */
        public static final String LIST_FRAGMENT = "/user/list-fragment";


    }

    /**
     * 用户模块路径
     */
     public static final class WEB {
        /**
         * 前缀，标识web相关
         */
        public static final String PREFIX = "/web";
        /**
         * 一般的web 页
         * @param url String
         */
        public static final String COMMON = "/web/common";
    }


}
