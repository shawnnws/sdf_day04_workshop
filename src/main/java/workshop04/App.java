package workshop04;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        //Argument 1 for the cookie file, argument 2 for the port the server will start on
        String fileName = args[0];
        String port = args[1];

        File cookieFile = new File(fileName);
        if (!cookieFile.exists()) {
            System.out.println("Cookie file not found");
            System.exit(0);
        }

        //Establish server connection
        ServerSocket server = new ServerSocket(Integer.parseInt(port));
        Socket socket = server.accept();

        /*
         * Method 1: Nested try, with while loop inside
         * Method 2: while loop with separate try inside
         * Difference: For method 2, will need to open close streams multiple times through each try;
         * Whereas for method 1, will only need to do once.
         */

        //Allow server to read and write over the communcation channel
        try (InputStream is = socket.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            
            //Store the data sent from the client into a variable
            String clientMessage = "";

            try (OutputStream os = socket.getOutputStream()) {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);
                
                //Logic to receive and send from cookie file - while loop
                while (!clientMessage.equals("close")) {

                    //Read message from client
                    clientMessage = dis.readUTF();

                    //Check for specific message from client
                    if (clientMessage.equals("get-cookie")) {
                        //Instantiate cookie.java to get a random cookie and send back information 
                        //using DataOutputStream (dos.writeUTF(___________))
                        Cookie newCookie = new Cookie();
                        newCookie.readCookieFile(fileName);
                        String cookieString = newCookie.getRandomCookie();
                        dos.writeUTF("cookie-text: " + cookieString);
                        dos.flush();
                    }
                }
                //Close all output stream in reverse order
                dos.close();
                bos.close();
                os.close();
                }
            catch (EOFException ex) {
                ex.printStackTrace();
            }
            //Close all input stream in reverse order
            dis.close();
            bis.close();
            is.close();
        }
        catch (EOFException ex) {
            socket.close();
            server.close();
        }
    }
}
