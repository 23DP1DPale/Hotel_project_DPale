import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
    private String guestPassword;
    
    public Booking(String guest, String room, int nights, String guestPassword) throws Exception{
        this.guest = guest;
        this.room = room;
        this.nights = nights;
        this.discount = getDiscount(room);
        this.totalCost = getTotalCost(room, nights, discount);
        this.type = getType(room);
        this.size = getSize(room);
        this.guestPassword = guestPassword;
    }

    // Gets discount from rooms.csv
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

    // Gets room type from rooms.csv
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

    // Gets room size from rooms.csv
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

    // Calculates total cost of book
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

    public String getGuestPassword() {
        return guestPassword;
    }

    // Checks if room is available or unavailable
    public static Boolean checkIfBooked(String roomNumber, Guest guest) throws Exception{
        HashMap<String, Room> roomsList = Room.getRoomsList();
        Room lineToCheck = roomsList.get(roomNumber);

        if (lineToCheck.getGuest().equals(guest.getName()) && lineToCheck.getGuestPassword().equals(guest.getPassword())) {
            app.clearScreen();
            System.out.print(ConsoleColors.RED);
            System.out.println("You have booked this room already");
            System.out.print(ConsoleColors.RESET);
            return true;
        } else if (lineToCheck.getAvailability().equals("occupied") || lineToCheck.getAvailability().equals("unavailable")) {
            app.clearScreen();
            System.out.print(ConsoleColors.RED);
            System.out.println("This room is not available");
            System.out.print(ConsoleColors.RESET);
            return true;
        } else {
            return false;
        }
    }

    public static void addBook(Booking book) throws Exception{
        HashMap<String, Room> roomsList = Room.getRoomsList();
        for (Room oneRoom: roomsList.values()) {
            if (oneRoom.getRoomnumber().equals(book.getRoom()) && oneRoom.getAvailability().equals("available")) {
                BufferedWriter writer =
                Helper.getWriter("book.csv", StandardOpenOption.APPEND);
                writer.write(bookToCsvRow(book) + "\n");
                writer.close();
            }
        }
    }
    
    // Adds book to book.csv
    public void addBook(String line) throws Exception{
        String[] parts = line.split(" ");
        
        String guest = parts[0];
        String room = parts[1];
        int nights = Integer.valueOf(parts[2]);
        String guestPassword = parts[3];

        Booking book = new Booking(guest, room, nights, guestPassword);
        addBook(book);
    }

    // Get books from book.csv
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
            String guestPassword = parts[5];

            Booking book = new Booking(guest, room, nights, guestPassword);
            bookList.add(book);
        }
        return bookList;
    }

    public static void deleteBook(String roomNumber) throws Exception{
        File oldFile = new File("/workspaces/Hotel_project_DPale/data/book.csv");
        File tempFile = new File("/workspaces/Hotel_project_DPale/data/tempbook.csv");
        tempFile.createNewFile();
        BufferedReader reader = Helper.gerReader("book.csv");
        BufferedWriter writer =

        Helper.getWriter("tempbook.csv", StandardOpenOption.APPEND);
        ArrayList<Booking> bookList = getBooks();
        String linetoremove = null;
        for (Booking book: bookList) {
            if (book.getRoom().equals(roomNumber)) {
                linetoremove = bookToCsvRow(book);
            }
        }
        
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.equals(linetoremove)) {
                continue;
            }
            writer.write(currentLine + "\n");
        }
        tempFile.renameTo(oldFile);

        writer.close(); 
        reader.close();
    }

    // Searches users booked rooms
    public static void searchBooked(Guest guest) throws Exception{
        ArrayList<Booking> books = getBooks();
        int bookedRoomCount = 0;
        for (Booking book: books) {
            if (book.getGuestName().equals(guest.getName()) && book.getGuestPassword().equals(guest.getPassword())) {
                bookedRoomCount++;
            }
        }
        if (bookedRoomCount == 0) {
            System.out.print(ConsoleColors.RED);
            System.out.println("No rooms booked\n");
            System.out.print(ConsoleColors.RESET);
        } else {
            System.out.println("Bookings");
            System.out.print(ConsoleColors.BLUE);
            System.out.println("=".repeat(58));
            System.out.print(ConsoleColors.RESET);
            System.out.printf("%5s %12s %12s %10s %14s\n","Room", "type", "size", "nights", "total cost");
            System.out.print(ConsoleColors.BLUE);
            System.out.println("=".repeat(58));
            System.out.print(ConsoleColors.RESET);
            for (Booking book: books) {
                if (book.getGuestName().equals(guest.getName())) {
                    book.showBookedRooms();
                    System.out.println("=".repeat(58));
                }
            }
            System.out.println();
        }
    }

    public static ArrayList<String> yourBookedRooms(Guest guest) throws Exception{
        ArrayList<Booking> books = getBooks();
        ArrayList<String> yourBooks = new ArrayList<>();
        for (Booking book: books) {
            if (book.getGuestName().equals(guest.getName()) && book.getGuestPassword().equals(guest.getPassword())) {
                yourBooks.add(book.getRoom());
            }
        }
        return yourBooks;
    }

    public void showBookedRooms() {
        System.out.printf("%4s %16s %7dm² %7d %13.2f EUR\n", this.room, this.type, this.size, this.nights, this.totalCost);
    }

    public static String bookToCsvRow(Booking book) {
        return book.guest + ", " + book.room + ", " + book.nights + ", " + book.discount + ", " + book.totalCost + ", " + book.guestPassword;
    }
}
