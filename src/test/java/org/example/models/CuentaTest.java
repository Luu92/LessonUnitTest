package org.example.models;

import org.example.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    Cuenta cuenta;

    @BeforeEach
    void initMethodTest(TestInfo testInfo ,  TestReporter testReporter){
        this.cuenta = new Cuenta("Laura",  new BigDecimal("1000.123"));
        System.out.println("Iniciamos el objeto BeforeEach");
        System.out.println("Ejecuntando " + testInfo.getTestMethod().orElse(null).getName() + " con etiqueta " + testInfo.getTags());
    }

    @AfterEach
    void tearDown(){
        System.out.println("Finalizando el metodo");
    }

    @Test
    void testNombreCuenta(){
        //Cuenta cuenta = new Cuenta();
        //cuenta.setPersona("Laura");
        //cuenta.setSaldo( new BigDecimal("1000.1234"));
        String valorEsperado = "Laura";
        assertEquals(cuenta.getPersona(),valorEsperado);
        assertNotNull(cuenta.getSaldo());
    }

    @Test
    void testSaldoCuenta(){
        //Cuenta cuenta = new Cuenta();
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
        //Cuenta cuenta =  new Cuenta("Ejemplo2", new BigDecimal("1000"));
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
        //Cuenta cuenta = new Cuenta("Ejemplo 3", new BigDecimal("1000.5"));
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
        //assertEquals("Cuenta 1", banco.getCuentas().stream().filter(c -> c.getPersona().equals("Cuenta 1")));
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
        //Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Laura Mariel");
        cuenta.setSaldo( new BigDecimal("1000.1234"));
        String valorEsperado = "Laura Mariel";
        assertEquals(cuenta.getPersona(),valorEsperado, "Error con el nombre se esperaba " + valorEsperado);
        assertNotNull(cuenta.getSaldo());
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSystemDiferentWindows(){

    }

    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void testSystemDiferentMacLinux(){

    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testNoWindows(){
    }

    @Test
    @DisabledOnOs({OS.MAC, OS.LINUX})
    void testNoLinuxMac(){
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void soloJRE17(){
    }
    //Imprime las variables del sistema
    @Test
    void imprimirVariablesAmbiente(){
       Map<String, String> getEnv = System.getenv();
       getEnv.forEach( (k,v) -> System.out.println(k + " = " + v) );
    }

    //Test que nos ayuda a saber las variable del sistema en caso de existir
    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches =  ".*jdk-17.0.13.*")
    void testJavaHome(){

    }

    //Clases anidadas con el proposito de agrupar las pruebas con una jerarqui, si la prueba falla de la clase
    //heredada falla todas la pruebas desde las clase hija hasta la padre
    @Nested
    class ClasesAnidada{
        //Propiedades y metodos de la clase
        @Test
        void testCaseAnidada(){
            assertEquals(2,2);
        }
    }

    @Nested
    class  ClasesAnidadados{
        @Test
        void testCaseAnidadaDos() {
            assertEquals(false, false);
        }
        //Podemos repetir metodos ya implementados en otras clases y minimo el parametro que lo va a realizar
        @RepeatedTest(2)
        void testCaseAnidada(){
            assertEquals(2,2);
        }
    }

    @Tag("param") // Marca para ejecutar las pruebas con la etiqueta "param", editamos las configuraciones como "run tags"
    @Nested
    class PruebasParametrizadas{
        //Test patametrizado
        @ParameterizedTest
        @ValueSource(strings = {"100","200","300","500", "700", "1000"})
        void testDebitoCuentaConParametros(String monto){
            cuenta.debito(new BigDecimal(monto));
            System.out.println("Saldo Actual: $" + cuenta.getSaldo());
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo( BigDecimal.ZERO) > 0);
        }

        //Test patametrizado
        @ParameterizedTest
        @CsvSource({"1,100","2,200","3,300","4,500", "5,700", "6,1000"})
        void testDebitoCuentaConParametrosCSVSource(String index , String monto){
            System.out.println("Indece " + index + " del archivo CSV -> " + monto);
            cuenta.debito(new BigDecimal(monto));
            System.out.println("Saldo Actual: $" + cuenta.getSaldo());
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo( BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest
        @CsvSource({"200,100","250,200","999,300","4000,500", "750,700", "1000,1000"})
        void testDebitoCuentaConParametrosCSVSource2(String saldo , String monto){
            System.out.println("Cuento con " + saldo + " y quiero retirar-> " + monto);
            cuenta.setSaldo(new BigDecimal(saldo));
            cuenta.debito(new BigDecimal(monto));
            System.out.println("Saldo Actual: $" + cuenta.getSaldo());
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo( BigDecimal.ZERO) >= 0);
        }

        //Test patametrizado con un archivo de entrada
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        void testDebitoCuentaConParametrosFileSource(String monto){
            System.out.println("Contenido en el archivo CSV -> " + monto);
            cuenta.debito(new BigDecimal(monto));
            System.out.println("Saldo Actual: $" + cuenta.getSaldo());
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo( BigDecimal.ZERO) > 0);
        }


        //Test  patametrizado con parametro de una función
        @ParameterizedTest
        @MethodSource("montoList")
        void testDebitoCuentaConParametrosMethodSource(String monto){
            System.out.println("Contenido en el retorno de la funcion -> " + monto);
            cuenta.debito(new BigDecimal(monto));
            System.out.println("Saldo Actual: $" + cuenta.getSaldo());
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo( BigDecimal.ZERO) > 0);
        }

        static List<String> montoList(){
            return Arrays.asList("100","200","300","500", "700", "1000");
        }
    }



}