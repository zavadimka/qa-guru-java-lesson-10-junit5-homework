package tests;

import data.Languages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Параметризованные тесты UI \"JavaRush\"")
public class JavaRushParametrizedTests extends TestBase {

    @BeforeEach
    void setUp() {
        open("https://javarush.com///");
    }

    static Stream<Arguments> sidebarGroupsShouldHaveCorrectNames() {
        return Stream.of(
                Arguments.of(
                        Languages.EN,
                        List.of("Java University",
                                "Learning",
                                "Community",
                                "Subscriptions"),
                        List.of("Course",
                                "Tasks",
                                "Surveys & Quizzes",
                                "Games",
                                "Help",
                                "Butt-kicking schedule",
                                "Internship",
                                "Users",
                                "Forum",
                                "Chat",
                                "Articles",
                                "Success stories",
                                "Activity",
                                "Reviews")),
                Arguments.of(
                        Languages.RU,
                        List.of("Java‑университет",
                                "Обучение",
                                "Сообщество",
                                "Подписки"),
                        List.of("Курс",
                                "Задачи",
                                "Опросы",
                                "Игры",
                                "Помощь",
                                "График пинков",
                                "Стажировка",
                                "Пользователи",
                                "Форум",
                                "Чат",
                                "Статьи",
                                "Истории успеха",
                                "Активности",
                                "Отзывы"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Для языка интерфейса \"{0}\" должны быть корректные названия элементов боковой панели")
    @Tag("Critical")
    void sidebarGroupsShouldHaveCorrectNames(Languages language, List<String> expectedSidebarGroupHead, List<String> expectedSidebarGroupItems) {
        $(".footer-lang .language-switcher").click();
        sleep(2_000);
        $$(".language-switcher-item__label").filter(visible).find(text(language.description)).click();
        $$(".sidebar-group-head:not(.sidebar-group-head--sale) .sidebar-group-head__link").filter(visible)
                .shouldHave(texts(expectedSidebarGroupHead));
        $$(".sidebar-group-items .sidebar-nav-item").filter(visible)
                .shouldHave(texts(expectedSidebarGroupItems));
    }
}
