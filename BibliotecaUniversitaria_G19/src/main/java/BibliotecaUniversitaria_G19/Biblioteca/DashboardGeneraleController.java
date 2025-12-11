package BibliotecaUniversitaria_G19.Biblioteca;

import BibliotecaUniversitaria_G19.SezioneLibri.SezioneLibriController;
import BibliotecaUniversitaria_G19.SezionePrestiti.SezionePrestitiController;
import BibliotecaUniversitaria_G19.SezioneUtenti.SezioneUtentiController;
import java.io.IOException;
import javafx.fxml.FXML;

/**
 * @brief La classe DashboardGeneraleController gestisce la logica della Dashboard Generale.
 * Questo Controller permette all'Utente di accedere alle Sezioni di gestione Utenti, Libri, Prestiti.
*/
public class DashboardGeneraleController {
    
    /**
     * @brief Metodo di inizializzazione del Controller.
     * Ha il compito di configurare lo stato iniziale della Dashboard.
     * @pre Viene caricata la scena della Dashboard Generale.
     * @post Viene visualizzata a schermo la Dashboard Generale.
    */
    @FXML
    private void initialize() {
    }
    
    /**
     * @brief Gestisce l'apertura della Sezione Utenti.
     * Metodo associato al pulsante di "Sezione Utenti". Si occupa di caricare 
     * la schermata dedicata alla visualizzazione dell'elenco e alla gestione degli 
     * Utenti iscritti alla Biblioteca.
     * @pre Viene premuto il bottone "Sezione Utenti".
     * @post Viene caricata la scena della Sezione Utenti.
    */
    @FXML
    private void apriSezUtenti() {
        try {
            App.setRoot("/BibliotecaUniversitaria_G19/SezioneUtenti/SezioneUtentiView", new SezioneUtentiController());
        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare SezioneUtentiView.fxml", ex);
        }
    }
    
    /**
     * @brief Gestisce l'apertura della Sezione Libri.
     * Metodo associato al pulsante di "Sezione Libri". Si occupa di caricare 
     * la schermata dedicata alla visualizzazione del catalogo e alla gestione dei 
     * Libri presenti nella Biblioteca.
     * @pre Viene premuto il bottone "Sezione Libri".
     * @post Viene caricata la scena della Sezione Libri.
    */
    @FXML
    private void apriSezLibri() {
        try {
            App.setRoot("/BibliotecaUniversitaria_G19/SezioneLibri/SezioneLibriView", new SezioneLibriController());
        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare SezioneLibriView.fxml", ex);
        }
    }
    
    /**
     * @brief Gestisce l'apertura della Sezione Prestiti.
     * Metodo associato al pulsante di "Sezione Prestiti". Si occupa di caricare 
     * la schermata dedicata alla visualizzazione della lista dei Prestiti attivi e alla gestione  
     * di essi.
     * @pre Viene premuto il bottone "Sezione Prestiti".
     * @post Viene caricata la scena della Sezione Prestiti.
    */
    @FXML
    private void apriSezPrestiti() {
        try {
            App.setRoot("/BibliotecaUniversitaria_G19/SezionePrestiti/SezionePrestitiView", new SezionePrestitiController());
        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare SezionePrestitiView.fxml", ex);
        }
    }
}
