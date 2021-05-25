package main.java.ua.goit.hw12;

import java.util.concurrent.Semaphore;

public class Molekel {
    static Semaphore semHexy = new Semaphore(1);
    static Semaphore semOxy = new Semaphore(1);
    int countHexy = 0;
    int countOxy = 0;
    int trident = 0;

    void hexy() {
        try {
            semHexy.acquire();
        }
        catch (InterruptedException exc) {
            System.out.println(exc);
        }
        if (trident < 3 ) {
            if (countHexy == 0 && countOxy == 0) {
                System.out.print("H ");
                countHexy++;
                trident++;
            } else if (countHexy == 1 && countOxy == 0) {
                System.out.print("H ");
                countHexy++;
                trident++;
            } else if (countHexy == 0 && countOxy == 1) {
                System.out.print("H ");
                countHexy++;
                trident++;
            } else if (countHexy == 1 && countOxy == 1) {
                System.out.print("H ");
                countHexy++;
                trident++;
            } else countHexy = 0;
        }
        else {
            trident = 0;
        }
        semOxy.release();
    }

    void oxy() {
        try {
            semOxy.acquire();
        }
        catch (InterruptedException exc){
            System.out.println(exc);
        }
        if(trident < 3) {
            if (countOxy == 0 && countHexy == 0) {
                System.out.print("O ");
                countOxy++;
                trident++;
            } else if (countOxy == 0 && countHexy == 1) {
                System.out.print("O ");
                countOxy++;
                trident++;
            } else countOxy = 0;
        }
        else {
            //System.out.println("\n");
            trident = 0;
        }
        semHexy.release();
    }
}

class ReleaseHydrogen implements Runnable {
    Molekel molekul;

    public ReleaseHydrogen(Molekel molekul) {
        this.molekul = molekul;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++)
            molekul.hexy();
    }
}

class ReleaseOxygen implements Runnable {
    Molekel molekul;

    public ReleaseOxygen(Molekel molekul) {
        this.molekul = molekul;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++ )
            molekul.oxy();
    }
}

class WasserMacher {
    public static void main(String[] args) {
        Molekel molekul = new Molekel();
        new Thread(new ReleaseHydrogen(molekul), "Hydrogen").start();
        new Thread(new ReleaseOxygen(molekul), "Oxygen").start();
    }
}

