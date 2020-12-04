package task15;

import java.util.ArrayList;
import java.util.function.Function;

public class Task15 {

    public static <T, R> ArrayList<R> map(ArrayList<T> arrayListT, Function<T, R> function){
        ArrayList<R> arrayListR = new ArrayList<>();
        for (T el : arrayListT){
            arrayListR.add(function.apply(el));
        }
        return arrayListR;
    }

    public static void main(String[] args) {
        ArrayList<String> arrayListString = new ArrayList<>();
        arrayListString.add("gjkrer");
        arrayListString.add("fejw");
        arrayListString.add("g");
        ArrayList<Integer> arrayListInteger = map(arrayListString, String::length);
        System.out.println(arrayListInteger);
    }
}
