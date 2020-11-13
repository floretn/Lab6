package task7And8;

import java.util.Comparator;

public class Pair<E extends Comparable<? super E>> {

    E val1;
    E val2;

    public Pair(E val1, E val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public E getVal1() {
        return val1;
    }

    public E getVal2() {
        return val2;
    }

    public E max(){
        if(val1.compareTo(val2) >= 0){
            return val1;
        }
        return val2;
    }

    public E max(Comparator<? super E> comparator){
        if(comparator.compare(val1, val2) >= 0){
            return val1;
        }
        return val2;
    }

    public E min(){
        if(val1.compareTo(val2) < 0){
            return val1;
        }
        return val2;
    }

    public E min(Comparator<? super E> comparator){
        if(comparator.compare(val1, val2) < 0){
            return val1;
        }
        return val2;
    }

    public static void main(String[] args) {
        Pair<Integer> pair = new Pair<>(12, 16);
        System.out.println(pair.max());
        System.out.println(pair.min());

        Pair<String> pair1 = new Pair<>("str", "abcd");
        System.out.println(pair1.max());
        System.out.println(pair1.min());
        Comparator<String> comparator = (s1, s2) -> {
            if(s1.length() > s2.length())
                return 1;
            return -1;};
        System.out.println(pair1.min(comparator));
        System.out.println(pair1.max(comparator));
    }
}