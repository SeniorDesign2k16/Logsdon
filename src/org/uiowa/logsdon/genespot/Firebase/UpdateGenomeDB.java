package org.uiowa.logsdon.genespot.Firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.uiowa.logsdon.genespot.NCBI.ResultstoFirebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by austinward on 4/25/17.
 */

/*
Database Structure:

    Kingdom
        Type
            Level
                taxID
                    Assembly #
                        Species Name = ""


File Structure:

    SPECIES[0]
    KINGDOM[1]
    TYPE[2]
    ASSEMBLY[3]
    LEVEL[5]
    ID[6]
 */

public class UpdateGenomeDB {

    //this will take in the path to the file containing the NCBI eukarytotic gene informaiton2
    public static void main(String[] args){

        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("thegenespot-efb8a-firebase-adminsdk-1phn3-cbe3ab49a4.json");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl("https://thegenespot-efb8a.firebaseio.com/").build();

        FirebaseApp.initializeApp(options);

        ///Users/austinward/TheGeneSpot_git/Logsdon/src/org/uiowa/logsdon/genespot/Firebase/genomes_euks-taxids-4-3-17.txt
        File file = new File("src/org/uiowa/logsdon/genespot/Firebase/genomes_euks-taxids-4-3-17.txt");

        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()){
                String currentLine = scanner.nextLine();
                String[] lineArray = currentLine.split("\t");
                String species = lineArray[0];
                String kingdom = lineArray[1];
                String type = lineArray[2];
                String assembly = lineArray[3].replace('.', 'v');
                String level = lineArray[5];
                String id = lineArray[6];
                DatabaseReference Genespot = FirebaseDatabase.getInstance().getReference();
                Genespot.child(kingdom).child(type).child(level).child(id).child(assembly).child("Species").setValue(species);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }


}
