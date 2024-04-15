package com.example.demo2.listener;

import com.mybatisflex.annotation.SetListener;

public class AccountPermissionListener implements SetListener {

    @Override
    public Object onSet(Object entity, String property, Object value) {
        // 只有 张三 用户才能读出 密码 信息
        if (isPassword(property)) {

            // 当前用户
            String currUser = "张三";

            boolean hasPermission = hasPermission(currUser);

            if (hasPermission) {
                return value;
            }

            return null;
        }

        // 别忘记返回其他属性的值！！！
        return value;
    }

    public boolean isPassword(String property) {
        return "password".equals(property);
    }

    public boolean hasPermission(String currUser) {
        return "张三".equals(currUser);
    }

}