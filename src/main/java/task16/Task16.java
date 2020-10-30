package task16;

import java.util.Collection;
import java.util.List;

public class Task16 {

    public static <T extends Comparable<? super T>> void sort(List<T> list){

    }
    public static <T extends Object & Comparable<? super T >> T max (Collection<? extends T> coll){
        return coll.iterator().next();
    }

    public static void main(String[] args) {

    }
}
