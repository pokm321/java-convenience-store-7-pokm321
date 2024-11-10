package store;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.view.ViewErrors;

class ApplicationTest extends NsTest {
    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 5개",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개"
            );
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    //
    @Test
    void 영수증_출력_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-3],[에너지바-5],[사이다-10]", "Y", "Y");
            assertThat(output().replaceAll("\\s", ""))
                    .contains("행사할인-3,000")
                    .contains("멤버십할인-4,200")
                    .contains("내실돈15,800");
        });
    }

    @Test
    void 초과_수량_예외_테스트() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", "N", "N");
            assertThat(output()).contains(ViewErrors.INVALID_QUANTITY.getMessage());
        });
    }

    @Test
    void 프로모션_무료_상품_추가_테스트() {
        assertSimpleTest(() -> {
            runException("[초코바-1],[콜라-2],[초코바-2]", "Y", "Y");
            assertThat(output()).contains("현재 콜라은(는) 1개를").contains("현재 초코바은(는) 1개를");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"n", " Y", "\n", " ", "yes", "aaa", "_"})
    void 잘못된_답변형식_예외_테스트(String answer) {
        assertSimpleTest(() -> {
            runException("[컵라면-5]", answer);
            assertThat(output()).contains(ViewErrors.OTHERS.getMessage());
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
