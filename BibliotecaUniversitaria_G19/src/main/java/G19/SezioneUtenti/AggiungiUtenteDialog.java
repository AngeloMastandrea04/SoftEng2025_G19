package G19.SezioneUtenti;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * @brief La classe AggiungiUtenteDialog si occupa della creazione di un nuovo oggetto Utente a partire dalla lettura degli attributi dai campi di testo.
 * 
 * Le operazioni interne nello specifico sono di lettura dai campi di testo, di creazione di un nuovo oggetto Libro,
 * di ritorno di un risultato opzionale e di aggiornamento del pulsante ok e 
 * sono gestite tramite il costruttore e un metodo privato.
 * 
 * La Finestra di Dialogo contiene i campi di testo per l'inserimento di Nome, Cognome, Matricola e Email, oltre alle relative Label di errore.
 */
public class AggiungiUtenteDialog extends Dialog<Utente>{

    @FXML private TextField nomeField;

    @FXML private TextField cognomeField;

    @FXML private TextField matricolaField;

    @FXML private Label matricolaError;

    @FXML private TextField emailField;

    @FXML private Label emailError;

    /**
     * @brief Costruttore.
     * @pre È stato premuto il bottone "Aggiungi" in SezioneUtentiController.
     * @post Se viene premuto il pulsante "OK" viene impostato come risultato un oggetto
     *       di tipo Utente con i corrispondenti attributi letti dai campi di testo, altrimenti null.
     */
    public AggiungiUtenteDialog() {
        try {
            // Caricamento file FXML, impostazione controller, DialogPane, grandezza fissa, icona e titolo.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AggiungiUtenteDialogView.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
            this.setResizable(false);
            Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/G19/Biblioteca/Icona.png").toExternalForm()));
            this.setTitle("Aggiungi un Utente");
            
            // Disabilitazione iniziale pulsante ok
            Node ok = this.getDialogPane().lookupButton(ButtonType.OK);
            ok.setDisable(true);
                        
            // Listener sui TextField, con eventuale gestione degli errori di validità
            nomeField.textProperty().addListener((observable, oldValue, newValue) -> {
                aggiornaOk(ok);
            });
            cognomeField.textProperty().addListener((observable, oldValue, newValue) -> {
                aggiornaOk(ok);
            });
            matricolaField.textProperty().addListener((observable, oldValue, newValue) -> {
                matricolaError.setVisible(!newValue.matches("^\\d{10}$"));
                aggiornaOk(ok);
            });
            emailField.textProperty().addListener((observable, oldValue, newValue) -> {
                emailError.setVisible(!newValue.matches("^.+(?:@studenti\\.uni\\.it|@uni\\.it)$"));
                aggiornaOk(ok);
            });
            
            // Imposta il risultato
            this.setResultConverter(db -> {
                if(db == ButtonType.OK)
                    return new Utente(nomeField.getText(), cognomeField.getText(), matricolaField.getText(), emailField.getText());
                return null;
            });
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare AggiungiUtenteDialogView.fxml", ex);
        }  
    }

    /**
     * @brief Metodo per l'aggiornamento del valore DisableProperty del nodo passato come parametro.
     * @pre Viene rilevato un cambiamento all'interno di un campo di testo.
     * @post Il valore DisableProperty sarà impostato True se è possibile procedere con l'inserimento, altrimenti False.
     * @param[in] ok Il Nodo (Bottone) di cui aggiornare la Property.
     */
    private void aggiornaOk(Node ok) {
        boolean valido = !nomeField.getText().isEmpty();
        valido &= !nomeField.getText().isEmpty();
        valido &= !matricolaField.getText().isEmpty();
        valido &= !matricolaError.isVisible();
        valido &= !emailField.getText().isEmpty();
        valido &= !emailError.isVisible();
        ok.setVisible(!valido);
    }
}
