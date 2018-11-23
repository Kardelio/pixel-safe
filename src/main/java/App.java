import javax.net.ssl.KeyManager;

public class App {
    public static void main(String[] args) {
        int outCode;
        OptionManager optMan = new OptionManager(args);

        if(optMan.allOptionsOk){
            Crypter crypt = null;
            KeyGenerator km = null;
            if(optMan.selectedMode == OptionManager.Mode.ENCRYPT){
                crypt = new Encrypter();
            } else if(optMan.selectedMode == OptionManager.Mode.DECRYPT){
                crypt = new Decrypter();
            } else if(optMan.selectedMode == OptionManager.Mode.GENERATE){
                km = new KeyGenerator();
            }

            if(crypt != null){
                System.out.println("Performing Cryption type selected...");
                outCode = crypt.carryOutCryption(optMan.selectedFileNameIn, optMan.selectedFileNameOut,optMan.selectedChannel, optMan.selectedKeyFile);
            } else if(km != null) {
                System.out.println("Performing Key Generation...");
                outCode = KeyGenerator.generateKeyFile(optMan.selectedFileNameIn,optMan.selectedFileNameOut);
            } else {
                System.out.println("Mode could not detect accurate route");
                outCode = 1;
            }
        } else {
            System.out.println("Option Manager reported a problem");
            System.out.println(optMan.problemMessage);
            outCode = 1;
        }
        System.exit(outCode);
    }
}
