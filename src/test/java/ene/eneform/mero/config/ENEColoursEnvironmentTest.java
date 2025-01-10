package ene.eneform.mero.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ENEColoursEnvironmentTest {
    @Test
    void initialise() {
        ENEColoursEnvironment env = ENEColoursEnvironment.getInstance();
        log.info(env.toString());
    }
}
