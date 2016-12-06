
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastAlignmentProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastOutputProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastService;

public class MakeRequest {

	private static final double evalue = 0.0000000010;

	public MakeRequest() {

	}

	public void sendRequest(Job currentJob) {

		NCBIQBlastService server = new NCBIQBlastService();
		NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();

		ArrayList<Genome> genomes = currentJob.getGenomes();

		for (Genome currentGenome : genomes) {

			// defining input properties object
			props.setBlastProgram(BlastProgramEnum.tblastn);
			props.setBlastDatabase("genomic/" + currentGenome.getTaxID() + "/" + currentGenome.getGenome());
			props.setBlastExpect(currentJob.getEvalue());

			// defining output properties object
			NCBIQBlastOutputProperties outputProps = new NCBIQBlastOutputProperties();

			String rid = null; // blast request ID

			BufferedReader reader = null;

			Gene currentGene = currentGenome.getGene(currentJob.getGeneName());

			ArrayList<String> queries = currentGene.getQueries();

			int x = 0;

			while (x < queries.size()) {

				try {

					rid = server.sendAlignmentRequest(queries.get(x), props);

					while (!server.isReady(rid)) {
						System.out.println("Waiting...");
						Thread.sleep(5000);
					}

					InputStream input = server.getAlignmentResults(rid, outputProps);
					reader = new BufferedReader(new InputStreamReader(input));

					String line;

					while ((line = reader.readLine()) != null) {

						System.out.println(line);
					}
				}

				catch (Exception e) {
					e.printStackTrace();
				}

				x++;
			}
		}
	}
}
