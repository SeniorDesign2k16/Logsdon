package org.uiowa.logsdon.genespot;
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

import java.util.ArrayList;

public class Gene {

	private final String geneName;
	private final ArrayList<String> proteinQueries;

	public Gene(String geneName, Genome genome, ArrayList<String> genesOfInterest) {

		this.geneName = geneName;

		if (!genome.findGene(geneName)) {

			genome.addGene(this);
			proteinQueries = genesOfInterest;
		}

		else {

			proteinQueries = genesOfInterest;
		}

	}

	public String getName() {

		return geneName;
	}

	public ArrayList<String> getQueries() {

		return proteinQueries;

	}

	@Override
	public String toString() {

		return String.format("Gene: %s\nQuery: %s\n-------------------------------------\n", geneName,
				proteinQueries.get(0));
	}

}
