import java.util.ArrayList;
import java.util.HashMap;

/**Class that implements controller design pattern*/
public class WorkoutController {

    /**This class's instance of the model*/
    private WorkoutModel model;

    /**Default Constructor*/
    public WorkoutController(){}

    /**Parameterized Constructor
     * @param model This class's instance of the model
     */
    public WorkoutController(WorkoutModel model){
        this.model = model;
    }

    /**Add selected exercise from Add Exercise panel to Current Plan panel
     * @param ex Exercise from Add Exercise panel to Current Plan panel
     */
    public void addToPlan(Exercise ex){
        model.addToPlan(ex);
    }

    /**Remove given exercise from current plan
     * @param exercise Exercise to remove
     */
    public void removeExercises(Exercise exercise){
        model.removeExercise(exercise);
    }

    /**Update exercises in the Add Exercises panel and Tab Two, Current Plan panel
     * @param type Type of exercises to show in Add Exercises panel
     * @param type2 Type of exercises to show in Tab Two, Current Plan panel
     */
    public void updateWorkouts(String type, String type2){
        try {
            model.updateWorkout(type, type2);
        }catch(Exception e){e.printStackTrace();}
    }

    /**Import selected workout from database*/
    public void importPlan(){
        model.clearPlan();
        Workout selectedWorkout = model.getSelectedWorkout();
        for(String exerciseNames: selectedWorkout.getExercisesInWorkout()){
            for(Exercise exercise: DBmanager.getExercises()){
                if(exercise.getName().equals(exerciseNames)){
                    model.addToPlan(exercise);
                }
            }
        }
    }

    /**Export selected workout to database
     * @param workoutName Name of workout to export to database
     */
    public void exportPlan(String workoutName){
        model.savePlanToDB(workoutName);
        model.notifyObservers();
    }

    /**Set selected exercise from Add Exercise panel
     * @param exercise Exercise selected from Add Exercise panel
     */
    public void setSelectedExercise(Exercise exercise){
        ExerciseSettings.setMult(exercise.getCalories());
        model.setSelectedExercise(exercise);
    }

    /**Set selected workout from Import Workout drop down menu
     * @param workout Selected workout from Import Workout drop down menu
     */
    public void setSelectedWorkout(Workout workout){model.setSelectedWorkout(workout);}

    /**Set the number of repetitions to perform for selected exercise
     * @param numberOfReps Number of repetitions to perform
     */
    public void setReps(int numberOfReps){
        model.setReps(numberOfReps);
        model.notifyObservers();
    }

    /**Adjust plan metrics according repetitions
     * @param nameOfWorkout Name of workout to adjust metrics for
     */
    public void adjustPlanMetrics(String nameOfWorkout){
        int totalTime = 0;
        int totalCal = 0;
        String maxType = "N/A";

        Workout selectedWorkout = null;

        for (Workout workout: DBmanager.getWorkouts()) {
            if(workout.getName().equals(nameOfWorkout)){
                selectedWorkout = workout;
            }
        }

        ArrayList<String> typeOccurrences = new ArrayList<>(); //every type token in a list

        for(String exerciseName: selectedWorkout.getExercisesInWorkout()){
            int reps = selectedWorkout.getReps().get(exerciseName);  // number of reps stored for that exercise
            Exercise exerciseAsObjectFromName = null;
            for(Exercise exercise: DBmanager.getExercises()){
                if(exerciseName.equals(exercise.getName())) {
                    exerciseAsObjectFromName = exercise;
                }
            }
            typeOccurrences.add(exerciseAsObjectFromName.getType());
            int time = reps / 5;
            int calPerMin = exerciseAsObjectFromName.getCalories();
            totalCal += (calPerMin * time);
            totalTime += time;
        }
        model.setTotalTime(totalTime);
        model.setTotalCalBurnt(totalCal);

        //now for the type occurrences calculation
        HashMap<String,Integer> dictOfOccurrences = new HashMap<>();
        for(String type: typeOccurrences){
            try{
                dictOfOccurrences.put(type,dictOfOccurrences.get(type) + 1);
            } catch(Exception ex){
                dictOfOccurrences.put(type,1);
            }
        }

        //whichever has the highest number stays
        //key is the name of the type of workout
        for(String key: dictOfOccurrences.keySet()){
            if(!dictOfOccurrences.keySet().contains(maxType)){
                maxType = key;
            } else if(dictOfOccurrences.get(key) > dictOfOccurrences.get(maxType)){
                maxType = key;
            }
        }
        model.setMainMuscleGroup(maxType);

        model.notifyObservers();
    }
}
