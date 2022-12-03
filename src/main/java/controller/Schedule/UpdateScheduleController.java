package controller.Schedule;

import boundary.Schedule.UpdateScheduleInputBoundary;
import javafx.scene.control.Label;
import presenter.AddSchedulePresenter;
import requestModel.ScheduleItemRequestModel;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateScheduleController {

    final UpdateScheduleInputBoundary addScheduleItemInputBoundary;

    final AddSchedulePresenter presenter;

    public UpdateScheduleController(UpdateScheduleInputBoundary inputBoundary, AddSchedulePresenter presenter) {
        this.addScheduleItemInputBoundary = inputBoundary;
        this.presenter = presenter;
    }

    public void create(Label label, String title, LocalDate startDate, LocalDate endDate,
                       String startTime, String endTime,
                       String startAMPM, String endAMPM) {
        if (title.isBlank() || (startDate == null) || (endDate == null) || startTime.isBlank() || endTime.isBlank()) {
            presenter.prepareFailView(label, "Please Fill in All Fields");
        } else if (!this.inputTimeChecker(startTime, endTime)) {
            presenter.prepareFailView(label, "Please Fill a Valid Time as HH:MM");
        } else {
            ScheduleItemRequestModel inputData = new ScheduleItemRequestModel(
                    title, startDate, endDate, timeConverter(startTime, startAMPM), timeConverter(endTime, endAMPM));
            addScheduleItemInputBoundary.create(inputData);
            presenter.prepareSuccessView(label, "Event Added!");
        }
    }

    // Checks if user inputs startTime and endTime is valid
    public boolean inputTimeChecker(String startTime, String endTime) {
        String[] time = (startTime + ":" + endTime).split(":");
        //if startTime = "12:30" and endTime is "2:15", then time = ["12", "30", "2", "15"]
        return (startTime.matches("\\d{2}:\\d{2}") || startTime.matches("\\d:\\d{2}")) &&
                (endTime.matches("\\d{2}:\\d{2}") || endTime.matches("\\d:\\d{2}")) &&
                (Integer.parseInt(time[0]) <= 12) && (Integer.parseInt(time[0]) > 0) &&
                (Integer.parseInt(time[2]) <= 12) && (Integer.parseInt(time[2]) > 0) &&
                (Integer.parseInt(time[1]) <= 59) && (Integer.parseInt(time[3]) <= 59);
    }

    // changes time from String to LocalTime, and checks if its AM or PM time
    public LocalTime timeConverter(String time, String AMPM) {
        String[] parsedTime = time.split(":");
        if (AMPM.equals("PM")) {
            parsedTime[0] = Integer.toString(Integer.parseInt(parsedTime[0]) + 12);
        }
        return LocalTime.of(Integer.parseInt(parsedTime[0]), Integer.parseInt(parsedTime[1]));
    }
}
