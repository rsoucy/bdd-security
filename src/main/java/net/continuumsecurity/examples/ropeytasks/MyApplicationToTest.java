public class MyApplicationToTest extends WebApplication {

    public class RopeyTasksApplication() {
         super();
    }

    public void navigate() {
        driver.get(baseUrl + "/universal-inbox/login”);
        driver.findElement(By.id("login_username")).clear();
        driver.findElement(By.id("login_username")).sendKeys("donotreply+1515@lifeimage.com”);
        driver.findElement(By.id("login_password")).clear();
        driver.findElement(By.id("login_password")).sendKeys("lifeimage1_new”);
        driver.findElement(By.name("submit")).click();
        driver.findElement(By.linkText("Log out")).click();
    }
}
