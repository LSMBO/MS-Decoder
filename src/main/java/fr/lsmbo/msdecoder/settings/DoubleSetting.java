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

public class DoubleSetting extends Setting {

	private Double value;
	private Double defaultValue;
	private String unit;

	public DoubleSetting(SettingsEnum id, String name, String shortName, String description, Double defaultValue, String unit) {
		super(id, name, shortName, description);
		this.defaultValue = defaultValue;
		this.value = defaultValue;
		this.unit = unit;
	}
	public DoubleSetting(SettingsEnum id, String name, String shortName, String description, Double defaultValue) {
		this(id, name, shortName, description, defaultValue, "");
	}

	public Double getValue() {
		return value;
	}
	
	public void setValue(String value) {
		try {
			this.value = Double.parseDouble(value);
		} catch(Exception e) {}
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Double defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getUnit() {
		return unit;
	}
	
	public void resetValue() {
		this.value = defaultValue;
	}

	@Override
	public ArrayList<String> formatValueForStoring(Boolean useDefault) {
		// example: mzTolerance=0.05
		ArrayList<String> values = new ArrayList<String>();
		values.add(this.shortName + "=" + (useDefault ? defaultValue : value));
		return values;
	}
}
