package SezioneUtenti;

import java.awt.Button;
import java.awt.TextField;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @brief La classe SezioneUtentiController si occupa di gestire tutte le operazioni sulla struttura dati contenente gli Utenti.
 * 
 * Il Controller contiene la lista degli Utenti catturata dalla classe Biblioteca
 * in fase di initialize().
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
     * Ha il compito di accedere al Singleton di Biblioteca e recuperare da esso il riferimento alla strutture dati
     * associandolo al relativo attributo del Controller.
     */
    private void initialize() {
    }
    
    /**
     * @brief Metodo di aggiunta dell'Utente. 
     * Ha il compito di aggiungere i dati dell'Utente passati dalla finestra di dialogo alla relativa struttura dati.
     */
    private void aggiungiUtente() {
    }
    
    /**
     * @brief Metodo di cancellazione dell'Utente. 
     * Ha il compito di cancellare l'Utente dalla relativa struttura dati.
    */
    private void cancellaUtente() {
    }

    private void tornaIndietro() {
    }
}
