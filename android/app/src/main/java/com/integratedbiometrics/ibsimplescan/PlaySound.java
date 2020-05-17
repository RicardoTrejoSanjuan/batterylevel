package com.integratedbiometrics.ibsimplescan;

import android.media.AudioTrack;
import java.util.Timer;
import java.util.TimerTask;

public class PlaySound {
  private final int duration = 1;
  
  private final double freqOfTone;
  
  private final byte[] generatedSnd;
  
  private final int numSamples;
  
  private final double[] sample;
  
  private final int sampleRate = 8000;
  
  public PlaySound() {
    getClass();
    getClass();
    this.numSamples = 8000;
    this.sample = new double[this.numSamples];
    this.freqOfTone = 880.0D;
    this.generatedSnd = new byte[this.numSamples * 2];
    int i;
    for (i = 0; i < this.numSamples; i++) {
      double[] arrayOfDouble1 = this.sample;
      double d = i;
      getClass();
      getClass();
      arrayOfDouble1[i] = Math.sin(6.283185307179586D * d / 8000.0D / 880.0D);
    } 
    double[] arrayOfDouble = this.sample;
    int k = arrayOfDouble.length;
    i = 0;
    int j = 0;
    while (i < k) {
      short s = (short)(int)(32767.0D * arrayOfDouble[i]);
      byte[] arrayOfByte = this.generatedSnd;
      int m = j + 1;
      arrayOfByte[j] = (byte)(s & 0xFF);
      arrayOfByte = this.generatedSnd;
      j = m + 1;
      arrayOfByte[m] = (byte)((0xFF00 & s) >>> 8);
      i++;
    } 
  }
  
  public void playSound() {
    getClass();
    final AudioTrack audioTrack = new AudioTrack(3, 8000, 4, 2, this.numSamples, 0);
    audioTrack.write(this.generatedSnd, 0, this.generatedSnd.length);
    audioTrack.play();
    TimerTask timerTask = new TimerTask() {
        public void run() {
          audioTrack.stop();
          audioTrack.release();
        }
      };
    (new Timer()).schedule(timerTask, 1000L);
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\integratedbiometrics\ibsimplescan\PlaySound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */