package booking_gamecenter;

public abstract class Konsol {

    protected final String nama;
    protected final int hargaPerJam;

    protected Konsol(String nama, int hargaPerJam) {
        this.nama = nama;
        this.hargaPerJam = hargaPerJam;
    }

    public String getNama() {
        return nama;
    }

    public int getHargaPerJam() {
        return hargaPerJam;
    }

    public abstract String deskripsi();

    @Override
    public String toString() {
        return nama;
    }
}
