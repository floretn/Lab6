package dop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Contravariance {

    public static void swap(List<Manager> list, Comparator<? super Manager> comparator){
        Manager manager = null;
        for (int i = 0; i < list.size() - 1; i++){
            for (int j = 1; j < list.size(); j++) {
                if (comparator.compare(list.get(i), list.get(j)) > 0) {
                    manager = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, manager);
                }
            }
        }
    }

    public static void main(String[] args) {
        List<Manager> managers = new ArrayList<>();
        managers.add(new Manager("Ivan", 150000, 10000));
        managers.add(new Manager("Vova", 100000, 1000));
        managers.add(new Manager("Vova", 100000, 1000));
        Comparator<Employee> comparator = (e1, e2) -> {
            if (e1.salary - e2.salary != 0){
                return e1.salary - e2.salary;
            } else{
                return e1.name.compareTo(e2.name);
            }
        };

        /*
        Comparator comparator1 = (e1, e2) -> {
            return e1.toString().length() - e2.toString().length();
        };*/
        swap(managers, comparator);
        System.out.println(managers);
    }
}