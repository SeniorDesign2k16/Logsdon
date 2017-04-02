package org.uiowa.logsdon.genespot.NCBI;

/**
 * Created by austinward on 3/20/17.
 */
public class test {

    public static void main(String args[]){

        //job information will come in

        //create genome objects

        //number of genomes objects made is (gene count)*(genome count) -- we can update job database at this point -- create array

        //create job object - job name, evalue threshold (other parameters can be added later), and array of genomes

        //Future -- add job handling system - will implement next step

        //send job information to ncbi -- tblastn --> tblastx --> update firebase (gene database)

        MakeRequestNonBiojava request = new MakeRequestNonBiojava();
        //request.makeRequestTBLASTN();

    }
}
