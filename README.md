To run this project, you need to do this:

1. Set the ANDROID\_HOME environment variable to point to the location of your installed Android SDK 2.2. For more information, look at [the documentation of maven-android-plugin](http://code.google.com/p/maven-android-plugin/wiki/GettingStarted).

2. Start an Android Virtual Device. Normally, this means you need to run "android avd" and then start one of the devices there.

Then, you can run "mvn clean install" in the main directory.

