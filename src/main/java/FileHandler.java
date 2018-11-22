import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    public static File getFile(String str){
        if (str.startsWith("~" + File.separator)) {
            str = System.getProperty("user.home") + str.substring(1);
        } else if (str.startsWith("~")) {
            throw new UnsupportedOperationException("Home dir expansion not implemented for explicit usernames");
        }
        return new File(str);
    }

    public static ArrayList<Character> readFileToCharArray(File file){
        ArrayList<Character> arrayOfLetters = new ArrayList<>();
        int line = 0;

        try {
            FileInputStream fin =  new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fin,"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.read()) != -1) {
                if(line == 10){
                    arrayOfLetters.add('\n');
                } else {
                    arrayOfLetters.add((char)line);
                }
            }
        } catch (Exception e){
            System.out.println("EXCEPTION: "+ e.getLocalizedMessage());
        }
        return arrayOfLetters;
    }

    public static void writeOutToFile(String toWrite, String fileName) throws Exception{
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter( new FileWriter(fileName));
            writer.write(toWrite);
        } catch ( IOException e) {
            throw e;
        } finally {
            try {
                if ( writer != null){
                    writer.close( );
                }
            } catch ( IOException e) {
                System.out.println("problem writing to and closing file");
            }
        }
    }

    public static ArrayList<OptionManager.Channel> getKeyValues(File file){
        String line = "";
        ArrayList<OptionManager.Channel> out = new ArrayList<>();
        try {
            FileInputStream fin =  new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fin,"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                String[] first = line.split(",");
                for (int i = 0; i < first.length; i++) {
                    if (first[i].equals("1")){
                        out.add(OptionManager.Channel.RED);
                    } else if(first[i].equals("2")){
                        out.add(OptionManager.Channel.GREEN);
                    } else if(first[i].equals("3")){
                        out.add(OptionManager.Channel.BLUE);
                    }
                }
            }
        } catch (Exception e){
            System.out.println("EXCEPTION getKeyValues: "+ e.getLocalizedMessage());
        }
        return out;
    }
}
