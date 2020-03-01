package com.mp4splitmaven.HelperClass;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TimeStampManagerTest {
    private final static Long SECOND = 1000L;
    private final static Long MINUTE = 60000l;
    private final static Long HOUR = 3600000l;

    @Test
    public void formatTime(){
        //Arrange
        TimeStampManager timeStampManager = new TimeStampManager();
        Long[] input  = {SECOND,MINUTE,HOUR};
        String[] expected = {"00:00:01","00:01:00","01:00:00"};
        //Act
        String[] result = timeStampManager.formatTime(input);
        //Assert
        assertArrayEquals(expected,result);
    }

    @Test
    public void formatTimeEdgeCaseNegative(){
        //Arrange
        TimeStampManager timeStampManager = new TimeStampManager();
        Long input  = -1000l;
        String expected = "00:00:00";
        //Act
        String result = timeStampManager.formatTime(input);
        //Assert
        assertEquals(expected,result);
    }

    @Test
    public void prepareTimeStamp() {
        //Arrange
        TimeStampManager timeStampManager = new TimeStampManager();
        int videoLengthInSeconds = 900;

        Long currentTime = new Date().getTime();
        Long cutTimes = currentTime-(MINUTE);

        String[] expectedStartTime = {"00:13:30"};
        String[] expectedClipLength = {"00:00:30"};
        //Act
        timeStampManager.addNewTimeStampMock(cutTimes);

        timeStampManager.clipHasEnded();

        timeStampManager.prepareTimeStamp(videoLengthInSeconds);

        String[] resultStartTime = timeStampManager.getCutStartTimeStamp();
        String[] resultClipLength = timeStampManager.getclipLengthToCut();
        //Assert
        assertArrayEquals(expectedClipLength,resultClipLength);
        assertArrayEquals(expectedStartTime,resultStartTime);
    }

    @Test
    public void prepareTimeStampRightBeforeEnd() {
        //Arrange
        TimeStampManager timeStampManager = new TimeStampManager();
        int videoLengthInSeconds = 900;

        Long currentTime = new Date().getTime();
        Long cutTimes = currentTime-(SECOND*10);

        String[] expectedStartTime = {"00:14:20"};
        String[] expectedClipLength = {"00:00:30"};
        //Act
        timeStampManager.clipHasEnded();

        timeStampManager.addNewTimeStampMock(cutTimes);

        timeStampManager.prepareTimeStamp(videoLengthInSeconds);

        String[] resultStartTime = timeStampManager.getCutStartTimeStamp();
        String[] resultClipLength = timeStampManager.getclipLengthToCut();
        //Assert
        assertArrayEquals(expectedClipLength,resultClipLength);
        assertArrayEquals(expectedStartTime,resultStartTime);
    }

    @Test
    public void prepareTimeStampEdgeCaseLongerThenVideo() {
        //Arrange
        TimeStampManager timeStampManager = new TimeStampManager();
        int videoLengthInSeconds = 900;

        Long currentTime = new Date().getTime();
        Long cutTimes = currentTime-(HOUR);

        String[] expectedStartTime = {};
        String[] expectedClipLength = {};
        //Act
        timeStampManager.addNewTimeStampMock(cutTimes);

        timeStampManager.clipHasEnded();

        timeStampManager.prepareTimeStamp(videoLengthInSeconds);

        String[] resultStartTime = timeStampManager.getCutStartTimeStamp();
        String[] resultClipLength = timeStampManager.getclipLengthToCut();
        //Assert
        assertArrayEquals(expectedClipLength,resultClipLength);
        assertArrayEquals(expectedStartTime,resultStartTime);
    }

    @Test
    public void prepareTimeStampToShort() {
        //Arrange
        TimeStampManager timeStampManager = new TimeStampManager();
        int videoLengthInSeconds = 900;

        Long currentTime = new Date().getTime();
        Long cutTimes = currentTime-((MINUTE*14)+(50*SECOND));

        String[] expectedStartTime = {"00:00:00"};
        String[] expectedClipLength = {"00:00:10"};
        //Act
        timeStampManager.addNewTimeStampMock(cutTimes);

        timeStampManager.clipHasEnded();

        timeStampManager.prepareTimeStamp(videoLengthInSeconds);

        String[] resultStartTime = timeStampManager.getCutStartTimeStamp();
        String[] resultClipLength = timeStampManager.getclipLengthToCut();
        //Assert
        assertArrayEquals(expectedClipLength,resultClipLength);
        assertArrayEquals(expectedStartTime,resultStartTime);
    }
}