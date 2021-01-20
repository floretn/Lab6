package dop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Covariance {

    public static void someMethod(List<? extends Employee> list){
        for (Employee employee : list){
            System.out.println(employee.name + "'s salary = " + employee.salary);
        }
        //list.add(new Employee("name", 123));

    }

    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Ivan", 150000));
        employees.add(new Employee("Vova", 100000));
        someMethod(employees);

        List<Manager> managers = new ArrayList<>();
        managers.add(new Manager("Ivan", 150000, 10000));
        managers.add(new Manager("Vova", 100000, 1000));

        //ArrayList<Employee> employees2 = (ArrayList<Employee>) managers;
        someMethod(managers);

        Manager[] managers1 = new Manager[2];
        managers1[0] = new Manager("Ivan", 150000, 10000);
        //managers1[1] = new Employee("Vova", 100000);
        Employee[] employees1 = managers1;
        employees1[1] = new Employee("Vova", 100000);
        System.out.println(Arrays.toString(employees1));
    }
}