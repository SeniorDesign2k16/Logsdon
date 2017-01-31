import java.io.*;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

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
 * Created on Dec 7, 2016
 * Author: austinward 
 *
 */

public class XMLHandler extends DefaultHandler {

	// private Hit hits = new Hit();
	private String hold;
	private ArrayList<Hit> hits = new ArrayList<>();

	private Hit newHit;

	private boolean hit_Id_found = false;
	private boolean Hsp_query_from_Found = false;
	private boolean Hsp_query_to_Found = false;
	private boolean Hsp_hit_from_Found = false;
	private boolean Hsp_hit_to_Found = false;
	private boolean Hsp_hit_frame_Found = false;

	private final File file;

	public XMLHandler() throws FileNotFoundException {

		file = new File("output.txt");

	}

	@Override
	public void startDocument() {
		System.out.println("begin parsing document..");
	}

	@Override
	public void endDocument() {
		System.out.println("end parsing document..");
	}

	@Override
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) {

		switch (qName) {
		case "Hit_num":
			newHit = new Hit();
			break;
		case "Hit_id":
			hit_Id_found = true;
			break;
		case "Hsp_query-from":
			Hsp_query_from_Found = true;
			break;
		case "Hsp_query-to":
			Hsp_query_to_Found = true;
			break;
		case "Hsp_hit-from":
			Hsp_hit_from_Found = true;
			break;
		case "Hsp_hit-to":
			Hsp_hit_to_Found = true;
			break;
		case "Hsp_hit-frame":
			Hsp_hit_frame_Found = true;
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {

		hold = new String(ch, start, length);

		if (hit_Id_found) {
			System.out.println("Hit ID: " + hold);
			writeToFile("Hit ID: " + hold + "\n");
			newHit.addAccessionNumber(hold);
		}

		else if (Hsp_query_from_Found) {
			System.out.println("Query Start: " + hold);
			writeToFile("Query Start: " + hold+ "\n");
			newHit.addQueryFrom(hold);
		}

		else if (Hsp_query_to_Found) {
			System.out.println("Query End: " + hold);
			writeToFile("Query End: " + hold+ "\n");
			newHit.addQueryTo(hold);
		}

		else if (Hsp_hit_from_Found) {
			System.out.println("Hit Start: " + hold);
			writeToFile("Hit Start: " + hold + "\n");
			newHit.addHitFrom(hold);
		}

		else if (Hsp_hit_to_Found) {
			System.out.println("Hit End: " + hold);
			writeToFile("Hit End: " + hold+ "\n");
			newHit.addHitEnd(hold);
		}

		else if (Hsp_hit_frame_Found) {
			System.out.println("Frame: " + hold);
			writeToFile("Frame: " + hold+ "\n");
			newHit.addHitFrame(hold);
		}

	}

	@Override
	public void endElement(String nameSpaceURI, String localName, String qName) {
		// System.out.println("</" + qName + ">");

		switch (localName) {

		case "Hit_id":
			hit_Id_found = false;
			break;
		case "Hsp_query-from":
			Hsp_query_from_Found = false;
			break;
		case "Hsp_query-to":
			Hsp_query_to_Found = false;
			break;
		case "Hsp_hit-from":
			Hsp_hit_from_Found = false;
			break;
		case "Hsp_hit-to":
			Hsp_hit_to_Found = false;
			break;
		case "Hsp_hit-frame":
			Hsp_hit_frame_Found = false;
			break;
		case "Hit_num":
			hits.add(newHit);
			break;
		}
	}

	public ArrayList<Hit> getHits() {

		return hits;
	}

	private void writeToFile(String data){

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

			writer.write(data);
			writer.flush();


		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
