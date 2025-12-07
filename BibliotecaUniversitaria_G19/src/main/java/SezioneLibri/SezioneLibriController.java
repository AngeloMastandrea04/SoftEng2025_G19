package SezioneLibri;

import java.awt.Button;
import java.awt.TextField;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @brief La classe SezioneLibriController si occupa di gestire tutte le operazioni sulla struttura dati contenente i Libri.
 * 
 * Il Controller contiene la lista di Libri catturata dalla classe Biblioteca
 * in fase di initialize().
 * 
 * @invariant
 * È possibile creare una sola istanza di questa classe seguendo il design
 * pattern del Singleton.
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
     * @brief Metodo di inizializzazione del Controller
     * Ha il compito di accedere al Singleton di Biblioteca e recuperare da esso il riferimento alla strutture dati
     * associandolo al relativo attributo del Controller
     */
    private void initialize() {
    }

     /**
     * @brief Metodo di aggiunta del Libro. 
     * Ha il compito di aggiungere i dati del Libro passati dalla finestra di dialogo alla relativa struttura dati
     */
    private void aggiungiLibro() {
    }

     /**
     * @brief Metodo di cancellazione del Libro. 
     * Ha il compito di cancellare il Libro dalla relativa struttura dati
     */
    private void cancellaLibro() {
    }

    private void tornaIndietro() {
    }
}
