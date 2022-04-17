package com.thssh.file;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by hutianhang on 2022/4/2
 */
public class DayParse {

    public static void main(String[] args) {
        Gson gson = new Gson();
        File file = new File("/Users/hutianhang/Downloads/thssh.day");
        if (file.exists()) {
            FileReader reader = null;
            BufferedReader bufferedReader = null;
            Model result = null;
            try {
                reader = new FileReader(file);
                bufferedReader = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                result = new Model();
                result.data = gson.fromJson(sb.toString(), List.class);
            } catch (IOException e) {
                e.printStackTrace();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            Objects.requireNonNull(result).data.forEach(item -> System.out.println(item.toString()));
        } else {
            System.out.println("file NOT exists!");
        }
    }
}
