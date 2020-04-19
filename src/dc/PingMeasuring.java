package dc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class PingMeasuring {
    public static Ping sendPingRequest(String ipAddress)
            throws UnknownHostException, IOException {
        long startT = System.currentTimeMillis();
        LocalDateTime ldt = LocalDateTime.now();
        InetAddress geek = InetAddress.getByName(ipAddress);
        System.out.println("Sending Ping Request to " + ipAddress);
        if (geek.isReachable(5000)) {
            long stopT = System.currentTimeMillis();
            long elapsedTime = stopT - startT;
            String czas = ldt.now().getHour()+ ":" + ldt.getMinute() +":" + ldt.getSecond();
            System.out.println(czas + " =" + elapsedTime);
            return new Ping(czas, (int)elapsedTime);
        } else
            System.out.println("We are very sorry ! We can't reach to this host");
        return null;
    }

    public static void main(String[] args)
            throws UnknownHostException, IOException {
        String[] ipAddress = new String[] {"1.1.1.1", "8.8.8.8", "139.130.4.5", "8.8.4.4"};
        LocalDate data = LocalDate.now();
        String dat = data.getDayOfMonth() + "." + data.getMonthValue() + "." + data.getYear();
        int count = 0;
        List<Ping> pingi = new ArrayList<Ping>();
        while (count < 1800) {
            Ping p = sendPingRequest(ipAddress[count %4 ]);
            if (p != null) pingi.add(p);
            try {
                Thread.sleep(30000);
                count++;
            }
            catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        // zapis do pliku
        File file;
        FileWriter writer = new FileWriter("pingi" + dat + ".json");
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        String napis = json.toJson(pingi);
        writer.write(napis);
        writer.close();
        writer = new FileWriter("pingi " + dat + ".csv");
        BufferedWriter bw = new BufferedWriter(writer);
        for(Ping p : pingi) {
            bw.write(p.toString());
            bw.newLine();
        }
        bw.close();
        writer.close();

    }
}