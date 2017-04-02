package org.uiowa.logsdon.genespot.NCBI;
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
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastAlignmentProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastOutputProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastService;
import org.uiowa.logsdon.genespot.JobInformation.Gene;
import org.uiowa.logsdon.genespot.JobInformation.Genome;
import org.uiowa.logsdon.genespot.JobInformation.Hit;
import org.uiowa.logsdon.genespot.JobInformation.Job;
import org.uiowa.logsdon.genespot.JobInformation.ProteinQuery;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class MakeRequest {

	private ResultstoFirebase sendToDB; // uses results from NCBI to update database

	// constructor
	public MakeRequest() {

		this.sendToDB = new ResultstoFirebase();
	}

	// takes in job object and makes request to NCBI
	public void sendRequest(Job currentJob) throws UnsupportedEncodingException, FileNotFoundException {

		NCBIQBlastService server = new NCBIQBlastService(); // setting connection to ncbi's server
		NCBIQBlastAlignmentProperties intputProperties = new NCBIQBlastAlignmentProperties();
		NCBIQBlastOutputProperties outputProperties = new NCBIQBlastOutputProperties();

		intputProperties.setBlastProgram(BlastProgramEnum.tblastn); // setting blast type
		intputProperties.setBlastExpect(currentJob.getEvalue()); // setting client specified e-value

		// getting the genomes of interest;
		Genome[] genomes = currentJob.getGenomesOfInterest();

		for (Genome currentGenome : genomes) {

			intputProperties.setBlastDatabase("genomic/" + currentGenome.getTaxID() + "/" + currentGenome.getGenome());

			Gene[] genes = currentGenome.getGenesOfInterest();

			for (Gene currentGene : genes) {

				ProteinQuery[] proteinQueries = currentGene.getProteinQueries();

				for (ProteinQuery currentQuery : proteinQueries) {

					try {

						XMLHandler handler = new XMLHandler(currentGene);
						XMLReader parse = XMLReaderFactory.createXMLReader();

						String rid = server.sendAlignmentRequest(currentQuery.getQueryID(), intputProperties);

						int time = 0;

						// waiting on alignment
						while (!server.isReady(rid)) {
							System.out.println("Waiting..." + String.valueOf(time) + "secs");

							Thread.sleep(5000);
							time += 5;
						}

						// getting alignment data
						InputStream input = server.getAlignmentResults(rid, outputProperties);

						// reading alignment data
						BufferedReader reader = new BufferedReader(new InputStreamReader(input));

						String line;
						String hold = "";

						while ((line = reader.readLine()) != null) {
							// System.out.println(line);
							hold += (line + "\n");
						}

						System.out.println(hold);

						parse.setContentHandler(handler);
						parse.parse(new InputSource(new ByteArrayInputStream(hold.getBytes("utf-8"))));

					} catch (SAXException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				Hit[] hits = currentGene.getHits();

				System.out.println("-------------------------------------");
				System.out.println("Genome: " + currentGenome.getGenome());
				System.out.println("Tax ID: " + currentGenome.getTaxID());
				System.out.println("Kingdom: " + currentGenome.getKingdom());
				System.out.println("-------------------------------------");

				for (Hit currentHit : hits) {

					System.out.println("Gene: " + currentGene.getName());
					System.out.println("Acession Number: " + currentHit.getAccesionNumber());
					System.out.println("Hit From: " + currentHit.getHitFrom());
					System.out.println("Hit To: " + currentHit.getHitTo());
				}

				//SequenceRetrieval sequenceRetrieval = new SequenceRetrieval();
				//sequenceRetrieval.getSequence(currentGene.getHits());


				//blastx
			}
		}
	}
}
