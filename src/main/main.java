package src.main;

import java.util.*;

public class main {
    public static void main(String[] args) throws Exception{
        showMenu();
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
        return answer;
    }
}