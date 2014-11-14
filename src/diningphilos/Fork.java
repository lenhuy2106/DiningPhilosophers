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
class Fork {

    Philosopher holder;
    boolean free = true;

    public synchronized boolean pick(Philosopher examiner) {
        boolean success = false;

        if (free) {
            holder = examiner;
            free = false;
            success = true;
        }

        return success;
    }

    public synchronized void drop() {
        holder = null;
        free = true;
    }
}
