package com.company.messageList;

import com.company.message.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MessagesList {
    private List<Message> messages;
    private ObjectMapper mapper;


    public MessagesList() {
        messages = new LinkedList<>();
        mapper = new ObjectMapper();
    }


    public void readFromJSON() throws IOException {
        byte[] jsonData = new byte[0];
        jsonData = Files.readAllBytes(Paths.get("log.json"));
        messages = mapper.readValue(jsonData, new TypeReference<List<Message>>() {
        });
    }

    public void showMessages() throws IOException {
        if (messages.size() != 0) {
            for (int i = 0; i < messages.size(); i++) {
                System.out.println(messages.get(i));
            }
        } else {
            System.out.println("There is no messages in previous dialogues. Please, add some new messages.");
        }
    }

    public void findByTime() {
        Long lowerLimit = new Long(0);
        Long upperLimit = new Long(0);
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter lower time period of required searching: ");
        if (sc.hasNextLong()) {
            lowerLimit = sc.nextLong();
        }
        System.out.print("Enter upper time period of required searching: ");
        if (sc.hasNextLong()) {
            upperLimit = sc.nextLong();
        }

        Long meesageTime;
        for(int i = 0; i < messages.size(); i++) {
            meesageTime = Long.parseLong(messages.get(i).getTimestamp());
            if (meesageTime > lowerLimit && meesageTime < upperLimit) {
                System.out.println(messages.get(i));
            }

        }
    }

    public void writeToJSON() throws IOException {
        if (messages.size() != 0) {
            Writer writer = new FileWriter("log.json");
            Gson gson = new GsonBuilder().create();
            gson.toJson(messages, writer);
            writer.close();
        }
    }

    public void addNewMessage() throws IOException {
        if (messages.size() != 0) {
            Message newMessage = new Message();
            String inputData;
            Scanner sc = new Scanner(System.in);

            UUID newID = UUID.randomUUID();
            newMessage.setId(newID.toString());
            System.out.print("Enter name: ");
            if (sc.hasNextLine()) {
                inputData = sc.nextLine();
                newMessage.setAuthor(inputData);
            }
            Date date = new Date();
            Long time = (long) (((((date.getHours() * 60) + date.getMinutes()) * 60) + date.getSeconds()) * 1000);
            newMessage.setTimestamp(time.toString());
            System.out.print("Enter message: ");
            if (sc.hasNextLine()) {
                inputData = sc.nextLine();
                newMessage.setMessage(inputData);
            }
            messages.add(newMessage);
        } else {
            readFromJSON();
            addNewMessage();
        }
    }

    public void deleteMessage() {
        Scanner sc = new Scanner(System.in);
        String inputData;

        System.out.print("Enter searching ID: ");
        if (sc.hasNextLine()) {
            inputData = sc.nextLine();
            boolean deletingCheck = false;
            for (int i = 0; i < messages.size() && !deletingCheck; i++) {
                if (inputData.compareTo(messages.get(i).getId()) == 0) {
                    messages.remove(i);
                    deletingCheck = true;
                    System.out.println("Successfully removed.");
                }
            }
            if (deletingCheck == false) {
                System.out.println("There is no message with the same ID.");
            }
        }
    }
}