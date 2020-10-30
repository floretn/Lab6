package task10And12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Arrays {

    public static <E extends Comparable<? super E>> E max(E[] array){
        E maxEl = array[0];
        for(E el : array){
            if(el.compareTo(maxEl) > 0){
                maxEl = el;
            }
        }
        return maxEl;
    }

    public static <E extends Comparable<? super E>> E min(E[] array){
        E maxEl = array[0];
        for(E el : array){
            if(el.compareTo(maxEl) < 0){
                maxEl = el;
            }
        }
        return maxEl;
    }

    public static <T> void maxMin(List<T> elements, Comparator<? super T> comp, List<? super T> result) {

        T max = elements.get(1);
        T min = elements.get(0);

        for(T el : elements){
            if(comp.compare(max, el) < 0){
                max = el;
                continue;
            }

            if(comp.compare(min, el) > 0){
                min = el;
            }
        }
        if(min != max) {
            result.add(min);
            result.add(max);
        }else{
            result.add(null);
            result.add(null);
        }
    }


        public static void main(String[] args) {
        Integer[] array1 = new Integer[] {1, 2, 12, 5434, 0, -124, 43};
        System.out.println(min(array1));
        System.out.println(max(array1));
        String[] array2 = new String[] {"Abcd", "fkerger", "uityrme", "qwetwdwfw", "aahevefd"};
        System.out.println(min(array2));
        System.out.println(max(array2));
        Comparator<String> comparator = (s1, s2) -> {
            if(s1.length() > s2.length())
                return 1;
            return -1;};
        List<String> list = new ArrayList<>();
        list.add("abcd");
        list.add("krvever");
        list.add("w");
        list.add("vrejbmelrbe");
        List<String> rez = new ArrayList<>();
        maxMin(list, comparator, rez);
        System.out.println(rez);
    }
}
