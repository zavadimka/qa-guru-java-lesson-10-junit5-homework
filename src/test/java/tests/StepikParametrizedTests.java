package tests;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Параметризованные тесты поиска курсов в \"Stepik\"")
public class StepikParametrizedTests extends TestBase {

    @BeforeEach
    void setUp() {
        open("https://stepik.org/catalog");
    }

    @ValueSource(strings = {
            "Python",
            "Java"
    })
    @ParameterizedTest(name = "Результат поискового запроса курсов по теме \"{0}\" должен быть непустым")
    @Tag("Critical")
    void coursesSearchQueryResultShouldNotBeEmpty(String searchQuery) {
        $(".search-form__input").setValue(searchQuery).pressEnter();
        $$(".course-cards__item").shouldBe(CollectionCondition.sizeGreaterThan(0));
    }

    @CsvFileSource(resources = "/test_data/courses_search_query_list.csv")
    @ParameterizedTest(name = "Для поискового запроса курсов по теме \"{0}\" в первой карточке должен быть курс \"{1}\"")
    @Tag("Major")
    void coursesSearchQueryResultShouldContainCourse(String searchQuery, String searchResult) {
        $(".search-form__input").setValue(searchQuery).pressEnter();
        $(".course-card__title").shouldHave(text(searchResult));
    }
}
