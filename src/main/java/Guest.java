public class Guest {
    private String name;
    private double balance;

    public Guest(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) {
        balance = newBalance;
    }

    public void showOptions() {
        System.out.println("Choose one of the option:");
        System.out.println("Book - book a room for a certain time period");
        System.out.println("Rooms - shows all rooms");
        System.out.println("Balance - check your balance or deposit money");
        System.out.println("Exit");
    }

    public void checkOption(String option) {
        if (option.matches("(B|b)ook")) {
            book();
        } else if (option.matches("(R|r)ooms)")) {
            showRooms();
        }
    }

    public void book() {
        
    }

    public void showRooms() {

    }
}
