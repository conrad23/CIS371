import java.io.*;
import java.net.*;

/**
 * AUTHOR: CONNER TONEY
 * CIS 371 - Assignment 5
 */
public class DynamicWebServer {
    public static void main(String[] args) throws IOException {

        //TODO: split query string by ?, split by &
        //TODO: read in filename, parameter for script
        //TODO: read in script output, write to website
        //create server socket, accept connection, set up input and output
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server now listening for connections...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Connection accepted...");
        System.out.println("-------------------------");

        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        PrintStream output = new PrintStream(clientSocket.getOutputStream(), true);

        //parse GET line
        String firstLine = input.readLine();
        System.out.println(firstLine);
        String[] parsedGet = firstLine.split(" ");

        //split GET request into file path and queries
        String request = parsedGet[1];
        String[] querySplit = request.split("\\?");
        String path = querySplit[0];
        path = path.substring(1, path.length());
        String parameters = querySplit[1];

        //get parameters
        String[] paramSplit = parameters.split("&");
        String p1 = paramSplit[0];
        String p2 = paramSplit[1];
        String[] p1Split = p1.split("=");
        String[] p2Split = p2.split("=");
        int parameter1 = Integer.parseInt(p1Split[1]);
        int parameter2 = Integer.parseInt(p2Split[1]);

        //read all request headers, print to standard output
        String line;
        while ((line = input.readLine()) != null) {
            if (line.trim().isEmpty())
                break;
            System.out.println(line);
        }

        //open the requested file
        Python python = new Python();
        String toPrint = python.getHTML(path, parameter1, parameter2);
        System.out.print("TOPRINT: " + toPrint);

        //print the required response headers
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html");
        output.println("Content-Length: " + toPrint.toString().length());
        output.println("Connection: close");
        output.println();
        output.print(toPrint);



        //close sockets
        clientSocket.close();
        serverSocket.close();
        System.out.println("-------------------------");
        System.out.println("Connection closed.");

    }
}
