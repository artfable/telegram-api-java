# Telegram Bot Api [ ![Artifactory](https://img.shields.io/badge/Artifactory-v1.0.1-green) ](https://artfable.jfrog.io/ui/packages/gav:%2F%2Fcom.artfable.telegram:telegram-api)
A simple wrapper for using telegram api with java ([Telegram Api](https://core.telegram.org/bots/api)). Bot API version: 5.0

Requirements:
* Java 11

*Note! [Kotlin 1.5.21](http://kotlinlang.org/) used as well. However, you can don't care about it, if you don't want to modify.*

## Dependency

```kotlin
repositories {
    maven(url = "https://artfable.jfrog.io/artifactory/default-maven-local")
}

dependencies {
    implementation("com.artfable.telegram:telegram-api:1.0.1")
}
```

## Usage

For start create instance of LongPollingTelegramBot or WebhookTelegramBot.
Both types require set of Behaviours - services that handling updates and do logic of the bot.

For Spring use [starter](https://gitlab.com/artfable/telegram-api-spring-starter)

### TelegramSender

Should be implemented sending logic. URL template provided in the interface.  

### LongPollingTelegramBot

Get updates by requesting them. All requests will be done through taskExecutor. 

### WebhookTelegramBot

Setup a webhook (proper configuration should be provided). 
**_Note!_** You can set up webhook only for https and 443, 80, 88, 8443 ports! (requirement by Telegram)
 
For generating self-signed certificate see [Telegram Bot FAQ](https://core.telegram.org/bots/self-signed). 
**_Note!_** CN **must** be a domain name or ip address. 

### Behaviour

It represents actions that can be done by the bot in response to user actions. Will not process actions that were already handled by CallbackBehaviours.
However, all others actions will be handled by all of registered Behaviours. 

### CallbackBehaviour

It represents actions that can be done in response to click on the bot's buttons by user. All that behaviours should have base class AbstractCallbackBehaviour.

### Commands configuration

To set up bots commands, provide an inputStream with json with commands description.

Example:

```json
[
  {
    "command": "start",
    "description": "Start bot process"
  }
]
``` 