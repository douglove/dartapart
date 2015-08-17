package net.magicstudios.jdart.comm;

import java.util.Vector;

public class OcclusionWatcher extends Thread {

    private int m_iTimeoutMillis = 500;
    private boolean m_bOcclusionResolved = false;
    private Vector m_vecListener = new Vector();

    public OcclusionWatcher() {
    }

    public OcclusionWatcher(int millis) {
        m_iTimeoutMillis = millis;
    }

    public void removeOcclusionListener(OcclusionListener listener) {
        m_vecListener.remove(listener);
    }

    public void addOcclusionListener(OcclusionListener listener) {
        m_vecListener.add(listener);
    }

    public void startOcclusionWatch() {
        if (! isAlive()) {
            m_bOcclusionResolved = false;
            start();
        } else {
            System.out.println("Occlusion watch already alive!");
        }
    }

    public void occlusionResolved() {
        m_bOcclusionResolved = true;
    }

    public void fireOcclusionTimerExpired() {
        Object [] listeners = m_vecListener.toArray();
        int count = listeners.length;


        for (int i = 0; i < count; i++) {
            OcclusionListener listener = (OcclusionListener) listeners[i];
            listener.occlusionTimerExpired();
        }
    }

    public void run() {

        try {
            Thread.sleep(m_iTimeoutMillis);
            if (!m_bOcclusionResolved) {
                System.out.println("Occlusion!!");
                fireOcclusionTimerExpired();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
