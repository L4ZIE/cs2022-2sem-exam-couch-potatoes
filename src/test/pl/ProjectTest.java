/*package test.pl;

import be.Devices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.runners.MockitoJUnitRunner;
import pl.controllers.ProjectViewController;
import pl.models.ProjectModel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectTest {

    @Mock
    private ProjectModel projectModelMock;

    private ProjectViewController projectViewController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        projectViewController = new ProjectViewController();
        projectViewController.projectModel = projectModelMock;
    }

    @Test
    public void testSaveDeviceToList() {
        Devices device = new Devices();
        ProjectViewController.staticRefNumber = "123";

        projectViewController.saveDeviceToList(device);

        assertEquals(1, projectViewController.devices.size());
        assertEquals(device, projectViewController.devices.get(0));
        verify(device).setRefNumber("123");
        verify(projectModelMock).createDevice(device);
    }
}
*/