package JobInformation;
/*

 *
 * Created on Dec 2, 2016
 * Update on Feb 15, 2017
 * Author: austinward 
 *
 */

import java.util.ArrayList;

public class Gene {

	private final String geneName;
	private final ProteinQuery[] proteinQueries;
	private ArrayList<Hit> hits = new ArrayList<>();



	public Gene(String geneName, ProteinQuery[] proteinQueries) {

		this.geneName = geneName;
		this.proteinQueries = proteinQueries;

	}

	//creates a list of hits that occured on the same contig. If there isn't any, we add it to the hits
	public void compareHits(Hit hit){

		ArrayList<Hit> similarHits = new ArrayList<>();   //hits on the same contig as the new hit

		for(Hit currentHit : hits){

			//grabbing all hits on the same contig
			if(currentHit.compare(hit)) {

				similarHits.add(currentHit);
			}
		}

		//no hits on the same contig found
		if(similarHits.isEmpty()){

			System.out.println("Here3");
			addHit(hit);
		}

		//compare hits and update if necessary
		else{

			update(hit, similarHits);
		}
	}

	//will update the hit if the range is widened, or create a new array
	private void update(Hit newHit, ArrayList<Hit> similarHits){

		int newHitLeft = newHit.getHitFrom();
		int newHitCenter = newHit.getCenter();
		int newHitRight = newHit.getHitTo();


		for(Hit currentHit : similarHits) {

			//if the center of the new hit is located to the left of the leftmost current hit
			if(newHitCenter < currentHit.getHitFrom()){

				//if there's overlap
				if(newHitRight >= currentHit.getHitFrom()){

					//set new hit starting position
					currentHit.setHitFrom(newHitLeft);
					return;
				}
			}

			//if the center of the new hit is located between the left and rightmost position of the current hit
			else if(newHitCenter >= currentHit.getHitFrom() && newHitCenter <= currentHit.getHitTo()){

				//checking left
				if(newHitLeft < currentHit.getHitFrom()){

					currentHit.setHitFrom(newHitLeft);
				}

				//checking right
				if(newHitRight > currentHit.getHitTo()){

					currentHit.setHitTo(newHitRight);
				}
				return;
			}


			//if the center of the new his located right of rightmost position of the current hit
			else if(newHitCenter > currentHit.getHitTo()){

				if(newHitLeft <= currentHit.getHitFrom()){

					currentHit.setHitTo(newHitRight);

					return;
				}
			}
		}

		//if no updates were made, a unique hit was found
		addHit(newHit);
		System.out.println("Here2");
	}

	public String getName() {

		return geneName;
	}

	public ProteinQuery[] getProteinQueries(){

		return this.proteinQueries;
	}

	public void addHit(Hit hit){

		this.hits.add(hit);
	}

	public Hit[] getHits(){

		return hits.toArray(new Hit[0]);

	}
}
