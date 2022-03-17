package au.edu.sydney.soft3202.reynholm.erp.billingsystem;

import au.edu.sydney.soft3202.reynholm.erp.project.Project;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class BSFacadeImplTest {

    @Test
    public void testSomething() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block
            BSFacadeImpl fixture = new BSFacadeImpl();
            Project p = fixture.addProject("name", "client", 1, 50);
            assertThat(p, equalTo(yourProjectMock));
        }
    }
}