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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import fr.lsmbo.msdecoder.decoder.PolymerTypes;

public class Tests {
	public static Map<String, TestCase> map() {
		Map<String, TestCase> map = new HashMap<String, TestCase>();
		Double mzTol = 0.05, intThreshold = 4.0, intThresIso = 25.0;
		
		// R1 C3
		map.put("R1C3_01", new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/1_R1-C3_BP-105-3_333.2.txt", mzTol, intThreshold, intThresIso, "00"));
		map.put("R1C3_02", new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/2_R1-C3_BP-074-1_434.2.txt", mzTol, intThreshold, intThresIso, "000"));
		map.put("R1C3_03", new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/3_R1-C3_BP-074_535.3.txt", mzTol, intThreshold, intThresIso, "0000"));
		map.put("R1C3_04", new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/4_R1-C3_BP-098-1_563.3.txt", mzTol, intThreshold, intThresIso, "0001"));
		map.put("R1C3_05", new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/5_R1-C3_BP-047_563.3.txt", mzTol, intThreshold, intThresIso, "0010"));
		map.put("R1C3_06", new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/6_R1-C3_BP-105-3_636.3.txt", mzTol, intThreshold, intThresIso, "00000"));
		map.put("R1C3_07", new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/7_R1-C3_BP-077_1023.5.txt", mzTol, intThreshold, intThresIso, "00010110"));
		map.put("R1C3_08", new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/8_R1-C3_BP-068_1023.5.txt", mzTol, intThreshold, intThresIso, "11100000"));

		// R1 C4
		map.put("R1C4_01", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/1_R1-C4_BP150-1_361.2.txt", mzTol, intThreshold, intThresIso, "00"));
		map.put("R1C4_02", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/2_R1-C4_DK-124_375.2.txt", mzTol, intThreshold, intThresIso, "01"));
		map.put("R1C4_03", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/3_R1-C4_DK138_476.3.txt", mzTol, intThreshold, intThresIso, "000"));
		map.put("R1C4_04", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/4_R1-C4_BP150-1_490.3.txt", mzTol, intThreshold, intThresIso, "001"));
		map.put("R1C4_05", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/5_R1-C4_DK154_490.3.txt", mzTol, intThreshold, intThresIso, "100"));
		map.put("R1C4_06", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/6_R1-C4_DK161_591.3.txt", mzTol, intThreshold, intThresIso, "0000"));
		map.put("R1C4_07", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/7_R1-C4_BP-005_605.4.txt", mzTol, intThreshold, intThresIso, "0001"));
		map.put("R1C4_08", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/8_R1-C4_BP-025_605.4.txt", mzTol, intThreshold, intThresIso, "0010"));
		map.put("R1C4_09", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/9_R1-C4_BP-038_605.4.txt", mzTol, intThreshold, intThresIso, "0100"));
		map.put("R1C4_10", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/10_R1-C4_BP-029_605.4.txt", mzTol, intThreshold, intThresIso, "1000"));
		map.put("R1C4_11", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/11_R1-C4_BP-090_619.4.txt", mzTol, intThreshold, intThresIso, "0011"));
		map.put("R1C4_12", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/12_R1-C4_BP-040_619.4.txt", mzTol, intThreshold, intThresIso, "1001"));
		map.put("R1C4_13", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/13_R1-C4_DK161_706.4.txt", mzTol, intThreshold, intThresIso, "00000"));
		map.put("R1C4_14", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/14_R1-C4_DK161_720.4.txt", mzTol, intThreshold, intThresIso, "00001"));
		map.put("R1C4_15", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/15_R1-C4_PC39_734.4.txt", mzTol, intThreshold, intThresIso, "00011"));
		map.put("R1C4_16", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/16_R1-C4_BP-050_734.4.txt", mzTol, intThreshold, intThresIso, "10001"));
		map.put("R1C4_17", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/17_R1-C4_INIT_748.4.txt", mzTol, intThreshold, intThresIso, "01101"));
		map.put("R1C4_18", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/18_R1-C4_BP-051_748.4.txt", mzTol, intThreshold, intThresIso, "01110"));
		map.put("R1C4_19", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/19_R1-C4_DK161_835.5.txt", mzTol, intThreshold, intThresIso, "000001"));
		map.put("R1C4_20", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/20_R1-C4_BP-073_849.5.txt", mzTol, intThreshold, intThresIso, "000101"));
		map.put("R1C4_21", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/21_R1-C4_BP-046_863.5.txt", mzTol, intThreshold, intThresIso, "000111"));
		map.put("R1C4_22", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/22_R1-C4_BP-053_863.5.txt", mzTol, intThreshold, intThresIso, "101010"));
		map.put("R1C4_23", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/23_R1-C4_DK55_891.5.txt", mzTol, intThreshold, intThresIso, "110111"));
		map.put("R1C4_24", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/24_R1-C4_DK154_964.6.txt", mzTol, intThreshold, intThresIso, "1000010"));
		map.put("R1C4_25", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/25_R1-C4_BP150-1_992.7.txt", mzTol, intThreshold, intThresIso, "0011110"));
		map.put("R1C4_26", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/26_R1-C4_BP-061_1079.6.txt", mzTol, intThreshold, intThresIso, "00110000"));
		map.put("R1C4_27", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/27_R1-C4_DK143_1079.6.txt", mzTol, intThreshold, intThresIso, "01000010"));
		map.put("R1C4_28", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/28_R1-C4_BP150-1_1107.8.txt", mzTol, intThreshold, intThresIso, "00111100"));
		map.put("R1C4_29", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/29_R1-C4_BP-078_1207.6.txt", mzTol, intThreshold, intThresIso, "00010101"));
		map.put("R1C4_30", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/30_R1-C4_BP-072_1121.7.txt", mzTol, intThreshold, intThresIso, "00111110"));
		map.put("R1C4_31", new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/31_R1-C4_BP-080_2084.2.txt", mzTol, intThreshold, intThresIso, "1101001011011000"));
		
		// R2
		mzTol = 0.03;
		map.put("R2_01", new TestCase(PolymerTypes.R2, "/R2/1_R2_RR-4-16_662.29_1.txt", mzTol, intThreshold, intThresIso, "000"));
		map.put("R2_02", new TestCase(PolymerTypes.R2, "/R2/2_R2_CL-20_676.31_1.txt", mzTol, intThreshold, intThresIso, "001"));
		map.put("R2_03", new TestCase(PolymerTypes.R2, "/R2/3_R2_CL-15_676.33_1.txt", mzTol, intThreshold, intThresIso, "010"));
		map.put("R2_04", new TestCase(PolymerTypes.R2, "/R2/4_R2_CL-7_676.32_1.txt", mzTol, intThreshold, intThresIso, "100"));
		map.put("R2_05", new TestCase(PolymerTypes.R2, "/R2/5_R2_CL-10_690.33_1.txt", mzTol, intThreshold, intThresIso, "011"));
		map.put("R2_06", new TestCase(PolymerTypes.R2, "/R2/6_R2_CL-18_690.33_1.txt", mzTol, intThreshold, intThresIso, "101"));
		map.put("R2_07", new TestCase(PolymerTypes.R2, "/R2/7_R2_CL-5_690.34_1.txt", mzTol, intThreshold, intThresIso, "110"));
		map.put("R2_08", new TestCase(PolymerTypes.R2, "/R2/8_R2_RR-1_704.37_1.txt", mzTol, intThreshold, intThresIso, "111"));
		map.put("R2_09", new TestCase(PolymerTypes.R2, "/R2/9_R2_CL-81_888.48_1.txt", mzTol, intThreshold, intThresIso, "0000"));
		map.put("R2_10", new TestCase(PolymerTypes.R2, "/R2/10_R2_CL-58_930.52_1.txt", mzTol, intThreshold, intThresIso, "1110"));
		map.put("R2_11a", new TestCase(PolymerTypes.R2, "/R2/11a_R2_RR-2_944.57_1.txt", mzTol, intThreshold, intThresIso, "1111"));
//		map.put("R2_11b", new TestCase(PolymerTypes.R2, "/R2/11b_R2_RR-2_472.78_2.txt", mzTol, intThreshold, intThresIso, "1111")); // séquence à trou
		map.put("R2_12", new TestCase(PolymerTypes.R2, "/R2/12_R2_CL-92_1156.67_1.txt", mzTol, intThreshold, intThresIso, "10101"));
//		map.put("R2_13", new TestCase(PolymerTypes.R2, "/R2/13_R2_CL-31_1156.73_1.txt", mzTol, intThreshold, intThresIso, "10110")); // séquence à trou
		map.put("R2_14", new TestCase(PolymerTypes.R2, "/R2/14_R2_CL-21_1156.66_1.txt", mzTol, intThreshold, intThresIso, "11010")); // example 1
//		map.put("R2_15a", new TestCase(PolymerTypes.R2, "/R2/15a_R2_CL-59_1170.70_1.txt", mzTol, intThreshold, intThresIso, "11101")); // C3 and Y1 are missing
		map.put("R2_15b", new TestCase(PolymerTypes.R2, "/R2/15b_new_R2_CL-59_585.86_2.txt", mzTol, intThreshold, intThresIso, "11101"));
		map.put("R2_16a", new TestCase(PolymerTypes.R2, "/R2/16a_R2_RR-3_1184.74_1.txt", mzTol, intThreshold, intThresIso, "11111"));
		map.put("R2_16b", new TestCase(PolymerTypes.R2, "/R2/16b_R2_RR-3_592.88_2.txt", mzTol, intThreshold, intThresIso, "11111"));
		map.put("R2_17", new TestCase(PolymerTypes.R2, "/R2/17_new_R2_DK-2_691.94_2.txt", mzTol, intThreshold, intThresIso, "011100"));
		map.put("R2_18a", new TestCase(PolymerTypes.R2, "/R2/18a_new_R2_DK-14_1368.85_1.txt", mzTol, intThreshold, intThresIso, "100100"));
		map.put("R2_18b", new TestCase(PolymerTypes.R2, "/R2/18b_new_R2_DK-14_684.93_2.txt", mzTol, intThreshold, intThresIso, "100100"));
		map.put("R2_19", new TestCase(PolymerTypes.R2, "/R2/19_R2_RR-4-83_698.93_2.txt", mzTol, intThreshold, intThresIso, "110101"));
//		map.put("R2_20a", new TestCase(PolymerTypes.R2, "/R2/20a_R2_RR-4-84_1396.88_1.txt", mzTol, intThreshold, intThresIso, "110110")); // spectre pas assez intense
		map.put("R2_20b", new TestCase(PolymerTypes.R2, "/R2/20b_R2_RR-4-84_698.94_2.txt", mzTol, intThreshold, intThresIso, "110110")); // example 2
		map.put("R2_21a", new TestCase(PolymerTypes.R2, "/R2/21a_R2_CL-71_1410.90_1.txt", mzTol, intThreshold, intThresIso, "111110"));
		map.put("R2_21b", new TestCase(PolymerTypes.R2, "/R2/21b_R2_CL-71_705.95_2.txt", mzTol, intThreshold, intThresIso, "111110"));
		map.put("R2_22a", new TestCase(PolymerTypes.R2, "/R2/22a_R2_RR-4-67_1424.91_1.txt", mzTol, intThreshold, intThresIso, "111111"));
		map.put("R2_22b", new TestCase(PolymerTypes.R2, "/R2/22b_R2_RR-4-67_712.95_2.txt", mzTol, intThreshold, intThresIso, "111111"));
		map.put("R2_23", new TestCase(PolymerTypes.R2, "/R2/23_R2_CL-93_797.97_2.txt", mzTol, intThreshold, intThresIso, "0001010"));
		map.put("R2_24", new TestCase(PolymerTypes.R2, "/R2/24_R2_CL-91_805.01_2.txt", mzTol, intThreshold, intThresIso, "1010010"));
		map.put("R2_25a", new TestCase(PolymerTypes.R2, "/R2/25a_R2_CL-38_1665.12_1.txt", mzTol, intThreshold, intThresIso, "1111111"));
		map.put("R2_25b", new TestCase(PolymerTypes.R2, "/R2/25b_R2_CL-38_833.05_2.txt", mzTol, intThreshold, intThresIso, "1111111"));
		map.put("R2_26", new TestCase(PolymerTypes.R2, "/R2/26_R2_CL-82_925.09_2.txt", mzTol, intThreshold, intThresIso, "01010011"));
//		map.put("R2_27", new TestCase(PolymerTypes.R2, "/R2/27_R2_CL-90P_932.10_2.txt", mzTol, intThreshold, intThresIso, "11100110")); // séquence à trou
//		map.put("R2_28", new TestCase(PolymerTypes.R2, "/R2/28_R2_CL-90E_939.10_2.txt", mzTol, intThreshold, intThresIso, "11110011")); // séquence à trou
//		map.put("R2_29", new TestCase(PolymerTypes.R2, "/R2/29_R2_CL-90C_939.10_2.txt", mzTol, intThreshold, intThresIso, "11110101")); // séquence à trou
//		map.put("R2_30", new TestCase(PolymerTypes.R2, "/R2/30_R2_CL-90A_946.11_2.txt", mzTol, intThreshold, intThresIso, "11110111")); // séquence à trou
		map.put("R2_31", new TestCase(PolymerTypes.R2, "/R2/31_R2_CL-61_953.15_2.txt", mzTol, intThreshold, intThresIso, "11111111"));
		map.put("R2_32", new TestCase(PolymerTypes.R2, "/R2/32_new_acq_R2_CL-89_1145.27_2.txt", mzTol, intThreshold, intThresIso, "1000001010"));
		map.put("R2_33", new TestCase(PolymerTypes.R2, "/R2/33_R2_RR-4-81_1193.29_2.txt", mzTol, intThreshold, intThresIso, "1111111111"));
		
		// R3 C3
		mzTol = 0.05;
		map.put("R3C3_01", new TestCase(PolymerTypes.R3C3, "/R3-C3/1_R3-C3_GC-87_528.3_2.txt", mzTol, intThreshold, intThresIso, "00"));
		map.put("R3C3_02", new TestCase(PolymerTypes.R3C3, "/R3-C3/2_R3-C3_GC-37_515.6_3.txt", mzTol, intThreshold, intThresIso, "000"));
		map.put("R3C3_03", new TestCase(PolymerTypes.R3C3, "/R3-C3/3_R3-C3_GC-65_509.3_4.txt", mzTol, intThreshold, intThresIso, "0000"));
		map.put("R3C3_04", new TestCase(PolymerTypes.R3C3, "/R3-C3/4_R3-C3_GC-59_523.5_4.txt", mzTol, intThreshold, intThresIso, "1001"));
		map.put("R3C3_05", new TestCase(PolymerTypes.R3C3, "/R3-C3/5_R3-C3_GC-76_513.9_8.txt", mzTol, intThreshold, intThresIso, "11000101"));
		
		// R3 C4
		map.put("R3C4_01", new TestCase(PolymerTypes.R3C4, "/R3-C4/1_R3-C4_GC-52_542.3.txt", mzTol, intThreshold, intThresIso, "00"));
		map.put("R3C4_02", new TestCase(PolymerTypes.R3C4, "/R3-C4/2_R3-C4_GC-95_556.3.txt", mzTol, intThreshold, intThresIso, "11"));
		map.put("R3C4_03", new TestCase(PolymerTypes.R3C4, "/R3-C4/3_R3-C4_GC-93_523.4.txt", mzTol, intThreshold, intThresIso, "0000"));
		map.put("R3C4_04", new TestCase(PolymerTypes.R3C4, "/R3-C4/4_R3-C4_GC-75_530.4.txt", mzTol, intThreshold, intThresIso, "0011"));
		map.put("R3C4_05", new TestCase(PolymerTypes.R3C4, "/R3-C4/5_R3-C4_GC-62_530.4.txt", mzTol, intThreshold, intThresIso, "1100"));
		map.put("R3C4_06", new TestCase(PolymerTypes.R3C4, "/R3-C4/6_R3-C4_GC-80_521.8.txt", mzTol, intThreshold, intThresIso, "01001110"));
		
		return map;
	}

	public static ArrayList<TestCase> list() {
		// transform the map into an ordered list
		ArrayList<TestCase> list = new ArrayList<TestCase>();
		for(TestCase tc: map().values()) {
			list.add(tc);
		}
		Collections.sort(list, new Comparator<TestCase>() {
		    @Override
		    public int compare(TestCase o1, TestCase o2) {
		        return o1.getSpectrumFilePath().compareTo(o2.getSpectrumFilePath());
		    }
		});
		return list;
		
//		ArrayList<TestCase> list = new ArrayList<TestCase>();
//		Double mzTol = 0.05, intThreshold = 4.0, intThresIso = 25.0;
//		
//		// R1 C3
//		list.add(new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/1_R1-C3_BP-105-3_333.2.txt", mzTol, intThreshold, intThresIso, "00"));
//		list.add(new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/2_R1-C3_BP-074-1_434.2.txt", mzTol, intThreshold, intThresIso, "000"));
//		list.add(new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/3_R1-C3_BP-074_535.3.txt", mzTol, intThreshold, intThresIso, "0000"));
//		list.add(new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/4_R1-C3_BP-098-1_563.3.txt", mzTol, intThreshold, intThresIso, "0001"));
//		list.add(new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/5_R1-C3_BP-047_563.3.txt", mzTol, intThreshold, intThresIso, "0010"));
//		list.add(new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/6_R1-C3_BP-105-3_636.3.txt", mzTol, intThreshold, intThresIso, "00000"));
//		list.add(new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/7_R1-C3_BP-077_1023.5.txt", mzTol, intThreshold, intThresIso, "00010110"));
//		list.add(new TestCase(PolymerTypes.R1C3, "/R1-C3_PU/8_R1-C3_BP-068_1023.5.txt", mzTol, intThreshold, intThresIso, "11100000"));
//
//		// R1 C4
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/1_R1-C4_BP150-1_361.2.txt", mzTol, intThreshold, intThresIso, "00"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/2_R1-C4_DK-124_375.2.txt", mzTol, intThreshold, intThresIso, "01"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/3_R1-C4_DK138_476.3.txt", mzTol, intThreshold, intThresIso, "000"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/4_R1-C4_BP150-1_490.3.txt", mzTol, intThreshold, intThresIso, "001"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/5_R1-C4_DK154_490.3.txt", mzTol, intThreshold, intThresIso, "100"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/6_R1-C4_DK161_591.3.txt", mzTol, intThreshold, intThresIso, "0000"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/7_R1-C4_BP-005_605.4.txt", mzTol, intThreshold, intThresIso, "0001"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/8_R1-C4_BP-025_605.4.txt", mzTol, intThreshold, intThresIso, "0010"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/9_R1-C4_BP-038_605.4.txt", mzTol, intThreshold, intThresIso, "0100"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/10_R1-C4_BP-029_605.4.txt", mzTol, intThreshold, intThresIso, "1000"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/11_R1-C4_BP-090_619.4.txt", mzTol, intThreshold, intThresIso, "0011"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/12_R1-C4_BP-040_619.4.txt", mzTol, intThreshold, intThresIso, "1001"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/13_R1-C4_DK161_706.4.txt", mzTol, intThreshold, intThresIso, "00000"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/14_R1-C4_DK161_720.4.txt", mzTol, intThreshold, intThresIso, "00001"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/15_R1-C4_PC39_734.4.txt", mzTol, intThreshold, intThresIso, "00011"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/16_R1-C4_BP-050_734.4.txt", mzTol, intThreshold, intThresIso, "10001"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/17_R1-C4_INIT_748.4.txt", mzTol, intThreshold, intThresIso, "01101"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/18_R1-C4_BP-051_748.4.txt", mzTol, intThreshold, intThresIso, "01110"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/19_R1-C4_DK161_835.5.txt", mzTol, intThreshold, intThresIso, "000001"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/20_R1-C4_BP-073_849.5.txt", mzTol, intThreshold, intThresIso, "000101"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/21_R1-C4_BP-046_863.5.txt", mzTol, intThreshold, intThresIso, "000111"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/22_R1-C4_BP-053_863.5.txt", mzTol, intThreshold, intThresIso, "101010"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/23_R1-C4_DK55_891.5.txt", mzTol, intThreshold, intThresIso, "110111"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/24_R1-C4_DK154_964.6.txt", mzTol, intThreshold, intThresIso, "1000010"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/25_R1-C4_BP150-1_992.7.txt", mzTol, intThreshold, intThresIso, "0011110"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/26_R1-C4_BP-061_1079.6.txt", mzTol, intThreshold, intThresIso, "00110000"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/27_R1-C4_DK143_1079.6.txt", mzTol, intThreshold, intThresIso, "01000010"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/28_R1-C4_BP150-1_1107.8.txt", mzTol, intThreshold, intThresIso, "00111100"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/29_R1-C4_BP-078_1207.6.txt", mzTol, intThreshold, intThresIso, "00010101"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/30_R1-C4_BP-072_1121.7.txt", mzTol, intThreshold, intThresIso, "00111110"));
//		list.add(new TestCase(PolymerTypes.R1C4, "/R1-C4_PU/31_R1-C4_BP-080_2084.2.txt", mzTol, intThreshold, intThresIso, "1101001011011000"));
//		
//		// R2
//		mzTol = 0.03;
//		list.add(new TestCase(PolymerTypes.R2, "/R2/1_R2_RR-4-16_662.29_1.txt", mzTol, intThreshold, intThresIso, "000"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/2_R2_CL-20_676.31_1.txt", mzTol, intThreshold, intThresIso, "001"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/3_R2_CL-15_676.33_1.txt", mzTol, intThreshold, intThresIso, "010"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/4_R2_CL-7_676.32_1.txt", mzTol, intThreshold, intThresIso, "100"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/5_R2_CL-10_690.33_1.txt", mzTol, intThreshold, intThresIso, "011"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/6_R2_CL-18_690.33_1.txt", mzTol, intThreshold, intThresIso, "101"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/7_R2_CL-5_690.34_1.txt", mzTol, intThreshold, intThresIso, "110"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/8_R2_RR-1_704.37_1.txt", mzTol, intThreshold, intThresIso, "111"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/9_R2_CL-81_888.48_1.txt", mzTol, intThreshold, intThresIso, "0000"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/10_R2_CL-58_930.52_1.txt", mzTol, intThreshold, intThresIso, "1110"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/11a_R2_RR-2_944.57_1.txt", mzTol, intThreshold, intThresIso, "1111"));
////		list.add(new TestCase(PolymerTypes.R2, "/R2/11b_R2_RR-2_472.78_2.txt", mzTol, intThreshold, intThresIso, "1111")); // séquence à trou
//		list.add(new TestCase(PolymerTypes.R2, "/R2/12_R2_CL-92_1156.67_1.txt", mzTol, intThreshold, intThresIso, "10101"));
////		list.add(new TestCase(PolymerTypes.R2, "/R2/13_R2_CL-31_1156.73_1.txt", mzTol, intThreshold, intThresIso, "10110")); // séquence à trou
//		list.add(new TestCase(PolymerTypes.R2, "/R2/14_R2_CL-21_1156.66_1.txt", mzTol, intThreshold, intThresIso, "11010")); // example 1
////		list.add(new TestCase(PolymerTypes.R2, "/R2/15a_R2_CL-59_1170.70_1.txt", mzTol, intThreshold, intThresIso, "11101")); // C3 and Y1 are missing
//		list.add(new TestCase(PolymerTypes.R2, "/R2/15b_new_R2_CL-59_585.86_2.txt", mzTol, intThreshold, intThresIso, "11101"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/16a_R2_RR-3_1184.74_1.txt", mzTol, intThreshold, intThresIso, "11111"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/16b_R2_RR-3_592.88_2.txt", mzTol, intThreshold, intThresIso, "11111"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/17_new_R2_DK-2_691.94_2.txt", mzTol, intThreshold, intThresIso, "011100"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/18a_new_R2_DK-14_1368.85_1.txt", mzTol, intThreshold, intThresIso, "100100"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/18b_new_R2_DK-14_684.93_2.txt", mzTol, intThreshold, intThresIso, "100100"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/19_R2_RR-4-83_698.93_2.txt", mzTol, intThreshold, intThresIso, "110101"));
////		list.add(new TestCase(PolymerTypes.R2, "/R2/20a_R2_RR-4-84_1396.88_1.txt", mzTol, intThreshold, intThresIso, "110110")); // spectre pas assez intense
//		list.add(new TestCase(PolymerTypes.R2, "/R2/20b_R2_RR-4-84_698.94_2.txt", mzTol, intThreshold, intThresIso, "110110")); // example 2
//		list.add(new TestCase(PolymerTypes.R2, "/R2/21a_R2_CL-71_1410.90_1.txt", mzTol, intThreshold, intThresIso, "111110"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/21b_R2_CL-71_705.95_2.txt", mzTol, intThreshold, intThresIso, "111110"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/22a_R2_RR-4-67_1424.91_1.txt", mzTol, intThreshold, intThresIso, "111111"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/22b_R2_RR-4-67_712.95_2.txt", mzTol, intThreshold, intThresIso, "111111"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/23_R2_CL-93_797.97_2.txt", mzTol, intThreshold, intThresIso, "0001010"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/24_R2_CL-91_805.01_2.txt", mzTol, intThreshold, intThresIso, "1010010"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/25a_R2_CL-38_1665.12_1.txt", mzTol, intThreshold, intThresIso, "1111111"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/25b_R2_CL-38_833.05_2.txt", mzTol, intThreshold, intThresIso, "1111111"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/26_R2_CL-82_925.09_2.txt", mzTol, intThreshold, intThresIso, "01010011"));
////		list.add(new TestCase(PolymerTypes.R2, "/R2/27_R2_CL-90P_932.10_2.txt", mzTol, intThreshold, intThresIso, "11100110")); // séquence à trou
////		list.add(new TestCase(PolymerTypes.R2, "/R2/28_R2_CL-90E_939.10_2.txt", mzTol, intThreshold, intThresIso, "11110011")); // séquence à trou
////		list.add(new TestCase(PolymerTypes.R2, "/R2/29_R2_CL-90C_939.10_2.txt", mzTol, intThreshold, intThresIso, "11110101")); // séquence à trou
////		list.add(new TestCase(PolymerTypes.R2, "/R2/30_R2_CL-90A_946.11_2.txt", mzTol, intThreshold, intThresIso, "11110111")); // séquence à trou
//		list.add(new TestCase(PolymerTypes.R2, "/R2/31_R2_CL-61_953.15_2.txt", mzTol, intThreshold, intThresIso, "11111111"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/32_new_acq_R2_CL-89_1145.27_2.txt", mzTol, intThreshold, intThresIso, "1000001010"));
//		list.add(new TestCase(PolymerTypes.R2, "/R2/33_R2_RR-4-81_1193.29_2.txt", mzTol, intThreshold, intThresIso, "1111111111"));
//		
//		// R3 C3
//		mzTol = 0.05;
//		list.add(new TestCase(PolymerTypes.R3C3, "/R3-C3/1_R3-C3_GC-87_528.3_2.txt", mzTol, intThreshold, intThresIso, "00"));
//		list.add(new TestCase(PolymerTypes.R3C3, "/R3-C3/2_R3-C3_GC-37_515.6_3.txt", mzTol, intThreshold, intThresIso, "000"));
//		list.add(new TestCase(PolymerTypes.R3C3, "/R3-C3/3_R3-C3_GC-65_509.3_4.txt", mzTol, intThreshold, intThresIso, "0000"));
//		list.add(new TestCase(PolymerTypes.R3C3, "/R3-C3/4_R3-C3_GC-59_523.5_4.txt", mzTol, intThreshold, intThresIso, "1001"));
//		list.add(new TestCase(PolymerTypes.R3C3, "/R3-C3/5_R3-C3_GC-76_513.9_8.txt", mzTol, intThreshold, intThresIso, "11000101"));
//		
//		// R3 C4
//		list.add(new TestCase(PolymerTypes.R3C4, "/R3-C4/1_R3-C4_GC-52_542.3.txt", mzTol, intThreshold, intThresIso, "00"));
//		list.add(new TestCase(PolymerTypes.R3C4, "/R3-C4/2_R3-C4_GC-95_556.3.txt", mzTol, intThreshold, intThresIso, "11"));
//		list.add(new TestCase(PolymerTypes.R3C4, "/R3-C4/3_R3-C4_GC-93_523.4.txt", mzTol, intThreshold, intThresIso, "0000"));
//		list.add(new TestCase(PolymerTypes.R3C4, "/R3-C4/4_R3-C4_GC-75_530.4.txt", mzTol, intThreshold, intThresIso, "0011"));
//		list.add(new TestCase(PolymerTypes.R3C4, "/R3-C4/5_R3-C4_GC-62_530.4.txt", mzTol, intThreshold, intThresIso, "1100"));
//		list.add(new TestCase(PolymerTypes.R3C4, "/R3-C4/6_R3-C4_GC-80_521.8.txt", mzTol, intThreshold, intThresIso, "01001110"));
//		
//		return list;
	}
}
