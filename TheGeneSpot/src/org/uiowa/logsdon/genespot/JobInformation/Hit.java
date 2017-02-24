package JobInformation;

import NCBI.MakeRequest;

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

	public Hit(String accesionNumber, String hitFrom, String hitTo) {

		this.accesionNumber = accesionNumber;
		this.hitFrom = Integer.parseInt(hitFrom);
		this.hitTo = Integer.parseInt(hitTo);
	}


	public boolean compare(Hit newHit){

		if(this.getAccesionNumber().equals(newHit.getAccesionNumber())){

			return true;

		}

		else{

			return false;
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


	public int getExtendedHitFrom(){

		//500 is our default standard extension -- will be adjustable in the future

		if((this.hitFrom - 500) < 0) {

			return 0;
		}

		else{

			return (this.hitFrom - 500);
		}
	}

	public int getExtendedHitTo(){

		return (this.hitTo + 500);

	}

	public int getCenter(){

		int difference = this.hitTo + this.hitFrom;


		return Math.round(difference / 2);

	}

	public void setHitTo(int hitTo) {
		this.hitTo = hitTo;
	}

	public void setHitFrom(int hitFrom) {
		this.hitFrom = hitFrom;
	}
}
