/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {

    	Integer anno = this.boxAnno.getValue();
    	if(anno == null) {
    		this.txtResult.appendText("Selezionare un anno!\n");
    		return;
    	}
    	this.model.creaGrafo(anno);
    	this.txtResult.appendText(String.format("Grafo creato! %d vertici - %d archi\n", model.getVertexNumber(), model.getEdgesNumber()));
    	this.txtResult.appendText(model.output());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	Integer giorno = this.boxGiorno.getValue();
    	Integer mese = this.boxMese.getValue();
    	Integer anno = this.boxAnno.getValue();
    	
    	if(anno%4!=0 && giorno==29 && mese==2) {
    		this.txtResult.appendText("Errore inserimento data! Riprovare");
    		return;
    	}
    	
    	try {
    		LocalDate data = LocalDate.of(anno, mese, giorno);
    	}catch(DateTimeException e) {
    		this.txtResult.appendText("Errore inserimento data! Riprovare");
    		return;
    	}
    	Integer N;
    	try{
    		N = Integer.parseInt(this.txtN.getText());
    	}catch(NumberFormatException n) {
    		this.txtResult.appendText("Inserire un numero da 1 a 10");
    		return;
    	}
    	if(N<1 || N>10) {
    		this.txtResult.appendText("Inserire un numero da 1 a 10");
    	}
    	String result = this.model.getSimulazione(giorno, mese, anno, N);
    	this.txtResult.appendText(result);

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxAnno.getItems().addAll(this.model.getAnni());
    	for(int i=1; i<=12; i++) {
    		this.boxMese.getItems().add(i);
    	}
    	for(int i=1; i<=31; i++) {
    		this.boxGiorno.getItems().add(i);
    	}
    }
}
