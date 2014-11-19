/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * (C) Mathias Long Yan, myan@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 */

package diningphilos;

/**
 * Philosoph Thread-Klasse.
 * Ein Philosoph ist eine Thread-Unterklasse mit Name, zugehörigem Tisch und
 * den booleschen Zuständen großer Hunger und ist verbannt. Er zählt bis drei
 * Speisen. In wiederholender Reihenfolge meditiert, isst und ggf. schläft er.
 * Gestartet und beendet wird ein Philosoph von der Hauptklasse. Er kann
 * versuchen, sich auf einen Platz zu setzen und eine der beiden dazugehörigen
 * Gabeln sowie danach die andere aufzunehmen. Die Philosophen kennen sich
 * untereinander nicht und können nach dem Instanziieren auch nur eigene
 * Attribute schreiben. Sonst keine anderer Philosophen und von keinem anderen
 * Objekt der Umgebung.
 * @author Nhu-Huy Le, Mathias Long Yan
 */
public class Philosopher extends Thread {

    /** Name of the philosopher. */
    private final String name;
    /** Table of the Philosopher. */
    private final Table table;
    /** Hungry or not. */
    private final boolean hungry;
    /** Meal counter. */
    private int meals;
    /** Currently banned or not. */
    private boolean banned;

    /**
     * Ctor.
     * @param name Name of the philosopher.
     * @param table Table of the Philosopher.
     * @param hungry Hungry or not.
     */
    public Philosopher(final String name, final Table table, final boolean hungry) {
        this.name = name;
        this.table = table;
        this.hungry = hungry;
        meals = 0;
        banned = false;
    }

    /**
     * Going to eat.
     * @throws InterruptedException
     */
    public void eat() throws InterruptedException {

        int i = 0;
        Seat seat;
        Fork first;
        Fork second;

        System.out.printf("%-30s %s %n", name, "seaches seat.");

        // waiting for seat
        while (true) {
            i %= table.getSeats().length;
            seat = table.getSeats()[i];

            if (seat.sit(this)) {
                break;
            }
            i++;
        }

        final int left = i;
        final int right = (i + 1) % table.getSeats().length;

        System.out.printf("%-45s %s %n", name, "needs forks.");

        //waiting for fork
        while (true) {

            // left or right first
            final boolean decision = Math.random() < 0.5;
            first = decision ? table.getForks()[left] : table.getForks()[right];
            second = decision ? table.getForks()[right] : table.getForks()[left];

            if (first.pick(this)) {
                if (second.pick(this)) {
                    break;
                } else {
                    first.drop();
                }
            }
        }

        System.out.printf("%-60s %s %d.%n", name, "eats at", i);
        Thread.sleep(1);

        second.drop();
        first.drop();
        seat.leave();
        meals++;
    }

    /**
     * Starts thread.
     */
    @Override
    public void run() {

        int mealsLeft = 3;
        if (hungry) {
            setPriority(Thread.MAX_PRIORITY);
        }

        try {
            while (!isInterrupted()) {
                System.out.println(name + " meditates.");
//                int meditate = hungry ? 0 : 5;
                Thread.sleep(5);
                System.out.printf("%-15s %s %n", name, "gets hungry.");
                eat();
                System.out.printf("%-75s %s %n", name, "leaves.");

                if (--mealsLeft == 0) {
                    System.out.printf("%-90s %s %n", name, "sleeps.");
                    Thread.sleep(10);
                    mealsLeft = 3;
                } else if (banned) {
                    System.out.printf("%-90s %s %n", name, "banned.");
                    Thread.sleep(5);
                    banned = false;
                }
            }
        } catch (InterruptedException ex) {
            System.out.println(name + " starves to death.");
        }
    }

    /**
     * Getter.
     * @return Name.
     */
    public String getPhilName() {
        return name;
    }

    /**
     * Getter
     * @return meals sum.
     */
    public int getMeals() {
        return meals;
    }

    /**
     * Ban this philosopher.
     */
    public void ban() {
        banned = true;
    }
}


