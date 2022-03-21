package au.edu.sydney.soft3202.reynholm.erp.billingsystem;
import au.edu.sydney.soft3202.reynholm.erp.project.Project;
import au.edu.sydney.soft3202.reynholm.erp.cheatmodule.ERPCheatFactory;
import au.edu.sydney.soft3202.reynholm.erp.compliance.ComplianceReporting;
import au.edu.sydney.soft3202.reynholm.erp.client.ClientReporting;

// import client.ClientReporting;
// import compliance.ComplianceReporting;
// import project.Project;
// import billingsystem.BSFacadeImpl;
// import cheatmodule.ERPCheatFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.IllegalStateException;
import java.lang.IllegalArgumentException;
import java.util.List;

public class BSFacadeImplTest {

    private BSFacadeImpl fixture;

    @BeforeEach
    public void setupFixture() {
        fixture = new BSFacadeImpl();
    }

    @AfterEach
    public void teardownFixture() {
        fixture = null;
    }

    //addProject tests
    @Test
    public void addProjectNoInjections() {
        assertThrows(IllegalStateException.class, ()-> fixture.addProject("Building A Renovations", "Mr. X", 40.0, 50));
    }

    @Test
    public void addProjectNoLogin() {
        //given
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        //when, then
        assertThrows(IllegalStateException.class, () -> fixture.addProject("Some name", "some client", 1.0, 40));
    }

    @Test
    public void nullName() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject(null, "some client", 1.0, 40));
    }

    @Test
    public void nullClient() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", null, 1.0, 40));
    }

    @Test
    public void nullStrings() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject(null, null, 1.0, 40));
    }

    @Test
    public void emptyName() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("", "some client", 1.0, 40));
    }

    @Test
    public void emptyClient() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "", 1.0, 40));
    }

    @Test
    public void emptyStrings() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("", "", 1.0, 40));
    }

    @Test
    public void standardRateBoundaryMin() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", 0.01, 40));
    }

    @Test
    public void standardRateBoundaryMax() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", 100.0, 40));
    }

    @Test
    public void standardRatePastMin() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", -5.0, 40));
    }

    @Test
    public void standardRatePastMax() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", 500.0, 40));
    }

    //will fail bc not 10% more than standard rate
    @Test
    public void overRateBoundaryMin() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", 0.1, 0.1));
    }

    @Test
    public void overRateBoundaryMax() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", 50.0, 100.0));
    }

    @Test
    public void overRatePastMin() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", 50, -5.0));
    }

    @Test
    public void overRatePastMax() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", 5.0, 100.01));
    }

    @Test
    public void overRateOver10Percent() {
        Project myProjectMock = mock(Project.class);
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");


        //when
        //set specifics for the static mock to ensure that addProject works. then compare to myProjectMock
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. X", 50, 56);

            //then
            assertThat(p, equalTo(myProjectMock));
        }
    }

    @Test
    public void overRateNotOver10Percent() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", 10, 10.1));
    }

    @Test
    public void bothRatesInvalid() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", 0.1, 100.01));
    }

    @Test
    public void allInvalidParams() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("", null, 0.1, 100.01));
    }

    //unsure if illegal state takes precedence
    @Test
    public void allInvalidParamsAndState() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("", null, 0.1, 100.01));
    }

    @Test
    public void normalAdd() {
        //given
        Project myProjectMock = mock(Project.class);
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");


        //when
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block
            Project p = fixture.addProject("A renovation", "Mr. X", 1, 50);
            
            //then
            assertThat(p, equalTo(myProjectMock));
            assertEquals(1, fixture.getAllProjects().size());
        }
    }

    //test removeProject()
    @Test
    public void removeProjectNoInjections() {
        assertThrows(IllegalStateException.class, () -> fixture.removeProject(1337));
    }

    @Test
    public void removeProjectNoLogin() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        assertThrows(IllegalStateException.class, () -> fixture.removeProject(1020));
    }

    @Test
    public void removeFromEmpty() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalStateException.class, () -> fixture.removeProject(1020));
    }

    @Test
    public void noProjectWithGivenId() {
        //given, any project.getId call will return 50
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(50);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        //when
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. X", 1, 50);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertThrows(IllegalStateException.class, () -> fixture.removeProject(9999));
        }
    }

    @Test
    public void negativeIDParam() {
        //given, any project.getId call will return 660
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(660);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        //when
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block
            Project p = fixture.addProject("A name", "A client", 1, 50);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertThrows(IllegalStateException.class, () -> fixture.removeProject(-1));
        }
    }

    @Test
    public void validRemove() {
        //given, any project.getId call will return 50
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(50);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        //when
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block
            Project p = fixture.addProject("A renovation", "Mr. X", 1, 50);
            fixture.removeProject(50);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertEquals(0, fixture.getAllProjects().size());
        }
    }

    //test addTask()
    @Test
    public void addTaskNoInjections() {
        assertThrows(IllegalStateException.class, () -> fixture.addTask(609, "New task", 20, false));
    }

    @Test
    public void addTaskNoLogin() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        assertThrows(IllegalStateException.class, () -> fixture.addTask(619, "New task", 20, false));
    }

    @Test
    public void addTaskToEmptyProjects() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalStateException.class, () -> fixture.addTask(10, "Some new task", 10, false));
    }

    @Test
    public void nullTaskDescription() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());

        assertThrows(IllegalArgumentException.class, () -> fixture.addTask(629, null, 20, false));
    }

    @Test
    public void emptyTaskDescription() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addTask(639, "", 20, false));
    }

    @Test
    public void taskHoursOver100() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addTask(639, "", 150, false));
    }

    @Test
    public void taskHoursNegative() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalArgumentException.class, () -> fixture.addTask(639, "", -10, false));
    }


    @Test
    public void addTaskNoProjectMatch() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(1);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. X", 1, 50);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertThrows(IllegalStateException.class, () -> fixture.addTask(10, "Some new task", 10, false));
        }
    }

    @Test
    public void addTaskNormalNoForce() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(20);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. X", 1, 50);
            boolean b = fixture.addTask(20, "Some new task", 10, false);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertTrue(b);
        }
    }

    @Test
    public void addTaskNormalOverWithForce() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(20);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            //go over default 100 ceiling, but give force
            Project p = fixture.addProject("A renovation", "Mr. X", 1, 50);
            boolean b = fixture.addTask(20, "Some new unreasonable task", 100, true);
            boolean c = fixture.addTask(20, "Another new unreasonable task", 100, true);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertTrue(b);
            assertTrue(c);
        }
    }

    @Test
    public void addTaskNormalOverWithoutForce() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(20);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            //go over default 100 ceiling, but give force
            Project p = fixture.addProject("A renovation", "Mr. Y", 1, 50);
            boolean b = fixture.addTask(20, "An unreasonable new task", 90, false);
            boolean c = fixture.addTask(20, "yet another unreasonable new task", 100, false);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertTrue(b);
            assertFalse(c);
        }
    }

    //test setProjectCeiling()

    @Test
    public void setProjectCeilingNoInjections() {
        assertThrows(IllegalStateException.class, () -> fixture.setProjectCeiling(10, 10));
    }

    @Test
    public void setProjectCeilingNoLogin() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        assertThrows(IllegalStateException.class, () -> fixture.setProjectCeiling(10, 10));
    }

    @Test
    public void setProjectCeilingNoProjects() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalStateException.class, () -> fixture.setProjectCeiling(10, 10));
    }

    @Test
    public void setProjectCeilingNoMatchingProjectID() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(20);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. Y", 1, 50);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertThrows(IllegalStateException.class, () -> fixture.setProjectCeiling(21, 20));
        }

    }

    @Test
    public void setProjectCeilingInvalidCeilingNeg() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(20);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. Y", 1, 50);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertThrows(IllegalArgumentException.class, () -> fixture.setProjectCeiling(20, -20));
        }

    }

    @Test
    public void setProjectCeilingInvalidCeilingOver() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(20);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. Y", 1, 50);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertThrows(IllegalArgumentException.class, () -> fixture.setProjectCeiling(20, 2000));
        }
    }

    @Test
    public void setProjectCeilingInvalidCeilingZero() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(20);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. Y", 1, 50);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertThrows(IllegalArgumentException.class, () -> fixture.setProjectCeiling(20, 0));
        }
    }

    @Test
    public void setProjectCeilingNormalMax() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(20);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");


        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. Y", 1, 50);
            fixture.setProjectCeiling(20, 1000);
            boolean b = fixture.addTask(20, "An absurdly long task 1", 100, false);
            boolean c = fixture.addTask(20, "An absurdly long task 2", 100, false);
            boolean d = fixture.addTask(20, "An absurdly long task 3", 100, false);
            boolean e = fixture.addTask(20, "An absurdly long task 4", 100, false);
            boolean f = fixture.addTask(20, "An absurdly long task 5", 100, false);
            boolean g = fixture.addTask(20, "An absurdly long task 6", 100, false);
            boolean h = fixture.addTask(20, "An absurdly long task 7", 100, false);
            boolean i = fixture.addTask(20, "An absurdly long task 8", 100, false);
            boolean j = fixture.addTask(20, "An absurdly long task 9", 100, false);
            boolean k = fixture.addTask(20, "An absurdly long task 10", 100, false);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertTrue(b);
            assertTrue(c);
            assertTrue(d);
            assertTrue(e);
            assertTrue(f);
            assertTrue(g);
            assertTrue(h);
            assertTrue(i);
            assertTrue(j);
            assertTrue(k);
        }
    }

    @Test
    public void setProjectCeilingNormalMin() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(20);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. Y", 1, 50);
            fixture.setProjectCeiling(20, 1);
            boolean b = fixture.addTask(20, "An absurdly short task", 1, false);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertTrue(b);
        }
    }


    //test findProjectId() - no test for empty strings because empty string project add should be invalidated by the system
    @Test
    public void findProjectIdNullName() {
        assertThrows(IllegalArgumentException.class, () -> fixture.findProjectID(null, "Ms. J"));
    }

    @Test
    public void findProjectIdNullClient() {
        assertThrows(IllegalArgumentException.class, () -> fixture.findProjectID("Ms. J's project", null));
    }

    @Test
    public void findProjectIdNullAllParams() {
        assertThrows(IllegalArgumentException.class, () -> fixture.findProjectID(null, null));
    }

    @Test
    public void findProjectIdNoProjectNameMatch() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(69);
        when(myProjectMock.getName()).thenReturn("Project X");

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            fixture.addProject("Project X", "Rai Mei", 1, 50);

            //then
            assertThrows(IllegalStateException.class, () -> fixture.findProjectID("Project B", "Rai Mei"));
        }
    }

    @Test
    public void findProjectIdNoClientNameMatch() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(69);
        when(myProjectMock.getName()).thenReturn("Project A");

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            fixture.addProject("Project A", "Rai Mei", 1, 50);

            //then
            assertThrows(IllegalStateException.class, () -> fixture.findProjectID("Project A", "Raii Mii"));
        }
    }

    @Test
    public void findProjectIdNoMatchForBoth() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(69);
        when(myProjectMock.getName()).thenReturn("Project X");

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            fixture.addProject("Project A", "Rai Mei", 1, 50);

            //then
            assertThrows(IllegalStateException.class, () -> fixture.findProjectID("Project C", "Mr. P"));
        }
    }

    @Test
    public void findProjectIdNormal() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(69);
        when(myProjectMock.getName()).thenReturn("Project A");

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            fixture.addProject("Project A", "Rai Mei", 1, 50);

            //then
            assertEquals(69, fixture.findProjectID("Project A", "Rai Mei"));
        }
    }

    @Test
    public void searchProjectsNullClient() {
        assertThrows(IllegalArgumentException.class, () -> fixture.searchProjects(null));
    }

    @Test
    public void searchProjectsNormal() {
        //given
        Project myProjectMock = mock(Project.class);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            //when
            fixture.addProject("Project A", "Mr Shelby", 30, 50);
            fixture.addProject("Project B", "Mr Shelby", 30, 50);
            fixture.addProject("Project C", "Mr Thorne", 25, 50);

            List<Project> projectList = fixture.searchProjects("Mr Shelby");

            //then
            assertNotNull(projectList);
            assertEquals(2, projectList.size());
            for (Project project : projectList) {
                assertThat(project, equalTo(myProjectMock));
            }
        }
    }

    @Test
    public void searchProjectsAddRemoveSearch() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(20);
        when(myProjectMock.getName()).thenReturn("Project A");

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            //when
            fixture.addProject("Project A", "Mr Shelby", 30, 50);
            int p1Id = fixture.findProjectID("Project A", "Mr Shelby");
            fixture.removeProject(p1Id);

            List<Project> projectList = fixture.searchProjects("Mr Shelby");

            //then
            assertNotNull(projectList);
            assertEquals(0, projectList.size());
        }
    }

    @Test
    public void searchProjectsNoMatch() {
        //given
        Project myProjectMock = mock(Project.class);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            //when
            fixture.addProject("Project A", "Mr Shelby", 30, 50);
            fixture.addProject("Project B", "Mr Shelby", 30, 50);
            fixture.addProject("Project C", "Mr Thorne", 25, 50);

            List<Project> projectList = fixture.searchProjects("Mr Gold");

            //then
            assertNotNull(projectList);
            assertEquals(0, projectList.size());
        }
    }

    @Test
    public void searchProjectsCaseMismatch() {
        //given
        Project myProjectMock = mock(Project.class);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            //when
            fixture.addProject("Project A", "Mr Shelby", 30, 50);
            fixture.addProject("Project B", "Mr Shelby", 30, 50);
            fixture.addProject("Project C", "mr thorne", 25, 50);

            List<Project> projectList = fixture.searchProjects("MR THORNE");

            //then
            assertNotNull(projectList);
            assertEquals(0, projectList.size());
        }
    }

    //test getAllProjects()
    @Test
    public void getAllProjectsEmpty() {
        List<Project> projectList = fixture.getAllProjects();
        assertNotNull(projectList);
        assertEquals(0, projectList.size());
    }

    @Test
    public void getAllProjectsNormal() {
        //given
        Project myProjectMock = mock(Project.class);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            //when
            fixture.addProject("Project A", "Mr Shelby", 30, 50);
            fixture.addProject("Project B", "Mr Shelby", 30, 50);
            fixture.addProject("Project C", "mr thorne", 25, 50);

            List<Project> projectList = fixture.getAllProjects();

            //then
            assertNotNull(projectList);
            assertEquals(3, projectList.size());
            for (Project project : projectList) {
                assertThat(project, equalTo(myProjectMock));
            }
        }
    }

    //test audit()
    @Test
    public void auditNoInjections() {
        assertThrows(IllegalStateException.class, () -> fixture.audit());
    }

    @Test
    public void auditNoLogin() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        assertThrows(IllegalStateException.class, () -> fixture.audit());
    }

    @Test
    public void auditNoCompliance() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalStateException.class, () -> fixture.audit());
    }

    @Test
    public void auditAllCompliant() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(1, 1, 2, 2, 3, 3);
        when(myProjectMock.getName()).thenReturn("Project A", "Project B", "Project C");
        ComplianceReporting myComplianceMock = mock(ComplianceReporting.class);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.injectCompliance(myComplianceMock);
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            //when
            fixture.addProject("Project A", "Mr Shelby", 30, 50);
            fixture.addProject("Project B", "Mr Shelby", 30, 50);
            fixture.addProject("Project C", "Mr Thorne", 25, 50);



            fixture.addTask(fixture.findProjectID("Project A", "Mr Shelby"), "Project A task", 50, false);
            fixture.addTask(fixture.findProjectID("Project B", "Mr Shelby"), "Project B task", 60, false);
            fixture.addTask(fixture.findProjectID("Project C", "Mr Thorne"), "Project C task", 70, false);

            fixture.audit();

            //then
            verify(myComplianceMock, never()).sendReport(anyString(), anyInt(), any());

        }
    }

    // @Test
    // public void auditNoneCompliant() {
    //     Project myProjectMock = mock(Project.class);
    //     when(myProjectMock.getId()).thenReturn(1, 1, 1, 1, 2);
    //     when(myProjectMock.getName()).thenReturn("Project A", "Project A", "Project A", "Project A", "Project C");
    //     ComplianceReporting myComplianceMock = mock(ComplianceReporting.class);

    //     ERPCheatFactory hax = new ERPCheatFactory();
    //     fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
    //     fixture.injectCompliance(myComplianceMock);
    //     fixture.login("user", "password");

    //     try (MockedStatic<Project> mock = mockStatic(Project.class)) {
    //         mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
    //                 .thenReturn(myProjectMock);


    //         fixture.addProject("Project A", "Mr Shelby", 30, 50);
    //         fixture.setProjectCeiling(fixture.findProjectID("Project A", "Mr Shelby"), 20);
    //         fixture.addTask(fixture.findProjectID("Project A", "Mr Shelby"), "Project A task", 50, true);

    //         fixture.addProject("Project C", "Mr Thorne", 25, 50);
    //         fixture.setProjectCeiling(fixture.findProjectID("Project C", "Mr Thorne"), 20);
    //         fixture.addTask(fixture.findProjectID("Project C", "Mr Thorne"), "Project C task", 70, true);

    //         fixture.audit();

    //         verify(myComplianceMock, times(2)).sendReport(anyString(), anyInt(), any());
    //     }
    // }

    // @Test
    // public void auditMixedCompliance() {
    //     Project myProjectMock = mock(Project.class);
    //     when(myProjectMock.getId()).thenReturn(1, 1, 1, 1, 2);
    //     when(myProjectMock.getName()).thenReturn("Project A", "Project A", "Project A", "Project A", "Project C");
    //     ComplianceReporting myComplianceMock = mock(ComplianceReporting.class);

    //     ERPCheatFactory hax = new ERPCheatFactory();
    //     fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
    //     fixture.injectCompliance(myComplianceMock);
    //     fixture.login("user", "password");

    //     try (MockedStatic<Project> mock = mockStatic(Project.class)) {
    //         mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
    //                 .thenReturn(myProjectMock);


    //         fixture.addProject("Project A", "Mr Shelby", 30, 50);
    //         fixture.setProjectCeiling(fixture.findProjectID("Project A", "Mr Shelby"), 20);
    //         fixture.addTask(fixture.findProjectID("Project A", "Mr Shelby"), "Project A task", 50, true);

    //         fixture.addProject("Project C", "Mr Thorne", 25, 50);
    //         fixture.setProjectCeiling(fixture.findProjectID("Project C", "Mr Thorne"), 20);
    //         fixture.addTask(fixture.findProjectID("Project C", "Mr Thorne"), "Project C task", 15, true);

    //         //getName will return project C
    //         fixture.audit();

    //         verify(myComplianceMock, times(1)).sendReport(anyString(), anyInt(), any());
    //     }
    // }

    //test finalizeProject()

    @Test
    public void finalizeProjectNoInjections() {
        assertThrows(IllegalStateException.class, () -> fixture.finaliseProject(1));
    }

    @Test
    public void finalizeProjectNoLogin() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        assertThrows(IllegalStateException.class, () -> fixture.finaliseProject(1));
    }

    @Test
    public void finalizeProjectNoClientReporting() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        assertThrows(IllegalStateException.class, () -> fixture.finaliseProject(1));
    }

    @Test
    public void finalizeProjectNoProjects() {
        ClientReporting myClientMock = mock(ClientReporting.class);
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        fixture.injectClient(myClientMock);
        assertThrows(IllegalStateException.class, () -> fixture.finaliseProject(1));
    }

    @Test
    public void finalizeProjectNoMatch() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(1);
        ClientReporting myClientMock = mock(ClientReporting.class);
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        fixture.injectClient(myClientMock);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            fixture.addProject("Project A", "Mr Shelby", 30, 50);
            assertThrows(IllegalStateException.class, () -> fixture.finaliseProject(3));
        }
    }

    @Test
    public void finalizeOneProject() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(1);
        when(myProjectMock.getName()).thenReturn("Project A");
        ClientReporting myClientMock = mock(ClientReporting.class);
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        fixture.injectClient(myClientMock);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            fixture.addProject("Project A", "Mr Shelby", 30, 50);
            fixture.addTask(1, "Some extra task needing finishing", 20, false);
            fixture.finaliseProject(1);

            verify(myClientMock, times(1)).sendReport(eq("Mr Shelby"), anyString(), any());
            assertEquals(0, fixture.getAllProjects().size());
        }
    }

    @Test
    public void finalizeNonCompliant() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(1);
        when(myProjectMock.getName()).thenReturn("Project A");

        ClientReporting myClientMock = mock(ClientReporting.class);
        ComplianceReporting myComplianceMock = mock(ComplianceReporting.class);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        fixture.injectClient(myClientMock);
        fixture.injectCompliance(myComplianceMock);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            fixture.addProject("Project A", "Mr Shelby", 30, 50);
            fixture.setProjectCeiling(fixture.findProjectID("Project A", "Mr Shelby"), 20);
            fixture.addTask(1, "Some extra task needing finishing", 25, true);
            fixture.audit();
            fixture.finaliseProject(1);

            verify(myClientMock, times(1)).sendReport(eq("Mr Shelby"), anyString(), any());
            verify(myComplianceMock, times(1)).sendReport(eq("Project A"), anyInt(), any());
            assertEquals(0, fixture.getAllProjects().size());
        }
    }

    //test injectAuth()
    @Test
    public void injectNullAuthentication() {
        ERPCheatFactory hax = new ERPCheatFactory();
        assertThrows(IllegalArgumentException.class, () -> fixture.injectAuth(null, hax.getAuthorisationModule()));
    }

    @Test
    public void injectNullAuthorisation() {
        ERPCheatFactory hax = new ERPCheatFactory();
        assertThrows(IllegalArgumentException.class, () -> fixture.injectAuth(hax.getAuthenticationModule(), null));
    }

    @Test
    public void unsetAuthModules() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(10);
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");


        //when
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block
            Project p = fixture.addProject("A renovation", "Mr. X", 1, 50);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertEquals(1, fixture.getAllProjects().size());

            fixture.injectAuth(null, null);

            assertThrows(IllegalStateException.class, () -> fixture.removeProject(10));
        }
    }

    //test injectCompliance()
    @Test
    public void unsetCompliance() {
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(1, 1, 2, 2, 3, 3);
        when(myProjectMock.getName()).thenReturn("Project A", "Project B", "Project C");
        ComplianceReporting myComplianceMock = mock(ComplianceReporting.class);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.injectCompliance(myComplianceMock);
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            //when
            fixture.addProject("Project A", "Mr Shelby", 30, 50);
            fixture.addProject("Project B", "Mr Shelby", 30, 50);
            fixture.addProject("Project C", "Mr Thorne", 25, 50);

            fixture.addTask(fixture.findProjectID("Project A", "Mr Shelby"), "Project A task", 50, false);
            fixture.addTask(fixture.findProjectID("Project B", "Mr Shelby"), "Project B task", 60, false);
            fixture.addTask(fixture.findProjectID("Project C", "Mr Thorne"), "Project C task", 70, false);

            fixture.audit();

            verify(myComplianceMock, never()).sendReport(anyString(), anyInt(), any());

            //subsequent getId/Name will now only return 3/Project C
            fixture.addTask(fixture.findProjectID("Project C", "Mr Thorne"), "Project C extra task", 90, true);
            fixture.injectCompliance(null);
            assertThrows(IllegalStateException.class, () -> fixture.audit());
        }
    }

    //test injectClient()
    // @Test
    // public void unsetClient() {
    //     Project myProjectMock = mock(Project.class);
    //     when(myProjectMock.getId()).thenReturn(1, 1, 1, 1, 2);
    //     when(myProjectMock.getName()).thenReturn("Project A", "Project A", "Project A", "Project A", "Project B");
    //     ClientReporting myClientMock = mock(ClientReporting.class);
    //     ERPCheatFactory hax = new ERPCheatFactory();
    //     fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
    //     fixture.login("user", "password");
    //     fixture.injectClient(myClientMock);

    //     try (MockedStatic<Project> mock = mockStatic(Project.class)) {
    //         mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
    //                 .thenReturn(myProjectMock);
    //         //when
    //         fixture.addProject("Project A", "Mr Shelby", 30, 50);
    //         fixture.addTask(fixture.findProjectID("Project A", "Mr Shelby"), "New task for project A", 50, false);
    //         fixture.finaliseProject(fixture.findProjectID("Project A", "Mr Shelby"));

    //         fixture.addProject("Project B", "Mr Client", 40, 50);
    //         fixture.addTask(fixture.findProjectID("Project B", "Mr Client"), "New task for project B", 50, false);

    //         verify(myClientMock, times(1)).sendReport(eq("Mr Shelby"), anyString(), any());
    //         assertEquals(1, fixture.getAllProjects().size());

    //         fixture.injectClient(null);

    //         assertThrows(IllegalStateException.class, () -> fixture.finaliseProject(fixture.findProjectID("Project B", "Mr Client")));
    //         assertEquals(1, fixture.getAllProjects().size());
    //     }
    // }

    //test login()
    @Test
    public void loginUsrNull() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        assertThrows(IllegalArgumentException.class, () -> fixture.login(null, "password"));
    }

    @Test
    public void loginPassNull() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        assertThrows(IllegalArgumentException.class, () -> fixture.login("user", null));
    }

    @Test
    public void loginInvalidCreds() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        boolean b = fixture.login("abur2581", "notmyrealpassword:)");
        assertFalse(b);
    }

    //test logout()
    @Test
    public void logoutAdd() {
        //given
        Project myProjectMock = mock(Project.class);
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");


        //when
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block
            Project p = fixture.addProject("A renovation", "Mr. X", 1, 50);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertEquals(1, fixture.getAllProjects().size());

            fixture.logout();

            assertThrows(IllegalStateException.class, () -> fixture.addProject("A renovation part 2", "Mr. Y", 1, 50));
        }
    }

    @Test
    public void logoutNoAuthInjections() {
        ERPCheatFactory hax = new ERPCheatFactory();
        assertThrows(IllegalStateException.class, () -> fixture.logout());
    }

    @Test
    public void logoutNoLogin() {
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        assertThrows(IllegalStateException.class , () -> fixture.logout());
    }

}
