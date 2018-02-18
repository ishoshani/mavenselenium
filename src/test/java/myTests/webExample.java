package myTests;

	import java.io.File;
	import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
	import java.io.PrintWriter;
	import java.net.MalformedURLException;
	import java.net.URL;
	import java.util.ArrayList;
	import java.util.LinkedList;
	import java.util.List;
import java.util.Properties;
import java.util.Queue;
	import java.util.concurrent.TimeUnit;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.remote.DesiredCapabilities;
	import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.internal.WebElementToJsonConverter;
import org.testng.annotations.*;


	public class webExample {
	    String CLOUDURL;
		  String testName;
		  WebDriver driver;
		  File output;
		  PrintWriter writer;
		  Properties cloudProperties = new Properties();

		  
		  @BeforeMethod
		  @Parameters({"Browser","testnum"})
		  public void setup(String Browser, String testnum) throws IOException {
			  initCloudProperties();
			  CLOUDURL = cloudProperties.getProperty("url")+"/wd/hub";
			  DesiredCapabilities dc = new DesiredCapabilities();

			  if(Browser.equals("Firefox")) {
			  dc = DesiredCapabilities.firefox();
			  }
			  if(Browser.equals("Chrome")) {
				  dc = DesiredCapabilities.chrome();
				  }
			  if(Browser.equals("IE")) {
				  dc = DesiredCapabilities.internetExplorer();
			  }
			  if(Browser.equals("IOS")) {
				  dc = DesiredCapabilities.iphone();
			      dc.setCapability("platformName", "ios");    
			  }
			  if(Browser.equals("Android")) {
				  dc = DesiredCapabilities.android();
				  dc.setCapability("platformName", "android");
			  }

			  testName = "Get Table Test "+Browser;
			  dc.setCapability("accessKey", cloudProperties.getProperty("accessKey"));
		      dc.setCapability("generateReport", true);
		      dc.setCapability("testName", testName+testnum);
		      dc.setCapability("acceptInsecureCerts", true);
		      System.out.println(dc);
		      System.out.println(CLOUDURL);		  
		      this.driver = new RemoteWebDriver(new URL(CLOUDURL), dc);

		        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		  }
	
		  @Test
		  public void TitleTest() throws InterruptedException {
			  driver.get("https://experitest.com/");
		        Thread.sleep(10000);
		        System.out.println("title of page is: " + driver.getTitle());
		  }
		  @AfterMethod
		  public void teardown() {
			  driver.quit();
		  }
		  protected String getProperty(String property, Properties props) throws FileNotFoundException, IOException {
				if (System.getProperty(property) != null) {
					return System.getProperty(property);
				} else if (System.getenv().containsKey(property)) {
					return System.getenv(property);
				} else if (props != null) {
					return props.getProperty(property);
				}
				return null;
			}

			private void initCloudProperties() throws FileNotFoundException, IOException {
				File cloudPropertiesFile = new File("cloud.properties");
				if(cloudPropertiesFile.exists()) {
					FileReader fr = new FileReader("cloud.properties");
					cloudProperties.load(fr);
					fr.close();
				}else {
					cloudProperties.setProperty("url", System.getenv("url"));
					cloudProperties.setProperty("accessKey", System.getenv("accessKey"));
				}
			
			}
	}

