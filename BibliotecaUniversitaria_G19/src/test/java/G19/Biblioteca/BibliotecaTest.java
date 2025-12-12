/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G19.Biblioteca;

import G19.SezioneLibri.Libro;
import G19.SezionePrestiti.Prestito;
import G19.SezioneUtenti.Utente;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author angelo
 */
public class BibliotecaTest {
    private Biblioteca bib;
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class Biblioteca.
     * Instance inzialmente nullo deve ottenere un oggetto Biblioteca.
     */
    @Test
    public void testGetInstance1() {
        System.out.println("getInstance1");
        Biblioteca result = Biblioteca.getInstance();
        assertNotNull(result);
    }
    
    @Test
    public void testGetInstance2() {
        System.out.println("getInstance2");
        Libro l1 = new Libro("c", "d", 3, "isbn_prova", 5);
        Utente u1 = new Utente("nomen", "cognomen", "matricolam", "emailz");
        Prestito p1 = new Prestito(u1.toStringPrestito(), l1.toStringPrestito(), LocalDate.of(2004, 02, 23));
        u1.getPrestitiAttivi().add(p1.toStringUtente());
        u1.getPrestitiAttivi().add(p1.toStringUtente());
        //u1.getPrestitiAttivi().remove(p1.toStringUtente());
        
        Biblioteca.getInstance().getListaLibri().add(l1);
        Biblioteca.getInstance().getListaUtenti().add(u1);
        Biblioteca.getInstance().getListaPrestiti().add(p1);
        
        Field fieldInstance=null;
        try {
            fieldInstance = Biblioteca.class.getDeclaredField("instance");
            fieldInstance.setAccessible(true);
            fieldInstance.set(null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        Biblioteca result = Biblioteca.getInstance();
        assertNotNull(result);
    }
    

    /**
     * Test of salvaSuFile method, of class Biblioteca.
     */
    /*@Test
    public void testSalvaSuFile() {
        System.out.println("salvaSuFile");
        String filename = "test.ser";
        Biblioteca bib = Biblioteca.getInstance();
        bib.salvaSuFile(filename);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
}
