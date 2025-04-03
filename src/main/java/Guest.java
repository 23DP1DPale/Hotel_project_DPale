import java.io.BufferedWriter;
import java.io.Console;
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
                checkOption(option);
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

    public void checkOption(int option) throws Exception {
        Scanner scanner = new Scanner(System.in);
        if (option == 1) {
            book();
        } else if (option == 2) {
            System.out.println("\n");
            showRooms();
            System.out.println("\n");
        } else if (option == 3) {
            checkBalance();
        }
    }

    public void book() throws Exception {
        Scanner scanner = new Scanner(System.in);
        Room.printRooms();
        System.out.print("Enter a room number to book it: ");
        int answer = Integer.valueOf(scanner.nextLine());
    }

    public void showRooms() throws Exception {
        Scanner scanner = new Scanner(System.in);
        Room.printRooms();
        System.out.println("1) Exit");
        int input = Integer.valueOf(scanner.nextLine());
        checkInput(input, 1, 1);
    }


    public void checkBalance() {
        Scanner scanner = new Scanner(System.in);
        int answer = 0;
        while (answer != 3) {
            System.out.println("\nYour balance: " + balance + " EUR");
            System.out.println("\nWould you like to:");
            System.out.println("1) Deposit money");
            System.out.println("2) Withdraw money");
            System.out.println("3) Exit\n");
            System.out.print("input: ");
                try {
                    answer = checkInput(answer, 1, 3);
                    if (answer == 1) {
                        System.out.print("How much would you like to deposit?: ");
                        double deposit = Double.valueOf(scanner.nextLine());
                        deposit(deposit);
                    } else if (answer == 2) {
                        System.out.print("How much would you like to withdraw?: ");
                        double withdraw = Double.valueOf(scanner.nextLine());
                        withdraw(withdraw);
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
            balance =+ money;
        } else {
            System.out.print(ConsoleColors.RED);
            System.out.println("Input must be above 0.0");
            System.out.print(ConsoleColors.RESET);
        }
    }

    public void withdraw(double money) {
        if (balance - money > 0.0) {
            balance =- money;
        } else {
            System.out.print(ConsoleColors.RED);
            System.out.println("Can't withdraw because don't have that much money");
            System.out.print(ConsoleColors.RESET);
        }
        
    }

    public void addGuest(Guest guest) throws Exception {
        BufferedWriter writer =
        Helper.getWriter("guests.csv", StandardOpenOption.APPEND);
        writer.write(guest.toCsvRow());
        writer.close();
    }

    public void addGuest(String line) throws Exception{
        String[] parts = line.split(" ");
        
        String name = parts[0];
        double balance = Double.valueOf(parts[1]);
        Guest guest = new Guest(name, balance);
        addGuest(guest);
    }

    public String toCsvRow() {
        return "\n" + this.name + ", " + this.balance;
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
}