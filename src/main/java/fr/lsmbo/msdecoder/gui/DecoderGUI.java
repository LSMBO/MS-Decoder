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

import java.io.IOException;

import fr.lsmbo.msdecoder.AppInfo;
import fr.lsmbo.msdecoder.settings.Settings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DecoderGUI extends Application {

	public static void run() {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// search for settings
		Settings.initialize();
		
		// window title
		primaryStage.setTitle(AppInfo.getAppName() + " " + AppInfo.getAppVersion());
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.DECODER);
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
	        DecoderController controller = loader.getController();
			controller.setDialogStage(primaryStage);
			
			// display frame
			primaryStage.show();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
