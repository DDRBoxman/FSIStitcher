package com.recursivepenguin.fisstitcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ScrapeSource {

    public static String scrape(String originUrl) {
        try {
            Document doc = Jsoup.connect(originUrl).get();
            Elements elements = doc.select("#fsiviewer param");
            String sourceUrl = elements.first().attr("value");
            String[] data = sourceUrl.split("=");
            return data[data.length-1];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
