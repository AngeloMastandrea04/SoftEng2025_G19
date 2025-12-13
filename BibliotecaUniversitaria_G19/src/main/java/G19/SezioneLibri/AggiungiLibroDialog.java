package G19.SezioneLibri;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.time.LocalDate;

/**
 * @brief La classe AggiungiLibroDialog si occupa della creazione di un nuovo oggetto Libro a partire dalla lettura degli attributi dai campi di testo.
 * 
 * Le operazioni interne nello specifico sono di lettura dai campi di testo, di creazione di un nuovo oggetto Libro,
 * di ritorno di un risultato opzionale e di aggiornamento del pulsante ok e
 * sono gestite tramite il costruttore e un metodo privato.
 * 
 * La Finestra di Dialogo contiene i campi di testo per l'inserimento di Titolo, Autori, Anno di rilascio, ISBN e numero di Copie Totali, oltre alle relative Label di errore.
 */
public class AggiungiLibroDialog extends Dialog<Libro> {

    @FXML private TextField titoloField;

    @FXML private TextField autoriField;

    @FXML private TextField annoField;

    @FXML private Label annoError;

    @FXML private TextField isbnField;

    @FXML private Label isbnError;

    @FXML private TextField copieField;

    @FXML private Label copieError;

    /**
     * @brief Costruttore.
     * @pre È stato premuto il bottone "Aggiungi" in SezioneLibriController.
     * @post Se viene premuto il pulsante "OK" viene impostato come risultato un oggetto
     *       di tipo Libro con i corrispondenti attributi letti dai campi di testo, altrimenti null.
     */
    public AggiungiLibroDialog() {
         try {
            // Caricamento file FXML, impostazione controller, DialogPane, grandezza fissa, icona e titolo.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AggiungiLibroDialogView.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
            this.setResizable(false);
            Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/G19/Biblioteca/Icona.png").toExternalForm()));
            this.setTitle("Aggiungi un Libro");
            
            // Disabilitazione iniziale pulsante ok
            Node ok = this.getDialogPane().lookupButton(ButtonType.OK);
            ok.setDisable(true);
                        
            // Listener sui TextField, con eventuale gestione degli errori di validità
            titoloField.textProperty().addListener((observable, oldValue, newValue) -> {
                aggiornaOk(ok);
            });
            autoriField.textProperty().addListener((observable, oldValue, newValue) -> {
                aggiornaOk(ok);
            });
            annoField.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!newValue.isEmpty())
                    try {
                        int annoInserito = Integer.parseInt(newValue);
                        annoError.setVisible(!newValue.isEmpty() && annoInserito < 0 || annoInserito > LocalDate.now().getYear());
                    } catch (NumberFormatException ex) {
                        annoError.setVisible(true);
                } else
                    annoError.setVisible(false);
                aggiornaOk(ok);
            });
            isbnField.textProperty().addListener((observable, oldValue, newValue) -> {
                isbnError.setVisible(!newValue.isEmpty() && !newValue.matches("^(978|979)\\d{10}$"));
                aggiornaOk(ok);
            });
            copieField.textProperty().addListener((observable, oldValue, newValue) -> {
                copieError.setVisible(!newValue.isEmpty() && !newValue.matches("^[1-9]\\d*$"));
                aggiornaOk(ok);
            });
            
            // Imposta il risultato
            this.setResultConverter(db -> {
                if(db == ButtonType.OK)
                    try{
                        int annoInserito = Integer.parseInt(annoField.getText());
                        int copieInserite = Integer.parseInt(copieField.getText());
                        return new Libro(titoloField.getText(), autoriField.getText(), annoInserito, isbnField.getText(), copieInserite);
                    }catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        throw new RuntimeException("Impossibile creare nuovo oggetto Libro", ex);
                    }
                return null;
            });
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare AggiungiLibriDialogView.fxml", ex);
        }  
    }

    /**
     * @brief Metodo per l'aggiornamento del valore DisableProperty del nodo passato come parametro.
     * @pre Viene rilevato un cambiamento all'interno di un campo di testo.
     * @post Il valore DisableProperty sarà impostato True se è possibile procedere con l'inserimento, altrimenti False.
     * @param[in] ok Il Nodo (Bottone) di cui aggiornare la Property.
     */
    private void aggiornaOk(Node ok) {
        boolean valido = !(titoloField.getText().isEmpty() || titoloField.getText().matches("^\\s+$"));
        valido &= !(autoriField.getText().isEmpty() || autoriField.getText().matches("^\\s+$"));
        valido &= !annoField.getText().isEmpty();
        valido &= !annoError.isVisible();
        valido &= !isbnField.getText().isEmpty();
        valido &= !isbnError.isVisible();
        valido &= !copieField.getText().isEmpty();
        valido &= !copieError.isVisible();
        ok.setDisable(!valido);
    }
}
