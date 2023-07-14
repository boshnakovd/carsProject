import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public void insertIntoDatabase() {
        try {
            // Establish a connection to the database
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/carinfo", "root", "1902");

            // Create the SQL insert statement
            String insertQuery = "INSERT INTO cars (brand, model, year, price, fuel, engine_capacity) VALUES (?, ?, ?, ?, ?, ?)";

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Set the parameter values
            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, model);
            preparedStatement.setInt(3, year);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, fuel);
            preparedStatement.setDouble(6, engineCapacity);

            // Execute the statement
            preparedStatement.executeUpdate();

            // Close the resources
            preparedStatement.close();
            connection.close();

            System.out.println("Car record inserted into the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all car records from the database
    public static List<Car> getAllFromDatabase() {
        List<Car> carList = new ArrayList<>();

        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name", "username", "password");

            // Create the SQL select statement
            String selectQuery = "SELECT * FROM cars";

            // Prepare the statement
            Statement statement = connection.createStatement();

            // Execute the query
            ResultSet resultSet = statement.executeQuery(selectQuery);

            // Process the result set
            while (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                double price = resultSet.getDouble("price");
                String fuel = resultSet.getString("fuel");
                double engineCapacity = resultSet.getDouble("engine_capacity");

                Car car = new Car(brand, model, year, price, fuel, engineCapacity);
                carList.add(car);
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }

    // Delete a car record from the database
    public void deleteFromDatabase() {
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name", "username", "password");

            // Create the SQL delete statement
            String deleteQuery = "DELETE FROM cars WHERE brand = ? AND model = ?";

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

            // Set the parameter values
            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, model);

            // Execute the statement
            preparedStatement.executeUpdate();

            // Close the resources
            preparedStatement.close();
            connection.close();

            System.out.println("Car record deleted from the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}






