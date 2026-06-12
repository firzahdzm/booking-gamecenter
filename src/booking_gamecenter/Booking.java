package booking_gamecenter;

import java.time.LocalDate;

public class Booking {

    private final int id;
    private final Member pemesan;
    private final Station station;
    private final LocalDate tanggal;
    private final int jamMulai;
    private final int durasiJam;
    private final int totalBiaya;
    private StatusBooking status;

    public Booking(int id, Member pemesan, Station station, LocalDate tanggal,
            int jamMulai, int durasiJam) {
        this.id = id;
        this.pemesan = pemesan;
        this.station = station;
        this.tanggal = tanggal;
        this.jamMulai = jamMulai;
        this.durasiJam = durasiJam;
        this.totalBiaya = station.hitungBiaya(durasiJam);
        this.status = StatusBooking.AKTIF;
    }

    public int getId() {
        return id;
    }

    public Member getPemesan() {
        return pemesan;
    }

    public Station getStation() {
        return station;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public int getJamMulai() {
        return jamMulai;
    }

    public int getDurasiJam() {
        return durasiJam;
    }

    public int getJamSelesai() {
        return jamMulai + durasiJam;
    }

    public int getTotalBiaya() {
        return totalBiaya;
    }

    public StatusBooking getStatus() {
        return status;
    }

    public void batalkan() {
        status = StatusBooking.DIBATALKAN;
    }

    public boolean bentrokDengan(Station st, LocalDate tgl, int jam, int durasi) {
        if (status != StatusBooking.AKTIF) {
            return false;
        }
        if (!station.getKode().equals(st.getKode())) {
            return false;
        }
        if (!tanggal.equals(tgl)) {
            return false;
        }
        return jam < getJamSelesai() && jamMulai < jam + durasi;
    }
}
