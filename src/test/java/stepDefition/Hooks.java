package stepDefition;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.sql.*;

public class Hooks {
    static Connection connection=null;
    static Statement statement=null;
    ResultSet resultSet=null;
    static WebDriver driver;
    @BeforeTest
    public void beforeHooks() throws ClassNotFoundException, SQLException {
        WebDriverManager.chromedriver().setup();
        driver= new ChromeDriver();
        System.out.println("Test Started");
        // Class.forName("mysql.jdbc.driver.MySqlDriver");
        Class.forName("com.mysql.jdbc.Driver");
        //Open Connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR",
                "root", "");

        statement = connection.createStatement();


    }
    @AfterTest
    public void afterHooks() throws SQLException {
        connection.close();
    }
}
