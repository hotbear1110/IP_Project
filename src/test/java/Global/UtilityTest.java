package Global;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilityTest {

    @Test
    public void addProcentToNumber() {
        assertEquals(330, Utility.addProcentToNumber(300, 10));
        assertEquals(440, Utility.addProcentToNumber(400, 10));
        assertEquals(240, Utility.addProcentToNumber(200,20));
        assertEquals(1000, Utility.addProcentToNumber(500, 100));
    }

    @Test
    public void roundUpToHundred() {
        assertEquals(300, Utility.roundUpToHundred(280));
        assertEquals(400, Utility.roundUpToHundred(305));
        assertEquals(100, Utility.roundUpToHundred(50));
        assertEquals(200, Utility.roundUpToHundred(145));
    }
}