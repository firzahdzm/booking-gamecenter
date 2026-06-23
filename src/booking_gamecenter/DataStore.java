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
    private static final ArrayList<Station> daftarStation = new ArrayList<>();
    private static final ArrayList<Booking> daftarBooking = new ArrayList<>();
    private static int nextId = 1;

    static {
        daftarStation.add(new Station("PS5-01", JenisKonsol.PS5));
        daftarStation.add(new Station("PS5-02", JenisKonsol.PS5));
        daftarStation.add(new Station("PS5-03", JenisKonsol.PS5));
        daftarStation.add(new Station("PS4-01", JenisKonsol.PS4));
        daftarStation.add(new Station("PS4-02", JenisKonsol.PS4));
        daftarStation.add(new Station("PS4-03", JenisKonsol.PS4));
        daftarStation.add(new Station("NS-01", JenisKonsol.NINTENDO));
        daftarStation.add(new Station("NS-02", JenisKonsol.NINTENDO));
        daftarStation.add(new Station("XB-01", JenisKonsol.XBOX));
        daftarStation.add(new Station("XB-02", JenisKonsol.XBOX));

        if (FILE_AKUN.exists()) {
            muatAkun();
        } else {
            daftarAkun.add(new Pengguna("Administrator", "admin", "admin123", "ADMIN"));
            daftarAkun.add(new Pengguna("Firza", "firza", "firza123", "MEMBER"));
            daftarAkun.add(new Pengguna("Banis", "banis", "banis123", "MEMBER"));
            daftarAkun.add(new Pengguna("Nanda", "nanda", "nanda123", "MEMBER"));
            daftarAkun.add(new Pengguna("Ilham", "ilham", "ilham123", "MEMBER"));
            simpanAkun();
        }
        if (FILE_BOOKING.exists()) {
            muatBooking();
        }
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
        daftarAkun.add(new Pengguna(nama, username, password, "MEMBER"));
        simpanAkun();
    }

    public static JenisKonsol[] daftarJenisKonsol() {
        return JenisKonsol.values();
    }

    public static ArrayList<Station> stationDenganKonsol(JenisKonsol konsol) {
        ArrayList<Station> hasil = new ArrayList<>();
        for (Station s : daftarStation) {
            if (s.getKonsol() == konsol) {
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

    public static Booking buatBooking(Pengguna pemesan, Station station, LocalDate tanggal,
            int jamMulai, int durasiJam) {
        for (Booking b : daftarBooking) {
            if (b.bentrokDengan(station, tanggal, jamMulai, durasiJam)) {
                throw new IllegalStateException(pesanBentrok(b));
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

    public static ArrayList<Booking> bookingMilik(Pengguna pengguna) {
        ArrayList<Booking> hasil = new ArrayList<>();
        for (Booking b : daftarBooking) {
            if (b.getPemesan().getUsername().equals(pengguna.getUsername())) {
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

    private static String pesanBentrok(Booking b) {
        return "Jadwal bentrok dengan Booking #" + b.getId()
                + " di " + b.getStation().getKode()
                + " tanggal " + b.getTanggal().format(FORMAT_TANGGAL)
                + " jam " + b.getJamMulai() + ":00-" + b.getJamSelesai() + ":00"
                + " atas nama " + b.getPemesan().getNama() + ".";
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
                daftarAkun.add(new Pengguna(kolom[1], kolom[2], kolom[3], kolom[0]));
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
                Pengguna pemesan = cariPengguna(kolom[1]);
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

    private static Pengguna cariPengguna(String username) {
        for (Pengguna p : daftarAkun) {
            if (p.getUsername().equals(username)) {
                return p;
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
}

enum JenisKonsol {
    PS5("PlayStation 5", 15000, "Konsol generasi terbaru Sony, grafis 4K dan loading super cepat."),
    PS4("PlayStation 4", 10000, "Konsol legendaris Sony dengan koleksi game paling banyak."),
    NINTENDO("Nintendo Switch", 12000, "Konsol hybrid Nintendo, cocok untuk main ramai-ramai."),
    XBOX("Xbox Series X", 12000, "Konsol Microsoft dengan performa tinggi dan Xbox Game Pass.");

    private final String nama;
    private final int hargaPerJam;
    private final String deskripsi;

    JenisKonsol(String nama, int hargaPerJam, String deskripsi) {
        this.nama = nama;
        this.hargaPerJam = hargaPerJam;
        this.deskripsi = deskripsi;
    }

    public String getNama() {
        return nama;
    }

    public int getHargaPerJam() {
        return hargaPerJam;
    }

    public String deskripsi() {
        return deskripsi;
    }

    @Override
    public String toString() {
        return nama;
    }
}

class Station {
    private final String kode;
    private final JenisKonsol konsol;

    Station(String kode, JenisKonsol konsol) {
        this.kode = kode;
        this.konsol = konsol;
    }

    public String getKode() {
        return kode;
    }

    public JenisKonsol getKonsol() {
        return konsol;
    }

    public int hitungBiaya(int durasiJam) {
        return konsol.getHargaPerJam() * durasiJam;
    }

    @Override
    public String toString() {
        return kode + " (" + konsol.getNama() + ")";
    }
}

class Pengguna {
    private final String nama;
    private final String username;
    private final String password;
    private final String role;

    Pengguna(String nama, String username, String password, String role) {
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean cekPassword(String input) {
        return password.equals(input);
    }

    public String keCsv() {
        return role + ";" + nama + ";" + username + ";" + password;
    }
}

enum StatusBooking {
    AKTIF, DIBATALKAN
}

class Booking {
    private final int id;
    private final Pengguna pemesan;
    private final Station station;
    private final LocalDate tanggal;
    private final int jamMulai;
    private final int durasiJam;
    private final int totalBiaya;
    private StatusBooking status;

    Booking(int id, Pengguna pemesan, Station station, LocalDate tanggal,
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

    public Pengguna getPemesan() {
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

    public String keCsv() {
        return id + ";" + pemesan.getUsername() + ";" + station.getKode() + ";"
                + tanggal + ";" + jamMulai + ";" + durasiJam + ";" + status;
    }
}
