import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }


    @Test
    public void positiveSuiteTest() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79673432288");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//div[contains(text(), 'Успешно')]").should(appear, Duration.ofSeconds(15));

    }

    @Test
    public void invalidCityTest() {

        $("[data-test-id=city] input").setValue("Зеленодольск");
        $("[data-test-id=name] input").setValue("Иванов Павел");
        $("[data-test-id=phone] input").setValue("+79774223345");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//[data-test-id=city].input_invalid input__sub[contains(text(), 'Доставка в выбранный город недоступна')]");

    }

    @Test
    public void cityIsEmptyTest() {

        $("[data-test-id=name] input").setValue("Луцкий Сергей");
        $("[data-test-id=phone] input").setValue("+79689049834");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//[data-test-id=city].input_invalid input__sub[contains(text(), 'Поле обязательно для заполнения')]");

    }

    @Test
    public void validCityLatinTest() {

        $("[data-test-id=city] input").setValue("Moskva");
        $("[data-test-id=name] input").setValue("Романов Терентий");
        $("[data-test-id=phone] input").setValue("+79232211221");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $(withText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void currentDatePlus2Test() {

        LocalDate currentDate = LocalDate.now();
        LocalDate date = currentDate.plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);


        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(formattedDate));

        $("[data-test-id=name] input").setValue("Леонов Лев");
        $("[data-test-id=phone] input").setValue("+79223344422");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//[data-test-id=date].input_invalid input__sub[contains(text(), 'Заказ на выбранную дату невозможен')]");

    }

    @Test
    public void currentDatePlus4Test() {

        LocalDate currentDate = LocalDate.now();
        LocalDate date = currentDate.plusDays(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);


        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(formattedDate));
        $("[data-test-id=name] input").setValue("Леонов Лев");
        $("[data-test-id=phone] input").setValue("+79223344422");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//div[contains(text(), 'Успешно')]").should(appear, Duration.ofSeconds(15));

    }

    @Test
    public void dateIsEmptyTest() {

        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Леонов Лев");
        $("[data-test-id=phone] input").setValue("+79223344422");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//[data-test-id=date].input_invalid input__sub[contains(text(), 'Неверно введена дата')]");

    }

    @Test
    public void nameIsEmptyTest() {

        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=phone] input").setValue("+79223344422");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//[data-test-id=name].input_invalid input__sub[contains(text(), 'Поле обязательно для заполнения')]");

    }

    @Test
    public void nameLatinTest() {

        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=name] input").setValue("Leonov Igor");
        $("[data-test-id=phone] input").setValue("+7922338809");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//[data-test-id=name].input_invalid input__sub[contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]");

    }

    @Test
    public void nameWithAHyphenTest() {

        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=name] input").setValue("Григорьев-Аполлонов Константин");
        $("[data-test-id=phone] input").setValue("+79223343355");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//div[contains(text(), 'Успешно')]").should(appear, Duration.ofSeconds(15));

    }

    @Test
    public void nameWithASpaceTest() {

        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=name] input").setValue("Григорьев Аполлонов Константин");
        $("[data-test-id=phone] input").setValue("+79223343355");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//div[contains(text(), 'Успешно')]").should(appear, Duration.ofSeconds(15));

    }

    @Test
    public void phoneWith8Test() {

        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=name] input").setValue("Григорьев Аполлонов Константин");
        $("[data-test-id=phone] input").setValue("89223343355");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $x("//[data-test-id=phone].input_invalid input__sub[contains(text(), 'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]");


    }

    @Test
    public void haveNotAgreementTest() {

        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=name] input").setValue("Григорьев Аполлонов Константин");
        $("[data-test-id=phone] input").setValue("+79672334355");
        $("button.button_view_extra").click();

        $x("//[data-test-id=agreement].input_invalid");


    }



}
