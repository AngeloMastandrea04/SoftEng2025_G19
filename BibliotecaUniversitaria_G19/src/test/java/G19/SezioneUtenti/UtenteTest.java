package G19.SezioneUtenti;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {

    // -------------------------------- TEST DEI COSTRUTTORI -------------------------------------------

    @Test
    void testCostruttore() {
        // Test del costruttore: Utente(String nome, String cognome, String matricola, String email)
        Utente utente = new Utente("Simone", "Pellecchia", "0000000001", "s.pellecchia@studenti.uni.it");
        
        assertAll("Verifica attributi iniziali",
            () -> assertEquals("Simone", utente.getNome()),
            () -> assertEquals("Pellecchia", utente.getCognome()),
            () -> assertEquals("0000000001", utente.getMatricola()),
            () -> assertEquals("s.pellecchia@studenti.uni.it", utente.getEmail()),
            () -> assertTrue(utente.getPrestitiAttivi().isEmpty())
        );
    }

    // --------------------------------- TEST GETTER E SETTER ------------------------------------

    @Test
    void testGettersAndSetters() {
        Utente utente = new Utente("Simone", "Pellecchia", "0000000001", "s.pellecchia@studenti.uni.it");

        // Caso 1: Modifica Nome
        utente.setNome("Martina");
        assertEquals("Martina", utente.getNome());

        // Caso 2: Modifica Cognome
        utente.setCognome("Turi");
        assertEquals("Turi", utente.getCognome());

        // Caso 3: Modifica Matricola
        utente.setMatricola("0000000002");
        assertEquals("0000000002", utente.getMatricola());

        // Caso 4: Modifica Email
        utente.setEmail("m.turi@uni.it");
        assertEquals("m.turi@uni.it", utente.getEmail());
        
        // Caso 5: Ottenimento Prestiti Attivi
        ObservableList<String> prestitiAttivi = FXCollections.observableArrayList("Prestito 1", "Prestito 2", "Prestito 3");
        utente.getPrestitiAttivi().addAll(prestitiAttivi);
        assertEquals(prestitiAttivi, utente.getPrestitiAttivi());
    }

    // ------------------------------- TEST LOGICA EQUALS ------------------------------------------

    @Test
    void testEquals() {
        Utente utente1 = new Utente("Vincenzo", "Pascariello", "0000000003", "v.pascariello@studenti.uni.it");
        Utente utente2 = new Utente("Angelo", "Mastandrea", "0000000003", "a.mastandrea@uni.it");
        Utente utente3 = new Utente("Vincenzo", "Pascariello", "0000000004", "v.pascariello@studenti.uni.it");

        // Caso 1: Un utente deve essere uguale a se stesso
        assertEquals(utente1, utente1);
        
        // Caso 2: Due utenti con la stessa matricola devono essere uguali
        assertEquals(utente1, utente2);
        assertEquals(utente2, utente1);
        
        // Caso 3: Due utenti con matricola diversa non devono essere uguali
        assertNotEquals(utente1, utente3);
        
        // Caso 4: Un utente inserito non deve essere uguale a null
        assertNotEquals(utente1, null);
        
        // Caso 5: Un utente inserito non deve essere uguale a un oggetto di tipo diverso
        assertNotEquals(utente1, "Stringa");
    }

    // ------------------------------- TEST PROPERTY JAVAFX ----------------------------------------

    @Test
    void testJavaFXProperties() {
        Utente utente = new Utente("Martina", "Turi", "0000000002", "m.turi@uni.it");
        //Le Property devono restituire oggetti validi non null
        assertNotNull(utente.nomeProperty());
        assertNotNull(utente.cognomeProperty());
        assertNotNull(utente.matricolaProperty());
        assertNotNull(utente.emailProperty());
    }

    // ------------------------------------- TEST TO STRING -----------------------------------------

    @Test
    void testToString() {
        Utente utente = new Utente("Vincenzo", "Pascariello", "0000000003", "v.pascariello@studenti.uni.it");
        
        String expected = "Cognome: Pascariello, Nome: Vincenzo, Matricola: 0000000003, Email: v.pascariello@studenti.uni.it";
        assertEquals(expected, utente.toString());
    }
    
    @Test
    void testToStringPrestito() {
        Utente utente = new Utente("Angelo", "Mastandrea", "0000000004", "a.mastandrea@uni.it");
        
        String expected = "0000000004 - Mastandrea Angelo";
        assertEquals(expected, utente.toStringPrestito());
    }
}