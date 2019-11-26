package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Solution0104 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newWorkStealingPool();
    }
}
