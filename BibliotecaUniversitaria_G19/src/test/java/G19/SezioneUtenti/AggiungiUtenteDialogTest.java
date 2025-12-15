package G19.SezioneUtenti;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.input.KeyCode;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;



public class AggiungiUtenteDialogTest extends ApplicationTest {

    // Poiché JavaFX gira su un thread dedicato, uso un riferimento atomico per catturare il risultato della Dialog in modo thread-safe.
    // AtomicReference è un contenitore che permette di "trasportare" l'oggetto Utente creato dalla Dialog nel thread di test per poter fare le verifiche.
    private final AtomicReference<Utente> risultatoDialog = new AtomicReference<>();
    
    //-----------------------------------------CONFIGURAZIONE AMBIENTE--------------------------------------------------------------------------------

    /*Poiché AggiungiUtenteDialog è una finestra bloccante, chiamata direttamente nel test lo bloccherebbe aspettando un input.
    Per evitare questo creo una "scena di supporto" con un bottone che, quando cliccato, apre la dialog. */
    @Override
    public void start(Stage stage) {
        Button openDialogBtn = new Button("Apri Dialog");
        openDialogBtn.setId("openBtn");
        
        openDialogBtn.setOnAction(event -> {
            AggiungiUtenteDialog dialog = new AggiungiUtenteDialog();
            Optional<Utente> result = dialog.showAndWait();
            result.ifPresent(utente -> risultatoDialog.set(utente));
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
     * Verifica che il pulsante OK si abiliti e che l'oggetto Utente venga creato correttamente.
     */
    @Test
    public void testInserimentoValido() {
        clickOn("#openBtn");

        clickOn("#nomeField").write("Simone");
        clickOn("#cognomeField").write("Pellecchia");
        clickOn("#matricolaField").write("0000000001");
        clickOn("#emailField").write("s.pellecchia@studenti.uni.it"); 

        verifyThat("OK", NodeMatchers.isEnabled());

        clickOn("OK");

        // Verifica il risultato dell'oggetto Utente creato
        Utente utenteCreato = risultatoDialog.get();
        assertNotNull(utenteCreato, "Il risultato della dialog non dovrebbe essere null");
        assertEquals("Simone", utenteCreato.getNome());
        assertEquals("Pellecchia", utenteCreato.getCognome());
        assertEquals("0000000001", utenteCreato.getMatricola());
        assertEquals("s.pellecchia@studenti.uni.it", utenteCreato.getEmail());
    }    
        
    /**
     * Test Validazione Nome.
     * Verifica che l'errore appaia se il Nome sia una stringa vuota o se il capo risulti vuoto.
     */
    @Test
    public void testValidazioneNome() { 
        clickOn("#openBtn");

        clickOn("#cognomeField").write("Pellecchia");
        clickOn("#matricolaField").write("0000000001");
        clickOn("#emailField").write("s.pellecchia@studenti.uni.it"); 
        
        // Caso 1: " " non deve risultare come nome valido
        doubleClickOn("#nomeField").write(" "); 
        verifyThat("OK", NodeMatchers.isDisabled()); 
        
        // Caso 2: "" non deve risultare come nome valido
        doubleClickOn("#nomeField").eraseText(1);
        verifyThat("OK", NodeMatchers.isDisabled());
        
        // Caso 3: Nome numerico valido
        doubleClickOn("#nomeField").write("X Æ A-12");
        verifyThat("OK", NodeMatchers.isEnabled());
        
        // Caso 4: Valido
        doubleClickOn("#nomeField").eraseText(8).write("Simone");
        verifyThat("OK", NodeMatchers.isEnabled());
    }

    /**
     * Test Validazione Cognome.
     * Verifica che l'errore appaia se il Cognome sia una stringa vuota o se il capo risulti vuoto.
     */
    @Test
    public void testValidazioneCognome() { 
        clickOn("#openBtn");

        clickOn("#nomeField").write("Martina");
        clickOn("#matricolaField").write("0000000002");
        clickOn("#emailField").write("m.turi@uni.it"); 
        
        // Caso 1: " " non deve risultare come cognome valido
        doubleClickOn("#cognomeField").write(" "); 
        verifyThat("OK", NodeMatchers.isDisabled()); 
        
        // Caso 2: "" non deve risultare come cognome valido
        doubleClickOn("#cognomeField").eraseText(1);
        verifyThat("OK", NodeMatchers.isDisabled());
        
        // Caso 3: Cognome numerico valido
        doubleClickOn("#cognomeField").write("55");
        verifyThat("OK", NodeMatchers.isEnabled());
        
        // Caso 4: Valido
        doubleClickOn("#cognomeField").eraseText(2).write("Turi");
        verifyThat("OK", NodeMatchers.isEnabled());
    }

    /**
     * Test Validazione Matricola.
     * Verifica che l'errore appaia se la Matricola non ha 10 cifre.
     */
    @Test
    public void testValidazioneMatricola() {
        clickOn("#openBtn");
        
        clickOn("#nomeField").write("Vincenzo");
        clickOn("#cognomeField").write("Pascariello");
        clickOn("#emailField").write("v.pascariello@studenti.uni.it"); 

        // Caso 1: Matricola corta
        clickOn("#matricolaField").write("012345");
        verifyThat("#matricolaError", NodeMatchers.isVisible()); 
        verifyThat("OK", NodeMatchers.isDisabled());       

        // Caso 2: Valido
        doubleClickOn("#matricolaField").write("0000000003");
        verifyThat("#matricolaError", NodeMatchers.isInvisible());
        verifyThat("OK", NodeMatchers.isEnabled());
    }

    /**
     * Test Validazione Email.
     * Verifica che l'errore appaia se l'Email non termina per @uni.it o @studenti.uni.it, se non ha nulla scritto prima di @ o ha più @.
     */
    @Test
    public void testValidazioneEmail() {
        clickOn("#openBtn");
        
        clickOn("#nomeField").write("Angelo");
        clickOn("#cognomeField").write("Mastandrea");
        clickOn("#matricolaField").write("0000000004");

        // Caso 1: Suffisso errato (non @uni.it o @studenti.uni.it)
        clickOn("#emailField").write("a.mastandrea@gmail.com");
        verifyThat("#emailError", NodeMatchers.isVisible()); 
        verifyThat("OK", NodeMatchers.isDisabled());       

        // Caso 2: Prefisso vuoto (niente prima di @)
        doubleClickOn("#emailField").eraseText(14).write("@uni.it");
        verifyThat("#emailError", NodeMatchers.isVisible()); 
        verifyThat("OK", NodeMatchers.isDisabled());       
        
        // Caso 3: Sono presenti più caratteri @
        doubleClickOn("#emailField").eraseText(2).write("a.mastandrea@@uni.it");
        verifyThat("#emailError", NodeMatchers.isVisible()); 
        verifyThat("OK", NodeMatchers.isDisabled());       

        
        // Caso 4: Valido
        doubleClickOn("#emailField").eraseText(15).write("a.mastandrea@uni.it");
        verifyThat("#emailError", NodeMatchers.isInvisible());
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

        clickOn("#nomeField").write("Utente non salvato");

        clickOn("Annulla");
        
        assertNull(risultatoDialog.get());
    }

}