# Android audio decoder sample

This app uses the OpenSL api to decode a few audio files, measuring the time taken for conversion.

OpenSLDecoder.cpp/hpp and OpenSLEngine.cpp/hpp are portable classes wrapping the OpenSL api.
DecodeTest.cpp and TestActivity.java show how such wrapper can be used.

The app does nothing with the decoded data (no operation on the audio samples is performed, and no disk writing either).

Below some test results are provided: you will notice that the decoding times skyrocket on Android 7.0+ devices.

Code references:
[Google sample](https://android.googlesource.com/platform/frameworks/wilhelm/+/master/tests/examples/slesTestDecodeToBuffQueue.cpp) ,
[Retieving metadata](https://groups.google.com/forum/#!topic/android-ndk/0x9jk-mEH60)

````
LENOVO A680 - Android 4.2.2

stereo_44.m4a   1.225 s
stereo_48.m4a   1.426 s
stereo_44.mp3   0.621 s
stereo_48.mp3   0.665 s
````
````
Samsung Galaxy S4 (GT-I9505) - Android 5.0.1  

stereo_44.m4a   1.817 s 
stereo_48.m4a   1.661 s 
stereo_44.mp3   1.36 s 
stereo_48.mp3   1.556 s 
````
````
 Nexus 5 - Android 6.0.1 

 stereo_44.m4a   0.895 s 
stereo_48.m4a   0.944 s 
stereo_44.mp3   0.582 s 
stereo_48.mp3   0.625 s  
````
````
Nexus 5X - Android 7.0

 stereo_44.m4a   14.577 s 
stereo_48.m4a   17.314 s 
stereo_44.mp3   13.401 s 
stereo_48.mp3   15.419 s  
````
````
Nexus 6 - Android 7.0  

stereo_44.m4a   13.704 s 
stereo_48.m4a   17.094 s 
stereo_44.mp3   13.565 s 
stereo_48.mp3   14.283 s  
````
````
Samsung S7 Edge (SM-G935F)

  stereo_44.m4a   17.423 s 
stereo_48.m4a   22.831 s 
stereo_44.mp3   15.555 s 
stereo_48.mp3   16.404 s  
````
````
Google Pixel - 7.1.1  

stereo_44.m4a   17.34 s 
stereo_48.m4a   19.043 s 
stereo_44.mp3   15.29 s 
stereo_48.mp3   16.495 s
````