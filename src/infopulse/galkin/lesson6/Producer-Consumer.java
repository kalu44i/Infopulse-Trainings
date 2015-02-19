package infopulse.galkin.lesson6;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by apple on 1/24/15.
 */
class Producer implements Runnable {
    Queue q;

    Producer(Queue q) {
        this.q=q;
    }

    public void run() {
        while (true) {
            synchronized (q) {
                while (q.size() > 10) {
                    try {
                        q.wait();
                    } catch (InterruptedException e) {

                    }
                }
                System.out.println("We put an element!" + Thread.currentThread());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                q.add(100);
                q.notifyAll();
            }
        }
    }

}

class Consumer implements Runnable {
    Queue q;

    Consumer(Queue q) {
        this.q = q;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (q) {
                while (q.size() == 0) {
                    try {
                        q.wait();
                    } catch (InterruptedException e) {

                    }
                }
                System.out.println("We take the element!" + Thread.currentThread());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                q.poll();
                q.notifyAll();
            }
        }
    }
}

class Test {
    public static void main(String[] args) {
        Queue q = new LinkedList();
        Producer p1 = new Producer(q);
        Producer p2 = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        Consumer c3 = new Consumer(q);
        new Thread(p1).start();
        new Thread(p2).start();
        new Thread(c1).start();
        new Thread(c2).start();
        new Thread(c3).start();

    }
}
