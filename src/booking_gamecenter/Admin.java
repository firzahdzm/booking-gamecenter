package booking_gamecenter;

public class Admin extends Pengguna {

    public Admin(String nama, String username, String password) {
        super(nama, username, password);
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
