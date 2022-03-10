package lab1;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class FlightHandler extends DefaultHandler {
    private static final String FLIGHTS = "flights";
    private static final String FLIGHT = "flight";
    private static final String NUMBER = "number";
    private static final String TARGET = "target";
    private static final String LENGTH = "length";
    private static final String PRICE = "price";
    private static final String DEPARTURE = "departure";
    private static final String ARRIVAL = "arrival";
    private static final String HOURS = "hours";
    private static final String MINS = "mins";


    private Schedule schedule;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() {
        schedule = new Schedule();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) {
        switch (qName) {
            case FLIGHTS:
                schedule.flights = new ArrayList<>();
                break;
            case FLIGHT:
                schedule.flights.add(new Flight());
                break;
            case NUMBER:
            case PRICE:
            case TARGET:
            case LENGTH:
            case HOURS:
            case MINS:
                elementValue = new StringBuilder();
                break;
            case DEPARTURE:
                latestFlight().setDeparture(new Time());
                break;
            case ARRIVAL:
                latestFlight().setArrival(new Time());
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case HOURS:
                if (latestFlight().getArrival() == null) {
                    latestFlight().getDeparture().setHours(Integer.parseInt(elementValue.toString()));
                } else {
                    latestFlight().getArrival().setHours(Integer.parseInt(elementValue.toString()));
                }
                break;
            case MINS:
                if (latestFlight().getArrival() == null) {
                    latestFlight().getDeparture().setMins(Integer.parseInt(elementValue.toString()));
                } else {
                    latestFlight().getArrival().setMins(Integer.parseInt(elementValue.toString()));
                }
                break;
            case NUMBER:
                latestFlight().setNumber(Integer.parseInt(elementValue.toString()));
                break;
            case PRICE:
                latestFlight().setPrice(Integer.parseInt(elementValue.toString()));
                break;
            case TARGET:
                latestFlight().setTarget(elementValue.toString());
                break;
            case LENGTH:
                latestFlight().setLength(Integer.parseInt(elementValue.toString()));
                break;
        }
    }

    private Flight latestFlight() {
        List<Flight> flightList = schedule.flights;
        int latestFlightIndex = flightList.size() - 1;
        return flightList.get(latestFlightIndex);
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
