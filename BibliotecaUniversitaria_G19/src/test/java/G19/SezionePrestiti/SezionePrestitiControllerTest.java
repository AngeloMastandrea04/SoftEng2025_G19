/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G19.SezionePrestiti;
import G19.SezioneUtenti.Utente;
import G19.SezioneLibri.Libro;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 *
 * @author Vincenzo
 */
public class SezionePrestitiControllerTest {
    
    public SezionePrestitiControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void aggiungiPrestito() {
        
        SezionePrestitiController controller=new SezionePrestitiController();
        System.out.println("aggiungiPrestito");
        Utente utente = new Utente("Vincenzo", "Rossi", "1234567890", "");
        controller.aggiungiPrestito();
    }
    
}
