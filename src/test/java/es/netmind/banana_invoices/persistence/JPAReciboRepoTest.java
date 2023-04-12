package es.netmind.banana_invoices.persistence;

import es.netmind.banana_invoices.config.ReposConfig;
import es.netmind.banana_invoices.config.SpringConfig;
import es.netmind.banana_invoices.models.Propietario;
import es.netmind.banana_invoices.models.Recibo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
//@ComponentScan(basePackages = {"es.netmind.banana_invoices"})
@Import({ReposConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("dev")
class JPAReciboRepoTest {
    static final Logger logger = LoggerFactory.getLogger(JPAReciboRepoTest.class);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    IReciboRepo reciboRepo;

    @Test
    void findAllPositive() {
        try {
            List<Recibo> recibos = reciboRepo.findAll();
            assertNotNull(recibos);
            logger.info("***Recibos:" + recibos);
        } catch (Exception e) {
            fail("Error:" + e.getMessage());
        }

    }

    @Test
    public void save_without_propietario_Positive() {
        try {
            Recibo rec = new Recibo(LocalDate.now(), "Lapiz", 200, 0.9f, 180d, 15f, 207d, true, true);
            reciboRepo.save(rec);
            assertTrue(rec.getId() > 0);
            logger.info("***Recibo:" + rec);
        } catch (Exception e) {
            fail("Error:" + e.getMessage());
        }

    }

    @Test
    public void save_with_propietario_Positive() {
        try {
            Propietario prop = new Propietario("juan", "j@j.com", "1234567", 25);
            Recibo rec = new Recibo(LocalDate.now(), "Lapiz", 200, 0.9f, 180d, 15f, 207d, true, true);
            rec.setPropietario(prop);
            reciboRepo.save(rec);
            assertTrue(rec.getId() > 0);
            logger.info("***Recibo:" + rec);
        } catch (Exception e) {
            fail("Error:" + e.getMessage());
        }

    }

    @Test
    void findByIdPositive() {
        try {
            // given
            Recibo recToSave = new Recibo(LocalDate.now(), "Lapiz", 200, 0.9f, 180d, 15f, 207d, true, true);

            entityManager.persist(recToSave);
            entityManager.flush();

            // when
            Recibo rec = reciboRepo.findById(recToSave.getId());
            logger.info("***Recibo:" + rec);

            // then
            assertNotNull(rec);
        } catch (Exception e) {
            fail("Error:" + e.getMessage());
        }
    }
}