//package au.edu.sydney.soft3202.reynholm.erp.billingsystem;
//import au.edu.sydney.soft3202.reynholm.erp.project.Project;
//import au.edu.sydney.soft3202.reynholm.erp.cheatmodule.ERPCheatFactory;
import billingsystem.BSFacadeImpl;
import cheatmodule.ERPCheatFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.stubbing.OngoingStubbing;
import project.Project;
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
    public void setupfixture() {
        fixture = new BSFacadeImpl();
    }

    @AfterEach
    public void teardownfixture() {
        fixture = null;
    }

    //addProject tests
    @Test
    public void noPermsAdd() {
        assertThrows(IllegalStateException.class, ()-> fixture.addProject("Building A Renovations", "Mr. X", 40.0, 50));
    }

    //Perms using cheat module
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
            BSFacadeImpl fixture = new BSFacadeImpl();
            Project p = fixture.addProject("A renovation", "Mr. X", 1, 50);
            
            //then
            assertThat(p, equalTo(myProjectMock));
        }
    }

    //test findProjectId()
    @Test
    public void findProjectIdNormal() {
        //given
        Project myProjectMock = mock(Project.class);
        when(myProjectMock.getId()).thenReturn(69);

        ERPCheatFactory hax = new ERPCheatFactory();
        fixture.injectAuth(hax.getAuthenticationModule(), hax.getAuthorisationModule());
        fixture.login("user", "password");
        //when
        // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block

        BSFacadeImpl fixture = new BSFacadeImpl();
        Project p = fixture.addProject("Project A", "Rai Mei", 1, 50);

        //then
        assertEquals(69, fixture.findProjectID("Project A", "Rai Mei"));
    }
}
