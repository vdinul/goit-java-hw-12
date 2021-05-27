package main.java.ua.goit.hw12;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Hexagen {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cbr = new CyclicBarrier(3);
        ProstoClass ps = new ProstoClass();

        for(int i = 0; i < 4; i++)  {
            new Thread(new Hydrogen(cbr, ps)).start();
            Thread.sleep(400);
        }
        for(int i = 0; i < 2; i++ ) {
            new Thread(new Oxygen(cbr, ps)).start();
            Thread.sleep(700);
        }
    }
}

class ProstoClass {
    static Semaphore semHexy = new Semaphore(2);
    static Semaphore semOxy = new Semaphore (0);

    void releaseHydrogen(){
        try {
            semHexy.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("H ");
        semOxy.release();
    }

    void releaseOxygen() {
        try {
            semOxy.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("O ");
        semHexy.release();
    }
}

class Hydrogen implements Runnable {
    CyclicBarrier cb;
    ProstoClass ps;

    public Hydrogen(CyclicBarrier cb, ProstoClass ps) {
        this.cb = cb;
        this.ps = ps;
    }

    @Override
    public void run() {
        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        ps.releaseHydrogen();
    }
}

class Oxygen implements Runnable {
    CyclicBarrier cb;
    ProstoClass ps;

    public Oxygen(CyclicBarrier cb, ProstoClass ps) {
        this.cb = cb;
        this.ps = ps;
    }

    @Override
    public void run() {
        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        ps.releaseOxygen();
    }
}




