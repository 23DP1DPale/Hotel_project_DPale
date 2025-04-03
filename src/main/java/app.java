import java.util.*;

public class app {
    public static void main(String[] args) throws Exception{
        if (showMenu().equalsIgnoreCase("guest")) {
            Guest guest = new Guest(null, 0.0);
            guest.guest();
        } else {
            Admin admin = new Admin(null);
            admin.admin();
        }
    }

    public static String showMenu() throws Exception {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 45; i++) {
            System.out.print(ConsoleColors.GREEN_BACKGROUND + " ");
        }
        System.out.println(ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD + " __   __  _______  _______  _______  ___     \n" +
                        "|  | |  ||       ||       ||       ||   |    \n" +
                        "|  |_|  ||   _   ||_     _||    ___||   |    \n" +
                        "|       ||  | |  |  |   |  |   |___ |   |    \n" +
                        "|   _   ||  |_|  |  |   |  |    ___||   |___ \n" +
                        "|  | |  ||       |  |   |  |   |___ |       |\n" +
                        "|__| |__||_______|  |___|  |_______||_______|\n");
        for (int i = 0; i < 45; i++) {
            System.out.print(ConsoleColors.GREEN_BACKGROUND + " ");
        }
        System.out.println(ConsoleColors.RESET + "\n");
        System.out.print("Choose your role (admin or guest): ");
        String answer = scanner.nextLine();
        while (true) {
            if (answer.equalsIgnoreCase("admin") || answer.equalsIgnoreCase("guest")) {
                break;
            } else {
                System.out.print(ConsoleColors.RED);
                System.out.print("Incorrect input, try again: ");
                System.out.print(ConsoleColors.RESET);
                answer = scanner.nextLine();
            }
        } 
        return answer;
    }
}