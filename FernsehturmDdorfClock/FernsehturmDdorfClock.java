import java.time.LocalTime;

public class FernsehturmDdorfClock {

    private static void fernsehturm(double bb, double th, int std, int min, int sek) {
        // bb = Bodenbreite
        // th = Turmhöhe

        // Voreinstellungen
        StdDraw.setXscale(-70, 70);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(0.01);
        
        // Hintergrund je nach Tageszeit in DÜSSELDORF
        boolean daytime = SunriseSunset.isDay(51.2311, 6.7724);
        if(daytime) {
           StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
        } else {
          StdDraw.clear(StdDraw.BOOK_BLUE);
        }
        

        // Horizontale Linien bei y =
        double[] hor = {th*0.74,th*0.8,th*0.83,th*0.86};

        // x und y für Umriss
        double[] y = {0,th*0.67,hor[1],hor[1],hor[2],hor[2],hor[3],hor[3],th*0.99};
        double[] x = {bb,bb/2,bb*1.5,bb,bb*0.9,bb*0.7,bb*0.6,bb*0.1,0};

        // Uhrlampen Koordinaten
        double abstand = th*0.015;
        double hoehe_sek = th*0.02;
        double hoehe_min = th*0.26;
        double hoehe_std = th*0.5;
        double[] sekn = new double[15];
        double[] minn = new double[15];
        double [] stdn = new double[15];
        for(int i = 0; i <15; i++) {
            sekn[i] = hoehe_sek+i*abstand;
            minn[i] = hoehe_min+i*abstand;
            stdn[i] = hoehe_std+i*abstand;
        }

        // Uhrlampen an / aus
        boolean[] sek_an = leuchtendeLampen(sek);
        boolean[] min_an = leuchtendeLampen(min);
        boolean[] std_an = leuchtendeLampen(std);

        // rote Lampen
        double [] rot = {hoehe_min-abstand,hoehe_std-abstand};

        // Umriss zeichnen
        for(int i=0;i<x.length-1;i++) {
            StdDraw.line(x[i],y[i],x[i+1],y[i+1]);
            StdDraw.line(-x[i],y[i],-x[i+1],y[i+1]);
        }

        // Horizontale Linien zeichnen
        for(int i = 0; i<hor.length; i++) {
        StdDraw.line(bb,hor[i],-bb,hor[i]);
        }

        // Fenster zeichnen
        for(int i = 0; i<bb; i+=2) {
            StdDraw.line(i,hor[0],i*1.5,hor[1]);
            StdDraw.line(-i,hor[0],-i*1.5,hor[1]);
        }

        // rote Lampen zeichnen
        StdDraw.setPenColor(StdDraw.RED);
        for(int i = 0; i<bb*0.75; i+=3) {
            StdDraw.point(i,rot[0]);
            StdDraw.point(i,rot[1]);
            StdDraw.point(-i,rot[0]);
            StdDraw.point(-i,rot[1]);
        }

        // Uhr zeichnen
        StdDraw.setPenColor(StdDraw.YELLOW);
        for(int i = 0; i<15; i++) {
            if(sek_an[i]) {
                StdDraw.point(0,sekn[i]);
            }
            if(min_an[i]) {
                StdDraw.point(0,minn[i]);
            }
            if(std_an[i]) {
                StdDraw.point(0,stdn[i]);
            }
        }

    }

    private static boolean[] leuchtendeLampen(int zeit) {
        boolean[] an = new boolean[15];
        int untereLampen = zeit%10;
        int obereLampen = (int)zeit/10;
        for(int i = 0; i<10; i++) {
            if(untereLampen>i) {
                an[i] = true;
            }
        }
        for(int i = 0; i<6; i++) {
            if(obereLampen>i) {
                an[i+10] = true;
            }
        }
        return an;
    }

    public static void main(String[] args) {
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        int minute = currentTime.getMinute();
        int second = currentTime.getSecond();
        fernsehturm(10,100,hour,minute,second);
    }

}
