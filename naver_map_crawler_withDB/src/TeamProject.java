import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.io.IOException;

public class TeamProject {
	
    static String Naver_map_URL = "https://map.naver.com";
    static String busan = "부산 ";
    static String[] busan_gu = {"강서구", "금정구", "남구", "동구", "동래구", "진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군"};
    static String[] menu = {"돈가스"};
    
    static HashSet restaurant = new HashSet();
    public static void getNadd(WebDriver driver) {
        try {
            for (int i = 1; i < 11; i++) {//위치 찾아서 해쉬set에 쓴다.
                String num = String.valueOf(i);
                String title = driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li["+num+"]/div[1]/dl/dt/a")).getText();
                String address = driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li["+num+"]/div[1]/dl/dd[1]")).getText();
                restaurant.add(title + "##" + address);
                
               }
        } catch (Exception e) {
            return;
        }
    }
    public static void crawler () throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get(Naver_map_URL);

        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"search-input\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/fieldset/button"));
        WebElement fix_map = driver.findElement(By.xpath("//*[@id=\"searchCurr\"]"));
        for(String gu:busan_gu){
            searchBar.clear();
            searchBar.sendKeys(busan + gu); //부산의 구를 검색하여 위치를 고정한다.
            searchButton.click();
            for(int i=0; i<1000000; i++){}    //잠시 기다린다.

            fix_map.click();
            for(String food:menu){
                searchBar.clear();
                searchBar.sendKeys(food);   //음식을 검색한다.
                searchButton.click();
                for(int i=0; i<1000000; i++){}    //잠시 기다린다.

                for(int j=0; j<6; j++) {
                    try {//10개받고 1~5페이지 연속
                        getNadd(driver);
                        for (int i = 2; i < 6; i++) {
                            if(j==0){
                                driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/div/div/a[1]")).click();
                                getNadd(driver);
                            }
                            String num = String.valueOf(i);
                            driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/div/div/a[" + num + "]")).click();
                            getNadd(driver);
                        }
                    } catch (Exception e) {
                        continue;
                    }
                    try {
                        if (j == 0) {
                            driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/div/div/a[5]")).click();//페이지 넘기기
                        } else {
                            driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/div/div/a[6]")).click();
                        }
                    }catch (Exception e){
                        continue;
                    }
                }
            }
            fix_map.click();
        }

    }

    public static void main(String[] args) {
    	Connection connection = null;
    	String Url = "jdbc:postgresql://localhost/";
		String Username = ""; //Postgresql id
		String Passward = ""; //Postgresql passward
		
        try {
            crawler();
            
            try {
            	int i = 0;
            	Iterator it = restaurant.iterator();
				connection = DriverManager.getConnection(Url, Username, Passward);
				System.out.println("Succesfully connected.");
				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30);

				statement.executeUpdate("drop table if exists store");
				statement.executeUpdate("create table store(Name char(100), Address char(100))");
				while(it.hasNext()) {
					String str = (String) it.next();
					String[] array = str.split("##");
					String sql = "insert into store(Name, Address)";
					sql = sql + " values('" + array[0] + "', '" + array[1] + "')";
					statement.executeUpdate(sql);	
				}
            
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            finally {
            	if(connection != null) {
            		try {
            			connection.close();
            		} catch(SQLException e) {
            			e.printStackTrace();
            		}
            	}
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}