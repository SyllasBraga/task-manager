package br.com.task_manager.model;

public enum Role {
    USER("user");

    private final String roleType;

    Role(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleType(){
        return roleType;
    }
}
