package G19.Biblioteca;

import G19.SezioneLibri.Libro;
import G19.SezionePrestiti.Prestito;
import G19.SezioneUtenti.Utente;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Callback;

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
        
        /*  Con la lambda expression overraido il metodo call dell'interfaccia funzionale
            Callback. 
            Quando verrà aggiunto un nuovo Utente/Libro alla lista, verrà creato un array
            osservabile di tutte le property del nuovo oggetto in modo tale che anche quando
            verrà modificato un campo dell'Utente/Libro presente in lista, tutti i Listener
            verranno avvisati.
        */
        Callback<Utente, Observable[]> extractorUtente = (Utente u) -> new Observable[] {
            u.nomeProperty(),
            u.cognomeProperty(),
            u.matricolaProperty(),
            u.emailProperty(),
            u.getPrestitiAttivi()
        };
        this.listaUtenti = FXCollections.observableArrayList(extractorUtente);
        
        Callback<Libro, Observable[]> extractorLibro = (Libro l) -> new Observable[] {
            l.titoloProperty(),
            l.autoriProperty(),
            l.annoProperty(),
            l.isbnProperty(),
            l.copieTotaliProperty(),
            l.copieDisponibiliProperty()
        };
        this.listaLibri = FXCollections.observableArrayList(extractorLibro);
        
        /*  I Prestiti non sono modificabili, non è necessario usare un extractor.
        */
        this.listaPrestiti = FXCollections.observableArrayList();
        
        /*  Creazione del Listener per osservare le modifiche alla lista.
            Per ogni cambiamento subito dalla lista, la Biblioteca verrà salvata su file.
            Senza extractor verrebbero avvisati solo in caso di aggiunta/rimozione, non
            di modifica di un campo di un elemento.
        */
        ListChangeListener<Utente> listenerSalvataggioUtenti = (change) -> {
            while(change.next()) {
                this.salvaSuFile("ArchivioBiblioteca.ser"); 
            }
        };
        this.listaUtenti.addListener(listenerSalvataggioUtenti);
        
        ListChangeListener<Libro> listenerSalvataggioLibri = (change) -> {
            while(change.next()) {
                this.salvaSuFile("ArchivioBiblioteca.ser"); 
            }
        };
        this.listaLibri.addListener(listenerSalvataggioLibri);
        
        ListChangeListener<Prestito> listenerSalvataggioPrestiti = (change) -> {
            while(change.next()) {
                this.salvaSuFile("ArchivioBiblioteca.ser"); 
            }
        };
        this.listaPrestiti.addListener(listenerSalvataggioPrestiti);
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
        if(instance == null) {  
            try {
                instance = Biblioteca.caricaDaFile("ArchivioBiblioteca.ser");
            } catch(EOFException | FileNotFoundException ex) {
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
        return listaUtenti;
    }

    /**
     * @brief Getter standard per la lista dei Libri.
     * @return La lista dei Libri.
     */
    public ObservableList<Libro> getListaLibri() {
        return listaLibri;
    }

    /**
     * @brief Getter standard per la lista dei Prestiti.
     * @return La lista dei Prestiti.
     */
    public ObservableList<Prestito> getListaPrestiti() {
        return listaPrestiti;
    }

    /**
     * @brief Carica da file l'intero archivio della Biblioteca 
     *        contenuto nel Singleton oggetto Biblioteca salvato sullo stesso file.
     * @param[in] filename Percorso del file da cui caricare l'archivio della Biblioteca.
     * @return Il Singleton instanza della classe Biblioteca salvata precedentemente su file.
     */
    private static Biblioteca caricaDaFile(String filename) throws EOFException, FileNotFoundException {
        Biblioteca ret = null;
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
                ret = (Biblioteca) ois.readObject();
        } catch(FileNotFoundException ex) {
            // Se il file non viene trovato, bisogna creare un nuovo oggetto Biblioteca in getInstance()
            throw new FileNotFoundException();
        } catch(EOFException ex) {
            // Allo stesso modo se il file è vuoto, bisogna creare un nuovo oggetto Biblioteca in getInstance()
            throw new EOFException();
        } catch(IOException | ClassNotFoundException ex) {
            System.err.println("E' stata generata un'eccezione durante la lettura del file." + filename);
        }
        return ret;
    }

    /**
     * @brief Salva su file l'intero archivio della Biblioteca
     *        contenuto nel Singleton oggetto Biblioteca.
     * @param[in] filename Percorso del file su cui caricare l'archivio della Biblioteca.
     */
    public void salvaSuFile(String filename) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
                oos.writeObject(this);
        } catch(IOException ex) {
            System.err.println("E' stata generata un'eccezione durante la scrittura sul file." + filename);
        }
    }
}
