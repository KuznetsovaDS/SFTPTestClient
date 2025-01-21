package org.example;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    public static void menu(String localFilePath){
        Scanner scanner = new Scanner(System.in);
        Map<String,String> data = JsonProcessing.readJson(localFilePath);

        while (true){
            System.out.println("\nMake a choice");
            System.out.println("1. Getting a list of \"domain - address\" pairs from a file");
            System.out.println("2. Getting an IP address by domain name");
            System.out.println("3. Getting a domain name by IP address");
            System.out.println("4. Adding a new \"domain - address\" pair to a file");
            System.out.println("5. Deleting a \"domain - address\" pair by domain name or IP address");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    System.out.println("All pairs from a file");
                    data.forEach((domain, ip) -> System.out.println(domain + " " + ip));
                    break;

                case 2:
                    System.out.println("Put domain name");
                    String domainName = scanner.nextLine();
                    System.out.println(data.getOrDefault(domainName, "not found"));
                    break;

                case 3:
                    System.out.println("Put ip");
                    String ip = scanner.nextLine();
                    String findDomian = data.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue().equals(ip))
                            .map(Map.Entry::getKey)
                            .findFirst()
                            .orElse(null);
                    System.out.println(findDomian);
                    break;
                case 4:
                    System.out.println("Put new domain");
                    String newDomain = scanner.nextLine();
                    System.out.println("Put new ip");
                    String newIp = scanner.nextLine();

                    if(!validIp(newIp)){
                        System.out.println("wrong ip");
                    } else if (data.containsKey(newDomain)||(data.containsValue(newIp))) {
                        System.out.println("domain or ip already exist");
                    }else {
                        data.put(newDomain,newIp);
                        JsonProcessing.writeJson(localFilePath,data);
                        System.out.println("pair added");
                    }
                    break;

                case 5:
                    System.out.println("Deleting pair by domain name (1) or IP address (2)");
                    int deleteChoice = scanner.nextInt();
                    scanner.nextLine();
                    if(deleteChoice == 1){
                        System.out.println("put domain");
                        String delDomain = scanner.nextLine();
                        if(data.containsKey(delDomain)){
                            data.remove(delDomain);
                            JsonProcessing.writeJson(localFilePath, data);
                            System.out.println("pair deleted");
                        }
                        else {
                            System.out.println("not found");
                        }
                    } else if (deleteChoice==2) {
                        System.out.println("put ip");
                        String delIp = scanner.nextLine();
                        String keyToRemove = data.entrySet()
                                .stream()
                                .filter(entry->entry.getValue().equals(delIp))
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElse(null);
                        if(keyToRemove !=null){
                            data.remove(keyToRemove);
                            JsonProcessing.writeJson(localFilePath, data);
                            System.out.println("pair deleted");
                        }
                        else {
                            System.out.println("not found");
                        }
                    }
                    break;

                case 6:
                    System.out.println("Exit");
                    scanner.close();
                    return;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }
    private static boolean validIp(String ip){
        return ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b") &&
                Arrays.stream(ip.split("\\."))
                        .mapToInt(Integer::parseInt)
                        .allMatch(part-> part >= 0 && part <=255);
    }
}
