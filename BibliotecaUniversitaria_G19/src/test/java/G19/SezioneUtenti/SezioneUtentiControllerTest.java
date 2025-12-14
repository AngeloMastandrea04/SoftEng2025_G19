package G19.SezioneUtenti;

import G19.Biblioteca.Biblioteca;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.TableViewMatchers;

import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;

public class SezioneUtentiControllerTest extends ApplicationTest {

    private SezioneUtentiController controller;

    //----------------------------------------- CONFIGURAZIONE AMBIENTE --------------------------------------------------------------------------------
    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlLocation = getClass().getResource("/G19/SezioneUtenti/SezioneUtentiView.fxml");
        if (fxmlLocation == null) fxmlLocation = getClass().getResource("SezioneUtentiView.fxml");
        if (fxmlLocation == null) throw new RuntimeException("File FXML non trovato!");

        String fxmlContent = new String(Files.readAllBytes(Paths.get(fxmlLocation.toURI())), StandardCharsets.UTF_8);
        fxmlContent = fxmlContent.replaceAll("fx:controller=\"[^\"]*\"", "");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlLocation);
        
        controller = new SezioneUtentiController();
        loader.setController(controller);

        Parent root = loader.load(new ByteArrayInputStream(fxmlContent.getBytes(StandardCharsets.UTF_8)));
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

    @BeforeEach
    public void setUpData() {
        interact(() -> {
            Biblioteca.getInstance().getListaUtenti().clear();
            Biblioteca.getInstance().getListaUtenti().addAll(
                new Utente("Simone", "Pellecchia", "0000000001", "s.pellecchia@studenti.uni.it"),
                new Utente("Martina", "Turi", "0000000002", "m.turi@uni.it"),
                new Utente("Vincenzo", "Pascariello", "0000000003", "v.pascariello@studenti.uni.it"),
                new Utente("Angelo", "Mastandrea", "0000000004", "a.mastandrea@uni.it"),
                new Utente("Aldo", "Malinconico", "0000000005", "a.malinconico@studenti.uni.it"),
                new Utente("Gabriele", "Imparato", "0000000006", "g.imparato@uni.it"),
                new Utente("Laura", "Nigro", "0000000007", "l.nigro@studenti.uni.it")
            );
        });
    }

    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new KeyCode[]{});    
    }

    // -------------------------------------------------- TEST VISUALIZZAZIONE e RICERCA e SELEZIONE --------------------------------------------
    
    /**
     * Test IF-1.4: Visualizzazione della lista dei Utenti.
     * Verifica che la tabella sia popolata correttamente all'avvio.
     */
    @Test
    public void testInizializzazioneTabella() {
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(7));
        verifyThat("#tabUtenti", TableViewMatchers.containsRow(
                "Pellecchia", "Simone", "0000000001", "s.pellecchia@studenti.uni.it"
        ));
    }
    
    /**
     * Test IF-1.5: Ricerca di un Utente.
     * Verifica il filtro tramite la barra di ricerca.
     */
    @Test
    public void testRicercaLibroPerCognome() {
        clickOn("#ricUtente").write("Pellecchia");
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(1));
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Pellecchia", "Simone", "0000000001", "s.pellecchia@studenti.uni.it", ""));
    }

    @Test
    public void testRicercaLibroPerMatricola() {
        clickOn("#ricUtente").write("0000000002");
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(1));
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Turi", "Martina", "0000000002", "m.turi@uni.it", ""));
    }

    @Test
    public void testRicercaNessunRisultato() {
        clickOn("#ricUtente").write("UtenteInesistenteXYZ");
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(0));
    }
    
    /**
     * Test Selezione e ButtonCancella.
     * Verifica che il pulsante Cancella si abiliti solo alla selezione.
     */
    @Test
    public void testAbilitazionePulsanteCancella() {
        verifyThat("#cancUtenteBtn", NodeMatchers.isDisabled());
        clickOn("Vincenzo");
        verifyThat("#cancUtenteBtn", NodeMatchers.isEnabled());
    }

    
    // -------------------------------------------------- TEST INSERIMENTO --------------------------------------------
    /**
     * Test IF-1.1: Inserimento delle informazioni di un Utente (Successo).
     * Simula il click su "Aggiungi Utente", compila la Dialog e verifica l'aggiunta.
     */
    @Test
    public void testAggiungiUtenteSuccesso() {
        int righeIniziali = controller.tabUtenti.getItems().size();
        clickOn("Aggiungi Utente");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());  
        clickOn("#nomeField").write("Samuele");
        clickOn("#cognomeField").write("Tortora");
        clickOn("#matricolaField").write("0000000008");
        clickOn("#emailField").write("s.tortora@uni.it");

        clickOn("OK");
        
        // Verifica che l'utente sia stato aggiunto alla tabella
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(righeIniziali + 1));
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Tortora", "Samuele", "0000000008", "s.tortora@uni.it", ""));
    }
    
    /**
    * Test IF-1.1: Inserimento Utente Fallito (Duplicato).
    * Tenta di inserire un utente con matricola già esistente.
    */
    @Test
    public void testAggiungiUtenteDuplicato() {
        int righeIniziali = controller.tabUtenti.getItems().size();
        clickOn("Aggiungi Utente");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());  
        clickOn("#nomeField").write("Vincenzo");
        clickOn("#cognomeField").write("Pascariello");
        clickOn("#matricolaField").write("0000000003");
        clickOn("#emailField").write("v.pascariello@studenti.uni.it");

        clickOn("OK");
        
        // Verifica Alert di Errore (Generato dal Controller)
        verifyThat("È già presente un Utente avente la matricola inserita (0000000003)!", NodeMatchers.isVisible());       //Verifica che sia apparso l'Alert

        clickOn("Annulla"); 
        
        // Verifica che l'utente NON sia stato aggiunto 
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(righeIniziali));
    }
    
    /**
    * Test IF-1.1: Inserimento Utente Fallito (Campi Vuoti).
    * Tenta di inserire un utente lasciando i campi vuoti.
    */
    @Test
    public void testAggiungiUtenteCampiVuoti() {
        int righeIniziali = controller.tabUtenti.getItems().size();
        clickOn("Aggiungi Utente");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());  
        verifyThat("OK", NodeMatchers.isDisabled());
        clickOn("Annulla");
        
        // Verifica che l'utente NON sia stato aggiunto
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(righeIniziali));
    }
    
    /**
    * Test IF-1.1: Inserimento Utente Fallito (Campi Errati).
    * Tenta di inserire un utente inserendo nei campi formati non validi.
    */
    @Test
    public void testAggiungiUtenteFormatoErrato() {
        int righeIniziali = controller.tabUtenti.getItems().size();
        clickOn("Aggiungi Utente");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());  
        clickOn("#nomeField").write("Angelo");
        clickOn("#cognomeField").write("Mastandrea");
        clickOn("#matricolaField").write("MatricolaErrata"); 
        clickOn("#emailField").write("EmailErrata");

        verifyThat("OK", NodeMatchers.isDisabled());
        clickOn("Annulla");
        
        // Verifica che l'utente NON sia stato aggiunto 
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(righeIniziali));
    }

    
    // -------------------------------------------------- TEST CANCELLAZIONE --------------------------------------------
    /**
     * Test IF-1.3: Cancellazione di un Utente (Successo).
     * Simula la selezione di un Utente, il click su "Cancella Utente" e il click su "Conferma".
     */
    @Test
    public void testCancellaUtenteSuccesso() {
        int righeIniziali = controller.tabUtenti.getItems().size();            
        interact(() -> {
             controller.tabUtenti.getSelectionModel().clearSelection();              //Deselezione di tutti i campi
             Utente target = controller.listaUtenti.stream()
                 .filter(l -> l.getNome().equals("Aldo"))         //Questo utente non ha prestiti attivi -> getPrestitiAttivi().size() == 0
                 .findFirst()
                 .orElseThrow(() -> new RuntimeException("Utente non trovato"));
             controller.tabUtenti.getSelectionModel().select(target);
        });
        
        clickOn("#cancUtenteBtn");
                
        // Verifica Alert di Conferma (Generato dal Controller)
        verifyThat("Confermi la cancellazione dell'Utente selezionato?", NodeMatchers.isVisible());   //Controlla il testo dell'Alert per vedere se è quello giusto
        clickOn("OK");
        
        // Verifica rimozione dalla tabella 
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(righeIniziali - 1));
    }
    
    /**
     * Test IF-1.3: Cancellazione di un Utente (Annnullata).
     * Simula la selezione di un Utente, il click su "Cancella Utente" e il click su "Annulla".
     */
    @Test
    public void testCancellaUtenteAnnullaConferma() {
        int righeIniziali = controller.tabUtenti.getItems().size();
        interact(() -> {
            Utente target = controller.listaUtenti.stream()
                .filter(l -> l.getNome().equals("Gabriele"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
            controller.tabUtenti.getSelectionModel().select(target);
        });

        clickOn("#cancUtenteBtn");
        
        // Verifica Alert di Conferma (Generato dal Controller)
        verifyThat("Confermi la cancellazione dell'Utente selezionato?", NodeMatchers.isVisible());
        clickOn("Annulla");
        
        // Verifica che il libro sia ancora lì
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(righeIniziali));
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Imparato", "Gabriele", "0000000006", "g.imparato@uni.it", ""));
    }
    
    /**
     * Test IF-1.3: Cancellazione di un Utente (Fallimento - Prestiti Attivi).
     * Tenta la cancellazione di un utente con prestiti Attivi.
     */
    @Test
    public void testCancellaUtenteFallimentoPrestitiAttivi() {
        int righeIniziali = controller.tabUtenti.getItems().size();
        
        interact(() -> {
            controller.tabUtenti.getSelectionModel().clearSelection();
            Utente target = controller.listaUtenti.stream()
                .filter(l -> l.getNome().equals("Laura"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
            target.getPrestitiAttivi().add("PrestitoTest");
            controller.tabUtenti.getSelectionModel().select(target);
        });
        
        clickOn("#cancUtenteBtn");

        // Verifica Alert di Errore (Generato dal Controller)
        verifyThat("L'Utente selezionato per la cancellazione ha ancora prestiti attivi! Non può essere cancellato.", NodeMatchers.isVisible());
        clickOn("Annulla"); 
        
        // Verifica che l'utente sia ancora lì
        verifyThat("#tabUtenti", TableViewMatchers.hasNumRows(righeIniziali));
    }
    
    // -------------------------------------------------- TEST MODIFICA ---------------------------------------------------------
    /**
     * Test IF-1.2: Modifica Matricola (Corta, Non valida).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaMatricolaNonValida() {
        doubleClickOn("0000000001");
        write("1");
        push(KeyCode.ENTER);
        
       // Verifica Alert di Errore (Generato dal Controller)
        verifyThat(".dialog-pane", NodeMatchers.isVisible());
        clickOn("Annulla"); 

        // Verifica che il valore NON sia cambiato
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Pellecchia", "Simone", "0000000001", "s.pellecchia@studenti.uni.it", ""));
    }
    
        /**
     * Test IF-1.2: Modifica Matricola (Duplicata).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaMatricolaDuplicata() {
        doubleClickOn("0000000002");
        write("0000000003");
        push(KeyCode.ENTER);
        
       // Verifica Alert di Errore (Generato dal Controller)
        verifyThat(".dialog-pane", NodeMatchers.isVisible());
        clickOn("Annulla"); 

        // Verifica che il valore NON sia cambiato
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Turi", "Martina", "0000000002", "m.turi@uni.it", ""));
    }
    
    /**
     * Test IF-1.2: Modifica Matricola (Successo).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaMatricolaValida() {
        doubleClickOn("0000000003");
        write("0000000008");
        push(KeyCode.ENTER);

        // Verifica che il valore sia cambiato
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Pascariello", "Vincenzo", "0000000008", "v.pascariello@studenti.uni.it", ""));
    }   
    
    /**
     * Test IF-1.2: Modifica Email (Dominio diverso, Non Valida).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaEmailNonValida() {
        doubleClickOn("a.mastandrea@uni.it");
        write("a.mastandrea@gmail.com");
        push(KeyCode.ENTER);
        
        // Verifica Alert di Errore (Generato dal Controller)
        verifyThat(".dialog-pane", NodeMatchers.isVisible());
        clickOn("Annulla");

        // Verifica che il valore NON sia cambiato
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Mastandrea", "Angelo", "0000000004", "a.mastandrea@uni.it", ""));
    }

    /**
     * Test IF-1.2: Modifica Email (Successo).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaEmailValida() {
        doubleClickOn("a.malinconico@studenti.uni.it"); 
        write("a.malinconico@uni.it");
        push(KeyCode.ENTER);
        
        // Verifica che il valore sia cambiato
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Malinconico", "Aldo", "0000000005", "a.malinconico@uni.it", ""));
    }

    /**
     * Test IF-1.2: Modifica Nome (Vuoto, Non Valido).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaNomeNonValidoVuoto() {
        doubleClickOn("Gabriele");
        
        // Seleziona tutto, poi cancella.
        push(KeyCode.SHORTCUT, KeyCode.A);
        push(KeyCode.BACK_SPACE);
        push(KeyCode.ENTER);

        // Verifica che il controller non mostra un Alert ma ripristina il valore.
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Imparato", "Gabriele", "0000000006", "g.imparato@uni.it", ""));
    }
    
    /**
     * Test IF-1.2: Modifica Nome (Successo).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaNomeValido() {
        doubleClickOn("Vincenzo");
        write("Vinny");
        push(KeyCode.ENTER);

        // Verifica che il valore sia cambiato
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Pascariello", "Vinny", "0000000003", "v.pascariello@studenti.uni.it", ""));
    }
    
    /**
     * Test IF-1.2: Modifica Cognome (Spazio, Non Valido).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaCognomeNonValidoSpazio() {
        doubleClickOn("Nigro");
        write(" ");
        push(KeyCode.ENTER);

        // Verifica che il controller non mostra un Alert ma ripristina il valore.
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Nigro", "Laura", "0000000007", "l.nigro@studenti.uni.it", ""));
    }
    
    /**
     * Test IF-1.2: Modifica Cognome (Successp).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaCognomeValido() {
        doubleClickOn("Mastandrea");
        write("Mastro");
        push(KeyCode.ENTER);

        // Verifica che il valore sia cambiato
        verifyThat("#tabUtenti", TableViewMatchers.containsRow("Mastro", "Angelo", "0000000004", "a.mastandrea@uni.it", ""));
    }
}