package dummyUASLabPBOB_3;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RestaurantDriver {
    
    // Kita buat Scanner dan Sistem sebagai static agar bisa diakses
    // oleh method-method helper di bawah.
    private static RestaurantSystem system = new RestaurantSystem();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Selamat Datang di RestaurantDriver!");

        while (true) {
            System.out.println("\n--- Menu Utama ---");
            System.out.println("1. Login");
            System.out.println("2. Register Customer Baru");
            System.out.println("0. Keluar");
            System.out.print("Pilihan: ");
            int pilihan = getInputAngka();
            sc.nextLine(); // consume newline

            switch (pilihan) {
                case 1:
                    loginMenu();
                    break;
                case 2:
                    registerMenu();
                    break;
                case 0:
                    System.out.println("Terima kasih!");
                    sc.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
    
    private static void loginMenu() {
        System.out.print("Masukkan ID: ");
        int id = getInputAngka();
        sc.nextLine(); // consume newline
        System.out.print("Masukkan Password: ");
        String pass = sc.nextLine();
        
        Akun user = system.login(id, pass);
        
        if (user == null) {
            System.out.println("Login gagal. ID atau Password salah.");
            return;
        }
        
        System.out.println("Login berhasil! Selamat datang, " + user.getNama());
        
        // Cek tipe akun dan tampilkan menu yang sesuai
        if (user instanceof Customer) {
            menuCustomer((Customer) user);
        } else if (user instanceof Pegawai) {
            menuPegawai((Pegawai) user);
        }
    }
    
    private static void registerMenu() {
        System.out.print("Masukkan Nama Baru: ");
        String nama = sc.nextLine();
        System.out.print("Masukkan Password Baru: ");
        String pass = sc.nextLine();
        
        Customer c = system.registerCustomer(nama, pass);
        if (c != null) {
            System.out.println("Registrasi berhasil! ID Customer baru Anda adalah: " + c.getId());
        } else {
            System.out.println("Registrasi gagal.");
        }
    }
    
    private static void menuCustomer(Customer c) {
        // Sesuai diagram: Customer bisa lihat menu dan buat pesanan
        system.lihatMenu();
        System.out.print("\nApakah Anda ingin membuat pesanan? (y/n): ");
        String YN = sc.nextLine();
        
        if (!YN.equalsIgnoreCase("y")) {
            return;
        }
        
        // Alur membuat pesanan
        try {
            System.out.print("Masukkan Nomor Meja: ");
            int noMeja = getInputAngka();
            sc.nextLine();
            
            // Siapkan pesanan
            int idPesananBaru = system.getDaftarPesanan().size() + 1;
            Meja meja = new Meja(noMeja);
            
            // Panggil method buatPesanan() dari Customer (sesuai diagram)
            Pesanan pesanan = c.buatPesanan(idPesananBaru, meja); 
            
            while(true) {
                system.lihatMenu();
                System.out.print("Pilih Nomor Menu (0 untuk selesai): ");
                int noMenu = getInputAngka();
                sc.nextLine();
                
                if (noMenu == 0) break;
                
                MenuItem item = system.getMenuByNomor(noMenu);
                if (item == null) {
                    System.out.println("Nomor menu tidak valid.");
                    continue;
                }
                
                System.out.print("Jumlah: ");
                int jumlah = getInputAngka();
                sc.nextLine();
                System.out.print("Catatan (opsional): ");
                String catatan = sc.nextLine();
                
                // Tambah item ke pesanan
                pesanan.tambahItem(new DetailPesanan(item, jumlah, catatan));
                System.out.println(item.getNama() + " ditambahkan.");
            }
            
            // Masukkan pesanan ke sistem
            system.tambahPesanan(pesanan);
            System.out.println("Pesanan berhasil dibuat dengan ID: " + pesanan.getIdPesanan());

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
    
    private static void menuPegawai(Pegawai p) {
        // Alur untuk Pegawai
        switch (p.getPeran().toLowerCase()) {
            case "pelayan":
                // Diagram hanya memberi pelayan 'updateStatusPesanan'
                // Ini bisa dipakai untuk membatalkan atau mengkonfirmasi pesanan
                System.out.println("Menu Pelayan (Update Status Pesanan)");
                System.out.print("Masukkan ID Pesanan: ");
                int id = getInputAngka();
                sc.nextLine();
                Pesanan pesanan = system.findPesananById(id);
                if(pesanan != null) {
                    System.out.print("Masukkan Status Baru (misal: 'Dibatalkan'): ");
                    String status = sc.nextLine();
                    p.updateStatusPesanan(pesanan, status);
                } else {
                    System.out.println("Pesanan tidak ditemukan.");
                }
                break;
                
            case "koki":
                System.out.println("\n--- Daftar Pesanan (Status: Dipesan) ---");
                List<Pesanan> pesananMasuk = system.getDaftarPesananByStatus("Dipesan");
                if (pesananMasuk.isEmpty()) {
                    System.out.println("Tidak ada pesanan untuk dimasak.");
                    return;
                }
                for (Pesanan psn : pesananMasuk) {
                    psn.tampilkanDetail(); // Perlu method helper di Pesanan
                }
                
                System.out.print("Masukkan ID Pesanan yang selesai dimasak: ");
                int idKoki = getInputAngka();
                sc.nextLine();
                Pesanan pesananKoki = system.findPesananById(idKoki);
                if(pesananKoki != null && pesananKoki.getStatus().equals("Dipesan")) {
                    p.updateStatusPesanan(pesananKoki, "Selesai Dimasak");
                    System.out.println("Status pesanan " + idKoki + " diubah menjadi 'Selesai Dimasak'.");
                } else {
                    System.out.println("Pesanan tidak ditemukan atau status tidak valid.");
                }
                break;
                
            case "kasir":
                System.out.println("\n--- Daftar Pesanan (Status: Selesai Dimasak) ---");
                List<Pesanan> pesananSiapBayar = system.getDaftarPesananByStatus("Selesai Dimasak");
                if (pesananSiapBayar.isEmpty()) {
                    System.out.println("Tidak ada pesanan yang siap dibayar.");
                    return;
                }
                for (Pesanan psn : pesananSiapBayar) {
                    psn.tampilkanDetail();
                }
                
                System.out.print("Masukkan ID Pesanan yang akan dibayar: ");
                int idKasir = getInputAngka();
                sc.nextLine();
                Pesanan pesananBayar = system.findPesananById(idKasir);
                
                if (pesananBayar != null && pesananBayar.getStatus().equals("Selesai Dimasak")) {
                    System.out.println("Total Tagihan: Rp " + pesananBayar.hitungTotal());
                    System.out.println("Pilih Metode Pembayaran:");
                    System.out.println("1. Cash");
                    System.out.println("2. Card");
                    System.out.println("3. QRIS");
                    System.out.print("Pilihan: ");
                    int metode = getInputAngka();
                    sc.nextLine();
                    
                    Pembayaran metodePembayaran = null;
                    switch(metode) {
                        case 1: metodePembayaran = new CashPayment(); break;
                        case 2: metodePembayaran = new CardPayment(); break;
                        case 3: metodePembayaran = new QRISPayment(); break;
                        default: System.out.println("Metode tidak valid."); return;
                    }
                    
                    int idTransaksi = (int) (System.currentTimeMillis() % 10000);
                    Transaksi transaksi = new Transaksi(idTransaksi, pesananBayar, metodePembayaran);
                    
                    // Panggil konfirmasi (sesuai diagram)
                    transaksi.konfirmasi(sc); 
                    
                    if(transaksi.isStatusKonfirmasi()) {
                        System.out.println("Pembayaran Berhasil!");
                        // Panggil Struk (sesuai diagram)
                        Struk struk = new Struk();
                        struk.Cetak(transaksi);
                    } else {
                        System.out.println("Pembayaran Gagal.");
                    }
                } else {
                     System.out.println("Pesanan tidak ditemukan atau status tidak valid.");
                }
                break;
        }
    }
    
    // Helper untuk input angka yang aman
    private static int getInputAngka() {
        while(true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Input tidak valid. Harap masukkan angka: ");
                sc.nextLine(); // bersihkan buffer
            }
        }
    }
}