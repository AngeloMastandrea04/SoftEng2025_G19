package G19.SezionePrestiti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import G19.SezionePrestiti.Prestito;
import G19.SezioneUtenti.Utente;
import G19.SezioneLibri.Libro;

public class PrestitoTest {

    private Libro l;
    private Utente u;
    private Prestito p;
    
    public PrestitoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        //Creazione di una Fixture per eliminare ridondanza ed aggiungere chiarezza
        l = new Libro("Norwegian wood", "Murakami", 2004, "1234", 3);
        u = new Utente("Vinny", "Pasca", "081", "@ggmail.com");
        p = new Prestito(u.toStringPrestito(), l.toStringPrestito(), LocalDate.of(2004, 8, 27));
    }
    
    @AfterEach
    public void tearDown() {
    }


/*
 *   Test Costruttore.
 */
    @Test
    public void testCostruttore() {
        
        //Test sulla rappresentazione scelta per Utente in Prestito.
        assertEquals(u.toStringPrestito(), p.getUtente());
        assertNotEquals(u.toStringPrestito(), "Vinny");

        //Test sulla rappresentazione scelta per Libro in Prestito.
        assertEquals(l.toStringPrestito(), p.getLibro());
        assertNotEquals(l.toStringPrestito(), "Norwegian wood");

        //Test sulla data
        assertEquals(LocalDate.of(2004, 8, 27), p.getDataRestituzione());
        assertNotEquals(LocalDate.of(2007, 8, 27), p.getDataRestituzione());
        assertNotEquals(LocalDate.of(2004, 12, 27), p.getDataRestituzione());
        assertNotEquals(LocalDate.of(2044, 8, 15), p.getDataRestituzione());

        /*I Test per l'inserimento di dati non coerenti sono svolti tramite FXTest nella sezione: SezionePrestitiControllerTest
        in quanto non è possibile testarli a questo livello*/

    }

/*
 *   Test metodi Getter Property.
 */   
    @Test
    public void testGetterProperty(){

        //Partendo dall'oggetto nella Fixture, testiamo la correttezza dei metodi getter degli attributi Property.
        assertNotNull(p.getUtente());
        assertNotNull(p.getLibro());

    }

    @Test
    public void toStringTest(){

        //Stringa che mi aspetto, costruita con una Stringa esplicita
        String expected1 = "Utente: " + "Cognome: " + "Pasca" + ", Nome: " + "Vinny" + ", Matricola: " + "081" + ", Libro: " + "Titolo: " + "Norwegian wood" + ", Autori: " + "Murakami" +", Data di restituzione: " + LocalDate.of(2004, 8, 27);

        //Stringa che mi aspetto, costruita con getter di altre classi già testati che hanno l'unico scopo di restituire il formato corretto
        String expected2 = "Utente: " + u.toStringPrestito() + ", Libro: " + l.toStringPrestito() + ", Data di restituzione: " + LocalDate.of(2004, 8, 27);

        assertEquals(expected1, p.toString());
        assertEquals(expected2, p.toString());

        //VANNO DIVISE PER MAGGIORE MODULABILITà
        
        //Aggiungo semplici variazioni ad ogni Stringa che precede il campo
        String notExpected1 = "Utte: " + u.toStringPrestito() + ", Libro: " + l.toStringPrestito() + ", Data di restituzione: " + LocalDate.of(2004, 8, 27);
        String notExpected2 = "Utente: " + u.toStringPrestito() + ", Lio: " + l.toStringPrestito() + ", Data di restituzione: " + LocalDate.of(2004, 8, 27);
        String notExpected3 = "Utente: " + u.toStringPrestito() + ", Lio: " + l.toStringPrestito() + ", Data di restituzione: " + LocalDate.of(2004, 8, 27);

        //Rimuovo gli spazi
        String notExpected4 = "Utente:" + u.toStringPrestito() + ", Libro:" + l.toStringPrestito() + ", Datadirestituzione:" + LocalDate.of(2004, 8, 27);
        //Cambio la data di restituzione
        String notExpected5 = "Utente:" + u.toStringPrestito() + ", Libro: " + l.toStringPrestito() + ", Data di restituzione: " + LocalDate.of(2044, 12, 15);
        //Cambio le rappresentazioni dei model (Non uso più quelle date da "Model".toStringPrestito())

        //Cambio la rappresentazione di Utente
        String notExpected6 = "Utente:" + "Vinny" + ", Libro: " + l.toStringPrestito() + ", Data di restituzione: " + LocalDate.of(2004, 8, 27);
        //Cambio la rappresentazione di Libro
        String notExpected7 = "Utente:" + u.toStringPrestito() + ", Libro: " + "Norwegian Wood" + ", Data di restituzione: " + LocalDate.of(2004, 8, 27);

        assertNotEquals(notExpected1, p.toString());
        assertNotEquals(notExpected2, p.toString());
        assertNotEquals(notExpected3, p.toString());
        assertNotEquals(notExpected4, p.toString());
        assertNotEquals(notExpected5, p.toString());
        assertNotEquals(notExpected6, p.toString());
        assertNotEquals(notExpected7, p.toString());

    }

    @Test
    public void toStringUtenteTest(){
        /*System.out.println(p.getLibro());
        System.out.println(p.getDataRestituzione());*/

        //Stringa costruita direttamente
        String expected1= "Libro: "+ "Titolo: Norwegian wood, Autori: Murakami" + ", Data di restituzione: " + "2004-08-27";

        /*Stringa costruita tramite metodi getter Property (già testati) che restituiscono 
        la rappresentazuine corretta del Prestito utilizzata nella sezione utenti*/
        String expected2= "Libro: "+ p.getLibro() + ", Data di restituzione: " + p.getDataRestituzione() ;

        assertEquals(expected1, p.toStringUtente());
        assertEquals(expected2, p.toStringUtente());
    }

    //IMPLEMENTARE IL TEST CON CONTENUTI SCORRETTI UTILIZZANDO assertTrue("Stringa".startsWith("Utente: ")); assertTrue(p.toString().Contains("Utente: ")); assertTrue(p.toString().endsWith("Utente: "));

}
