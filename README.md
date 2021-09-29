Engage Digital Android Sample Application
==========

This Android Studio Project is a Sample App showing how to use the [Engage Digital](https://www.ringcentral.com/digital-customer-engagement.html) Messaging SDK.

	Engage Digital provides a mobile messaging component that allows users of your app
	to easily communicate with your customer support agents. You can send text messages
	and receive push notifications and automatic server-provided replies.

	The component integrates nicely in any Android phone or tablet, allows presenting
	the chat through Fragment or Activity and has rich customization options to fit
	perfectly in your application.

This sample shows how to
- Add Engage Digital repository and dependency.
- Display a Engage Digital Messaging session in two different ways (simple SDK built-in activity & complex custom nested fragments)
- Customize the Engage Digital Messaging UI (programmatically & Xml).
- Integrate push notification support

<br>

:information: The `app/src/main/assets/RcConfigSource.json` file should be edited in order to connect this application to your Engage Digital Messaging channel.

Here is the file structure:
```json
[
  {
    "name": "CHANGE_HERE",
    "description": "CHANGE_HERE",
    "domainName": "CHANGE_HERE",
    "apiSecret": "CHANGE_HERE",
    "hostname": "CHANGE_HERE"
  }
]
```

| Parameter     | Description                                                                 | Mandatory                                               |
| ------------- | --------------------------------------------------------------------------- | ------------------------------------------------------- |
| `name`        | Informative text                                                            | NO                                                      |
| `description` | Informative text                                                            | NO                                                      |
| `domainName`  | Your Engage Digital domain name                                             | **YES**                                                 |
| `apiSecret`   | Your Engage Digital Messaging channel API secret                            | **YES**                                                 |
| `hostname`    | Engage Digital Messaging hostname (ask your Engage Digital project manager) | NO (defaults to `.messaging.dimelo.com` if not present) |

<br>

For more informations checkout the [documentation reference](http://mobile-messaging.dimelo.com)
