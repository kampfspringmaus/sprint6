import java.util.Comparator;

public class TimeOfDayComparator implements Comparator<TimeOfDay> {
    @Override
    public int compare(TimeOfDay a, TimeOfDay b) {
        if (Integer.compare(a.getHours(),b.getHours()) != 0) {
            return Integer.compare(a.getHours(),b.getHours());
        } else {
            return Integer.compare(a.getMinutes(),b.getMinutes());
        }
    }
}
