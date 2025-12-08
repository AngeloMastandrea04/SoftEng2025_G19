package SezioneUtenti;

import java.awt.Button;
import java.awt.TextField;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @brief La classe SezioneUtentiController si occupa di gestire tutte le operazioni sulla struttura dati contenente gli Utenti.
 * 
 * Il Controller contiene la TableView per la lista degli Utenti e le relative colonne,
 * un campo di testo per la ricerca e il bottone per cancellare un Utente.
 * Il Controller ha un riferimento alla lista degli Utenti contenuta nella classe Biblioteca.
 * 
*/
public class SezioneUtentiController {

    private TableView tabUtenti;

    private TableColumn cCognome;

    private TableColumn cNome;

    private TableColumn cMatricola;

    private TableColumn cEmail;

    private TableColumn cPrestitiAttivi;

    private TextField ricUtente;

    private Button cancUtenteBtn;
    
    /**
     * @brief Contiene il riferimento alla lista contenente tutti gli Utenti registrati nella Biblioteca.
     */
    private final ObservableList<Utente> listaUtenti;
    
    /**
     * @brief Metodo di inizializzazione del Controller.
     * Ha il compito di accedere al Singleton di Biblioteca e recuperare da esso
     * il riferimento alla struttura dati associandolo al relativo attributo del Controller.
     * @pre Viene caricata la scena della Sezione Utenti.
     * @post Viene visualizzata a schermo la Sezione Utenti.
     */
    private void initialize() {
    }
    
    /**
     * @brief Metodo di aggiunta dell'Utente. 
     * Ha il compito di aggiungere i dati dell'Utente passati dalla finestra di dialogo alla relativa struttura dati.
     * @pre Viene premuto il bottone "Aggiungi".
     * @post Se non ci sono duplicati viene aggiunto un Utente alla lista, altrimenti lancia un alert di errore.
     */
    private void aggiungiUtente() {
    }
    
    /**
     * @brief Metodo di cancellazione dell'Utente. 
     * Ha il compito di cancellare l'Utente dalla relativa struttura dati.
     * @pre Viene selezionato un Utente dalla lista e viene premuto il bottone "Cancella".
     * @post Viene mostrato un alert di conferma e, se l'Utente selezionato non ha prestiti attivi viene cancellato dalla lista,
     * altrimenti lancia un alert di errore.
    */
    private void cancellaUtente() {
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
