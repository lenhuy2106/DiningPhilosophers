/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 */

package diningphilos;

import java.util.concurrent.Semaphore;

/**
 *
 * @author T500
 */
public class Table {

    Seat[] seats;
    Fork[] forks;
    Semaphore seatSem;

    Table(int nSeats, int nPhilosophers) {

        seats = new Seat[nSeats];
        forks = new Fork[nSeats];
        seatSem = new Semaphore(nSeats);

        for (int i = 0; i < nSeats; i++) {
            seats[i] = new Seat();
            forks[i] = new Fork();
        }

        for (int i = 0; i < nPhilosophers; i++) {
            new Philosopher("id " + i, 1000, this).start();
        }
    }

    public static void main(String[]args) {

        int nPhilosophers = 5;
        int nSeats = 5;

        Table table = new Table(nSeats, nPhilosophers);
    }
}
