package booking_gamecenter;

public abstract class Pengguna {

    private final String nama;
    private final String username;
    private final String password;

    protected Pengguna(String nama, String username, String password) {
        this.nama = nama;
        this.username = username;
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public boolean cekPassword(String input) {
        return password.equals(input);
    }

    public String keCsv() {
        return getRole() + ";" + nama + ";" + username + ";" + password;
    }

    public abstract String getRole();
}
