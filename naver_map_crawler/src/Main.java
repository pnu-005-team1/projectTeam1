import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class Main {
    static String Naver_map_URL = "https://map.naver.com";
    public static void crawler () throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get(Naver_map_URL);
    }

    public static void main(String[] args) {

        try {
            crawler();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
