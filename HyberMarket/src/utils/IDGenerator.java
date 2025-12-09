package utils;

public class IDGenerator {
    public static String generateUserId() {
        return "USR" + System.currentTimeMillis();
    }

    public static String generateProductId() {
        return "PROD" + System.currentTimeMillis();
    }

    public static String generateOrderId() {
        return "ORD" + System.currentTimeMillis();
    }

    public static String generateReportId() {
        return "REP" + System.currentTimeMillis();
    }
}
