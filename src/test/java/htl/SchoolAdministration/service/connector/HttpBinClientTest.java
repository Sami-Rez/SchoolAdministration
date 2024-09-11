package htl.SchoolAdministration.service.connector;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class HttpBinClientTest {

    private @Autowired HttpBinClient httpClient;

    @Test
    void ensureRetrieveKeyReturnsAValue() {
        assertThat(httpClient.retrieveKey()).isNotNull();
    }
}
