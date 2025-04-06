import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

public class Room {
    private int number;
    private String type;
    private int size;
    private int cost;
    private boolean available;
    private String guest;
    private int discount;

    public Room(int number, String type, int size, int cost, int discount, boolean available, String guest) {
        this.number = number;
        this.type = type;
        this.size = size;
        this.cost = cost;
        this.available = available;
        this.discount = discount;
        this.guest = guest;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getCost() {
        return cost;
    }

    public boolean getAvailability() {
        return available;
    }

    public String getGuest() {
        return guest;
    }

    public void setCost(int newCost) {
        cost = newCost;
    }

    public void setGuest(String newGuest) {
        guest = newGuest;
    }

    public void setAvailability(boolean availability) {
        available = availability;
    }

    public void setDiscount(int newDiscount) {
        discount = newDiscount;
    }

    public static HashMap<Integer, Room> getRoomsList() throws Exception {
        BufferedReader reader = Helper.gerReader("rooms.csv");

        HashMap<Integer, Room> roomList = new HashMap<>();
        String line;

        reader.readLine();       
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");

            int number = Integer.valueOf(parts[0]);
            String type = parts[1];
            int size = Integer.valueOf(parts[2]);
            int cost = Integer.valueOf(parts[3]);
            int discount = Integer.valueOf(parts[4]);
            boolean available = Boolean.valueOf(parts[5]);
            String guest = parts[6];

            Room room = new Room(number, type, size, cost, discount, available, guest);
            roomList.put(number, room);
        }
        return roomList;
    }

    public String toCsvRow() {
        return this.number + ") " + this.type + ", " + this.size + "m², " + this.cost + " EUR, " + this.discount + " %, " + this.available;
    }

    public static void printRooms() throws Exception{
        HashMap<Integer, Room> rooms = getRoomsList();
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=============================================");
        System.out.print(ConsoleColors.RESET);
        System.out.println("Room, type, size, cost, discount, available");
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=============================================");
        System.out.print(ConsoleColors.RESET);
        for (Room room: rooms.values()) {
            System.out.println(room.toCsvRow());
            System.out.println("=============================================");
        }
    }
}
