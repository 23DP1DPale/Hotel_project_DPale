import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

public class Room {
    private int number;
    private String type;
    private double size;
    private double cost;
    private boolean available;
    private String guest;
    private double discount;

    public Room(int number, String type, double size, double cost, double discount, boolean available, String guest) {
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

    public double getSize() {
        return size;
    }

    public double getCost() {
        return cost;
    }

    public boolean getAvailability() {
        return available;
    }

    public String getGuest() {
        return guest;
    }

    public void setCost(double newCost) {
        cost = newCost;
    }

    public void setGuest(String newGuest) {
        guest = newGuest;
    }

    public void setAvailability(boolean availability) {
        available = availability;
    }

    public void setDiscount(double newDiscount) {
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
            double size = Double.valueOf(parts[2]);
            double cost = Double.valueOf(parts[3]);
            double discount = Double.valueOf(parts[4]);
            boolean available = Boolean.valueOf(parts[5]);
            String guest = parts[6];

            Room room = new Room(number, type, size, cost, discount, available, guest);
            roomList.put(number, room);
        }
        return roomList;
    }

    public String toCsvRow() {
        return this.number + ", " + this.type + ", " + this.size + ", " + this.cost + " EUR, " + this.discount + " %, " + this.available;
    }

    public static void printRooms() throws Exception{
        HashMap<Integer, Room> rooms = getRoomsList();
        System.out.println("=============================================");
        System.out.println("Room, type, size, cost, discount, available");
        System.out.println("=============================================");
        for (Room room: rooms.values()) {
            System.out.println(room.toCsvRow());
        }
        System.out.println("=============================================\n");
    }
}
