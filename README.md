# Serverless Telegram Audio Transcription
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![ChatGPT](https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white)

The project aims to show how you can use Kotlin, AWS, Serverless, and various AI models to build a telegram bot with audio transcription functionality

## Prerequisites
To start building your bot, you need to follow these steps
1. Install serverless and serverless offline plugin [official installation guide](https://www.serverless.com/framework/docs-getting-started)
2. Create a bot with [@BotFather](https://core.telegram.org/bots#botfather) and get an API Token
3. Create a .env.dev file in the project root to set up the following environment variables
 ```
TELEGRAM_TOKEN=*******:***************
OPEN_AI_TOKEN=**************
DEEPGRAM_TOKEN=*************
 ```

## Build
`./gradlew build` build bot

To test the bot on your machine you can use `sls offline`.
The plugin requires java8, before using it you'll need to set java JDK8 as it recommended doing here: [sls offline jdk17 support](https://github.com/dherault/serverless-offline/issues/1710)

## Deployment to AWS Lambda
1. Create a free tier AWS account

2. Install and set up AWS CLI

3. Run the `./gradlew deploy` to deploy the Telegram Bot to AWS Lambda

4. Connect AWS Lambda to your bot using the Telegram webHook
    ```
    curl --request POST \
    --url https://api.telegram.org/YOUR_TELEGRAM_BOT_ID:YOUR_TELEGRAM_BOT_TOKEN/setWebhook \
    --header 'content-type: application/json' \
    --data '{"url": "https://YOUR_AWS_ENDPOINT.execute-api.eu-west-1.amazonaws.com/dev/stt"}'
    ```
    Now your bot is linked to your aws lambda

5. Send an audio message to the bot and wait for a response with audio transcription


# License
```
Copyright (c) 2024 Transcriber bot project and open source contributors.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```