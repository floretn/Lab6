package bigDop;

import com.sun.corba.se.spi.orb.ORBVersion;
import dop.Employee;
import org.reflections.Reflections;
import java.io.ObjectStreamField;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Inject {

    static ListOne al;
    ArrayList<Float> listFloat;
    Employee employee;
    int i;
    Object object;
    Integer[] ints;
    long[] longs;
    Number[] numbers;
    ArrayList<? extends Number> arrayList;
    Comparator<? super Integer> comparator;
    Comparable<? super String> comparable;
    MyInterface myInterface;
    ArrayList<Comparable<String>> als;
    ArrayList<MyComparator<? extends String>> acs;
    ArrayList<? extends Comparable<String>> acsHard;
    Comparator<String>[] arr;


    private List<Class<?>> myList;
    private List<Class<?>> listSubSubClass = new ArrayList<>();
    private Field fieldO;

    public List<Class<?>> list(Object o, String field) throws NoSuchFieldException, ClassNotFoundException {
        Class<?> clazz = o.getClass();
        fieldO = clazz.getDeclaredField(field);
        ParameterizedType parameterizedType;
        Class<?> fieldClass = fieldO.getType();
        myList = new ArrayList<>();
        try {
            parameterizedType = (ParameterizedType) fieldO.getGenericType();
        }catch (ClassCastException ex){
            if (fieldClass.isArray()){
                String name = fieldClass.toString().substring(8);
                myList.add(fieldClass);
                if (name.length() == 0) {
                    return myList;
                }
                Class<?> classOfArray = Class.forName(name.substring(0, name.length() - 1));
                myList.addAll((new Reflections()).getSubTypesOf(classOfArray));
                for (int i = 0; i < myList.size(); i++) {
                    myList.set(i, (Array.newInstance(myList.get(i), 0)).getClass());
                }
                return myList;
            }
            if (fieldClass.equals(Object.class)){
                myList.add(Object.class);
                return myList;
            }
            myList.add(fieldO.getType());
            if (!fieldClass.isPrimitive()) {
                myList.addAll((new Reflections()).getSubTypesOf(fieldO.getType()));
            }
            return myList;
        }

        Type[] types = parameterizedType.getActualTypeArguments();
        List<Class<?>> listSubClass = new ArrayList<>((new Reflections()).getSubTypesOf(fieldClass));
        for (Class<?> subClass : listSubClass){
            if (listRecursion(fieldClass, fieldO.getGenericType(), subClass, null, types)){
                myList.add(subClass);
            }
        }
        forAllChildren();
        return myList;
    }

    private boolean listRecursion(Class<?> fieldClass, Type fieldType, Class<?> clazz, Type clazzType, Type... types)
            throws ClassNotFoundException {

        System.out.println(fieldClass);
        System.out.println(fieldType);
        System.out.println(clazz);
        System.out.println(clazzType);
        System.out.println(types[0]);
        System.out.println("******************");
        Type[] typesCurrentClass;
        //System.out.println(clazz);
        if (clazz != null) {

            if (clazz.getTypeParameters().length != 0) {
                return false;
            }

            ParameterizedType ptCurrentClass;
            if (fieldClass.isInterface()) {
                //System.out.println(clazz);
                Type[] interfaces = clazz.getGenericInterfaces();
                //System.out.println(Arrays.toString(interfaces));
                Type interfaceOf = null;
                String fieldONameInterface = fieldType.getTypeName();
                fieldONameInterface = fieldONameInterface.substring(0, fieldONameInterface.lastIndexOf("<"));
                for (Type type : interfaces) {
                    if (type.getTypeName().contains(fieldONameInterface)) {
                        interfaceOf = type;
                    }
                }
                if (!(interfaceOf == null)) {
                    try {
                        ptCurrentClass = (ParameterizedType) interfaceOf;
                    } catch (java.lang.ClassCastException ex) {
                        listSubSubClass.add(clazz);
                        return false;
                    }
                } else {
                    try {
                        ptCurrentClass = (ParameterizedType) clazz.getGenericSuperclass();
                    } catch (java.lang.ClassCastException ex) {
                        listSubSubClass.add(clazz);
                        return false;
                    }
                }
            } else {
                try {
                    ptCurrentClass = (ParameterizedType) clazz.getGenericSuperclass();
                } catch (java.lang.ClassCastException ex) {
                    listSubSubClass.add(clazz);
                    return false;
                }
            }
            //System.out.println(clazz);
            typesCurrentClass = ptCurrentClass.getActualTypeArguments();
        }else{
            typesCurrentClass = new Type[1];
            typesCurrentClass[0] = clazzType;
        }

        //System.out.println(clazz);
        boolean good = false;

        for (int i = 0; i < types.length; i++){

            good = false;

            if (types[i].equals(typesCurrentClass[i])){
                good = true;
                continue;
            }

            if (!(types[i] instanceof WildcardType) && types[i] instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) types[i];
                ParameterizedType parameterizedTypeCurrentClass;
                try {
                    parameterizedTypeCurrentClass = (ParameterizedType) typesCurrentClass[i];
                }catch (ClassCastException ex){
                    return false;
                }
                if (parameterizedType.getRawType().equals(parameterizedTypeCurrentClass.getRawType())){
                    good = workWithArray(parameterizedType.getActualTypeArguments(), parameterizedTypeCurrentClass.getActualTypeArguments());
                    if (!good){
                        return false;
                    }
                    continue;
                }else{
                    return  false;
                }
            }

            ParameterizedType parameterizedType;
            ParameterizedType parameterizedTypeCurrentClass;
            if (types[i] instanceof ParameterizedType && types[i] instanceof WildcardType &&
                    !(typesCurrentClass[i] instanceof ParameterizedType)){
                System.out.println("Tut");
                parameterizedType = (ParameterizedType) types[i];
                good = workWithParameterizedWildcard(parameterizedType, types[i], typesCurrentClass[i]);
                if (!good){
                    return false;
                }
                continue;
            }

            WildcardType wt;
            try{
                wt = (WildcardType) types[i];
                System.out.println(wt.getTypeName());
            }catch (ClassCastException ex){
                return false;
            }

            Type[] lower = wt.getLowerBounds();
            Type[] upper = wt.getUpperBounds();
            boolean checkUpLow = false;

            if (lower.length != 0){
                if (types[i] instanceof ParameterizedType && typesCurrentClass[i] instanceof ParameterizedType){
                    parameterizedType = (ParameterizedType) types[i];
                    parameterizedTypeCurrentClass = (ParameterizedType) typesCurrentClass[i];

                    checkUpLow = norm(getOnlyName(parameterizedTypeCurrentClass), getOnlyName(parameterizedType));
                    if (!checkUpLow) {
                        return false;
                    }
                    good = workWithArray(parameterizedType.getActualTypeArguments(), parameterizedTypeCurrentClass.getActualTypeArguments());
                    if (!good){
                        return false;
                    }
                    continue;
                }
                checkUpLow = norm(typesCurrentClass[i].getTypeName(), lower[0].getTypeName());
                if (checkUpLow){
                    good = true;
                }
                continue;
            }

            if (upper.length != 0){
                if (types[i] instanceof ParameterizedType && typesCurrentClass[i] instanceof ParameterizedType){
                    parameterizedType = (ParameterizedType) types[i];
                    parameterizedTypeCurrentClass = (ParameterizedType) typesCurrentClass[i];
                    checkUpLow = norm(getOnlyName(parameterizedType), getOnlyName(parameterizedTypeCurrentClass));
                    if (!checkUpLow) {
                        return false;
                    }
                    good = workWithArray(parameterizedType.getActualTypeArguments(), parameterizedTypeCurrentClass.getActualTypeArguments());
                    if (!good){
                        return false;
                    }
                    continue;
                }
                checkUpLow = norm(upper[0].getTypeName(), typesCurrentClass[i].getTypeName());
            }

            if (!checkUpLow){
                return false;
            }
            good = true;
        }

        return good;
    }

    private String getOnlyName(ParameterizedType parameterizedType){
        String name = parameterizedType.getRawType().toString();
        return name.substring(name.lastIndexOf(" ") + 1);
    }

    private boolean workWithParameterizedWildcard(ParameterizedType parameterizedType, Type type, Type typeCurrentClass)
            throws ClassNotFoundException {
        System.out.println("And Tut");
        String nameCur = typeCurrentClass.toString();
        nameCur = nameCur.substring(nameCur.lastIndexOf(" ") + 1);
        return listRecursion(Class.forName(getOnlyName(parameterizedType)), type, Class.forName(nameCur), null,
                parameterizedType.getActualTypeArguments());
    }

    private boolean workWithArray(Type[] typesNew, Type[] typesNewCurrentClass) throws ClassNotFoundException {
        boolean good;
        for (int g = 0; g < typesNew.length; g++){
            good = listRecursion(null, null, null, typesNewCurrentClass[g], typesNew[g]);
            if (!good){
                return false;
            }
        }
        return true;
    }

    private void forAllChildren(){
        boolean check = false;
        for (int i = 0; i < listSubSubClass.size(); i++) {
            for (int j = 0; j < myList.size(); j++) {
                Class<?> superClass = listSubSubClass.get(i).getSuperclass();
                if (superClass != null) {
                    if (superClass.equals(myList.get(j))) {
                        myList.add(listSubSubClass.get(i));
                        listSubSubClass.remove(i);
                        check = true;
                        i--;
                        break;
                    }
                }else{
                    myList.add(listSubSubClass.get(i));
                    listSubSubClass.remove(i);
                    check = true;
                    i--;
                    break;
                }
            }
        }
        if (check){
            forAllChildren();
        }
    }

    private boolean norm(String up, String low) throws ClassNotFoundException {
        //System.out.println(low);
        //System.out.println(up);
        Class<?> classLow;
        Class<?> classUp;
        try {
            classLow = Class.forName(low);
            classUp = Class.forName(up);
        }catch (Throwable ex){
            return false;
        }
        if (classUp.equals(Object.class)){
            return true;
        }
        boolean check = false;
        while (!classLow.equals(Object.class)){
            if (classUp.equals(classLow)){
                check = true;
                break;
            }
            //System.out.println(classLow);
            //System.out.println(classUp);
            classLow = classLow.getSuperclass();
            if (classLow == null){
                break;
            }
        }
        return check;
    }

    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException {
        Inject inject = new Inject();
        //List<Class<?>> list = inject.list(inject, "acsHard");
        //list.forEach(System.out::println);

        //Type type = inject.getClass().getDeclaredField("employee").getGenericType();
        //System.out.println(type.toString());
        Field fieldClass = inject.getClass().getDeclaredField("arr");
        String name = fieldClass.toString();
        System.out.println(name);

        /*
        list.forEach(System.out::println);
        Class<?> cl = ORBVersion.class;
        System.out.println(cl.getGenericSuperclass());
        java.io.ObjectStreamField osf = new ObjectStreamField("Object", Object.class);
        inject.comparable = osf;
        MyClass mc = new MyClass();
        inject.comparable = mc;
        System.out.println(inject.list(inject, "i"));
        System.out.println(inject.list(inject, "object"));
        Class<?> clazz = Number.class;
        String name = clazz.toString().substring(7);
        System.out.println(name);
        clazz = Class.forName("java.lang.Number");
        System.out.println(clazz);
        Class<?> clazz = Comparable.class;
        List<Class<?>> list = new ArrayList<>((new Reflections()).getSubTypesOf(clazz));
        list.forEach(System.out::println);
        Field field = inject.getClass().getDeclaredField("comparable");
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Type[] types = parameterizedType.getActualTypeArguments();
        WildcardType wildcardType = (WildcardType) types[0];
        System.out.println(types[0].getTypeName());
        //System.out.println(wildcardType.getUpperBounds()[0].getTypeName());
        System.out.println(Arrays.toString(wildcardType.getUpperBounds()));
        System.out.println(Arrays.toString(wildcardType.getLowerBounds()));
        System.out.println(wildcardType.getLowerBounds()[0].getTypeName());

         */
    }
}
