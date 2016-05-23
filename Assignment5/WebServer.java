import java.io.*;
import java.net.*;

/**
 * AUTHOR: CONNER TONEY
 * CIS 371 - Assignment 5
 */
public class WebServer {
    public static void main(String[] args) throws IOException {

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
        String[] parsedGet = firstLine.split(" ");
        String path = parsedGet[1];
        System.out.println(firstLine);

        //read all request headers, print to standard output
        String line;
        while ((line = input.readLine()) != null) {
            if (line.trim().isEmpty())
                break;
            System.out.println(line);
        }

        //open the requested file
        File requestedFile = new File(path);
        FileInputStream fis = new FileInputStream("C:\\Users\\Fallen\\IdeaProjects\\CIS371_Assignment5" + requestedFile);
        long length = fis.getChannel().size();
        DataInputStream dis = new DataInputStream(fis);

        //print the required response headers
        output.println("HTTP/1.1 200 OK");
        if (path.contains(("jpg")))
            output.println("Content-Type: image/jpg");
        if (path.contains(("html")))
            output.println("Content-Type: text/html");
        if (path.contains(("png")))
            output.println("Content-Type: image/png");
        else
            output.println("Content-Type: text/plain");
        output.println("Content-Length: " + length);
        output.println("Connection: close");
        output.println();

        //use a loop to read a block of data from file then write the data to the socket
        byte[] bytes = new byte[1024];
        int count;
        while ((count = dis.read(bytes)) > 0) {
            output.write(bytes, 0, count);
        }

        //close sockets
        clientSocket.close();
        serverSocket.close();
        System.out.println("-------------------------");
        System.out.println("Connection closed.");

    }
}
