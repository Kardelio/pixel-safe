import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Decrypter implements Crypter {
    @Override
    public int carryOutCryption(String fileIn, String fileOut, OptionManager.Channel channel, String keyFile) {
        File file = FileHandler.getFile(fileIn);

        ArrayList<OptionManager.Channel> individualKeys = new ArrayList<>();
        if(keyFile != null && keyFile.length() > 0){
            File convertedKF = FileHandler.getFile(keyFile);
            individualKeys = FileHandler.getKeyValues(convertedKF);
        }

        try{
            BufferedImage bfImg = ImageIO.read(file);

            int width = bfImg.getWidth();
            int height = bfImg.getHeight();
            String messageOut = "";
            int counter = 0;

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int clr=  bfImg.getRGB(j,i);
                    int  red   = (clr & 0x00ff0000) >> 16;
                    int  green = (clr & 0x0000ff00) >> 8;
                    int  blue  =  clr & 0x000000ff;

                    if(individualKeys.size() > 0) {
                        if(counter >= individualKeys.size()){
                            break;
                        }
                    } else {
                        if(red == 0 || blue == 0 || green == 0){
                            break;
                        }
                    }

                    if(individualKeys.size() > 0){
                        if(individualKeys.get(counter) == OptionManager.Channel.RED){
                            messageOut += (char) red;
                        } else if(individualKeys.get(counter) == OptionManager.Channel.GREEN){
                            messageOut += (char) green;
                        } else if(individualKeys.get(counter) == OptionManager.Channel.BLUE) {
                            messageOut += (char) blue;
                        } else {
                            messageOut += '.';
                        }
                    } else {
                        if (channel == OptionManager.Channel.RED) {
                            messageOut += (char) red;
                        } else if (channel == OptionManager.Channel.GREEN) {
                            messageOut += (char) green;
                        } else if (channel == OptionManager.Channel.BLUE) {
                            messageOut += (char) blue;
                        } else {
                            messageOut += '.';
                        }
                    }
                    counter++;
                }
            }
            FileHandler.writeOutToFile(messageOut,fileOut+".txt");
        } catch (Exception e){

        }
        return 0;
    }
}
