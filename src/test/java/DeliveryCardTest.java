import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    public int getDay(long addDays) {
        return LocalDate.now().plusDays(addDays).getDayOfMonth();
    }

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }


    @Test
    public void positiveSuiteTest() {

        String planningDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79673432288");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=notification] .notification__content")
                .should(appear, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    public void invalidCityTest() {

        $("[data-test-id=city] input").setValue("Зеленодольск");
        $("[data-test-id=name] input").setValue("Иванов Павел");
        $("[data-test-id=phone] input").setValue("+79774223345");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));

    }

    @Test
    public void cityIsEmptyTest() {

        $("[data-test-id=name] input").setValue("Луцкий Сергей");
        $("[data-test-id=phone] input").setValue("+79689049834");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    public void validCityLatinTest() {

        $("[data-test-id=city] input").setValue("Moskva");
        $("[data-test-id=name] input").setValue("Романов Терентий");
        $("[data-test-id=phone] input").setValue("+79232211221");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));

    }

    @Test
    public void currentDatePlus2Test() {

        String planningDate = generateDate(2, "dd.MM.yyyy");


        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));

        $("[data-test-id=name] input").setValue("Леонов Лев");
        $("[data-test-id=phone] input").setValue("+79223344422");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));

    }

    @Test
    public void currentDatePlus4Test() {

        String planningDate = generateDate(4, "dd.MM.yyyy");


        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(String.valueOf(planningDate));
        $("[data-test-id=name] input").setValue("Леонов Лев");
        $("[data-test-id=phone] input").setValue("+79223344422");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=notification] .notification__content")
                .should(appear, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    public void dateIsEmptyTest() {

        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Леонов Лев");
        $("[data-test-id=phone] input").setValue("+79223344422");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(text("Неверно введена дата"));

    }

    @Test
    public void nameIsEmptyTest() {

        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=phone] input").setValue("+79223344422");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    public void nameLatinTest() {

        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=name] input").setValue("Leonov Igor");
        $("[data-test-id=phone] input").setValue("+7922338809");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithAHyphenTest() {

        String planningDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=name] input").setValue("Григорьев-Аполлонов Константин");
        $("[data-test-id=phone] input").setValue("+79223343355");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=notification] .notification__content")
                .should(appear, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    public void nameWithASpaceTest() {

        String planningDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=name] input").setValue("Григорьев Аполлонов Константин");
        $("[data-test-id=phone] input").setValue("+79223343355");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=notification] .notification__content")
                .should(appear, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    public void phoneWith8Test() {

        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=name] input").setValue("Григорьев Аполлонов Константин");
        $("[data-test-id=phone] input").setValue("89223343355");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void phoneIsEmptyTest() {

        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=name] input").setValue("Григорьев Аполлонов Константин");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    public void haveNotAgreementTest() {

        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=name] input").setValue("Григорьев Аполлонов Константин");
        $("[data-test-id=phone] input").setValue("+79672334355");
        $("button.button_view_extra").click();

        $("[data-test-id=agreement].input_invalid").exists();

    }

    @Test
    public void cityWithPopupTest() {

        String planningDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Мо");
        $(".menu-item:nth-child(3)").click();
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79673432288");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=notification] .notification__content")
                .should(appear, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    public void selectDateWithCalendarWithinAMonthTest() {

        long daysAmount = 7;
        String currentMonth = generateDate(0, "MM");
        String planningMonth = generateDate(daysAmount, "MM");
        String planningDate = String.valueOf(getDay(daysAmount));
        String planningFullDate = generateDate(daysAmount, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] button").click();

        if (currentMonth.equals(planningMonth)) {
            $$("[data-day]").findBy(text(planningDate)).click();
        } else {
            $("[data-step=1]").click();
            $$("[data-day]").findBy(text(planningDate)).click();
            ;
        }

        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79673432288");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=notification] .notification__content")
                .should(appear, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningFullDate));

    }

    @Test
    public void selectDateWithCalendarOutsideOfTheMonthTest() {

        long daysAmount = 14;
        String currentMonth = generateDate(0, "MM");
        String planningMonth = generateDate(daysAmount, "MM");
        String planningDate = String.valueOf(getDay(daysAmount));
        String planningFullDate = generateDate(daysAmount, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] button").click();

        if (currentMonth.equals(planningMonth)) {
            $$("[data-day]").findBy(text(planningDate)).click();
        } else {
            $("[data-step='1']").click();
            $$("[data-day]").findBy(text(planningDate)).click();
            ;
        }

        $("[data-test-id=name] input").setValue("Шукшин Иван");
        $("[data-test-id=phone] input").setValue("+79673432288");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra").click();

        $("[data-test-id=notification] .notification__content")
                .should(appear, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningFullDate));

    }

}
