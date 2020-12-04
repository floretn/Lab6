package dop;

import java.util.ArrayList;

public class Manager extends Employee{

    int bonus;

    public Manager(String name, int salary, int bonus) {
        super(name, salary);
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public static ArrayList<Manager> doManagers(ArrayList<Employee> employees){
        ArrayList<Manager> managers = new ArrayList<>();
        for (Employee employee : employees){
            managers.add(new Manager(employee.name, employee.salary, 1000));
        }
        return managers;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "bonus=" + bonus +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

    public int compareTo(Manager o) {
        if (this.bonus != o.bonus){
            return this.bonus - o.bonus;
        }
        return super.compareTo(o);
    }
}
