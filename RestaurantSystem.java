package dummyUASLabPBOB_3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantSystem {
    
    // Daftar data utama
    private List<MenuItem> daftarMenu;
    private List<Pegawai> daftarPegawai;
    private List<Customer> daftarCustomer;
    // Daftar pesanan aktif
    private List<Pesanan> daftarPesanan;

    public RestaurantSystem() {
        // Load semua data dari file saat sistem dinyalakan
        this.daftarMenu = FileManager.loadMenu();
        this.daftarPegawai = FileManager.loadPegawai();
        this.daftarCustomer = FileManager.loadCustomer();
        this.daftarPesanan = new ArrayList<>(); // Pesanan dimulai kosong
    }

    // Menambahkan objek Pesanan baru ke daftar pesanan aktif
    public void tambahPesanan(Pesanan pesanan) {
        this.daftarPesanan.add(pesanan);
    }

    // Menampilkan semua item dalam menu
    public void lihatMenu() {
        System.out.println("\n--- Daftar Menu Restoran ---");
        for (int i = 0; i < daftarMenu.size(); i++) {
            // Tampilkan nomor urut (i+1)
            System.out.println((i + 1) + ". " + daftarMenu.get(i).getInfo());
        }
    }
    
    // --- Method Helper untuk Driver ---
    
    // Mengembalikan seluruh daftar pesanan aktif
    public List<Pesanan> getDaftarPesanan() {
        return this.daftarPesanan;
    }

    // Memproses login
    public Akun login(int id, String password) {
        // Cek di pegawai
        for (Pegawai p : daftarPegawai) {
            if (p.getId() == id && p.getPassword().equals(password)) {
                return p;
            }
        }
        // Cek di customer
        for (Customer c : daftarCustomer) {
            if (c.getId() == id && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null; // Tidak ketemu
    }

    // Mendaftarkan Customer baru
    public Customer registerCustomer(String nama, String pass) {
        // Buat ID baru (ID customer terakhir + 1)
        int newId = 101; // default
        if (!daftarCustomer.isEmpty()) {
            newId = daftarCustomer.get(daftarCustomer.size() - 1).getId() + 1;
        }
        Customer c = new Customer(newId, nama, pass);
        daftarCustomer.add(c);
        FileManager.saveCustomer(c); // Simpan ke .txt
        return c;
    }
    
    // Mengambil menu berdasarkan NOMOR URUT (bukan ID)
    public MenuItem getMenuByNomor(int nomor) {
        int index = nomor - 1; // Konversi ke 0-based index
        if (index >= 0 && index < daftarMenu.size()) {
            return daftarMenu.get(index);
        }
        return null;
    }

    // Mencari dan mengembalikan objek Pesanan berdasarkan ID Pesanan
    public Pesanan findPesananById(int id) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == id) {
                return p;
            }
        }
        return null;
    }

    // Mengambil List Pesanan yang memiliki status tertentu (misal "Dipesan")
    public List<Pesanan> getDaftarPesananByStatus(String status) {
        // Menggunakan Java Stream untuk memfilter daftar pesanan
        return daftarPesanan.stream()
            .filter(p -> p.getStatus().equalsIgnoreCase(status))
            .collect(Collectors.toList());
    }
}
