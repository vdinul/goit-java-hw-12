package main.java.ua.goit.hw12;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class WaterMaker {
    public static final String ATOME = "OOOHHHHHH";

    /* QUESTION.  Why does this whole thing work when the Semaphore & CyclicBarrier are static
    * and does not work when 'static' is not there?*/
    static Semaphore semOxygen = new Semaphore(1);
    static Semaphore semHydrogen = new Semaphore(2);
    static CyclicBarrier cb = new CyclicBarrier(3);

    public static void main(String[] args) {
        for (int i = 0; i < ATOME.length(); i++) {
            if(ATOME.charAt(i) == 'O')
                new Thread(new Oxygen()).start();
            else
                new Thread(new Hydrogen()).start();
        }
    }

    void releaseOxygen () {
        System.out.print("O ");
    }

    void releaseHydrogen () {
        System.out.print("H ");
    }
}

class Hydrogen implements Runnable {
    WaterMaker wm = new WaterMaker();
    @Override
    public void run() {
        try {
            wm.semHydrogen.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            wm.cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        wm.releaseHydrogen();
        wm.semHydrogen.release();
    }
}

class Oxygen implements Runnable {
    WaterMaker wm = new WaterMaker();
    @Override
    public void run() {
        try {
            wm.semOxygen.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            wm.cb.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wm.releaseOxygen();
        wm.semOxygen.release();
    }
}