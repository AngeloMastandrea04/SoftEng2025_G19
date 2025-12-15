/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G19.Biblioteca;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

/**
 *
 * @author angelo
 */
public class DashboardGeneraleControllerTest extends ApplicationTest {
 
    /**
     * Prima di ogni Test:
     * L'Applicazione viene lanciata.
     */
    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
    }

    /**
     * Test of apriSezUtenti method, of class DashboardGeneraleController.
     * Dopo aver cliccato sul bottone per spostarsi nella Sezione Utenti,
     * verifica che sia presente una label con su scritto "Sezione Utenti".
     */
    @Test
    void testApriSezUtenti() {
        System.out.print("test Sezione Utenti: ");
        clickOn("#btnSezUtenti");
        WaitForAsyncUtils.waitForFxEvents();    // Aspetta che venga caricata la scena per sincronizzare i controlli

        // Restituisce un valore non null (il nodo trovato) solo se trova un nodo contenente il testo specificato come parametro
        assertNotNull(lookup("Sezione Utenti").query());
        System.out.println("OK");
    }

    /**
     * Test of apriSezLibri method, of class DashboardGeneraleController.
     * Dopo aver cliccato sul bottone per spostarsi nella Sezione Libri,
     * verifica che sia presente una label con su scritto "Sezione Libri".
     */
    @Test
    void testApriSezLibri() {
        System.out.print("test Sezione Libri: ");
        clickOn("#btnSezLibri");
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(lookup("Sezione Libri").query());
        System.out.println("OK");
    }

    /**
     * Test of apriSezPrestiti method, of class DashboardGeneraleController.
     * Dopo aver cliccato sul bottone per spostarsi nella Sezione Prestiti,
     * verifica che sia presente una label con su scritto "Sezione Prestiti".
     */
    @Test
    void testApriSezPrestiti() {
        System.out.print("test Sezione Prestiti: ");
        clickOn("#btnSezPrestiti");
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(lookup("Sezione Prestiti").query());
        System.out.println("OK");
    }
    
    /**
     * Test of tornaIndietro method, of class SezioneUtentiController.
     * Dopo aver cliccato sul bottone per spostarsi nella Sezione Utenti,
     * clicca sul bottone per tornare indietro e verifica che sia presente 
     * una label con su scritto "Biblioteca Universiataria".
     */
    @Test
    void testTornaIndietroSezUtenti() {
        System.out.print("test Sezione Utenti con TornaIndietro: ");
        clickOn("#btnSezUtenti");
        WaitForAsyncUtils.waitForFxEvents();
        
        clickOn("#btnIndietroDaSezUtenti");
        WaitForAsyncUtils.waitForFxEvents();
        
        assertNotNull(lookup("Biblioteca Universitaria").query());
        
        System.out.println("OK");
    }
    
    /**
     * Test of tornaIndietro method, of class SezioneLibriController.
     * Dopo aver cliccato sul bottone per spostarsi nella Sezione Libri,
     * clicca sul bottone per tornare indietro e verifica che sia presente 
     * una label con su scritto "Biblioteca Universiataria".
     */
    @Test
    void testTornaIndietroSezLibri() {
        System.out.print("test Sezione Libri con TornaIndietro: ");
        clickOn("#btnSezLibri");
        WaitForAsyncUtils.waitForFxEvents();
        
        clickOn("#btnIndietroDaSezLibri");
        WaitForAsyncUtils.waitForFxEvents();
        
        assertNotNull(lookup("Biblioteca Universitaria").query());
        
        System.out.println("OK");
    }
    
    /**
     * Test of tornaIndietro method, of class SezionePrestitiController.
     * Dopo aver cliccato sul bottone per spostarsi nella Sezione Prestiti,
     * clicca sul bottone per tornare indietro e verifica che sia presente 
     * una label con su scritto "Biblioteca Universiataria".
     */
    @Test
    void testTornaIndietroSezPrestiti() {
        System.out.print("test Sezione Prestiti con TornaIndietro: ");
        clickOn("#btnSezPrestiti");
        WaitForAsyncUtils.waitForFxEvents();
        
        clickOn("#btnIndietroDaSezPrestiti");
        WaitForAsyncUtils.waitForFxEvents();
        
        assertNotNull(lookup("Biblioteca Universitaria").query());
        
        System.out.println("OK");
    }
}