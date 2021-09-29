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

To build a fully functional version please edit `assets/RcConfigSource.json`:
- replace apiSecret by your own Engage Digital Messaging SDK API secret (ask your Engage Digital project manager)
- replace domainName by your own Engage Digital domain name (ask your
   Engage Digital project manager)
- replace hostname by your own Engage Digital hostname (ask your Engage Digital project manager)

For more informations checkout the
[documentation reference](http://mobile-messaging.dimelo.com)
