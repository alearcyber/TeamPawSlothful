public class Exercise{

    private String name;
    private String calories;
    private String type;

    public Exercise(String name, String calories, String type){
        this.name = name;
        this.calories = calories;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public String getCalories() {
        return calories;
    }


    public String getType() {
        return type;
    }
}
