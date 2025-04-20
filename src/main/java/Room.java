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

    public Room(String number, String type, int size, int cost, int discount, String availability, String guest) {
        this.number = number;
        this.type = type;
        this.size = size;
        this.cost = cost;
        this.availability = availability;
        this.discount = discount;
        this.guest = guest;
    }

    public String getRoomnumber() {
        return number;
    }

    public void setRoomNumber(String newNumber) {
        this.number = newNumber;
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

    public int getDiscount() {
        return discount;
    }

    public String getAvailability() {
        return availability;
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

    public void setAvailability(String newAvailability) {
        availability = newAvailability;
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
            String availability = parts[5];
            String guest = parts[6];

            Room room = new Room(number, type, size, cost, discount, availability, guest);
            roomList.put(number, room);
        }
        return roomList;
    }

    public static void getRoomCost(String roomNumber) throws Exception{
        HashMap<String, Room> roomsList = getRoomsList();
        Room room = roomsList.get(roomNumber);
        System.out.println(room.getCost());
    }

    public static Boolean updateRoom(String roomNumber, Guest guest) throws Exception{
        File oldFile = new File("/workspaces/Hotel_project_DPale/data/rooms.csv");
        File tempFile = new File("/workspaces/Hotel_project_DPale/data/temprooms.csv");
        tempFile.createNewFile();
        Boolean success = false;
        BufferedReader reader = Helper.gerReader("rooms.csv");
        BufferedWriter writer =

        Helper.getWriter("temprooms.csv", StandardOpenOption.APPEND);
        HashMap<String, Room> roomsList = getRoomsList();
        String linetoupdate = roomsList.get(roomNumber).roomsToCsvRow();

        if (linetoupdate.equals(roomsList.get(roomNumber).roomsToCsvRow("occupied", guest.getName()))) {
            System.out.print(ConsoleColors.RED);
            System.out.println("You have booked this room already");
            System.out.print(ConsoleColors.RESET);
            tempFile.delete();
        } else if (linetoupdate.equals(roomsList.get(roomNumber).roomsToCsvRow("occupied"))) {
            System.out.print(ConsoleColors.RED);
            System.out.println("This room is already occupied");
            System.out.print(ConsoleColors.RESET);
            tempFile.delete();
        } else {
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(linetoupdate)) {
                    writer.write(roomsList.get(roomNumber).roomsToCsvRow("occupied", guest.getName()) + "\n");
                    continue;
                }
                writer.write(currentLine + "\n");
            }
            success = true;
            boolean successful = tempFile.renameTo(oldFile);
        }
        writer.close(); 
        reader.close();
        return success;
    }

    public String roomsToCsvRow(String availability) {
        return this.number + ", " + this.type + ", " + this.size + ", " + this.cost + ", " + this.discount + ", " + availability + ", " + this.guest;
    }

    public String roomsToCsvRow(String availability, String name) {
        return this.number + ", " + this.type + ", " + this.size + ", " + this.cost + ", " + this.discount + ", " + availability + ", " + name;
    }

    public String roomsToCsvRow() {
        return this.number + ", " + this.type + ", " + this.size + ", " + this.cost + ", " + this.discount + ", " + this.availability + ", " + this.guest;
    }

    public String roomsToCsvRowSymb() {
        Double costWithDiscount = Double.valueOf(this.cost) - (Double.valueOf(this.discount) / 100) * Double.valueOf(this.cost);
        return this.number + " " + this.type + ", " + this.size + "m², " + costWithDiscount + " EUR, " + this.discount + " %, " + this.availability;
    }

    public String roomsToCsvRowSymbAdmin() {
        Double costWithDiscount = Double.valueOf(this.cost) - (Double.valueOf(this.discount) / 100) * Double.valueOf(this.cost);
        return this.number + " " + this.type + ", " + this.size + "m², " + costWithDiscount + " EUR, " +  this.cost + " EUR, " + this.discount + " %, " + this.availability;
    }

    public String printBookedRooms() {
        return this.number + " " + this.type + ", " + this.size + "m², " + this.cost + " EUR, " + this.discount + " %, ";
    }

    public static void printRooms() throws Exception{
        HashMap<String, Room> rooms = getRoomsList();
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=============================================");
        System.out.print(ConsoleColors.RESET);
        System.out.println("Room, type, size, cost, discount, availability");
        System.out.print(ConsoleColors.BLUE);
        System.out.println("=============================================");
        System.out.print(ConsoleColors.RESET);
        for (Room room: rooms.values()) {
            System.out.println(room.roomsToCsvRowSymb());
            System.out.println("=============================================");
        }
    }
}
