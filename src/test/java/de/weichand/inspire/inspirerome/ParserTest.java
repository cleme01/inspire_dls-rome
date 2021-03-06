package de.weichand.inspire.inspirerome;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.impl.Atom10Parser;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Unit test for ROME-Parser
 * 
 * @author Juergen Weichand <juergen@weichand.de>
 */
public class ParserTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ParserTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ParserTest.class);
    }

    /**
     * Parser-Test (INSPIRE Service Feed)
     * @throws java.io.IOException
     * @throws org.jdom2.JDOMException
     * @throws com.rometools.rome.io.FeedException
     */
    public void testGenerator() throws IOException, JDOMException, IllegalArgumentException, FeedException {

        final String file = "src/test/resources/dgm200.xml";        
        final Document xml = new SAXBuilder().build(new File(file));
        
        // parse Atom 
        Feed atomFeed = (Feed) new Atom10Parser().parse(xml, true, Locale.GERMAN);
        
        // get first Entry-Element
        Entry atomEntry = (Entry) atomFeed.getEntries().get(0);
        
        // get INSPIRE_DLS-Module
        InspireDlsModule inspireDlsModule = (InspireDlsModuleImpl) atomEntry.getModule(InspireDlsModule.URI);        
        assertTrue(inspireDlsModule.getSpatialDatasetIdentifier().getCode().equals("DEBY_1d4ab890-27e7-3ebb-95ba-2d2ab8071871"));
        assertTrue(inspireDlsModule.getSpatialDatasetIdentifier().getNamespace().equals("http://www.geodaten.bayern.de"));        
        System.out.println(inspireDlsModule.getSpatialDatasetIdentifier());                
    }
}
