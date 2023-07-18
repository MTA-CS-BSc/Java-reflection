package reflection.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Inspector implements Investigator {
    private Class<?> classToInspect;

    //region Functionality
    public Inspector() { }
    private Field[] getFields() {
        return classToInspect.getDeclaredFields();
    }

    private Method[] getMethods() {
        return classToInspect.getDeclaredMethods();
    }

    private Class<?> getParent() {
        return classToInspect.getSuperclass();
    }

    private List<String> getInheritanceChainArray(Class<?> someClass) {
        List<String> res = new ArrayList<>();

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
    }

    @Override
    public int getTotalNumberOfMethods() {
        return getMethods().length;
    }

    @Override
    public int getTotalNumberOfConstructors() {
        return classToInspect.getConstructors().length;
    }

    @Override
    public int getTotalNumberOfFields() {
        return getFields().length;
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {
        Set<String> interfaces = new HashSet<>();

        try {
            Class<?>[] implementedInteraces =  classToInspect.getInterfaces();

            for (Class<?> c : implementedInteraces)
                interfaces.add(c.getSimpleName());

            return interfaces;
        }

        catch (Error err) {
            return Collections.emptySet();
        }
    }

    @Override
    public int getCountOfConstantFields() {
        return (int) Arrays.stream(getFields())
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
        return null;
    }

    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args) {
        return 0;
    }

    @Override
    public Object createInstance(int numberOfArgs, Object... args) {
        return null;
    }

    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {
        return null;
    }

    @Override
    public String getInheritanceChain(String delimiter) {
        return String.join(delimiter, getInheritanceChainArray(classToInspect));
    }
    //#endregion
}
