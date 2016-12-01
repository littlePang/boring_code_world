package serivce;

/**
 * Created by jaky on 9/5/16.
 */

/*
 这个类可能会持续循环下去, 因为ReaderThread可能永远也读不到ready的值,
 也有可能 ReaderThread会输出0(ReaderThread看到的ready的写入值,却没能看到number的写入值)

*/
public class VisibilityThread {

    private static int number;
    private static boolean ready;

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }


}

