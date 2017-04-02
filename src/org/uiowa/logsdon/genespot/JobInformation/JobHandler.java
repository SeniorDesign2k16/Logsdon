package org.uiowa.logsdon.genespot.JobInformation;

import org.uiowa.logsdon.genespot.NCBI.MakeRequestNonBiojava;

import java.util.ArrayList;

/**
 * Created by austinward on 4/2/17.
 */
public class JobHandler {

    //job information will come in

    //create genome objects

    //number of genomes objects made is (gene count)*(genome count) -- we can update job database at this point -- create array

    //create job object - job name, evalue threshold (other parameters can be added later), and array of genomes

    //Future -- add job handling system - will implement next step

    //send job information to ncbi -- tblastn --> tblastx --> update firebase (gene database)

    //MakeRequestNonBiojava request = new MakeRequestNonBiojava();
    //request.makeRequestTBLASTN();

    private final MakeRequestNonBiojava request = new MakeRequestNonBiojava();

    public void addJob(Job newJob){

        ArrayList<Genome> genomes = newJob.getGenomesOfInterest();

        while (genomes.size() > 0){

            submitGenome(genomes.get(0));
            genomes.remove(0);

        }

    }


    public void submitGenome(Genome genome){

        request.makeRequestTBLASTN(genome);

        //update GeneDatabase

    }


}
