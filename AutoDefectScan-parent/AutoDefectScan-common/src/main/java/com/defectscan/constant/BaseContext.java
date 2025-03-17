package com.defectscan.constant;

public class BaseContext {

    private static ThreadLocal<String> currentUsername = new ThreadLocal<>();
    private static ThreadLocal<String> currentPassword = new ThreadLocal<>();
    private static ThreadLocal<String> currentType = new ThreadLocal<>();

    public static void setCurrentUsername(String username) {
        currentUsername.set(username);
    }

    public static void setCurrentPassword(String password) {
        currentPassword.set(password);
    }

    public static void setCurrentType(String type) {
        currentType.set(type);
    }

    public static String getCurrentUsername() {
        return currentUsername.get();
    }

    public static String getCurrentPassword() {
        return currentPassword.get();
    }

    public static String getCurrentType() {
        return currentType.get();
    }

    public static void removeCurrentUsername() {
        currentUsername.remove();
    }

    public static void removeCurrentPassword() {
        currentPassword.remove();
    }

    public static void removeCurrentType() {
        currentType.remove();
    }
}
