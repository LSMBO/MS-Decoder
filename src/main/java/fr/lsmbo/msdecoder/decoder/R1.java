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
import fr.lsmbo.msdecoder.decoder.model.MzPair;
import fr.lsmbo.msdecoder.decoder.model.Spectrum;

/*
 * Deprotonated polyurethane (PU)
 * R1C3 is PU Type I
 * R1C4 is PU Type II 
 * 
 */
public class R1 extends AbstractDecoder {
	
	protected Double beginMz;
	protected MzPair mzCode;
	
	@Override
	public Code run(Spectrum spectrum, boolean verbose) {
		String code = "";
		spectrum.setCharge(1);
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		// there must be a peak at 131.1
		Fragment fragment = spectrum.getBestFragment(beginMz, beginMz, false, verbose);
		if(fragment != null) {
			if(verbose) System.out.println("\t"+fragment.toString());
			fragments.add(fragment);
			while(fragment != null) {
				Double mz0 = fragment.getMz() + mzCode.getMz0();
				Double mz1 = fragment.getMz() + mzCode.getMz1();
				fragment = spectrum.getBestFragment(mz0, mz1, false, verbose);
				if(fragment != null) {
					code += fragment.getAssociatedCode();
					fragments.add(fragment);
				}
			}
		}
		// return result
		return new Code(code, fragments);
	}
	
}
