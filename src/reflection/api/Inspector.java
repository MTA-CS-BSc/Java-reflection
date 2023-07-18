package reflection.api;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class Inspector implements reflection.api.Investigator {
    private Class<?> classToInspect;
    private Object objectToInspect;

    //region Functionality
    public Inspector() { }

    private Field[] getClassFields(Class<?> someClass) {
        return someClass.getDeclaredFields();
    }

    private List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>();
        Class<?> someClass = classToInspect;

        while (someClass != null) {
            fields.addAll(Arrays.stream(getClassFields(someClass)).collect(Collectors.toList()));
            someClass = someClass.getSuperclass();
        }

        return fields;
    }

    private Method[] getMethods() {
        return classToInspect.getDeclaredMethods();
    }

    private Class<?> getParent() {
        return classToInspect.getSuperclass();
    }

    private Constructor<?>[] getCtors() {
        return classToInspect.getConstructors();
    }

    private List<String> getInheritanceChainArray() {
        List<String> res = new ArrayList<>();
        Class<?> someClass = classToInspect;

        while (someClass != null) {
            res.add(someClass.getSimpleName());
            someClass = someClass.getSuperclass();
        }

        Collections.reverse(res);
        return res;
    }
    //endregion

    //region interface implementations
    @Override
    public void load(Object anInstanceOfSomething) {
        classToInspect = anInstanceOfSomething.getClass();
        objectToInspect = anInstanceOfSomething;
    }

    @Override
    public int getTotalNumberOfMethods() {
        return getMethods().length;
    }

    @Override
    public int getTotalNumberOfConstructors() {
        return getCtors().length;
    }

    @Override
    public int getTotalNumberOfFields() {
        return getClassFields(classToInspect).length;
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {
        try {
            return Arrays.stream(classToInspect.getInterfaces())
                         .map(Class::getSimpleName)
                          .collect(Collectors.toSet());
        } catch (Error err) {
            return Collections.emptySet();
        }
    }

    @Override
    public int getCountOfConstantFields() {
        return (int) Arrays.stream(getClassFields(classToInspect))
                .filter(field -> Modifier.isFinal(field.getModifiers()))
                .count();
    }

    @Override
    public int getCountOfStaticMethods() {
        return (int) Arrays.stream(getMethods())
                .filter(method -> Modifier.isStatic(method.getModifiers()))
                .count();
    }

    @Override
    public String getParentClassSimpleName() {
        String parentClassName = getParent().getSimpleName();
        return parentClassName.equals("Object") ? null : parentClassName;
    }

    @Override
    public boolean isExtending() {
        return getParentClassSimpleName() != null;
    }

    @Override
    public boolean isParentClassAbstract() {
        return getParentClassSimpleName() != null &&
                Modifier.isAbstract(getParent().getModifiers());
    }

    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain() {
        return getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args) {
        try {
            Method relevantMethod = classToInspect.getDeclaredMethod(methodName);
            return (int)relevantMethod.invoke(objectToInspect, args);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public Object createInstance(int numberOfArgs, Object... args) {
        Optional<Constructor<?>> relevantCtor = Arrays.stream(getCtors())
                                      .filter(ctor -> ctor.getParameterCount() == numberOfArgs)
                                      .findFirst();

        if (relevantCtor.isPresent()) {
            try {
                return relevantCtor.get().newInstance(args);
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {
        try {
            Method relevantMethod = classToInspect.getDeclaredMethod(name, parametersTypes);
            relevantMethod.setAccessible(true);
            return relevantMethod.invoke(objectToInspect, args);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getInheritanceChain(String delimiter) {
        return String.join(delimiter, getInheritanceChainArray());
    }
    //#endregion
}
