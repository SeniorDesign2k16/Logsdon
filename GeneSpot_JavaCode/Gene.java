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

public class Gene {

	private final String geneName;
	private final ArrayList<String> proteinQueries = new ArrayList<>();

	public Gene(String geneName) {

		this.geneName = geneName;

	}

	public String getName() {

		return geneName;
	}

	public ArrayList<String> getQueries() {

		return proteinQueries;

	}

	public void addQuery(String query){

		proteinQueries.add(query);
	}

	public void printQueries(){

		System.out.println("Queries: ");
		System.out.println();

		int i = 0;

		while( i < getQueries().size()){

			System.out.println(proteinQueries.get(i));

			i++;
		}

		System.out.println("__________________________________");

	}
}
