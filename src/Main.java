import util.Input;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Contact> contacts = new ArrayList<>();
        Input input = new Input();
        String userChoice;

//        try {
////            writeTestFile();
//            writeContactsToFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//            System.out.println("IO Exception, fix your stuff!");
//        } catch (Exception e) {  //if there was any other Exception being thrown it would catch it here
//            System.out.println(e.getStackTrace());
//            System.out.println(e.getMessage());
//            System.out.println("Something went wrong.!");
//        }

        contacts = readAllContacts();
//        for (Contact contact : contacts) {
//            System.out.println(contact.getName());
//            System.out.println(contact.getPhoneNumber());
//        }



        do {
            showMenu();
            System.out.println("Enter your choice: ");
            userChoice = input.getString();

            if (userChoice.equals("1")) {
                showAll(contacts);
            } else if (userChoice.equals("2")) {
                addContact(contacts);
            } else if(userChoice.equals("3")) {
                searchContact(contacts);
            } else if(userChoice.equals("4")) {
                deleteContact(contacts);
            }

            System.out.println();

        } while(!userChoice.equals("5"));
        updateFile(contacts);
        System.out.println("Bye");


    }

    public static void writeContactsToFile() throws IOException {
        String directory = "data";
        String filename = "contacts.txt";
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }

        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }

        ArrayList<String> contacts = new ArrayList<>();
        contacts.add("Another name,915-471-5340");
        contacts.add("Some Else,254-368-9782");
        contacts.add("Mickey Mouse,201-913-5865");


        //having append here adds to our file instead of overriding it
//        Files.write(dataFile, contacts, StandardOpenOption.APPEND);
        Files.write(dataFile, contacts);

    }

    public static List<Contact> readAllContacts() {
        String directory = "data";
        String filename = "contacts.txt";
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);
        List<String> contacts = new ArrayList<>();
        List<Contact> listOfContacts = new ArrayList<>();
        String name, phoneNumber;


        try {
            contacts = Files.readAllLines(dataFile);

//            enhanced for loop to iterate our list of strings
            for (String person : contacts) {
//                listOfContacts.name.add(person);
//                System.out.println(person.substring(0,person.indexOf(",")));
//                System.out.println(person.substring(person.indexOf(",")+1));
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

    public static void showMenu(){
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name;");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.println("(1, 2,3 4, or 5):");
    }

    public static void showAll(List<Contact> contacts) {
//        for (Contact contact : contacts) {
//            System.out.println(contact.getName());
//            System.out.println(contact.getPhoneNumber());
//        }
        int width = 20;

        System.out.printf("%-" + width + "s| ", "Name");
        System.out.printf("%-" + width + "s|%n ", "Phone number");
        System.out.println("------------------------------------------");

        for (Contact contact : contacts) {
            System.out.printf("%-" + width + "s| ", contact.getName());
            System.out.printf("%-" + width + "s| %n", contact.getPhoneNumber());
        }
    }

    public static void addContact(List<Contact> contacts){
        System.out.println("Enter contact's name.");
        Input input = new Input();
        String nameInput = input.getString();
        System.out.println("Enter contact's phone number.");
        String phoneNumberInput = input.getString();

        Contact contactToAdd = new Contact(nameInput, phoneNumberInput);
        contacts.add(contactToAdd);
    }

    public static void deleteContact(List<Contact> contacts){
        System.out.println("Enter contact's name to delete");
        Input input = new Input();
        String nameInput = input.getString();

        for(int i = 0; i < contacts.size(); i++) {
           if (contacts.get(i).getName().equals(nameInput)){
               System.out.println("Contact Removed");
               contacts.remove(i);
           }
        }
    }

    public static void searchContact(List<Contact> contacts) {

        System.out.println("Enter contact's name to search");
        Input input = new Input();
        String nameInput = input.getString();

        for(int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equals(nameInput)){
                System.out.println(contacts.get(i).getName());
                System.out.println(contacts.get(i).getPhoneNumber());
            }
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

        for(int i = 0; i < contacts.size(); i++) {
           String outputString = contacts.get(i).getName() + "," + contacts.get(i).getPhoneNumber();
            contactsStrings.add(outputString);
        }
           try {
                Files.write(dataFile, contactsStrings);
           } catch (IOException e){
                e.printStackTrace();
           }



    }

}

