# Settingsfile

## DebugLevel

#### Default : 3
The debug level defines what being showed to you when the application is running. There 5 Levels:
```
Level 5 "Keypress": Shows you every Key you press
Level 4 "Debug": Shows you Debug information while the program is running
Level 3 "Info": Shows you in which state the program is
Level 2 "Warn": Shows you a warning when something didn't go as planned
Level 1 "Error": Shows you when something went wrong but the program can still function
Level 0 "Fatal": Show you a messages when something went horrible wrong and the operation needs to be stoped
```

## ClipLength

###### Default : 30
This determines how many seconds the clip will go back from the moment to pressed the timestamp macro.

## SecondsUntilCut

###### Default : 10
Sometime NVIDIA does some post processing on the video and it can happen that Mp4Split can't access it. SecondsUntilCut is the time that the program will wait before it will try to cut the video.

## TrysToCut

###### Default : 5
If the program can't access the video trysToCut is the amount of times the program will try again to cut it until it gives up.

## FileLocation:

###### Default : C:\Users\Default\Videos
This is the location Mp4Split will look for the newest created Video to cut.

## Macro

If you want to change it you please see [List](MacroList.md)

### TimestampMacroOne

###### Default : 56 
###### 56 = Alt
The first button you need to press to activate the timestamp macro.

### TimestampMacroTwo

###### Default : 11
###### 11 = "0"
The second button you need to press to activate the timestamp macro.

### CutVideoMacroOne

###### Default : 56
###### 56 = Alt
The first button you need to press to activate the cut video macro.

### CutVideoMactoTwo

###### Default : 10
###### 10 = "9"
The second button you need to press to activate the cut video macro.
