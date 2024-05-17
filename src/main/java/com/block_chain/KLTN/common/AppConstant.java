package com.block_chain.KLTN.common;

import lombok.Getter;

public class AppConstant {
    @Getter
    public enum Roles {
        SUPER_ADMIN("super_admin", "Super Admin"),
        USER("user", "User"),
        EMPLOYEE("employee", "Employee");

        private String roleCode;
        private String roleName;

        Roles(String code, String name) {
            this.roleCode = code;
            this.roleName = name;
        }
    }
}
