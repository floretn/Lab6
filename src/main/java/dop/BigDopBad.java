package dop;

import org.reflections.Reflections;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class BigDopBad {

    private List<Class<?>> findSuperClasses(Class<?> clazz){
        List<Class<?>> list = new ArrayList<>();
        Class<?> cls = clazz.getSuperclass();
        list.add(cls);
        if (!cls.equals(Object.class)){
            list.addAll(findSuperClasses(cls));
        }
        return list;
    }


    private List<Class<?>> listRecursion(String onlyFieldType, boolean extendsNeed)
            throws ClassNotFoundException {
        List<Class<?>> list = new ArrayList<>();
        char[] chars = onlyFieldType.toCharArray();
        int index = 0;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '<') {
                index = i;
                break;
            }
        }
        String currentEl = onlyFieldType.substring(0, index);
        Class<?> clazz = Class.forName(currentEl);
        list.add(clazz);
        if (extendsNeed) {
            //List<Class<?>> subTypes = (List<Class<?>>) (new Reflections()).getSubTypesOf(clazz);
            list.addAll((new Reflections()).getSubTypesOf(clazz));
        }

        ArrayList<String> typeParameters = new ArrayList<>();
        int num = 0;
        int lastIndex = index + 1;
        for (int i = index + 1; i < chars.length; i++){
            if (chars[i] == ',') {
                if (num != 0) {
                    continue;
                } else {
                    typeParameters.add(onlyFieldType.substring(lastIndex, i));
                    lastIndex = i;
                }
            }
            if (chars[i] == '<'){
                num++;
            }
            if (chars[i] == '>'){
                num--;
            }
            if (i == (chars.length - 1)){
                typeParameters.add((onlyFieldType.substring(lastIndex, i)).trim());
            }
        }

        for (int i = 1; i < typeParameters.size(); i++){
            if (typeParameters.get(i).startsWith(", ")) {
                typeParameters.set(i, typeParameters.get(i).substring(2));
            }
        }

        for (int i = 0; i <= typeParameters.size(); i++){
            list.add(null);
        }

        for (String s : typeParameters){

            if (s.startsWith("? super ")){
                if (s.contains("<")){
                    String name = s.substring(9);
                    list.addAll(findSuperClasses(Class.forName(name)));
                    list.addAll(listRecursion(name, false));
                }else {
                    String obj = s.substring(s.lastIndexOf(" ") + 1);
                    Class<?> classObj = Class.forName(obj);
                    list.addAll(findSuperClasses(classObj));
                    list.add(classObj);
                    list.add(null);
                }
                continue;
            }

            if (s.startsWith("? extends ")){
                if (s.contains("<")){
                    list.addAll(listRecursion(s.substring(10), true));
                }else {
                    String obj = s.substring(s.lastIndexOf(" ") + 1);
                    Class<?> classObj = Class.forName(obj);
                    list.add(classObj);
                    list.addAll((new Reflections()).getSubTypesOf(classObj));
                    list.add(null);
                }
                continue;
            }

            if (s.contains("<")){
                list.addAll(listRecursion(s, false));
            }else{
                list.add(Class.forName(s));
            }
            list.add(null);
        }
        return list;
    }

    public List<Class<?>> list(Object o, String field) throws NoSuchFieldException, ClassNotFoundException {
        List<Class<?>> myList = new ArrayList<>();
        Class<?> clazz = o.getClass();
        System.out.println(clazz);
        Field fieldO = clazz.getDeclaredField(field);
        String fieldString = fieldO.toGenericString();
        String fieldWithoutName = fieldString.substring(0, fieldString.lastIndexOf(" "));
        if (!fieldWithoutName.contains("<")){
            String onlyFieldType = fieldWithoutName.substring(fieldWithoutName.lastIndexOf(" ") + 1);
            if (onlyFieldType.equals("java.lang.Object")){
                myList.add(Object.class);
                return myList;
            }
            myList.addAll((new Reflections()).getSubTypesOf(Class.forName(onlyFieldType)));
            return myList;
        }

        char[] chars = fieldString.toCharArray();
        int index = 0;
        for (int i = 0; i < chars.length; i++){
            if (chars[i] == '<'){
                index = i;
                break;
            }
        }
        String onlyFieldType = fieldWithoutName.substring(fieldWithoutName.substring(0, index).lastIndexOf(" ") + 1).trim();
        myList.addAll(listRecursion(onlyFieldType, true));
        return myList;
    }

    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException {
        //BigDop bigDop = new BigDop();
        //List<Class<?>> list = bigDop.list(new TestClass(), "fieldHard");
        //for (Class<?> clazz : list){
          //  System.out.println(clazz);
        //}


        Class<?> clazz = Class.forName("dop.NewClass");
        clazz.isAnnotation();
        Type type = null;
        TypeVariable<?> tv = null;


        System.out.println(clazz.toGenericString());
        Class<?> clazzPar = clazz.getSuperclass();
        System.out.println(clazzPar.toGenericString());
    }
}