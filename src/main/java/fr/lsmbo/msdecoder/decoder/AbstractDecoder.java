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

package fr.lsmbo.msdecoder.decoder;

import java.util.ArrayList;

import fr.lsmbo.msdecoder.decoder.model.Code;
import fr.lsmbo.msdecoder.decoder.model.Fragment;
import fr.lsmbo.msdecoder.decoder.model.Spectrum;

public class AbstractDecoder {
	
	public Code decode(String spectrumFilePath, Double mzTolerance, Double intensityThreshold,
			Double intensityThresholdForIsotopeSearch, boolean verbose) {
		long startTime = System.currentTimeMillis();
		
		// read file
	    Spectrum spectrum = new Spectrum(spectrumFilePath, mzTolerance, intensityThreshold, intensityThresholdForIsotopeSearch);
	    // run decoder
		Code code = run(spectrum, verbose);
		
		code.setComputationTime(System.currentTimeMillis() - startTime);
		// exit method
		return code;
	}
	
	public Code decode(String spectrumFilePath, Double mzTolerance, Double intensityThreshold, Double intensityThresholdForIsotopeSearch) {
		return decode(spectrumFilePath, mzTolerance, intensityThreshold, intensityThresholdForIsotopeSearch, false);
	}
	
	protected Code run(Spectrum spectrum, boolean decode) {
		return new Code("", new ArrayList<Fragment>());
	}
}