import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        // Create the table if it doesn't exist
        createTable();

        List<Car> carList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add a car");
            System.out.println("2. Search cars");
            System.out.println("3. Update/Delete cars");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCar(carList, scanner);
                    break;
                case 2:
                    searchCars(carList, scanner);
                    break;
                case 3:
                    updateOrDeleteCars(carList, scanner);
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }

    // Helper method to create the "cars" table in the database
    private static void createTable() {
        String url = "jdbc:mysql://localhost:3306/carinfo";
        String username = "root";
        String password = "1902";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS cars (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "brand VARCHAR(50) NOT NULL," +
                    "model VARCHAR(50) NOT NULL," +
                    "year INT NOT NULL," +
                    "price DECIMAL(10,2) NOT NULL," +
                    "fuel VARCHAR(50) NOT NULL," +
                    "engine_capacity DOUBLE NOT NULL," +
                    "color VARCHAR(50) NOT NULL" +
                    ")";

            Statement statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addCar(List<Car> carList, Scanner scanner) {
        System.out.println("Enter details for the car:");

        System.out.print("Brand: ");
        String brand = scanner.nextLine();

        System.out.print("Model: ");
        String model = scanner.nextLine();

        int year;
        while (true) {
            System.out.print("Year: ");
            year = scanner.nextInt();
            scanner.nextLine();

            if (year >= 1890) {
                break;
            } else {
                System.out.println("Invalid year. Please enter a year not less than 1890.");
            }
        }

        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Fuel: ");
        FuelType fuel = FuelType.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Engine capacity: ");
        double engineCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Color: ");
        String color = scanner.nextLine();

        Car car = new Car(brand, model, year, price, fuel, engineCapacity, color);
        car.insertIntoDatabase();
        carList.add(car);

        System.out.println("Car added successfully.");
    }

    private static void searchCars(List<Car> carList, Scanner scanner) {
        System.out.print("Enter the brand to search for: ");
        String searchBrand = scanner.nextLine();

        List<Car> searchResults = searchByBrand(carList, searchBrand);

        System.out.println("Search results:");
        if (searchResults.isEmpty()) {
            System.out.println("No cars found with the given brand.");
        } else {
            for (Car car : searchResults) {
                System.out.println("Car:");
                car.displayInfo();
                System.out.println();
            }
        }
    }

    private static List<Car> searchByBrand(List<Car> carList, String searchBrand) {
        List<Car> result = new ArrayList<>();

        for (Car car : carList) {
            if (car.getBrand().equalsIgnoreCase(searchBrand)) {
                result.add(car);
            }
        }

        return result;
    }

    private static void updateOrDeleteCars(List<Car> carList, Scanner scanner) {
        displayCarList(carList);

        System.out.print("Select a car by index to update or delete (0 to cancel): ");
        int selectedCarIndex = scanner.nextInt();
        scanner.nextLine();

        if (selectedCarIndex == 0) {
            System.out.println("Operation cancelled.");
            return;
        }

        if (selectedCarIndex < 1 || selectedCarIndex > carList.size()) {
            System.out.println("Invalid car index.");
            return;
        }

        Car selectedCar = carList.get(selectedCarIndex - 1);

        System.out.println("Selected Car:");
        selectedCar.displayInfo();

        System.out.println("Choose an operation:");
        System.out.println("1. Update car details");
        System.out.println("2. Delete car");
        System.out.println("3. Cancel");
        System.out.print("Enter your choice: ");
        int operationChoice = scanner.nextInt();
        scanner.nextLine();

        switch (operationChoice) {
            case 1:
                updateCarDetails(selectedCar, scanner);
                break;
            case 2:
                deleteCar(selectedCar, carList);
                break;
            case 3:
                System.out.println("Operation cancelled.");
                break;
            default:
                System.out.println("Invalid choice.");
        }

        System.out.println();
    }

    private static void displayCarList(List<Car> carList) {
        System.out.println("Car List:");
        for (int i = 0; i < carList.size(); i++) {
            System.out.println("Car " + (i + 1) + ":");
            carList.get(i).displayInfo();
            System.out.println();
        }
    }

    private static void updateCarDetails(Car car, Scanner scanner) {
        System.out.println("Update Car Details:");

        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        car.setBrand(brand);

        System.out.print("Model: ");
        String model = scanner.nextLine();
        car.setModel(model);

        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        car.setYear(year);

        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        car.setPrice(price);

        System.out.print("Fuel: ");
        FuelType fuel = FuelType.valueOf(scanner.nextLine().toUpperCase());
        car.setFuel(fuel);

        System.out.print("Engine Capacity: ");
        double engineCapacity = scanner.nextDouble();
        scanner.nextLine();
        car.setEngineCapacity(engineCapacity);

        System.out.print("Color: ");
        String color = scanner.nextLine();
        car.setColor(color);

        car.insertIntoDatabase();

        System.out.println("Car details updated.");
    }

    private static void deleteCar(Car car, List<Car> carList) {
        try {
            car.deleteFromDatabase();
            carList.remove(car);
            System.out.println("Car deleted.");
        } catch (SQLException e) {
            System.out.println("Unable to delete the car from the database.");
        }
    }
}