package dummyUASLabPBOB_3;

public class Pegawai extends Akun {
    private String peran;

    public Pegawai(int id, String nama, String password, String peran) {
        super(id, nama, password);
        this.peran = peran;
    }

    public String getPeran() {
         return peran; 
        }

    public void updateStatusPesanan(Pesanan pesanan, String status) {
        pesanan.setStatus(status);
        System.out.println("Status pesanan " + pesanan.getIdPesanan() + " diupdate menjadi: " + status);
    }
}
