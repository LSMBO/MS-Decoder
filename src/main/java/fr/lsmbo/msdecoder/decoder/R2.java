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
import fr.lsmbo.msdecoder.decoder.model.DualCode;
import fr.lsmbo.msdecoder.decoder.model.Fragment;
import fr.lsmbo.msdecoder.decoder.model.MzPair;
import fr.lsmbo.msdecoder.decoder.model.Spectrum;

/*
 * Deprotonated poly(alkoxyamine acide) (PAA) 
 * 
 */
public class R2 extends AbstractDecoder {

	protected MzPair cIonsBegin;
	protected MzPair yIonsBegin;
	protected MzPair xIonsNext;
	protected MzPair codeLengthMz;
	
	private boolean verbose = true;

	public R2() {
		cIonsBegin = new MzPair(130.05, 144.07);
		yIonsBegin = new MzPair(305.09, 319.10);
		xIonsNext = new MzPair(226.17, 240.18);
		codeLengthMz = new MzPair(209.0, 226.0);
	}
	
	private int getLength(Spectrum spectrum) {
		// sample name: 2_R2_CL-20_676.31_1.txt
		// name pattern: R2_[sample name]_[precursor mz]_[charge].txt
		final Pattern p = Pattern.compile(".*R2_[^_]*_([^_]*)_(\\d+).txt$");
		Matcher m = p.matcher(spectrum.getFileName());
		if(m.find()) {
			spectrum.setPrecursorMz(Float.parseFloat(m.group(1)));
			spectrum.setCharge(Integer.parseInt(m.group(2)));
			return (int)(java.lang.Math.floor((spectrum.getPrecursorMz() * spectrum.getCharge()) - spectrum.getCharge() - codeLengthMz.getMz0()) / codeLengthMz.getMz1() + 1);
		}
		return -1;
	}
	
	@Override
	public Code run(Spectrum spectrum, boolean _verbose) {
		this.verbose = _verbose;
		if(verbose) spectrum.printIntensities();
		int codeLength = getLength(spectrum);
		if(verbose) System.out.println("\tSpectrum '"+spectrum.getFileName()+"' ; Code length: "+codeLength);
	
		// get code for C ions
		Code codeL2R = computeCode(spectrum, codeLength, this.cIonsBegin);
		if(verbose) System.out.println("\t>>> C code: "+codeL2R.getCode()+" fragments: "+codeL2R.toString());
		// get code for Y ions
		Code codeR2L = computeCode(spectrum, codeLength, this.yIonsBegin);
		if(verbose) System.out.println("\t>>> Y code: "+codeR2L.getCode()+" fragments: "+codeR2L.toString());

		// return the merge of both codes
		return new DualCode(codeL2R, codeR2L, codeLength);
	}
	
	private Code computeCode(Spectrum spectrum, int codeLength, MzPair begin) {
		String code = "";
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		// a) find first fragment
		fragments.add(new Fragment(-1, 0d, 0d));
		Double mz0 = spectrum.getPrecursorMz() - begin.getMz0() / spectrum.getCharge();
		Double mz1 = spectrum.getPrecursorMz() - begin.getMz1() / spectrum.getCharge();
		Fragment fragment = spectrum.getBestFragment(mz0, mz1, true, verbose);
		if(fragment != null) {
			code += fragment.getAssociatedCode();
			if(verbose) System.out.println("\t> Keep fragment "+fragment.toString() + " with code "+fragment.getAssociatedCode());
			Double mz = (code.equals("0") ? begin.getMz0() : begin.getMz1());
			fragments.add(fragment);
	
			int z = 1;
			while(true) {
				mz = mz * (z < 3 ? 1 : z - 1) + 1;
				Code c = loopForCode(spectrum, new Code(code, fragments), codeLength, mz, z);
				code = c.getCode();
				fragments.addAll(c.getFragments());
				if(fragments.size() == 2) {
					mz = (code.equals("0") ? begin.getMz0() : begin.getMz1());
//					if(verbose) System.out.println("\t> code='"+code+"' with "+fragments.size()+" fragments ; consider begin mz="+mz);
				} else {
					mz = fragments.get(fragments.size()-1).getMz();
//					if(verbose) System.out.println("\t> code='"+code+"' with "+fragments.size()+" fragments ; consider previous mz="+mz);
				}
				// exit rules
				if(code.length() == codeLength || z == 3 || spectrum.getCharge() == z) break;
				z++;
			}
		}
		return new Code(code, fragments);
	}
	
	private Code loopForCode(Spectrum spectrum, Code startingCode, int codeLength, Double mz, int divider) {
		if(verbose) System.out.println("\t>> Search at charge "+divider);
		String code = startingCode.getCode();
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		Double lastMz = mz / divider;
		while(code.length() < codeLength) {
			Double mz0 = lastMz + (xIonsNext.getMz0()) / divider;
			Double mz1 = lastMz + (xIonsNext.getMz1()) / divider;

			Fragment fragment = spectrum.getBestFragment(mz0, mz1, true, divider, verbose);
			if(fragment != null) {
				code += fragment.getAssociatedCode();
				if(verbose) System.out.println("\t> Keep fragment "+fragment.toString() + " with code "+fragment.getAssociatedCode());
				fragments.add(fragment);
				lastMz = fragment.getMz();
			} else break;
		}
		// fragments and code should be returned...
		return new Code(code, fragments);
	}

}
