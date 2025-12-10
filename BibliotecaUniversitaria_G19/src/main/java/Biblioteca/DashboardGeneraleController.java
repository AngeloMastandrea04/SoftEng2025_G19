package Biblioteca;

import SezioneLibri.SezioneLibriController;
import SezionePrestiti.SezionePrestitiController;
import SezioneUtenti.SezioneUtentiController;
import java.io.IOException;

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
    private void apriSezUtente() {
        try {
            App.setRoot("/Biblioteca/SezioneUtentiView.fxml", new SezioneUtentiController());
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
    private void apriSezLibro() {
        try {
            App.setRoot("/Biblioteca/SezioneLibriView.fxml", new SezioneLibriController());
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
    private void apriSezPrestiti() {
        try {
            App.setRoot("/Biblioteca/SezionePrestitiView.fxml", new SezionePrestitiController());
        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile caricare SezionePrestitiView.fxml", ex);
        }
    }
}
