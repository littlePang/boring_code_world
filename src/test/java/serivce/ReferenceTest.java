package serivce;

import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * Created by jaky on 8/11/16.
 */
public class ReferenceTest {

    @Test
    public void reference_queue_test() throws InterruptedException {

        ReferenceQueue<MyClass> referenceQueue = new ReferenceQueue<MyClass>();

        MyClass myClass = new MyClass();

        SoftReference<MyClass> weakReference = new SoftReference<MyClass>(myClass, referenceQueue);
        myClass = null;

        // 第一次垃圾回收
        System.out.println("first gc");
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        Reference<? extends MyClass> garbage = referenceQueue.poll();
        print(garbage);

        // 第二次垃圾回收
        System.out.println("second gc");
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        garbage = referenceQueue.poll();
        print(garbage);

        // 第三次垃圾回收
        System.out.println("third gc");
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        garbage = referenceQueue.poll();
        print(garbage);

    }

    private void print(Reference<? extends MyClass> garbage) {
        if (garbage == null) {
            System.out.println("not find referenceQueue element");
        } else {
            System.out.println("reference queue = {" + garbage + "} element = " + garbage.get());

        }
    }

    public static class MyClass {

        @Override
        public String toString() {
            return "MyClass Object";
        }

    }

}
