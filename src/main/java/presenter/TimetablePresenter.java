package presenter;

import boundary.Schedule.AddScheduleItemInputBoundary;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import controller.Schedule.TimetableController;
import database.MongoDBAccess;
import entity.Schedule.CommonScheduleItemFactory;
import entity.Schedule.ScheduleItemFactory;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import entity.Schedule.TimeManagement;
import main.LoginPage;
import main.collectCollection;
import requestModel.ScheduleItemRequestModel;
import useCaseInteractor.Schedule.AddScheduleItem;
import useCaseInteractor.User.userCollection;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Map;


public class TimetablePresenter {

    private final TimeManagement TM  = new TimeManagement();

    public void showSchedule(CalendarView calendar) {
        ArrayList<ArrayList<Object>> entireSchedule = null;
        for (Calendar temp : calendar.getCalendars()) {
            temp.clear();
            for (ArrayList<Object> schedule : entireSchedule) {
                temp.addEntry(createEntry(schedule));
            }
        }
    }

    public Entry<String> createEntry(ArrayList<Object> schedule) {
        Entry<String> entry = new Entry<>((String) schedule.get(0));

        String[] startDate = ((String )schedule.get(1)).split("-");
        String[] endDate = ((String )schedule.get(2)).split("-");
        String[] startTime = ((String )schedule.get(3)).split(":");
        String[] endTime = ((String )schedule.get(4)).split(":");

        entry.setInterval(LocalDate.now());
        entry.changeStartDate(LocalDate.of(
                Integer.parseInt(startDate[0]), Integer.parseInt(startDate[1]), Integer.parseInt(startDate[2])));
        entry.changeEndDate(LocalDate.of(
                Integer.parseInt(endDate[0]), Integer.parseInt(endDate[1]), Integer.parseInt(endDate[2])));
        entry.changeStartTime(LocalTime.of(Integer.parseInt(startTime[0]),Integer.parseInt(startTime[1])));
        entry.changeEndTime(LocalTime.of(Integer.parseInt(endTime[0]),Integer.parseInt(endTime[1])));
        return entry;
    }

    public void printCalendarEntries(Label entriesSaved, CalendarView calendar) throws UnknownHostException {
        for (Calendar temp : calendar.getCalendars()) {
            //System.out.println(temp.findEntries(TM.getStartDate(), TM.getEndDate(), TM.getTimeZone()));
            Map map = temp.findEntries(TM.getStartDate(), TM.getEndDate(), TM.getTimeZone());
            //creates a temp varible called entry of type object
            for (Object entry : map.values()) {
                //if the string of the object contains "]," then we know it has more than 1 entry
                if (entry.toString().contains("],")) {
                    //make a new list and a string
                    List<String> list = new ArrayList<String>();
                    String dataString = entry.toString();
                    //makes a loop to loop of the string to loop while dataString contains "],"
                    while (dataString.contains("],")) {
                        //add to the list a substring from the start (0) till the first instance of "],"
                        //list.add(dataString.substring(0, dataString.indexOf("],")));
                        //removes the substring we already added to the list from the string
                        list.add(dataString.substring(0, dataString.indexOf("],") + 2));
                        if (dataString.contains("],")) {
                            dataString = dataString.substring(dataString.indexOf("],") + 3);
                        }
                    list.add(dataString);

                    for (String s : list) {
                        //System.out.println(s);
                        findEntryData(s);
                        }
                    }
                } else {
                    findEntryData(entry);
                }
            }

            entriesSaved.setText("ALL ENTIRES SAVED");
            FadeTransition ft = new FadeTransition(Duration.millis(1850), entriesSaved);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setAutoReverse(true);
            ft.play();
            }
        }



    public void findEntryData(Object entry){
        //Finds the title in the values of the map
        int title_start = entry.toString().toUpperCase().indexOf("TITLE=") + ("TITLE=").length();
        int title_end = entry.toString().toUpperCase().indexOf(",");
        String title = entry.toString().substring(title_start, title_end);

        //Finds the start date in the values of the map
        int startDate_start = entry.toString().toUpperCase().indexOf("STARTDATE=") + ("STARTDATE=").length();
        int startDate_end = startDate_start + 10;
        String startDate = entry.toString().substring(startDate_start, startDate_end);

        //Finds the end date in the values of the map
        int endDate_start = entry.toString().toUpperCase().indexOf("ENDDATE=") + ("ENDDATE=").length();
        int endDate_end = endDate_start + 10;
        String endDate = entry.toString().substring(endDate_start, endDate_end);

        //Finds the start time in the values of the map
        int startTime_start = entry.toString().toUpperCase().indexOf("STARTTIME=") + ("STARTTIME=").length();
        int startTime_end = startTime_start + 5;
        String startTime = entry.toString().substring(startTime_start, startTime_end);

        //Finds the end time in the values of the map
        int endTime_start = entry.toString().toUpperCase().indexOf("ENDTIME=") + ("ENDTIME=").length();
        int endTime_end = endTime_start + 5;
        String endTime = entry.toString().substring(endTime_start, endTime_end);


        System.out.println("Event" + ": " + title + ", " + startDate + ", " + endDate +
                ", " + startTime + ", " + endTime);
//            String[] newStartDate = startDate.split("-");
//            String[] newEndDate = endDate.split("-");
//
//            String[] newStartTime = startTime.split(":");
//            String[] newEndTime = endTime.split(":");
//            System.out.println(newStartDate[0] + ", "+newEndDate.toString()+", "+newStartTime[0]+", "+newEndDate.toString());
//            ScheduleItemFactory item = new CommonSchedule ItemFactory();
//            MongoDBAccess dataAccess = new MongoDBAccess(collectCollection.main(), userCollection.getUsername());
//            AddScheduleItemInputBoundary addSchedule = new AddScheduleItem(dataAccess, item);
//            ScheduleItemRequestModel request = new ScheduleItemRequestModel(title, LocalDate.of(Integer.parseInt(newStartDate[0]),
//                    Integer.parseInt(newStartDate[1]),Integer.parseInt(newStartDate[2])), LocalDate.of(Integer.parseInt(newEndDate[0]),
//                    Integer.parseInt(newEndDate[1]),Integer.parseInt(newEndDate[2])),
//                    LocalTime.of(Integer.parseInt(newStartTime[0]),Integer.parseInt(newStartTime[1])), LocalTime.of(Integer.parseInt(newEndTime[0]),Integer.parseInt(newEndTime[1])));
//
//            addSchedule.create(request);
    }
    public void loadTODO(GridPane TODO) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getClassLoader().getResource("todo.fxml"));
        TODO.getChildren().add(fxmlLoader.load());
    }
}