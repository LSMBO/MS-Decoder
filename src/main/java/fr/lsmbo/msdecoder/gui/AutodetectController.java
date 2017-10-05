/*
 * Copyright 2017 CNRS
 * Author: Alexandre BUREL
 * Email: alexandre.burel@unistra.Fr
 * 
 * This software is a computer program whose purpose is to extract polymer 
 * codes encoded into MS/MS spectra. The goal of this software is to 
 * automate the decoding for several spectra, in a short amount of time 
 * using a user-friendly user-interface.
 * 
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 * 
 */

package fr.lsmbo.msdecoder.gui;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import fr.lsmbo.msdecoder.decoder.PolymerTypes;
import fr.lsmbo.msdecoder.settings.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class AutodetectController {

	private Stage dialogStage;
	@FXML
	private Button btnConfirm, btnCancel;
	@FXML
	private Hyperlink lnkResetToDefault;
	@FXML
	private TableView<AutodetectValue> tblParsingRules;
	@FXML
	private TableColumn<AutodetectValue, String> tbcPolymerTypes, tbcParsingRules;
	@FXML
	private Label lblInfo, lblInstructions;
	
	private Alert resetPopup = new Alert(AlertType.CONFIRMATION);
	private ButtonType btnYes = new ButtonType("Yes", ButtonData.YES);
	private ButtonType btnNo = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	
	private final ObservableList<AutodetectValue> data = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		
		tblParsingRules.setItems(data);
        tbcPolymerTypes.setCellValueFactory(new PropertyValueFactory<AutodetectValue, String>("PolymerType"));
        tbcParsingRules.setCellValueFactory(new PropertyValueFactory<AutodetectValue, String>("parsingRule"));
        tbcParsingRules.setCellFactory(TextFieldTableCell.<AutodetectValue>forTableColumn());
        tbcParsingRules.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AutodetectValue,String>>() {
        	@Override
	        public void handle(CellEditEvent<AutodetectValue, String> t) {
        		AutodetectValue adv = (AutodetectValue) t.getTableView().getItems().get(t.getTablePosition().getRow());
        		adv.setParsingRule(t.getNewValue());
	        }
		});
		setData(Settings.getAutodetectRules().getValue());
		
		tbcPolymerTypes.prefWidthProperty().bind(tblParsingRules.widthProperty().divide(2).subtract(1));
		tbcParsingRules.prefWidthProperty().bind(tblParsingRules.widthProperty().divide(2).subtract(1));

		lblInfo.prefWidthProperty().bind(((VBox)lblInfo.getParent()).widthProperty());
		lblInfo.setText(Settings.getAutodetectRules().getDescription());
		lblInstructions.prefWidthProperty().bind(lblInfo.widthProperty());
		lblInstructions.setAlignment(Pos.CENTER);
		
		resetPopup.setTitle("Reset Autodetect settings");
		resetPopup.setHeaderText("Do you want to reset all values ?");
		resetPopup.getButtonTypes().setAll(btnYes, btnNo);
	}
	
	private void setData(Map<PolymerTypes, String> map) {
		data.clear();
		for(PolymerTypes pt: map.keySet()) {
			data.add(new AutodetectValue(pt, map.get(pt)));
		}
		data.sort(new Comparator<AutodetectValue>() {
		    @Override
		    public int compare(AutodetectValue o1, AutodetectValue o2) {
		        return o1.getPolymerType().compareTo(o2.getPolymerType());
		    }
		});
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	@FXML
	private void btnConfirmListener() {
		for(AutodetectValue adv: data) {
			Settings.getAutodetectRules().setValue(adv.getPolymerType(), adv.getParsingRule());
		}
		dialogStage.close();
	}
	
	@FXML
	private void btnCancelListener() {
		dialogStage.close();
	}
	
	@FXML
	private void lnkResetToDefaultListener() {
		Optional<ButtonType> result = resetPopup.showAndWait();
		if (result.get() == btnYes) {
			setData(Settings.getAutodetectRules().getDefaultValue());
		}
	}
}
