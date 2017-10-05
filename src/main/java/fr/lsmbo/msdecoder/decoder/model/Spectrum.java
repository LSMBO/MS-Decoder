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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Spectrum {

	private String fileName;
	private ArrayList<Fragment> fragments;
	private Double mzTolerance;
	private Double intensityThreshold;
	private Double intensityThresholdForIsotopeSearch;
	
	// optional information retrieved from file name
	private Float precursorMz;
	private int charge;
	
	public Spectrum(String filePath, Double mzTolerance, Double intensityThreshold, Double intensityThresholdForIsotopeSearch) {
		File file = new File(filePath);
		this.fileName = file.getName();
		this.mzTolerance = mzTolerance;
		this.intensityThreshold = intensityThreshold;
		this.intensityThresholdForIsotopeSearch = intensityThresholdForIsotopeSearch;
		fragments = new ArrayList<Fragment>();
		// this try will close the file automatically at the end
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			// read file and store values locally
			String line;
			while ((line = br.readLine()) != null) {
				try {
					String[] items = line.split("[\\s\\t]");
					Double mz = new Double(items[0]);
					Double intensity = new Double(items[1]);
					fragments.add(new Fragment(fragments.size(), mz, intensity));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Double getMzTolerance() {
		return mzTolerance;
	}

	public void setMzTolerance(Double mzTolerance) {
		this.mzTolerance = mzTolerance;
	}
	
	public Float getPrecursorMz() {
		return precursorMz;
	}

	public void setPrecursorMz(Float precursorMz) {
		this.precursorMz = precursorMz;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	private Fragment finalFragment(Fragment fragment, String code, boolean verbose) {
//		if(verbose) System.out.println("\t> Keep fragment ="+fragment.toString() + " with code "+code);
		fragment.setAssociatedCode(code);
		return fragment;
	}
	
	public void printIntensities() {
		System.out.println("\tIntensity threshold is "+intensityThreshold);
	}
	
	private boolean fragmentIsBetterThan(Fragment candidate, Fragment current, Double targetMz, boolean verbose) {
		Double mz = candidate.getMz();
		// candidate needs to be in the correct range
		if(mz >= targetMz - this.mzTolerance && mz <= targetMz + this.mzTolerance) {
//			if(verbose) System.out.println("\t? candidate "+mz+" is in range");
//			if(verbose && current == null) System.out.println("\t? no current fragment, so candidate is the best");
			if(current == null) return true; // no current fragment, so candidate is the best
//			if(verbose && candidate.getIntensity() > current.getIntensity()) System.out.println("\t? candidate is more intense so it's the best");
			if(candidate.getIntensity() > current.getIntensity()) return true; // candidate is more intense so it's the best
//			if(candidate.getIntensity() < current.getIntensity()) return false; // candidate is less intense so it's not the best
			Double diffCandidate = java.lang.Math.abs(targetMz - mz);
			Double diffCurrent = java.lang.Math.abs(targetMz - current.getMz());
			// candidate as the same intensity but is closer to the target mz so it's the best
//			if(verbose && candidate.getIntensity().equals(current.getIntensity()) && diffCandidate < diffCurrent) System.out.println("\t? candidate as the same intensity but is closer to the target mz so it's the best");
			if(candidate.getIntensity().equals(current.getIntensity()) && diffCandidate < diffCurrent) return true;
		}
		return false;
	}
	
	private boolean isFragmentValid(Fragment fragment, Fragment isotope, boolean considerIsotope, boolean verbose) {
		// in any cases, the fragment has to exist and have some intensity
		if(fragment == null || fragment.getIntensity() < this.intensityThreshold) return false;
		// if the isotope has to be considered
		if(considerIsotope) {
			if(isotope == null) return false;
			if(fragment.getIntensity() < this.intensityThresholdForIsotopeSearch) return true; // if the fragment is not very intense, its isotope will be even lower !
			if(isotope.getIntensity() < this.intensityThreshold) return false;
		}
		// everything is ok !
		return true;
	}
	
	public Fragment getBestFragment(Double mz0, Double mz1, Boolean considerIsotopes, int z, Boolean verbose) {
		// prepare variables
		Fragment f0 = null, f1 = null, i0 = null, i1 = null; 
		int i = 0;
		Double deltaIsotope = (considerIsotopes ? (double)1 / z : 0.0);
		Double min = java.lang.Math.min(mz0, mz1) - mzTolerance;
		Double max = java.lang.Math.max(mz0, mz1) + deltaIsotope + 2*mzTolerance;
		if(verbose) System.out.println("\tSearching for mz0="+mz0+" and mz1="+mz1+" in the range["+min+", "+max+"]");
		// loop until the lowest mz value to consider
		while(i < this.fragments.size() && this.fragments.get(i).getMz() < min) {
			i++;
		}
		// search for all 4 fragments
		while(i < this.fragments.size() && this.fragments.get(i).getMz() <= max) {
			Fragment fragment = this.fragments.get(i);
			if(fragmentIsBetterThan(fragment, f0, mz0, false)) f0 = fragment;
			if(fragmentIsBetterThan(fragment, f1, mz1, false)) f1 = fragment;
			if(considerIsotopes && fragmentIsBetterThan(fragment, i0, mz0 + deltaIsotope, false)) i0 = fragment;
			if(considerIsotopes && fragmentIsBetterThan(fragment, i1, mz1 + deltaIsotope, false)) i1 = fragment;
			i++;
		}
		// analyse fragments found, a fragment is valid only if the isotope has been found
		boolean f0isValid = isFragmentValid(f0, i0, considerIsotopes, verbose);
		boolean f1isValid = isFragmentValid(f1, i1, considerIsotopes, verbose);
		// return the best fragment
		if(verbose) {
			System.out.println("\tComparing F0="+(f0 == null ? "<null>" : f0.toString())+" (target="+mz0+") to F1="+(f1 == null ? "<null>" : f1.toString())+" (target="+mz1+")");
			System.out.println("\tIsotopes are I0="+(i0 == null ? "<null>" : i0.toString())+" (target="+mz0 + deltaIsotope+") and I1="+(i1 == null ? "<null>" : i1.toString())+" (target="+mz1 + deltaIsotope+")");
			System.out.println("\t> F0 is "+(isFragmentValid(f0, i0, false, false) ? "" : "not ")+"valid with "+(isFragmentValid(i0, null, false, false) ? "valid" : "invalid")+" isotope");
			System.out.println("\t> F1 is "+(isFragmentValid(f1, i1, false, false) ? "" : "not ")+"valid with "+(isFragmentValid(i1, null, false, false) ? "valid" : "invalid")+" isotope");
			if(f0isValid && !f1isValid) System.out.println("\t= return F0");
			if(!f0isValid && f1isValid) System.out.println("\t= return F1");
			if(f0isValid && f1isValid) System.out.println("\t= return most intense: "+(f0.getIntensity() > f1.getIntensity() ? "F0" : "F1"));
			if(!f0isValid && !f1isValid) System.out.println("\t= return null");
		}
		if(f0isValid && !f1isValid) return finalFragment(f0, "0", verbose);
		if(!f0isValid && f1isValid) return finalFragment(f1, "1", verbose);
		if(f0isValid && f1isValid) {
			return (f0.getIntensity() > f1.getIntensity() ? finalFragment(f0, "0", verbose) : finalFragment(f1, "1", verbose));
		};
		// both are invalid, it's the end
		return null;
	}
	
	public Fragment getBestFragment(Double mz0, Double mz1, Boolean considerIsotopes, Boolean verbose) {
		return getBestFragment(mz0, mz1, considerIsotopes, this.charge, verbose);
	}
}
