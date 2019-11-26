package multithreading;

//Example
//Executor service

import java.util.concurrent.*;

public class Solution0103 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        // запуск runnable
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(String.format("This is: %s", Thread.currentThread().getName()));
            }
        });

        // или так
        executorService.submit(() -> {
            System.out.println(String.format("This is: %s", Thread.currentThread().getName()));
        });

        // остановка
        try {
            System.out.println("This is trying to stop executors");
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Task interrupted");
        } finally {
            if (!executorService.isTerminated()) {
                executorService.shutdownNow();
                System.out.println("Cancel non-finished tasks");
            }
            executorService.shutdownNow();
            System.out.println("ShutdownNow finished");
        }

        Callable<Integer> task = () -> {
            TimeUnit.SECONDS.sleep(2);
            return 123;
        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> submit = executor.submit(task);
        System.out.println(submit.isDone());
        Integer integer = submit.get(1, TimeUnit.SECONDS);
        System.out.println(submit.isDone());
        System.out.println(integer);

        executor.shutdownNow();
    }
}
