import java.util.Objects;
import java.util.concurrent.Semaphore;

public class MotherWaitsForChild {

    private static volatile boolean done = false;
    private final static  Object lock = new Object();

    private static class Child implements Runnable {

        private final Object lock;

        public Child(Object lock) {
            this.lock = lock;
        }

        private void thr_exit() {
            synchronized (lock) {
                System.out.println("child: finished, signal mother...");
                done = true;
                lock.notify();
            }
        }

        @Override
        public void run() {
            thr_exit();
        }

    }

    public static void thr_join(Object lock){
        synchronized (lock) {
            while (!done) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("mother: woke up!");
    }


    public static void main(String[] args){
        System.out.println("mother: begin");
        Thread childThread = new Thread(new Child(lock), "child-thread");
        childThread.start();

        thr_join(lock);
        System.out.println("mother: end");

    }
}
