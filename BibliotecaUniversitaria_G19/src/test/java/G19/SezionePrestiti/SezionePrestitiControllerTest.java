/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G19.SezionePrestiti;
import G19.SezioneUtenti.Utente;
import G19.SezioneLibri.Libro;
import G19.SezionePrestiti.Prestito;
import G19.Biblioteca.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import G19.SezioneLibri.Libro;
import javafx.fxml.FXMLLoader;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.util.WaitForAsyncUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

import javafx.application.Application;
import javafx.application.Platform;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeoutException;


import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;



/**
 *
 * @author Vincenzo
 */
public class SezionePrestitiControllerTest extends ApplicationTest{

    private SezionePrestitiController controller;
    Scene scene;
   


    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = getClass().getResource("/G19/SezionePrestiti/SezionePrestitiView.fxml");
        if (fxmlLocation == null) fxmlLocation = getClass().getResource("SezionePrestitiView.fxml");
        if (fxmlLocation == null) throw new RuntimeException("File FXML non trovato!");
        controller= new SezionePrestitiController();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("SezionePrestitiView.fxml")); ///MODIFICATO PRIMA C ERA SEZIONELIBRIVIEW
        fxmlLoader.setController(controller);
        fxmlLoader.setLocation(fxmlLocation);
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUpData() {
        Utente u1= new Utente("Vinny","Pasca","0123456789","vinny@studenti.uni.it");
        Utente u2= new Utente("Martin","Turi","1123456789","martin@studenti.uni.it");
        Utente u3= new Utente("Simon","Pelle","2123456789","simon@studenti.uni.it");

        Libro l1= new Libro("Norwegian wood", "Murakami", 2004 , "9780123456789" , 3);
        Libro l2= new Libro("L'arte di correre", "Murakami", 2004 , "9781123456789" , 3);

        Prestito p1= new Prestito(u1.toStringPrestito(), l1.toStringPrestito() , LocalDate.now().plusDays(3));
        u1.getPrestitiAttivi().add(p1.toStringUtente()); l1.setCopieDisponibili(l1.getCopieDisponibili()-1);
        Prestito p2= new Prestito(u2.toStringPrestito(), l2.toStringPrestito() , LocalDate.now().plusDays(3));
        u2.getPrestitiAttivi().add(p2.toStringUtente()); l2.setCopieDisponibili(l2.getCopieDisponibili()-1);
        Prestito p3= new Prestito(u3.toStringPrestito(), l2.toStringPrestito() , LocalDate.now().minusDays(1));
        u3.getPrestitiAttivi().add(p3.toStringUtente()); l2.setCopieDisponibili(l2.getCopieDisponibili()-1);

        interact(() -> {
            Biblioteca.getInstance().getListaPrestiti().clear();
            Biblioteca.getInstance().getListaLibri().clear();
            Biblioteca.getInstance().getListaUtenti().clear();

            Biblioteca.getInstance().getListaUtenti().addAll(
               u1, u2, u3
            );

            Biblioteca.getInstance().getListaLibri().addAll(
                l1, l2
            );
                                                                           
            Biblioteca.getInstance().getListaPrestiti().addAll(             
                p1, p2,
                //Aggiungiamo un Utente con il prestito scaduto
                p3
            );
        });
    }
    
   @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new KeyCode[]{});    
    }

    @Test
    public void testInizializzazioneTabella() {
        verifyThat("#tabPrestiti", TableViewMatchers.hasNumRows(3));
        verifyThat("#tabPrestiti",
        TableViewMatchers.containsRow("0123456789 - Pasca Vinny" , "9780123456789 - Norwegian wood, Murakami" ,  LocalDate.now().plusDays(3))
        );
        verifyThat("#tabPrestiti",
        TableViewMatchers.containsRow("1123456789 - Turi Martin" , "9781123456789 - L'arte di correre, Murakami" ,  LocalDate.now().plusDays(3)
)
        );
         verifyThat("#tabPrestiti",
         
        TableViewMatchers.containsRow("2123456789 - Pelle Simon" , "9781123456789 - L'arte di correre, Murakami" ,  LocalDate.now().minusDays(1))
        );

    }

    /**
     * Test Selezione e ButtonCancella.
     * Verifica che il pulsante Cancella si abiliti solo alla selezione.
     */
    @Test
    public void testAbilitazionePulsanteCancella() {
        sleep(1000);
        verifyThat("#cancPrestitoBtn", NodeMatchers.isDisabled());
        clickOn("0123456789 - Pasca Vinny");
        verifyThat("#cancPrestitoBtn", NodeMatchers.isEnabled());
        sleep(1000);
    }

    /**
     * Test IF-3.1: Registrazione di un prestito(Successo).
     * Simula il click su "Registra Prestito", compila la Dialog e verifica l'aggiunta.
     */
    @Test
    public void testAggiungiPrestitoSuccesso() {
        sleep(1000);
        int righeIniziali = controller.tabPrestiti.getItems().size();

        //Seleziono l'utente e il libro dalla lista del controller 
        Utente utente= controller.listaUtenti.stream()
                .filter( l  -> l.toStringPrestito().equals("0123456789 - Pasca Vinny")).findFirst().orElse(null);
        Libro libro= controller.listaLibri.stream()
                .filter( l  -> l.toStringPrestito().equals("9780123456789 - Norwegian wood, Murakami")).findFirst().orElse(null);
        int numPrestitiIniziali= utente.getPrestitiAttivi().size();
        int copieDisponibiliIniziali= libro.getCopieDisponibili();

        clickOn("Registra Prestito");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());

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
        Node cell1 = lookup(".list-cell")
        .match(n ->
            n instanceof ListCell &&
            n.isVisible() &&
            ((ListCell<?>) n).getText() != null &&
            ((ListCell<?>) n).getText().equals("9780123456789 - Norwegian wood, Murakami")
        )
        .query();
        clickOn(cell1);
        
        clickOn("#dataRestituzionePicker .arrow-button");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(".next-month"); //ci assicuriamo che selezioni una data futura
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("OK");
        sleep(3000);
        
        // Verifica che il Prestito sia stato aggiunto alla tabella
        verifyThat("#tabPrestiti", TableViewMatchers.hasNumRows(righeIniziali + 1));
        verifyThat("#tabPrestiti", TableViewMatchers.containsRow("0123456789 - Pasca Vinny" , "9780123456789 - Norwegian wood, Murakami" ,  LocalDate.now().plusDays(3)));

        //Controlli sugli attributi di Utente e Libro coinvolti
        assertEquals(numPrestitiIniziali + 1, utente.getPrestitiAttivi().size());
        assertEquals(copieDisponibiliIniziali - 1, libro.getCopieDisponibili());
    }



/**
    * Test IF-3.1: Registrazione di un Prestito Fallito(Campi vuoti).
    * Tenta di inserire un Prestito lasciando i campi vuoti.
    */
    @Test
    public void testAggiungiPrestitoCampiVuoti() {
        sleep(1000);
        int righeIniziali = controller.tabPrestiti.getItems().size();
        clickOn("Registra Prestito");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());  
        verifyThat("OK", NodeMatchers.isDisabled());
        sleep(1500);
        clickOn("Annulla");

        sleep(2000);
        
        // Verifica che il Prestito NON sia stato aggiunto
        verifyThat("#tabPrestiti", TableViewMatchers.hasNumRows(righeIniziali));
    }

    /**
    * Test IF-3.1: Registrazione di un Prestito Fallito(Campi errati).
    * Tenta di inserire un Prestito inserendo nei campi formati non validi.
    */
    @Test
    public void testAggiungiPrestitoPreviousDate() {
        int righeIniziali = controller.tabPrestiti.getItems().size();
        sleep(1000);

        clickOn("Registra Prestito");
        
        verifyThat(".dialog-pane", NodeMatchers.isVisible());

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
        Node cell1 = lookup(".list-cell")
        .match(n ->
            n instanceof ListCell &&
            n.isVisible() &&
            ((ListCell<?>) n).getText() != null &&
            ((ListCell<?>) n).getText().equals("9780123456789 - Norwegian wood, Murakami")
        )
        .query();
        clickOn(cell1);
        
        clickOn("#dataRestituzionePicker .arrow-button");
        WaitForAsyncUtils.waitForFxEvents();

        sleep(1000);
        //ci assicuriamo che selezioni una data precedente
        DatePicker picker = lookup("#dataRestituzionePicker").query();
        Platform.runLater(() -> picker.setValue(LocalDate.now().minusMonths(1)));
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#dataRestituzionePicker");//clicca fuori per far scomparire la DatePicker
        verifyThat("OK", NodeMatchers.isDisabled());
        sleep(1000);
        clickOn("Annulla");
        
        sleep(1000);
        // Verifica che il Prestito NON sia stato aggiunto 
        verifyThat("#tabPrestiti", TableViewMatchers.hasNumRows(righeIniziali));
    }

     // -------------------------------------------------- TEST CANCELLAZIONE --------------------------------------------
    /**
     * IF-3.2: Registrazione di una restituzione (Successo).
     * Simula la selezione di un Prestito, il click su "Registra Restituzione" e il click su "OK".
     * Inoltre controlla la corretta modifica degli attributi di Utente e Libro coinvolti.
     */
    @Test
    public void testRegistraRestituzioneSuccesso() {
        int righeIniziali = controller.tabPrestiti.getItems().size(); 
        String prestito = "Utente: " + "0123456789 - Pasca Vinny" + "; Libro: " + "9780123456789 - Norwegian wood, Murakami" +"; Data di Restituzione: " + LocalDate.now().plusDays(3);
        
        //Seleziono l'utente e il libro dalla lista del controller 
        Utente utente= controller.listaUtenti.stream()
                .filter( l  -> l.toStringPrestito().equals("0123456789 - Pasca Vinny")).findFirst().orElse(null);
        Libro libro= controller.listaLibri.stream()
                .filter( l  -> l.toStringPrestito().equals("9780123456789 - Norwegian wood, Murakami")).findFirst().orElse(null);
        int numPrestitiIniziali= utente.getPrestitiAttivi().size();
        int copieDisponibiliIniziali= libro.getCopieDisponibili();


        interact(() -> {
             controller.tabPrestiti.getSelectionModel().clearSelection();              //Deselezione di tutti i campi
             Prestito target = controller.listaPrestiti.stream()
                 .filter(p -> p.toString().equals(prestito))        //Filtra dalla Lista del controller il prestito
                 .findFirst()
                 .orElseThrow(() -> new RuntimeException("Prestito"));
             controller.tabPrestiti.getSelectionModel().select(target);
        });

        sleep(1000);
        clickOn("#cancPrestitoBtn");
                
        // Verifica Alert di Conferma (Generato dal Controller)
        verifyThat("Confermi la cancellazione del Prestito selezionato?", NodeMatchers.isVisible());   //Controlla il testo dell'Alert per vedere se è quello giusto
        clickOn("OK");
        
        sleep(3000);
        // Verifica rimozione dalla tabella 
        verifyThat("#tabPrestiti", TableViewMatchers.hasNumRows(righeIniziali - 1));

         //Controlli sugli attributi di Utente e Libro coinvolti
        assertEquals(numPrestitiIniziali - 1, utente.getPrestitiAttivi().size());
        assertEquals(copieDisponibiliIniziali + 1, libro.getCopieDisponibili());
    }
    
 /**
     * IF-3.2: Registrazione di una restituzione (Annullata).
     * Simula la selezione di un Prestito, il click su "Registra Restituzione" e il click su "Annulla".
     * Inoltre controlla la corretta modifica degli attributi di Utente e Libro coinvolti.
     */
@Test
    public void testRegistraRestituzioneAnnullata() {
        int righeIniziali = controller.tabPrestiti.getItems().size(); 
        String prestito = "Utente: " + "0123456789 - Pasca Vinny" + "; Libro: " + "9780123456789 - Norwegian wood, Murakami" +"; Data di Restituzione: " + LocalDate.now().plusDays(3);
           
        interact(() -> {
             controller.tabPrestiti.getSelectionModel().clearSelection();              //Deselezione di tutti i campi
             Prestito target = controller.listaPrestiti.stream()
                 .filter(p -> p.toString().equals(prestito))        //Filtra dalla Lista del controller il prestito
                 .findFirst()
                 .orElseThrow(() -> new RuntimeException("Prestito"));
             controller.tabPrestiti.getSelectionModel().select(target);
        });

        sleep(1000);
        clickOn("#cancPrestitoBtn");
                
        // Verifica Alert di Conferma (Generato dal Controller)
        verifyThat("Confermi la cancellazione del Prestito selezionato?", NodeMatchers.isVisible());   //Controlla il testo dell'Alert per vedere se è quello giusto
        clickOn("Annulla");
        
        sleep(3000);
        // Verifica rimozione dalla tabella 
        verifyThat("#tabPrestiti", TableViewMatchers.hasNumRows(righeIniziali));
        verifyThat("#tabPrestiti", TableViewMatchers.containsRow("0123456789 - Pasca Vinny", "9780123456789 - Norwegian wood, Murakami",  LocalDate.now().plusDays(3)));

    }

    /**
     * IF-3.2 Registrazione di una restituzione (Successo).
     * Simula la cancellazione di un Prestito verificando la comparsa dell'allert sul Ritardo
     */
    @Test
    public void testRegistraRestituzioneInRitardo() {
        sleep(1500);
        int righeIniziali = controller.tabPrestiti.getItems().size(); 
        String prestito = "Utente: " + "2123456789 - Pelle Simon" + "; Libro: " + "9781123456789 - L'arte di correre, Murakami" +"; Data di Restituzione: " + LocalDate.now().minusDays(1);

        StringBuffer date_of_prestito= new StringBuffer();
        interact(() -> {
             controller.tabPrestiti.getSelectionModel().clearSelection();              //Deselezione di tutti i campi
             Prestito target = controller.listaPrestiti.stream()
                 .filter(p -> p.toString().equals(prestito))        //Filtra dalla Lista del controller il prestito
                 .findFirst()
                 .orElseThrow(() -> new RuntimeException("Prestito"));
             controller.tabPrestiti.getSelectionModel().select(target);
             date_of_prestito.append(target.getDataRestituzione().toString());
        });
        
        clickOn("#cancPrestitoBtn");
                
        // Verifica Alert di Conferma (Generato dal Controller)
        verifyThat("La restituzione è in ritardo era prevista per il " + date_of_prestito.toString() + ".\nConfermi la cancellazione del Prestito selezionato?", NodeMatchers.isVisible());   //Controlla il testo dell'Alert per vedere se è quello giusto
        sleep(2000);
        clickOn("OK");
        
        sleep(1000);
        // Verifica rimozione dalla tabella 
        verifyThat("#tabPrestiti", TableViewMatchers.hasNumRows(righeIniziali - 1));
    }
    
}
