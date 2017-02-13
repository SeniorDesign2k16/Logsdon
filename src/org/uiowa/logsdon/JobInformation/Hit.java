package org.uiowa.logsdon.JobInformation;

/*
 *
 * Created on Dec 7, 2016
 * Author: austinward
 *
 */
public class Hit {

	private final String accesionNumber;
	private int hitTo;
	private int hitFrom;
	private final int hitFrame;

	public Hit(String accesionNumber, String hitTo, String hitFrom, String hitFrame) {

		this.accesionNumber = accesionNumber;
		this.hitFrom = Integer.parseInt(hitFrom);
		this.hitTo = Integer.parseInt(hitTo);
		this.hitFrame = Integer.parseInt(hitFrame);

	}


	private boolean compare(String accesionNumber, int hitFrame){

		if(this.accesionNumber.equals(accesionNumber) && this.hitFrame == hitFrame){

			return true;

		}

		return false;

	}

	public boolean update(String accesionNumber, String hitTo, String hitFrom, String hitFrame){

		int hitFrameInt = Integer.parseInt(hitFrame);
		int hitToInt = Integer.parseInt(hitTo);
		int hitFromInt = Integer.parseInt(hitFrom);

		//need to keep looking for another hit
		if(!this.compare(accesionNumber, hitFrameInt)){

			return false;
		}

		//otherwise, trying and update
		else{

			if(hitToInt < this.hitTo){

				this.hitTo = hitToInt;
			}

			if(hitFromInt > this.hitFrom){

				this.hitFrom = hitFromInt;
			}

			return true; //telling the program that the object was updated and to stop searching
		}
	}

	public int lengthOfGene(){

		return hitFrom - hitTo;

	}

	//getters and setters
	public String getAccesionNumber() {
		return accesionNumber;
	}

	public int getHitTo() {
		return hitTo;
	}

	public int getHitFrom() {
		return hitFrom;
	}

	public int getHitFrame() {
		return hitFrame;
	}
}
