package newMobileContacts;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Tento riadok nastavuje cestu k súboru, ktorý bude používaný na uchovávanie a čítanie kontaktov.
        String filePath = "contactsDatabase/contactsList.txt";
        //Tento riadok inicializuje ContactController, ktorý bude spravovať čítanie a zápis kontaktov do súboru.
        ContactController contactController = new ContactController(filePath);
        //inicializuje DatabaseManager, ktorý bude poskytovať interaktívne rozhranie pre pridávanie a ďalšie operácie s kontaktmi.
        DatabaseManager databaseManager = new DatabaseManager(contactController);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Choose an option: ");
            System.out.println("1. Add Contact");
            System.out.println("2. Remove Contact");
            System.out.println("3. Search Contact");
            System.out.println("4. Update Contact");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();//cisti vstupny buffer

            switch (choice) {
                case 1:
                    databaseManager.addContactInteractive();
                    break;
                case 2:
                    databaseManager.removeContactInteractive();
                    break;
                case 3:
                    databaseManager.searchContactInteractive();
                    break;
                case 4:
                    databaseManager.updateContactInteractive();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again");
            }
        }
        scanner.close();
    }
}
