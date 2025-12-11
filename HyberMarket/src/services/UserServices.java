package services;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.User;

public class UserServices {
    public void updateUserInfo(User user) {
    File f = new File("C:\\Users\\hp\\Desktop\\user services\\user.txt");
    if (!f.exists()) {
        System.out.println("File doesn't exist");
        return;
    }

    List<String> lines = new ArrayList<>();

    try (Scanner input = new Scanner(f)) {
        while (input.hasNextLine()) {
            String line = input.nextLine();

            // لو السطر فيه نفس الـ ID
            if (line.contains("userID=\"" + user.getId() + "\"") || line.contains("id=\"" + user.getId() + "\"")) {

                // نحدث باقي البيانات
                line = line.replaceAll("name=\"[^\"]*\"", "name=\"" + user.getName() + "\"");
                line = line.replaceAll("email=\"[^\"]*\"", "email=\"" + user.getEmail() + "\"");
                line = line.replaceAll("password=\"[^\"]*\"", "password=\"" + user.getPassword() + "\"");
                line = line.replaceAll("role=\"[^\"]*\"", "role=\"" + user.getRole() + "\"");
            }

            lines.add(line);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return;
    }

    // كتابة الملف بعد التعديل
    try (PrintWriter writer = new PrintWriter(f)) {
        for (String l : lines) {
            writer.println(l);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public boolean changeUsername(String userID, String newName){
        File f = new File("C:\\Users\\hp\\Desktop\\user services\\user.txt");

    if (!f.exists()) {
        System.out.println("This file doesn't exist");
        return false;
    }

    List<String> lines = new ArrayList<>();
    boolean updated = false;

    try (Scanner input = new Scanner(f)) {
        while (input.hasNextLine()) {
            String line = input.nextLine();

            // لو السطر فيه userID أو id اللي بندور عليه
            if (line.contains("userID=\"" + userID + "\"") || line.contains("id=\"" + userID + "\"")) {
                line = line.replaceAll("name=\"[^\"]*\"", "name=\"" + newName + "\"");
                updated = true;
            }

            lines.add(line);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }

    // اكتب الملف بعد التعديل
    if (updated) {
        try (PrintWriter writer = new PrintWriter(f)) {
            for (String l : lines) {
                writer.println(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    return updated;
    }

    public boolean changeEmail(String userID, String newEmail){
         File f = new File("C:\\Users\\hp\\Desktop\\user services\\user.txt");

    if (!f.exists()) {
        System.out.println("This file doesn't exist");
        return false;
    }
    List<String> lines = new ArrayList<>();
    boolean updated = false;

    try (Scanner input = new Scanner(f)) {
        while (input.hasNextLine()) {
            String line = input.nextLine();

            // لو السطر فيه userID أو id اللي بندور عليه
            if (line.contains("userID=\"" + userID + "\"") || line.contains("id=\"" + userID + "\"")) {
                line = line.replaceAll("email=\"[^\"]*\"", "email=\"" + newEmail + "\"");
                updated = true;
            }

            lines.add(line);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }

    // اكتب الملف بعد التعديل
    if (updated) {
        try (PrintWriter writer = new PrintWriter(f)) {
            for (String l : lines) {
                writer.println(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    return updated;
    }

    public boolean changePassword(String userID, String oldPassword, String newPassword){

    }
}
