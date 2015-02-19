package infopulse.galkin.lesson7;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Nick Veremeichyk on 2/18/15.
 */
public class MySemaphore {
    private final static int COUNTER_CONST = 3;
    private int count;
    Set<Thread> q = new HashSet<Thread>();
    Queue<Thread> sleepThreads = new LinkedList<Thread>();

    public MySemaphore() {
       count = COUNTER_CONST;
    }

    public MySemaphore(int count) {
        this.count = count;
    }

    public synchronized void acquire() throws InterruptedException {
        if (q.contains(Thread.currentThread())) {
            throw new IllegalStateException();
        }
        while (count == 0) {
            System.out.println(Thread.currentThread().getName() + " is waiting for Semaphore");
            sleepThreads.add(Thread.currentThread());
            this.wait();

            Thread t = sleepThreads.element();

            while (t != Thread.currentThread()) {
                this.wait();
            }
            sleepThreads.poll();
        }
        q.add(Thread.currentThread());

        count--;
    }

    public synchronized void release() {
        if (!q.contains(Thread.currentThread())) {
            throw new IllegalStateException();
        }
        if (count == 0) {
            this.notify();
        }
        q.remove(Thread.currentThread());
        count++;
    }

}

class MyThread extends Thread {
    MySemaphore s;
    public MyThread(MySemaphore s) {
        this.s = s;
    }

    public void run() {
            while (true) {
                try {
                    s.acquire();
                    System.out.println(Thread.currentThread().getName() + " takes MySemaphore");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                } catch (IllegalStateException e) {
                    System.out.println("The method ACQUIRE has called at the inappropriate time");
                } finally {
                    try {
                        s.release();
                    } catch (IllegalStateException e) {
                        System.out.println("The method RELEASE has called at the inappropriate time");
                    }
                    System.out.println(Thread.currentThread().getName() + " releases MySemaphore");
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}

class DemoSemaphore {
    public static void main(String[] args) {
        MySemaphore s = new MySemaphore(3);
        MyThread m1 = new MyThread(s);
        m1.setName("1");
        m1.start();
        MyThread m2 = new MyThread(s);
        m2.setName("2");
        m2.start();
        MyThread m3 = new MyThread(s);
        m3.setName("3");
        m3.start();
        MyThread m4 = new MyThread(s);
        m4.setName("4");
        m4.start();
        MyThread m5 = new MyThread(s);
        m5.setName("5");
        m5.start();
    }
}
