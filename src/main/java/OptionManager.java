import org.apache.commons.cli.*;

public class OptionManager {

    static String MODE_ENCRYPT_STRING = "encrypt";
    static String MODE_ENCRYPT_STRING_SHORT = "e";
    static String MODE_DECRYPT_STRING = "decrypt";
    static String MODE_DECRYPT_STRING_SHORT = "d";
    static String MODE_GENERATE_STRING = "generate";
    static String MODE_GENERATE_STRING_SHORT = "g";
    static String CHANNEL_R_STRING = "R";
    static String CHANNEL_G_STRING = "G";
    static String CHANNEL_B_STRING = "B";

    boolean allOptionsOk = false;
    Mode selectedMode = null;
    Channel selectedChannel = null;
    String selectedFileNameIn = null;
    String selectedFileNameOut = null;
    String selectedKeyFile = null;

    public enum Mode {
        ENCRYPT, DECRYPT, GENERATE
    }

    public enum Channel{
        RED, GREEN, BLUE, NOPE
    }

    public OptionManager(String[] args){
        Options optionsHelp= new Options();
        optionsHelp.addRequiredOption("h","help", false , "Help for this program");


        Options options = new Options();
        options.addRequiredOption("m", "mode", true, "The mode to run the application (encrypt or decrypt or generate)")
                .addRequiredOption("f", "file", true, "The file to decrypt from or encrypt to depending on the mode")
                .addRequiredOption("o","fileOut", true , "The file name to create")
                .addOption("k","key",true,"JSON File containing the channel pattern to encrypt with")
                .addOption("c", "channel", true, "Channel to encrypt into or read from (R, G or B)");

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(optionsHelp, args);
            if(cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("ImageEncrypter", options);
                return;
            }
        } catch (Exception e){
            System.out.println("HELP PARSE EXCEPTION: "+e.getMessage());
            allOptionsOk = false;
        }

        try{
            CommandLine cmd = parser.parse(options, args);

            String mode = cmd.getOptionValue("m");
            if(mode.equals(MODE_DECRYPT_STRING) || mode.equals(MODE_DECRYPT_STRING_SHORT)){
                selectedMode = Mode.DECRYPT;
            } else if(mode.equals(MODE_ENCRYPT_STRING) || mode.equals(MODE_ENCRYPT_STRING_SHORT)){
                selectedMode = Mode.ENCRYPT;
            } else if(mode.equals(MODE_GENERATE_STRING) || mode.equals(MODE_GENERATE_STRING_SHORT)){
                selectedMode = Mode.GENERATE;
            }else {
                allOptionsOk = false;
                return;
            }

            selectedFileNameIn = cmd.getOptionValue("f");
            if(selectedFileNameIn == null){
                allOptionsOk = false;
                return;
            }

            selectedFileNameOut = cmd.getOptionValue("o");
            if(selectedFileNameOut == null){
                allOptionsOk = false;
                return;
            }

            String channelFirst = cmd.getOptionValue("c");
            String displayChannel = "";
            if(channelFirst != null){
                if(channelFirst.equals(CHANNEL_R_STRING)){
                    selectedChannel = Channel.RED;
                } else if(channelFirst.equals(CHANNEL_G_STRING)){
                    selectedChannel = Channel.GREEN;
                } else if(channelFirst.equals(CHANNEL_B_STRING)) {
                    selectedChannel = Channel.BLUE;
                }
                displayChannel = ", Channel to use: "+selectedChannel;
            } else {
                selectedChannel = Channel.NOPE;
            }

            selectedKeyFile = cmd.getOptionValue("k");
            String displayKey = "";
            if(selectedKeyFile != null){
                displayKey = ", Using Key File: "+selectedKeyFile;
            }

            System.out.println("===> Mode: "+mode+" and File in: "+selectedFileNameIn+" and File out: "+ selectedFileNameOut+""+displayChannel+""+displayKey);
        allOptionsOk = true;
        } catch (Exception e){
            System.out.println("ERROR: "+e.getMessage());
            allOptionsOk = false;
        }
    }

}
