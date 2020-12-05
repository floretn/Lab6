package bigDop;

import org.reflections.Reflections;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class InjectHelpWithHardField {

    private List<Class<?>> listSubSubClass = new ArrayList<>();

    List<Class<?>> listWork(Field field, Class<?> clazz, Type clazzType, Type[] types) throws ClassNotFoundException {
        ParameterizedType pt;
        Type fieldTypeHowTypeParameter = field.getGenericType();
        Type fieldType;
        InjectHelpWithSimpleField withSimpleField = new InjectHelpWithSimpleField();
        try{
            pt = (ParameterizedType) fieldTypeHowTypeParameter;
        }catch (ClassCastException ex){
            if (fieldTypeHowTypeParameter instanceof TypeVariable){
                fieldType = findTypeOfField(clazz, fieldTypeHowTypeParameter, types);
                return withSimpleField.listWork(null, Class.forName(fieldType.getTypeName()));
            }

            if (fieldTypeHowTypeParameter instanceof GenericArrayType){
                GenericArrayType gat = (GenericArrayType) fieldTypeHowTypeParameter;
                fieldType = findTypeOfField(clazz, gat.getGenericComponentType(), types);
                return withSimpleField.listWork(null, Array.newInstance(Class.forName(fieldType.getTypeName()), 0).getClass());
            }
            return null;
        }

        Type[] typesOfPT = pt.getActualTypeArguments();
        Class<?> fieldOClazz = field.getType();
        List<Class<?>> listSubClass = new ArrayList<>((new Reflections()).getSubTypesOf(fieldOClazz));
        List<Class<?>> myList = new ArrayList<>();
        for (Class<?> subClass : listSubClass){
            if (listRecursion(clazz, withSimpleField, fieldOClazz, field.getType(), subClass,  typesOfPT, null, types)){
                myList.add(subClass);
            }
        }
        withSimpleField.forAllChildren(listSubSubClass, myList);
        return myList;
    }

    private boolean listRecursion(Class<?> genClass, InjectHelpWithSimpleField withSimpleField,
                                  Class<?> classO, Type typeO, Class<?> clazz, Type[] typesOfPT,
                                 Type[] typesRec, Type... types) throws ClassNotFoundException {

        Type[] typesOfSubClass;
        if (clazz != null) {
            if (clazz.getTypeParameters().length != 0) {
                return false;
            }

            ParameterizedType ptCurrentClass;
            if (classO.isInterface() && !clazz.isInterface()) {
                Type[] interfaces = clazz.getGenericInterfaces();

                Type interfaceOf = null;
                String fieldONameInterface;
                if (typeO instanceof ParameterizedType) {
                    fieldONameInterface = withSimpleField.getOnlyName((ParameterizedType) typeO);
                }else{
                    fieldONameInterface = typeO.getTypeName();
                }
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
            if (ptCurrentClass == null){
                return false;
            }
            typesOfSubClass = ptCurrentClass.getActualTypeArguments();
        }else {
            typesOfSubClass = typesRec;
        }
        for (int i = 0; i < typesOfPT.length; i++) {
            if (!(typesOfPT[i] instanceof WildcardType || typesOfPT[i] instanceof ParameterizedType ||
                    typesOfPT[i] instanceof TypeVariable)) {
                if (!withSimpleField.listRecursion(null, null, null, typesOfSubClass[i], typesOfPT[i])) {
                    return false;
                }
            }

            if (typesOfPT[i] instanceof TypeVariable){
                if (!withSimpleField.listRecursion(null, null, null, typesOfSubClass[i],
                        findTypeOfField(genClass, typesOfPT[i], types))) {
                    return false;
                }
            }

            ParameterizedType parameterizedType;
            ParameterizedType parameterizedTypeCurrentClass;

            if (typesOfPT[i] instanceof ParameterizedType && typesOfSubClass[i] instanceof ParameterizedType){
                parameterizedType = (ParameterizedType) typesOfPT[i];
                parameterizedTypeCurrentClass = (ParameterizedType) typesOfSubClass[i];
                if (!parameterizedType.getRawType().equals(parameterizedTypeCurrentClass.getRawType())){
                    return false;
                }

                if (!listRecursion(genClass, withSimpleField, null, null, null,
                        parameterizedType.getActualTypeArguments(),
                        parameterizedTypeCurrentClass.getActualTypeArguments(), types)){
                    return false;
                }
                continue;
            }

            if (typesOfPT[i] instanceof WildcardType){
                WildcardType wt = (WildcardType) typesOfPT[i];
                Type[] lower = wt.getLowerBounds();
                Type[] upper = wt.getUpperBounds();

                if(!(typesOfSubClass[i] instanceof ParameterizedType)) {
                    if (lower.length != 0 && lower[0] instanceof ParameterizedType) {
                        parameterizedType = (ParameterizedType) lower[0];
                        if (!norm1(genClass, withSimpleField, typesOfSubClass[i],
                                Class.forName(parameterizedType.getRawType().getTypeName()), parameterizedType, types)) {
                            return false;
                        }
                        continue;
                    }

                    if (upper[0] instanceof ParameterizedType) {
                        parameterizedType = (ParameterizedType) upper[0];
                        if (!norm1(genClass, withSimpleField, parameterizedType, Class.forName(typesOfSubClass[i].getTypeName()),
                                typesOfSubClass[i], types)) {
                            return false;
                        }
                        continue;
                    }
                }

                if (lower.length != 0){
                    if (typesOfSubClass[i] instanceof ParameterizedType && lower[0] instanceof ParameterizedType){
                        parameterizedType = (ParameterizedType) lower[0];
                        parameterizedTypeCurrentClass = (ParameterizedType) typesOfSubClass[i];
                        if (!withSimpleField.norm(withSimpleField.getOnlyName(parameterizedTypeCurrentClass),
                                withSimpleField.getOnlyName(parameterizedType))){
                            return false;
                        }

                        if (!listRecursion(genClass, withSimpleField, null, null, null,
                                parameterizedType.getActualTypeArguments(),
                                parameterizedTypeCurrentClass.getActualTypeArguments(), types)){
                            return false;
                        }
                        continue;
                    }
                    if (!withSimpleField.norm(typesOfSubClass[i].getTypeName(),
                            findTypeOfField(genClass, lower[0], types).getTypeName())){
                        return false;
                    }
                    continue;
                }

                if (upper.length != 0){
                    if (typesOfSubClass[i] instanceof ParameterizedType && upper[0] instanceof ParameterizedType){
                        parameterizedType = (ParameterizedType) upper[0];
                        parameterizedTypeCurrentClass = (ParameterizedType) typesOfSubClass[i];
                        if (!withSimpleField.norm(withSimpleField.getOnlyName(parameterizedType),
                                withSimpleField.getOnlyName(parameterizedTypeCurrentClass))){
                            return false;
                        }

                        if (!listRecursion(genClass, withSimpleField, null, null, null, parameterizedType.getActualTypeArguments(),
                                parameterizedTypeCurrentClass.getActualTypeArguments(), types)){
                            return false;
                        }
                        continue;
                    }

                    if (!withSimpleField.norm(findTypeOfField(genClass, upper[0], types).getTypeName(),
                            typesOfSubClass[i].getTypeName())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Type findTypeOfField(Class<?> clazz,  Type fieldTypeHowTypeParameter, Type... types){
        Type[] typesOfClazz = clazz.getTypeParameters();
        Type fieldType = null;
        for (int i = 0; i < typesOfClazz.length; i++){
            if (fieldTypeHowTypeParameter.equals(typesOfClazz[i])){
                fieldType = types[i];
                break;
            }
        }
        return fieldType;
    }

    private boolean norm1(Class<?> genClass, InjectHelpWithSimpleField withSimpleField, Type type, Class<?> clazz,
                          Type typeParent, Type... types) throws ClassNotFoundException {
        if (type instanceof ParameterizedType) {
            if (clazz.equals(Object.class)){
                return true;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] interfaces = clazz.getGenericInterfaces();
            while (!clazz.equals(Object.class)) {
                if (typeParent != null) {
                    if (typeParent instanceof ParameterizedType &&
                            ((ParameterizedType) typeParent).getRawType().equals(((ParameterizedType) type).getRawType())) {
                        if (listRecursion(genClass, withSimpleField, null, null, null, parameterizedType.getActualTypeArguments(),
                                ((ParameterizedType) typeParent).getActualTypeArguments(), types)){
                            return true;
                        }
                    }
                }
                if (interfaces != null) {
                    for (Type t : interfaces) {
                        if (t instanceof ParameterizedType &&
                                ((ParameterizedType) t).getRawType().equals(((ParameterizedType) type).getRawType())) {
                            if (listRecursion(genClass, withSimpleField, null, null, null, parameterizedType.getActualTypeArguments(),
                                    ((ParameterizedType) t).getActualTypeArguments(), types)){
                                return true;
                            }
                        }
                    }
                }
                clazz = clazz.getSuperclass();
                if (clazz != null) {
                    typeParent = clazz.getGenericSuperclass();
                    interfaces = clazz.getGenericInterfaces();
                }else{
                    break;
                }
            }
        }else{
            return withSimpleField.norm1(type, clazz);
        }
        return false;
    }
}
