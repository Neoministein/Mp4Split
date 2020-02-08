# Mp4Split

This program was created as an adon for Nvideas instant replay and record function. 
This appication allows you to define highlight section while you are recording the video and automaticly cuts them out after you stoped recording.

## Getting Started

If you just want to use Mp4Split you can download it with link provied at "Newest Deployed Version".

If you want to download the project please see "Project installation guide"

### Newest Deployed Version

Here is the download link for the newest Version of Mp4Split.

[Download](https://drive.google.com/file/d/15OgYNGXUTlzvazoMc8OPjjb61gDEqTV0/view)

For instruction on how to use Mp4Split please see [Video]()

### Project installation guide

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

#### Prerequisites

In order to compile Mp4Split you need to have JDK installed on your system (version 8 or later). This is basically the Java compiler.
Maven also needs to be installed.

Bolth ffmpeg.exe and ffprobe.exe which are used in the project are not uploaded to GitHub. You can find both files eather at:  [Offical Site](https://www.ffmpeg.org/download.html) or in the download of newest version.

#### Installing

To get the project up and running you can eather check out the project using an SVN or donwload the project as a zip file and import it in to your programing enviroment of choice as a maven project. 

## Deployment

To deploy the project I used this following command: 

```
mvn clean compile assembly:single
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [JNativeHook](https://github.com/kwhat/jnativehook) - Keyboard Listner for activation macro

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

* **Neoministein** - *Initial work* - [Neoministein](https://www.youtube.com/channel/UCtfBiBXVXqTotONMq6VSMbg)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* This project uses the JNativeHook
* This project uses FFmpeg and FFprobe
