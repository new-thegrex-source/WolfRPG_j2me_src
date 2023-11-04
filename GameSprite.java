/* Decompiler 2ms, total 389ms, lines 27 */
final class GameSprite {
   public static int touchMe = 1;
   public static final int FLAG_INUSE = 1;
   public static final int FLAG_PROPOGATE = 2;
   public static final int FLAG_NORELINK = 4;
   public static final int FLAG_NOREFRESH = 8;
   public static final int FLAG_ANIM = 64;
   public static final int FLAG_NOEXPIRE = 512;
   public static final int FLAG_SCALE = 1024;
   public static final int FLAG_UNLINK = 2048;
   public static final int FLAG_FACEPLAYER = 4096;
   public static final int FLAG_LERP_ENT = 8192;
   public static final int FLAG_LERP_PARABOLA = 16384;
   public static final int FLAG_LERP_TRUNC = 32768;
   public int time;
   public int flags;
   public short[] pos = new short[6];
   public int sprite;
   public Entity data;
   byte startScale;
   byte destScale;
   public int duration;
   public int[] vel = new int[3];
   public int scaleStep;
   public byte numAnimFrames;
}
