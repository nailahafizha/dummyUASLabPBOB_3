import java.util.*;

class Waiter extends User {
    public Waiter(String username, String password) {
        super(username, password, "Waiter");
    }

    public Order createOrder(Customer customer, List<MenuItem> menuList, Scanner scanner) {
        Order order = new Order(customer);
        while (true) {
            System.out.println("\nPilih menu (0 untuk selesai):");
            for (int i = 0; i < menuList.size(); i++) {
                System.out.println((i + 1) + ". " + menuList.get(i));
            }
            System.out.print("Nomor menu: ");
            int choice = scanner.nextInt();
            if (choice == 0) break;
            if (choice > 0 && choice <= menuList.size()) {
                order.addItem(menuList.get(choice - 1));
                System.out.println(menuList.get(choice - 1).getName() + " ditambahkan ke pesanan.");
            } else {
                System.out.println("Menu tidak valid!");
            }
        }
        return order;
    }
}
