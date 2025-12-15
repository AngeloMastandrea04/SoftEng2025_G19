package G19.SezioneLibri;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.Label;

import static org.testfx.api.FxAssert.verifyThat;
import static org.junit.jupiter.api.Assertions.*;
import org.testfx.util.WaitForAsyncUtils;

public class SezioneLibriControllerTest extends ApplicationTest {

    private SezioneLibriController controller;

    //----------------------------------------- CONFIGURAZIONE AMBIENTE --------------------------------------------------------------------------------
    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlLocation = getClass().getResource("/G19/SezioneLibri/SezioneLibriView.fxml");
        if (fxmlLocation == null) fxmlLocation = getClass().getResource("SezioneLibriView.fxml");
        if (fxmlLocation == null) throw new RuntimeException("File FXML non trovato!");

        String fxmlContent = new String(Files.readAllBytes(Paths.get(fxmlLocation.toURI())), StandardCharsets.UTF_8);
        fxmlContent = fxmlContent.replaceAll("fx:controller=\"[^\"]*\"", "");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlLocation);
        
        controller = new SezioneLibriController();
        loader.setController(controller);

        Parent root = loader.load(new ByteArrayInputStream(fxmlContent.getBytes(StandardCharsets.UTF_8)));
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

    @BeforeEach
    public void setUpData() {
        interact(() -> {
            Biblioteca.getInstance().getListaLibri().clear();
            Biblioteca.getInstance().getListaLibri().addAll(
                new Libro("Clean Code", "Robert C. Martin", 2008, "9780132350884", 5),
                new Libro("Effective Java", "Joshua Bloch", 2017, "9780134685991", 3),
                new Libro("Design Patterns", "Erich Gamma", 1994, "9780201633610", 2),
                new Libro("Refactoring", "Martin Fowler", 1999, "9780201485677", 4),
                new Libro("Domain-Driven Design", "Eric Evans", 2003, "9780321125217", 5),
                new Libro("Test Driven Development", "Kent Beck", 2002, "9780321146533", 6, 4),
                new Libro("The Clean Coder", "Robert C. Martin", 2011, "9780137081073", 5),
                new Libro("Pragmatic Programmer", "Andrew Hunt", 1999, "9780201616224", 4)
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
     * Test IF-2.4: Visualizzazione della lista dei Libri.
     * Verifica che la tabella sia popolata correttamente all'avvio.
     */
    @Test
    public void testInizializzazioneTabella() {
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(8));
        verifyThat("#tabLibri", TableViewMatchers.containsRow(
                "Clean Code", "Robert C. Martin", 2008, "9780132350884", 5, 5
        ));
    }
    
    /**
     * Test IF-2.5: Ricerca di un Libro.
     * Verifica il filtro tramite la barra di ricerca.
     */
    @Test
    public void testRicercaLibroPerTitolo() {
        clickOn("#ricLibro").write("Design");
        verifyThat("#tabLibri", TableViewMatchers.containsRow("Design Patterns", "Erich Gamma", 1994, "9780201633610", 2, 2));
    }

    @Test
    public void testRicercaLibroPerISBN() {
        clickOn("#ricLibro").write("9780201485677");
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(1));
        verifyThat("#tabLibri", TableViewMatchers.containsRow("Refactoring", "Martin Fowler", 1999, "9780201485677", 4, 4));
    }

    @Test
    public void testRicercaNessunRisultato() {
        clickOn("#ricLibro").write("LibroInesistenteXYZ");
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(0));
    }
    
    /**
     * Test Selezione e ButtonCancella.
     * Verifica che il pulsante Cancella si abiliti solo alla selezione.
     */
    @Test
    public void testAbilitazionePulsanteCancella() {
        verifyThat("#cancLibroBtn", NodeMatchers.isDisabled());
        clickOn("Refactoring");
        verifyThat("#cancLibroBtn", NodeMatchers.isEnabled());
    }

    
    // -------------------------------------------------- TEST INSERIMENTO --------------------------------------------
    /**
     * Test IF-2.1: Inserimento delle informazioni di un Libro (Successo).
     * Simula il click su "Aggiungi Libro", compila la Dialog e verifica l'aggiunta.
     */
    @Test
    public void testAggiungiLibroSuccesso() {
        int righeIniziali = controller.tabLibri.getItems().size();
        clickOn("Aggiungi Libro");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());  
        clickOn("#titoloField").write("Il Signore degli Anelli");
        clickOn("#autoriField").write("J.R.R. Tolkien");
        clickOn("#annoField").write("1954");
        clickOn("#isbnField").write("9788845292613");
        clickOn("#copieField").write("10");

        clickOn("OK");
        
        // Verifica che il libro sia stato aggiunto alla tabella
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(righeIniziali + 1));
        verifyThat("#tabLibri", TableViewMatchers.containsRow("Il Signore degli Anelli", "J.R.R. Tolkien", 1954, "9788845292613", 10, 10));
    }
    
    /**
    * Test IF-2.1: Inserimento Libro Fallito (Duplicato).
    * Tenta di inserire un libro con ISBN già esistente.
    */
    @Test
    public void testAggiungiLibroDuplicato() {
        int righeIniziali = controller.tabLibri.getItems().size();
        clickOn("Aggiungi Libro");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());  
        clickOn("#titoloField").write("Clean Code 2");
        clickOn("#autoriField").write("Martin");
        clickOn("#annoField").write("2020");
        clickOn("#isbnField").write("9780132350884");
        clickOn("#copieField").write("5");

        clickOn("OK");
        
        // Verifica Alert di Errore (Generato dal Controller)
        verifyThat("È già presente un Libro avente l'ISBN inserito (9780132350884)!", NodeMatchers.isVisible());       //Verifica che sia apparso l'Alert

        clickOn("Annulla"); 
        
        // Verifica che il libro NON sia stato aggiunto 
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(righeIniziali));
    }
    
    /**
    * Test IF-2.1: Inserimento Libro Fallito (Campi Vuoti).
    * Tenta di inserire un libro lasciando i campi vuoti.
    */
    @Test
    public void testAggiungiLibroCampiVuoti() {
        int righeIniziali = controller.tabLibri.getItems().size();
        clickOn("Aggiungi Libro");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());  
        verifyThat("OK", NodeMatchers.isDisabled());
        clickOn("Annulla");
        
        // Verifica che il libro NON sia stato aggiunto
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(righeIniziali));
    }
    
    /**
    * Test IF-2.1: Inserimento Libro Fallito (Campi Errati).
    * Tenta di inserire un libro inserendo nei campi formati non validi.
    */
    @Test
    public void testAggiungiLibroFormatoNumericoErrato() {
        int righeIniziali = controller.tabLibri.getItems().size();
        clickOn("Aggiungi Libro");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());  
        clickOn("#titoloField").write("Test Errori");
        clickOn("#autoriField").write("Autore");
        clickOn("#annoField").write("AnnoErrato"); 
        clickOn("#isbnField").write("1234567890123");
        clickOn("#copieField").write("-5"); 

        verifyThat("OK", NodeMatchers.isDisabled());
        clickOn("Annulla");
        
        // Verifica che il libro NON sia stato aggiunto 
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(righeIniziali));
    }

    
    // -------------------------------------------------- TEST CANCELLAZIONE --------------------------------------------
    /**
     * Test IF-2.3: Cancellazione di un Libro (Successo).
     * Simula la selezione di un Libro, il click su "Cancella Libro" e il click su "OK".
     */
    @Test
    public void testCancellaLibroSuccesso() {
        int righeIniziali = controller.tabLibri.getItems().size();            
        interact(() -> {
             controller.tabLibri.getSelectionModel().clearSelection();              //Deselezione di tutti i campi
             Libro target = controller.listaLibri.stream()
                 .filter(l -> l.getTitolo().equals("Domain-Driven Design"))         //Questo libro non ha copie in prestito -> CopieTotali == CopieDisponibili
                 .findFirst()
                 .orElseThrow(() -> new RuntimeException("Libro non trovato"));
             controller.tabLibri.getSelectionModel().select(target);
        });
        
        clickOn("#cancLibroBtn");
                
        // Verifica Alert di Conferma (Generato dal Controller)
        verifyThat("Confermi la cancellazione del Libro selezionato?", NodeMatchers.isVisible());   //Controlla il testo dell'Alert per vedere se è quello giusto
        clickOn("OK");
        
        // Verifica rimozione dalla tabella 
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(righeIniziali - 1));
    }
    
    /**
     * Test IF-2.3: Cancellazione di un Libro (Annullata).
     * Simula la selezione di un Libro, il click su "Cancella Libro" e il click su "Annulla".
     */
    @Test
    public void testCancellaLibroAnnullaConferma() {
        int righeIniziali = controller.tabLibri.getItems().size();
        interact(() -> {
            Libro target = controller.listaLibri.stream()
                .filter(l -> l.getTitolo().equals("Pragmatic Programmer"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Libro non trovato"));
            controller.tabLibri.getSelectionModel().select(target);
        });

        clickOn("#cancLibroBtn");
        
        // Verifica Alert di Conferma (Generato dal Controller)
        verifyThat("Confermi la cancellazione del Libro selezionato?", NodeMatchers.isVisible());
        clickOn("Annulla");
        
        // Verifica che il libro sia ancora lì
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(righeIniziali));
        verifyThat("#tabLibri", TableViewMatchers.containsRow("Pragmatic Programmer", "Andrew Hunt", 1999, "9780201616224", 4, 4));
    }
    
    /**
     * Test IF-2.3: Cancellazione di un Libro (Copie in prestito, Fallimento).
     * Tenta la cancellazione di un libro con copie in prestito.
     */
    @Test
    public void testCancellaLibroFallimentoCopieInPrestito() {
        int righeIniziali = controller.tabLibri.getItems().size();
        
        interact(() -> {
            controller.tabLibri.getSelectionModel().clearSelection();
            Libro libroTarget = Biblioteca.getInstance().getListaLibri().stream()
                .filter(l -> l.getTitolo().equals("Test Driven Development"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Libro non trovato per il test"));
            controller.tabLibri.getSelectionModel().select(libroTarget);
        });
        
        clickOn("#cancLibroBtn");

        // Verifica Alert di Errore (Generato dal Controller)
        verifyThat("Il Libro selezionato per la cancellazione ha ancora sue copie in prestitoi! Non può essere cancellato.", NodeMatchers.isVisible());
        clickOn("Annulla"); 
        
        // Verifica che il libro sia ancora lì
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(righeIniziali));
    }
    
    // -------------------------------------------------- TEST MODIFICA ---------------------------------------------------------
    /**
     * Test IF-2.2: Modifica Anno (Futuro, Non valido).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaAnnoNonValidoFuturo() {
        int annoFuturo = LocalDate.now().getYear() + 5;
        clickOn("2011");
        clickOn("2011"); // Doppio click sull'anno 
        write(String.valueOf(annoFuturo));
        push(KeyCode.ENTER);
        
       // Verifica Alert di Errore (Generato dal Controller)
        verifyThat(".dialog-pane", NodeMatchers.isVisible());
        clickOn("Annulla"); 

        // Verifica che il valore NON sia cambiato
        verifyThat("#tabLibri", TableViewMatchers.containsRow("The Clean Coder", "Robert C. Martin", 2011, "9780137081073", 5, 5));
    }
    
    /**
     * Test IF-2.2: Modifica Anno (Successo).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaAnnoValido() {
        int anno = LocalDate.now().getYear();
        clickOn("2011");
        clickOn("2011"); // Doppio click sull'anno 
        write(String.valueOf(anno));
        push(KeyCode.ENTER);
        
        // Verifica che il valore sia cambiato
        verifyThat("#tabLibri", TableViewMatchers.containsRow("The Clean Coder", "Robert C. Martin", anno, "9780137081073", 5, 5));
    }
    
    /**
     * Test IF-2.2: Modifica ISBN (Duplicato, Fallimento).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaIsbnDuplicato() {
        clickOn("9780134685991");
        clickOn("9780134685991");   // Doppio click sul ISBN 
        
        String nuovoIsbn = "9780201616224";
        write(nuovoIsbn);
        push(KeyCode.ENTER);
        
        // Verifica Alert di Errore (Generato dal Controller)
        verifyThat(".dialog-pane", NodeMatchers.isVisible());
        clickOn("Annulla");
        
        // Verifica che il valore NON sia cambiato
        verifyThat("#tabLibri", TableViewMatchers.containsRow("Effective Java", "Joshua Bloch", 2017, "9780134685991", 3, 3));
    }
    
    /**
     * Test IF-2.2: Modifica ISBN (Non Valido).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaIsbnNonValido() {
        clickOn("9780134685991");
        clickOn("9780134685991");   // Doppio click sul ISBN 
        
        String nuovoIsbn = "9991234567890";
        write(nuovoIsbn);
        push(KeyCode.ENTER);
        
        // Verifica Alert di Errore (Generato dal Controller)
        verifyThat(".dialog-pane", NodeMatchers.isVisible());
        clickOn("Annulla");
        
        // Verifica che il valore NON sia cambiato
        verifyThat("#tabLibri", TableViewMatchers.containsRow("Effective Java", "Joshua Bloch", 2017, "9780134685991", 3, 3));
    }
    
    /**
     * Test IF-2.2: Modifica ISBN (Successo).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaIsbnValido() {
        clickOn("9780134685991");
        clickOn("9780134685991");   // Doppio click sul ISBN 
        
        String nuovoIsbn = "9781234567890";
        write(nuovoIsbn);
        push(KeyCode.ENTER);
        
        // Verifica che il valore sia cambiato
        verifyThat("#tabLibri", TableViewMatchers.containsRow("Effective Java", "Joshua Bloch", 2017, nuovoIsbn, 3, 3));
    }
    
    /**
     * Test IF-2.2: Modifica Titolo (Vuoto, Non Valido).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaTitoloNonValidoVuoto() {
        clickOn("Clean Code");
        clickOn("Clean Code");  // Doppio click sul Titolo 
        
        // Seleziona tutto, poi cancella.
        push(KeyCode.SHORTCUT, KeyCode.A);
        push(KeyCode.BACK_SPACE);
        push(KeyCode.ENTER);

        // Verifica che il controller non mostra un Alert ma ripristina il valore.
        verifyThat("#tabLibri", TableViewMatchers.containsRow("Clean Code", "Robert C. Martin", 2008, "9780132350884", 5, 5));
    }
    
    /**
     * Test IF-2.2: Modifica Autori (Spazio, Non Valido).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaAutoriNonValidoSpazio() {
        clickOn("Robert C. Martin");
        clickOn("Robert C. Martin");  // Doppio click sul Titolo 
        
        // Seleziona tutto, poi cancella.
        write(" ");
        push(KeyCode.ENTER);

        // Verifica che il controller non mostra un Alert ma ripristina il valore.
        verifyThat("#tabLibri", TableViewMatchers.containsRow("Clean Code", "Robert C. Martin", 2008, "9780132350884", 5, 5));
    }
     
    /**
     * Test IF-2.2: Modifica CopieTotali (Inferiori alle CopieDisponibili,Non Valido).
     * Verifica che la modifica sulla cella effetui i controlli.
     */
    @Test
    public void testModificaCopieTotaliInferioriAlPrestito() {
        // Scenario: Un libro ha 2 copie totali e 1 in prestito. 
        interact(() -> {
            Libro libro = Biblioteca.getInstance().getListaLibri().stream()
                .filter(l -> l.getTitolo().equals("Design Patterns"))
                .findFirst()
                .get();
            // Totali 2, Disponibili 1
            libro.setCopieDisponibili(1); 
        });

        // Clicca la riga, poi naviga a destra fino alla colonna copie per evitare problemi con il focus. Titolo -> Autori -> Anno -> ISBN -> CopieTotali
        clickOn("Design Patterns");
        type(KeyCode.HOME); 
        type(KeyCode.RIGHT); 
        type(KeyCode.RIGHT); 
        type(KeyCode.RIGHT); 
        type(KeyCode.RIGHT); 
        
        push(KeyCode.ENTER); 
        
        write("0");
        push(KeyCode.ENTER);
        
        // Verifica Alert di Errore (Generato dal Controller)
        verifyThat(".dialog-pane", NodeMatchers.isVisible());
        verifyThat(".dialog-pane .content", (Label label) -> 
            label.getText().contains("Le copieTotali inserite (0) non sono valide! Ci deve essere almeno una copia del Libro."));
        
        clickOn("Annulla");
    }
    
      /** Test IF-2.2: Modifica Campi con Prestiti Attivi (Bloccata).
     *  Verifica che la modifica sulla cella non sia possibile.
     */
    @Test
    public void testModificaUtenteFallimentoPrestitiAttivi() {
        interact(() -> {
            controller.tabLibri.getSelectionModel().clearSelection();
            Libro target = controller.listaLibri.stream()
                .filter(l -> l.getTitolo().equals("Test Driven Development"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
            controller.tabLibri.getSelectionModel().select(target);
        });
        
        clickOn("Test Driven Development");
        
        // Verifica che le colonne non siano modificabili
        assertFalse(controller.cTitolo.isEditable());
        assertFalse(controller.cAutori.isEditable());
        assertFalse(controller.cAnno.isEditable());
        assertFalse(controller.cIsbn.isEditable());
        assertFalse(controller.cCopieTotali.isEditable());
        
        clickOn("Pragmatic Programmer");
        
        // Verifica che le colonne siano nuovamente modificabili
        assertTrue(controller.cTitolo.isEditable());
        assertTrue(controller.cAutori.isEditable());
        assertTrue(controller.cAnno.isEditable());
        assertTrue(controller.cIsbn.isEditable());
        assertTrue(controller.cCopieTotali.isEditable());
    }
    
    /**
     * Test Scalabilità: Inserimento di 3000 libri.
     * Verifica che la tabella gestisca correttamente un alto volume di libri.
     */
    @Test
    public void testScalabilitaTremilaLibri() {
        //Creazione ed aggiunta libri che non generano conflitti.
        List<Libro> tremilaLibri = new ArrayList<>();
        for (int i = 0; i < 3000; i++) {
            tremilaLibri.add(new Libro(
                "Libro Scalabilità " + i, 
                "Autore Test", 
                2024, 
                "ISBN-TEST-" + i, 
                5
            ));
        }

        interact(() -> {
            Biblioteca.getInstance().getListaLibri().addAll(tremilaLibri);
        });

        WaitForAsyncUtils.waitForFxEvents();                            //Attesa esplicita per l'aggiornamento della tabella
 
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(3008));    //Verifica 8 libri iniziali + 3000 aggiunti = 3008

        clickOn("#ricLibro").write("ISBN-TEST-2999");                   //Verifica che l'ultimo libro sia ricercabile
        verifyThat("#tabLibri", TableViewMatchers.hasNumRows(1));
        
        interact(() -> {
            Biblioteca.getInstance().getListaLibri().removeAll(tremilaLibri);
        });
    }
}