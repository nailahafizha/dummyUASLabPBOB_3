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
    
    // [INI BAGIAN YANG BERUBAH TOTAL]
    // Method ini sekarang menjadi sub-menu untuk Customer
    private static void menuCustomer(Customer c) {
        // Kita buat menu loop untuk customer
        while (true) {
            System.out.println("\n--- Menu Customer: " + c.getNama() + " ---");
            System.out.println("1. Buat Pesanan Baru");
            System.out.println("2. Lihat Status & Bayar Pesanan Saya");
            System.out.println("0. Logout");
            System.out.print("Pilihan: ");
            int pilihan = getInputAngka();
            sc.nextLine(); // consume newline

            if (pilihan == 1) {
                // Panggil logika buat pesanan
                buatPesananBaru(c); // Kita pindah ke method baru
            } else if (pilihan == 2) {
                // Panggil logika bayar
                bayarPesananCustomer(c);
            } else if (pilihan == 0) {
                break; // Keluar dari loop, kembali ke menu utama
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }
    
    private static void menuPegawai(Pegawai p) {
        // Alur untuk Pegawai
        switch (p.getPeran().toLowerCase()) {
            case "pelayan":
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
                        System.out.println("Customer memilih Cash. Memproses pembayaran...");
                        metodePembayaran = new CashPayment();
                    } else {
                        // Jika status "Selesai Dimasak", berarti Kasir yang pilih
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

    // [METHOD HELPER BARU 1]
    // Ini adalah logika 'buatPesanan' yang kita pindah dari menuCustomer lama
    private static void buatPesananBaru(Customer c) {
        try {
            System.out.print("Masukkan Nomor Meja: ");
            int noMeja = getInputAngka();
            sc.nextLine();
            
            int idPesananBaru = system.getDaftarPesanan().size() + 1;
            Meja meja = new Meja(noMeja);
            
            // PENTING: Pastikan Customer.java dan Pesanan.java sudah di-update
            Pesanan pesanan = c.buatPesanan(idPesananBaru, meja); 
            
            while(true) {
                system.lihatMenu(); // Tampilkan menu
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

    // [METHOD HELPER BARU 2]
    // Ini adalah logika baru untuk Customer membayar
    private static void bayarPesananCustomer(Customer c) {
        System.out.println("\n--- Pesanan Saya (ID: " + c.getId() + ") ---");
        
        // 1. Cari pesanan yang 'Selesai Dimasak' DAN milik customer ini
        Pesanan pesananBayar = null;
        for (Pesanan p : system.getDaftarPesananByStatus("Selesai Dimasak")) {
            // PENTING: Pastikan Pesanan.java punya getCustomer()
            if (p.getCustomer().getId() == c.getId()) { 
                pesananBayar = p;
                break; // Ambil satu pesanan saja
            }
        }
        
        // 2. Jika tidak ada yang siap bayar
        if (pesananBayar == null) {
            System.out.println("Anda tidak memiliki pesanan yang siap dibayar.");
            System.out.println("(Pastikan Koki sudah menyelesaikan masakan Anda).");
            return;
        }
        
        // 3. [BILL DITAMPILKAN KE CUSTOMER]
        System.out.println("Pesanan Anda (ID: " + pesananBayar.getIdPesanan() + ") siap dibayar!");
        pesananBayar.tampilkanDetail(); // Tampilkan rincian bill
        
        // 4. [CUSTOMER MEMILIH METODE BAYAR]
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
        
        // 5. Proses pembayaran Card/QRIS
        int idTransaksi = (int) (System.currentTimeMillis() % 10000);
        Transaksi transaksi = new Transaksi(idTransaksi, pesananBayar, metodePembayaran);
        
        // Oper 'sc' untuk proses (jika Pembayaran butuh input)
        transaksi.konfirmasi(sc); 
        
        if(transaksi.isStatusKonfirmasi()) {
            System.out.println("Pembayaran Berhasil!");
            // [STRUK DITAMPILKAN KE CUSTOMER]
            Struk struk = new Struk();
            struk.Cetak(transaksi);
        } else {
            System.out.println("Pembayaran Gagal.");
        }
    }
}
