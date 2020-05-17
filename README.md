# Mp4Split

This program was created as an addon for NVIDIA's instant replay and record function. This application allows you to define mutiple highlight sections while you are recording the video and automatically cuts them out after you stopped recording.

## Getting Started

If you just want to use Mp4Split you can download it with link provide at "Newest Deployed Version".

If you want to download the project please see "Project installation guide"

### Newest Deployed Version

Here is the download link for the newest Version of Mp4Split: [Download](https://drive.google.com/file/d/15OgYNGXUTlzvazoMc8OPjjb61gDEqTV0/view)

For instruction on how to use Mp4Split please see [Text instruction](UserInstructions.md) or [Video Tutorial](https://youtu.be/LI3YnAIBl0w)

### Project installation guide

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

#### Prerequisites

In order to compile Mp4Split you need to have JDK installed on your system (version 8 or later). This is basically the Java compiler.
Maven also needs to be installed.

Both ffmpeg.exe and ffprobe.exe which are used in the project are not uploaded to GitHub. You can find both files either at [Offical Site](https://www.ffmpeg.org/download.html) or in the download of the newest version.

#### Installing

To get the project up and running you can either check out the project using an SVN or download the project as a zip file and import it in to your programming  environment of choice as a maven project. After that you'll need to copy Ffmpeg.exe and Ffprobe.exe into the base directory of the project.

**Important**
This project uses the NeoUtil project as a dependency.
You can find the project [here](https://github.com/Neoministein/NeoUtil).

## Deployment

To deploy the project I used this following command: 

```
mvn clean compile assembly:single
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [JNativeHook](https://github.com/kwhat/jnativehook) - Keyboard Listener for activation macro
* NeoUtil - Logging and File Handeling

## Authors

* **Neoministein** - *Initial work* - [Neoministein](https://www.youtube.com/channel/UCtfBiBXVXqTotONMq6VSMbg) Neoministein#1245

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details

## Acknowledgments

* This project uses the JNativeHook
* This project uses FFmpeg and FFprobe
