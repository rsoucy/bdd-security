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

public class MyApplicationToTest extends WebApplication implements ILogin {
     //@Override
     public void openLoginPage() {
        driver.get(Config.getInstance().getBaseUrl() + "universal-inbox/login");
        //verifyTextPresent("Login");
     }

       @Override
       public void login(Credentials credentials) {
           UserPassCredentials creds = new UserPassCredentials(credentials);
           driver.findElement(By.id("login_username")).clear();
           driver.findElement(By.id("login_username")).sendKeys(creds.getUsername());
           driver.findElement(By.id("login_password")).clear();
           driver.findElement(By.id("login_password")).sendKeys(creds.getPassword());
           driver.findElement(By.name("submit")).click();
       }

       @Override
     public boolean isLoggedIn() {
        if (driver.getPageSource().contains("outbox")) {
            return true;
        } else {
            return false;
        }
     }

     public void navigate() {
        openLoginPage();
        login(Config.getInstance().getUsers().getDefaultCredentials());
        //navigate the app
        driver.findElement(By.linkText("first last")).click();
        driver.findElement(By.cssSelector("span.menu-item-name")).click();
        driver.findElement(By.id("li_view_user_EditProfile_0-given-name")).clear();
        driver.findElement(By.id("li_view_user_EditProfile_0-given-name")).sendKeys("Roger");
        driver.findElement(By.id("li_view_user_EditProfile_0-family-name")).clear();
        driver.findElement(By.id("li_view_user_EditProfile_0-family-name")).sendKeys("Soucy");
        driver.findElement(By.id("li_widget_Button_3")).click();
     }
}
           
