package net.eventsandtickets.eventandtickets.dto;

public class LoginResponse {

    private boolean success;
    private String message;
    private String role;
    private String token;
    private Long userId;

    public LoginResponse(boolean success, String message, String role, String token, Long userId) {
        this.success = success;
        this.message = message;
        this.role = role;
        this.token = token;
        this.userId = userId;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getRole() { return role; }
    public String getToken() { return token; }
    public Long getUserId() { return userId; }
}