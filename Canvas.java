/* Decompiler 2374ms, total 3019ms, lines 5433 */
import com.ea.sdk.SDKMoreGames;
import com.ea.sdk.SDKTextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

final class Canvas extends javax.microedition.lcdui.Canvas implements Runnable {
   public static int touchMe = 1;
   static byte[] OSC_CYCLE = null;
   public static final String BIN_TABLES = "/tables.bin";
   public static final String BIN_ENTITIES = "/entities.bin";
   public static final String BIN_MAPPINGS = "/mappings.bin";
   public static final String BIN_PALETTES = "/palettes.bin";
   public static final String BIN_MENUS = "/menus.bin";
   public static final String IDX_SOUNDS = "/sounds.idx";
   public static final String IDX_IMAGES = "/images.idx";
   public static final String IDX_STEXELS = "/stexels.idx";
   public static final String IDX_WTEXELS = "/wtexels.idx";
   public static final String IDX_FTEXELS = "/ftexels.idx";
   public static final String IDX_SHAPES = "/shapes.idx";
   public static final String IDX_MAPS = "/maps.idx";
   public static final String IDX_STRINGS = "/strings.idx";
   public static final int KEY_ARROWUP = -1;
   public static final int KEY_ARROWDOWN = -2;
   public static final int KEY_ARROWLEFT = -3;
   public static final int KEY_ARROWRIGHT = -4;
   public static final int KEY_OK = -5;
   public static final int KEY_CLR = -8;
   private static final int ATTACK_TRACE_DIST = 6;
   public static final int ST_MENU = 1;
   public static final int ST_INTRO_MOVIE = 2;
   public static final int ST_PLAYING = 3;
   public static final int ST_COMBAT = 5;
   public static final int ST_AUTOMAP = 6;
   public static final int ST_LOADING = 7;
   public static final int ST_DIALOG = 8;
   public static final int ST_INTRO = 9;
   private static final int ST_BENCHMARK = 10;
   private static final int ST_BENCHMARKDONE = 11;
   public static final int ST_CARD_GAME = 12;
   public static final int ST_DYING = 13;
   public static final int ST_EPILOGUE = 14;
   public static final int ST_CREDITS = 15;
   public static final int ST_SAVING = 16;
   public static final int ST_CAMERA = 18;
   public static final int ST_TAF = 19;
   public static final int ST_MIXING = 20;
   public static final int ST_TRAVELMAP = 21;
   public static final int ST_MORE_GAMES = 22;
   static final int UI_IMAGE_SCROLL_XOFS = 45;
   static final int UI_IMAGE_SCROLL_SIZE = 7;
   static final int UI_IMAGE_CHAT_MONSTER_XOFS = 0;
   static final int UI_IMAGE_CHAT_NPC_XOFS = 10;
   static final int UI_IMAGE_CHAT_PLAYER_XOFS = 30;
   static final int UI_IMAGE_CHAT_HEIGHT = 6;
   static final int UI_IMAGE_CHAT_WIDTH = 10;
   static final int UI_IMAGE_PLAYER_HEIGHT = 9;
   static final int UI_IMAGE_PLAYER_WIDTH = 15;
   private static final int ST_PUMP = 2101260;
   private static final int MS_PER_CHAR = 25;
   public static final int SCROLLBAR_SIZE = 7;
   private static final int ICONSIZE = 10;
   public static final int BUFFICONSIZE = 12;
   public static final int STEP_POS = 64;
   public static final int STEP_ANGLE = 256;
   static final int BIT_AM_WALL = 1;
   static final int BIT_AM_SECRET = 2;
   static final int BIT_AM_SECRET_DOOR = 4;
   static final int BIT_AM_ENTRANCE = 8;
   static final int BIT_AM_EXIT = 16;
   static final int BIT_AM_LADDER = 32;
   static final int BIT_AM_EVENTS = 64;
   static final int BIT_AM_VISITED = 128;
   private static final int MAX_HELP_MESSAGES = 16;
   public static byte[] keys_numeric = null;
   private static final int PLAYER_DIALOG_LINES = 3;
   private static final int HELP_DIALOG_LINES = 3;
   private static final int DEFAULT_DIALOG_LINES = 4;
   private static final int SCROLL_DIALOG_LINES = 4;
   private static final int MS_TURN_TIME = 1500;
   private static final int BACKLIGHT_REFRESH_MS = 100;
   private static int lastBacklightRefresh = 0;
   public static int blockInputTime;
   public static boolean changeMapStarted = false;
   private static int storyX;
   private static int storyY;
   private static int storyPage;
   private static int storyTotalPages;
   private static int[] storyIndexes = new int[5];
   public static Image imgFont;
   public static Image imgUIImages;
   public static Image imgMapCursor;
   public static Image imgBuffIcons;
   public static Image imgFabricBg;
   public static Image imgDialogScroll;
   public static Image imgNotebookBg;
   public static Image imgStartupLogo;
   private static Image imgMagGlass = null;
   private static Image imgMagBG = null;
   private static Image imgTMArrow = null;
   private static Image imgTMHighlight = null;
   private static Image imgFlak = null;
   private static Image imgTravelBG = null;
   public static Image imgMixingVials = null;
   public static Image imgMixingSyringe = null;
   public static Image imgMixingHighlight = null;
   public static boolean abortMove;
   public static int SCR_CX;
   public static int SCR_CY;
   public static int saveX;
   public static int saveY;
   public static int saveAngle;
   public static short saveCeilingHeight;
   public static int viewX;
   public static int viewY;
   public static int viewZ;
   public static int viewAngle;
   public static int viewPitch;
   public static int viewRoll;
   public static int destX;
   public static int destY;
   public static int destZ;
   public static int destAngle;
   public static int destPitch;
   public static int destRoll;
   public static int zStep = 0;
   public static int pitchStep = 0;
   public static int prevX;
   public static int prevY;
   public static int viewSin;
   public static int viewCos;
   public static int viewStepX;
   public static int viewStepY;
   public static int viewRightStepX;
   public static int viewRightStepY;
   public static int knockbackX;
   public static int knockbackY;
   public static int knockbackDist;
   public static int knockbackStart;
   public static int knockbackWorldDist;
   public static final int KNOCKBACK_HEIGHT = 10;
   public static int animFrames;
   public static int animPos;
   private static int animAngle;
   public static boolean automapDrawn;
   private static int automapTime;
   public static int specialLootIcon;
   public static boolean showSpeeds;
   public static boolean showLocation;
   public static boolean tellAFriend;
   public static boolean showMoreGames;
   private static boolean combatDone;
   public static int scrollWithBarMaxChars;
   public static int scrollMaxChars;
   public static int dialogMaxChars;
   private static int dialogWithBarMaxChars;
   public static int menuHelpMaxChars;
   public static int subtitleMaxChars;
   public static int menuScrollWithBarMaxChars;
   public static int ingameScrollWithBarMaxChars;
   public static int state;
   public static int oldState;
   public static boolean stateChanged;
   public static int loadMapID;
   public static short loadMapStringID;
   public static int lastMapID;
   public static int startupMap;
   public static int loadType;
   private static int saveType;
   public static boolean renderOnly;
   public static boolean skipIntro;
   public static boolean recentBriefSave;
   public static int shakeTime;
   private static int shakeIntensity;
   public static int shakeX;
   public static int shakeY;
   private static short[] dialogIndexes = new short[1024];
   public static Text dialogBuffer;
   public static EntityDef dialogItem;
   private static int dialogViewLines;
   private static int dialogLineStartTime;
   private static int dialogStartTime;
   private static int dialogTypeLineIdx;
   public static int dialogStyle;
   public static int dialogType;
   private static int dialogFlags;
   private static boolean dialogResumeScriptAfterClosed;
   public static boolean dialogResumeMenu;
   public static boolean dialogClosing;
   public static ScriptThread dialogThread;
   private static int numDialogLines;
   private static int currentDialogLine;
   public static boolean showingLoot = false;
   public static ScriptThread gotoThread = null;
   private static int beforeRender;
   private static int afterRender;
   private static int flushTime;
   private static int loopStart;
   private static int loopEnd;
   private static int pauseTime;
   private static int lastPacifierUpdate = 0;
   private static int lastRenderTime;
   private static int lastFrameTime;
   private static int lastHudTime;
   private static int lastTotalTime;
   private static int debugTime;
   private static int deathTime;
   private static int scrollingTextStart;
   private static int scrollingTextEnd;
   private static int scrollingTextMSLine;
   private static int scrollingTextLines;
   private static int scrollingTextSpacing;
   private static boolean scrollingTextDone;
   private static final int FALLDOWNDEATHTIME = 750;
   private static final int DEATHFADETIME = 2000;
   private static final int TOTALDEATHTIME = 2750;
   private static final int SPD_RENDER = 0;
   private static final int SPD_BSP = 1;
   private static final int SPD_HUD = 2;
   private static final int SPD_WEAPON = 3;
   private static final int SPD_BLIT = 4;
   private static final int SPD_FLUSH = 5;
   private static final int SPD_PAUSED = 6;
   private static final int SPD_LOOP = 7;
   private static final int SPD_TOTAL = 8;
   private static final int SPD_DEBUG = 9;
   private static final int SPD_NUM_FIELDS = 10;
   private static int[] st_fields = new int[10];
   private static boolean st_enabled;
   private static int st_count;
   private static final int MAX_EVENTS = 4;
   private static final int[] events = new int[4];
   public static int numEvents;
   public static final int MAX_STATE_VARS = 9;
   public static int[] stateVars = new int[9];
   public static int pacifierX;
   private static int automapBlinkTime;
   private static boolean automapBlinkState;
   public static boolean endingGame;
   public static boolean running = true;
   private static boolean displaySoftKeys;
   public static int readyWeaponSound;
   public static final int[] displayRect = new int[4];
   public static final int[] screenRect = new int[4];
   public static final int[] viewRect = new int[4];
   public static final int[] hudRect = new int[4];
   public static final int[] menuRect = new int[4];
   public static final int[] cinRect = new int[4];
   public static int softKeyY;
   public static int softKeyLeftID;
   public static int softKeyRightID;
   private static final int HELPMSG_TYPE_ITEM = 1;
   private static final int HELPMSG_TYPE_CODESTRING = 2;
   public static final int HELPMSG_TYPE_MEDAL = 3;
   private static final int[] helpMessageTypes = new int[16];
   private static final int[] helpMessageInts = new int[16];
   private static final Object[] helpMessageObjs = new Object[16];
   private static final byte[] helpMessageThreads = new byte[16];
   public static int numHelpMessages;
   private static int renderSceneCount;
   public static int staleTime;
   public static boolean staleView;
   private static Thread thread;
   private static int keyPressedTime = 0;
   private static int lastKeyPressedTime = 0;
   private static boolean mixingMsg;
   private static boolean msgSeen;
   public static int curStation = 0;
   private static Text cocktailName = null;
   private static Text mixingInstructions = null;
   public static byte[] cocktailRecipes = null;
   public static short[] cocktailNames = null;
   public static final int KPHASE_FREE = 0;
   public static final int KPHASE_SETANGLE = 1;
   public static final int KPHASE_SETDIST = 2;
   public static final int KPHASE_KICKING = 3;
   public static final int KPHASE_SHOWGRID = 4;
   public static final int KPHASE_HIGHSCORE = 5;
   public static int chickenDestFwd;
   public static int chickenDestRight;
   public static boolean isChickenKicking = false;
   public static int kickingPhase = 0;
   public static int kickingDir = 0;
   private static Image hKickingBar = null;
   private static Image vKickingBar = null;
   public static int lastScore = 0;
   public static int curScore = 0;
   public static int gridTime = 0;
   public static int gridIdx = 0;
   public static int highScoreIndex = -1;
   private static int kickHPos = 0;
   private static int kickVPos = 0;
   public static boolean kickingFromMenu = false;
   public static boolean pushedWall = false;
   public static int pushedTime = 0;
   Image backImage;
   Graphics backBuffer = new Graphics();
   public static final int DEF_SCR_HEIGHT = 220;
   public static final int MIN_RENDER_SIZE = 128;
   public SDKMoreGames mgInstance;
   Object mfont;
   static final Object[] mgFonts = new Object[5];
   public static final int REPAINT_CLEAR = 1;
   public static final int REPAINT_SOFTKEYS = 2;
   public static final int REPAINT_MENU = 4;
   public static final int REPAINT_STARTUP_LOGO = 8;
   public static final int REPAINT_HUD = 16;
   public static final int REPAINT_PARTICLES = 32;
   public static final int REPAINT_VIEW3D = 64;
   public static final int REPAINT_LOADING_BAR = 128;
   public static int repaintFlags = 0;
   public static int totalFrameTime;
   private static boolean ignoreFrameInput;
   public static boolean forcePump;
   private static final int ALWAYS_PUMP_MASK = 321390;
   public static boolean updateFacingEntity = false;
   static final int NOT_QUITE_HALF_TILE = 28;
   static final int NUM_TRACE_TILES = 6;
   static final int DEST_MATRIX_SHIFT = 8;
   static final int POS_PRECISION = 6;
   public static byte[] viewStepValues = new byte[]{64, 0, 64, -64, 0, -64, -64, -64, -64, 0, -64, 64, 0, 64, 64, 64};
   private static final int STORE_V_BORDER = 20;
   private static final int STORE_H_BORDER = 10;
   private static final int BRIEFING_TEXT_LS = 16;
   public static final int SCROLL_TYPE_NORMAL = 0;
   public static final int SCROLL_TYPE_DIALOG = 1;
   public static final int NOTEBOOK_REPEAT_W = 38;
   public static final int SCROLL_REPEAT_ROWS = 6;
   public static final int SCROLL_REPEAT_COLS = 14;
   public static final int SCROLL_REPEAT_FILL = 6;
   static final int MAX_TEXT_HEIGHT = 220;
   static final int PUSH_WALL_TIME = 1000;
   public static boolean mediaLoading;
   private static int pacLogoTime = 0;
   private static int pacLogoIndex = -1;
   private static final int LOGO_TIME = 1500;
   private static final int[] dialogRect = new int[4];
   public static final int NPC_DLG_COLOR = -16766876;
   public static final int MONSTER_DLG_COLOR = -8388608;
   public static final int PLAYER_DLG_COLOR = -11577833;
   public static final int CHEST_DLG_COLOR = -16754176;
   public static final int CHEST_OFFER_DLG_COLOR = -5142015;
   public static final int MEDAL_DLG_COLOR = -16777114;
   public static final int PLYR_MAX_DARKEN_FRAC = 96;
   public static final int PLYR_BLEND_ONE = 256;
   public static final int PLYR_FRAC_RECIP = 160;
   public static final int CHAT_X_OFS = 64;
   public static final int INT_OPT_HEIGHT = 17;
   public static final int INT_OPT_SPACE = 2;
   public static final int INT_OPT_XOFS = 3;
   public static final int DIALOG_BOX_HEIGHT = 15;
   public static final int OPT_SPACE = 10;
   static final int MIN_DEATH_Z = 18;
   static final int MAX_DEATH_Z = 20;
   static final int MAX_DEATH_PITCH = 96;
   static final int MAX_DEATH_ROLL = 16;
   static final int RENDER_SCENE_OFS = 8;
   static int CAMERAVIEW_BAR_HEIGHT;
   public static final int LD_BOX_WIDTH = 150;
   public static final int LD_BOX_XOFS = 9;
   private static final int LD_BOX_HEIGHT = 44;
   private static final int LOADING_BACKGROUND = 1;
   private static final int LOADING_BAR = 2;
   private static final int LOADING_ALL = 3;
   private static int loadingFlags = 0;
   private static short loadingStringType = -1;
   private static short loadingStringID = -1;
   private static final int MAXUPDATE = 1000;
   private static final int MINUPDATE = 75;
   static boolean isZoomedIn = false;
   static int zoomFOV;
   static int zoomDestFOV;
   static int zoomMinFOVPercent;
   static int zoomCurFOVPercent;
   static int zoomTime;
   static int zoomAngle;
   static int zoomPitch;
   static int zoomStateTime;
   static int zoomAccuracy;
   static int zoomMaxAngle;
   static int zoomCollisionX;
   static int zoomCollisionY;
   static int zoomCollisionZ;
   static int zoomTurn = 0;
   static final int ZOOM_ANIM_TIME = 360;
   static final int ZOOM_FADE_TIME = 500;
   static final int ZOOM_SHIFT = 8;
   static final int ZOOM_HUNDRED_PERCENT = 256;
   static final int ZOOM_BASE_ACCURACY = 26;
   static final int ZOOM_FG42_PERCENT = 156;
   static final int ZOOM_STEP_SCALAR = 20;
   private static final int numAdded = 0;
   private static final int firstIng = 3;
   private static final int secondIng = 2;
   private static final int thirdIng = 1;
   private static final int curSelection = 4;
   private static final int curCocktail = 5;
   private static final int stationRed = 6;
   private static final int stationGrn = 7;
   private static final int stationBlu = 8;
   private static final int invalidColor = 3;
   private static final int MIXING_FILL_XOFS = 16;
   private static final int MIXING_FILL_SIZE = 20;
   private static final int MIXING_SCREW_XOFS = 36;
   private static final int MIXING_SCREW_SIZE = 10;
   private static final int MIXING_VIAL_XOFS = 16;
   private static final int MIXING_VIAL_YOFS = 20;
   private static final int MIXING_VIAL_WIDTH = 12;
   private static final int MIXING_VIAL_HEIGHT = 27;
   private static final int MIXING_EMPTY_VIAL_WIDTH = 16;
   private static final int MIXING_EMPTY_VIAL_HEIGHT = 47;
   private static final int MIXING_VIAL_XSHIFT = 2;
   private static final int MIXING_VIAL_YSHIFT = 19;
   private static final int MIXING_GLOW_XSHIFT = -7;
   private static final int MIXING_GLOW_YSHIFT = -4;
   private static final int MIXING_SYRINGE_YOFS = 126;
   private static final int MIXING_SYRINGE_BREAK = 52;
   private static final int MIXING_SYRINGE_WIDTH = 114;
   private static final int MIXING_SYRINGE_FULL_WIDTH = 164;
   private static final int MIXING_SYRINGE_HEIGHT = 29;
   private static final int MIXING_SYRINGE_FILL_XOFS = 36;
   private static final int MIXING_SYRINGE_FILL_YOFS = 6;
   private static final int MIXING_SYRINGE_FILL_WIDTH = 30;
   private static final int MIXING_SYRINGE_FILL_HEIGHT = 15;
   private static final int LIGHTGREY = -7039852;
   private static final int DARKGREY = -11711155;
   private static final int BACKCOLOR = -4737097;
   private static final int MIXINGRED = -3407872;
   private static final int MIXINGGRN = -16724992;
   private static final int MIXINGBLU = -16777012;
   private static final int VIAL_LIGHT = -3814192;
   private static final int VIAL_DARK = -13223881;
   private static final int MIXING_VIAL_SPACE = 22;
   private static final int MIXING_VIAL_FULL_WIDTH = 92;
   private static final int[] REFLECTION_OFFSET = new int[]{-1, 0, 4, 15, 16};
   public static final int BG_COLOR = -3036160;
   public static final int BORDER_COLOR = -16777216;
   public static final int BASE_Y_POS = 56;
   public static final int KICKING_MAP = 4;
   public static final int KICK_TIME = 0;
   private static final int KICK_CHAR = 1;
   private static final int KICK_TIMESPRESSED = 2;
   private static final int KICK_LASTNUM = 3;
   private static final int KICK_KEYTIME = 4;
   private static final int HBAR_W = 126;
   private static final int HBAR_H = 13;
   private static final int HBAR_INNER = 102;
   private static final int VBAR_H = 105;
   private static final int VBAR_W = 13;
   private static final int VBAR_INNER = 84;
   private static final int LERPTIME1 = 1000;
   private static final int LERPTIME2 = 800;
   private static final int BAR_PADDING = 12;
   private static final int KNOB_W = 4;
   public static final int POINT_TOP_X = 1;
   public static final int POINT_TOP_Y = 18;
   private static final int[] CKPOINTS = new int[]{5, 20, 20, 20, 5, 5, 20, 30, 20, 5, 5, 20, 20, 20, 5, 5, 5, 5, 5, 5};
   private static final int NEXT_POS_TIME = 2000;
   public static final int MAX_HIGH_SCORE = 5;
   public static short[] highScores = new short[]{290, 250, 200, 150, 145};
   public static char[] highScoreInitials = new char[]{'B', 'E', 'N', 'K', 'D', 'S', 'B', 'L', 'E', 'M', 'C', 'R', 'K', 'A', 'K'};
   static final int SCORE_PADDING_X = 20;
   static final int SCORE_PADDING_Y = 45;
   static final int SCORE_TEXT_START = 70;
   static final int NUM_GRID_W = 5;
   static final int NUM_GRID_H = 4;
   static final int GRID_SIZE = 20;
   static final int GRID_W = 100;
   static final int GRID_H = 80;
   static final int[] GRID_COLORS = new int[]{-13421773, -13421773, -13421773, -13421773, -13421773, -13421773, -2775808, -2775808, -2775808, -13421773, -13421773, -2775808, -2818048, -2775808, -13421773, -13421773, -2775808, -2775808, -2775808, -13421773};
   private static final String[] numCharTable = new String[]{"0", "1", "ABC2", "DEF3", "GHI4", "JKL5", "MNO6", "PQRS7", "TUV8", "WXYZ9"};
   private static final int TM_TIME = 0;
   private static final int TM_ARROW_DONE = 1;
   private static final int TM_MAG_DONE = 2;
   private static final int TM_FLASH_DONE = 3;
   private static final int TM_FINISHED = 4;
   private static final int MAG_LERP_TIME = 1200;
   private static final int ARROW_WIPE_TIME = 700;
   private static final int HIGHLIGHT_LERP_TIME = 500;
   private static final int[] ARROW_DATA = new int[]{61, 37, 0, 2, 68, 65, 3, 1, 82, 111, 5, 0, 80, 106, 1, 2};
   private static final int NUM_ARROW_DATA = 4;
   private static final int[] SELECTORPOS = new int[]{-13, 15, -14, 35, -12, 47, -16, 4, -12, 20, -4, 38, -12, 43, -13, 26, -12, 8};
   private static final int MAG_WIDTH = 122;
   private static final int MAG_HEIGHT = 122;
   private static final int[] MAG_DATA = new int[]{15, 26, 0, 26, 29, 39, 49, 2, 83, 52, 12, 68, 1, 22, 119};
   public static final int FLAK_SIZE = 64;

   public static byte[] getByteArrayFromFile(String var0) {
      byte[] var3 = null;

      try {
         InputStream var2 = "".getClass().getResourceAsStream(var0);
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();

         for(int var4 = var2.read(); var4 >= 0; var4 = var2.read()) {
            var1.write(var4);
         }

         var3 = var1.toByteArray();
         var2.close();
         var1.close();
      } catch (Exception var5) {
         App.Error(var5, 1);
      }

      return var3;
   }

   Canvas(MIDlet var1) {
      this.setFullScreenMode(true);
      displayRect[0] = 0;
      displayRect[1] = 0;
      displayRect[2] = this.getWidth();
      displayRect[3] = this.getHeight();
      softKeyY = displayRect[3];
      repaintFlags |= 1;
      this.backImage = Image.createImage(displayRect[2], displayRect[3]);
      this.backBuffer.setGraphics(this.backImage.getGraphics());
      this.flushGraphics();
      Canvas var10004 = App.canvas;
      int var3 = displayRect[2];
      Canvas var10005 = App.canvas;
      this.mgInstance = new SDKMoreGames(var3, displayRect[3]) {
         public Image getImage(String var1) {
            try {
               App.beginImageLoading();
               Image var2;
               if (var1.toLowerCase().compareTo("24342") == 0) {
                  var2 = App.loadImageFromIndex(23);
               } else if (var1.toLowerCase().compareTo("24922") == 0) {
                  var2 = App.loadImageFromIndex(24);
               } else if (var1.toLowerCase().compareTo("27022") == 0) {
                  var2 = App.loadImageFromIndex(25);
               } else {
                  var2 = App.loadImageFromIndex(26);
               }

               App.endImageLoading();
               return var2;
            } catch (Exception var3) {
               return null;
            }
         }

         protected void drawBackground(javax.microedition.lcdui.Graphics var1) {
            var1.setColor(0);
            Canvas var10003 = App.canvas;
            int var2 = Canvas.displayRect[2];
            Canvas var10004 = App.canvas;
            var1.fillRect(0, 0, var2, Canvas.displayRect[3]);
         }
      };
      SDKTextUtils.setMidlet(App.app);
      SDKTextUtils.loadTextHeader(getByteArrayFromFile("/hdr"));
      this.mgInstance.setProductData(getByteArrayFromFile("/moregames"));
      SDKTextUtils.loadStringsChunk(1);
      App.beginImageLoading();
      imgFont = App.loadImageFromIndex(6);
      App.endImageLoading();
      this.mfont = SDKTextUtils.loadFont(imgFont, getByteArrayFromFile("/Font.rff"));
      System.out.print("shit" + this.mfont);

      for(int var2 = 0; var2 < 5; ++var2) {
         mgFonts[var2] = this.mfont;
      }

      this.mgInstance.setFonts(mgFonts);
   }

   final void start() {
      thread = new Thread(this);
      thread.start();
   }

   final void stop() {
      running = false;

      try {
         thread.join();
      } catch (InterruptedException var2) {
         App.Error(var2, -1);
      }

   }

   public static final boolean startup() {
      OSC_CYCLE = App.TBL_ENUMS_OSC_CYCLE;
      keys_numeric = App.TBL_CANVAS_KEYSNUMERIC;
      cocktailRecipes = App.TBL_COCKTAIL_RECIPES;
      cocktailNames = App.TBL_COCKTAIL_NAMES;
      ignoreFrameInput = false;
      blockInputTime = 0;
      showLocation = false;
      specialLootIcon = -1;
      numEvents = 0;
      dialogItem = null;
      dialogViewLines = 0;
      lastMapID = 0;
      loadMapID = 0;
      loadMapStringID = -1;
      automapDrawn = false;
      loadType = 0;
      saveType = 0;
      st_count = 0;
      knockbackDist = 0;
      numHelpMessages = 0;
      destZ = 36;
      viewZ = 36;
      int var0 = 0;
      int var1 = 0;
      screenRect[2] = 0;
      screenRect[3] = 0;
      String var2 = App.app.getAppProperty("App-ViewWidth");
      String var3 = App.app.getAppProperty("App-ViewHeight");
      if (var2 != null && var2.length() != 0 && var3 != null && var3.length() != 0) {
         var0 = Integer.parseInt(var2);
         var1 = Integer.parseInt(var3);
      }

      int[] var4 = viewRect;
      int[] var5 = screenRect;
      int[] var6 = displayRect;
      int[] var7 = cinRect;
      var6[2] &= -2;
      var5[2] = var6[2];
      var5[3] = var6[3];
      dialogMaxChars = (var6[2] - 3) / 7;
      dialogWithBarMaxChars = (var6[2] - 10) / 7;
      scrollMaxChars = dialogMaxChars;
      scrollWithBarMaxChars = dialogWithBarMaxChars;
      menuScrollWithBarMaxChars = (var6[2] - 9) / 7;
      ingameScrollWithBarMaxChars = (var6[2] - 25) / 7;
      menuHelpMaxChars = (var6[2] - 16) / 7;
      subtitleMaxChars = var6[2] / 7;
      if (!Hud.startup()) {
         return false;
      } else {
         int var8 = var5[3] - 25 - 25;
         if (var6[3] >= 154) {
            displaySoftKeys = true;
            softKeyY = var6[3] - 26;
            if (var6[3] - var5[3] < 26) {
               var8 -= 26 - (var6[3] - var5[3]);
            }
         } else {
            softKeyY = var6[3];
         }

         var8 &= -2;
         var5[3] = var8 + 25 + 25;
         var5[0] = (var6[2] - var5[2]) / 2;
         if (displaySoftKeys) {
            var5[1] = (softKeyY - var5[3]) / 2;
         } else {
            var5[1] = (var6[3] - var5[3]) / 2;
         }

         SCR_CX = var5[2] / 2;
         SCR_CY = var5[3] / 2;
         var4[0] = var5[0];
         var4[1] = var5[1] + 25;
         var4[2] = var5[2];
         var4[3] = var8;
         if (var0 != 0 && var1 != 0) {
            var4[0] += var4[2] - var0 >> 1;
            var4[1] += var4[3] - var1 >> 1;
            var4[2] = var0;
            var4[3] = var1;
         }

         hudRect[0] = var6[0];
         hudRect[1] = var5[1];
         hudRect[2] = var6[2];
         hudRect[3] = var5[3];
         menuRect[0] = var6[0];
         menuRect[1] = var6[1];
         menuRect[2] = var6[2];
         menuRect[3] = var6[3] - 26;
         CAMERAVIEW_BAR_HEIGHT = 4;
         var7[0] = 0;
         var7[1] = CAMERAVIEW_BAR_HEIGHT;
         var7[2] = var4[2];
         var7[3] = var4[3] - CAMERAVIEW_BAR_HEIGHT * 2;
         if (var5[1] + var5[3] == softKeyY - 1) {
            --softKeyY;
         }

         boolean var9 = false;
         String var10 = App.app.getAppProperty("App-Frames");
         int var11;
         if (var10 != null && var10.length() != 0) {
            var11 = Integer.parseInt(var10);
            if (var11 < 2) {
               var11 = 2;
            } else if (var11 > 64) {
               var11 = 64;
            }
         } else {
            var11 = 4;
         }

         setAnimFrames(var11);
         var10 = App.app.getAppProperty("App-StartupMap");
         if (var10 != null && var10.length() != 0) {
            startupMap = Integer.parseInt(var10);
         } else {
            startupMap = 1;
         }

         var10 = App.app.getAppProperty("App-SkipIntro");
         if (var10 != null && var10.length() != 0) {
            skipIntro = Integer.parseInt(var10) != 0;
         } else {
            skipIntro = false;
         }

         var10 = System.getProperty("loadmap");
         if (var10 != null && var10.length() != 0 && var10.startsWith("m_0")) {
            var10 = var10.substring(3);
            skipIntro = true;
            startupMap = Integer.parseInt(var10);
         } else if (var10 != null && var10.startsWith("m_test")) {
            skipIntro = true;
            startupMap = 10;
         }

         var10 = App.app.getAppProperty("App-TellAFriend");
         if (var10 != null && var10.length() != 0) {
            tellAFriend = Integer.parseInt(var10) != 0;
         } else {
            tellAFriend = false;
         }

         App.beginImageLoading();
         imgDialogScroll = App.loadImageFromIndex(3);
         imgFabricBg = App.loadImageFromIndex(4);
         Hud.imgAmmoIcons = App.loadImageFromIndex(10);
         Hud.imgAttArrow = App.loadImageFromIndex(11);
         Hud.imgPortraitsSM = App.loadImageFromIndex(15);
         imgBuffIcons = App.loadImageFromIndex(17);
         imgNotebookBg = App.loadImageFromIndex(27);
         Hud.imgSoftKeyFill = App.loadImageFromIndex(30);
         imgUIImages = App.loadImageFromIndex(36);
         App.endImageLoading();
         lastBacklightRefresh = 0;
         chickenDestFwd = 2;
         chickenDestRight = 0;
         return true;
      }
   }

   void shutdown() {
   }

   public void flushGraphics() {
      this.backBuffer.resetScreenSpace();
      this.backBuffer.setGraphics(this.backImage.getGraphics());
      this.backPaint(this.backBuffer);
      this.repaint();
      this.serviceRepaints();
   }

   protected void paint(javax.microedition.lcdui.Graphics var1) {
      var1.drawImage(this.backImage, 0, 0, 0);
   }

   protected void backPaint(Graphics var1) {
      var1.clearClipRect();
      if ((repaintFlags & 1) != 0) {
         repaintFlags &= -2;
         var1.eraseRgn(displayRect);
      }

      if ((repaintFlags & 64) != 0) {
         repaintFlags &= -65;
         Render.drawRGB(var1);
      }

      if ((repaintFlags & 32) != 0) {
         repaintFlags &= -33;
         ParticleSystem.renderSystems(var1);
      }

      if ((repaintFlags & 16) != 0) {
         repaintFlags &= -17;
         Hud.draw(var1);
      }

      if (state == 2) {
         drawScrollingText(var1);
      } else if (state == 9) {
         drawStory(var1);
      } else if (state == 14) {
         drawScrollingText(var1);
      } else if (state == 15) {
         drawCredits(var1);
      } else if (state == 21) {
         drawTravelMap(var1);
      } else if (state == 6) {
         drawAutomap(var1, automapDrawn);
         automapDrawn = true;
      } else if (state == 20) {
         mixingState(var1);
      } else if (state == 8) {
         dialogState(var1);
      } else if (state == 12) {
         CardGames.updateGame(var1);
      } else if (state == 3 || state == 5) {
         if (state == 3 && isChickenKicking) {
            if (kickingPhase == 5 && stateVars[0] < App.gameTime) {
               drawHighScore(var1);
            } else if (kickingPhase == 4 && gridTime >= App.gameTime) {
               drawKickingGrid(var1);
            }
         }

         if (isChickenKicking && kickingPhase < 4) {
            drawKickingBars(var1);
         }
      }

      if (state == 22) {
         SDKTextUtils.setGraphics(var1.g);
         this.mgInstance.paint(var1.g);
      }

      if ((repaintFlags & 2) != 0) {
         repaintFlags &= -3;
         drawSoftKeys(var1);
      }

      if ((repaintFlags & 4) != 0) {
         repaintFlags &= -5;
         MenuSystem.paint(var1);
      }

      if ((repaintFlags & 8) != 0) {
         repaintFlags &= -9;
         var1.fillRegion(imgFabricBg, 0, 0, displayRect[2], displayRect[3]);
         var1.drawImage(imgStartupLogo, displayRect[2] / 2, displayRect[3] / 2, 3);
      }

      if ((repaintFlags & 128) != 0) {
         repaintFlags &= -129;
         drawLoadingBar(var1);
      }

      int var2;
      int var4;
      if (state == 10) {
         if (st_enabled) {
            var2 = viewRect[1];
            debugTime = App.getUpTimeMs();
            Text var3 = Text.getLargeBuffer();
            var3.setLength(0);
            var3.append("Rndr ms: ");
            var3.append(st_fields[0] / st_count).append('.');
            var3.append(st_fields[0] * 100 / st_count - st_fields[0] / st_count * 100);
            var1.drawString(var3, viewRect[0], var2, 0);
            var2 += 12;
            var3.setLength(0);
            var3.append("Bsp ms: ");
            var3.append(st_fields[1] / st_count).append('.');
            var3.append(st_fields[1] * 100 / st_count - st_fields[1] / st_count * 100);
            var1.drawString(var3, viewRect[0], var2, 0);
            var2 += 12;
            var3.setLength(0);
            var3.append(" Hud ms: ");
            var3.append(st_fields[2] / st_count).append('.');
            var3.append(st_fields[2] * 100 / st_count - st_fields[2] / st_count * 100);
            var1.drawString(var3, viewRect[0], var2, 0);
            var2 += 12;
            var4 = st_fields[4] + st_fields[5];
            var3.setLength(0);
            var3.append("Blit ms: ");
            var3.append(var4 / st_count).append('.');
            var3.append(var4 * 100 / st_count - var4 / st_count * 100);
            var1.drawString(var3, viewRect[0], var2, 0);
            var2 += 12;
            var3.setLength(0);
            var3.append("Paus ms: ");
            var3.append(st_fields[6] / st_count).append('.');
            var3.append(st_fields[6] * 100 / st_count - st_fields[6] / st_count * 100);
            var1.drawString(var3, viewRect[0], var2, 0);
            var2 += 12;
            var3.setLength(0);
            var3.append(" Dbg ms: ");
            var3.append(st_fields[9] / st_count).append('.');
            var3.append(st_fields[9] * 100 / st_count - st_fields[9] / st_count * 100);
            var1.drawString(var3, viewRect[0], var2, 0);
            var2 += 12;
            var3.setLength(0);
            var3.append("Loop ms: ");
            var3.append(st_fields[7] / st_count).append('.');
            var3.append(st_fields[7] * 100 / st_count - st_fields[7] / st_count * 100);
            var1.drawString(var3, viewRect[0], var2, 0);
            var2 += 12;
            var3.setLength(0);
            var3.append("Totl ms: ");
            var3.append(st_fields[8] / st_count).append('.');
            var3.append(st_fields[8] * 100 / st_count - st_fields[8] / st_count * 100);
            var1.drawString(var3, viewRect[0], var2, 0);
            var2 += 12;
            var3.setLength(0);
            var3.append(st_count);
            var1.drawString(var3, viewRect[0], var2, 0);
            var2 += 12;
            debugTime = App.getUpTimeMs() - debugTime;
            var3.dispose();
         }
      } else if (state == 18 || state == 3 || state == 5) {
         int var7;
         if (showSpeeds) {
            var2 = afterRender - beforeRender;
            var7 = Hud.drawTime;
            if (lastFrameTime == App.time) {
               beforeRender = 0;
               afterRender = 0;
               lastRenderTime = var2;
               lastHudTime = var7;
               lastTotalTime = totalFrameTime;
            }

            var4 = viewRect[1];
            int var5 = App.getUpTimeMs() - totalFrameTime;
            totalFrameTime = App.getUpTimeMs();
            Text var6 = Text.getLargeBuffer();
            var6.setLength(0);
            var6.append("ms: ");
            var6.append(lastRenderTime).append('/');
            var6.append(Render.clearColorBuffer).append('/');
            var6.append(Render.bltTime).append('/');
            var6.append(var5);
            var1.drawString(var6, viewRect[0], var4, 0);
            var4 += 12;
            var6.setLength(0);
            var6.append("li: ");
            var6.append(Render.lineRasterCount).append('/');
            var6.append(Render.lineCount);
            var1.drawString(var6, viewRect[0], var4, 0);
            var4 += 12;
            var6.setLength(0);
            var6.append("sp: ");
            var6.append(Render.spriteRasterCount).append('/');
            var6.append(Render.spriteCount).append('/');
            var6.append(Render.numMapSprites);
            var1.drawString(var6, viewRect[0], var4, 0);
            var4 += 12;
            if (Render.renderMode == 63) {
               var6.setLength(0);
               var6.append("cnt: ");
               var6.append(Span.spanCalls).append('/');
               var6.append(Span.spanPixels);
               var1.drawString(var6, viewRect[0], var4, 0);
               var4 += 12;
               var6.setLength(0);
               var6.append("tris: ");
               var6.append(Span.countBackFace).append('/');
               var6.append(Span.countDrawn);
               var1.drawString(var6, viewRect[0], var4, 0);
               var4 += 12;
            }

            var6.dispose();
         } else if (showLocation) {
            Text var8 = Text.getSmallBuffer();
            var8.setLength(0);
            var8.append(viewX >> 6);
            var8.append(' ');
            var8.append(viewY >> 6);
            var8.append(' ');
            var7 = viewAngle & 1023;
            if (var7 == 256) {
               var8.append('N');
            } else if (var7 == 0) {
               var8.append('E');
            } else if (var7 == 768) {
               var8.append('S');
            } else if (var7 == 512) {
               var8.append('W');
            } else if (var7 == 128) {
               var8.append("NE");
            } else if (var7 == 384) {
               var8.append("NW");
            } else if (var7 == 896) {
               var8.append("SE");
            } else if (var7 == 640) {
               var8.append("SW");
            }

            var1.drawString(var8, viewRect[0], 62, 0);
            var8.dispose();
         }
      }

      var1.resetScreenSpace();
   }

   public void run() {
      thread.setPriority(10);
      Resource.initTableLoading();
      App.loadTables();
      if (!App.app.startup()) {
         App.Error(57);
      } else {
         try {
            backToMain(true);

            while(true) {
               while(running) {
                  App.lastTime = App.time;
                  App.time = App.getUpTimeMs();
                  if (st_enabled) {
                     ++st_count;
                     int[] var10000 = st_fields;
                     var10000[0] += Render.frameTime;
                     var10000 = st_fields;
                     var10000[1] += Render.bspTime;
                     var10000 = st_fields;
                     var10000[2] += Hud.drawTime;
                     var10000 = st_fields;
                     var10000[4] += Render.bltTime;
                     var10000 = st_fields;
                     var10000[5] += pauseTime - flushTime;
                     var10000 = st_fields;
                     var10000[6] += loopEnd - pauseTime;
                     var10000 = st_fields;
                     var10000[8] += App.time - App.lastTime;
                     var10000 = st_fields;
                     var10000[3] += Combat.renderTime;
                     var10000 = st_fields;
                     var10000[9] += debugTime;
                     var10000 = st_fields;
                     var10000[7] += loopEnd - loopStart;
                  }

                  loopStart = App.getUpTimeMs();
                  if (!Game.pauseGameTime && state != 1) {
                     App.gameTime += App.time - App.lastTime;
                  }

                  if (state != 8 && state != 1 && Game.isInputBlockedByScript()) {
                     clearEvents();
                  }

                  runInputEvents();
                  if (state != 1 || state == 1 && MenuSystem.menu != 20) {
                     Game.numTraceEntities = 0;
                     Game.UpdatePlayerVars();
                     Game.gsprite_update(App.time);
                     Game.runScriptThreads(App.gameTime);
                  }

                  Game.updateAutomap = false;
                  if (state == 3) {
                     Game.updateAutomap = true;
                     if (numHelpMessages == 0 && Game.queueAdvanceTurn) {
                        Game.snapMonsters(true);
                        Game.advanceTurn();
                     }

                     playingState();
                     if (kickingPhase == 4 && gridTime < App.gameTime) {
                        repaintFlags |= 16;
                        Hud.repaintFlags |= 4;
                        kickingPhase = 0;
                        Game.executeStaticFunc(10);
                     }

                     if (state == 3) {
                        repaintFlags |= 16;
                        Hud.repaintFlags |= 11;
                     }
                  } else if (state != 15 && state != 21 && state != 20 && state != 12) {
                     if (state == 5) {
                        Game.updateAutomap = true;
                        combatState();
                     } else if (state == 2) {
                        if (Game.hasSeenIntro && numEvents != 0 || scrollingTextDone) {
                           dialogBuffer.dispose();
                           dialogBuffer = null;
                           Game.hasSeenIntro = true;
                           Game.saveConfig();
                           System.gc();
                           backToMain(false);
                        }
                     } else if (state == 14) {
                        if (scrollingTextDone) {
                           disposeEpilogue();
                        }
                     } else if (state == 9) {
                        if (storyPage >= storyTotalPages) {
                           disposeIntro();
                        }
                     } else if (state == 16) {
                        if ((saveType & 2) == 0 && (saveType & 1) == 0) {
                           App.Error(48);
                        } else {
                           int var1;
                           int var2;
                           int var3;
                           if ((saveType & 8) != 0) {
                              if (Game.spawnParam != 0) {
                                 var1 = 32 + ((Game.spawnParam & 31) << 6);
                                 var2 = 32 + ((Game.spawnParam >> 5 & 31) << 6);
                                 var3 = (Game.spawnParam >> 10 & 255) << 7;
                                 Game.saveState(lastMapID, loadMapID, var1, var2, var3, 0, var1, var2, saveType);
                              } else {
                                 Game.saveState(lastMapID, loadMapID, 0, 0, 0, 0, 0, 0, saveType);
                              }
                           } else if ((saveType & 16) != 0) {
                              var1 = 32 + ((Game.spawnParam & 31) << 6);
                              var2 = 32 + ((Game.spawnParam >> 5 & 31) << 6);
                              var3 = (Game.spawnParam >> 10 & 255) << 7;
                              Game.saveState(loadMapID, MenuSystem.LEVEL_STATS_nextMap, var1, var2, var3, 0, var1, var2, saveType);
                           } else {
                              Game.saveState(loadMapID, loadMapID, destX, destY, destAngle, viewPitch, prevX, prevY, saveType);
                           }

                           Hud.addMessage((short)1, (short)37);
                        }

                        if ((saveType & 4) != 0) {
                           backToMain(false);
                        } else if ((saveType & 64) != 0) {
                           App.app.shutdown();
                        } else if ((saveType & 8) != 0) {
                           setState(21);
                        } else if ((saveType & 16) != 0) {
                           MenuSystem.setMenu(1);
                        } else if ((saveType & 256) != 0) {
                           MenuSystem.setMenu(22);
                        } else {
                           if ((saveType & 128) != 0) {
                              MenuSystem.returnToGame();
                           }

                           setState(3);
                        }

                        saveType = 0;
                        clearEvents();
                     } else if (state == 7) {
                        if (loadType == 0) {
                           if (!loadMedia()) {
                              this.flushGraphics();
                              continue;
                           }
                        } else if (loadType == 3) {
                           boolean var6 = Player.enableHelp;
                           Player.enableHelp = false;
                           if (!loadMedia()) {
                              this.flushGraphics();
                              continue;
                           }

                           Player.enableHelp = var6;
                           startKicking(true);
                        } else {
                           Game.loadState(loadType);
                           Hud.addMessage((short)1, (short)38);
                           loadType = 0;
                        }
                     } else if (state == 1) {
                        menuState();
                     } else if (state == 8) {
                        Game.updateLerpSprites();
                        updateView();
                        repaintFlags |= 48;
                        Hud.repaintFlags |= 15;
                     } else if (state == 6) {
                        Game.updateAutomap = true;
                        automapState();
                     } else if (state == 13) {
                        dyingState();
                     } else if (state == 18) {
                        if (Game.activeCameraKey != -1) {
                           Game.activeCamera.Update(Game.activeCameraKey, App.gameTime - Game.activeCameraTime);
                        }

                        Game.updateLerpSprites();
                        updateView();
                        if (state == 18 && App.gameTime > Game.cinUnpauseTime && softKeyRightID == -1) {
                           clearLeftSoftKey();
                           setRightSoftKey((short)1, (short)39);
                        }
                     } else if (state == 10) {
                        renderOnlyState();
                     } else if (state == 22) {
                        if (this.mgInstance.isActive()) {
                           this.mgInstance.update();
                        } else {
                           MenuSystem.setMenu(3);
                        }
                     } else {
                        App.Error(51);
                     }
                  }

                  if (state == 16 || state == 7) {
                     repaintFlags &= -65;
                  }

                  flushTime = App.getUpTimeMs();
                  this.flushGraphics();
                  if (keyPressedTime != 0) {
                     lastKeyPressedTime = App.getUpTimeMs() - keyPressedTime;
                     keyPressedTime = 0;
                  }

                  pauseTime = App.getUpTimeMs();

                  do {
                     try {
                        Thread.sleep(5L);
                     } catch (InterruptedException var4) {
                     }

                     App.checkPausedState(5);
                  } while(!shouldPump() && !this.isShown());

                  showBacklight();
                  loopEnd = App.getUpTimeMs();
                  stateChanged = false;
                  if (ignoreFrameInput) {
                     clearEvents();
                     ignoreFrameInput = false;
                  }
               }

               return;
            }
         } catch (Exception var5) {
            App.Error(var5, 51);
         }
      }
   }

   public void hideNotify() {
      Sound.soundStop();
   }

   public void showNotify() {
   }

   public static void clearEvents() {
      synchronized(App.canvas) {
         numEvents = 0;
      }

      ignoreFrameInput = true;
   }

   public static boolean shouldPump() {
      int var0 = App.gameTime + (App.getUpTimeMs() - App.lastTime);
      synchronized(App.canvas) {
         if (numEvents > 0) {
            return true;
         }
      }

      if (!stateChanged && !forcePump && (1 << state & 321390) == 0) {
         Game.runScriptThreads(var0);
         return (1 << state & 2101260) != 0 && (staleView || viewX != destX || viewY != destY || viewZ != destZ || viewAngle != destAngle || Game.interpolatingMonsters || Game.monstersTurn != 0 || Game.activeSprites > 0 || Game.numLerpSprites > 0 || Hud.msgCount > 0 || Hud.playerStartHealth != Hud.playerDestHealth || Player.ce.getStat(0) <= 0);
      } else {
         forcePump = false;
         return true;
      }
   }

   public static void loadRuntimeData() {
      System.gc();
      App.loadRuntimeImages();
      App.checkPeakMemory("after loadRuntimeData");
   }

   public static void freeRuntimeData() {
      Sound.soundStop();
      App.freeRuntimeImages();
   }

   public static final void startShake(int var0, int var1, int var2) {
      if (!Game.skippingCinematic) {
         if (var1 != 0) {
            shakeTime = App.time + var0;
            shakeIntensity = 2 * var1;
            staleTime = shakeTime + 1;
         }

         if (var2 != 0 && Sound.isVibrateEnabled()) {
            App.display.vibrate(var2);
         }

      }
   }

   public static final void setState(int var0) {
      stateChanged = true;

      for(int var1 = 0; var1 < stateVars.length; ++var1) {
         stateVars[var1] = 0;
      }

      if (state == 6) {
         Player.unpause(App.time - automapTime);
      } else if (state == 1) {
         Player.unpause(App.time - MenuSystem.startTime);
         MenuSystem.clearStack();
         App.beginImageUnload();
         MenuSystem.imgMedals = null;
         App.endImageUnload();
      } else if (state == 18) {
         Render.disableRenderActivate = false;
         Game.skippingCinematic = false;
      } else if (state == 5 && var0 != 5 && Combat.stage != 1) {
         Combat.cleanUpAttack();
      } else if (state == 15) {
         dialogBuffer.dispose();
         dialogBuffer = null;
      } else if (state == 20) {
         cocktailName.dispose();
         mixingInstructions.dispose();
         App.beginImageUnload();
         imgMixingHighlight = null;
         imgMixingVials = null;
         imgMixingSyringe = null;
         App.endImageUnload();
      }

      oldState = state;
      state = var0;
      if (var0 == 5) {
         Hud.repaintFlags = 15;
         repaintFlags |= 16;
         clearSoftKeys();
         combatDone = false;
      } else if (var0 == 21) {
         initTravelMap();
      } else if (var0 == 3) {
         Hud.repaintFlags = 15;
         repaintFlags |= 16;
         Game.lastTurnTime = App.time;
         if (Game.monstersTurn == 0 || oldState == 18) {
            drawPlayingSoftKeys();
         }

         if (oldState == 5 && Combat.curTarget != null && Combat.curTarget.def.eType == 3) {
            Game.executeStaticFunc(7);
         }

         updateFacingEntity = true;
         if (oldState != 5 && oldState != 8) {
            invalidateRect();
         }
      } else if (var0 == 8) {
         if (Game.isCameraActive()) {
            Game.activeCameraTime = App.gameTime - Game.activeCameraTime;
         }

         Hud.repaintFlags = 15;
         repaintFlags |= 16;
         TinyGL.resetViewPort();
         clearLeftSoftKey();
         setRightSoftKey((short)1, (short)39);
         byte var2 = 0;
         if (dialogStyle == 2 || dialogStyle == 9 || dialogStyle == 4 && dialogItem != null) {
            var2 = 1;
         }

         if (numDialogLines - var2 > dialogViewLines) {
            setLeftSoftKey((short)1, (short)133);
         } else {
            clearLeftSoftKey();
         }

         clearEvents();
      } else if (var0 == 13) {
         Hud.repaintFlags = 15;
         repaintFlags |= 16;
         if (isZoomedIn) {
            isZoomedIn = false;
            viewAngle += zoomAngle;
            short var3 = 255;
            destAngle = viewAngle = viewAngle + (var3 >> 1) & ~var3;
            TinyGL.resetViewPort();
            drawPlayingSoftKeys();
         }

         clearSoftKeys();
         deathTime = App.time;
         destPitch = 64;
      } else if (var0 == 14) {
         clearSoftKeys();
         loadEpilogueText();
         stateVars[0] = 1;
      } else if (var0 == 15) {
         Text.loadText(3);
         initScrollingText((short)3, (short)0, false, 12, 5, 500);
         Text.unloadText(3);
      } else if (var0 == 9) {
         clearSoftKeys();
         loadPrologueText();
         stateVars[0] = 1;
      } else if (var0 == 2) {
         initScrollingText((short)1, (short)172, true, 24, 1, 800);
         stateVars[0] = 1;
      } else if (var0 != 7 && var0 != 16) {
         if (var0 == 6) {
            automapDrawn = false;
            automapTime = App.time;
         } else if (var0 == 1) {
            MenuSystem.startTime = App.time;
            if (oldState == 3) {
               MenuSystem.soundClick();
            } else if (oldState == 20) {
               MenuSystem.goBackToStation = true;
            }

            if (oldState != 1) {
               clearEvents();
            }

            App.beginImageLoading();
            MenuSystem.imgMedals = App.loadImageFromIndex(19);
            App.endImageLoading();
         } else if (var0 == 18) {
            Hud.msgCount = 0;
            Hud.subTitleID = -1;
            Hud.cinTitleID = -1;
            Render.disableRenderActivate = true;
            repaintFlags |= 17;
            Hud.repaintFlags = 16;
            clearSoftKeys();
            TinyGL.setViewport(cinRect[0], cinRect[1], cinRect[2], cinRect[3]);
         } else if (var0 == 20) {
            App.beginImageLoading();
            imgMixingHighlight = App.loadImageFromIndex(20);
            imgMixingSyringe = App.loadImageFromIndex(21);
            imgMixingVials = App.loadImageFromIndex(22);
            App.endImageLoading();
            setSoftKeys((short)5, (short)75, (short)5, (short)150);
            cocktailName = Text.getSmallBuffer();
            mixingInstructions = Text.getSmallBuffer();
            stateVars[3] = stateVars[2] = stateVars[1] = -4737097;
            stateVars[6] = Game.mixingStations[curStation + 1];
            stateVars[7] = Game.mixingStations[curStation + 2];
            stateVars[8] = Game.mixingStations[curStation + 3];
            stateVars[4] = 3;
            nextMixingIngredient(1);
            mixingMsg = false;
            msgSeen = false;
            updateMixingText();
         }
      } else {
         pacifierX = SCR_CX - 75 + 9;
         updateLoadingBar(false);
      }

   }

   public static final void setAnimFrames(int var0) {
      animFrames = var0;
      animPos = (64 + animFrames - 1) / animFrames;
      animAngle = (256 + animFrames - 1) / animFrames;
   }

   public static final void checkFacingEntity() {
      if (updateFacingEntity) {
         int var0 = destX;
         int var1 = destY;
         int var2 = destZ;
         short var3 = 21741;
         int[] var4 = TinyGL.view;
         int var5 = var2 + (-var4[10] * 28 >> 14);
         int var6 = var2 + (6 * -var4[10] >> 8);
         if (Render.chatZoom) {
            var6 = -1;
            var5 = -1;
         }

         Game.trace(var0 + (-var4[2] * 28 >> 14), var1 + (-var4[6] * 28 >> 14), var5, var0 + (6 * -var4[2] >> 8), var1 + (6 * -var4[6] >> 8), var6, (Entity)null, var3, 2);
         Entity var7 = Game.traceEntity;
         if (var7 != null && (var7.def.eType == 6 || var7.def.eType == 11 || var7.def.eType == 12 || var7.def.eType == 10 || var7.def.eType == 14)) {
            for(int var8 = 0; var8 < Game.numTraceEntities; ++var8) {
               Entity var9 = Game.traceEntities[var8];
               short var10 = var9.linkIndex;
               if (var9.def.eType == 2) {
                  if (var7.def.eType != 12) {
                     var7 = var9;
                  }
                  break;
               }

               if (var9.def.eType == 5 || var9.def.eType == 4 || var9.def.eType == 0 || var9.def.eType == 12 && (Render.mapFlags[var10] & 2) != 0) {
                  break;
               }

               if (var9.def.eType == 7) {
                  if (var7.def.eType == 12) {
                     var7 = var9;
                  }
                  break;
               }

               if (var9.def.eType == 14) {
                  if (var7.def.eSubType != 6) {
                     var7 = var9;
                     break;
                  }
               } else if (var9.def.eType == 10 && (var9.def.eSubType == 1 || var9.def.eSubType == 2 || var9.def.eSubType == 3 || var9.def.eSubType == 5 || var9.def.eSubType == 10) && var7 != null && var7.def.eType != 6) {
                  var7 = var9;
                  break;
               }
            }
         }

         Player.facingEntity = var7;
         Entity var12;
         if (Player.facingEntity != null) {
            var12 = Player.facingEntity;
            int var13 = var12.distFrom(viewX, viewY);
            boolean var15 = var12.def.eType == 2 && (var12.monster.flags & 2048) != 0;
            if (var12.def.eType != 2 && var13 > Combat.tileDistances[2]) {
               Player.facingEntity = null;
            } else if ((var12.def.eType == 3 || var15) && var13 <= Combat.tileDistances[0]) {
               Player.showHelp((short)0, false);
            } else if (var12.def.eType == 14 && var12.def.tileIndex == 203 && var13 <= Combat.tileDistances[1]) {
               Player.showHelp((short)12, false);
            } else if (var13 <= Combat.tileDistances[0]) {
               if (var12.def.eType == 10) {
                  if (var12.def.eSubType == 1) {
                     Player.showHelp((short)2, false);
                  } else if (var12.def.eSubType == 5) {
                     Player.showHelp((short)3, false);
                  } else if (var12.def.eSubType == 2) {
                     Player.showHelp((short)10, false);
                  } else if (var12.def.eSubType == 10) {
                     Player.showHelp((short)11, false);
                  } else if (var12.def.eSubType != 3) {
                     Player.showHelp((short)7, false);
                  }
               } else if (var12.def.eType == 7) {
                  if (var12.def.tileIndex == 147) {
                     Player.showHelp((short)4, false);
                  }
               } else if (var12.def.eType == 5) {
                  if (var12.def.eSubType == 1) {
                     Player.showHelp((short)1, false);
                  }

                  Player.showHelp((short)8, false);
               } else if (var12.def.eType == 6 && var12.def.eSubType == 4) {
                  Player.showHelp((short)5, false);
               }
            }
         }

         var12 = Game.traceEntity;
         short var14 = 4141;
         int var16;
         if (var12 != null && (1 << var12.def.eType & var14) == 0) {
            for(var16 = 1; var16 < Game.numTraceEntities; ++var16) {
               Entity var11 = Game.traceEntities[var16];
               if ((1 << var11.def.eType & var14) != 0) {
                  var12 = var11;
                  break;
               }
            }
         }

         if (var12 != null) {
            var16 = var12.distFrom(viewX, viewY);
            boolean var17 = var12.def.eType == 2 && (var12.monster.flags & 2048) != 0;
            if (var16 <= Combat.tileDistances[0] && var12.def.eType == 0 && Combat.weaponDown) {
               Combat.shiftWeapon(true);
            } else if (state != 3 && state != 8 || (32783L & 1L << Player.ce.weapon) != 0L && var16 > Combat.tileDistances[0]) {
               if (state == 8) {
                  Combat.shiftWeapon(true);
               } else {
                  Combat.shiftWeapon(false);
               }
            } else if (var12.def.eType != 3 && !var17) {
               if (Combat.weaponDown) {
                  Combat.shiftWeapon(false);
               }
            } else {
               Combat.shiftWeapon(true);
            }
         } else {
            Combat.shiftWeapon(false);
         }

         updateFacingEntity = false;
      }
   }

   private static final void finishMovement() {
      if (gotoThread != null && viewAngle == destAngle) {
         gotoThread.run();
         gotoThread = null;
      }

      Game.executeTile(destX >> 6, destY >> 6, flagForFacingDir(8), true);
      Game.executeTile(destX >> 6, destY >> 6, Game.eventFlags[1], true);
      Game.touchTile(destX, destY, true);
      if (knockbackDist > 0) {
         --knockbackDist;
         destZ += 12;
         if (knockbackDist == 0) {
            destZ = 36 + Render.getHeight(destX, destY);
         }
      } else if (gotoThread == null && state == 3 && Game.monstersTurn == 0) {
         if (state != 6) {
            updateFacingEntity = true;
         }

         uncoverAutomap();
         Game.advanceTurn();
      } else if (state == 6) {
         uncoverAutomap();
         Game.advanceTurn();
         if (Game.animatingEffects != 0) {
            setState(3);
         } else {
            Game.snapMonsters(true);
            Game.snapLerpSprites(-1);
         }
      }

   }

   public static final int flagForWeapon(long var0) {
      var0 = 1L << (int)var0;
      if ((var0 & 32783L) != 0L) {
         return 4096;
      } else {
         return (var0 & 4096L) != 0L ? 16384 : 8192;
      }
   }

   public static final int flagForFacingDir(int var0) {
      int var1 = destAngle;
      if (var0 == 4) {
         var1 += 512;
      }

      if (var0 != 8 && var0 != 4) {
         return 0;
      } else {
         int var2 = 1 << ((var1 & 1023) >> 7) + 4;
         return var0 | var2;
      }
   }

   public static final void startRotation(boolean var0) {
      byte var1 = viewStepValues[(destAngle & 1023) >> 7 << 1];
      byte var2 = viewStepValues[((destAngle & 1023) >> 7 << 1) + 1];
      short var3 = 384;
      short var4 = 4133;
      Game.trace(destX, destY, destX + (var1 * var3 >> 6), destY + (var2 * var3 >> 6), (Entity)null, var4, 2);
      Entity var5 = Game.traceEntity;
      int var9 = Game.traceFracs[0] * var3 >> 14;
      boolean var6 = false;
      boolean var7;
      int var10;
      if (var5 != null && (var5.def.eType == 0 || var5.def.eType == 12) && var9 <= 36) {
         var10 = destZ;
         var7 = !var0;
      } else if (var5 != null && var5.def.eType == 2) {
         int[] var8 = var5.calcPosition();
         var10 = Render.getHeight(var8[0], var8[1]) + 36;
         var7 = true;
      } else {
         var9 = 64;
         var10 = Render.getHeight(destX + var1, destY + var2) + 36;
         var7 = !var0;
      }

      if (var7) {
         if (var9 == 0) {
            destPitch = 0;
         } else {
            destPitch = (var10 - destZ << 7) / var9;
         }

         if (destPitch < -64) {
            destPitch = -64;
         } else if (destPitch > 64) {
            destPitch = 64;
         }

         pitchStep = Math.abs((destPitch - viewPitch) / animFrames);
      }
   }

   public static final void finishRotation(boolean var0) {
      viewSin = Render.sinTable[destAngle & 1023];
      viewCos = Render.sinTable[destAngle + 256 & 1023];
      viewStepX = viewStepValues[(destAngle & 1023) >> 7 << 1];
      viewStepY = viewStepValues[((destAngle & 1023) >> 7 << 1) + 1];
      int var1 = destAngle - 256 & 1023;
      viewRightStepX = viewStepValues[var1 >> 7 << 1];
      viewRightStepY = viewStepValues[(var1 >> 7 << 1) + 1];
      if (var0 && Hud.msgCount > 0 && (Hud.messageFlags[0] & 2) != 0) {
         Hud.msgTime = 0;
      }

      if (gotoThread != null && viewX == destX && viewY == destY) {
         ScriptThread var2 = gotoThread;
         gotoThread = null;
         var2.run();
      }

      if (state == 5) {
         updateFacingEntity = true;
      } else {
         int var3 = flagForFacingDir(8);
         Game.executeTile(destX >> 6, destY >> 6, var3, true);
         updateFacingEntity = true;
      }

   }

   public static final int getKeyAction(int var0) {
      if (var0 >= 48 && var0 <= 57) {
         return keys_numeric[var0 - 48];
      } else if (var0 == 42) {
         return 11;
      } else if (var0 == 35) {
         return 7;
      } else if (var0 == 2) {
         return 3;
      } else if (var0 == 5) {
         return 4;
      } else if (var0 == 1) {
         return 1;
      } else if (var0 == 6) {
         return 2;
      } else if (var0 == 8) {
         return 6;
      } else {
         int var1;
         try {
            var1 = App.canvas.getGameAction(var0);
            if (var1 == 2) {
               return 3;
            }

            if (var1 == 5) {
               return 4;
            }

            if (var1 == 1) {
               return 1;
            }

            if (var1 == 6) {
               return 2;
            }

            if (var1 == 8) {
               return 6;
            }
         } catch (IllegalArgumentException var2) {
         }

         var1 = Math.abs(var0);
         if (var1 == 6) {
            return 5;
         } else if (var1 == 7) {
            return 7;
         } else {
            return var1 == 8 ? 15 : 0;
         }
      }
   }

   public static final void attemptMove(int var0, int var1, boolean var2) {
      if (renderOnly) {
         destX = var0;
         destY = var1;
      } else if (!isChickenKicking || kickingPhase == 0) {
         int var3 = Player.noclip ? 0 : 13501;
         Game.eventFlagsForMovement(viewX, viewY, var0, var1);
         abortMove = false;
         Game.executeTile(viewX >> 6, viewY >> 6, Game.eventFlags[0], true);
         if (!abortMove) {
            Game.trace(viewX, viewY, var0, var1, Player.getPlayerEnt(), var3, 16);
            if (Game.traceEntity == null) {
               if (Hud.msgCount > 0 && (Hud.messageFlags[0] & 2) != 0) {
                  Hud.msgTime = 0;
               }

               automapDrawn = false;
               destX = var0;
               destY = var1;
               destZ = 36 + Render.getHeight(destX, destY);
               zStep = (Math.abs(destZ - viewZ) + animFrames - 1) / animFrames;
               prevX = viewX;
               prevY = viewY;
               startRotation(false);
               Player.relink();
            } else if (knockbackDist == 0 && state == 6) {
               Game.advanceTurn();
            }
         } else if (knockbackDist != 0) {
            knockbackDist = 0;
         }

      }
   }

   public void keyPressed(int var1) {
      synchronized(this) {
         if (state == 22) {
            this.mgInstance.processKey(var1);
         }

         if (numEvents != 4) {
            events[numEvents++] = var1;
            keyPressedTime = App.getUpTimeMs();
         }
      }
   }

   public static void loadState(int var0, short var1, short var2) {
      loadType = var0;
      Game.saveConfig();
      setLoadingBarText(var1, var2);
      setState(7);
   }

   public static final void saveState(int var0, short var1, short var2) {
      saveType = var0;
      setLoadingBarText(var1, var2);
      setState(16);
   }

   public static final void loadMap(int var0, boolean var1) {
      lastMapID = loadMapID;
      loadMapID = var0;
      Sound.soundStop();
      if (!var1 && Game.activeLoadType == 0 && lastMapID >= 1 && lastMapID <= 10) {
         saveState(43, (short)5, (short)179);
      } else if (loadType == 3) {
         setLoadingBarText((short)1, (short)169);
         setState(7);
      } else {
         setLoadingBarText((short)5, Game.levelNames[loadMapID - 1]);
         setState(21);
      }

   }

   private static final void loadPrologueText() {
      storyPage = 0;
      storyTotalPages = 0;
      Text var0 = Text.getLargeBuffer();
      Text.loadText(3);
      Text.composeText((short)3, (short)14, var0);
      Text.unloadText(3);
      int var1 = displayRect[2] - 20;
      int var2 = displayRect[3] - 40;
      int var3 = var2 / 16;
      var0.wrapText(var1 / 7);
      storyIndexes[storyTotalPages++] = 0;
      int var4 = 0;
      int var5 = 0;

      while((var5 = var0.findFirstOf('|', var5)) != -1) {
         ++var5;
         ++var4;
         if (var4 % var3 == 0) {
            storyIndexes[storyTotalPages++] = var5;
         }
      }

      storyIndexes[storyTotalPages] = var0.length();
      storyX = displayRect[0] + 10;
      storyY = displayRect[1] + 20;
      dialogBuffer = var0;
   }

   private static final void loadEpilogueText() {
      initScrollingText((short)1, (short)173, true, 24, 1, 1000);
   }

   private static final void disposeIntro() {
      dialogBuffer.dispose();
      dialogBuffer = null;
      System.gc();
      loadMap(startupMap, false);
   }

   private static final void disposeEpilogue() {
      dialogBuffer.dispose();
      dialogBuffer = null;
      System.gc();
      MenuSystem.setMenu(1);
   }

   public static final void drawScroll(Graphics var0, int var1, int var2, int var3, int var4, int var5) {
      int var6;
      int var7;
      if (var5 == 0) {
         var6 = 0;
         var7 = imgNotebookBg.getWidth() > screenRect[2] ? screenRect[2] : imgNotebookBg.getWidth();
         int var8 = var7;
         int var9 = imgNotebookBg.getHeight();

         for(int var10 = 38; var6 < var4; var6 += var9) {
            var9 = var6 + var9 > var4 ? var4 - var6 : var9;
            var0.drawRegion(imgNotebookBg, 0, 0, var7, var9, var1, var2 + var6);

            while(var8 < var3) {
               var10 = var8 + var10 > var3 ? var3 - var8 : var10;
               var0.drawRegion(imgNotebookBg, var7 - 38, 0, 38, var9, var1 + var8, var2 + var6);
               var8 += var10;
            }

            var8 = var7;
            var10 = 38;
         }
      } else if (var5 == 1) {
         var6 = imgDialogScroll.getWidth();
         var7 = imgDialogScroll.getHeight();
         var0.drawRegion(imgDialogScroll, 0, 0, var6, var7, var1, var2 + var4 - var7, 0, 0);
         var0.drawRegion(imgDialogScroll, 0, 0, var6, var7, var1 + var3 - var6, var2 + var4 - var7, 0, 2);
         var0.drawRegion(imgDialogScroll, 0, 0, var6, var7, var1, var2, 0, 1);
         var0.drawRegion(imgDialogScroll, 0, 0, var6, var7, var1 + var3 - var6, var2, 0, 3);
         var0.fillRegion(imgDialogScroll, var6 - 14, 0, 14, var7, var1 + var6, var2, var3 - 2 * var6, var7, 3);
         var0.fillRegion(imgDialogScroll, var6 - 14, 0, 14, var7, var1 + var6, var2 + (var4 - var7), var3 - 2 * var6, var7, 0);
         var0.fillRegion(imgDialogScroll, 0, 0, var6, 6, var1, var2 + var7, var6, var4 - 2 * var7, 0);
         var0.fillRegion(imgDialogScroll, 0, 0, var6, 6, var1 + var3 - var6, var2 + var7, var6, var4 - 2 * var7, 3);
         var0.fillRegion(imgDialogScroll, var6 - 6, 0, 6, 6, var1 + var6, var2 + var7, var3 - 2 * var6, var4 - 2 * var7, 0);
      }

   }

   private static final void initScrollingText(short var0, short var1, boolean var2, int var3, int var4, int var5) {
      if (dialogBuffer == null) {
         dialogBuffer = Text.getLargeBuffer();
      } else {
         dialogBuffer.setLength(0);
      }

      Text.composeText(var0, var1, dialogBuffer);
      if (var2) {
         dialogBuffer.dehyphenate();
      }

      dialogBuffer.wrapText((displayRect[2] - 8) / 7);
      scrollingTextLines = dialogBuffer.getNumLines() + var4;
      scrollingTextStart = -1;
      scrollingTextMSLine = var5;
      scrollingTextSpacing = var3;
      scrollingTextDone = false;
   }

   private static final void drawCredits(Graphics var0) {
      drawScrollingText(var0);
      if (scrollingTextDone) {
         Text var1 = Text.getSmallBuffer();
         var1.setLength(0);
         Text.composeText((short)1, (short)42, var1);
         var1.dehyphenate();
         var0.drawString(var1, SCR_CX - 24, screenRect[3] - 24, 2);
         byte var2 = OSC_CYCLE[App.time / 100 % 4];
         var0.drawCursor(SCR_CX - 38 + var2, screenRect[3] - 24, 2);
         var1.dispose();
      }

   }

   public static final void drawScrollingText(Graphics var0) {
      int var1 = scrollingTextMSLine * ((scrollingTextSpacing << 16) / 12) >> 16;
      int var2;
      if (scrollingTextStart == -1) {
         scrollingTextStart = App.gameTime;
         var2 = screenRect[3] / scrollingTextSpacing;
         if (state == 15) {
            scrollingTextEnd = var1 * scrollingTextLines;
         } else {
            scrollingTextEnd = var1 * (scrollingTextLines + (var2 - 2));
         }
      }

      var2 = App.gameTime;
      int var3 = scrollingTextStart;
      if (var2 - var3 > scrollingTextEnd) {
         var3 = var2 - scrollingTextEnd;
         scrollingTextDone = true;
      }

      var0.eraseRgn(displayRect);
      if (displayRect[3] > 220) {
         var0.setScreenSpace(0, (displayRect[3] - 220) / 2, displayRect[2], 220);
      }

      int var4 = (var2 - var3 << 8) / var1 * (scrollingTextSpacing << 8);
      int var5 = screenRect[3] - (var4 >> 16);
      var0.drawString(dialogBuffer, SCR_CX, var5, scrollingTextSpacing, 1, 0, -1);
      var0.resetScreenSpace();
   }

   private static final void handleDialogEvents(int var0) {
      int var1 = getKeyAction(var0);
      if (var1 == 6) {
         if (dialogTypeLineIdx < dialogViewLines && dialogTypeLineIdx < numDialogLines - currentDialogLine) {
            dialogTypeLineIdx = dialogViewLines;
         } else if (currentDialogLine < numDialogLines - dialogViewLines) {
            dialogLineStartTime = App.time;
            dialogTypeLineIdx = 0;
            currentDialogLine += dialogViewLines;
            if (((dialogFlags & 4) != 0 || (dialogFlags & 1) != 0) && currentDialogLine + dialogViewLines > numDialogLines) {
               currentDialogLine = numDialogLines - dialogViewLines;
            }
         } else {
            closeDialog(false);
         }
      } else {
         short var2;
         if (var1 == 1) {
            if (currentDialogLine >= numDialogLines - dialogViewLines && 0 != (dialogFlags & 2)) {
               var2 = Game.scriptStateVars[4];
               if (var2 == 0) {
                  --currentDialogLine;
                  if (currentDialogLine < 0) {
                     currentDialogLine = 0;
                  }
               } else {
                  --Game.scriptStateVars[4];
               }
            } else {
               --currentDialogLine;
               if (currentDialogLine < 0) {
                  currentDialogLine = 0;
               }
            }
         } else if (var1 == 2) {
            if (currentDialogLine >= numDialogLines - dialogViewLines && 0 != (dialogFlags & 2)) {
               var2 = Game.scriptStateVars[4];
               if (var2 < 2) {
                  ++Game.scriptStateVars[4];
               }
            } else {
               ++currentDialogLine;
               if (currentDialogLine > numDialogLines - dialogViewLines) {
                  currentDialogLine = numDialogLines - dialogViewLines;
                  if (0 == (dialogFlags & 2) && currentDialogLine < 0) {
                     currentDialogLine = 0;
                  }
               } else {
                  dialogLineStartTime = App.time;
                  dialogTypeLineIdx = dialogViewLines - 1;
               }
            }
         } else if ((var1 == 3 || var1 == 4) && (dialogFlags & 5) != 0 && currentDialogLine >= numDialogLines - dialogViewLines) {
            var2 = Game.scriptStateVars[4];
            Game.scriptStateVars[4] = (short)(var2 ^ 1);
         } else if (var1 != 14 && var1 != 7) {
            if (var1 != 5 && var1 != 3) {
               if (var1 == 4) {
                  currentDialogLine += dialogViewLines;
                  if (currentDialogLine > numDialogLines - dialogViewLines) {
                     currentDialogLine = Math.max(numDialogLines - dialogViewLines, 0);
                  }
               }
            } else {
               currentDialogLine -= dialogViewLines;
               if (currentDialogLine < 0) {
                  currentDialogLine = 0;
               }
            }
         } else {
            closeDialog(true);
         }
      }

      if (state == 3 && Game.monstersTurn == 0) {
         dequeueHelpDialog();
      }

   }

   private static final boolean handlePlayingEvents(int var0, int var1) {
      boolean var2 = false;
      if (isZoomedIn || viewX == destX && viewY == destY && viewAngle == destAngle) {
         if (knockbackDist == 0 && !changeMapStarted) {
            if (renderOnly) {
               viewX = destX;
               viewY = destY;
               viewZ = destZ;
               viewAngle = destAngle;
               viewAngle = destPitch;
               viewSin = Render.sinTable[destAngle & 1023];
               viewCos = Render.sinTable[destAngle + 256 & 1023];
               viewStepX = viewCos * 64 >> 16;
               viewStepY = -viewSin * 64 >> 16;
               invalidateRect();
            } else {
               if (!isZoomedIn) {
                  if (viewX == destX && viewY == destY) {
                     if (viewAngle != destAngle) {
                        var2 = true;
                        viewAngle = destAngle;
                        viewAngle = destPitch;
                        finishRotation(true);
                        invalidateRect();
                     }
                  } else {
                     var2 = true;
                     viewX = destX;
                     viewY = destY;
                     viewZ = destZ;
                     finishMovement();
                     invalidateRect();
                  }
               }

               if (blockInputTime != 0) {
                  return true;
               }

               boolean var3 = state == 6;
               if (var1 != 11 && var1 != 12 && var1 != 3 && var1 != 4 && (Game.activePropogators != 0 || !Game.snapMonsters(var3) || Game.animatingEffects != 0)) {
                  return true;
               }
            }

            if (isChickenKicking) {
               if (kickingPhase == 5) {
                  handleHighScoreInput(var1, var0);
                  return true;
               }

               if (kickingPhase == 4) {
                  if (gridTime - App.gameTime >= 1000) {
                     gridTime = 0;
                  }

                  return true;
               }
            }

            if (var1 == 7) {
               if (!isChickenKicking) {
                  setState(state != 6 ? 6 : 3);
               }
            } else if (var1 == 1) {
               attemptMove(viewX + viewStepX, viewY + viewStepY, true);
            } else if (var1 == 2) {
               attemptMove(viewX - viewStepX, viewY - viewStepY, true);
            } else if (var1 == 9) {
               attemptMove(viewX + viewStepY, viewY - viewStepX, true);
            } else if (var1 == 10) {
               attemptMove(viewX - viewStepY, viewY + viewStepX, true);
            } else if (var1 != 3 && var1 != 4) {
               int var21;
               if (var1 != 11 && var1 != 12) {
                  if (var1 == 15) {
                     if (state == 6) {
                        setState(3);
                     } else {
                        Hud.msgCount = 0;
                        MenuSystem.setMenu(28);
                     }
                  } else if (var1 == 5) {
                     Hud.msgCount = 0;
                     MenuSystem.setMenu(28);
                  } else if (var1 == 6) {
                     if (null != Player.facingEntity && (Player.facingEntity.def.eType == 10 || Player.facingEntity.def.eType == 10)) {
                        ScriptThread.lootSource = Player.facingEntity.name;
                     } else {
                        ScriptThread.lootSource = -1;
                     }

                     var21 = Player.ce.weapon;
                     Entity var4 = null;
                     int var5 = 16384;
                     int var6 = 13997;
                     if ((1L << var21 & 65551L) != 0L) {
                        var6 |= 8192;
                     }

                     Entity var7 = null;
                     byte var8 = 6;
                     if ((1L << var21 & 32783L) != 0L) {
                        var8 = 1;
                        var6 |= 16;
                     }

                     int var9 = viewX + (var8 * -TinyGL.view[2] >> 8);
                     int var10 = viewY + (var8 * -TinyGL.view[6] >> 8);
                     int var11 = viewZ + (var8 * -TinyGL.view[10] >> 8);
                     Game.trace(viewX, viewY, viewZ, var9, var10, var11, (Entity)null, var6, 2);

                     int var12;
                     int var14;
                     int var15;
                     int var17;
                     for(var12 = 0; var12 < Game.numTraceEntities; ++var12) {
                        Entity var13 = Game.traceEntities[var12];
                        var14 = Game.traceFracs[var12];
                        var15 = var13.distFrom(viewX, viewY);
                        byte var16 = var13.def.eType;
                        if (var16 == 0 || var16 == 12 || var16 == 4) {
                           if (var4 == null) {
                              var4 = var13;
                              var5 = var14;
                           }
                           break;
                        }

                        if (var16 == 10) {
                           if ((1 << var13.def.eSubType & 33) == 0) {
                              var4 = var13;
                              var5 = var14;
                              break;
                           }

                           if (var15 <= Combat.tileDistances[0] && (1L << var21 & 32783L) != 0L) {
                              var4 = var13;
                              var5 = var14;
                              break;
                           }
                        } else if (var16 == 13) {
                           if ((1L << var21 & 65551L) != 0L) {
                              var7 = var13;
                           }
                        } else if (var16 == 3) {
                           if (var15 >= 8192) {
                              var4 = var13;
                              var5 = var14;
                              break;
                           }
                        } else {
                           if (var16 == 2 || var16 == 5) {
                              var4 = var13;
                              var5 = var14;
                              break;
                           }

                           if (var16 == 9) {
                              if (var21 == 3 && var15 == Combat.tileDistances[0]) {
                                 if (var4 != null && var4.def.eType == 9) {
                                    if (var4.linkIndex < var13.linkIndex) {
                                       var4 = var13;
                                       var5 = var14;
                                    }
                                 } else {
                                    var4 = var13;
                                    var5 = var14;
                                 }
                              }
                           } else if (var16 == 7) {
                              var17 = Render.mapSpriteInfo[var13.getSprite()] & 255;
                              if (var17 == 188 || var17 == 189) {
                                 var4 = var13;
                                 var5 = var14;
                                 break;
                              }
                           } else if (var16 != 7 && var16 != 6 && var4 == null) {
                              var4 = var13;
                              var5 = var14;
                           }
                        }
                     }

                     var12 = Combat.tileDistances[9];
                     if (var4 != null) {
                        var12 = var4.distFrom(viewX, viewY);
                     }

                     int var22 = var21 * 9;
                     if (var4 != null && var4.def.eType == 10 && (1 << var4.def.eSubType & 33) == 0) {
                        var14 = Combat.WorldDistToTileDist(var12);
                        if (var14 > Combat.weapons[var22 + 3]) {
                           var4 = null;
                        }
                     }

                     if (isChickenKicking && var4 != null && var4.def.eType == 10 && var4.def.eSubType == 8 && (viewAngle & 1023) == kickingDir) {
                        if (kickingPhase == 0 && vKickingBar == null && hKickingBar == null) {
                           App.beginImageLoading();
                           hKickingBar = App.loadImageFromIndex(8);
                           vKickingBar = App.loadImageFromIndex(37);
                           App.endImageLoading();
                        }

                        ++kickingPhase;
                        clearEvents();
                        stateVars[0] = App.getUpTimeMs();
                        if (kickingPhase != 3) {
                           return true;
                        }
                     }

                     if (var7 != null && (var4 == null || (1L << var21 & 7L) != 0L || var4.def.eType != 2 && var4.def.eType != 9)) {
                        var4 = var7;
                     }

                     var14 = flagForFacingDir(4);
                     var15 = destX + viewStepX >> 6;
                     int var23 = destY + viewStepY >> 6;
                     if (0 != Game.executeTile(var15, var23, var14, true)) {
                        if (!Game.skipAdvanceTurn && state == 3) {
                           Game.touchTile(destX, destY, false);
                           Game.snapMonsters(true);
                           Game.advanceTurn();
                        }
                     } else {
                        if (var4 != null && var4.def.eType == 10 && var4.def.eSubType == 10 && var12 <= Combat.tileDistances[0] && Player.ammo[9] == 0) {
                           Player.setPickUpWeapon(var4.def.tileIndex);
                           Player.give(2, 9, 1, true);
                           Player.giveAmmoWeapon(16, true);
                           if (0 != (var4.info & 1048576)) {
                              Game.unlinkEntity(var4);
                           }

                           var17 = var4.getSprite();
                           Render.mapSpriteInfo[var17] = Render.mapSpriteInfo[var17] & -65281 | 512;
                           var4.info |= 4194304;
                           int[] var25 = var4.calcPosition();
                           if (shouldFakeCombat(var25[0] >> 6, var25[1] >> 6, var14) && Combat.explodeThread != null) {
                              Combat.explodeThread.run();
                              Combat.explodeThread = null;
                           }

                           return true;
                        }

                        if (var4 != null && var4.def.eType == 5 && var12 <= Combat.tileDistances[0] && var21 != 16) {
                           if (var4.def.eSubType == 1) {
                              Hud.addMessage((short)43, 2);
                           } else {
                              Game.performDoorEvent(0, var4, 1);
                              Game.advanceTurn();
                           }

                           return true;
                        }

                        if (var4 != null && var4.def.eType == 12 && var12 <= Combat.tileDistances[0] && (Render.mapFlags[var23 * 32 + var15] & 4) != 0) {
                           if (Game.performDoorEvent(0, var4, 1, true)) {
                              Game.awardSecret();
                           }

                           return true;
                        }

                        if (Player.ce.weapon != 11 && var4 != null && var4.def.eType == 0 && var12 <= Combat.tileDistances[0]) {
                           var4 = null;
                           Combat.shiftWeapon(true);
                           pushedWall = true;
                           pushedTime = App.gameTime + 500;
                           Render.rockView(1000, viewX + (viewStepX >> 6) * 2, viewY + (viewStepY >> 6) * 2, viewZ);
                           return true;
                        }

                        if (Game.isUnderWater() && (1L << var21 & 32783L) == 0L && var21 != 11) {
                           Player.showHelp((short)15, false);
                           return true;
                        }

                        if ((var4 == null || var4.def.eType == 0) && !isZoomedIn && (1L << var21 & 1536L) != 0L && Player.ammo[Combat.weapons[var22 + 4]] > 0) {
                           initZoom();
                           return true;
                        }

                        if (var4 != null && var4.def.eType != 0) {
                           if (!isZoomedIn && (1L << var21 & 1536L) != 0L && Player.ammo[Combat.weapons[var22 + 4]] > 0) {
                              initZoom();
                              return true;
                           }

                           int[] var24 = var4.calcPosition();
                           boolean var18 = shouldFakeCombat(var24[0] >> 6, var24[1] >> 6, var14);
                           int var19 = var21 * 9;
                           if (var18 || Combat.weapons[var19 + 3] == 1 && Combat.weapons[var19 + 2] == 1 && var12 > Combat.tileDistances[0]) {
                              Game.traceCollisionX = var24[0];
                              Game.traceCollisionY = var24[1];
                              Player.fireWeapon(Game.entities[0], var24[0], var24[1]);
                           } else {
                              if (isZoomedIn) {
                                 zoomCollisionX = viewX + (var5 * (var9 - viewX) >> 14);
                                 zoomCollisionY = viewY + (var5 * (var10 - viewY) >> 14);
                                 zoomCollisionZ = viewZ + (var5 * (var11 - viewZ) >> 14);
                              }

                              Player.fireWeapon(var4, var24[0], var24[1]);
                           }
                        } else {
                           shouldFakeCombat(Game.traceCollisionX >> 6, Game.traceCollisionY >> 6, var14);
                           Player.fireWeapon(Game.entities[0], Game.traceCollisionX, Game.traceCollisionY);
                        }
                     }
                  } else if (var1 == 14) {
                     Hud.addMessage((short)44);
                     Game.touchTile(destX, destY, false);
                     Game.advanceTurn();
                     invalidateRect();
                  }
               } else {
                  var21 = Player.ce.weapon;
                  if (var21 == 16) {
                     return true;
                  }

                  if (var1 == 11) {
                     Player.selectPrevWeapon();
                  } else {
                     Player.selectNextWeapon();
                  }

                  if (var21 != Player.ce.weapon) {
                     Hud.addMessage((short)2, Player.activeWeaponDef.longName, 1);
                  }

                  Player.helpBitmask |= 512;
               }
            } else if (!isChickenKicking || kickingPhase == 0) {
               short var20 = 256;
               Hud.damageTime = 0;
               if (var1 == 4) {
                  var20 = -256;
               }

               destAngle += var20;
               startRotation(false);
               automapDrawn = false;
            }

            return endOfHandlePlayingEvent(var1, var2);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static final boolean shouldFakeCombat(int var0, int var1, int var2) {
      boolean var3 = false;
      int var4 = Player.ce.weapon * 9;
      if (Combat.weapons[var4 + 4] != 0) {
         short var5 = Player.ammo[Combat.weapons[var4 + 4]];
         if (Combat.weapons[var4 + 5] > 0 && var5 - Combat.weapons[var4 + 5] < 0) {
            return false;
         }
      }

      var2 |= flagForWeapon((long)Player.ce.weapon);
      if (Game.doesScriptExist(var0, var1, var2)) {
         Combat.explodeThread = Game.allocScriptThread();
         Combat.explodeThread.queueTile(var0, var1, var2);
         var3 = true;
      }

      return var3;
   }

   private static final boolean endOfHandlePlayingEvent(int var0, boolean var1) {
      if ((var0 == 9 || var0 == 10 || var0 == 3 || var0 == 4) && (viewX != destX || viewY != destY || viewAngle != destAngle)) {
         Player.facingEntity = null;
      }

      return true;
   }

   private static final boolean handleEvent(int var0) {
      int var1 = state;
      int var2 = getKeyAction(var0);
      int var3 = Render.getFadeFlags();
      if (st_enabled) {
         st_enabled = false;
         renderOnly = false;
      }

      if (var3 != 0 && (var3 & 16) != 0) {
         return true;
      } else {
         if (var1 == 1) {
            MenuSystem.handleMenuEvents(var0, var2);
         } else if (var1 == 9) {
            handleStoryInput(var0, var2);
         } else {
            if (var1 == 14) {
               return true;
            }

            if (var1 == 8) {
               handleDialogEvents(var0);
            } else if (var1 == 12) {
               CardGames.handleInput(var2, var0);
            } else if (var1 != 11 && var1 != 10) {
               if (var1 == 6) {
                  automapDrawn = false;
                  return handlePlayingEvents(var0, var2);
               }

               if (var1 == 3) {
                  if (isZoomedIn) {
                     return handleZoomEvents(var0, var2);
                  }

                  return handlePlayingEvents(var0, var2);
               }

               if (var1 == 5) {
                  if (Combat.curAttacker != null && !isZoomedIn) {
                     if (var2 == 4) {
                        destAngle -= 256;
                     } else if (var2 == 3) {
                        destAngle += 256;
                     }

                     startRotation(false);
                  }

                  if (combatDone && !Game.interpolatingMonsters) {
                     setState(3);
                     if (Combat.curAttacker == null) {
                        Game.advanceTurn();
                        if (var1 == 3) {
                           if (isZoomedIn) {
                              return handleZoomEvents(var0, var2);
                           }

                           return handlePlayingEvents(var0, var2);
                        }
                     }
                  }
               } else if (var1 == 15) {
                  if (endingGame) {
                     if (var2 == 6 && scrollingTextDone) {
                        endingGame = false;
                        Sound.soundStop();
                        System.gc();
                        MenuSystem.setMenu(21);
                     }
                  } else if (var2 == 6 || var2 == 15) {
                     if (loadMapID == 0) {
                        int var4 = 3;
                        if (Game.hasSavedState()) {
                           ++var4;
                        }

                        MenuSystem.pushMenu(3, var4);
                        MenuSystem.setMenu(4);
                     } else {
                        Sound.soundStop();
                        MenuSystem.pushMenu(28, 7);
                        MenuSystem.setMenu(36);
                     }
                  }
               } else if (var1 == 18) {
                  if (!changeMapStarted && App.gameTime > Game.cinUnpauseTime && (var2 == 14 || var2 == 7)) {
                     Game.skipCinematic();
                  }
               } else if (var1 == 13) {
                  if (stateVars[0] > 0 && (var2 == 6 || var2 == -1)) {
                     MenuSystem.setMenu(50);
                  }
               } else if (var1 == 20) {
                  handleMixingEvents(var2);
               } else {
                  if (var1 != 21) {
                     return false;
                  }

                  if (stateVars[4] == 1 && var2 == 6) {
                     clearSoftKeys();
                     disposeTravelMap();
                     setLoadingBarText((short)1, (short)40);
                     setState(7);
                  } else {
                     stateVars[4] = 1;
                  }
               }
            } else {
               setState(3);
               setAnimFrames(animFrames);
            }
         }

         return true;
      }
   }

   private static final void runInputEvents() {
      if (!ignoreFrameInput) {
         while(blockInputTime == 0) {
            boolean var0 = false;
            int[] var1 = events;
            int var7;
            synchronized(App.canvas) {
               if (numEvents == 0) {
                  return;
               }

               var7 = var1[0];
            }

            if (!handleEvent(var7)) {
               return;
            }

            synchronized(App.canvas) {
               if (numEvents > 0) {
                  --numEvents;
                  System.arraycopy(var1, 1, var1, 0, numEvents);
               }
            }
         }

         if (App.gameTime > blockInputTime) {
            if (state == 3) {
               drawPlayingSoftKeys();
            }

            blockInputTime = 0;
         }

         clearEvents();
      }
   }

   private static final boolean loadMedia() throws IOException {
      boolean var0 = Sound.isSoundEnabled();
      Sound.setSound(false);
      state = 0;
      mediaLoading = true;
      boolean var1 = displaySoftKeys;
      updateLoadingBar(false);
      unloadMedia();
      displaySoftKeys = false;
      isZoomedIn = false;
      TinyGL.resetViewPort();

      for(int var2 = 0; var2 < 64; ++var2) {
         Game.scriptStateVars[var2] = 0;
      }

      Render.mapMemoryUsage = App.getFreeMemory();
      if (!Render.beginLoadMap(loadMapID)) {
         return false;
      } else {
         if (loadMapID <= 10 && loadMapID > Player.highestMap) {
            Player.highestMap = loadMapID;
         }

         if (Game.isSaved) {
            setLoadingBarText((short)1, (short)37);
         } else if (Game.isLoaded) {
            setLoadingBarText((short)1, (short)38);
         } else if (loadType != 3) {
            setLoadingBarText((short)5, (short)(Render.mapNameField & 1023));
         }

         updateLoadingBar(false);
         Render.mapMemoryUsage -= App.getFreeMemory();
         App.checkPeakMemory("after map loaded");
         Game.loadMapEntities();
         Hud.msgCount = 0;
         updateLoadingBar(false);
         Game.curLevelTime = Player.playTime = App.gameTime;
         clearEvents();
         ParticleSystem.freeAllParticles();
         displaySoftKeys = var1;
         Player.levelInit();
         updateLoadingBar(false);
         Game.loadWorldState();
         updateLoadingBar(false);
         Game.spawnPlayer();
         knockbackDist = 0;
         if (!Game.isLoaded && loadType != 3) {
            Game.saveLevelSnapshot();
         }

         updateLoadingBar(false);
         boolean var3 = !Game.isLoaded && !Game.hasSavedState();
         if (var3 && loadType != 3) {
            Game.saveState(loadMapID, loadMapID, destX, destY, destAngle, destPitch, destX, destY, 3);
         } else {
            loadRuntimeData();
         }

         updateLoadingBar(false);
         Player.selectWeapon(Player.ce.weapon);
         Game.scriptStateVars[12] = (short)Game.difficulty;
         Game.executeStaticFunc(0);
         updateLoadingBar(false);
         if (Player.gameCompleted) {
            Game.executeStaticFunc(1);
         }

         if (!Game.isLoaded) {
            prevX = destX;
            prevY = destY;
            Game.executeTile(viewX >> 6, viewY >> 6, 4081, true);
            finishRotation(false);
            dequeueHelpDialog(true);
         }

         finishRotation(false);
         Game.endMonstersTurn();
         uncoverAutomap();
         updateLoadingBar(false);
         Game.isLoaded = false;
         Game.isSaved = false;
         Game.activeLoadType = 0;
         dequeueHelpDialog(true);
         if (state == 0) {
            setState(3);
         }

         Game.pauseGameTime = false;
         App.lastTime = App.time = App.getUpTimeMs();
         Sound.setSound(var0);
         mediaLoading = false;
         renderScene(viewX, viewY, viewZ, viewAngle, viewPitch, 0, 250);
         if (state != 18) {
            repaintFlags |= 16;
         }

         repaintFlags |= 2;
         if (state == 3) {
            drawPlayingSoftKeys();
         }

         clearEvents();
         return true;
      }
   }

   public static final boolean updateLogos() {
      App.checkPausedState();
      showBacklight();
      if (pacLogoIndex == -1 || App.getUpTimeMs() - pacLogoTime > 1500) {
         if (pacLogoIndex == 1) {
            return false;
         }

         ++pacLogoIndex;

         try {
            imgStartupLogo = Image.createImage("/l" + (pacLogoIndex + 1) + ".png");
         } catch (Exception var1) {
            App.Error(var1, 10);
         }

         App.checkPausedState();
         repaintFlags |= 8;
         App.canvas.flushGraphics();
         App.checkPausedState();
         imgStartupLogo = null;
         System.gc();
         pacLogoTime = App.getUpTimeMs();
      }

      return true;
   }

   private static final void combatState() {
      Game.monsterLerp();
      Game.updateLerpSprites();
      if (combatDone) {
         if (!Game.interpolatingMonsters) {
            if (Combat.curAttacker == null) {
               Game.advanceTurn();
            }

            if (!Game.isCameraActive()) {
               setState(3);
            } else {
               setState(18);
               Game.activeCamera.cameraThread.run();
            }
         }
      } else if (Combat.runFrame() == 0) {
         if (state == 13) {
            while(Game.combatMonsters != null) {
               Game.combatMonsters.undoAttack();
            }

            return;
         }

         if (Combat.curAttacker == null) {
            Game.touchTile(destX, destY, false);
            combatDone = true;
         } else if (knockbackDist == 0) {
            Entity var0 = Combat.curAttacker;
            if ((var0.monster.goalFlags & 8) != 0) {
               var0.monster.resetGoal();
               var0.monster.goalType = 5;
               var0.monster.goalParam = 1;
               var0.aiThink(false);
            }

            Entity var1;
            for(var0 = var0.monster.nextAttacker; var0 != null && var0.monster.target == null && !var0.aiIsAttackValid(); var0 = var1) {
               var1 = var0.monster.nextAttacker;
               var0.undoAttack();
            }

            if (var0 != null) {
               Combat.performAttack(var0, var0.monster.target, 0, 0, false);
            } else {
               Game.combatMonsters = null;
               if (Game.interpolatingMonsters) {
                  setState(3);
               } else {
                  Game.endMonstersTurn();
                  drawPlayingSoftKeys();
                  combatDone = true;
               }
            }
         }
      }

      updateView();
      repaintFlags |= 32;
      Hud.repaintFlags |= 11;
      if (!Game.isCameraActive()) {
         repaintFlags |= 16;
      }

   }

   private static final void dialogState(Graphics var0) {
      if (dialogBuffer == null || dialogBuffer.length() != 0) {
         int[] var1 = dialogRect;
         var1[0] = -screenRect[0];
         var1[2] = hudRect[2];
         var1[3] = dialogViewLines * 12 + 6;
         var1[1] = screenRect[3] - var1[3] - 1;
         dialogTypeLineIdx = numDialogLines;
         int var2 = var1[0] + 1;
         int var3 = -16777216;
         byte var4 = -1;
         int var5 = -10066330;
         switch(dialogStyle) {
         case 1:
         case 6:
            var3 = -16766876;
         case 2:
         case 7:
         default:
            break;
         case 3:
            var2 = -screenRect[0] + 1;
            var1[1] = hudRect[3] - var1[3] - 10;
            drawScroll(var0, var1[0], var1[1] - 10, hudRect[2], var1[3] + 20, 1);
            break;
         case 4:
            if ((dialogFlags & 1) != 0) {
               var3 = -5142015;
            } else {
               var3 = -16754176;
            }
            break;
         case 5:
            var3 = -8388608;
            if ((dialogFlags & 2) != 0) {
               var1[1] = hudRect[1] + 25;
            }
            break;
         case 8:
            var1[1] -= 25;
            var3 = -11577833;
            break;
         case 9:
            var5 = -16777114;
         }

         byte var6 = 0;
         int var7;
         int var8;
         int var10;
         int var11;
         int var13;
         if (dialogStyle != 2 && dialogStyle != 9) {
            if (dialogStyle == 4) {
               var0.setColor(var3);
               var0.fillRect(var1[0], var1[1], var1[2], var1[3]);
               var0.setColor(var4);
               var0.drawRect(var1[0], var1[1], var1[2] - 1, var1[3]);
               if (dialogItem != null) {
                  var6 = 1;
                  var0.setColor(var3);
                  var0.fillRect(var1[0], var1[1] - 10 - 2, var1[2], 12);
                  var0.setColor(var4);
                  var0.drawRect(var1[0], var1[1] - 10 - 2, var1[2] - 1, 12);
                  var0.drawString(dialogBuffer, var1[0] + (var1[2] + 10 - 2 >> 1), var1[1] - 5, 3, dialogIndexes[0], dialogIndexes[1]);
               }
            } else if (dialogStyle != 3) {
               var0.setColor(var3);
               var0.fillRect(var1[0], var1[1], var1[2], var1[3]);
               var0.setColor(var4);
               var0.drawRect(var1[0], var1[1], var1[2] - 1, var1[3]);
               if (dialogStyle == 8) {
                  var8 = var7 = var1[1] + 1;
                  int var9 = var8 + (var1[3] - 1);

                  while(true) {
                     ++var7;
                     if (var7 >= var9) {
                        var0.drawRegion(imgUIImages, 30, 0, 15, 9, SCR_CX + 10, var1[1] + var1[3]);
                        if (currentDialogLine < 2) {
                           int var12 = Hud.imgPortraitsSM.getWidth();
                           var13 = Hud.imgPortraitsSM.getHeight() / 2;
                           var0.drawRegion(Hud.imgPortraitsSM, 0, currentDialogLine * 10, var12, var13 - currentDialogLine * 10, var1[0] + 2, var1[1] + 3);
                        }
                        break;
                     }

                     var10 = 96 + ((256 - (var7 - var8 << 8) / (var9 - var8)) * 160 >> 8);
                     var11 = ((var3 & -16711936) >> 8) * var10 & -16711936;
                     var11 |= (var3 & 16711935) * var10 >> 8 & 16711935 & 16711422;
                     var0.setColor(var11);
                     var0.drawLine(var1[0] + 1, var7, var1[0] + (var1[2] - 2), var7);
                  }
               } else if (dialogStyle == 5) {
                  if ((dialogFlags & 2) != 0) {
                     var0.drawRegion(imgUIImages, 0, 12, 10, 6, SCR_CX - 64, var1[1] + var1[3] + 6, 36, 0);
                  } else {
                     var0.drawRegion(imgUIImages, 0, 0, 10, 6, SCR_CX - 64, var1[1] + 1, 36, 0);
                  }
               } else if (dialogStyle == 1) {
                  var0.drawRegion(imgUIImages, 10, 0, 10, 6, SCR_CX - 64, var1[1] + 1, 36, 0);
               }
            }
         } else {
            var6 = 1;
            var0.setColor(var3);
            var0.fillRect(var1[0], var1[1], var1[2], var1[3]);
            var0.setColor(var5);
            var0.fillRect(var1[0], var1[1] - 12 - 2, var1[2], 14);
            var0.setColor(var4);
            var0.drawRect(var1[0], var1[1] - 12 - 2, var1[2] - 1, 14);
            var0.drawRect(var1[0], var1[1], var1[2] - 1, var1[3]);
            if (specialLootIcon != -1) {
               var0.drawRegion(Hud.imgActions, 0, 18 * specialLootIcon, 18, 18, var1[0], var1[1] - 12 - 1);
               var0.drawRegion(Hud.imgActions, 0, 18 * specialLootIcon, 18, 18, var1[2] - 18, var1[1] - 12 - 1);
            }

            var0.drawString(dialogBuffer, SCR_CX, var1[1] - 12, 1, dialogIndexes[0], dialogIndexes[1]);
         }

         if (currentDialogLine < var6) {
            currentDialogLine = var6;
         }

         var7 = var1[1] + 2;

         short var16;
         for(var8 = 0; var8 < dialogViewLines && currentDialogLine + var8 < numDialogLines; ++var8) {
            var16 = dialogIndexes[(currentDialogLine + var8) * 2];
            short var17 = dialogIndexes[(currentDialogLine + var8) * 2 + 1];
            var11 = 0;
            if (var8 == dialogTypeLineIdx) {
               var11 = (App.time - dialogLineStartTime) / 25;
               if (var11 >= var17) {
                  var11 = var17;
                  ++dialogTypeLineIdx;
                  dialogLineStartTime = App.time;
               }
            } else if (var8 < dialogTypeLineIdx) {
               var11 = var17;
            }

            var0.drawString(dialogBuffer, var2, var7, 0, var16, var11);
            var7 += 12;
         }

         byte var15 = OSC_CYCLE[App.time / 200 % 4];
         var16 = Game.scriptStateVars[4];
         int var14;
         Text var19;
         if ((dialogFlags & 2) != 0) {
            var10 = screenRect[0] + 3;
            var7 = screenRect[3] - 25 - 51;
            Text var20 = Text.getSmallBuffer();
            var19 = Text.getSmallBuffer();
            Text var21 = Text.getSmallBuffer();
            Text.composeText((short)1, (short)26, var20);
            var20.dehyphenate();
            Text.composeText((short)1, (short)27, var19);
            var19.dehyphenate();
            Text.composeText((short)1, (short)28, var21);
            var21.dehyphenate();
            var14 = Math.max(var20.getStringWidth(), var19.getStringWidth());
            var14 = Math.max(var14, var21.getStringWidth()) + 18;
            var0.fillRect(var10, var7, var14, 15, -11908534);
            var0.drawRect(var10, var7, var14, 15, -1);
            var0.drawString(var20, var10 + 3, var7 + 2, 4);
            if (var16 == 0) {
               var0.drawCursor(var10 + (var14 - 4) + var15, var7 + 2, 24, false);
            }

            var7 += 17;
            var0.fillRect(var10, var7, var14, 15, -11908534);
            var0.drawRect(var10, var7, var14, 15, -1);
            var0.drawString(var19, var10 + 3, var7 + 2, 4);
            if (var16 == 1) {
               var0.drawCursor(var10 + (var14 - 4) + var15, var7 + 2, 24, false);
            }

            var7 += 17;
            var0.fillRect(var10, var7, var14, 15, -11908534);
            var0.drawRect(var10, var7, var14, 15, -1);
            var0.drawString(var21, var10 + 3, var7 + 2, 4);
            if (var16 == 2) {
               var0.drawCursor(var10 + (var14 - 4) + var15, var7 + 2, 24, false);
            }

            var20.dispose();
            var19.dispose();
            var21.dispose();
         } else if (dialogFlags != 0 && currentDialogLine >= numDialogLines - dialogViewLines) {
            short var18 = 26;
            short var22 = 29;
            var19 = Text.getSmallBuffer();
            if ((dialogFlags & 1) != 0) {
               var18 = 175;
               var22 = 176;
            }

            if ((dialogFlags & 4) != 0 || (dialogFlags & 1) != 0) {
               var13 = (screenRect[0] + screenRect[2] - 1) / 3 - 10;
               var7 = var1[1] + 2 + 12 * (dialogViewLines - 1);
               Text.composeText((short)1, var18, var19);
               var19.dehyphenate();
               var14 = var13 - (var19.getStringWidth() >> 1);
               var0.drawBoxedString(var19, var13, var7, 1, var3, -1);
               if (var16 == 0) {
                  var0.drawCursor(var14 + var15 - 19, var7, 0);
               }

               var13 += var13 + 20;
               var19.setLength(0);
               Text.composeText((short)1, var22, var19);
               var19.dehyphenate();
               var14 = var13 - (var19.getStringWidth() >> 1);
               var0.drawBoxedString(var19, var13, var7, 1, var3, -1);
               if (var16 == 1) {
                  var0.drawCursor(var14 + var15 - 19, var7, 4, true);
               }
            }

            var19.dispose();
         }

         if (numDialogLines > dialogViewLines) {
            if (currentDialogLine + dialogViewLines > numDialogLines) {
               var10 = numDialogLines;
            } else {
               var10 = currentDialogLine + dialogViewLines;
            }

            drawScrollBar(var0, var1[0] + var1[2] - 1, var1[1] + 2, var1[3] - 4, currentDialogLine - var6, var10 - var6, numDialogLines - var6, dialogViewLines);
         }

         if (currentDialogLine > var6) {
            setLeftSoftKey((short)1, (short)133);
         } else {
            clearLeftSoftKey();
         }

      }
   }

   private static final void automapState() {
      Game.updateLerpSprites();
      if (!automapDrawn && Game.animatingEffects == 0) {
         updateView();
         repaintFlags &= -65;
         if (state != 6) {
            updateView();
         }
      }

      if (state == 6 || state == 3) {
         drawPlayingSoftKeys();
      }

   }

   private static final void renderOnlyState() {
      if (st_enabled) {
         viewAngle = viewAngle + animAngle & 1023;
         viewPitch = 0;
      } else {
         if (viewX == destX && viewY == destY && viewAngle == destAngle) {
            return;
         }

         if (viewX < destX) {
            viewX += animPos;
            if (viewX > destX) {
               viewX = destX;
            }
         } else if (viewX > destX) {
            viewX -= animPos;
            if (viewX < destX) {
               viewX = destX;
            }
         }

         if (viewY < destY) {
            viewY += animPos;
            if (viewY > destY) {
               viewY = destY;
            }
         } else if (viewY > destY) {
            viewY -= animPos;
            if (viewY < destY) {
               viewY = destY;
            }
         }

         if (viewZ < destZ) {
            ++viewZ;
         } else if (viewZ > destZ) {
            --viewZ;
         }

         if (viewAngle < destAngle) {
            viewAngle += animAngle;
            if (viewAngle > destAngle) {
               viewAngle = destAngle;
            }
         } else if (viewAngle > destAngle) {
            viewAngle -= animAngle;
            if (viewAngle < destAngle) {
               viewAngle = destAngle;
            }
         }

         if (viewPitch < destPitch) {
            viewPitch += pitchStep;
            if (viewPitch > destPitch) {
               viewPitch = destPitch;
            }
         } else if (viewPitch > destPitch) {
            viewPitch -= pitchStep;
            if (viewPitch < destPitch) {
               viewPitch = destPitch;
            }
         }
      }

      lastFrameTime = App.time;
      Render.render((viewX << 4) + 8, (viewY << 4) + 8, (viewZ << 4) + 8, viewAngle, 0, 0, 250);
      Combat.drawWeapon(0, 0);
      repaintFlags |= 80;
   }

   public static final void playingState() {
      if (pushedWall && pushedTime <= App.gameTime) {
         Combat.shiftWeapon(false);
         pushedWall = false;
      }

      if (Player.ce.getStat(0) <= 0) {
         Player.died();
      } else {
         if (Hud.isShiftingCenterMsg()) {
            staleView = true;
         }

         if (knockbackDist == 0 && Game.activePropogators == 0 && Game.animatingEffects == 0 && Game.monstersTurn != 0 && numHelpMessages == 0) {
            Game.updateMonsters();
         }

         Game.updateLerpSprites();
         updateView();
         if (state != 7 && state != 16) {
            if (state == 3) {
               repaintFlags |= 32;
               if (!Game.isCameraActive()) {
                  repaintFlags |= 16;
                  Hud.repaintFlags |= 11;
               }

               if (!Game.isCameraActive() && state == 3) {
                  dequeueHelpDialog();
               }

            }
         }
      }
   }

   private static void menuState() {
      short var0 = -1;
      int var1 = MenuSystem.items[MenuSystem.selectedIndex].flags;
      int var2 = MenuSystem.menu;
      if ((var1 & 32) != 0) {
         var0 = 49;
      } else if (var2 != 18 && var2 != 1) {
         if (var2 == 54 && MenuSystem.peekMenu() == -1) {
            var0 = 39;
         } else if (MenuSystem.type != 5 && MenuSystem.type != 7 && var2 != 69) {
            if (var2 == 60) {
               var0 = 48;
            } else if (MenuSystem.items[MenuSystem.selectedIndex].action != 0) {
               var0 = 128;
            }
         }
      } else {
         var0 = 42;
      }

      if (var2 != 25) {
         clearSoftKeys();
         if (MenuSystem.getStackCount() == 0 && !MenuSystem.goBackToStation && var2 != 24) {
            if (var2 == 28 || var2 == 56 || var2 == 40 || var2 == 55) {
               setLeftSoftKey((short)1, (short)53);
            }
         } else if (MenuSystem.peekMenu() != 20) {
            setLeftSoftKey((short)5, (short)75);
         }

         if (var0 != -1) {
            setRightSoftKey((short)1, var0);
         } else if (MenuSystem.goBackToStation && MenuSystem.menu == 69) {
            setRightSoftKey((short)1, (short)39);
         }
      }

      repaintFlags |= 4;
   }

   private static final void dyingState() {
      if (App.time < deathTime + 750) {
         int var0 = 750 - (App.time - deathTime);
         int var1 = (var0 << 16) / 750;
         viewZ = Render.getHeight(destX, destY) + 18 + (20 * var1 >> 16);
         viewPitch = 96 + (-96 * var1 >> 16);
         int var2 = 16 + (-16 * var1 >> 16);
         updateView();
         renderScene(viewX, viewY, viewZ, viewAngle, viewPitch, var2, 250);
         repaintFlags |= 48;
      } else if (App.time < deathTime + 2750) {
         if (!Render.isFading()) {
            Render.startFade(2000, 1);
         }

         renderScene(viewX, viewY, viewZ, viewAngle, viewPitch, 16, 250);
         repaintFlags |= 48;
      } else {
         Render.destDizzy = 0;
         Render.baseDizzy = 0;
         MenuSystem.setMenu(50);
      }

   }

   public static final void drawScrollBar(Graphics var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var7 != 0 && var6 > var7) {
         int var9 = (var6 + var7 - 1) / var7;
         int var10 = var3 / var9;
         int var11 = var3 - var10 - 14;
         int var12 = var6 - var7;
         if (var12 < var4) {
            var12 = var4;
         }

         int var13 = (var4 << 16) / (var12 << 8);
         int var14 = var13 * (var11 << 8) >> 16;
         if (var5 == var6) {
            var14 = var3 - 14 - var10;
         }

         var14 += 7;
         var0.drawRegion(imgUIImages, 45, 0, 7, 7, var1, var2, 24, 0);
         var0.drawRegion(imgUIImages, 45, 7, 7, 7, var1, var2 + var3, 40, 0);
         var0.setColor(-5002605);
         var0.fillRect(var1 - 7, var2 + 7, 7, var3 - 14);
         var0.setColor(-1585235);
         var0.fillRect(var1 - 7, var2 + var14, 7, var10);
         var0.setColor(-16777216);
         var0.drawRect(var1 - 7, var2 + var14, 6, var10 - 1);
         var0.drawRect(var1 - 7, var2, 6, var3 - 1);
      }
   }

   public static final void uncoverAutomap() {
      if (Game.updateAutomap) {
         int var0 = destX >> 6;
         int var1 = destY >> 6;
         if (var0 >= 0 && var0 < 32 && var1 >= 0 && var1 < 32) {
            for(int var2 = var1 - 1; var2 <= var1 + 1; ++var2) {
               if (var2 >= 0 && var2 < 31) {
                  for(int var3 = var0 - 1; var3 <= var0 + 1; ++var3) {
                     if (var3 >= 0 && var3 < 31) {
                        byte var4 = Render.mapFlags[var2 * 32 + var3];
                        if (var3 == var0 && var2 == var1 || (var4 & 2) == 0) {
                           byte[] var10000 = Render.mapFlags;
                           var10000[var2 * 32 + var3] = (byte)(var10000[var2 * 32 + var3] | 128);
                        }
                     }
                  }
               }
            }

         }
      }
   }

   private static final void drawAutomap(Graphics var0, boolean var1) {
      int var3 = displayRect[2] - 8;
      int var4 = softKeyY - 28;
      int var2;
      if (var3 < var4) {
         var2 = var3 / 32;
      } else {
         var2 = var4 / 32;
      }

      int var5 = displayRect[2] / 2 - var2 * 16 - screenRect[0] + 10;
      int var6 = softKeyY / 2 - var2 * 16 - screenRect[1];
      int var7 = 4194304 / (var2 << 8);
      int var8;
      int var9;
      int var10;
      int var13;
      int var14;
      int var15;
      int var16;
      int var26;
      Entity var27;
      if (var1) {
         drawScroll(var0, -screenRect[0], -screenRect[1], displayRect[2], displayRect[3] - 26, 0);
         var8 = 0;

         byte var11;
         int var12;
         for(var9 = 0; var9 < 32; ++var9) {
            for(var10 = 0; var10 < 32; ++var10) {
               var11 = Render.mapFlags[var8 + var10];
               if ((var11 & 8) != 0) {
                  var0.setColor(-16711936);
                  var0.fillRect(var5 + var2 * var10 + screenRect[0], var6 + var2 * var9 + screenRect[1], var2, var2);
               } else if ((var11 & 128) != 0) {
                  if ((var11 & 16) != 0) {
                     var0.setColor(-65536);
                     var0.fillRect(var5 + var2 * var10 + screenRect[0], var6 + var2 * var9 + screenRect[1], var2, var2);
                  } else if ((var11 & 32) == 0) {
                     if ((var11 & 1) == 0) {
                        var0.setColor(-2043984);
                        var0.fillRect(var5 + var2 * var10 + screenRect[0], var6 + var2 * var9 + screenRect[1] + 1, var2, var2);
                        var0.setColor(-9147303);
                     }
                  } else {
                     var0.setColor(-256);
                     var0.fillRect(var5 + var2 * var10, var6 + var2 * var9, var2, var2);
                     var0.setColor(-16777216);

                     for(var12 = 0; var12 < var2; var12 += 2) {
                        var0.drawLine(var5 + var2 * var10, var6 + var2 * var9 + var12, var5 + var2 * var10 + var2, var6 + var2 * var9 + var12);
                     }

                     var0.drawLine(var5 + var2 * var10, var6 + var2 * var9, var5 + var2 * var10, var6 + var2 * var9 + var2);
                     var0.drawLine(var5 + var2 * var10 + var2, var6 + var2 * var9, var5 + var2 * var10 + var2, var6 + var2 * var9 + var2);
                  }
               }
            }

            var8 += 32;
         }

         var0.setColor(-9147303);

         for(var9 = 0; var9 < Render.numLines; ++var9) {
            var10 = Render.lineFlags[var9 >> 1] >> ((var9 & 1) << 2) & 15;
            if ((var10 & 8) != 0) {
               var26 = var10 & 7;
               if (var26 == 6 || var26 == 0) {
                  var12 = ((Render.lineXs[var9 << 1] & 255) << 19) / var7;
                  var13 = ((Render.lineXs[(var9 << 1) + 1] & 255) << 19) / var7;
                  var14 = ((Render.lineYs[var9 << 1] & 255) << 19) / var7;
                  var15 = ((Render.lineYs[(var9 << 1) + 1] & 255) << 19) / var7;
                  var0.drawLine(var5 + (var12 + 128 >> 8), var6 + (var14 + 128 >> 8), var5 + (var13 + 128 >> 8), var6 + (var15 + 128 >> 8));
               }
            }
         }

         var8 = 0;

         for(var9 = 0; var9 < 32; ++var9) {
            for(var10 = 0; var10 < 32; ++var10) {
               var11 = Render.mapFlags[var8 + var10];
               if ((var11 & 56) == 0) {
                  for(var27 = Game.entityDb[var8 + var10]; var27 != null; var27 = var27.nextOnTile) {
                     if (var27 != Game.entities[1] && var27 != Game.entities[0]) {
                        var13 = var27.getSprite();
                        var14 = Render.mapSpriteInfo[var13];
                        var15 = (var14 & '\uff00') >> 8;
                        if (0 != (var14 & 2097152) && 0 == (var14 & 65536)) {
                           var16 = 0;
                           boolean var17 = false;
                           boolean var18 = false;
                           short var19 = 2;
                           short var20;
                           if (var27.def.eType == 5) {
                              var20 = var27.def.tileIndex;
                              if (var20 != 271 && var20 != 272) {
                                 if (var20 != 273 && var20 != 274) {
                                    var16 = -11915240;
                                 } else {
                                    var16 = -31744;
                                 }
                              } else {
                                 var16 = -16727809;
                              }

                              var18 = true;
                           } else if ((var14 & 4194304) != 0) {
                              var16 = -9147303;
                              var18 = true;
                           } else if (var27.def.eType == 13) {
                              var16 = -7503768;
                              var18 = true;
                           } else if (var27.def.eType == 3) {
                              var17 = true;
                              var16 = -16776961;
                              var19 = 128;
                           } else if (var27.def.eType == 10 && var15 == 0) {
                              if (var27.def.eSubType != 8) {
                                 var16 = -8388353;
                              }
                           } else if (var27.def.eType == 2) {
                              if ((var27.monster.flags & 2048) != 0) {
                                 var17 = true;
                                 var16 = -16776961;
                              } else {
                                 var16 = -32768;
                              }

                              var19 = 128;
                           } else if (var27.def.eType == 7) {
                              var16 = -7503768;
                              if (Player.god && var15 == 0) {
                                 var20 = var27.def.tileIndex;
                                 if (var20 == 153 || var20 == 121 || var20 == 158) {
                                    var16 = -16711702;
                                 }
                              } else if (var27.def.tileIndex == 173 || var27.def.tileIndex == 180) {
                                 var16 = 0;
                              }
                           } else if (Player.god && var27.def.eType == 6 && var27.def.eSubType != 4) {
                              var16 = -16711702;
                           }

                           if (var16 != 0 && ((var11 & var19) != 0 || (var19 & 128) == 0)) {
                              var0.setColor(var16);
                              if ((var14 & 251658240) != 0) {
                                 int var22;
                                 int var30 = var22 = Render.mapSprites[Render.S_X + var13];
                                 int var23;
                                 int var21 = var23 = Render.mapSprites[Render.S_Y + var13];
                                 if ((var14 & 50331648) != 0) {
                                    var30 -= 32;
                                    var22 += 32;
                                 } else {
                                    var21 -= 32;
                                    var23 += 32;
                                 }

                                 var30 = (var30 << 16) / var7 + 1 + 128 >> 8;
                                 var22 = (var22 << 16) / var7 + 128 >> 8;
                                 var21 = (var21 << 16) / var7 + 128 >> 8;
                                 var23 = (var23 << 16) / var7 + 128 >> 8;
                                 if (var18) {
                                    var0.drawLine(var5 + var30, var6 + var21, var5 + var22, var6 + var23);
                                 } else {
                                    var30 = var30 + var22 >> 1;
                                    var21 = var21 + var23 >> 1;
                                    var0.fillRect(var5 + var30 - var2 / 4, var6 + var21 - var2 / 4, var2 / 2, var2 / 2);
                                 }
                              } else if (var17) {
                                 var0.fillRect(var5 + var2 * var10 + var2 / 4, var6 + var2 * var9 + var2 / 4, var2 / 2 + 2, var2 / 2 + 2);
                              } else {
                                 var0.fillRect(var5 + var2 * var10 + var2 / 4 + 1, var6 + var2 * var9 + var2 / 4 + 1, var2 / 2, var2 / 2);
                              }
                           }
                        }
                     }
                  }
               }
            }

            var8 += 32;
         }

         boolean var24 = false;
      }

      for(var8 = 0; var8 < Player.numNotebookIndexes; ++var8) {
         if (!Player.isQuestDone(var8) && !Player.isQuestFailed(var8)) {
            var9 = Player.notebookPositions[var8] >> 5 & 31;
            var10 = Player.notebookPositions[var8] & 31;
            if (var9 + var10 != 0 && (128 & Render.mapFlags[var10 * 32 + var9]) != 0) {
               var26 = (App.time / 1024 & 1) == 0 ? -65536 : -16711936;
               var0.setColor(var26);
               var27 = Game.findMapEntity(var9 << 6, var10 << 6, 32);
               if (null != var27) {
                  var13 = var27.getSprite();
                  var14 = Render.mapSpriteInfo[var13];
                  int var28;
                  var15 = var28 = Render.mapSprites[Render.S_X + var13];
                  int var29;
                  var16 = var29 = Render.mapSprites[Render.S_Y + var13];
                  if ((var14 & 50331648) != 0) {
                     var15 -= 32;
                     var28 += 32;
                  } else {
                     var16 -= 32;
                     var29 += 32;
                  }

                  var15 = (var15 << 16) / var7 + 1 + 128 >> 8;
                  var28 = (var28 << 16) / var7 + 128 >> 8;
                  var16 = (var16 << 16) / var7 + 128 >> 8;
                  var29 = (var29 << 16) / var7 + 128 >> 8;
                  var0.drawLine(var5 + var15, var6 + var16, var5 + var28, var6 + var29);
               } else {
                  var0.fillRect(var5 + var2 * var9 + var2 / 4, var6 + var2 * var10 + var2 / 4, var2 / 2 + 2, var2 / 2 + 2);
               }
            }
         }
      }

      if (App.time > automapBlinkTime) {
         automapBlinkTime = App.time + 333;
         automapBlinkState = !automapBlinkState;
      }

      byte var25 = 0;
      switch(destAngle & 1023) {
      case 0:
         var25 = 2;
         break;
      case 256:
         var25 = 0;
         break;
      case 512:
         var25 = 3;
         break;
      case 768:
         var25 = 1;
      }

      var8 = var25 * 4;
      if (automapBlinkState) {
         var8 += 16;
      }

      var9 = var5 + var2 * (viewX - 32) / 64 + var2 / 2;
      var10 = var6 + var2 * (viewY - 32) / 64 + var2 / 2;
      if (var10 < screenRect[1] + screenRect[3]) {
         var0.drawRegion(imgMapCursor, 0, var8, 4, 4, var9, var10, 3, 0);
      }

   }

   private static void closeDialog(boolean var0) {
      dialogClosing = true;
      specialLootIcon = -1;
      showingLoot = false;
      Player.unpause(App.time - dialogStartTime);
      dialogBuffer.dispose();
      dialogBuffer = null;
      if (numHelpMessages == 0 && (dialogStyle == 3 || dialogStyle == 4 || dialogStyle == 2 && dialogType == 1)) {
         Game.queueAdvanceTurn = true;
      }

      if (Render.chatZoom && Combat.curTarget != null) {
         int var1 = Combat.curTarget.getSprite();
         Render.mapSpriteInfo[var1] = Render.mapSpriteInfo[var1] & -65281 | 0;
         Render.chatZoom = false;
         Combat.curTarget = null;
      }

      if (Game.isCameraActive()) {
         Game.activeCameraTime = App.gameTime - Game.activeCameraTime;
         setState(18);
      } else if (oldState == 5 && !combatDone) {
         setState(5);
      } else {
         setState(3);
      }

      if (dialogResumeMenu) {
         setState(1);
      }

      dialogClosing = false;
      if (dialogResumeScriptAfterClosed) {
         Game.skipDialog = var0;
         dialogThread.run();
         Game.skipDialog = false;
      }

      repaintFlags |= 64;
   }

   private static final void prepareDialog(Text var0, int var1, int var2) {
      int var3 = 0;
      int var4 = 0;
      Text var5 = Text.getSmallBuffer();
      if (var1 == 3) {
         dialogViewLines = 4;
      } else if (var1 == 8) {
         dialogViewLines = 3;
      } else if (var1 == 2) {
         dialogViewLines = 3;
      } else {
         dialogViewLines = 4;
      }

      if (var1 == 1 || var1 == 5 && ((var2 & 2) != 0 || (var2 & 4) != 0)) {
         updateFacingEntity = true;
         Entity var6 = Player.facingEntity;
         if (var6 != null && var6.def != null && (var6.def.eType == 2 || var6.def.eType == 3)) {
            short var7 = 160;
            Render.chatZoom = true;
            Combat.curTarget = var6;
            int var8 = var6.getSprite();
            if (var6.def.eType == 2) {
               var7 = 128;
            }

            Render.mapSpriteInfo[var8] = Render.mapSpriteInfo[var8] & -65281 | var7 << 8;
            Game.scriptStateVars[4] = 0;
         }
      }

      if (dialogBuffer == null) {
         dialogBuffer = Text.getLargeBuffer();
      } else {
         dialogBuffer.setLength(0);
      }

      if ((var2 & 4) != 0 || (var2 & 1) != 0) {
         Game.scriptStateVars[4] = 1;
         var5.setLength(0);
         Text.composeText((short)1, (short)50, var5);
         var0.append(var5);
      }

      int var12;
      if (var1 == 8) {
         if (Player.statusEffects[15] > 0) {
            var5.setLength(0);
            Text.composeText((short)1, (short)129, var5);
            var0.append(var5);
         }

         var12 = dialogMaxChars;
         var5.setLength(0);
         var5.append("   ");

         for(int var13 = 0; var13 < 2; ++var13) {
            Text var14 = dialogBuffer;
            boolean var9 = false;
            byte var10 = 0;
            var14.append(var5);
            int var15 = var14.length();
            var14.append(var0, var10);
            int var16 = var10 + var14.wrapText(var15, var12 - var5.length(), 1, '|');
            var14.append(var5);
            var15 = var14.length();
            var14.append(var0, var16);
            var16 += var14.wrapText(var15, var12 - var5.length(), 1, '|');
            var15 = var14.length();
            var14.append(var0, var16);
            int var10000 = var16 + var14.wrapText(var15, var12, -1, '|');
            if (var13 == 0) {
               int var11 = var14.getNumLines();
               if (var11 <= 3) {
                  break;
               }

               var14.setLength(0);
               var12 = dialogWithBarMaxChars;
            }
         }
      } else if (var1 == 3) {
         dialogBuffer.append(var0);
         dialogBuffer.wrapText(scrollMaxChars);
         var12 = dialogBuffer.getNumLines();
         if (var12 > dialogViewLines) {
            dialogBuffer.setLength(0);
            dialogBuffer.append(var0);
            dialogBuffer.wrapText(scrollWithBarMaxChars);
         }
      } else {
         dialogBuffer.append(var0);
         dialogBuffer.wrapText(dialogMaxChars);
         var12 = dialogBuffer.getNumLines();
         if (var1 == 2 || var1 == 9) {
            --var12;
         }

         if (var12 > dialogViewLines) {
            dialogBuffer.setLength(0);
            dialogBuffer.append(var0);
            dialogBuffer.wrapText(dialogWithBarMaxChars);
         }
      }

      var12 = dialogBuffer.length();

      for(numDialogLines = 0; var3 < var12; ++var3) {
         if (dialogBuffer.charAt(var3) == '|') {
            dialogIndexes[numDialogLines * 2] = (short)var4;
            dialogIndexes[numDialogLines * 2 + 1] = (short)(var3 - var4);
            ++numDialogLines;
            var4 = var3 + 1;
         }
      }

      dialogIndexes[numDialogLines * 2] = (short)var4;
      dialogIndexes[numDialogLines * 2 + 1] = (short)(var12 - var4);
      ++numDialogLines;
      currentDialogLine = 0;
      dialogLineStartTime = App.time;
      dialogTypeLineIdx = 0;
      dialogStartTime = App.time;
      dialogItem = null;
      dialogFlags = var2;
      dialogStyle = var1;
      var5.dispose();
   }

   public static final void startDialog(ScriptThread var0, short var1, int var2, int var3) {
      startDialog(var0, (short)1, var1, var2, var3, false);
   }

   public static final void startDialog(ScriptThread var0, short var1, short var2, int var3, int var4, boolean var5) {
      Text var6 = Text.getLargeBuffer();
      Text.composeText(var1, var2, var6);
      startDialog(var0, var6, var3, var4, var5);
      var6.dispose();
   }

   public static final void startDialog(ScriptThread var0, Text var1, int var2, int var3) {
      startDialog(var0, var1, var2, var3, false);
   }

   public static final void startDialog(ScriptThread var0, Text var1, int var2, int var3, boolean var4) {
      dialogResumeScriptAfterClosed = var4;
      dialogResumeMenu = false;
      dialogThread = var0;
      readyWeaponSound = 0;
      prepareDialog(var1, var2, var3);
      setState(8);
   }

   private static final void renderScene(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      staleView = true;
      if (staleView || staleTime != 0 && App.time >= staleTime) {
         staleView = false;
         staleTime = 0;
         lastFrameTime = App.time;
         beforeRender = App.getUpTimeMs();
         Render.render((var0 << 4) + 8, (var1 << 4) + 8, (var2 << 4) + 8, var3, var4, var5, var6);
         afterRender = App.getUpTimeMs();
         ++renderSceneCount;
         if (!isZoomedIn) {
            Combat.drawWeapon(shakeX, shakeY);
         }

         if (Render.isFading()) {
            if (Render.fadeScene()) {
               repaintFlags |= 64;
            }
         } else {
            repaintFlags |= 64;
         }

      }
   }

   public static final void startSpeedTest(boolean var0) {
      renderOnly = true;
      st_enabled = true;
      st_count = 1;

      for(int var1 = 0; var1 < 10; ++var1) {
         st_fields[var1] = 0;
      }

      if (!var0) {
         animAngle = 4;
         destAngle = viewAngle;
         setState(10);
      }

   }

   public static final void backToMain(boolean var0) {
      loadMapID = 0;
      Player.reset();
      unloadMedia();
      Render.endFade();

      try {
         MenuSystem.imgMainBG = Image.createImage("/logo.png");
      } catch (Exception var3) {
         App.Error(var3, -1);
      }

      System.gc();
      if (var0) {
         try {
            Thread.sleep(1L);
         } catch (InterruptedException var2) {
         }

         clearEvents();
         if (!skipIntro) {
            if (Text.selectLanguage) {
               MenuSystem.clearStack();
               MenuSystem.selectedIndex = 2;
               MenuSystem.pushMenu(20, MenuSystem.selectedIndex);
               MenuSystem.setMenu(19);
            } else {
               MenuSystem.setMenu(20);
            }
         } else {
            Player.reset();
            Render.unloadMap();
            Game.unloadMapData();
            loadMap(startupMap, true);
         }
      } else {
         MenuSystem.setMenu(3);
      }

   }

   public static final void drawPlayingSoftKeys() {
      if (isZoomedIn) {
         setSoftKeys((short)1, (short)53, (short)1, (short)52);
      } else if (state == 6) {
         setSoftKeys((short)1, (short)52, (short)1, (short)54);
      } else {
         setSoftKeys((short)1, (short)52, (short)1, (short)56);
         if (isChickenKicking) {
            clearRightSoftKey();
         }
      }

   }

   public static final void resume() {
      invalidateRect();
      repaintFlags |= 2;
      forcePump = true;
      App.time = App.lastTime = App.getUpTimeMs();
      if (state != 1) {
         if (state != 14 && state != 9) {
            if (state == 15) {
               repaintFlags &= -3;
            } else if (state == 6) {
               automapDrawn = false;
            } else if (state != 21 && state != 7 && state != 16) {
               if (state == 3) {
                  if (isChickenKicking && kickingPhase == 5) {
                     repaintFlags &= -3;
                  }
               } else if (state == 8) {
                  stateVars[0] = 0;
                  stateChanged = true;
               } else if (state == 2) {
                  repaintFlags &= -3;
               }
            } else {
               repaintFlags &= -3;
            }
         } else {
            repaintFlags &= -3;
         }
      }

      clearEvents();
   }

   private static final void changeStoryPage(int var0) {
      if (var0 < 0 && storyPage == 0) {
         if (state == 9) {
            backToMain(false);
         }

      } else {
         storyPage += var0;
      }
   }

   private static final void drawStory(Graphics var0) {
      if (storyPage < storyTotalPages) {
         drawScroll(var0, 0, 0, displayRect[2], displayRect[3], 1);
         int var3 = App.getUpTimeMs();
         byte var4 = OSC_CYCLE[var3 / 100 % 4];
         int var1 = -screenRect[0] + displayRect[2] - 6;
         int var2 = -screenRect[1] + displayRect[3] - 2;
         Text var5 = Text.getLargeBuffer();
         if (state != 14 || storyPage > 0) {
            var5.setLength(0);
            Text.composeText((short)5, (short)75, var5);
            var5.dehyphenate();
            var0.drawString(var5, -screenRect[0] + 15, var2, 36);
         }

         var5.setLength(0);
         Text var6 = Text.getSmallBuffer();
         if (storyPage < storyTotalPages - 1) {
            Text.composeText((short)1, (short)57, var5);
            Text.composeText((short)1, (short)39, var6);
         } else {
            Text.composeText((short)1, (short)42, var5);
         }

         var5.dehyphenate();
         var6.dehyphenate();
         var0.drawString(var5, var1, var2, 40);
         var0.drawString(var6, var1, 2, 8);
         if (stateVars[0] == 0) {
            var0.drawCursor(var4 + -screenRect[0] + 3, var2 - 1, 32);
         } else if (stateVars[0] == 1) {
            var0.drawCursor(var1 + var4 - (var5.length() * 7 + 5), var2 - 1, 40);
         } else {
            var0.drawCursor(var1 + var4 - (var6.length() * 7 + 5), 13, 40);
         }

         var5.dispose();
         var6.dispose();
         int var7 = storyIndexes[storyPage + 1] - storyIndexes[storyPage];
         var0.drawString(dialogBuffer, storyX, storyY, 16, 0, storyIndexes[storyPage], var7);
      }
   }

   public static final void dequeueHelpDialog() {
      dequeueHelpDialog(false);
   }

   public static final void dequeueHelpDialog(boolean var0) {
      if (numHelpMessages != 0) {
         if (state != 8 && !dialogClosing) {
            if (var0 || state == 3 || Game.monstersTurn != 0) {
               if (!Game.secretActive) {
                  byte var1 = 2;
                  byte var2 = 0;
                  Text var3 = Text.getLargeBuffer();
                  dialogType = helpMessageTypes[0];
                  Object var4 = helpMessageObjs[0];
                  short var5 = (short)helpMessageThreads[0];
                  int var9;
                  if (dialogType == 1) {
                     EntityDef var6 = (EntityDef)var4;
                     byte var7 = var6.eSubType;
                     byte var8 = -1;
                     if (var7 == 0) {
                        if (var6.parm >= 0 && var6.parm < 15) {
                           var8 = 30;
                        } else if (var6.parm >= 16 && var6.parm < 18) {
                           var8 = 33;
                        } else if (var6.parm == 18) {
                           var8 = 32;
                        } else if (var6.parm == 22) {
                           var8 = 31;
                        }
                     } else if (var7 == 1) {
                        var8 = 34;
                     } else if (var7 != 6 && var7 != 2) {
                        App.Error(0);
                        return;
                     }

                     if (null != var6) {
                        Text.composeText((short)2, var6.longName, var3);
                        var3.append("|");
                        Text.composeText((short)2, var6.description, var3);
                        if (var8 != -1) {
                           var3.append(" ");
                           Text.composeText((short)1, var8, var3);
                        }
                     }
                  } else if (dialogType == 2) {
                     var9 = helpMessageInts[0];
                     Text.composeText((short)(var9 >> 16), (short)(var9 & '\uffff'), var3);
                  } else if (dialogType == 3) {
                     var1 = 9;
                     specialLootIcon = 4;
                     var3.dispose();
                     var3 = (Text)var4;
                  } else {
                     var3.dispose();
                     var3 = (Text)var4;
                  }

                  for(var9 = 0; var9 < 15; ++var9) {
                     helpMessageTypes[var9] = helpMessageTypes[var9 + 1];
                     helpMessageInts[var9] = helpMessageInts[var9 + 1];
                     helpMessageObjs[var9] = helpMessageObjs[var9 + 1];
                     helpMessageThreads[var9] = helpMessageThreads[var9 + 1];
                  }

                  helpMessageObjs[15] = var4;
                  --numHelpMessages;
                  if (Player.enableHelp) {
                     if (var5 == -1) {
                        startDialog((ScriptThread)null, var3, var1, var2, false);
                     } else {
                        startDialog(Game.scriptThreads[var5], var3, var1, var2, true);
                     }
                  }

                  var3.dispose();
               }
            }
         }
      }
   }

   public static final void enqueueHelpDialog(short var0) {
      enqueueHelpDialog((short)1, var0, (byte)-1);
   }

   public static final boolean enqueueHelpDialog(short var0, short var1, byte var2) {
      if (Player.enableHelp && !isChickenKicking && state != 13) {
         if (numHelpMessages == 16) {
            App.Error(41);
            return false;
         } else {
            helpMessageTypes[numHelpMessages] = 2;
            helpMessageInts[numHelpMessages] = var0 << 16 | var1;
            helpMessageObjs[numHelpMessages] = null;
            helpMessageThreads[numHelpMessages] = var2;
            ++numHelpMessages;
            if (state == 3) {
               dequeueHelpDialog();
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static final boolean enqueueHelpDialog(Text var0) {
      return enqueueHelpDialog(var0, 0);
   }

   public static final boolean enqueueHelpDialog(Text var0, int var1) {
      if (Player.enableHelp && !isChickenKicking && state != 13) {
         if (numHelpMessages == 16) {
            App.Error(41);
            return false;
         } else {
            helpMessageTypes[numHelpMessages] = var1;
            helpMessageObjs[numHelpMessages] = var0;
            helpMessageThreads[numHelpMessages] = -1;
            ++numHelpMessages;
            if (state == 3) {
               dequeueHelpDialog();
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static final void enqueueHelpDialog(EntityDef var0) {
      if (Player.enableHelp && !isChickenKicking && state != 13) {
         if (numHelpMessages == 16) {
            App.Error(41);
         } else {
            helpMessageTypes[numHelpMessages] = 1;
            helpMessageObjs[numHelpMessages] = var0;
            helpMessageThreads[numHelpMessages] = -1;
            ++numHelpMessages;
            if (state == 3) {
               dequeueHelpDialog();
            }

         }
      }
   }

   public static final void updateView() {
      if (App.time < shakeTime) {
         shakeX = App.nextByte() % (shakeIntensity * 2) - shakeIntensity;
         shakeY = App.nextByte() % (shakeIntensity * 2) - shakeIntensity;
         staleView = true;
      } else if (shakeX != 0 || shakeY != 0) {
         staleView = true;
         shakeY = 0;
         shakeX = 0;
      }

      if (Game.isCameraActive()) {
         Game.activeCamera.Render();
         repaintFlags |= 48;
         Hud.repaintFlags &= 16;
      } else {
         if (knockbackDist > 0 && viewX == destX && viewY == destY) {
            attemptMove(viewX + knockbackX * 64, viewY + knockbackY * 64, true);
         }

         boolean var0 = viewX == destX && viewY == destY;
         boolean var1 = viewAngle == destAngle;
         int var2 = animPos;
         int var3 = animAngle;
         if (Player.statusEffects[2] > 0 || knockbackDist > 0) {
            var2 += var2 / 2;
            var3 += var3 / 2;
         }

         if (viewX != destX || viewY != destY || viewZ != destZ || viewAngle != destAngle) {
            invalidateRect();
         }

         if (viewX < destX) {
            viewX += var2;
            if (viewX > destX) {
               viewX = destX;
            }
         } else if (viewX > destX) {
            viewX -= var2;
            if (viewX < destX) {
               viewX = destX;
            }
         }

         if (viewY < destY) {
            viewY += var2;
            if (viewY > destY) {
               viewY = destY;
            }
         } else if (viewY > destY) {
            viewY -= var2;
            if (viewY < destY) {
               viewY = destY;
            }
         }

         if (viewZ < destZ) {
            viewZ += zStep;
            if (viewZ > destZ) {
               viewZ = destZ;
            }
         } else if (viewZ > destZ) {
            viewZ -= zStep;
            if (viewZ < destZ) {
               viewZ = destZ;
            }
         }

         if (viewAngle < destAngle) {
            viewAngle += var3;
            if (viewAngle > destAngle) {
               viewAngle = destAngle;
            }
         } else if (viewAngle > destAngle) {
            viewAngle -= var3;
            if (viewAngle < destAngle) {
               viewAngle = destAngle;
            }
         }

         if (viewPitch < destPitch) {
            viewPitch += pitchStep;
            if (viewPitch > destPitch) {
               updateFacingEntity = true;
               viewPitch = destPitch;
            }
         } else if (viewPitch > destPitch) {
            viewPitch -= pitchStep;
            if (viewPitch < destPitch) {
               updateFacingEntity = true;
               viewPitch = destPitch;
            }
         }

         int var4 = viewZ;
         int var5;
         int var6;
         if (knockbackDist != 0) {
            var5 = viewX;
            if (knockbackX == 0) {
               var5 = viewY;
            }

            var6 = (Math.abs(var5 - knockbackStart) << 9) / knockbackWorldDist;
            var4 = viewZ + (10 * Render.sinTable[var6 & 1023] >> 16);
         }

         if (state == 6) {
            viewX = destX;
            viewY = destY;
            viewZ = destZ;
            viewAngle = destAngle;
            viewPitch = destPitch;
         }

         if (state == 5) {
            Game.gsprite_update(App.time);
         }

         if (Game.gotoTriggered) {
            Game.gotoTriggered = false;
            var5 = flagForFacingDir(8);
            Game.eventFlagsForMovement(-1, -1, -1, -1);
            Game.executeTile(destX >> 6, destY >> 6, Game.eventFlags[1], true);
            Game.executeTile(destX >> 6, destY >> 6, var5, true);
         } else if (!var0 && viewX == destX && viewY == destY) {
            finishMovement();
         }

         if (!var1 && viewAngle == destAngle) {
            finishRotation(true);
         }

         if (Game.isCameraActive()) {
            Game.activeCamera.Update(Game.activeCameraKey, App.gameTime - Game.activeCameraTime);
            Game.activeCamera.Render();
            repaintFlags |= 48;
         } else {
            if (isZoomedIn) {
               var5 = zoomAccuracy * Render.sinTable[(App.time - zoomStateTime) / 2 & 1023] >> 24;
               var6 = zoomAccuracy * Render.sinTable[(App.time - zoomStateTime) / 3 & 1023] >> 24;
               int var7 = zoomFOV;
               if (App.time < zoomTime) {
                  var7 = zoomDestFOV + (zoomFOV - zoomDestFOV) * (zoomTime - App.time) / 360;
               } else {
                  zoomTime = 0;
                  var7 = zoomFOV = zoomDestFOV;
               }

               int var8 = zoomPitch + viewPitch;
               if (Combat.curAttacker == null && state == 5 && Combat.nextStage == 1) {
                  int var9 = 512 * ((App.gameTime - Combat.animStartTime << 16) / Combat.animTime) >> 16;
                  var8 += Render.sinTable[var9 & 1023] * 28 >> 16;
               }

               renderScene(viewX, viewY, viewZ, viewAngle + zoomAngle + var5, var8 + var6, viewRoll, var7);
               updateFacingEntity = true;
            } else if (loadMapID != 0) {
               renderScene(viewX, viewY, var4, viewAngle, viewPitch, viewRoll, 250);
            }

         }
      }
   }

   public static final void clearSoftKeys() {
      softKeyLeftID = -1;
      softKeyRightID = -1;
      repaintFlags |= 2;
   }

   public static final void clearLeftSoftKey() {
      softKeyLeftID = -1;
      repaintFlags |= 2;
   }

   public static final void clearRightSoftKey() {
      softKeyRightID = -1;
      repaintFlags |= 2;
   }

   public static final void setLeftSoftKey(short var0, short var1) {
      if (displaySoftKeys) {
         softKeyLeftID = Text.STRINGID(var0, var1);
         repaintFlags |= 2;
      }
   }

   public static final void setRightSoftKey(short var0, short var1) {
      if (displaySoftKeys) {
         softKeyRightID = Text.STRINGID(var0, var1);
         repaintFlags |= 2;
      }
   }

   public static final void setSoftKeys(short var0, short var1, short var2, short var3) {
      if (displaySoftKeys) {
         softKeyLeftID = Text.STRINGID(var0, var1);
         softKeyRightID = Text.STRINGID(var2, var3);
         repaintFlags |= 2;
      }
   }

   public static final void drawSoftKeys(Graphics var0) {
      byte var1 = 0;
      int var2 = softKeyY;
      var0.fillRegion(Hud.imgSoftKeyFill, var1, var2, displayRect[2], Hud.imgSoftKeyFill.getHeight());
      if (!mediaLoading) {
         Text var3 = Text.getSmallBuffer();
         int var4;
         if (softKeyLeftID != -1) {
            var3.setLength(0);
            Text.composeText(softKeyLeftID, var3);
            var3.dehyphenate();
            var4 = 9 + var3.length() * 7 + 8;
            var0.setColor(-13553359);
            var0.drawLine(var1 + var4, var2, var1 + var4, var2 + 26 - 1);
            var0.setColor(-8354415);
            var0.drawLine(var1 + var4 + 1, var2, var1 + var4 + 1, var2 + 26 - 1);
            var0.drawString(var3, var1 + var4 / 2, var2 + 26 - 12 - 1, 3);
         }

         if (softKeyRightID != -1) {
            var3.setLength(0);
            Text.composeText(softKeyRightID, var3);
            var3.dehyphenate();
            var4 = displayRect[2] - (9 + var3.length() * 7 + 8);
            var0.setColor(-13553359);
            var0.drawLine(var1 + var4, var2, var1 + var4, var2 + 26 - 1);
            var0.setColor(-8354415);
            var0.drawLine(var1 + var4 + 1, var2, var1 + var4 + 1, var2 + 26 - 1);
            var0.drawString(var3, var1 + displayRect[2] - (displayRect[2] - var4) / 2, var2 + 26 - 12 - 1, 3);
         }

         var3.dispose();
      }
   }

   public static final void setLoadingBarText(short var0, short var1) {
      loadingStringType = var0;
      loadingStringID = var1;
      loadingFlags |= 3;
   }

   public static final void updateLoadingBar(boolean var0) {
      showBacklight();
      App.checkPausedState();
      if (!var0) {
         int var1 = App.getUpTimeMs();
         if (var1 - lastPacifierUpdate < 75) {
            return;
         }

         lastPacifierUpdate = var1;
      }

      if (loadingStringType == -1 || loadingStringID == -1) {
         setLoadingBarText((short)1, (short)58);
      }

      loadingFlags |= 2;
      repaintFlags |= 128;
      App.canvas.flushGraphics();
   }

   public static final void drawLoadingBar(Graphics var0) {
      int var1 = SCR_CX - 75;
      int var2 = SCR_CY - 22;
      int var3 = var1 + 134;
      if ((loadingFlags & 1) != 0) {
         Text var4 = Text.getSmallBuffer();
         loadingFlags &= -2;
         var0.eraseRgn(displayRect);
         var0.fillRegion(imgFabricBg, var1, var2, 150, 44);
         var0.setColor(-1);
         var0.drawRect(var1, var2, 150, 44);
         Text.composeText(loadingStringType, loadingStringID, var4);
         var4.dehyphenate();
         var0.drawString(var4, SCR_CX, SCR_CY - 12 - 6, 17);
         var4.setLength(0);
         Text.composeText((short)1, (short)59, var4);
         var4.dehyphenate();
         var0.drawString(var4, SCR_CX, SCR_CY - 6, 17);
         var4.dispose();
      }

      if ((loadingFlags & 2) != 0) {
         loadingFlags &= -3;
         var1 = SCR_CX - 75 + 8;
         var2 = SCR_CY - 22 + 30;
         var3 = var1 + 134;
         pacifierX += 10;
         if (pacifierX >= var3 - 5 || pacifierX < var1 + 1) {
            pacifierX = var1 + 1;
         }

         var0.setColor(-16777216);
         var0.fillRect(var1, var2, 134, 12);
         var0.setColor(-1);
         var0.drawRect(var1, var2, 134, 12);
         int var5 = Math.min(30, var3 - pacifierX);
         var0.drawRegion(Hud.imgAmmoIcons, 0, 14, var5, 14, pacifierX, var2);
      }

   }

   private static final void unloadMedia() {
      freeRuntimeData();
      App.beginImageUnload();
      MenuSystem.background = null;
      App.endImageUnload();
      Game.unloadMapData();
      Render.unloadMap();
      System.gc();
   }

   public static final void invalidateRect() {
      staleView = true;
   }

   public static final int getRecentLoadType() {
      return recentBriefSave ? 2 : 1;
   }

   public static void showBacklight() {
   }

   private static void initZoom() {
      zoomTime = 0;
      zoomCurFOVPercent = 0;
      zoomDestFOV = 150;
      zoomFOV = 150;
      zoomAngle = 0;
      zoomPitch = 0;
      zoomTurn = 0;
      destPitch = 0;
      viewPitch = 0;
      zoomStateTime = App.time;
      isZoomedIn = true;
      int var0 = Player.ce.getStatPercent(5);
      var0 = (256 - var0 << 8) / 26;
      var0 = Math.max(0, Math.min(var0, 256));
      zoomAccuracy = 2560 * var0 >> 8;
      zoomMinFOVPercent = 256;
      if (Player.ce.weapon == 10) {
         zoomAccuracy = zoomAccuracy * 156 >> 8;
      }

      zoomMaxAngle = 64 - (zoomAccuracy >> 8);
      int var1 = Hud.imgScope.getWidth();
      int var2 = Hud.imgScope.getHeight();
      int var3 = (viewRect[2] - 2 * var1) / 2 & -2;
      int var4 = (viewRect[3] - 2 * var2) / 2 & -2;
      TinyGL.setViewport(var3, var4, 2 * var1, 2 * var2);
      Render.startFade(500, 2);
      drawPlayingSoftKeys();
   }

   private static boolean handleZoomEvents(int var0, int var1) {
      if (zoomTime == 0 && Game.activePropogators == 0 && Game.animatingEffects == 0 && Game.snapMonsters(false)) {
         int var2 = 5 + (20 * (256 - zoomCurFOVPercent) >> 8);
         if (var1 != 5 && var1 != 15) {
            if (var1 == 7) {
               Hud.msgCount = 0;
               MenuSystem.setMenu(28);
               return true;
            } else {
               if (var1 == 4) {
                  zoomAngle -= var2;
                  updateFacingEntity = true;
                  ++zoomTurn;
               } else if (var1 == 3) {
                  zoomAngle += var2;
                  updateFacingEntity = true;
                  ++zoomTurn;
               } else if (var1 == 2) {
                  zoomPitch -= var2;
                  ++zoomTurn;
               } else if (var1 == 1) {
                  zoomPitch += var2;
                  ++zoomTurn;
               } else if (var1 == 14) {
                  Hud.addMessage((short)44);
                  Game.touchTile(destX, destY, false);
                  Game.advanceTurn();
                  invalidateRect();
                  zoomTurn = 0;
               } else if (var1 == 12) {
                  if (zoomCurFOVPercent < zoomMinFOVPercent) {
                     zoomCurFOVPercent += 64;
                     ++zoomTurn;
                     zoomDestFOV = 150 + (-70 * zoomCurFOVPercent >> 8);
                     zoomTime = App.time + 360;
                     Sound.playSound(14);
                  }
               } else if (var1 == 11) {
                  if (zoomCurFOVPercent > 0) {
                     zoomCurFOVPercent -= 64;
                     ++zoomTurn;
                     zoomDestFOV = 150 + (-70 * zoomCurFOVPercent >> 8);
                     zoomTime = App.time + 360;
                     Sound.playSound(14);
                  }

                  ++zoomTurn;
               } else if (var1 == 6) {
                  zoomTurn = 0;
                  return handlePlayingEvents(var0, var1);
               }

               if (zoomPitch < -zoomMaxAngle) {
                  zoomPitch = -zoomMaxAngle;
               } else if (zoomPitch > zoomMaxAngle) {
                  zoomPitch = zoomMaxAngle;
               }

               if ((zoomTurn & 7) == 7) {
                  Game.advanceTurn();
               }

               return true;
            }
         } else {
            isZoomedIn = false;
            viewAngle += zoomAngle;
            short var3 = 255;
            destAngle = viewAngle = viewAngle + (var3 >> 1) & ~var3;
            Render.startFade(500, 2);
            finishRotation(true);
            TinyGL.resetViewPort();
            drawPlayingSoftKeys();
            return true;
         }
      } else {
         return true;
      }
   }

   private static void mixingState(Graphics var0) {
      Text.resetTextArgs();
      var0.setColor(-4737097);
      var0.fillRect(-screenRect[0], -screenRect[1], menuRect[2], menuRect[3]);
      Text var1 = Text.getSmallBuffer();
      Text.composeText((short)1, (short)134, var1);
      int var2 = var1.length() * 7;
      int var3 = var2 + 50;
      int var4 = displayRect[2] - var3 >> 1;
      var4 &= ~(var4 >> 31);
      var0.fillRegion(imgMixingVials, 16, 0, 20, 20, var4, 0, var3, 20);
      int var6 = var4 + var3;
      var0.setColor(-7039852);
      var0.drawLine(var4, 0, var6, 0);
      var0.drawLine(var4, 0, var4, 20);
      var0.setColor(-11711155);
      var0.drawLine(var4, 20, var6, 20);
      var0.drawLine(var6, 0, var6, 20);
      var0.drawRegion(imgMixingVials, 36, 0, 10, 10, var4 + 3, 4);
      var0.drawRegion(imgMixingVials, 36, 0, 10, 10, var6 - 13, 4);
      var1.dehyphenate();
      var0.drawString(var1, displayRect[2] >> 1, 5, 1);
      if (mixingMsg && msgSeen) {
         updateMixingText();
         mixingMsg = false;
         msgSeen = false;
      }

      if (!mixingMsg) {
         var1.setLength(0);
         Text.composeText((short)1, (short)138, var1);
         var1.dehyphenate();
         var0.drawString(var1, SCR_CX, 25, 1);
         int var5 = displayRect[2] - 92 >> 1;

         int var7;
         int var8;
         for(var7 = 0; var7 < 3; ++var7) {
            var8 = stateVars[6 + var7];
            var0.drawRegion(imgMixingVials, 0, 0, 16, 47, var5, 56);
            if (var8 != 0) {
               if (stateVars[4] == var7) {
                  var0.drawImage(imgMixingHighlight, var5 + -7, 52);
               }

               var0.drawRegion(imgMixingVials, 16 + var7 * 12, 20, 12, 27, var5 + 2, 75);
            }

            var1.setLength(0);
            var1.append(var8);
            var0.drawBoxedString(var1, var5 + 8, 40, 1, -3036160, -16777216);
            var5 += 38;
         }

         var4 = displayRect[2] - 164 >> 1;
         var5 = var4 + 36;
         var0.setColor(stateVars[3]);
         var0.fillRect(var5, 132, 30, 15);
         var5 += 30;
         var0.setColor(stateVars[2]);
         var0.fillRect(var5, 132, 30, 15);
         var5 += 30;
         var0.setColor(stateVars[1]);
         var0.fillRect(var5, 132, 30, 15);
         var0.drawRegion(imgMixingSyringe, 0, 0, 52, 29, var4, 126);
         var0.drawRegion(imgMixingSyringe, 52, 0, 62, 29, var4 + 52 + 50, 126);
         var7 = var4 + 52;
         var8 = var7 + 50;
         short var9 = 132;
         var0.drawLine(var7, var9 + REFLECTION_OFFSET[0], var8, var9 + REFLECTION_OFFSET[0], -13223881);
         var0.drawLine(var7, var9 + REFLECTION_OFFSET[1], var8, var9 + REFLECTION_OFFSET[1], -3814192);
         var0.drawLine(var7, var9 + REFLECTION_OFFSET[2], var8, var9 + REFLECTION_OFFSET[2], -3814192);
         var0.drawLine(var7, var9 + REFLECTION_OFFSET[3], var8, var9 + REFLECTION_OFFSET[3], -13223881);
         var0.drawLine(var7, var9 + REFLECTION_OFFSET[4], var8, var9 + REFLECTION_OFFSET[4], -13223881);
         cocktailName.dehyphenate();
         var0.drawString(cocktailName, displayRect[2] >> 1, 112, 1);
         var1.setLength(0);
         Text.composeText((short)1, (short)171, var1);
         var1.dehyphenate();
         var0.drawString(var1, var4 + 40, 162, 0);
         var4 += 40 + var1.getStringWidth(false) + 10;
         var1.setLength(0);
         var1.append(Player.inventory[21]);
         var1.dehyphenate();
         var0.drawBoxedString(var1, var4, 160, 1, -3036160, -16777216);
      }

      short var10 = 182;
      if (mixingMsg) {
         var1.setLength(0);
         Text.composeText((short)1, (short)100, var1);
         var1.dehyphenate();
         var0.drawString(var1, displayRect[2] >> 1, 170, 1);
         var10 = 70;
      }

      var0.drawString(mixingInstructions, displayRect[2] >> 1, var10, 1);
      var1.dispose();
   }

   private static void handleMixingEvents(int var0) {
      if (mixingMsg) {
         if (var0 == 6) {
            msgSeen = true;
            setSoftKeys((short)5, (short)75, (short)5, (short)150);
         }

      } else {
         if (var0 != 5 && var0 != 15) {
            if (var0 == 6) {
               if (stateVars[0] < 3) {
                  if (stateVars[4] == 3 || Player.inventory[21] == 0) {
                     Game.checkEmptyStation(curStation);
                     setState(3);
                     return;
                  }

                  int var1 = 0;
                  switch(stateVars[4]) {
                  case 0:
                     var1 = -3407872;
                     break;
                  case 1:
                     var1 = -16724992;
                     break;
                  case 2:
                     var1 = -16777012;
                  }

                  pushIngredient(var1);
               } else {
                  mixCocktail();
                  clearSoftKeys();
               }
            } else if (var0 == 7) {
               MenuSystem.setMenu(46);
            } else if (var0 == 3) {
               nextMixingIngredient(-1);
            } else if (var0 == 4) {
               nextMixingIngredient(1);
            }
         } else if (stateVars[0] == 0) {
            Game.checkEmptyStation(curStation);
            setState(3);
         } else {
            popIngredient();
            if (stateVars[4] == 3) {
               nextMixingIngredient(1);
            }
         }

      }
   }

   private static void nextMixingIngredient(int var0) {
      int var1 = stateVars[4];

      do {
         var1 = var1 + var0 & 3;
      } while(var1 != stateVars[4] && (var1 == 3 || stateVars[6 + var1] == 0));

      if (var1 != 3 && stateVars[6 + var1] == 0) {
         var1 = 3;
      }

      stateVars[4] = var1;
   }

   private static void pushIngredient(int var0) {
      if (stateVars[0] + 1 <= 3) {
         if (--stateVars[6 + stateVars[4]] == 0) {
            nextMixingIngredient(1);
         }

         int var10002 = stateVars[0]++;
         stateVars[stateVars[0]] = var0;
         updateMixingText();
      }
   }

   private static void popIngredient() {
      if (stateVars[0] - 1 >= 0) {
         int var10002;
         switch(stateVars[stateVars[0]]) {
         case -16777012:
            var10002 = stateVars[8]++;
            break;
         case -16724992:
            var10002 = stateVars[7]++;
            break;
         case -3407872:
            var10002 = stateVars[6]++;
         }

         stateVars[stateVars[0]] = -4737097;
         var10002 = stateVars[0]--;
         updateMixingText();
      }
   }

   private static void updateMixingText() {
      if (stateVars[0] != 3) {
         if (stateVars[0] == 0) {
            setLeftSoftKey((short)5, (short)75);
         }

         cocktailName.setLength(0);
         if (stateVars[4] != 3 && Player.inventory[21] != 0) {
            Text.composeText((short)1, (short)141, cocktailName);
            cocktailName.dehyphenate();
            mixingInstructions.setLength(0);
            Text.composeText((short)1, (short)136, mixingInstructions);
            mixingInstructions.dehyphenate();
         } else {
            mixingInstructions.setLength(0);
            Text.composeText((short)1, (short)137, mixingInstructions);
            mixingInstructions.dehyphenate();
         }
      } else {
         mixingInstructions.setLength(0);
         Text.composeText((short)1, (short)135, mixingInstructions);
         mixingInstructions.dehyphenate();
         int var0 = 0;

         int var1;
         for(var1 = 0; var1 < 3; ++var1) {
            int var2 = stateVars[3 - var1];
            switch(var2) {
            case -16777012:
               ++var0;
               break;
            case -16724992:
               var0 += 4;
               break;
            case -3407872:
               var0 += 16;
            }
         }

         for(var1 = 0; var1 < cocktailRecipes.length; ++var1) {
            if (var0 == cocktailRecipes[var1]) {
               if ((Player.cocktailDiscoverMask & 1 << var1) != 0) {
                  cocktailName.setLength(0);
                  Text.composeText((short)2, cocktailNames[var1], cocktailName);
                  cocktailName.dehyphenate();
               }

               stateVars[5] = var1;
               mixingInstructions.setLength(0);
               Text.composeText((short)1, (short)135, mixingInstructions);
               mixingInstructions.dehyphenate();
               return;
            }
         }

         App.Error(new Exception("Cannot find a match for the cocktail."), 112);
      }

      if (stateVars[0] != 0) {
         setLeftSoftKey((short)1, (short)140);
      }

   }

   private static void mixCocktail() {
      mixingMsg = true;
      msgSeen = false;
      if (Player.inventory[21] == 0) {
         mixingInstructions.setLength(0);
         Text.composeText((short)1, (short)143, mixingInstructions);
      } else {
         mixingInstructions.setLength(0);
         EntityDef var0 = EntityDef.find(6, 0, 5 + stateVars[5]);
         Text.resetTextArgs();
         Text.addTextArg((short)2, var0.name);
         if (Player.give(0, 5 + stateVars[5], 1)) {
            for(int var1 = 0; var1 < 3; ++var1) {
               int var10002;
               switch(stateVars[3 - var1]) {
               case -16777012:
                  var10002 = Game.mixingStations[curStation + 3]--;
                  break;
               case -16724992:
                  var10002 = Game.mixingStations[curStation + 2]--;
                  break;
               case -3407872:
                  var10002 = Game.mixingStations[curStation + 1]--;
               }
            }

            Player.cocktailDiscoverMask |= 1 << stateVars[5];
            --Player.inventory[21];
            stateVars[0] = 0;
            stateVars[3] = stateVars[2] = stateVars[1] = -4737097;
            Text.composeText((short)1, (short)144, mixingInstructions);
         } else {
            Text.composeText((short)1, (short)85, mixingInstructions);
         }

         mixingInstructions.dehyphenate();
      }
   }

   public static final void recipeToString(int var0, Text var1) {
      byte var2 = cocktailRecipes[var0];
      int var3 = 4;

      for(int var4 = 0; var4 < 3; var3 -= 2) {
         for(int var5 = (var2 & 3 << var3) >> var3; var5 > 0; --var5) {
            if (var3 == 4) {
               var1.append('¼');
            } else if (var3 == 2) {
               var1.append('¾');
            } else {
               var1.append('½');
            }
         }

         ++var4;
      }

   }

   public static final void handleStoryInput(int var0, int var1) {
      if (var1 != 4 && var1 != 3) {
         if (var1 == 1) {
            if (stateVars[0] != 2 && storyPage < storyTotalPages - 1) {
               stateVars[0] = 2;
            }
         } else if (var1 == 2) {
            if (stateVars[0] == 2) {
               stateVars[0] = 1;
            }
         } else if (var1 == 6) {
            switch(stateVars[0]) {
            case 0:
               changeStoryPage(-1);
               break;
            case 1:
               changeStoryPage(1);
               break;
            case 2:
               storyPage = storyTotalPages;
            }
         } else if (var1 == 7) {
            changeStoryPage(1);
            stateVars[0] = 1;
         } else if (var1 == 15 || var1 == 5) {
            changeStoryPage(-1);
            stateVars[0] = 0;
         }
      } else if (stateVars[0] != 2) {
         int[] var10000 = stateVars;
         var10000[0] ^= 1;
      }

   }

   private static final void drawKickingBars(Graphics var0) {
      if (kickingPhase != 0) {
         int var1 = SCR_CX - 63;
         int var2 = viewRect[1] + viewRect[3] - (hKickingBar.getHeight() + 1);
         int var3 = viewRect[0] + 1;
         int var4 = SCR_CY - 52;
         var0.drawRegion(hKickingBar, 0, 0, 126, 13, var1, var2, 0, 0);
         var0.drawRegion(vKickingBar, 0, 0, 13, 105, var3, var4, 0, 0);
         int var5 = App.getUpTimeMs() - stateVars[0];
         int var6;
         if (kickingPhase == 1) {
            var1 += 12;
            var6 = (var5 % 2000 << 16) / 256000;
            if (var6 >= 256) {
               var6 -= 256;
               var1 = var1 + 102 - (var6 * 102 >> 8);
               if (var6 <= 85) {
                  chickenDestRight = 1;
               } else if (var6 <= 170) {
                  chickenDestRight = 0;
               } else {
                  chickenDestRight = -1;
               }
            } else {
               var1 += var6 * 102 >> 8;
               if (var6 <= 85) {
                  chickenDestRight = -1;
               } else if (var6 <= 170) {
                  chickenDestRight = 0;
               } else {
                  chickenDestRight = 1;
               }
            }

            var0.drawRegion(hKickingBar, 126, 0, 4, 13, var1, var2, 0, 0);
            kickHPos = var1;
         } else if (kickingPhase == 2) {
            var4 += 12;
            var6 = (var5 % 1600 << 16) / 204800;
            if (var6 >= 256) {
               var6 -= 256;
               var4 = var4 + 84 - (var6 * 84 >> 8);
               if (var6 <= 64) {
                  chickenDestFwd = 2;
               } else if (var6 <= 128) {
                  chickenDestFwd = 3;
               } else if (var6 <= 192) {
                  chickenDestFwd = 4;
               } else {
                  chickenDestFwd = 5;
               }
            } else {
               var4 += var6 * 84 >> 8;
               if (var6 <= 64) {
                  chickenDestFwd = 5;
               } else if (var6 <= 128) {
                  chickenDestFwd = 4;
               } else if (var6 <= 192) {
                  chickenDestFwd = 3;
               } else {
                  chickenDestFwd = 2;
               }
            }

            var0.drawRegion(vKickingBar, 0, 105, 13, 4, var3, var4, 0, 0);
            kickVPos = var4;
            var0.drawRegion(hKickingBar, 126, 0, 4, 13, kickHPos, var2, 0, 0);
         } else if (kickingPhase == 3) {
            var0.drawRegion(vKickingBar, 0, 105, 13, 4, var3, kickVPos, 0, 0);
            var0.drawRegion(hKickingBar, 126, 0, 4, 13, kickHPos, var2, 0, 0);
         }

      }
   }

   public static final void startKicking(boolean var0) {
      isChickenKicking = true;
      curScore = 0;
      kickingPhase = 0;
      Player.selectWeapon(3);
      kickingFromMenu = var0;
   }

   public static final void endKickingRound() {
      if (isChickenKicking) {
         lastScore = curScore;
         curScore = 0;
         highScoreIndex = -1;
         stateVars[1] = 0;
         stateVars[2] = -1;

         int var0;
         for(var0 = 0; var0 < 5; ++var0) {
            if (highScores[var0] <= lastScore) {
               highScoreIndex = var0;
               break;
            }
         }

         if (highScoreIndex != -1) {
            for(var0 = 4; var0 > highScoreIndex; --var0) {
               highScores[var0] = highScores[var0 - 1];
               int var1 = var0 * 3;
               int var2 = (var0 - 1) * 3;
               highScoreInitials[var1 + 0] = highScoreInitials[var2 + 0];
               highScoreInitials[var1 + 1] = highScoreInitials[var2 + 1];
               highScoreInitials[var1 + 2] = highScoreInitials[var2 + 2];
            }

            highScores[highScoreIndex] = (short)lastScore;
            highScoreInitials[highScoreIndex * 3 + 0] = 'A';
            highScoreInitials[highScoreIndex * 3 + 1] = 'A';
            highScoreInitials[highScoreIndex * 3 + 2] = 'A';
         }

         Hud.addMessage((short)1, (short)170, 2);
         stateVars[0] = App.gameTime + 2000;
         kickingPhase = 5;
      }
   }

   public static final void endKickingGame() {
      if (isChickenKicking) {
         App.beginImageUnload();
         hKickingBar = null;
         vKickingBar = null;
         App.endImageUnload();
         kickingPhase = 0;
         isChickenKicking = false;
      }
   }

   public static final int getKickPoint(int var0, int var1) {
      --var0;
      var0 &= ~(var0 >> 31);
      var1 -= 18;
      var1 &= ~(var1 >> 31);
      return CKPOINTS[var0 + var1 * 5];
   }

   public static final void drawHighScore(Graphics var0) {
      int var1 = App.getUpTimeMs();
      Text var2 = Text.getSmallBuffer();
      var0.setColor(-16777216);
      var0.fillRect(screenRect[0], screenRect[1], displayRect[2], displayRect[3]);
      var2.setLength(0);
      Text.resetTextArgs();
      Text.addTextArg((short)1, (short)169);
      Text.composeText((short)1, (short)158, var2);
      var2.dehyphenate();
      var0.drawString(var2, SCR_CX, 10, 1);
      var0.setColor(-10066330);
      var0.drawLine(20, 45, screenRect[2] - 20, 45);
      if (stateVars[4] <= var1 && stateVars[1] != 2 && stateVars[2] != -1) {
         stateVars[4] = App.getUpTimeMs() + 2000;
         int var10002 = stateVars[1]++;
         stateVars[2] = -1;
      }

      int var3 = stateVars[1];
      byte var4 = 70;
      byte var5 = 20;
      var2.setLength(0);
      Text.composeText((short)1, (short)161, var2);
      var2.dehyphenate();
      var0.drawString(var2, var5, var4, 0);
      var2.setLength(0);
      Text.composeText((short)1, (short)162, var2);
      var2.dehyphenate();
      var0.drawString(var2, SCR_CX, var4, 1);
      int var6 = screenRect[2] - 20;
      var2.setLength(0);
      Text.composeText((short)1, (short)163, var2);
      var2.dehyphenate();
      var0.drawString(var2, var6, var4, 8);
      int var9 = var4 + 16;
      if (highScoreIndex >= 0 && (var1 / 512 & 1) != 0) {
         var2.setLength(0);
         var2.append('_');
         var0.drawString(var2, SCR_CX - 10 + var3 * 7, var9 + highScoreIndex * 12 + 3, 0);
      }

      short var7 = 164;

      for(int var8 = 0; var8 < 5; ++var7) {
         var2.setLength(0);
         Text.composeText((short)1, var7, var2);
         var0.drawString(var2, var5, var9, 0);
         var2.setLength(0);
         var2.append(highScoreInitials[var8 * 3 + 0]);
         var2.append(highScoreInitials[var8 * 3 + 1]);
         var2.append(highScoreInitials[var8 * 3 + 2]);
         var0.drawString(var2, SCR_CX, var9, 1);
         var2.setLength(0);
         var2.append(highScores[var8]);
         var0.drawString(var2, var6, var9, 8);
         var9 += 12;
         ++var8;
      }

      var9 += 36;
      var2.setLength(0);
      if (highScoreIndex == -1) {
         Text.resetTextArgs();
         Text.addTextArg(lastScore);
         Text.composeText((short)1, (short)159, var2);
         var2.append('\n');
         Text.composeText((short)1, (short)100, var2);
         var2.dehyphenate();
         var0.drawString(var2, SCR_CX, var9, 1);
      } else if (highScoreIndex == -2) {
         Text.composeText((short)1, (short)100, var2);
         var0.drawString(var2, SCR_CX, var9, 1);
      } else {
         Text.composeText((short)1, (short)160, var2);
         var2.dehyphenate();
         var0.drawString(var2, SCR_CX, var9, 1);
      }

      staleView = true;
      var2.dispose();
   }

   public static final void handleHighScoreInput(int var0, int var1) {
      if (stateVars[0] > App.gameTime) {
         clearEvents();
      } else if (highScoreIndex < 0) {
         if (var0 == 6 || var0 == 15) {
            kickingPhase = 0;
            if (!kickingFromMenu) {
               Player.addXP(10);
            }

            Hud.repaintFlags |= 4;
            repaintFlags |= 2;
            Game.saveConfig();
         }

      } else {
         if (var1 >= 48 && var1 <= 57) {
            stateVars[4] = App.getUpTimeMs() + 2000;
            char var2 = getNextChar(var1 - 48, ++stateVars[2]);
            highScoreInitials[highScoreIndex * 3 + stateVars[1]] = var2;
         } else {
            int var10002;
            if (var1 != 42 && var0 != 3 && var0 != 15) {
               if (var1 == 35 || var0 == 6 || var0 == 4) {
                  var10002 = stateVars[1]++;
                  if (stateVars[1] >= 3) {
                     highScoreIndex = -2;
                  } else {
                     stateVars[2] = -1;
                  }
               }
            } else {
               var10002 = stateVars[1]--;
               if (stateVars[1] < 0) {
                  stateVars[1] = 0;
               }

               stateVars[2] = -1;
            }
         }

      }
   }

   private static final void drawKickingGrid(Graphics var0) {
      int var1 = SCR_CX - 50;
      int var2 = SCR_CY - 40;
      Text var3 = Text.getLargeBuffer();
      var3.setLength(0);
      Text.resetTextArgs();
      Text.addTextArg(lastScore);
      Text.composeText((short)1, (short)155, var3);
      var0.drawBoxedString(var3, SCR_CX, var2 - 18, 2, -16777216, -1);
      int var4 = var1;
      int var5 = var2;
      boolean var6 = false;
      boolean var7 = false;

      for(int var8 = 0; var8 < 4; ++var8) {
         for(int var9 = 0; var9 < 5; var4 += 20) {
            int var12 = var8 * 5 + var9;
            int var10 = GRID_COLORS[var12];
            if (var12 == gridIdx && (App.gameTime & 256) != 0) {
               var10 = -15244544;
            }

            var0.fillRect(var4, var5, 20, 20, var10);
            var0.drawRect(var4, var5, 20, 20, -1);
            var3.setLength(0);
            int var11 = CKPOINTS[20 - var12 - 1];
            var3.append(var11);
            var0.drawString(var3, var4 + 10 - 1, var5 + 10 + 1, 3);
            ++var9;
         }

         var4 = var1;
         var5 += 20;
      }

      if ((App.gameTime & 256) != 0) {
         var4 = var1 + gridIdx % 5 * 20;
         var5 = var2 + gridIdx / 5 * 20;
         var0.drawRect(var4, var5, 20, 20, -16777216);
      }

      var3.setLength(0);
      Text.resetTextArgs();
      Text.addTextArg(Game.scriptStateVars[13] + 1);
      Text.composeText((short)1, (short)156, var3);
      var0.drawBoxedString(var3, SCR_CX, var2 + 80 + 7, 2, -16777216, -1);
      var3.dispose();
   }

   private static final char getNextChar(int var0, int var1) {
      int var2 = stateVars[3];
      stateVars[3] = var0;
      if (var0 != var2) {
         if (stateVars[1] < 2 && stateVars[2] != 0) {
            int var10002 = stateVars[1]++;
         }

         var1 = stateVars[2] = 0;
      }

      stateVars[2] = var1 %= numCharTable[var0].length();
      return numCharTable[var0].charAt(var1);
   }

   private static final void drawTravelMap(Graphics var0) {
      int var1 = displayRect[3] - imgTravelBG.getHeight() >> 1;
      var0.drawImage(imgTravelBG, SCR_CX, var1, 17);
      boolean var2 = false;
      int var3 = imgTravelBG.getWidth() / 2;
      int var4 = imgTravelBG.getHeight() / 2;
      int var5 = Math.max(0, displayRect[2] / 2 - var3);
      int var6 = Math.max(0, displayRect[3] / 2 - var4);
      if (imgTravelBG.getWidth() + 2 < displayRect[2] || imgTravelBG.getHeight() + 2 < displayRect[3]) {
         var0.fillRegion(imgFabricBg, 0, 0, displayRect[2], var6);
         var0.fillRegion(imgFabricBg, 0, var6, var5, displayRect[3] - var6);
         var0.fillRegion(imgFabricBg, var5, var6 + var4 * 2, var3 * 2, var6);
         var0.fillRegion(imgFabricBg, var5 + var3 * 2, var6, var5, displayRect[3] - var6);
         var0.drawRect(var5 - 1, var6 - 1, var3 * 2 + 1, var4 * 2 + 1, -16777216);
         var2 = true;
      }

      Text var7 = Text.getSmallBuffer();
      var7.setLength(0);
      Text.composeText((short)5, Game.levelNames[loadMapID - 1], var7);
      var7.dehyphenate();
      var0.drawString(var7, SCR_CX, 5, 17);
      if (stateVars[4] == 1) {
         var7.setLength(0);
         Text.composeText((short)1, (short)100, var7);
         var0.drawString(var7, SCR_CX, displayRect[3] - 12 - 5, 17);
      }

      if (var2) {
         var0.clipRect(var5, var6, imgTravelBG.getWidth(), imgTravelBG.getHeight());
      }

      if (drawTMArrow(var0, var5, var6) && drawMagGlass(var0, var5, var6)) {
         drawSelector(var0, var5, var6);
      }

      var7.dispose();
      staleView = true;
   }

   private static final boolean drawTMArrow(Graphics var0, int var1, int var2) {
      boolean var3 = true;
      boolean var4 = false;
      byte var14;
      if (lastMapID >= 0 && lastMapID <= 3 && loadMapID == 4) {
         var14 = 0;
      } else if (lastMapID >= 4 && lastMapID <= 6 && loadMapID == 3) {
         var14 = 4;
      } else if (lastMapID >= 4 && lastMapID <= 6 && loadMapID == 7) {
         var14 = 8;
      } else {
         if (lastMapID <= 6 || loadMapID != 6) {
            return var3;
         }

         var14 = 12;
      }

      int var5 = App.getUpTimeMs() - stateVars[0];
      if (var5 > 700 && stateVars[1] == 0) {
         stateVars[1] = 1;
         stateVars[0] = App.getUpTimeMs();
      }

      if (stateVars[1] != 1 && stateVars[4] != 1) {
         var3 = false;
         var5 = (var5 << 16) / 700;
         byte var8 = 0;
         byte var9 = 0;
         int var10 = var5 * 46 >> 16;
         int var11 = 46;
         int var12 = 0;
         byte var13 = 0;
         if (ARROW_DATA[var14 + 3] == 0) {
            var11 = var10;
            var10 = 46;
            var12 = 46 - var11;
         } else if (ARROW_DATA[var14 + 3] == 1) {
            var12 = 46 - var10;
         }

         if (var10 > 0) {
            var0.drawRegion(imgTMArrow, var8, var9, var10, var11, ARROW_DATA[var14 + 0] + var12 + var1, ARROW_DATA[var14 + 1] + var13 + var2, 0, ARROW_DATA[var14 + 2]);
         }
      } else {
         var0.drawRegion(imgTMArrow, 0, 0, 46, 46, ARROW_DATA[var14 + 0] + var1, ARROW_DATA[var14 + 1] + var2, 0, ARROW_DATA[var14 + 2]);
      }

      return var3;
   }

   private static final boolean drawSelector(Graphics var0, int var1, int var2) {
      int var3 = App.getUpTimeMs() - stateVars[0];
      boolean var4 = true;
      int var7 = Math.max(1, Math.min(loadMapID, 9));
      int var8 = Math.max(1, Math.min(lastMapID, 9));
      int var9 = (var7 - 1) / 3;
      int var10 = var9 * 5;
      int var11 = (var7 - 1) % 3;
      int var12 = MAG_DATA[var10 + 3] + SELECTORPOS[(var7 - 1) * 2 + 0];
      int var13 = MAG_DATA[var10 + 4] + SELECTORPOS[(var7 - 1) * 2 + 1];
      if (stateVars[4] != 1 && var3 <= 500 && (var11 != 0 || lastMapID >= loadMapID) && var9 == (var8 - 1) / 3) {
         var3 = (var3 << 16) / 500;
         int var14 = MAG_DATA[var10 + 3] + SELECTORPOS[(var8 - 1) * 2 + 0];
         int var15 = MAG_DATA[var10 + 4] + SELECTORPOS[(var8 - 1) * 2 + 1];
         var12 = var14 + (var3 * (var12 - var14) >> 16);
         var13 = var15 + (var3 * (var13 - var15) >> 16);
         var0.drawRegion(imgTMHighlight, 0, 0, 92, 19, var12 + var1, var13);
         var4 = false;
      } else {
         stateVars[4] = 1;
         if ((var3 & 512) != 0) {
            var0.drawRegion(imgTMHighlight, 0, 0, 92, 19, var12 + var1, var13 + var2);
         }
      }

      return var4;
   }

   private static final boolean drawMagGlass(Graphics var0, int var1, int var2) {
      int var3 = App.getUpTimeMs() - stateVars[0];
      boolean var4 = true;
      int var5 = 0;
      int var6 = 0;
      int var7 = Math.max(1, Math.min(loadMapID, 9));
      int var8 = (var7 - 1) / 3;
      int var9 = (lastMapID - 1) / 3;
      if (var8 == var9 && var7 != 1) {
         stateVars[2] = 1;
         stateVars[3] = 1;
      } else {
         switch(var8) {
         case 0:
            var5 = displayRect[2] + var1;
            var6 = SCR_CY + 30 + var2;
            break;
         case 1:
            var5 = displayRect[0] - 122 + var1;
            var6 = SCR_CY - 20 + var2;
            break;
         case 2:
         default:
            var5 = displayRect[2] + var1;
            var6 = SCR_CY - 40 + var2;
         }
      }

      var8 *= 5;
      if (var3 > 1200 && stateVars[2] == 0) {
         stateVars[2] = 1;
         stateVars[0] = App.getUpTimeMs();
         var3 = 0;
      }

      if (stateVars[2] != 1 && stateVars[4] != 1) {
         var3 = (var3 << 16) / 1200;
         int var10 = (var5 << 16) + var3 * (MAG_DATA[var8 + 0] + var1 - var5) >> 16;
         int var11 = (var6 << 16) + var3 * (MAG_DATA[var8 + 1] + var2 - var6) >> 16;
         var0.drawRegion(imgMagGlass, 0, 0, 122, 122, var10, var11, 0, MAG_DATA[var8 + 2]);
         var4 = false;
      } else {
         var0.drawRegion(imgMagGlass, 0, 0, 122, 122, MAG_DATA[var8 + 0] + var1, MAG_DATA[var8 + 1] + var2, 0, MAG_DATA[var8 + 2]);
         var0.drawRegion(imgMagBG, 0, 0, imgMagBG.getWidth(), imgMagBG.getHeight(), MAG_DATA[var8 + 3] + var1, MAG_DATA[var8 + 4] + var2, 0, 0);
         if (var3 > 300 && stateVars[3] == 0) {
            stateVars[3] = 1;
            stateVars[0] = App.getUpTimeMs() | 512;
         }

         if (stateVars[4] == 0 && stateVars[3] == 0) {
            var3 /= 100;
            var4 = false;
            var0.drawRegion(imgFlak, 64 * (var3 & 3), 0, 64, 64, MAG_DATA[var8 + 3] + var1, MAG_DATA[var8 + 4] + var2, 0, 0);
         }
      }

      return var4;
   }

   private static final void initTravelMap() {
      int var0 = Math.max(1, Math.min(loadMapID, 9));
      int var1 = (var0 - 1) / 3;
      App.beginImageLoading();
      imgTMArrow = App.loadImageFromIndex(0);
      imgFlak = App.loadImageFromIndex(5);
      imgTMHighlight = App.loadImageFromIndex(7);
      imgMagGlass = App.loadImageFromIndex(18);
      imgMagBG = App.loadImageFromIndex(32 + var1);
      imgTravelBG = App.loadImageFromIndex(35);
      App.endImageLoading();
      stateVars[0] = App.getUpTimeMs();
   }

   private static final void disposeTravelMap() {
      App.beginImageUnload();
      imgTMArrow = null;
      imgFlak = null;
      imgTMHighlight = null;
      imgMagGlass = null;
      imgMagBG = null;
      imgTravelBG = null;
      App.endImageUnload();
   }
}
