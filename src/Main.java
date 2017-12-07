import util.Input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        List<Contact> contacts = new ArrayList<>();
        Input input = new Input();
        int userChoice;

        writeContactsToFile();

        contacts = readAllContacts();

        do {
            showMenu();
            System.out.println("Enter your choice: ");
            userChoice = input.getInt(1, 5);

            if (userChoice == 1) {
                showAll(contacts);
            } else if (userChoice == 2) {
                do {
                    addContact(contacts);
                } while (input.yesNo("Do you want to add another? y/n"));
            } else if (userChoice == 3) {
                searchContact(contacts);
            } else if (userChoice == 4) {
                deleteContact(contacts);
            }

            System.out.println();
        } while (!(userChoice == 5));
        updateFile(contacts);
        System.out.println("Bye");
    }



    public static void writeContactsToFile() {
        String directory = "data";
        String filename = "contacts.txt";
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);
        List<String> contactsStrings = new ArrayList<>();

        if (Files.notExists(dataDirectory)) {
            try {
                Files.createDirectories(dataDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Files.exists(dataFile)) {
            try {
                Files.createFile(dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            contactsStrings = Files.readAllLines(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contactsStrings.size() == 0) {
            contactsStrings.add("Another name,9154715340");
            contactsStrings.add("Some Else,2543689782");
            contactsStrings.add("Mickey Mouse,2019135865");
            try {
                Files.write(dataFile, contactsStrings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String capitalize(String text){
        String c = (text != null)? text.trim() : "";
        String[] words = c.split(" ");
        String result = "";
        for(String w : words){
            result += (w.length() > 1? w.substring(0, 1).toUpperCase(Locale.US) + w.substring(1, w.length()).toLowerCase(Locale.US) : w) + " ";
        }
        return result.trim();
    }


    public static List<Contact> readAllContacts() {
        String directory = "data";
        String filename = "contacts.txt";
        Path dataFile = Paths.get(directory, filename);
        List<String> contactsStrings = new ArrayList<>();
        List<Contact> listOfContacts = new ArrayList<>();
        String name, phoneNumber;

        try {
            contactsStrings = Files.readAllLines(dataFile);

            for (String person : contactsStrings) {
                name = person.substring(0, person.indexOf(","));
                phoneNumber = person.substring(person.indexOf(",") + 1);
                Contact contactObject = new Contact(name, phoneNumber);
                listOfContacts.add(contactObject);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return listOfContacts;
    }

    public static void showMenu() {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name;");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.println("(1, 2,3 4, or 5):");
    }

    public static void showAll(List<Contact> contacts) {

        int width = 20;

        System.out.printf("%-" + width + "s| ", "Name");
        System.out.printf("%-" + width + "s|%n ", "Phone number");
        System.out.println("------------------------------------------");

        for (Contact contact : contacts) {
            System.out.printf("%-" + width + "s| ", contact.getName());
            System.out.printf("(" + contact.getPhoneNumber().substring(0, 3) + ")");
            System.out.printf(contact.getPhoneNumber().substring(3, 6));
            System.out.printf("-" + contact.getPhoneNumber().substring(6));
            System.out.printf("       |\n");
        }
    }

    public static void addContact(List<Contact> contacts) {
        boolean alreadyExists;
        Input input = new Input();
        String nameInput = input.getAlphaOnly("Enter contact's name.");
        nameInput = capitalize(nameInput);
        String phoneNumberInput = input.getString(10, "Enter contact's phone number.");
        Contact contactToAdd = new Contact(nameInput, phoneNumberInput);

        alreadyExists = checkForExisting(contacts, nameInput);

        if (alreadyExists) {
            Boolean yesResponse = input.yesNo("Contact already exists. Do you wish to continue?");
            if(yesResponse) {
                contacts.add(contactToAdd);
                System.out.println("Contact added.");
                displayContact(contactToAdd);
            } else {
                System.out.println("Contact not added.");
                displayContact(contactToAdd);
            }
        } else {
            contacts.add(contactToAdd);
            System.out.println("Contact added.");
            displayContact(contactToAdd);
        }
    }

    public static Boolean checkForExisting(List<Contact> contacts, String nameInput) {

        boolean found = false;

        for(Contact contact: contacts) {
            if(contact.getName().equalsIgnoreCase(nameInput) ) {
                found = true;
            }
        }
        return found;

    }

    public static void displayContact(Contact contact) {
        System.out.println(contact.getName() + " " + contact.getPhoneNumber());
    }

    public static void deleteContact(List<Contact> contacts) {
        System.out.println("Enter contact's name to delete");
        Input input = new Input();
        String nameInput = input.getString();
        boolean found = false;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equalsIgnoreCase(nameInput)) {
                System.out.println("Contact Removed");
                displayContact(contacts.get(i));
                contacts.remove(i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Contact not found.");
        }
    }


    public static void searchContact(List<Contact> contacts) {

        System.out.println("Enter contact's name to search");
        Input input = new Input();
        String nameInput = input.getString();
        boolean found = false;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equalsIgnoreCase(nameInput)) {
                System.out.println(contacts.get(i).getName());
                System.out.println(contacts.get(i).getPhoneNumber());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Contact not found.");
        }
    }

    public static void updateFile(List<Contact> contacts) {

        String directory = "data";
        String filename = "contacts.txt";
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        if (Files.notExists(dataDirectory)) {
            try {
                Files.createDirectories(dataDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Files.exists(dataFile)) {
            try {
                Files.createFile(dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ArrayList<String> contactsStrings = new ArrayList<>();

        for (int i = 0; i < contacts.size(); i++) {
            String outputString = contacts.get(i).getName() + "," + contacts.get(i).getPhoneNumber();
            contactsStrings.add(outputString);
        }
        try {
            Files.write(dataFile, contactsStrings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

