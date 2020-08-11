package com.example.demo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UnitJavaUtils {

    public static void main(String[] args) {
//        toText("D:\\CodeRepo\\GitHub\\springboot-trial\\spring-mvc");
        toFile("D:\\CodeRepo\\GitHub\\springboot-trial\\spring-mvc\\spring-mvc.src.txt");
    }

    public static void toText(String path) {

        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        String[] split = path.split("\\\\");
        String s = file.getPath() + "\\"+split[split.length-1]+".src.txt";
        File text = new File(s);
        if (text.exists()) {
            text.delete();
        }
        Collection<File> src = FileUtils.listFiles(file, new String[] {"java"}, true);
        src.forEach(i -> {
            try {
                List<String> utf8 = FileUtils.readLines(i, "UTF8");
                FileUtils.writeLines(text, Collections.singleton(i.getAbsolutePath()), true);
                FileUtils.writeLines(text,utf8,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        src.forEach(i -> System.out.println(i.getAbsolutePath()));
    }

    public static void toFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        String replace = file.getName().replace(".txt", "").replace(".", "\\\\");

        File root = new File("C:\\Users\\尚宏\\Desktop\\" + replace);
        if (!root.exists()) {
            root.mkdir();
        }
        List<String> utf8 = new ArrayList<>();

        try {
            utf8 = FileUtils.readLines(file, "UTF8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File f = null;
        for (String i : utf8) {

            if (i.startsWith("C:\\codeRepo")) {
                String fileName = i.replace("C:\\codeRepo", "C:\\Users\\尚宏\\Desktop");
                f = new File(fileName);
                if (f.exists()) {
                    f.delete();
                }
                System.out.println(f.getAbsolutePath());

            } else  {
                try {
                    FileUtils.writeStringToFile(f, i+"\n", "utf8",true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
