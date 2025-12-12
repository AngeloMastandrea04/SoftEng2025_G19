package G19.SezioneLibri;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * @brief Un libro inserito o che deve essere inserito nella Biblioteca.
 * Contiene titolo, autori, anno, ISBN, copie totali e disponibili del Libro.
 * @invariant
 * L'anno deve essere compreso tra 0 e l'anno corrente (inclusi).
 * @invariant
 * L'ISBN deve essere di 13 cifre e iniziare per 978 o 979.
 * @invariant
 * I numeri di copie devono essere sempre maggiori o uguali a 0 e
 * il numero di copie disponibili deve essere minore o uguale al numero di copie totali.
 */
public class Libro {

    private final StringProperty titolo = new SimpleStringProperty();

    private final StringProperty autori = new SimpleStringProperty();

    private final IntegerProperty anno = new SimpleIntegerProperty();

    private final StringProperty isbn = new SimpleStringProperty();

    private final IntegerProperty copieTotali = new SimpleIntegerProperty();

    private final IntegerProperty copieDisponibili = new SimpleIntegerProperty();

    /**
     * @brief Costruttore.
     * @param[in] titolo Titolo del Libro.
     * @param[in] autori Autori del Libro.
     * @param[in] anno Anno di rilascio del Libro.
     * @param[in] isbn Codice ISBN del Libro
     * @param[in] copieTotali Numero di copie del Libro nella Biblioteca.
     * @post
     * Verrà creato un Libro con i parametri specificati in input inseriti nei
     * corrispondenti attributi property e con il numero di copie disponibili
     * inizialmente uguale a quello di copie totali.
     */
    public Libro(String titolo, String autori, int anno, String isbn, int copieTotali) {
        this.titolo.set(titolo);
        this.autori.set(autori);
        this.anno.set(anno);
        this.isbn.set(isbn);
        this.copieTotali.set(copieTotali);
        this.copieDisponibili.set(copieTotali);
    }
    
    /**
     * @brief Costruttore.
     * @param[in] titolo Titolo del Libro.
     * @param[in] autori Autori del Libro.
     * @param[in] anno Anno di rilascio del Libro.
     * @param[in] isbn Codice ISBN del Libro
     * @param[in] copieTotali Numero di copie del Libro nella Biblioteca.
     * @param[in] copieDisponibili Numero di copie non in prestito del Libro.
     * @post
     * Verrà creato un Libro con i parametri specificati in input inseriti nei
     * corrispondenti attributi property e con il numero di copie disponibili
     * inizializzato a quello inserito come parametro in ingresso.
     */
    public Libro(String titolo, String autori, int anno, String isbn, int copieTotali, int copieDisponibili) {
        this.titolo.set(titolo);
        this.autori.set(autori);
        this.anno.set(anno);
        this.isbn.set(isbn);
        this.copieTotali.set(copieTotali);
        this.copieDisponibili.set(copieDisponibili);
    }

    /**
     * @brief Getter standard per il titolo.
     * @return Il valore della property titolo del Libro.
     */
    public String getTitolo() {
        return titolo.get();
    }

    /**
     * @brief Setter standard per il campo titolo.
     * @post Il valore della property titolo è aggiornato con il valore del parametro in ingresso.
     * @param[in] titolo Il valore da settare per il campo titolo.
     */
    public void setTitolo(String titolo) {
        this.titolo.set(titolo);
    }

    /**
     * @brief Getter standard per gli autori.
     * @return Il valore della property autori del Libro.
     */
    public String getAutori() {
       return autori.get();
    }

    /**
     * @brief Setter standard per il campo autori.
     * @post Il valore della property autori è aggiornato con il valore del parametro in ingresso.
     * @param[in] autori Il valore da settare per il campo autori.
     */
    public void setAutori(String autori) {
        this.autori.set(autori);
    }

    /**
     * @brief Getter standard per l'anno.
     * @return Il valore della property anno del Libro.
     */
    public int getAnno() {
        return anno.get();
    }

    /**
     * @brief Setter standard per il campo anno.
     * @post Il valore della property anno è aggiornato con il valore del parametro in ingresso.
     * @param[in] anno Il valore da settare per il campo anno.
     */
    public void setAnno(int anno) {
        this.anno.set(anno);
    }

    /**
     * @brief Getter standard per l'isbn.
     * @return Il valore della property isbn del Libro.
     */
    public String getIsbn() {
        return isbn.get();
    }

    /**
     * @brief Setter standard per il campo isbn.
     * @post Il valore della property isbn è aggiornato con il valore del parametro in ingresso.
     * @param[in] isbn Il valore da settare per il campo isbn.
     */
    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    /**
     * @brief Getter standard per il numero di copie totali.
     * @return Il valore della property copieTotali del Libro.
     */
    public int getCopieTotali() {
        return copieTotali.get();
    }

    /**
     * @brief Setter standard per il campo copieTotali.
     * @post Il valore della property copieTotali è aggiornato con il valore del parametro in ingresso.
     * @param[in] copieTotali Il valore da settare per il campo copieTotali.
     */
    public void setCopieTotali(int copieTotali) {
        this.copieTotali.set(copieTotali);
    }

    /**
     * @brief Getter standard per il numero di copie disponibili.
     * @return Il valore della property copieDisponibili del Libro.
     */
    public int getCopieDisponibili() {
        return copieDisponibili.get();
    }

    /**
     * @brief Setter standard per il campo copieDisponibili.
     * @post Il valore della property copieDisponibili è aggiornato con il valore del parametro in ingresso.
     * @param[in] copieDisponibili Il valore da settare per il campo copieDisponibili.
     */
    public void setCopieDisponibili(int copieDisponibili) {
        this.copieDisponibili.set(copieDisponibili);
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property titolo.
     * @return La property titolo del Libro.
     */
    public StringProperty titoloProperty() {
        return titolo;
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property autori.
     * @return La property autori del Libro.
     */
    public StringProperty autoriProperty() {
        return autori;
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property anno.
     * @return La property anno del Libro.
     */
    public IntegerProperty annoProperty() {
        return anno;
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property isbn.
     * @return La property isbn del Libro.
     */
    public StringProperty isbnProperty() {
        return isbn;
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property copieTotali.
     * @return La property copieTotali del Libro.
     */
    public IntegerProperty copieTotaliProperty() {
        return copieTotali;
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property copieDisponibili.
     * @return La property copieDisponibili del Libro.
     */
    public IntegerProperty copieDisponibiliProperty() {
        return copieDisponibili;
    }

    /**
     * @brief Il metodo equals() di Object overraidato per la classe Libro.
     *        Il confronto è fatto sull'ISBN.
     * @return True se l'oggetto passato come parametro è uguale a questo oggetto Libro, False altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(!(this.getClass() == obj.getClass())) return false;
        Libro other=(Libro) obj;
        return this.isbn.get().equalsIgnoreCase(other.isbn.get());  
    }

    /**
     * @brief Override del metodo toString() di Object.
     * @return Una rappresentazione in String dell'oggetto Libro. 
     */
    @Override
    public String toString() {
        return ("Titolo: " + getTitolo() + ", Autori: " + getAutori() + ", Anno: " + getAnno() +
                ", ISBN: " + getIsbn() + ", CopieTotali: " + getCopieTotali() + ", CopieDisponibili: " + getCopieDisponibili());
    }

    /**
     * @brief Una rappresentazione in Stringa dell'oggetto Libro, comoda per la visualizzazione nella sez Prestiti.
     * @return Ulteriore rappresentazione in String dell'oggetto Libro.
     */
    public String toStringPrestito() {
        return ("Titolo: " + getTitolo() + ", Autori: " + getAutori());
    }
}
