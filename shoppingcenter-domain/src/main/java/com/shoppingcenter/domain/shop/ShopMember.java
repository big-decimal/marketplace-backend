package com.shoppingcenter.domain.shop;

import com.shoppingcenter.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMember {

    public enum Role {
        OWNER, ADMIN
    }

    private long userId;

    private long shopId;

    private Role role;

    private User member;

    public ShopMember() {
        this.role = Role.ADMIN;
    }
}