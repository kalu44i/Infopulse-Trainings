package infopulse.galkin.lesson6;

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.util.*;
import java.util.Queue;

/**
 * Created by apple on 2/7/15.
 */
public class Restoraunt_Producer_Consumer {
    static class Client implements Runnable {
        Queue soup;
        Queue cake;
        Queue coffe;

//        HashMap<String, Boolean> status;

        public Client(Queue soup, Queue cake, Queue coffe) {
//            {
//                status = new HashMap<String, Boolean>(3);
//                status.put("soup", false);
//                status.put("cake", false);
//                status.put("coffe", false);
//            }

            this.soup = soup;
            this.cake = cake;
            this.coffe = coffe;
        }

        public void run() {

            while (true) {
                synchronized (soup) {
                    while (soup.size() == 0) {
                        try {
                            soup.wait();
                        } catch (InterruptedException e) {

                        }

                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    soup.poll();
                    soup.notifyAll();
                    System.out.println(Thread.currentThread().getName() + " take a Soup");
                }
//            }

//        while (true) {
            synchronized (cake) {
                while (cake.size() == 0) {
                    try {
                        cake.wait();
                    } catch (InterruptedException e) {

                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cake.poll();
                cake.notifyAll();
                System.out.println(Thread.currentThread().getName() + " take a Cake");
            }
//        }

//while (true) {
    synchronized (coffe) {
        while (coffe.size() == 0) {
            try {
                coffe.wait();
            } catch (InterruptedException e) {

            }

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        coffe.poll();
        coffe.notifyAll();
        System.out.println(Thread.currentThread().getName() + " take a Coffe");
    }
}

        }
    }

    static class Chief implements Runnable {
        Queue soup;
        Queue cake;
        Queue coffe;

        public Chief(Queue soup, Queue cake, Queue coffe) {
            this.soup = soup;
            this.cake = cake;
            this.coffe = coffe;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (soup) {
                    while (soup.size() > 10) {
                        try {
                            soup.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    soup.add("soup");
                    soup.notifyAll();
                    System.out.println("Chief make a soup!");
                }
//            }

//            while (true) {
                synchronized (cake) {
                    while (cake.size() > 10) {
                        try {
                            cake.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cake.add("cake");
                    cake.notifyAll();
                    System.out.println("Chief make a cake!");
                }
//            }

//        while (true) {
            synchronized (coffe) {
                while (coffe.size() > 10) {
                    try {
                        coffe.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                coffe.add("coffe");
                coffe.notifyAll();
                System.out.println("Chief make a coffe!");
            }
        }

        }
    }

    public static void main(String args[]) {
        Queue soup = new LinkedList();
        Queue cake = new LinkedList();
        Queue coffe = new LinkedList();

        Client cl1 = new Client(soup, cake, coffe);
        Client cl2 = new Client(soup, cake, coffe);
        Client cl3 = new Client(soup, cake, coffe);

        Chief chief = new Chief(soup, cake, coffe);

        new Thread(cl1).start();
        new Thread(cl2).start();
        new Thread(cl3).start();
        new Thread(chief).start();

    }
}
