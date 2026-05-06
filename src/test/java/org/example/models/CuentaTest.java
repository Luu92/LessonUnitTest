package org.example.models;

import org.example.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Test;

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

}