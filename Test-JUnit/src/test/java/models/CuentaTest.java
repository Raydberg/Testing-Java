package models;

import exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class CuentaTest {
    Cuenta cuenta;
    private TestInfo testInfo;
    private TestReporter testReporter;

    @BeforeEach
        //-> amtes de cada metodo
    void initMethodTest(TestInfo testInfo, TestReporter testReporter) {
        testReporter.publishEntry("Ejecutando " + testInfo.getDisplayName() + " " + testInfo.getTestMethod().orElseThrow().getName() + " con las etiquetas " + testInfo.getTags());
        this.cuenta = new Cuenta("Ryan", new BigDecimal("1000.125"));
        this.testInfo = testInfo;
        this.testReporter = testReporter;
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando metodo del programa");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando el test");
    }


    @Test
    @DisplayName("Probando nombre de la cuenta")
    @Disabled
    void setPersona() {

        fail(); //-> forzar error
        cuenta = new Cuenta("Ryan");
        String esperado = "Ryan";
        String real = cuenta.getPersona();
        //Poner estos mensajes de manera directa , JUnit lo qye hace es crear
        // la instancia pase o no las pruebas  y pyede consumir memoria incesaria
        assertNotNull(real, "La cuenta no puede ser nula");
        //Es mejor hacer uso de expreciones lambda , se ejecuta solo si falla
        assertEquals(esperado, real, () -> "El nombre de la cuenta no es el que se esperaba");

        assertTrue(real.equals(esperado), "El esperado debe ser igual a la real");
    }

    @Test
    void getSaldo() {
        if (testInfo.getTags().contains("cuenta")) {
            System.out.println("Hacer algo con el tag de cuenta");
        }
        cuenta = new Cuenta("Ryan", new BigDecimal("1000.434"));
        assertEquals(1000.434, cuenta.getSaldo().doubleValue());
        //Siempre sea falso
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Nested
    @DisplayName("Probando atributos de la cuenta")
    class CuentaTestNombreSaldo {
        @Test
        void testReferenciaCuenta() {
            var cuenta1 = new Cuenta("Ryan", new BigDecimal("1000.23"));
            var cuenta2 = new Cuenta("Ryan", new BigDecimal("1000.23"));
//        assertNotEquals(cuenta1, cuenta2);//-> por referencia
            assertNotNull(cuenta1);
            assertNotNull(cuenta2);
            assertEquals(cuenta1, cuenta2);
        }

        @Test
        void testDebitoCuenta() {
            cuenta.debito(new BigDecimal(100)); //-> se le resta 100 al monto
            assertNotNull(cuenta.getSaldo());
            assertEquals(900, cuenta.getSaldo().intValue());
            assertEquals("900.125", cuenta.getSaldo().toString());
        }

        @Test
        void testCreditoCuenta() {
            cuenta.credito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(1100, cuenta.getSaldo().intValue());
            assertEquals("1100.125", cuenta.getSaldo().toString());
        }

        @Test
        void testDineroInsuficienteException() {
            Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
                cuenta.debito(new BigDecimal("2000"));
            });
            String actual = exception.getMessage();
            String expect = "Dinero Insuficiente";
            assertEquals(expect, actual);
        }
    }

    @Nested
    class CuentaTransferenciasTest {
        @Test
        void testTransferirDineroCuentas() {
            var cuenta1 = new Cuenta("Ryan", new BigDecimal("2500"));
            var cuenta2 = new Cuenta("Ryan", new BigDecimal("1500.8989"));
            var banco = new Banco();
            banco.setNombre("Banco del estado");
            banco.transferir(cuenta2, cuenta1, new BigDecimal("500"));
            assertEquals("1000.8989", cuenta2.getSaldo().toString());
            assertEquals("3000", cuenta1.getSaldo().toString());
        }

        @Test
        void testRelacionBancoCuentas() {
            var cuenta1 = new Cuenta("Ryan", new BigDecimal("2500"));
            var cuenta2 = new Cuenta("Ryan", new BigDecimal("1500.8989"));
            var banco = new Banco();
            banco.addCuenta(cuenta1);
            banco.addCuenta(cuenta2);
            banco.setNombre("Banco del estado");
            banco.transferir(cuenta2, cuenta1, new BigDecimal("500"));
            assertAll(
                    () -> assertEquals("1000.8989", cuenta2.getSaldo().toString(), () -> "El valor del saldo de la cuenta2 no es el esperado"),
                    () -> assertEquals("3000", cuenta1.getSaldo().toString()),
                    () -> assertEquals(2, banco.getCuentas().size()),
                    () -> assertEquals("Banco del estado", cuenta1.getBanco().getNombre()),
                    () -> assertEquals("Ryan",
                            banco.getCuentas()
                                    .stream()
                                    .filter(c -> c.getPersona().equals("Ryan"))
                                    .findFirst()
                                    .get()
                                    .getPersona()
                    ),
                    () -> assertTrue(banco.getCuentas()
                                    .stream()
                                    .anyMatch(c -> c.getPersona().equals("Ryan")),
                            "Ryan"
                    )
            );
        }
    }


    @Nested
    class SistemaOperativoTest {
        @Test
        //Solo en cierto sistemas operativos
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows() {

        }

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void testSoloLinuxMac() {

        }

        @Test
        //No ejecutar en un sistema operativo
        @DisabledOnOs(OS.WINDOWS)
        void testNoWindows() {

        }
    }

    @Nested
    class JavaVersionTest {
        @Test
        // Ejecutar en una version especifica de java
        @EnabledOnJre(JRE.JAVA_8)
        void testSoloJdk8() {

        }

        @Test
        @DisabledOnJre(JRE.JAVA_15)
        void testSoloJdk15() {

        }
    }

    @Nested
    class SistemPropertiesTest {
        @Test
        void imprimirSystemProperties() {
            Properties properties = System.getProperties();
            properties.forEach((k, v) -> System.out.println(k + ":" + v));
        }

        @Test
        @EnabledIfSystemProperty(named = "java.version", matches = "15.0.1")
        void testJavaVersion() {
        }

        @Test
        @EnabledIfSystemProperty(named = "ENV", matches = "dev")
        void testDev() {

        }
    }

    @Nested
    class VariableDeAmbienteTest {

        @Test
        void imprimirVariablesAmbiente() {
            Map<String, String> getenv = System.getenv();
            getenv.forEach((k, v) -> System.out.println(k + ":" + v));
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = "")
        void testJavaHome() {
        }

        /**
         * Desabilitar una prueeba de forma programatica
         */
        @Test
        @DisplayName("Probando el saldo de la cuenta corriente")
        void testDebitoCuentaDev() {
            boolean idDev = "dev".equals(System.getProperty("ENV"));
            assumeTrue(idDev);
            cuenta.debito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(900, cuenta.getSaldo().intValue());
            assertEquals("900.125", cuenta.getSaldo().toString());
        }

        @Test
        @DisplayName("Probando el saldo de la cuenta corriente")
        void testDebitoCuentaDev2() {
            boolean idDev = "dev".equals(System.getProperty("ENV"));
            assumingThat(idDev, () -> {
                cuenta.debito(new BigDecimal(100));
                assertNotNull(cuenta.getSaldo());
                assertEquals(900, cuenta.getSaldo().intValue());
            });
            assertEquals("900.125", cuenta.getSaldo().toString());
        }
    }

    @RepeatedTest(value = 5, name = "Repeticion numero {currentRepetition} de {totalRepetitions}")
    void testDebitoCuenta(RepetitionInfo info) {
        if (info.getCurrentRepetition() == 3) {
            System.out.println("Estamos en la repeticion 3");
        }

        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.125", cuenta.getSaldo().toString());
    }

    @Tag("param")
    @Nested
    class PruebasParametizadastest {
        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} {argumentWithName}")
        @ValueSource(strings = {"100", "200", "300", "500", "700", "1000"})
        void testDebitoCuentaParameter(String monto) {
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} {argumentWithName}")
        @CsvSource({"1,100", "2,200", "3,300", "4,500", "5,700", "6,1000.12345"})
        void testDebitoCuentaParameterCsvSource(String index, String monto) {
            System.out.println(index + "->" + monto);
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} {argumentWithName}")
        //El archivo debe estar dentro de resource
        @CsvFileSource(resources = "/data.csv")
        void testDebitoCuentaParameterCsvFileSource(String monto) {
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} {argumentWithName}")
        @MethodSource("montoList")
        void testDebitoCuentaParameterMethodSource(String monto) {
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        static List<String> montoList() {
            return Arrays.asList("100", "200", "300");
        }
    }


    @Test
    @Timeout(5)
    void pruebaTimeout() throws InterruptedException {
        TimeUnit.MICROSECONDS.sleep(6);
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void pruebaTimeout2() throws InterruptedException {
        TimeUnit.MICROSECONDS.sleep(6);
    }

    //Sin usar anotaciones


    @Test
    void testTimeoutAssertions() {
        assertTimeout(Duration.ofSeconds(5), () -> {
            //Nuesto codigo de prueba
            TimeUnit.SECONDS.sleep(6);
        });
    }
}