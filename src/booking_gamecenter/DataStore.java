package booking_gamecenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    private static final File FILE_AKUN = new File("data/akun.csv");
    private static final File FILE_BOOKING = new File("data/booking.csv");

    private static final ArrayList<Pengguna> daftarAkun = new ArrayList<>();
    private static final ArrayList<Konsol> daftarKonsol = new ArrayList<>();
    private static final ArrayList<Station> daftarStation = new ArrayList<>();
    private static final ArrayList<Booking> daftarBooking = new ArrayList<>();
    private static int nextId = 1;

    static {
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

        if (FILE_AKUN.exists()) {
            muatAkun();
        } else {
            daftarAkun.add(new Admin("Administrator", "admin", "admin123"));
            daftarAkun.add(new Member("Firza", "firza", "firza123"));
            daftarAkun.add(new Member("Banis", "banis", "banis123"));
            daftarAkun.add(new Member("Nanda", "nanda", "nanda123"));
            daftarAkun.add(new Member("Ilham", "ilham", "ilham123"));
            simpanAkun();
        }
        if (FILE_BOOKING.exists()) {
            muatBooking();
        }
    }

    private static void muatAkun() {
        daftarAkun.clear();
        try (BufferedReader pembaca = new BufferedReader(new FileReader(FILE_AKUN))) {
            String baris;
            while ((baris = pembaca.readLine()) != null) {
                if (baris.isBlank()) {
                    continue;
                }
                String[] kolom = baris.split(";");
                if (kolom.length < 4) {
                    continue;
                }
                if (kolom[0].equals("ADMIN")) {
                    daftarAkun.add(new Admin(kolom[1], kolom[2], kolom[3]));
                } else {
                    daftarAkun.add(new Member(kolom[1], kolom[2], kolom[3]));
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal membaca " + FILE_AKUN + ": " + e.getMessage());
        }
    }

    private static void muatBooking() {
        daftarBooking.clear();
        try (BufferedReader pembaca = new BufferedReader(new FileReader(FILE_BOOKING))) {
            String baris;
            while ((baris = pembaca.readLine()) != null) {
                if (baris.isBlank()) {
                    continue;
                }
                String[] kolom = baris.split(";");
                if (kolom.length < 7) {
                    continue;
                }
                Member pemesan = cariMember(kolom[1]);
                Station station = cariStation(kolom[2]);
                if (pemesan == null || station == null) {
                    continue;
                }
                Booking b = new Booking(Integer.parseInt(kolom[0]), pemesan, station,
                        LocalDate.parse(kolom[3]), Integer.parseInt(kolom[4]),
                        Integer.parseInt(kolom[5]));
                if (kolom[6].equals(StatusBooking.DIBATALKAN.toString())) {
                    b.batalkan();
                }
                daftarBooking.add(b);
                if (b.getId() >= nextId) {
                    nextId = b.getId() + 1;
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal membaca " + FILE_BOOKING + ": " + e.getMessage());
        }
    }

    private static void simpanAkun() {
        FILE_AKUN.getParentFile().mkdirs();
        try (PrintWriter penulis = new PrintWriter(new FileWriter(FILE_AKUN))) {
            for (Pengguna p : daftarAkun) {
                penulis.println(p.keCsv());
            }
        } catch (IOException e) {
            System.err.println("Gagal menyimpan " + FILE_AKUN + ": " + e.getMessage());
        }
    }

    private static void simpanBooking() {
        FILE_BOOKING.getParentFile().mkdirs();
        try (PrintWriter penulis = new PrintWriter(new FileWriter(FILE_BOOKING))) {
            for (Booking b : daftarBooking) {
                penulis.println(b.keCsv());
            }
        } catch (IOException e) {
            System.err.println("Gagal menyimpan " + FILE_BOOKING + ": " + e.getMessage());
        }
    }

    private static Member cariMember(String username) {
        for (Pengguna p : daftarAkun) {
            if (p instanceof Member && p.getUsername().equals(username)) {
                return (Member) p;
            }
        }
        return null;
    }

    private static Station cariStation(String kode) {
        for (Station s : daftarStation) {
            if (s.getKode().equals(kode)) {
                return s;
            }
        }
        return null;
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
        simpanAkun();
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
        simpanBooking();
        return baru;
    }

    public static void batalkanBooking(Booking booking) {
        booking.batalkan();
        simpanBooking();
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
