import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class KeyGenerator {

    public KeyGenerator(){

    }

    public static int generateKeyFile(String fileIn, String fileOut){

        File file = FileHandler.getFile(fileIn);
        ArrayList<Character> listOfChars = FileHandler.readFileToCharArray(file);

        Random rand = new Random();

        String outSequence = "";

        for (int i = 0; i < listOfChars.size(); i++) {
            int r = rand.nextInt(4-1)+1;  //1 to 3  //1R 2G 3B
            if(i == (listOfChars.size() - 1)){
                outSequence += r;
            } else {
                outSequence += r+",";
            }
        }

        try {
            FileHandler.writeOutToFile(outSequence,fileOut+".key");
        } catch (Exception e){

        }

        return 0;
    }

}
