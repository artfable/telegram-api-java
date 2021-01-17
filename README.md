# Telegram Bot Api [ ![Download](https://api.bintray.com/packages/artfable/telegram-tools/telegram-api/images/download.svg?version=0.6.1) ](https://bintray.com/artfable/telegram-tools/telegram-api/0.6.1/link)
Simple wrapper for using telegram api with java ([Telegram Api](https://core.telegram.org/bots/api)). Bot API version: 5.0

Requirements:
* Java 11

*Note! [Kotlin 1.4.21](http://kotlinlang.org/) used as well. However, you can don't care about it, if you don't want to modify.*

Works with [Spring Framework](https://spring.io/)

## Dependency

```kotlin
repositories {
    jcenter()
}

dependencies {
    implementation("org.artfable:telegram-api:0.6.1")
}
```

## Usage

For start create instance of LongPollingTelegramBot or WebhookTelegramBot.
Both types require set of Behaviours - services that handling updates and do logic of the bot.

### TelegramSender

Property **telegram.bot.token** should be provided for Spring context to configure the sender.

### LongPollingTelegramBot

Get updates by requesting them. All requests will be done through taskExecutor. 

### WebhookTelegramBot

Automatically setup a webhook (proper configuration should be provided). 
**_Note!_** You can set up webhook only for https and 443, 80, 88, 8443 ports! (requirement by Telegram)
 
For generating self-signed certificate see [Telegram Bot FAQ](https://core.telegram.org/bots/self-signed). 
**_Note!_** CN **must** be a domain name or ip address. 

### Behaviour

It represents actions that can be done by the bot in response to user actions. Will not process actions that were already handled by CallbackBehaviours.
However, all others actions will be handled by all of registered Behaviours. 

### CallbackBehaviour

It represents actions that can be done in response to click on the bot's buttons by user. All that behaviours should have base class AbstractCallbackBehaviour.

### Commands configuration

To set up bots commands, provide a json file with commands description to property _telegram.bot.commands_.

File example:

```json
[
  {
    "command": "start",
    "description": "Start bot process"
  }
]
``` 