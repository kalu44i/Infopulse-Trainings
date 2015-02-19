package infopulse.galkin.lesson7;

import java.util.*;

/**
 * Created by Nick Veremeichyk on 2/14/15.
 * Thу realization of Semaphore with Galkin.
 */
public class Semaphore {
    int value;
    Set<Thread> q = new HashSet<Thread>();
    Queue<Thread> sleepThreads = new LinkedList<Thread>();
    public Semaphore(int value) {
        this.value = value;
    }

    public void aquire() throws InterruptedException {
        synchronized (this) {
            if (q.contains(Thread.currentThread())) {
                throw new IllegalStateException();
            }
            q.add(Thread.currentThread());
            while (value == 0) {
                System.out.println(Thread.currentThread() + " is waiting for Semaphore");
                sleepThreads.add(Thread.currentThread());
                this.wait();
                Thread t = sleepThreads.element();

                while (t != Thread.currentThread()) {
                    this.wait();
                    sleepThreads.poll();
                }
            }


            value--;
        }

    }


    public void release() {
        synchronized (this) {
            if (!q.contains(Thread.currentThread())) {
                throw new IllegalStateException();
            }
            q.remove(Thread.currentThread());
                value++;
                this.notify();
        }

    }
}

class MyTread extends Thread {
    Semaphore s;
    public MyTread(Semaphore s) {
        this.s = s;
    }

    public void run() {
        while (true) {
            try {
                s.aquire();
                System.out.println(Thread.currentThread() + " takes Semaphore");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {

                }
                System.out.println(Thread.currentThread() + " is released Semaphore");
            }

        }
    }
}

class A {
    public  static  void main(String[] args) {
        Semaphore s = new Semaphore(3);
        new MyTread(s).start();
        new MyTread(s).start();
        new MyTread(s).start();
        new MyTread(s).start();
        new MyTread(s).start();
        new MyTread(s).start();
        new MyTread(s).start();



    }
}


