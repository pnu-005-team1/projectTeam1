import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class Main {
    static String Naver_map_URL = "https://map.naver.com";
    static String busan = "부산 ";
    static String[] busan_gu = {"강서구"};
    static String[] menu = {"돈까스"};

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
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get(Naver_map_URL);

        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"search-input\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/fieldset/button"));
        WebElement fix_map = driver.findElement(By.xpath("//*[@id=\"searchCurr\"]"));
        for(String gu:busan_gu){
            searchBar.clear();
            searchBar.sendKeys(busan + gu); //부산의 구를 검색하여 위치를 고정한다.
            searchButton.click();
            long time1 = System.currentTimeMillis();
            while(System.currentTimeMillis()-1000>time1){}

            fix_map.click();
            for(String food:menu){
                searchBar.clear();
                searchBar.sendKeys(food);   //음식을 검색한다.
                searchButton.click();
                long time2 = System.currentTimeMillis();
                while(System.currentTimeMillis()-1000>time2){}

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
        driver.quit();
    }

    public static void menu_get() throws IOException{
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get(Naver_map_URL);

        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"search-input\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/fieldset/button"));

        Object[] rawdata = restaurant.toArray();

        String strFirstWindowHandle = driver.getWindowHandle();
        for(int i = 0; i < rawdata.length; i++){
            //문자열 처리
            String temp = rawdata[i].toString();
            //System.out.println(temp);
            String name = temp.substring(0, temp.indexOf("##"));
            String address = temp.substring(temp.indexOf("##")+2, temp.length());
            //System.out.println(name);
            //System.out.println(address);


            searchBar.clear();
            searchBar.sendKeys(name);   //식당을 검색한다.
            searchButton.click();

            //잠시 기다린다.
            long time = System.currentTimeMillis();
            while(System.currentTimeMillis()-1000>time){}



            try {
                driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li[1]/div[1]/dl/dt/a/b")).click();
                driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li[1]/div[2]/ul/li[4]/a")).click();
            }
            catch (Exception e){
                driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li[1]/div[1]/dl/dt/a/b")).click();
                driver.findElement(By.xpath("//*[@id=\"panel\"]/div[2]/div[1]/div[2]/div[2]/ul/li[1]/div[2]/ul/li[4]/a")).click();
            }
            //잠시 기다린다.
            long time3 = System.currentTimeMillis();
            while(System.currentTimeMillis()-1000>time3){}
            Object[] setWindowHandles = driver.getWindowHandles().toArray();
            driver.switchTo().window(setWindowHandles[1].toString());
            driver.get(driver.getCurrentUrl() + "&tab=menu");

            HashSet menuNprice = new HashSet();
            try{    //메인메뉴 크롤링
                for(int index = 1; index<10; index++){
                    String menu1 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/div/a["+index+"]/div[2]/div[1]/text()")).getText();
                    String price1 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/div/a["+index+"]/div[2]/div[2]")).getText();
                    menuNprice.add(menu1+"##"+price1);
                }
            }
            catch (Exception e){
            }
            for(int j=0; j<1000000; j++){}    //잠시 기다린다.
            try{    //일반메뉴 크롤링
                for(int index = 1; index<10; index++){
                    String menu1 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/ul["+index+"]/li[1]/div/div[2]/div")).getText();
                    String price1 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/ul["+index+"]/li[1]/div/div[1]")).getText();
                    menuNprice.add(menu1+"##"+price1);
                    String menu2 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/ul["+index+"]/li[2]/div/div[2]/div")).getText();
                    String price2 = driver.findElement(By.xpath("//*[@id=\"panel02\"]/div/div/div[1]/ul["+index+"]/li[2]/div/div[1]")).getText();
                    menuNprice.add(menu2+"##"+price2);
                }
            }
            catch (Exception e){
            }
            //####################################################################
            Object[] menus = menuNprice.toArray();
            for(int j = 0; j < menus.length; j++){
                temp = temp + "$$" + menus[j].toString();
            }
            HashSet restaurantNmenu = new HashSet();
            restaurantNmenu.add(temp);
            //전환되었던 탭을 닫는다.
            driver.close();
            System.out.println(restaurantNmenu);
            driver.switchTo().window(strFirstWindowHandle);
        }


    }
    public static void main(String[] args) {

        try {
            crawler();
            menu_get();
            System.out.println(restaurant);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}