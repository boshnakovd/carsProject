public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String fuel;
    private double engineCapacity;
    private String color;
    private TransmissionType transmission;
    private int mileage;
    private int numberOfSeats;
    private String numOfDoors;

    public Car(String brand, String model, int year, double price, String fuel, double engineCapacity, String color,
               TransmissionType transmission, int mileage, int numberOfSeats, String numOfDoors) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.fuel = fuel;
        this.engineCapacity = engineCapacity;
        this.color = color;
        this.transmission = transmission;
        this.mileage = mileage;
        this.numberOfSeats = numberOfSeats;
        this.numOfDoors = numOfDoors;
    }

    // Getters and setters...


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getNumOfDoors() {
        return numOfDoors;
    }

    public void setNumOfDoors(String numOfDoors) {
        this.numOfDoors = numOfDoors;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", fuel='" + fuel + '\'' +
                ", engineCapacity=" + engineCapacity +
                ", color='" + color + '\'' +
                ", transmission=" + transmission +
                ", mileage=" + mileage +
                ", numberOfSeats=" + numberOfSeats +
                ", numOfDoors=" + numOfDoors +
                '}';
    }
}