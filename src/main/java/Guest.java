import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.naming.InvalidNameException;
import javax.naming.LimitExceededException;

public class Guest {
    private String name;
    private double balance;
    private String password;

    public Guest(String name, double balance, String password) {
        this.name = name;
        this.balance = balance;
        this.password = password;
    }

    public void guest() throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        while (name.length() < 3 || name.length() > 20 || name.matches("[a-zA-Z]+") == false) {
            System.out.print(ConsoleColors.RED);
            System.out.println("Name has to be at least 3 characters long and without numbers ");
            System.out.print(ConsoleColors.RESET);
            System.out.print("Enter your name again: ");
            name = scanner.nextLine();
        }
        System.out.print("Enter unique password: ");
        String password = scanner.nextLine();
        while (true) {
            if (password.length() <= 3) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Password must be at least 4 symbols long");
                System.out.print(ConsoleColors.RESET);
                System.out.print("Enter unique password again: ");
                password = scanner.nextLine();
            } else if (checkIfPasswordExists(name, password) == true) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Password already exists");
                System.out.print(ConsoleColors.RESET);
                System.out.print("Enter unique password again: ");
                password = scanner.nextLine();
            } else {
                break;
            }
        }
        Double balance = getBalanceFromExistingGuest(name, password);
        app.clearScreen();
        Guest guest = new Guest(name, balance, password);
        if (checkIfGuestExists(name, password) == false) {
            addGuest(guest);
        }
        Room.printRooms();
        System.out.println("\nWelcome " + name);
        showOptions();
        int option = 0;
        while (option != 5) {
            option = checkInput(1, 5);
            if (option == 5) {
                break;
            }
            checkOption(guest, option);
            Room.printRooms();
            System.out.println();
            showOptions();
        }
        app.clearScreen();
        System.out.println("See you soon");
    }

    // Shows available options
    public void showOptions() {
        System.out.println("Choose one of the option:");
        System.out.println("1) Book - book a room for a certain time period");
        System.out.println("2) Search and filter");
        System.out.println("3) Balance - check your balance or deposit money");
        System.out.println("4) See your booked rooms");
        System.out.println("5) Exit\n");
    }

    // Checks option user chose and does the requested function
    public void checkOption(Guest guest, int option) throws Exception {
        if (option == 1) {
            System.out.println("\n");
            book(guest);
        } else if (option == 2) {
            System.out.println("\n");
            showRooms();
        } else if (option == 3) {
            System.out.println("\n");
            checkBalance(guest);
        } else if (option == 4) {
            System.out.println("\n");
            checkBookedRooms(guest);
        }
    }

    // Book a room for a certain amount of nights
    public void book(Guest guest) throws Exception {
        Scanner scanner = new Scanner(System.in);
        app.clearScreen();
        Room.printRooms();
        System.out.println("\nEnter a room number to book it");
        System.out.println();
        String answer = "0";
        answer = String.valueOf(checkInput(1, 10));
        Boolean success = false;
        while (true) {
            if (Booking.checkIfBooked(answer, guest)) {
                break;
            }
            try {
                System.out.print("For how many nights?: ");
                int nights = Integer.valueOf(scanner.nextLine());
                if (nights <= 0) {
                    throw new InvalidNameException();
                }
                if (nights > 30) {
                    throw new LimitExceededException();
                }
                Booking book = new Booking(guest.getName(), answer, nights, guest.getPassword());
                System.out.printf("Total cost: %.2f\n", book.getTotalCost());
                if (book.getTotalCost() <= guest.getBalance()) {
                    Booking.addBook(book);
                    Double oldBalance = guest.getBalance();
                    guest.withdraw(book.getTotalCost());
                    updateBalance(guest, oldBalance);
                    success = true;
                } else {
                    app.clearScreen();
                    System.out.print(ConsoleColors.RED);
                    System.out.println("\nYou don't have enough money");
                    System.out.print(ConsoleColors.RESET);
                    success = false;
                }
                break;
            } catch(InvalidNameException e){
                System.out.print(ConsoleColors.RED);
                System.out.println("Night count has to be above 0");
                System.out.print(ConsoleColors.RESET);
            } catch(LimitExceededException e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Night count can't be over 30");
                System.out.print(ConsoleColors.RESET);
            } catch(Exception e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect night count input");
                System.out.print(ConsoleColors.RESET);
            }
        }
        if (success == true) {
            app.clearScreen();
            Room.updateRoom(answer, guest);
            System.out.print(ConsoleColors.GREEN);
            System.out.println("Successfuly booked a room");
            System.out.print(ConsoleColors.RESET);
        }
    }

    // Lets filter or search rooms by requirements
    public void showRooms() throws Exception {
        Scanner scanner = new Scanner(System.in);
        app.clearScreen();
        Room.printRooms();
        System.out.println();
        while (true) {
            System.out.println("Choose one of the options:");
            System.out.println("1) Sort");
            System.out.println("2) Search");
            System.out.println("3) Exit");
            int input = 0;
            input = checkInput(1, 3);
            if (input == 1) {
                System.out.println("\nSort by property:");
                System.out.println("1) Cost");
                System.out.println("2) Size");
                if ((input = checkInput(1, 2)) == 1) {
                    System.out.println("\n1) From the most expensive");
                    System.out.println("2) From the cheapest");
                    if ((input = checkInput(1, 2)) == 1) {
                        app.clearScreen();
                        Room.sortByHighestCost();
                    } else {
                        app.clearScreen();
                        Room.sortByLowestCost();
                    }
                } else if (input == 2) {
                    System.out.println("\n1) From the biggest");
                    System.out.println("2) From the smallest");
                    if ((input = checkInput(1, 2)) == 1) {
                        app.clearScreen();
                        Room.sortByBiggestSize();
                    } else {
                        app.clearScreen();
                        Room.sortBySmallestSize();
                    }
                }
            } else if (input == 2) {
                System.out.println("\nSearch by");
                System.out.println("1) Cost");
                System.out.println("2) Size");
                System.out.println("3) Discount");
                if ((input = checkInput(1, 3)) == 1) {
                    enterRangeOfCost();
                } else if (input == 2) {
                    enterRangeOfSize();
                } else {
                    enterRangeOfDiscount();
                }
            } else if (input == 3) {
                break;
            }
        }
        app.clearScreen();
    }

    // Lets to enter range of cost to show rooms
    public static void enterRangeOfCost() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\nEnter range of price (EUR)");
                System.out.print("From: ");
                int from = Integer.valueOf(scanner.nextLine());
                System.out.print("To: ");
                int to = Integer.valueOf(scanner.nextLine());
                if (from < 0 || to < 0) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Range values cannot be negative");
                    System.out.print(ConsoleColors.RESET);
                } else if (from > to) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Starting value cannot be lesser than end value");
                    System.out.print(ConsoleColors.RESET);
                } else {
                    app.clearScreen();
                    Room.searchByCost(from, to);
                    break;
                }
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect range value\n");
                System.out.print(ConsoleColors.RESET);
            }
        }
    }

    // Lets to enter range of room size to show rooms
    public static void enterRangeOfSize() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\nEnter range of size (m²)");
                System.out.print("From: ");
                int from = Integer.valueOf(scanner.nextLine());
                System.out.print("To: ");
                int to = Integer.valueOf(scanner.nextLine());
                if (from < 0 || to < 0) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Range values cannot be negative");
                    System.out.print(ConsoleColors.RESET);
                } else if (from > to) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Starting value cannot be lesser than end value");
                    System.out.print(ConsoleColors.RESET);
                } else {
                    app.clearScreen();
                    Room.searchBySize(from, to);
                    break;
                }
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect range value\n");
                System.out.print(ConsoleColors.RESET);
            }
        }
    }

    // Lets to enter range of discount to show rooms
    public static void enterRangeOfDiscount() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\nEnter range of discount (%)");
                System.out.print("From: ");
                int from = Integer.valueOf(scanner.nextLine());
                System.out.print("To: ");
                int to = Integer.valueOf(scanner.nextLine());
                if (from < 0 || to < 0) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Range values cannot be negative");
                    System.out.print(ConsoleColors.RESET);
                } else if (from > to) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Starting value cannot be lesser than end value");
                    System.out.print(ConsoleColors.RESET);
                } else {
                    app.clearScreen();
                    Room.searchByDiscount(from, to);
                    break;
                }
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect range value\n");
                System.out.print(ConsoleColors.RESET);
            }
        }
    }

    // Shows your balance and lets to interact with it
    public void checkBalance(Guest guest) throws Exception {
        Scanner scanner = new Scanner(System.in);
        app.clearScreen();
        int answer = 0;
        while (answer != 3) {
            Room.printRooms();
            System.out.printf("\nYour balance: %.2f EUR\n", guest.balance);
            System.out.println("\nWould you like to:");
            System.out.println("1) Deposit money");
            System.out.println("2) Withdraw money");
            System.out.println("3) Exit\n");
                try {
                    answer = 0;
                    answer = checkInput(1, 3);
                    Guest oldGuest = new Guest(guest.name, guest.balance, guest.password);
                    if (answer == 1) {
                        System.out.print("How much would you like to deposit?: ");
                        double deposit = Double.valueOf(scanner.nextLine());
                        guest.deposit(deposit);
                        Guest newGuest = new Guest(guest.name, guest.balance, guest.password);
                        updateBalance(newGuest, oldGuest.balance);
                    } else if (answer == 2) {
                        System.out.print("How much would you like to withdraw?: ");
                        double withdraw = Double.valueOf(scanner.nextLine());
                        guest.withdraw(withdraw);
                        Guest newGuest = new Guest(guest.name, guest.balance, guest.password);
                        updateBalance(newGuest, oldGuest.balance);
                    }
                } catch (Exception e) {
                    app.clearScreen();
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Incorrect input");
                    System.out.print(ConsoleColors.RESET);
                }
        }
        app.clearScreen();
    }

    // Shows your booked rooms if you have any
    public void checkBookedRooms(Guest guest) throws Exception{
        Scanner scanner = new Scanner(System.in);
        app.clearScreen();
        Booking.searchBooked(guest);
        System.out.println("1) Exit\n");
        int input = 0;
        input = checkInput(1, 1);
        app.clearScreen();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    // Adds money to balance
    public void deposit(double money) {
        if (money > 0.0) {
            app.clearScreen();
            System.out.print(ConsoleColors.GREEN);
            System.out.println("Successfully added money");
            System.out.print(ConsoleColors.RESET);
            balance += money;
        } else {
            app.clearScreen();
            System.out.print(ConsoleColors.RED);
            System.out.println("Input must be above 0.0");
            System.out.print(ConsoleColors.RESET);
        }
    }

    // Takes out money from balance
    public void withdraw(double money) {
        if ((balance - money) >= 0.0 && money > 0.0) {
            app.clearScreen();
            System.out.print(ConsoleColors.GREEN);
            System.out.println("Successfully withdrawn money");
            System.out.print(ConsoleColors.RESET);
            balance -= money;
        } else {
            app.clearScreen();
            System.out.print(ConsoleColors.RED);
            System.out.println("Incorrect withdraw value");
            System.out.print(ConsoleColors.RESET);
        }
    }

    // Checks if password already exists
    public static Boolean checkIfPasswordExists(String name, String password) throws Exception{
        HashMap<String, Guest> guestList = getGuestList();
        for(Guest guest: guestList.values()) {
            if (guest.getPassword().equals(password) && guest.getName().equals(name) == false) {
                return true;
            }
        }
        return false;
    }

    // Checks if guest already exists
    public static boolean checkIfGuestExists(String name, String password) throws Exception {
        HashMap<String, Guest> guestList = getGuestList();
        Boolean exists = false;
        for (Guest guest: guestList.values()) {
            if (guest.getPassword().equals(password) && guest.getName().equals(name)) {
                exists = true;
            }
        }

        return exists;
    }

    // Gets balance of guest if name and password are the same
    public static Double getBalanceFromExistingGuest(String name, String password) throws Exception{
        HashMap<String, Guest> guestList = getGuestList();
        Double balance = 0.0;
        for(Guest guest: guestList.values()) {
            if (guest.getPassword().equals(password) && guest.getName().equals(name)) {
                balance = guest.getBalance();
            }
        }
        return balance;
    }

    // Gets list of guests from guests.csv
    public static HashMap<String, Guest> getGuestList() throws Exception {
        BufferedReader reader = Helper.gerReader("guests.csv");

        HashMap<String, Guest> guestList = new HashMap<>();
        String line;

        reader.readLine();       
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");

            String name = parts[0];
            Double balance = Double.valueOf(parts[1]);
            String password = parts[2];

            Guest guest = new Guest(name, balance, password);
            guestList.put(password, guest);
        }
        return guestList;
    }
    
    public void addGuest(Guest guest) throws Exception {
        BufferedWriter writer =
        Helper.getWriter("guests.csv", StandardOpenOption.APPEND);
        writer.write(guestToCsvRow(guest));
        writer.close();
    }

    // Adds guest to guests.csv
    public void addGuest(String line) throws Exception{
        String[] parts = line.split(" ");
        
        String name = parts[0];
        double balance = Double.valueOf(parts[1]);
        String password = parts[2];
        Guest guest = new Guest(name, balance, password);
        addGuest(guest);
    }

    public static String guestToCsvRow(Guest guest) {
        return guest.name + ", " + guest.balance + ", " + guest.password + "\n";
    }

    // Checks if input is in the diapason
    public static int checkInput(int start, int end) {
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        while (true) {
            try {
                System.out.print("Input: ");
                input = Integer.valueOf(scanner.nextLine());
                if (start <= input && input <= end) {
                    break;
                }
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect input, try again");
                System.out.print(ConsoleColors.RESET);
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect input, try again");
                System.out.print(ConsoleColors.RESET);
            }
            if (start <= input && input <= end) {
                break;
            }
        }
        return input;
    }

    // Updates balance for a existing guest
    public static void updateBalance(Guest guest, double oldBalance) throws Exception{
        File oldFile = new File("/workspaces/Hotel_project_DPale/data/guests.csv");
        File tempFile = new File("/workspaces/Hotel_project_DPale/data/tempguests.csv");
        tempFile.createNewFile();
        
        BufferedReader reader = Helper.gerReader("guests.csv");
        BufferedWriter writer =

        Helper.getWriter("tempguests.csv", StandardOpenOption.APPEND);
        String linetoupdate = guest.name + ", " + oldBalance + ", " + guest.password;
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.equals(linetoupdate)) {
                writer.write(guest.name + ", " + guest.balance + ", " + guest.password + "\n");
                continue;
            }
            
            writer.write(currentLine + "\n");
            
        }
        writer.close(); 
        reader.close();
        tempFile.renameTo(oldFile);
    }
}