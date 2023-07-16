public enum NumOfDoors {
    TWO("2"),
    FOUR("4");

    private final String description;

    NumOfDoors(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
