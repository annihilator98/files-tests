package learning;


import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideTest {

    @Test
    void downloadTest() throws Exception{
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File file = $("#raw-url").download();
        try (InputStream is = new FileInputStream(file)) {
            byte[] fileContent = is.readAllBytes();
            String asString = new String(fileContent, StandardCharsets.UTF_8);
            assertThat(asString).contains("Contributions to JUnit 5");
        }
    }

    void uploadTest() {
        Selenide.open("https://fineuploader.com/demos.html");
        $("input[type='file']").uploadFromClasspath("files/1.txt");
        $$("div.qq-dialog-message-selector")
                .find(text(
                        "1.txt has an invalid extension. " +
                                "Valid extension(s): jpeg, jpg, gif, png."
                )).shouldBe(visible);
    }
}
