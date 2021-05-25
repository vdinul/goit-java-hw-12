package main.java.ua.goit.hw12;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Hexagen {
    public static void main(String[] args) {
        CyclicBarrier cbr = new CyclicBarrier(3, new ThisIsIt());

        ProstoClass ps = new ProstoClass();
        for(int i = 0; i < 6; i++)  {
            new Thread(new Hydrogen(cbr, ps)).start();
        }
        for(int i = 0; i < 6; i++ ) {
            new Thread(new Oxygen(cbr, ps)).start();
        }
    }
}

class ProstoClass {
    static Semaphore semHexy = new Semaphore(1);
    static Semaphore semOxy = new Semaphore (0);

    void releaseHexy(){
        try {
            semHexy.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("H ");
        semOxy.release();
    }

    void releaseOxy() {
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
        ps.releaseHexy();
        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
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
        ps.releaseOxy();
        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class ThisIsIt implements Runnable {
    @Override
    public void run() {
        System.out.println("");
    }
}


