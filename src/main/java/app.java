import java.util.*;

public class app {
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        if (showMenu().matches("(G|g)uest")) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            Guest guest = new Guest(name);
            System.out.println("Welcome " + name);
            guest.showOptions();
            String option = scanner.nextLine();
            while (option.matches("(E|e)xit") == false) {
                
            }
        } else {
            System.out.println("Welcome administrator");
        }
    }

    public static String showMenu() throws Exception {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 45; i++) {
            Thread.sleep(25);
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
            Thread.sleep(25);
            System.out.print(ConsoleColors.GREEN_BACKGROUND + " ");
        }
        System.out.println(ConsoleColors.RESET + "\n");
        System.out.print("Choose your role (admin or guest): ");
        String answer = scanner.nextLine();
        while (true) {
            if (answer.matches("(A|a)dmin") || answer.matches("(G|g)uest")) {
                break;
            } else {
                System.out.print("Incorrect input, try again: ");
                answer = scanner.nextLine();
            }
        } 
        return answer;
    }
}