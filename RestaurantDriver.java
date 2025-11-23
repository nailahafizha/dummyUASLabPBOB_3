package dummyUASLabPBOB_3;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Kelas utama (Driver) untuk menjalankan sistem restoran
public class RestaurantDriver {
    
    // Objek utama sistem dan Scanner untuk input global
    private static RestaurantSystem system = new RestaurantSystem();
    private static Scanner sc = new Scanner(System.in);

    // Menjalankan loop menu utama
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
                    loginMenu(); // Masuk sebagai Customer atau Pegawai
                    break;
                case 2:
                    registerMenu(); // Mendaftar Customer baru
                    break;
                case 0:
                    System.out.println("Terima kasih!");
                    sc.close();
                    return; // Keluar dari program
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // Menangani proses login
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

    // Menangani registrasi Customer baru dan menyimpan ke file
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
    
    // Menu loop setelah Customer berhasil login
    private static void menuCustomer(Customer c) {
        // Menu loop untuk customer
        while (true) {
            System.out.println("\n--- Menu Customer: " + c.getNama() + " ---");
            System.out.println("1. Buat Pesanan Baru");
            System.out.println("2. Lihat Status & Bayar Pesanan Saya");
            System.out.println("0. Logout");
            System.out.print("Pilihan: ");
            int pilihan = getInputAngka();
            sc.nextLine(); // consume newline

            if (pilihan == 1) {
                buatPesananBaru(c); // Kita pindah ke method buat pesanan
            } else if (pilihan == 2) {
                bayarPesananCustomer(c); // Pindah ke method bayar pesanan
            } else if (pilihan == 0) {
                break; // Keluar dari loop, kembali ke menu utama
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // Menu untuk Pegawai
    private static void menuPegawai(Pegawai p) {
        // Alur untuk Pegawai
        switch (p.getPeran().toLowerCase()) {
            case "pelayan":
                // Pelayan: Update status pesanan (misal: "Diantar", "Dibatalkan")
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
                // Koki: Melihat pesanan 'Dipesan' dan mengubahnya menjadi 'Selesai Dimasak'
                System.out.println("\n--- Daftar Pesanan (Status: Dipesan) ---");
                List<Pesanan> pesananMasuk = system.getDaftarPesananByStatus("Dipesan");
                if (pesananMasuk.isEmpty()) {
                    System.out.println("Tidak ada pesanan untuk dimasak.");
                    return;
                }
                for (Pesanan psn : pesananMasuk) {
                    psn.tampilkanDetail(); 
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
                // Kasir: Melihat pesanan 'Siap Bayar' dan memproses Transaksi
                System.out.println("\n--- Daftar Pesanan (Status: Selesai Dimasak atau Menunggu Pembayaran Cash) ---");
                List<Pesanan> pesananSiapBayar = system.getDaftarPesananByStatus("Selesai Dimasak");
                pesananSiapBayar.addAll(system.getDaftarPesananByStatus("Menunggu Pembayaran Cash")); // Tambahkan pesanan cash

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
                
                // Cek apakah statusnya valid untuk dibayar
                if (pesananBayar != null && (pesananBayar.getStatus().equals("Selesai Dimasak") || pesananBayar.getStatus().equals("Menunggu Pembayaran Cash"))) {
                    
                    Pembayaran metodePembayaran = null;
                    
                    if (pesananBayar.getStatus().equals("Menunggu Pembayaran Cash")) {
                        // Kasir memproses pembayaran Cash yang dipilih Customer
                        System.out.println("Customer memilih Cash. Memproses pembayaran...");
                        metodePembayaran = new CashPayment();
                    } else {
                        // Jika status "Selesai Dimasak", berarti Kasir yang pilih metode pembayaran
                        System.out.println("Total Tagihan: Rp " + pesananBayar.hitungTotal());
                        System.out.println("Pilih Metode Pembayaran:");
                        System.out.println("1. Cash");
                        System.out.println("2. Card");
                        System.out.println("3. QRIS");
                        System.out.print("Pilihan: ");
                        int metode = getInputAngka();
                        sc.nextLine();
                        
                        switch(metode) {
                            case 1: metodePembayaran = new CashPayment(); break;
                            case 2: metodePembayaran = new CardPayment(); break;
                            case 3: metodePembayaran = new QRISPayment(); break;
                            default: System.out.println("Metode tidak valid."); return;
                        }
                    }
                    
                    int idTransaksi = (int) (System.currentTimeMillis() % 10000);
                    Transaksi transaksi = new Transaksi(idTransaksi, pesananBayar, metodePembayaran);
                    
                    // Panggil konfirmasi (oper 'sc' untuk CashPayment)
                    transaksi.konfirmasi(sc); 
                    
                    if(transaksi.isStatusKonfirmasi()) {
                        System.out.println("Pembayaran Berhasil!");
                        Struk struk = new Struk();
                        struk.Cetak(transaksi); // Cetak struk
                    } else {
                        System.out.println("Pembayaran Gagal.");
                    }
                } else {
                     System.out.println("Pesanan tidak ditemukan atau status tidak valid.");
                }
                break;
        }
    }
    
    // Untuk input angka yang aman
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

    // Untuk membuat objek Pesanan dan DetailPesanan
    private static void buatPesananBaru(Customer c) {
        try {
            System.out.print("Masukkan Nomor Meja: ");
            int noMeja = getInputAngka();
            sc.nextLine();
            
            int idPesananBaru = system.getDaftarPesanan().size() + 1;
            Meja meja = new Meja(noMeja);
            
            // Customer membuat objek Pesanan
            Pesanan pesanan = c.buatPesanan(idPesananBaru, meja); 
            
            while(true) {
                system.lihatMenu(); // Tampilkan daftar menu
                System.out.print("Pilih Nomor Menu (0 untuk selesai): ");
                int noMenu = getInputAngka();
                sc.nextLine();
                if (noMenu == 0) break;
                
                MenuItem item = system.getMenuByNomor(noMenu);
                if (item == null) {
                    System.out.println("Nomor menu tidak valid.");
                    continue;
                }

                // Meminta detail dan menambahkan DetailPesanan ke Pesanan
                System.out.print("Jumlah: ");
                int jumlah = getInputAngka();
                sc.nextLine();
                System.out.print("Catatan (opsional): ");
                String catatan = sc.nextLine();
                
                pesanan.tambahItem(new DetailPesanan(item, jumlah, catatan));
                System.out.println(item.getNama() + " ditambahkan.");
            }
            
            if (pesanan.getDaftarItem().isEmpty()) {
                System.out.println("Pesanan dibatalkan (tidak ada item).");
            } else {
                system.tambahPesanan(pesanan);
                System.out.println("Pesanan berhasil dibuat dengan ID: " + pesanan.getIdPesanan());
                System.out.println("Silakan tunggu Koki memasak pesanan Anda.");
            }

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    // Untuk melihat status dan memproses pembayaran pesanan customer
    private static void bayarPesananCustomer(Customer c) {
        System.out.println("\n--- Pesanan Saya (ID: " + c.getId() + ") ---");
        
        // Cari pesanan milik Customer yang sudah 'Selesai Dimasak'
        Pesanan pesananBayar = null;
        for (Pesanan p : system.getDaftarPesananByStatus("Selesai Dimasak")) {
            if (p.getCustomer().getId() == c.getId()) { 
                pesananBayar = p;
                break; // Ambil satu pesanan saja
            }
        }
        
        // Jika tidak ada yang siap bayar
        if (pesananBayar == null) {
            System.out.println("Anda tidak memiliki pesanan yang siap dibayar.");
            System.out.println("(Pastikan Koki sudah menyelesaikan masakan Anda).");
            return;
        }
        
        // Tampilkan tagihan dan rincian
        System.out.println("Pesanan Anda (ID: " + pesananBayar.getIdPesanan() + ") siap dibayar!");
        pesananBayar.tampilkanDetail(); 
        
        // Customer Memilihan metode pembayaran
        System.out.println("Pilih Metode Pembayaran:");
        System.out.println("1. Cash (Bayar di Kasir)");
        System.out.println("2. Card (Proses Sekarang)");
        System.out.println("3. QRIS (Proses Sekarang)");
        System.out.print("Pilihan: ");
        int metode = getInputAngka();
        sc.nextLine();
        
        Pembayaran metodePembayaran = null;
        switch(metode) {
            case 1: 
                // Jika cash, Customer hanya tandai, Kasir yang selesaikan
                pesananBayar.setStatus("Menunggu Pembayaran Cash");
                System.out.println("Status pesanan diubah. Silakan lakukan pembayaran di Kasir.");
                return; // Selesai
            case 2: metodePembayaran = new CardPayment(); break;
            case 3: metodePembayaran = new QRISPayment(); break;
            default: System.out.println("Metode tidak valid."); return;
        }
        
        // Proses pembayaran Card/QRIS
        int idTransaksi = (int) (System.currentTimeMillis() % 10000);
        Transaksi transaksi = new Transaksi(idTransaksi, pesananBayar, metodePembayaran);
        
        transaksi.konfirmasi(sc); // Eksekusi pembayaran
        
        if(transaksi.isStatusKonfirmasi()) {
            System.out.println("Pembayaran Berhasil!");
            // Struk ditampilkan ke customer
            Struk struk = new Struk();
            struk.Cetak(transaksi);
        } else {
            System.out.println("Pembayaran Gagal.");
        }
    }
}
