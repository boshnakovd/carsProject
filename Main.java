import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/carinfo?createDatabaseIfNotExist=true";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "1902";

    public static void main(String[] args) {
        createTable();

        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                System.out.println("Choose an option:");
                System.out.println("1. Add a car");
                System.out.println("2. Search cars");
                System.out.println("3. Update/Delete cars");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();
                scanner.useLocale(Locale.US); // Consume newline character

                switch (choice) {
                    case 1:
                        addCar(scanner);
                        break;
                    case 2:
                        searchCars();
                        break;
                    case 3:
                        updateOrDeleteCars(scanner);
                        break;
                    case 4:
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                System.out.println();
            } while (choice != 4);
        }
    }

    private static void createTable() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS cars (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "brand VARCHAR(255) NOT NULL," +
                    "model VARCHAR(255) NOT NULL," +
                    "year INT NOT NULL," +
                    "price DOUBLE NOT NULL," +
                    "fuel VARCHAR(50) NOT NULL," +
                    "engineCapacity DOUBLE NOT NULL," +
                    "color VARCHAR(50) NOT NULL," +
                    "transmission VARCHAR(50) NOT NULL," +
                    "mileage INT NOT NULL," +
                    "numberOfSeats INT NOT NULL," +
                    "numOfDoors INT NOT NULL," +
                    "category VARCHAR(50) NOT NULL" +
                    ")";

            statement.executeUpdate(createTableQuery);
            System.out.println("Table 'car' created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addCar(Scanner scanner) {
        System.out.println("Enter details for the car:");
        System.out.print("Brand: ");
        String brand = scanner.nextLine();

        System.out.print("Model: ");
        String model = scanner.nextLine();

        System.out.print("Year: ");
        int year = scanner.nextInt();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        scanner.nextLine(); // Consume newline character

        System.out.print("Fuel (diesel, petrol, methane, LPG, electric): ");
        String fuel = scanner.nextLine();
        System.out.print("Engine capacity: ");
        double engineCapacity = scanner.nextDouble();

        scanner.nextLine(); // Consume newline character

        System.out.print("Color: ");
        String color = scanner.nextLine();

        System.out.print("Transmission (manual, semi auto, auto, no transmission): ");
        TransmissionType transmission = TransmissionType.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Mileage: ");
        int mileage = scanner.nextInt();

        System.out.print("Number of seats: ");
        int numberOfSeats = scanner.nextInt();

        scanner.nextLine(); // Consume newline character

        System.out.print("Number of doors: ");
        int numOfDoors = scanner.nextInt();

        System.out.print("Category(SUV, hatchback, sedan, pickup, truck, minivan, van, cabrio, liftback, kombi): ");
        Category category = Category.valueOf(scanner.nextLine().toUpperCase());

        Car car = new Car(brand, model, year, price, fuel, engineCapacity, color, transmission, mileage, numberOfSeats, numOfDoors, category);
        addCarToDatabase(car);
        System.out.println("Car added successfully.");
    }

    private static void addCarToDatabase(Car car) {
        String insertQuery = "INSERT INTO cars (brand, model, year, price, fuel, engineCapacity, color, transmission, mileage, numberOfSeats, numOfDoors, category) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setDouble(4, car.getPrice());
            statement.setString(5, car.getFuel());
            statement.setDouble(6, car.getEngineCapacity());
            statement.setString(7, car.getColor());
            statement.setString(8, car.getTransmission().toString());
            statement.setInt(9, car.getMileage());
            statement.setInt(10, car.getNumberOfSeats());
            statement.setInt(11, car.getNumOfDoors());
            statement.setString(12, car.getCategory().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchCars() {
        String selectQuery = "SELECT * FROM cars";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            System.out.println("List of cars:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                double price = resultSet.getDouble("price");
                String fuel = resultSet.getString("fuel");
                double engineCapacity = resultSet.getDouble("engineCapacity");
                String color = resultSet.getString("color");
                String transmission = resultSet.getString("transmission");
                int mileage = resultSet.getInt("mileage");
                int numberOfSeats = resultSet.getInt("numberOfSeats");
                int numOfDoors = resultSet.getInt("numOfDoors");
                String category = resultSet.getString("category");

                Car car = new Car(brand, model, year, price, fuel, engineCapacity, color,
                        TransmissionType.valueOf(transmission.toUpperCase()), mileage, numberOfSeats, numOfDoors, Category.valueOf(category.toUpperCase()));
                car.setId(id);

                System.out.println(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateOrDeleteCars(Scanner scanner) {
        System.out.println("Enter car ID: ");
        int carId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String selectQuery = "SELECT * FROM cars WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

            selectStatement.setInt(1, carId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                double price = resultSet.getDouble("price");
                String fuel = resultSet.getString("fuel");
                double engineCapacity = resultSet.getDouble("engineCapacity");
                String color = resultSet.getString("color");
                String transmission = resultSet.getString("transmission");
                int mileage = resultSet.getInt("mileage");
                int numberOfSeats = resultSet.getInt("numberOfSeats");
                int numOfDoors = resultSet.getInt("numOfDoors");
                String category = resultSet.getString("category");

                Car car = new Car(brand, model, year, price, fuel, engineCapacity, color,
                        TransmissionType.valueOf(transmission.toUpperCase()), mileage, numberOfSeats, numOfDoors, Category.valueOf(category.toUpperCase()));
                car.setId(id);

                System.out.println("Car found:");
                System.out.println(car);

                System.out.println("Choose an option:");
                System.out.println("1. Update car");
                System.out.println("2. Delete car");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                if (choice == 1) {
                    updateCar(scanner, car);
                } else if (choice == 2) {
                    deleteCar(car);
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Car not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateCar(Scanner scanner, Car car) {
        System.out.println("Enter updated details for the car:");
        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character
        System.out.print("Fuel (diesel, petrol, methane, LPG, electric): ");
        String fuel = scanner.nextLine();
        System.out.print("Engine capacity: ");
        double engineCapacity = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Transmission (manual, semi auto, auto, no transmission): ");
        TransmissionType transmission = TransmissionType.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Mileage: ");
        int mileage = scanner.nextInt();
        System.out.print("Number of seats: ");
        int numberOfSeats = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Number of doors: ");
        int numOfDoors = scanner.nextInt();
        System.out.print("Category(SUV, hatchback, sedan, pickup, truck, minivan, van, cabrio, liftback, kombi): ");
        Category category = Category.valueOf(scanner.nextLine().toUpperCase());

        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPrice(price);
        car.setFuel(fuel);
        car.setEngineCapacity(engineCapacity);
        car.setColor(color);
        car.setTransmission(transmission);
        car.setMileage(mileage);
        car.setNumberOfSeats(numberOfSeats);
        car.setNumOfDoors(numOfDoors);
        car.setCategory(category);

        updateCarInDatabase(car);
        System.out.println("Car updated successfully.");
    }

    private static void updateCarInDatabase(Car car) {
        String updateQuery = "UPDATE cars SET brand = ?, model = ?, year = ?, price = ?, fuel = ?, " +
                "engineCapacity = ?, color = ?, transmission = ?, mileage = ?, numberOfSeats = ?, numOfDoors = ?, category = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setDouble(4, car.getPrice());
            statement.setString(5, car.getFuel());
            statement.setDouble(6, car.getEngineCapacity());
            statement.setString(7, car.getColor());
            statement.setString(8, car.getTransmission().toString());
            statement.setInt(9, car.getMileage());
            statement.setInt(10, car.getNumberOfSeats());
            statement.setInt(11, car.getNumOfDoors());
            statement.setString(12, car.getCategory().toString());
            statement.setInt(13, car.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCar(Car car) {
        String deleteQuery = "DELETE FROM cars WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.setInt(1, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Car> getAllCarsFromDatabase() {
        List<Car> cars = new ArrayList<>();

        String selectQuery = "SELECT * FROM cars";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                double price = resultSet.getDouble("price");
                String fuel = resultSet.getString("fuel");
                double engineCapacity = resultSet.getDouble("engineCapacity");
                String color = resultSet.getString("color");
                String transmission = resultSet.getString("transmission");
                int mileage = resultSet.getInt("mileage");
                int numberOfSeats = resultSet.getInt("numberOfSeats");
                int numOfDoors = resultSet.getInt("numOfDoors");
                String category = resultSet.getString("category");

                Car car = new Car(brand, model, year, price, fuel, engineCapacity, color,
                        TransmissionType.valueOf(transmission.toUpperCase()), mileage, numberOfSeats, numOfDoors, Category.valueOf(category.toUpperCase()));
                car.setId(id);

                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    public static void clearCarDatabase() {
        String clearQuery = "DELETE FROM cars";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(clearQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}