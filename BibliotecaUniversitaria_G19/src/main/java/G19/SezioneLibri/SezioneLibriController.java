package G19.SezioneLibri;

import G19.Biblioteca.App;
import G19.Biblioteca.Biblioteca;
import G19.Biblioteca.DashboardGeneraleController;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;

/**
 * @brief La classe SezioneLibriController si occupa di gestire tutte le operazioni sulla struttura dati contenente i Libri.
 * 
 * Il Controller contiene la TableView per la lista dei Libri e le relative colonne,
 * un campo di testo per la ricerca e il bottone per cancellare un Libro.
 * Il Controller ha un riferimento alla lista di Libri contenuta nella classe Biblioteca.
 * 
*/

public class SezioneLibriController {

    @FXML TableView<Libro> tabLibri;                                // Visibilità di default per testing

    @FXML TableColumn<Libro, String> cTitolo;                       // Visibilità di default per testing

    @FXML TableColumn<Libro, String> cAutori;                       // Visibilità di default per testing

    @FXML TableColumn<Libro, Integer> cAnno;                        // Visibilità di default per testing

    @FXML TableColumn<Libro, String> cIsbn;                         // Visibilità di default per testing

    @FXML TableColumn<Libro, Integer> cCopieTotali;                 // Visibilità di default per testing

    @FXML TableColumn<Libro, Integer> cCopieDisponibili;            // Visibilità di default per testing

    @FXML TextField ricLibro;                                       // Visibilità di default per testing

    @FXML Button cancLibroBtn;                                      // Visibilità di default per testing

    /**
     * @brief Contiene il riferimento alla lista contenente tutti i Libri registrati nella Biblioteca.
    */
    ObservableList<Libro> listaLibri;                               // Visibilità di default per testing

    /**
     * @brief Metodo di inizializzazione del Controller.
     * Ha il compito di accedere al Singleton di Biblioteca e recuperare da esso 
     * il riferimento alla struttura dati associandolo al relativo attributo del Controller.
     * @pre Viene caricata la scena della Sezione Libri.
     * @post Viene visualizzata a schermo la Sezione Libri.
    */
    @FXML
    private void initialize() {
        // Recupero il riferimento alla struttura dati contenente i libri
        listaLibri = Biblioteca.getInstance().getListaLibri();
        
        // Impostazione valori delle celle della TableView
        cTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        cAutori.setCellValueFactory(new PropertyValueFactory<>("autori"));
        cAnno.setCellValueFactory(new PropertyValueFactory<>("anno"));
        cIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        cCopieTotali.setCellValueFactory(new PropertyValueFactory<>("copieTotali"));
        cCopieDisponibili.setCellValueFactory(new PropertyValueFactory<>("copieDisponibili"));

        // Creazione lista filtrata per la ricerca e impostazione listener per la ricerca
        FilteredList<Libro> libriFiltrati = new FilteredList<>(listaLibri, p -> true);
        ricLibro.textProperty().addListener((observable, oldValue, newValue) -> {
            libriFiltrati.setPredicate(u -> {
                if(newValue == null || newValue.isEmpty())
                    return true;
                if(u.getTitolo().toLowerCase().contains(newValue.toLowerCase()))
                    return true;
                else if(u.getAutori().toLowerCase().contains(newValue.toLowerCase()))
                    return true;
                else if(u.getIsbn().contains(newValue))
                    return true;
                return false;
            });
        });
        
        /* Creazione lista ordinata per l'ordinamento nella tabella e
           binding tra il comparatore della tabella e quello usato dalla
           SortedList.
        */
        SortedList<Libro> libriOrdinati = new SortedList<>(libriFiltrati);
        libriOrdinati.comparatorProperty().bind((tabLibri.comparatorProperty()));
        
        // Impostazione di cambiamento cella con TextField per modifica campo
        cTitolo.setCellFactory(TextFieldTableCell.forTableColumn());
        cAutori.setCellFactory(TextFieldTableCell.forTableColumn());
        cAnno.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cIsbn.setCellFactory(TextFieldTableCell.forTableColumn());
        cCopieTotali.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        // Impostazione event handler al completamento della modifica
        cTitolo.setOnEditCommit(e -> {
            if(e.getNewValue().isEmpty() || e.getNewValue().matches("^\\s+$"))
                tabLibri.refresh();
            else{
                e.getRowValue().setTitolo(e.getNewValue());
                System.out.println("Modifica titolo -> " + e.getRowValue());
            }
        });
        cAutori.setOnEditCommit(e -> {
            if(e.getNewValue().isEmpty() || e.getNewValue().matches("^\\s+$"))
                tabLibri.refresh();
            else{
                e.getRowValue().setAutori(e.getNewValue());
                System.out.println("Modifica autori -> " + e.getRowValue());
            }
        });
        cAnno.setOnEditCommit(e -> {
            if(e.getNewValue() == null || e.getNewValue().intValue() < 0 ||  e.getNewValue().intValue() > LocalDate.now().getYear()){
                new Alert(Alert.AlertType.ERROR, "L'anno inserito (" + e.getNewValue() + ") non è valido! Deve essere compreso tra 0 e l'anno corrente.", ButtonType.CANCEL).showAndWait();
                tabLibri.refresh();
            }
            else{
                e.getRowValue().setAnno(e.getNewValue());
                System.out.println("Modifica anno -> " + e.getRowValue());
            }
        });
        cIsbn.setOnEditCommit(e -> {
            if(!(e.getNewValue().matches("^(978|979)\\d{10}$"))){
                new Alert(Alert.AlertType.ERROR, "L'ISBN inserito (" + e.getNewValue() + ") non è valido! Deve essere ISBN di 13 cifre avente prefisso standard 978 o 979.", ButtonType.CANCEL).showAndWait();
                tabLibri.refresh();
            } else if (!e.getNewValue().equals(e.getOldValue()) && listaLibri.contains(new Libro("", "", -1, e.getNewValue(), -1))){
                new Alert(Alert.AlertType.ERROR, "È già presente un Libro avente l'ISBN inserito (" + e.getNewValue() + ")!", ButtonType.CANCEL).showAndWait();
                tabLibri.refresh();
            } else{
            e.getRowValue().setIsbn(e.getNewValue());
            System.out.println("Modifica isbn -> " + e.getRowValue());
            }
        });
        cCopieTotali.setOnEditCommit(e -> {
            if(e.getNewValue() == null || e.getNewValue().intValue()<1){
                new Alert(Alert.AlertType.ERROR, "Le copieTotali inserite (" + e.getNewValue() + ") non sono valide! Ci deve essere almeno una copia del Libro.", ButtonType.CANCEL).showAndWait();
                tabLibri.refresh();
            } else if(e.getNewValue() < e.getOldValue() - e.getRowValue().getCopieDisponibili()){
                new Alert(Alert.AlertType.ERROR, "Le copieTotali inserite (" + e.getNewValue() + ") non sono valide! Devono essere almeno uguali alle copie date in prestito.", ButtonType.CANCEL).showAndWait();
            } else {
                e.getRowValue().setCopieDisponibili(e.getNewValue() - e.getOldValue() + e.getRowValue().getCopieDisponibili());
                System.out.println("Modifica copieDisponibili -> " + e.getRowValue());
                e.getRowValue().setCopieTotali(e.getNewValue());
                System.out.println("Modifica copieTotali -> " + e.getRowValue());
            }
        });
        
        // Impostazione elementi tabella
        tabLibri.setItems(libriOrdinati);
        
        // Ridimensionamento automatico colonne
        tabLibri.getColumns().forEach(column -> {
            Text t = new Text(column.getText());
            t.setFont(Font.font("System", 16));
            double maxW = t.getLayoutBounds().getWidth();
            for (int i = 0; i < tabLibri.getItems().size(); i++) {
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
        
        // Blocco modifica per libri con prestiti attivi
        tabLibri.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                boolean editable;
                editable = newValue.getCopieTotali() == newValue.getCopieDisponibili();     //se il libro ha copie in prestito, non può essere modificato
                cTitolo.setEditable(editable);
                cAutori.setEditable(editable);
                cAnno.setEditable(editable);
                cIsbn.setEditable(editable);
                cCopieTotali.setEditable(editable);
            }
        });
        
        //Mosta l'Alert se si tenta di modificare un libro con copie in prestito
        tabLibri.setRowFactory(tv -> {
            TableRow<Libro> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {      //Doppio click e la riga non è vuota
                    Libro libro = row.getItem();
                    if (libro.getCopieTotali() != libro.getCopieDisponibili()) {
                        new Alert(Alert.AlertType.ERROR, "Non è possibile modificare questo Libro perché ci sono copie attualmente in prestito.", ButtonType.CANCEL).showAndWait();
                    }
                }
            });
            return row;
        });
        
        // Binding tra l'abilitazione del pulsante cancella e la selezione di un elemento nella tabella
        cancLibroBtn.disableProperty().bind(tabLibri.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * @brief Metodo di aggiunta del Libro. 
     * Ha il compito di aggiungere i dati del Libro passati dalla finestra di dialogo alla relativa struttura dati.
     * @pre Viene premuto il bottone "Aggiungi".
     * @post Se non ci sono duplicati viene aggiunto un Libro alla lista, altrimenti lancia un alert di errore.
    */
    @FXML
    private void aggiungiLibro() {
        // Chiamata alla finestra di dialogo e attesa per un risultato opzionale
        Optional<Libro> result = new AggiungiLibroDialog().showAndWait();
        // Se il risultato è presente controlla che non sia un duplicato e lo aggiunge alla lista
        result.ifPresent(libro -> {
            if(listaLibri.contains(libro))
                new Alert(Alert.AlertType.ERROR, "È già presente un Libro avente l'ISBN inserito (" + libro.getIsbn() + ")!", ButtonType.CANCEL).showAndWait();
            else{
                listaLibri.add(libro);
                System.out.println("Aggiunta libro -> " + libro);
            }
        });
    }

    /**
     * @brief Metodo di cancellazione del Libro. 
     * Ha il compito di cancellare il Libro dalla relativa struttura dati.
     * @pre Viene selezionato un Libro dalla lista e viene premuto il bottone "Cancella".
     * @post Viene mostrato un alert di conferma e, se i numeri di copie totali e disponibili combaciano il Libro selezionato viene cancellato dalla lista,
     * altrimenti lancia un alert di errore.
    */
    @FXML
    private void cancellaLibro() {
        // Riferimento al libro selezionato
        Libro sel = tabLibri.getSelectionModel().getSelectedItem();
        // Se il libro selezionato ha copie attualmente in prestito (copieTotali != copieDisponibili) viene mostrato un alert di errore, altrimenti un alert di conferma della cancellazione
        if(sel.getCopieTotali() != sel.getCopieDisponibili())
            new Alert(Alert.AlertType.ERROR, "Il Libro selezionato per la cancellazione ha ancora sue copie in prestitoi! Non può essere cancellato.", ButtonType.CANCEL).showAndWait();
        else {
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Confermi la cancellazione del Libro selezionato?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
            result.ifPresent(db -> {
                if(db == ButtonType.OK){
                    listaLibri.remove(sel);
                    System.out.println("Cancellazione libro -> " + sel);
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
        } catch(IOException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare DashboardGeneraleView.fxml", ex); 
        }
    }
}
