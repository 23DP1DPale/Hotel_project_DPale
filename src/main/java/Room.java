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
    private String available;
    private String guest;
    private int discount;

    public Room(String number, String type, int size, int cost, int discount, String available, String guest) {
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

    public String getAvailability() {
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

    public void setAvailability(String availability) {
        available = availability;
    }

    public void setDiscount(int newDiscount) {
        discount = newDiscount;
    }

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
            String available = parts[5];
            String guest = parts[6];

            Room room = new Room(number, type, size, cost, discount, available, guest);
            roomList.put(number, room);
        }
        return roomList;
    }

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
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(linetoupdate)) {
                writer.write(roomsList.get(roomNumber).roomsToCsvRow("Occupied", guest.getName()) + "\n");
                continue;
            }
            
            writer.write(currentLine + "\n");
            
        }
        writer.close(); 
        reader.close();
        boolean successful = tempFile.renameTo(oldFile);
    }

    public static void searchBooked(Guest guest) throws Exception{

        HashMap<String, Room> roomsList = getRoomsList();
        int bookedRoomCount = 0;
        for (Room room: roomsList.values()) {
            if (room.roomsToCsvRow().equals(room.roomsToCsvRow("Occupied", guest.getName()))) {
                bookedRoomCount++;
            }
        }
        if (bookedRoomCount == 0) {
            System.out.println("You don't have any booked rooms yet\n");
        } else {
            System.out.println("Your booked rooms");
            System.out.print(ConsoleColors.BLUE);
            System.out.println("==================================");
            System.out.print(ConsoleColors.RESET);
            System.out.println("Room, type, size, cost, discount");
            System.out.print(ConsoleColors.BLUE);
            System.out.println("==================================");
            System.out.print(ConsoleColors.RESET);
            for (Room room: roomsList.values()) {
                if (room.roomsToCsvRow().equals(room.roomsToCsvRow("Occupied", guest.getName()))) {
                    System.out.println(room.printBookedRooms());
                    System.out.println("==================================");
                }
            }
            System.out.println();
        }
    }

    public String roomsToCsvRow(String availability, String name) {
        return this.number + ", " + this.type + ", " + this.size + ", " + this.cost + ", " + this.discount + ", " + availability + ", " + name;
    }

    public String roomsToCsvRow() {
        return this.number + ", " + this.type + ", " + this.size + ", " + this.cost + ", " + this.discount + ", " + this.available + ", " + this.guest;
    }

    public String roomsToCsvRowSymb() {
        return this.number + " " + this.type + ", " + this.size + "m², " + this.cost + " EUR, " + this.discount + " %, " + this.available;
    }

    public String printBookedRooms() {
        return this.number + " " + this.type + ", " + this.size + "m², " + this.cost + " EUR, " + this.discount + " %, ";
    }

    public static void printRooms() throws Exception{
        HashMap<String, Room> rooms = getRoomsList();
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=============================================");
        System.out.print(ConsoleColors.RESET);
        System.out.println("Room, type, size, cost, discount, available");
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=============================================");
        System.out.print(ConsoleColors.RESET);
        for (Room room: rooms.values()) {
            System.out.println(room.roomsToCsvRowSymb());
            System.out.println("=============================================");
        }
    }
 
}
