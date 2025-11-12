import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Waiter waiter = new Waiter("pelayan", "123");

        List<MenuItem> menuList = new ArrayList<>();
        menuList.add(new MenuItem("Nasi Goreng", 20000));
        menuList.add(new MenuItem("Mie Ayam", 18000));
        menuList.add(new MenuItem("Es Teh", 5000));
        menuList.add(new MenuItem("Jus Jeruk", 8000));

        System.out.println("=== SISTEM MANAJEMEN RESTORAN ===");
        System.out.println("1. Login sebagai Pegawai");
        System.out.println("2. Masuk sebagai Customer");
        System.out.print("Pilih opsi (1/2): ");
        int pilihan = scanner.nextInt();
        scanner.nextLine(); 
        if (pilihan == 1) {
            // Login pegawai
            System.out.print("Masukkan username: ");
            String user = scanner.nextLine();
            System.out.print("Masukkan password: ");
            String pass = scanner.nextLine();

            if (waiter.login(user, pass)) {
                System.out.println("\nLogin berhasil sebagai Pegawai!");
                System.out.print("Masukkan nama pelanggan: ");
                String namaCust = scanner.nextLine();

                Customer customer = new Customer(namaCust, "123");
                System.out.println("\nData Pelanggan: " + customer.getName());

                Order order = waiter.createOrder(customer, menuList, scanner);
                order.printReceipt();
                System.out.println("Pesanan selesai!");
            } else {
                System.out.println("Login gagal! Username atau password salah.");
            }

        } else if (pilihan == 2) {
            // Masuk sebagai customer
            System.out.print("Buat username Anda: ");
            String user = scanner.nextLine();
            System.out.print("Buat password Anda: ");
            String pass = scanner.nextLine();

            Customer customer = new Customer(user, pass);

            System.out.println("\nHalo, " + customer.getName() + "! Anda berhasil masuk sebagai Customer.\n");

            System.out.println("--Menu--");
            for (int i = 0; i < menuList.size(); i++) {
                System.out.println((i + 1) + ". " + menuList.get(i));
            }

            System.out.println("\nIngin langsung memesan? (y/n): ");
            String jawab = scanner.nextLine();

            if (jawab.equalsIgnoreCase("y")) {
                Order order = new Order(customer);
                while (true) {
                    System.out.print("Pilih nomor menu (0 untuk selesai): ");
                    int menuPilihan = scanner.nextInt();
                    if (menuPilihan == 0) break;

                    if (menuPilihan > 0 && menuPilihan <= menuList.size()) {
                        order.addItem(menuList.get(menuPilihan - 1));
                        System.out.println(menuList.get(menuPilihan - 1).getName() + " ditambahkan ke pesanan.");
                    } else {
                        System.out.println("Menu tidak valid.");
                    }
                }
                order.printReceipt();
            } else {
                System.out.println("Silakan lihat-lihat dulu 😄");
            }

        } else {
            System.out.println("Pilihan tidak valid!");
        }

        scanner.close();
    }
}
