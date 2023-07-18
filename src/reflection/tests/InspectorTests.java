package reflection.tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reflection.api.Inspector;
import reflection.classes.Rectangle;

import java.util.Set;

public class InspectorTests {
    Rectangle testRectangle;
    Inspector inspector;

    public InspectorTests() {
        testRectangle = new Rectangle(1, 1);
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
}
