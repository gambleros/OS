import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author ristes
 */
public class TancSoStudentite {
    //TODO: Definicija na globalni promenlivi i semafori
    public Semaphore tanc;
    public Semaphore preM;
    public Semaphore preZ;

    public void init() {
        //TODO: da se implementira
        tanc=new Semaphore(3);
        preM=new Semaphore(10);
        preZ=new Semaphore(10);
    }

    class Masko extends Thread {
        //TODO: Definicija  na promenlivi za sostojbata

        public void ucestvo() throws InterruptedException {
            //TODO: da se implementira
            preM.acquire();
            show.presobleci();
            preM.release();
            // samo maskoto go povikuva metodot tancuvaj
            tanc.acquire();
            show.tancuvaj();
            tanc.release();
        }

        @Override
        public void run() {
            try {
                ucestvo();
            } catch (InterruptedException e) {
                // Do nothing
            } catch (Exception e) {
                exception = e;
                hasException = true;
            }
        }

        @Override
        public String toString() {
            return String.format("m\t%d", getId());
        }
        public Exception exception = null;
    }

    class Zensko extends Thread {
        //TODO: Definicija  na promenlivi za sostojbata

        public void ucestvo() throws InterruptedException {
            //TODO: da se implementira
            preZ.acquire();
            show.presobleci();
            preZ.release();

        }

        @Override
        public void run() {
            try {
                ucestvo();
            } catch (InterruptedException e) {
                // Do nothing
            } catch (Exception e) {
                exception = e;
                hasException = true;
            }
        }

        @Override
        public String toString() {
            return String.format("z\t%d", getId());
        }
        public Exception exception = null;
    }

    public static void main(String[] args) {
        try {
            TancSoStudentite environment = new TancSoStudentite();
            environment.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void start() throws Exception {
        show = new Show();
        init();
        HashSet<Thread> threads = new HashSet<Thread>();
        for (int i = 0; i < BROJ_INSTANCI; i++) {
            Zensko z = new Zensko();
            Masko m = new Masko();
            threads.add(z);
            threads.add(m);
        }

        for (Thread t : threads) {
            t.start();
        }

        boolean valid = true;
        for (Thread t : threads) {
            if (!hasException) {
                t.join();
            } else {
                t.interrupt();
            }
        }
        show.printStatus();

    }

    public class Show {

        public static final int BROJ_GARDEROBA = 10;
        public static final int BROJ_TEREN = 3;
        public static final int TYPE_MASKO = 1;
        public static final int TYPE_ZENSKO = 2;
        public static final int TYPE_UNKNOWN = -1;

        public Show() {
        }
        public int brojMaskiGarderoba = 0;
        public int brojZenskiGarderoba = 0;
        public int brojTancuvanja = 0;
        public int maxMaskiGarderoba = 0;
        public int maxZenskiGarderoba = 0;
        public int maxTancuvanja = 0;

        public void presobleci() throws RuntimeException {
            log(null, "presobleci start");
            Thread t = Thread.currentThread();
            if (t instanceof Masko) {
                synchronized (RANDOM) {
                    brojMaskiGarderoba++;
                    if (brojMaskiGarderoba > 10) {
                        exception("Ne mozhe da ima povekje od 10 mashki vo mashkata garderoba.");
                    }
                    if (brojMaskiGarderoba > maxMaskiGarderoba) {
                        maxMaskiGarderoba = brojMaskiGarderoba;
                    }
                }
                waitRandom();
                synchronized (RANDOM) {
                    brojMaskiGarderoba--;
                }
            } else {
                synchronized (RANDOM) {
                    brojZenskiGarderoba++;
                    if (brojZenskiGarderoba > 10) {
                        exception("Ne mozhe da ima povekje od 10 zhenski vo zhenskata garderoba.");
                    }
                    if (brojZenskiGarderoba > maxZenskiGarderoba) {
                        maxZenskiGarderoba = brojZenskiGarderoba;
                    }
                }
                waitRandom();
                synchronized (RANDOM) {
                    brojZenskiGarderoba--;
                }
            }
            log(null, "presoblechi kraj");
        }

        public void tancuvaj() throws RuntimeException {
            log(null, "tancuvaj start");
            synchronized (RANDOM) {
                brojTancuvanja++;
                if (brojTancuvanja > BROJ_TEREN) {
                    exception("Ne mozhe paralelno da tancuvaat povekje od 3 para.");
                }

                if (brojTancuvanja > maxTancuvanja) {
                    maxTancuvanja = brojTancuvanja;
                }
            }
            waitRandom();
            synchronized (RANDOM) {
                brojTancuvanja--;
            }
            log(null, "tancuvaj kraj");
        }

        private void waitRandom() {
            try {
                int r;
                synchronized (RANDOM) {
                    r = RANDOM.nextInt(RANDOM_RANGE);
                }
                Thread.sleep(r);
            } catch (Exception e) {
                //do nothing
            }
        }

        private void exception(String message) {
            RuntimeException e = new RuntimeException(message);
            log(e, null);
            hasError = true;
            throw e;
        }

        public int getType() {
            Thread t = Thread.currentThread();
            if (t instanceof Masko) {
                return TYPE_MASKO;
            } else if (t instanceof Zensko) {
                return TYPE_ZENSKO;
            } else {
                return TYPE_UNKNOWN;
            }
        }

        private synchronized void log(RuntimeException e, String action) {
            Thread t = Thread.currentThread();
            if (e == null) {
                actions.add(t.toString() + "\t(a): " + action);
            } else {
                actions.add(t.toString() + "\t(e): " + e.getMessage());
            }
        }

        public synchronized void printLog() {
            System.out.println("Poradi konkurentnosta za pristap za pechatenje, mozhno e nekoja od porakite da ne e na soodvetnoto mesto.");
            System.out.println("Log na izvrshuvanje na akciite:");
            System.out.println("=========================");
            System.out.println("(tip m<=>Mashko, tip z<=>Zhensko)");
            System.out.println("tip\tid\takcija/error");
            System.out.println("=========================");
            for (String l : actions) {
                System.out.println(l);
            }
        }

        public void printStatus() {
            if (!hasError) {
                int poeni = 25;
                System.out.println("Procesot e uspeshno sinhroniziran");
                if (show.maxMaskiGarderoba == 1 || show.maxZenskiGarderoba == 1) {
                    System.out.println("\t-no ima maksimum eden uchesnik vo garderobata.");
                    poeni -= 5;
                }
                if (show.maxTancuvanja == 1) {
                    System.out.println("\t-no ima maksimum edna proverka vo eden moment.");
                    poeni -= 5;
                }

                System.out.println("Osvoeni poeni: " + poeni);

            } else {
                System.out.println("Procesot ne e sinhroniziran spored uslovite na zadachata");
                show.printLog();
                System.out.println("Maksimum mozni poeni: 15");
            }

        }
        private List<String> actions = new ArrayList<String>();
        private boolean hasError = false;
    }
    // Konstanti
    public static int BROJ_INSTANCI = 1000;
    public static final Random RANDOM = new Random();
    public static final int RANDOM_RANGE = 3;
    // Instanca od bafferot
    public Show show;
    public boolean hasException = false;
}
