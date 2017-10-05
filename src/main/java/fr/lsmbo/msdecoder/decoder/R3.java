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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lsmbo.msdecoder.decoder.model.Code;
import fr.lsmbo.msdecoder.decoder.model.Fragment;
import fr.lsmbo.msdecoder.decoder.model.MzPair;
import fr.lsmbo.msdecoder.decoder.model.Spectrum;

/*
 * Deprotonated poly(alkoxyamine phosphodiesther) (PAP)
 * R3C3 is PAP Type I
 * R3C4 is PAP Type II 
 * 
 */
public class R3 extends AbstractDecoder {
	
	protected MzPair beginMzCode;
	protected MzPair mzCode;

	private int getLength(String fileName) {
		final Pattern p = Pattern.compile(".*[_\\.](\\d+).txt$");
		int length = 0;
		Matcher m = p.matcher(fileName);
		if(m.find()) {
			length = Integer.parseInt(m.group(1));
		}
		return length;
	}
	
	private Double getExpectedMz(Double expectedMz, Double currentMz, int multiplier) {
		return currentMz + (expectedMz - currentMz) / multiplier;
	}
	
	@Override
	public Code run(Spectrum spectrum, boolean verbose) {
		String code = "";
		spectrum.setCharge(1);
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();

		fragments.add(new Fragment(-1, 0d, 0d));
		Fragment fragment = spectrum.getBestFragment(beginMzCode.getMz0(), beginMzCode.getMz1(), true, verbose);
		if(fragment != null) {
			code += fragment.getAssociatedCode();
			fragments.add(fragment);
	
			int length = getLength(spectrum.getFileName());
			while(code.length() < length) {
				Double mz0 = this.getExpectedMz(mzCode.getMz0(), fragment.getMz(), code.length() + 1);
				Double mz1 = this.getExpectedMz(mzCode.getMz1(), fragment.getMz(), code.length() + 1);
				fragment = spectrum.getBestFragment(mz0, mz1, true, code.length() + 1, verbose);
				if(fragment != null) {
					code += fragment.getAssociatedCode();
					fragments.add(fragment);
				} else break;
			}
		}

		return new Code(code, fragments);
	}
	
}
