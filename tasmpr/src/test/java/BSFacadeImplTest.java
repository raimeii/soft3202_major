//package au.edu.sydney.soft3202.reynholm.erp.billingsystem;
//import au.edu.sydney.soft3202.reynholm.erp.project.Project;
//import au.edu.sydney.soft3202.reynholm.erp.cheatmodule.ERPCheatFactory;
import project.Project;
import billingsystem.BSFacadeImpl;
import cheatmodule.ERPCheatFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.stubbing.OngoingStubbing;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.IllegalStateException;
import java.lang.IllegalArgumentException;

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
        assertThrows(IllegalArgumentException.class, () -> fixture.addProject("some name", "some client", 0.1, 40));
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
    public void overRateExactly10Percent() {
        Project myProjectMock = mock(Project.class);
        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");


        //when
        //set specifics for the static mock to ensure that addProject works. then compare to myProjectMock
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), 50, 5))
                    .thenReturn(myProjectMock);

            Project p = fixture.addProject("A renovation", "Mr. X", 50, 55);

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
        assertThrows(IllegalStateException.class, () -> fixture.addProject("", null, 0.1, 100.01));
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
            boolean b = fixture.addTask(20, "Some new unreasonable task", 200, true);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertTrue(b);
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
            boolean b = fixture.addTask(20, "Another unreasonable new task", 200, false);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertFalse(b);
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
            boolean b = fixture.addTask(20, "An absurdly long task", 999, false);

            //then
            assertThat(p, equalTo(myProjectMock));
            assertTrue(b);
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

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            fixture.addProject("Project A", "Rai Mei", 1, 50);

            //then
            assertThrows(IllegalStateException.class, () -> fixture.findProjectID("Project B", "Rai Mei"));
        }
    }

    @Test
    public void findProjectIdNoClientNameMatch() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(69);

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
}
