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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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

    @FXML public TableView<Libro> tabLibri;

    @FXML public TableColumn<Libro, String> cTitolo;

    @FXML public TableColumn<Libro, String> cAutori;

    @FXML public TableColumn<Libro, Integer> cAnno;

    @FXML public TableColumn<Libro, String> cIsbn;

    @FXML public TableColumn<Libro, Integer> cCopieTotali;

    @FXML public TableColumn<Libro, Integer> cCopieDisponibili;

    @FXML public TextField ricLibro;

    @FXML public Button cancLibroBtn;

    /**
     * @brief Contiene il riferimento alla lista contenente tutti i Libri registrati nella Biblioteca.
    */
    public ObservableList<Libro> listaLibri;

    /**
     * @brief Metodo di inizializzazione del Controller.
     * Ha il compito di accedere al Singleton di Biblioteca e recuperare da esso 
     * il riferimento alla struttura dati associandolo al relativo attributo del Controller.
     * @pre Viene caricata la scena della Sezione Libri.
     * @post Viene visualizzata a schermo la Sezione Libri.
    */
    @FXML
    public void initialize() {
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
                else if(u.getAutori().contains(newValue))
                    return true;
                else if(u.getIsbn().contains(newValue))
                    return true;
                return false;
            });
        });
        
        // Creazione lista ordinata per l'ordinamento nella tabella
        SortedList<Libro> libriOrdinati = new SortedList<>(libriFiltrati);
        
        // Impostazione di cambiamento cella con TextField per modifica campo
        cTitolo.setCellFactory(TextFieldTableCell.forTableColumn());
        cAutori.setCellFactory(TextFieldTableCell.forTableColumn());
        cAnno.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cIsbn.setCellFactory(TextFieldTableCell.forTableColumn());
        cCopieTotali.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        // Impostazione event handler al completamento della modifica
        cTitolo.setOnEditCommit(e -> {
            e.getRowValue().setTitolo(e.getNewValue());
            System.out.println("Modifica cognome -> " + libriOrdinati);
        });
        cAutori.setOnEditCommit(e -> {
            e.getRowValue().setAutori(e.getNewValue());
            System.out.println("Modifica autori -> " + libriOrdinati);
        });
        cAnno.setOnEditCommit(e -> {
            if(e.getNewValue().intValue() < 0 ||  e.getNewValue().intValue() > LocalDate.now().getYear()){
                new Alert(Alert.AlertType.ERROR, "L'anno inserito (" + e.getNewValue() + ") non è valido! Deve essere compreso tra 0 e l'anno corrente.", ButtonType.CANCEL).showAndWait();
                tabLibri.refresh();
            }
            else{
                e.getRowValue().setAnno(e.getNewValue());
                System.out.println("Modifica anno -> " + libriOrdinati);
            }
        });
        cIsbn.setOnEditCommit(e -> {
            if(!(e.getNewValue().matches("^(978|979)\\d{10}$"))){
                new Alert(Alert.AlertType.ERROR, "L'ISBN inserito (" + e.getNewValue() + ") non è valido! Deve essere ISBN di 13 cifre avente prefisso standard 978 o 979.", ButtonType.CANCEL).showAndWait();
                tabLibri.refresh();
            } else if (listaLibri.contains(new Libro("", "", -1, e.getNewValue(), -1))){
                new Alert(Alert.AlertType.ERROR, "È già presente un Libro avente l'ISBN inserito (" + e.getNewValue() + ")!", ButtonType.CANCEL).showAndWait();
                tabLibri.refresh();
            } else{
            e.getRowValue().setIsbn(e.getNewValue());
            System.out.println("Modifica isbn -> " + libriOrdinati);
            }
        });
        cCopieTotali.setOnEditCommit(e -> {
            if(e.getNewValue().intValue()<1){
                new Alert(Alert.AlertType.ERROR, "Le copieTotali inserite (" + e.getNewValue() + ") non sono valide! Ci deve essere almeno una copia del Libro.", ButtonType.CANCEL).showAndWait();
                tabLibri.refresh();
            } else {
                e.getRowValue().setCopieTotali(e.getNewValue());
                System.out.println("Modifica copieTotali -> " + libriOrdinati);
                }
        });
        
        // Impostazione elementi tabella
        tabLibri.setItems(libriOrdinati);
        
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
    public void aggiungiLibro() {
        // Chiamata alla finestra di dialogo e attesa per un risultato opzionale
        Optional<Libro> result = new AggiungiLibroDialog().showAndWait();
        // Se il risultato è presente controlla che non sia un duplicato e lo aggiunge alla lista
        result.ifPresent(libro -> {
            if(listaLibri.contains(libro))
                new Alert(Alert.AlertType.ERROR, "È già presente un Libro avente l'ISBN inserito (" + libro.getIsbn() + ")!", ButtonType.CANCEL).showAndWait();
            else
                listaLibri.add(libro);
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
    public void cancellaLibro() {
        // Riferimento al libro selezionato
        Libro sel = tabLibri.getSelectionModel().getSelectedItem();
        // Se il libro selezionato ha copie attualmente in prestito (copieTotali != copieDisponibili) viene mostrato un alert di errore, altrimenti un alert di conferma della cancellazione
        if(sel.getCopieTotali() != sel.getCopieDisponibili())
            new Alert(Alert.AlertType.ERROR, "Il Libro selezionato per la cancellazione ha ancora sue copie in prestitoi! Non può essere cancellato.", ButtonType.CANCEL).showAndWait();
        else {
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Confermi la cancellazione del Libro selezionato?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
            result.ifPresent(db -> {
                if(db == ButtonType.OK)
                    listaLibri.remove(sel);
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
    public void tornaIndietro() {
        try {
            App.setRoot("/G19/Biblioteca/DashboardGeneraleView", new DashboardGeneraleController());
        } catch(IOException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare DashboardGeneraleView.fxml", ex); 
        }
    }
}
