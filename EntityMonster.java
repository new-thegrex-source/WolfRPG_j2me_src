/* Decompiler 26ms, total 192ms, lines 97 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

final class EntityMonster {
   public static int touchMe = 1;
   public static final int GOAL_NONE = 0;
   public static final int GOAL_MOVE = 1;
   public static final int GOAL_MOVETOENTITY = 2;
   public static final int GOAL_FIGHT = 3;
   public static final int GOAL_FLEE = 4;
   public static final int GOAL_EVADE = 5;
   public static final int GOAL_STUN = 6;
   public static final int GFL_LERPING = 1;
   public static final int GFL_SPECIAL = 2;
   public static final int GFL_MOVE2ATTACK = 4;
   public static final int GFL_ATTACK2EVADE = 8;
   public static final int GFL_MOVEAGAIN = 16;
   public static final int GFL_STEALTHMOVE = 32;
   public static final int GFL_RAMBOMOVE = 64;
   public static final int MAX_GOAL_TURNS = 16;
   public CombatEntity ce = new CombatEntity();
   public Entity nextOnList;
   public Entity prevOnList;
   public Entity nextAttacker;
   public Entity target;
   public int frameTime;
   public short flags;
   public short monsterEffects;
   public byte goalType;
   public byte goalFlags;
   public byte goalTurns;
   public int goalX;
   public int goalY;
   public int goalParam;
   public static final int DEFAULT_PAIN_TIME = 250;
   public static final int MFX_COUNT = 5;
   public static final int MFX_NONE = 0;
   public static final int MFX_POISON = 1;
   public static final int MFX_FREEZE = 2;
   public static final int MFX_RAISE_TIMER = 4;
   public static final int MFX_FIRE = 8;
   public static final int MFX_SHIELD = 16;
   public static final int MFX_MAX = 16;
   public static final int MFX_MASK_ALL = 31;
   public static final int MFX_POISON_SHIFT = 5;
   public static final int MFX_FREEZE_SHIFT = 7;
   public static final int MFX_RAISE_SHIFT = 9;
   public static final int MFX_FIRE_SHIFT = 11;
   public static final int MFX_SHIELD_SHIFT = 13;
   public static final int MFX_TURN_MASK = 3;
   public static final int MFX_POISON_REMOVE = -98;
   public static final int MFX_FREEZE_REMOVE = -387;
   public static final int MFX_RAISE_REMOVE = -1541;
   public static final int MFX_FIRE_REMOVE = -6153;
   public static final int MFX_SHIELD_REMOVE = -24593;
   public static final int MFX_REMOVE_TURNS = -32737;
   public static final int MFX_ALL_ONE_TURNS = 10400;

   void clearEffects() {
      this.monsterEffects = 0;
   }

   public void reset() {
      this.nextOnList = this.prevOnList = null;
      this.nextAttacker = this.target = null;
      this.frameTime = 0;
      this.flags = 0;
      this.monsterEffects = 0;
      this.clearEffects();
      this.resetGoal();
   }

   public void saveGoalState(DataOutputStream var1) throws IOException {
      int var2 = this.goalType | this.goalFlags << 4 | this.goalTurns << 8 | this.goalX << 12 | this.goalY << 17 | this.goalParam << 22;
      var1.writeInt(var2);
   }

   public void loadGoalState(DataInputStream var1) throws IOException {
      int var2 = var1.readInt();
      this.goalType = (byte)(var2 & 15);
      this.goalFlags = (byte)(var2 >> 4 & 15);
      this.goalTurns = (byte)(var2 >> 8 & 15);
      this.goalX = var2 >> 12 & 31;
      this.goalY = var2 >> 17 & 31;
      this.goalParam = var2 >> 22 & 1023;
   }

   public void resetGoal() {
      this.goalType = 0;
      this.goalFlags = 0;
      this.goalTurns = 0;
      this.goalX = this.goalY = 0;
      this.goalParam = 0;
   }
}
