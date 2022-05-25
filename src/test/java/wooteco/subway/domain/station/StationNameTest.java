package wooteco.subway.domain.station;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("지하철역 이름")
class StationNameTest {

    @DisplayName("이름은 공백이 될 수 없다.")
    @ParameterizedTest(name = "{index} 입력 : \"{0}\"")
    @ValueSource(strings = {"", " "})
    void createWithBlankName(String name) {
        assertThatThrownBy(() -> new StationName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지하철역 이름은 공백이 될 수 없습니다.");
    }
}
