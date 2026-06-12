package booking_gamecenter;

public class Member extends Pengguna {

    public Member(String nama, String username, String password) {
        super(nama, username, password);
    }

    @Override
    public String getRole() {
        return "MEMBER";
    }
}
