package es.netmind.banana_invoices.persistence;

import es.netmind.banana_invoices.config.ReposConfig;
import es.netmind.banana_invoices.models.Propietario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
//@ComponentScan(basePackages = {"es.netmind.banana_invoices.config"})
@Import({ReposConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("dev")
public class JPAPropietarioRepoTest {
    static final Logger logger = LoggerFactory.getLogger(JPAPropietarioRepoTest.class);

    @Autowired
    IPropietarioRepo propietarioRepo;

    @Test
    public void findAllPositive() {
        try {
            List<Propietario> propietarios = propietarioRepo.findAll();
            logger.info("****Propietarios:"+propietarios);
            assertNotNull(propietarios);
        } catch (Exception e) {
            fail("Error:" + e.getMessage());
        }

    }

    @Test
    public void savePositive() {
        try {
            Propietario prop = new Propietario("rodrigo", "r@r.com", "1234567", 45);
            propietarioRepo.save(prop);
            logger.info("****Propietario:"+prop);

            assertTrue(prop.getPid() > 0);
        } catch (Exception e) {
            fail("Error:" + e.getMessage());
        }

    }
}