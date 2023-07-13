class Car {
    private String brand;
    private String model;
    private int year;
    private double price;
    private String fuel;
    private double engineCapacity;

    public Car(String brand, String model, int year, double price, String fuel, double engineCapacity) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.fuel = fuel;
        this.engineCapacity = engineCapacity;
    }

    // Getters
    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public String getFuel() {
        return fuel;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    // Setters
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setEngineCapacity(double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public void displayInfo() {
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Price: " + price);
        System.out.println("Fuel: " + fuel);
        System.out.println("Engine Capacity: " + engineCapacity + "L");

        if (price <= 10000) {
            System.out.println("low-end car");
        } else if (price <= 20000) {
            System.out.println("mid-class car");
        } else {
            System.out.println("high-class car");
        }
        switch (fuel) {
            case "diesel":
                System.out.println("The car is with diesel engine");
                break;
            case "petrol":
                System.out.println("The car is with petrol engine");
                break;
            case "methane":
                System.out.println("The car is with methane engine");
                break;
            case "LPG":
                System.out.println("The car is with LPG");
                break;
            case "electric":
                System.out.println("The car is with electric motor");
                break;
            default:
                System.out.println("Unknown fuel type");
                break;
        }
    }


}


