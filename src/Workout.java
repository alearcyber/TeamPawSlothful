public class Workout {

    private String workouts;
    private String name;

    public Workout(String name, String workouts){
        this.name = name;
        this.workouts = workouts;
    }


    public String getName(){
        return this.name;
    }

    public String getWorkouts() {
        return workouts;
    }
}
