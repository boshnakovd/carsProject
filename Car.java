import java.sql.*;
import java.util.ArrayList;
import java.util.List;



class Car implements Displayable, DatabaseOperations {
    private String brand;
    private String model;
    private int year;
    private double price;
    private FuelType fuel;
    private double engineCapacity;
    private String color;

    public Car(String brand, String model, int year, double price, FuelType fuel, double engineCapacity, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.fuel = fuel;
        this.engineCapacity = engineCapacity;
        this.color = color;
    }
    // Getters and Setters
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

    public FuelType getFuel() {
        return fuel;
    }

    public void setFuel(FuelType fuel) {
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
    @Override
    public void displayInfo() {
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Price: " + price);
        System.out.println("Fuel: " + fuel);
        System.out.println("Engine Capacity: " + engineCapacity + "L");
        System.out.println("Color: " + color);

        if (year <= 1990) {
            System.out.println("This car is retro");
        } else if (year <= 2015) {
            System.out.println("This car is old");
        } else {
            System.out.println("This is a new car");
        }

        if (price <= 10000) {
            System.out.println("Low-end car");
        } else if (price <= 20000) {
            System.out.println("Mid-class car");
        } else {
            System.out.println("High-class car");
        }

        switch (fuel) {
            case DIESEL:
                System.out.println("The car is with a diesel engine");
                break;
            case PETROL:
                System.out.println("The car is with a petrol engine");
                break;
            case METHANE:
                System.out.println("The car is with a methane engine");
                break;
            case LPG:
                System.out.println("The car is with LPG");
                break;
            case ELECTRIC:
                System.out.println("The car is with an electric motor");
                break;
            default:
                System.out.println("Unknown fuel type");
                break;
        }
    }

    @Override
    public void insertIntoDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/carinfo";
            String username = "root";
            String password = "1902";

            Connection connection = DriverManager.getConnection(url, username, password);

            String insertQuery = "INSERT INTO cars (brand, model, year, price, fuel, engine_capacity, color) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, model);
            preparedStatement.setInt(3, year);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, fuel.name());
            preparedStatement.setDouble(6, engineCapacity);
            preparedStatement.setString(7, color);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            System.out.println("Car record inserted into the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromDatabase() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/carinfo";
            String username = "root";
            String password = "1902";

            Connection connection = DriverManager.getConnection(url, username, password);

            String deleteQuery = "DELETE FROM cars WHERE brand = ? AND model = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, model);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            System.out.println("Car record deleted from the database.");

        } catch (SQLException e) {
            throw new SQLException("Unable to find the database");
        }
    }

    @Override
    public List<Car> getAllFromDatabase() {
        List<Car> carList = new ArrayList<>();

        try {
            String url = "jdbc:mysql://localhost:3306/carinfo";
            String username = "root";
            String password = "1902";

            Connection connection = DriverManager.getConnection(url, username, password);

            String selectQuery = "SELECT * FROM cars";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                double price = resultSet.getDouble("price");
                FuelType fuel = FuelType.valueOf(resultSet.getString("fuel"));
                double engineCapacity = resultSet.getDouble("engine_capacity");
                String color = resultSet.getString("color");

                Car car = new Car(brand, model, year, price, fuel, engineCapacity, color);
                carList.add(car);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }
}
