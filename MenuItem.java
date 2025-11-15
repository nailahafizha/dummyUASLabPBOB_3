package dummyUASLabPBOB_3;

public abstract class MenuItem {
    private String nama;
    private int harga; // Sesuai diagram: int

    public MenuItem(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() { return nama; }
    public int getHarga() { return harga; }

    // Sesuai diagram: +getInfo()
    public abstract String getInfo();
    
    @Override
    public String toString() {
        return getInfo();
    }
}