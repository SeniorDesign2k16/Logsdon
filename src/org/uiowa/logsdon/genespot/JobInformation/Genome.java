package org.uiowa.logsdon.genespot.JobInformation;/*
													
													*
													* Created on Dec 2, 2016
													* Author: austinward 
													*
													*/

public class Genome extends Gene{

	// used for database structure
	private final String genome; // assembly number
	private final String kingdom; // kingdom of organism
	private final String subType; // type of organism
	private final String taxID; // tax ID

	private String assemblyType; // type of assembly -- scaffold, contig, or chromsome

	public Genome(String genome, String type, String subType, String assemblyType, String taxID,
			String geneName, ProteinQuery[] proteinQueries) {

		super(geneName, proteinQueries);

		this.genome = genome;
		this.kingdom = type;
		this.subType = subType;
		this.assemblyType = assemblyType;
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

	public String getAssemblyType() {

		return assemblyType;
	}

	public String getTaxID() {

		return taxID;
	}

	@Override
	public String toString() {

		return String.format(
				"Organism: %s\nTax ID: %s\nSubtype: %s\n\nAssembly: %s\nAssembly Type: %s\n\n"
						+ "-------------------------------------\n",
				taxID, kingdom, subType, genome, assemblyType);

	}
}
