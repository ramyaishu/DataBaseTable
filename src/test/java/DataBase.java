import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.sql.*;

public class DataBase
{
    Connection connection=null;
    Statement statement=null;
    ResultSet resultSet=null;
    WebDriver driver;
    int rowCount;
    int columnCount;

    @Test
    public void setUPConnection() throws ClassNotFoundException, SQLException {
        // Class.forName("mysql.jdbc.driver.MySqlDriver");
        Class.forName("com.mysql.jdbc.Driver");
        //Open Connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR",
                "root", "");

        statement = connection.createStatement();
    }

}
