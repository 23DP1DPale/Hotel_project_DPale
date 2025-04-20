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
            while (name.length() < 3 || name.length() > 20 || name.matches("[a-zA-Z]+") == false) {
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
            int option = 0;
            while (option != 5) {
                option = checkInput(option, 1, 5);
                if (option == 5) {
                    break;
                }
                checkOption(guest, option);
                System.out.println();
                showOptions();
            }
            System.out.println("See you soon");
    }

    public void showOptions() {
        System.out.println("Choose one of the option:");
        System.out.println("1) Book - book a room for a certain time period");
        System.out.println("2) Rooms - shows all rooms");
        System.out.println("3) Balance - check your balance or deposit money");
        System.out.println("4) See your booked rooms");
        System.out.println("5) Exit\n");
    }

    public void checkOption(Guest guest, int option) throws Exception {
        Scanner scanner = new Scanner(System.in);
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

    public void book(Guest guest) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Room.printRooms();
        System.out.println("\nEnter a room number to book it");
        System.out.println();
        String answer = "0";
        answer = String.valueOf(checkInput(Integer.valueOf(answer), 1, 10));
        Boolean success = false;
        while (true) {
            if (Booking.checkIfBooked(answer, guest)) {
                break;
            }
            try {
                System.out.print("For how many nights?: ");
                int nights = Integer.valueOf(scanner.nextLine());
                if (nights <= 0) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Night count has to be above 0");
                    System.out.print(ConsoleColors.RESET);
                    break;
                }
                if (nights > 30) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Night count can't be over 30");
                    System.out.print(ConsoleColors.RESET);
                    break;
                }
                Booking book = new Booking(guest.getName(), answer, nights);
                System.out.println("Total cost: " + book.getTotalCost());
                if (book.getTotalCost() <= guest.getBalance()) {
                    Booking.addBook(book);
                    Double oldBalance = guest.getBalance();
                    guest.withdraw(book.getTotalCost());
                    updateBalance(guest.getName(), oldBalance, guest.getBalance());
                    success = true;
                } else {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("\nYou don't have enough money");
                    System.out.print(ConsoleColors.RESET);
                    success = false;
                }
                break;
            } catch(Exception e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect night count input");
                System.out.print(ConsoleColors.RESET);
            }
        }

        if (success == true) {
            Room.updateRoom(answer, guest);
            System.out.print(ConsoleColors.GREEN);
            System.out.println("Successfuly booked a room");
            System.out.print(ConsoleColors.RESET);
        }
    }

    public void showRooms() throws Exception {
        Scanner scanner = new Scanner(System.in);
        Room.printRooms();
        System.out.println();
        while (true) {
            System.out.println("Choose one of the options:");
            System.out.println("1) Sort");
            System.out.println("2) Exit");
            int input = 0;
            input = checkInput(input, 1, 2);
            if (input == 1) {
                System.out.println("Sort by property:");
                System.out.println("\n1) Cost");
                System.out.println("2) Size");
                if ((input = checkInput(input, 1, 2)) == 1) {
                    System.out.println("\n1) From the most expensive");
                    System.out.println("2) From the cheapest");
                    if ((input = checkInput(input, 1, 2)) == 1) {
                        Room.sortByHighestCost();
                    } else {
                        Room.sortByLowestCost();
                    }
                } else if (input == 2) {
                    System.out.println("\n1) From the biggest");
                    System.out.println("2) From the smallest");
                    if ((input = checkInput(input, 1, 2)) == 1) {
                        Room.sortByBiggestSize();
                    } else {
                        Room.sortBySmallestSize();
                    }
                }
            } else if (input == 2) {
                break;
            }
        }
    }

    public void checkBalance(Guest guest) {
        Scanner scanner = new Scanner(System.in);
        int answer = 0;
        while (answer != 3) {
            System.out.println("Your balance: " + guest.balance + " EUR");
            System.out.println("\nWould you like to:");
            System.out.println("1) Deposit money");
            System.out.println("2) Withdraw money");
            System.out.println("3) Exit\n");
                try {
                    answer = 0;
                    answer = checkInput(answer, 1, 3);
                    Guest oldGuest = new Guest(guest.name, guest.balance);
                    if (answer == 1) {
                        System.out.print("How much would you like to deposit?: ");
                        double deposit = Double.valueOf(scanner.nextLine());
                        guest.deposit(deposit);
                        System.out.println();
                        Guest newGuest = new Guest(guest.name, guest.balance);
                        updateBalance(guest.name, oldGuest.balance, newGuest.balance);
                    } else if (answer == 2) {
                        System.out.print("How much would you like to withdraw?: ");
                        double withdraw = Double.valueOf(scanner.nextLine());
                        guest.withdraw(withdraw);
                        System.out.println();
                        Guest newGuest = new Guest(guest.name, guest.balance);
                        updateBalance(guest.name, oldGuest.balance, newGuest.balance);
                    }
                } catch (Exception e) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Incorrect input\n");
                    System.out.print(ConsoleColors.RESET);
                }
        }
    }

    public void checkBookedRooms(Guest guest) throws Exception{
        Scanner scanner = new Scanner(System.in);
        Booking.searchBooked(guest);
        System.out.println("1) Exit\n");
        int input = 0;
        input = checkInput(input, 1, 1);
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
        if ((balance - money) >= 0.0 && money >= 0) {
            balance -= money;
        } else {
            System.out.print(ConsoleColors.RED);
            System.out.println("Incorrect withdraw value");
            System.out.print(ConsoleColors.RESET);
        }
    }

    public void addGuest(Guest guest) throws Exception {
        BufferedWriter writer =
        Helper.getWriter("guests.csv", StandardOpenOption.APPEND);
        writer.write(guestToCsvRow(guest));
        writer.close();
    }

    public void addGuest(String line) throws Exception{
        String[] parts = line.split(" ");
        
        String name = parts[0];
        double balance = Double.valueOf(parts[1]);
        Guest guest = new Guest(name, balance);
        addGuest(guest);
    }

    public static String guestToCsvRow(Guest guest) {
        return guest.name + ", " + guest.balance + "\n";
    }

    public static int checkInput(int input, int start, int end) {
        Scanner scanner = new Scanner(System.in);
        input = 0;
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

    public static void updateBalance(String name, double oldBalance, double newBalance) throws Exception{
        File oldFile = new File("/workspaces/Hotel_project_DPale/data/guests.csv");
        File tempFile = new File("/workspaces/Hotel_project_DPale/data/tempguests.csv");
        tempFile.createNewFile();
        
        BufferedReader reader = Helper.gerReader("guests.csv");
        BufferedWriter writer =

        Helper.getWriter("tempguests.csv", StandardOpenOption.APPEND);
        String linetoupdate = name + ", " + oldBalance;
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.equals(linetoupdate)) {
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