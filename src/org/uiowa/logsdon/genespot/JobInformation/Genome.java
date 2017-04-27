package org.uiowa.logsdon.genespot.JobInformation;/*
													
													*
													* Created on Dec 2, 2016
													* Author: austinward 
													*
													*/

public class Genome extends Gene {

	// used for database structure
	private final String genome; // assembly number
	private final String kingdom; // kingdom of organism
	private final String subType; // type of organism
	private final String taxID; // tax ID
	private final String speciesName;// Name of species

	private String assembly; // type of assembly -- scaffold, contig, or chromsome

	public Genome(String genome, String type, String subType, String assembly, String taxID, String geneName,
			ProteinQuery[] proteinQueries, String speciesName) {

		super(geneName, proteinQueries);

		this.genome = genome.replace("v", ".");
		this.kingdom = type;
		this.subType = subType;
		this.assembly = assembly;
		this.taxID = taxID;
		this.speciesName = speciesName;

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

		return assembly;
	}

	public String getTaxID() {

		return taxID;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	@Override
	public String toString() {

		return String.format("Organism: %s\nTax ID: %s\nSubtype: %s\n\nAssembly: %s\nAssembly Type: %s\n\n"
				+ "-------------------------------------\n", taxID, kingdom, subType, genome, assembly);

	}

}
