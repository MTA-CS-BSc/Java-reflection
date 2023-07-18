package reflection.tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reflection.api.Inspector;
import reflection.classes.Rectangle;

public class InspectorTests {

    @Test
    public void testGetTotalNumberOfCtors() {
        Rectangle testRectangle = new Rectangle(1, 1);
        Inspector inspector = new Inspector();

        inspector.load(testRectangle);
        Assertions.assertEquals(2, inspector.getTotalNumberOfConstructors());
    }

}
