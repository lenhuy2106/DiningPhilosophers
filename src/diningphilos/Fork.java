/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 */

package diningphilos;

/**
 * Eine Gabel ist eine gemeinsam genutzte Klasse.
 * Sie kann nur ausschließlich von einem Philosophen, der auf einer der
 * zwei zugehörigen, nebeneinander liegenden Plätzen sitzt, aufgenommen
 * werden. Dieser kennt die Gabel und setzt ggf. den booleschen Zustand
 * aufgenommen. Danach kann er von diesem wieder abgelegt werden.
 * @author T500
 */
class Fork {

    private Philosopher holder;
    private boolean free = true;

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