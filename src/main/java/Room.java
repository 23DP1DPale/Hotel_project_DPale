public class Room {
    private String type;
    private double size;
    private double cost;
    private boolean available;
    private String guest;

    public Room(String type, double size, double cost, String guest) {
        this.type = type;
        this.size = size;
        this.cost = cost;
        this.available = true;
        this.guest = null;
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
}
