package com.example.demo2.listener;

import com.example.demo2.entity.Account;
import com.mybatisflex.annotation.SetListener;

public class AccountDictListener implements SetListener {

    @Override
    public Object onSet(Object entity, String property, Object value) {
        if (isGender(property)) {

            Account account = (Account) entity;

            // noinspection AlibabaSwitchStatement
            switch ((Integer) value) {
                case 0 : account.setGenderName("女");
                case 1 : account.setGenderName("男");
                default:  account.setGenderName("未知");
            }

        }

        // 别忘记返回其他属性的值！！！
        return value;
    }

    private boolean isGender(String property) {
        return "gender".equals(property);
    }

}
