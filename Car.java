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

        if(year <= 1990){
            System.out.println("This car is retro");
        } else if (year <= 2015){
            System.out.println("This car is old");
        } else {
            System.out.println("This is a new car");
        }

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
            String url = "jdbc:mysql://localhost:3306/carinfo";
            String username = "root";
            String password = "1902";

            Connection connection =
                    DriverManager.getConnection(url, username, password);


            String insertQuery = "INSERT INTO cars (brand, model, year, price, fuel, engine_capacity) VALUES (?, ?, ?, ?, ?, ?)";


            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);


            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, model);
            preparedStatement.setInt(3, year);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, fuel);
            preparedStatement.setDouble(6, engineCapacity);


            preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.close();

            System.out.println("Car record inserted into the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Car> getAllFromDatabase() {
        List<Car> carList = new ArrayList<>();

        try {
            String url = "jdbc:mysql://localhost:3306/carinfo";
            String username = "root";
            String password = "1902";

            Connection connection =
                    DriverManager.getConnection(url, username, password);

            String selectQuery = "SELECT * FROM cars";


            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery(selectQuery);


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


            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }


    public void deleteFromDatabase() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/carinfo";
            String username = "root";
            String password = "1902";

            Connection connection =
                    DriverManager.getConnection(url, username, password);

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
    public static List<Car> searchByBrand(String searchBrand) {
        List<Car> carList = new ArrayList<>();

        try {
            String url = "jdbc:mysql://localhost:3306/carinfo";
            String username = "root";
            String password = "1902";

            Connection connection = DriverManager.getConnection(url, username, password);

            String searchQuery = "SELECT * FROM cars WHERE brand LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, "%" + searchBrand + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

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

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }
}







