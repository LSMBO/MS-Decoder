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

package fr.lsmbo.msdecoder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import fr.lsmbo.msdecoder.decoder.*;
import fr.lsmbo.msdecoder.gui.DecoderGUI;

public class Main {

	// TODO add algorithm-specific parameters
	public static class ArgumentList {
		@Parameter
		private List<String> parameters = new ArrayList<String>();

		@Parameter(names = { "-input", "--input_spectrum_path" }, description = "Spectrum text file")
		private String spectrum = "";

		@Parameter(names = { "-tol", "--mz_tolerance" }, description = "M/z tolerance")
		private Double mzTolerance = 0.05;

		@Parameter(names = { "-it", "--intensity_threshold" }, description = "Intensity minimal value")
		private Double intensityThreshold = 4.0; // default should be 4

		@Parameter(names = { "-iti", "--intensity_threshold_for_isotope_search" }, description = "Intensity minimal value to search for the first isotope")
		private Double intensityThresholdForIsotopeSearch = 25.0; // default should be 25

		@Parameter(names = { "-p", "--polymer_type" }, description = "Indicate which polymer should be searched")
		private String polymer = "";

		@Parameter(names = { "-g", "--graphical_interface" }, description = "Use user-friendly GUI (default if CLI arguments are not provided)")
		private boolean gui = false;

		@Parameter(names = { "-h", "--help" }, description = "Display this help")
		private boolean help = false;
		
		@Parameter(names = { "-v", "--version" }, description = "Print version number")
		private boolean version = false;
		
//		@Parameter(names = { "-t", "--test" }, description = "Run some internal test [to be removed]")
//		private boolean test = false;
	}
	
	private static boolean checkSpectrumFile(String file) {
		try {
			File f = new File(file);
			if(f.exists() && f.isFile() && f.canRead()) {
				return true;
			}
		} catch(Exception e) {
			System.out.println("File '"+file+"' is not readable");
			e.printStackTrace();
		}
		return false;
	}
	
	private static boolean checkPolymerType(String polymer) {
		for(PolymerTypes p: PolymerTypes.values()) {
			if(p.name().equals(polymer)) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		ArgumentList jc = new ArgumentList();
		JCommander cmds = new JCommander(jc, args);
		cmds.setProgramName(AppInfo.getAppName());

		if(jc.help) {
			// just print usage and quit
			cmds.usage();
		} else if(jc.version) {
			// just print version number and quit
			System.out.println(AppInfo.getAppTitle());
		} else if(jc.parameters.size() == 0 || jc.gui) {
			// open gui
			DecoderGUI.run();
		} else {
			// if CLI, then spectrum and mode must be provided !
			if(checkSpectrumFile(jc.spectrum) && checkPolymerType(jc.polymer)) {
				// prepare a string for the expected response
				String code = "";
				
				// call the requested algorithm
				try {
					AbstractDecoder d = Decoders.get(PolymerTypes.valueOf(jc.polymer));
					code = d.decode(jc.spectrum, jc.mzTolerance, jc.intensityThreshold, jc.intensityThresholdForIsotopeSearch).getCode();
				} catch(Exception e) {
					e.printStackTrace();
				}
				// print the response
				System.out.println(code);
			} else {
				System.err.println("Error: input spectrum file and polymer type must be provided !");
			}
		}
	}

}
