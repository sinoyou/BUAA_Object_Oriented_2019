import static org.junit.Assert.assertTrue;

public class PolyTest {

    @org.junit.Test
    public void formatCheck() {
        Poly poly = new Poly("x");
        assertTrue(poly.formatCheck());
    }
}