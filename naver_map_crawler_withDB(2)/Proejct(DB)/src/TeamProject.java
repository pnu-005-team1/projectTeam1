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
import java.util.ArrayList;
import java.util.HashSet;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class TeamProject {
	static Connection connection = null;
	static String Url = "jdbc:postgresql://localhost/";
	static String Username = ""; //Postgresql id
	static String Passward = ""; //Postgresql passward
    static String Naver_map_URL = "https://map.naver.com";
    static String busan = "�λ� ";
    static String[] busan_gu = {"������"};
    static String[] menu = {"���"};

    static HashSet restaurant = new HashSet();
    

    public static void getNadd(WebDriver driver) {
        try {
            for (int i = 1; i < 11; i++) {//��ġ ã�Ƽ� �ؽ�set�� ����.
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
            searchBar.sendKeys(busan + gu); //�λ��� ���� �˻��Ͽ� ��ġ�� �����Ѵ�.
            searchButton.click();
            long time1 = System.currentTimeMillis();
            while(System.currentTimeMillis()-1000>time1){}

            fix_map.click();
            for(String food:menu){
                searchBar.clear();
                searchBar.clear();
                searchBar.sendKeys(food);   //������ �˻��Ѵ�.
                searchButton.click();
                long time2 = System.currentTimeMillis();
                while(System.currentTimeMillis()-1000>time2){}

                for(int j=0; j<6; j++) {
                    try {//10���ް� 1~5������ ����
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
                            driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/div/div/a[5]")).click();//������ �ѱ��
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
        driver.quit();
    }

    public static void menu_get() throws IOException{
		
		
    	System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get(Naver_map_URL);

        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"search-input\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/fieldset/button"));

        Object[] rawdata = restaurant.toArray();

        String strFirstWindowHandle = driver.getWindowHandle();
        for(int i = 0; i < rawdata.length; i++){
            //���ڿ� ó��
            String temp = rawdata[i].toString();
            //System.out.println(temp);
            String name = temp.substring(0, temp.indexOf("##"));
            String address = temp.substring(temp.indexOf("##")+2, temp.length());
            //System.out.println(name);
            //System.out.println(address);


            searchBar.clear();
            searchBar.sendKeys(name);   //�Ĵ��� �˻��Ѵ�.
            searchButton.click();

            //��� ��ٸ���.
            long time = System.currentTimeMillis();
            while(System.currentTimeMillis()-1000>time){}

            try {
                driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li[1]/div[1]/dl/dt/a/b")).click();
                driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li[1]/div[2]/ul/li[4]/a")).click();
            }
            catch (Exception e){
                try{
                    driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li/div[1]/dl/dt/a/b")).click();
                    driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li/div[2]/ul/li[4]/a")).click();
                }
                catch (Exception e2){
                    driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li[1]/div[1]/dl/dt/a")).click();
                    driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li[1]/div[2]/ul/li[4]/a")).click();
                }
            }
            //��� ��ٸ���.
            long time3 = System.currentTimeMillis();
            while(System.currentTimeMillis()-1000>time3){}
            Object[] setWindowHandles = driver.getWindowHandles().toArray();
            try{
                driver.switchTo().window(setWindowHandles[1].toString());
            }
            catch (Exception e){
                continue;
            }
            driver.get(driver.getCurrentUrl() + "&tab=menu");

            HashSet menuNprice = new HashSet();
            try{    //���θ޴� ũ�Ѹ�
                for(int index = 1; index<10; index++){
                    String menu1 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/div/a["+index+"]/div[2]/div[1]/text()")).getText();
                    String price1 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/div/a["+index+"]/div[2]/div[2]")).getText();
                    menuNprice.add(menu1+" $"+price1);
                }
            }
            catch (Exception e){
            }
            for(int j=0; j<1000000; j++){}    //��� ��ٸ���.
            try{    //�Ϲݸ޴� ũ�Ѹ�
                for(int index = 1; index<10; index++){
                    String menu1 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/ul["+index+"]/li[1]/div/div[2]/div")).getText();
                    String price1 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/ul["+index+"]/li[1]/div/div[1]")).getText();
                    menuNprice.add(menu1+" $"+price1);
                    String menu2 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/ul["+index+"]/li[2]/div/div[2]/div")).getText();
                    String price2 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/ul["+index+"]/li[2]/div/div[1]")).getText();
                    menuNprice.add(menu2+" $"+price2);
                }
            }
            catch (Exception e){
            }
            //####################################################################
            Object[] menus = menuNprice.toArray();
            for(int j = 0; j < menus.length; j++){
                temp = temp + "##" + menus[j].toString();
            }
            
            //HashSet restaurantNmenu = new HashSet();
            //restaurantNmenu.add(temp);
            
           try {
				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30);
				String str = temp;
				String[] array = str.split("##");
				String sql = "insert into store(Name, Address, Menu)";
				sql = sql + " values('" + array[0] + "', '" + array[1] + "', '" + menuNprice + "')";
				statement.executeUpdate(sql);	

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("NULL");
			}
            //��ȯ�Ǿ��� ���� �ݴ´�.
            driver.close();
            //System.out.println(temp);
            driver.switchTo().window(strFirstWindowHandle);
        }
    }
    
    public static void main(String[] args) {
    	try {
			connection = DriverManager.getConnection(Url, Username, Passward);
			System.out.println("Successfully connected.");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("drop table if exists store");
			statement.executeUpdate("create table store(Name char(100), Address char(100), Menu char(1000))");
			
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
            crawler();
            menu_get();  
            //System.out.println(restaurant);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}