package ru.netology.data;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.*;

class DataTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginActiveUser() {
        Configuration.holdBrowserOpen = true;
        UserInfo user = DataGenerator.Registration.generateUser("active");
        sendRequest(user);
        $(".input__box>[type='text']").setValue(user.getLogin());
        $(".input__box>[type='password']").setValue(user.getPassword());
        $(".button").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginBlockedUser() {
        Configuration.holdBrowserOpen = true;
        UserInfo user = DataGenerator.Registration.generateUser("blocked");
        sendRequest(user);
        $(".input__box>[type='text']").setValue(user.getLogin());
        $(".input__box>[type='password']").setValue(user.getPassword());
        $(".button").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }
    @Test
    void shouldWrongLogin() {
        Configuration.holdBrowserOpen = true;
        UserInfo user = DataGenerator.Registration.generateUser("active");
        sendRequest(user);
        var anotherLogin = generateLogin();
        $(".input__box>[type='text']").setValue(anotherLogin);
        $(".input__box>[type='password']").setValue(user.getPassword());
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
    @Test
    void shouldWrongPassword() {
        Configuration.holdBrowserOpen = true;
        UserInfo user = DataGenerator.Registration.generateUser("active");
        sendRequest(user);
        var anotherPassword = generatePassword();
        $(".input__box>[type='text']").setValue(user.getLogin());
        $(".input__box>[type='password']").setValue(anotherPassword);
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

}