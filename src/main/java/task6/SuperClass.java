package task6;

public class SuperClass {

    String s;

    public SuperClass(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "SuperClass{" +
                "s='" + s + '\'' +
                '}';
    }
}
