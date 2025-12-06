package SezionePrestiti;

import SezioneUtenti.Utente;
import SezioneLibri.Libro;
import java.awt.Label;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class AggiungiPrestitoDialog {

    private ComboBox<Utente> utenteBox;

    private Label utenteError;

    private ComboBox<Libro> libroBox;

    private Label libroError;

    private DatePicker dataRestituzionePicker;

    private Label dataRestituzioneError;

    public AggiungiPrestitoDialog(ObservableList<Utente> utenti, ObservableList<Libro> libri) {
    }

    private void aggiornaOk(Node ok) {
    }
}
