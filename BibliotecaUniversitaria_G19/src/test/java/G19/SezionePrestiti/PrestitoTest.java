package G19.SezionePrestiti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        //Creazione di una Fixture per eliminare ridondanza all'interno dei test incrementando la chiarezza
        l = new Libro("Norwegian wood", "Murakami", 2004, "9780123456789", 3);
        u = new Utente("Vinny", "Pasca", "0123456789", "vinny@studenti.uni.it");
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

        //Test sulla rappresentazione scelta per Libro in Prestito.
        assertEquals(l.toStringPrestito(), p.getLibro());

        //Test sulla data
        assertEquals(LocalDate.of(2004, 8, 27), p.getDataRestituzione());
        
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
        //Le Property devono avere il valore dato rispettivamente dai metodi Utente:: toStringPrestito() ed Libro:: toStringPrestito() 
        assertTrue(p.getUtente().equals(u.toStringPrestito()));
        assertTrue(p.getLibro().equals(l.toStringPrestito()));
    }

    @Test
    public void toStringTest(){

        //Stringa che mi aspetto, costruita con una Stringa esplicita
        String expected1 = "Utente: " + "0123456789 - Pasca Vinny"+ "; Libro: " + "9780123456789 - Norwegian wood, Murakami" +"; Data di Restituzione: " + LocalDate.of(2004, 8, 27);

        //Stringa che mi aspetto, costruita con getter di altre classi già testati che hanno l'unico scopo di restituire il formato corretto
        String expected2 = "Utente: " + u.toStringPrestito() + "; Libro: " + l.toStringPrestito() + "; Data di Restituzione: " + LocalDate.of(2004, 8, 27);

        assertEquals(expected1, p.toString());
        assertEquals(expected2, p.toString());
    }

    @Test
    public void toStringFormatTest(){
        
        //Controllo solo se il formato della toString() sia corretto, escluse le rappresentazioni scelte dei dati.
        String s= p.toString();
        assertTrue(s.startsWith("Utente: "));
        assertTrue(s.contains("; Libro: "));
        assertTrue(s.contains("; Data di Restituzione: "));
    }

    @Test
    public void toStringRappresentationTest(){

        String actual = p.toString();
        /*Le rappresentazioni utilizzate per l'utente ed il libro all'interno della
        toString() di Prestito devono essere solo e soltanto quelle messe a disposizione
        dalle classi utente e libro*/
        assertTrue(actual.contains(u.toStringPrestito()));
        assertTrue(actual.contains(l.toStringPrestito()));
    }

    @Test 
    public void toStringWrongDate(){

        //In questo modo si testa anche la corretta data di restituzione
        assertTrue(p.toString().endsWith("2004-08-27")); 
        //Cambio la data di restituzione
        assertTrue(! p.toString().endsWith(LocalDate.of(2044, 8, 27).toString()));
    }

    @Test
    public void toStringUtenteTest(){
        /*System.out.println(p.getLibro());
        System.out.println(p.getDataRestituzione());*/

        //Stringa costruita direttamente
        //String expected1= "Libro: "+ "Titolo: Norwegian wood, Autori: Murakami" + ", Data di restituzione: " + "2004-08-27";
        String expected1="Libro: " + "9780123456789 - Norwegian wood, Murakami" +"; Data di Restituzione: " + "2004-08-27";

        /*Stringa costruita tramite metodi getter Property (già testati) che restituiscono 
        la rappresentazuine corretta del Prestito utilizzata nella sezione utenti*/
        String expected2= "Libro: "+ p.getLibro() + "; Data di Restituzione: " + p.getDataRestituzione() ;

        assertEquals(expected1, p.toStringUtente());
        assertEquals(expected2, p.toStringUtente());
    }

    @Test
    public void toStringUtenteFormatTest(){
        
        //Controllo solo se il formato della toString() sia corretto, escluse le rappresentazioni scelte dei dati.
        String s= p.toStringUtente();
        assertTrue(s.startsWith("Libro: "));
        assertTrue(s.contains("; Data di Restituzione: "));
    }

    @Test
    public void toStringUtenteRappresentationTest(){

        String actual = p.toStringUtente();
        /*La rappresentazione utilizzata per il Libro all'interno della
        toStringUtente() di Prestito deve essere solo e soltanto quella messa a disposizione
        dalla classe libro*/
        assertTrue(actual.contains(l.toStringPrestito()));
    }

    @Test 
    public void toStringUtenteWrongDate(){

        //In questo modo si testa anche la corretta data di restituzione
        assertTrue(p.toStringUtente().endsWith("2004-08-27")); 
        //Cambio la data di restituzione
        assertTrue(! p.toStringUtente().endsWith(LocalDate.of(2044, 8, 27).toString()));
    }

}
