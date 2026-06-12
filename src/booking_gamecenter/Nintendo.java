package booking_gamecenter;

public class Nintendo extends Konsol {

    public Nintendo() {
        super("Nintendo Switch", 12000);
    }

    @Override
    public String deskripsi() {
        return "Konsol hybrid Nintendo, cocok untuk main ramai-ramai.";
    }
}
