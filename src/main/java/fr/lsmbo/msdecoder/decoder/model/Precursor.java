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

public class Precursor {
	private Fragment fragment;
	private int charge;
	
	public Precursor(Fragment fragment, int charge) {
		super();
		this.fragment = fragment;
		this.charge = charge;
	}

	public Precursor(Double precursorMz, int precursorCharge) {
		super();
		this.fragment = new Fragment(-1, precursorMz, 0d);
		this.charge = precursorCharge;
	}

	public Double getMz() {
		return fragment.getMz();
	}

	public int getCharge() {
		return charge;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public void setFragment(Fragment fragment) {
		if(fragment == null) {
			System.out.println("ABU setFragment(null)");
		} else {
			fragment.setCharge(this.charge);
		}
		this.fragment = fragment;
	}
	
	public String toString() {
		if(fragment == null) {
			return "Precursor of charge "+charge+" and fragment null";
		} else {
			return "Precursor of charge "+charge+" and fragment "+fragment.toString();
		}
	}
}
