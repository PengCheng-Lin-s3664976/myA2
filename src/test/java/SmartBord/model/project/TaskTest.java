package SmartBord.model.project;

import SmartBord.exception.SmartBordException;
import SmartBord.model.user.RegularUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task;
    @BeforeEach
    void setUp() throws SmartBordException {

    }
    @Test
    void TaskExceptionTest() {
        assertAll(
                () -> assertThrows(SmartBordException.class,
                        () -> task = new Task(" ", " ")),
                () -> assertThrows(SmartBordException.class,
                        () -> task = new Task(" ", "1"))
        );
    }
}