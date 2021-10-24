package SmartBord.model.project;

import SmartBord.exception.SmartBordException;
import SmartBord.model.user.RegularUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    Project project;

    @BeforeEach
    void setUp() throws SmartBordException {
        project = new Project("1");
        project.addColumn("1");
        project.addColumn("2");
        project.addColumn("3");
        project.addColumn("4");
        project.addColumn("5");
        project.addColumn("6");
        project.addColumn("7");
        project.addColumn("8");
        project.addColumn("9");
        project.addColumn("10");


    }

    @Test
    void regularUserExceptionTest() throws FileNotFoundException {
        File file = new File("src/main/resources/R7VzUr.jpg");
        InputStream inputStream = new FileInputStream(file);
        assertAll(
                () -> assertThrows(SmartBordException.class,
                        () -> project = new Project(" "))
        );
    }

    @Test
    void getColumns() {
        assertEquals(10, project.getColumns().size());
    }

    @Test
    void addColumn() throws SmartBordException {
        project.addColumn("102");
        assertTrue(project.findColumn("102"));
    }

    @Test
    void deleteColumn() {

        assertTrue(project.deleteColumn("1"));
        assertEquals(9,project.getColumns().size());
    }

}