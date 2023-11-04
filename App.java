/* Decompiler 52ms, total 362ms, lines 535 */
import java.io.InputStream;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.RecordStore;

public class App extends MIDlet {
   public static App app;
   private static Runtime rt;
   private static Class appClass;
   public static Display display;
   public static Canvas canvas;
   static final int START_NONE = 0;
   static final int START_INITIALIZING = 1;
   static final int START_TOUCHING_CLASSES = 2;
   static final int START_STARTING = 3;
   static final int START_STARTED = 4;
   private static int started = 0;
   public static long baseTime = 0L;
   public static int lastTime = 0;
   public static int time = 0;
   public static int gameTime = 0;
   public static int touchClassesTime = 0;
   public static int startupMemory;
   public static int imageMemory = 0;
   public static int imageStartMem = 0;
   public static long initialMemory;
   public static long peakMemoryUsage = 0L;
   public static String peakMemoryDesc;
   public static final Integer pausingLock = new Integer(0);
   public static final int NOT_PAUSED = 0;
   public static final int PAUSING = 1;
   public static final int PAUSED = 2;
   public static int paused = 0;
   public static final int UNSAFE_PAUSE_STATES = 82561;
   private static boolean inError = false;
   private static int[] imageIndex;
   private static int lastImageChunk;
   private static int lastImageIndex;
   private static InputStream lastImageStream;
   private static int seed = (int)System.currentTimeMillis();
   public static byte[] TBL_ENUMS_OSC_CYCLE;
   public static byte[] TBL_CANVAS_KEYSNUMERIC;
   public static byte[] TBL_COMBAT_WEAPONINFO;
   public static byte[] TBL_COMBAT_MONSTERSTATS;
   public static byte[] TBL_COMBAT_WEAPONDATA;
   public static byte[] TBL_MONSTER_WEAKNESS;
   public static byte[] TBL_MONSTER_COLORS;
   public static byte[] TBL_COCKTAIL_RECIPES;
   public static short[] TBL_COCKTAIL_NAMES;
   public static int[] TBL_COMBAT_COMBATMASKS;
   public static short[] TBL_GAME_LEVELNAMES;
   public static short[] TBL_COMBAT_MONSTERATTACKS;
   public static byte[] TBL_MEDALS;
   public static byte[] TBL_BOOKS;
   public static int[] TBL_RENDER_SINETABLE;

   protected void startApp() throws MIDletStateChangeException {
      if (started == 0) {
         started = 1;
         app = this;
         appClass = this.getClass();
         rt = Runtime.getRuntime();
         initialMemory = (long)getMemFootprint();
         baseTime = System.currentTimeMillis();
         display = Display.getDisplay(this);
         touchClasses();
         canvas = new Canvas(this);
         display.setCurrent(canvas);
         canvas.start();
      } else {
         unpause();
         if (started == 4) {
            Canvas.resume();
         }
      }

   }

   private static final void pause(boolean var0) {
      synchronized(pausingLock) {
         if (paused == 0) {
            paused = 1;
         } else if (paused == 2) {
            return;
         }

         if (var0) {
            try {
               pausingLock.wait();
            } catch (InterruptedException var4) {
            }
         }

      }
   }

   private static final void unpause() {
      synchronized(pausingLock) {
         if (paused == 2) {
            paused = 0;
            pausingLock.notifyAll();
         } else if (paused == 1) {
            paused = 0;
            pausingLock.notifyAll();
         }

      }
   }

   public static final void checkPausedState() {
      checkPausedState(1);
   }

   public static final void checkPausedState(int var0) {
      if (var0 > 0) {
         try {
            Thread.sleep((long)var0);
         } catch (InterruptedException var6) {
         }
      }

      if (paused != 0) {
         synchronized(pausingLock) {
            if (paused == 1) {
               paused = 2;
               pausingLock.notifyAll();
            }

            if (paused == 2) {
               try {
                  pausingLock.wait();
               } catch (InterruptedException var4) {
               }
            }
         }
      }

   }

   protected void pauseApp() {
      pause((1 << Canvas.state & 82561) != 0);
      Sound.soundStop();
   }

   protected void destroyApp(boolean var1) throws MIDletStateChangeException {
      unpause();
      canvas.stop();
      Sound.soundStop();
      Game.unloadMapData();
      Render.shutdown();
      Canvas.freeRuntimeData();
   }

   public boolean startup() {
      int var1 = getUpTimeMs();

      try {
         gameTime = getUpTimeMs();
         startupMemory = getFreeMemory();
         if (!Canvas.startup()) {
            return false;
         }

         Canvas.updateLogos();
         Canvas.updateLogos();
         if (!Text.startup()) {
            return false;
         }

         Canvas.updateLogos();
         if (!Render.startup()) {
            return false;
         }

         Canvas.updateLogos();
         if (!TinyGL.startup(Render.screenWidth, Render.screenHeight)) {
            return false;
         }

         Canvas.updateLogos();
         if (!EntityDef.startup()) {
            return false;
         }

         Canvas.updateLogos();
         if (!Player.startup()) {
            return false;
         }

         Canvas.updateLogos();
         if (!MenuSystem.startup()) {
            return false;
         }

         Canvas.updateLogos();
         if (!Sound.startup()) {
            return false;
         }

         Canvas.updateLogos();
         if (!Game.startup()) {
            return false;
         }

         Canvas.updateLogos();
         ParticleSystem.startup();
         Canvas.updateLogos();
         if (!Combat.startup()) {
            return false;
         }

         int var2 = getFreeMemory();
         startupMemory -= var2;
         Canvas.updateLogos();
         Game.loadConfig();
         checkPausedState();

         try {
            Thread.sleep(1L);
         } catch (InterruptedException var5) {
         }

         Canvas.clearEvents();

         for(; !Canvas.skipIntro && Canvas.updateLogos(); checkPausedState()) {
            try {
               Thread.sleep(10L);
            } catch (InterruptedException var4) {
            }
         }

         var1 = getUpTimeMs() - var1;
         started = 4;
      } catch (Exception var6) {
         Error(var6, 57);
      }

      return true;
   }

   public static final void Error(Throwable var0, int var1) {
      TinyGL.pixels = null;
      Text.freeAllBuffers();
      System.gc();
      if (!inError) {
         inError = true;
         Text var2 = Text.getLargeBuffer();
         Text.resetTextArgs();
         Text.addTextArg(var1);
         Text.composeText((short)1, (short)36, var2);
         var2.dehyphenate();
         if (var0 != null) {
            var2.append(" (");
            var2.append(var0.toString());
            var2.append(")");
         }

         Alert var3 = new Alert("Error " + var1 + "!");
         var3.setType(AlertType.ERROR);
         var3.setTimeout(-2);
         StringBuffer var4 = new StringBuffer(var2.length());

         for(int var5 = 0; var5 < var2.length(); ++var5) {
            var4.append(var2.charAt(var5));
         }

         var3.setString(var4.toString());
         display.setCurrent(var3);
         var2.dispose();

         while(true) {
            while(true) {
               try {
                  Thread.sleep(5000L);
               } catch (Exception var6) {
               }
            }
         }
      }

   }

   public static final void Error(int var0) {
      Error((Throwable)null, var0);
   }

   public final void shutdown() {
      this.notifyDestroyed();
   }

   public static final void beginImageLoading() {
      imageStartMem = getFreeMemory();

      try {
         imageIndex = Resource.loadFileIndex("/images.idx");
         lastImageIndex = -1;
         lastImageChunk = -1;
         lastImageStream = null;
      } catch (Throwable var1) {
         Error(var1, 56);
      }

   }

   public static final void endImageLoading() {
      imageIndex = null;
      lastImageStream = null;
      System.gc();
      imageMemory += imageStartMem - getFreeMemory();
   }

   public static final void beginImageUnload() {
      imageStartMem = getFreeMemory();
   }

   public static final void endImageUnload() {
      imageMemory -= getFreeMemory() - imageStartMem;
   }

   public static final InputStream getResourceAsStream(String var0) {
      checkPausedState();
      InputStream var1 = appClass.getResourceAsStream(var0);
      checkPausedState();
      checkPeakMemory(var0);
      return var1;
   }

   public static final Image loadImageFromIndex(int var0) {
      Image var1 = null;
      checkPausedState();

      try {
         int var2 = imageIndex[var0 * 3 + 2];
         InputStream var3;
         if (lastImageChunk != imageIndex[var0 * 3]) {
            lastImageChunk = imageIndex[var0 * 3];
            lastImageIndex = var0;
            var3 = lastImageStream = getResourceAsStream("images" + lastImageChunk + ".bin");
            checkPausedState();
            Resource.bufSkip(var3, imageIndex[var0 * 3 + 1], false);
            checkPausedState();
         } else {
            var3 = lastImageStream;
            Resource.bufSkip(var3, imageIndex[var0 * 3 + 1] - imageIndex[lastImageIndex * 3 + 1] - imageIndex[lastImageIndex * 3 + 2], false);
            checkPausedState();
            lastImageIndex = var0;
         }

         Resource.read(var3, var2);
         checkPausedState();
         System.gc();
         checkPausedState();
         int var4 = getFreeMemory();
         var1 = Image.createImage(Resource.ioBuffer, 0, var2);
         int var5 = var4 - getFreeMemory();
         checkPausedState();
      } catch (Throwable var6) {
         var6.printStackTrace();
         Error(var6, 56);
      }

      return var1;
   }

   public static final int getUpTimeMs() {
      return (int)(System.currentTimeMillis() - baseTime);
   }

   public static final int getMemFootprint() {
      System.gc();
      return (int)(rt.totalMemory() - rt.freeMemory());
   }

   public static int getTotalMemory() {
      System.gc();
      return (int)rt.totalMemory();
   }

   public static int getFreeMemory() {
      System.gc();
      return (int)rt.freeMemory();
   }

   public static void checkPeakMemory(String var0) {
      int var1 = getMemFootprint();
      if ((long)var1 > peakMemoryUsage) {
         peakMemoryUsage = (long)var1;
         peakMemoryDesc = var0;
      }

   }

   public static int findLargestMemoryBlock() {
      int var0 = 0;
      int var1 = getFreeMemory();
      int var2 = 0;
      byte[] var3 = null;

      while(var1 - var2 > 1) {
         var0 = (var1 + var2) / 2;

         try {
            var3 = new byte[var0];
         } catch (OutOfMemoryError var5) {
         }

         if (var3 != null) {
            var3 = null;
            System.gc();
            var2 = var0;
         } else {
            var1 = var0;
         }
      }

      return var0;
   }

   public static int getFragmentSize() {
      return getFreeMemory() - findLargestMemoryBlock();
   }

   public static void loadRuntimeImages() {
      Canvas.updateLoadingBar(false);
      beginImageLoading();
      Canvas.imgMapCursor = loadImageFromIndex(1);
      Hud.imgDamageVignette = loadImageFromIndex(2);
      Hud.imgActions = loadImageFromIndex(9);
      Canvas.updateLoadingBar(false);
      Hud.imgBottomBarIcons = loadImageFromIndex(12);
      Canvas.updateLoadingBar(false);
      Hud.imgHudFill = loadImageFromIndex(13);
      Hud.imgPlayerFaces = loadImageFromIndex(14);
      Hud.imgIce = loadImageFromIndex(16);
      Hud.imgScope = loadImageFromIndex(28);
      ParticleSystem.imgGibs[2] = loadImageFromIndex(29);
      ParticleSystem.imgGibs[1] = loadImageFromIndex(31);
      ParticleSystem.imgGibs[0] = loadImageFromIndex(40);
      endImageLoading();
      Canvas.updateLoadingBar(false);
   }

   public static void freeRuntimeImages() {
      beginImageUnload();
      Canvas.imgMapCursor = null;
      Hud.imgDamageVignette = null;
      Hud.imgActions = null;
      Hud.imgBottomBarIcons = null;
      Hud.imgHudFill = null;
      Hud.imgPlayerFaces = null;
      Hud.imgScope = null;
      Hud.imgIce = null;
      ParticleSystem.imgGibs[2] = null;
      ParticleSystem.imgGibs[1] = null;
      ParticleSystem.imgGibs[0] = null;
      endImageUnload();
   }

   public static final short nextByte() {
      return (short)(nextInt() & 255);
   }

   public static final int nextInt() {
      seed = 69069 * seed + 1;
      return seed & Integer.MAX_VALUE;
   }

   private static final void touchClasses() {
      touchClassesTime = getUpTimeMs();
      startupMemory = getFreeMemory();
      display.vibrate(0);
      System.gc();
      if (0 == Resource.touchMe + Canvas.touchMe + Combat.touchMe + CombatEntity.touchMe + Entity.touchMe + EntityDef.touchMe + EntityMonster.touchMe + Game.touchMe + GameSprite.touchMe + Hud.touchMe + LerpSprite.touchMe + MenuItem.touchMe + MenuSystem.touchMe + ParticleSystem.touchMe + Player.touchMe + Render.touchMe + ScriptThread.touchMe + Sound.touchMe + CardGames.touchMe + MayaCamera.touchMe) {
         started = 2;
      }

      System.gc();
      System.gc();
      String[] var0 = RecordStore.listRecordStores();
      if (var0 != null && var0.length > 0) {
         started = 2;
      }

      var0 = null;
      System.gc();
      startupMemory -= getFreeMemory();
      touchClassesTime = getUpTimeMs() - touchClassesTime;
   }

   public static void loadTables() {
      TBL_COMBAT_WEAPONINFO = new byte[Resource.getNumTableBytes(1)];
      TBL_COMBAT_WEAPONDATA = new byte[Resource.getNumTableBytes(2)];
      TBL_COMBAT_MONSTERSTATS = new byte[Resource.getNumTableBytes(3)];
      TBL_CANVAS_KEYSNUMERIC = new byte[Resource.getNumTableBytes(5)];
      TBL_ENUMS_OSC_CYCLE = new byte[Resource.getNumTableBytes(6)];
      TBL_COMBAT_MONSTERATTACKS = new short[Resource.getNumTableShorts(0)];
      TBL_COMBAT_COMBATMASKS = new int[Resource.getNumTableInts(4)];
      TBL_GAME_LEVELNAMES = new short[Resource.getNumTableShorts(7)];
      TBL_MONSTER_COLORS = new byte[Resource.getNumTableBytes(8)];
      TBL_MONSTER_WEAKNESS = new byte[Resource.getNumTableBytes(12)];
      TBL_RENDER_SINETABLE = new int[Resource.getNumTableInts(9)];
      TBL_COCKTAIL_RECIPES = new byte[Resource.getNumTableBytes(10)];
      TBL_COCKTAIL_NAMES = new short[Resource.getNumTableShorts(11)];
      TBL_MEDALS = new byte[Resource.getNumTableBytes(13)];
      TBL_BOOKS = new byte[Resource.getNumTableBytes(14)];

      try {
         Resource.beginTableLoading();
         Resource.loadShortTable(TBL_COMBAT_MONSTERATTACKS, 0);
         Resource.loadByteTable(TBL_COMBAT_WEAPONINFO, 1);
         Resource.loadByteTable(TBL_COMBAT_WEAPONDATA, 2);
         Resource.loadByteTable(TBL_COMBAT_MONSTERSTATS, 3);
         Resource.loadIntTable(TBL_COMBAT_COMBATMASKS, 4);
         Resource.loadByteTable(TBL_CANVAS_KEYSNUMERIC, 5);
         Resource.loadByteTable(TBL_ENUMS_OSC_CYCLE, 6);
         Resource.loadShortTable(TBL_GAME_LEVELNAMES, 7);
         Resource.loadByteTable(TBL_MONSTER_COLORS, 8);
         Resource.loadIntTable(TBL_RENDER_SINETABLE, 9);
         Resource.loadByteTable(TBL_COCKTAIL_RECIPES, 10);
         Resource.loadShortTable(TBL_COCKTAIL_NAMES, 11);
         Resource.loadByteTable(TBL_MONSTER_WEAKNESS, 12);
         Resource.loadByteTable(TBL_MEDALS, 13);
         Resource.loadByteTable(TBL_BOOKS, 14);
         Resource.finishTableLoading();
      } catch (Exception var1) {
         Error(var1, 77);
      }

   }
}
