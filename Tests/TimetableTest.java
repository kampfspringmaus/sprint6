import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class TimetableTest {

@Test
void testAddNewTrainingSession() {
    Timetable timetable = new Timetable();

    Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
    Coach coach2 = new Coach("Данилов", "Пётр", "Францевич");
    Coach coach3 = new Coach("Мотыга", "Эльвира", "Красьевна");

    Group group1 = new Group("Йога для взрослых", Age.ADULT, 60);
    Group group2 = new Group("Кейтеринг для подростков", Age.ADULT, 60);
    Group group3 = new Group("Бокс для детей", Age.CHILD, 60);

    TrainingSession firstTrainingSession = new TrainingSession(group1, coach1,
            DayOfWeek.MONDAY, new TimeOfDay(13, 0));
    TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
            DayOfWeek.MONDAY, new TimeOfDay(14, 0));
    TrainingSession thirdTrainingSession = new TrainingSession(group3, coach3,
            DayOfWeek.TUESDAY, new TimeOfDay(13, 0));

    timetable.addNewTrainingSession(firstTrainingSession);
    timetable.addNewTrainingSession(secondTrainingSession);
    timetable.addNewTrainingSession(thirdTrainingSession);

    Assertions.assertEquals(2,timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
    Assertions.assertEquals(1,timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    Assertions.assertEquals(0,timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY).size());
}

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size(),1);
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size(),0);
        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size(),1);
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size(),2);
    ArrayList<TimeOfDay> thursdayTrainings = new ArrayList<>(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).keySet());
        Assertions.assertEquals(thursdayTrainings.get(0).getHours(),13);
        Assertions.assertEquals(thursdayTrainings.get(0).getMinutes(),0);
        Assertions.assertEquals(thursdayTrainings.get(1).getHours(),20);
        Assertions.assertEquals(thursdayTrainings.get(1).getMinutes(),0);// Assertions.assertEquals(t1.getTimeOfDay(),13);
       // Assertions.assertEquals(t2.getTimeOfDay(),20);
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size(),0);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(13,0)).size(),1);
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(14,0)));
    }

    //проверить, что если три тренировки начинаются в одно и то же время, метод GetTrainingSessionsForDayAndTime() возвращает список из трёх позиций
    @Test
    void testGetTrainingSessionsForDayAndTimeWithThreeTrainingsAtTheSameTime() {

        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Данилов", "Пётр", "Францевич");
        Coach coach3 = new Coach("Мотыга", "Эльвира", "Красьевна");

        Group group1 = new Group("Йога для взрослых", Age.ADULT, 60);
        Group group2 = new Group("Кейтеринг для подростков", Age.ADULT, 60);
        Group group3 = new Group("Бокс для детей", Age.CHILD, 60);

        TrainingSession firstTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(group3, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);

        Assertions.assertEquals(3,timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());

    }

    //проверки getCountByCoaches()
    //проверить, что если тренировок нет, то список возвращается пустым
    @Test
            void testGetCountByCoachesWithoutTrainings() {
        Timetable timetable = new Timetable();
        List<CounterOfTrainings> emptyList = Collections.emptyList();
        Assertions.assertEquals(timetable.getCountByCoaches(),emptyList);
    }
    //проверить, что выдаётся отсортированный список тренеров по убыванию количества тренировок
@Test
        void testGetCountByCoachesSorting() {
    Timetable timetable = new Timetable();
    Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
    Coach coach2 = new Coach("Данилов", "Пётр", "Францевич");
    Coach coach3 = new Coach("Мотыга", "Эльвира", "Красьевна");

    Group group1 = new Group("Йога для взрослых", Age.ADULT, 60);
    Group group2 = new Group("Кейтеринг для подростков", Age.ADULT, 60);
    Group group3 = new Group("Бокс для детей", Age.CHILD, 60);
    Group group4 = new Group("Акробатика для детей", Age.CHILD, 60);

    TrainingSession firstTrainingSession = new TrainingSession(group1, coach1,
            DayOfWeek.MONDAY, new TimeOfDay(12, 0));
    TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
            DayOfWeek.MONDAY, new TimeOfDay(13, 0));
    TrainingSession thirdTrainingSession = new TrainingSession(group3, coach2,
            DayOfWeek.MONDAY, new TimeOfDay(13, 0));
    TrainingSession fourthTrainingSession = new TrainingSession(group4, coach3,
            DayOfWeek.TUESDAY, new TimeOfDay(13, 0));
    TrainingSession fifthTrainingSession = new TrainingSession(group1, coach3,
            DayOfWeek.FRIDAY, new TimeOfDay(13, 0));
    TrainingSession sixthTrainingSession = new TrainingSession(group2, coach3,
            DayOfWeek.FRIDAY, new TimeOfDay(13, 0));
    timetable.addNewTrainingSession(firstTrainingSession);
    timetable.addNewTrainingSession(secondTrainingSession);
    timetable.addNewTrainingSession(thirdTrainingSession);
    timetable.addNewTrainingSession(fourthTrainingSession);
    timetable.addNewTrainingSession(fifthTrainingSession);
    timetable.addNewTrainingSession(sixthTrainingSession);

    List<CounterOfTrainings> result = timetable.getCountByCoaches();
    Assertions.assertEquals(3,result.size());
   Assertions.assertEquals(3,result.get(0).getTrainings());
    Assertions.assertEquals(2,result.get(1).getTrainings());
    Assertions.assertEquals(1,result.get(2).getTrainings());

}
//проверить, что метод работает, если у всех тренеров одинаковое количество тренировок

    @Test
    void testGetCountByCoachesWithEqualTrainingNumber() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Данилов", "Пётр", "Францевич");
        Coach coach3 = new Coach("Мотыга", "Эльвира", "Красьевна");

        Group group1 = new Group("Йога для взрослых", Age.ADULT, 60);
        Group group2 = new Group("Кейтеринг для подростков", Age.ADULT, 60);
        Group group3 = new Group("Бокс для детей", Age.CHILD, 60);
        Group group4 = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession firstTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group2, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(group3, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession fourthTrainingSession = new TrainingSession(group4, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));
        TrainingSession fifthTrainingSession = new TrainingSession(group1, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(13, 0));
        TrainingSession sixthTrainingSession = new TrainingSession(group2, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);
        timetable.addNewTrainingSession(fourthTrainingSession);
        timetable.addNewTrainingSession(fifthTrainingSession);
        timetable.addNewTrainingSession(sixthTrainingSession);

        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        Assertions.assertEquals(3,result.size());
        Assertions.assertEquals(2,result.get(0).getTrainings());
        Assertions.assertEquals(2,result.get(1).getTrainings());
        Assertions.assertEquals(2,result.get(2).getTrainings());
    }






}