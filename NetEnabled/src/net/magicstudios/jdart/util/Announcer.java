package net.magicstudios.jdart.util;

import java.applet.*;
import java.io.*;
import java.net.*;

import javax.sound.sampled.*;
import javax.swing.*;

import net.magicstudios.jdart.data.*;
import java.util.HashMap;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Announcer {

  public static final int TAUNT_THROW_ALREADY = 0;
  public static final int TAUNT_SEE_TARGET    = 1;

  public static final AudioClip [] TAUNTS = new AudioClip[] {
      JApplet.newAudioClip(Announcer.class.getResource("/net/magicstudios/jdart/util/miss.wav")),
      JApplet.newAudioClip(Announcer.class.getResource("/net/magicstudios/jdart/util/miss.wav")),
      JApplet.newAudioClip(Announcer.class.getResource("/net/magicstudios/jdart/util/miss.wav")),
      JApplet.newAudioClip(Announcer.class.getResource("/net/magicstudios/jdart/util/miss.wav")),
      JApplet.newAudioClip(Announcer.class.getResource("/net/magicstudios/jdart/util/miss.wav"))
  };


  public static String DOUBLE = "/net/magicstudios/jdart/util/double.wav";
  public static String TRIPLE = "/net/magicstudios/jdart/util/triple.wav";
  public static String MISS = "/net/magicstudios/jdart/util/miss.wav";
  public static String NEXT_PLAYER = "/net/magicstudios/jdart/util/nextplayer.wav";
  public static String OCCLUSION = "/net/magicstudios/jdart/util/occlusion.wav";
  public static String WINNER = "/net/magicstudios/jdart/util/winner.wav";
  public static String BASE_PATH = "/net/magicstudios/jdart/util/";

  public AudioClip[] mClipSlices = new AudioClip[21];
  public AudioClip mClipDouble = null;
  public AudioClip mClipTriple = null;
  public AudioClip mClipMiss = null;
  public AudioClip mClipNextPlayer = null;
  public AudioClip mClipOcclusion = null;
  public AudioClip mClipWinner = null;
  public HashMap m_mapNameToVoice = null;

  public Announcer() {

    mClipDouble = JApplet.newAudioClip(this.getClass().getResource(DOUBLE));
    mClipTriple = JApplet.newAudioClip(this.getClass().getResource(TRIPLE));
    mClipMiss = JApplet.newAudioClip(this.getClass().getResource(MISS));
    mClipNextPlayer = JApplet.newAudioClip(this.getClass().getResource(NEXT_PLAYER));
    mClipOcclusion = JApplet.newAudioClip(this.getClass().getResource(OCCLUSION));
    mClipWinner = JApplet.newAudioClip(this.getClass().getResource(WINNER));

    m_mapNameToVoice = new HashMap();

    for (int i = 0; i < mClipSlices.length; i++) {
      mClipSlices[i] = JApplet.newAudioClip(this.getClass().getResource(BASE_PATH + i + ".wav"));
    }
  }

  public void play(Zone z) throws MalformedURLException, LineUnavailableException, IOException, UnsupportedAudioFileException {

    AudioClip clipRing = null;
    int ring = z.getRingMultiplier();

    int slice = z.getSlice();
    AudioClip clipSlice = mClipSlices[slice];

    switch (ring) {
      case 0:
        clipRing = mClipMiss;
        clipSlice = null;
        break;
      case 2:
        clipRing = mClipDouble;
        break;
      case 3:
        clipRing = mClipTriple;
        break;
    }

    if (clipRing != null) {
      clipRing.play();
      try {
        Thread.sleep(600);
      } catch (InterruptedException ex) {
      }
    }
    if (clipSlice != null) {
      clipSlice.play();
    }
  }

  public void taunt(int taunt) {
    AudioClip clip = TAUNTS[taunt];
    clip.play();
  }

  public void nextPlayer() {
    if (mClipNextPlayer != null) {
      mClipNextPlayer.play();
    }
  }

  public void occlusion() {
    if (mClipOcclusion != null) {
      mClipOcclusion.play();
    }
  }

  public void winner() {
    if (mClipWinner != null) {
      mClipWinner.play();
    }
  }

  public void play(String name) {
    AudioClip clipName = (AudioClip) m_mapNameToVoice.get(name.toLowerCase());
    if (clipName != null && mClipNextPlayer != null) {
      mClipNextPlayer.play();
      try {
        Thread.sleep(800);
      } catch (InterruptedException ex) {
      }

      clipName.play();
    }
  }

  public static void main(String[] args) {
    try {
      Announcer an = new Announcer();
      an.play(new Zone(19, Zone.RING_TRIPLE));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
