package reflection.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Inspector implements Investigator {
    static final int ERROR_INDICATOR = -1;
    private Class<?> classToInspect;

    public Inspector() { }

    @Override
    public void load(Object anInstanceOfSomething) {
        classToInspect = anInstanceOfSomething.getClass();
    }

    @Override
    public int getTotalNumberOfMethods() {
        try {
            return classToInspect.getDeclaredMethods().length;
        }

        catch (Error err) {
            return ERROR_INDICATOR;
        }
    }

    @Override
    public int getTotalNumberOfConstructors() {
        try {
            return classToInspect.getConstructors().length;
        }

        catch (Error err) {
            return ERROR_INDICATOR;
        }
    }

    @Override
    public int getTotalNumberOfFields() {
        try {
            return classToInspect.getDeclaredFields().length;
        }

        catch (Error err) {
            return ERROR_INDICATOR;
        }
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {
        Set<String> interfaces = new HashSet<>();

        try {
            Class<?>[] implementedInteraces =  classToInspect.getInterfaces();

            for (Class<?> c : implementedInteraces) {
                interfaces.add(c.getSimpleName());
            }

            return interfaces;
        }

        catch (Error err) {
            return Collections.emptySet();
        }
    }

    @Override
    public int getCountOfConstantFields() {
        return 0;
    }

    @Override
    public int getCountOfStaticMethods() {
        return 0;
    }

    @Override
    public boolean isExtending() {
        return false;
    }

    @Override
    public String getParentClassSimpleName() {
        return null;
    }

    @Override
    public boolean isParentClassAbstract() {
        return false;
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
        return null;
    }
}
