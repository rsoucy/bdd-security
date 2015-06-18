package net.continuumsecurity.examples.ropeytasks;
import net.continuumsecurity.Config;
import net.continuumsecurity.Credentials;
import net.continuumsecurity.Restricted;
import net.continuumsecurity.UserPassCredentials;
import net.continuumsecurity.behaviour.ICaptcha;
import net.continuumsecurity.behaviour.ILogin;
import net.continuumsecurity.behaviour.ILogout;
import net.continuumsecurity.behaviour.IRecoverPassword;
import net.continuumsecurity.web.CaptchaSolver;
import net.continuumsecurity.web.WebApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class MyApplicationToTest extends WebApplication {

    public class MyApplicationToTest() {
         super();
    }

    public void navigate() {
        driver.get(Config.getInstance().getBaseUrl());
        driver.findElement(By.id("login_username")).clear();
        driver.findElement(By.id("login_username")).sendKeys("donotreply+1515@lifeimage.com”);
        driver.findElement(By.id("login_password")).clear();
        driver.findElement(By.id("login_password")).sendKeys("lifeimage1_new”);
        driver.findElement(By.name("submit")).click();
        driver.findElement(By.linkText("Log out")).click();
    }
}
