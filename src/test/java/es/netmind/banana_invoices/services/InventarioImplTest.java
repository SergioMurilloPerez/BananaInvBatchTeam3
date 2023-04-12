package es.netmind.banana_invoices.services;

import es.netmind.banana_invoices.config.SpringConfig;
import es.netmind.banana_invoices.models.Propietario;
import es.netmind.banana_invoices.models.Recibo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = {"es.netmind.banana_invoices"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("dev")
class InventarioImplTest {
    static final Logger logger = LoggerFactory.getLogger(InventarioImplTest.class);

    private Long savedPropietarioId = 0L;
    private Long savedReciboId = 0L;

    @Autowired
    IInventario inventario;

    @Test
    @Order(1)
    void given_propietario_when_savePropietario_then_Positive() {
        // given
        Propietario prop = new Propietario("luisa", "l@l.com", "1234567", 13);

        // when
        inventario.savePropietario(prop);
        logger.info("**** Saved propietario:", prop);

        // then
        assertThat(prop).isNotNull();
        assertThat(prop.getPid()).isGreaterThan(0);

        savedPropietarioId = prop.getPid();
    }

    @Test
    @Order(2)
    void given_propietarios_when_findAllPropietarios_then_Positive() {
        logger.info("**** savePropietarioId:" + savedPropietarioId);

        // given: ya existen propietarios almacenados
        given_propietario_when_savePropietario_then_Positive();

        // when
        List<Propietario> propietarios = inventario.findAllPropietarios();
        logger.info("**** Lista propietarios:" + propietarios);

        // then
        assertThat(propietarios).isNotNull().hasSizeGreaterThan(0);
        assertThat(propietarios).extracting(Propietario::getPid)
                .contains(savedPropietarioId);
    }

    @Test
    @Order(3)
    void given_recibo_with_no_propietario_when_saveRecibo_then_Positive() {
        // given
        Recibo rec = new Recibo(LocalDate.now(), "Lapiz", 200, 0.9f, 180d, 15f, 207d, true, true);

        // when
        inventario.saveRecibo(rec);
        logger.info("**** Saved recibo:"+ rec);

        // then
        assertThat(rec).isNotNull();
        assertThat(rec.getId()).isGreaterThan(0);

        savedReciboId = rec.getId();

    }

    @Test
    @Order(4)
    void given_recibos_when_findAllRecibos_then_Positive() {
        // given: ya existen recibos almacenados
        given_recibo_with_no_propietario_when_saveRecibo_then_Positive();

        // when
        List<Recibo> recibos = inventario.findAllRecibos();
        logger.info("**** Lista recibos:" + recibos);

        // then
        assertThat(recibos).isNotNull().hasSizeGreaterThan(0);
        assertThat(recibos).extracting(Recibo::getId)
                .contains(savedReciboId);
    }

    @Test
    @Order(5)
    void given_propietarioId_and_reciboId_when_asocia_then_Positive() {
        try {
            // given
            Long propietarioId = savedPropietarioId;
            Long reciboId = savedReciboId;

            // when
            Recibo recibo = inventario.asocia(propietarioId, reciboId);
            logger.info("**** Associated recibo:"+ recibo);

            // then
            assertThat(recibo).isNotNull();
            assertThat(recibo.getPropietario()).isNotNull();
            assertThat(recibo.getPropietario().getPid()).isEqualTo(propietarioId);
        } catch (Exception e) {
            fail("Error:" + e.getMessage());
        }
    }

    @Test
    @Order(6)
     void given_recibo_valido_when_esValidoRecibo_then_Positive() {
        try {
            // given
            Long reciboId = savedReciboId;

            // when
            Set<String> errores = inventario.esValidoRecibo(reciboId);

            // then
            assertThat(errores).hasSizeGreaterThanOrEqualTo(0);
        } catch (Exception e) {

            fail("Error:" + e.getMessage());

        }
    }

    @Test
    @Order(7)
    void given_recibo_pagado_when_estaPagadoRecibo_then_Positive() {
        try {
            // given
            Long reciboId = savedReciboId;

            // when
            boolean estaPagado = inventario.estaPagadoRecibo(reciboId);

            // then
            assertThat(estaPagado).isTrue();
        } catch (Exception e) {

            fail("Error:" + e.getMessage());

        }
    }

}