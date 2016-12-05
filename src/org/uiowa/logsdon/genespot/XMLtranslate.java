package org.uiowa.logsdon.genespot;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLtranslate {
	private static Results results;
	private static String jobIdEncrypted="13312";
	static Document doc;
public static String xmlBuild()
{
try {
	DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuild = docFact.newDocumentBuilder();
	 doc = docBuild.newDocument();
	Element rootElement =doc.createElement("genespot-results");
	doc.appendChild(rootElement);
	rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
	//Job Id
	Element jobId = doc.createElement("Job-Id");
	doc.getDocumentElement().appendChild(jobId);
	jobId.setAttribute("jobId", jobIdEncrypted);
	//Dummy variable name
	addGeneLists(results);
	
} catch (ParserConfigurationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return getStringfromDoc();
}
private static String getStringfromDoc() {
	String xmlString = null;
    try
    {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
        xmlString = result.getWriter().toString();
    }
    catch(Exception e)
    {
    }
    return xmlString;
}
private static void addGeneLists(Results[] results)
{
	for(int i = 0; i< results.length;i++)
	{
		Element resultNode = doc.createElement("Cell "+i);
		Element geneElement = doc.createElement("gene-name");
		Element organismElement = doc.createElement("organism");
		Attr binaryAttri = doc.createAttribute("present");
		geneElement.appendChild(doc.createTextNode(results[i].getGene()));
		organismElement.appendChild(doc.createTextNode(results[i].getOrganism()));
		binaryAttri.setValue(results[i].getResult());
		resultNode.setAttributeNode(binaryAttri);
		resultNode.appendChild(geneElement);
		resultNode.appendChild(organismElement);
		doc.getDocumentElement().appendChild(resultNode);
	}
}



}
