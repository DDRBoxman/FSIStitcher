FIS Stitcher
============

Fetch image links that need to be stitched together to get the full size image from a FSI viewer that has downloading disabled.

Usage
-----
1.  Change the imageServer string to the server that FSI content is on.
2.  Change the string passed into ScrapeSource.scrape() to the url of the page with the viewer on it.
3.  ```./gradlew runSimple```

TODO
----
Auto-download and stitch images together.

