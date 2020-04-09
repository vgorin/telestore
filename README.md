# Telestore
Telegram based E-commerce platform with Crypto support by OpenChat Systems
 
## Building and Running
* Prerequisites:
  * Java Runtime Environment 8 (JRE 1.8) – get it at [oracle.com](https://www.oracle.com/java/technologies/javase-jre8-downloads.html)
  * Telegram Bot – created via [BotFather](https://t.me/botfather)
    * telegram.bot.username - telegram bot username (without "@") – if, for example, your bot username is ```@username_bot``` then ```username_bot``` is your telegram.bot.username
    * telegram.bot.api_token – telegram bot API token
* Building:  
Unix: ```./mvnw package -Dtelegram.bot.username=<required_value> -Dtelegram.bot.api_token=<required_value>```   
Windows: ```mvnw package -Dtelegram.bot.username=<required_value> -Dtelegram.bot.api_token=<required_value>```
* Running:
```java -Dtelegram.bot.username=<required_value> -Dtelegram.bot.api_token=<required_value> -jar target/telestore-0.0.1-SNAPSHOT.jar```
* Troubleshooting:
  * Verify JRE version (1.8): ```java --version```
  * Ensure projects builds - locate ```target/telestore-0.0.1-SNAPSHOT.jar``` file and check it's accessible for reading
  * Check port 8080 is not in use
