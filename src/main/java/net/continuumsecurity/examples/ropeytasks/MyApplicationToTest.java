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

public class MyApplicationToTest extends WebApplication implements ILogin,
        ILogout, IRecoverPassword {

    public MyApplicationToTest() {
        super();
    }

    @Override
    public void openLoginPage() {
        driver.get(Config.getInstance().getBaseUrl() + "/universal-inbox/login");
        verifyTextPresent("Login");
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

  // Convenience method
    public void login(String username, String password) {
        login(new UserPassCredentials(username, password));
    }

    @Override
    public boolean isLoggedIn() {
        if (driver.getPageSource().contains("Tasks")) {
            return true;
        } else {
            return false;
        }
    }

    public void viewProfile() {
        driver.findElement(By.linkText("Profile")).click();
    }

    @Restricted(users = {"bob", "admin"},
            sensitiveData = "Robert")
    public void viewProfileForBob() {
        viewProfile();
    }

    @Restricted(users = {"alice", "admin"},
            sensitiveData = "alice@continuumsecurity.net")
    public void viewProfileForAlice() {
        viewProfile();
    }

    @Restricted(users = {"admin"},
            sensitiveData = "User List")
    public void viewUserList() {
        driver.get(Config.getInstance().getBaseUrl() + "admin/list");
    }

    @Override
    public void logout() {
        driver.findElement(By.linkText("Logout")).click();
    }

    public void search(String query) {
        driver.findElement(By.linkText("Tasks")).click();
        driver.findElement(By.id("q")).clear();
        driver.findElement(By.id("q")).sendKeys(query);
        driver.findElement(By.id("search")).click();
    }

    public void navigate() {
        openLoginPage();
        login(Config.getInstance().getUsers().getDefaultCredentials());
        verifyTextPresent("Welcome");
        viewProfile();
        search("test");
    }

    /*
     * The details map will be created from the name and value attributes of the
     * <recoverpassword> tags defined for each user in the config.xml file.
     *
     * (non-Javadoc)
     *
     * @see
     * net.continuumsecurity.behaviour.IRecoverPassword#submitRecover(java.util.Map)
     */
    @Override
    public void submitRecover(Map<String, String> details) {
        driver.get(Config.getInstance().getBaseUrl() + "user/recover");
        driver.findElement(By.id("email")).sendKeys(details.get("email"));
        driver.findElement(By.xpath("//input[@value='Recover']")).click();
    }
    
    /*
        To enable CAPTCHA solving, there should be a deathbycaptcha.properties file in the project root with the format:
        type=DeathByCaptcha
        username=deathbycaptcha.com username
        password=my password
    @Override
	public WebElement getCaptchaImage() {
		return driver.findElement(By.id("recaptcha_challenge_image"));		
	}
	@Override
	public WebElement getCaptchaResponseField() {
		return driver.findElement(By.id("recaptcha_response_field"));
	}
	@Override
	public void setDefaultSolver() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("deathbycaptcha.properties"));
			setCaptchaSolver(new CaptchaSolver(this, props));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    */
}
