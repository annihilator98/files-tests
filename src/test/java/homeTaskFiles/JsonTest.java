package homeTaskFiles;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {
    ClassLoader classLoader = JsonTest.class.getClassLoader();

    @DisplayName("Json with Jackson test")
    @Test
    void jsonTest() throws Exception {
        //получаю json
        InputStream inputStream = classLoader.getResourceAsStream("testJson.json");

        //инициализация обьект ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        //вызываем readTree с джейсоном как параметром
        JsonNode jsonNode = objectMapper.readTree(new InputStreamReader(inputStream));

        //проверка содержимого
        assertThat(jsonNode.get("age").asInt()).isEqualTo(23);
        assertThat(jsonNode.get("name").asText()).isEqualTo("Hanna");
        assertThat(jsonNode.get("isGoodStudent").asBoolean()).isEqualTo(true);
        assertThat(jsonNode.findValue("passport").findValue("number").asInt()).isEqualTo(12345);
    }
}