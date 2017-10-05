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

public class DualCode extends Code {

	private Code cCode;
	private Code yCode;
	private boolean verbose;

	public DualCode(Code c, Code y, int expectedLength, boolean _verbose) {
		super();
		// store codes
		this.cCode = c;
		this.yCode = y;
		this.verbose = _verbose;
		// unify codes
		mergeCodes(expectedLength);
	}
	
	public DualCode(Code c, Code y, int expectedLength) {
		this(c, y, expectedLength, false);
	}
	
	public Code getC() {
		return cCode;
	}
	
	public Code getY() {
		return yCode;
	}
	
	private void mergeCodes(int expectedLength) {
		if(verbose && cCode.getCode().length() > expectedLength) System.out.println("Left to right code is too long !");
		if(verbose && yCode.getCode().length() > expectedLength) System.out.println("Right to left code is too long !");
		char[] mergedCode = new char[expectedLength];
		Fragment[] mergedFragments = new Fragment[expectedLength];
		
		// insert c code, and put '?' chars for unknown characters
		for(int i = 0; i < expectedLength; i++) {
			mergedCode[i] = (i < cCode.getCode().length() ? cCode.getCode().charAt(i) : '?');
			mergedFragments[i] = (i < cCode.getCode().length() ? cCode.getFragments().get(i) : null);
		}
		// insert y code, check for inconsistencies
		for(int i = 0; i < yCode.getCode().length(); i++) {
			int index = expectedLength - i - 1;
			if(mergedCode[index] == '?') {
				mergedCode[index] = yCode.getCode().charAt(i);
				mergedFragments[index] = yCode.getFragments().get(i); // non-sense for dual code, it's just to make sure the number of items is correct
			} else if(mergedCode[index] != yCode.getCode().charAt(i)) {
				if(verbose) System.out.println("ABU Code ambiguity at position "+index+": C="+mergedCode[index]+" and Y="+yCode.getCode().charAt(i));
				mergedCode[index] = 'X';
			}
		}
		// store results
		this.code = new String(mergedCode);
		ArrayList<Fragment> mergedArray = new ArrayList<Fragment>();
		for(int i = 0; i < expectedLength; i++) {
			mergedArray.add(mergedFragments[i]);
		}
		this.setFragments(mergedArray);
	}
	
	public String toString() {
		return "\""+cCode.toString() + "\n" + yCode.toString()+"\"";
	}

}
