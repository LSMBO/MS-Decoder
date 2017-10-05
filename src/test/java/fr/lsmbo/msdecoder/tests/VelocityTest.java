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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fr.lsmbo.msdecoder.decoder.PolymerTypes;
import fr.lsmbo.msdecoder.decoder.model.Code;

public class VelocityTest {
	
	private static int nbMaxLoopsPerTestCase = 10000; // about 3h to achieve 10000 loops
	private static File exitCondition = new File("./ExitPolymereDecoder.txt");

	public void run() {
		
		// initialize test by creating a map to store the cumulated time for each test
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		Map<Integer, ArrayList<Float>> fullMap = new HashMap<Integer, ArrayList<Float>>();
		ArrayList<Integer> testIds = new ArrayList<Integer>();
		for(int i = 0; i < Tests.list().size(); i++) {
			map.put(i, 0.0);
			fullMap.put(i, new ArrayList<Float>());
			testIds.add(i);
		}
		
		// run the tests many times
		int nbLoopsSoFar = 1;
		while(nbLoopsSoFar <= nbMaxLoopsPerTestCase && !exitCondition.exists()) {
			Collections.shuffle(testIds);
			for(int i: testIds) {
				Code code = Tests.list().get(i).run();
				// store the computation time
				map.put(i, map.get(i) + code.getComputationTimeMilli());
				fullMap.get(i).add(code.getComputationTimeMilli());
			}
			if(nbLoopsSoFar % 50 == 0) System.out.println(""+nbLoopsSoFar+" iterations so far...");
			nbLoopsSoFar++;
		}
		nbLoopsSoFar--; // this is the real number of loops realized
		
		// display average results: one file = one line
		System.out.println("\nAlgorithm\tFile\tAverage time (ms) on "+nbLoopsSoFar+" runs");
		for(int i = 0; i < Tests.list().size(); i++) {
			TestCase tc = Tests.list().get(i);
			String output = tc.getPolymerType().toString() + "\t" + tc.getSpectrumFileName() + "\t" + (map.get(i) / nbLoopsSoFar);
			System.out.println(output);
		}
		System.out.println("");
		
		Double[][] matrix = new Double[PolymerTypes.values().length][nbLoopsSoFar];
		Map<Integer, Integer> mapRunsPerType = new HashMap<Integer, Integer>();
		// display all results : one file = one column
		for(int j = 0; j < nbLoopsSoFar; j++) {
			// print header line
			if(j == 0) {
				String firstLine = "\n#run";
				for(TestCase tc: Tests.list()) firstLine += "\t" + tc.getSpectrumFileName();
				System.out.println(firstLine);
			}
			// print all lines
			String line = ""+(j+1);
			for(int i = 0; i < Tests.list().size(); i++) {
				line += "\t" + fullMap.get(i).get(j);
				// store sum per type
				for(int p = 0; p < PolymerTypes.values().length; p++) {
					if(PolymerTypes.values()[p].equals(Tests.list().get(i).getPolymerType())) {
						if(matrix[p][j] == null) matrix[p][j] = 0.0;
						matrix[p][j] += fullMap.get(i).get(j);
						if(!mapRunsPerType.containsKey(p)) mapRunsPerType.put(p, 0);
						mapRunsPerType.put(p, mapRunsPerType.get(p) + 1);
					}
				}
			}
			System.out.println(line);
		}
		
		// also display results grouped by polymer type
		for(int j = 0; j < nbLoopsSoFar; j++) {
			// print header line
			if(j == 0) {
				String firstLine = "\n#run";
				for(int p = 1; p < PolymerTypes.values().length; p++) firstLine += "\t" + PolymerTypes.values()[p].getValue();
				System.out.println(firstLine);
			}
			// print all lines
			String line = ""+(j+1);
			for(int p = 1; p < PolymerTypes.values().length; p++) {
				Double sum = matrix[p][j];
				Integer nb = mapRunsPerType.get(p);
				line += "\t" + (sum / nb);
			}
			System.out.println(line);
		}
	}
	
}
