package stepDefition;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import hookshr.Hrhooks;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HRStepDefinition extends Hrhooks
{
    Connection connection=null;
    Statement statement=null;
    ResultSet resultSet=null;
    static WebDriver driver;
    List<WebElement> departmentname;
    List<String> count;
    List<String> city;
    List<String> employee_Table;
    List<String> dataBase_Employee;
    List<String> department_Name = new ArrayList<>();
    List<String> dataBase_DepartmentName = new  ArrayList<>();
    Map<String,String> Database_City_data=new HashMap<String,String>();
    Map<String,String> Table_City_data=new HashMap<String,String>();
    int rowCount;
    int columnCount;

    public HRStepDefinition()
    {
        driver=Hooks.driver;
        connection=Hooks.connection;

    }

    @Given("^User navigate to given url$")
    public void user_navigate_to_given_url() throws Throwable
    {
        WebDriverManager.chromedriver().setup();
        driver  =new ChromeDriver();

        driver.get("http://databasetesting.s3-website-us-west-2.amazonaws.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);



    }

    @When("^User select dropdown$")
    public void user_select_dropdown() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }

    @Then("^User selects data from DropDown$")
    public void user_selects_data_from_DropDown() throws Throwable {
        Class.forName("com.mysql.jdbc.Driver");
        //Open Connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR",
                "root", "");

        statement = connection.createStatement();
        resultSet = statement.executeQuery("select department_name from hr.departments where location_id not in (select location_id from hr.locations where country_id='US')");
        ResultSetMetaData metaData = resultSet.getMetaData();
        columnCount = metaData.getColumnCount();
        dataBase_DepartmentName.add(resultSet.getString(columnCount));
        System.out.println("From DataBase");
        while ((resultSet.next()))
        {
            System.out.println(resultSet.getString(1));
            dataBase_DepartmentName.add(resultSet.getString(1));
            rowCount++;

        }
        System.out.println(dataBase_DepartmentName);


    }

    @Then("^User needs to verify data with DataBase$")
    public void user_needs_to_verify_data_with_DataBase() throws Throwable {
       if (departmentname.size()==dataBase_DepartmentName.size()) {
           if (departmentname.equals(dataBase_DepartmentName)) {
               System.out.println("Verified department with data base");
           }
       }
       else
           System.out.println("Couldn't match with the database");

    }

    @When("^User select department from city$")
    public void user_select_department_from_city() throws Throwable {
        city=new ArrayList<String>();
        List<WebElement> listcount=driver.findElements(By.xpath("//table[@class='adap-table']//tbody//tr//td[1]"));
        List<WebElement> listcity=driver.findElements(By.xpath("//table[@class='adap-table']//tbody//tr//td[2]"));
        for(WebElement w:listcity)
        {
            String s1=w.getText();
            city.add(s1);
        }
        Collections.sort(city);
        System.out.println(city);
    }

    @Then("^User takes data from database$")
    public void user_takes_data_from_database() throws Throwable {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select count(d.department_name),l.city from hr.departments d join hr.locations l using(location_id) group by l.city;");
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        columnCount= rsMetaData.getColumnCount();
        count=new ArrayList<String>();

        System.out.println("From database") ;

        while (resultSet.next())
        {


            count.add(resultSet.getString(2));

            rowCount++;
        }
        Collections.sort(count);
        System.out.print(count);

    }

    @Then("^User needs to verify with the database$")
    public void user_needs_to_verify_with_the_database() throws Throwable {
        if (city.equals(count)) {
            System.out.println("Cities are same in both table and database");
        } else {
            for (int i = 0; i < count.size(); i++) {
                if (!(city.contains(count.get(i))))


                    System.out.println("THe city " + count.get(i) + "  :is missing in table");
            }

        }
    }

    @When("^User select employee with  (\\d+) largest salary$")
    public void user_select_employee_with_largest_salary(int arg1) throws Throwable {
        List<WebElement> emplist=driver.findElements(By.xpath("//table[@class='salary_employee']//tbody//tr//td"));
       employee_Table=new ArrayList<String>();
        for(WebElement w:emplist)
        {
            String s=w.getText();
            String[] s1=s.split(":");
           employee_Table.add(s1[1]);
        }
        System.out.println(employee_Table);

    }

    @Then("^USRE NEEDS TO VERIfy with database$")
    public void usre_NEEDS_TO_VERIfy_with_database() throws Throwable {
      statement = connection.createStatement();
        resultSet =statement.executeQuery("Select   Employee_id,concat(First_name,\" \" ,Last_name ) as Name, round(salary,0) as salary from hr.employees where salary = (select salary from  (select distinct salary from hr.employees order by  salary desc limit 3) x order by salary limit 1);");
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        columnCount = rsMetaData.getColumnCount();
        dataBase_Employee=new ArrayList<String>();
        while(resultSet.next())
        {
            for(int i=1;i<=columnCount;i++)
            {
                dataBase_Employee.add(resultSet.getString(i));
            }
            rowCount++;
        }
        System.out.println(dataBase_Employee);
        int count=0;


        for(int i=0;i<employee_Table.size();i++)
        {
            if(employee_Table.contains(dataBase_Employee.get(i)))

                count++;
            //System.out.println(count);

        }
        if(count==columnCount)
            System.out.println("Emplyee data verified");


    }


}
