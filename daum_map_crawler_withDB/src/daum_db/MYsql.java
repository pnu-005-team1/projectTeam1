package daum_db;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MYsql {

	static String Daum_map_URL = "https://map.kakao.com";
	static String busan = "부산";
	static String[] busan_gu = { "강서구", "금정구", "남구", "동구", "동래구", "진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구",
			"중구", "해당대구", "기장군" };
	static String[] menu = { "돈가스" };
	static ArrayList<String> all = new ArrayList<>();

	static ArrayList restaurant = new ArrayList();

	private static void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	// click후 그 리스트에 있는 15개의 attribute를 가져오는 함수
	public static void getDadd(WebDriver driver) {
		driver.findElement(By.cssSelector("#info\\2e search\\2e place\\2e more")).click();
		// page 넘어가는 term
		sleep();

		driver.findElement(By.cssSelector("#info\\2e search\\2e page\\2e no2")).click();
		sleep();
		// 1,2,3,4,5page 16개 line 읽는 함수.
		while (true) {
			try {
				for (int i = 1; i < 6; i++) {
					try {
						// page 넘어가는 시간
						driver.findElement(By.cssSelector("#info\\2e search\\2e page\\2e no" + i)).click();
						sleep();
						for (int j = 1; j < 16; j++) {
							String a = driver
									.findElement(By.cssSelector("#info\\2e search\\2e place\\2e list > li:nth-child("
											+ j + ") > div.head_item.clickArea > strong > a.link_name"))
									.getText();
							String b = driver
									.findElement(By.cssSelector("#info\\2e search\\2e place\\2e list > li:nth-child("
											+ j + ") > div.info_item > div.addr > p:nth-child(1)"))
									.getText();

							restaurant.add(a);
							restaurant.add(b);
							

						}
						System.out.println("next page");
					} catch (Exception e) {
						System.out.println("16개 X");
						return;
					}
					// 4번째 A
					// #info\2e search\2e place\2e list > li:nth-child(1) > div.head_item.clickArea
					// > strong > a.link_name
					// #info\2e search\2e place\2e list > li:nth-child(15) > div.head_item.clickArea
					// > strong > a.link_name
				}
				driver.findElement(By.cssSelector("#info\\2e search\\2e page\\2e next")).click();
				sleep();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		// 1번째 A 주소 #info\2e search\2e place\2e list > li:nth-child(1) > div.info_item >
		// div.addr > p:nth-child(1)
		// 1번째 B 주소 #info\2e search\2e place\2e list > li:nth-child(2) > div.info_item >
		// div.addr > p:nth-child(1)
		// #info\2e search\2e page\2e no3
		// #info\2e search\2e page\2e no4
		// 5번째 A
		// #info\2e search\2e place\2e list > li:nth-child(1) > div.head_item.clickArea
		// > strong > a.link_name
		// 16page
		// #info\2e search\2e page\2e no1
	}

	public static void crawler() throws IOException {
		// 셀레니움 셋팅
		if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
			System.setProperty("webdriver.chrome.driver", "C:\\\\Windows\\\\System32\\\\chromedriver.exe");
		}
		ChromeDriver driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);

		driver.get(Daum_map_URL);
		WebElement webElement = null;
		driver.findElement(By.xpath("/html/body/div[10]/div/div[2]/a")).click();
		driver.findElement(By.xpath("/html/body/div[10]/div/div/div/span")).click();

		// driver.switchTo().frame(driver.findElement(By.className("box_searchbar")));

		webElement = driver.findElement(By.id("search.keyword.query"));
		String city = "부산";
		for (String i : busan_gu) {
			all.add(city + " " + i + " " + menu[0]);
		}
		webElement.sendKeys(all.get(0));
		driver.findElement(By.xpath("//*[@id=\"search.keyword.submit\"]")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

		getDadd(driver);
		
		
		/*for (int i = 0; i < all.size(); i++) {
			// 부산 강서구 돈가스를 검색창에 보냄
			webElement.sendKeys(all.get(i));
			

			// 검색창 클릭
			driver.findElement(By.xpath("//*[@id=\"search.keyword.submit\"]")).click();
			// click후 term
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}

			getDadd(driver);
			driver.findElement(By.id("search.keyword.query")).clear();
		}*/
		System.out.println("Finish");

	}

	public static void main(String[] args) {
		
		Connection connection = null;
		String Url = "jdbc:postgresql://localhost/";
		String Username = "postgres";
		String Passward = "4755";

  try { crawler();
  
  		
  
  try {
  int i = 0; 
  Iterator it = restaurant.iterator(); 
  connection = DriverManager.getConnection(Url, Username, Passward);
  System.out.println("Succesfully connected.");
  Statement statement =connection.createStatement();
  statement.setQueryTimeout(30);
  
  statement.executeUpdate("drop table if exists store"); 
  statement.executeUpdate("create table store(Name char(100), Address char(100))");
  while(it.hasNext())
  { 
	  String str = (String) it.next(); 
	  String[] array =   str.split(","); 
	 
	  String sql = "insert into store(Name, Address)"; 
	 //+ "', '" + array[1] + "')"
	  sql = sql +  " values('" + array[0];
	  String str1 = (String) it.next(); 
	  String[] array1 =   str1.split(","); 
	  sql = sql + "', '" + array1[0] + "')";
	  
	  statement.executeUpdate(sql); 
  }
  
  } catch (SQLException e) { // TODO Auto-generated catch block
	  System.out.println(e);
	  } finally { if(connection != null) { try {
		  connection.close(); } catch(SQLException e) {
			  System.out.println(e); } } }
  
  } catch (IOException e) {System.out.println(e); }
} 
}
 
