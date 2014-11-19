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

    private final Philosopher[] philosophers;

    public Master(final int nPhilosophers) {
        philosophers = new Philosopher[nPhilosophers];
    }

    public void run() {

        while (!isInterrupted()) {
            Philosopher min = philosophers[0];
            Philosopher max = philosophers[0];

            if (philosophers[philosophers.length-1] != null) {
                for (final Philosopher cur : philosophers) {
                    if (cur.getMeals() < min.getMeals()) {
                        min = cur;
                    } else if (cur.getMeals() > max.getMeals()) {
                        max = cur;
                    }
                }

                // max differ of 5
                if (max.getMeals() - min.getMeals() > 5) {
                    max.ban();
                }
            }
        }
        System.out.println("master counts: ");
        int total = 0;

        for (final Philosopher cur : philosophers) {
            total += cur.getMeals();
            System.out.printf("%15s %s %3d meals.%n", cur.getPhilName(), "ate", cur.getMeals());
        }
        System.out.printf("- %d meals in total.%n", total);
        System.out.println("master leaves room.");
    }

    public Philosopher[] getPhilosophers() {
        return philosophers;
    }
}