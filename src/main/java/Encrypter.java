import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Encrypter implements Crypter {

    static int maximumHeight = 100;

    @Override
    public int carryOutCryption(String fileIn, String fileOut, OptionManager.Channel channel, String keyFile) {
        File file = FileHandler.getFile(fileIn);
        ArrayList<Character> listOfChars = FileHandler.readFileToCharArray(file);

        ArrayList<OptionManager.Channel> individualKeys = new ArrayList<>();
        if(keyFile != null && keyFile.length() > 0){
            File convertedKF = FileHandler.getFile(keyFile);
            individualKeys = FileHandler.getKeyValues(convertedKF);
        }

        int colCounter = 0;
        int rowCounter = 0;
        int lowRange = 32;
        int highRange = 127;
        boolean endBitAdded=false;

        int actualWidth = listOfChars.size() > maximumHeight ? maximumHeight : listOfChars.size();
        int initialHeight = listOfChars.size() / maximumHeight;
        int actualHeight = initialHeight + ((listOfChars.size() % maximumHeight) > 0 ? 1 : 0);

        Random rand = new Random();

        BufferedImage bfImg = new BufferedImage(actualWidth,actualHeight,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bfImg.createGraphics();
        for (int i = 0; i < listOfChars.size(); i++) {
            int val = (int)listOfChars.get(i);

            int randoA = rand.nextInt(highRange-lowRange) + lowRange; //0 in // num ex //32 - 126
            int randoB = rand.nextInt(highRange-lowRange) + lowRange; //0 in // num ex //32 - 126
            String a = Integer.toHexString(randoA);
            String b = Integer.toHexString(randoB);

            String valofchar = Integer.toHexString(val);
            if(valofchar.length() == 1){
                valofchar = "0"+valofchar;
            }

            if(individualKeys.size() > 0){
                if(individualKeys.get(i) == OptionManager.Channel.RED){
                    g2d.setColor(Color.decode("#"+valofchar+""+a+""+b));
                } else if(individualKeys.get(i) == OptionManager.Channel.GREEN){
                    g2d.setColor(Color.decode("#"+a+""+valofchar+""+b));
                } else if(individualKeys.get(i) == OptionManager.Channel.BLUE) {
                    g2d.setColor(Color.decode("#"+a+""+b+""+valofchar));
                } else {
                    g2d.setColor(Color.decode("#FFFFFF"));
                }
            } else {
                if(channel == OptionManager.Channel.RED){
                    g2d.setColor(Color.decode("#"+valofchar+""+a+""+b));
                } else if(channel == OptionManager.Channel.GREEN){
                    g2d.setColor(Color.decode("#"+a+""+valofchar+""+b));

                } else if(channel == OptionManager.Channel.BLUE) {
                    g2d.setColor(Color.decode("#"+a+""+b+""+valofchar));
                } else {
                    g2d.setColor(Color.decode("#FFFFFF"));
                }
            }


            g2d.fillRect(rowCounter,colCounter,1,1);
            rowCounter++;

            if(rowCounter > 99){
                rowCounter = 0;
                colCounter++;
            }

        }

        while(rowCounter != maximumHeight){
            int randoA = rand.nextInt(highRange-lowRange) + lowRange; //0 in // num ex //32 - 126
            int randoB = rand.nextInt(highRange-lowRange) + lowRange; //0 in // num ex //32 - 126
            int randoC = rand.nextInt(highRange-lowRange) + lowRange; //0 in // num ex //32 - 126
            String a = Integer.toHexString(randoA);
            String b = Integer.toHexString(randoB);
            String c = Integer.toHexString(randoC);
            if(!endBitAdded){
                if(channel == OptionManager.Channel.RED){
                    g2d.setColor(Color.decode("#00"+a+""+b));
                } else if(channel == OptionManager.Channel.GREEN){
                    g2d.setColor(Color.decode("#"+a+"00"+b));

                } else if(channel == OptionManager.Channel.BLUE) {
                    g2d.setColor(Color.decode("#"+a+""+b+"00"));
                } else {
                    g2d.setColor(Color.decode("#"+a+""+b+"00"));
                }
                g2d.fillRect(rowCounter,colCounter,1,1);
                endBitAdded = true;
                rowCounter++;
            } else {
                g2d.setColor(Color.decode("#"+a+""+b+""+c));
                g2d.fillRect(rowCounter,colCounter,1,1);
                rowCounter++;
            }

        }

        g2d.dispose();

        try{
            File filenew = new File(fileOut+".png");
            ImageIO.write(bfImg,"png",filenew);
        } catch(IOException e){
            System.out.println("Problem writing to files");
        }
        return 0;
    }
}
