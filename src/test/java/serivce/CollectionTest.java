package serivce;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Created by jaky on 7/19/16.
 */
public class CollectionTest {

   @Test
    public void collection_to_array_test() {
       Data data1 = new Data(1);
       Data data2 = new Data(2);
       Data data3 = new Data(3);
       HashSet<Data> hashSet = Sets.newHashSet(data1, data2, data3);

       Object[] dataArray = hashSet.toArray();

       Object[] sysDataArray = new Object[hashSet.size()];
       System.arraycopy(dataArray, 0, sysDataArray, 0, sysDataArray.length);

       System.out.println("old hashSet "+hashSet);
       System.out.println("old dataArray "+ Arrays.toString(dataArray));
       System.out.println("old sysDataArray" + Arrays.toString(sysDataArray));

       data1.setCode(5);
       System.out.println(1000/64 + (1000%64 == 0 ? 0 : 1));
       System.out.println("=======================================");
       System.out.println("first hashSet "+hashSet);
       System.out.println("first dataArray "+ Arrays.toString(dataArray));
       System.out.println("first sysDataArray" + Arrays.toString(sysDataArray));

   }

   private static class Data {
       private int code;

       private Data(int code) {
           this.code = code;
       }

       public int getCode() {
           return code;
       }

       public void setCode(int code) {
           this.code = code;
       }

       @Override
       public String toString() {
           return "code=" + code;
       }
   }

}
