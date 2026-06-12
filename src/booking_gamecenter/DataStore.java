package booking_gamecenter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public final class DataStore {

    public static final int JAM_BUKA = 10;
    public static final int JAM_TUTUP = 24;
    public static final int DURASI_MIN = 1;
    public static final int DURASI_MAX = 6;
    public static final int HARI_KE_DEPAN = 6;
    public static final DateTimeFormatter FORMAT_TANGGAL = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final ArrayList<Pengguna> daftarAkun = new ArrayList<>();
    private static final ArrayList<Konsol> daftarKonsol = new ArrayList<>();
    private static final ArrayList<Station> daftarStation = new ArrayList<>();
    private static final ArrayList<Booking> daftarBooking = new ArrayList<>();
    private static int nextId = 1;

    static {
        daftarAkun.add(new Admin("Administrator", "admin", "admin123"));
        daftarAkun.add(new Member("Firza", "firza", "firza123"));
        daftarAkun.add(new Member("Banis", "banis", "banis123"));
        daftarAkun.add(new Member("Nanda", "nanda", "nanda123"));
        daftarAkun.add(new Member("Ilham", "ilham", "ilham123"));

        Konsol ps5 = new PS5();
        Konsol ps4 = new PS4();
        Konsol nintendo = new Nintendo();
        Konsol xbox = new Xbox();
        daftarKonsol.add(ps5);
        daftarKonsol.add(ps4);
        daftarKonsol.add(nintendo);
        daftarKonsol.add(xbox);

        daftarStation.add(new Station("PS5-01", ps5));
        daftarStation.add(new Station("PS5-02", ps5));
        daftarStation.add(new Station("PS5-03", ps5));
        daftarStation.add(new Station("PS4-01", ps4));
        daftarStation.add(new Station("PS4-02", ps4));
        daftarStation.add(new Station("PS4-03", ps4));
        daftarStation.add(new Station("NS-01", nintendo));
        daftarStation.add(new Station("NS-02", nintendo));
        daftarStation.add(new Station("XB-01", xbox));
        daftarStation.add(new Station("XB-02", xbox));
    }

    private DataStore() {
    }

    public static Pengguna login(String username, String password) {
        for (Pengguna p : daftarAkun) {
            if (p.getUsername().equals(username) && p.cekPassword(password)) {
                return p;
            }
        }
        return null;
    }

    public static boolean usernameDipakai(String username) {
        for (Pengguna p : daftarAkun) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public static void daftarMember(String nama, String username, String password) {
        daftarAkun.add(new Member(nama, username, password));
    }

    public static ArrayList<Konsol> daftarJenisKonsol() {
        return new ArrayList<>(daftarKonsol);
    }

    public static ArrayList<Station> stationDenganKonsol(Konsol konsol) {
        ArrayList<Station> hasil = new ArrayList<>();
        for (Station s : daftarStation) {
            if (s.getKonsol().getNama().equals(konsol.getNama())) {
                hasil.add(s);
            }
        }
        return hasil;
    }

    public static ArrayList<LocalDate> pilihanTanggal() {
        ArrayList<LocalDate> hasil = new ArrayList<>();
        LocalDate hariIni = LocalDate.now();
        for (int i = 0; i <= HARI_KE_DEPAN; i++) {
            hasil.add(hariIni.plusDays(i));
        }
        return hasil;
    }

    public static Booking buatBooking(Member pemesan, Station station, LocalDate tanggal,
            int jamMulai, int durasiJam) throws BentrokException {
        for (Booking b : daftarBooking) {
            if (b.bentrokDengan(station, tanggal, jamMulai, durasiJam)) {
                throw new BentrokException(b);
            }
        }
        Booking baru = new Booking(nextId++, pemesan, station, tanggal, jamMulai, durasiJam);
        daftarBooking.add(baru);
        return baru;
    }

    public static boolean jamTersedia(Station station, LocalDate tanggal, int jamMulai, int durasiJam) {
        for (Booking b : daftarBooking) {
            if (b.bentrokDengan(station, tanggal, jamMulai, durasiJam)) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Booking> bookingMilik(Member member) {
        ArrayList<Booking> hasil = new ArrayList<>();
        for (Booking b : daftarBooking) {
            if (b.getPemesan().getUsername().equals(member.getUsername())) {
                hasil.add(b);
            }
        }
        return hasil;
    }

    public static ArrayList<Booking> semuaBooking() {
        return new ArrayList<>(daftarBooking);
    }

    public static int jumlahBookingAktif() {
        int jumlah = 0;
        for (Booking b : daftarBooking) {
            if (b.getStatus() == StatusBooking.AKTIF) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public static String formatRupiah(int nilai) {
        String angka = Integer.toString(nilai);
        StringBuilder hasil = new StringBuilder();
        int hitung = 0;
        for (int i = angka.length() - 1; i >= 0; i--) {
            hasil.insert(0, angka.charAt(i));
            hitung++;
            if (hitung % 3 == 0 && i > 0) {
                hasil.insert(0, '.');
            }
        }
        return "Rp " + hasil;
    }
}
