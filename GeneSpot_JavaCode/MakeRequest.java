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
 * Created on Nov 30, 2016
 * Author: austinward 
 *
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastAlignmentProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastOutputProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastService;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class MakeRequest {

	private final ResultstoFirebase sendToDB;

	public MakeRequest() {

		sendToDB = new ResultstoFirebase();
	}

	public void sendRequest(Job currentJob) {

		try {

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

				String hold = "";

				while (x < queries.size()) {
					
					// creates a new handler for the next query
					XMLHandler handler = new XMLHandler();
					XMLReader p = XMLReaderFactory.createXMLReader();
					p.setContentHandler(handler);

					try {

						rid = server.sendAlignmentRequest(queries.get(x), props);
						
						int time = 0;
						while (!server.isReady(rid)) {
							System.out.println("Waiting..."+ String.valueOf(time) + " secs");
							time+=5;
							Thread.sleep(5000);
						}
						
						time = 0;

						InputStream input = server.getAlignmentResults(rid, outputProps);
						reader = new BufferedReader(new InputStreamReader(input));

						String line;

						// writing to xml file
						while ((line = reader.readLine()) != null) {
							System.out.println(line);
							hold += (line + "\n");
						}
					}

					catch (Exception e) {
						e.printStackTrace();
					}

					try {

						p.parse(new InputSource(new ByteArrayInputStream(hold.getBytes("utf-8"))));
						sendToDB.SendtoGenespot(handler.getHits(), currentJob.getJobName(), currentGenome.getSpecies(),
								queries.get(x), currentJob.getGeneName(), currentGenome.getGenome(),
								currentGenome.getType());
						
						hold = "";

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					x++;
				}
			}

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
