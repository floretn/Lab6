package task18And20;

import org.omg.CORBA.Object;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.lang.reflect.Array;

public class Task18And20 {

    public static <T> T[] repeat(int n, T obj, IntFunction<T[]> constr) {
        T [ ] result = constr.apply(n);
        for (int i = 0; i < n; i++) result[i] = obj;
        return result;
    }

    @SafeVarargs public static <T> T[] repeat(int n, T... objs){
        @SuppressWarnings("unchecked") T[] array =
                (T[]) Array.newInstance(objs.getClass().getComponentType(), objs.length*n);
        for (int i = 0; i < objs.length; i++){
            for (int j = i; j < array.length; j += objs.length){
                array[j] = objs[i];
            }
        }
        return array;
    }

    public static void main(String[] args) {
        String[] array = new String[] {"Hello", " ", "World", "!"};
        String[] arrayNew = repeat(2, array);
        System.out.println(Arrays.toString(arrayNew));

        //repeat(10, 42, int[]::new);
        Integer[] a = repeat(10, 42, Integer[]::new);
        Character[] b = repeat(10, 'c', Character[]::new);
        //Short[] c = (Short[]) repeat(10, 1, Short[]::new); //Подумать!
        Boolean[] d = repeat(10, false, Boolean[]::new);
    }
}
