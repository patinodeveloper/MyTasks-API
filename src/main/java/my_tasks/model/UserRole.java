package my_tasks.model;

public enum UserRole {
    ADMIN,
    USER;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
