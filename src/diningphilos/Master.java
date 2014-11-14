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
class Master extends Thread {

    Philosopher[] philosophers;


    public Master(int nPhilosophers) {
        philosophers = new Philosopher[nPhilosophers];
    }

    public void run() {

        while (!isInterrupted()) {
            Philosopher min = philosophers[0];
            Philosopher max = philosophers[0];

            if (philosophers[philosophers.length-1] != null) {
                for (Philosopher cur : philosophers) {
                    if (cur.meals < min.meals) {
                        min = cur;
                    } else if (cur.meals > max.meals) {
                        max = cur;
                    }
                }

                // max differ of 5
                if (max.meals - min.meals > 5) {
                    max.banned = true;
                }
            }
        }
        System.out.println("master counts: ");
        int total = 0;

        for (Philosopher cur : philosophers) {
            total += cur.meals;
            System.out.printf("%15s %s %3d meals.%n", cur.name, "ate", cur.meals);
        }
        System.out.printf("- %d meals in total.%n", total);
        System.out.println("master leaves room.");
    }
}
