import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<CounterOfTrainings>  getCountByCoaches() {
        List<TrainingSession> trainingsList = new ArrayList<>();
        List<CounterOfTrainings> coachWorkload = new ArrayList<>();
        HashMap<Coach,Integer> counters = new HashMap<>();
        //выбираем все тренировки за неделю в единый список
        for (DayOfWeek day : timetable.keySet()) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> daySchedule = timetable.get(day);
            for (TimeOfDay time : daySchedule.keySet()) {
                ArrayList<TrainingSession> currentTimeTrainings = daySchedule.get(time);
                for (TrainingSession t : currentTimeTrainings) {
                    trainingsList.add(t);
                }
            }
        }
        // из единого списка получаем агрегат с количеством тренировок по каждому тренеру
        for (TrainingSession t : trainingsList) {
            Coach coach = t.getCoach();
            if (!counters.containsKey(coach)) {
                counters.put(coach,1);
            } else {
                counters.put(coach,counters.get(coach)+1);
            }
        }
        //формируем список из хэш-таблицы
        for (Coach coach : counters.keySet()) {
            coachWorkload.add(new CounterOfTrainings(coach,counters.get(coach)));
        }

        CounterOfTrainingsComparator compareWorkload = new CounterOfTrainingsComparator();
        coachWorkload.sort(compareWorkload);
        return coachWorkload;

    }

}