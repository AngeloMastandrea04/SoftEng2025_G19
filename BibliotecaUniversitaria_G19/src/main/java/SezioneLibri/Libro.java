package SezioneLibri;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Libro {

    private final StringProperty titolo;

    private final StringProperty autore;

    private final IntegerProperty anno;

    private final StringProperty isbn;

    private final IntegerProperty copieTotali;

    private final IntegerProperty copieDisponibili;

    public Libro(String nome, String autori, int anno, String isbn, int copieTotali, int copieDisponibili) {
    }

    public String getTitolo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTitolo(String titolo) {
    }

    public String getAutori() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAutori(String autori) {
    }

    public int getAnno() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAnno(int anno) {
    }

    public String getIsbn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setIsbn(String isbn) {
    }

    public int getCopieTotali() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCopieTotali(int copieTotali) {
    }

    public int getCopieDisponibili() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCopieDisponibili(int copieDisponibili) {
    }

    public StringProperty titoloProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public StringProperty autoriProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IntegerProperty annoProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public StringProperty isbnProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IntegerProperty copieTotaliProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IntegerProperty copieDisponibiliProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String toStringPrestito() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
