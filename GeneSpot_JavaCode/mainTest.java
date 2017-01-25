import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
 * Created on Nov 30, 2016
 * Author: austinward 
 *
 */
public class mainTest {


	public static void main(String[] argv) {

		ArrayList<Genome> genomes = new ArrayList<>();
		ArrayList<Gene> genes = new ArrayList<>();
		ArrayList<String> geneName = new ArrayList<>();

		File file = new File("testingData.txt");
		File file2 = new File("proteinIDs.txt");

		String jobName = "Testing";

		double evalue = .00000000001;

		String[] hold;
		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String text = null;

			while ((text = reader.readLine()) != null) {

				hold = text.split("\t");

				Genome newGenome = new Genome(hold[4], hold[0], hold[2], hold[3], hold[5], hold[1]);
				genomes.add(newGenome);
			}

			reader = new BufferedReader(new FileReader(file2));

			int i = 0;
			boolean flag;

			while((text = reader.readLine()) != null) {

				flag = false;

				hold = text.split("\t");

				System.out.println(hold[0] + " " + hold[1]);

				if(genes.size() == 0){

					Gene newGene = new Gene(hold[1]);
					newGene.addQuery(hold[0]);
					genes.add(newGene);
				}

				else{
					i = 0;


					while(i < genes.size()){

						if(genes.get(i).getName().equals(hold[1])){
							flag = true;
							genes.get(i).addQuery(hold[0]);
						}

						i++;
					}
				}

				if(!flag){

					geneName.add(hold[1]);
					Gene newGene = new Gene(hold[1]);
					newGene.addQuery(hold[0]);
					genes.add(newGene);
				}
			}

			reader.close();

			i = 0;

			for(Genome thisGenome : genomes){

				for(Gene thisGene : genes){

					thisGenome.addGene(thisGene);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		Job testJob = new Job(jobName, genomes, evalue, geneName);

		testJob.printJob();

		MakeRequest sendJob = new MakeRequest();

		sendJob.sendRequest(testJob);

	}
}
