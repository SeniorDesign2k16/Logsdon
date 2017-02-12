package org.uiowa.logsdon.NCBI;
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

import java.io.*;
import java.util.ArrayList;
import JobInformation.*;

import JobInformation.Gene;
import JobInformation.Genome;
import JobInformation.Job;
import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastAlignmentProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastOutputProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastService;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class MakeRequest {

	private final ResultstoFirebaseCopy sendToDB;         //uses results from NCBI to update database
	private BufferedReader reader;


	//constructor
	public MakeRequest() {

		this.sendToDB = new ResultstoFirebaseCopy();
	}


	//takes in job object and makes request to NCBI
	public void sendRequest(Job currentJob) {

		Genome[] genomes = currentJob.getGenomesOfInterest();   //getting the array of genomes the job is holding

		try {

			NCBIQBlastService server = new NCBIQBlastService();
			NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();

			// defining input properties object
			props.setBlastProgram(BlastProgramEnum.tblastn);
			props.setBlastExpect(currentJob.getEvalue());

			// defining output properties object
			NCBIQBlastOutputProperties outputProps = new NCBIQBlastOutputProperties();


			// creates a new handler for the next query
			XMLReader p = XMLReaderFactory.createXMLReader();


			//looping through Genome by Genome
			for (Genome currentGenome : genomes) {

				Gene[] geneOfInterest = currentGenome.getGenesOfInterest(); // getting the genes

				//looping throught Gene by Gene
				for (Gene currentGene : geneOfInterest) {

					ProteinQuery[] proteinQueries = currentGene.getProteinQueries();

					for (ProteinQuery currentQuery : proteinQueries) {

						XMLHandler handler = new XMLHandler(currentQuery);


						props.setBlastDatabase("genomic/" + currentGenome.getTaxID() + "/" + currentGenome.getGenome());

						p.setContentHandler(handler);

						String rid = null; // blast request ID

						int time = 0;

						try {

							rid = server.sendAlignmentRequest(currentQuery.getQueryNumber(), props);

							while (!server.isReady(rid)) {
								System.out.println("Waiting..." + String.valueOf(time) + " secs");
								time += 5;
								Thread.sleep(5000);
							}

							InputStream input = server.getAlignmentResults(rid, outputProps);
							reader = new BufferedReader(new InputStreamReader(input));


							String line;
							String hold = null;

							// writing to xml file
							while ((line = reader.readLine()) != null) {
								//System.out.println(line);
								hold += (line + "\n");
							}


							System.out.println("here");
							p.parse(new InputSource(new ByteArrayInputStream(hold.getBytes("utf-8"))));


							//Job name --- Current Genome --- Current Gene ---

							sendToDB.SendtoGenespot(currentJob.getJobName(), currentGenome, currentGene);

							/*
							sendToDB.SendtoGenespot(handler.getHits(), currentJob.getJobName(), currentGenome.getSpecies(),
									queries.get(x).replace('.', 'v'), currentGeneName, currentGenome.getGenome(),
									currentGenome.getKingdom(), queries.size());
							*/


						} catch (Exception e) {

						}
					}

				}
			}
		}catch (Exception e){

		}
	}
}
