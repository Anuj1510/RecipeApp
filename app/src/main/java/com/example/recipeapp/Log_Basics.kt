/*

1) Log.e: This is for when bad stuff happens.
Use this tag in places like inside a catch statement. You know that an error has occurred and therefore you're
logging an error.

2) Log.w: Use this when you suspect something shady is going on.
You may not be completely in full on error mode, but maybe you recovered from some unexpected behavior.
 Basically, use this to log stuff you didn't expect to happen but isn't necessarily an error. Kind of like a
 "hey, this happened, and it's weird, we should look into it."

3) Log.i: Use this to post useful information to the log. For example:
that you have successfully connected to a server. Basically use it to report successes.

4) Log.d: Use this for debugging purposes. If you want to print out a bunch of messages so you can log the
exact flow of your program, use this. If you want to keep a log of variable values, use this.

5) Log.v: Use this when you want to go absolutely nuts with your logging. If for some reason you've
decided to log every little thing in a particular part of your app, use the Log.v tag.

And as a bonus...

6) Log.wtf: Use this when stuff goes absolutely, horribly, holy-crap wrong.
You know those catch blocks where you're catching errors that you never should get...yeah,
if you wanna log them use Log.wtf
 */