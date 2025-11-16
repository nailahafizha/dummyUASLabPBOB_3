package dummyUASLabPBOB_3;


public class Customer extends Akun {
    public Customer(int id, String nama, String password) {
        super(id, nama, password);
    }

    // Sesuai diagram: +buatPesanan()
    // Method ini membuat dan mengembalikan objek Pesanan baru
    public Pesanan buatPesanan(int idPesanan, Meja meja) {
        System.out.println("Customer " + getNama() + " membuat pesanan baru (ID: " + idPesanan + ").");
        
        // [PERBAIKAN DI SINI]
        // Kita tambahkan 'this' (objek Customer ini)
        // agar constructor Pesanan yang baru (3 argumen) terpenuhi
        return new Pesanan(idPesanan, meja, this); 
    }
}