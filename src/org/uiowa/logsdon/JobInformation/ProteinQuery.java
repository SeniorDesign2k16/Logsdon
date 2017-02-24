package org.uiowa.logsdon.JobInformation;

import java.util.ArrayList;

/**
 * Created by austinward on 2/11/17.
 */
public class ProteinQuery {

    private final String queryNumber;

    private Hit[] hits; // should be the unique hits from all the queries used

    public ProteinQuery(String queryNumber){

        this.queryNumber = queryNumber;

    }

    public void setHits(Hit[] hits){

        this.hits = hits;
    }

    public String getQueryNumber(){

        return this.queryNumber;
    }


    public Hit[] getHitObjects(){

        return this.hits;
    }
}
