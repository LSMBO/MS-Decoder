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

import org.junit.Assert;
import org.junit.Test;

import fr.lsmbo.msdecoder.decoder.model.Code;

public class DecodingTest {

	
	private void check(TestCase test) {
		Code code = test.run();
		Assert.assertEquals(code.getCode(), test.getExpectedCode());
	}

	// R1 C3
	@Test
	public void r1c3_01() { check(Tests.map().get("R1C3_01")); }
	@Test
	public void r1c3_02() { check(Tests.map().get("R1C3_02")); }
	@Test
	public void r1c3_03() { check(Tests.map().get("R1C3_03")); }
	@Test
	public void r1c3_04() { check(Tests.map().get("R1C3_04")); }
	@Test
	public void r1c3_05() { check(Tests.map().get("R1C3_05")); }
	@Test
	public void r1c3_06() { check(Tests.map().get("R1C3_06")); }
	@Test
	public void r1c3_07() { check(Tests.map().get("R1C3_07")); }
	@Test
	public void r1c3_08() { check(Tests.map().get("R1C3_08")); }

	// R1 C4
	@Test
	public void r1c4_01() { check(Tests.map().get("R1C4_01")); }
	@Test
	public void r1c4_02() { check(Tests.map().get("R1C4_02")); }
	@Test
	public void r1c4_03() { check(Tests.map().get("R1C4_03")); }
	@Test
	public void r1c4_04() { check(Tests.map().get("R1C4_04")); }
	@Test
	public void r1c4_05() { check(Tests.map().get("R1C4_05")); }
	@Test
	public void r1c4_06() { check(Tests.map().get("R1C4_06")); }
	@Test
	public void r1c4_07() { check(Tests.map().get("R1C4_07")); }
	@Test
	public void r1c4_08() { check(Tests.map().get("R1C4_08")); }
	@Test
	public void r1c4_09() { check(Tests.map().get("R1C4_09")); }
	@Test
	public void r1c4_10() { check(Tests.map().get("R1C4_10")); }
	@Test
	public void r1c4_11() { check(Tests.map().get("R1C4_11")); }
	@Test
	public void r1c4_12() { check(Tests.map().get("R1C4_12")); }
	@Test
	public void r1c4_13() { check(Tests.map().get("R1C4_13")); }
	@Test
	public void r1c4_14() { check(Tests.map().get("R1C4_14")); }
	@Test
	public void r1c4_15() { check(Tests.map().get("R1C4_15")); }
	@Test
	public void r1c4_16() { check(Tests.map().get("R1C4_16")); }
	@Test
	public void r1c4_17() { check(Tests.map().get("R1C4_17")); }
	@Test
	public void r1c4_18() { check(Tests.map().get("R1C4_18")); }
	@Test
	public void r1c4_19() { check(Tests.map().get("R1C4_19")); }
	@Test
	public void r1c4_20() { check(Tests.map().get("R1C4_20")); }
	@Test
	public void r1c4_21() { check(Tests.map().get("R1C4_21")); }
	@Test
	public void r1c4_22() { check(Tests.map().get("R1C4_22")); }
	@Test
	public void r1c4_23() { check(Tests.map().get("R1C4_23")); }
	@Test
	public void r1c4_24() { check(Tests.map().get("R1C4_24")); }
	@Test
	public void r1c4_25() { check(Tests.map().get("R1C4_25")); }
	@Test
	public void r1c4_26() { check(Tests.map().get("R1C4_26")); }
	@Test
	public void r1c4_27() { check(Tests.map().get("R1C4_27")); }
	@Test
	public void r1c4_28() { check(Tests.map().get("R1C4_28")); }
	@Test
	public void r1c4_29() { check(Tests.map().get("R1C4_29")); }
	@Test
	public void r1c4_30() { check(Tests.map().get("R1C4_30")); }
	@Test
	public void r1c4_31() { check(Tests.map().get("R1C4_31")); }

	// R2
	@Test
	public void r2_01() { check(Tests.map().get("R2_01")); }
	@Test
	public void r2_02() { check(Tests.map().get("R2_02")); }
	@Test
	public void r2_03() { check(Tests.map().get("R2_03")); }
	@Test
	public void r2_04() { check(Tests.map().get("R2_04")); }
	@Test
	public void r2_05() { check(Tests.map().get("R2_05")); }
	@Test
	public void r2_06() { check(Tests.map().get("R2_06")); }
	@Test
	public void r2_07() { check(Tests.map().get("R2_07")); }
	@Test
	public void r2_08() { check(Tests.map().get("R2_08")); }
	@Test
	public void r2_09() { check(Tests.map().get("R2_09")); }
	@Test
	public void r2_10() { check(Tests.map().get("R2_10")); }
	@Test
	public void r2_11a() { check(Tests.map().get("R2_11a")); }
	@Test
	public void r2_12() { check(Tests.map().get("R2_12")); }
	@Test
	public void r2_14() { check(Tests.map().get("R2_14")); }
	@Test
	public void r2_15b() { check(Tests.map().get("R2_15b")); }
	@Test
	public void r2_16a() { check(Tests.map().get("R2_16a")); }
	@Test
	public void r2_16b() { check(Tests.map().get("R2_16b")); }
	@Test
	public void r2_17() { check(Tests.map().get("R2_17")); }
	@Test
	public void r2_18a() { check(Tests.map().get("R2_18a")); }
	@Test
	public void r2_19() { check(Tests.map().get("R2_19")); }
	@Test
	public void r2_20b() { check(Tests.map().get("R2_20b")); }
	@Test
	public void r2_21a() { check(Tests.map().get("R2_21a")); }
	@Test
	public void r2_21b() { check(Tests.map().get("R2_21b")); }
	@Test
	public void r2_22a() { check(Tests.map().get("R2_22a")); }
	@Test
	public void r2_22b() { check(Tests.map().get("R2_22b")); }
	@Test
	public void r2_23() { check(Tests.map().get("R2_23")); }
	@Test
	public void r2_24() { check(Tests.map().get("R2_24")); }
	@Test
	public void r2_25a() { check(Tests.map().get("R2_25a")); }
	@Test
	public void r2_25b() { check(Tests.map().get("R2_25b")); }
	@Test
	public void r2_26() { check(Tests.map().get("R2_26")); }
	@Test
	public void r2_31() { check(Tests.map().get("R2_31")); }
	@Test
	public void r2_32() { check(Tests.map().get("R2_32")); }
	@Test
	public void r2_33() { check(Tests.map().get("R2_33")); }

	// R3 C3
	@Test
	public void r3c3_01() { check(Tests.map().get("R3C3_01")); }
	@Test
	public void r3c3_02() { check(Tests.map().get("R3C3_02")); }
	@Test
	public void r3c3_03() { check(Tests.map().get("R3C3_03")); }
	@Test
	public void r3c3_04() { check(Tests.map().get("R3C3_04")); }
	@Test
	public void r3c3_05() { check(Tests.map().get("R3C3_05")); }

	// R3 C4
	@Test
	public void r3c4_01() { check(Tests.map().get("R3C4_01")); }
	@Test
	public void r3c4_02() { check(Tests.map().get("R3C4_02")); }
	@Test
	public void r3c4_03() { check(Tests.map().get("R3C4_03")); }
	@Test
	public void r3c4_04() { check(Tests.map().get("R3C4_04")); }
	@Test
	public void r3c4_05() { check(Tests.map().get("R3C4_05")); }
	@Test
	public void r3c4_06() { check(Tests.map().get("R3C4_06")); }

}
