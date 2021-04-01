public class Exercise{

    private String name;
    private String calories;
    private String type;

    /**Default Constructor*/
    public Exercise(){}

    /**Parameterized Constructor
     * @param name Name of this exercise
     * @param calories Number of calories this exercise burns per minute
     * @param type Type of this exercise
     */
    public Exercise(String name, String calories, String type){
        this.name = name;
        this.calories = calories;
        this.type = type;
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
    public String getCalories() {
        return calories;
    }

    /**Gets exercise type
     * @return The type of this exercise
     */
    public String getType() {
        return type;
    }
}
