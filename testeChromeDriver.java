
//import javafx.util.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class testeChromeDriver {
    public static String path = "C:\\Program Files\\Java\\105\\chromedriver.exe";
    public static Dimension newDimension = new Dimension(1300, 800);
    public static String urlUser = "https://www.ea.com/pt-br/fifa/ultimate-team/web-app/";
    protected static WebDriverWait wait;
    static ChromeDriver driver;
    static By elementoTexto;
    static By campoNome = By.xpath("//*[contains(@class, 'ut-text-input-control')]");
    static By campoJaMax = By.xpath("(//*[contains(@class, 'ut-number-input-control')])[4]");
    static By campoLanceMin = By.xpath("(//*[contains(@class, 'ut-number-input-control')])[1]");
    static By bind = By.xpath("//button[contains(text(),'Comprar agora por')]");
    static By bindWon = By.xpath("//li[contains(@class, 'listFUTItem has-auction-data selected won']");
    static By bindLost = By.xpath("//div[contains(@class, 'Notification negative']");
    static By nadaEncontrado = By.xpath("//*[contains(text(),'Nenhum resultado encontrado')]");

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", path);

        ChromeOptions options = new ChromeOptions();

        options.addArguments("use-fake-device-for-media-stream");
        options.addArguments("user-data-dir=C:\\teste\\Profile 1");
        options.addArguments("use-fake-ui-for-media-stream");
        //options.addArguments("profile-directory=C:\\teste");
        //options.setHeadless(true);
        //

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.manage().window().setSize(newDimension);

        driver.get(urlUser);
        String nome = "Luke Shaw";
        //ut-text-input-control
        System.out.println("Logado");
        clica("Transfe");
        System.out.println("clicou em transferir");
        TimeUnit.SECONDS.sleep(5);
        clica("Buscar no Mercado");
        System.out.println("clicou em buscar");
        clearAndSendKeys(campoNome,nome);
        System.out.println("escreveu nome");
        TimeUnit.SECONDS.sleep(5);
        clica(nome);
        System.out.println("clicou no nome");
        int valorJogador = 1300;
        int loop = valorJogador/100;
        int preco = 350;
        System.out.println("vamos ter " +loop+" rodadas e vamos pagar no máximo "+(preco+(50*loop)));
        for (int i = 0; i <= loop; i++) {
            System.out.println("rodada "+i+" e preco "+preco);
            clearAndSendKeys(campoJaMax, ""+preco);
            System.out.println("escreveu preço");
            TimeUnit.SECONDS.sleep(1);
            preco += 50;
            botao("Buscar");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("esperou 1 segundo e clicou em buscar jogador");
            if (returnIfElementExistsByTime(nadaEncontrado,450)) {
                voltar();
                System.out.println("clicou em voltar");
            } else if(returnIfElementExistsByTime(bind,250)){
                //TimeUnit.SECONDS.sleep(1);
                botao("Comprar agora");
                //TimeUnit.MILLISECONDS.sleep(250);
                clica("OK");
                if (returnIfElementExistsByTime(bindWon,2000)) {
                    System.out.println("COMPRADO POR "+preco);
                } else if (returnIfElementExistsByTime(bindLost,2000)) {
                    System.out.println("NÃO COMPROU POR ALGUM MOTIVO");
                }
                break;
            }
        }
        driver.quit();
    }
    public static void clica(String botão){
        //button[contains(text(),'Entrar')]
        WebElement element = null;
        elementoTexto = By.xpath("//*[contains(text(),'" + botão + "')]");
        element = waitForElement(elementoTexto);
        element.click();
    }
    public static void botao(String botão){
        //button[contains(text(),'Entrar')]
        WebElement element = null;
        elementoTexto = By.xpath("//button[contains(text(),'" + botão + "')]");
        element = waitForElement(elementoTexto);
        element.click();
    }
    public static void voltar(){
        //button[contains(text(),'Entrar')]
        WebElement element = null;
        elementoTexto = By.xpath("//button[contains(@class, 'ut-navigation-button-control')]");
        element = waitForElement(elementoTexto);
        element.click();
    }

    protected static WebElement waitForElement(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement element = driver.findElement(locator);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }
    protected static void clearAndSendKeys(By locator, CharSequence text) {
        WebElement webElement = waitForElement(locator);
        webElement.sendKeys(Keys.CONTROL + "a");
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(text);
    }
    protected static boolean returnIfElementExists(By locator) {
        boolean result = false;

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            result = true;
        } catch (Exception var4) {
            result = false;
        }

        return result;
    }
    protected static boolean returnIfElementExistsByTime(By locator, int time) {
        boolean result = false;
        wait = new WebDriverWait(driver,Duration.ofMillis(time));

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            result = true;
            wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        } catch (Exception e) {
        }
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        return result;
    }
}

