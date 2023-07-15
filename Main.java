import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Car> carList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.print("Enter the number of cars: ");
        int numCars = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numCars; i++) {
            System.out.println("Enter details for Car " + i + ":");
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
            String fuel = scanner.nextLine();

            System.out.print("Engine capacity: ");
            double engineCapacity = scanner.nextDouble();
            scanner.nextLine();

            Car car = new Car(brand, model, year, price, fuel, engineCapacity);
            carList.add(car);

            System.out.println();
        }


        for (int i = 0; i < carList.size(); i++) {
            System.out.println("Car " + (i + 1) + ":");
            Car car = carList.get(i);
            System.out.println("Brand: " + car.getBrand());
            System.out.println("Model: " + car.getModel());
            System.out.println("Year: " + car.getYear());
            System.out.println("Price: " + car.getPrice() + "$");
            System.out.println("Fuel: " + car.getFuel());
            System.out.println("Engine Capacity: " + car.getEngineCapacity() + "L");
            car.displayInfo();
            System.out.println();
        }

        for (Car car : carList) {
            car.insertIntoDatabase();
        }


        List<Car> carListFromDatabase = Car.getAllFromDatabase();


        for (Car car : carListFromDatabase) {
            System.out.println("Car:");
            car.displayInfo();
            System.out.println();
        }
        System.out.println("Enter the brand to search for:");
        String searchBrand = scanner.nextLine();

        List<Car> searchResults = Car.searchByBrand(searchBrand);

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
}
