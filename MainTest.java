import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class MainTest {
    private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private static final PrintStream originalOutput = System.out;

    @BeforeClass
    public static void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
        createTestTable();
    }

    @AfterClass
    public static void restoreStreams() {
        System.setOut(originalOutput);
        clearTestTable();
    }

    @Before
    public void resetOutputStream() {
        outputStream.reset();
    }

    private static void createTestTable() {
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

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "root");
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void clearTestTable() {
        String clearQuery = "DELETE FROM cars";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "root");
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(clearQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void provideInput(String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
    }

    @Test
    public void testAddCarAndSearchCars() {
        String input = "1\n" + "VW\n" + "Passat\n" + "2003\n" + "5600\n" + "diesel\n" + "1.9\n" + "blue\n" + "manual\n"
                + "245000\n" + "4\n" + "4\n" + "sedan\n" + "2\n" + "4\n";
        provideInput(input);

        Main.main(new String[]{});

        List<Car> cars = Main.getAllCarsFromDatabase();
        assertEquals(1, cars.size());

        Car addedCar = cars.get(0);
        assertEquals("VW", addedCar.getBrand());
        assertEquals("Passat", addedCar.getModel());
        assertEquals(2003, addedCar.getYear());
        assertEquals(5600, addedCar.getPrice(), 0.001);
        assertEquals("diesel", addedCar.getFuel());
        assertEquals(1.9, addedCar.getEngineCapacity(), 0.001);
        assertEquals("blue", addedCar.getColor());
        assertEquals(TransmissionType.MANUAL, addedCar.getTransmission());
        assertEquals(245000, addedCar.getMileage());
        assertEquals(4, addedCar.getNumberOfSeats());
        assertEquals(4, addedCar.getNumOfDoors());
        assertEquals(Category.SEDAN, addedCar.getCategory());

        input = "2\n";
        provideInput(input);
        Main.main(new String[]{});

        String expectedOutput = "List of cars:\n" +
                "Car{id=1, brand='VW', model='Passat', year=2003, price=5600.0, fuel='diesel', engineCapacity=1.9, color='blue', transmission=MANUAL, mileage=245000, numberOfSeats=4, numOfDoors=4, category=SEDAN}\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testUpdateCar() {
        String input = "1\n" + "VW\n" + "Passat\n" + "2003\n" + "5600\n" + "diesel\n" + "1.9\n" + "blue\n" + "manual\n"
                + "245000\n" + "4\n" + "4\n" + "sedan\n" + "3\n" + "2\n" + "2\n" + "hatchback\n" + "2\n";
        provideInput(input);

        Main.main(new String[]{});

        List<Car> cars = Main.getAllCarsFromDatabase();
        assertEquals(1, cars.size());

        Car addedCar = cars.get(0);
        assertEquals("VW", addedCar.getBrand());
        assertEquals("Passat", addedCar.getModel());
        assertEquals(2003, addedCar.getYear());
        assertEquals(5600, addedCar.getPrice(), 0.001);
        assertEquals("diesel", addedCar.getFuel());
        assertEquals(1.9, addedCar.getEngineCapacity(), 0.001);
        assertEquals("blue", addedCar.getColor());
        assertEquals(TransmissionType.MANUAL, addedCar.getTransmission());
        assertEquals(245000, addedCar.getMileage());
        assertEquals(4, addedCar.getNumberOfSeats());
        assertEquals(4, addedCar.getNumOfDoors());
        assertEquals(Category.SEDAN, addedCar.getCategory());

        input = "3\n" + "1\n" + "Mazda\n" + "3\n" + "2008\n" + "5200\n" + "petrol\n" + "1.6\n" + "gray\n" + "manual\n"
                + "198000\n" + "5\n" + "4\n" + "hatchback\n";
        provideInput(input);
        Main.main(new String[]{});

        cars = Main.getAllCarsFromDatabase();
        assertEquals(1, cars.size());

        Car updatedCar = cars.get(0);
        assertEquals("Mazda", updatedCar.getBrand());
        assertEquals("3", updatedCar.getModel());
        assertEquals(2008, updatedCar.getYear());
        assertEquals(5200, updatedCar.getPrice(), 0.001);
        assertEquals("petrol", updatedCar.getFuel());
        assertEquals(1.6, updatedCar.getEngineCapacity(), 0.001);
        assertEquals("gray", updatedCar.getColor());
        assertEquals(TransmissionType.MANUAL, updatedCar.getTransmission());
        assertEquals(198000, updatedCar.getMileage());
        assertEquals(5, updatedCar.getNumberOfSeats());
        assertEquals(4, updatedCar.getNumOfDoors());
        assertEquals(Category.HATCHBACK, updatedCar.getCategory());

        input = "2\n";
        provideInput(input);
        Main.main(new String[]{});

        String expectedOutput = "List of cars:\n" +
                "Car{id=1, brand='Mazda', model='3', year=2008, price=5200.0, fuel='petrol', engineCapacity=1.6, color='gray', transmission=MANUAL, mileage=198000, numberOfSeats=5, numOfDoors=4, category=HATCHBACK}\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testDeleteCar() {
        String input = "1\n" + "VW\n" + "Passat\n" + "2003\n" + "5600\n" + "diesel\n" + "1.9\n" + "blue\n" + "manual\n"
                + "245000\n" + "4\n" + "4\n" + "sedan\n" + "2\n";
        provideInput(input);

        Main.main(new String[]{});

        List<Car> cars = Main.getAllCarsFromDatabase();
        assertEquals(1, cars.size());

        Car addedCar = cars.get(0);
        assertEquals("VW", addedCar.getBrand());
        assertEquals("Passat", addedCar.getModel());
        assertEquals(2003, addedCar.getYear());
        assertEquals(5600, addedCar.getPrice(), 0.001);
        assertEquals("diesel", addedCar.getFuel());
        assertEquals(1.9, addedCar.getEngineCapacity(), 0.001);
        assertEquals("blue", addedCar.getColor());
        assertEquals(TransmissionType.MANUAL, addedCar.getTransmission());
        assertEquals(245000, addedCar.getMileage());
        assertEquals(4, addedCar.getNumberOfSeats());
        assertEquals(4, addedCar.getNumOfDoors());
        assertEquals(Category.SEDAN, addedCar.getCategory());

        input = "3\n" + "1\n" + "2\n";
        provideInput(input);
        Main.main(new String[]{});

        cars = Main.getAllCarsFromDatabase();
        assertEquals(0, cars.size());

        String expectedOutput = "List of cars:\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}

