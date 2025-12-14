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
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
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
        
        setNullAttributo("instance");
        
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
        
        setNullAttributo("instance");
        
        Biblioteca result1 = Biblioteca.getInstance();
        Biblioteca result2 = Biblioteca.getInstance();
        assertSame(result1, result2);   //Verifica che getInstance() restituisca la stessa Biblitoeca.
        System.out.println("OK");
    }
    
    /**
     * Test of salvaSuFile method, of class Biblioteca.
     * Vengono salvate su file delle aggiunte, il contenuto del file viene infine confrontato
     * con il contenuto di un file oracolo.
     */
    @Test
    public void testSalvaSuFileAggiunta() {
        System.out.print("testSalvaSuFileAggiunta: ");
        
        setNullAttributo("instance");
        
        // Si aggiunge un libro, un Utente, un Prestito e un Prestito attivo per quell'Utente.
        Libro l1 = new Libro("I Promessi Sposi", "Alessandro Manzoni", 1840, "9788880801234", 10);
        Utente u1 = new Utente("Paolo", "Bianchi", "0612709555", "p.bianchi@uni.it");
        Prestito p1 = new Prestito(u1.toStringPrestito(), l1.toStringPrestito(), LocalDate.of(2004, 02, 23));
        
        Biblioteca.getInstance().getListaLibri().add(l1);
        Biblioteca.getInstance().getListaUtenti().add(u1);
        Biblioteca.getInstance().getListaPrestiti().add(p1);
        u1.getPrestitiAttivi().add(p1.toStringUtente());
        
        
        Path archivioPath = Paths.get("ArchivioBiblioteca.json");
        Path oracoloPath = Paths.get("src/test/java/G19/Biblioteca/Oracolo_TestSalvaSuFileAggiunta.json");
        
        List<String> archivio=null;
        List<String> oracolo=null;
        try {
            archivio = Files.readAllLines(archivioPath);
            oracolo = Files.readAllLines(oracoloPath);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        
        assertEquals(archivio, oracolo);
        System.out.println("OK");
    }
    
    /**
     * Test of salvaSuFile method, of class Biblioteca.
     * Vengono salvate su file delle modifiche, il contenuto del file viene infine confrontato
     * con il contenuto di un file oracolo.
     */
    @Test
    public void testSalvaSuFileModifica() {
        System.out.print("testSalvaSuFileModifica: ");
        
        setNullAttributo("instance");
        
        // Si aggiunge un Libro e un Utente. Un Prestito non è modificabile.
        Libro l1 = new Libro("I Promessi Sposi", "Alessandro Manzoni", 1840, "9788880801234", 10);
        Utente u1 = new Utente("Paolo", "Bianchi", "0612709555", "p.bianchi@uni.it");
        
        Biblioteca.getInstance().getListaLibri().add(l1);
        Biblioteca.getInstance().getListaUtenti().add(u1);
        
        // Si modificano il Libro e l'Utente aggiunto.
        Biblioteca.getInstance().getListaLibri().get(0).setIsbn("9780000000000");
        Biblioteca.getInstance().getListaUtenti().get(0).setMatricola("0612700000");
        
        
        Path archivioPath = Paths.get("ArchivioBiblioteca.json");
        Path oracoloPath = Paths.get("src/test/java/G19/Biblioteca/Oracolo_TestSalvaSuFileModifica.json");
        
        List<String> archivio=null;
        List<String> oracolo=null;
        try {
            archivio = Files.readAllLines(archivioPath);
            oracolo = Files.readAllLines(oracoloPath);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        
        assertEquals(archivio, oracolo);
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
        System.out.print("testCaricaDaFileAggiunta: ");
        
        setNullAttributo("instance");
        
        // Si aggiunge un libro, un Utente, un Prestito e un Prestito attivo per quell'Utente.
        Libro l1 = new Libro("I Promessi Sposi", "Alessandro Manzoni", 1840, "9788880801234", 10);
        Utente u1 = new Utente("Paolo", "Bianchi", "0612709555", "p.bianchi@uni.it");
        Prestito p1 = new Prestito(u1.toStringPrestito(), l1.toStringPrestito(), LocalDate.of(2004, 02, 23));
        
        Biblioteca.getInstance().getListaLibri().add(l1);
        Biblioteca.getInstance().getListaUtenti().add(u1);
        Biblioteca.getInstance().getListaPrestiti().add(p1);
        u1.getPrestitiAttivi().add(p1.toStringUtente());
        
        
        // Forza la lettura da file.
        setNullAttributo("instance");
        
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
     * Test of caricaDaFile method, of class Biblioteca.
     * caricaDaFile(), richiamato da getInstance(), quando instance==null, legge
     * il file .json contenente l'archivio. Si testa se avviene una corretta lettura
     * dei dati in seguito a delle modifiche.
     */
    @Test
    public void testCaricaDaFileModifica() {      
        System.out.print("testCaricaDaFileModifica: ");
        
        setNullAttributo("instance");
        
        // Si aggiunge un Libro e un Utente. Un Prestito non è modificabile.
        Libro l1 = new Libro("I Promessi Sposi", "Alessandro Manzoni", 1840, "9788880801234", 10);
        Utente u1 = new Utente("Paolo", "Bianchi", "0612709555", "p.bianchi@uni.it");
        
        Biblioteca.getInstance().getListaLibri().add(l1);
        Biblioteca.getInstance().getListaUtenti().add(u1);
        
        // Si modificano il Libro e l'Utente aggiunto.
        Biblioteca.getInstance().getListaLibri().get(0).setIsbn("9780000000000");
        Biblioteca.getInstance().getListaUtenti().get(0).setMatricola("0612700000");
        
        
        // Forza la lettura da file.
        setNullAttributo("instance");
        
        // Si verifica se è presente il Libro modificato.
        boolean res1 = Biblioteca.getInstance().getListaLibri().contains(new Libro("I Promessi Sposi", "Alessandro Manzoni", 1840, "9780000000000", 10));
        res1 &= !(Biblioteca.getInstance().getListaLibri().contains(new Libro("I Promessi Sposi", "Alessandro Manzoni", 1840, "9788880801234", 10)));
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getTitolo().equals(l1.getTitolo());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getAutori().equals(l1.getAutori());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getAnno() == (l1.getAnno());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getCopieTotali() == (l1.getCopieTotali());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getCopieDisponibili() == (l1.getCopieDisponibili());
        assertTrue(res1);
        System.out.print("Libro OK, ");
        
        // Si verifica se è presente l'Utente modificato.
        boolean res2 = Biblioteca.getInstance().getListaUtenti().contains(new Utente("Paolo", "Bianchi", "0612700000", "p.bianchi@uni.it"));
        res2 &= !(Biblioteca.getInstance().getListaUtenti().contains(new Utente("Paolo", "Bianchi", "0612709555", "p.bianchi@uni.it")));
        res2 &= Biblioteca.getInstance().getListaUtenti().get(0).getNome().equals(u1.getNome());
        res2 &= Biblioteca.getInstance().getListaUtenti().get(0).getCognome().equals(u1.getCognome());
        res2 &= Biblioteca.getInstance().getListaUtenti().get(0).getEmail().equals(u1.getEmail());
        assertTrue(res2);
        System.out.println("Utente OK.");
    }
    
    /**
     * Test of caricaDaFile method, of class Biblioteca.
     * caricaDaFile(), richiamato da getInstance(), quando instance==null, legge
     * il file .json contenente l'archivio. Si testa se avviene una corretta lettura
     * dei dati in seguito a delle cancellazioni.
     */
    @Test
    public void testCaricaDaFileCancellazione() {      
        System.out.print("testCaricaDaFileCancellazione: ");
        
        setNullAttributo("instance");
        
        // Si aggiunge un Libro, un Utente, un Prestito e un Prestito attivo per quell'Utente.
        Libro l1 = new Libro("I Promessi Sposi", "Alessandro Manzoni", 1840, "9788880801234", 10);
        Utente u1 = new Utente("Paolo", "Bianchi", "0612709555", "p.bianchi@uni.it");
        Prestito p1 = new Prestito(u1.toStringPrestito(), l1.toStringPrestito(), LocalDate.of(2004, 02, 23));
        
        Biblioteca.getInstance().getListaLibri().add(l1);
        Biblioteca.getInstance().getListaUtenti().add(u1);
        Biblioteca.getInstance().getListaPrestiti().add(p1);
        u1.getPrestitiAttivi().add(p1.toStringUtente());
        
        // Si aggiungono un altro Libro, un altro Utente, un altro Prestito e un Prestito attivo per quell'Utente.
        Libro l2 = new Libro("Divina Commedia", "Dante Alighieri", 1321, "9788880805678", 15);
        Utente u2 = new Utente("Giuseppe", "Verdi", "06127084444", "g.verdi@studenti.uni.it");
        Prestito p2 = new Prestito(u2.toStringPrestito(), l2.toStringPrestito(), LocalDate.of(2004, 05, 15));
        
        Biblioteca.getInstance().getListaLibri().add(l2);
        Biblioteca.getInstance().getListaUtenti().add(u2);
        Biblioteca.getInstance().getListaPrestiti().add(p2);
        u2.getPrestitiAttivi().add(p2.toStringUtente());
        u1.getPrestitiAttivi().add(p2.toStringUtente());
        u2.getPrestitiAttivi().add(p1.toStringUtente());
        
        // Effettuo la cancellazione dei primi inserimenti, lasciando solo i secondi.
        Biblioteca.getInstance().getListaLibri().remove(l1);
        Biblioteca.getInstance().getListaUtenti().remove(u1);
        Biblioteca.getInstance().getListaPrestiti().remove(p1);
        Biblioteca.getInstance().getListaUtenti().get(0).getPrestitiAttivi().remove(p1.toStringUtente());
        
        
        // Forza la lettura da file.
        setNullAttributo("instance");
        
        // Si verifica se è presente il secondo Libro e se è stato cancellato il primo Libro.
        boolean res1 = Biblioteca.getInstance().getListaLibri().contains(l2);
        res1 &= !(Biblioteca.getInstance().getListaLibri().contains(l1));
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getTitolo().equals(l2.getTitolo());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getAutori().equals(l2.getAutori());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getAnno() == (l2.getAnno());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getCopieTotali() == (l2.getCopieTotali());
        res1 &= Biblioteca.getInstance().getListaLibri().get(0).getCopieDisponibili() == (l2.getCopieDisponibili());
        assertTrue(res1);
        System.out.print("Libro OK, ");
        
        // Si verifica se è presente il secondo Utente e se è stato cancellato il primo Utente.
        boolean res2 = Biblioteca.getInstance().getListaUtenti().contains(u2);
        res2 &= !(Biblioteca.getInstance().getListaUtenti().contains(u1));
        res2 &= Biblioteca.getInstance().getListaUtenti().get(0).getNome().equals(u2.getNome());
        res2 &= Biblioteca.getInstance().getListaUtenti().get(0).getCognome().equals(u2.getCognome());
        res2 &= Biblioteca.getInstance().getListaUtenti().get(0).getEmail().equals(u2.getEmail());
        assertTrue(res2);
        System.out.print("Utente OK, ");
                
        // Si verifica se è presente il secondo Prestito e se è stato cancellato il primo Prestito, attraverso la sua rappresentazione in String.
        boolean res3 = Biblioteca.getInstance().getListaPrestiti().get(0).toString().equals(p2.toString());
        res3 &= !(Biblioteca.getInstance().getListaPrestiti().get(0).toString().equals(p1.toString()));
        assertTrue(res3);
        System.out.print("Prestito OK, ");
                
        // Si verifica se è presente il secondo Prestito Attivo aggiunto all'oggetto Utente e se è stato cancellato il primo Prestito Attivo
        // per quell'Utente, attraverso la sua rappresentazione in String.
        boolean res4 = Biblioteca.getInstance().getListaUtenti().get(0).getPrestitiAttivi().get(0).equals(p2.toStringUtente());
        res4 &= !(Biblioteca.getInstance().getListaUtenti().get(0).getPrestitiAttivi().get(0).equals(p1.toStringUtente()));
        assertTrue(res4);
        System.out.println("Prestito attivo in un Utente OK.");
    }
    
    // Blocco che imposta il valore di instance a null per forzare la lettura da file.
    private void setNullAttributo(String attributo) {
        Field fieldInstance=null;
        try {
            fieldInstance = Biblioteca.class.getDeclaredField(attributo);
            fieldInstance.setAccessible(true);
            fieldInstance.set(null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}