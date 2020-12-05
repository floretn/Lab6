package bigDop;

import dop.CoolManager;
import dop.Manager;

public class ClassExtendsCWTP extends ClassWithTypeParameter<Number, Manager, String> {

    public static void main(String[] args) {
        ClassExtendsCWTP classExtendsCWTP = new ClassExtendsCWTP();
        classExtendsCWTP.g = new CoolManager("fgd", 100, 100);
        classExtendsCWTP.es = new Integer[5];
    }
}
