package dummyUASLabPBOB_3;

public class Customer extends Akun {

    // Konstruktor Customer. Memanggil konstruktor superclass Akun
    public Customer(int id, String nama, String password) {
        super(id, nama, password);
    }

    // Membuat dan mengembalikan objek Pesanan baru
    public Pesanan buatPesanan(int idPesanan, Meja meja) {
        System.out.println("Customer " + getNama() + " membuat pesanan baru (ID: " + idPesanan + ").");
        
        return new Pesanan(idPesanan, meja, this); 
    }
}
