package booking_gamecenter;

public class Station implements BisaDibooking {

    private final String kode;
    private final Konsol konsol;

    public Station(String kode, Konsol konsol) {
        this.kode = kode;
        this.konsol = konsol;
    }

    @Override
    public String getKode() {
        return kode;
    }

    public Konsol getKonsol() {
        return konsol;
    }

    @Override
    public int hitungBiaya(int durasiJam) {
        return konsol.getHargaPerJam() * durasiJam;
    }

    @Override
    public String toString() {
        return kode + " (" + konsol.getNama() + ")";
    }
}
