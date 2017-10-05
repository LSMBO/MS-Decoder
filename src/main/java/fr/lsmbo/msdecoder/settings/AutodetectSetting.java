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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.lsmbo.msdecoder.decoder.PolymerTypes;

public class AutodetectSetting extends Setting {

	private Map<PolymerTypes, String> value;
	private Map<PolymerTypes, String> defaultValue;
	
	public AutodetectSetting(SettingsEnum id, String name, String shortName, String description, Map<PolymerTypes, String> defaultValue) {
		super(id, name, shortName, description);
		this.defaultValue = defaultValue;
		this.value = new HashMap<PolymerTypes, String>();
	}

	public Map<PolymerTypes, String> getValue() {
		return value;
	}
	
	public void setValue(String value) {
		
	}

	public void setValue(Map<PolymerTypes, String> value) {
		this.value = value;
	}
	
	public void setValue(PolymerTypes pt, String value) {
		this.value.put(pt, value);
	}
	
	public void setValue(String key, String value) {
		for(PolymerTypes pt: PolymerTypes.values()) {
			if(key.equals(getAutodetectKey(pt))) setValue(pt, value);
		}
	}

	public Map<PolymerTypes, String> getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Map<PolymerTypes, String> defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public void resetValue() {
		this.value.clear();
		this.value.putAll(this.defaultValue);
	}
	
	private String getAutodetectKey(String key) {
		return this.shortName + "[" + key + "]";
	}
	private String getAutodetectKey(PolymerTypes key) {
		return getAutodetectKey(key.getName());
	}

	@Override
	public ArrayList<String> formatValueForStoring(Boolean useDefault) {
		// example: autodetectRules[R1C3]=_R1-C3_
		ArrayList<String> values = new ArrayList<String>();
		Map<PolymerTypes, String> adValues = (useDefault ? defaultValue : value);
		for(PolymerTypes key: adValues.keySet()) {
			values.add(getAutodetectKey(key) + "=" + adValues.get(key));
		}
		return values;
	}
	
}
