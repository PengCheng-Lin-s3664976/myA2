package SmartBord.model.user;

import SmartBord.exception.SmartBordException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class RegularUserTest {

    private static RegularUser regularUser;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        String username = "caffrey";
        String password = "1234567890";
        String registerFirstName = "PengCheng";
        String registerLastName = "Lin";

        File file = new File("src/main/resources/R7VzUr.jpg");
        InputStream inputStream = new FileInputStream(file);
        regularUser = new RegularUser(username, password, registerFirstName, registerLastName, inputStream);
        regularUser.addProject("1");
        regularUser.addProject("2");
        regularUser.addProject("3");
        regularUser.addProject("4");
        regularUser.addProject("5");
        regularUser.addProject("6");
        regularUser.addProject("7");
        regularUser.addProject("8");
        regularUser.addProject("9");
        regularUser.addProject("10");
    }

    @Test
    void regularUserExceptionTest() throws FileNotFoundException {
        File file = new File("src/main/resources/R7VzUr.jpg");
        InputStream inputStream = new FileInputStream(file);
        assertAll(
                () -> assertThrows(SmartBordException.class,
                        () -> regularUser = new RegularUser(" ", " ", "　", "", null)),
                () -> assertThrows(SmartBordException.class,
                        () -> regularUser = new RegularUser(" ", " ", "　", " ", inputStream)),
                () -> assertThrows(SmartBordException.class,
                        () -> regularUser = new RegularUser(" ", " ", "", "1", inputStream)),
                () -> assertThrows(SmartBordException.class,
                        () -> regularUser = new RegularUser(" ", " ", "1", "1", inputStream)),
                () -> assertThrows(SmartBordException.class,
                        () -> regularUser = new RegularUser(" ", " ", "1", "1", inputStream)),
                () -> assertThrows(SmartBordException.class,
                        () -> regularUser = new RegularUser(" ", "1", "1", "1", inputStream))
        );

    }

    @Test
    void addProject() throws SmartBordException {
        assertTrue(regularUser.addProject("ok"));
        assertEquals("ok", regularUser.findProject("ok").getProjectName());

    }

    @Test
    void addTenProjects() {
        assertAll(
                () -> assertEquals("1", regularUser.findProject("1").getProjectName()),
                () -> assertEquals("2", regularUser.findProject("2").getProjectName()),
                () -> assertEquals("3", regularUser.findProject("3").getProjectName()),
                () -> assertEquals("4", regularUser.findProject("4").getProjectName()),
                () -> assertEquals("5", regularUser.findProject("5").getProjectName()),
                () -> assertEquals("6", regularUser.findProject("6").getProjectName()),
                () -> assertEquals("7", regularUser.findProject("7").getProjectName()),
                () -> assertEquals("8", regularUser.findProject("8").getProjectName()),
                () -> assertEquals("9", regularUser.findProject("9").getProjectName()),
                () -> assertEquals("10", regularUser.findProject("10").getProjectName())
        );

    }

    @Test
    void ProjectsSize() {
        assertEquals(10, regularUser.getProjects().size());
    }

    @Test
    void deleteProject() throws SmartBordException {
        regularUser.addProject("ok");
        assertTrue(regularUser.deleteProject("ok"));
    }

    @Test
    void findProject() {
    }

    @Test
    void getProjects() {
    }

    @Test
    void setProjects() {
    }
}