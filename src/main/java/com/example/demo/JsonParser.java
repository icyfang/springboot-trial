package com.example.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class JsonParser {
    public static void main(String[] args) throws IOException {
        deduplication();
    }

    private static void generateTotal() throws IOException {
        File file = new File("D:\\codeRepo\\be-hk-pos-report-engine\\public\\cor\\epos\\products\\manuLeisure\\bi\\body.html");
        InputStream is = new FileInputStream(file);
        BufferedInputStream stream = new BufferedInputStream(is);
        byte[] b = new byte[(int) (file.length())];
        stream.read(b);
        String s = new String(b);
        Document document = Jsoup.parse(s);
        Elements children = document.body().children().first().children();
        List<Element> list = new ArrayList<>();
        File target = new File("D:\\codeRepo\\be-hk-pos-report-engine\\public\\cor\\epos\\products\\manuLeisure\\bi\\total.html");
        if (target.exists()) {
            target.delete();
        }
        Set<String> list1 = new HashSet<>();
        BufferedOutputStream fo = new BufferedOutputStream(new FileOutputStream("D:\\codeRepo\\be-hk-pos-report-engine\\public\\cor\\epos\\products\\manuLeisure\\bi\\total.html"));
        Consumer<Element> elementConsumer = i -> {
            String id = i.attr("id");
//            System.out.println("<!--"+id+"-->");
            try {
                fo.write(("<!--" + id + "-->").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s2 = getString(id);
            JSONObject jo = JSONObject.parseObject(s2);
            JSONArray sections = jo.getJSONArray("sections");

            for (int j = 0; j < sections.size(); j++) {
                JSONObject jsonObject = (JSONObject) (sections.get(j));
                String section = (String) jsonObject.get("section");
                try {
                    fo.write(("\n<!--" + section + "-->\n").getBytes());
                    list1.add(section);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File newFile = new File("D:\\codeRepo\\be-hk-pos-report-engine\\public\\cor\\epos\\products\\manuLeisure\\bi\\sections\\" + section);
                try {
                    fo.write(Jsoup.parse(newFile, "UTF8").toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };

        children.forEach(elementConsumer);
//        list1.stream().filter(i1->!i1.startsWith("..")).sorted().forEach(System.out::println);
        fo.flush();
        fo.close();
    }

    private static String getString(String id) {
        File jsonFile = new File(id);
        InputStream is2 = null;
        try {
            is2 = new FileInputStream(jsonFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedInputStream stream2 = new BufferedInputStream(is2);
        byte[] b2 = new byte[(int) (jsonFile.length())];
        try {
            stream2.read(b2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(b2);
    }

    private static void deduplication() {

        JSONObject jo = JSONObject.parseObject(getString("D:\\codeRepo\\be-hk-pos-report-engine\\public\\cor\\epos\\products\\manuLeisure\\bi\\languages\\en.json"));
        JSONObject sections = jo.getJSONObject("values");
        JSONObject jo1 = JSONObject.parseObject(getString("D:\\codeRepo\\be-hk-pos-report-engine\\public\\cor\\epos\\products\\manuLeisure\\bi\\languages\\zh.json"));
        JSONObject sections1 = jo1.getJSONObject("values");
        List<String> collect1 = sections1.entrySet().stream().map(i -> i.getKey()).collect(Collectors.toList());
        List<String> collect = sections.entrySet().stream().map(i -> i.getKey()).collect(Collectors.toList());

        String string = getString("D:\\codeRepo\\be-hk-pos-report-engine\\public\\cor\\epos\\products\\manuLeisure\\bi\\total.html");
        String string1 = getString("D:\\codeRepo\\be-hk-pos-report-engine\\public\\cor\\epos\\products\\manuLeisure\\bi\\js\\product.js");
        List<String> strings = collect.stream().filter(i -> !string.contains(i) && !string1.contains(i)).collect(Collectors.toList());
        List<String> strings1 = collect1.stream().filter(i -> !string.contains(i) && !string1.contains(i)).collect(Collectors.toList());
        strings.forEach(i -> System.out.println(i));
        strings1.forEach(i -> System.out.println(i));


    }

}
