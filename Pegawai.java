package dummyUASLabPBOB_3;

public class Pegawai extends Akun {
    private String peran;

    // Konstruktor
    public Pegawai(int id, String nama, String password, String peran) {
        super(id, nama, password); // Panggil konstruktor superclass Akun
        this.peran = peran;
    }

    // Mengembalikan peran pegawai
    public String getPeran() { return peran; }

    // Mengubah status Pesanan
    public void updateStatusPesanan(Pesanan pesanan, String status) {
        pesanan.setStatus(status);
        System.out.println("Status pesanan " + pesanan.getIdPesanan() + " diupdate menjadi: " + status);
    }
}
