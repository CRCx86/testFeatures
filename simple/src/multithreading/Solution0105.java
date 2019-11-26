package multithreading;

/*
Синхронизация
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

public class Solution0105 {

    private static int count = 0;

    private static final Object lock = new Object();

    private static ReentrantLock reentrantLock = new ReentrantLock();

    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static StampedLock stampedLock = new StampedLock();

    public static void main(String[] args) {
        Test01();
        Test02();
        Test03();
        Test04();
        Test05();
        Test06();
        Test07();
        Test08();
    }

    private static void increment() {
        synchronized (Solution0105.lock) {
            count++;
        }

    }

    private static void incrementLock() {
        reentrantLock.lock();
        try {
            count++;
        } finally {
            reentrantLock.unlock();
        }
    }

    private static void Test01() {
        ExecutorService service = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10000).forEach(i -> service.submit(Solution0105::increment));

        ConcurrentUtils.stop(service);
        System.out.println(count);
    }

    private static void Test02() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        IntStream.range(0, 10000).forEach(i -> service.submit(Solution0105::incrementLock));

        ConcurrentUtils.stop(service);
        System.out.println(count);
    }

    private static void Test03() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(() -> {
            reentrantLock.lock();
            try {
                ConcurrentUtils.sleep(1);
            } finally {
                reentrantLock.unlock();
            }
        });

        service.submit(() -> {
            System.out.println("Заблокирован?: " + reentrantLock.isLocked());
            System.out.println("Удерживает поток: " + Thread.currentThread().getName() + "?: " + reentrantLock.isHeldByCurrentThread());
            boolean tryLock = reentrantLock.tryLock();
            System.out.println("Получил блокировку?: " + tryLock);
        });

        ConcurrentUtils.stop(service);

    }

    private static void Test04() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();

        service.submit(() -> {

            readWriteLock.writeLock().lock();
            try {
                ConcurrentUtils.sleep(1);
                System.out.println("put: foo");
                map.put("foo", "bar");
            } finally {
                readWriteLock.writeLock().unlock();
            }

        });

        Runnable task = () -> {
            readWriteLock.readLock().lock();
            try {
                System.out.println(map.get("foo"));
                ConcurrentUtils.sleep(1);
            } finally {
                readWriteLock.readLock().unlock();
            }
        };

        service.submit(task);
        service.submit(task);

        ConcurrentUtils.stop(service);

    }

    private static void Test05() {

        ExecutorService service = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();

        service.submit(() -> {

            long stamped = stampedLock.writeLock();
            try {
                ConcurrentUtils.sleep(1);
                System.out.println("put: foo");
                map.put("foo", "bar");
            } finally {
                stampedLock.unlockWrite(stamped);
            }

        });

        Runnable task = () -> {
            long stamped = stampedLock.readLock();
            try {
                System.out.println(map.get("foo"));
                ConcurrentUtils.sleep(1);
            } finally {
                stampedLock.unlockRead(stamped);
            }
        };

        service.submit(task);
        service.submit(task);

        ConcurrentUtils.stop(service);

    }

    private static void Test06() {

        ExecutorService service = Executors.newFixedThreadPool(2);

        service.submit(() -> {
            long stamped = stampedLock.tryOptimisticRead();
            try {
                System.out.println("Optimistic lock valid: " + stampedLock.validate(stamped));
                ConcurrentUtils.sleep(1);
                System.out.println("Optimistic lock valid: " + stampedLock.validate(stamped));
                ConcurrentUtils.sleep(1);
                System.out.println("Optimistic lock valid: " + stampedLock.validate(stamped));
            } finally {
                stampedLock.unlock(stamped);
            }
        });

        service.submit(() -> {
            long stamped = stampedLock.writeLock();
            try {
                System.out.println("Write lock acquired");
                ConcurrentUtils.sleep(2);
            } finally {
                stampedLock.unlock(stamped);
                System.out.println("Write done");
            }
        });
        ConcurrentUtils.stop(service);
    }

    private static void Test07() {

        ExecutorService service = Executors.newFixedThreadPool(2);

        service.submit(() -> {
            long stamped = stampedLock.readLock();
            try {
                if (count > 0) {
                    stamped = stampedLock.tryConvertToWriteLock(stamped);
                    if (stamped == 0L) {
                        System.out.println("Could not convert to write lock");
                        stamped = stampedLock.writeLock();
                    }
                    count = 23;
                }
                System.out.println("Count:" + count);
            } finally {
                stampedLock.unlock(stamped);
            }

        });

        ConcurrentUtils.stop(service);

    }

    private static void Test08() {

        ExecutorService service = Executors.newFixedThreadPool(10);

        Semaphore semaphore = new Semaphore(5);

        Runnable task = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired");
                    ConcurrentUtils.sleep(5);
                } else {
                    System.out.println("Could not semaphore acquired");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };

        IntStream.range(0, 10).forEach(i -> {
            service.submit(task);
        });

        ConcurrentUtils.stop(service);
    }

}
