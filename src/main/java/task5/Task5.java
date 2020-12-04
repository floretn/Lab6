package task5;

import java.util.Arrays;

public class Task5 {

    public static <T> T[] swap(int i, int j, T... values) {
        T temp = values[i];
        values[i] = values[j];
        values[j] = temp;
        return values;
    }

    public static void main(String[] args) {
        Number[] result = swap(0, 1, 1.5, 2, 3);
        System.out.println(Arrays.toString(result));

        Double a = 1.5;
        Double b = 2.0;
        Double c = 3.0;
        Double[] rez = swap(0, 1, a, b, c);
        System.out.println(Arrays.toString(rez));
    }
}
