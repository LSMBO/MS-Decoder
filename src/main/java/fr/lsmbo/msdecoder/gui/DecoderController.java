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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.lsmbo.msdecoder.AppInfo;
import fr.lsmbo.msdecoder.decoder.AbstractDecoder;
import fr.lsmbo.msdecoder.decoder.Decoders;
import fr.lsmbo.msdecoder.decoder.PolymerTypes;
import fr.lsmbo.msdecoder.settings.DoubleSetting;
import fr.lsmbo.msdecoder.settings.Settings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DecoderController {
	@FXML
	private TableView<TableData> tblFilesAndResults;
	@FXML
	private TableColumn<TableData, String> tbcFiles, tbcCodes;
	@FXML
	private Button btnBrowse, btnRemoveSelection, btnStart;
	@FXML
	private Hyperlink lnkCopyToClipboard;
	@FXML
	private ComboBox<PolymerTypes> cmbFileTypes;
	@FXML
	private Slider sldMzTolerance, sldIntensityThreshold, sldIntensityThresholdIsotope;
	@FXML
	private Label txtMzTolerance, txtIntensityThreshold, txtIntensityThresholdIsotope, txtStatusLeft, txtStatusRight;
	@FXML
	private MenuItem mnuBrowse, mnuClearList, mnuClose, mnuStart, mnuResetSettings, mnuCopyResultsToClipboard, mnuAbout, mnuAutodetect;
	@FXML
	private ImageView imgHelpMZT, imgHelpIT, imgHelpITI;

	private Stage dialogStage;
	
	private final ObservableList<TableData> data = FXCollections.observableArrayList();
	
	private Alert exitPopup = new Alert(AlertType.CONFIRMATION);
	private ButtonType btnYes = new ButtonType("Yes", ButtonData.YES);
	private ButtonType btnNo = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	
	@FXML
	private void initialize() {
		
		// fill combobox
		cmbFileTypes.setItems(FXCollections.observableArrayList(PolymerTypes.values()));
		cmbFileTypes.getSelectionModel().select(Settings.getLastPolymerType().getValue());
		
		// update sliders values
		setSettings();
		
		// prepare table
        tblFilesAndResults.setItems(data);
        tbcFiles.setCellValueFactory(new PropertyValueFactory<TableData, String>("fileName"));
        tbcCodes.setCellValueFactory(new PropertyValueFactory<TableData, String>("textCode"));
        tblFilesAndResults.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        // set column sizes
        tbcFiles.prefWidthProperty().bind(tblFilesAndResults.widthProperty().multiply(2).divide(3).subtract(10));
        tbcCodes.prefWidthProperty().bind(tblFilesAndResults.widthProperty().multiply(1).divide(3).subtract(5));
        
        // add some style on code column (align right)
        tbcCodes.setStyle("-fx-alignment: CENTER-RIGHT;");        
        
        // define listeners
        sldMzTolerance.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) { reportMzToleranceToLabel(new_val); }
        });
        sldIntensityThreshold.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) { reportIntensityThresholdToLabel(new_val); }
        });
        sldIntensityThresholdIsotope.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) { reportIntensityThresholdIsotopeToLabel(new_val); }
        });

        // make it possible to delete all selected items using Delete key on table
        tblFilesAndResults.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.DELETE)) {
					List<TableData> items = tblFilesAndResults.getSelectionModel().getSelectedItems();
					data.removeAll(items);
					tblFilesAndResults.getSelectionModel().clearSelection();
				}
			}
		});
        
        exitPopup.setTitle("Quit application");
        exitPopup.setHeaderText("Do you want to quit this application ?");
    	exitPopup.getButtonTypes().setAll(btnYes, btnNo);
	}
	
	private void reportMzToleranceToLabel(Number value) {
		txtMzTolerance.setText(Settings.getMzTolerance().getName() + ": " + String.format("%.2f", value) + " Da");
	}
	
	private void reportIntensityThresholdToLabel(Number value) {
		txtIntensityThreshold.setText(Settings.getIntensityThreshold().getName() + ": " + String.format("%.0f", value));
	}

	private void reportIntensityThresholdIsotopeToLabel(Number value) {
		txtIntensityThresholdIsotope.setText(Settings.getIntensityThresholdIsotope().getName() + ": " + String.format("%.0f", value));
	}

	public void setDialogStage(Stage primaryStage) {
		this.dialogStage = primaryStage;
		
    	dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				beforeClosing();
			}
	    });
	}

	@FXML
	private void btnBrowseListener() {
		// prepare file selector
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select files");
		fileChooser.setInitialDirectory(Settings.getLastDirectory().getValue());
		fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files", "*.txt"),
				new FileChooser.ExtensionFilter("All types", "*.*")
            );
		// call file selector and load selected files into table
		List<File> files = fileChooser.showOpenMultipleDialog(this.dialogStage);
		if(files != null) { // null if user canceled the filechooser
			// keep in memory the current folder
			Settings.getLastDirectory().setValue(files.get(0).getParentFile());
			// make sure there can't be redundancies
			for(File f: files) {
				TableData td = new TableData(f);
				// make sure there can't be redundancies
				if(!fileIsAlreadyLoaded(f)) {
					data.add(td);
				}
			}
			txtStatusLeft.setText(data.size()+" file(s) selected");
		}
	}

	@FXML
	private void btnCloseListener() {
		Optional<ButtonType> result = exitPopup.showAndWait();
		if (result.get() == btnYes) {
			beforeClosing();
			dialogStage.close();
		}
	}
	
	private void beforeClosing() {
		Settings.getMzTolerance().setValue(sldMzTolerance.getValue());
		Settings.getIntensityThreshold().setValue(sldIntensityThreshold.getValue());
		Settings.getIntensityThresholdIsotope().setValue(sldIntensityThresholdIsotope.getValue());
		Settings.getLastPolymerType().setValue(cmbFileTypes.getValue());
		Settings.saveSettings();
	}

	@FXML
	private void btnResetSettingsListener() {
		Settings.resetToDefault();
		setSettings();
	}

	private void setSettings() {
		sldMzTolerance.setValue(Settings.getMzTolerance().getValue());
		reportMzToleranceToLabel(sldMzTolerance.getValue());
		sldIntensityThreshold.setValue(Settings.getIntensityThreshold().getValue());
		reportIntensityThresholdToLabel(sldIntensityThreshold.getValue());
		sldIntensityThresholdIsotope.setValue(Settings.getIntensityThresholdIsotope().getValue());
		reportIntensityThresholdIsotopeToLabel(sldIntensityThresholdIsotope.getValue());
	}
	
	private boolean fileIsAlreadyLoaded(File f) {
		boolean result = false;
		for(int i = 0; i < data.size(); i++) {
			if(data.get(i).getFile().getAbsolutePath().equals(f.getAbsolutePath())) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	@FXML
	private void btnRemoveAllItemsListener() {
		data.clear();
		tblFilesAndResults.getSelectionModel().clearSelection();
		txtStatusLeft.setText("");
	}
	
	@FXML
	private void btnSelectAllItemsListener() {
		tblFilesAndResults.getSelectionModel().selectAll();
		tblFilesAndResults.requestFocus();
	}
	
	@FXML
	private void btnStartListener() {
		try {
			Settings.getMzTolerance().setValue(sldMzTolerance.getValue());
			Settings.getIntensityThreshold().setValue(sldIntensityThreshold.getValue());
			Settings.getIntensityThresholdIsotope().setValue(sldIntensityThresholdIsotope.getValue());
			Float totalTimeMs = 0f;
			int nbErrors = 0;
			// determine all polymer types first
			PolymerTypes selectedPolymertype = cmbFileTypes.getSelectionModel().getSelectedItem();
			Map<TableData, PolymerTypes> polymerTypes = new HashMap<TableData, PolymerTypes>();
			for(TableData td: data) {
				if(selectedPolymertype.equals(PolymerTypes.Autodetect)) {
					PolymerTypes pt = Decoders.getPolymerTypeFromFileName(td.getFileName());
					if(pt != null) polymerTypes.put(td, pt);
				} else polymerTypes.put(td, selectedPolymertype);
			}
//			if(cmbFileTypes.getSelectionModel().getSelectedItem() == PolymerTypes.Autodetect && polymerTypes.size() != data.size()) {
			if(selectedPolymertype == PolymerTypes.Autodetect && polymerTypes.size() != data.size()) {
				// find the unrecognized files
				String unrecognizedFiles = "The following file(s) will not be treated:";
				for(TableData td: data) {
					if(!polymerTypes.containsKey(td)) unrecognizedFiles += "\n- "+td.getFileName();
				}
				// display some warning pop up
		        Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Autodetect mode");
		        alert.setHeaderText("Some files are not recognized by the Autodetect algorithm :");
		        alert.setContentText(unrecognizedFiles);
		        alert.showAndWait();
			}
			for(TableData td: data) {
				try {
					if(polymerTypes.containsKey(td)) {
						PolymerTypes selectedType = polymerTypes.get(td);
						AbstractDecoder r = Decoders.get(selectedType);
						td.setCode(r.decode(
								td.getFile().getAbsolutePath(), 
								Settings.getMzTolerance().getValue(), 
								Settings.getIntensityThreshold().getValue(), 
								Settings.getIntensityThresholdIsotope().getValue(),
								false));
						td.setPolymerType(selectedType);
						totalTimeMs += td.getCode().getComputationTimeMilli();
					}
				} catch(Exception e) {
					td.setError(e);
					nbErrors++;
					e.printStackTrace();
				}
			}
			if(nbErrors == 0) {
				txtStatusRight.setText("Decoding ended in "+totalTimeMs+"ms");
			} else {
				txtStatusRight.setText("Decoding ended with "+nbErrors+" errors !!");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		tblFilesAndResults.refresh();
	}
	
	@FXML
	private void lnkCopyToClipboardListener() {
		
		if(data.size() == 0) {
			
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle(lnkCopyToClipboard.getText());
	        alert.setHeaderText("Data table is empty...");
	        alert.setContentText("");
	        alert.showAndWait();
			
		} else {
			StringBuilder clipboardString = new StringBuilder();
	
			clipboardString.append("Directory\tFile name\tCode\tPolymer type\tFragments\tTime (ms)\n");
	        for(TableData d: data) {
	        	clipboardString.append(d.getFile().getParent() + "\t" + d.getFileName() + "\t" + d.getTextCode(false) + "\t" + d.getEffectivePolymerType().getValue() + "\t" + d.getCode().toString() + "\t" + d.getCode().getComputationTimeMilli() + "\n");
	        }
	        
	        final ClipboardContent content = new ClipboardContent();
	        content.putString(clipboardString.toString());
	        Clipboard.getSystemClipboard().setContent(content);
	
	        // popup message
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle(lnkCopyToClipboard.getText());
	        alert.setHeaderText("Data table have been copied");
	        alert.setContentText("");
	        alert.showAndWait();
		}

	}
	
	@FXML
	private void btnAboutListener() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.ABOUT);
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("About "+AppInfo.getAppName());
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.dialogStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			AboutController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void btnAutodetectListener() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.AUTODETECTRULES);
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Autodetect file tags");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.dialogStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			AutodetectController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void displayDescription(DoubleSetting setting) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(setting.getName());
        alert.setHeaderText(setting.getDescription());
        alert.setContentText("Default value: " + setting.getDefaultValue() + " " + setting.getUnit());
        alert.getDialogPane().setMaxWidth(500);
        alert.showAndWait();
	}
	
	@FXML
	private void imgHelpMztListener() {
		displayDescription(Settings.getMzTolerance());
	}
	
	@FXML
	private void imgHelpItListener() {
		displayDescription(Settings.getIntensityThreshold());
	}
	
	@FXML
	private void imgHelpItiListener() {
		displayDescription(Settings.getIntensityThresholdIsotope());
	}
}
