package org.example.models;

import org.example.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Laura");
        cuenta.setSaldo( new BigDecimal("1000.1234"));
        String valorEsperado = "Laura";
        assertEquals(cuenta.getPersona(),valorEsperado);
        assertNotNull(cuenta.getSaldo());
    }

    @Test
    void testSaldoCuenta(){
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(new BigDecimal("12443.1234"));
        cuenta.setPersona("EjemploNombre");
        assertEquals(12443.1234,cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta =  new Cuenta("Ejemplo2", new BigDecimal("12443.123"));
        Cuenta cuenta2 =  new Cuenta("Ejemplo2", new BigDecimal("12443.123"));
        //assertNotEquals(cuenta,cuenta2);
        assertEquals(cuenta,cuenta2);
    }

    @Test
    void testDebitoCuenta(){
        Cuenta cuenta =  new Cuenta("Ejemplo2", new BigDecimal("1000"));
        cuenta.debito(new BigDecimal("100"));
        System.out.println("Saldo Actual: $" + cuenta.getSaldo());
        assertNotNull(cuenta.getSaldo());
        assertEquals(900,cuenta.getSaldo().intValue());
    }

    @Test
    void testCreditoCuenta(){
        Cuenta cuenta =  new Cuenta("Ejemplo2", new BigDecimal("12443.123"));
        cuenta.credito(new BigDecimal("100"));
        System.out.println("Saldo Actual: $" + cuenta.getSaldo());
        assertNotNull(cuenta.getSaldo());
        assertEquals(12543,cuenta.getSaldo().intValue());
    }

    @Test
    void testDineroInsuficienteExceptionCuenta(){
        Cuenta cuenta = new Cuenta("Ejemplo 3", new BigDecimal("1000.5"));
        Exception exception = assertThrows(DineroInsuficienteException.class, ()->{
            cuenta.debito(new BigDecimal("1001"));
        });
        String real = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(real.toLowerCase(),esperado.toLowerCase());
    }

    @Test
    void testDineroSuficiente(){
        Cuenta cuenta = new Cuenta("Ejemplo 4", new BigDecimal("900.05"));
        cuenta.debito(new BigDecimal("0.01"));
        BigDecimal esperado = new BigDecimal("900.04");
        assertEquals(esperado,cuenta.getSaldo());
    }

    @Test
    void testTransferirDineroCuentas(){
       Cuenta cuenta1 = new Cuenta("Cuenta 1", new BigDecimal("2500"));
       Cuenta cuenta2 = new Cuenta("Cuenta 2", new BigDecimal("3500.5"));
       Banco banco = new Banco();
       banco.transferir(cuenta2,cuenta1,new BigDecimal("3500"));
       assertEquals("0.5",cuenta2.getSaldo().toPlainString());
       assertEquals("6000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuentas(){
        Cuenta cuenta1 = new Cuenta("Cuenta 1", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Cuenta 2", new BigDecimal("3500.5"));
        Banco banco = new Banco();
        banco.setNombre("BBVA");
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.transferir(cuenta2,cuenta1,new BigDecimal("3500"));
        assertEquals("0.5",cuenta2.getSaldo().toPlainString());
        assertEquals("6000", cuenta1.getSaldo().toPlainString());

        assertEquals(2,banco.getCuentas().size());
        assertEquals("BBVA", cuenta1.getBanco().getNombre());
        assertEquals("Cuenta 1", banco.getCuentas().stream().filter(c -> c.getPersona().equals("Cuenta 1")));
    }

    @Test
    @DisplayName("Test con varias assertions")
    void restAsertAll(){
        Cuenta cuenta1 = new Cuenta("Cuenta 1", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Cuenta 2", new BigDecimal("3500.5"));
        Banco banco = new Banco();
        banco.setNombre("BBVA");
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.transferir(cuenta2,cuenta1,new BigDecimal("3500"));
        assertAll(() -> {
            assertEquals("0.5",cuenta2.getSaldo().toPlainString());},
                () -> { assertEquals("6000", cuenta1.getSaldo().toPlainString()); },
                () -> { assertEquals(2,banco.getCuentas().size()); },
                () -> { assertEquals("BBVA", cuenta1.getBanco().getNombre()); });
    }

    @Test
    @DisplayName("Test con mensaje de error desde assert")
    void testAssertErrorConstructor(){
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Laura Mariel");
        cuenta.setSaldo( new BigDecimal("1000.1234"));
        String valorEsperado = "Laura Mariel";
        assertEquals(cuenta.getPersona(),valorEsperado, "Error con el nombre se esperaba " + valorEsperado);
        assertNotNull(cuenta.getSaldo());
    }


}