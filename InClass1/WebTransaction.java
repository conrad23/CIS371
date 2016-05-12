import java.io.*;
import java.net.*;

/**
 * AUTHOR: Conner Toney
 * CIS 371 - In-Class 1
 */
public class WebTransaction {
    public static void main(String[] args) {
        String host = "www.cis.gvsu.edu";
        int port = 80;
        String file = "/~kurmasz/Humor/stupid.html";

        // (1) Create a socket connecting to the host and port set up above.
        try {
            Socket socket = new Socket(host, port);
        // (2) Get the InputStream from the socket and "wrap it up"
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            DataInputStream in = new DataInputStream(bis);
        // (3) Get the OutputStream from the socket and "wrap it up"
            PrintWriter out = new PrintWriter(socket.getOutputStream());

        // (4) Send the GET request and the other request headers
            String request = "GET " + file + " HTTP/1.1\n";
            request += "host: " + host;
            request += "\n\n";
            out.print(request);
            out.flush();

        // (5) Read data from the socket until you get a blank line.
        //     Write each line you receive to System.out
            String line;
            while ((line = in.readLine()) != null)
                System.out.println(line);

        // (6) Create a FileOutputStream object.
            FileOutputStream fos = new FileOutputStream("webTransaction.html");
        // (7) Read the rest of the data from the socket and write it to a file using
        //     the FileOutputStream you just created.
        //     (Hint:  readLine() returns null when there is no more data to read.
            fos.write(in.read());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end main
}
