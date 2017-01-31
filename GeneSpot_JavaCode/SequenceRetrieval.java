import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.RNASequence;
import org.biojava.nbio.core.sequence.Strand;
import org.biojava.nbio.core.sequence.transcription.Frame;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by austinward on 1/2/17.
 */



public class SequenceRetrieval {


    private String scaffold_sequence = "CCGAACTGATACTCGCCAGCGCCTCACCGCGAAAGCCCAGGCTGATAATGGCTTCGAGATCGTCCAGAGA" +
            "GGCGATTTTACTGGTGGCATGACGAGCCAGCGCCAGCGCCAGCTCATCTTTTTTGATACCGCAGCCGTTA" +
            "TCACGAATGCGGATAAGTTTCGCCCCACCGCGTTCGATATCAA";

    private String new_Sequence;

    private int hit_Start = 5;
    private int hit_End = 178;
    private int frame = -3;

    private ArrayList<Hit> hits = new ArrayList<>();

    public SequenceRetrieval(){

        readInputFile("sampleTest.txt");

        RNASequence rnaSequence = getFrameShiftedSequence(scaffold_sequence, frame);
        new_Sequence = rnaSequence.toString();

        new_Sequence = getHitSequence(new_Sequence, hit_Start, hit_End);

        System.out.println(scaffold_sequence.length());
        System.out.println(new_Sequence.length());
        System.out.println(hit_End - hit_Start);
    }

    private RNASequence getFrameShiftedSequence(String sequence, int frame){

        RNASequence rnaSequence;

        try {
            DNASequence dnaSequence = new DNASequence(sequence);
            switch (frame){

                case 1:
                    rnaSequence = dnaSequence.getRNASequence(Frame.ONE);
                    break;
                case 2:
                    rnaSequence = dnaSequence.getRNASequence(Frame.TWO);
                    break;
                case 3:
                    rnaSequence = dnaSequence.getRNASequence(Frame.THREE);
                    break;
                case -1:
                    rnaSequence = dnaSequence.getRNASequence(Frame.REVERSED_ONE);
                    break;
                case -2:
                    rnaSequence = dnaSequence.getRNASequence(Frame.REVERSED_TWO);
                    break;
                case -3:
                    rnaSequence = dnaSequence.getRNASequence(Frame.REVERSED_THREE);
                    break;
                default:
                    return rnaSequence = null;
            }

        } catch (CompoundNotFoundException e) {
            e.printStackTrace();
        }

        finally {

            return rnaSequence = null;

        }
    }

    private String getHitSequence(String sequence, int hit_Start, int hit_End) {


        //making offset for array indexing
        hit_Start = hit_Start - 1;
        hit_End = hit_End - 1;

        return sequence.substring(hit_Start, hit_End);

    }

    private void writeToFile(String hitID, String geneName, String species, String kingdom){

    }

    private void readInputFile(String fileName){

        File file = new File(fileName);

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = "";

            String sequence_hold = new String();
            ArrayList<String> sequences = new ArrayList<>();

            boolean buffer = true; //takes care of first case
            boolean found = false; //true when new sequence header is found

            try {

                while ((line = reader.readLine()) != null) {

                    //ignoring spaces
                    if(!line.equals("")){

                        //start of new sequence
                        if (line.charAt(0) == '>') {

                            found = true;
                        }

                        //adding to sequence
                        else{

                            sequence_hold+=line.split("\n");
                        }

                        if(found && !buffer) {

                            //System.out.println(sequence_hold);
                            sequences.add(sequence_hold);

                            sequence_hold = null;

                            found = false;
                        }
                    }



                    buffer = false;
                }

                System.out.println(sequence_hold);

                sequences.add(sequence_hold.toString()); //takes care of last case

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
