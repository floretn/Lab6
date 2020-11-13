package task10To13;

import task7And8.Pair;

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
        E minEl = array[0];
        for(E el : array){
            if(el.compareTo(minEl) < 0){
                minEl = el;
            }
        }
        return minEl;
    }

    public static <E extends Comparable<? super E>> Pair<E> minMax(E[] array){
        E min = array[0];
        E max = array[0];
        for(E el : array){
            if(el.compareTo(min) < 0){
                min = el;
            }
            if(el.compareTo(max) > 0){
                max = el;
            }
        }
        return new Pair<>(min, max);
    }

    public static <T> void minMax(List<T> elements, Comparator<? super T> comp, List<? super T> result) {

        T max = elements.get(0);
        T min = elements.get(0);

        for (T el : elements) {
            if (comp.compare(max, el) < 0) {
                max = el;
                continue;
            }

            if (comp.compare(min, el) > 0) {
                min = el;
            }
        }
        result.add(min);
        result.add(max);
    }

    public static <T> void maxMin(List<T> elements,
                                  Comparator<? super T> comp, List<? super T> result) {
        minMax(elements, comp, result);
        swapHelper(result, 0, 1);
    }

    private static <T> void swapHelper(List<T> elements, int i1, int i2){
        T el = elements.get(i1);
        elements.set(i1, elements.get(i2));
        elements.set(i2, el);
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
        minMax(list, comparator, rez);
        System.out.println(rez);
        Pair<String> pair = minMax(array2);
        System.out.println(pair.getVal1() + " " + pair.getVal2());
        rez = new ArrayList<>();
        maxMin(list, comparator, rez);
        System.out.println(rez);
    }
}
