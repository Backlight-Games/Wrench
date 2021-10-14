package games.backlight.wrench;

public class Main {
    public static void main(String[] args) {
        try {
            new Wrench();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
