package factorymethod;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

public class ChromeDriverCreator extends WebDriverCreator {
	@Override
	public WebDriver FactoryMethod() {
		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(
				new File("d:\\chromedriver_win32_2.22.exe")).build();
		try {
			service.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver = new ChromeDriver(service);
		return driver;
	}
}
