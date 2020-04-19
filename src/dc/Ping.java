package dc;

public class Ping {
    public String czas;
    public int time;
    public Ping(String czas, int time) {
        this.czas = czas;
        this.time = time;
    }
    @Override
    public String toString() {
        return czas + ";" + time;
    }
}
