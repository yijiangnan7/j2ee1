package com.example.demo2.listener;

import com.mybatisflex.annotation.SetListener;

public class AccountCryptListener implements SetListener {

    @Override
    public int order() {
        return 1;
    }

    @Override
    public Object onSet(Object entity, String property, Object value) {
        if (isUserName(property)) {

            if (value != null) {
                return ((String) value).codePoints()
                        .mapToObj(code -> "\\u" + code)
                        .reduce(String::concat)
                        .orElse("");
            }

            return null;
        }

        if (isPassword(property)) {
            return "解密：" + value;
        }

        // 别忘记返回其他属性的值！！！
        return value;
    }

    public boolean isUserName(String property) {
        return "userName".equals(property);
    }

    public boolean isPassword(String property) {
        return "password".equals(property);
    }

}
