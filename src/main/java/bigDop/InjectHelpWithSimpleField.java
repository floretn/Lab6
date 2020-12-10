package bigDop;

import org.reflections.Reflections;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class InjectHelpWithSimpleField {

    private List<Class<?>> listSubSubClass = new ArrayList<>();

    List<Class<?>> listWork(Field fieldO, Class<?> fieldOClazz) throws ClassNotFoundException {
        ParameterizedType parameterizedType;
        List<Class<?>> myList = new ArrayList<>();
        if (fieldO == null || !(fieldO.getGenericType() instanceof ParameterizedType) ){
            if (fieldOClazz.isArray()){
                String name = fieldOClazz.toString().substring(8);
                myList.add(fieldOClazz);
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
            if (fieldOClazz.equals(Object.class)){
                myList.add(Object.class);
                return myList;
            }
            myList.add(fieldOClazz);
            if (!fieldOClazz.isPrimitive()) {
                myList.addAll((new Reflections()).getSubTypesOf(fieldOClazz));
            }
            return myList;
        }

        parameterizedType = (ParameterizedType) fieldO.getGenericType();
        Type[] types = parameterizedType.getActualTypeArguments();
        List<Class<?>> listSubClass = new ArrayList<>((new Reflections()).getSubTypesOf(fieldOClazz));
        for (Class<?> subClass : listSubClass){
            if (listRecursion(fieldOClazz, fieldO.getGenericType(), subClass, null, types)){
                myList.add(subClass);
            }
        }
        forAllChildren(listSubSubClass, myList);
        return myList;
    }

    boolean listRecursion(Class<?> fieldClass, Type fieldType, Class<?> clazz, Type clazzType, Type... types)
            throws ClassNotFoundException {

        Type[] typesCurrentClass;

        if (clazz != null) {

            if (clazz.getTypeParameters().length != 0) {
                return false;
            }

            ParameterizedType ptCurrentClass;
            if (fieldClass.isInterface() && !clazz.isInterface()) {
                Type[] interfaces = clazz.getGenericInterfaces();

                Type interfaceOf = null;
                String fieldONameInterface;
                if (fieldType instanceof ParameterizedType) {
                    fieldONameInterface = getOnlyName((ParameterizedType) fieldType);
                }else{
                    fieldONameInterface = fieldType.getTypeName();
                }
                //fieldONameInterface = fieldONameInterface.substring(0, fieldONameInterface.lastIndexOf("<"));
                for (Type type : interfaces) {
                    if (type.getTypeName().contains(fieldONameInterface)) {
                        interfaceOf = type;
                    }
                }
                if (!(interfaceOf == null)) {
                    if (interfaceOf instanceof ParameterizedType) {
                        ptCurrentClass = (ParameterizedType) interfaceOf;
                    } else {
                        listSubSubClass.add(clazz);
                        return false;
                    }
                } else {
                    Type superTypeForClazz = clazz.getGenericSuperclass();
                    if (superTypeForClazz instanceof ParameterizedType) {
                        ptCurrentClass = (ParameterizedType) superTypeForClazz;
                    } else {
                        listSubSubClass.add(clazz);
                        return false;
                    }
                }
            } else {
                Type superTypeForClazz = clazz.getGenericSuperclass();
                if (superTypeForClazz instanceof ParameterizedType) {
                    ptCurrentClass = (ParameterizedType) superTypeForClazz;
                } else {
                    listSubSubClass.add(clazz);
                    return false;
                }
            }
            typesCurrentClass = ptCurrentClass.getActualTypeArguments();
        }else{
            typesCurrentClass = new Type[1];
            typesCurrentClass[0] = clazzType;
        }


        for (int i = 0; i < types.length; i++){

            if (types[i].equals(typesCurrentClass[i])){
                continue;
            }

            if (types[i] instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) types[i];
                ParameterizedType parameterizedTypeCurrentClass;
                if (typesCurrentClass[i] instanceof ParameterizedType) {
                    parameterizedTypeCurrentClass = (ParameterizedType) typesCurrentClass[i];
                }else{
                    return false;
                }
                if (parameterizedType.getRawType().equals(parameterizedTypeCurrentClass.getRawType())){
                    if (!workWithArray(parameterizedType.getActualTypeArguments(), parameterizedTypeCurrentClass.getActualTypeArguments())){
                        return false;
                    }
                    continue;
                }else{
                    return  false;
                }
            }

            WildcardType wt;
            Type[] lower;
            Type[] upper;
            if (types[i] instanceof WildcardType){
                wt = (WildcardType) types[i];
                lower = wt.getLowerBounds();
                upper = wt.getUpperBounds();
            }else{
                return false;
            }

            ParameterizedType parameterizedType;
            ParameterizedType parameterizedTypeCurrentClass;

            if(!(typesCurrentClass[i] instanceof ParameterizedType)) {
                if (lower.length != 0 && lower[0] instanceof ParameterizedType) {
                    parameterizedType = (ParameterizedType) lower[0];
                    if (!norm1(typesCurrentClass[i], Class.forName(parameterizedType.getRawType().getTypeName()))) {
                        return false;
                    }
                    continue;
                }

                if (upper[0] instanceof ParameterizedType) {
                    parameterizedType = (ParameterizedType) upper[0];
                    if (!norm1(parameterizedType, Class.forName(typesCurrentClass[i].getTypeName()))) {
                        return false;
                    }
                    continue;
                }
            }

            if (lower.length != 0){
                if (lower[0] instanceof ParameterizedType && typesCurrentClass[i] instanceof ParameterizedType){
                    parameterizedType = (ParameterizedType) lower[0];
                    parameterizedTypeCurrentClass = (ParameterizedType) typesCurrentClass[i];

                    if (!norm(getOnlyName(parameterizedTypeCurrentClass), getOnlyName(parameterizedType))) {
                        return false;
                    }
                    if (!workWithArray(parameterizedType.getActualTypeArguments(), parameterizedTypeCurrentClass.getActualTypeArguments())){
                        return false;
                    }
                    continue;
                }
                if (!norm(typesCurrentClass[i].getTypeName(), lower[0].getTypeName())){
                    return false;
                }
                continue;
            }

            if (upper.length != 0){
                if (upper[0] instanceof ParameterizedType && typesCurrentClass[i] instanceof ParameterizedType){
                    parameterizedType = (ParameterizedType) upper[0];
                    parameterizedTypeCurrentClass = (ParameterizedType) typesCurrentClass[i];
                    if (!norm(getOnlyName(parameterizedType), getOnlyName(parameterizedTypeCurrentClass))) {
                        return false;
                    }
                    if (!workWithArray(parameterizedType.getActualTypeArguments(), parameterizedTypeCurrentClass.getActualTypeArguments())){
                        return false;
                    }
                    continue;
                }
                if (!norm(upper[0].getTypeName(), typesCurrentClass[i].getTypeName())){
                    return false;
                }
            }

        }

        return true;
    }

    String getOnlyName(ParameterizedType parameterizedType){
        String name = parameterizedType.getRawType().toString();
        return name.substring(name.lastIndexOf(" ") + 1);
    }

    boolean workWithArray(Type[] typesNew, Type[] typesNewCurrentClass) throws ClassNotFoundException {
        for (int g = 0; g < typesNew.length; g++){
            if (!listRecursion(null, null, null, typesNewCurrentClass[g], typesNew[g])){
                return false;
            }
        }
        return true;
    }

    void forAllChildren(List<Class<?>> listSubSubClass, List<Class<?>> myList){
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
                }
            }
        }
        if (check){
            forAllChildren(listSubSubClass, myList);
        }
    }

    boolean norm(String up, String low) throws ClassNotFoundException {
        Class<?> classLow;
        Class<?> classUp;
        if (up.contains("<") || low.contains("<")){
            return false;
        }
        classLow = Class.forName(low);
        classUp = Class.forName(up);
        if (classUp.equals(Object.class)){
            return true;
        }
        while (!classLow.equals(Object.class)){
            if (classUp.equals(classLow)){
                return true;
            }
            if (classLow.getGenericInterfaces().length != 0){
                for (Class<?> clazz : classLow.getInterfaces()){
                    if (clazz.equals(classUp)){
                        return true;
                    }
                }
            }
            classLow = classLow.getSuperclass();
            if (classLow == null){
                break;
            }
        }
        return false;
    }

    boolean norm1(Type type, Class<?> clazz){
        while (!clazz.equals(Object.class)){
            if (clazz.getGenericSuperclass() != null && clazz.getGenericSuperclass().equals(type)){
                return true;
            }

            if (clazz.getGenericInterfaces().length != 0) {
                for (Type tp : clazz.getGenericInterfaces()) {
                    if (tp.equals(type)) {
                        return true;
                    }
                }
            }

            clazz = clazz.getSuperclass();
            if (clazz == null){
                return false;
            }
        }
        return false;
    }
}