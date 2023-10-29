package org.example;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.FileWriter;
import java.io.IOException;



public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Пожалуйста, введите свои данные в следующем формате:");
        System.out.println("[Фамилия] [Имя] [Отчество] [dd.mm.yyyy] [Номер телефона] [Пол (f/m)]");

        String input = sc.nextLine();
        String[] data = input.split(" ");

        if (data.length != 8) {
            System.err.println("Вы ввели неверные данные. Пожалуйста, попробуйте еще раз.");
            return;
        }

        String lastName = data[0];
        String firstName = data[1];
        String patronymic = data[2];
        String dateOfBirth = data[3];
        String phoneNumber = data[4];
        String gender = data[5];

        String dobPattern = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((19|20)\\d\\d)$";
        String phonePattern = "^\\d+$";
        String genderPattern = "^[fm]$";

        try {
            if (!Pattern.matches(dobPattern, dateOfBirth)) {
                throw new InvalidDateFormatException("Ваша дата рождения не совсем верна. Пожалуйста, введите в нужном формате dd.mm.yyyy");
            } else if (!Pattern.matches(phonePattern, phoneNumber)) {
                throw new InvalidPhoneNumberException("Ваш номер телефона не совсем верен. Убедитесь, что это все цифры без пробелов и других символов");
            } else if (!Pattern.matches(genderPattern, gender)) {
                throw new InvalidGenderException("Пол указан неправильно. Пожалуйста, введите «f» для женщин или «m» для мужчин.");
            } else {
                System.out.println("Данные успешно собраны.");


                try (FileWriter writer = new FileWriter(lastName + ".txt")) {
                    writer.write(lastName + " " + firstName + " " + patronymic + " " + dateOfBirth + " " + phoneNumber + " " + gender + "\n");
                    System.out.println("Данные успешно записаны в файл.");
                } catch (IOException e) {
                    System.err.println("Произошла ошибка при записи в файл: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}