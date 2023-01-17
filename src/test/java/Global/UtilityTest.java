package Global;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilityTest {

    @Test
    public void addProcentToNumberTest() {
        assertEquals(330, Utility.addProcentToNumber(300, 10));
        assertEquals(440, Utility.addProcentToNumber(400, 10));
        assertEquals(240, Utility.addProcentToNumber(200,20));
        assertEquals(1000, Utility.addProcentToNumber(500, 100));
    }

    @Test
    public void removeProcentFromNumberTest(){
        assertEquals(27000, Utility.removeProcentFromNumber(30000, 10));
    }
    @Test
    public void roundUpToHundredTest() {
        assertEquals(300, Utility.roundUpToHundred(280));
        assertEquals(400, Utility.roundUpToHundred(305));
        assertEquals(100, Utility.roundUpToHundred(50));
        assertEquals(200, Utility.roundUpToHundred(145));
        assertEquals(32000, Utility.roundUpToHundred(32000));
    }

    @Test
    public void parseTwoIntsTest(){
        assertArrayEquals(new int[]{2, 2}, Utility.parseTwoIntsToArray("2 & 2"));
        assertArrayEquals(new int[]{3, 5}, Utility.parseTwoIntsToArray("3 & 5"));
        assertArrayEquals(new int[]{6, 6}, Utility.parseTwoIntsToArray("6 & 6"));
        assertArrayEquals(new int[]{1, 2}, Utility.parseTwoIntsToArray("1 & 2"));
        assertArrayEquals(new int[]{5, 5}, Utility.parseTwoIntsToArray("5 & 5"));
    }
}