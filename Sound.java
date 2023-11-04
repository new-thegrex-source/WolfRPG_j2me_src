/* Decompiler 71ms, total 359ms, lines 334 */
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

final class Sound {
   public static int touchMe = 1;
   private static final int SLOT_COUNT = 3;
   public static final int FLAGS_NONE = 0;
   public static final int FLAGS_LOOP = 1;
   public static final int SND_NEG = 0;
   public static final int SND_POS = 1;
   public static final int SND_DYN = 2;
   private static Player[] sounds = new Player[3];
   private static int[] soundTimes = new int[3];
   public static boolean soundLooped;
   public static boolean soundFormatAllowed = true;
   private static boolean vibrateAllowed;
   private static boolean vibrateEnabled;
   private static boolean allowSounds = true;
   public static boolean isRegistered = false;
   private static boolean sndLimited = false;
   private static final boolean sndSupportsSetMediaTime = false;
   private static int sndMaxPrefetched = 2;
   private static int sndMaxRealized = 1;
   public static final String SOUND_FORMAT = "audio/midi";
   private static int[] sndIndex;
   private static final int MAX_PREFETCH_ATTEMPTS = 5;
   public static long lLastTime_StartSound = 0L;
   private static int activeSound = -1;

   public static final boolean startup() {
      try {
         sndIndex = Resource.loadFileIndex("/sounds.idx");
      } catch (Exception var1) {
         App.Error(var1, 80);
         return false;
      }

      soundRegister();
      vibrateAllowed = true;
      if (vibrateAllowed) {
         vibrateAllowed = App.display.vibrate(0);
      }

      setVibrate(true);
      allowSounds = soundFormatAllowed;
      return !allowSounds ? true : true;
   }

   public static boolean isSoundEnabled() {
      return allowSounds;
   }

   public static void setSound(boolean var0) {
      if (soundFormatAllowed) {
         allowSounds = var0;
      }
   }

   public static boolean isVibrateEnabled() {
      return vibrateEnabled;
   }

   public static void setVibrate(boolean var0) {
      if (vibrateAllowed) {
         vibrateEnabled = var0;
      }
   }

   public static final void soundUnregister() {
      isRegistered = false;
      activeSound = -1;
      if (sounds[1] != null) {
         sounds[1].close();
         sounds[1] = null;
      }

      if (!sndLimited) {
         if (sounds[0] != null) {
            sounds[0].close();
            sounds[0] = null;
         }

         if (sounds[2] != null) {
            sounds[2].close();
            sounds[2] = null;
         }
      }

   }

   public static final void soundRegister() {
      isRegistered = true;
      cacheSound(0);
      cacheSound(1);
   }

   public static final void soundPrefetch(Player var0) {
      int var1 = 0;

      while(var1 < 5) {
         try {
            var0.prefetch();
            break;
         } catch (MediaException var5) {
            try {
               Thread.sleep(10L);
            } catch (InterruptedException var4) {
            }

            ++var1;
         }
      }

   }

   private static Player loadSoundFromFile(int var0) throws MediaException, IOException {
      int var1 = App.getUpTimeMs();
      InputStream var2 = App.getResourceAsStream("sounds" + sndIndex[var0 * 3] + ".bin");
      Player var3 = Manager.createPlayer(var2, "audio/midi");
      return var3;
   }

   private static final void cacheSound(int var0) {
      if (!sndLimited || var0 == 1) {
         Player var1 = sounds[var0];
         if (var1 == null || var1.getState() < 300) {
            int var2 = 0;
            int var3 = 0;

            int var4;
            for(var4 = 0; var4 < 3; ++var4) {
               if (var4 != var0 && sounds[var4] != null) {
                  if (sounds[var4].getState() >= 300) {
                     ++var2;
                  } else if (sounds[var4].getState() >= 200) {
                     ++var3;
                  }
               }
            }

            if (var2 == sndMaxPrefetched) {
               long var10 = Long.MAX_VALUE;
               int var6 = -1;

               Player var7;
               for(int var8 = 0; var8 < 3; ++var8) {
                  var7 = sounds[var8];
                  if (var8 != var0 && var7 != null && (long)soundTimes[var8] < var10) {
                     var6 = var8;
                     var10 = (long)soundTimes[var8];
                  }
               }

               var7 = sounds[var6];
               byte var11;
               if (var7.getState() >= 300) {
                  var7.deallocate();
                  if (var3 == sndMaxRealized) {
                     if ((var6 != 0 || var0 != 1) && (var6 != 1 || var0 != 0)) {
                        if ((var6 != 0 || var0 != 2) && (var6 != 2 || var0 != 0)) {
                           var11 = 0;
                        } else {
                           var11 = 1;
                        }
                     } else {
                        var11 = 2;
                     }

                     sounds[var11].close();
                     sounds[var11] = null;
                     soundTimes[var11] = 0;
                  }
               } else {
                  if (var3 == sndMaxRealized) {
                     var7.close();
                     sounds[var6] = null;
                     soundTimes[var6] = 0;
                  }

                  if (var6 == 0 && var0 == 1 || var6 == 1 && var0 == 0) {
                     var11 = 2;
                  } else if ((var6 != 0 || var0 != 2) && (var6 != 2 || var0 != 0)) {
                     var11 = 0;
                  } else {
                     var11 = 1;
                  }

                  sounds[var11].deallocate();
               }
            }

            try {
               if (sounds[var0] == null) {
                  if (var0 == 0) {
                     var4 = 7;
                  } else if (var0 == 1) {
                     var4 = 8;
                  } else {
                     if (activeSound == -1) {
                        return;
                     }

                     var4 = activeSound;
                  }

                  sounds[var0] = loadSoundFromFile(var4);
               }

               soundPrefetch(sounds[var0]);
               soundTimes[var0] = App.getUpTimeMs();
            } catch (Exception var9) {
            }

         }
      }
   }

   public static final void startSound(int var0) {
      if (System.currentTimeMillis() - lLastTime_StartSound >= 500L) {
         lLastTime_StartSound = System.currentTimeMillis();
         if (allowSounds) {
            if (!sndLimited || var0 == 1) {
               if (!soundLooped || var0 == 2) {
                  if (sndLimited) {
                     soundStop();
                  } else {
                     for(int var1 = 0; var1 < 3; ++var1) {
                        if (sounds[var1] != null && sounds[var1].getState() == 400) {
                           try {
                              sounds[var1].stop();
                           } catch (Exception var5) {
                           }
                        }
                     }
                  }

                  if (!sndLimited) {
                     try {
                        Thread.sleep(30L);
                     } catch (InterruptedException var4) {
                     }
                  }

                  cacheSound(var0);

                  try {
                     if (soundLooped) {
                        sounds[var0].setLoopCount(-1);
                     }

                     ((VolumeControl)sounds[var0].getControl("VolumeControl")).setLevel(60);
                     sounds[var0].start();
                  } catch (Exception var3) {
                  }

               }
            }
         }
      }
   }

   public static int getActiveSound() {
      return activeSound;
   }

   public static final void loadSound(int var0, boolean var1) {
      if (allowSounds && !sndLimited && var0 != activeSound) {
         if (sounds[2] != null) {
            sounds[2].close();
            sounds[2] = null;
            activeSound = -1;
            soundLooped = false;
            System.gc();
         }

         activeSound = var0;

         try {
            sounds[2] = loadSoundFromFile(var0);
            if (var1) {
               sounds[2].setLoopCount(-1);
            }

            soundLooped = var1;
         } catch (Exception var3) {
         }

         cacheSound(2);
      }
   }

   public static final void playSound(int var0) {
      playSound(var0, 0);
   }

   public static final void playSound(int var0, int var1) {
      if (var0 != 14) {
         if (var0 == 8) {
            soundLooped = false;
            startSound(1);
         } else if (var0 == 7) {
            soundLooped = false;
            startSound(0);
         } else {
            boolean var2 = (var1 & 1) != 0;
            loadSound(var0, var2);
            startSound(2);
         }
      }
   }

   public static final void soundStop() {
      if (sounds[2] != null) {
         try {
            sounds[2].stop();
         } catch (Exception var1) {
         }
      }

   }

   public static final void clearDynamicSound() {
      if (sounds[2] != null) {
         sounds[2].close();
         sounds[2] = null;
      }

   }
}
