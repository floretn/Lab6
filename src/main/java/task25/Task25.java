package task25;

import task1.Stack;
import task21.Task21;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public class Task25 {

    public <T extends Comparable<T>, K> T met(int i, T t, K k, ArrayList<? super String> ar) throws IOException{
        if (i == 0) {
            throw new IOException();
        }
        return t;
    }

    public static String genericDeclaration(Method m){
        if (m == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Annotation[] annotations = m.getDeclaredAnnotations();
        String s;
        for (Annotation a : annotations){
            s = a.toString();
            sb.append("@");
            sb.append(s.substring(s.lastIndexOf(".") + 1, s.lastIndexOf("(")));
            sb.append(" ");
        }
        sb.append(Modifier.toString(m.getModifiers()));
        sb.append(" ");
        //sb.append(m.toGenericString());
        sb.append(m.getGenericReturnType());


        s = sb.toString();
        return s;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = Stack.class.getDeclaredMethod("isEmpty");
        System.out.println(genericDeclaration(method));
        System.out.println(method.toGenericString());

        method = Task25.class.getDeclaredMethod("met", int.class, Comparable.class, Object.class, ArrayList.class);
        System.out.println(method.toGenericString());

        System.out.println(Arrays.toString(method.getParameters()));
    }
}
