package ERFAN.menuScreen.SongPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class SongPlayer implements LineListener{

    private Clip audioClip;

    public void play(String filePath) {
        File audioFile = new File((filePath));
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);

            audioClip.open(audioStream);

            audioClip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        audioClip.stop();
        audioClip.close();
    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");

        } else if (type == LineEvent.Type.STOP) {
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Playback completed.\nrestarting");
        }
    }
}
