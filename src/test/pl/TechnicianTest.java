/*package test.pl;

import be.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.controllers.TechnicianViewController;
import pl.models.ProjectModel;

import static org.mockito.Mockito.*;

public class TechnicianTest {

    private TechnicianViewController technicianViewController;

    @Mock
    private TableView<Project> mockTableView;

    @Mock
    private ProjectModel mockProjectModel;

    private ObservableList<Project> projects;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        technicianViewController = new TechnicianViewController();
        technicianViewController.projectTableView = mockTableView;
        technicianViewController.projectModel = mockProjectModel;
        projects = FXCollections.observableArrayList();
        when(mockTableView.getSelectionModel().getSelectedItem()).thenReturn(null);
        when(mockTableView.getSelectionModel().getSelectedItem()).thenReturn(new Project());
    }

    @Test
    public void testCreateBtnPressed() {
        technicianViewController.createBtnPressed(null);

        verify(mockTableView, never()).getSelectionModel();
        verify(mockTableView, never()).getSelectionModel().getSelectedItem();
    }

    @Test
    public void testUpdateBtnPressed_NoSelection() {
        technicianViewController.updateBtnPressed(null);

        verify(mockTableView).getSelectionModel();
        verify(mockTableView, never()).getSelectionModel().getSelectedItem();
    }

    @Test
    public void testUpdateBtnPressed_Selection() {
        when(mockTableView.getSelectionModel().getSelectedItem()).thenReturn(new Project());
        when(mockTableView.getItems()).thenReturn(projects);

        technicianViewController.updateBtnPressed(null);

        verify(mockTableView).getSelectionModel();
        verify(mockTableView, times(2)).getSelectionModel().getSelectedItem();
    }

    @Test
    public void testDeleteBtnPressed_NoSelection() {
        technicianViewController.deleteBtnPressed(null);

        verify(mockTableView, never()).getSelectionModel();
        verify(mockProjectModel, never()).deleteProject(any(Project.class));
    }

    @Test
    public void testDeleteBtnPressed_Selection() {
        when(mockTableView.getSelectionModel().getSelectedItem()).thenReturn(new Project());
        when(mockTableView.getItems()).thenReturn(projects);

        technicianViewController.deleteBtnPressed(null);

        // Verify that the project is deleted and the table is refreshed
        verify(mockTableView, times(2)).getSelectionModel();
        verify(mockProjectModel).deleteProject(any(Project.class));
        verify(mockTableView).setItems(any(ObservableList.class));
    }
}
*/