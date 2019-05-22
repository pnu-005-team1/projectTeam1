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
			"�߱�", "�ؿ�뱸", "���屺" };
	static String[] menu = { "������" };
	static int count = 0;

	static int count2 = 0;
	static ArrayList<String> all = new ArrayList<>();

	static ArrayList<String> restaurant = new ArrayList<>();
	// ������ �޴��� ���ݵ��� (�޴�, ����, ......., "\\", �޴�, ����, .......,"\",...)�̷� ������ ����
	static ArrayList<String> menu2 = new ArrayList<>();

	// ������ ������ �󼼺��� Ŭ���� ���� page�� url List
	static ArrayList<String> places = new ArrayList<>();

	private static void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void getMenu(WebDriver driver2) {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
		driver.get(places.get(count2++));
		sleep();
		for (int i = 0; i < 1; i++) {

			// �޴� ��ġ��.
			for (int j = 0; j < 5; j++) {
				try {

					driver.findElement(By.xpath("//*[@id=\"mArticle\"]/div[2]/a/span[1]")).click();
					System.out.println("������ Ŭ��");
					sleep();

					// #mArticle > div.cont_menu > a �޴� ��ġ��.
					// //*[@id="mArticle"]/div[2]/a/span[1]
					// //*[@id="mArticle"]/div[2]/a/span[1]
					// #mArticle > div.cont_menu > a �޴�����
					// //*[@id="mArticle"]/div[2]/a/span[2]

					// 1��° �޴� #mArticle > div.cont_menu > ul > li.nophoto_type.menu_fst > div > span
					// 2��° �޴� #mArticle > div.cont_menu > ul > li:nth-child(2) > div > span
					// 1��° ���� #mArticle > div.cont_menu > ul > li.nophoto_type.menu_fst > div >
					// em.price_menu
					// 2��° ���� #mArticle > div.cont_menu > ul > li:nth-child(2) > div > em.price_menu
					// #mArticle > div.cont_menu > ul > li.nophoto_type.opened_last > div > span
					// #mArticle > div.cont_menu > ul > li:nth-child(21) > div > span
					// #mArticle > div.cont_menu > ul > li.nophoto_type.opened_last > div > span
					// #mArticle > div.cont_menu > ul > li.nophoto_type.opened_last > div >
					// em.price_menu
				} catch (Exception e) {
					System.out.println("�޴� ������ X");
				}
			}
			try {
				menu2.add(driver.findElement(By.cssSelector("#mArticle > div.cont_essential > div:nth-child(1) > div.place_details > div > h2")).getText());
				String a = driver
						.findElement(By
								.cssSelector("#mArticle > div.cont_menu > ul > li.nophoto_type.menu_fst > div > span"))
						.getText();
				String b = driver
						.findElement(By.cssSelector(
								"#mArticle > div.cont_menu > ul > li.nophoto_type.menu_fst > div > em.price_menu"))
						.getText();
				menu2.add(a);
				menu2.add(b);
				//menu2.add("#");
				for (int j = 2; j < 5/*30*/; j++) {
					a = driver.findElement(
							By.cssSelector("#mArticle > div.cont_menu > ul > li:nth-child(" + j + ") > div > span"))
							.getText();
					b = driver
							.findElement(By.cssSelector(
									"#mArticle > div.cont_menu > ul > li:nth-child(" + j + ") > div > em.price_menu"))
							.getText();
					//menu2.add(driver.findElement(By.cssSelector("#mArticle > div.cont_essential > div:nth-child(1) > div.place_details > div > h2")).getText());
					menu2.add(" ");
					menu2.add(a);
					menu2.add(b);
					//menu2.add("#");
					System.out.println("�޴��߰�");
					
				}
			} catch (Exception e) {
				System.out.println("�޴� X");
			}

			//System.out.println(menu2);
			menu2.add("\\");
			driver.quit();
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
						for (int j = 1; j < 16; j=j+5) {
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
							count++;

							String c = driver
									.findElement(By.cssSelector("#info\\.search\\.place\\.list > li:nth-child(" + j
											+ ") > div.info_item > div.contact.clickArea > a.moreview"))
									.getAttribute("href");
							places.add(c);

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

					// ���� 1��° A
					// #info\.search\.place\.list > li:nth-child(1) > div.rating.clickArea >
					// span.score > em
					// #info\.search\.place\.list > li:nth-child(2) > div.rating.clickArea >
					// span.score > em

					// �󼼺��� 1��° A
					// #info\.search\.place\.list > li:nth-child(1) > div.info_item >
					// div.contact.clickArea > a.moreview
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
			System.setProperty("webdriver.chrome.driver",
					"C:\\\\db\\\\chromedriver.exe");
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
		// �λ� ������ ������ click
		driver.findElement(By.xpath("//*[@id=\"search.keyword.submit\"]")).click();
		// click�� term
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

		getDadd(driver);
		
		
		System.out.println("Finish");
		
		for (int i = 0; i < count; i++) {
			getMenu(driver);
		}
		count = 0;
	




	}


public static void main(String[] args) {
		
		Connection connection = null;
		String Url = "jdbc:postgresql://localhost/";
		String Username = "postgres";
		String Passward = "4755";

  try { crawler();
  		
  		System.out.println(restaurant);
  		System.out.println(menu2);
  		
  
  try {
  int i = 0; 
  //Iterator it = restaurant.iterator(); 
  Iterator it = menu2.iterator();
  connection = DriverManager.getConnection(Url, Username, Passward);
  System.out.println("Succesfully connected.");
  Statement statement =connection.createStatement();
  statement.setQueryTimeout(30);
  
  statement.executeUpdate("drop table if exists store"); 
  //statement.executeUpdate("create table store(Name char(100), Address char(100))");
  statement.executeUpdate("create table store(Name char(100  ), Menu char(100), Price char(100))");
  /*while(it.hasNext())
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
  }*/
 while(it.hasNext())
  {
	  String str = (String) it.next();
	  String[] array = str.split(",");
	 
	  String sql = "insert into store(Name, Menu, Price)";
	 if(array[0] == "\\") 
	  {	 // sql=sql + "values('" +array[0] + "','" + "����" + "','" + "����" +"')";
		  continue;}
	  sql = sql +  " values('" + array[0];
	  
	  String str1 = (String) it.next(); 
	  String[] array1 =   str1.split(","); 
	  
	  if(array1[0] == "\\")
	  {
		  sql= sql + "','" + "no data" + "','" + "no data" +"')";
		  continue;
	  }
	  sql = sql + "', '" + array1[0] ;	  
	 
	  String str2 = (String) it.next();
	  String[] array2 = str2.split(",");
	  sql = sql + "', '" + array2[0] +"000"+"��"+ "')";
	  
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
 
