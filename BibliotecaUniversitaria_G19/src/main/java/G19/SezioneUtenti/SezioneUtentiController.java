package G19.SezioneUtenti;

import G19.Biblioteca.App;
import G19.Biblioteca.Biblioteca;
import G19.Biblioteca.DashboardGeneraleController;
import java.io.IOException;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @brief La classe SezioneUtentiController si occupa di gestire tutte le operazioni sulla struttura dati contenente gli Utenti.
 * 
 * Il Controller contiene la TableView per la lista degli Utenti e le relative colonne,
 * un campo di testo per la ricerca e il bottone per cancellare un Utente.
 * Il Controller ha un riferimento alla lista degli Utenti contenuta nella classe Biblioteca.
 * 
*/
public class SezioneUtentiController {

    @FXML TableView<Utente> tabUtenti; // Visibilità di default per testing

    @FXML TableColumn<Utente, String> cCognome; // Visibilità di default per testing

    @FXML TableColumn<Utente, String> cNome; // Visibilità di default per testing

    @FXML TableColumn<Utente, String> cMatricola; // Visibilità di default per testing

    @FXML TableColumn<Utente, String> cEmail; // Visibilità di default per testing

    @FXML TableColumn<Utente, String> cPrestitiAttivi; // Visibilità di default per testing

    @FXML TextField ricUtente; // Visibilità di default per testing

    @FXML Button cancUtenteBtn; // Visibilità di default per testing
    /**
     * @brief Contiene il riferimento alla lista contenente tutti gli Utenti registrati nella Biblioteca.
     */
    ObservableList<Utente> listaUtenti; // Visibilità di default per testing
    
    /**
     * @brief Metodo di inizializzazione del Controller.
     * Ha il compito di accedere al Singleton di Biblioteca e recuperare da esso
     * il riferimento alla struttura dati associandolo al relativo attributo del Controller.
     * @pre Viene caricata la scena della Sezione Utenti.
     * @post Viene visualizzata a schermo la Sezione Utenti.
     */
    @FXML
    private void initialize() {
        // Recupero il riferimento alla struttura dati contenente gli utenti
        listaUtenti = Biblioteca.getInstance().getListaUtenti();

        // Impostazione valori delle celle della TableView
        cCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cMatricola.setCellValueFactory(new PropertyValueFactory<>("matricola"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cPrestitiAttivi.setCellValueFactory(dati -> {
            ObservableList<String> prestiti = dati.getValue().getPrestitiAttivi();
            return Bindings.createStringBinding(() -> String.join(",\n", prestiti), prestiti);
        });

        // Creazione lista filtrata per la ricerca e impostazione listener per la ricerca
        FilteredList<Utente> utentiFiltrati = new FilteredList<>(listaUtenti, p -> true);
        ricUtente.textProperty().addListener((observable, oldValue, newValue) -> {
            utentiFiltrati.setPredicate(u -> {
                if(newValue == null || newValue.isEmpty())
                    return true;
                if(u.getCognome().toLowerCase().contains(newValue.toLowerCase()))
                    return true;
                else if(u.getMatricola().contains(newValue))
                    return true;
                return false;
            });
        });
        
        /* Creazione lista ordinata per l'ordinamento nella tabella e
           binding tra il comparatore della tabella e quello usato dalla
           SortedList.
        */
        SortedList<Utente> utentiOrdinati = new SortedList<>(utentiFiltrati);  
        utentiOrdinati.comparatorProperty().bind((tabUtenti.comparatorProperty()));
        
        // Impostazione di cambiamento cella con TextField per modifica campo
        cCognome.setCellFactory(TextFieldTableCell.forTableColumn());
        cNome.setCellFactory(TextFieldTableCell.forTableColumn());
        cMatricola.setCellFactory(TextFieldTableCell.forTableColumn());
        cEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        
        // Impostazione event handler al completamento della modifica
        cCognome.setOnEditCommit(e -> {
            if(e.getNewValue().isEmpty() || e.getNewValue().matches("^\\s+$"))
                tabUtenti.refresh();
            else{
                e.getRowValue().setCognome(e.getNewValue());
                System.out.println("Modifica cognome -> " + e.getRowValue());
            }
        });
        cNome.setOnEditCommit(e -> {
            if(e.getNewValue().isEmpty() || e.getNewValue().matches("^\\s+$"))
                tabUtenti.refresh();
            else{
                e.getRowValue().setNome(e.getNewValue());
                System.out.println("Modifica nome -> " + e.getRowValue());
            }
        });
        cMatricola.setOnEditCommit(e -> {
            if(!e.getNewValue().matches("^\\d{10}$")){
                new Alert(Alert.AlertType.ERROR, "La matricola inserita (" + e.getNewValue() + ") non è valida! Deve essere di 10 cifre.", ButtonType.CANCEL).showAndWait();
                tabUtenti.refresh();
            } else if(!e.getNewValue().equals(e.getOldValue()) && listaUtenti.contains(new Utente("", "", e.getNewValue(), ""))){
                new Alert(Alert.AlertType.ERROR, "È già presente un Utente avente la matricola inserita (" + e.getNewValue() + ")!", ButtonType.CANCEL).showAndWait();
                tabUtenti.refresh();
            } else {
                e.getRowValue().setMatricola(e.getNewValue());
                System.out.println("Modifica matricola -> " + e.getRowValue());
            }
        });
        cEmail.setOnEditCommit(e -> {
            if(!e.getNewValue().matches("^[^@\\s]+@(studenti\\.uni\\.it|uni\\.it)$")){
                new Alert(Alert.AlertType.ERROR, "L'email inserita (" + e.getNewValue() + ") non è valida! Deve finire per @studenti.uni.it o @uni.it.", ButtonType.CANCEL).showAndWait();
                tabUtenti.refresh();
            } else {
                e.getRowValue().setEmail(e.getNewValue());
                System.out.println("Modifica email -> " + e.getRowValue());
            }
        });
        
        // Impostazione elementi tabella
        tabUtenti.setItems(utentiOrdinati);
        
        // Ridimensionamento automatico colonne
        tabUtenti.getColumns().forEach(column -> {
            Text t = new Text(column.getText());
            t.setFont(Font.font("System", 16));
            double maxW = t.getLayoutBounds().getWidth();
            for (int i = 0; i < tabUtenti.getItems().size(); i++) {
                if (column.getCellData(i) != null) {
                    t = new Text(column.getCellData(i).toString());
                    t.setFont(Font.font("System", 16));
                    double cellW = t.getLayoutBounds().getWidth();
                    if (cellW > maxW)
                        maxW = cellW;
                }
            }
            column.setPrefWidth(maxW + 20.0d);
        });
        
        // Blocco modifica per utenti con prestiti attivi
        tabUtenti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                boolean editable = newValue.getPrestitiAttivi().isEmpty();
                cCognome.setEditable(editable);
                cNome.setEditable(editable);
                cMatricola.setEditable(editable);
                cEmail.setEditable(editable);
            }
        });
        
        // Binding tra l'abilitazione del pulsante cancella e la selezione di un elemento nella tabella
        cancUtenteBtn.disableProperty().bind(tabUtenti.getSelectionModel().selectedItemProperty().isNull());
    }
    
    /**
     * @brief Metodo di aggiunta dell'Utente. 
     * Ha il compito di aggiungere i dati dell'Utente passati dalla finestra di dialogo alla relativa struttura dati.
     * @pre Viene premuto il bottone "Aggiungi".
     * @post Se non ci sono duplicati viene aggiunto un Utente alla lista, altrimenti lancia un alert di errore.
     */
    @FXML
    private void aggiungiUtente() {
        // Chiamata alla finestra di dialogo e attesa per un risultato opzionale
        Optional<Utente> result = new AggiungiUtenteDialog().showAndWait();
        // Se il risultato è presente controlla che non sia un duplicato e lo aggiunge alla lista
        result.ifPresent(utente -> {
            if(listaUtenti.contains(utente))
                new Alert(Alert.AlertType.ERROR, "È già presente un Utente avente la matricola inserita (" + utente.getMatricola() + ")!", ButtonType.CANCEL).showAndWait();
            else {
                listaUtenti.add(utente);
                System.out.println("Aggiunta utente -> " + utente);
            }
        });
    }
    
    /**
     * @brief Metodo di cancellazione dell'Utente. 
     * Ha il compito di cancellare l'Utente dalla relativa struttura dati.
     * @pre Viene selezionato un Utente dalla lista e viene premuto il bottone "Cancella".
     * @post Viene mostrato un alert di conferma e, se l'Utente selezionato non ha prestiti attivi viene cancellato dalla lista,
     * altrimenti lancia un alert di errore.
    */
    @FXML
    private void cancellaUtente() {
        // Riferimento all'utente selezionato
        Utente sel = tabUtenti.getSelectionModel().getSelectedItem();
        // Se l'utente selezionato ha prestiti attivi viene mostrato un alert di errore, altrimenti un alert di conferma della cancellazione
        if(!sel.getPrestitiAttivi().isEmpty())
            new Alert(Alert.AlertType.ERROR, "L'Utente selezionato per la cancellazione ha ancora prestiti attivi! Non può essere cancellato.", ButtonType.CANCEL).showAndWait();
        else {
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Confermi la cancellazione dell'Utente selezionato?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
            result.ifPresent(db -> {
                if(db == ButtonType.OK){
                    listaUtenti.remove(sel);
                    System.out.println("Cancellazione utente -> " + sel);
                }
            });
        }
    }

    /**
     * @brief Gestisce il ritorno alla Dashboard Generale.
     * Ha il compito di far tornare alla Dashboard Generale
     * @pre Viene premuto il bottone "Torna indietro".
     * @post Viene caricata la scena della Dashboard Generale.
    */
    @FXML
    private void tornaIndietro() {
        try {
            App.setRoot("/G19/Biblioteca/DashboardGeneraleView", new DashboardGeneraleController());
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare DashboardGeneraleView.fxml", ex);
        }
    }
}
