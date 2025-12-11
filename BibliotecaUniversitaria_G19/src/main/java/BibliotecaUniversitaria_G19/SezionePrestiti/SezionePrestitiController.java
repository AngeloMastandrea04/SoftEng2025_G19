package BibliotecaUniversitaria_G19.SezionePrestiti;

import BibliotecaUniversitaria_G19.SezioneLibri.Libro;
import BibliotecaUniversitaria_G19.SezioneUtenti.Utente;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import BibliotecaUniversitaria_G19.Biblioteca.App;
import BibliotecaUniversitaria_G19.Biblioteca.Biblioteca;
import BibliotecaUniversitaria_G19.Biblioteca.DashboardGeneraleController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.util.Scanner;
/**
 * @brief La classe SezioniPrestitiController si occupa delle operazioni da effettuare sulla struttura dati contenente i Prestiti.
 * 
 * Il Controller contiene la Tableview per la lista dei Prestiti e le relative colonne e 
 * il bottone per registrare una restituzione.
 * Il Controller ha riferimenti alle liste dei Libri, degli Utenti e dei Prestiti 
 * contenute nella classe Biblioteca.
 * 
*/
public class SezionePrestitiController {

    @FXML private TableView<Prestito> tabPrestiti;

    @FXML private TableColumn<Utente, String> cUtente;

    @FXML private TableColumn<Libro, String> cLibro;

    @FXML private TableColumn<Libro, LocalDate> cData;

    @FXML private Button cancPrestitoBtn;

    /**
     * @brief Contiene il riferimento alla lista contenente tutti gli Utenti registrati nella Biblioteca.
     */
    @FXML private ObservableList<Utente> listaUtenti;

    /**
     * @brief Contiene il riferimento alla lista contenente tutti i Libri registrati nella Biblioteca.
     */
    @FXML private ObservableList<Libro> listaLibri;

    /**
     * @brief Contiene il riferimento alla lista contenente tutti i Prestiti registrati nella Biblioteca.
     */
    @FXML private ObservableList<Prestito> listaPrestiti;

    /**
     * @brief Metodo di inizializzazione del Controller.
     * Ha il compito di accedere al Singleton di Biblioteca e recuperare da esso 
     * i riferimenti alle strutture dati associandoli ai relativi attributi del Controller.
     * @pre Viene caricata la scena della Sezione Prestiti.
     * @post Viene visualizzata a schermo la Sezione Prestiti.
     */
    @FXML 
    private void initialize() {
        // Recupero il riferimento alle strutture dati contenenti gli utenti, i libri ed i prestiti
        listaUtenti = Biblioteca.getInstance().getListaUtenti();
        listaLibri = Biblioteca.getInstance().getListaLibri();
        listaPrestiti = Biblioteca.getInstance().getListaPrestiti();

        // Collego le colonne ai getter di Persona
        cUtente.setCellValueFactory(new PropertyValueFactory<Utente, String>("utente"));
        cLibro.setCellValueFactory(new PropertyValueFactory<Libro, String>("libro"));
        cData.setCellValueFactory(new PropertyValueFactory<Libro, LocalDate>("dataRestituzione"));

        // Creazione lista ordinata per l'ordinamento nella tabella
        SortedList<Prestito> prestitiOrdinati = new SortedList<>(listaPrestiti);

        // Impostazione elementi tabella
        tabPrestiti.setItems(prestitiOrdinati);

        tabPrestiti.setRowFactory(tv -> new TableRow<Prestito>() {
            @Override
            protected void updateItem(Prestito item, boolean empty) {
                super.updateItem(item, empty); // IMPORTANTE: Mantiene la funzionalità base
                if (item == null || empty) {
                // Se la riga è vuota, rimuovi lo stile (altrimenti eredita lo stile della riga precedente riciclata)
                setStyle("");
                // Controllo ritardo
                } else if (item.getDataRestituzione().isBefore(LocalDate.now())) {
                        setStyle("-fx-background-color: rgb(255,127,127); -fx-font-weight: bold; -fx-font-style: italic;"); // Rosso chiaro
                } else {
                    setStyle("-fx-font-weight: normal;"); // Stile default per i maggiorenni
                }
            }
        });
    
        // Binding tra l'abilitazione del pulsante cancella e la selezione di un elemento nella tabella
        cancPrestitoBtn.disableProperty().bind(tabPrestiti.getSelectionModel().selectedItemProperty().isNull());

    }

    /**
     * @brief Metodo di aggiunta (registrazione) del Prestito. 
     * Ha il compito di aggiungere i dati del prestito passati dalla finestra di dialogo alla relativa struttura dati,
     * inoltre si occupa di decrementare le copie disponibili del Libro e di aggiungere il prestito tra quelli attivi per l'Utente.
     * @pre Viene premuto il bottone "Registra nuovo Prestito".
     * @post Viene aggiunto un Prestito alla lista e modificati Libro e Utente corrispondenti.
     */
    @FXML 
    private void aggiungiPrestito() {
        // Chiamata alla finestra di dialogo e attesa per un risultato opzionale
        Optional<Prestito> result = new AggiungiPrestitoDialog().showAndWait();

        // Se il risultato è presente controlla che non sia un duplicato e lo aggiunge alla lista
        result.ifPresent(prestito -> {
                listaPrestiti.add(prestito);
                //decremento le copie del libro

                Scanner scan_libro=new Scanner(prestito.getLibro());
                scan_libro.useDelimiter("ISBN:\\s*");
                Scanner scan_utente=new Scanner(prestito.getUtente());
                scan_utente.useDelimiter("Matricola:\\s*");
                String isbn= scan_libro.next();
                String matricola= scan_utente.next();
                scan_libro.close(); scan_utente.close();
                FilteredList<Libro> filteredList_libro;
                FilteredList<Utente> filteredList_utente;
                filteredList_libro= listaLibri.filtered( l -> {
                    if(l.getIsbn()==isbn)
                        return true;
                    return false;
                });
                filteredList_utente= listaUtenti.filtered( u -> {
                    if((u.getMatricola())==matricola)
                        return true;
                    return false;
                });
                Libro libro=filteredList_libro.remove(0);
                if(libro!=null) 
                libro.setCopieDisponibili(libro.getCopieDisponibili() -1);

                Utente utente=filteredList_utente.remove(0);
                if(utente!=null) 
                utente.getPrestitiAttivi().add(utente.toStringPrestito());

        });
    }
    /* 
    @FXML 
    private void aggiungiPrestito() {
        // Chiamata alla finestra di dialogo e attesa per un risultato opzionale
        Optional<Prestito> result = new AggiungiPrestitoDialog().showAndWait();

        // Se il risultato è presente controlla che non sia un duplicato e lo aggiunge alla lista
        result.ifPresent(prestito -> {
                listaPrestiti.add(prestito);
                //decremento le copie del libro

                Libro libro= listaLibri.stream()
                .filter( l  -> l.toStringPrestito().equals(prestito.getLibro())).findFirst().orElse(null);

                if(libro!=null) 
                libro.setCopieDisponibili(libro.getCopieDisponibili() -1);
        });
    }*/

    /**
     * @brief Metodo di cancellazione (restituzione) del Prestito. 
     * Ha il compito di cancellare il prestito dalla relativa struttura dati,
     * inoltre si occupa di incrementare le copie disponibili del Libro e di rimuovere il prestito da quelli attivi per l'Utente.
     * @pre Viene selezionato un Prestito dalla lista e viene premuto il bottone "Registra la Restituzione".
     * @post Viene mostrato un alert di conferma (di warning nel caso la restituzione sia in ritardo),
     * rimosso il Prestito dalla lista e modificati Libro e Utente corrispondenti.
     */
    @FXML 
    private void cancellaPrestito() {
          // Chiamata alla finestra di dialogo e attesa per un risultato opzionale
        Optional<Prestito> result = new AggiungiPrestitoDialog().showAndWait();

        // Se il risultato è presente
        result.ifPresent(prestito -> {
            listaPrestiti.remove(prestito);

                Scanner scan_libro=new Scanner(prestito.getLibro());
                scan_libro.useDelimiter("ISBN:\\s*");
                Scanner scan_utente=new Scanner(prestito.getUtente());
                scan_utente.useDelimiter("Matricola:\\s*");
                String isbn= scan_libro.next();
                String matricola= scan_utente.next();
                scan_libro.close(); scan_utente.close();
                FilteredList<Libro> filteredList_libro;
                FilteredList<Utente> filteredList_utente;
                filteredList_libro= listaLibri.filtered( l -> {
                    if(l.getIsbn()==isbn)
                        return true;
                    return false;
                });
                filteredList_utente= listaUtenti.filtered( u -> {
                    if((u.getMatricola())==matricola)
                        return true;
                    return false;
                });
                Libro libro=filteredList_libro.remove(0);
                if(libro!=null) 
                libro.setCopieDisponibili(libro.getCopieDisponibili() +1);

                Utente utente=filteredList_utente.remove(0);
                if(utente!=null) 
                utente.getPrestitiAttivi().remove(utente.toStringPrestito());

        });
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
            App.setRoot("/Biblioteca/DashboardGeneraleView.fxml", new DashboardGeneraleController());
        } catch(IOException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare DashboardGeneraleView.fxml", ex); 
        }
    }
}
