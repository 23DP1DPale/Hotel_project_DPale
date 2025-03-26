public class Booking {
    public String guest;
    public String room; 
    public int days;
    public double totalCost;
    
    public Booking(String name, String room, int days, double totalCost) {
        this.guest = name;
        this.room = room;
        this.days = days;
    }
}
