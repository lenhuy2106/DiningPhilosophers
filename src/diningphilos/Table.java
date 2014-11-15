/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 */

package diningphilos;

import java.util.Arrays;

/**
 *
 * @author T500
 */
public class Table {

    Seat[] seats;
    Fork[] forks;
    Master master;

    Table(int nSeats, int nPhilosophers, String... hungry) {

        seats = new Seat[nSeats];
        forks = new Fork[nSeats];

        // master feature
        master = new Master(nPhilosophers);
        master.start();
        System.out.println("master enters room.");

        for (int i = 0; i < nSeats; i++) {
            seats[i] = new Seat();
            forks[i] = new Fork();
        }

        for (int i = 0; i < nPhilosophers; i++) {
            if (Arrays.asList(hungry).contains(i+"")) {
                master.philosophers[i] = new Philosopher("id " + i, this, true);
                System.out.println("id " + i + " stomach seems to growl faster.");
            } else {
                master.philosophers[i] = new Philosopher("id " + i, this, false);
            }
            master.philosophers[i].start();
        }
    }

    public static void main(String[]args) throws InterruptedException {

        int nPhilosophers = 5;
        int nSeats = 5;
        String[] hungry = {"0", "3"};

        System.out.println("table opens.");
        Table table = new Table(nSeats, nPhilosophers, hungry);
        Thread.sleep(60000);

        System.out.println("table closes.");
        // stop all
        for (Philosopher cur : table.master.philosophers) {
            cur.interrupt();
        }

        table.master.interrupt();
    }
}
