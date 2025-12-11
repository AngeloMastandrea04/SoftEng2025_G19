package SezionePrestiti;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * @brief Un Prestito inserito o che deve essere registrato nell'archivio.
 * Contiene utente, libro e dataRestituzione del libro.
 * @invariant
 * Il Libro deve disponibile.
 * @invariant
 * L'Utente deve essere registrato nel Sistema.
 * @invariant
 * La data di restituzione deve essere uguale o successiva a quella corrente.
 */
public class Prestito {

    private final StringProperty utente=new SimpleStringProperty();

    private final StringProperty libro=new SimpleStringProperty();

    private final LocalDate dataRestituzione;
/**
     * @brief Costruttore.
     * @param[in] utente Utente al quale associare il Prestito.
     * @param[in] libro Libro coinvolto nel Prestito.
     * @param[in] dataRestituzione Data di restituzione del Prestito.
     * @post
     * Verrà creato un Prestito con i parametri specificati in input inseriti nei
     * corrispondenti attributi property.
     */
    public Prestito(String utente, String libro, LocalDate dataRestituzione){
        this.utente.set(utente);
        this.libro.set(libro);
        this.dataRestituzione=dataRestituzione;
    }

    /**
     * @brief Getter standard per l'utente.
     * @return Il valore della property utente del Prestito.
     */
    public String getUtente() {
        return utente.get();
    }

    /**
     * @brief Getter standard per il libro.
     * @return Il valore della property libro del Prestito.
     */
    public String getLibro() {
        return libro.get();
    }

    /**
     * @brief Getter standard per la data di restituzione.
     * @return Il valore della LocalDate dataRestituzione del Prestito.
     */
    public LocalDate getDataRestituzione() {
        return dataRestituzione;
    }

    /**
     * @brief Metodo necessario per Binding tra la TableView e la property utente.
     * @return La property utente del Prestito.
     */
    public StringProperty utenteProperty() {
        return utente;
    }

    /**
     * @brief Metodo necessario per Binding tra la TableView e la property libro.
     * @return La property libro del Prestito.
     */
    public StringProperty libroProperty() {
        return libro;
    }
    
    /**
     * @brief Override del metodo toString() di Object.
     * @return Una rappresentazione in String dell'oggetto Prestito.
     */
    @Override
    public String toString() {
        return "Utente: " + getUtente() + ", Libro: " + getLibro() + ", Data di restituzione: " + getDataRestituzione() + "\n";
    }

    /**
     * @brief Una rappresentazione in Stringa dell'oggetto Prestito, comoda per la visualizzazione dei prestiti attivi di un Utente.
     * @return Ulteriore rappresentazione in String dell'oggetto Prestito.
     */
    public String toStringUtente() {
        throw new UnsupportedOperationException("Not supported yet.");
        // da vedere in seguito
    }
}
