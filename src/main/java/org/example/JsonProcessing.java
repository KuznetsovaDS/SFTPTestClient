package org.example;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class JsonProcessing {
    public static Map<String,String> readJson(String filePath){

        Map<String, String> data = new TreeMap<>();

        try (
                BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line.trim());
            }
            String json = stringBuilder.toString().trim();
            if (!json.startsWith("{") || !json.endsWith("}")) {
                System.err.println("Invalid JSON format");
                return data;
            }
            int startIndex = json.indexOf("\"addresses\":");
            if (startIndex == -1) {
                System.err.println("Key 'addresses' not found in JSON");
                return data;
            }
            startIndex = json.indexOf("[", startIndex);
            int endIndex = json.indexOf("]", startIndex);
            if (startIndex == -1 || endIndex == -1) {
                System.err.println("Invalid JSON structure for 'addresses'");
                return data;
            }

            String addressesArray = json.substring(startIndex + 1, endIndex).trim();

            String[] entries = addressesArray.split("},\\{");

            for (String entry : entries) {
                entry = entry.replace("{", "")
                        .replace("}", "")
                        .replace("\"", "")
                        .trim();
                String[] keyValuePairs = entry.split(",");

                String domain  = null;
                String ip = null;
                for (String pair : keyValuePairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        if (key.equals("domain")) {
                            domain = value;
                        } else if (key.equals("ip")) {
                            ip = value;
                        }
                    }
                }
                if(domain!= null && ip !=null){
                    data.put(domain,ip);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
    public static void writeJson(String filePath, Map<String, String> data){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{\n");
            stringBuilder.append("  \"addresses\": [\n");
            int size = data.size();
            int i = 0;
            for (Map.Entry<String, String> entry : data.entrySet()) {
                stringBuilder.append(" {\n");
                stringBuilder.append(" \"domain\": \"").append(entry.getKey()).append("\",\n");
                stringBuilder.append(" \"ip\": \"").append(entry.getValue()).append("\"\n");
                stringBuilder.append(" }");
                if (++i < size) {
                    stringBuilder.append(",");
                }
                stringBuilder.append("\n");
            }
            stringBuilder.append(" ]\n");
            stringBuilder.append("}");
            writer.write(stringBuilder.toString());
        } catch (IOException e) {
            System.err.println("Errors with writing");
        }
    }
}
