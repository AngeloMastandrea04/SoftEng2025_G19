package G19.SezioneUtenti;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
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
            
    private final StringProperty nome= new SimpleStringProperty();

    private final StringProperty cognome= new SimpleStringProperty();

    private final StringProperty matricola= new SimpleStringProperty();

    private final StringProperty email= new SimpleStringProperty();;

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
        this.nome.set(nome);
        this.cognome.set(cognome);
        this.matricola.set(matricola);
        this.email.set(email);
        prestitiAttivi = FXCollections.observableArrayList();
    }

    /**
     * @brief Getter standard per la matricola.
     * @return Il valore della property matricola dell'Utente.
     */
    public String getNome() {
        return nome.get();
    }

    /**
     * @brief Setter standard per il campo nome.
     * @post Il valore della property nome è aggiornato con il valore del parametro in ingresso.
     * @param[in] nome Il valore da settare per il campo nome.
     */
    public void setNome(String nome) {
        this.nome.set(nome);
    }

    /**
     * @brief Getter standard per la matricola.
     * @return Il valore della property matricola dell'Utente.
     */
    public String getCognome() {
        return cognome.get();
    }

    /**
     * @brief Setter standard per il campo cognome.
     * @post Il valore della property cognome è aggiornato con il valore del parametro in ingresso.
     * @param[in] cognome Il valore da settare per il campo cognome.
     */
    public void setCognome(String cognome) {
        this.cognome.set(cognome);
    }

    /**
     * @brief Getter standard per la matricola.
     * @return Il valore della property matricola dell'Utente.
     */
    public String getMatricola() {
        return matricola.get();
    }

    /**
     * @brief Setter standard per il campo matricola.
     * @post Il valore della property matricola è aggiornato con il valore del parametro in ingresso.
     * @param[in] matricola Il valore da settare per il campo matricola.
     */
    public void setMatricola(String matricola) {
        this.matricola.set(matricola);
    }

    /**
     * @brief Getter standard per la matricola.
     * @return Il valore della property matricola dell'Utente.
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * @brief Setter standard per il campo email.
     * @post Il valore della property email è aggiornato con il valore del parametro in ingresso.
     * @param[in] nome Il valore da settare per il campo email.
     */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /**
     * @brief Getter standard per la lista prestiti dell'Utente.
     * @return La lista prestiti dell'Utente.
     */
    public ObservableList<String> getPrestitiAttivi() {
        return prestitiAttivi;
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property nome.
     * @return La property nome dell'Utente.
     */
    public StringProperty nomeProperty() {
       return nome;
    }

    /**
     * @brief Metodo necessario per Binding tra la TableView e la property cognome.
     * @return La property cognome dell'Utente.
     */
    public StringProperty cognomeProperty() {
        return cognome;
    }

    /**
     * @brief Metodo necessario per Binding tra la TableView e la property matricola.
     * @return La property matricola dell'Utente.
     */
    public StringProperty matricolaProperty() {
        return matricola;
    }

    /**
     * @brief Metodo necessario per Binding tra la TableView e la property email.
     * @return La property email dell'Utente.
     */
    public StringProperty emailProperty() {
        return email;
    }

    /**
     * @brief Il metodo equals() di Object overraidato per la classe Utente.
     *        Il confronto è fatto sulla matricola.
     * @return True se l'oggetto passato come parametro è uguale a questo oggetto Utente, False altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj==this) return true;
        if(!(obj instanceof Utente)) return false;
            Utente u=(Utente) obj;
            return this.matricola.get().equals(u.matricola.get());
    }

    /**
     * @brief Override del metodo toString() di Object.
     * @return Una rappresentazione in String dell'oggetto Utente. 
     */
    @Override
    public String toString() {
        return "Cognome: " + getCognome() + ", Nome: " + getNome() + ", Matricola: " + getMatricola() + ", Email: " + getEmail();
    }

    /**
     * @brief Una rappresentazione in Stringa dell'oggetto Utente, comoda per la visualizzazione nella sez Prestiti.
     * @return Ulteriore rappresentazione in String dell'oggetto Utente.
     */
    public String toStringPrestito() {
        return "Cognome: " + getCognome() + ", Nome: " + getNome() + ", Matricola: " + getMatricola();
    }
}
