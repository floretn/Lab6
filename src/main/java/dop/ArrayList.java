package dop;

public class ArrayList {

    private Object[] objects = new Object[10];
    public int size = 0;

    public void add(Object obj){
        objects[size] = obj;
        size++;
    }

    public Object get(int index){
        return objects[index];
    }

    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("Abcde");
        arrayList.add(12);
        arrayList.add(new Employee("Bgjr", 1323435));
        String s = (String) arrayList.get(0);
        System.out.println(s);
        s = (String) arrayList.get(2);
        System.out.println(s);
        int i = (int) arrayList.get(3);
    }
}
