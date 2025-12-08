package SezionePrestiti;

import java.time.LocalDate;
import javafx.beans.property.StringProperty;
/**
 * @brief Un Prestito inserito o che deve essere registrato nell'archivio.
 * Contiene utente, libro e dataRestituzione del libro.
 */
public class Prestito {

    private final StringProperty utente;

    private final StringProperty libro;

    private final LocalDate dataRestituzione;
/**
     * @brief Costruttore.
     * @param[in] utente Utente al quale associare il Prestito.
     * @param[in] libro Libro coinvolto nel Prestito.
     * @param[in] dataRestituzione Data di scadenza del Prestito.
     * @post
     * Verrà creato un Prestito con i parametri specificati in input inseriti nei
     * corrispondenti attributi property.
     * @pre
     * Il Libro è disponibile.
     * @pre
     * L'Utente è registrato nel sistema.
     * @pre 
     * La data di restituzione non è ambigua
     */
    public Prestito(String utente, String libro, LocalDate dataRestituzione) {
    }

    /**
     * @brief Getter standard per l'utente.
     * @return Il valore della property utente del Prestito.
     */
    public String getUtente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Getter standard per il libro.
     * @return Il valore della property libro del Prestito.
     */
    public String getLibro() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Getter standard per la data di restituzione.
     * @return Il valore della LocalDate dataRestituzione del Prestito.
     */
    public LocalDate getDataRestituzione() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Metodo necessario per Binding tra la TableView e la property utente.
     * @return La property utente del Prestito.
     */
    public StringProperty utenteProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Metodo necessario per Binding tra la TableView e la property libro.
     * @return La property libro del Prestito.
     */
    public StringProperty libroProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Override di toString() di Object per la stampa delle informazioni riguardanti il Prestito.
     * @return Stringa rappresentante del Prestito.
     */
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Ulteriore rappresentazione in Stringa dell'oggetto Prestito, comoda per la visualizzazione dei prestiti attivi di un utente.
     *
     * * @return Una rappresentazione in String dell'oggetto Prestito.
     */
    public String toStringUtente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
