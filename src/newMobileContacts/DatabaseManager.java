package newMobileContacts;

import java.util.Scanner;

public class DatabaseManager {
    private ContactController contactController;
    public DatabaseManager (ContactController contactController) {
        this.contactController = contactController;
    }
    public void addContactInteractive() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.println("Enter mobile:(+421, +420) ");
        String mobile = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        Contact newContact = new Contact(name,phone,mobile,email);
        boolean success = contactController.addContact(newContact);
        if (success) {
            System.out.println("Contact added succesfully");
        } else  {
            System.out.println("Failed to add contact. Please try again.");
        }
    }

    public void removeContactInteractive() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter mobile of contact to remove: ");
        String mobile = scanner.nextLine();

        boolean success = contactController.removeContactByMobile(mobile);
        if (success) {
            System.out.println("Contact removed successfully.");
        }else {
            System.out.println("Failed to remove contact.");
        }
    }
    public void searchContactInteractive() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of contact to search: ");
        String name = scanner.nextLine();

        boolean found = contactController.searchContact(name);
        if (!found) {
            System.out.println("No contacts found with name: " + name);
        }
    }
    public void updateContactInteractive() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the current mobile of the contact to update: ");
        String oldMobile = scanner.nextLine();

        System.out.println("Enter new name (or press Enter to skip): ");
        String newName = scanner.nextLine();
        System.out.println("Enter new phone number (or press Enter to skip): ");
        String newPhone = scanner.nextLine();
        System.out.println("Enter new mobile number (or press Enter to skip): ");
        String newMobile = scanner.nextLine();
        System.out.println("Enter new email (or press Enter to skip): ");
        String newEmail = scanner.nextLine();

        Contact newContact = new Contact(newName, newPhone, newMobile, newEmail);
        boolean success = contactController.updateContact(newContact);
        if (success) {
            System.out.println("Contact updated successfully.");
        } else {
            System.out.println("Failed to update contact.");
        }
    }
}
