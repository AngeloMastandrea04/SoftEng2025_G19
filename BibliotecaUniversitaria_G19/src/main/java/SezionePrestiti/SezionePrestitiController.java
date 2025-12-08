package SezionePrestiti;

import SezioneLibri.Libro;
import SezioneUtenti.Utente;
import java.awt.Button;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
/**
 * @brief La classe SezioniPrestitiController si occupa delle operazioni da effettuare sulla struttura dati contenente i Prestiti.
 * 
 * Il Controller contiene la Tableview per la lista dei Prestiti e le relative colonne e 
 * il bottone per registrare una restituzione.
 * Il Controller ha riferimenti alle liste dei Libri, degli Utenti e dei Prestiti 
 * contenute nella classe Biblioteca.
 * 
*/
public class SezionePrestitiController {

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
     * @brief Metodo di inizializzazione del Controller.
     * Ha il compito di accedere al Singleton di Biblioteca e recuperare da esso 
     * i riferimenti alle strutture dati associandoli ai relativi attributi del Controller.
     * @pre Viene caricata la scena della Sezione Prestiti.
     * @post Viene visualizzata a schermo la Sezione Prestiti.
     */
    private void initialize() {
    }

    /**
     * @brief Metodo di aggiunta (registrazione) del Prestito. 
     * Ha il compito di aggiungere i dati del prestito passati dalla finestra di dialogo alla relativa struttura dati,
     * inoltre si occupa di decrementare le copie disponibili del Libro e di aggiungere il prestito tra quelli attivi per l'Utente.
     * @pre Viene premuto il bottone "Registra nuovo Prestito".
     * @post Viene aggiunto un Prestito alla lista e modificati Libro e Utente corrispondenti.
     */
    private void aggiungiPrestito() {
    }

    /**
     * @brief Metodo di cancellazione (restituzione) del Prestito. 
     * Ha il compito di cancellare il prestito dalla relativa struttura dati,
     * inoltre si occupa di incrementare le copie disponibili del Libro e di rimuovere il prestito da quelli attivi per l'Utente.
     * @pre Viene selezionato un Prestito dalla lista e viene premuto il bottone "Registra la Restituzione".
     * @post Viene mostrato un alert di conferma (di warning nel caso la restituzione sia in ritardo),
     * rimosso il Prestito dalla lista e modificati Libro e Utente corrispondenti.
     */
    private void cancellaPrestito() {
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
