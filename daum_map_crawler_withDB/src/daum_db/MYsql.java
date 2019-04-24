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
	static String busan = "�λ�";
	static String[] busan_gu = { "������", "������", "����", "����", "������", "����", "�ϱ�", "���", "���ϱ�", "����", "������", "������", "������",
			"�߱�", "�ش�뱸", "���屺" };
	static String[] menu = { "������" };
	static ArrayList<String> all = new ArrayList<>();

	static ArrayList restaurant = new ArrayList();

	private static void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	// click�� �� ����Ʈ�� �ִ� 15���� attribute�� �������� �Լ�
	public static void getDadd(WebDriver driver) {
		driver.findElement(By.cssSelector("#info\\2e search\\2e place\\2e more")).click();
		// page �Ѿ�� term
		sleep();

		driver.findElement(By.cssSelector("#info\\2e search\\2e page\\2e no2")).click();
		sleep();
		// 1,2,3,4,5page 16�� line �д� �Լ�.
		while (true) {
			try {
				for (int i = 1; i < 6; i++) {
					try {
						// page �Ѿ�� �ð�
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
						System.out.println("16�� X");
						return;
					}
					// 4��° A
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
		// 1��° A �ּ� #info\2e search\2e place\2e list > li:nth-child(1) > div.info_item >
		// div.addr > p:nth-child(1)
		// 1��° B �ּ� #info\2e search\2e place\2e list > li:nth-child(2) > div.info_item >
		// div.addr > p:nth-child(1)
		// #info\2e search\2e page\2e no3
		// #info\2e search\2e page\2e no4
		// 5��° A
		// #info\2e search\2e place\2e list > li:nth-child(1) > div.head_item.clickArea
		// > strong > a.link_name
		// 16page
		// #info\2e search\2e page\2e no1
	}

	public static void crawler() throws IOException {
		// �����Ͽ� ����
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
		String city = "�λ�";
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
			// �λ� ������ �������� �˻�â�� ����
			webElement.sendKeys(all.get(i));
			

			// �˻�â Ŭ��
			driver.findElement(By.xpath("//*[@id=\"search.keyword.submit\"]")).click();
			// click�� term
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
 
