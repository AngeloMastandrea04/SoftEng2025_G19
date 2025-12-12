package G19.Biblioteca;

import G19.SezioneLibri.Libro;
import G19.SezionePrestiti.Prestito;
import G19.SezioneUtenti.Utente;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Callback;

/**
 * @brief La struttura Biblioteca che contiene tutti i dati dell'archivio tra Utenti, Libri e Prestiti.
 * 
 * La Biblioteca è implementata attraverso il design pattern del Singleton.
 * Contiene le tre liste di Utenti, Libri e Prestiti che il Bibliotecario
 * è interessato a visualizzare durante l'utilizzo del Sistema.
 * 
 * @invariant
 * È possibile creare una sola istanza di questa classe seguendo il design
 * pattern del Singleton.
 */
public class Biblioteca {

    /**
     * @brief Contiene la lista di tutti gli Utenti registrati nella Biblioteca.
     */
    private final ObservableList<Utente> listaUtenti;

    /**
     * @brief Contiene la lista di tutti i Libri registrati nella Biblioteca.
     */
    private final ObservableList<Libro> listaLibri;

    /**
     * @brief Contiene la lista di tutti i Prestiti attivi registrati nella Biblioteca.
     */
    private final ObservableList<Prestito> listaPrestiti;

    /**
     * @brief Inizialmente uguale a null, contiene l'unica istanza per tutto il programma del Singleton Biblioteca.
     */
    private static Biblioteca instance = null;

    /**
     * @brief Costruttore privato (seguendo il design pattern del Singleton).
     * @post
     * Crea una biblioteca vuota, viene utilizzato solo una volta durante
     * l'esecuzione del programma e solo se precedentemente non è stata 
     * trovata una Biblioteca salvata su file.
     */
    private Biblioteca() {
        
        /*  Con la lambda expression overraido il metodo call dell'interfaccia funzionale
            Callback. 
            Quando verrà aggiunto un nuovo Utente/Libro alla lista, verrà creato un array
            osservabile di tutte le property del nuovo oggetto in modo tale che anche quando
            verrà modificato un campo dell'Utente/Libro presente in lista, tutti i Listener
            verranno avvisati.
        */
        Callback<Utente, Observable[]> extractorUtente = (Utente u) -> new Observable[] {
            u.nomeProperty(),
            u.cognomeProperty(),
            u.matricolaProperty(),
            u.emailProperty(),
            u.getPrestitiAttivi()
        };
        this.listaUtenti = FXCollections.observableArrayList(extractorUtente);
        
        Callback<Libro, Observable[]> extractorLibro = (Libro l) -> new Observable[] {
            l.titoloProperty(),
            l.autoriProperty(),
            l.annoProperty(),
            l.isbnProperty(),
            l.copieTotaliProperty(),
            l.copieDisponibiliProperty()
        };
        this.listaLibri = FXCollections.observableArrayList(extractorLibro);
        
        /*  I Prestiti non sono modificabili, non è necessario usare un extractor.
        */
        this.listaPrestiti = FXCollections.observableArrayList();
        
        /*  Creazione del Listener per osservare le modifiche alla lista.
            Per ogni cambiamento subito dalla lista, la Biblioteca verrà salvata su file.
            Senza extractor verrebbero avvisati solo in caso di aggiunta/rimozione, non
            di modifica di un campo di un elemento.
        */
        ListChangeListener<Utente> listenerSalvataggioUtenti = (change) -> {
            while(change.next()) {
                this.salvaSuFile("ArchivioBiblioteca.json"); 
            }
        };
        this.listaUtenti.addListener(listenerSalvataggioUtenti);
        
        ListChangeListener<Libro> listenerSalvataggioLibri = (change) -> {
            while(change.next()) {
                this.salvaSuFile("ArchivioBiblioteca.json"); 
            }
        };
        this.listaLibri.addListener(listenerSalvataggioLibri);
        
        ListChangeListener<Prestito> listenerSalvataggioPrestiti = (change) -> {
            while(change.next()) {
                this.salvaSuFile("ArchivioBiblioteca.json"); 
            }
        };
        this.listaPrestiti.addListener(listenerSalvataggioPrestiti);
    }

    /**
     * @brief Metodo per ottenere l'unica istanza del Singleton Biblioteca.
     * 
     * Ritorna l'istanza del Singleton Biblioteca se è già stata creata, altrimenti
     * legge dal file e restituisce la Biblioteca letta. Se nel file non è ancora
     * presente alcuna Biblioteca salvata, invoca il costruttore pricato e ne crea
     * e restituisce una vuota.
     * 
     * @return L'unica istanza del Singleton Biblioteca
     */
    public static Biblioteca getInstance() {
        if(instance == null) {  
            instance = Biblioteca.caricaDaFile("ArchivioBiblioteca.json");
        }
        return instance;
    }

    /**
     * @brief Getter standard per la lista degli Utenti.
     * @return La lista degli Utenti.
     */
    public ObservableList<Utente> getListaUtenti() {
        return listaUtenti;
    }

    /**
     * @brief Getter standard per la lista dei Libri.
     * @return La lista dei Libri.
     */
    public ObservableList<Libro> getListaLibri() {
        return listaLibri;
    }

    /**
     * @brief Getter standard per la lista dei Prestiti.
     * @return La lista dei Prestiti.
     */
    public ObservableList<Prestito> getListaPrestiti() {
        return listaPrestiti;
    }

    /**
     * @brief Carica da file l'intero archivio della Biblioteca 
     *        contenuto nel Singleton oggetto Biblioteca salvato sullo stesso file.
     * @param[in] filename Percorso del file da cui caricare l'archivio della Biblioteca.
     * @return Il Singleton instanza della classe Biblioteca salvata precedentemente su file.
     */
    private static Biblioteca caricaDaFile(String filename) {
        Biblioteca bib = new Biblioteca();
        try (Reader reader = new BufferedReader(new FileReader(filename))) {
            Gson gson = new Gson();
            BibliotecaData data = gson.fromJson(reader, BibliotecaData.class);

            if (data != null) {
                // Utenti
                if (data.utenti != null) {
                    for (UtenteData ud : data.utenti) {
                        Utente u = new Utente(ud.nome, ud.cognome, ud.matricola, ud.email);
                        u.getPrestitiAttivi().addAll(ud.prestitiAttivi);
                        bib.listaUtenti.add(u);
                    }
                }
                // Libri
                if (data.libri != null) {
                    for (LibroData ld : data.libri) {
                        Libro l = new Libro(ld.titolo, ld.autori, ld.anno, ld.isbn, ld.copieTotali, ld.copieDisponibili);
                        bib.listaLibri.add(l);
                    }
                }
                // Prestiti
                if (data.prestiti != null) {
                    for (PrestitoData pd : data.prestiti) {
                        Prestito p = new Prestito(pd.utente, pd.libro, LocalDate.parse(pd.dataRestituzione));
                        bib.listaPrestiti.add(p);
                    }
                }
            }
        } catch (FileNotFoundException | EOFException ex) {
            // In caso di File non trovato o  File vuoto, ritorna Biblioteca vuota
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return bib;
    }

    /**
     * @brief Salva su file l'intero archivio della Biblioteca
     *        contenuto nel Singleton oggetto Biblioteca.
     * @param[in] filename Percorso del file su cui caricare l'archivio della Biblioteca.
     */
    public void salvaSuFile(String filename) {
        try (Writer writer = new BufferedWriter(new FileWriter(filename))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BibliotecaData data = new BibliotecaData();

            // Utenti
            data.utenti = new ArrayList<>();
            for (Utente u : listaUtenti) {
                UtenteData ud = new UtenteData();
                ud.nome = u.getNome();
                ud.cognome = u.getCognome();
                ud.matricola = u.getMatricola();
                ud.email = u.getEmail();
                ud.prestitiAttivi = new ArrayList<>(u.getPrestitiAttivi());
                data.utenti.add(ud);
            }

            // Libri
            data.libri = new ArrayList<>();
            for (Libro l : listaLibri) {
                LibroData ld = new LibroData();
                ld.titolo = l.getTitolo();
                ld.autori = l.getAutori();
                ld.anno = l.getAnno();
                ld.isbn = l.getIsbn();
                ld.copieTotali = l.getCopieTotali();
                ld.copieDisponibili = l.getCopieDisponibili();
                data.libri.add(ld);
            }

            // Prestiti
            data.prestiti = new ArrayList<>();
            for (Prestito p : listaPrestiti) {
                PrestitoData pd = new PrestitoData();
                pd.utente = p.getUtente();
                pd.libro = p.getLibro();
                pd.dataRestituzione = p.getDataRestituzione().toString();
                data.prestiti.add(pd);
            }

            gson.toJson(data, writer);
        } catch (IOException ex) {
            System.err.println("Errore durante la scrittura su file: " + filename);
            ex.printStackTrace();
        }
    }
    
    private static class BibliotecaData {
        List<UtenteData> utenti;
        List<LibroData> libri;
        List<PrestitoData> prestiti;
    }

    private static class UtenteData {
        String nome;
        String cognome;
        String matricola;
        String email;
        List<String> prestitiAttivi;
    }

    private static class LibroData {
        String titolo;
        String autori;
        int anno;
        String isbn;
        int copieTotali;
        int copieDisponibili;
    }

    private static class PrestitoData {
        String utente;
        String libro;
        String dataRestituzione;    // la data viene salvata: yyyy-MM-dd
    }
}
