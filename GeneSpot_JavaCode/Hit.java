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
 * Created on Dec 7, 2016
 * Author: austinward 
 *
 */

public class Hit {

	private ArrayList<String> accessionNumber = new ArrayList<>();
	private ArrayList<String> queryFrom = new ArrayList<>();
	private ArrayList<String> queryTo = new ArrayList<>();
	private ArrayList<String> hitFrom = new ArrayList<>();
	private ArrayList<String> hitTo = new ArrayList<>();
	private ArrayList<String> hitFrame = new ArrayList<>();

	public ArrayList<String> getAccessionNumber() {
		return accessionNumber;
	}

	public ArrayList<String> getQueryFrom() {
		return queryFrom;
	}

	public ArrayList<String> getQueryTo() {
		return queryTo;
	}

	public ArrayList<String> getHitFrom() {
		return hitFrom;
	}

	public ArrayList<String> getHitTo() {
		return hitTo;
	}

	public ArrayList<String> getHitFrame() {
		return hitFrame;
	}

	public void addAccessionNumber(String accession) {

		accessionNumber.add(accession);
	}

	public void addQueryFrom(String queryStart) {

		queryFrom.add(queryStart);
	}

	public void addQueryTo(String queryEnd) {

		queryTo.add(queryEnd);
	}

	public void addHitFrom(String hitStart) {

		hitFrom.add(hitStart);
	}

	public void addHitEnd(String hitEnd) {

		hitTo.add(hitEnd);
	}

	public void addHitFrame(String frame) {

		hitFrame.add(frame);
	}

}
