/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * (C) Mathias Long Yan, myan@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 */

package diningphilos;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Tisch Main Klasse.
 * Ein Tisch ist eine Klasse und erstellt für sich die gleiche Anzahl an
 * Sitzen und Gabeln. Er kennt auch ihre Reihenfolge, greift aber sonst
 * nicht mehr auf sie zu.
 *
 * @author T500
 */
public class Table {

    private final Seat[] seats;
    private final Fork[] forks;

    /**
     * Ctor
     * @param nSeats Seats number.
     */
    public Table(final int nSeats) {

        seats = new Seat[nSeats];
        forks = new Fork[nSeats];

        for (int i = 0; i < nSeats; i++) {
            seats[i] = new Seat();
            forks[i] = new Fork();
        }
    }

    /**
     * Getter
     * @return Array of seats.
     */
    public Seat[] getSeats() {
        return seats;
    }

    /**
     * Getter
     * @return Array of forks.
     */
    public Fork[] getForks() {
        return forks;
    }

    /**
     * Die Main Methode liest eine Anzahl an Philosophen und Sitzen und
     * erstellt entsprechend  einen Tisch und die Philosophen. Sie lässt
     * die Threads für eine bestimmte Zeit laufen und unterbricht danach
     * alle. Sie kann mit einem Raum verglichen werden, in der die Objekte
     * existieren.
     *
     * @throws InterruptedException
     */
    public static void main(final String[]args) throws InterruptedException {

        final Scanner in = new Scanner(System.in);

        // read console input
        System.out.println("Number Philosophers:");
        final int nPhilosophers = in.nextInt();
        System.out.println("Index of very hungry Philosophers (seperated by space):");
        String hungryInput = "";

        while (in.hasNext()) {
            hungryInput = in.nextLine();
            if (!hungryInput.isEmpty()) break;
        }
        final String[] hungry = hungryInput.split(" ");
        System.out.println("Number Seats:");
        final int nSeats = in.nextInt();

        System.out.println("table opens.");
        final Table table = new Table(nSeats);

        // master feature
        final Master master = new Master(nPhilosophers);
        master.start();
        System.out.println("master enters room.");

        for (int i = 0; i < nPhilosophers; i++) {
            if (Arrays.asList(hungry).contains(i+"")) {
                master.getPhilosophers()[i] = new Philosopher("id " + i, table, true);
                System.out.println("id " + i + " stomach seems to growl faster.");
            } else {
                master.getPhilosophers()[i] = new Philosopher("id " + i, table, false);
            }
            master.getPhilosophers()[i].start();
        }

        // run time
        Thread.sleep(60000);

        System.out.println("table closes.");
        // stop all
        for (final Philosopher cur : master.getPhilosophers()) {
            cur.interrupt();
        }

        master.interrupt();
    }
}