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

package fr.lsmbo.msdecoder.decoder.model;

import java.util.ArrayList;

public class Code {

	protected String code;
	protected ArrayList<Fragment> fragments;
	private long computationTime;

	public Code() {
		this.computationTime = 0;
	}
	
	public Code(String code, ArrayList<Fragment> fragments) {
		super();
		this.code = code;
		this.fragments = fragments;
	}

	public String getCode() {
		return code;
	}
	
	public String getCode(boolean formatCode) {
		if(!formatCode) {
			return getCode();
		} else {
			String formattedCode = code;
			// prepend space characters to the code to make sure it can be splitted in groups of 4 characters
			int nbMissingChars = 4 - formattedCode.length() % 4;
			for(int i = 0; i < nbMissingChars; i++) formattedCode = " " + formattedCode;
			// split in groups of 4
			String returnedCode = "";
			for(String s: formattedCode.split("(?<=\\G....)")) {
				returnedCode += s + " ";
			}
			return returnedCode.substring(0, returnedCode.length() - 1);
		}
	}

	protected void setCode(String code) {
		this.code = code;
	}

	public ArrayList<Fragment> getFragments() {
		return fragments;
	}

	protected void setFragments(ArrayList<Fragment> fragments) {
		this.fragments = fragments;
	}
	
	public void setComputationTime(long time) {
		this.computationTime = time;
	}
	
//	public long getComputationTime() {
//		return this.computationTime;
//	}
	
	public float getComputationTimeMilli() {
		return (float)this.computationTime;
	}
	
	public String toString() {
		String string = "";
		if(code.length() > 0) {
			String[] codes = code.split("");
			if(code.length() != fragments.size() - 1) {
				return "Error: code length ("+code.length()+") does not match fragment list ("+fragments.size()+")!";
			}
			string = "" + fragments.get(0).getMz();
			for(int i = 0; i < codes.length; i++) {
				string += " -["+codes[i]+"]-> " + fragments.get(i+1).getMz();
			}
		}
		return string;
	}
}
