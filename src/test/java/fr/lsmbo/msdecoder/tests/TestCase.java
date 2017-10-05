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

package fr.lsmbo.msdecoder.tests;

import java.io.File;

import fr.lsmbo.msdecoder.decoder.AbstractDecoder;
import fr.lsmbo.msdecoder.decoder.Decoders;
import fr.lsmbo.msdecoder.decoder.PolymerTypes;
import fr.lsmbo.msdecoder.decoder.model.Code;

public class TestCase {

	private PolymerTypes polymerType;
	private String spectrumFilePath;
	private Double mzTolerance;
	private Double intensityThreshold;
	private Double intensityThresholdForIsotopeSearch;
	private String expectedCode;
	
	public TestCase(PolymerTypes polymerType, String spectrumFilePath, Double mzTolerance, Double intensityThreshold,
			Double intensityThresholdForIsotopeSearch, String expectedCode) {
		super();
		this.polymerType = polymerType;
		this.spectrumFilePath = this.getClass().getResource(spectrumFilePath).getPath();
		this.mzTolerance = mzTolerance;
		this.intensityThreshold = intensityThreshold;
		this.intensityThresholdForIsotopeSearch = intensityThresholdForIsotopeSearch;
		this.expectedCode = expectedCode;
	}
	
//	public TestCase(PolymerTypes polymerType, String spectrumFilePath, Double mzTolerance, Double intensityThreshold,
//			Double intensityThresholdForIsotopeSearch) {
//		super();
//		this.polymerType = polymerType;
//		this.spectrumFilePath = spectrumFilePath;
//		this.mzTolerance = mzTolerance;
//		this.intensityThreshold = intensityThreshold;
//		this.intensityThresholdForIsotopeSearch = intensityThresholdForIsotopeSearch;
//		this.expectedCode = expectedCode = "";
//	}

	public PolymerTypes getPolymerType() {
		return polymerType;
	}

//	public void setPolymerType(PolymerTypes polymerType) {
//		this.polymerType = polymerType;
//	}

	public String getSpectrumFilePath() {
		return spectrumFilePath;
	}

	public String getSpectrumFileName() {
		return (new File(spectrumFilePath)).getName();
	}

//	public void setSpectrumFilePath(String spectrumFilePath) {
//		this.spectrumFilePath = spectrumFilePath;
//	}
//
//	public Double getMzTolerance() {
//		return mzTolerance;
//	}
//
//	public void setMzTolerance(Double mzTolerance) {
//		this.mzTolerance = mzTolerance;
//	}
//
//	public Double getIntensityThreshold() {
//		return intensityThreshold;
//	}
//
//	public void setIntensityThreshold(Double intensityThreshold) {
//		this.intensityThreshold = intensityThreshold;
//	}
//
//	public Double getIntensityThresholdForIsotopeSearch() {
//		return intensityThresholdForIsotopeSearch;
//	}
//
//	public void setIntensityThresholdForIsotopeSearch(Double intensityThresholdForIsotopeSearch) {
//		this.intensityThresholdForIsotopeSearch = intensityThresholdForIsotopeSearch;
//	}

	public String getExpectedCode() {
		return expectedCode;
	}

//	public void setExpectedCode(String expectedCode) {
//		this.expectedCode = expectedCode;
//	}
	
	public Code run() {
		Code code = null;
		try {
			AbstractDecoder d = Decoders.get(polymerType);
			code = d.decode(spectrumFilePath, mzTolerance, intensityThreshold, intensityThresholdForIsotopeSearch);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	
//	public boolean testDecoding() {
//		Code code = run();
//		return (code.getCode().equals(expectedCode));
//	}
	
	public float testSpeed() {
		Code code = run();
		return code.getComputationTimeMilli();
	}
}
