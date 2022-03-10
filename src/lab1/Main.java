package lab1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

    public static String inFilePath;
    public static String outFilePath;
    static DocumentBuilder builder;
    static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


    public static void main(String[] args) {
        inFilePath = getFilePath("inFile");
        outFilePath = getFilePath("outFile");
        int len = getMinFlightLength();
        File inXmlFile = new File(inFilePath);

        try {
            FlightHandler flightHandler = new FlightHandler();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inXmlFile, flightHandler);
            List<Flight> collect = flightHandler.getSchedule().getFlights().stream().filter(x -> x.getLength() < len)
                    .sorted(Comparator.comparingInt(Flight::getFlightTime).reversed()).limit(3).collect(Collectors.toList());
            saveResult(collect);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void saveResult(List<Flight> collect) throws ParserConfigurationException, TransformerException {
        builder = factory.newDocumentBuilder();
        Document outXml = builder.newDocument();
        Element root = outXml.createElement("LongestFlights");
        outXml.appendChild(root);
        for (Flight flight : collect) {
            Element fl = outXml.createElement("Flight");

            Element number = outXml.createElement("number");
            number.appendChild(outXml.createTextNode(String.valueOf(flight.getNumber())));
            fl.appendChild(number);

            Element target = outXml.createElement("target");
            target.appendChild(outXml.createTextNode(String.valueOf(flight.getTarget())));
            fl.appendChild(target);

            Element length = outXml.createElement("length");
            length.appendChild(outXml.createTextNode(String.valueOf(flight.getLength())));
            fl.appendChild(length);

            Element price = outXml.createElement("price");
            price.appendChild(outXml.createTextNode(String.valueOf(flight.getPrice())));
            fl.appendChild(price);

            Element departureHours = outXml.createElement("hours");
            departureHours.appendChild(outXml.createTextNode(String.valueOf(flight.getDeparture().getHours())));
            Element departureMins = outXml.createElement("mins");
            departureMins.appendChild(outXml.createTextNode(String.valueOf(flight.getDeparture().getMins())));
            Element departure = outXml.createElement("departure");
            departure.appendChild(departureHours);
            departure.appendChild(departureMins);
            fl.appendChild(departure);

            Element arrivalHours = outXml.createElement("hours");
            arrivalHours.appendChild(outXml.createTextNode(String.valueOf(flight.getArrival().getHours())));
            Element arrivalMins = outXml.createElement("mins");
            arrivalMins.appendChild(outXml.createTextNode(String.valueOf(flight.getArrival().getMins())));
            Element arrival = outXml.createElement("arrival");
            arrival.appendChild(arrivalHours);
            arrival.appendChild(arrivalMins);
            fl.appendChild(arrival);

            root.appendChild(fl);
        }

        StreamResult file = new StreamResult(new File(outFilePath));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(outXml);

        transformer.transform(source, file);
    }

    private static int getMinFlightLength() {
        System.out.println("Enter max length:");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static String getFilePath(String fileName) {
        System.out.println("Please, enter the path to " + fileName);
        Scanner sc = new Scanner(System.in);
        String path = null;
        File f;
        do {
            if (path != null) {
                System.out.println("Wrong path. Repeat.");
            }
            path = sc.nextLine();
            f = new File(path);
        } while (!f.exists());
        return path;
    }
}
