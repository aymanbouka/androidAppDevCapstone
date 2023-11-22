package com.example.termproject2023;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class EventsXMLParser extends DefaultHandler {
    Event event;
    ArrayList<Event> events = new ArrayList<>();
    final String TAG = "demo";

    StringBuilder buffer = new StringBuilder();

    public ArrayList<Event> parse(InputStream inputStream) throws IOException, SAXException {
        //code to initiate the parse
        Xml.parse(inputStream, Xml.Encoding.UTF_8, this);

        return events;
    }


    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        events = new ArrayList<>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    /*
    <item>
  <title>Movie: The Equalizer 3</title>
  <guid>https://ninerengage.charlotte.edu/event/9596115</guid>
  <link>https://ninerengage.charlotte.edu/event/9596115</link>
  <enclosure url="https://se-images.campuslabs.com/clink/images/1f2d6a57-0cb6-497b-911b-da76b4518e83f18382da-dfbe-4317-b924-c1e6b2ffa434.png?preset=med-w" length="1" type="image/jpeg" />
  <category>Social</category>
  <pubDate>Mon, 20 Nov 2023 03:43:35 GMT</pubDate>
  <start xmlns="events">Mon, 20 Nov 2023 03:00:00 GMT</start>
  <end xmlns="events">Mon, 20 Nov 2023 05:00:00 GMT</end>
  <location xmlns="events">Popp Martin Student Union</location>
  <status xmlns="events">confirmed</status>
  <host xmlns="events">Popp Martin Student Union</host>
</item>
     */

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);



        if (localName.equals("item")) {
            event = new Event();
        } else {
            if(event != null){
                if (localName.equals("enclosure")) {
                    event.setImg_url(attributes.getValue("url"));
                }
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (event != null){
            if (localName.equals("item")) {
                events.add(event);
                event = null;
            } else if (localName.equals("title")) {
                event.setTitle(buffer.toString());
            } else if (localName.equals("guid")) {
                event.setGuid(buffer.toString());
            } else if (localName.equals("location")) {
                event.setLocation(buffer.toString());
            } else if (localName.equals("start")) {
                event.setTime(buffer.toString());
            } else if (localName.equals("host")) {
                event.setHost(buffer.toString());
            } else if (localName.equals("description")) {
                event.setDescription(buffer.toString());
            }
        }

        buffer.setLength(0);

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        buffer.append(ch, start, length);
    }
}


