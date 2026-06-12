package booking_gamecenter;

import java.time.format.DateTimeFormatter;

public class BentrokException extends Exception {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BentrokException(Booking penabrak) {
        super("Jadwal bentrok dengan Booking #" + penabrak.getId()
                + " di " + penabrak.getStation().getKode()
                + " tanggal " + penabrak.getTanggal().format(FORMAT)
                + " jam " + penabrak.getJamMulai() + ":00-" + penabrak.getJamSelesai() + ":00"
                + " atas nama " + penabrak.getPemesan().getNama() + ".");
    }
}
