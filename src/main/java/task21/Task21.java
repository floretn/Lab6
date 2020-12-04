package task21;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Task21 {

    @SafeVarargs
    public static <T> T[] construct(int n, T... objs){
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(objs.getClass().getComponentType(), n);
        return  array;
    }

    public static void main(String[] args) {
        ArrayList<Integer>[] mass = Task21.<ArrayList<Integer>>construct(5, new ArrayList<Integer>());
        mass[0] = new ArrayList<>();
        mass[0].add(0);
        mass[1] = new ArrayList<>();
        mass[1].add(1);
        mass[2] = new ArrayList<>();
        mass[2].add(2);
        mass[3] = new ArrayList<>();
        mass[3].add(3);
        mass[4] = new ArrayList<>();
        mass[4].add(4);

        for (ArrayList<Integer> al : mass){
            System.out.println(al);
        }
    }
}