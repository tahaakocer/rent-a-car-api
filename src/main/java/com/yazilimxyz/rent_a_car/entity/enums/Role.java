package com.yazilimxyz.rent_a_car.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER( "USER"),

    ROLE_ADMIN("ADMIN");
    private String value;

    Role(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
    @Override
    public String getAuthority() {
        return name();
    }
}
