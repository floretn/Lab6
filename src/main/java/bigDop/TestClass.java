package bigDop;

import dop.ClassWithManyParameterType;
import dop.CoolManager;
import dop.Employee;
import dop.Manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TestClass {
    public volatile HashMap<String, ? extends Employee> field1;
    private Integer field2;
    protected HashMap<ArrayList<Comparator<? super Manager>>, Manager> field3;
    ArrayList<Integer> field4;
    ArrayList<? extends Employee> field5;
    static int field6;
    Comparator<? super Manager> field7;
    Integer field8;
    Employee field9;
    public static Number field10;
    HashMap<HashMap<String, ? extends Employee>, ArrayList<? extends Employee>> field11;
    Object field12;
    ClassWithManyParameterType<String, HashMap<String, ? extends Employee>, Comparator<? super Manager>,
                ? super CoolManager, Integer> fieldHard;

    public TestClass() {}
}
