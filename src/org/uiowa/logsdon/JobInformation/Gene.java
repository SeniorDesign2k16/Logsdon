package org.uiowa.logsdon.JobInformation;
/*

 *
 * Created on Dec 2, 2016
 * Author: austinward 
 *
 */

public class Gene {

	private final String geneName;
	private final ProteinQuery[] proteinQueries;


	public Gene(String geneName, ProteinQuery[] proteinQueries) {

		this.geneName = geneName;
		this.proteinQueries = proteinQueries;

	}

	public String getName() {

		return geneName;
	}

	public ProteinQuery[] getProteinQueries(){

		return this.proteinQueries;
	}
}
