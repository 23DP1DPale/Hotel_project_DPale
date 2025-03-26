import java.util.Scanner;

public class Guest {
    private String name;
    private double balance;

    public Guest(String name, double balance) {
        this.name = name;
        this.balance = balance;
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

    public void setBalance(double newBalance) {
        balance = newBalance;
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
            setName(name);
            System.out.println("\nWelcome " + name);
            showOptions();
            int option = Integer.valueOf(scanner.nextLine());
            while (option != 4) {
                checkOption(option);
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

    public void checkOption(int option) throws Exception{
        if (option == 1) {
            book();
        } else if (option == 2) {
            showRooms();
        } else if (option == 3) {
            
        }
    }

    public void book() {
        
    }

    public void showRooms() throws Exception{

    }
}
