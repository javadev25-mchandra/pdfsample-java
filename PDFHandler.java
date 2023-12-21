package com.optum.pdfdemo;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.fop.apps.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;


public class PDFHandler {
    public static final String EXTENSION = ".pdf";
    public String PRESCRIPTION_URL = "template.xsl";

    public String createPDFFile(ByteArrayOutputStream xmlSource, String templateFilePath) throws IOException {
        File file = File.createTempFile("" + System.currentTimeMillis(), EXTENSION);
        URL url = new File(templateFilePath + PRESCRIPTION_URL).toURI().toURL();
        // creation of transform source
        StreamSource transformSource = new StreamSource(url.openStream());
        // create an instance of fop factory

        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI()); // my add
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // to store output
        ByteArrayOutputStream pdfoutStream = new ByteArrayOutputStream();
        StreamSource source = new StreamSource(new ByteArrayInputStream(xmlSource.toByteArray()));
        Transformer xslfoTransformer;
        try {
            TransformerFactory transFact = TransformerFactory.newInstance();

            xslfoTransformer = transFact.newTransformer(transformSource);
            // Construct fop with desired output format
            Fop fop;
            try {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, pdfoutStream);
                // Resulting SAX events (the generated FO)
                // must be piped through to FOP
                Result res = new SAXResult(fop.getDefaultHandler());

                // Start XSLT transformation and FOP processing
                try {
                    // everything will happen here.
                    xslfoTransformer.transform(source, res);

                    // to save PDF file use below code
                    OutputStream outStr = new java.io.FileOutputStream(file);
                    outStr = new java.io.BufferedOutputStream(outStr);
                    FileOutputStream str = new FileOutputStream(file);
                    str.write(pdfoutStream.toByteArray());
                    str.close();
                    outStr.close();

                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            } catch (FOPException e) {
                e.printStackTrace();
            }
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        }
        return file.getPath();
    }

    public ByteArrayOutputStream getXMLSource(EmployeeData data) throws Exception {
        JAXBContext context;

        ByteArrayOutputStream arrOutStream = new ByteArrayOutputStream();

        try {
            context = JAXBContext.newInstance(com.optum.pdfdemo.EmployeeData.class);
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(data, arrOutStream);
        } catch (JAXBException e) {

            e.printStackTrace();
        }
        return arrOutStream;
    }

}