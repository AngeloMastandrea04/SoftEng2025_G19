package Biblioteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @brief JavaFX App, Classe principale dell'applicazione Biblioteca.
 * 
 * Questa classe si occupa di:
 * - Avviare l'applicazione.
 * - Inizializzare lo Stage principale.
 * - Gestire la navigazione cambiando la root della scena tramite il caricamento di file FXML.
 */
public class App extends Application {

    private static Scene scene;
    
    /**
     * @brief Metodo di avvio dell'interfaccia.
     * Viene invocato automaticamente al runtime e permette di inizializzare lo Stage primario,
     * mostrando come prima cosa la DashboardGenerale.
     * @param[in] stage La finestra principale caricata all'avvio dell'applicazione.
     * @throws IOException Se il file FXML della dashboard non viene trovato o non può essere caricato.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("DashboardGeneraleView", new DashboardGeneraleController()), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @brief Cambia la schermata visualizzata.
     * Sostituisce il nodo radice della scena attuale con quello caricato
     * dal file FXML specificato e dal rispettivo controller. Questo metodo permette la navigazione tra le schermate.
     * @param[in] fxml Il nome del file FXML da caricare.
     * @param[in] controller L'istanza dell'oggetto controller da associare alla nuova Sezione.
     * @throws IOException Se il file FXML non può essere caricato.
     */
    public static void setRoot(String fxml, Object controller) throws IOException {
        scene.setRoot(loadFXML(fxml, controller));
    }

    /**
     * @brief Metodo per il caricamento delle risorse FXML.
     * Carica il file .fxml specificato e imposta il controller passato come parametro.
     * @param[in] fxml Il nome del file FXML da caricare.
     * @param[in] controller L'istanza dell'oggetto controller da associare alla nuova Sezione.
     * @return Ritorna la schermata da caricare rispetto la gerarchia.
     * @throws IOException Se si verificano errori di I/O durante il caricamento.
     */
    private static Parent loadFXML(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }
    
    /**
     * @brief Punto di ingresso dell'applicazione Java.
     * Invoca il metodo `launch()` per avviare l'applicazione.
     * @param[in] args Array di stringhe contenente gli argomenti passati da riga di comando.
    */
    public static void main(String[] args) {
        launch();
    }

}