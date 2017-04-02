package org.uiowa.logsdon.genespot.JobInformation;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;

/*
 *
 * Created on Dec 7, 2016
 * Author: austinward
 *
 */
public class Hit{

	private final String accesionNumber;
	private int hitTo;
	private int hitFrom;

	private String sequenceTitle;
	private String sequence;
	private int[] scoreSequence;

	//constructor
	public Hit(String accesionNumber, String hitFrom, String hitTo) {

		this.accesionNumber = accesionNumber;
		this.hitFrom = Integer.parseInt(hitFrom);
		this.hitTo = Integer.parseInt(hitTo);
	}

	private boolean compare(Hit newHit) {

		if (this.getAccesionNumber().equals(newHit.getAccesionNumber())) {

			return true;

		}

		else {

			return false;
		}
	}

	//hits comes from the gene object
	public void addHit(ArrayList<Hit> hits){

		if(hits.isEmpty()){

			hits.add(this);

			return;

		}

		else{

			for(Hit currentHit : hits){

				//on same contig if true -- now looking at overlaps between other hits
				if(compare(currentHit)){

					//if this has overlap with the current hit and extends to the left
					if(this.hitFrom < currentHit.hitFrom && this.hitTo >= currentHit.hitFrom){

						currentHit.setHitFrom(this.hitFrom);

						if(this.hitTo > currentHit.hitTo) {

							currentHit.setHitTo(this.hitTo);

						}

						return;
					}

					//else if this has overlap with the current hit and extends to the right
					else if(this.hitFrom <= currentHit.hitTo && this.hitTo > currentHit.hitTo){

						currentHit.setHitTo(this.hitTo);

						if(this.hitFrom < currentHit.hitFrom){

							currentHit.setHitFrom(this.hitFrom);
						}

						return;
					}

					//hit is contained within a prior hit
					else if(this.getHitFrom() >= currentHit.getHitFrom() && this.getHitTo() <= currentHit.getHitTo()){

						return;

					}

				}

			}

		}

		//if no hits were updated or found to update
		hits.add(this);
	}

	public int lengthOfGene() {

		return hitFrom - hitTo;

	}

	// getters and setters
	public String getAccesionNumber() {
		return accesionNumber;
	}

	public int getHitTo() {
		return hitTo;
	}

	public int getHitFrom() {
		return hitFrom;
	}

	public String getSequence() { return sequence; }

	public void setHitTo(int hitTo) {
		this.hitTo = hitTo;
	}

	public void setHitFrom(int hitFrom) {
		this.hitFrom = hitFrom;
	}

	public void setSequence(String sequence) {

		this.sequence = sequence;
		this.scoreSequence = new int[sequence.length()];
		System.out.println(this.scoreSequence.length);
	}

	public String getSequenceTitle() {
		return sequenceTitle;
	}

	public void setSequenceTitle(String sequenceTitle) {
		this.sequenceTitle = sequenceTitle;

	}

	public void score(String start, String end, String sequence){

		/*
		System.out.println("tblastn start: " + this.getHitFrom());
		System.out.println("tblastn end: " + this.getHitTo());

		System.out.println("blastx start: " + start);
		System.out.println("blastx start: " + end);

		*/

		int from = Integer.parseInt(start);
		int to = Integer.parseInt(end);

		//System.out.println(to - from);
		//System.out.println(sequence.length() * 3);
		//System.out.println(sequence);

		from = from - this.getHitFrom() - 1; //since the index of arraylist starts at 10;

		if(from < 0){

			from = 0;
		}

		//ever position in sequenceMidLine is 3 amino acids in the nucleotide
		int counter = 0;
		int codon = 0;
		int score = 0;

		char[] chars = sequence.toCharArray();

		while(counter < chars.length){

			if(chars[counter] == '-'){

				score = 0;

			}

			else{

				score = 1;
			}

			codon = 0;

			while(codon < 3){


				scoreSequence[from]+=score;
				from++;
				codon++;
			}

			counter++;
		}
	}
}
