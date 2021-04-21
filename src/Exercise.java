
/**Class that defines an exercise*/
public class Exercise{

    /**Name of exercise*/
    private String name;

    /**Calories burned per minute of exercise*/
    private String calories;

    /**Type of exercise*/
    private String type;

    /**Exercise details*/
    private String details;

    /**Default Constructor*/
    public Exercise(){}

    /**Parameterized Constructor
     * @param name Name of this exercise
     * @param calories Number of calories this exercise burns per minute
     * @param type Type of this exercise
     * @param details Details of this exercise
     */
    public Exercise(String name, String calories, String type, String details){
        this.name = name;
        this.calories = calories;
        this.type = type;
        this.details = details;
    }

    /**Gets exercise name
     * @return The name of this exercise
     */
    public String getName() {
        return name;
    }

    /**Gets number of calories burned per minute of this exercise
     * @return Number of calories burned per minute of this exercise
     */
    public int getCalories() {
        return Integer.parseInt(calories);
    }

    /**Gets exercise type
     * @return The type of this exercise
     */
    public String getType() {
        return type;
    }

    public String getDetails(){
        return details;
    }
}
