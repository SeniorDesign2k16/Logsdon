package org.uiowa.logsdon.genespot;
import java.util.ArrayList;

/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 * Created on Dec 2, 2016
 * Author: austinward 
 *
 */

public class Genome {

	private final String genome;
	private final String species;
	private final String type;
	private final String subType;
	private final String taxID;

	private String assemblyType;

	private ArrayList<Gene> genes = new ArrayList<>();

	public Genome(String genome, String species, String type, String subType, String assemblyType, String taxID) {

		this.genome = genome;
		this.species = species;
		this.type = type;
		this.subType = subType;
		this.assemblyType = assemblyType;
		this.taxID = taxID;
	}

	public String getGenome() {

		return genome;
	}

	public String getSpecies() {

		return species;
	}

	public String getType() {

		return type;
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

	public boolean findGene(String geneName) {

		int i = 0;

		while (i < genes.size()) {

			if (genes.get(i).getName() == geneName) {

				return true; // gene has prior data
			}

		}

		return false; // gene has no prior data
	}

	public void addGene(Gene gene) {

		genes.add(gene);

	}

	public Gene getGene(String geneName) {

		int i = 0;
		String currentName;

		while (i < genes.size()) {

			currentName = genes.get(i).getName();

			if (currentName == geneName) {

				return genes.get(i);
			}

			i++;
		}

		return null;
	}

	public int getGeneCount() {

		return genes.size();
	}

	@Override
	public String toString() {

		return String.format(
				"Organism: %s\nTax ID: %s\nType: %s\nSubtype: %s\n\nAssembly: %s\nAssembly Type: %s\n\nGene Count: %d\n\n"
						+ "-------------------------------------\n",
				species, taxID, type, subType, genome, assemblyType, this.getGeneCount());

	}

}
