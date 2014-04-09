package com.recursivepenguin.fisstitcher;

import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {

    static String imageServer = "https://www.bna.no/erez4/erez";

    public static void main(String[] args) {
        String source = ScrapeSource.scrape("https://www.blomqvist.no/image/view/qaGoq5adpg==");
        System.out.println(source);

        try {
            URIBuilder builder = new URIBuilder(imageServer);
            builder.addParameter("src", source);
            builder.addParameter("cmd", "view");
            builder.addParameter("vtl", "fsi/info.xml");
            builder.addParameter("tmp", "fsi");
            URI uri = builder.build();

            Document document = Jsoup.parse(new URL(uri.toString()).openStream(), null, "", Parser.xmlParser());
            String widthString = document.select("Width").first().attr("value");
            String heightString = document.select("Height").first().attr("value");

            System.out.println(String.format("width: %s, height: %s" , widthString, heightString));

            double width = Double.parseDouble(widthString);
            double height = Double.parseDouble(heightString);

            double widthLeft = width;
            double heightLeft = height;

            double numCols = Math.ceil(width / 2000.0);
            double numRows = Math.ceil(height / 2000.0);

            for (int y = 0; y < numRows; y++) {
                widthLeft = width;
                for (int x = 0; x < numCols; x++) {
                    int tileWidth = 2000;
                    if (widthLeft < tileWidth) {
                        tileWidth = (int) widthLeft;
                    }

                    int tileHeight = 2000;
                    if (heightLeft < tileHeight) {
                        tileHeight = (int) heightLeft;
                    }

                    double left = x * (2000 / width);
                    double right = x * (2000 / width) + (2000 / width);
                    if (right > 1)
                        right = 1;
                    double top = y * (2000 / height);
                    double bottom = y * (2000 / height) + (2000 / height);
                    if (bottom > 1)
                        bottom = 1;

                    String tileUrl = generateTileUrl(source, tileWidth, tileHeight, left, top, right, bottom);

                    widthLeft -= 2000;

                    System.out.println(tileUrl);
                }
                heightLeft -= 2000;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static String generateTileUrl(String source, int width, int height, double left, double top, double right, double bottom) {
        try {
            URIBuilder builder = new URIBuilder(imageServer);
            builder.addParameter("src", source);
            builder.addParameter("width", "" + width);
            builder.addParameter("height", "" + height);
            builder.addParameter("left", "" + left);
            builder.addParameter("top", "" + top);
            builder.addParameter("right", "" + right);
            builder.addParameter("bottom", "" + bottom);
            builder.addParameter("tmp", "fsi");
            builder.addParameter("scale", "1");
            URI uri = builder.build();
            return uri.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

}
