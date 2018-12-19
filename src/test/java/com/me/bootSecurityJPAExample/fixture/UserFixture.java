package com.me.bootSecurityJPAExample.fixture;

import com.me.bootSecurityJPAExample.domain.User;


/**
 * A fixture class for creating {@link User} test data.
 */
public class UserFixture {

    /**
     * Creates an instance from {@link User}.
     *
     * @param loginId User login ID
     * @param password User password
     * @return A new {@link User} object
     */
    public static User constructTestUser(String loginId, String password) {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .createdBy("creator")
                .updatedBy("creator")
                .build();
    }
}
