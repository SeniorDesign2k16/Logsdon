package org.uiowa.logsdon.genespot.JobInformation;

import org.uiowa.logsdon.genespot.NCBI.GeneDatabase;
import org.uiowa.logsdon.genespot.NCBI.MakeRequestNonBiojava;

import java.io.FileNotFoundException;
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

    private final MakeRequestNonBiojava request = new MakeRequestNonBiojava();
    private final GeneDatabase geneDatabase = new GeneDatabase();

    //can take one job at a time - eventually need to a add a multithread so new jobs can be added to a queue
    public void addJob(Job newJob){

        System.out.println("Here");
        ArrayList<Genome> genomes = newJob.getGenomesOfInterest();

        while (genomes.size() > 0){

            submitGenome(genomes.get(0));
            Genome currentGenome = genomes.remove(0);

            //update gene database database
            if(currentGenome != null){

                try {
                    geneDatabase.update(newJob.getJobName(), currentGenome);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void submitGenome(Genome genome){

        System.out.println("Here2");
        System.out.println(genome.getGenome());
        request.makeRequestTBLASTN(genome);

        //update GeneDatabase

    }
}
