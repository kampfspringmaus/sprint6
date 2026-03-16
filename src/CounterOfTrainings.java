public class CounterOfTrainings {

    public CounterOfTrainings (Coach coach,Integer trainings) {
        this.coach = coach;
        this.trainings = trainings;
    }

    private Coach coach;
    private int trainings;



    public void setTrainings(int trainings) {
        this.trainings = trainings;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainings() {
        return trainings;
    }
}
