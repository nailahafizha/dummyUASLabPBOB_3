package dummyUASLabPBOB_3;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    private static final String FILE_MENU = "dummyUASLabPBOB_3/menu.txt";
    private static final String FILE_PEGAWAI = "dummyUASLabPBOB_3/pegawai.txt";
    private static final String FILE_CUSTOMER = "dummyUASLabPBOB_3/customer.txt";
    
    public static ArrayList<MenuItem> loadMenu() {
        ArrayList<MenuItem> daftarMenu = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(FILE_MENU))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length < 3) continue;
                try {
                    String tipe = parts[0];
                    String nama = parts[1];
                    int harga = Integer.parseInt(parts[2]); // Sesuai diagram (int)
                    
                    if (tipe.equalsIgnoreCase("Makanan") && parts.length == 5) {
                        int pedas = Integer.parseInt(parts[3]);
                        String kategori = parts[4];
                        daftarMenu.add(new Makanan(nama, harga, pedas, kategori));
                    } else if (tipe.equalsIgnoreCase("Minuman") && parts.length == 5) {
                        String ukuran = parts[3];
                        String suhu = parts[4];
                        daftarMenu.add(new Minuman(nama, harga, ukuran, suhu));
                    }
                } catch (Exception e) { System.err.println("Gagal parse menu: " + e.getMessage()); }
            }
        } catch (FileNotFoundException e) { System.err.println(FILE_MENU + " tidak ditemukan."); }
        return daftarMenu;
    }
    
    public static ArrayList<Pegawai> loadPegawai() {
        ArrayList<Pegawai> daftarPegawai = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(FILE_PEGAWAI))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length == 4) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        daftarPegawai.add(new Pegawai(id, parts[1], parts[2], parts[3]));
                    } catch (Exception e) { System.err.println("Gagal parse pegawai."); }
                }
            }
        } catch (FileNotFoundException e) { System.err.println(FILE_PEGAWAI + " tidak ditemukan."); }
        return daftarPegawai;
    }

    public static ArrayList<Customer> loadCustomer() {
        ArrayList<Customer> daftarCustomer = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(FILE_CUSTOMER))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length == 3) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        daftarCustomer.add(new Customer(id, parts[1], parts[2]));
                    } catch (Exception e) { System.err.println("Gagal parse customer."); }
                }
            }
        } catch (FileNotFoundException e) { System.err.println(FILE_CUSTOMER + " tidak ditemukan."); }
        return daftarCustomer;
    }
    
    public static void saveCustomer(Customer c) {
        try (FileWriter fw = new FileWriter(FILE_CUSTOMER, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(c.getId() + "," + c.getNama() + "," + c.getPassword());
        } catch (IOException e) {
            System.err.println("Gagal menyimpan customer.");
        }
    }
}