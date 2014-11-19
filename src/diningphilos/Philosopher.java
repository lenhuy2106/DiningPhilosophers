/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 */

package diningphilos;

/**
 * Philosoph Thread-Klasse.
 *
 * Ein Philosoph ist eine Thread-Unterklasse mit Name, zugehörigem Tisch und
 * den booleschen Zuständen großer Hunger und ist verbannt. Er zählt bis drei
 * Speisen. In wiederholender Reihenfolge meditiert, isst und ggf. schläft er.
 * Gestartet und beendet wird ein Philosoph von der Hauptklasse. Er kann
 * versuchen, sich auf einen Platz zu setzen und eine der beiden dazugehörigen
 * Gabeln sowie danach die andere aufzunehmen. Die Philosophen kennen sich
 * untereinander nicht und können nach dem Instanziieren auch nur eigene
 * Attribute schreiben. Sonst keine anderer Philosophen und von keinem anderen
 * Objekt der Umgebung.
 *
 * @author T500
 */
public class Philosopher extends Thread {

    private final String name;
    private final Table table;
    private final boolean hungry;
    private int meals;
    private boolean banned;

    public Philosopher(final String name, final Table table, final boolean hungry) {
        this.name = name;
        this.table = table;
        this.hungry = hungry;
        meals = 0;
        banned = false;
    }

    public void eat() throws InterruptedException {

        int i = 0;
        Seat seat;
        Fork first;
        Fork second;

        System.out.printf("%-30s %s %n", name, "seaches seat.");

        // waiting for seat
        while (true) {
            i %= table.seats.length;
            seat = table.seats[i];

            if (seat.sit(this)) {
                break;
            }
            i++;
        }

        final int left = i;
        final int right = (i + 1) % table.seats.length;

        System.out.printf("%-45s %s %n", name, "needs forks.");

        //waiting for fork
        while (true) {

            // left or right first
            final boolean decision = Math.random() < 0.5;
            first = decision ? table.forks[left] : table.forks[right];
            second = decision ? table.forks[right] : table.forks[left];

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
                    Thread.sleep(10);
                    banned = false;
                }
            }
        } catch (InterruptedException ex) {
            System.out.println(name + " starves to death.");
        }
    }

    public String getPhilName() {
        return name;
    }

    public int getMeals() {
        return meals;
    }

    public void ban() {
        banned = true;
    }
}


