package task6;

public class Class extends SuperClass{

    int i;

    public Class(String s, int i) {
        super(s);
        this.i = i;
    }

    @Override
    public String toString() {
        return "Class{" +
                "i=" + i +
                ", s='" + s + '\'' +
                '}';
    }
}
