import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;


import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryCardTest {

    @Test
    public void positiveSuiteTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        //$("[data-test-id=date] input").setValue("12.05.2023");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79673432288");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//div[contains(text(), 'Успешно')]").should(appear, Duration.ofSeconds(15));


    }
}
