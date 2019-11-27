package multithreading;

/*
    конкурентные таблицы
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;

public class Solution0107 {

    private static ConcurrentMap<String, String> hashMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap();

    static {

        hashMap.put("foo", "bar");
        hashMap.put("han", "solo");
        hashMap.put("r2", "d2");
        hashMap.put("c3", "p0");

        map.put("foo", "bar");
        map.put("han", "solo");
        map.put("r2", "d2");
        map.put("c3", "p0");

    }

    public static void main(String[] args) {
        Test01();
        Test02();
    }

    private static void Test01() {
        String s = hashMap.putIfAbsent("c3", "p1");
        System.out.println(s);

        String aDefault = hashMap.getOrDefault("c4", "default");
        System.out.println(aDefault);

        hashMap.replaceAll((key, value) -> "r2".equals(key) ? "d5" : value);
        System.out.println(hashMap.get("r2"));

        hashMap.compute("foo", (key, value) -> value + value);
        System.out.println(hashMap.get("foo"));

        hashMap.merge("foo", "boo", (oldValue, newValue) -> newValue + " was " + oldValue);
        System.out.println(hashMap.get("foo"));
    }

    private static void Test02() {

        System.out.println(ForkJoinPool.getCommonPoolParallelism());

        map.forEach(1, (key, value) -> { // первый параметр, минимальное кол-во элементво в коллекции, чтобы обработка распаллелилась
            System.out.printf("key: %s; value: %s; thread: %s\n", key, value, Thread.currentThread().getName());
        });

        String search = map.search(1, (key, value) -> { // первый параметр, минимальное кол-во элементво в коллекции, чтобы обработка распаллелилась
            System.out.printf("key: %s; value: %s; thread: %s\n", key, value, Thread.currentThread().getName());
            if ("foo".equals(key))
                return key;
            return null;
        });
        System.out.println("result: " + search);

        search = map.searchValues(1, value -> { // первый параметр, минимальное кол-во элементво в коллекции, чтобы обработка распаллелилась
            System.out.printf("value: %s; thread: %s\n", value, Thread.currentThread().getName());
            if (value.length() > 3)
                return value;
            return null;
        });
        System.out.println("result: " + search);

        String reduce = map.reduce(1, (key, value) -> {
                    System.out.println("Transform: " + Thread.currentThread().getName());
                    return key + "=" + value;
                },
                (s1, s2) -> {
                    System.out.println("Reduce: " + Thread.currentThread().getName());
                    return s1 + "," + s2;
                });
        System.out.println("Result: " + reduce);




    }

}
