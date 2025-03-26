import java.util.Scanner;

public class Admin {
    private String name;

    public Admin(String name) {
        this.name = name;
    }

    public void setName(String newName) {
        name = newName;
    }
    public String getName() {
        return name;
    }

    public void admin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            while (name.length() < 3 || name.matches("[a-zA-Z]+") == false) {
                System.out.print(ConsoleColors.RED);
                System.out.print("Your name has to be at least 3 characters long: ");
                System.out.print(ConsoleColors.RESET);
                name = scanner.nextLine();
            }
            setName(name);
            System.out.println("\nWelcome " + getName());
            showOptions();
            int option = Integer.valueOf(scanner.nextLine());
            while (option != 4) {
                option = Integer.valueOf(scanner.nextLine());
            }
    }

    public void checkOption(int option) throws Exception{
        if (option == 1) {
            changeCost();
        } else if (option == 2) {
            
        } else if (option == 3) {
            addDiscount();
        }
    }

    public void showOptions() {
        System.out.println("Choose one of the option:");
        System.out.println("1) Cost - change room cost per night");
        System.out.println("2) Availability - set rooms to be avaible or unavaible for guests");
        System.out.println("3) Discount - add a discount for a room");
        System.out.println("4) Exit\n");
    }

    public void changeCost() {

    }

    public void addDiscount() {

    }
}
