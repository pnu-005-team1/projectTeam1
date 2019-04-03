import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.IOException;

public class Main {
    static String Naver_map_URL = "https://map.naver.com";
    String busan = "부산 "
    String[] busan_gu = {"강서구", "금정구", "남구", "동구", "동래구", "진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해당대구", "기장군"};
    String[] menu = {"돈가스"};

    public static void crawler () throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get(Naver_map_URL);

        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"search-input\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/fieldset/button"));
        WebElement fix_map = driver.findElement(By.xpath("//*[@id=\"searchCurr\"]"));
        for(String gu:busan_gu){
            searchBar.sendKeys(busan + gu); //부산의 구를 검색하여 위치를 고정한다.
            searchButton.click();
            for(int i=0; i<10000; i++){}    //잠시 기다린다.

            fix_map.click();
            for(String food:menu){
                searchBar.clear();
                searchBar.sendKeys(food);   //음식을 검색한다.
                searchButton.click();
                for(int i=0; i<10000; i++){}    //잠시 기다린다.

                //위치 찾아서 해쉬set에 쓴다.
            }
            fix_map.click();
        }

    }

    public static void main(String[] args) {

        try {
            crawler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
