package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class MdReaderTest {

    @Test
    void 파일_한줄읽기_기능_테스트() throws IOException {
        MdReader reader = new MdReader("src/main/resources/products.md");

        assertThat(reader.readLine()).contains("name,price,quantity,promotion");
        assertThat(reader.readLine()).contains("콜라");

        reader = new MdReader("src/main/resources/promotions.md");

        assertThat(reader.readLine()).contains("name,buy,get,start_date,end_date");
        assertThat(reader.readLine()).contains("탄산2+1,2,1,2024-01-01,2024-12-31");

        for (int i = 0; i < 20; i++) {
            reader.readLine();
        }
    }

}
