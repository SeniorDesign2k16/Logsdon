package org.uiowa.logsdon.genespot.JobInformation;/*
													
													*
													* Created on Dec 2, 2016
													* Author: austinward 
													*
													*/

public class Genome extends Gene{

	//Used for database structure
	private final String genome;  // species name
	private final String kingdom; // kingdom of organism
	private final String subType; // type of organism
	private final String taxID;   //tax ID

	private String assembly;  //

	public Genome(String genome, String type, String subType, String assembly, String taxID,
			String geneName, ProteinQuery[] proteinQueries) {

		super(geneName, proteinQueries);

		this.genome = genome;
		this.kingdom = type;
		this.subType = subType;
		this.assembly = assembly;
		this.taxID = taxID;
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

	public String getAssembly() {

		return assembly;
	}

	public String getTaxID() {

		return taxID;
	}

	@Override
	public String toString() {

		return String.format(
				"Organism: %s\nTax ID: %s\nSubtype: %s\n\nAssembly: %s\nAssembly Type: %s\n\n"
						+ "-------------------------------------\n",
				taxID, kingdom, subType, genome, assembly);

	}
}
