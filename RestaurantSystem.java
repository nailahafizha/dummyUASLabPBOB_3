package dummyUASLabPBOB_3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantSystem {

    private List<MenuItem> daftarMenu;
    private List<Pegawai> daftarPegawai;
    private List<Customer> daftarCustomer;
    private List<Pesanan> daftarPesanan;

    public RestaurantSystem() {
        this.daftarMenu = FileManager.loadMenu();
        this.daftarPegawai = FileManager.loadPegawai();
        this.daftarCustomer = FileManager.loadCustomer();
        this.daftarPesanan = new ArrayList<>();
    }

    public void tambahPesanan(Pesanan pesanan) {
        this.daftarPesanan.add(pesanan);
    }

    public void lihatMenu() {
        System.out.println("\n--- Daftar Menu Restoran ---");
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.println((i + 1) + ". " + daftarMenu.get(i).getInfo());
        }
    }

    public List<Pesanan> getDaftarPesanan() {
        return this.daftarPesanan;
    }

    public Akun login(int id, String password) {
        for (Pegawai p : daftarPegawai) {
            if (p.getId() == id && p.getPassword().equals(password)) return p;
        }
        for (Customer c : daftarCustomer) {
            if (c.getId() == id && c.getPassword().equals(password)) return c;
        }
        return null;
    }

    public Customer registerCustomer(String nama, String pass) {
        int newId = 101;
        if (!daftarCustomer.isEmpty()) {
            newId = daftarCustomer.get(daftarCustomer.size() - 1).getId() + 1;
        }
        Customer c = new Customer(newId, nama, pass);
        daftarCustomer.add(c);
        FileManager.saveCustomer(c);
        return c;
    }

    public MenuItem getMenuByNomor(int nomor) {
        int index = nomor - 1;
        if (index >= 0 && index < daftarMenu.size()) return daftarMenu.get(index);
        return null;
    }

    public Pesanan findPesananById(int id) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == id) return p;
        }
        return null;
    }

    public List<Pesanan> getDaftarPesananByStatus(String status) {
        return daftarPesanan.stream()
                .filter(p -> p.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    // ===== helper GUI =====
    public List<MenuItem> getDaftarMenu() {
        return this.daftarMenu;
    }

    public Customer findCustomerById(int id) {
        for (Customer c : daftarCustomer) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public int generateIdPesananBaru() {
        if (daftarPesanan.isEmpty()) return 1;
        return daftarPesanan.get(daftarPesanan.size() - 1).getIdPesanan() + 1;
    }

    public int generateIdTransaksiBaru() {
        long countLunas = daftarPesanan.stream()
                .filter(p -> p.getStatus().equalsIgnoreCase("Lunas"))
                .count();
        return (int) countLunas + 1;
    }
}
