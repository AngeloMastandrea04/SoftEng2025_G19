package SezionePrestiti;

import java.time.LocalDate;
import javafx.beans.property.StringProperty;

public class Prestito {

    private final StringProperty utente;

    private final StringProperty libro;

    private final LocalDate dataRestituzione;

    public Prestito(String utente, String libro, LocalDate dataRestituzione) {
    }

    public String getUtente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getLibro() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LocalDate getDataRestituzione() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public StringProperty utenteProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public StringProperty libroProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String toStringUtente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
