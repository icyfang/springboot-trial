package com.example.demo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FileConverter {

    static String target = "C:\\codeRepo\\";

    public static void main(String[] args) {

        toFile("C:\\Users\\尚宏\\Desktop\\src.txt");
    }

    public static void toText(String path) {

        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        String[] split = path.split("\\\\");
        String s = file.getPath() + "\\" + split[split.length - 1] + ".src.txt";
        File text = new File(s);
        if (text.exists()) {
            text.delete();
        }
        Collection<File> src = FileUtils.listFiles(file, new String[]{"java", "yml", "xml"}, true);
        src.forEach(i -> {
            if (i.getName().contains("MavenWrapperDownloader") || i.getAbsolutePath().contains(".idea")
                    || i.getName().contains("FileConverter")) {
                return;
            }
            try {
                List<String> utf8 = FileUtils.readLines(i, "UTF8");
                FileUtils.writeLines(text, Collections.singleton(i.getAbsolutePath()), true);
                FileUtils.writeLines(text, utf8, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public static void toFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        String replace = file.getName().replace(".txt", "").replace(".", "\\\\");

        File root = new File(target + replace);
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
                String fileName = i.replace("C:\\codeRepo", target);
                f = new File(fileName);
                if (f.exists()) {
                    f.delete();
                }
                System.out.println(f.getAbsolutePath());

            } else {
                try {
                    FileUtils.writeStringToFile(f, i + "\r\n", "utf8", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
