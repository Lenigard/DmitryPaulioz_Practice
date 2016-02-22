package com.company.consoleInterace;

import com.company.messageList.MessagesList;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by user on 22.02.2016.
 */
public class Interface {

    MessagesList messages;

    public Interface() {
        messages = new MessagesList();
    }

    public void showInterface() throws IOException {
        System.out.println("Welcome to Chat prototype ver. 0.1");
        System.out.println("Enter 1 to show previous messages.");
        System.out.println("Enter 2 to add your own message.");
        System.out.println("Enter 3 to delete some of previous messages.");
        System.out.println("Enter 4 to find all messages in some period of time.");
        System.out.println("Enter 0 to exit.");
        int choice = -1;
        messages.readFromJSON();
        Scanner sc = new Scanner(System.in);
        while(choice != 0) {
            System.out.print("Waiting for your commands: ");
            if (sc.hasNextLine()) {
                choice = sc.nextInt();
            }
            switch (choice) {
                case 1:
                    messages.showMessages();
                    break;
                case 2:
                    messages.addNewMessage();
                    break;
                case 3:
                    messages.deleteMessage();
                    break;
                case 4:
                    messages.findByTime();
                    break;
                case 0:
                    messages.writeToJSON();
                    System.out.println("\nAll changes saved.");
                    System.out.println("See you later. Goodbye. ");
                default:
                    break;
            }
        }
    }
}
