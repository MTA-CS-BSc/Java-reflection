package reflection.tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reflection.api.Inspector;
import reflection.classes.Polygon;
import reflection.classes.Rectangle;

import java.util.Set;

public class InspectorTests {
    Rectangle testRectangle;

    Polygon testPolygon;

    Inspector inspector;

    public InspectorTests() {
        testRectangle = new Rectangle(5, 7);
        testPolygon = new Polygon();
        inspector = new Inspector();
        inspector.load(testRectangle);
    }

    @Test
    @DisplayName("Test for ctors amount")
    public void testGetTotalNumberOfCtors() {
        Assertions.assertEquals(2, inspector.getTotalNumberOfConstructors());
    }

    @Test
    @DisplayName("Test for methods amount")
    public void testGetTotalNumberOfMethod() {
        Assertions.assertEquals(6, inspector.getTotalNumberOfMethods());
    }

    @Test
    @DisplayName("Test for class data members amount")
    public void testGetTotalNumberOfFields() { Assertions.assertEquals(3, inspector.getTotalNumberOfFields()); }

    private boolean validateInterfacesNames(Set<String> result) {
        return result.size() == 2 && result.contains("Comparable") && result.contains("Serializable");
    }

    @Test
    @DisplayName("Test for class interfaces simple names")
    public void testGetSimpleInterfacesNames() {
        Assertions.assertTrue(validateInterfacesNames(inspector.getAllImplementedInterfaces()));
    }

    @Test
    @DisplayName("Test for constant(=final) fields amount")
    public void testConstantsAmount() {
        Assertions.assertEquals(1, inspector.getCountOfConstantFields());
    }

    @Test
    @DisplayName("Test for static methods amount")
    public void testStaticMethodsAmount() {
        Assertions.assertEquals(1, inspector.getCountOfStaticMethods());
    }

    @Test
    @DisplayName("Test for is extending not Object")
    public void testIsExtending() {
        Assertions.assertTrue(inspector.isExtending());
    }

    @Test
    @DisplayName("Test for is abstract superclass")
    public void testIsSuperclassAbstract() {
        Assertions.assertFalse(inspector.isParentClassAbstract());
    }

    @Test
    @DisplayName("Test inheritance chain")
    public void testInheritanceChain() {
        Assertions.assertEquals("Object,Polygon,Rectangle", inspector.getInheritanceChain(","));
    }

    private boolean validateFieldNames(Set<String> fieldNames) {
        return fieldNames.contains("x") && fieldNames.contains("y")
                && fieldNames.contains("SCALE") && fieldNames.contains("points")
                && fieldNames.size() == 4;
    }
    @Test
    @DisplayName("Test for all field names including inheritance")
    public void testAllFieldNames() {
        Assertions.assertTrue(validateFieldNames(inspector.getNamesOfAllFieldsIncludingInheritanceChain()));
    }

    @Test
    @DisplayName("Test for instance creation")
    public void testInstanceCreation() {
        Object dup = inspector.createInstance(2, 5, 7);
        Assertions.assertInstanceOf(Rectangle.class, dup);
        Assertions.assertEquals(35, ((Rectangle)dup).calcArea());
    }

    @Test
    @DisplayName("Test for invoke int method")
    public void testInvokeIntMethod() {
        Assertions.assertEquals(35, inspector.invokeMethodThatReturnsInt("calcArea"));
    }
}
