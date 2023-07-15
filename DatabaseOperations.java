import java.sql.SQLException;
import java.util.List;

interface DatabaseOperations {
    void insertIntoDatabase();
    void deleteFromDatabase() throws SQLException;
    List<Car> getAllFromDatabase();
}
