package dop;

import task7And8.Pair;
import java.lang.reflect.InvocationTargetException;

public class RowTypesAreDangerous {

    public static <T extends Comparable<T>> Pair<T> create(Object o1, Object o2){
        return new Pair<>((T) o1,(T) o2);
    }

    public static <T extends Comparable<T>> Pair<T> createNorm(T o1, T o2){
        return new Pair<>( o1, o2);
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Pair<String> pair = create(new Employee("Name1", 1234), new Employee("Name2", 4321));
        System.out.println(pair);
        System.out.println((Object) pair.max());
        Employee employee = (Employee) ((Object) pair.min());
        System.out.println(employee);

        Pair<String> pairNorm = (Pair<String>) RowTypesAreDangerous.class.
                getDeclaredMethod("createNorm", Comparable.class, Comparable.class).
                invoke(new RowTypesAreDangerous(),
                        new Employee("Name1", 1234), new Employee("Name2", 4321));
        System.out.println(pairNorm);
        System.out.println((Object) pairNorm.max());
        employee = (Employee) ((Object) pairNorm.min());
        System.out.println(employee);

        Pair<Employee> pairEmp = (Pair<Employee>) ((Object) pairNorm);
    }
}

