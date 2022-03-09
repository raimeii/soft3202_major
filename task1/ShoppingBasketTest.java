package au.edu.sydney.soft3202.task1;

import javafx.util.Pair;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**possible undefined behaviour
- get item list of empty cart
- clear empty cart behaviour
- if pair list is initialized or not at shoppingbasket creation

confirmed undefined behaviour
- items being deleted from pair list when number = 0
- items being deleted by clear doing the same thing as above
**/
public class ShoppingBasketTest {
    private ShoppingBasket sb;

    @BeforeEach
    public void setupBasket() {
        sb = new ShoppingBasket();
    }

    @AfterEach
    public void teardownBasket() {
        sb = null;
    }

    //test addItem
    @Test
    public void normalAdd() {
        sb.addItem("apple", 1);
        assertEquals(2.50, sb.getValue());
    }

    @Test 
    public void nullStringAdd() {
        assertThrows(IllegalArgumentException.class, ()-> {
            sb.addItem(null, 1);
        });
    }

    @Test 
    public void zeroAdd() {
        assertThrows(IllegalArgumentException.class, ()-> {
            sb.addItem("pear", 0);
        });
    }

    @Test 
    public void negativeAdd() {
        assertThrows(IllegalArgumentException.class, ()-> {
            sb.addItem("banana", -1);
        });
    }

    @Test
    public void uppercaseAdd() {
        sb.addItem("BANANA", 1);
        assertEquals(4.95, sb.getValue());
    }

    @Test
    public void mixcaseAdd() {
        sb.addItem("Apple", 1);
        assertEquals(2.50, sb.getValue());
    }

    //test removeItem
    @Test
    public void removeNothing() {
        assertFalse(sb.removeItem("apple", 1));
    }
    
    @Test
    public void removeOverContents() {
        sb.addItem("orange", 5);
        assertFalse(sb.removeItem("orange", 20));
    }

    @Test
    public void normalRemove() {
        sb.addItem("pear", 5);
        assertTrue(sb.removeItem("pear", 1));
        assertEquals(12.0, sb.getValue());
    }

    @Test 
    public void nullStringRemove() {
        sb.addItem("orange", 5);
        assertThrows(IllegalArgumentException.class, () -> {
            sb.removeItem(null, 1);
        });
    }

    @Test 
    public void zeroRemove() {
        sb.addItem("orange", 5);
        assertThrows(IllegalArgumentException.class, () -> {
            sb.removeItem("orange", 2.75);
        });
    }

    @Test 
    public void negativeRemove() {
        sb.addItem("orange", 5);
        assertThrows(IllegalArgumentException.class, () -> {
            sb.removeItem("orange", -1);
        });
    }

    @Test
    public void uppercaseRemove() {
        sb.addItem("orange", 1);
        assertTrue(sb.removeItem("ORANGE", 1));
        assertNull(sb.getValue());
    }

    @Test
    public void mixcaseRemove() {
        sb.addItem("orange", 1);
        sb.addItem("apple", 1);
        assertTrue(sb.removeItem("oRaNGe", 1));
        assertEquals(2.50, sb.getValue());
    }

    @Test
    public void wrongSpelling() {
        sb.addItem("apple", 2);
        assertFalse(sb.removeItem("apples", 1));
    }

    //test getItems
    @Test
    public void normalGetItems() {
        sb.addItem("apple", 5);
        sb.addItem("orange", 10);
        assertNotNull(sb.getItems());
        assertEquals(2, sb.getItems().size());
    }

    //test getValue 
    @Test
    public void normalGetValue() {
        sb.addItem("banana", 5);
        sb.addItem("orange", 2);
        assertEquals(27.25, sb.getValue());
    }

    @Test
    public void emptyGetValue() {
        assertNull(sb.getValue());
    }

    //test clear
    @Test
    public void clearNormal() {
        sb.addItem("apple", 5);
        sb.addItem("pear", 10);
        sb.clear();
        assertNull(sb.getValue());
    }

    @Test
    public void clearAndAdd() {
        sb.addItem("apple", 10);
        sb.clear();
        sb.addItem("pear", 10);
        assertEquals(30.0, sb.getValue());
    }


}