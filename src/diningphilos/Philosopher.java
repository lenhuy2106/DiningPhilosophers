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

    int msMeditate;
    String name;
    Table table;

    public Philosopher(String name, int msMeditate, Table table) {
        this.msMeditate = msMeditate;
        this.name = name;
        this.table = table;
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
        Thread.sleep(3000);

        second.drop();
        first.drop();
        seat.leave();
    }

    public void run() {

        int meals = 0;

        while(true) {
            try {
                System.out.println(name + " meditates.");
                Thread.sleep(msMeditate);
                System.out.printf("%-15s %s %n", name, "gets hungry.");
                eat();
                System.out.printf("%-75s %s %n", name, "leaves.");

                if (++meals == 3) {
                    System.out.printf("%-90s %s %n", name, "sleeps.");
                    Thread.sleep(5000);
                    meals = 0;
                }

            } catch (InterruptedException ex) {}
        }
    }

}
