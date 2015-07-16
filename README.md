Dimelo Android Sample Application
==========

This Android Studio Project is a Sample App showing how to use the [Dimelo](http://www.dimelo.com) SDK.

	Dimelo provides a mobile messaging component that allows users of your app
	to easily communicate with your customer support agents. You can send text messages 
	and receive push notifications and automatic server-provided replies.

	The component integrates nicely in any Android phone or tablet, allows presenting
	the chat through Fragment or Activity and has rich customization options to fit
	perfectly in your application.

This sample shows how to
- Add Dimelo repository and dependency.
- Display a Dimelo Mobile Chat session in two different ways (simple SDK built-in activity & complex custom nested fragments)
- Customize the Dimelo Mobile Chat UI (programmatically & Xml).
- Integrate push notification support

To built a fully functional version please edit `gradle.properties` :
 - replace GCM_API_KEY with your own GCM id (for notification)
 - replace DIMELO_SDK_SECRET by your own Dimelo Mobile SDK API secret (ask your
   Dimelo project manager)

For more informations checkout the
[documentation reference](http://mobile-messaging.dimelo.com)
