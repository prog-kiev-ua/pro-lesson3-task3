package ua.kovalev.prolesson3task3;

import java.io.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        User user = new User("Сергей", "Иванов", 27, "Киев");
        String json = user.toJson();
        System.out.println(json);

        /*----- Сохраняю json в файл -----*/
        try(FileWriter fileWriter = new FileWriter("json.txt")){
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*----- Вычитываю json из файла -----*/
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("json.txt")))
        {
            json = bufferedReader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }

        User userNew = User.fromJson(json);
        System.out.println(userNew);

    }
}
