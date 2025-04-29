import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
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

    public void admin() throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            while (name.length() < 3 || name.length() > 20 || name.matches("[a-zA-Z]+") == false) {
                System.out.print(ConsoleColors.RED);
                System.out.print("Your name has to be at least 3 characters long: ");
                System.out.print(ConsoleColors.RESET);
                name = scanner.nextLine();
            }
            System.out.print("\033[2J\033[3J\033[H");
            System.out.flush();
            setName(name);
            printRooms();
            System.out.println("\nWelcome " + getName());
            showOptions();
            int option = 0;
            while (option != 4) {
                option = Guest.checkInput(option, 1, 4);
                if (option == 5) {
                    break;
                }
                checkOption(option);
                printRooms();
                System.out.println();
                showOptions();
            }
            System.out.print("\033[2J\033[3J\033[H");
            System.out.flush();
            System.out.println("See you soon");
    }

    public void checkOption(int option) throws Exception{
        if (option == 1) {
            System.out.println("\n");
            changeRoomCost();
        } else if (option == 2) {
            System.out.println("\n");
            changeRoomAvailability();
        } else if (option == 3) {
            System.out.println("\n");
            changeRoomDiscount();
        }
    }

    public void showOptions() {
        System.out.println("Choose one of the option:");
        System.out.println("1) Cost - change room cost per night");
        System.out.println("2) Availability - set rooms to be avaible or unavaible for guests");
        System.out.println("3) Discount - add a discount for a room");
        System.out.println("4) Exit\n");
    }

    public void changeRoomCost() throws Exception{
        Scanner scanner = new Scanner(System.in);
        app.clearScreen();
        printRooms();
        System.out.println("\nEnter a room number to change its cost");
        String room = "0";
        room = String.valueOf(Guest.checkInput(Integer.valueOf(room), 1, 10));
        while (true) {
            try {
                System.out.print("Cost: ");
                int cost = Integer.valueOf(scanner.nextLine());
                if (cost <= 0) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Cost must be above 0");
                    System.out.print(ConsoleColors.RESET);
                } else if (cost > 1000) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Cost is too high");
                    System.out.print(ConsoleColors.RESET);
                } else {
                    changeCost(room, cost);
                    app.clearScreen();
                    System.out.print(ConsoleColors.GREEN);
                    System.out.println("Successfully changed cost");
                    System.out.print(ConsoleColors.RESET);
                    break;
                }
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect cost input");
                System.out.print(ConsoleColors.RESET);
            }
        }
    }

    public void changeRoomAvailability() throws Exception{
        Scanner scanner = new Scanner(System.in);
        app.clearScreen();
        ArrayList<String> availableRooms = searchAvailable();
        System.out.println("Enter a room number to change its availability state");
        while (true) {
            try {
                System.out.print("Input: ");
                String room = scanner.nextLine();
                if (availableRooms.contains(room)) {
                    System.out.println("\nChange availability to:");
                    System.out.println("1)Available");
                    System.out.println("2)Unavailable");
                    int answer = 0;
                    answer = Guest.checkInput(answer, 1, 2);
                    if (answer == 1) {
                        changeAvailability(room, "available");
                        break;
                    } else {
                        changeAvailability(room, "unavailable");
                        break;
                    }
                } else {
                System.out.print(ConsoleColors.RED);
                System.out.println("There is no such room");
                System.out.print(ConsoleColors.RESET);
                }
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect input, try again");
                System.out.print(ConsoleColors.RESET);
            }
        }
        app.clearScreen();
        System.out.print(ConsoleColors.GREEN);
        System.out.println("Successfully changed availability");
        System.out.print(ConsoleColors.RESET);
    }

    public void changeRoomDiscount() throws Exception{
        Scanner scanner = new Scanner(System.in);
        app.clearScreen();
        printRooms();
        System.out.println("\nEnter a room number to change its discount");
        String room = "0";
        room = String.valueOf(Guest.checkInput(Integer.valueOf(room), 1, 10));
        while (true) {
            try {
                System.out.print("Discount(0-100%): ");
                int discount = Integer.valueOf(scanner.nextLine());
                if (discount < 0) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Can't be negative");
                    System.out.print(ConsoleColors.RESET);
                } else if (discount > 100) {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Can't be over 100");
                    System.out.print(ConsoleColors.RESET);
                } else {
                    app.clearScreen();
                    changeDiscount(room, discount);
                    System.out.print(ConsoleColors.GREEN);
                    System.out.println("Successfully changed discount");
                    System.out.print(ConsoleColors.RESET);
                    break;
                }
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED);
                System.out.println("Incorrect discount input");
                System.out.print(ConsoleColors.RESET);
            }
        }
    }

    public static void changeCost(String roomNumber, int cost) throws Exception{
        File oldFile = new File("/workspaces/Hotel_project_DPale/data/rooms.csv");
        File tempFile = new File("/workspaces/Hotel_project_DPale/data/temprooms.csv");
        tempFile.createNewFile();
        BufferedReader reader = Helper.gerReader("rooms.csv");
        BufferedWriter writer =

        Helper.getWriter("temprooms.csv", StandardOpenOption.APPEND);
        HashMap<String, Room> roomsList = Room.getRoomsList();
        String linetoupdate = roomsList.get(roomNumber).roomsToCsvRow();
        roomsList.get(roomNumber).setCost(cost);
        String currentLine;
        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(linetoupdate)) {
                writer.write(roomsList.get(roomNumber).roomsToCsvRow() + "\n");
                continue;
            }
            writer.write(currentLine + "\n");
        }
        writer.close(); 
        reader.close();
        boolean successful = tempFile.renameTo(oldFile);
    }

    public static void changeAvailability(String roomNumber, String availability) throws Exception{
        File oldFile = new File("/workspaces/Hotel_project_DPale/data/rooms.csv");
        File tempFile = new File("/workspaces/Hotel_project_DPale/data/temprooms.csv");
        tempFile.createNewFile();
        BufferedReader reader = Helper.gerReader("rooms.csv");
        BufferedWriter writer =

        Helper.getWriter("temprooms.csv", StandardOpenOption.APPEND);
        HashMap<String, Room> roomsList = Room.getRoomsList();
        String linetoupdate = roomsList.get(roomNumber).roomsToCsvRow();
        roomsList.get(roomNumber).setAvailability(availability);
        String currentLine;
        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(linetoupdate)) {
                writer.write(roomsList.get(roomNumber).roomsToCsvRow() + "\n");
                continue;
            }
            writer.write(currentLine + "\n");
        }
        writer.close(); 
        reader.close();
        boolean successful = tempFile.renameTo(oldFile);
    }

    public static ArrayList<String> searchAvailable() throws Exception{
        HashMap<String, Room> roomList = Room.getRoomsList();
        ArrayList<String> availableRooms = new ArrayList<>();
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=".repeat(100));
        System.out.print(ConsoleColors.RESET);
        System.out.printf("%4s %12s %13s %22s %11s %14s %15s\n", "Room", "type", "size", "cost with discount", "cost", "discount", "availability");
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=".repeat(100));
        System.out.print(ConsoleColors.RESET);
        for (Room room: roomList.values()) {
            if (room.getAvailability().equals("available") || room.getAvailability().equals("unavailable")) {
                room.roomsToCsvRowSymbAdmin();
                System.out.println("=".repeat(100));
                availableRooms.add(room.getRoomnumber());
            }
        }
        System.out.println();
        return availableRooms;
    }

    public static void changeDiscount(String roomNumber, int discount) throws Exception{
        File oldFile = new File("/workspaces/Hotel_project_DPale/data/rooms.csv");
        File tempFile = new File("/workspaces/Hotel_project_DPale/data/temprooms.csv");
        tempFile.createNewFile();
        BufferedReader reader = Helper.gerReader("rooms.csv");
        BufferedWriter writer =

        Helper.getWriter("temprooms.csv", StandardOpenOption.APPEND);
        HashMap<String, Room> roomsList = Room.getRoomsList();
        String linetoupdate = roomsList.get(roomNumber).roomsToCsvRow();
        roomsList.get(roomNumber).setDiscount(discount);
        String currentLine;
        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(linetoupdate)) {
                writer.write(roomsList.get(roomNumber).roomsToCsvRow() + "\n");
                continue;
            }
            writer.write(currentLine + "\n");
        }
        writer.close(); 
        reader.close();
        boolean successful = tempFile.renameTo(oldFile);
    }

    public static void printRooms() throws Exception{
        HashMap<String, Room> rooms = Room.getRoomsList();
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=".repeat(100));
        System.out.print(ConsoleColors.RESET);
        System.out.printf("%5s %11s %13s %22s %11s %14s %15s\n", "Room", "type", "size", "cost with discount", "cost", "discount", "availability");
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=".repeat(100));
        System.out.print(ConsoleColors.RESET);
        for (Room room: rooms.values()) {
            room.roomsToCsvRowSymbAdmin();
            System.out.println("=".repeat(100));
        }
    }
}
