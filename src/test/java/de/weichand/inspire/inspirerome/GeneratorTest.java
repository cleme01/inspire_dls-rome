package de.weichand.inspire.inspirerome;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.module.Module;
import com.rometools.modules.georss.GeoRSSModule;
import com.rometools.modules.georss.SimpleModuleImpl;
import com.rometools.modules.georss.geometries.Envelope;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.impl.Atom10Generator;
import de.weichand.inspire.inspirerome.types.SpatialDatasetIdentifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Unit test for ROME-Generator
 * 
 * @author Juergen Weichand <juergen@weichand.de>
 */
public class GeneratorTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GeneratorTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(GeneratorTest.class);
    }

    /**
     * Generator-Test (Simplified INSPIRE Service Feed)
     * @throws com.rometools.rome.io.FeedException
     * @throws java.io.IOException
     */
    public void testGenerator() throws FeedException, IOException {

        // Atom Container-Element
        Feed atomFeed = new Feed(); 
        atomFeed.setEncoding("utf-8");
        
        atomFeed.setTitle("Digitales Geländemodell 200m Bayern - INSPIRE Atom Example");                       
        atomFeed.setUpdated(new Date());
        
        // Atom Entry-Element
        Entry atomEntry = new Entry();
        atomEntry.setTitle("Digitales Geländemodell 200m Bayern - INSPIRE Atom Example");       
        atomEntry.setUpdated(new Date());
        
        // GeoRSS-Extension (Bounding-Box)        
        GeoRSSModule geoRssModule = new SimpleModuleImpl();
        geoRssModule.setGeometry(new Envelope(47.2279397510939845, 8.8934968721451053, 50.5798028875686470, 13.9247471058637764));
        
        // INSPIRE_DLS-Extension
        InspireDlsModule inspireDlsModule = new InspireDlsModuleImpl();
        SpatialDatasetIdentifier identifier = new SpatialDatasetIdentifier();
        identifier.setCode("DEBY_1d4ab890-27e7-3ebb-95ba-2d2ab8071871");
        identifier.setNamespace("http://www.geodaten.bayern.de");
        inspireDlsModule.setSpatialDatasetIdentifier(identifier);
        
        List<Module> modules = new ArrayList<Module>();
        modules.add(geoRssModule);
        modules.add(inspireDlsModule);
        atomEntry.setModules(modules);
              
        atomFeed.setEntries(Arrays.asList(atomEntry));
        
        // build JDOM-Document 
        Document atomXml = new Atom10Generator().generate(atomFeed);
        
        // print JDOM-Document to System.out
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(atomXml, System.out);                
    }
}
