package G19.SezioneLibri;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.input.KeyCode;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;



public class AggiungiLibroDialogTest extends ApplicationTest {

    // Poiché JavaFX gira su un thread dedicato, uso un riferimento atomico per catturare il risultato della Dialog in modo thread-safe.
    // AtomicReference è un contenitore che permette di "trasportare" l'oggetto Libro creato dalla Dialog nel thread di test per poter fare le verifiche.
    private final AtomicReference<Libro> risultatoDialog = new AtomicReference<>();
    
    //-----------------------------------------CONFIGURAZIONE AMBIENTE--------------------------------------------------------------------------------

    /*Poiché AggiungiLibroDialog è una finestra bloccante, chiamata direttamente nel test lo bloccherebbe aspettando un input.
    Per evitare questo creo una "scena di supporto" con un bottone che, quando cliccato, apre la dialog. */
    @Override
    public void start(Stage stage) {
        Button openDialogBtn = new Button("Apri Dialog");
        openDialogBtn.setId("openBtn");
        
        openDialogBtn.setOnAction(event -> {
            AggiungiLibroDialog dialog = new AggiungiLibroDialog();
            Optional<Libro> result = dialog.showAndWait();
            result.ifPresent(libro -> risultatoDialog.set(libro));
        });

        stage.setScene(new Scene(new StackPane(openDialogBtn), 400, 200));
        stage.show();
    }

    @BeforeEach
    public void resetResult() {
        risultatoDialog.set(null);                      // Resetta il risultato prima di ogni test
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();                          //Chiude lo Stage
        release(new KeyCode[]{});                       //Rialscia eventuali tsati premuti
    }

    //-----------------------------------------------------------TEST INSERIMENTO----------------------------------------------------------------------
    
    /**
     * Test Inserimento di dati validi.
     * Verifica che il pulsante OK si abiliti e che l'oggetto Libro venga creato correttamente.
     */
    @Test
    public void testInserimentoValido() {
        clickOn("#openBtn");

        clickOn("#titoloField").write("Il Signore degli Anelli");
        clickOn("#autoriField").write("J.R.R. Tolkien");
        clickOn("#annoField").write("1954");
        clickOn("#isbnField").write("9788845292613"); 
        clickOn("#copieField").write("10");

        verifyThat("OK", NodeMatchers.isEnabled());

        clickOn("OK");

        // Verifica il risultato dell'oggetto Libro creato
        Libro libroCreato = risultatoDialog.get();
        assertNotNull(libroCreato, "Il risultato della dialog non dovrebbe essere null");
        assertEquals("Il Signore degli Anelli", libroCreato.getTitolo());
        assertEquals(1954, libroCreato.getAnno());
        assertEquals("9788845292613", libroCreato.getIsbn());
        assertEquals(10, libroCreato.getCopieTotali());
        assertEquals(10, libroCreato.getCopieDisponibili()); 
    }    
        
    /**
     * Test Validazione Titolo.
     * Verifica che l'errore appaia se il Titolo sia una stringa vuota o se il capo risulti vuoto.
     */
    @Test
    public void testValidazioneTitolo() { 
        clickOn("#openBtn");

        clickOn("#autoriField").write("A");
        clickOn("#annoField").write("2017");
        clickOn("#isbnField").write("9781234887890");
        clickOn("#copieField").write("5");
        
        // Caso 1: " " non deve risultare come titolo valido
        doubleClickOn("#titoloField").write(" "); 
        verifyThat("OK", NodeMatchers.isDisabled()); 
        
        // Caso 2: "" non deve risultare come titolo valido
        doubleClickOn("#titoloField").eraseText(1);
        verifyThat("OK", NodeMatchers.isDisabled());
        
        // Caso 3: Titolo numerico valido
        doubleClickOn("#titoloField").write("1984");
        verifyThat("OK", NodeMatchers.isEnabled());
        
        // Caso 4: Valido
        doubleClickOn("#titoloField").write("Frankenstein");
        verifyThat("OK", NodeMatchers.isEnabled());
    }

    /**
     * Test Validazione ISBN.
     * Verifica che l'errore appaia se l'ISBN non inizia con 978/979 o non ha 13 cifre.
     */
    @Test
    public void testValidazioneISBN() {
        clickOn("#openBtn");
        
        clickOn("#titoloField").write("Test");
        clickOn("#autoriField").write("Test");
        clickOn("#annoField").write("2020");
        clickOn("#copieField").write("5");

        // Caso 1: ISBN Corto
        clickOn("#isbnField").write("978123");
        verifyThat("#isbnError", NodeMatchers.isVisible()); 
        verifyThat("OK", NodeMatchers.isDisabled());       

        // Caso 2: Prefisso Errato (non 978 o 979)
        doubleClickOn("#isbnField").write("1234567890123");
        verifyThat("#isbnError", NodeMatchers.isVisible());

        // Caso 3: Valido
        doubleClickOn("#isbnField").write("9791234567890");
        verifyThat("#isbnError", NodeMatchers.isInvisible());
        verifyThat("OK", NodeMatchers.isEnabled());
    }

    /**
     * Test Validazione Anno.
     * Verifica che l'anno debba essere numerico e non futuro.
     */
    @Test
    public void testValidazioneAnno() {
        clickOn("#openBtn");
        
        clickOn("#titoloField").write("T");
        clickOn("#autoriField").write("A");
        clickOn("#isbnField").write("9781234567890");
        clickOn("#copieField").write("1");

        // Caso 1: Anno non numerico
        clickOn("#annoField").write("Ciao");
        verifyThat("#annoError", NodeMatchers.isVisible());
        verifyThat("OK", NodeMatchers.isDisabled());

        // Caso 2: Anno Futuro
        int annoFuturo = LocalDate.now().getYear() + 1;
        doubleClickOn("#annoField").write(String.valueOf(annoFuturo));
        verifyThat("#annoError", NodeMatchers.isVisible());
        
        // Caso 3: il limite inferiore 0 deve essere valido
        doubleClickOn("#annoField").write("0");
        verifyThat("#annoError", NodeMatchers.isInvisible());
        verifyThat("OK", NodeMatchers.isEnabled());

        // Caso 4: il limite superiore, Anno Corrente, deve essere valido
        String annoCorrente = String.valueOf(LocalDate.now().getYear());
        doubleClickOn("#annoField").write(annoCorrente);
        verifyThat("#annoError", NodeMatchers.isInvisible());
        verifyThat("OK", NodeMatchers.isEnabled());

        // Caso 5: Valido
        doubleClickOn("#annoField").write("2000");
        verifyThat("#annoError", NodeMatchers.isInvisible());
        verifyThat("OK", NodeMatchers.isEnabled());
    }

    /**
     * Test Validazione Copie.
     * Verifica che le copie siano > 0.
     * Corretto per gestire la pulizia del campo in modo robusto.
     */
    @Test
    public void testValidazioneCopie() {
        clickOn("#openBtn");

        clickOn("#titoloField").write("T");
        clickOn("#autoriField").write("A");
        clickOn("#annoField").write("2020");
        clickOn("#isbnField").write("9781234567890");

        // Caso 1: Copie = 0 
        clickOn("#copieField").write("0");
        verifyThat("#copieError", NodeMatchers.isVisible());
        verifyThat("OK", NodeMatchers.isDisabled());

        // Caso 2: Copie negative
        clickOn("#copieField").eraseText(5).write("-5");
        verifyThat("#copieError", NodeMatchers.isVisible());
        
        // Caso 3: il limite Inferiore, 1, deve essere valido
        clickOn("#copieField").eraseText(5).write("1");
        verifyThat("#copieError", NodeMatchers.isInvisible());
        verifyThat("OK", NodeMatchers.isEnabled());

        // caso 4: Valido
        clickOn("#copieField").eraseText(5).write("5");        
        verifyThat("#copieError", NodeMatchers.isInvisible());
        verifyThat("OK", NodeMatchers.isEnabled());
    }

    //------------------------------------------------------TEST CHIUSURA SENZA CONFERMA---------------------------------------------------------------------
    /**
     * Test Chiusura senza conferma.
     * Verifica che se si chiude la dialog senza OK, il risultato sia nullo.
     */
    @Test
    public void testAnnullaInserimento() {
        clickOn("#openBtn");

        clickOn("#titoloField").write("Libro non salvato");

        clickOn("Annulla");
        
        assertNull(risultatoDialog.get());
    }

}