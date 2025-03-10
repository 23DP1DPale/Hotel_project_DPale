package src;

public class Room {
    private String type;
    private double size;
    private double cost;

    public Room(String type, double size, double cost) {
        this.type = type;
        this.size = size;
        this.cost = cost;
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
}
