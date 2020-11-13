package task6;

import java.util.ArrayList;

public class Task6 {

    public static <E> void split1(ArrayList<E> arrayListTo, ArrayList<? extends E> arrayListFrom){
        arrayListTo.addAll(arrayListFrom);
    }

    public static <E> void split2(ArrayList<? super E> arrayListTo, ArrayList<E> arrayListFrom){
        arrayListTo.addAll(arrayListFrom);
    }

    public static <E> void split3(ArrayList<? super E> arrayListTo, ArrayList<? extends E> arrayListFrom){
        arrayListTo.addAll(arrayListFrom);
    }

    public static void main(String[] args) {
        ArrayList<SuperClass> arrayListTo = new ArrayList<>();
        arrayListTo.add(new SuperClass("One"));
        arrayListTo.add(new SuperClass("Two"));
        ArrayList<Class> arrayListFrom = new ArrayList<>();
        arrayListFrom.add(new Class("Three", 3));
        arrayListFrom.add(new Class("Four", 4));

        split1(arrayListTo, arrayListFrom);
        System.out.println(arrayListTo);
        arrayListTo.remove(3);
        arrayListTo.remove(2);
        System.out.println(arrayListTo);

        split2(arrayListTo, arrayListFrom);
        System.out.println(arrayListTo);
    }
}
