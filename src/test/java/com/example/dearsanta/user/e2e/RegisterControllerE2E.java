package com.example.dearsanta.user.e2e;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterControllerE2E {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "/path/to/your/chromedriver"); // Establece la ruta al ejecutable de ChromeDriver
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--headless"); // Quita esta línea si quieres ver el navegador en acción
        chromeOptions.addArguments("disable-gpu");
        chromeOptions.addArguments("--window-size=1920,1200");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }

        // Limpieza de datos de prueba
        User user = userRepository.findByEmail("testuser@example.com").orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Test
    public void shouldRegisterUserSuccessfully() {

        // Navegar a la página de registro
        driver.get("http://localhost:" + port + "/register");

        // Rellenar el formulario de registro
        driver.findElement(By.name("name")).sendKeys("Test User");
        driver.findElement(By.name("email")).sendKeys("testuser@example.com");
        driver.findElement(By.name("password")).sendKeys("password");

        // Enviar el formulario
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Verificar que se muestra el mensaje de éxito
        WebElement successMessage = driver.findElement(By.id("successMessage"));
        assertThat(successMessage.getText()).contains("Registration successful");

        // Verificar que el usuario fue creado en la base de datos
        User user = userRepository.findByEmail("testuser@example.com").orElse(null);
        assertThat(user).isNotNull();
    }

    @Test
    public void shouldFailToRegisterUserWithExistingEmail() {

        // Preparar datos de prueba
        User existingUser = new User();
        existingUser.setName("Existing User");
        existingUser.setEmail("existinguser@example.com");
        existingUser.setPassword("password");
        userRepository.save(existingUser);

        // Navegar a la página de registro
        driver.get("http://localhost:" + port + "/register");

        // Rellenar el formulario de registro con un email que ya existe
        driver.findElement(By.name("name")).sendKeys("Test User");
        driver.findElement(By.name("email")).sendKeys("existinguser@example.com");
        driver.findElement(By.name("password")).sendKeys("password");

        // Enviar el formulario
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Verificar que se muestra el mensaje de error
        WebElement errorMessage = driver.findElement(By.id("errorMessage"));
        assertThat(errorMessage.getText()).contains("User already exists");
    }
}
