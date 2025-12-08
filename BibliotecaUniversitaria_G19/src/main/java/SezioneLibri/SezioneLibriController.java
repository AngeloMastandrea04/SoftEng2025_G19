package SezioneLibri;

import java.awt.Button;
import java.awt.TextField;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @brief La classe SezioneLibriController si occupa di gestire tutte le operazioni sulla struttura dati contenente i Libri.
 * 
 * Il Controller contiene la TableView per la lista dei Libri e le relative colonne,
 * un campo di testo per la ricerca e il bottone per cancellare un Libro.
 * Il Controller ha un riferimento alla lista di Libri contenuta nella classe Biblioteca.
 * 
*/

public class SezioneLibriController {

    private TableView tabLibri;

    private TableColumn cTitolo;

    private TableColumn cAutori;

    private TableColumn cAnno;

    private TableColumn cIsbn;

    private TableColumn cCopieTotali;

    private TableColumn cCopieDisponibili;

    private TextField ricLibro;

    private Button cancLibroBtn;

    /**
     * @brief Contiene il riferimento alla lista contenente tutti i Libri registrati nella Biblioteca.
    */
    private final ObservableList<Libro> listaLibri;

    /**
     * @brief Metodo di inizializzazione del Controller.
     * Ha il compito di accedere al Singleton di Biblioteca e recuperare da esso 
     * il riferimento alla struttura dati associandolo al relativo attributo del Controller.
     * @pre Viene caricata la scena della Sezione Libri.
     * @post Viene visualizzata a schermo la Sezione Libri.
    */
    private void initialize() {
    }

    /**
     * @brief Metodo di aggiunta del Libro. 
     * Ha il compito di aggiungere i dati del Libro passati dalla finestra di dialogo alla relativa struttura dati.
     * @pre Viene premuto il bottone "Aggiungi".
     * @post Se non ci sono duplicati viene aggiunto un Libro alla lista, altrimenti lancia un alert di errore.
    */
    private void aggiungiLibro() {
    }

    /**
     * @brief Metodo di cancellazione del Libro. 
     * Ha il compito di cancellare il Libro dalla relativa struttura dati.
     * @pre Viene selezionato un Libro dalla lista e viene premuto il bottone "Cancella".
     * @post Viene mostrato un alert di conferma e, se i numeri di copie totali e disponibili combaciano il Libro selezionato viene cancellato dalla lista,
     * altrimenti lancia un alert di errore.
    */
    private void cancellaLibro() {
    }

    /**
     * @brief Gestisce il ritorno alla Dashboard Generale.
     * Ha il compito di far tornare alla Dashboard Generale
     * @pre Viene premuto il bottone "Torna indietro".
     * @post Viene caricata la scena della Dashboard Generale.
    */
    private void tornaIndietro() {
    }
}
