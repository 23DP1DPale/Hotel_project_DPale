import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

public class Room {
    private String number;
    private String type;
    private int size;
    private int cost;
    private String availability;
    private String guest;
    private int discount;
    private String guestPassword;

    public Room(String number, String type, int size, int cost, int discount, String availability, String guest, String guestPassword) {
        this.number = number;
        this.type = type;
        this.size = size;
        this.cost = cost;
        this.availability = availability;
        this.discount = discount;
        this.guest = guest;
        this.guestPassword = guestPassword;
    }

    public String getRoomnumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public double getCostWithDiscount() {
        Double costWithDiscount = Double.valueOf(this.cost) - (Double.valueOf(this.discount) / 100) * Double.valueOf(this.cost);
        return costWithDiscount;
    }

    public int getCost() {
        return cost;
    }

    public int getDiscount() {
        return discount;
    }

    public String getAvailability() {
        return availability;
    }

    public String getGuest() {
        return guest;
    }

    public String getGuestPassword() {
        return guestPassword;
    }

    public void setCost(int newCost) {
        cost = newCost;
    }

    public void setAvailability(String newAvailability) {
        availability = newAvailability;
    }

    public void setDiscount(int newDiscount) {
        discount = newDiscount;
    }

    // Gets rooms from rooms.csv
    public static HashMap<String, Room> getRoomsList() throws Exception {
        BufferedReader reader = Helper.gerReader("rooms.csv");

        HashMap<String, Room> roomList = new HashMap<>();
        String line;

        reader.readLine();       
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");

            String number = parts[0];
            String type = parts[1];
            int size = Integer.valueOf(parts[2]);
            int cost = Integer.valueOf(parts[3]);
            int discount = Integer.valueOf(parts[4]);
            String availability = parts[5];
            String guest = parts[6];
            String guestPassword = parts[7];

            Room room = new Room(number, type, size, cost, discount, availability, guest, guestPassword);
            roomList.put(number, room);
        }
        return roomList;
    }

    // Updates rooms availability to occupied, adds guest and guest password in rooms.csv
    public static void updateRoom(String roomNumber, Guest guest) throws Exception{
        File oldFile = new File("/workspaces/Hotel_project_DPale/data/rooms.csv");
        File tempFile = new File("/workspaces/Hotel_project_DPale/data/temprooms.csv");
        tempFile.createNewFile();
        BufferedReader reader = Helper.gerReader("rooms.csv");
        BufferedWriter writer =

        Helper.getWriter("temprooms.csv", StandardOpenOption.APPEND);
        HashMap<String, Room> roomsList = getRoomsList();
        String linetoupdate = roomsList.get(roomNumber).roomsToCsvRow();

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(linetoupdate)) {
                writer.write(roomsList.get(roomNumber).roomsToCsvRow("occupied", guest.getName(), guest.getPassword()) + "\n");
                continue;
            }
            writer.write(currentLine + "\n");
        }
        tempFile.renameTo(oldFile);

        writer.close(); 
        reader.close();
    }

    public static void removeBookedRoom(String roomNumber) throws Exception{
        File oldFile = new File("/workspaces/Hotel_project_DPale/data/rooms.csv");
        File tempFile = new File("/workspaces/Hotel_project_DPale/data/temprooms.csv");
        tempFile.createNewFile();
        BufferedReader reader = Helper.gerReader("rooms.csv");
        BufferedWriter writer =

        Helper.getWriter("temprooms.csv", StandardOpenOption.APPEND);
        HashMap<String, Room> roomsList = getRoomsList();
        String linetoupdate = roomsList.get(roomNumber).roomsToCsvRow();
        
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(linetoupdate)) {
                writer.write(roomsList.get(roomNumber).roomsToCsvRow("available", "none", "none" + "\n"));
                continue;
            }
            writer.write(currentLine + "\n");
        }
        tempFile.renameTo(oldFile);

        writer.close(); 
        reader.close();
    }
    
    // Print table of rooms sorted from most expensive to cheapest
    public static void sortByHighestCost() throws Exception{
        HashMap<String, Room> roomsList = getRoomsList();
        for (int i = 1; i <= roomsList.size(); i++) {
            for (int j=i+1; j <= roomsList.size(); j++) {
                Room tempI= roomsList.get(String.valueOf(i));
                Room tempJ= roomsList.get(String.valueOf(j));
                if (roomsList.get(String.valueOf(i)).getCostWithDiscount() < roomsList.get(String.valueOf(j)).getCostWithDiscount()) {
                    roomsList.put(String.valueOf(i), tempJ);
                    roomsList.put(String.valueOf(j), tempI);
                }
            }
        }
        printRoomHeader();
        System.out.print(ConsoleColors.RESET);
        for (Room room: roomsList.values()) {
            room.roomsToCsvRowSymb();
            System.out.println("=".repeat(80));
        }
        System.out.println();
    }

    // Print table of rooms sorted from most cheapest to expensive
    public static void sortByLowestCost() throws Exception{
        HashMap<String, Room> roomsList = getRoomsList();
        for (int i = 1; i <= roomsList.size(); i++) {
            for (int j=i+1; j <= roomsList.size(); j++) {
                Room tempI= roomsList.get(String.valueOf(i));
                Room tempJ= roomsList.get(String.valueOf(j));
                if (roomsList.get(String.valueOf(i)).getCostWithDiscount() > roomsList.get(String.valueOf(j)).getCostWithDiscount()) {
                    roomsList.put(String.valueOf(i), tempJ);
                    roomsList.put(String.valueOf(j), tempI);
                }
            }
        }
        printRoomHeader();
        System.out.print(ConsoleColors.RESET);
        for (Room room: roomsList.values()) {
            room.roomsToCsvRowSymb();
            System.out.println("=".repeat(80));
        }
        System.out.println();
    }

    // Print table of rooms sorted from biggest to smallest
    public static void sortByBiggestSize() throws Exception{
        HashMap<String, Room> roomsList = getRoomsList();
        for (int i = 1; i <= roomsList.size(); i++) {
            for (int j=i+1; j <= roomsList.size(); j++) {
                Room tempI= roomsList.get(String.valueOf(i));
                Room tempJ= roomsList.get(String.valueOf(j));
                if (roomsList.get(String.valueOf(i)).getSize() < roomsList.get(String.valueOf(j)).getSize()) {
                    roomsList.put(String.valueOf(i), tempJ);
                    roomsList.put(String.valueOf(j), tempI);
                }
            }
        }
        printRoomHeader();
        for (Room room: roomsList.values()) {
            room.roomsToCsvRowSymb();
            System.out.println("=".repeat(80));
        }
        System.out.println();
    }

    // Print table of rooms sorted from smallest to biggest
    public static void sortBySmallestSize() throws Exception {
        HashMap<String, Room> roomsList = getRoomsList();
        for (int i = 1; i <= roomsList.size(); i++) {
            for (int j=i+1; j <= roomsList.size(); j++) {
                Room tempI= roomsList.get(String.valueOf(i));
                Room tempJ= roomsList.get(String.valueOf(j));
                if (roomsList.get(String.valueOf(i)).getSize() > roomsList.get(String.valueOf(j)).getSize()) {
                    roomsList.put(String.valueOf(i), tempJ);
                    roomsList.put(String.valueOf(j), tempI);
                }
            }
        }
        printRoomHeader();
        for (Room room: roomsList.values()) {
            room.roomsToCsvRowSymb();
            System.out.println("=".repeat(80));
        }
        System.out.println();
    }

    // Print table of rooms by range of cost
    public static void searchByCost(int from, int to) throws Exception {
        HashMap<String, Room> roomsList = getRoomsList();
        printRoomHeader();
        for (Room room: roomsList.values()) {
            if (from <= room.getCost() && room.getCost() <= to) {
                room.roomsToCsvRowSymb();
                System.out.println("=".repeat(80));
            }
        }
        System.out.println();
    }

    // Print table of rooms by range of size
    public static void searchBySize(int from, int to) throws Exception {
        HashMap<String, Room> roomsList = getRoomsList();
        printRoomHeader();
        for (Room room: roomsList.values()) {
            if (from <= room.getSize() && room.getSize() <= to) {
                room.roomsToCsvRowSymb();
                System.out.println("=".repeat(80));
            }
        }
        System.out.println();
    }

    // Print table of rooms by range of discount
    public static void searchByDiscount(int from, int to) throws Exception {
        HashMap<String, Room> roomsList = getRoomsList();
        printRoomHeader();
        for (Room room: roomsList.values()) {
            if (from <= room.getDiscount() && room.getDiscount() <= to) {
                room.roomsToCsvRowSymb();
                System.out.println("=".repeat(80));
            }
        }
        System.out.println();
    }

    public String roomsToCsvRow(String availability) {
        return this.number + ", " + this.type + ", " + this.size + ", " + this.cost + ", " + this.discount + ", " + availability + ", " + this.guest + ", " + this.guestPassword;
    }

    public String roomsToCsvRow(String availability, String name, String password) {
        return this.number + ", " + this.type + ", " + this.size + ", " + this.cost + ", " + this.discount + ", " + availability + ", " + name + ", " + password;
    }

    public String roomsToCsvRow() {
        return this.number + ", " + this.type + ", " + this.size + ", " + this.cost + ", " + this.discount + ", " + this.availability + ", " + this.guest + ", " + this.guestPassword;
    }

    public void roomsToCsvRowSymb() {
        String checkAvailability = this.availability;
        int padding = 27;
        if (checkAvailability.equals("unavailable")) {
            checkAvailability = ConsoleColors.RED;
            padding = 28;
        } else if (checkAvailability.equals("occupied")) {
            checkAvailability = ConsoleColors.RED;
            padding = 26;
        } else {
            checkAvailability = ConsoleColors.GREEN;
        }
        Double costWithDiscount = Double.valueOf(this.cost) - (Double.valueOf(this.discount) / 100) * Double.valueOf(this.cost);
        System.out.printf("%3s  %15s %8dm² %11.2f EUR %6d %% %" + padding +"s\n", this.number, this.type, this.size, costWithDiscount, this.discount, checkAvailability + this.availability + ConsoleColors.RESET);
    }

    public void roomsToCsvRowSymbAdmin() {
        String checkAvailability = this.availability;
        int padding = 27;
        if (checkAvailability.equals("unavailable")) {
            checkAvailability = ConsoleColors.RED;
            padding = 28;
        } else if (checkAvailability.equals("occupied")){
            checkAvailability = ConsoleColors.RED;
            padding = 26;
        } else {
            checkAvailability = ConsoleColors.GREEN;
        }
        Double costWithDiscount = Double.valueOf(this.cost) - (Double.valueOf(this.discount) / 100) * Double.valueOf(this.cost);
        System.out.printf("%3s  %15s %8dm² %13.2f EUR %13d EUR %9d %% %" + padding +"s\n", this.number, this.type, this.size, costWithDiscount, this.cost, this.discount, checkAvailability + this.availability + ConsoleColors.RESET);
    }

    public static void printRoomHeader() {
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=".repeat(80));
        System.out.print(ConsoleColors.RESET);
        System.out.printf("%4s %12s %13s %12s %14s %15s", "Room", "type", "size", "cost", "discount", "availability");
        System.out.print(ConsoleColors.BLUE);
        System.out.println("\n" + "=".repeat(80));
        System.out.print(ConsoleColors.RESET);
    }

    // Prints table of rooms
    public static void printRooms() throws Exception{
        HashMap<String, Room> rooms = getRoomsList();
        printRoomHeader();
        for (Room room: rooms.values()) {
            room.roomsToCsvRowSymb();
            System.out.println("=".repeat(80));
        }
    }
}
