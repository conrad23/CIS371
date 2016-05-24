/**
 * AUTHOR: CONNER TONEY
 * CIS 371 - Assignment 4
 * Represents a URL
 */

public class MyURL {

    private String scheme = "http";
    private String domainName = null;
    private int port = 80;
    private String path = "/";

    /**
     * Split {@code url} into the various components of a URL
     *
     * @param url the {@code String} to parse
     */
    public MyURL(String url) {

        // TODO:  Split the url into scheme, domainName, port, and path.
        // Only the domainName is required.  Default values given above.
        // See the test file for examples of correct and incorrect behavior.
        // Hints:  (1) My implementation is mostly calls to String.indexOf and String.substring.
        // (2) indexOf can take a String as a parameter (it need not be a single character).

        if (url.contains("://")) {
            handleURLWithScheme(url);
            if (path.endsWith("/") && path.length() > 1)
                path = path.substring(0, path.length()-1);

        } else {
            String[] sectionsOfURL = url.split("/");

            domainName = sectionsOfURL[0];
            if (domainName.contains(":")) {
                String[] sectionsOfDomain = domainName.split(":");
                domainName = sectionsOfDomain[0];
                port = Integer.parseInt(sectionsOfDomain[1]);
            }
            for (int i = 1; i < sectionsOfURL.length; i++) {
                if (!sectionsOfURL[i].contains(".")) {
                    path += sectionsOfURL[i] + "/";
                } else
                    path += sectionsOfURL[i];
            }
            if (path.endsWith("/") && path.length() > 1)
                path = path.substring(0, path.length()-1);
        }

        if (url.isEmpty())
            throw new RuntimeException();
        if (scheme.equals("http") && domainName.isEmpty() && path.equals("/"))
            throw new RuntimeException();
        if ((domainName.isEmpty()) && path.equals("/"))
            throw new RuntimeException();
    }

    /**
     * If {@code newURL} has a scheme (e.g., begins with "http://", "ftp://", etc), then parse {@code newURL}
     * and ignore {@code currentURL}.  If {@code newURL} does not have a scheme, then assume it is intended
     * to be a relative link and replace the file component of {@code currentURL}'s path with {@code newURL}.
     *
     * @param newURL     a {@code String} representing the new URL.
     * @param currentURL the current URL
     */
    public MyURL(String newURL, MyURL currentURL) {

        // TODO: If newURL has a scheme, then take the same action as the other constructor.
        // If newURL does not have a scheme
        // (1) Make a copy of currentURL
        // (2) Replace the filename (i.e., the last segment of the path) with the relative link.
        // See the test file for examples of correct and incorrect behavior.
        // Hint:  Consider using String.lastIndexOf
        if (newURL.contains("://")) {
            handleURLWithScheme(newURL);


        } else {
            MyURL copyOfCurrentURL = currentURL;
            int i = copyOfCurrentURL.toString().lastIndexOf("/");
            System.out.println(i);
            String shortenedURL = copyOfCurrentURL.toString().substring(0, i);
            //String newFileComponent = newURL.substring(i);
            handleURLWithScheme(shortenedURL+"/"+newURL);


        }
    }


    public String scheme() {
        return scheme;
    }

    public String domainName() {
        return domainName;
    }

    public int port() {
        return port;
    }

    public String path() {
        return path;
    }

    /**
     * Format this URL as a {@code String}
     *
     * @return this URL formatted as a string.
     */
    public String toString() {
        // TODO:  Format this URL as a string
        //return String.format("");
        StringBuffer str = new StringBuffer();
        str.append(scheme + "://");
        str.append(domainName + ":" + port);
        str.append(path);
        return str.toString();
    }

    public void handleURLWithScheme(String url) {
        String[] sectionsOfURL = url.split("/");

        scheme = sectionsOfURL[0];
        scheme = scheme.substring(0, scheme.length()-1);

        domainName = sectionsOfURL[2];
        if (domainName.contains(":")) {
            String[] sectionsOfDomain = domainName.split(":");
            domainName = sectionsOfDomain[0];
            port = Integer.parseInt(sectionsOfDomain[1]);
        }

        for (int i = 3; i < sectionsOfURL.length; i++) {
            if (!sectionsOfURL[i].contains(".")) {
                path += sectionsOfURL[i] + "/";
            }
            else
                path += sectionsOfURL[i];
        }
    }

    // Needed in order to use MyURL as a key to a HashMap
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    // Needed in order to use MyURL as a key to a HashMap
    @Override
    public boolean equals(Object other) {
        if (other instanceof MyURL) {
            MyURL otherURL = (MyURL) other;
            return this.scheme.equals(otherURL.scheme) &&
                    this.domainName.equals(otherURL.domainName) &&
                    this.port == otherURL.port() &&
                    this.path.equals(otherURL.path);
        } else {
            return false;
        }
    }

//    public static void main(String[] args) {
//        MyURL u = new MyURL("www.cis.gvsu.edu/dir1/dir2/file.html");
//    }
} // end class