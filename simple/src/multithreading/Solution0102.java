package multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Solution0102 {
    public static void main(String[] args) throws InterruptedException {

//        Example 1
        Runnable task1 = () -> {
            System.out.println(String.format("This is: %s", Thread.currentThread().getName()));
        };
        task1.run();
        new Thread(task1).start();
        System.out.println("Done");

//        Example 2
        Runnable task2 = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println(String.format("This is: %s", name));
                TimeUnit.SECONDS.sleep(1);
                System.out.println(String.format("This is: %s", name));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        task2.run();
        new Thread(task2).start();
        System.out.println("Done");


        // callable
        System.out.println("Callable block");
        Callable<Integer> task3 = () -> {
            String name = Thread.currentThread().getName();
            System.out.println(String.format("This is: %s", name));
            return 123;
        };

        FutureTask futureTask = new FutureTask<Integer>(task3);
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            System.out.println(futureTask.get());
            if (futureTask.isDone()) {
                System.out.println("result: " + futureTask.isDone());
            } else {
                System.out.println("result: " + futureTask.isDone());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
