package com.example.termproject2023;

import java.io.Serializable;

public class Event implements Serializable {

    String title, guid, description, time, location, img_url, author, host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", guid='" + guid + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
    /*/
    <item>
  <title>Movie: The Equalizer 3</title>
  <guid>https://ninerengage.charlotte.edu/event/9596115</guid>
  <link>https://ninerengage.charlotte.edu/event/9596115</link>
  <enclosure url="https://se-images.campuslabs.com/clink/images/1f2d6a57-0cb6-497b-911b-da76b4518e83f18382da-dfbe-4317-b924-c1e6b2ffa434.png?preset=med-w" length="1" type="image/jpeg" />
  <description><![CDATA[<div class="h-event vevent">
  <div class="p-name summary">Movie: The Equalizer 3</div>
  <div class="p-description description">
  <p>Robert McCall finds himself at home in Southern Italy but he discovers his friends are under the control of local crime bosses. As events turn deadly, McCall knows what he has to do: become his friends' protector by taking on the mafia.</p>
  </div>
  <div>
    <p>
      From <time class="dt-start dtstart" datetime="2023-11-19T22:00:00.0000000-05:00" title="2023-11-19T22:00:00.0000000-05:00">Sunday, November 19, 2023 10:00 PM</time>
      to <time class="dt-end dtend" datetime="2023-11-20T00:00:00.0000000-05:00" title="2023-11-20T00:00:00.0000000-05:00">Monday, November 20, 2023 12:00 AM EST</time>
      at <span class="p-location location">Popp Martin Student Union</span>.
    </p>
  </div>
</div>]]></description>
  <category>Social</category>
  <pubDate>Mon, 20 Nov 2023 02:18:48 GMT</pubDate>
  <start xmlns="events">Mon, 20 Nov 2023 03:00:00 GMT</start>
  <end xmlns="events">Mon, 20 Nov 2023 05:00:00 GMT</end>
  <location xmlns="events">Popp Martin Student Union</location>
  <status xmlns="events">confirmed</status>
  <host xmlns="events">Popp Martin Student Union</host>
</item>
     */