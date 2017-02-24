package JobInformation;

/**
 * Created by austinward on 2/11/17.
 */
public class ProteinQuery {

    private final String queryID;

    public ProteinQuery(String queryNumber){

        this.queryID = queryNumber;

    }

    public String getQueryID(){

        return this.queryID;
    }
}
