package controller;

import java.io.File;

import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import javafx.concurrent.Task;

public class JavaSoundRecorder extends Task<Void> {
	
	    static final long RECORD_TIME = 60000;  

	    File wavFile ;
	    
	    
	    public File getWavFile() {
			return wavFile;
		}

		public void setWavFile(File wavFile) {
			this.wavFile = wavFile;
		}

	    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

	    TargetDataLine line;

	    @Override
	    protected Void call() throws Exception
	    {
	        try {
	            AudioFormat format = getAudioFormat();
	            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

	            if (!AudioSystem.isLineSupported(info)) {
	                System.out.println("Line not supported");
	                System.exit(0);
	            }
	            line = (TargetDataLine) AudioSystem.getLine(info);
	            line.open(format);
	            line.start();  

	            System.out.println("Start capturing...");

	            AudioInputStream ais = new AudioInputStream(line);

	            System.out.println("Start recording...");

	            AudioSystem.write(ais, fileType, wavFile);

	        }
	        catch (LineUnavailableException | IOException ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }

	    AudioFormat getAudioFormat()
	    {
	        float sampleRate = 16000;
	        int sampleSizeInBits = 8;
	        int channels = 2;
	        boolean signed = true;
	        boolean bigEndian = true;
	        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
	                channels, signed, bigEndian);
	        return format;
	    }

	    public void finish()
	    {
	        line.stop();
	        line.close();
	        System.out.println("Finished");
	    }

}
