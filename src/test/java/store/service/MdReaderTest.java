package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import store.domain.Products;

public class MdReaderTest {

    @Test
    void 파일을_읽고_객체생성_테스트() throws IOException {
        MdReader reader = new MdReader("src/main/resources/products.md");

        Products products = reader.addItems(new Products());

        assertThat(products.getAll().get(4).getName()).isEqualTo("오렌지주스");
        assertThat(products.getAll().get(5).getName()).isEqualTo("탄산수");
        assertThat(products.getAll().get(6).getPromotion()).isEqualTo("null");
    }

}
