import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;

public class Booking {
    public String guest;
    public String room; 
    public int nights;
    public int discount;
    public double totalCost;
    private String type;
    private int size;
    
    public Booking(String guest, String room, int nights) throws Exception{
        this.guest = guest;
        this.room = room;
        this.nights = nights;
        this.discount = getDiscount(room);
        this.totalCost = getTotalCost(room, nights, discount);
        this.type = getType(room);
        this.size = getSize(room);
    }

    public int getDiscount(String room) throws Exception{
        HashMap<String, Room> roomsList = Room.getRoomsList();
        int discount = 0;
        for (Room oneRoom: roomsList.values()) {
            if (oneRoom.getRoomnumber().equals(room)) {
                discount = oneRoom.getDiscount();
            }
        }
        return discount;
    }

    public String getType(String room) throws Exception{
        HashMap<String, Room> roomsList = Room.getRoomsList();
        String type = null;
        for (Room oneRoom: roomsList.values()) {
            if (oneRoom.getRoomnumber().equals(room)) {
                type = oneRoom.getType();
            }
        }
        return type;
    }

    public int getSize(String room) throws Exception{
        HashMap<String, Room> roomsList = Room.getRoomsList();
        int size = 0;
        for (Room oneRoom: roomsList.values()) {
            if (oneRoom.getRoomnumber().equals(room)) {
                size = oneRoom.getSize();
            }
        }
        return size;
    }

    public Double getTotalCost(String room, int nights, int discount) throws Exception{
        HashMap<String, Room> roomsList = Room.getRoomsList();
        Double totalCost = 0.0;
        for (Room oneRoom: roomsList.values()) {
            if (oneRoom.getRoomnumber().equals(room)) {
                Double roomCost = Double.valueOf(oneRoom.getCost());
                totalCost = Double.valueOf(nights) * (roomCost - ((Double.valueOf(discount) / 100) * roomCost));
            }
        }
        return totalCost;
    }

    public String getGuestName() {
        return guest;
    }

    public String getRoom() {
        return room;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public int getNights() {
        return nights;
    }

    public static Boolean checkIfBooked(String roomNumber, Guest guest) throws Exception{
        HashMap<String, Room> roomsList = Room.getRoomsList();
        Room lineToCheck = roomsList.get(roomNumber);

        if (lineToCheck.getGuest().equals(guest.getName())) {
            System.out.print(ConsoleColors.RED);
            System.out.println("You have booked this room already");
            System.out.print(ConsoleColors.RESET);
            return true;
        } else if (lineToCheck.getAvailability().equals("Occupied")){
            System.out.print(ConsoleColors.RED);
            System.out.println("This room is already occupied");
            System.out.print(ConsoleColors.RESET);
            return true;
        } else {
            return false;
        }
    }

    public static void addBook(Booking book) throws Exception{
        HashMap<String, Room> roomsList = Room.getRoomsList();
        for (Room oneRoom: roomsList.values()) {
            if (oneRoom.getRoomnumber().equals(book.getRoom()) && oneRoom.getAvailability().equals("true")) {
                BufferedWriter writer =
                Helper.getWriter("book.csv", StandardOpenOption.APPEND);
                writer.write(bookToCsvRow(book) + "\n");
                writer.close();
            }
        }
    }
    
    public void addBook(String line) throws Exception{
        String[] parts = line.split(" ");
        
        String guest = parts[0];
        String room = parts[1];
        int nights = Integer.valueOf(parts[2]);

        Booking book = new Booking(guest, room, nights);
        addBook(book);
    }

    public static ArrayList<Booking> getBooks() throws Exception{
        BufferedReader reader = Helper.gerReader("book.csv");

        ArrayList<Booking> bookList = new ArrayList<>();
        String line;

        reader.readLine();       
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");

            String guest = parts[0];
            String room = parts[1];
            int nights = Integer.valueOf(parts[2]);

            Booking book = new Booking(guest, room, nights);
            bookList.add(book);
        }
        return bookList;
    }

    public static void searchBooked(Guest guest) throws Exception{
        ArrayList<Booking> books = getBooks();
        int bookedRoomCount = 0;
        for (Booking book: books) {
            if (book.getGuestName().equals(guest.getName())) {
                bookedRoomCount++;
            }
        }
        if (bookedRoomCount == 0) {
            System.out.println("You don't have any booked rooms yet\n");
        } else {
            System.out.println("Booked rooms");
            System.out.print(ConsoleColors.BLUE);
            System.out.println("==================================");
            System.out.print(ConsoleColors.RESET);
            System.out.println("Room, type, size, nights, total cost");
            System.out.print(ConsoleColors.BLUE);
            System.out.println("==================================");
            System.out.print(ConsoleColors.RESET);
            for (Booking book: books) {
                if (book.getGuestName().equals(guest.getName())) {
                    System.out.println(book.showBookedRooms());
                    System.out.println("==================================");
                }
            }
            System.out.println();
        }
    }

    public String showBookedRooms() {
        return this.room + ", " + this.type + ", " + this.size + "m² , " + this.nights + ", " + this.totalCost + " EUR";
    }

    public static String bookToCsvRow(Booking book) {
        return book.guest + ", " + book.room + ", " + book.nights + ", " + book.discount + ", " + book.totalCost;
    }
}
