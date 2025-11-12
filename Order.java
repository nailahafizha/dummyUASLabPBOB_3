import java.util.*;

class Order {
    private Customer customer;
    private List<MenuItem> items = new ArrayList<>();

    public Order(Customer customer) {
        this.customer = customer;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void printReceipt() {
        System.out.println("\n--- STRUK PESANAN ---");
        System.out.println("\nCustomer: " + customer.getName());
        double total = 0;
        for (MenuItem item : items) {
            System.out.println("" + item.getName() + " : Rp" + item.getPrice());
            total += item.getPrice();
        }
        System.out.println("\nTotal: Rp" + total);
        System.out.println("---------------------");
    }
}
