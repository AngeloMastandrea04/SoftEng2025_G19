package SezioneUtenti;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * @brief Un Utente registrato o che deve essere registrato nella Biblioteca.
 * Contiene nome, cognome, matricola, email e lista dei prestiti attivi dell'Utente.
 * @invariant
 * La matricola deve essere di 10 cifre.
 * @invariant
 * L'email deve terminare per @studenti.uni.it o @uni.it.
 */
public class Utente {

    private final StringProperty nome;

    private final StringProperty cognome;

    private final StringProperty matricola;

    private final StringProperty email;

    private final ObservableList<String> prestitiAttivi;

    /**
     * @brief Costruttore.
     * @param[in] nome Nome dell'Utente.
     * @param[in] cognome Cognome dell'Utente.
     * @param[in] matricola Matricola dell'Utente.
     * @param[in] email Email dell'Utente.
     * @post 
     * Verrà creato un Utente con i parametri specificati in input inseriti nei
     * corrispondenti attributi property e con la lista dei prestiti attivi 
     * inizialmente vuota.
     */
    public Utente(String nome, String cognome, String matricola, String email) {
    }

    /**
     * @brief Getter standard per la matricola.
     * @return Il valore della property matricola dell'Utente.
     */
    public String getNome() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Setter standard per il campo nome.
     * @post Il valore della property nome è aggiornato con il valore del parametro in ingresso.
     * @param[in] nome Il valore da settare per il campo nome.
     */
    public void setNome(String nome) {
    }

    /**
     * @brief Getter standard per la matricola.
     * @return Il valore della property matricola dell'Utente.
     */
    public String getCognome() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Setter standard per il campo cognome.
     * @post Il valore della property cognome è aggiornato con il valore del parametro in ingresso.
     * @param[in] cognome Il valore da settare per il campo cognome.
     */
    public void setCognome(String cognome) {
    }

    /**
     * @brief Getter standard per la matricola.
     * @return Il valore della property matricola dell'Utente.
     */
    public String getMatricola() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Setter standard per il campo matricola.
     * @post Il valore della property matricola è aggiornato con il valore del parametro in ingresso.
     * @param[in] matricola Il valore da settare per il campo matricola.
     */
    public void setMatricola(String matricola) {
    }

    /**
     * @brief Getter standard per la matricola.
     * @return Il valore della property matricola dell'Utente.
     */
    public String getEmail() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Setter standard per il campo email.
     * @post Il valore della property email è aggiornato con il valore del parametro in ingresso.
     * @param[in] nome Il valore da settare per il campo email.
     */
    public void setEmail(String email) {
    }

    /**
     * @brief Getter standard per la lista prestiti dell'Utente.
     * @return La lista prestiti dell'Utente.
     */
    public ObservableList<String> getPrestitiAttivi() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property nome.
     * @return La property nome dell'Utente.
     */
    public StringProperty nomeProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Metodo necessario per Binding tra la TableView e la property cognome.
     * @return La property cognome dell'Utente.
     */
    public StringProperty cognomeProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Metodo necessario per Binding tra la TableView e la property matricola.
     * @return La property matricola dell'Utente.
     */
    public StringProperty matricolaProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Metodo necessario per Binding tra la TableView e la property email.
     * @return La property email dell'Utente.
     */
    public StringProperty emailProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Il metodo equals() di Object overraidato per la classe Utente.
     *        Il confronto è fatto sulla matricola.
     * @return True se l'oggetto passato come parametro è uguale a questo oggetto Utente, False altrimenti.
     */
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Override del metodo toString() di Object.
     * @return Una rappresentazione in String dell'oggetto Utente. 
     */
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Ulteriore rappresentazione in Stringa dell'oggetto Utente, comoda per la visualizzazione nella sez Prestiti.
     * @return Una rappresentazione in String dell'oggetto Utente comoda per la visualizzazione nella sez Prestiti.
     */
    public String toStringPrestito() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
