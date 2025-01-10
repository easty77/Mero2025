package ene.eneform.mero;

import ene.eneform.mero.service.MeroService;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.SchedulingConfiguration;

@SpringBootConfiguration
@AutoConfiguration
@Import({
        MeroService.class
        })
public class SpringBootStarterConfig {
}
