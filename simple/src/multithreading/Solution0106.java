package multithreading;

/*

    Атомарные операции

*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

public class Solution0106 {

    public static void main(String[] args) {
        Test01();
        Test02();
        Test03();
        Test04();
        Test05();
    }

    private static void Test01() {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10).forEach(i -> executor.submit(atomicInteger::incrementAndGet));

        ConcurrentUtils.stop(executor);

        System.out.println(atomicInteger.get());

    }

    private static void Test02() {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10).forEach(i -> {

            Runnable task = () -> {
                atomicInteger.updateAndGet(n -> n + 2);
            };

            executor.submit(task);
        });

        ConcurrentUtils.stop(executor);

        System.out.println(atomicInteger.get());


    }

    private static void Test03() {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000).forEach(i -> {

            Runnable task = () -> {
                atomicInteger.accumulateAndGet(i, (n, m) -> n + m);
//                atomicInteger.accumulateAndGet(i, Integer::sum);
            };

            executor.submit(task);
        });

        ConcurrentUtils.stop(executor);

        System.out.println(atomicInteger.get());

    }

    private static void Test04() {

        LongAdder longAdder = new LongAdder();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000).forEach(i -> executor.submit(longAdder::increment));

        ConcurrentUtils.stop(executor);

        System.out.println(longAdder.sumThenReset());

    }

    private static void Test05() {

        LongBinaryOperator longBinaryOperator = (x, y) -> 2 * x + y;
        LongAccumulator longAccumulator = new LongAccumulator(longBinaryOperator, 1L);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10).forEach(i -> executor.submit(() -> longAccumulator.accumulate(i)));

        ConcurrentUtils.stop(executor);

        System.out.println(longAccumulator.getThenReset());

    }

}
