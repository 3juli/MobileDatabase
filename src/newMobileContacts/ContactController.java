package newMobileContacts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ContactController {
    private Path path;//tato premenna uchovava cestu k suboru, kde su ulozene kontakty

    public ContactController(String filePath) {
        this.path = Paths.get(filePath);
        initializeFile(); //Volá metódu initializeFile, ktorá zabezpečí, že súbor je inicializovaný správne.
    }

    private void initializeFile() {
        try {
            //skontroluje ci subor na ceste 'path' existuje
            if (!Files.exists(path)) {
                //ak neexistuje, vytvory novy subor s hlavickami a pociatocnymi udajmi
                List<String> initialLines = List.of(
                    "Contact Database",
                    "0",
                    "Name; Phone; Mobile; Email"
                );
                Files.write(path, initialLines);//Zápis zoznamu initialLines do súboru na ceste path.
                System.out.println("File initialized with default headers.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addContact(Contact contact) {
        try {
            // Precita existujuce kontakty zo suboru
            List<String> lines = Files.readAllLines(path);

            if(lines.isEmpty()) {//kontrola,ci subor je prazdny. Ak ano, prida pociatocne riadky
                lines.add("Contact Database");
                lines.add("0");
                lines.add("Name; Phone; Mobile; Email");
            }

            // Overuje ci je email platny, ak nie vypise chybove hlasenie a vrati 'false'
            if (!Utils.isValidEmail(contact.getEmail())) {
                System.out.println("Invalid email: " + contact.getEmail());
                return false;
            }
            //Overuje, či je mobilné číslo platné, ak nie je prázdne. Ak nie, vypíše chybové hlásenie a vráti false
            if (!contact.getMobile().isEmpty() && !Utils.isValidMobile(contact.getMobile())) {
                System.out.println("Invalid mobile: " + contact.getMobile());
                return false;
            }

            //  Skontroluje, či už kontakt existuje v súbore. Ak áno, vypíše hlásenie a vráti false.
            if (existContact(lines, contact)) {//prechádza všetkými riadkami v zozname lines a porovnáva každý riadok s textovým predstavovaním contact (contact.toString()).
                System.out.println("Contact already exists: " + contact.toString());
                return false;
            }
            // Pridanie noveho kontaktu
            lines.add(contact.toString());

            // Aktualizacia poctu kontaktov
            int count = Integer.parseInt(lines.get(1)) + 1;
            lines.set(1, String.valueOf(count));//Nastaví nový počet kontaktov na druhý riadok.

            // Zapisanie aktualizovanych udajov do suboru
            Files.write(path, lines);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean existContact(List<String> lines, Contact contact) {
        for (String line : lines) {
            if (line.equals(contact.toString())) {//Porovnáva každý riadok s reťazcom reprezentujúcim kontakt.
                return true;//Ak nájde zhodu, vráti true.
            }
        }
        return false;// Ak nenájde zhodu, vráti false
    }
    public boolean removeContactByMobile(String mobile) {
        try {
            //precita vsetku riadku zo suboru
            List<String> lines = Files.readAllLines(path);

            //vytvori novy zoznam, do ktoreho prida len tie riadky, kt.sa nezhoduju s kontaktom na odstranenie
            List<String> updateLines = new ArrayList<>();
            boolean contactFound = false;

            for (String line : lines) {
                //Ak sa nájde riadok s daným mobilným číslom, nastaví contactFound na true.
                if (line.contains(mobile)) {
                    contactFound = true;
                } else {
                    updateLines.add(line);
                }
            }
            //Ak sa nenašiel kontakt s daným mobilným číslom, vypíše chybovú správu a vráti false.
            if (!contactFound) {
                System.out.println("Contact not found: " + mobile);
                return false;
            }
            //aktualizuje pocet kontaktov v subore
            int count = Integer.parseInt(updateLines.get(1)) -1;
            updateLines.set(1, String.valueOf(count));

            //zapise aktualizovane udaje spat do suboru
            Files.write(path,updateLines);
            return true;


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean searchContact(String name) {
        try {
            List<String> lines = Files.readAllLines(path);

            boolean found = false;
            for (String line : lines) {
                if (line.startsWith(name + ";")) {
                    System.out.println("Found contact: " + line);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Contact not found.");
            }
            return found;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateContact(Contact contact) {
        try {
            List<String> lines = Files.readAllLines(path);

            // Skontrolujeme platnosť nového mobilného čísla a e-mailu
            if (!Utils.isValidMobile(contact.getMobile())) {
                System.out.println("Invalid new mobile number: " + contact.getMobile());
                return false;
            }
            if (!Utils.isValidEmail(contact.getEmail())) {
                System.out.println("Invalid new email address: " + contact.getEmail());
                return false;
            }

            // Pripravíme zoznam riadkov, ktoré budeme aktualizovať
            List<String> updatedLines = new ArrayList<>();
            boolean contactFound = false;

            for (String line : lines) {
                if (line.contains(contact.getMobile())) {
                    // Ak nájdeme kontakt so starým mobilným číslom, aktualizujeme ho
                    String[] parts = line.split(";");
                    if (parts.length == 4) {
                        // Aktualizujeme jednotlivé polia
                        if (!contact.getName().isEmpty()) parts[0] = contact.getName();
                        if (!contact.getPhone().isEmpty()) parts[1] = contact.getPhone();
                        if (!contact.getMobile().isEmpty()) parts[2] = contact.getMobile();
                        if (!contact.getEmail().isEmpty()) parts[3] = contact.getEmail();

                        updatedLines.add(String.join("; ", parts));
                        contactFound = true;
                    }
                } else {
                    updatedLines.add(line);
                }
            }

            if (!contactFound) {
                System.out.println("Contact not found with mobile: " + contact.getMobile());
                return false;
            }

            // Aktualizujeme počet kontaktov
            int count = Integer.parseInt(updatedLines.get(1));
            updatedLines.set(1, String.valueOf(count));

            // Zapíšeme aktualizované údaje späť do súboru
            Files.write(path, updatedLines);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
