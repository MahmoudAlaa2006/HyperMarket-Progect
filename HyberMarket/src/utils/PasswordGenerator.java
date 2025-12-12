package utils;

public class PasswordGenerator {
    public static String generatePassword(){
         return "PSW" + System.currentTimeMillis();
    }
}