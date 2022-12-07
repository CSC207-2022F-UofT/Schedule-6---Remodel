package boundary.Task;

import javafx.scene.control.TableView;
import responseModel.Event.LoadEventsResponseModel;
import responseModel.Task.LoadTasksResponseModel;

public interface LoadTasksOutputBoundary {
    void loadTasks(LoadTasksResponseModel responseModel, TableView tableView);
}