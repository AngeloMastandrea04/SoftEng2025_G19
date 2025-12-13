/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G19.Biblioteca;

import G19.SezioneLibri.Libro;
import G19.SezionePrestiti.Prestito;
import G19.SezioneUtenti.Utente;
import java.io.File;
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
    
    /**
     * Prima di ogni Test:
     * Elimina il file contenente l'archivio se esiste.
     */
    @BeforeEach
    public void setUp() {
        new File("ArchivioBiblioteca.json").delete();
    }
    
    /**
     * Dopo ogni Test:
     * Elimina il file contenente l'archivio se esiste.
     */
    @AfterEach
    public void tearDown() {
        new File("ArchivioBiblioteca.json").delete();
    }

    
    /**
     * Test of getInstance method, of class Biblioteca.
     * Instance inzialmente nullo deve ottenere un oggetto Biblioteca.
     */
    @Test
    public void testGetInstanceNotNull() {
        System.out.print("testGetInstanceNotNull: ");
        
        // Blocco che forza il valore di instance a null.
        Field fieldInstance=null;
        try {
            fieldInstance = Biblioteca.class.getDeclaredField("instance");
            fieldInstance.setAccessible(true);
            fieldInstance.set(null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        Biblioteca result = Biblioteca.getInstance();
        assertNotNull(result);   //Verifica che, dopo getInstance(), instance non rimanga null.
        System.out.println("OK");
    }
        
    /**
     * Test of getInstance method, of class Biblioteca.
     * getInstance() deve fornire la stessa istanza di Biblioteca ogni volta che viene chiamato.
     */
    @Test
    public void testGetInstanceSame() {
        System.out.print("testGetInstanceSame: ");
        Biblioteca result1 = Biblioteca.getInstance();
        Biblioteca result2 = Biblioteca.getInstance();
        assertSame(result1, result2);   //Verifica che getInstance() restituisca la stessa Biblitoeca.
        System.out.println("OK");
    }
    
    /**
     * Test of caricaDaFile method, of class Biblioteca.
     * caricaDaFile(), richiamato da getInstance(), quando instance==null, legge
     * il file .json contenente l'archivio. Si testa se avviene una corretta lettura
     * dei dati in seguito a delle aggiunte.
     */
    @Test
    public void testCaricaDaFileAggiunta() {
        
        // Si aggiunge un libro, un Utente, un Prestito e un Prestito attivo per quell'Utente.
        System.out.print("testCaricaDaFileAggiunta: ");
        Libro l1 = new Libro("I Promessi Sposi", "Alessandro Manzoni", 1840, "9788880801234", 10);
        Utente u1 = new Utente("Paolo", "Bianchi", "0612709555", "p.bianchi@uni.it");
        Prestito p1 = new Prestito(u1.toStringPrestito(), l1.toStringPrestito(), LocalDate.of(2004, 02, 23));
        
        Biblioteca.getInstance().getListaLibri().add(l1);
        Biblioteca.getInstance().getListaUtenti().add(u1);
        Biblioteca.getInstance().getListaPrestiti().add(p1);
        u1.getPrestitiAttivi().add(p1.toStringUtente());
        
        // Blocco che forza il valore di instance a null per forzare, a sua volta, la lettura da file.
        Field fieldInstance=null;
        try {
            fieldInstance = Biblioteca.class.getDeclaredField("instance");
            fieldInstance.setAccessible(true);
            fieldInstance.set(null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // Si verifica se è presente il Libro aggiunto.
        boolean res1 = Biblioteca.getInstance().getListaLibri().contains(l1);
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getTitolo().equals(l1.getTitolo());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getAutori().equals(l1.getAutori());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getAnno() == (l1.getAnno());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getCopieTotali() == (l1.getCopieTotali());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getCopieDisponibili() == (l1.getCopieDisponibili());
        assertTrue(res1);
        System.out.print("Libro OK, ");
        
        // Si verifica se è presente l'Utente aggiunto.
        boolean res2 = Biblioteca.getInstance().getListaUtenti().contains(u1);
        res2 &= Biblioteca.getInstance().getListaUtenti().get(0).getNome().equals(u1.getNome());
        res2 &= Biblioteca.getInstance().getListaUtenti().get(0).getCognome().equals(u1.getCognome());
        res2 &= Biblioteca.getInstance().getListaUtenti().get(0).getEmail().equals(u1.getEmail());
        assertTrue(res2);
        System.out.print("Utente OK, ");
                
        // Si verifica se è presente il Prestito aggiunto, attraverso la sua rappresentazione in String.
        boolean res3 = Biblioteca.getInstance().getListaPrestiti().get(0).toString().equals(p1.toString());
        assertTrue(res3);
        System.out.print("Prestito OK, ");
                
        // Si verifica se è presente il Prestito Attivo aggiunto all'oggetto Utente, attraverso la sua rappresentazione in String.
        boolean res4 = Biblioteca.getInstance().getListaUtenti().get(0).getPrestitiAttivi().get(0).equals(p1.toStringUtente());
        assertTrue(res4);
        System.out.println("Prestito attivo in un Utente OK.");
    }
    

    /**
     * Test of salvaSuFile method, of class Biblioteca.
     */
    /*@Test
    public void testSalvaSuFile() {
        System.out.println("salvaSuFile");
        String filename = "ArchivioBiblioteca.json";
        Biblioteca bib = Biblioteca.getInstance();
        bib.salvaSuFile(filename);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
}
