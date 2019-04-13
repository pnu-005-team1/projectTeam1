import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Daum {

	static String Daum_map_URL = "https://map.kakao.com";
	static String busan = "�λ�";
	static String[] busan_gu = { "������", "������", "����", "����", "������", "����", "�ϱ�", "���", "���ϱ�", "����", "������", "������", "������",
			"�߱�", "�ش�뱸", "���屺" };
	static String[] menu = { "������" };
	static ArrayList<String> all = new ArrayList<>();

	static ArrayList<String> restaurant = new ArrayList<>();

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
							String a = driver.findElement(By.cssSelector("#info\\2e search\\2e place\\2e list > li:nth-child("+ j + ") > div.head_item.clickArea > strong > a.link_name")).getText();
							String b = driver.findElement(By.cssSelector("#info\\2e search\\2e place\\2e list > li:nth-child("+ j +") > div.info_item > div.addr > p:nth-child(1)")).getText();

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
		//1��° A �ּ� #info\2e search\2e place\2e list > li:nth-child(1) > div.info_item > div.addr > p:nth-child(1)
		//1��° B �ּ� #info\2e search\2e place\2e list > li:nth-child(2) > div.info_item > div.addr > p:nth-child(1)
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
					"C:\\Users\\ghkdq\\Desktop\\chromedriver_win32\\chromedriver.exe");
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
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

		getDadd(driver);
		System.out.println("Finish");

	}

	public static void main(String[] args) {

		try {
			crawler();
			System.out.println(restaurant);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
