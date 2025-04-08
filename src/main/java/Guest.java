import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class Guest {
    private String name;
    private double balance;

    public Guest(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void guest() throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            while (name.length() < 3 || name.matches("[a-zA-Z]+") == false) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Name has to be at least 3 characters long and without numbers ");
                System.out.print(ConsoleColors.RESET);
                System.out.print("Enter your name again: ");
                name = scanner.nextLine();
            }
            Guest guest = new Guest(name, 0.0);
            addGuest(guest);
            System.out.println("\nWelcome " + name);
            showOptions();
            System.out.print("Input: ");
            int option = Integer.valueOf(scanner.nextLine());
            while (option != 4) {
                checkOption(guest, option);
                showOptions();
                System.out.print("Input: ");
                option = Integer.valueOf(scanner.nextLine());
            }
    }

    public void showOptions() {
        System.out.println("Choose one of the option:");
        System.out.println("1) Book - book a room for a certain time period");
        System.out.println("2) Rooms - shows all rooms");
        System.out.println("3) Balance - check your balance or deposit money");
        System.out.println("4) Exit\n");
    }

    public void checkOption(Guest guest, int option) throws Exception {
        Scanner scanner = new Scanner(System.in);
        if (option == 1) {
            book(guest);
        } else if (option == 2) {
            System.out.println("\n");
            showRooms();
            System.out.println("\n");
        } else if (option == 3) {
            checkBalance(guest);
        }
    }

    public void book(Guest guest) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Room.printRooms();
        System.out.print("Enter a room number to book it: ");
        String answer = scanner.nextLine();
        Room.updateRoom(answer, guest);
    }

    public void showRooms() throws Exception {
        Scanner scanner = new Scanner(System.in);
        Room.printRooms();
        System.out.println("1) Exit");
        System.out.println("Input: ");
        int input = Integer.valueOf(scanner.nextLine());
        checkInput(input, 1, 1);
    }

    public void checkBalance(Guest guest) {
        Scanner scanner = new Scanner(System.in);
        int answer = 0;
        while (answer != 3) {
            System.out.println("\nYour balance: " + guest.balance + " EUR");
            System.out.println("\nWould you like to:");
            System.out.println("1) Deposit money");
            System.out.println("2) Withdraw money");
            System.out.println("3) Exit\n");
            System.out.print("input: ");
                try {
                    answer = Integer.valueOf(scanner.nextLine());
                    checkInput(answer, 1, 3);
                    Guest oldGuest = new Guest(guest.name, guest.balance);
                    if (answer == 1) {
                        System.out.print("How much would you like to deposit?: ");
                        double deposit = Double.valueOf(scanner.nextLine());
                        guest.deposit(deposit);
                        Guest newGuest = new Guest(guest.name, guest.balance);
                        updateBalance(guest.name, oldGuest.balance, newGuest.balance);
                    } else if (answer == 2) {
                        System.out.print("How much would you like to withdraw?: ");
                        double withdraw = Double.valueOf(scanner.nextLine());
                        guest.withdraw(withdraw);
                        Guest newGuest = new Guest(guest.name, guest.balance);
                        updateBalance(guest.name, oldGuest.balance, newGuest.balance);
                    }
                } catch (Exception e) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Try again");
                    System.out.print(ConsoleColors.RESET);
                    answer = checkInput(answer, 1, 3);
                }
        }
    }

    public void setName(String newName) {
            name = newName;
        }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double money) {
        if (money > 0.0) {
            balance += money;
        } else {
            System.out.print(ConsoleColors.RED);
            System.out.println("Input must be above 0.0");
            System.out.print(ConsoleColors.RESET);
        }
    }

    public void withdraw(double money) {
        if ((balance - money) > 0.0) {
            balance -= money;
        } else {
            System.out.print(ConsoleColors.RED);
            System.out.println("Can't withdraw because don't have that much money");
            System.out.print(ConsoleColors.RESET);
        }
        
    }

    public void addGuest(Guest guest) throws Exception {
        BufferedWriter writer =
        Helper.getWriter("guests.csv", StandardOpenOption.APPEND);
        writer.write(guest.guestToCsvRow());
        writer.close();
    }

    public void addGuest(String line) throws Exception{
        String[] parts = line.split(" ");
        
        String name = parts[0];
        double balance = Double.valueOf(parts[1]);
        Guest guest = new Guest(name, balance);
        addGuest(guest);
    }

    public String guestToCsvRow() {
        return this.name + ", " + this.balance + "\n";
    }

    public int checkInput(int input, int start, int end) {
        Scanner scanner = new Scanner(System.in);
        while (start > input || input > end) {
            try {
                input = Integer.valueOf(scanner.nextLine());
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect input, try again");
                System.out.print(ConsoleColors.RESET);
                System.out.print("Input: ");
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect input, try again");
                System.out.print(ConsoleColors.RESET);
            }
        }
        return input;
    }

    public static void updateBalance(String name, double oldBalance, double newBalance) throws Exception{
        File oldFile = new File("/workspaces/Hotel_project_DPale/data/guests.csv");
        File tempFile = new File("/workspaces/Hotel_project_DPale/data/tempguests.csv");
        tempFile.createNewFile();
        
        BufferedReader reader = Helper.gerReader("guests.csv");
        BufferedWriter writer =

        Helper.getWriter("tempguests.csv", StandardOpenOption.APPEND);
        String linetoupdate = name + ", " + oldBalance;
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(linetoupdate)) {
                writer.write(name + ", " + newBalance + "\n");
                continue;
            }
            
            writer.write(currentLine + "\n");
            
        }
        writer.close(); 
        reader.close();
        boolean successful = tempFile.renameTo(oldFile);
    }
}