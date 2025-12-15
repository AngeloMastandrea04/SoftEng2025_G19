package G19.SezioneLibri;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibroTest {

    // -------------------------------- TEST DEI COSTRUTTORI -------------------------------------------

    @Test
    void testCostruttorePrincipale() {
        // Test del costruttore: Libro(String titolo, String autori, int anno, String isbn, int copieTotali)
        Libro libro = new Libro("Clean Code", "Robert C. Martin", 2008, "9780132350884", 5);

        assertAll("Verifica attributi iniziali",
            () -> assertEquals("Clean Code", libro.getTitolo()),
            () -> assertEquals("Robert C. Martin", libro.getAutori()),
            () -> assertEquals(2008, libro.getAnno()),
            () -> assertEquals("9780132350884", libro.getIsbn()),
            () -> assertEquals(5, libro.getCopieTotali()),
            () -> assertEquals(5, libro.getCopieDisponibili()) 
        );
    }

    @Test
    void testCostruttoreCompleto() {
        // Test del costruttore: Libro(String titolo, String autori, int anno, String isbn, int copieTotali, int copieDisponibili)
        Libro libro = new Libro("Effective Java", "Joshua Bloch", 2017, "9780134685991", 5, 2);
        
        assertAll("Verifica attributi iniziali",
            () -> assertEquals("Effective Java", libro.getTitolo()),
            () -> assertEquals("Joshua Bloch", libro.getAutori()),
            () -> assertEquals(2017, libro.getAnno()),
            () -> assertEquals("9780134685991", libro.getIsbn()),
            () -> assertEquals(5, libro.getCopieTotali()),
            () -> assertEquals(2, libro.getCopieDisponibili()) 
        );
    }

    // --------------------------------- TEST GETTER E SETTER ------------------------------------

    @Test
    void testGettersAndSetters() {
        Libro libro = new Libro("Titolo", "Autore", 2000, "9781234567890", 1);

        // Caso 1: Modifica Titolo
        libro.setTitolo("Nuovo Titolo");
        assertEquals("Nuovo Titolo", libro.getTitolo());

        // Caso 2: Modifica Autori
        libro.setAutori("Nuovi Autori");
        assertEquals("Nuovi Autori", libro.getAutori());

        // Caso 3: Modifica Anno
        libro.setAnno(2025);
        assertEquals(2025, libro.getAnno());

        // Caso 4: Modifica ISBN
        libro.setIsbn("9780000000000");
        assertEquals("9780000000000", libro.getIsbn());

        // Caso 5: Modifica Copie
        libro.setCopieTotali(10);
        assertEquals(10, libro.getCopieTotali());

        libro.setCopieDisponibili(8);
        assertEquals(8, libro.getCopieDisponibili());
    }

    // ------------------------------- TEST LOGICA EQUALS ------------------------------------------

    @Test
    void testEquals() {
        Libro libro1 = new Libro("Libro A", "Autore A", 2020, "9781111111111", 5);
        Libro libro2 = new Libro("Libro B", "Autore B", 2021, "9781111111111", 3);
        Libro libro3 = new Libro("Libro A", "Autore A", 2020, "9782222222222", 5);

        // Caso 1: Un libro deve essere uguale a se stesso
        assertEquals(libro1, libro1);
        
        // Caso 2: Due libri con lo stesso ISBN devono essere uguali
        assertEquals(libro1, libro2);
        assertEquals(libro2, libro1);
        
        // Caso 3: Due libri con ISBN diverso non devono essere uguali
        assertNotEquals(libro1, libro3);
        
        // Caso 4: Un libro inserito non deve essere uguale a null
        assertNotEquals(libro1, null);
        
        // Caso 5: Un libro inserito non deve essere uguale a un oggetto di tipo diverso
        assertNotEquals(libro1, "Stringa");
    }

    // ------------------------------- TEST PROPERTY JAVAFX ----------------------------------------

    @Test
    void testJavaFXProperties() {
        Libro libro = new Libro("Test", "Test", 2000, "9781234567899", 1);
        //Le Property devono restituire oggetti validi non null
        assertNotNull(libro.titoloProperty());
        assertNotNull(libro.autoriProperty());
        assertNotNull(libro.annoProperty());
        assertNotNull(libro.isbnProperty());
        assertNotNull(libro.copieTotaliProperty());
        assertNotNull(libro.copieDisponibiliProperty());
    }

    // ------------------------------------- TEST TO STRING -----------------------------------------

    @Test
    void testToString() {
        Libro libro = new Libro("Java", "Gosling", 1995, "9791234567890", 10, 5);
        
        String expected = "Titolo: Java, Autori: Gosling, Anno: 1995, ISBN: 9791234567890, CopieTotali: 10, CopieDisponibili: 5";
        assertEquals(expected, libro.toString());
    }
    
    @Test
    void testToStringPrestito() {
        Libro libro = new Libro("Design Patterns", "Gamma", 1994, "9791234567899", 5);
        
        String expected = "9791234567899 - Design Patterns, Gamma";
        assertEquals(expected, libro.toStringPrestito());
    }
}