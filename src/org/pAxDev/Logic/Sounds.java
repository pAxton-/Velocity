package org.pAxDev.Logic;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.lwjgl.openal.AL10.*;
/**
 * Created by Lance on 3/5/2016.
 */
public class Sounds {
    int buffer;
    int playButtonSource;
    int bufferChange;
    int getSquareSource;
    int bufferTaken;
    int playTakenSource;
    int bufferLose;
    int playLoseSource;
    public Sounds() {
        try {
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public void init() throws FileNotFoundException {
        WaveData data = WaveData.create(new BufferedInputStream(new FileInputStream("src/res/playb.wav")));
        buffer = alGenBuffers();
        alBufferData(buffer, data.format, data.data, data.samplerate);
        data.dispose();
        playButtonSource = alGenSources();
        alSourcei(playButtonSource,AL_BUFFER, buffer);
        WaveData changeData = WaveData.create(new BufferedInputStream(new FileInputStream("src/res/squareChange.wav")));
        bufferChange = alGenBuffers();
        alBufferData(bufferChange, changeData.format, changeData.data, changeData.samplerate);
        changeData.dispose();
        getSquareSource = alGenSources();
        alSourcei(getSquareSource,AL_BUFFER, bufferChange);

        WaveData takenData = WaveData.create(new BufferedInputStream(new FileInputStream("src/res/taken.wav")));
        bufferTaken = alGenBuffers();
        alBufferData(bufferTaken, takenData.format, takenData.data, takenData.samplerate);
        takenData.dispose();
        playTakenSource = alGenSources();
        alSourcei(playTakenSource,AL_BUFFER, bufferTaken);

        WaveData loseData = WaveData.create(new BufferedInputStream(new FileInputStream("src/res/lose.wav")));
        bufferLose = alGenBuffers();

        alBufferData(bufferLose, loseData.format, loseData.data, loseData.samplerate);
        loseData.dispose();
        playLoseSource = alGenSources();
        alSourcei(playLoseSource,AL_BUFFER, bufferLose);

    }

    public void destroy() {
        alDeleteBuffers(buffer);
        alDeleteBuffers(bufferChange);
        alDeleteBuffers(bufferTaken);
        alDeleteBuffers(bufferLose);
        AL.destroy();
    }

    public void playButtonSound() {
        alSourcePlay(playButtonSource);
    }
    public void playSquareChangeSound() {
        alSourcePlay(getSquareSource);
    }
    public void playTakenSound() {
        alSourcePlay(playTakenSource);
    }
    public void playLoseSound() {
        alSourcePlay(playLoseSource);
    }
}
