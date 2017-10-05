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

package fr.lsmbo.msdecoder.settings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fr.lsmbo.msdecoder.decoder.PolymerTypes;

public class Settings {

	private static File file = new File(System.getenv("TEMP")+"/.disp-gui.txt");
	
	public static Map<SettingsEnum, Setting> map = new HashMap<SettingsEnum, Setting>();
	
	private static void setDefaultValues() {
		map.put(SettingsEnum.MZT, new DoubleSetting(SettingsEnum.MZT, "m/z tolerance", "mzTolerance", "The m/z tolerance window to include matching ions", new Double(0.05), "Da"));
		map.put(SettingsEnum.IT, new DoubleSetting(SettingsEnum.IT, "Intensity threshold", "intensityThreshold", "An absolute intensity threshold that defines the lowest measured absolute intensity value for a peak to be considered as a real non-background peak", new Double(4.0)));
		map.put(SettingsEnum.ITI, new DoubleSetting(SettingsEnum.ITI, "First isotope searched threshold", "intensityThresholdForIsotopeSearch", "A second absolute intensity threshold on the matching peak above which a peak corresponding to the first isotope is searched", new Double(25.0)));
		map.put(SettingsEnum.DIR, new FileSetting(SettingsEnum.DIR, "Last directory selected", "lastDirectory", "The last directory selected", new File(System.getProperty("user.home"))));
		map.put(SettingsEnum.PT, new PolymerTypeSetting(SettingsEnum.PT, "Last polymer type", "lastPolymerSelected", "The last polymer type selected", PolymerTypes.Autodetect));
		Map<PolymerTypes, String> autodetectRules = new HashMap<PolymerTypes, String>();
		autodetectRules.put(PolymerTypes.R1C3, "_R1-C3_");
		autodetectRules.put(PolymerTypes.R1C4, "_R1-C4_");
		autodetectRules.put(PolymerTypes.R2, "_R2_");
		autodetectRules.put(PolymerTypes.R3C3, "_R3-C3_");
		autodetectRules.put(PolymerTypes.R3C4, "_R3-C4_");
		map.put(SettingsEnum.AD, new AutodetectSetting(SettingsEnum.AD, "Autodetect settings", "autodetectRules", "When using \"Autodetect mode\", files containing the tag on the right column will be treated with the corresponding algorithm.", autodetectRules));
	}
	
	public static void resetToDefault() {
		map.get(SettingsEnum.MZT).resetValue();
		map.get(SettingsEnum.IT).resetValue();
		map.get(SettingsEnum.ITI).resetValue();
	}
	
	public static void initialize() {
		setDefaultValues();
		// create the file with default values if it does not exist
		if(!file.exists()) {
			for(Setting setting: map.values()) setting.resetValue();
			saveSettings();
		}
		// read the file if it exists and set the parameters
		try {
			Files.lines(file.toPath()).forEach(line -> parseLine(line));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveSettings() {
		// preparing what to write
		ArrayList<String> lines = new ArrayList<String>();
		for(Setting setting: map.values()) {
			lines.addAll(setting.formatValueForStoring());
		}
		Collections.sort(lines);
		// write file
		try {
			Files.write(file.toPath(), lines);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void parseLine(String line) {
		String[] kv = line.split("=", 2);
		if(kv.length == 2) {
			String key = kv[0], value = kv[1];
			// key should be one of the Setting.shortName
			for(SettingsEnum se: map.keySet()) {
				if(key.equals(map.get(se).getShortName())) {
					map.get(se).setValue(value);
					break;
				} else if(se.equals(SettingsEnum.AD) && key.startsWith(map.get(se).getShortName())) {
					((AutodetectSetting)map.get(se)).setValue(key, value);
				}
			}
		}
	}
	
	public static DoubleSetting getMzTolerance() {
		return (DoubleSetting)map.get(SettingsEnum.MZT);
	}
	
	public static DoubleSetting getIntensityThreshold() {
		return (DoubleSetting)map.get(SettingsEnum.IT);
	}
	
	public static DoubleSetting getIntensityThresholdIsotope() {
		return (DoubleSetting)map.get(SettingsEnum.ITI);
	}
	
	public static FileSetting getLastDirectory() {
		return (FileSetting)map.get(SettingsEnum.DIR);
	}
	
	public static PolymerTypeSetting getLastPolymerType() {
		return (PolymerTypeSetting)map.get(SettingsEnum.PT);
	}
	
	public static AutodetectSetting getAutodetectRules() {
		return (AutodetectSetting)map.get(SettingsEnum.AD);
	}
	
}
