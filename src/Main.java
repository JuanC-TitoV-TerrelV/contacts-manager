import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Contact> contacts = new ArrayList<>();


        try {
//            writeTestFile();
            writeContactsToFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("IO Exception, fix your stuff!");
        } catch (Exception e) {  //if there was any other Exception being thrown it would catch it here
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            System.out.println("Something went wrong.!");
        }

        contacts = readAllContacts();
        for (Contact contact : contacts) {
            System.out.println(contact.getName());
            System.out.println(contact.getPhoneNumber());
        }

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


}

