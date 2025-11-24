package dummyUASLabPBOB_3;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// class untuk operasi baca (load) dan tulis (save) data dari file
public class FileManager {

    // Konstanta path file untuk data Menu
    private static final String FILE_MENU = "dummyUASLabPBOB_3/menu.txt";
    // Konstanta path file untuk data Pegawai
    private static final String FILE_PEGAWAI = "dummyUASLabPBOB_3/pegawai.txt";
    // Konstanta path file untuk data Customer
    private static final String FILE_CUSTOMER = "dummyUASLabPBOB_3/customer.txt";

    // Memuat (load) daftar menu dari FILE_MENU
    public static ArrayList<MenuItem> loadMenu() {
        ArrayList<MenuItem> daftarMenu = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(FILE_MENU))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length < 3) continue;
                try {
                    String tipe = parts[0];
                    String nama = parts[1];
                    int harga = Integer.parseInt(parts[2]); 

                    // Cek tipe "Makanan"
                    if (tipe.equalsIgnoreCase("Makanan") && parts.length == 5) {
                        int pedas = Integer.parseInt(parts[3]);
                        String kategori = parts[4];
                        // Membuat dan menambahkan objek Makanan
                        daftarMenu.add(new Makanan(nama, harga, pedas, kategori));

                    // Cek tipe "Minuman"
                    } else if (tipe.equalsIgnoreCase("Minuman") && parts.length == 5) {
                        String ukuran = parts[3];
                        String suhu = parts[4];
                        // Membuat dan menambahkan objek Minuman
                        daftarMenu.add(new Minuman(nama, harga, ukuran, suhu));
                    }
                } catch (Exception e) { System.err.println("Gagal parse menu: " + e.getMessage()); }
            }
        } catch (FileNotFoundException e) { System.err.println(FILE_MENU + " tidak ditemukan."); }
        return daftarMenu;
    }

    // Memuat (load) daftar pegawai dari FILE_PEGAWAI
    public static ArrayList<Pegawai> loadPegawai() {
        ArrayList<Pegawai> daftarPegawai = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(FILE_PEGAWAI))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length == 4) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        // Membuat objek Pegawai
                        daftarPegawai.add(new Pegawai(id, parts[1], parts[2], parts[3]));
                    } catch (Exception e) { System.err.println("Gagal parse pegawai."); }
                }
            }
        } catch (FileNotFoundException e) { System.err.println(FILE_PEGAWAI + " tidak ditemukan."); }
        return daftarPegawai;
    }

    // Memuat (load) daftar customer dari FILE_CUSTOMER
    public static ArrayList<Customer> loadCustomer() {
        ArrayList<Customer> daftarCustomer = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(FILE_CUSTOMER))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length == 3) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        // Membuat objek Customer
                        daftarCustomer.add(new Customer(id, parts[1], parts[2]));
                    } catch (Exception e) { System.err.println("Gagal parse customer."); }
                }
            }
        } catch (FileNotFoundException e) { System.err.println(FILE_CUSTOMER + " tidak ditemukan."); }
        return daftarCustomer;
    }

    // Menyimpan objek Customer 
    public static void saveCustomer(Customer c) {
        try (FileWriter fw = new FileWriter(FILE_CUSTOMER, true); // true = append mode
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(c.getId() + "," + c.getNama() + "," + c.getPassword());
        } catch (IOException e) {
            System.err.println("Gagal menyimpan customer.");
        }
    }
}