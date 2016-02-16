import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Message {
    String id;
    String author;
    String timestamp;
    String message;

    Message() {
        id = "";
        author = "";
        timestamp = "";
        message = "";
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Long getTimestamp() {
        return Long.parseLong(timestamp);
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder subString = new StringBuilder();
        subString.append(getAuthor());
        subString.append(" [").append(timestamp).append("] ");
        subString.append("======> ");
        subString.append(getMessage() + "\n");
        return subString.toString();

    }

}

class MessagesList {
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
            for (Message message : messages) {
                System.out.println(message);
            }
        } else {
            System.out.println("There is no messages in previous dialogues. Please, add some new messages.");
        }
    }

    public void writeToJSON() throws IOException {
        if (messages.size() != 0) {
            Writer writer = new FileWriter("log.json");
            Gson gson = new GsonBuilder().create();
            gson.toJson(messages, writer);
            writer.close();
        } else {
            readFromJSON();
            writeToJSON();
        }
    }

    public void addNewMessage() throws IOException {
        if (messages.size() != 0) {
            Message newMessage = new Message();
            String inputData;
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter ID: ");
            if (sc.hasNextLine()) {
                inputData = sc.nextLine();
                newMessage.setId(inputData);
            }
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

    public void searchingAuthor() throws IOException {
        Scanner sc = new Scanner(System.in);
        String inputData;

        System.out.print("Enter searching author: ");
        if (sc.hasNextLine()) {
            inputData = sc.nextLine();
            boolean searchingCheck = false;
            if(messages.size() == 0) {
                readFromJSON();
            }
            for (int i = 0; i < messages.size(); i++) {
                if (inputData.compareTo(messages.get(i).getAuthor()) == 0) {
                    System.out.println(messages.get(i));
                    searchingCheck = true;
                }
            }
            if (!searchingCheck) {
                System.out.println("There is no messages from this user.");
            }
        }
    }

    public void searchingTime() throws IOException {
        Scanner sc = new Scanner(System.in);
        String str1;
        String str2;

        System.out.print("Enter diapason of searching by time (two numbers): ");
        if (sc.hasNextLine()) {
            str1 = sc.nextLine();
            str2 = sc.nextLine();
            Long lowerLimit = Long.parseLong(str1);
            Long upperLimit = Long.parseLong(str2);
            if(messages.size() == 0) {
                readFromJSON();
            }
            if (lowerLimit > upperLimit) {
                Long stepNumber = lowerLimit;
                lowerLimit = upperLimit;
                upperLimit = stepNumber;
            }

            boolean searchingCheck = false;
            for (int i = 0; i < messages.size(); i++) {
                if ((lowerLimit < messages.get(i).getTimestamp()) && (upperLimit > messages.get(i).getTimestamp())) {
                    System.out.println(messages.get(i));
                    searchingCheck = true;
                }
            }
            if (!searchingCheck) {
                System.out.println("There is no messages in this period.");
            }
        }
    }

}

public class Main {

    public static void main(String[] args) throws IOException {
        MessagesList getMessages = new MessagesList();
        System.out.println("Welcome to Chat prototype ver. 0.1");
        System.out.println("Enter 1 to show previous messages.");
        System.out.println("Enter 2 to add your own message.");
        System.out.println("Enter 3 to delete some of previous messages.");
        System.out.println("Enter 4 to find all messages of specific user.");
        System.out.println("Enter 5 to find all messages in some specific period of time.");
        System.out.println("Enter 0 to exit.");
        int choice = -1;
        Scanner sc = new Scanner(System.in);
        while (choice != 0) {
            System.out.print("Waiting for your commands: ");
            if (sc.hasNextLine()) {
                choice = sc.nextInt();
            }
            switch (choice) {
                case 1:
                    getMessages.readFromJSON();
                    getMessages.showMessages();
                    break;
                case 2:
                    getMessages.addNewMessage();
                    getMessages.writeToJSON();
                    break;
                case 3:
                    getMessages.deleteMessage();
                    getMessages.writeToJSON();
                    break;
                case 4:
                    getMessages.searchingAuthor();
                    break;
                case 5:
                    getMessages.searchingTime();
                    break;
                case 0:
                    getMessages.writeToJSON();
                    System.out.println("\nAll changes saved.");
                    System.out.println("See you later. Goodbye. ");
                default:
                    break;
            }
        }
    }
}

