package SezionePrestiti;

import Biblioteca.Biblioteca;
import SezioneUtenti.Utente;
import SezioneLibri.Libro;
import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * @brief La classe AggiungiPrestitoDialog si occupa della creazione di un nuovo oggetto Prestito a partire dalla lettura degli attributi dai ComboBox e dal DatePicker.
 * 
 * Le operazioni interne nello specifico sono di lettura dai ComboBox e dal DatePicker, di creazione di un nuovo oggetto Prestito,
 * di ritorno di un risultato opzionale e di aggiornamento del pulsante ok e
 * sono gestite tramite il costruttore e un metodo privato.
 * 
 * La Finestra di Dialogo contiene i ComboBox per l'inserimento di Utente e Libro, un DatePicker per l'inserimento della Data di Restituzione, le relative Label di errore e le liste contenenti Utenti e Libri.
 */
public class AggiungiPrestitoDialog extends Dialog<Prestito>{

    @FXML private ComboBox<Utente> utenteBox;

    @FXML private Label utenteError;

    @FXML private ComboBox<Libro> libroBox;

    @FXML private Label libroError;

    @FXML private DatePicker dataRestituzionePicker;

    @FXML private Label dataRestituzioneError;

    @FXML private final ObservableList<Utente> utenti;
    
    @FXML private final ObservableList<Libro> libri;

    /**
     * @brief Costruttore.
     * @pre È stato premuto il bottone "Aggiungi" in SezionePrestitiController.
     * @post Se viene premuto il pulsante "OK" viene impostato come risultato un oggetto
     *       di tipo Prestito con i corrispondenti attributi letti dai ComboBox e dal DatePicker, altrimenti null.
     */
    public AggiungiPrestitoDialog() {
        try{
            // Caricamento file FXML, impostazione controller, DialogPane, grandezza fissa, icona e titolo.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SezionePrestiti/AggiungiPrestitoDialogView.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
            this.setResizable(false);
            Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/Biblioteca/Icona.png").toExternalForm()));
            this.setTitle("Registra un Prestito");
            
            // Disabilitazione iniziale pulsante ok
            Node ok = this.getDialogPane().lookupButton(ButtonType.OK);
            ok.setDisable(true);
                        
            //Impostazione StringConverter per le ComboBox
            utenteBox.setConverter(new StringConverter<Utente>(){
                @Override
                public String toString(Utente u){
                    if(u == null)
                        return null;
                    return u.toStringPrestito();
                }
                @Override
                public Utente fromString(String s){
                    if(s == null || s.isEmpty())
                        return null;
                    return utenti.stream().filter(u -> u.toStringPrestito().equals(s)).findFirst().orElse(null);
                }
            });
            libroBox.setConverter(new StringConverter<Libro>(){
                @Override
                public String toString(Libro l){
                    if(l == null)
                        return null;
                    return l.toStringPrestito();
                }
                @Override
                public Libro fromString(String s){
                    if(s == null || s.isEmpty())
                        return null;
                    return libri.stream().filter(l -> l.toStringPrestito().equals(s)).findFirst().orElse(null);
                }
            });
            
            // Caricamento liste Utenti e Libri, creazione liste filtrabili e assegnazione elementi ComboBox
            utenti = Biblioteca.getInstance().getListaUtenti();
            libri = Biblioteca.getInstance().getListaLibri();
            FilteredList<Utente> utentiFiltrati = new FilteredList(utenti, p -> true);
            FilteredList<Libro> libriFiltrati = new FilteredList(libri, p -> true);
            utenteBox.setItems(utentiFiltrati);
            libroBox.setItems(libriFiltrati);
            
            // Listener su ComboBox e DatePicker e impostazione predicati delle liste filtrabili
            utenteBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                utenteError.setVisible(newValue.getPrestitiAttivi().size() > 2);
                aggiornaOk(ok);
            });
            utenteBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                utentiFiltrati.setPredicate(u -> {
                    if(newValue == null || newValue.isEmpty())
                        return true;
                    return u.toStringPrestito().toLowerCase().contains(newValue.toLowerCase());
                });
            });
            libroBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                libroError.setVisible(newValue.getCopieDisponibili() < 1);
                aggiornaOk(ok);
            });
            libroBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                libriFiltrati.setPredicate(l -> {
                    if(newValue == null || newValue.isEmpty())
                        return true;
                    return l.toStringPrestito().toLowerCase().contains(newValue.toLowerCase());
                });
            });
            dataRestituzionePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                dataRestituzioneError.setVisible(newValue.isBefore(LocalDate.now()));
                aggiornaOk(ok);
            });
            
            // Imposta il risultato
            this.setResultConverter(db -> {
                if(db == ButtonType.OK)
                    return new Prestito(utenteBox.getSelectionModel().getSelectedItem().toStringPrestito(),
                                        libroBox.getSelectionModel().getSelectedItem().toStringPrestito(),
                                        dataRestituzionePicker.getValue());
                return null;
            });  
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare AggiungiPrestitoDialogView.fxml", ex);
        }
    }

    /**
     * @brief Metodo per l'aggiornamento del valore DisableProperty del nodo passato come parametro.
     * @pre Viene rilevato un cambiamento all'interno di una ComboBox o del DatePicker.
     * @post Il valore DisableProperty sarà impostato True se è possibile procedere con l'inserimento, altrimenti False.
     * @param[in] ok Il Nodo (Bottone) di cui aggiornare la Property.
     */
    private void aggiornaOk(Node ok) {
        boolean valido = !utenteBox.getSelectionModel().isEmpty();
        valido &= !utenteError.isVisible();
        valido &= !libroBox.getSelectionModel().isEmpty();
        valido &= !libroError.isVisible();
        valido &= dataRestituzionePicker.getValue() != null;
        valido &= !dataRestituzioneError.isVisible();
        ok.setDisable(!valido);
    }
}
