package SezionePrestiti;

import SezioneUtenti.Utente;
import SezioneLibri.Libro;
import java.awt.Label;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

/**
 * @brief La classe AggiungiPrestitoDialog si occupa della creazione di un nuovo oggetto Prestito a partire dalla lettura degli attributi dai ComboBox e dal DatePicker.
 * 
 * Le operazioni interne nello specifico sono di lettura dai ComboBox e dal DatePicker, di creazione di un nuovo oggetto Prestito,
 * di ritorno di un risultato opzionale e di aggiornamento del pulsante ok e
 * sono gestite tramite il costruttore e un metodo privato.
 * 
 * La Finestra di Dialogo contiene i ComboBox per l'inserimento di Utente e Libro, un DatePicker per l'inserimento della Data di Restituzione, le relative Label di errore e le liste contenenti Utenti e Libri.
 */
public class AggiungiPrestitoDialog {

    private ComboBox<Utente> utenteBox;

    private Label utenteError;

    private ComboBox<Libro> libroBox;

    private Label libroError;

    private DatePicker dataRestituzionePicker;

    private Label dataRestituzioneError;

    private final ObservableList<Utente> utenti;
    
    private final ObservableList<Libro> libri;

    /**
     * @brief Costruttore.
     * @pre È stato premuto il bottone "Aggiungi" in SezionePrestitiController.
     * @post Se viene premuto il pulsante "OK" viene impostato come risultato un oggetto
     *       di tipo Prestito con i corrispondenti attributi letti dai ComboBox e dal DatePicker, altrimenti null.
     */
    public AggiungiPrestitoDialog() {
    }

    /**
     * @brief Metodo per l'aggiornamento del valore DisableProperty del nodo passato come parametro.
     * @pre Viene rilevato un cambiamento all'interno di una ComboBox o del DatePicker.
     * @post Il valore DisableProperty sarà impostato True se è possibile procedere con l'inserimento, altrimenti False.
     * @param[in] ok Il Nodo (Bottone) di cui aggiornare la Property.
     */
    private void aggiornaOk(Node ok) {
    }
}
