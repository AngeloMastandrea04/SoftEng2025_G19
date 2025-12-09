package Biblioteca;

import SezioneLibri.Libro;
import SezionePrestiti.Prestito;
import SezioneUtenti.Utente;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @brief La struttura Biblioteca che contiene tutti i dati dell'archivio tra Utenti, Libri e Prestiti.
 * 
 * La Biblioteca è implementata attraverso il design pattern del Singleton.
 * Contiene le tre liste di Utenti, Libri e Prestiti che il Bibliotecario
 * è interessato a visualizzare durante l'utilizzo del Sistema.
 * 
 * @invariant
 * È possibile creare una sola istanza di questa classe seguendo il design
 * pattern del Singleton.
 */
public class Biblioteca {

    /**
     * @brief Contiene la lista di tutti gli Utenti registrati nella Biblioteca.
     */
    private final ObservableList<Utente> listaUtenti;

    /**
     * @brief Contiene la lista di tutti i Libri registrati nella Biblioteca.
     */
    private final ObservableList<Libro> listaLibri;

    /**
     * @brief Contiene la lista di tutti i Prestiti attivi registrati nella Biblioteca.
     */
    private final ObservableList<Prestito> listaPrestiti;

    /**
     * @brief Inizialmente uguale a null, contiene l'unica istanza per tutto il programma del Singleton Biblioteca.
     */
    private static Biblioteca instance = null;

    /**
     * @brief Costruttore privato (seguendo il design pattern del Singleton).
     * @post
     * Crea una biblioteca vuota, viene utilizzato solo una volta durante
     * l'esecuzione del programma e solo se precedentemente non è stata 
     * trovata una Biblioteca salvata su file.
     */
    private Biblioteca() {
        this.listaUtenti = FXCollections.observableArrayList();
        this.listaLibri = FXCollections.observableArrayList();
        this.listaPrestiti = FXCollections.observableArrayList();
    }

    /**
     * @brief Metodo per ottenere l'unica istanza del Singleton Biblioteca.
     * 
     * Ritorna l'istanza del Singleton Biblioteca se è già stata creata, altrimenti
     * legge dal file e restituisce la Biblioteca letta. Se nel file non è ancora
     * presente alcuna Biblioteca salvata, invoca il costruttore pricato e ne crea
     * e restituisce una vuota.
     * 
     * @return L'unica istanza del Singleton Biblioteca
     */
    public static Biblioteca getInstance() {
        if (instance == null) {  
            try {
                instance = Biblioteca.caricaDaFile("ArchivioBiblioteca.ser");
            } catch(EOFException ex) {
                // Se il file è vuoto, crea un nuovo oggetto Biblioteca vuoto.
                instance = new Biblioteca();
            }
        }
        return instance;
    }

    /**
     * @brief Getter standard per la lista degli Utenti.
     * @return La lista degli Utenti.
     */
    public ObservableList<Utente> getListaUtenti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Getter standard per la lista dei Libri.
     * @return La lista dei Libri.
     */
    public ObservableList<Libro> getListaLibri() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Getter standard per la lista dei Prestiti.
     * @return La lista dei Prestiti.
     */
    public ObservableList<Prestito> getListaPrestiti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Carica da file l'intero archivio della Biblioteca 
     *        contenuto nel Singleton oggetto Biblioteca salvato sullo stesso file.
     * @param[in] filename Percorso del file da cui caricare l'archivio della Biblioteca.
     * @return Il Singleton instanza della classe Biblioteca salvata precedentemente su file.
     */
    private static Biblioteca caricaDaFile(String filename) throws EOFException {
        Biblioteca ret = null;
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
                ret = (Biblioteca) ois.readObject();
        } catch(EOFException ex) {
            // Se il file è vuoto, bisogna creare un nuovo oggetto Biblioteca in getInstance()
            throw new EOFException();
        } catch(IOException | ClassNotFoundException ex) {
            System.err.println("Il file " + filename + " ha generato un'eccezione in lettura.");
        }
        return ret;
    }

    /**
     * @brief Salva su file l'intero archivio della Biblioteca
     *        contenuto nel Singleton oggetto Biblioteca.
     * @param[in] filename Percorso del file su cui caricare l'archivio della Biblioteca.
     */
    public void salvaSuFile(String filename) {
    }
}
