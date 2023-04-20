package workshop04;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    //List to store all cookies.
    List<String> cookieList = null;

    //Function to read in cookie file.
    public void readCookieFile(String fileName) throws IOException {
        //ArrayList to store file info.
        cookieList = new ArrayList<String>();

        //Instantiate FileReader and BufferedReader.
        File newFile = new File(fileName);
        FileReader fr = new FileReader(newFile);
        BufferedReader br = new BufferedReader(fr);
        String line = "";

        //while loop to read each line and add into ArrayList.
        while ((line = br.readLine()) != null) {
            cookieList.add(line);
        }

        br.close();
        fr.close();
    }

    //Function to get a random cookie info.
    public String getRandomCookie() {
        Random rand = new Random();
        int randIndex = rand.nextInt(cookieList.size());
        String cookieString = cookieList.get(randIndex);
        return cookieString;
    }
}
