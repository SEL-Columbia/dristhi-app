//package org.ei.telemedicine.bluetooth;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.media.AudioFormat;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.view.View;
//import android.content.Context;
//import android.util.Log;
//import android.media.MediaRecorder;
//import android.media.MediaPlayer;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class AudioSystem {
//    static final long RECORD_TIME = 60000;  // 1 minute
//
//    // path of the wav file
//    File wavFile = new File("E:/Test/RecordAudio.wav");
//
//    // format of audio file
//    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
//
//    // the line from which audio data is captured
//    TargetDataLine line;
//
//    /**
//     * Defines an audio format
//     */
//    AudioFormat getAudioFormat() {
//        float sampleRate = 16000;
//        int sampleSizeInBits = 8;
//        int channels = 2;
//        boolean signed = true;
//        boolean bigEndian = true;
//        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
//                channels, signed, bigEndian);
//        return format;
//    }
//
//    /**
//     * Captures the sound and record into a WAV file
//     */
//    void start() {
//        try {
//            AudioFormat format = getAudioFormat();
//            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
//
//            // checks if system supports the data line
//            if (!AudioSystem.isLineSupported(info)) {
//                System.out.println("Line not supported");
//                System.exit(0);
//            }
//            line = (TargetDataLine) AudioSystem.getLine(info);
//            line.open(format);
//            line.start();   // start capturing
//
//            System.out.println("Start capturing...");
//
//            AudioInputStream ais = new AudioInputStream(line);
//
//            System.out.println("Start recording...");
//
//            // start recording
//            AudioSystem.write(ais, fileType, wavFile);
//
//        } catch (LineUnavailableException ex) {
//            ex.printStackTrace();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }
//
//    /**
//     * Closes the target data line to finish capturing and recording
//     */
//    void finish() {
//        line.stop();
//        line.close();
//        System.out.println("Finished");
//    }
//
//}
