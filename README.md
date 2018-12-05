<div style="text-align: center;">
    <img src="media/pixelSafeLogo.png" width="200px" title="Self created logo of Pixel-Safe">
    <p>Pixel-Safe is a file encrypter/decrypter that takes the contents of a file and converts it into an image and back again.</p>
    <p>Interested? Head over to the <a href="#what-is-it">What is it</a> section...</p>
</div>



Table of contents
=================
- [What is it?](#what-is-it)
- [Requirements](#requirements)
- [How to build](#how-to-build)
- [How to run](#how-to-run)
    - [Command Line Java](#command-line-java)
    - [Gradle Run Task](#gradle-run-task)
- [Arguements](#arguements)
    - [Simple encryption](#simple-encryption)
    - [Simple decryption](#simple-decryption)
    - [Generating and using a key](#generating-and-using-a-key)

## What is it?
-----
Pixel-Safe is a small console based application written in Java. It uses Gradle as it's build tool and has only 1 (currently) external dependency in Apache Commons CLI. It takes any text based file and encrypts the text character by character into an image. The data from the file can be encrypted into a specific single colour channel of the image (R,G or B) or instead a variety of channels using a generated key file that the application can also generate.

## Requirements
-----
The application is built into a JAR file so therefore requires Java to run the completed built JAR and as stated the JAR file is built using Gradle, so if you want to contribute to the code and build the JAR yourself you will need Gradle.

## How to build
-----
If you wish to build the JAR file there is a gradle task that has been created that will take care of everything.
```bash
gradle superBuild
# OR ./gradlew superBuild
```
The superBuild task will package all the dependencies together and create a fatJar and then copy the JAR into the /example folder in the root of the project. This JAR can then be used.

## How to run
-----
The application can be run by either by calling the JAR file directly from the command line or through a gradle task (run)...

### Command Line Java
Once you have a built JAR file you can ofcourse use Java via the command line and pass the JAR file as a -jar arguement. Then pass your specified arguements...
```bash
java -jar pixel-safe-{version}.jar -m e -f lorem -o out -c R
```

### Gradle Run Task
Alternatively you can run the un-built application through gradle.
This is an example of the gradle run task that also allows you to pass in the command line arguements to the application... 
```bash
gradle run -PappArgs="['-m', 'e', '-f', 'lorem', '-o', 'out', '-c', 'R']"
```

## Arguements
-----
If you use the -h arguement the application will provide you back with relatively extensive infomation about all the command options available to the application.

Currently these are the available arguements...

 #### -c,--channel [arg]   
 Channel to encrypt into or read from [R, G or B], this arguement is not needed when using or generating a key file using mode g.

 #### -f,--file [arg]      
 The file to decrypt from or encrypt to (create) depending on the mode

 #### -h,--help            
 Help for this program

 #### -k,--key [arg]       
 JSON File containing the channel pattern to encrypt with.

 #### -m,--mode [arg]      
 The mode to run the application [encrypt/e, decrypt/d or generate/g]

 #### -o,--fileOut [arg]   
 The file name to create (You dont need to add the file extension at the end, the application does it for you)

 ## Examples
 -----

### Simple encryption
The following example would run the application in the ENCRYPT MODE (-m e), meaning that the application will output an image. The application receives the FILE IN (-f) called lorem and spits the FILE OUT (-o) called out which will called out.png and the data will be encrypted into the RED CHANNEL (-c R). This is important when it comes to decrypting the image later...
 ```bash
 java -jar pixel-safe-{version}.jar -m e -f lorem -o out -c R
 ```
### Simple decryption
 The following example would run the application in the DECRYPT MODE (-m d), meaning that the application will output a file containing the original text that was encrypted. The application receives the FILE IN (-f) called image.png and spits the FILE OUT (-o) called result and the COLOUR CHANNEL (-c G) will be inspected in the image for the data.
 ```bash
 java -jar pixel-safe-{version}.jar -m d -f image.png -o result -c G
 ```

### Generating and using a key
 The following example would run the application in the GENERATE MODE (-m g), meaning that the application will output a key file containing the random combination of channels that the data will be encrypted into in the image. The application receives the FILE IN (-f) called lorem, this will allow the application to create a specific key file tailored for that file. The application spits the FILE OUT (-o) called key.key, this is the key file that contains the random assortment of channels that can be used to encrypt the file with...
 ```bash
 java -jar pixel-safe-{version}.jar -m g -f lorem -o key.key
 ```
 Once you have created the key file you can use it like this, instead of specifying a single channel...
 ```bash
 java -jar pixel-safe-{version}.jar -m e -f lorem -o image -k key.key
 ```
 The key file is the only way to decrypt this image properly and can be used in the following way...
 ```bash
 java -jar pixel-safe-{version}.jar -m d -f image.png -o result -k key.key 
 ```