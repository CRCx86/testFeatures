package multithreading;

public class Solution0101 {

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        int i1 = -2 >>> 1;
        System.out.println(Integer.toString(-2, 2));
        System.out.println(Integer.toString(i1, 2));
        System.out.println(i1);

        int i2 = 3 & 1;
        System.out.println(i2);

//        for (int i = 0; i < 10; i++) {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(count++);
//                }
//            });
//            thread.start();
//            thread.join();
//        }

    }
}
