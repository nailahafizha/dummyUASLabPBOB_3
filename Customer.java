package dummyUASLabPBOB_3;

public class Customer extends Akun {

    public Customer(int id, String nama, String password) {
        super(id, nama, password);
    }

    public Pesanan buatPesanan(int idPesanan, Meja meja) {
        System.out.println("Customer " + getNama() + " membuat pesanan baru (ID: " + idPesanan + ").");
        return new Pesanan(idPesanan, meja, this);
    }
}
