package SezioneLibri;

import java.awt.Label;
import java.awt.TextField;
import javafx.scene.Node;

/**
 * @brief La classe AggiungiLibroDialog si occupa della creazione di un nuovo oggetto Libro a partire dalla lettura degli attributi dai campi di testo.
 * Le operazioni interne nello specifico sono di lettura dai campi di testo, di creazione di un nuovo oggetto Libro, di ritorno di un risultato opzionale e di aggiornamento del pulsante ok e sono gestite tramite il costruttore e un metodo privato.
 * La classe non espone metodi pubblici in modo da poter creare un nuovo oggetto solo internamente.
 * 
 * La Finestra di Dialogo contiene i campi di testo per l'inserimento di Titolo, Autori, Anno di rilascio, ISBN e numero di Copie Totali, oltre alle relative Label di errore.
 */
public class AggiungiLibroDialog {

    private TextField titoloField;

    private TextField autoriField;

    private TextField annoField;

    private Label annoError;

    private TextField isbnField;

    private Label isbnError;

    private TextField copieField;

    private Label copieError;

    /**
     * @brief Costruttore.
     * @pre 
     * È stato premuto il bottone "Aggiungi" in SezioneLibriController
     * @post
     * Se viene premuto il pulsante "OK" viene impostato come risultato un oggetto di tipo Libro con i corrispondenti attributi letti dai campi di testo, altrimenti null.
     */
    public AggiungiLibroDialog() {
    }

    /**
     * @brief Metodo per l'aggiornamento del valore DisableProperty del nodo passato come parametro.
     * @post Il valore DisableProperty sarà impostato True se è possibile procedere con l'inserimento, altrimenti False
     * @param[in] ok Il Nodo (Bottone) di cui aggiornare la Property.
     */
    private void aggiornaOk(Node ok) {
    }
}
