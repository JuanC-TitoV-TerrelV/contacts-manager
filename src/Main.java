import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)  {

        try {
//            writeTestFile();
            writeContactsToFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("IO Exception, fix your stuff!");
        } catch(Exception e) {  //if there was any other Exception being thrown it would catch it here
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            System.out.println("Something went wrong.!");
        }

        readAllContacts();
    }

    public static void writeContactsToFile() throws IOException {
        String directory = "data";
        String filename = "contacts.txt";
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }

        if (! Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }

        ArrayList<String> contacts = new ArrayList<>();
        contacts.add("Juan Candia 915-471-5340");
        contacts.add("Tito Valiente 254-368-9782");
        contacts.add("Terrell Stewart 201-913-5865");


        //having append here adds to our file instead of overriding it
        Files.write(dataFile, contacts, StandardOpenOption.APPEND);
    }

    public static void readAllContacts() {
        String directory = "data";
        String filename = "contacts.txt";
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);
        List<String> contacts;

        try {
            contacts = Files.readAllLines(dataFile);
            //enhanced for loop to iterate our list of strings
            for(String contact : contacts) {
                System.out.println(contact);
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }


//    Example
//
//    Using a relative path to create a directory and file if they do not yet exist.
//    data/info.txt

//public static void writeTestFile() throws IOException {
//    String directory = "data";
//    String filename = "info.txt";
//    Path dataDirectory = Paths.get(directory);
//    Path dataFile = Paths.get(directory, filename);
//    if (Files.notExists(dataDirectory)) {
//        Files.createDirectories(dataDirectory);
//    }
//    if (! Files.exists(dataFile)) {
//        Files.createFile(dataFile);
//    }
//}
}

