package ua.kovalev.prolesson3task3;

import com.google.gson.Gson;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class User {
    @Save
    private String name;
    @Save
    private String surName;
    @Save
    private Integer age;
    
    private String address;
    
    public User(){
        super();
    }
    
    public User(String name, String surName, int age, String address){
        super();
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString(){
        return String.format("User: [name: %s, surName: %s, age: %d, address: %s]", 
                              this.name, this.surName, this.age, this.address);
    }

    public String toJson(){
        Class<?> cl = this.getClass();

        Map<String, Object> map = new HashMap<>();
        Field[] fields = cl.getDeclaredFields();

        for(Field field : fields){
            if(field.getAnnotation(Save.class) != null)
            {
                try {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return new Gson().toJson(map);
    }

    public static User fromJson(String json){
        User user = null;
        Map<String, Object> map = new Gson().fromJson(json, HashMap.class);
        try {
            Class<?> clUser = User.class;
            Constructor<?> constructorEmpty = clUser.getConstructor();
            user = (User) constructorEmpty.newInstance();
            Field[] fields = clUser.getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                if(map.get(field.getName()) != null){
                    if (field.getType() == Integer.class){
                        field.set(user, ((Double)map.get(field.getName())).intValue());
                    }
                    if (field.getType() == String.class){
                        field.set(user, map.get(field.getName()));
                    }
                }
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return user;
    }
}
