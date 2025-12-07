package SezioneLibri;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
/**
 * @brief Un libro inserito o che deve essere inserito nella Biblioteca.
 * Contiene titolo, autori, anno, ISBN, copie totali e disponibili del Libro.
 * @invariant
 * L'anno deve essere valido.
 * @invariant
 * L'ISBN deve essere di 13 cifre e iniziare per 978 o 979.
 */
public class Libro {

    private final StringProperty titolo;

    private final StringProperty autori;

    private final IntegerProperty anno;

    private final StringProperty isbn;

    private final IntegerProperty copieTotali;

    private final IntegerProperty copieDisponibili;

    /**
     * @brief Costruttore.
     * @param[in] titolo Titolo del Libro.
     * @param[in] autori Autori del Libro.
     * @param[in] anno Anno di rilascio del Libro.
     * @param[in] isbn Codice ISBN del Libro
     * @param[in] copieTotali Numero di copie del Libro nella Biblioteca.
     * @pre 
     * Il formato dell'ISBN è di 13 cifre e inizia per 978 o 979
     * @pre 
     * l'anno non deve essere ambiguo
     * @post
     * Verrà creato un Libro con i parametri specificati in input inseriti nei
     * corrispondenti attributi property e con il numero di copie disponibili
     * inizialmente uguale a quello di copie totali.
     */
    public Libro(String titolo, String autori, int anno, String isbn, int copieTotali) {
    }

    /**
     * @brief Getter standard per il titolo.
     * @return Il valore della property titolo del Libro.
     */
    public String getTitolo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Setter standard per il campo titolo.
     * @post Il valore della property titolo è aggiornato con il valore del parametro in ingresso.
     * @param[in] titolo Il valore da settare per il campo titolo.
     */
    public void setTitolo(String titolo) {
    }

    /**
     * @brief Getter standard per gli autori.
     * @return Il valore della property autori del Libro.
     */
    public String getAutori() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Setter standard per il campo autori.
     * @post Il valore della property autori è aggiornato con il valore del parametro in ingresso.
     * @param[in] autori Il valore da settare per il campo autori.
     */
    public void setAutori(String autori) {
    }

    /**
     * @brief Getter standard per l'anno.
     * @return Il valore della property anno del Libro.
     */
    public int getAnno() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Setter standard per il campo anno.
     * @post Il valore della property anno è aggiornato con il valore del parametro in ingresso.
     * @param[in] anno Il valore da settare per il campo anno.
     */
    public void setAnno(int anno) {
    }

    /**
     * @brief Getter standard per l'ISBN.
     * @return Il valore della property isbn del Libro.
     */
    public String getIsbn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Setter standard per il campo isbn.
     * @post Il valore della property isbn è aggiornato con il valore del parametro in ingresso.
     * @param[in] isbn Il valore da settare per il campo isbn.
     */
    public void setIsbn(String isbn) {
    }

    /**
     * @brief Getter standard per il numero di copie totali.
     * @return Il valore della property copieTotali del Libro.
     */
    public int getCopieTotali() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Setter standard per il campo copieTotali.
     * @post Il valore della property copieTotali è aggiornato con il valore del parametro in ingresso.
     * @param[in] copieTotali Il valore da settare per il campo copieTotali.
     */
    public void setCopieTotali(int copieTotali) {
    }

    /**
     * @brief Getter standard per il numero di copie disponibili.
     * @return Il valore della property copieDisponibili del Libro.
     */
    public int getCopieDisponibili() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Setter standard per il campo copieDisponibili.
     * @post Il valore della property copieDisponibili è aggiornato con il valore del parametro in ingresso.
     * @param[in] copieDisponibili Il valore da settare per il campo copieDisponibili.
     */
    public void setCopieDisponibili(int copieDisponibili) {
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property titolo.
     * @return La property titolo del Libro.
     */
    public StringProperty titoloProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property autori.
     * @return La property autori del Libro.
     */
    public StringProperty autoriProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property anno.
     * @return La property anno del Libro.
     */
    public IntegerProperty annoProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property isbn.
     * @return La property isbn del Libro.
     */
    public StringProperty isbnProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property copieTotali.
     * @return La property copieTotali del Libro.
     */
    public IntegerProperty copieTotaliProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Metodo necessario per Binding tra la TableView e la property copieDisponibili.
     * @return La property copieDisponibili del Libro.
     */
    public IntegerProperty copieDisponibiliProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Il metodo equals() di Object overraidato per la classe Libro.
     *        Il confronto è fatto sull'ISBN.
     * @return True se l'oggetto passato come parametro è uguale a questo oggetto Libro, False altrimenti.
     */
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Override del metodo toString() di Object.
     * @return Una rappresentazione in String dell'oggetto Libro. 
     */
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Ulteriore rappresentazione in Stringa dell'oggetto Libro, comoda per la visualizzazione nella sez Prestiti.
     * @return Una rappresentazione in String dell'oggetto Libro comoda per la visualizzazione nella sez Prestiti.
     */
    public String toStringPrestito() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
