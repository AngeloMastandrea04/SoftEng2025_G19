package SezionePrestiti;

import SezioneLibri.Libro;
import SezioneUtenti.Utente;
import java.awt.Button;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
/**
 * @brief La classe SezioniPrestitiController si occupa delle operazioni da effettuare sulla struttura dati contenente i prestiti.
 * Le operazioni interne nello specifico sono di aggiunta e cancellazione di un Prestito e sono gestite tramite metodi privati.
 * La classe non espone metodi publici in modo da manipolare la struttura dati solo internamente e all'interno di un singolo modulo
 * 
 * Il Controller contiene le liste dei Libri, degli Utenti e dei Prestiti catturate dalla classe Biblioteca
 * in fase di initialize().
 * 
 * @invariant
 * È possibile creare una sola istanza di questa classe seguendo il design
 * pattern del Singleton.
 */
public class SezionePresitiController {

    private TableView tabPrestiti;

    private TableColumn cUtente;

    private TableColumn cLibro;

    private TableColumn cData;

    private Button cancPrestitoBtn;

    /**
     * @brief Contiene il riferimento alla lista contenente tutti gli Utenti registrati nella Biblioteca.
     */
    private final ObservableList<Utente> listaUtenti;

    /**
     * @brief Contiene il riferimento alla lista contenente tutti i Libri registrati nella Biblioteca.
     */
    private final ObservableList<Libro> listaLibri;

    /**
     * @brief Contiene il riferimento alla lista contenente tutti i Prestiti registrati nella Biblioteca.
     */
    private final ObservableList<Prestito> listaPrestiti;

    /**
     * @brief Metodo di inizializzazione del Controller
     * Ha il compito di accedere al Singleton di Biblioteca e recuperare da esso i riferimenti alle strutture dati
     * associandoli ai relativi attributi del Controller
     */
    private void initialize() {
    }

    /**
     * @brief Metodo di aggiunta del Prestito. 
     * Ha il compito di aggiungere i dati del prestito passati dalla finestra di dialogo alla relativa struttura dati, inoltre si occupa di decrementare le copie del Libro
     * e di aggiungere all'attributo prestitiAttivi di Utente la rappresentazione del prestito data dalla Prestiti.toStringUtente()
     * @see Prestito::toStringUtente()
     * @see Utente::prestitiAttivi
     */
    private void aggiungiPrestito() {
    }

    /**
     * @brief Metodo di cancellazione del Prestito. 
     * Ha il compito di cancellare il prestito dalla relativa struttura dati, inoltre si occupa di incrementare le copie del Libro
     * e di rimuovere dall'attributo prestitiAttivi di Utente la rappresentazione del prestito
     * @see Utente::prestitiAttivi
     */
    private void cancellaPrestito() {
    }

    private void tornaIndietro() {
    }
}
