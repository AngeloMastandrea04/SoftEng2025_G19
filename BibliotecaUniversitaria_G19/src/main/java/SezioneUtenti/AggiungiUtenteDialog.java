package SezioneUtenti;

import java.awt.Label;
import java.awt.TextField;
import javafx.scene.Node;

/**
 * @brief La classe AggiungiUtenteDialog si occupa della creazione di un nuovo oggetto Utente a partire dalla lettura degli attributi dai campi di testo.
 * Le operazioni interne nello specifico sono di lettura dai campi di testo, di creazione di un nuovo oggetto Libro, di ritorno di un risultato opzionale e di aggiornamento del pulsante ok e sono gestite tramite il costruttore e un metodo privato.
 * La classe non espone metodi pubblici in modo da creare un nuovo oggetto solo internamente.
 * 
 * La Finestra di Dialogo contiene i campi di testo per l'inserimento di Nome, Cognome, Matricola e Email, oltre alle relative Label di errore.
 */
public class AggiungiUtenteDialog {

    private TextField nomeField;

    private TextField cognomeField;

    private TextField matricolaField;

    private Label matricolaError;

    private TextField emailField;

    private Label emailError;

    /**
     * @brief Costruttore.
     * @pre 
     * È stato premuto il bottone "Aggiungi" in SezioneUtentiController
     * @post
     * Se viene premuto il pulsante "OK" viene impostato come risultato un oggetto di tipo Utente con i corrispondenti attributi letti dai campi di testo, altrimenti null.
     */
    public AggiungiUtenteDialog() {
    }

    /**
     * @brief Metodo per l'aggiornamento del valore DisableProperty del nodo passato come parametro.
     * @post Il valore DisableProperty sarà impostato True se è possibile procedere con l'inserimento, altrimenti False
     * @param[in] ok Il Nodo (Bottone) di cui aggiornare la Property.
     */
    private void aggiornaOk(Node ok) {
    }
}
