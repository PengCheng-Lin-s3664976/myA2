package SmartBord.model.project;

import SmartBord.exception.SmartBordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    Column column;
    @BeforeEach
    void setUp() throws SmartBordException {
        column = new Column("1");
        column.addTask("1","1");
        column.addTask("2","2");
        column.addTask("3","3");
        column.addTask("4","4");
        column.addTask("5","5");
        column.addTask("6","6");
        column.addTask("7","7");
        column.addTask("8","8");
        column.addTask("9","9");
        column.addTask("10","10");
    }

    @Test
    void TaskExceptionTest() {
        assertThrows(SmartBordException.class,
                () -> column = new Column(" "));
    }

    @Test
    void getTasks() {
        assertEquals(10, column.getTasks().size());
    }

    @Test
    void addTask() throws SmartBordException {
        assertTrue(column.addTask("102","23"));
    }

    @Test
    void dragTask() throws SmartBordException {
        assertTrue(column.DragTask("100","23",0));
    }

    @Test
    void notFindTask() {
        assertTrue(column.notFindTask("100"));
    }

    @Test
    void deleteTask() {
        assertTrue(column.deleteTask("1"));
    }

    @Test
    void updateTask() throws SmartBordException {
        assertTrue(column.updateTask("1","23","213"));
    }
}