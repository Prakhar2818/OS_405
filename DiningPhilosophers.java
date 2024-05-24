import java.util.concurrent.Semaphore;

class Philosopher extends Thread {
    private int id;
    private Semaphore leftFork;
    private Semaphore rightFork;
    private int eatCount;

    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork, int eatCount) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.eatCount = eatCount;
    }

    public void run() {
        try {
            for (int i = 0; i < eatCount; i++) {
                think();
                if (id % 2 == 0) {
                    leftFork.acquire();
                    rightFork.acquire();
                } else {
                    rightFork.acquire();
                    leftFork.acquire();
                }
                eat();
                leftFork.release();
                rightFork.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep((long) (Math.random() * 1000));
        System.out.println("Philosopher " + id + " has finished eating.");
    }
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        int numPhilosophers = 3;  // Reduced number of philosophers to 3
        int eatCount = 5;  // Number of times each philosopher will eat
        Philosopher[] philosophers = new Philosopher[numPhilosophers];
        Semaphore[] forks = new Semaphore[numPhilosophers];

        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i = 0; i < numPhilosophers; i++) {
            int leftForkIndex = i;
            int rightForkIndex = (i + 1) % numPhilosophers;
            philosophers[i] = new Philosopher(i, forks[leftForkIndex], forks[rightForkIndex], eatCount);
            philosophers[i].start();
        }

        // Wait for all philosophers to finish
        for (int i = 0; i < numPhilosophers; i++) {
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("All philosophers have finished their meals.");
    }
}
