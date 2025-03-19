public class Admin {
    private String name;

    public Admin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void showOptions() {
        System.out.println("Choose one of the option:");
        System.out.println("Cost - change room cost per night");
        System.out.println("Availability - set rooms to be avaible or unavaible for guests");
        System.out.println("Discount - add a discount for a room");
        System.out.println("Exit");
    }

    
}
