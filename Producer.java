import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;
import java.util.Random;

class ProducerConsumer {
    private static final int BUFFER_SIZE = 10;
    private final int[] buffer = new int[BUFFER_SIZE];
    private int in = 0;
    private int out = 0;

    private final Semaphore empty = new Semaphore(BUFFER_SIZE);
    private final Semaphore full = new Semaphore(0);
    private final ReentrantLock mutex = new ReentrantLock();
    private final Random rand = new Random();

    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 20; i++) {
                    int item = rand.nextInt(100);
                    empty.acquire();
                    mutex.lock();
                    try {
                        buffer[in] = item;
                        in = (in + 1) % BUFFER_SIZE;
                        System.out.println("Producer produced " + item);
                    } finally {
                        mutex.unlock();
                    }
                    full.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    full.acquire();
                    mutex.lock();
                    try {
                        int item = buffer[out];
                        out = (out + 1) % BUFFER_SIZE;
                        System.out.println("Consumer consumed " + item);
                    } finally {
                        mutex.unlock();
                    }
                    empty.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void start() {
        Thread producerThread = new Thread(new Producer());
        Thread consumerThread = new Thread(new Consumer());

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        new ProducerConsumer().start();
    }
}
