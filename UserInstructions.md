# User Instruction

This is a step by step guide on how to get the Mp4Split application up and running.

## Download

You can find the official download link in the [Readme.md](Readne.md) file under "Newest Deployed Version".

After downloading the Zip file I would advise you to move it to a location where you'll easily find it again, like on the desktop or in the C:\ directory.

## Information

You can find further information on the settings.txt under [settingReadme](SettingsReadme.md).
You can find further information about the program under [Readme.md](Readne.md) file.

## Setup

After you got the Zip file where you want to have it, right click and extract all the files.

Open the newly created folder.

Open the logs folder and delete the file "testfile.txt".

Open the finalcut folder and delete the file "testfile.txt".

Open the file "settings.txt" and locate "FileLocation".
By default, replace the "C:\Users\Default\Videos" with the path to your Videos folder. If you changed the output location of shadowplay set the path to the folder which you've selected.

After defining the location save the file and exit out.

## Run Mp4Split

Double click on "start.bat". A console should appear on your screen. If you close the console the application will stop.

To activate the timestamp macro press alt+0.

To activate the cutVideo macro press alt+9.

Both key combination can be changed in the settings.txt file.
For further information see [settingReadme](SettingsReadme.md).

## Use Mp4Split

While you are recording and Mp4Split is open you can press the timestamp macro and by default, the last 30 seconds will be cut into a second clip, after you stop recording and you pressed the cutVideo macro.

There is no limit on how many timestamps you can set during a recording session.

After you pressed the cutVideo macro a preview/demo will be created at the location of the original video.
The file may look something like this "originalFileName_clip_00.00.mp4".

Please Note:
```
The preview may have...
   a unstable Framerate
   asynq sound
   the last few frames missing
...which the final product won't have.
```

## Final Cut

To create the final product out of the preview/demo you'll need to drag the preview into the finalCut folder and click on "FinalCut.bat". The final product will appear at the original location with the same name.

Please Note:
```
Creating the final product may take a couple of minutes, Depens on your CPU.
Do not rename the demo/preview or original file until you are finished.
Do not delete the original file you recorded until you've got the finished product.
```

## Tips

```
If the demo isn't long enough you can change the clipLength in the setting before you initiate the final cut.
You can change the start time in the clipname.
```
