package task17;

public class Employee implements Comparable<Employee> {

    String name;
    int salary;

    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }


    @Override
    public int compareTo(Employee o) {
        if (this.salary > o.salary){
            return 1;
        }

        if (this.salary < o.salary){
            return -1;
        }
        return this.name.compareTo(o.name);
    }

    public static void main(String[] args) {

    }
}
