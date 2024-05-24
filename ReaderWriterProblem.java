import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class SharedResource {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition canRead = lock.newCondition();
    private final Condition canWrite = lock.newCondition();
    private int readCount = 0;
    private boolean isWriting = false;

    public void startRead(int readerId) throws InterruptedException {
        lock.lock();
        try {
            while (isWriting) {
                canRead.await();
            }
            readCount++;
            System.out.println("Reader " + readerId + " is reading. Readers count: " + readCount);
        } finally {
            lock.unlock();
        }
    }

    public void endRead(int readerId) {
        lock.lock();
        try {
            readCount--;
            System.out.println("Reader " + readerId + " finished reading. Readers count: " + readCount);
            if (readCount == 0) {
                canWrite.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void startWrite(int writerId) throws InterruptedException {
        lock.lock();
        try {
            while (isWriting || readCount > 0) {
                canWrite.await();
            }
            isWriting = true;
            System.out.println("Writer " + writerId + " is writing.");
        } finally {
            lock.unlock();
        }
    }

    public void endWrite(int writerId) {
        lock.lock();
        try {
            isWriting = false;
            System.out.println("Writer " + writerId + " finished writing.");
            canRead.signalAll();
            canWrite.signal();
        } finally {
            lock.unlock();
        }
    }
}

class Reader implements Runnable {
    private final SharedResource resource;
    private final int readerId;

    public Reader(SharedResource resource, int readerId) {
        this.resource = resource;
        this.readerId = readerId;
    }

    @Override
    public void run() {
        try {
            resource.startRead(readerId);
            Thread.sleep(1000); // Simulate reading time
            resource.endRead(readerId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Writer implements Runnable {
    private final SharedResource resource;
    private final int writerId;

    public Writer(SharedResource resource, int writerId) {
        this.resource = resource;
        this.writerId = writerId;
    }

    @Override
    public void run() {
        try {
            resource.startWrite(writerId);
            Thread.sleep(2000); // Simulate writing time
            resource.endWrite(writerId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ReaderWriterProblem {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread[] readers = new Thread[10];
        Thread[] writers = new Thread[5];

        for (int i = 0; i < 10; i++) {
            readers[i] = new Thread(new Reader(resource, i + 1));
        }

        for (int i = 0; i < 5; i++) {
            writers[i] = new Thread(new Writer(resource, i + 1));
        }

        for (Thread reader : readers) {
            reader.start();
        }

        for (Thread writer : writers) {
            writer.start();
        }

        try {
            for (Thread reader : readers) {
                reader.join();
            }
            for (Thread writer : writers) {
                writer.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
