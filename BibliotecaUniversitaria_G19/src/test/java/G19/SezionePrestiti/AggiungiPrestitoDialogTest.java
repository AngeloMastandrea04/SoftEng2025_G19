package G19.SezionePrestiti;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import G19.Biblioteca.Biblioteca;
import G19.SezioneLibri.Libro;
import G19.SezioneUtenti.Utente;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.input.KeyCode;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

public class AggiungiPrestitoDialogTest extends ApplicationTest{

     // Poiché JavaFX gira su un thread dedicato, uso un riferimento atomico per catturare il risultato della Dialog in modo thread-safe.
    // AtomicReference è un contenitore che permette di "trasportare" l'oggetto Libro creato dalla Dialog nel thread di test per poter fare le verifiche.
    private final AtomicReference<Prestito> risultatoDialog = new AtomicReference<>();
    
    //-----------------------------------------CONFIGURAZIONE AMBIENTE--------------------------------------------------------------------------------

    /*Poiché AggiungiLibroDialog è una finestra bloccante, chiamata direttamente nel test lo bloccherebbe aspettando un input.
    Per evitare questo creo una "scena di supporto" con un bottone che, quando cliccato, apre la dialog. */
    @Override
    public void start(Stage stage) {
        Button openDialogBtn = new Button("Apri Dialog");
        openDialogBtn.setId("openBtn");
        
        openDialogBtn.setOnAction(event -> {
            AggiungiPrestitoDialog dialog = new AggiungiPrestitoDialog();
            Optional<Prestito> result = dialog.showAndWait();
            result.ifPresent(libro -> risultatoDialog.set(libro));
        });

        stage.setScene(new Scene(new StackPane(openDialogBtn), 400, 200));
        stage.show();
    }

    @BeforeEach
    public void resetResult() {
        risultatoDialog.set(null);                      // Resetta il risultato prima di ogni test
    }

    @BeforeEach
    public void setUpData() {
        Utente u1= new Utente("Vinny","Pasca","0123456789","vinny@studenti.uni.it");
        Utente u2= new Utente("Martin","Turi","1123456789","martin@studenti.uni.it");
        Utente u3= new Utente("Simon","Pelle","2123456789","simon@studenti.uni.it");

        Libro l1= new Libro("Norwegian wood", "Murakami", 2004 , "9780123456789" , 3);
        Libro l2= new Libro("L'arte di correre", "Murakami", 2004 , "9781123456789" , 3);
        Libro l3= new Libro("Notti Bianche", "Dostoevsky", 2012 , "9782123456789" , 3);

        Prestito p1= new Prestito(u1.toStringPrestito(), l1.toStringPrestito() , LocalDate.now().plusDays(3));
        u1.getPrestitiAttivi().add(p1.toStringUtente()); l1.setCopieDisponibili(l1.getCopieDisponibili()-1);
        //Martin Turi con 3 PrestitiAttivi
        Prestito p2= new Prestito(u2.toStringPrestito(), l1.toStringPrestito() , LocalDate.now().plusDays(3));
        u2.getPrestitiAttivi().add(p2.toStringUtente()); l1.setCopieDisponibili(l1.getCopieDisponibili()-1);

        Prestito p3= new Prestito(u2.toStringPrestito(), l2.toStringPrestito() , LocalDate.now().plusDays(3));
        u2.getPrestitiAttivi().add(p3.toStringUtente()); l2.setCopieDisponibili(l2.getCopieDisponibili()-1);

        Prestito p4= new Prestito(u2.toStringPrestito(), l3.toStringPrestito() , LocalDate.now().plusDays(3));
        u2.getPrestitiAttivi().add(p4.toStringUtente()); l3.setCopieDisponibili(l3.getCopieDisponibili()-1);

        //Aggiungiamo un Utente con il prestito scaduto
        Prestito p5= new Prestito(u3.toStringPrestito(), l1.toStringPrestito() , LocalDate.now().minusDays(1));
        u3.getPrestitiAttivi().add(p5.toStringUtente()); l1.setCopieDisponibili(l1.getCopieDisponibili()-1);

        interact(() -> {
            Biblioteca.getInstance().getListaPrestiti().clear();
            Biblioteca.getInstance().getListaLibri().clear();
            Biblioteca.getInstance().getListaUtenti().clear();

            Biblioteca.getInstance().getListaUtenti().addAll(
               u1, u2, u3
            );

            Biblioteca.getInstance().getListaLibri().addAll(
                l1, l2, l3
            );
                                                                           
            Biblioteca.getInstance().getListaPrestiti().addAll(             
                p1,p2,p3,p4,p5
            );
        });
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
        clickOn("#utenteBox .arrow-button");
        WaitForAsyncUtils.waitForFxEvents(); //si aspetta che la finestra sia pronta
        Node cell = lookup(".list-cell")
        .match(n ->
            n instanceof ListCell &&
            n.isVisible() &&
            ((ListCell<?>) n).getText() != null &&
            ((ListCell<?>) n).getText().equals("0123456789 - Pasca Vinny")
        )
        .query();
        clickOn(cell);

       clickOn("#libroBox .arrow-button");
        WaitForAsyncUtils.waitForFxEvents(); //si aspetta che la finestra sia pronta
        ComboBox<Libro> libroBox = lookup("#libroBox").queryComboBox();
    interact(() ->         
        libroBox.getSelectionModel().select(
            libroBox.getItems().stream()
                .filter(u -> u.toStringPrestito().contains("9781123456789 - L'arte di correre, Murakami"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Libro non trovato"))
        )
    );
        clickOn("#dataRestituzionePicker");
        clickOn("#dataRestituzionePicker .arrow-button");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(".next-month"); //ci assicuriamo che selezioni una data futura
        WaitForAsyncUtils.waitForFxEvents();
        
        verifyThat("OK", NodeMatchers.isEnabled());
        sleep(1000);
        clickOn("OK");
        // Verifica il risultato dell'oggetto Libro creato
        Prestito prestitoCreato = risultatoDialog.get();
        assertNotNull(prestitoCreato, "Il risultato della dialog non dovrebbe essere null");
        assertEquals("Utente: " + "0123456789 - Pasca Vinny" + "; Libro: " + "9781123456789 - L'arte di correre, Murakami" +"; Data di Restituzione: " + LocalDate.now().plusMonths(1).withDayOfMonth(1), prestitoCreato.toString());
    }

    /**
     * Test Validazione Utente.
     * Verifica che l'errore appaia se l'Utente ha già 3 prestiti attivi.
     */
@Test
public void testValidazioneUtente() {
    clickOn("#openBtn");
    clickOn("#utenteBox .arrow-button");

     /*Unico modo per selezionare un elemento che non è il primo in una ComboBox in TestFX dato
     che click on funziona solo sui Node renderizzati, e nella simulazione solo la prima entry 
     della lista è renderizzata*/
    ComboBox<Utente> utenteBox = lookup("#utenteBox").queryComboBox();
    interact(() ->         
        utenteBox.getSelectionModel().select(
            utenteBox.getItems().stream()
                .filter(u -> u.toStringPrestito().contains("1123456789 - Turi Martin"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Utente non trovato"))
        )
    );
    WaitForAsyncUtils.waitForFxEvents();
    verifyThat("#utenteError", NodeMatchers.isVisible()); 
    clickOn();

clickOn("#libroBox .arrow-button");
        WaitForAsyncUtils.waitForFxEvents(); //si aspetta che la finestra sia pronta
        ComboBox<Libro> libroBox = lookup("#libroBox").queryComboBox();
    interact(() ->         
        libroBox.getSelectionModel().select(
            libroBox.getItems().stream()
                .filter(u -> u.toStringPrestito().contains("9781123456789 - L'arte di correre, Murakami"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Libro non trovato"))
        )
    );
        clickOn("#dataRestituzionePicker");
        clickOn("#dataRestituzionePicker .arrow-button");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(".next-month"); //ci assicuriamo che selezioni una data futura
        WaitForAsyncUtils.waitForFxEvents();

    sleep(2000);

    verifyThat("OK", NodeMatchers.isDisabled());

    //Ora cambio l'utente in modo da inserirne uno valido
    clickOn("#utenteBox").eraseText(50);
    clickOn("#utenteBox .arrow-button");

    interact(() ->         
        utenteBox.getSelectionModel().select(
            utenteBox.getItems().stream()
                .filter(u -> u.toStringPrestito().contains("0123456789 - Pasca Vinny"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Utente non trovato"))
        )
    );
    WaitForAsyncUtils.waitForFxEvents();
    clickOn();
    sleep(2000);
    clickOn("OK");
}

 /**
     * Test Validazione Libro.
     * Verifica che l'errore appaia se il Libro non ha copie disponibili.
     */
@Test
public void testValidazioneLibro() {
    clickOn("#openBtn");

    clickOn("#utenteBox .arrow-button");
        WaitForAsyncUtils.waitForFxEvents(); //si aspetta che la finestra sia pronta
        Node cell = lookup(".list-cell")
        .match(n ->
            n instanceof ListCell &&
            n.isVisible() &&
            ((ListCell<?>) n).getText() != null &&
            ((ListCell<?>) n).getText().equals("0123456789 - Pasca Vinny")
        )
        .query();
        clickOn(cell);

    clickOn("#libroBox .arrow-button");

     /*Unico modo per selezionare un elemento che non è il primo in una ComboBox in TestFX dato
     che click on funziona solo sui Node renderizzati, e nella simulazione solo la prima entry 
     della lista è renderizzata*/
    ComboBox<Libro> libroBox = lookup("#libroBox").queryComboBox();
    interact(() ->         
        libroBox.getSelectionModel().select(
            libroBox.getItems().stream()
                .filter(u -> u.toStringPrestito().contains("9780123456789 - Norwegian wood, Murakami"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Libro non trovato"))
        )
    );
    WaitForAsyncUtils.waitForFxEvents();
    verifyThat("#libroError", NodeMatchers.isVisible()); 
    clickOn();

        clickOn("#dataRestituzionePicker");
        clickOn("#dataRestituzionePicker .arrow-button");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(".next-month"); //ci assicuriamo che selezioni una data futura
        WaitForAsyncUtils.waitForFxEvents();

    sleep(2000);

    verifyThat("OK", NodeMatchers.isDisabled());

    //Ora cambio il Libro in modo da inserirne uno valido
    interact(() -> libroBox.getSelectionModel().clearSelection()); //cancello contenuto precedente
    WaitForAsyncUtils.waitForFxEvents();

    clickOn("#libroBox .arrow-button");
    interact(() ->         
        libroBox.getSelectionModel().select(
            libroBox.getItems().stream()
                .filter(l -> l.toStringPrestito().contains("9781123456789 - L'arte di correre, Murakami"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Libro non trovato"))
        )
    );
    WaitForAsyncUtils.waitForFxEvents();
    clickOn();
    sleep(2000);
    clickOn("OK");
}
/**
     * Test Validazione Data.
     * Verifica che l'errore appaia se la data è passata.
     */
@Test
public void testValidazioneData() {
    clickOn("#openBtn");
     clickOn("#utenteBox .arrow-button");
        WaitForAsyncUtils.waitForFxEvents(); //si aspetta che la finestra sia pronta
        Node cell = lookup(".list-cell")
        .match(n ->
            n instanceof ListCell &&
            n.isVisible() &&
            ((ListCell<?>) n).getText() != null &&
            ((ListCell<?>) n).getText().equals("0123456789 - Pasca Vinny")
        )
        .query();
        clickOn(cell);//Non è possibile selezionare in modo più semplice dalla lista perchè le combo box rimangono in memoria, e clickOn non sa su quale lista cliccare

        clickOn("#libroBox .arrow-button");
        WaitForAsyncUtils.waitForFxEvents(); //si aspetta che la finestra sia pronta
       
     /*Unico modo per selezionare un elemento che non è il primo in una ComboBox in TestFX dato
     che click on funziona solo sui Node renderizzati, e nella simulazione solo la prima entry 
     della lista è renderizzata*/
     ComboBox<Libro> libroBox = lookup("#libroBox").queryComboBox();
    interact(() ->         
        libroBox.getSelectionModel().select(
            libroBox.getItems().stream()
                .filter(u -> u.toStringPrestito().contains("9781123456789 - L'arte di correre, Murakami"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Libro non trovato"))
        )
    );
        
        clickOn("#dataRestituzionePicker .arrow-button");
        WaitForAsyncUtils.waitForFxEvents();
        DatePicker picker = lookup("#dataRestituzionePicker").query();
        Platform.runLater(() -> picker.setValue(LocalDate.now().minusDays(1)));
        WaitForAsyncUtils.waitForFxEvents();
        sleep(1500);

        verifyThat("OK", NodeMatchers.isDisabled());
        
        //Selezione data nel futuro (corretta)
        clickOn("#dataRestituzionePicker .arrow-button");
        WaitForAsyncUtils.waitForFxEvents();
        DatePicker picker1 = lookup("#dataRestituzionePicker").query();
        Platform.runLater(() -> picker1.setValue(LocalDate.now().plusDays(1)));
        WaitForAsyncUtils.waitForFxEvents();
        sleep(1000);
        clickOn("#dataRestituzionePicker");//clicca fuori per far scomparire la DatePicker
        sleep(1500);

        verifyThat("OK", NodeMatchers.isEnabled());
        clickOn("OK");
}

//------------------------------------------------------TEST CHIUSURA SENZA CONFERMA---------------------------------------------------------------------
    /**
     * Test Chiusura senza conferma.
     * Verifica che se si chiude la dialog senza OK, il risultato sia nullo.
     */
    @Test
    public void testAnnullaInserimento() {
        clickOn("#openBtn");

        clickOn("#utenteBox .arrow-button");

    //Selezioniamo un utente che non è in prima posizione nella ComboBox
    ComboBox<Utente> utenteBox = lookup("#utenteBox").queryComboBox();
    interact(() ->         
        utenteBox.getSelectionModel().select(
            utenteBox.getItems().stream()
                .filter(u -> u.toStringPrestito().contains("2123456789 - Pelle Simon"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Utente non trovato"))
        )
    );
    clickOn("#utenteBox .arrow-button");
        sleep(1000);

        clickOn("Annulla");
        assertNull(risultatoDialog.get());
    }

}
