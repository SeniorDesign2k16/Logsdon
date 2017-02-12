package org.uiowa.logsdon.JobInformation;/*

 *
 * Created on Dec 2, 2016
 * Author: austinward 
 *
 */

public class Genome {

	//used for database structure
	private final String genome;    		//assembly number
	private final String kingdom;			//kingdom of organism
	private final String subType;			//type of organism
	private final String taxID;				//tax ID

	private String assemblyType; 			//type of assembly -- scaffold, contig, or chromsome

	private Gene[] genesOfInterest; 		//contains JobInformation.Gene objects that hold information pertain to the protein data on NCBI

	public Genome(String genome, String type, String subType, String assemblyType, String taxID, Gene[] genesOfInterest) {

		this.genome = genome;
		this.kingdom = type;
		this.subType = subType;
		this.assemblyType = assemblyType;
		this.taxID = taxID;

		this.genesOfInterest = genesOfInterest;
	}

	public String getGenome() {

		return genome;
	}


	public String getKingdom() {

		return kingdom;
	}

	public String getSubType() {

		return subType;
	}

	public String getAssemblyType() {

		return assemblyType;
	}

	public String getTaxID() {

		return taxID;
	}

	@Override
	public String toString() {

		return String.format(
				"Organism: %s\nTax ID: %s\nSubtype: %s\n\nAssembly: %s\nAssembly Type: %s\n\nJobInformation.Gene Count: %d\n\n"
						+ "-------------------------------------\n",
				taxID, kingdom, subType, genome, assemblyType, genesOfInterest.length);

	}

	public Gene[] getGenesOfInterest() {

		return this.genesOfInterest;
	}
}
