import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Timetable {

    //private /* как это хранить??? */ timetable
    public Timetable() {
        this.timetable = new HashMap<>();
        timetable.put(DayOfWeek.MONDAY,new TreeMap<>(comparator));
        timetable.put(DayOfWeek.TUESDAY,new TreeMap<>(comparator));
        timetable.put(DayOfWeek.WEDNESDAY,new TreeMap<>(comparator));
        timetable.put(DayOfWeek.THURSDAY,new TreeMap<>(comparator));
        timetable.put(DayOfWeek.FRIDAY,new TreeMap<>(comparator));
        timetable.put(DayOfWeek.SATURDAY,new TreeMap<>(comparator));
        timetable.put(DayOfWeek.SUNDAY,new TreeMap<>(comparator));

    }

    //private /* как это хранить??? */ timetable
    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();
    TimeOfDayComparator comparator = new TimeOfDayComparator();



    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> daySchedule = timetable.get(trainingSession.getDayOfWeek());
        if (daySchedule.containsKey(trainingSession.getTimeOfDay())) {
            daySchedule.get(trainingSession.getTimeOfDay()).add(trainingSession);
        } else {
            ArrayList<TrainingSession> sessions = new ArrayList<>();
            sessions.add(trainingSession);
            daySchedule.put(trainingSession.getTimeOfDay(), sessions);
        }
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

}