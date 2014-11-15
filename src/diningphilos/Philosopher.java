/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 */

package diningphilos;

/**
 *
 * @author T500
 */
public class Philosopher extends Thread {

    String name;
    Table table;
    int meals;
    boolean banned;
    boolean hungry;


    public Philosopher(String name, Table table, boolean hungry) {
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
            i %= (table.seats.length);
            seat = table.seats[i];

            if (seat.sit(this) == true) {
                break;
            }
            i++;
        }

        int left = i;
        int right = (i + 1) % table.seats.length;

        System.out.printf("%-45s %s %n", name, "needs forks.");

        //waiting for fork
        while (true) {

            // left or right first
            boolean decision = (Math.random() < 0.5);
            first = decision ? table.forks[left] : table.forks[right];
            second = decision ? table.forks[right] : table.forks[left];

            if (first.pick(this) == true) {
                if (second.pick(this) == true) {
                    break;
                } else {
                    first.drop();
                }
            }

            // no famous deadlock
            Thread.sleep(1000);
        }

        System.out.printf("%-60s %s %d.%n", name, "eats at", i);
        Thread.sleep(1);

        second.drop();
        first.drop();
        seat.leave();
        meals++;
    }

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
                } else if (banned == true) {
                    System.out.printf("%-90s %s %n", name, "banned.");
                    Thread.sleep(10);
                    banned = false;
                }
            }
        } catch (InterruptedException ex) {
            System.out.println(name + " starves to death. :(");
        }
    }
}


