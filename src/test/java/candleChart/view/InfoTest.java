package candleChart.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InfoTest {
    private Info infoPanel;

    private String info1;
    private String info2;
    private String info3;

    @BeforeEach
    public void setUp() {
        infoPanel = new Info();

        info1 = "información1";
        info2 = "información2";
        info3 = "información3";
    }


    @Test
    public void testAddInfo_StoresInformationCorrectly() {
        infoPanel.addInfo(info1);

        assertEquals(info1, infoPanel.getInfo(0));
    }

    @Test
    public void testAddInfo_ThrowsExceptionForNullInput() {
        IllegalArgumentException exception;
        exception = assertThrows(IllegalArgumentException.class, () -> {
            infoPanel.addInfo(null);
        });
        assertEquals("El parámetro 'info' proporcionado es nulo", exception.getMessage());
    }

    @Test
    public void testGetInfo_RetrievesAddedInformation() {
        infoPanel.addInfo(info1);
        infoPanel.addInfo(info2);
        infoPanel.addInfo(info3);

        assertEquals(info1, infoPanel.getInfo(0));
        assertEquals(info2, infoPanel.getInfo(1));
        assertEquals(info3, infoPanel.getInfo(2));
    }

    @Test
    public void testGetInfo_ThrowsIndexOutOfBoundsForInvalidIndex() {
        infoPanel.addInfo(info1);

        IndexOutOfBoundsException exception1 = assertThrows(IndexOutOfBoundsException.class,
                () -> infoPanel.getInfo(1));
        assertEquals("Valor fuera de rango", exception1.getMessage());

        IndexOutOfBoundsException exception2 = assertThrows(IndexOutOfBoundsException.class,
                () -> infoPanel.getInfo(-1));
        assertEquals("Valor fuera de rango", exception2.getMessage());
    }

    @Test
    public void testUpdateInfo_UpdatesExistingInformation() {
        String infoUpdate1 = "Información1 actualizada";
        String infoUpdate2 = "Información2 actualizada";

        infoPanel.addInfo(info1);
        infoPanel.addInfo(info2);
        infoPanel.updateInfo(0, infoUpdate1);
        infoPanel.updateInfo(1, infoUpdate2);

        assertEquals(infoUpdate1, infoPanel.getInfo(0));
        assertEquals(infoUpdate2, infoPanel.getInfo(1));
    }

    @Test
    public void testUpdateInfo_ThrowsExceptionForNullInput() {
        infoPanel.addInfo(info1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> infoPanel.updateInfo(0, null));
        assertEquals("El parámetro 'info' proporcionado es nulo", exception.getMessage());
    }

    @Test
    public void testUpdateInfo_ThrowsIndexOutOfBoundsForInvalidIndex() {
        infoPanel.addInfo(info1);

        IndexOutOfBoundsException exception1 = assertThrows(IndexOutOfBoundsException.class,
                () -> infoPanel.updateInfo(1, info2));
        assertEquals("Valor fuera de rango", exception1.getMessage());

        IndexOutOfBoundsException exception2 = assertThrows(IndexOutOfBoundsException.class,
                () -> infoPanel.updateInfo(-1, info2));
        assertEquals("Valor fuera de rango", exception2.getMessage());
    }

    @Test
    public void testDeleteInfo_RemovesElementAtIndex() {
        infoPanel.addInfo(info1);
        infoPanel.addInfo(info2);

        infoPanel.deleteInfo(0);

        assertEquals(1, infoPanel.getComponentCount());
        assertEquals(info2, infoPanel.getInfo(0));
    }

    @Test
    public void testDeleteInfo_ThrowsIndexOutOfBoundsForInvalidIndex() {
        infoPanel.addInfo(info1);

        IndexOutOfBoundsException exception1 = assertThrows(IndexOutOfBoundsException.class,
                () -> infoPanel.deleteInfo(1));
        assertEquals("Valor fuera de rango", exception1.getMessage());

        IndexOutOfBoundsException exception2 = assertThrows(IndexOutOfBoundsException.class,
                () -> infoPanel.deleteInfo(-1));
        assertEquals("Valor fuera de rango", exception2.getMessage());
    }

    @Test
    public void testSetVisibilityInfo_ValidIndex() {
        infoPanel.addInfo(info1);
        infoPanel.addInfo(info2);
        
        infoPanel.setVisibilityInfo(0, true);
        assertTrue(infoPanel.getComponent(0).isVisible());

        infoPanel.setVisibilityInfo(1, false);
        assertFalse(infoPanel.getComponent(1).isVisible());
    }

    @Test
    public void testSetVisibilityInfo_ThrowsIndexOutOfBoundsForInvalidIndex() {
        infoPanel.addInfo(info1);

        IndexOutOfBoundsException exception1 = assertThrows(IndexOutOfBoundsException.class,
                () -> infoPanel.setVisibilityInfo(1, true));
        assertEquals("Valor fuera de rango", exception1.getMessage());

        IndexOutOfBoundsException exception2 = assertThrows(IndexOutOfBoundsException.class,
                () -> infoPanel.setVisibilityInfo(-1, false));
        assertEquals("Valor fuera de rango", exception2.getMessage());
    }

    @Test
    public void testIsVisibleInfo_ValidIndex() {
        infoPanel.addInfo(info1);
        infoPanel.addInfo(info2);
        infoPanel.setVisibilityInfo(0, true);
        infoPanel.setVisibilityInfo(1, false);

        assertTrue(infoPanel.isVisibleInfo(0));
        assertFalse(infoPanel.isVisibleInfo(1));
    }

    @Test
    public void testIsVisibleInfo_ThrowsIndexOutOfBoundsForInvalidIndex() {
        infoPanel.addInfo(info1);

        IndexOutOfBoundsException exception1 = assertThrows(IndexOutOfBoundsException.class,
                () -> infoPanel.isVisibleInfo(1));
        assertEquals("Valor fuera de rango", exception1.getMessage());

        IndexOutOfBoundsException exception2 = assertThrows(IndexOutOfBoundsException.class,
                () -> infoPanel.isVisibleInfo(-1));
        assertEquals("Valor fuera de rango", exception2.getMessage());
    }

    @Test
    public void testCleanInfo() {
        infoPanel.addInfo(info1);
        infoPanel.addInfo(info2);
        infoPanel.addInfo(info3);

        assertEquals(3, infoPanel.getComponentCount());

        infoPanel.cleanInfo();
        assertEquals(0, infoPanel.getComponentCount());
    }
}
