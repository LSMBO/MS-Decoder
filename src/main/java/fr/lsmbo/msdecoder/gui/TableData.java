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

package fr.lsmbo.msdecoder.gui;

import java.io.File;

import fr.lsmbo.msdecoder.decoder.PolymerTypes;
import fr.lsmbo.msdecoder.decoder.model.Code;

public class TableData {
	private File file;
	private String fileName;
    private Code code;
    private Exception error;
    private PolymerTypes effectiveType;

    TableData(File _file) {
        this.file = _file;
        this.fileName = this.file.getName();
        this.code = null;
        this.error = null;
        this.effectiveType = null;
    }

	public File getFile() {
		return file;
	}

	public String getFileName() {
		return fileName;
	}

	public Code getCode() {
		return code;
	}

	public String getTextCode() {
		return getTextCode(true);
	}

	public String getTextCode(boolean formatted) {
		if(error != null) {
			return error.getMessage();
		} else if(code == null) {
			return ""; // code is null when files are loaded but not decoded yet
		} else if(code.getCode().isEmpty() || code.getCode().equals("")) {
			return "<empty>"; // may happen when wrong algorithm is selected
		} else if(formatted && code.getCode().length() > 4) {
			return code.getCode(true);
		} else {
			return code.getCode();
		}
	}

	public void setCode(Code _code) {
//		if(_code == null) {
//			this.error = new Exception("Code is null");
//		}
		this.code = _code;
	}
	
	public void setError(Exception e) {
		this.error = e;
	}
	
	public PolymerTypes getPolymerType() {
		for(PolymerTypes p: PolymerTypes.values()) {
			if(this.getFileName().contains(p.getValue()))
				return p;
		}
		return PolymerTypes.Autodetect;
	}
	
	public PolymerTypes getEffectivePolymerType() {
		return this.effectiveType;
	}

	public void setPolymerType(PolymerTypes _type) {
		this.effectiveType = _type;
	}

}
