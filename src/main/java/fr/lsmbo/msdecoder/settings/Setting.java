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

public class Setting implements ISetting {

	protected SettingsEnum id;
	protected String name;
	protected String shortName;
	protected String description;
	
	public Setting(SettingsEnum id, String name, String shortName, String description) {
		super();
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.description = description;
	}

	public SettingsEnum getId() {
		return id;
	}

	public void setId(SettingsEnum id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setValue(String value) {}
	
	public Object getValue() {
		return null;
	}
	
	public void resetValue() {}
	
	public ArrayList<String> formatValueForStoring(Boolean useDefault) {
		return new ArrayList<String>();
	}
	
	public ArrayList<String> formatValueForStoring() {
		return formatValueForStoring(false);
	}
	
	public String toString() {
		ArrayList<String> values = formatValueForStoring();
		String v = (values.size() == 0 ? "<null>" : (values.size() == 1 ? values.get(0) : "[Map of " + values.size() + " items]"));
		return "Setting[id:" + id.name() + " name:'" + name + "' shortName:'" + shortName + "' value:'" + v + "'";
	}
}
