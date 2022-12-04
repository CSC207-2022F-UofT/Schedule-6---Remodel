package useCaseInteractor.Task;

import entity.Task.Task;
import entity.Task.TaskFactory;
import presenter.TaskPresenter;
import requestModel.TaskRequestModel;
import responseModel.Task.TaskResponseModel;
import useCaseInteractor.DataAccess;

public class AddTask implements boundary.Task.AddTaskItemInputBoundary{
    final DataAccess dataAccess;

    final TaskFactory taskFactory;

    final TaskPresenter taskPresenter;

    public AddTask(DataAccess dataAccess, TaskFactory taskFactory, TaskPresenter taskPresenter) {
        this.dataAccess = dataAccess;
        this.taskFactory = taskFactory;
        this.taskPresenter = taskPresenter;
    }

    @Override
    public TaskResponseModel create(TaskRequestModel inputData) {
        Task task = taskFactory.create(inputData.getDescription(), inputData.getDate(), inputData.getCategory());

        TaskResponseModel taskResponseModel = new TaskResponseModel(task.getDescription(), task.getDate(),
                task.getCategory());

        dataAccess.setTask(taskResponseModel);

        return taskPresenter.prepareSuccessView(taskResponseModel);
    }
}
