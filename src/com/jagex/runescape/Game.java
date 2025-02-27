package com.jagex.runescape;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.CRC32;

import com.jagex.runescape.cache.Archive;
import com.jagex.runescape.cache.Index;
import com.jagex.runescape.cache.cfg.ChatCensor;
import com.jagex.runescape.cache.cfg.Varbit;
import com.jagex.runescape.cache.cfg.Varp;
import com.jagex.runescape.cache.def.ActorDefinition;
import com.jagex.runescape.cache.def.FloorDefinition;
import com.jagex.runescape.cache.def.GameObjectDefinition;
import com.jagex.runescape.cache.def.ItemDefinition;
import com.jagex.runescape.cache.media.*;
import com.jagex.runescape.collection.Node;
import com.jagex.runescape.media.Animation;
import com.jagex.runescape.media.ProducingGraphicsBuffer;
import com.jagex.runescape.media.Rasterizer;
import com.jagex.runescape.media.Rasterizer3D;
import com.jagex.runescape.media.renderable.*;
import com.jagex.runescape.media.renderable.actor.Actor;
import com.jagex.runescape.media.renderable.actor.Npc;
import com.jagex.runescape.media.renderable.actor.Player;
import com.jagex.runescape.net.Buffer;
import com.jagex.runescape.net.BufferedConnection;
import com.jagex.runescape.net.ISAACCipher;
import com.jagex.runescape.net.requester.OnDemandNode;
import com.jagex.runescape.net.requester.OnDemandRequester;
import com.jagex.runescape.scene.Region;
import com.jagex.runescape.scene.Scene;
import com.jagex.runescape.scene.InteractiveObject;
import com.jagex.runescape.scene.SpawnObjectNode;
import com.jagex.runescape.scene.tile.FloorDecoration;
import com.jagex.runescape.scene.tile.Wall;
import com.jagex.runescape.scene.tile.WallDecoration;
import com.jagex.runescape.scene.util.CollisionMap;
import com.jagex.runescape.sound.SoundPlayer;
import com.jagex.runescape.sound.SoundTrack;
import com.jagex.runescape.util.*;
import tech.henning.client.Actions;
import tech.henning.client.Configuration;

import static tech.henning.client.Configuration.*;

@SuppressWarnings("serial")
public class Game extends GameShell {


    private final int[] soundVolume = new int[50];
    private int archiveHashes[] = new int[9];
    private byte terrainData[][];
    private String reportedName = "";
    private int anIntArray842[] = {0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff};
    private int skillExperience[] = new int[SkillConstants.SKILL_COUNT];
    private int anInt844;
    private int anInt845;
    private int anInt846;
    private int anInt847;
    private int anInt848;
    private String friendUsernames[] = new String[200];
    private int anInt850;
    public int chatboxScroll;
    public static int cameraZoom = 1200;
    private int cameraAmplitude[] = new int[5];
    private int anInt853;
    private int anInt854 = 2;
    private int ignoresCount;
    private int mapCoordinates[];
    private int terrainDataIds[];
    private int objectDataIds[];
    private int friendsCount;
    private int friendListStatus;
    private String aString861 = "";
    private int anInt862;
    private String aStringArray863[] = new String[100];
    private int anIntArray864[] = new int[100];
    private int anInt865;
    private boolean messagePromptRaised = false;
    private int playerRights;
    private static boolean fps;
    private int packetSize;
    public int opcode;
    private int timeoutCounter;
    private int anInt872;
    private int anInt873;
    private int anInt874;
    private int anInt875;
    private int anInt876;
    private int anInt877;
    private int anInt878;
    private int constructedMapPalette[][][] = new int[4][13][13];
    private IndexedImage aClass50_Sub1_Sub1_Sub3_880;
    private IndexedImage aClass50_Sub1_Sub1_Sub3_881;
    private IndexedImage aClass50_Sub1_Sub1_Sub3_882;
    private IndexedImage aClass50_Sub1_Sub1_Sub3_883;
    private IndexedImage aClass50_Sub1_Sub1_Sub3_884;
    private int anIntArrayArray885[][] = new int[104][104];
    private int anIntArrayArray886[][] = new int[104][104];
    private int privateChatMode;
    private Archive titleArchive;
    private int chunkX;
    private int chunkY;
    private int intGroundArray[][][];
    private int anInt893;
    private static int anInt895;
    private ImageRGB cursorCross[] = new ImageRGB[8];
    private ISAACCipher incomingRandom;
    private boolean useJaggrab = Configuration.JAGGRAB_ENABLED;
    private long aLong902;
    private int lastOpcode;
    private int secondLastOpcode;
    private int thirdLastOpcode;
    private ProducingGraphicsBuffer aClass18_906;
    private ProducingGraphicsBuffer aClass18_907;
    private ProducingGraphicsBuffer aClass18_908;
    private ProducingGraphicsBuffer aClass18_909;
    private ProducingGraphicsBuffer aClass18_910;
    private ProducingGraphicsBuffer aClass18_911;
    private ProducingGraphicsBuffer aClass18_912;
    private ProducingGraphicsBuffer aClass18_913;
    private ProducingGraphicsBuffer aClass18_914;
    private int anInt915;
    private int anInt916;
    private int anInt917 = 2;
    private int anIntArray920[] = new int[151];
    private int anInt921 = 8;
    private static int world = 1;
    public static int portOffset;
    private static boolean memberServer = true;
    public static boolean lowMemory;
    private boolean customCameraActive[] = new boolean[5];
    private int anInt928 = -188;
    private Buffer tempBuffer = Buffer.allocate(1);
    private long serverSeed;
    private int anInt931 = 0x23201b;
    private int anInt932 = -1;
    private int anInt933 = -1;
    private int anInt935 = -1;
    private String chatboxInputMessage = "";
    private int anInt939;
    private int anInt940 = 50;
    private int anIntArray941[] = new int[anInt940];
    private int anIntArray942[] = new int[anInt940];
    private int anIntArray943[] = new int[anInt940];
    private int anIntArray944[] = new int[anInt940];
    private int anIntArray945[] = new int[anInt940];
    private int anIntArray946[] = new int[anInt940];
    private int anIntArray947[] = new int[anInt940];
    private String aStringArray948[] = new String[anInt940];
    private String inputInputMessage = "";
    private boolean drawTabIcons = false;
    private int tickDelta;
    private static int SKILL_EXPERIENCE[];
    private boolean aBoolean953 = false;
    private ImageRGB aClass50_Sub1_Sub1_Sub1Array954[] = new ImageRGB[32];
    private int anInt955;
    private byte aByte956 = 1;
    private String statusLineOne = "";
    private String statusLineTwo = "";
    private boolean aBoolean959 = true;
    private int anInt960 = -1;
    private int thisPlayerServerId = -1;
    private static boolean accountFlagged;
    private Buffer outBuffer = Buffer.allocate(1);
    private IndexedImage anIndexedImage1052;
    private IndexedImage anIndexedImage1053;
    private IndexedImage anIndexedImage1054;
    private int anInt968 = 2048;
    private int thisPlayerId = 2047;
    private Player players[] = new Player[anInt968];
    private int localPlayerCount;
    private int playerList[] = new int[anInt968];
    private int updatedPlayerCount;
    private int updatedPlayers[] = new int[anInt968];
    private Buffer cachedAppearances[] = new Buffer[anInt968];
    private IndexedImage tabIcon[] = new IndexedImage[13];
    private int anInt977;
    private static int anInt978;
    private int firstMenuOperand[] = new int[500];
    private int secondMenuOperand[] = new int[500];
    private int menuActionTypes[] = new int[500];
    private int selectedMenuActions[] = new int[500];
    private IndexedImage aClass50_Sub1_Sub1_Sub3_983;
    private IndexedImage aClass50_Sub1_Sub1_Sub3_984;
    private IndexedImage aClass50_Sub1_Sub1_Sub3_985;
    private IndexedImage aClass50_Sub1_Sub1_Sub3_986;
    private IndexedImage aClass50_Sub1_Sub1_Sub3_987;
    public int backDialogueId = -1;
    private int placementX;
    private int placementY;
    private int cameraFrequency[] = new int[5];
    private int anInt992;
    private int anInt993;
    private int anInt994;
    private int anInt995;
    private int anInt996;
    private int anInt997;
    private int anInt998;
    private int chatboxLineOffsets[];
    private int sidebarOffsets[];
    private int viewportOffsets[];
    private int anIntArray1003[];
    private int anInt1004;
    private int anIntArray1005[] = new int[2000];
    private int publicChatMode;
    public static final int playerColours[][] = {
            {6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 2983, 54193},
            {8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003, 25239},
            {25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003},
            {4626, 11146, 6439, 12, 4758, 10270}, {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574}};
    private int anInt1009;
    private int anInt1010 = 2;
    private int anInt1011;
    private int anInt1012;
    private boolean aBoolean1014 = false;
    public int anInt1015;
    private boolean aBoolean1016 = false;
    private ImageRGB anImageRGB1226;
    private ImageRGB anImageRGB1227;
    private int anIntArray1019[] = new int[151];
    private int lastClickX;
    private int lastClickY;
    private int crossIndex;
    private int crossType;
    private BufferedConnection gameConnection;
    private String chatMessage = "";
    private String aString1027;
    private int skillLevel[] = new int[SkillConstants.SKILL_COUNT];
    private int userWeight;
    private ImageRGB worldMapHintIcons[] = new ImageRGB[100];
    private final int objectTypes[] = {0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3};
    private int anInt1034;
    private int currentSound;
    private ImageRGB mapFlagMarker;
    private ImageRGB aClass50_Sub1_Sub1_Sub1_1037;
    private boolean aBoolean1038 = true;
    public int widgetSettings[] = new int[2000];
    private int nextTopLeftTileX;
    private int nextTopRightTileY;
    private int topLeftTileX;
    private int topLeftTileY;
    private int anInt1044;
    private int anInt1045;
    private boolean aBoolean1046 = false;
    private int anInt1047;
    private int anInt1048;
    private int minimapState;
    private static int anInt1052;
    private int anInt1053 = -1;
    private int skillMaxLevel[] = new int[SkillConstants.SKILL_COUNT];
    private int anInt1055 = 2;
    private int anInt1056 = 3;
    private int systemUpdateTime;
    private String clickToContinueString;
    private TypeFace fontSmall;
    private TypeFace fontNormal;
    private TypeFace fontBold;
    private TypeFace fontFancy;
    private int mouseInvInterfaceIndex;
    private int lastActiveInvInterface;
    private boolean menuOpen = false;
    private byte aByte1066 = 1;
    private boolean aBoolean1067 = false;
    private int playerMembers;
    private String aStringArray1069[] = new String[5];
    private boolean aBooleanArray1070[] = new boolean[5];
    private int loadingStage;
    private int anInt1072 = 20411;
    private long ignores[] = new long[100];
    private int anInt1075;
    private int minimapHintCount;
    private int minimapHintX[] = new int[1000];
    private int minimapHintY[] = new int[1000];
    private ImageRGB aClass50_Sub1_Sub1_Sub1Array1079[] = new ImageRGB[32];
    private int anInt1080 = 0x4d4233;
    public int tabInterfaceIDs[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    private static int anInt1082;
    private int anInt1083;
    private int anIntArray1084[];
    private int anIntArray1085[];
    private ImageRGB aClass50_Sub1_Sub1_Sub1_1086;
    private CRC32 archiveCrc = new CRC32();
    private int openInvOverLayId = -1;
    public int sound[] = new int[50];
    public int plane;
    private String username;
    private String password;
    private int anInt1094;
    private IndexedImage scrollbarUp;
    private IndexedImage scrollbarDown;
    private boolean aBoolean1097 = false;
    private boolean reportMutePlayer = false;
    private int characterEditColors[] = new int[5];
    private static int anInt1100;
    private int flameCycle;
    private ImageRGB aClass50_Sub1_Sub1_Sub1_1102;
    private ImageRGB aClass50_Sub1_Sub1_Sub1_1103;
    private String chatboxInput = "";
    private int cameraJitter[] = new int[5];
    private int anInt1106;
    private int chatboxScrollMax = 78;
    private ProducingGraphicsBuffer aClass18_1108;
    private ProducingGraphicsBuffer aClass18_1109;
    private ProducingGraphicsBuffer aClass18_1110;
    private int modifiedWidgetId;
    private int selectedInventorySlot;
    private int activeInterfaceType;
    private int anInt1114;
    private int anInt1115;
    private ImageRGB minimapCompass;
    private IndexedImage titleFlameEmblem[];
    private int anInt1118;
    private int anInt1119 = -30658;
    private int destinationX;
    private int destinationY;
    private ImageRGB minimapImage;
    private int anIntArray1123[] = new int[4000];
    private int anIntArray1124[] = new int[4000];
    private byte currentSceneTileFlags[][][];
    private int anInt1126;
    private boolean aBoolean1127 = false;
    private int previousSong;
    private int anInt1129;
    private long friends[] = new long[200];
    private Buffer chatBuffer = new Buffer(new byte[5000]);
    private Npc npcs[] = new Npc[16384];
    private int anInt1133;
    private int anIntArray1134[] = new int[16384];
    private int anInt1135 = 0x766654;
    public boolean loggedIn = false;
    private int renderCount;
    private static int anInt1139;
    private int anInt1140 = -110;
    private long aLong1141;
    private ImageRGB moderatorIcon[] = new ImageRGB[2];
    private byte aByte1143 = -80;
    private boolean characterEditChangeGenger = true;
    private int quakeTimes[] = new int[5];
    private int itemSelected;
    private int anInt1147;
    private int anInt1148;
    private int anInt1149;
    private String selectedItemName;
    private int otherPlayerId;
    private int anInt1152;
    private IndexedImage aClass50_Sub1_Sub1_Sub3Array1153[] = new IndexedImage[100];
    private boolean aBoolean1155 = false;
    private ProducingGraphicsBuffer tabImageProducer;
    private ProducingGraphicsBuffer aClass18_1157;
    private ProducingGraphicsBuffer gameScreenImageProducer;
    private ProducingGraphicsBuffer chatboxProducingGraphicsBuffer;
    private static int anInt1160;
    private byte aByte1161 = 97;
    private boolean aBoolean1163 = false;
    private Scene currentScene;
    private static int anInt1165;
    private int anIntArray1166[] = new int[256];
    public static Player localPlayer;
    private static int anInt1168;
    public int openInterfaceId = -1;
    private int anInt1170;
    private int widgetSelected;
    private int anInt1172;
    private int anInt1173;
    private String selectedWidgetName;
    private int anInt1175 = -89;
    private int anIntArray1176[];
    private int anIntArray1177[];
    private int anInt1178 = 300;
    private int anInt1257;
    private int anIntArray1180[] = new int[33];
    public boolean redrawTabArea = false;
    private ImageRGB aClass50_Sub1_Sub1_Sub1Array1182[] = new ImageRGB[20];
    private int menuActionRow;
    private String menuActionTexts[] = new String[500];
    private IndexedImage inventoryBackgroundImage;
    private IndexedImage minimapBackgroundImage;
    private IndexedImage chatboxBackgroundImage;
    public Buffer buffer = Buffer.allocate(1);
    private int cost[][] = new int[104][104];
    private int dialogueId = -1;
    private ImageRGB mapdotItem;
    private ImageRGB mapdotActor;
    private ImageRGB mapdotPlayer;
    private ImageRGB mapdotFriend;
    private ImageRGB mapdotTeammate;
    private int headIconDrawType;
    private ProducingGraphicsBuffer aClass18_1198;
    private ProducingGraphicsBuffer aClass18_1199;
    private ProducingGraphicsBuffer aClass18_1200;
    private ProducingGraphicsBuffer flameLeftBackground;
    private ProducingGraphicsBuffer flameRightBackground;
    private ProducingGraphicsBuffer aClass18_1203;
    private ProducingGraphicsBuffer aClass18_1204;
    private ProducingGraphicsBuffer aClass18_1205;
    private ProducingGraphicsBuffer aClass18_1206;
    private static boolean aBoolean1207;
    private int anInt1208;
    private boolean aBoolean1209 = false;
    private LinkedList aClass6_1210 = new LinkedList();
    private boolean cutsceneActive = false;
    private boolean redrawChatMode = false;
    private int anInt1213 = -1;
    public static int BITFIELD_MAX_VALUE[];
    private int anInt1215;
    private int cameraX;
    private int cameraZ;
    private int cameraY;
    private int cameraVerticalRotation;
    private int cameraHorizontalRotation;
    private int friendsListAction;
    private int anInt1222;
    private int anInt1223;
    private Socket jaggrabSocket;
    private int loginScreenState;
    private int anInt1226;
    private int tradeMode;
    public Index stores[] = new Index[5];
    private long aLong1229;
    private int reportAbuseInterfaceID = -1;
    private byte objectData[][];
    private int anInt1233;
    private int anInt1234 = 1;
    private static int anInt1235;
    private int anInt1236 = 326;
    private static int anInt1237;
    private int anInt1238;
    private boolean aBoolean1239 = false;
    public boolean redrawChatbox = false;
    private int lastAddress;
    private static boolean aBoolean1242 = true;
    private volatile boolean aBoolean1243 = false;
    private int inputType;
    private byte aByteArray1245[] = new byte[16384];
    private boolean inTutorialIsland;
    private ImageRGB minimapEdge;
    private MouseCapturer mouseCapturer;
    public Widget chatboxInterface = new Widget();
    private long aLong1172;
    private int cameraVertical = 128;
    private int cameraHorizontal;
    private int cameraVelocityHorizontal;
    private int cameraVelocityVertical;
    private int cameraRandomisationA;
    private int anInt1256 = 1;
    private int anIntArray1258[] = new int[100];
    private int soundDelay[] = new int[50];
    private CollisionMap currentCollisionMap[] = new CollisionMap[4];
    private LinkedList spawnObjectList = new LinkedList();
    private int currentCameraPositionH;
    private int currentCameraPositionV;
    private int anInt1264;
    private boolean aBoolean1265 = false;
    private boolean musicEnabled = true;
    private int friendWorlds[] = new int[200];
    public static final int SKIN_COLOURS[] = {9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 58654,
            5027, 1457, 16565, 34991, 25486};
    private int anInt1269;
    private int nextSong;
    private boolean songChanging = true;
    private int anInt1272 = -1;
    private int anInt1273;
    private boolean aBoolean1275 = true;
    private int anInt1276 = -1;
    private boolean aBoolean1277 = false;
    private ImageRGB minimapHint[] = new ImageRGB[1000];
    private int walkableWidgetId = -1;
    private int anInt1280;
    private LinkedList aClass6_1282 = new LinkedList();
    private boolean aBoolean1283 = false;
    private int anInt1284;
    public int currentTabId = 3;
    private int anIntArray1286[] = new int[33];
    private int anInt1287 = 0x332d25;
    private ImageRGB aClass50_Sub1_Sub1_Sub1Array1288[] = new ImageRGB[32];
    private int secondaryCameraVertical;
    private int anIntArray1290[] = {17, 24, 34, 40};
    public OnDemandRequester onDemandRequester;
    private IndexedImage titleboxImage;
    private IndexedImage titleboxButtonImage;
    private int removePlayerCount;
    private int removePlayers[] = new int[1000];
    private int chatTypes[] = new int[100];
    private String chatPlayerNames[] = new String[100];
    private String chatMessages[] = new String[100];
    private int duplicateClickCount;
    private int anInt1300;
    private boolean aBoolean1301 = true;
    private int anInt1302;
    private int anInt1303;
    private int menuScreenArea;
    private int menuOffsetX;
    private int menuOffsetY;
    private int anInt1307;
    private int anInt1308;
    private static int drawCycle;
    private int anIntArray1310[];
    private int anIntArray1311[];
    private int anIntArray1312[];
    private int anIntArray1313[];
    private volatile boolean aBoolean1314 = false;
    private int anInt1315;
    private int anInt1319;
    private volatile boolean aBoolean1320 = false;
    private int soundType[] = new int[50];
    private int anInt1322;
    private LinkedList groundItems[][][] = new LinkedList[4][104][104];
    private int runEnergy;
    public static int pulseCycle;
    private int characterEditIdentityKits[] = new int[7];
    private int currentSong = -1;
    private int atInventoryLoopCycle;
    private int anInt1330;
    private int anInt1331;
    private int atInventoryInterfaceType;
    private static String VALID_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";

    static {
        SKILL_EXPERIENCE = new int[99];
        int value = 0;
        for (int level = 0; level < 99; level++) {
            int realLevel = level + 1;
            int expDiff = (int) ((double) realLevel + 300D * Math.pow(2D, (double) realLevel / 7D));
            value += expDiff;
            SKILL_EXPERIENCE[level] = value / 4;
        }

        BITFIELD_MAX_VALUE = new int[32];
        value = 2;
        for (int k = 0; k < 32; k++) {
            BITFIELD_MAX_VALUE[k] = value - 1;
            value += value;
        }

    }

    private static String getCombatLevelColour(int user, int opponent) {
        int difference = user - opponent;
        if (difference < -9)
            return "@red@";
        if (difference < -6)
            return "@or3@";
        if (difference < -3)
            return "@or2@";
        if (difference < 0)
            return "@or1@";
        if (difference > 9)
            return "@gre@";
        if (difference > 6)
            return "@gr3@";
        if (difference > 3)
            return "@gr2@";
        if (difference > 0)
            return "@gr1@";
        else
            return "@yel@";
    }

    public static void main(String args[]) {
        try {
            System.out.println("RS2 user client - release #" + 377);
            world = 1;
            portOffset = 0;
            setHighMemory();
            memberServer = true;
            SignLink.storeId = 32;
            SignLink.initialize(InetAddress.getLocalHost());
            Game cl = new Game();
            cl.initializeApplication(765, 503);
        } catch (Exception exception) {
        }
    }

    private static void setHighMemory() {
        Scene.lowMemory = false;
        Rasterizer3D.lowMemory = false;
        lowMemory = false;
        Region.lowMemory = false;
        GameObjectDefinition.lowMemory = false;
    }

    private static void setLowMemory() {
        Scene.lowMemory = true;
        Rasterizer3D.lowMemory = true;
        lowMemory = true;
        Region.lowMemory = true;
        GameObjectDefinition.lowMemory = true;
    }

    private static String getFullAmountText(int amount) {
        String string = String.valueOf(amount);
        for (int index = string.length() - 3; index > 0; index -= 3)
            string = string.substring(0, index) + "," + string.substring(index);

        if (string.length() > 8)
            string = "@gre@" + string.substring(0, string.length() - 8) + " million @whi@(" + string + ")";
        else if (string.length() > 4)
            string = "@cya@" + string.substring(0, string.length() - 4) + "K @whi@(" + string + ")";
        return " " + string;
    }

    private static String getShortenedAmountText(int coins) {
        if (coins < 0x186a0)
            return String.valueOf(coins);
        if (coins < 0x989680)
            return coins / 1000 + "K";
        else
            return coins / 0xf4240 + "M";
    }

    private void addChatMessage(String name, String message, int type) {
        if (type == 0 && dialogueId != -1) {
            clickToContinueString = message;
            super.clickType = 0;
        }
        if (backDialogueId == -1)
            redrawChatbox = true;
        for (int index = 99; index > 0; index--) {
            chatTypes[index] = chatTypes[index - 1];
            chatPlayerNames[index] = chatPlayerNames[index - 1];
            chatMessages[index] = chatMessages[index - 1];
        }

        chatTypes[0] = type;
        chatPlayerNames[0] = name;
        chatMessages[0] = message;
    }

    private void addFriend(long name) {
        try {
            if (name == 0L)
                return;
            if (friendsCount >= 100 && playerMembers != 1) {
                addChatMessage("", "Your friendlist is full. Max of 100 for free users, and 200 for members", 0);
                return;
            }
            if (friendsCount >= 200) {
                addChatMessage("", "Your friendlist is full. Max of 100 for free users, and 200 for members", 0);
                return;
            }
            String username = TextUtils.formatName(TextUtils.longToName(name));
            for (int index = 0; index < friendsCount; index++)
                if (friends[index] == name) {
                    addChatMessage("", username + " is already on your friend list", 0);
                    return;
                }

            for (int index = 0; index < ignoresCount; index++)
                if (ignores[index] == name) {
                    addChatMessage("", "Please remove " + username + " from your ignore list first", 0);
                    return;
                }

            if (username.equals(localPlayer.playerName))
                return;
            friendUsernames[friendsCount] = username;
            friends[friendsCount] = name;
            friendWorlds[friendsCount] = 0;
            friendsCount++;
            redrawTabArea = true;
            outBuffer.putOpcode(120);
            outBuffer.putLongBE(name);
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.reportError("94629, " + name + ", " + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private void addIgnore(long name) {
        try {
            if (name == 0L)
                return;
            if (ignoresCount >= 100) {
                addChatMessage("", "Your ignore list is full. Max of 100 hit", 0);
                return;
            }
            String username = TextUtils.formatName(TextUtils.longToName(name));
            for (int index = 0; index < ignoresCount; index++)
                if (ignores[index] == name) {
                    addChatMessage("", username + " is already on your ignore list", 0);
                    return;
                }

            for (int index = 0; index < friendsCount; index++)
                if (friends[index] == name) {
                    addChatMessage("", "Please remove " + username + " from your friend list first", 0);
                    return;
                }

            ignores[ignoresCount++] = name;
            redrawTabArea = true;
            outBuffer.putOpcode(217);
            outBuffer.putLongBE(name);
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.reportError("27939, " + -916 + ", " + name + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private void adjustMidiVolume(boolean flag, byte byte0, int volume) {
        SignLink.midiVolume = volume;
        if (flag)
            SignLink.midi = "voladjust";
    }

    private void method14(String s, int i) {
        if (s == null || s.length() == 0) {
            anInt862 = 0;
            return;
        }
        String s1 = s;
        String as[] = new String[100];
        int j = 0;
        do {
            int k = s1.indexOf(" ");
            if (k == -1)
                break;
            String s2 = s1.substring(0, k).trim();
            if (s2.length() > 0)
                as[j++] = s2.toLowerCase();
            s1 = s1.substring(k + 1);
        } while (true);
        s1 = s1.trim();
        if (s1.length() > 0)
            as[j++] = s1.toLowerCase();
        anInt862 = 0;
        if (i != 2)
            aBoolean959 = !aBoolean959;
        label0:
        for (int l = 0; l < ItemDefinition.count; l++) {
            ItemDefinition class16 = ItemDefinition.lookup(l);
            if (class16.notedTemplateId != -1 || class16.name == null)
                continue;
            String s3 = class16.name.toLowerCase();
            for (int i1 = 0; i1 < j; i1++)
                if (s3.indexOf(as[i1]) == -1)
                    continue label0;

            aStringArray863[anInt862] = s3;
            anIntArray864[anInt862] = l;
            anInt862++;
            if (anInt862 >= aStringArray863.length)
                return;
        }

    }

    private void closeWidgets() {
        outBuffer.putOpcode(110);
        if (openInvOverLayId != -1) {
            method44(openInvOverLayId);
            openInvOverLayId = -1;
            redrawTabArea = true;
            aBoolean1239 = false;
            drawTabIcons = true;
        }
        if (backDialogueId != -1) {
            method44(backDialogueId);
            backDialogueId = -1;
            redrawChatbox = true;
            aBoolean1239 = false;
        }
        if (anInt1053 != -1) {
            method44(anInt1053);
            anInt1053 = -1;
            aBoolean1046 = true;
        }
        if (anInt960 != -1) {
            method44(anInt960);
            anInt960 = -1;
        }
        if (openInterfaceId != -1) {
            method44(openInterfaceId);
            openInterfaceId = -1;
        }
    }

    private void addNewPlayers(int size, Buffer buffer) {
        while (buffer.bitPosition + 10 < size * 8) {
            int id = buffer.getBits(11);

            if (id == 2047)
                break;

            if (players[id] == null) {
                players[id] = new Player();

                if (cachedAppearances[id] != null)
                    players[id].updateAppearance(cachedAppearances[id]);
            }

            playerList[localPlayerCount++] = id;
            Player player = players[id];
            player.pulseCycle = pulseCycle;
            int x = buffer.getBits(5);

            if (x > 15)
                x -= 32;

            int updated = buffer.getBits(1);

            if (updated == 1)
                updatedPlayers[updatedPlayerCount++] = id;

            int discardQueue = buffer.getBits(1);
            int y = buffer.getBits(5);

            if (y > 15)
                y -= 32;

            player.setPosition(localPlayer.pathX[0] + x, localPlayer.pathY[0] + y, discardQueue == 1);
        }

        buffer.finishBitAccess();
    }

    private void processFlamesCycle() {
        aBoolean1320 = true;

        try {
            long startTime = System.currentTimeMillis();
            int cycle = 0;
            int interval = 20;

            while (aBoolean1243) {
                flameCycle++;

                calculateFlamePositions();
                calculateFlamePositions();
                renderFlames();

                if (++cycle > 10) {
                    long currentTime = System.currentTimeMillis();
                    int difference = (int) (currentTime - startTime) / 10 - interval;
                    interval = 40 - difference;

                    if (interval < 5)
                        interval = 5;

                    cycle = 0;
                    startTime = currentTime;
                }
                try {
                    Thread.sleep(interval);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }

        aBoolean1320 = false;
    }

    private void method18(byte byte0) {
        if (byte0 != 3)
            return;
        for (SpawnObjectNode spawnObjectNode = (SpawnObjectNode) spawnObjectList.first(); spawnObjectNode != null; spawnObjectNode = (SpawnObjectNode) spawnObjectList
                .next())
            if (spawnObjectNode.anInt1390 == -1) {
                spawnObjectNode.anInt1395 = 0;
                method140((byte) -61, spawnObjectNode);
            } else {
                spawnObjectNode.remove();
            }

    }

    private void method19(String s) {
        System.out.println(s);
        try {
//            getAppletContext().showDocument(new URL(getCodeBase(), "loaderror_" + s + ".html"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        do
            try {
                Thread.sleep(1000L);
            } catch (Exception _ex) {
            }
        while (true);
    }

    public void shutdown() {
        players = null;
        playerList = null;
        updatedPlayers = null;
        cachedAppearances = null;
        removePlayers = null;
        aClass18_906 = null;
        aClass18_907 = null;
        aClass18_908 = null;
        aClass18_909 = null;
        aClass50_Sub1_Sub1_Sub3_880 = null;
        aClass50_Sub1_Sub1_Sub3_881 = null;
        aClass50_Sub1_Sub1_Sub3_882 = null;
        aClass50_Sub1_Sub1_Sub3_883 = null;
        aClass50_Sub1_Sub1_Sub3_884 = null;
        aClass50_Sub1_Sub1_Sub3_983 = null;
        aClass50_Sub1_Sub1_Sub3_984 = null;
        aClass50_Sub1_Sub1_Sub3_985 = null;
        aClass50_Sub1_Sub1_Sub3_986 = null;
        aClass50_Sub1_Sub1_Sub3_987 = null;
        friendUsernames = null;
        friends = null;
        friendWorlds = null;
        aClass18_1108 = null;
        aClass18_1109 = null;
        aClass18_1110 = null;
        widgetSettings = null;
        mapCoordinates = null;
        terrainData = null;
        objectData = null;
        terrainDataIds = null;
        objectDataIds = null;
        aClass18_1203 = null;
        aClass18_1204 = null;
        aClass18_1205 = null;
        aClass18_1206 = null;
        anIntArrayArray885 = null;
        cost = null;
        anIntArray1123 = null;
        anIntArray1124 = null;
        mapdotItem = null;
        mapdotActor = null;
        mapdotPlayer = null;
        mapdotFriend = null;
        mapdotTeammate = null;
        if (mouseCapturer != null)
            mouseCapturer.capturing = false;
        mouseCapturer = null;
        anIndexedImage1052 = null;
        anIndexedImage1053 = null;
        anIndexedImage1054 = null;
        aClass18_910 = null;
        aClass18_911 = null;
        aClass18_912 = null;
        aClass18_913 = null;
        aClass18_914 = null;
        intGroundArray = null;
        currentSceneTileFlags = null;
        currentScene = null;
        currentCollisionMap = null;
        minimapImage = null;
        flameLeftBackground = null;
        flameRightBackground = null;
        aClass18_1198 = null;
        aClass18_1199 = null;
        aClass18_1200 = null;
        minimapCompass = null;
        aClass50_Sub1_Sub1_Sub1Array1182 = null;
        aClass50_Sub1_Sub1_Sub1Array1288 = null;
        aClass50_Sub1_Sub1_Sub1Array1079 = null;
        aClass50_Sub1_Sub1_Sub1Array954 = null;
        cursorCross = null;
        stopMidi();
        outBuffer = null;
        tempBuffer = null;
        buffer = null;
        tabImageProducer = null;
        aClass18_1157 = null;
        gameScreenImageProducer = null;
        chatboxProducingGraphicsBuffer = null;
        inventoryBackgroundImage = null;
        minimapBackgroundImage = null;
        chatboxBackgroundImage = null;
        try {
            if (gameConnection != null)
                gameConnection.close();
        } catch (Exception _ex) {
        }
        gameConnection = null;
        minimapHintX = null;
        minimapHintY = null;
        minimapHint = null;
        npcs = null;
        anIntArray1134 = null;
        aByteArray1245 = null;
        chatBuffer = null;
        aClass50_Sub1_Sub1_Sub3Array1153 = null;
        worldMapHintIcons = null;
        anIntArrayArray886 = null;
        tabIcon = null;
        aClass6_1282 = null;
        aClass6_1210 = null;
        aClass50_Sub1_Sub1_Sub1_1086 = null;
        if (onDemandRequester != null)
            onDemandRequester.stop();
        onDemandRequester = null;
        firstMenuOperand = null;
        secondMenuOperand = null;
        menuActionTypes = null;
        selectedMenuActions = null;
        menuActionTexts = null;
        groundItems = null;
        spawnObjectList = null;
        method141();
        GameObjectDefinition.method433(false);
        ActorDefinition.reset();
        ItemDefinition.dispose();
        Widget.reset();
        FloorDefinition.cache = null;
        IdentityKit.cache = null;
        AnimationSequence.animations = null;
        SpotAnimation.cache = null;
        SpotAnimation.modelCache = null;
        Varp.cache = null;
        super.imageProducer = null;
        Player.modelCache = null;
        Rasterizer3D.reset();
        Scene.nullLoader();
        Model.reset();
        Animation.reset();
        System.gc();
    }

    private void method21(boolean flag) {
        if (flag)
            return;
        if (super.clickType == 1) {
            if (super.clickX >= 539 && super.clickX <= 573 && super.clickY >= 169 && super.clickY < 205
                    && tabInterfaceIDs[0] != -1) {
                redrawTabArea = true;
                currentTabId = 0;
                drawTabIcons = true;
            }
            if (super.clickX >= 569 && super.clickX <= 599 && super.clickY >= 168 && super.clickY < 205
                    && tabInterfaceIDs[1] != -1) {
                redrawTabArea = true;
                currentTabId = 1;
                drawTabIcons = true;
            }
            if (super.clickX >= 597 && super.clickX <= 627 && super.clickY >= 168 && super.clickY < 205
                    && tabInterfaceIDs[2] != -1) {
                redrawTabArea = true;
                currentTabId = 2;
                drawTabIcons = true;
            }
            if (super.clickX >= 625 && super.clickX <= 669 && super.clickY >= 168 && super.clickY < 203
                    && tabInterfaceIDs[3] != -1) {
                redrawTabArea = true;
                currentTabId = 3;
                drawTabIcons = true;
            }
            if (super.clickX >= 666 && super.clickX <= 696 && super.clickY >= 168 && super.clickY < 205
                    && tabInterfaceIDs[4] != -1) {
                redrawTabArea = true;
                currentTabId = 4;
                drawTabIcons = true;
            }
            if (super.clickX >= 694 && super.clickX <= 724 && super.clickY >= 168 && super.clickY < 205
                    && tabInterfaceIDs[5] != -1) {
                redrawTabArea = true;
                currentTabId = 5;
                drawTabIcons = true;
            }
            if (super.clickX >= 722 && super.clickX <= 756 && super.clickY >= 169 && super.clickY < 205
                    && tabInterfaceIDs[6] != -1) {
                redrawTabArea = true;
                currentTabId = 6;
                drawTabIcons = true;
            }
            if (super.clickX >= 540 && super.clickX <= 574 && super.clickY >= 466 && super.clickY < 502
                    && tabInterfaceIDs[7] != -1) {
                redrawTabArea = true;
                currentTabId = 7;
                drawTabIcons = true;
            }
            if (super.clickX >= 572 && super.clickX <= 602 && super.clickY >= 466 && super.clickY < 503
                    && tabInterfaceIDs[8] != -1) {
                redrawTabArea = true;
                currentTabId = 8;
                drawTabIcons = true;
            }
            if (super.clickX >= 599 && super.clickX <= 629 && super.clickY >= 466 && super.clickY < 503
                    && tabInterfaceIDs[9] != -1) {
                redrawTabArea = true;
                currentTabId = 9;
                drawTabIcons = true;
            }
            if (super.clickX >= 627 && super.clickX <= 671 && super.clickY >= 467 && super.clickY < 502
                    && tabInterfaceIDs[10] != -1) {
                redrawTabArea = true;
                currentTabId = 10;
                drawTabIcons = true;
            }
            if (super.clickX >= 669 && super.clickX <= 699 && super.clickY >= 466 && super.clickY < 503
                    && tabInterfaceIDs[11] != -1) {
                redrawTabArea = true;
                currentTabId = 11;
                drawTabIcons = true;
            }
            if (super.clickX >= 696 && super.clickX <= 726 && super.clickY >= 466 && super.clickY < 503
                    && tabInterfaceIDs[12] != -1) {
                redrawTabArea = true;
                currentTabId = 12;
                drawTabIcons = true;
            }
            if (super.clickX >= 724 && super.clickX <= 758 && super.clickY >= 466 && super.clickY < 502
                    && tabInterfaceIDs[13] != -1) {
                redrawTabArea = true;
                currentTabId = 13;
                drawTabIcons = true;
            }
        }
    }

    void mouseWheelDragged(int i, int j) {
        if (!mouseWheelDown)
            return;
        this.cameraVelocityHorizontal += i * 3;
        this.cameraVelocityVertical += (j << 1);
    }

    private void checkForGameUsages(int i) {
        i = 61 / i;
        try {
            int j = localPlayer.worldX + anInt853;
            int k = localPlayer.worldY + anInt1009;
            if (currentCameraPositionH - j < -500 || currentCameraPositionH - j > 500 || currentCameraPositionV - k < -500 || currentCameraPositionV - k > 500) {
                currentCameraPositionH = j;
                currentCameraPositionV = k;
            }
            if (currentCameraPositionH != j)
                currentCameraPositionH += (j - currentCameraPositionH) / 16;
            if (currentCameraPositionV != k)
                currentCameraPositionV += (k - currentCameraPositionV) / 16;
            if (super.keyStatus[1] == 1)
                cameraVelocityHorizontal += (-24 - cameraVelocityHorizontal) / 2;
            else if (super.keyStatus[2] == 1)
                cameraVelocityHorizontal += (24 - cameraVelocityHorizontal) / 2;
            else
                cameraVelocityHorizontal /= 2;
            if (super.keyStatus[3] == 1)
                cameraVelocityVertical += (12 - cameraVelocityVertical) / 2;
            else if (super.keyStatus[4] == 1)
                cameraVelocityVertical += (-12 - cameraVelocityVertical) / 2;
            else
                cameraVelocityVertical /= 2;
            cameraHorizontal = cameraHorizontal + cameraVelocityHorizontal / 2 & 0x7ff;
            cameraVertical += cameraVelocityVertical / 2;
            if (cameraVertical < 128)
                cameraVertical = 128;
            if (cameraVertical > 383)
                cameraVertical = 383;
            int l = currentCameraPositionH >> 7;
            int i1 = currentCameraPositionV >> 7;
            int j1 = getFloorDrawHeight(plane, currentCameraPositionH, currentCameraPositionV);
            int k1 = 0;
            if (l > 3 && i1 > 3 && l < 100 && i1 < 100) {
                for (int l1 = l - 4; l1 <= l + 4; l1++) {
                    for (int j2 = i1 - 4; j2 <= i1 + 4; j2++) {
                        int k2 = plane;
                        if (k2 < 3 && (currentSceneTileFlags[1][l1][j2] & 2) == 2)
                            k2++;
                        int l2 = j1 - intGroundArray[k2][l1][j2];
                        if (l2 > k1)
                            k1 = l2;
                    }

                }

            }
            int i2 = k1 * 192;
            if (i2 > 98048)
                i2 = 98048;
            if (i2 < 32768)
                i2 = 32768;
            if (i2 > secondaryCameraVertical) {
                secondaryCameraVertical += (i2 - secondaryCameraVertical) / 24;
                return;
            }
            if (i2 < secondaryCameraVertical) {
                secondaryCameraVertical += (i2 - secondaryCameraVertical) / 80;
            }
        } catch (Exception _ex) {
            SignLink.reportError("glfc_ex " + localPlayer.worldX + ","
                    + localPlayer.worldY + "," + currentCameraPositionH + "," + currentCameraPositionV + "," + chunkX + ","
                    + chunkY + "," + nextTopLeftTileX + "," + nextTopRightTileY);
            throw new RuntimeException("eek");
        }
    }

    private boolean processFriendListClick(Widget widget) {
        int row = widget.contentType;
        if (row >= 1 && row <= 200 || row >= 701 && row <= 900) {
            if (row >= 801)
                row -= 701;
            else if (row >= 701)
                row -= 601;
            else if (row >= 101)
                row -= 101;
            else
                row--;
            menuActionTexts[menuActionRow] = "Remove @whi@" + friendUsernames[row];
            menuActionTypes[menuActionRow] = Actions.REMOVE_FRIEND;
            menuActionRow++;
            menuActionTexts[menuActionRow] = "Message @whi@" + friendUsernames[row];
            menuActionTypes[menuActionRow] = Actions.PRIVATE_MESSAGE;
            menuActionRow++;
            return true;
        }
        if (row >= 401 && row <= 500) {
            menuActionTexts[menuActionRow] = "Remove @whi@" + widget.disabledText;
            menuActionTypes[menuActionRow] = Actions.REMOVE_FRIEND;
            menuActionRow++;
            return true;
        } else {
            return false;
        }
    }

    private void method24(boolean flag, byte abyte0[], int i) {
        if (!musicEnabled) {
        } else {
            SignLink.fadeMidi = flag ? 1 : 0;
            SignLink.saveMidi(abyte0, abyte0.length);
            i = 71 / i;
        }
    }

    private void method25() {
        aBoolean1277 = true;
        for (int j = 0; j < 7; j++) {
            characterEditIdentityKits[j] = -1;
            for (int k = 0; k < IdentityKit.count; k++) {
                if (IdentityKit.cache[k].widgetDisplayed
                        || IdentityKit.cache[k].partId != j + (characterEditChangeGenger ? 0 : 7))
                    continue;
                characterEditIdentityKits[j] = k;
                break;
            }

        }

    }

    private void processGroundItems(int x, int y) {
        LinkedList linkedList = groundItems[plane][x][y];
        if (linkedList == null) {
            currentScene.clearGroundItem(plane, x, y);
            return;
        }
        int maxValue = 0xfa0a1f01;
        Object mostValuable = null;
        for (Item item = (Item) linkedList.first(); item != null; item = (Item) linkedList
                .next()) {
            ItemDefinition definition = ItemDefinition.lookup(item.itemId);
            int value = definition.value;
            if (definition.stackable)
                value *= item.itemCount + 1;
            if (value > maxValue) {
                maxValue = value;
                mostValuable = item;
            }
        }

        linkedList.push(((Node) (mostValuable)));
        Object first = null;
        Object second = null;
        for (Item item = (Item) linkedList.first(); item != null; item = (Item) linkedList
                .next()) {
            if (item.itemId != ((Item) (mostValuable)).itemId && first == null)
                first = item;
            if (item.itemId != ((Item) (mostValuable)).itemId
                    && item.itemId != ((Item) (first)).itemId
                    && second == null)
                second = item;
        }

        int key = x + (y << 7) + 0x60000000;
        currentScene.addGroundItemTile(x, y, plane, getFloorDrawHeight(plane, x * 128 + 64, y * 128 + 64),
                key, ((Renderable) (mostValuable)), ((Renderable) (first)), ((Renderable) (second)));
    }


    private void processGame() {
        if (systemUpdateTime > 1)
            systemUpdateTime--;
        if (anInt873 > 0)
            anInt873--;
        for (int i = 0; i < 5; i++)
            if (!parseIncomingPacket())
                break;

        if (!loggedIn)
            return;
        synchronized (mouseCapturer.objectLock) {
            if (accountFlagged) {
                if (super.clickType != 0 || mouseCapturer.coord >= 40) {
                    outBuffer.putOpcode(171);
                    outBuffer.putByte(0);
                    int i2 = outBuffer.currentPosition;
                    int i3 = 0;
                    for (int i4 = 0; i4 < mouseCapturer.coord; i4++) {
                        if (i2 - outBuffer.currentPosition >= 240)
                            break;
                        i3++;
                        int k4 = mouseCapturer.coordsY[i4];
                        if (k4 < 0)
                            k4 = 0;
                        else if (k4 > 502)
                            k4 = 502;
                        int j5 = mouseCapturer.coordsX[i4];
                        if (j5 < 0)
                            j5 = 0;
                        else if (j5 > 764)
                            j5 = 764;
                        int l5 = k4 * 765 + j5;
                        if (mouseCapturer.coordsY[i4] == -1 && mouseCapturer.coordsX[i4] == -1) {
                            j5 = -1;
                            k4 = -1;
                            l5 = 0x7ffff;
                        }
                        if (j5 == anInt1011 && k4 == anInt1012) {
                            if (duplicateClickCount < 2047)
                                duplicateClickCount++;
                        } else {
                            int i6 = j5 - anInt1011;
                            anInt1011 = j5;
                            int j6 = k4 - anInt1012;
                            anInt1012 = k4;
                            if (duplicateClickCount < 8 && i6 >= -32 && i6 <= 31 && j6 >= -32 && j6 <= 31) {
                                i6 += 32;
                                j6 += 32;
                                outBuffer.putShortBE((duplicateClickCount << 12) + (i6 << 6) + j6);
                                duplicateClickCount = 0;
                            } else if (duplicateClickCount < 8) {
                                outBuffer.putMediumBE(0x800000 + (duplicateClickCount << 19) + l5);
                                duplicateClickCount = 0;
                            } else {
                                outBuffer.putIntBE(0xc0000000 + (duplicateClickCount << 19) + l5);
                                duplicateClickCount = 0;
                            }
                        }
                    }

                    outBuffer.putLength(outBuffer.currentPosition - i2);
                    if (i3 >= mouseCapturer.coord) {
                        mouseCapturer.coord = 0;
                    } else {
                        mouseCapturer.coord -= i3;
                        for (int l4 = 0; l4 < mouseCapturer.coord; l4++) {
                            mouseCapturer.coordsX[l4] = mouseCapturer.coordsX[l4 + i3];
                            mouseCapturer.coordsY[l4] = mouseCapturer.coordsY[l4 + i3];
                        }

                    }
                }
            } else {
                mouseCapturer.coord = 0;
            }
        }
        if (super.clickType != 0) {
            long l = (super.clickTime - aLong902) / 50L;
            if (l > 4095L)
                l = 4095L;
            aLong902 = super.clickTime;
            int j2 = super.clickY;
            if (j2 < 0)
                j2 = 0;
            else if (j2 > 502)
                j2 = 502;
            int j3 = super.clickX;
            if (j3 < 0)
                j3 = 0;
            else if (j3 > 764)
                j3 = 764;
            int j4 = j2 * 765 + j3;
            int i5 = 0;
            if (super.clickType == 2)
                i5 = 1;
            int k5 = (int) l;
            outBuffer.putOpcode(19);
            outBuffer.putIntBE((k5 << 20) + (i5 << 19) + j4);
        }
        if (anInt1264 > 0)
            anInt1264--;
        if (super.keyStatus[1] == 1 || super.keyStatus[2] == 1 || super.keyStatus[3] == 1
                || super.keyStatus[4] == 1)
            aBoolean1265 = true;
        if (aBoolean1265 && anInt1264 <= 0) {
            anInt1264 = 20;
            aBoolean1265 = false;
            outBuffer.putOpcode(140);
            outBuffer.putShortLE(cameraVertical);
            outBuffer.putShortLE(cameraHorizontal);
        }
        if (super.awtFocus && !aBoolean1275) {
            aBoolean1275 = true;
            outBuffer.putOpcode(187);
            outBuffer.putByte(1);
        }
        if (!super.awtFocus && aBoolean1275) {
            aBoolean1275 = false;
            outBuffer.putOpcode(187);
            outBuffer.putByte(0);
        }
        method143((byte) -40);
        method36();
        method152();
        timeoutCounter++;
        if (timeoutCounter > 750)
            dropClient();
        method100(0);
        method67(-37214);
        processActorOverheadText();
        tickDelta++;
        if (crossType != 0) {
            crossIndex += 20;
            if (crossIndex >= 400)
                crossType = 0;
        }
        if (atInventoryInterfaceType != 0) {
            atInventoryLoopCycle++;
            if (atInventoryLoopCycle >= 15) {
                if (atInventoryInterfaceType == 2)
                    redrawTabArea = true;
                if (atInventoryInterfaceType == 3)
                    redrawChatbox = true;
                atInventoryInterfaceType = 0;
            }
        }
        if (activeInterfaceType != 0) {
            anInt1269++;
            if (super.mouseX > anInt1114 + 5 || super.mouseX < anInt1114 - 5 || super.mouseY > anInt1115 + 5
                    || super.mouseY < anInt1115 - 5)
                aBoolean1155 = true;
            if (super.mouseButtonPressed == 0) {
                if (activeInterfaceType == 2)
                    redrawTabArea = true;
                if (activeInterfaceType == 3)
                    redrawChatbox = true;
                activeInterfaceType = 0;
                if (aBoolean1155 && anInt1269 >= 5) {
                    lastActiveInvInterface = -1;
                    processRightClick(-521);
                    if (lastActiveInvInterface == modifiedWidgetId && mouseInvInterfaceIndex != selectedInventorySlot) {
                        Widget childInterface = Widget.forId(modifiedWidgetId);
                        int i1 = 0;
                        if (anInt955 == 1 && childInterface.contentType == 206)
                            i1 = 1;
                        if (childInterface.items[mouseInvInterfaceIndex] <= 0)
                            i1 = 0;
                        if (childInterface.itemDeletesDraged) {
                            int k2 = selectedInventorySlot;
                            int k3 = mouseInvInterfaceIndex;
                            childInterface.items[k3] = childInterface.items[k2];
                            childInterface.itemAmounts[k3] = childInterface.itemAmounts[k2];
                            childInterface.items[k2] = -1;
                            childInterface.itemAmounts[k2] = 0;
                        } else if (i1 == 1) {
                            int l2 = selectedInventorySlot;
                            for (int l3 = mouseInvInterfaceIndex; l2 != l3; )
                                if (l2 > l3) {
                                    childInterface.swapItems(l2, l2 - 1);
                                    l2--;
                                } else if (l2 < l3) {
                                    childInterface.swapItems(l2, l2 + 1);
                                    l2++;
                                }

                        } else {
                            childInterface.swapItems(selectedInventorySlot, mouseInvInterfaceIndex);
                        }
                        outBuffer.putOpcode(123);
                        outBuffer.putOffsetShortLE(mouseInvInterfaceIndex);
                        outBuffer.putOffsetByte(i1);
                        outBuffer.putOffsetShortBE(modifiedWidgetId);
                        outBuffer.putShortLE(selectedInventorySlot);
                    }
                } else if ((anInt1300 == 1 || menuHasAddFriend(menuActionRow - 1, aByte1161)) && menuActionRow > 2)
                    determineMenuSize();
                else if (menuActionRow > 0)
                    processMenuActions(menuActionRow - 1);
                atInventoryLoopCycle = 10;
                super.clickType = 0;
            }
        }
        if (Scene.clickedTileX != -1) {
            int dstX = Scene.clickedTileX;
            int dstY = Scene.clickedTileY;
            boolean flag = walk(true, false, dstY, localPlayer.pathY[0], 0, 0, 0, 0, dstX, 0, 0,
                    localPlayer.pathX[0]);
            Scene.clickedTileX = -1;
            if (flag) {
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 1;
                crossIndex = 0;
            }
        }
        if (super.clickType == 1 && clickToContinueString != null) {
            clickToContinueString = null;
            redrawChatbox = true;
            super.clickType = 0;
        }
        processMenuClick();
        if (anInt1053 == -1) {
            method146((byte) 4);
            method21(false);
            method39(true);
        }
        if (super.mouseButtonPressed == 1 || super.clickType == 1)
            anInt1094++;
        if (anInt1284 != 0 || anInt1044 != 0 || anInt1129 != 0) {
            if (anInt893 < 100) {
                anInt893++;
                if (anInt893 == 100) {
                    if (anInt1284 != 0)
                        redrawChatbox = true;
                    if (anInt1044 != 0)
                        redrawTabArea = true;
                }
            }
        } else if (anInt893 > 0)
            anInt893--;
        if (loadingStage == 2)
            checkForGameUsages(409);
        if (loadingStage == 2 && cutsceneActive)
            calculateCameraPosition();
        for (int k = 0; k < 5; k++)
            quakeTimes[k]++;

        manageTextInputs();
        super.idleTime++;
        if (super.idleTime > 4500) {
            anInt873 = 250;
            super.idleTime -= 500;
            outBuffer.putOpcode(202);
        }
        anInt1118++;
        if (anInt1118 > 500) {
            anInt1118 = 0;
            int k1 = (int) (Math.random() * 8D);
            if ((k1 & 1) == 1)
                anInt853 += anInt854;
            if ((k1 & 2) == 2)
                anInt1009 += anInt1010;
            if ((k1 & 4) == 4)
                cameraRandomisationA += anInt1256;
        }
        if (anInt853 < -50)
            anInt854 = 2;
        if (anInt853 > 50)
            anInt854 = -2;
        if (anInt1009 < -55)
            anInt1010 = 2;
        if (anInt1009 > 55)
            anInt1010 = -2;
        if (cameraRandomisationA < -40)
            anInt1256 = 1;
        if (cameraRandomisationA > 40)
            anInt1256 = -1;
        anInt1045++;
        if (anInt1045 > 500) {
            anInt1045 = 0;
            int l1 = (int) (Math.random() * 8D);
            if ((l1 & 1) == 1)
                anInt916 += anInt917;
            if ((l1 & 2) == 2)
                anInt1233 += anInt1234;
        }
        if (anInt916 < -60)
            anInt917 = 2;
        if (anInt916 > 60)
            anInt917 = -2;
        if (anInt1233 < -20)
            anInt1234 = 1;
        if (anInt1233 > 10)
            anInt1234 = -1;
        anInt872++;
        if (anInt872 > 50)
            outBuffer.putOpcode(40);
        try {
            if (gameConnection != null && outBuffer.currentPosition > 0) {
                gameConnection.write(outBuffer.currentPosition, 0, outBuffer.buffer);
                outBuffer.currentPosition = 0;
                anInt872 = 0;
            }
        } catch (IOException _ex) {
            dropClient();
        } catch (Exception exception) {
            logout();
        }
    }

    private void calculateCameraPosition() {
        int i = anInt874 * 128 + 64;
        int j = anInt875 * 128 + 64;
        int k = getFloorDrawHeight(plane, i, j) - anInt876;
        if (cameraX < i) {
            cameraX += anInt877 + ((i - cameraX) * anInt878) / 1000;
            if (cameraX > i)
                cameraX = i;
        }
        if (cameraX > i) {
            cameraX -= anInt877 + ((cameraX - i) * anInt878) / 1000;
            if (cameraX < i)
                cameraX = i;
        }
        if (cameraZ < k) {
            cameraZ += anInt877 + ((k - cameraZ) * anInt878) / 1000;
            if (cameraZ > k)
                cameraZ = k;
        }
        if (cameraZ > k) {
            cameraZ -= anInt877 + ((cameraZ - k) * anInt878) / 1000;
            if (cameraZ < k)
                cameraZ = k;
        }
        if (cameraY < j) {
            cameraY += anInt877 + ((j - cameraY) * anInt878) / 1000;
            if (cameraY > j)
                cameraY = j;
        }
        if (cameraY > j) {
            cameraY -= anInt877 + ((cameraY - j) * anInt878) / 1000;
            if (cameraY < j)
                cameraY = j;
        }
        i = anInt993 * 128 + 64;
        j = anInt994 * 128 + 64;
        k = getFloorDrawHeight(plane, i, j) - anInt995;
        int l = i - cameraX;
        int i1 = k - cameraZ;
        int j1 = j - cameraY;
        int k1 = (int) Math.sqrt(l * l + j1 * j1);
        int l1 = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
        int j2 = (int) (Math.atan2(l, j1) * -325.94900000000001D) & 0x7ff;
        if (l1 < 128)
            l1 = 128;
        if (l1 > 383)
            l1 = 383;
        if (cameraVerticalRotation < l1) {
            cameraVerticalRotation += anInt996 + ((l1 - cameraVerticalRotation) * anInt997) / 1000;
            if (cameraVerticalRotation > l1)
                cameraVerticalRotation = l1;
        }
        if (cameraVerticalRotation > l1) {
            cameraVerticalRotation -= anInt996 + ((cameraVerticalRotation - l1) * anInt997) / 1000;
            if (cameraVerticalRotation < l1)
                cameraVerticalRotation = l1;
        }
        int k2 = j2 - cameraHorizontalRotation;
        if (k2 > 1024)
            k2 -= 2048;
        if (k2 < -1024)
            k2 += 2048;
        if (k2 > 0) {
            cameraHorizontalRotation += anInt996 + (k2 * anInt997) / 1000;
            cameraHorizontalRotation &= 0x7ff;
        }
        if (k2 < 0) {
            cameraHorizontalRotation -= anInt996 + (-k2 * anInt997) / 1000;
            cameraHorizontalRotation &= 0x7ff;
        }
        int l2 = j2 - cameraHorizontalRotation;
        if (l2 > 1024)
            l2 -= 2048;
        if (l2 < -1024)
            l2 += 2048;
        if (l2 < 0 && k2 > 0 || l2 > 0 && k2 < 0)
            cameraHorizontalRotation = j2;
    }

    private void manageTextInputs() {
        while (true) {
            int key = readCharacter();
            if (key == -1)
                break;
            if (openInterfaceId != -1 && openInterfaceId == reportAbuseInterfaceID) {
                if (key == 8 && reportedName.length() > 0)
                    reportedName = reportedName.substring(0, reportedName.length() - 1);
                if ((key >= 97 && key <= 122 || key >= 65 && key <= 90 || key >= 48 && key <= 57 || key == 32)
                        && reportedName.length() < 12)
                    reportedName += (char) key;
            } else if (messagePromptRaised) {
                if (key >= 32 && key <= 122 && chatMessage.length() < 80) {
                    chatMessage += (char) key;
                    redrawChatbox = true;
                }
                if (key == 8 && chatMessage.length() > 0) {
                    chatMessage = chatMessage.substring(0, chatMessage.length() - 1);
                    redrawChatbox = true;
                }
                if (key == 13 || key == 10) {
                    messagePromptRaised = false;
                    redrawChatbox = true;
                    if (friendsListAction == 1) {
                        long l = TextUtils.nameToLong(chatMessage);
                        addFriend(l);
                    }
                    if (friendsListAction == 2 && friendsCount > 0) {
                        long l1 = TextUtils.nameToLong(chatMessage);
                        removeFriend(l1);
                    }
                    if (friendsListAction == 3 && chatMessage.length() > 0) {
                        outBuffer.putOpcode(227);
                        outBuffer.putByte(0);
                        int j = outBuffer.currentPosition;
                        outBuffer.putLongBE(aLong1141);
                        ChatEncoder.put(chatMessage, outBuffer);
                        outBuffer.putLength(outBuffer.currentPosition - j);
                        chatMessage = ChatEncoder.formatChatMessage(chatMessage);
                        //chatMessage = ChatCensor.censorString(chatMessage);
                        addChatMessage(TextUtils.formatName(TextUtils.longToName(aLong1141)),
                                chatMessage, 6);
                        if (privateChatMode == 2) {
                            privateChatMode = 1;
                            redrawChatMode = true;
                            outBuffer.putOpcode(176);
                            outBuffer.putByte(publicChatMode);
                            outBuffer.putByte(privateChatMode);
                            outBuffer.putByte(tradeMode);
                        }
                    }
                    if (friendsListAction == 4 && ignoresCount < 100) {
                        long nameAsLong = TextUtils.nameToLong(chatMessage);
                        addIgnore(nameAsLong);
                    }
                    if (friendsListAction == 5 && ignoresCount > 0) {
                        long nameAsLong = TextUtils.nameToLong(chatMessage);
                        removeIgnore(nameAsLong);
                    }
                }
            } else if (inputType == 1) {
                if (key >= 48 && key <= 57 && inputInputMessage.length() < 10) {
                    inputInputMessage += (char) key;
                    redrawChatbox = true;
                }
                if (key == 8 && inputInputMessage.length() > 0) {
                    inputInputMessage = inputInputMessage.substring(0, inputInputMessage.length() - 1);
                    redrawChatbox = true;
                }
                if (key == 13 || key == 10) {
                    if (inputInputMessage.length() > 0) {
                        int inputValue = 0;
                        try {
                            inputValue = Integer.parseInt(inputInputMessage);
                        } catch (Exception _ex) {
                            /* empty */
                        }
                        outBuffer.putOpcode(75);
                        outBuffer.putIntBE(inputValue);
                    }
                    inputType = 0;
                    redrawChatbox = true;
                }
            } else if (inputType == 2) {
                if (key >= 32 && key <= 122 && inputInputMessage.length() < 12) {
                    inputInputMessage += (char) key;
                    redrawChatbox = true;
                }
                if (key == 8 && inputInputMessage.length() > 0) {
                    inputInputMessage = inputInputMessage.substring(0, inputInputMessage.length() - 1);
                    redrawChatbox = true;
                }
                if (key == 13 || key == 10) {
                    if (inputInputMessage.length() > 0) {
                        outBuffer.putOpcode(206);
                        outBuffer.putLongBE(TextUtils.nameToLong(inputInputMessage));
                    }
                    inputType = 0;
                    redrawChatbox = true;
                }
            } else if (inputType == 3) {
                if (key >= 32 && key <= 122 && inputInputMessage.length() < 40) {
                    inputInputMessage += (char) key;
                    redrawChatbox = true;
                }
                if (key == 8 && inputInputMessage.length() > 0) {
                    inputInputMessage = inputInputMessage.substring(0, inputInputMessage.length() - 1);
                    redrawChatbox = true;
                }
            } else if (backDialogueId == -1 && anInt1053 == -1) {
                if (key >= 32 && key <= 122 && chatboxInput.length() < 80) {
                    chatboxInput += (char) key;
                    redrawChatbox = true;
                }
                if (key == 8 && chatboxInput.length() > 0) {
                    chatboxInput = chatboxInput.substring(0, chatboxInput.length() - 1);
                    redrawChatbox = true;
                }
                if ((key == 13 || key == 10) && chatboxInput.length() > 0) {
                    if (playerRights == 2) {
                        if (chatboxInput.equals("::clientdrop"))
                            dropClient();
                        if (chatboxInput.equals("::lag"))
                            infoDump();
                        if (chatboxInput.equals("::prefetchmusic")) {
                            for (int i_417_ = 0; i_417_ < onDemandRequester.fileCount(2); i_417_++)
                                onDemandRequester.setPriority((byte) 1, 2, i_417_);

                        }
                        if (chatboxInput.equals("::fpson"))
                            fps = true;
                        if (chatboxInput.equals("::fpsoff"))
                            fps = false;
                        if (chatboxInput.equals("::noclip")) {
                            for (int floorLevel = 0; floorLevel < 4; floorLevel++) {
                                for (int width = 1; width < 103; width++) {
                                    for (int heigth = 1; heigth < 103; heigth++)
                                        currentCollisionMap[floorLevel].adjacency[width][heigth] = 0;

                                }

                            }

                        }
                    }
                    if (chatboxInput.startsWith("::")) {
                        outBuffer.putOpcode(56);
                        outBuffer.putByte(chatboxInput.length() - 1);
                        outBuffer.putString(chatboxInput.substring(2));
                    } else {
                        String s = chatboxInput.toLowerCase();
                        int colourCode = 0;
                        if (s.startsWith("yellow:")) {
                            colourCode = 0;
                            chatboxInput = chatboxInput.substring(7);
                        } else if (s.startsWith("red:")) {
                            colourCode = 1;
                            chatboxInput = chatboxInput.substring(4);
                        } else if (s.startsWith("green:")) {
                            colourCode = 2;
                            chatboxInput = chatboxInput.substring(6);
                        } else if (s.startsWith("cyan:")) {
                            colourCode = 3;
                            chatboxInput = chatboxInput.substring(5);
                        } else if (s.startsWith("purple:")) {
                            colourCode = 4;
                            chatboxInput = chatboxInput.substring(7);
                        } else if (s.startsWith("white:")) {
                            colourCode = 5;
                            chatboxInput = chatboxInput.substring(6);
                        } else if (s.startsWith("flash1:")) {
                            colourCode = 6;
                            chatboxInput = chatboxInput.substring(7);
                        } else if (s.startsWith("flash2:")) {
                            colourCode = 7;
                            chatboxInput = chatboxInput.substring(7);
                        } else if (s.startsWith("flash3:")) {
                            colourCode = 8;
                            chatboxInput = chatboxInput.substring(7);
                        } else if (s.startsWith("glow1:")) {
                            colourCode = 9;
                            chatboxInput = chatboxInput.substring(6);
                        } else if (s.startsWith("glow2:")) {
                            colourCode = 10;
                            chatboxInput = chatboxInput.substring(6);
                        } else if (s.startsWith("glow3:")) {
                            colourCode = 11;
                            chatboxInput = chatboxInput.substring(6);
                        }
                        s = chatboxInput.toLowerCase();
                        int effectCode = 0;
                        if (s.startsWith("wave:")) {
                            effectCode = 1;
                            chatboxInput = chatboxInput.substring(5);
                        } else if (s.startsWith("wave2:")) {
                            effectCode = 2;
                            chatboxInput = chatboxInput.substring(6);
                        } else if (s.startsWith("shake:")) {
                            effectCode = 3;
                            chatboxInput = chatboxInput.substring(6);
                        } else if (s.startsWith("scroll:")) {
                            effectCode = 4;
                            chatboxInput = chatboxInput.substring(7);
                        } else if (s.startsWith("slide:")) {
                            effectCode = 5;
                            chatboxInput = chatboxInput.substring(6);
                        }
                        outBuffer.putOpcode(49);
                        outBuffer.putByte(0);
                        int bufferPos = outBuffer.currentPosition;
                        outBuffer.putInvertedByte(colourCode);
                        outBuffer.putOffsetByte(effectCode);
                        chatBuffer.currentPosition = 0;
                        ChatEncoder.put(chatboxInput, chatBuffer);
                        outBuffer.putBytes(chatBuffer.buffer, 0,
                                chatBuffer.currentPosition);
                        outBuffer.putLength(outBuffer.currentPosition - bufferPos);
                        chatboxInput = ChatEncoder.formatChatMessage(chatboxInput);
                        chatboxInput = ChatCensor.censorString(chatboxInput);
                        localPlayer.forcedChat = chatboxInput;
                        localPlayer.textColour = colourCode;
                        localPlayer.textEffect = effectCode;
                        localPlayer.textCycle = 150;
                        if (playerRights == 2)
                            addChatMessage("@cr2@" + localPlayer.playerName,
                                    localPlayer.forcedChat, 2);
                        else if (playerRights == 1)
                            addChatMessage("@cr1@" + localPlayer.playerName,
                                    localPlayer.forcedChat, 2);
                        else
                            addChatMessage(localPlayer.playerName, localPlayer.forcedChat, 2);
                        if (publicChatMode == 2) {
                            publicChatMode = 3;
                            redrawChatMode = true;
                            outBuffer.putOpcode(176);
                            outBuffer.putByte(publicChatMode);
                            outBuffer.putByte(privateChatMode);
                            outBuffer.putByte(tradeMode);
                        }
                    }
                    chatboxInput = "";
                    redrawChatbox = true;
                }
            }
        }
    }

    private DataInputStream openJaggrabStream(String request) throws IOException {
        if (!useJaggrab) {
            if (SignLink.applet != null)
                return SignLink.openURL(request);
            else
                return new DataInputStream((new URL(getCodeBase(), request)).openStream());
        }

        if (jaggrabSocket != null) {
            try {
                jaggrabSocket.close();
            } catch (Exception ignored) {
            }

            jaggrabSocket = null;
        }

        byte[] buffer = String.format("JAGGRAB /%s\n\n", request).getBytes();
        jaggrabSocket = openSocket(Configuration.JAGGRAB_PORT);

        jaggrabSocket.setSoTimeout(10000);
        jaggrabSocket.getOutputStream().write(buffer);

        return new DataInputStream(jaggrabSocket.getInputStream());
    }

    public Socket openSocket(int port) throws IOException {
        if (SignLink.applet != null)
            return SignLink.openSocket(port);

        return new Socket(InetAddress.getByName(getCodeBase().getHost()), port);
    }

    private boolean parseIncomingPacket() {
        if (gameConnection == null)
            return false;
        try {
            int available = gameConnection.getAvailable();
            if (available == 0)
                return false;
            if (opcode == -1) {
                gameConnection.read(buffer.buffer, 0, 1);
                opcode = buffer.buffer[0] & 0xff;
                if (incomingRandom != null)
                    opcode = opcode - incomingRandom.nextInt() & 0xff;
                packetSize = PacketConstants.PACKET_SIZES[opcode];
                available--;
            }
            if (packetSize == -1)
                if (available > 0) {
                    gameConnection.read(buffer.buffer, 0, 1);
                    packetSize = buffer.buffer[0] & 0xff;
                    available--;
                } else {
                    return false;
                }
            if (packetSize == -2)
                if (available > 1) {
                    gameConnection.read(buffer.buffer, 0, 2);
                    buffer.currentPosition = 0;
                    packetSize = buffer.getUnsignedShortBE();
                    available -= 2;
                } else {
                    return false;
                }
            if (available < packetSize)
                return false;
            buffer.currentPosition = 0;
            gameConnection.read(buffer.buffer, 0, packetSize);
            timeoutCounter = 0;
            thirdLastOpcode = secondLastOpcode;
            secondLastOpcode = lastOpcode;
            lastOpcode = opcode;
            if (opcode == 166) {
                int yOffset = buffer.getShortLE();
                int xOffset = buffer.getShortLE();
                int interfaceId = buffer.getUnsignedShortBE();
                Widget widget = Widget.forId(interfaceId);
                widget.xOffset = xOffset;
                widget.yOffset = yOffset;
                opcode = -1;
                return true;
            }
            if (opcode == 186) {
                int rotationX = buffer.getUnsignedNegativeOffsetShortBE();
                int interfaceId = buffer.getUnsignedNegativeOffsetShortLE();
                int zoom = buffer.getUnsignedNegativeOffsetShortBE();
                int rotationY = buffer.getUnsignedShortLE();
                Widget.forId(interfaceId).rotationX = rotationX;
                Widget.forId(interfaceId).rotationY = rotationY;
                Widget.forId(interfaceId).zoom = zoom;
                opcode = -1;
                return true;
            }
            if (opcode == 216) {
                int j1 = buffer.getUnsignedNegativeOffsetShortLE();
                int interfaceId = buffer.getUnsignedNegativeOffsetShortLE();
                Widget.forId(interfaceId).modelType = 1;
                Widget.forId(interfaceId).modelId = j1;
                opcode = -1;
                return true;
            }
            if (opcode == 26) {
                int k1 = buffer.getUnsignedShortBE();
                int k11 = buffer.getUnsignedByte();
                int i17 = buffer.getUnsignedShortBE();
                if (i17 == 65535) {
                    if (currentSound < 50) {
                        sound[currentSound] = (short) k1;
                        soundType[currentSound] = k11;
                        soundDelay[currentSound] = 0;
                        currentSound++;
                    }
                } else if (aBoolean1301 && !lowMemory && currentSound < 50) {
                    sound[currentSound] = k1;
                    soundType[currentSound] = k11;
                    soundDelay[currentSound] = i17 + SoundTrack.trackDelays[k1];
                    currentSound++;
                }
                opcode = -1;
                return true;
            }
            if (opcode == 182) { // interface config/setting
                int configId = buffer.getUnsignedNegativeOffsetShortBE();
                byte configValue = buffer.getPreNegativeOffsetByte();
                anIntArray1005[configId] = configValue;
                if (widgetSettings[configId] != configValue) {
                    widgetSettings[configId] = configValue;
                    updateVarp(0, configId);
                    redrawTabArea = true;
                    if (dialogueId != -1)
                        redrawChatbox = true;
                }
                opcode = -1;
                return true;
            }
            if (opcode == 13) {
                for (int i2 = 0; i2 < players.length; i2++)
                    if (players[i2] != null)
                        players[i2].emoteAnimation = -1;

                for (int l11 = 0; l11 < npcs.length; l11++)
                    if (npcs[l11] != null)
                        npcs[l11].emoteAnimation = -1;

                opcode = -1;
                return true;
            }
            if (opcode == 156) {
                minimapState = buffer.getUnsignedByte();
                opcode = -1;
                return true;
            }
            if (opcode == 162) {
                int j2 = buffer.getUnsignedNegativeOffsetShortBE();
                int interfaceId = buffer.getUnsignedShortLE();
                Widget.forId(interfaceId).modelType = 2;
                Widget.forId(interfaceId).modelId = j2;
                opcode = -1;
                return true;
            }
            if (opcode == 109) {
                int k2 = buffer.getUnsignedShortBE();
                method112((byte) 36, k2);
                if (openInvOverLayId != -1) {
                    method44(openInvOverLayId);
                    openInvOverLayId = -1;
                    redrawTabArea = true;
                    drawTabIcons = true;
                }
                if (anInt1053 != -1) {
                    method44(anInt1053);
                    anInt1053 = -1;
                    aBoolean1046 = true;
                }
                if (anInt960 != -1) {
                    method44(anInt960);
                    anInt960 = -1;
                }
                if (openInterfaceId != -1) {
                    method44(openInterfaceId);
                    openInterfaceId = -1;
                }
                if (backDialogueId != k2) {
                    method44(backDialogueId);
                    backDialogueId = k2;
                }
                aBoolean1239 = false;
                redrawChatbox = true;
                opcode = -1;
                return true;
            }
            if (opcode == 220) {
                int songID = buffer.getUnsignedNegativeOffsetShortLE();
                if (songID == 65535)
                    songID = -1;
                if (songID != currentSong && musicEnabled && !lowMemory && previousSong == 0) {
                    nextSong = songID;
                    songChanging = true;
                    onDemandRequester.request(2, nextSong);
                }
                currentSong = songID;
                opcode = -1;
                return true;
            }
            if (opcode == 249) {
                int fileId = buffer.getUnsignedShortLE();
                int j12 = buffer.getMediumME();
                if (musicEnabled && !lowMemory) {
                    nextSong = fileId;
                    songChanging = false;
                    onDemandRequester.request(2, nextSong); // request something from cache!?!
                    previousSong = j12;
                }
                opcode = -1;
                return true;
            }
            if (opcode == 158) {
                int j3 = buffer.getShortLE();
                if (j3 != dialogueId) {
                    method44(dialogueId);
                    dialogueId = j3;
                }
                redrawChatbox = true;
                opcode = -1;
                return true;
            }
            if (opcode == 218) { // set interface colour(?)
                int interfaceId = buffer.getUnsignedShortBE();
                int rgb = buffer.getUnsignedNegativeOffsetShortBE();
                int j17 = rgb >> 10 & 0x1f;
                int j22 = rgb >> 5 & 0x1f;
                int l24 = rgb & 0x1f;
                Widget.forId(interfaceId).disabledColor = (j17 << 19) + (j22 << 11) + (l24 << 3);
                opcode = -1;
                return true;
            }
            if (opcode == 157) { // update player option
                int slot = buffer.getUnsignedInvertedByte();
                String option = buffer.getString();
                int alwaysOnTop = buffer.getUnsignedByte();
                if (slot >= 1 && slot <= 5) {
                    if (option.equalsIgnoreCase("null"))
                        option = null;
                    aStringArray1069[slot - 1] = option;
                    aBooleanArray1070[slot - 1] = alwaysOnTop == 0;
                }
                opcode = -1;
                return true;
            }
            if (opcode == 6) {
                messagePromptRaised = false;
                inputType = 2;
                inputInputMessage = "";
                redrawChatbox = true;
                opcode = -1;
                return true;
            }
            if (opcode == 201) {
                publicChatMode = buffer.getUnsignedByte();
                privateChatMode = buffer.getUnsignedByte();
                tradeMode = buffer.getUnsignedByte();
                redrawChatMode = true;
                redrawChatbox = true;
                opcode = -1;
                return true;
            }
            if (opcode == 199) {
                headIconDrawType = buffer.getUnsignedByte();
                if (headIconDrawType == 1)
                    anInt1226 = buffer.getUnsignedShortBE();
                if (headIconDrawType >= 2 && headIconDrawType <= 6) {
                    if (headIconDrawType == 2) {
                        anInt847 = 64;
                        anInt848 = 64;
                    }
                    if (headIconDrawType == 3) {
                        anInt847 = 0;
                        anInt848 = 64;
                    }
                    if (headIconDrawType == 4) {
                        anInt847 = 128;
                        anInt848 = 64;
                    }
                    if (headIconDrawType == 5) {
                        anInt847 = 64;
                        anInt848 = 0;
                    }
                    if (headIconDrawType == 6) {
                        anInt847 = 64;
                        anInt848 = 128;
                    }
                    headIconDrawType = 2;
                    anInt844 = buffer.getUnsignedShortBE();
                    anInt845 = buffer.getUnsignedShortBE();
                    anInt846 = buffer.getUnsignedByte();
                }
                if (headIconDrawType == 10)
                    otherPlayerId = buffer.getUnsignedShortBE();
                opcode = -1;
                return true;
            }
            if (opcode == 167) {
                cutsceneActive = true;
                anInt993 = buffer.getUnsignedByte();
                anInt994 = buffer.getUnsignedByte();
                anInt995 = buffer.getUnsignedShortBE();
                anInt996 = buffer.getUnsignedByte();
                anInt997 = buffer.getUnsignedByte();
                if (anInt997 >= 100) {
                    int i4 = anInt993 * 128 + 64;
                    int l12 = anInt994 * 128 + 64;
                    int l17 = getFloorDrawHeight(plane, i4, l12) - anInt995;
                    int k22 = i4 - cameraX;
                    int i25 = l17 - cameraZ;
                    int k27 = l12 - cameraY;
                    int i30 = (int) Math.sqrt(k22 * k22 + k27 * k27);
                    cameraVerticalRotation = (int) (Math.atan2(i25, i30) * 325.94900000000001D) & 0x7ff;
                    cameraHorizontalRotation = (int) (Math.atan2(k22, k27) * -325.94900000000001D) & 0x7ff;
                    if (cameraVerticalRotation < 128)
                        cameraVerticalRotation = 128;
                    if (cameraVerticalRotation > 383)
                        cameraVerticalRotation = 383;
                }
                opcode = -1;
                return true;
            }
            if (opcode == 5) {
                logout(); // simulate a crash??
                opcode = -1;
                return false;
            }
            if (opcode == 115) {
                int j4 = buffer.getIntME2();
                int i13 = buffer.getUnsignedShortLE();
                anIntArray1005[i13] = j4;
                if (widgetSettings[i13] != j4) {
                    widgetSettings[i13] = j4;
                    updateVarp(0, i13);
                    redrawTabArea = true;
                    if (dialogueId != -1)
                        redrawChatbox = true;
                }
                opcode = -1;
                return true;
            }
            if (opcode == 29) { // close open interfaces??
                if (openInvOverLayId != -1) {
                    method44(openInvOverLayId);
                    openInvOverLayId = -1;
                    redrawTabArea = true;
                    drawTabIcons = true;
                }
                if (backDialogueId != -1) {
                    method44(backDialogueId);
                    backDialogueId = -1;
                    redrawChatbox = true;
                }
                if (anInt1053 != -1) {
                    method44(anInt1053);
                    anInt1053 = -1;
                    aBoolean1046 = true;
                }
                if (anInt960 != -1) {
                    method44(anInt960);
                    anInt960 = -1;
                }
                if (openInterfaceId != -1) {
                    method44(openInterfaceId);
                    openInterfaceId = -1;
                }
                if (inputType != 0) {
                    inputType = 0;
                    redrawChatbox = true;
                }
                aBoolean1239 = false;
                opcode = -1;
                return true;
            }
            if (opcode == 76) { // open welcome screen
                anInt1083 = buffer.getUnsignedShortLE();
                anInt1075 = buffer.getUnsignedNegativeOffsetShortLE();
                buffer.getUnsignedShortBE();
                anInt1208 = buffer.getUnsignedShortBE();
                anInt1170 = buffer.getUnsignedShortLE();
                anInt1273 = buffer.getUnsignedNegativeOffsetShortBE();
                anInt1215 = buffer.getUnsignedNegativeOffsetShortBE();
                anInt992 = buffer.getUnsignedShortBE();
                lastAddress = buffer.getIntLE();
                anInt1034 = buffer.getUnsignedNegativeOffsetShortLE();
                buffer.getUnsignedPostNegativeOffsetByte();
                SignLink.dnsLookup(TextUtils.decodeAddress(lastAddress));
                opcode = -1;
                return true;
            }
            if (opcode == 63) { // server message
                String message = buffer.getString();
                if (message.endsWith(":tradereq:")) {
                    String s3 = message.substring(0, message.indexOf(":"));
                    long l18 = TextUtils.nameToLong(s3);
                    boolean flag1 = false;
                    for (int l27 = 0; l27 < ignoresCount; l27++) {
                        if (ignores[l27] != l18)
                            continue;
                        flag1 = true;
                        break;
                    }

                    if (!flag1 && !inTutorialIsland)
                        addChatMessage(s3, "wishes to trade with you.", 4);
                } else if (message.endsWith(":duelreq:")) {
                    String s4 = message.substring(0, message.indexOf(":"));
                    long l19 = TextUtils.nameToLong(s4);
                    boolean flag2 = false;
                    for (int i28 = 0; i28 < ignoresCount; i28++) {
                        if (ignores[i28] != l19)
                            continue;
                        flag2 = true;
                        break;
                    }

                    if (!flag2 && !inTutorialIsland)
                        addChatMessage(s4, "wishes to duel with you.", 8);
                } else if (message.endsWith(":chalreq:")) {
                    String s5 = message.substring(0, message.indexOf(":"));
                    long l20 = TextUtils.nameToLong(s5);
                    boolean flag3 = false;
                    for (int j28 = 0; j28 < ignoresCount; j28++) {
                        if (ignores[j28] != l20)
                            continue;
                        flag3 = true;
                        break;
                    }

                    if (!flag3 && !inTutorialIsland) {
                        String s8 = message.substring(message.indexOf(":") + 1, message.length() - 9);
                        addChatMessage(s5, s8, 8);
                    }
                } else {
                    addChatMessage("", message, 0);
                }
                opcode = -1;
                return true;
            }
            if (opcode == 50) {
                int k4 = buffer.getShortBE();
                if (k4 >= 0)
                    method112((byte) 36, k4);
                if (k4 != walkableWidgetId) {
                    method44(walkableWidgetId);
                    walkableWidgetId = k4;
                }
                opcode = -1;
                return true;
            }
            if (opcode == 82) { // make interface (in)visible maybe?
                boolean flag = buffer.getUnsignedByte() == 1;
                int interfaceId = buffer.getUnsignedShortBE();
                Widget.forId(interfaceId).hiddenUntilHovered = flag;
                opcode = -1;
                return true;
            }
            if (opcode == 174) {
                if (currentTabId == 12)
                    redrawTabArea = true;
                userWeight = buffer.getShortBE();
                opcode = -1;
                return true;
            }
            if (opcode == 233) {
                anInt1319 = buffer.getUnsignedByte();
                opcode = -1;
                return true;
            }
            if (opcode == 61) {
                destinationX = 0;
                opcode = -1;
                return true;
            }
            if (opcode == 128) {
                int interfaceId = buffer.getUnsignedNegativeOffsetShortBE();
                int invOverlayId = buffer.getUnsignedNegativeOffsetShortLE();
                if (backDialogueId != -1) {
                    method44(backDialogueId);
                    backDialogueId = -1;
                    redrawChatbox = true;
                }
                if (anInt1053 != -1) {
                    method44(anInt1053);
                    anInt1053 = -1;
                    aBoolean1046 = true;
                }
                if (anInt960 != -1) {
                    method44(anInt960);
                    anInt960 = -1;
                }
                if (openInterfaceId != interfaceId) {
                    method44(openInterfaceId);
                    openInterfaceId = interfaceId;
                }
                if (openInvOverLayId != invOverlayId) {
                    method44(openInvOverLayId);
                    openInvOverLayId = invOverlayId;
                }
                if (inputType != 0) {
                    inputType = 0;
                    redrawChatbox = true;
                }
                redrawTabArea = true;
                drawTabIcons = true;
                aBoolean1239 = false;
                opcode = -1;
                return true;
            }
            if (opcode == 67) {
                int i5 = buffer.getUnsignedByte();
                int l13 = buffer.getUnsignedByte();
                int i18 = buffer.getUnsignedByte();
                int l22 = buffer.getUnsignedByte();
                customCameraActive[i5] = true;
                cameraJitter[i5] = l13;
                cameraAmplitude[i5] = i18;
                cameraFrequency[i5] = l22;
                quakeTimes[i5] = 0;
                opcode = -1;
                return true;
            }
            if (opcode == 134) { // set items in interface
                redrawTabArea = true;
                int interfaceId = buffer.getUnsignedShortBE();
                Widget inter = Widget.forId(interfaceId);
                while (buffer.currentPosition < packetSize) {
                    int slot = buffer.getSmart();
                    int id = buffer.getUnsignedShortBE();
                    int amount = buffer.getUnsignedByte();
                    if (amount == 255)
                        amount = buffer.getIntBE();
                    if (slot >= 0 && slot < inter.items.length) {
                        inter.items[slot] = id;
                        inter.itemAmounts[slot] = amount;
                    }
                }
                opcode = -1;
                return true;
            }
            if (opcode == 78) { // update friend status
                long friend = buffer.getLongBE();
                int nodeId = buffer.getUnsignedByte();
                String friendName = TextUtils.formatName(TextUtils.longToName(friend));
                for (int k25 = 0; k25 < friendsCount; k25++) {
                    if (friend != friends[k25])
                        continue;
                    if (friendWorlds[k25] != nodeId) {
                        friendWorlds[k25] = nodeId;
                        redrawTabArea = true;
                        if (nodeId > 0)
                            addChatMessage("", friendName + " has logged in.", 5);
                        if (nodeId == 0)
                            addChatMessage("", friendName + " has logged out.", 5);
                    }
                    friendName = null;
                    break;
                }

                if (friendName != null && friendsCount < 200) {
                    friends[friendsCount] = friend;
                    friendUsernames[friendsCount] = friendName;
                    friendWorlds[friendsCount] = nodeId;
                    friendsCount++;
                    redrawTabArea = true;
                }
                for (boolean flag5 = false; !flag5; ) {
                    flag5 = true;
                    for (int j30 = 0; j30 < friendsCount - 1; j30++)
                        if (friendWorlds[j30] != world && friendWorlds[j30 + 1] == world
                                || friendWorlds[j30] == 0 && friendWorlds[j30 + 1] != 0) {
                            int l31 = friendWorlds[j30];
                            friendWorlds[j30] = friendWorlds[j30 + 1];
                            friendWorlds[j30 + 1] = l31;
                            String s10 = friendUsernames[j30];
                            friendUsernames[j30] = friendUsernames[j30 + 1];
                            friendUsernames[j30 + 1] = s10;
                            long l33 = friends[j30];
                            friends[j30] = friends[j30 + 1];
                            friends[j30 + 1] = l33;
                            redrawTabArea = true;
                            flag5 = false;
                        }

                }

                opcode = -1;
                return true;
            }
            if (opcode == 58) { // enter amount interface
                messagePromptRaised = false;
                inputType = 1;
                inputInputMessage = "";
                redrawChatbox = true;
                opcode = -1;
                return true;
            }
            if (opcode == 252) {
                currentTabId = buffer.getUnsignedInvertedByte();
                redrawTabArea = true;
                drawTabIcons = true;
                opcode = -1;
                return true;
            }
            if (opcode == 40) {
                placementY = buffer.getUnsignedPreNegativeOffsetByte();
                placementX = buffer.getUnsignedInvertedByte();
                for (int k5 = placementX; k5 < placementX + 8; k5++) {
                    for (int i14 = placementY; i14 < placementY + 8; i14++)
                        if (groundItems[plane][k5][i14] != null) {
                            groundItems[plane][k5][i14] = null;
                            processGroundItems(k5, i14);
                        }

                }

                for (SpawnObjectNode spawnObjectNode = (SpawnObjectNode) spawnObjectList.first(); spawnObjectNode != null; spawnObjectNode = (SpawnObjectNode) spawnObjectList
                        .next())
                    if (spawnObjectNode.anInt1393 >= placementX && spawnObjectNode.anInt1393 < placementX + 8
                            && spawnObjectNode.anInt1394 >= placementY && spawnObjectNode.anInt1394 < placementY + 8
                            && spawnObjectNode.anInt1391 == plane)
                        spawnObjectNode.anInt1390 = 0;

                opcode = -1;
                return true;
            }
            if (opcode == 255) { // show player in an interface *maybe*?
                int interfaceId = buffer.getUnsignedNegativeOffsetShortLE();
                Widget.forId(interfaceId).modelType = 3;
                if (localPlayer.npcDefinition == null) // maybe that is the appear as npc thing?
                    Widget.forId(interfaceId).modelId = (localPlayer.appearanceColors[0] << 25) + (localPlayer.appearanceColors[4] << 20)
                            + (localPlayer.appearance[0] << 15) + (localPlayer.appearance[8] << 10)
                            + (localPlayer.appearance[11] << 5) + localPlayer.appearance[1];
                else
                    Widget.forId(interfaceId).modelId = (int) (0x12345678L + localPlayer.npcDefinition.id);
                opcode = -1;
                return true;
            }
            if (opcode == 135) { // private message (?)
                long l6 = buffer.getLongBE();
                int i19 = buffer.getIntBE();
                int j23 = buffer.getUnsignedByte();
                boolean flag4 = false;
                for (int k28 = 0; k28 < 100; k28++) {
                    if (anIntArray1258[k28] != i19)
                        continue;
                    flag4 = true;
                    break;
                }

                if (j23 <= 1) {
                    for (int k30 = 0; k30 < ignoresCount; k30++) {
                        if (ignores[k30] != l6)
                            continue;
                        flag4 = true;
                        break;
                    }

                }
                if (!flag4 && !inTutorialIsland)
                    try {
                        anIntArray1258[anInt1152] = i19;
                        anInt1152 = (anInt1152 + 1) % 100;
                        String s9 = ChatEncoder.get(packetSize - 13, buffer);
                        if (j23 != 3)
                            s9 = ChatCensor.censorString(s9);
                        if (j23 == 2 || j23 == 3)
                            addChatMessage("@cr2@" + TextUtils.formatName(TextUtils.longToName(l6)),
                                    s9, 7);
                        else if (j23 == 1)
                            addChatMessage("@cr1@" + TextUtils.formatName(TextUtils.longToName(l6)),
                                    s9, 7);
                        else
                            addChatMessage(TextUtils.formatName(TextUtils.longToName(l6)), s9, 3);
                    } catch (Exception exception1) {
                        SignLink.reportError("cde1");
                    }
                opcode = -1;
                return true;
            }
            if (opcode == 183) {
                placementX = buffer.getUnsignedByte();
                placementY = buffer.getUnsignedPostNegativeOffsetByte();
                while (buffer.currentPosition < packetSize) {
                    int subPacketId = buffer.getUnsignedByte();
                    parsePlacementPacket(buffer, subPacketId);
                }
                opcode = -1;
                return true;
            }
            if (opcode == 159) { // open interface
                int interfaceId = buffer.getUnsignedNegativeOffsetShortLE();
                method112((byte) 36, interfaceId);
                if (openInvOverLayId != -1) {
                    method44(openInvOverLayId);
                    openInvOverLayId = -1;
                    redrawTabArea = true;
                    drawTabIcons = true;
                }
                if (backDialogueId != -1) {
                    method44(backDialogueId);
                    backDialogueId = -1;
                    redrawChatbox = true;
                }
                if (anInt1053 != -1) {
                    method44(anInt1053);
                    anInt1053 = -1;
                    aBoolean1046 = true;
                }
                if (anInt960 != -1) {
                    method44(anInt960);
                    anInt960 = -1;
                }
                if (openInterfaceId != interfaceId) {
                    method44(openInterfaceId);
                    openInterfaceId = interfaceId;
                }
                if (inputType != 0) {
                    inputType = 0;
                    redrawChatbox = true;
                }
                aBoolean1239 = false;
                opcode = -1;
                return true;
            }
            if (opcode == 246) {
                int i7 = buffer.getUnsignedNegativeOffsetShortLE();
                method112((byte) 36, i7);
                if (backDialogueId != -1) {
                    method44(backDialogueId);
                    backDialogueId = -1;
                    redrawChatbox = true;
                }
                if (anInt1053 != -1) {
                    method44(anInt1053);
                    anInt1053 = -1;
                    aBoolean1046 = true;
                }
                if (anInt960 != -1) {
                    method44(anInt960);
                    anInt960 = -1;
                }
                if (openInterfaceId != -1) {
                    method44(openInterfaceId);
                    openInterfaceId = -1;
                }
                if (openInvOverLayId != i7) {
                    method44(openInvOverLayId);
                    openInvOverLayId = i7;
                }
                if (inputType != 0) {
                    inputType = 0;
                    redrawChatbox = true;
                }
                redrawTabArea = true;
                drawTabIcons = true;
                aBoolean1239 = false;
                opcode = -1;
                return true;
            }
            if (opcode == 49) {
                redrawTabArea = true;
                int _skillId = buffer.getUnsignedInvertedByte();
                int _skillLevel = buffer.getUnsignedByte();
                int _skillExp = buffer.getIntBE();
                skillExperience[_skillId] = _skillExp;
                skillLevel[_skillId] = _skillLevel;
                skillMaxLevel[_skillId] = 1;
                for (int level = 0; level < 98; level++)
                    if (_skillExp >= SKILL_EXPERIENCE[level])
                        skillMaxLevel[_skillId] = level + 2;

                opcode = -1;
                return true;
            }
            if (opcode == 206) { // update all items in interface
                redrawTabArea = true;
                int interfaceId = buffer.getUnsignedShortBE();
                Widget inter = Widget.forId(interfaceId);
                int items = buffer.getUnsignedShortBE();
                for (int item = 0; item < items; item++) {
                    inter.items[item] = buffer.getUnsignedNegativeOffsetShortLE();
                    int amount = buffer.getUnsignedInvertedByte();
                    if (amount == 255)
                        amount = buffer.getIntLE();
                    inter.itemAmounts[item] = amount;
                }

                for (int i26 = items; i26 < inter.items.length; i26++) {
                    inter.items[i26] = 0;
                    inter.itemAmounts[i26] = 0;
                }

                opcode = -1;
                return true;
            }
            if (opcode == 222 || opcode == 53) { // new map region
                int tmpChunkX = chunkX;
                int tmpChunkY = chunkY;
                if (opcode == 222) {
                    tmpChunkY = buffer.getUnsignedShortBE();
                    tmpChunkX = buffer.getUnsignedNegativeOffsetShortLE();
                    aBoolean1163 = false;
                }
                if (opcode == 53) {
                    tmpChunkX = buffer.getUnsignedNegativeOffsetShortBE();
                    buffer.initBitAccess();
                    for (int z = 0; z < 4; z++) {
                        for (int x = 0; x < 13; x++) {
                            for (int y = 0; y < 13; y++) {
                                int flag = buffer.getBits(1);
                                if (flag == 1)
                                    constructedMapPalette[z][x][y] = buffer.getBits(26);
                                else
                                    constructedMapPalette[z][x][y] = -1;
                            }

                        }

                    }

                    buffer.finishBitAccess();
                    tmpChunkY = buffer.getUnsignedNegativeOffsetShortBE();
                    aBoolean1163 = true;
                }
                if (chunkX == tmpChunkX && chunkY == tmpChunkY && loadingStage == 2) {
                    opcode = -1;
                    return true;
                }
                chunkX = tmpChunkX;
                chunkY = tmpChunkY;
                nextTopLeftTileX = (chunkX - 6) * 8;
                nextTopRightTileY = (chunkY - 6) * 8;
                aBoolean1067 = false;
                if ((chunkX / 8 == 48 || chunkX / 8 == 49) && chunkY / 8 == 48)
                    aBoolean1067 = true;
                if (chunkX / 8 == 48 && chunkY / 8 == 148)
                    aBoolean1067 = true;
                loadingStage = 1;
                aLong1229 = System.currentTimeMillis();
                method125(null, "Loading - please wait.");
                if (opcode == 222) {
                    int count = 0;
                    for (int fileX = (chunkX - 6) / 8; fileX <= (chunkX + 6) / 8; fileX++) {
                        for (int fileY = (chunkY - 6) / 8; fileY <= (chunkY + 6) / 8; fileY++)
                            count++;

                    }

                    terrainData = new byte[count][];
                    objectData = new byte[count][];
                    mapCoordinates = new int[count];
                    terrainDataIds = new int[count];
                    objectDataIds = new int[count];
                    count = 0;
                    for (int fileX = (chunkX - 6) / 8; fileX <= (chunkX + 6) / 8; fileX++) {
                        for (int fileY = (chunkY - 6) / 8; fileY <= (chunkY + 6) / 8; fileY++) {
                            mapCoordinates[count] = (fileX << 8) + fileY;
                            if (aBoolean1067
                                    && (fileY == 49 || fileY == 149 || fileY == 147 || fileX == 50 || fileX == 49 && fileY == 47)) {
                                terrainDataIds[count] = -1;
                                objectDataIds[count] = -1;
                                count++;
                            } else {
                                int l30 = terrainDataIds[count] = onDemandRequester.regId(0, fileX, fileY, 0);
                                if (l30 != -1)
                                    onDemandRequester.request(3, l30);
                                int i32 = objectDataIds[count] = onDemandRequester.regId(0, fileX, fileY, 1);
                                if (i32 != -1)
                                    onDemandRequester.request(3, i32);
                                count++;
                            }
                        }

                    }

                }
                if (opcode == 53) {
                    int uniqueCount = 0;
                    int fileIndices[] = new int[676];
                    for (int tileZ = 0; tileZ < 4; tileZ++) {
                        for (int tileX = 0; tileX < 13; tileX++) {
                            for (int tileY = 0; tileY < 13; tileY++) {
                                int data = constructedMapPalette[tileZ][tileX][tileY];
                                if (data != -1) {
                                    int chunkX = data >> 14 & 0x3ff;
                                    int chunkY = data >> 3 & 0x7ff;
                                    int fileIndex = (chunkX / 8 << 8) + chunkY / 8;
                                    for (int pos = 0; pos < uniqueCount; pos++) {
                                        if (fileIndices[pos] != fileIndex)
                                            continue;
                                        fileIndex = -1;
                                        break;
                                    }

                                    if (fileIndex != -1)
                                        fileIndices[uniqueCount++] = fileIndex;
                                }
                            }

                        }

                    }

                    terrainData = new byte[uniqueCount][];
                    objectData = new byte[uniqueCount][];
                    mapCoordinates = new int[uniqueCount];
                    terrainDataIds = new int[uniqueCount];
                    objectDataIds = new int[uniqueCount];
                    for (int pos = 0; pos < uniqueCount; pos++) {
                        int j31 = mapCoordinates[pos] = fileIndices[pos];
                        int fileX = j31 >> 8 & 0xff;
                        int fileY = j31 & 0xff;
                        int i34 = terrainDataIds[pos] = onDemandRequester.regId(0, fileX, fileY, 0);
                        if (i34 != -1)
                            onDemandRequester.request(3, i34);
                        int k34 = objectDataIds[pos] = onDemandRequester.regId(0, fileX, fileY, 1);
                        if (k34 != -1)
                            onDemandRequester.request(3, k34);
                    }

                }
                int deltaX = nextTopLeftTileX - topLeftTileX;
                int deltaY = nextTopRightTileY - topLeftTileY;
                topLeftTileX = nextTopLeftTileX;
                topLeftTileY = nextTopRightTileY;
                for (int id = 0; id < 16384; id++) {
                    Npc npc = npcs[id];
                    if (npc != null) {
                        for (int pos = 0; pos < 10; pos++) {
                            ((Actor) (npc)).pathX[pos] -= deltaX;
                            ((Actor) (npc)).pathY[pos] -= deltaY;
                        }

                        npc.worldX -= deltaX * 128;
                        npc.worldY -= deltaY * 128;
                    }
                }

                for (int id = 0; id < anInt968; id++) {
                    Player player = players[id];
                    if (player != null) {
                        for (int pos = 0; pos < 10; pos++) {
                            ((Actor) (player)).pathX[pos] -= deltaX;
                            ((Actor) (player)).pathY[pos] -= deltaY;
                        }

                        player.worldX -= deltaX * 128;
                        player.worldY -= deltaY * 128;
                    }
                }

                aBoolean1209 = true;
                byte byte1 = 0;
                byte byte2 = 104;
                byte byte3 = 1;
                if (deltaX < 0) {
                    byte1 = 103;
                    byte2 = -1;
                    byte3 = -1;
                }
                byte byte4 = 0;
                byte byte5 = 104;
                byte byte6 = 1;
                if (deltaY < 0) {
                    byte4 = 103;
                    byte5 = -1;
                    byte6 = -1;
                }
                for (int i35 = byte1; i35 != byte2; i35 += byte3) {
                    for (int j35 = byte4; j35 != byte5; j35 += byte6) {
                        int k35 = i35 + deltaX;
                        int l35 = j35 + deltaY;
                        for (int i36 = 0; i36 < 4; i36++)
                            if (k35 >= 0 && l35 >= 0 && k35 < 104 && l35 < 104)
                                groundItems[i36][i35][j35] = groundItems[i36][k35][l35];
                            else
                                groundItems[i36][i35][j35] = null;

                    }

                }

                for (SpawnObjectNode spawnObjectNode_1 = (SpawnObjectNode) spawnObjectList.first(); spawnObjectNode_1 != null; spawnObjectNode_1 = (SpawnObjectNode) spawnObjectList
                        .next()) {
                    spawnObjectNode_1.anInt1393 -= deltaX;
                    spawnObjectNode_1.anInt1394 -= deltaY;
                    if (spawnObjectNode_1.anInt1393 < 0 || spawnObjectNode_1.anInt1394 < 0 || spawnObjectNode_1.anInt1393 >= 104
                            || spawnObjectNode_1.anInt1394 >= 104)
                        spawnObjectNode_1.remove();
                }

                if (destinationX != 0) {
                    destinationX -= deltaX;
                    destinationY -= deltaY;
                }
                cutsceneActive = false;
                opcode = -1;
                return true;
            }
            if (opcode == 190) {
                systemUpdateTime = buffer.getUnsignedShortLE() * 30;
                opcode = -1;
                return true;
            }
            if (opcode == 41 || opcode == 121 || opcode == 203 || opcode == 106 || opcode == 59 || opcode == 181
                    || opcode == 208 || opcode == 107 || opcode == 142 || opcode == 88 || opcode == 152) {
                parsePlacementPacket(buffer, opcode); // these are to do with objects iirc
                opcode = -1;
                return true;
            }
            if (opcode == 125) {
                if (currentTabId == 12)
                    redrawTabArea = true;
                runEnergy = buffer.getUnsignedByte();
                opcode = -1;
                return true;
            }
            if (opcode == 21) { // show a model on an interface??
                int scale = buffer.getUnsignedShortBE();
                int itemId = buffer.getUnsignedShortLE();
                int interfaceId = buffer.getUnsignedNegativeOffsetShortLE();
                if (itemId == 65535) {
                    Widget.forId(interfaceId).modelType = 0;
                    opcode = -1;
                    return true;
                } else {
                    ItemDefinition class16 = ItemDefinition.lookup(itemId);
                    Widget.forId(interfaceId).modelType = 4;
                    Widget.forId(interfaceId).modelId = itemId;
                    Widget.forId(interfaceId).rotationX = class16.modelRotationX;
                    Widget.forId(interfaceId).rotationY = class16.modelRotationY;
                    Widget.forId(interfaceId).zoom = (class16.modelScale * 100) / scale;
                    opcode = -1;
                    return true;
                }
            }
            if (opcode == 3) {
                cutsceneActive = true;
                anInt874 = buffer.getUnsignedByte();
                anInt875 = buffer.getUnsignedByte();
                anInt876 = buffer.getUnsignedShortBE();
                anInt877 = buffer.getUnsignedByte();
                anInt878 = buffer.getUnsignedByte();
                if (anInt878 >= 100) {
                    cameraX = anInt874 * 128 + 64;
                    cameraY = anInt875 * 128 + 64;
                    cameraZ = getFloorDrawHeight(plane, cameraX, cameraY) - anInt876;
                }
                opcode = -1;
                return true;
            }
            if (opcode == 2) {
                int interfaceId = buffer.getUnsignedNegativeOffsetShortLE();
                int animationId = buffer.getNegativeOffsetShortBE();
                Widget class13_3 = Widget.forId(interfaceId);
                if (class13_3.disabledAnimation != animationId || animationId == -1) {
                    class13_3.disabledAnimation = animationId;
                    class13_3.animationFrame = 0;
                    class13_3.animationDuration = 0;
                }
                opcode = -1;
                return true;
            }
            if (opcode == 71) {
                updateNpcs(buffer, aBoolean1038, packetSize);
                opcode = -1;
                return true;
            }
            if (opcode == 226) { // ignore list
                ignoresCount = packetSize / 8;
                for (int k8 = 0; k8 < ignoresCount; k8++)
                    ignores[k8] = buffer.getLongBE();

                opcode = -1;
                return true;
            }
            if (opcode == 10) {
                int sidebarIcon = buffer.getUnsignedPreNegativeOffsetByte();
                int interfaceId = buffer.getUnsignedNegativeOffsetShortBE();
                if (interfaceId == 65535)
                    interfaceId = -1;
                if (tabInterfaceIDs[sidebarIcon] != interfaceId) {
                    method44(tabInterfaceIDs[sidebarIcon]);
                    tabInterfaceIDs[sidebarIcon] = interfaceId;
                }
                redrawTabArea = true;
                drawTabIcons = true;
                opcode = -1;
                return true;
            }
            if (opcode == 219) { // reset all items on interface?
                int interfaceId = buffer.getUnsignedShortLE();
                Widget class13_2 = Widget.forId(interfaceId);
                for (int k21 = 0; k21 < class13_2.items.length; k21++) {
                    class13_2.items[k21] = -1;
                    class13_2.items[k21] = 0;
                }

                opcode = -1;
                return true;
            }
            if (opcode == 238) {
                anInt1213 = buffer.getUnsignedByte();
                if (anInt1213 == currentTabId) {
                    if (anInt1213 == 3)
                        currentTabId = 1;
                    else
                        currentTabId = 3;
                    redrawTabArea = true;
                }
                opcode = -1;
                return true;
            }
            if (opcode == 148) {
                cutsceneActive = false;
                for (int j9 = 0; j9 < 5; j9++)
                    customCameraActive[j9] = false;

                opcode = -1;
                return true;
            }
            if (opcode == 126) {
                playerMembers = buffer.getUnsignedByte();
                thisPlayerServerId = buffer.getUnsignedShortLE();
                opcode = -1;
                return true;
            }
            if (opcode == 75) {
                placementX = buffer.getUnsignedInvertedByte();
                placementY = buffer.getUnsignedPostNegativeOffsetByte();
                opcode = -1;
                return true;
            }
            if (opcode == 253) { // open fullscreen interface
                int k9 = buffer.getUnsignedShortLE();
                int k15 = buffer.getUnsignedNegativeOffsetShortBE();
                method112((byte) 36, k15);
                if (k9 != -1)
                    method112((byte) 36, k9);
                if (openInterfaceId != -1) {
                    method44(openInterfaceId);
                    openInterfaceId = -1;
                }
                if (openInvOverLayId != -1) {
                    method44(openInvOverLayId);
                    openInvOverLayId = -1;
                }
                if (backDialogueId != -1) {
                    method44(backDialogueId);
                    backDialogueId = -1;
                }
                if (anInt1053 != k15) {
                    method44(anInt1053);
                    anInt1053 = k15;
                }
                if (anInt960 != k15) {
                    method44(anInt960);
                    anInt960 = k9;
                }
                inputType = 0;
                aBoolean1239 = false;
                opcode = -1;
                return true;
            }
            if (opcode == 251) {
                friendListStatus = buffer.getUnsignedByte();
                redrawTabArea = true;
                opcode = -1;
                return true;
            }
            if (opcode == 18) {
                int l9 = buffer.getUnsignedShortBE();
                int interfaceId = buffer.getUnsignedNegativeOffsetShortBE();
                int l21 = buffer.getUnsignedShortLE();
                Widget.forId(interfaceId).anInt218 = (l9 << 16) + l21;
                opcode = -1;
                return true;
            }
            if (opcode == 90) { // player update
                updatePlayers(packetSize, buffer);
                aBoolean1209 = false;
                opcode = -1;
                return true;
            }
            if (opcode == 113) {
                for (int i10 = 0; i10 < widgetSettings.length; i10++)
                    if (widgetSettings[i10] != anIntArray1005[i10]) {
                        widgetSettings[i10] = anIntArray1005[i10];
                        updateVarp(0, i10);
                        redrawTabArea = true;
                    }

                opcode = -1;
                return true;
            }
            if (opcode == 232) { // update interface string?
                int j10 = buffer.getUnsignedNegativeOffsetShortLE();
                String s6 = buffer.getString();
                Widget.forId(j10).disabledText = s6;
                if (Widget.forId(j10).parentId == tabInterfaceIDs[currentTabId])
                    redrawTabArea = true;
                opcode = -1;
                return true;
            }
            if (opcode == 200) {
                int interfaceId = buffer.getUnsignedShortBE();
                int scrollPosition = buffer.getUnsignedNegativeOffsetShortLE();
                Widget widget = Widget.forId(interfaceId);
                if (widget != null && widget.type == 0) {
                    if (scrollPosition < 0)
                        scrollPosition = 0;
                    if (scrollPosition > widget.scrollLimit - widget.height)
                        scrollPosition = widget.scrollLimit - widget.height;
                    widget.scrollPosition = scrollPosition;
                }
                opcode = -1;
                return true;
            }
            SignLink.reportError("T1 - " + opcode + "," + packetSize + " - " + secondLastOpcode + "," + thirdLastOpcode);
            logout();
        } catch (IOException _ex) {
            dropClient();
        } catch (Exception exception) {
            String s1 = "T2 - " + opcode + "," + secondLastOpcode + "," + thirdLastOpcode + " - " + packetSize + ","
                    + (nextTopLeftTileX + localPlayer.pathX[0]) + ","
                    + (nextTopRightTileY + localPlayer.pathY[0]) + " - ";
            for (int j16 = 0; j16 < packetSize && j16 < 50; j16++)
                s1 = s1 + buffer.buffer[j16] + ",";

            SignLink.reportError(s1);
            logout();

            exception.printStackTrace();
        }
        return true;
    }

    private void drawMenuTooltip() {
        if (menuActionRow < 2 && itemSelected == 0 && widgetSelected == 0)
            return;

        String str;

        if (itemSelected == 1 && menuActionRow < 2)
            str = "Use " + selectedItemName + " with...";
        else if (widgetSelected == 1 && menuActionRow < 2)
            str = selectedWidgetName + "...";
        else
            str = menuActionTexts[menuActionRow - 1];

        if (menuActionRow > 2)
            str = str + "@whi@ / " + (menuActionRow - 2) + " more options";

        fontBold.drawShadowedSeededAlphaString(str, 4, 15, 0xffffff, pulseCycle / 1000);
    }

    private boolean walk(boolean flag, boolean flag1, int dstY, int srcY, int k, int l, int packetType, int j1, int dstX, int l1,
                         int i2, int srcX) {
        byte byte0 = 104;
        byte byte1 = 104;
        for (int x = 0; x < byte0; x++) {
            for (int y = 0; y < byte1; y++) {
                anIntArrayArray885[x][y] = 0;
                cost[x][y] = 0x5f5e0ff;
            }

        }

        int curX = srcX;
        int curY = srcY;
        anIntArrayArray885[srcX][srcY] = 99;
        cost[srcX][srcY] = 0;
        int k3 = 0;
        int l3 = 0;
        anIntArray1123[k3] = srcX;
        anIntArray1124[k3++] = srcY;
        boolean flag2 = false;
        int i4 = anIntArray1123.length;
        int masks[][] = currentCollisionMap[plane].adjacency;
        while (l3 != k3) {
            curX = anIntArray1123[l3];
            curY = anIntArray1124[l3];
            l3 = (l3 + 1) % i4;
            if (curX == dstX && curY == dstY) {
                flag2 = true;
                break;
            }
            if (j1 != 0) {
                if ((j1 < 5 || j1 == 10) && currentCollisionMap[plane].reachedWall(curX, curY, dstX, dstY, j1 - 1, i2)) {
                    flag2 = true;
                    break;
                }
                if (j1 < 10 && currentCollisionMap[plane].reachedWallDecoration(curX, curY, dstX, dstY, j1 - 1, i2)) {
                    flag2 = true;
                    break;
                }
            }
            if (k != 0 && l != 0 && currentCollisionMap[plane].reachedFacingObject(curX, curY, dstX, dstY, k, l, l1)) {
                flag2 = true;
                break;
            }
            int nextCost = cost[curX][curY] + 1;
            if (curX > 0 && anIntArrayArray885[curX - 1][curY] == 0 && (masks[curX - 1][curY] & 0x1280108) == 0) {
                anIntArray1123[k3] = curX - 1;
                anIntArray1124[k3] = curY;
                k3 = (k3 + 1) % i4;
                anIntArrayArray885[curX - 1][curY] = 2;
                cost[curX - 1][curY] = nextCost;
            }
            if (curX < byte0 - 1 && anIntArrayArray885[curX + 1][curY] == 0 && (masks[curX + 1][curY] & 0x1280180) == 0) {
                anIntArray1123[k3] = curX + 1;
                anIntArray1124[k3] = curY;
                k3 = (k3 + 1) % i4;
                anIntArrayArray885[curX + 1][curY] = 8;
                cost[curX + 1][curY] = nextCost;
            }
            if (curY > 0 && anIntArrayArray885[curX][curY - 1] == 0 && (masks[curX][curY - 1] & 0x1280102) == 0) {
                anIntArray1123[k3] = curX;
                anIntArray1124[k3] = curY - 1;
                k3 = (k3 + 1) % i4;
                anIntArrayArray885[curX][curY - 1] = 1;
                cost[curX][curY - 1] = nextCost;
            }
            if (curY < byte1 - 1 && anIntArrayArray885[curX][curY + 1] == 0 && (masks[curX][curY + 1] & 0x1280120) == 0) {
                anIntArray1123[k3] = curX;
                anIntArray1124[k3] = curY + 1;
                k3 = (k3 + 1) % i4;
                anIntArrayArray885[curX][curY + 1] = 4;
                cost[curX][curY + 1] = nextCost;
            }
            if (curX > 0 && curY > 0 && anIntArrayArray885[curX - 1][curY - 1] == 0 && (masks[curX - 1][curY - 1] & 0x128010e) == 0
                    && (masks[curX - 1][curY] & 0x1280108) == 0 && (masks[curX][curY - 1] & 0x1280102) == 0) {
                anIntArray1123[k3] = curX - 1;
                anIntArray1124[k3] = curY - 1;
                k3 = (k3 + 1) % i4;
                anIntArrayArray885[curX - 1][curY - 1] = 3;
                cost[curX - 1][curY - 1] = nextCost;
            }
            if (curX < byte0 - 1 && curY > 0 && anIntArrayArray885[curX + 1][curY - 1] == 0
                    && (masks[curX + 1][curY - 1] & 0x1280183) == 0 && (masks[curX + 1][curY] & 0x1280180) == 0
                    && (masks[curX][curY - 1] & 0x1280102) == 0) {
                anIntArray1123[k3] = curX + 1;
                anIntArray1124[k3] = curY - 1;
                k3 = (k3 + 1) % i4;
                anIntArrayArray885[curX + 1][curY - 1] = 9;
                cost[curX + 1][curY - 1] = nextCost;
            }
            if (curX > 0 && curY < byte1 - 1 && anIntArrayArray885[curX - 1][curY + 1] == 0
                    && (masks[curX - 1][curY + 1] & 0x1280138) == 0 && (masks[curX - 1][curY] & 0x1280108) == 0
                    && (masks[curX][curY + 1] & 0x1280120) == 0) {
                anIntArray1123[k3] = curX - 1;
                anIntArray1124[k3] = curY + 1;
                k3 = (k3 + 1) % i4;
                anIntArrayArray885[curX - 1][curY + 1] = 6;
                cost[curX - 1][curY + 1] = nextCost;
            }
            if (curX < byte0 - 1 && curY < byte1 - 1 && anIntArrayArray885[curX + 1][curY + 1] == 0
                    && (masks[curX + 1][curY + 1] & 0x12801e0) == 0 && (masks[curX + 1][curY] & 0x1280180) == 0
                    && (masks[curX][curY + 1] & 0x1280120) == 0) {
                anIntArray1123[k3] = curX + 1;
                anIntArray1124[k3] = curY + 1;
                k3 = (k3 + 1) % i4;
                anIntArrayArray885[curX + 1][curY + 1] = 12;
                cost[curX + 1][curY + 1] = nextCost;
            }
        }
        anInt1126 = 0;
        if (!flag2)
            if (flag) {
                int l4 = 1000;
                int j5 = 100;
                byte byte2 = 10;
                for (int i6 = dstX - byte2; i6 <= dstX + byte2; i6++) {
                    for (int k6 = dstY - byte2; k6 <= dstY + byte2; k6++)
                        if (i6 >= 0 && k6 >= 0 && i6 < 104 && k6 < 104 && cost[i6][k6] < 100) {
                            int i7 = 0;
                            if (i6 < dstX)
                                i7 = dstX - i6;
                            else if (i6 > (dstX + k) - 1)
                                i7 = i6 - ((dstX + k) - 1);
                            int j7 = 0;
                            if (k6 < dstY)
                                j7 = dstY - k6;
                            else if (k6 > (dstY + l) - 1)
                                j7 = k6 - ((dstY + l) - 1);
                            int k7 = i7 * i7 + j7 * j7;
                            if (k7 < l4 || k7 == l4 && cost[i6][k6] < j5) {
                                l4 = k7;
                                j5 = cost[i6][k6];
                                curX = i6;
                                curY = k6;
                            }
                        }

                }

                if (l4 == 1000)
                    return false;
                if (curX == srcX && curY == srcY)
                    return false;
                anInt1126 = 1;
            } else {
                return false;
            }
        l3 = 0;
        if (flag1)
            startup();
        anIntArray1123[l3] = curX;
        anIntArray1124[l3++] = curY;
        int k5;
        for (int i5 = k5 = anIntArrayArray885[curX][curY]; curX != srcX || curY != srcY; i5 = anIntArrayArray885[curX][curY]) {
            if (i5 != k5) {
                k5 = i5;
                anIntArray1123[l3] = curX;
                anIntArray1124[l3++] = curY;
            }
            if ((i5 & 2) != 0)
                curX++;
            else if ((i5 & 8) != 0)
                curX--;
            if ((i5 & 1) != 0)
                curY++;
            else if ((i5 & 4) != 0)
                curY--;
        }

        if (l3 > 0) {
            int j4 = l3;
            if (j4 > 25)
                j4 = 25;
            l3--;
            int l5 = anIntArray1123[l3];
            int j6 = anIntArray1124[l3];
            if (packetType == 0) {
                outBuffer.putOpcode(28);
                outBuffer.putByte(j4 + j4 + 3);
            }
            if (packetType == 1) {
                outBuffer.putOpcode(213);
                outBuffer.putByte(j4 + j4 + 3 + 14);
            }
            if (packetType == 2) {
                outBuffer.putOpcode(247);
                outBuffer.putByte(j4 + j4 + 3);
            }
            outBuffer.putOffsetShortLE(l5 + nextTopLeftTileX);
            outBuffer.putByte(super.keyStatus[5] != 1 ? 0 : 1);
            outBuffer.putOffsetShortLE(j6 + nextTopRightTileY);
            destinationX = anIntArray1123[0];
            destinationY = anIntArray1124[0];
            for (int l6 = 1; l6 < j4; l6++) {
                l3--;
                outBuffer.putByte(anIntArray1123[l3] - l5);
                outBuffer.putNegativeOffsetByte(anIntArray1124[l3] - j6);
            }

            return true;
        }
        return packetType != 1;
    }

    private void method36() {
        if (loadingStage == 2) {
            for (SpawnObjectNode spawnObjectNode = (SpawnObjectNode) spawnObjectList.first(); spawnObjectNode != null; spawnObjectNode = (SpawnObjectNode) spawnObjectList
                    .next()) {
                if (spawnObjectNode.anInt1390 > 0)
                    spawnObjectNode.anInt1390--;
                if (spawnObjectNode.anInt1390 == 0) {
                    if (spawnObjectNode.anInt1387 < 0
                            || Region.method170(spawnObjectNode.anInt1389, aByte1143, spawnObjectNode.anInt1387)) {
                        method45(spawnObjectNode.anInt1388, spawnObjectNode.anInt1393, spawnObjectNode.anInt1387,
                                spawnObjectNode.anInt1394, spawnObjectNode.anInt1391, spawnObjectNode.anInt1389,
                                spawnObjectNode.anInt1392);
                        spawnObjectNode.remove();
                    }
                } else {
                    if (spawnObjectNode.anInt1395 > 0)
                        spawnObjectNode.anInt1395--;
                    if (spawnObjectNode.anInt1395 == 0
                            && spawnObjectNode.anInt1393 >= 1
                            && spawnObjectNode.anInt1394 >= 1
                            && spawnObjectNode.anInt1393 <= 102
                            && spawnObjectNode.anInt1394 <= 102
                            && (spawnObjectNode.anInt1384 < 0 || Region.method170(spawnObjectNode.anInt1386, aByte1143,
                            spawnObjectNode.anInt1384))) {
                        method45(spawnObjectNode.anInt1385, spawnObjectNode.anInt1393, spawnObjectNode.anInt1384,
                                spawnObjectNode.anInt1394, spawnObjectNode.anInt1391, spawnObjectNode.anInt1386,
                                spawnObjectNode.anInt1392);
                        spawnObjectNode.anInt1395 = -1;
                        if (spawnObjectNode.anInt1384 == spawnObjectNode.anInt1387 && spawnObjectNode.anInt1387 == -1)
                            spawnObjectNode.remove();
                        else if (spawnObjectNode.anInt1384 == spawnObjectNode.anInt1387
                                && spawnObjectNode.anInt1385 == spawnObjectNode.anInt1388
                                && spawnObjectNode.anInt1386 == spawnObjectNode.anInt1389)
                            spawnObjectNode.remove();
                    }
                }
            }

        }
    }

    public String method37(int i) {
        if (i != -42588)
            opcode = buffer.getUnsignedByte();
        if (SignLink.applet != null)
            return SignLink.applet.getDocumentBase().getHost().toLowerCase();
        else
            return "runescape.com";

    }

    private void method38(int i, int j, int k, Player player) {
        if (player == localPlayer)
            return;
        if (menuActionRow >= 400)
            return;
        String s;
        if (player.skillLevel == 0)
            s = player.playerName
                    + getCombatLevelColour(localPlayer.combatLevel, player.combatLevel) + " (level-"
                    + player.combatLevel + ")";
        else
            s = player.playerName + " (skill-" + player.skillLevel + ")";
        if (itemSelected == 1) {
            menuActionTexts[menuActionRow] = "Use " + selectedItemName + " with @whi@" + s;
            menuActionTypes[menuActionRow] = 596;
            selectedMenuActions[menuActionRow] = i;
            firstMenuOperand[menuActionRow] = k;
            secondMenuOperand[menuActionRow] = j;
            menuActionRow++;
        } else if (widgetSelected == 1) {
            if ((anInt1173 & 8) == 8) {
                menuActionTexts[menuActionRow] = selectedWidgetName + " @whi@" + s;
                menuActionTypes[menuActionRow] = 918;
                selectedMenuActions[menuActionRow] = i;
                firstMenuOperand[menuActionRow] = k;
                secondMenuOperand[menuActionRow] = j;
                menuActionRow++;
            }
        } else {
            for (int i1 = 4; i1 >= 0; i1--)
                if (aStringArray1069[i1] != null) {
                    menuActionTexts[menuActionRow] = aStringArray1069[i1] + " @whi@" + s;
                    char c = '\0';
                    if (aStringArray1069[i1].equalsIgnoreCase("attack")) {
                        if (player.combatLevel > localPlayer.combatLevel)
                            c = '\u07D0';
                        if (localPlayer.teamId != 0 && player.teamId != 0)
                            if (localPlayer.teamId == player.teamId)
                                c = '\u07D0';
                            else
                                c = '\0';
                    } else if (aBooleanArray1070[i1])
                        c = '\u07D0';
                    if (i1 == 0)
                        menuActionTypes[menuActionRow] = 200 + c;
                    if (i1 == 1)
                        menuActionTypes[menuActionRow] = 493 + c;
                    if (i1 == 2)
                        menuActionTypes[menuActionRow] = 408 + c;
                    if (i1 == 3)
                        menuActionTypes[menuActionRow] = 677 + c;
                    if (i1 == 4)
                        menuActionTypes[menuActionRow] = 876 + c;
                    selectedMenuActions[menuActionRow] = i;
                    firstMenuOperand[menuActionRow] = k;
                    secondMenuOperand[menuActionRow] = j;
                    menuActionRow++;
                }

        }
        for (int j1 = 0; j1 < menuActionRow; j1++)
            if (menuActionTypes[j1] == 14) {
                menuActionTexts[j1] = "Walk here @whi@" + s;
                return;
            }

    }

    private void method39(boolean flag) {
        if (!flag)
            groundItems = null;
        if (super.clickType == 1) {
            if (super.clickX >= 6 && super.clickX <= 106 && super.clickY >= 467 && super.clickY <= 499) {
                publicChatMode = (publicChatMode + 1) % 4;
                redrawChatMode = true;
                redrawChatbox = true;
                outBuffer.putOpcode(176);
                outBuffer.putByte(publicChatMode);
                outBuffer.putByte(privateChatMode);
                outBuffer.putByte(tradeMode);
            }
            if (super.clickX >= 135 && super.clickX <= 235 && super.clickY >= 467 && super.clickY <= 499) {
                privateChatMode = (privateChatMode + 1) % 3;
                redrawChatMode = true;
                redrawChatbox = true;
                outBuffer.putOpcode(176);
                outBuffer.putByte(publicChatMode);
                outBuffer.putByte(privateChatMode);
                outBuffer.putByte(tradeMode);
            }
            if (super.clickX >= 273 && super.clickX <= 373 && super.clickY >= 467 && super.clickY <= 499) {
                tradeMode = (tradeMode + 1) % 3;
                redrawChatMode = true;
                redrawChatbox = true;
                outBuffer.putOpcode(176);
                outBuffer.putByte(publicChatMode);
                outBuffer.putByte(privateChatMode);
                outBuffer.putByte(tradeMode);
            }
            if (super.clickX >= 412 && super.clickX <= 512 && super.clickY >= 467 && super.clickY <= 499)
                if (openInterfaceId == -1) {
                    closeWidgets();
                    reportedName = "";
                    reportMutePlayer = false;
                    reportAbuseInterfaceID = openInterfaceId = Widget.anInt246;
                } else {
                    addChatMessage("", "Please close the interface you have open before using 'report abuse'", 0);
                }
            anInt1160++;
            if (anInt1160 > 161) {
                anInt1160 = 0;
                outBuffer.putOpcode(22);
                outBuffer.putShortBE(38304);
            }
        }
    }

    private void parsePlayerBlocks(Buffer buffer) {
        for (int i = 0; i < updatedPlayerCount; i++) {
            int id = updatedPlayers[i];
            Player player = players[id];
            int mask = buffer.getUnsignedByte();

            if ((mask & 0x20) != 0)
                mask += buffer.getUnsignedByte() << 8;

            parsePlayerBlock(id, player, mask, buffer);
        }
    }

    private void updateLocalPlayerMovement(Buffer buffer) {
        buffer.initBitAccess();

        int moved = buffer.getBits(1);

        if (moved == 0)
            return;

        int moveType = buffer.getBits(2);

        if (moveType == 0) {
            updatedPlayers[updatedPlayerCount++] = thisPlayerId;
            return;
        }

        if (moveType == 1) {
            int direction = buffer.getBits(3);

            localPlayer.move(direction, false);

            int blockUpdateRequired = buffer.getBits(1);

            if (blockUpdateRequired == 1)
                updatedPlayers[updatedPlayerCount++] = thisPlayerId;
            return;
        }

        if (moveType == 2) {
            int direction1 = buffer.getBits(3);

            localPlayer.move(direction1, true);

            int direction2 = buffer.getBits(3);

            localPlayer.move(direction2, true);

            int blockUpdateRequired = buffer.getBits(1);

            if (blockUpdateRequired == 1)
                updatedPlayers[updatedPlayerCount++] = thisPlayerId;
            return;
        }

        if (moveType == 3) {
            int discardWalkingQueue = buffer.getBits(1);
            plane = buffer.getBits(2);
            int localY = buffer.getBits(7);
            int localX = buffer.getBits(7);
            int blockUpdateRequired = buffer.getBits(1);

            if (blockUpdateRequired == 1)
                updatedPlayers[updatedPlayerCount++] = thisPlayerId;

            localPlayer.setPosition(localX, localY, discardWalkingQueue == 1);
        }
    }

    private void scrollInterface(int i, int j, Widget class13, byte byte0, int k, int l, int i1, int j1, int k1) {
        if (aBoolean1127)
            anInt1303 = 32;
        else
            anInt1303 = 0;
        aBoolean1127 = false;
        if (byte0 != 102) {
            for (int l1 = 1; l1 > 0; l1++) ;
        }
        if (i1 >= k1 && i1 < k1 + 16 && k >= j && k < j + 16) {
            class13.scrollPosition -= anInt1094 * 4;
            if (l == 1)
                redrawTabArea = true;
            if (l == 2 || l == 3)
                redrawChatbox = true;
            return;
        }
        if (i1 >= k1 && i1 < k1 + 16 && k >= (j + j1) - 16 && k < j + j1) {
            class13.scrollPosition += anInt1094 * 4;
            if (l == 1)
                redrawTabArea = true;
            if (l == 2 || l == 3)
                redrawChatbox = true;
            return;
        }
        if (i1 >= k1 - anInt1303 && i1 < k1 + 16 + anInt1303 && k >= j + 16 && k < (j + j1) - 16 && anInt1094 > 0) {
            int i2 = ((j1 - 32) * j1) / i;
            if (i2 < 8)
                i2 = 8;
            int j2 = k - j - 16 - i2 / 2;
            int k2 = j1 - 32 - i2;
            class13.scrollPosition = ((i - j1) * j2) / k2;
            if (l == 1)
                redrawTabArea = true;
            if (l == 2 || l == 3)
                redrawChatbox = true;
            aBoolean1127 = true;
        }
    }

    private void method43(byte byte0) {
        if (itemSelected == 0 && widgetSelected == 0) {
            menuActionTexts[menuActionRow] = "Walk here";
            menuActionTypes[menuActionRow] = 14;
            firstMenuOperand[menuActionRow] = super.mouseX;
            secondMenuOperand[menuActionRow] = super.mouseY;
            menuActionRow++;
        }
        int i = -1;
        if (byte0 != 7)
            opcode = -1;
        for (int j = 0; j < Model.resourceCount; j++) {
            int k = Model.anIntArray1709[j];
            int l = k & 0x7f;
            int i1 = k >> 7 & 0x7f;
            int j1 = k >> 29 & 3;
            int k1 = k >> 14 & 0x7fff;
            if (k == i)
                continue;
            i = k;
            if (j1 == 2 && currentScene.method271(plane, l, i1, k) >= 0) {
                GameObjectDefinition class47 = GameObjectDefinition.getDefinition(k1);
                if (class47.childrenIds != null)
                    class47 = class47.getChildDefinition();
                if (class47 == null)
                    continue;
                if (itemSelected == 1) {
                    menuActionTexts[menuActionRow] = "Use " + selectedItemName + " with @cya@" + class47.name;
                    menuActionTypes[menuActionRow] = 467;
                    selectedMenuActions[menuActionRow] = k;
                    firstMenuOperand[menuActionRow] = l;
                    secondMenuOperand[menuActionRow] = i1;
                    menuActionRow++;
                } else if (widgetSelected == 1) {
                    if ((anInt1173 & 4) == 4) {
                        menuActionTexts[menuActionRow] = selectedWidgetName + " @cya@" + class47.name;
                        menuActionTypes[menuActionRow] = 376;
                        selectedMenuActions[menuActionRow] = k;
                        firstMenuOperand[menuActionRow] = l;
                        secondMenuOperand[menuActionRow] = i1;
                        menuActionRow++;
                    }
                } else {
                    if (class47.options != null) {
                        for (int l1 = 4; l1 >= 0; l1--)
                            if (class47.options[l1] != null) {
                                menuActionTexts[menuActionRow] = class47.options[l1] + " @cya@"
                                        + class47.name;
                                if (l1 == 0)
                                    menuActionTypes[menuActionRow] = 35;
                                if (l1 == 1)
                                    menuActionTypes[menuActionRow] = 389;
                                if (l1 == 2)
                                    menuActionTypes[menuActionRow] = 888;
                                if (l1 == 3)
                                    menuActionTypes[menuActionRow] = 892;
                                if (l1 == 4)
                                    menuActionTypes[menuActionRow] = 1280;
                                selectedMenuActions[menuActionRow] = k;
                                firstMenuOperand[menuActionRow] = l;
                                secondMenuOperand[menuActionRow] = i1;
                                menuActionRow++;
                            }

                    }
                    menuActionTexts[menuActionRow] = "Examine @cya@" + class47.name;
                    menuActionTypes[menuActionRow] = 1412;
                    selectedMenuActions[menuActionRow] = class47.id << 14;
                    firstMenuOperand[menuActionRow] = l;
                    secondMenuOperand[menuActionRow] = i1;
                    menuActionRow++;
                }
            }
            if (j1 == 1) {
                Npc npc = npcs[k1];
                if (npc.npcDefinition.boundaryDimension == 1
                        && (npc.worldX & 0x7f) == 64
                        && (npc.worldY & 0x7f) == 64) {
                    for (int i2 = 0; i2 < anInt1133; i2++) {
                        Npc npc1 = npcs[anIntArray1134[i2]];
                        if (npc1 != null
                                && npc1 != npc
                                && npc1.npcDefinition.boundaryDimension == 1
                                && npc1.worldX == npc.worldX
                                && npc1.worldY == npc.worldY)
                            method82(npc1.npcDefinition, i1, l, anIntArray1134[i2]);
                    }

                    for (int k2 = 0; k2 < localPlayerCount; k2++) {
                        Player player = players[playerList[k2]];
                        if (player != null
                                && player.worldX == npc.worldX
                                && player.worldY == npc.worldY)
                            method38(playerList[k2], i1, l, player);
                    }

                }
                method82(npc.npcDefinition, i1, l, k1);
            }
            if (j1 == 0) {
                Player player1 = players[k1];
                if ((player1.worldX & 0x7f) == 64
                        && (player1.worldY & 0x7f) == 64) {
                    for (int j2 = 0; j2 < anInt1133; j2++) {
                        Npc npc = npcs[anIntArray1134[j2]];
                        if (npc != null
                                && npc.npcDefinition.boundaryDimension == 1
                                && npc.worldX == player1.worldX
                                && npc.worldY == player1.worldY)
                            method82(npc.npcDefinition, i1, l, anIntArray1134[j2]);
                    }

                    for (int l2 = 0; l2 < localPlayerCount; l2++) {
                        Player player = players[playerList[l2]];
                        if (player != null
                                && player != player1
                                && player.worldX == player1.worldX
                                && player.worldY == player1.worldY)
                            method38(playerList[l2], i1, l, player);
                    }

                }
                method38(k1, i1, l, player1);
            }
            if (j1 == 3) {
                LinkedList itemList = groundItems[plane][l][i1];
                if (itemList != null) {
                    for (Item item = (Item) itemList.last(); item != null; item = (Item) itemList
                            .previous()) {
                        ItemDefinition itemDefinition = ItemDefinition.lookup(item.itemId);
                        if (itemSelected == 1) {
                            menuActionTexts[menuActionRow] = "Use " + selectedItemName + " with @lre@" + itemDefinition.name;
                            menuActionTypes[menuActionRow] = 100;
                            selectedMenuActions[menuActionRow] = item.itemId;
                            firstMenuOperand[menuActionRow] = l;
                            secondMenuOperand[menuActionRow] = i1;
                            menuActionRow++;
                        } else if (widgetSelected == 1) {
                            if ((anInt1173 & 1) == 1) {
                                menuActionTexts[menuActionRow] = selectedWidgetName + " @lre@" + itemDefinition.name;
                                menuActionTypes[menuActionRow] = 199;
                                selectedMenuActions[menuActionRow] = item.itemId;
                                firstMenuOperand[menuActionRow] = l;
                                secondMenuOperand[menuActionRow] = i1;
                                menuActionRow++;
                            }
                        } else {
                            for (int i3 = 4; i3 >= 0; i3--)
                                if (itemDefinition.groundActions != null && itemDefinition.groundActions[i3] != null) {
                                    menuActionTexts[menuActionRow] = itemDefinition.groundActions[i3] + " @lre@" + itemDefinition.name;
                                    if (i3 == 0)
                                        menuActionTypes[menuActionRow] = 68;
                                    if (i3 == 1)
                                        menuActionTypes[menuActionRow] = 26;
                                    if (i3 == 2)
                                        menuActionTypes[menuActionRow] = 684;
                                    if (i3 == 3)
                                        menuActionTypes[menuActionRow] = 930;
                                    if (i3 == 4)
                                        menuActionTypes[menuActionRow] = 270;
                                    selectedMenuActions[menuActionRow] = item.itemId;
                                    firstMenuOperand[menuActionRow] = l;
                                    secondMenuOperand[menuActionRow] = i1;
                                    menuActionRow++;
                                } else if (i3 == 2) {
                                    menuActionTexts[menuActionRow] = "Take @lre@" + itemDefinition.name;
                                    menuActionTypes[menuActionRow] = 684;
                                    selectedMenuActions[menuActionRow] = item.itemId;
                                    firstMenuOperand[menuActionRow] = l;
                                    secondMenuOperand[menuActionRow] = i1;
                                    menuActionRow++;
                                }

                            menuActionTexts[menuActionRow] = "Examine @lre@" + itemDefinition.name;
                            menuActionTypes[menuActionRow] = 1564;
                            selectedMenuActions[menuActionRow] = item.itemId;
                            firstMenuOperand[menuActionRow] = l;
                            secondMenuOperand[menuActionRow] = i1;
                            menuActionRow++;
                        }
                    }

                }
            }
        }

    }

    private void method44(int i) {
        Widget.method200(i);
    }

    private void method45(int i, int j, int k, int l, int i1, int j1, int k1) {
        if (j >= 1 && l >= 1 && j <= 102 && l <= 102) {
            if (lowMemory && i1 != plane)
                return;
            int l1 = 0;
            if (k1 == 0)
                l1 = currentScene.method267(i1, j, l);
            if (k1 == 1)
                l1 = currentScene.method268(j, (byte) 4, i1, l);
            if (k1 == 2)
                l1 = currentScene.method269(i1, j, l);
            if (k1 == 3)
                l1 = currentScene.getFloorDecorationHash(i1, j, l);
            if (l1 != 0) {
                int l2 = currentScene.method271(i1, j, l, l1);
                int i2 = l1 >> 14 & 0x7fff;
                int j2 = l2 & 0x1f;
                int k2 = l2 >> 6;
                if (k1 == 0) {
                    currentScene.removeWallObject(j, l, i1);
                    GameObjectDefinition class47 = GameObjectDefinition.getDefinition(i2);
                    if (class47.solid)
                        currentCollisionMap[i1].unmarkWall(k2, j, l, j2, class47.walkable);
                }
                if (k1 == 1)
                    currentScene.removeWallDecoration(j, l, i1);
                if (k1 == 2) {
                    currentScene.removeInteractiveObject(j, l, i1);
                    GameObjectDefinition class47_1 = GameObjectDefinition.getDefinition(i2);
                    if (j + class47_1.sizeX > 103 || l + class47_1.sizeX > 103 || j + class47_1.sizeY > 103
                            || l + class47_1.sizeY > 103)
                        return;
                    if (class47_1.solid)
                        currentCollisionMap[i1].unmarkSolidOccupant(anInt1055, l, j, k2, class47_1.sizeY, class47_1.walkable,
                                class47_1.sizeX);
                }
                if (k1 == 3) {
                    currentScene.method261(j, l, i1);
                    GameObjectDefinition class47_2 = GameObjectDefinition.getDefinition(i2);
                    if (class47_2.solid && class47_2.actionsBoolean)
                        currentCollisionMap[i1].unmarkConcealed(j, l);
                }
            }
            if (k >= 0) {
                int i3 = i1;
                if (i3 < 3 && (currentSceneTileFlags[1][j][l] & 2) == 2)
                    i3++;
                Region.method165(k, i3, j1, l, currentCollisionMap[i1], i, j, 0, i1, currentScene,
                        intGroundArray);
            }
        }
    }

    private void method46(Buffer buffer) {
        buffer.initBitAccess();
        int j = buffer.getBits(8);
        if (j < anInt1133) {
            for (int k = j; k < anInt1133; k++)
                removePlayers[removePlayerCount++] = anIntArray1134[k];

        }
        if (j > anInt1133) {
            SignLink.reportError(username + " Too many npcs");
            throw new RuntimeException("eek");
        }
        anInt1133 = 0;
        for (int l = 0; l < j; l++) {
            int i1 = anIntArray1134[l];
            Npc npc = npcs[i1];
            int updateRequired = buffer.getBits(1);
            if (updateRequired == 0) {
                anIntArray1134[anInt1133++] = i1;
                npc.pulseCycle = pulseCycle;
            } else {
                int moveType = buffer.getBits(2);
                if (moveType == 0) {
                    anIntArray1134[anInt1133++] = i1;
                    npc.pulseCycle = pulseCycle;
                    updatedPlayers[updatedPlayerCount++] = i1;
                } else if (moveType == 1) {
                    anIntArray1134[anInt1133++] = i1;
                    npc.pulseCycle = pulseCycle;
                    int direction = buffer.getBits(3);
                    npc.move(direction, false);
                    int blockUpdateRequired = buffer.getBits(1);
                    if (blockUpdateRequired == 1)
                        updatedPlayers[updatedPlayerCount++] = i1;
                } else if (moveType == 2) {
                    anIntArray1134[anInt1133++] = i1;
                    npc.pulseCycle = pulseCycle;
                    int direction1 = buffer.getBits(3);
                    npc.move(direction1, true);
                    int direction2 = buffer.getBits(3);
                    npc.move(direction2, true);
                    int blockUpdateRequired = buffer.getBits(1);
                    if (blockUpdateRequired == 1)
                        updatedPlayers[updatedPlayerCount++] = i1;
                } else if (moveType == 3)
                    removePlayers[removePlayerCount++] = i1;
            }
        }

    }

    private void updateNpcs(Buffer buffer, boolean flag, int packetSize) {
        loggedIn &= flag;
        removePlayerCount = 0;
        updatedPlayerCount = 0;
        method46(buffer);
        method132(buffer, packetSize, false);
        parseNpcUpdateMasks(buffer, packetSize, 838);
        for (int j = 0; j < removePlayerCount; j++) {
            int k = removePlayers[j];
            if (npcs[k].pulseCycle != pulseCycle) {
                npcs[k].npcDefinition = null;
                npcs[k] = null;
            }
        }

        if (buffer.currentPosition != packetSize) {
            SignLink.reportError(username + " size mismatch in getnpcpos - coord:" + buffer.currentPosition
                    + " psize:" + packetSize);
            throw new RuntimeException("eek");
        }
        for (int l = 0; l < anInt1133; l++)
            if (npcs[anIntArray1134[l]] == null) {
                SignLink.reportError(username + " null entry in npc list - coord:" + l + " size:" + anInt1133);
                throw new RuntimeException("eek");
            }

    }

    private void resetModelCaches() {
        GameObjectDefinition.modelCache.removeAll();
        GameObjectDefinition.animatedModelCache.removeAll();
        ActorDefinition.modelCache.removeAll();
        ItemDefinition.modelCache.removeAll();
        ItemDefinition.rgbImageCache.removeAll();
        Player.modelCache.removeAll();
        SpotAnimation.modelCache.removeAll();
    }


    private void renderProjectiles() {
        Projectile class50_sub1_sub4_sub2 = (Projectile) aClass6_1282.first();
        for (; class50_sub1_sub4_sub2 != null; class50_sub1_sub4_sub2 = (Projectile) aClass6_1282
                .next())
            if (class50_sub1_sub4_sub2.sceneId != plane || pulseCycle > class50_sub1_sub4_sub2.endCycle)
                class50_sub1_sub4_sub2.remove();
            else if (pulseCycle >= class50_sub1_sub4_sub2.delay) {
                if (class50_sub1_sub4_sub2.targetedEntityId > 0) {
                    Npc class50_sub1_sub4_sub3_sub1 = npcs[class50_sub1_sub4_sub2.targetedEntityId - 1];
                    if (class50_sub1_sub4_sub3_sub1 != null
                            && class50_sub1_sub4_sub3_sub1.worldX >= 0
                            && class50_sub1_sub4_sub3_sub1.worldX < 13312
                            && class50_sub1_sub4_sub3_sub1.worldY >= 0
                            && class50_sub1_sub4_sub3_sub1.worldY < 13312)
                        class50_sub1_sub4_sub2.trackTarget(class50_sub1_sub4_sub3_sub1.worldX,
                                class50_sub1_sub4_sub3_sub1.worldY, getFloorDrawHeight(
                                        class50_sub1_sub4_sub2.sceneId, class50_sub1_sub4_sub3_sub1.worldX, class50_sub1_sub4_sub3_sub1.worldY
                                )
                                        - class50_sub1_sub4_sub2.endHeight, pulseCycle);
                }
                if (class50_sub1_sub4_sub2.targetedEntityId < 0) {
                    int i = -class50_sub1_sub4_sub2.targetedEntityId - 1;
                    Player class50_sub1_sub4_sub3_sub2;
                    if (i == thisPlayerServerId)
                        class50_sub1_sub4_sub3_sub2 = localPlayer;
                    else
                        class50_sub1_sub4_sub3_sub2 = players[i];
                    if (class50_sub1_sub4_sub3_sub2 != null
                            && class50_sub1_sub4_sub3_sub2.worldX >= 0
                            && class50_sub1_sub4_sub3_sub2.worldX < 13312
                            && class50_sub1_sub4_sub3_sub2.worldY >= 0
                            && class50_sub1_sub4_sub3_sub2.worldY < 13312)
                        class50_sub1_sub4_sub2.trackTarget(class50_sub1_sub4_sub3_sub2.worldX,
                                class50_sub1_sub4_sub3_sub2.worldY, getFloorDrawHeight(
                                        class50_sub1_sub4_sub2.sceneId, class50_sub1_sub4_sub3_sub2.worldX, class50_sub1_sub4_sub3_sub2.worldY
                                )
                                        - class50_sub1_sub4_sub2.endHeight, pulseCycle);
                }
                class50_sub1_sub4_sub2.move(tickDelta);
                currentScene.addEntity(plane, (int) class50_sub1_sub4_sub2.currentX, (int) class50_sub1_sub4_sub2.currentY, (int) class50_sub1_sub4_sub2.currentHeight, class50_sub1_sub4_sub2, -1,
                        60, false,
                        class50_sub1_sub4_sub2.anInt1562);
            }

        anInt1168++;
        if (anInt1168 > 51) {
            anInt1168 = 0;
            outBuffer.putOpcode(248);
        }
    }

    private void prepareTitle() {
        titleboxImage = new IndexedImage(titleArchive, "titlebox", 0);
        titleboxButtonImage = new IndexedImage(titleArchive, "titlebutton", 0);
        titleFlameEmblem = new IndexedImage[12];
        for (int i = 0; i < 12; i++)
            titleFlameEmblem[i] = new IndexedImage(titleArchive, "runes", i);

        anImageRGB1226 = new ImageRGB(128, 265);
        anImageRGB1227 = new ImageRGB(128, 265);
        System.arraycopy(flameLeftBackground.pixels, 0, anImageRGB1226.pixels, 0, (128 * 265));
        System.arraycopy(flameRightBackground.pixels, 0, anImageRGB1227.pixels, 0, (128 * 265));


        anIntArray1311 = new int[256];
        for (int l = 0; l < 64; l++)
            anIntArray1311[l] = l * 0x40000;

        for (int i1 = 0; i1 < 64; i1++)
            anIntArray1311[i1 + 64] = 0xff0000 + 1024 * i1;

        for (int j1 = 0; j1 < 64; j1++)
            anIntArray1311[j1 + 128] = 0xffff00 + 4 * j1;

        for (int k1 = 0; k1 < 64; k1++)
            anIntArray1311[k1 + 192] = 0xffffff;

        anIntArray1312 = new int[256];
        for (int l1 = 0; l1 < 64; l1++)
            anIntArray1312[l1] = l1 * 1024;

        for (int i2 = 0; i2 < 64; i2++)
            anIntArray1312[i2 + 64] = 65280 + 4 * i2;

        for (int j2 = 0; j2 < 64; j2++)
            anIntArray1312[j2 + 128] = 65535 + 0x40000 * j2;

        for (int k2 = 0; k2 < 64; k2++)
            anIntArray1312[k2 + 192] = 0xffffff;

        anIntArray1313 = new int[256];
        for (int l2 = 0; l2 < 64; l2++)
            anIntArray1313[l2] = l2 * 4;

        for (int i3 = 0; i3 < 64; i3++)
            anIntArray1313[i3 + 64] = 255 + 0x40000 * i3;

        for (int j3 = 0; j3 < 64; j3++)
            anIntArray1313[j3 + 128] = 0xff00ff + 1024 * j3;

        for (int k3 = 0; k3 < 64; k3++)
            anIntArray1313[k3 + 192] = 0xffffff;

        anIntArray1310 = new int[256];
        anIntArray1176 = new int[32768];
        anIntArray1177 = new int[32768];
        method83(null, 0);
        anIntArray1084 = new int[32768];
        anIntArray1085 = new int[32768];
        drawLoadingText(10, "Connecting to fileserver");
        if (!aBoolean1243) {
            aBoolean1314 = true;
            aBoolean1243 = true;
            startRunnable(this, 2);
        }
    }

    private void removeFriend(long l) {
        try {
            if (l == 0L)
                return;
            for (int j = 0; j < friendsCount; j++) {
                if (friends[j] != l)
                    continue;
                friendsCount--;
                redrawTabArea = true;
                for (int k = j; k < friendsCount; k++) {
                    friendUsernames[k] = friendUsernames[k + 1];
                    friendWorlds[k] = friendWorlds[k + 1];
                    friends[k] = friends[k + 1];
                }

                outBuffer.putOpcode(141);
                outBuffer.putLongBE(l);
                break;
            }

        } catch (RuntimeException runtimeexception) {
            SignLink.reportError("38799, " + l + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private void processMenuClick() {
        if (activeInterfaceType != 0)
            return;
        int meta = super.clickType;
        if (widgetSelected == 1 && super.clickX >= 516 && super.clickY >= 160 && super.clickX <= 765
                && super.clickY <= 205)
            meta = 0;
        if (menuOpen) {
            if (meta != 1) {
                int x = super.mouseX;
                int y = super.mouseY;
                if (menuScreenArea == 0) {
                    x -= 4;
                    y -= 4;
                }
                if (menuScreenArea == 1) {
                    x -= 553;
                    y -= 205;
                }
                if (menuScreenArea == 2) {
                    x -= 17;
                    y -= 357;
                }
                if (x < menuOffsetX - 10 || x > menuOffsetX + anInt1307 + 10 || y < menuOffsetY - 10
                        || y > menuOffsetY + anInt1308 + 10) {
                    menuOpen = false;
                    if (menuScreenArea == 1)
                        redrawTabArea = true;
                    if (menuScreenArea == 2)
                        redrawChatbox = true;
                }
            }
            if (meta == 1) {
                int menuX = menuOffsetX;
                int menuY = menuOffsetY;
                int dx = anInt1307;
                int x = super.clickX;
                int y = super.clickY;
                if (menuScreenArea == 0) {
                    x -= 4;
                    y -= 4;
                }
                if (menuScreenArea == 1) {
                    x -= 553;
                    y -= 205;
                }
                if (menuScreenArea == 2) {
                    x -= 17;
                    y -= 357;
                }
                int id = -1;
                for (int row = 0; row < menuActionRow; row++) {
                    int k3 = menuY + 31 + (menuActionRow - 1 - row) * 15;
                    if (x > menuX && x < menuX + dx && y > k3 - 13 && y < k3 + 3)
                        id = row;
                }

                if (id != -1)
                    processMenuActions(id);
                menuOpen = false;
                if (menuScreenArea == 1)
                    redrawTabArea = true;
                if (menuScreenArea == 2) {
                    redrawChatbox = true;
                }
            }
        } else {
            if (meta == 1 && menuActionRow > 0) {
                int action = menuActionTypes[menuActionRow - 1];
                if (action == 9 || action == 225 || action == 444 || action == 564 || action == 894 || action == 961 || action == 399 || action == 324
                        || action == 227 || action == 891 || action == 52 || action == Actions.EXAMINE_ITEM) {
                    int item = firstMenuOperand[menuActionRow - 1];
                    int id = secondMenuOperand[menuActionRow - 1];
                    Widget widget = Widget.forId(id);
                    if (widget.itemSwapable || widget.itemDeletesDraged) {
                        aBoolean1155 = false;
                        anInt1269 = 0;
                        modifiedWidgetId = id;
                        selectedInventorySlot = item;
                        activeInterfaceType = 2;
                        anInt1114 = super.clickX;
                        anInt1115 = super.clickY;
                        if (Widget.forId(id).parentId == openInterfaceId)
                            activeInterfaceType = 1;
                        if (Widget.forId(id).parentId == backDialogueId)
                            activeInterfaceType = 3;
                        return;
                    }
                }
            }
            if (meta == 1 && (anInt1300 == 1 || menuHasAddFriend(menuActionRow - 1, aByte1161)) && menuActionRow > 2)
                meta = 2;
            if (meta == 1 && menuActionRow > 0)
                processMenuActions(menuActionRow - 1);
            if (meta == 2 && menuActionRow > 0)
                determineMenuSize();
        }
    }

    private void drawMinimap(ImageRGB sprite, int x, int y) {
        int r = x * x + y * y;

        if (r > 4225 && r < 0x15f90) {
            int theta = cameraHorizontal + anInt916 & 0x7ff;
            int sin = Model.SINE[theta];
            int cos = Model.COSINE[theta];
            sin = (sin * 256) / (anInt1233 + 256);
            cos = (cos * 256) / (anInt1233 + 256);
            int l1 = y * sin + x * cos >> 16;
            int i2 = y * cos - x * sin >> 16;
            double d = Math.atan2(l1, i2);
            int j2 = (int) (Math.sin(d) * 63D);
            int k2 = (int) (Math.cos(d) * 57D);
            minimapEdge.method466(256, 15, (94 + j2 + 4) - 10, 15, 20, anInt1119, 20, d, 83 - k2 - 20);
        } else {
            drawOnMinimap(sprite, x, y);
        }
    }

    private void method56(boolean flag, int i, int j, int k, int l, int i1) {
        scrollbarUp.drawImage(j, i1);
        scrollbarDown.drawImage(j, (i1 + k) - 16);
        Rasterizer.drawFilledRectangle(j, i1 + 16, 16, k - 32, anInt931);
        int j1 = ((k - 32) * k) / l;
        if (j1 < 8)
            j1 = 8;
        int k1 = ((k - 32 - j1) * i) / (l - k);
        Rasterizer.drawFilledRectangle(j, i1 + 16 + k1, 16, j1, anInt1080);
        Rasterizer.drawVerticalLine(j, i1 + 16 + k1, j1, anInt1135);
        Rasterizer.drawVerticalLine(j + 1, i1 + 16 + k1, j1, anInt1135);
        if (!flag)
            anInt921 = -136;
        Rasterizer.drawHorizontalLine(j, i1 + 16 + k1, 16, anInt1135);
        Rasterizer.drawHorizontalLine(j, i1 + 17 + k1, 16, anInt1135);
        Rasterizer.drawVerticalLine(j + 15, i1 + 16 + k1, j1, anInt1287);
        Rasterizer.drawVerticalLine(j + 14, i1 + 17 + k1, j1 - 1, anInt1287);
        Rasterizer.drawHorizontalLine(j, i1 + 15 + k1 + j1, 16, anInt1287);
        Rasterizer.drawHorizontalLine(j + 1, i1 + 14 + k1 + j1, 15, anInt1287);
    }

    private void renderNPCs(int i, boolean flag) {
        i = 26 / i;
        for (int j = 0; j < anInt1133; j++) {
            Npc class50_sub1_sub4_sub3_sub1 = npcs[anIntArray1134[j]];
            int k = 0x20000000 + (anIntArray1134[j] << 14);
            if (class50_sub1_sub4_sub3_sub1 == null || !class50_sub1_sub4_sub3_sub1.isVisible()
                    || class50_sub1_sub4_sub3_sub1.npcDefinition.visible != flag
                    || !class50_sub1_sub4_sub3_sub1.npcDefinition.method360())
                continue;
            int l = class50_sub1_sub4_sub3_sub1.worldX >> 7;
            int i1 = class50_sub1_sub4_sub3_sub1.worldY >> 7;
            if (l < 0 || l >= 104 || i1 < 0 || i1 >= 104)
                continue;
            if (class50_sub1_sub4_sub3_sub1.boundaryDimension == 1
                    && (class50_sub1_sub4_sub3_sub1.worldX & 0x7f) == 64
                    && (class50_sub1_sub4_sub3_sub1.worldY & 0x7f) == 64) {
                if (anIntArrayArray886[l][i1] == renderCount)
                    continue;
                anIntArrayArray886[l][i1] = renderCount;
            }
            if (!class50_sub1_sub4_sub3_sub1.npcDefinition.clickable)
                k += 0x80000000;
            currentScene.addEntity(plane, class50_sub1_sub4_sub3_sub1.worldX, class50_sub1_sub4_sub3_sub1.worldY, getFloorDrawHeight(
                    plane, class50_sub1_sub4_sub3_sub1.worldX, class50_sub1_sub4_sub3_sub1.worldY
            ), class50_sub1_sub4_sub3_sub1, k,
                    (class50_sub1_sub4_sub3_sub1.boundaryDimension - 1) * 64 + 60, class50_sub1_sub4_sub3_sub1.aBoolean1592,
                    class50_sub1_sub4_sub3_sub1.anInt1612);
        }

    }

    private void setWaveVolume(int j) {
        SignLink.waveVolume = j;
    }

    private void dropClient() {
        if (anInt873 > 0) {
            logout();
            return;
        }
        method125("Please wait - attempting to reestablish", "Connection lost");
        minimapState = 0;
        destinationX = 0;
        BufferedConnection class17 = gameConnection;
        loggedIn = false;
        anInt850 = 0;
        login(username, password, true);
        if (!loggedIn)
            logout();
        try {
            class17.close();
        } catch (Exception _ex) {
        }
    }

    private boolean handleWidgetDynamicAction(Widget widget) {
        int type = widget.contentType;
        if (friendListStatus == 2) {
            if (type == 201) {
                redrawChatbox = true;
                inputType = 0;
                messagePromptRaised = true;
                chatMessage = "";
                friendsListAction = 1;
                chatboxInputMessage = "Enter name of friend to add to list";
            }
            if (type == 202) {
                redrawChatbox = true;
                inputType = 0;
                messagePromptRaised = true;
                chatMessage = "";
                friendsListAction = 2;
                chatboxInputMessage = "Enter name of friend to delete from list";
            }
        }
        if (type == 205) {
            anInt873 = 250;
            return true;
        }
        if (type == 501) {
            redrawChatbox = true;
            inputType = 0;
            messagePromptRaised = true;
            chatMessage = "";
            friendsListAction = 4;
            chatboxInputMessage = "Enter name of player to add to list";
        }
        if (type == 502) {
            redrawChatbox = true;
            inputType = 0;
            messagePromptRaised = true;
            chatMessage = "";
            friendsListAction = 5;
            chatboxInputMessage = "Enter name of player to delete from list";
        }
        if (type >= 300 && type <= 313) {
            int k = (type - 300) / 2;
            int j1 = type & 1;
            int i2 = characterEditIdentityKits[k];
            if (i2 != -1) {
                do {
                    if (j1 == 0 && --i2 < 0)
                        i2 = IdentityKit.count - 1;
                    if (j1 == 1 && ++i2 >= IdentityKit.count)
                        i2 = 0;
                } while (IdentityKit.cache[i2].widgetDisplayed
                        || IdentityKit.cache[i2].partId != k + (characterEditChangeGenger ? 0 : 7));
                characterEditIdentityKits[k] = i2;
                aBoolean1277 = true;
            }
        }
        if (type >= 314 && type <= 323) {
            int l = (type - 314) / 2;
            int k1 = type & 1;
            int j2 = characterEditColors[l];
            if (k1 == 0 && --j2 < 0)
                j2 = playerColours[l].length - 1;
            if (k1 == 1 && ++j2 >= playerColours[l].length)
                j2 = 0;
            characterEditColors[l] = j2;
            aBoolean1277 = true;
        }
        if (type == 324 && !characterEditChangeGenger) {
            characterEditChangeGenger = true;
            method25();
        }
        if (type == 325 && characterEditChangeGenger) {
            characterEditChangeGenger = false;
            method25();
        }
        if (type == 326) {
            outBuffer.putOpcode(163);
            outBuffer.putByte(characterEditChangeGenger ? 0 : 1);
            for (int i1 = 0; i1 < 7; i1++)
                outBuffer.putByte(characterEditIdentityKits[i1]);

            for (int l1 = 0; l1 < 5; l1++)
                outBuffer.putByte(characterEditColors[l1]);

            return true;
        }
        if (type == 620)
            reportMutePlayer = !reportMutePlayer;
        if (type >= 601 && type <= 613) {
            closeWidgets();
            if (reportedName.length() > 0) {
                outBuffer.putOpcode(184);
                outBuffer.putLongBE(TextUtils.nameToLong(reportedName));
                outBuffer.putByte(type - 601);
                outBuffer.putByte(reportMutePlayer ? 1 : 0);
            }
        }
        return false;
    }

    private Archive requestArchive(int id, String file, int expectedCrc, int x, String displayName) {
        byte[] archiveBuffer = null;
        int reconnectionDelay = 5;

        try {
            if (stores[0] != null)
                archiveBuffer = stores[0].get(id);
        } catch (Exception ignored) {
        }

        if (archiveBuffer != null && Configuration.JAGGRAB_ENABLED) {
            archiveCrc.reset();
            archiveCrc.update(archiveBuffer);

            int calculatedCrc = (int) archiveCrc.getValue();

            if (calculatedCrc != expectedCrc)
                archiveBuffer = null;
        }

        if (archiveBuffer != null)
            return new Archive(archiveBuffer);

        int attempts = 0;

        while (archiveBuffer == null) {
            String error = "Unknown error";

            drawLoadingText(x, "Requesting " + displayName);

            try {
                int currentPercentage = 0;
                DataInputStream jaggrabStream = openJaggrabStream(file + expectedCrc);
                byte[] bytes = new byte[6];

                jaggrabStream.readFully(bytes, 0, 6);

                Buffer buffer = new Buffer(bytes);
                buffer.currentPosition = 3;
                int archiveLength = buffer.getMediumBE() + 6;
                int archiveRead = 6;
                archiveBuffer = new byte[archiveLength];

                System.arraycopy(bytes, 0, archiveBuffer, 0, 6);

                while (archiveRead < archiveLength) {
                    int remaining = archiveLength - archiveRead;

                    if (remaining > 1000)
                        remaining = 1000;

                    int read = jaggrabStream.read(archiveBuffer, archiveRead, remaining);

                    if (read < 0) {
                        error = "Length error: " + archiveRead + "/" + archiveLength;
                        throw new IOException("EOF");
                    }

                    archiveRead += read;
                    int calculatedPercentage = (archiveRead * 100) / archiveLength;

                    if (calculatedPercentage != currentPercentage)
                        drawLoadingText(x, "Loading " + displayName + " - " + calculatedPercentage + "%");

                    currentPercentage = calculatedPercentage;
                }

                jaggrabStream.close();

                try {
                    if (stores[0] != null)
                        stores[0].put(archiveBuffer.length, archiveBuffer, id);
                } catch (Exception _ex) {
                    stores[0] = null;
                }

                if (Configuration.JAGGRAB_ENABLED) {
                    archiveCrc.reset();
                    archiveCrc.update(archiveBuffer);

                    int calculatedCrc = (int) archiveCrc.getValue();

                    if (calculatedCrc != expectedCrc) {
                        archiveBuffer = null;
                        attempts++;
                        error = "Checksum error: " + calculatedCrc;
                    }
                }
            } catch (IOException ex) {
                if (error.equals("Unknown error"))
                    error = "Connection error";

                archiveBuffer = null;
            } catch (NullPointerException ex) {
                error = "Null error";
                archiveBuffer = null;

                if (!SignLink.reportError)
                    return null;
            } catch (ArrayIndexOutOfBoundsException ex) {
                error = "Bounds error";
                archiveBuffer = null;

                if (!SignLink.reportError)
                    return null;
            } catch (Exception ex) {
                error = "Unexpected error";
                archiveBuffer = null;

                if (!SignLink.reportError)
                    return null;
            }

            if (archiveBuffer == null) {
                for (int time = reconnectionDelay; time > 0; time--) {
                    if (attempts >= 3) {
                        drawLoadingText(x, "Game updated - please reload page");
                        time = 10;
                    } else {
                        drawLoadingText(x, error + " - Retrying in " + time);
                    }

                    try {
                        Thread.sleep(1000L);
                    } catch (Exception ignored) {
                    }
                }

                reconnectionDelay *= 2;

                if (reconnectionDelay > 60)
                    reconnectionDelay = 60;

                useJaggrab = !useJaggrab;
            }
        }

        return new Archive(archiveBuffer);
    }

    public void redraw() {
        aBoolean1046 = true;
    }

    private void parseNpcUpdateMasks(Buffer class50_sub1_sub2, int i, int j) {
        j = 24 / j;
        for (int k = 0; k < updatedPlayerCount; k++) {
            int l = updatedPlayers[k];
            Npc class50_sub1_sub4_sub3_sub1 = npcs[l];
            int i1 = class50_sub1_sub2.getUnsignedByte();
            if ((i1 & 1) != 0) {
                class50_sub1_sub4_sub3_sub1.npcDefinition = ActorDefinition.getDefinition(class50_sub1_sub2.getUnsignedNegativeOffsetShortBE());
                class50_sub1_sub4_sub3_sub1.boundaryDimension = class50_sub1_sub4_sub3_sub1.npcDefinition.boundaryDimension;
                class50_sub1_sub4_sub3_sub1.anInt1600 = class50_sub1_sub4_sub3_sub1.npcDefinition.degreesToTurn;
                class50_sub1_sub4_sub3_sub1.walkAnimationId = class50_sub1_sub4_sub3_sub1.npcDefinition.walkAnimationId;
                class50_sub1_sub4_sub3_sub1.turnAroundAnimationId = class50_sub1_sub4_sub3_sub1.npcDefinition.turnAroundAnimationId;
                class50_sub1_sub4_sub3_sub1.turnRightAnimationId = class50_sub1_sub4_sub3_sub1.npcDefinition.turnRightAnimationId;
                class50_sub1_sub4_sub3_sub1.turnLeftAnimationId = class50_sub1_sub4_sub3_sub1.npcDefinition.turnLeftAnimationId;
                class50_sub1_sub4_sub3_sub1.idleAnimation = class50_sub1_sub4_sub3_sub1.npcDefinition.standAnimationId;
            }
            if ((i1 & 0x40) != 0) {
                class50_sub1_sub4_sub3_sub1.anInt1609 = class50_sub1_sub2.getUnsignedShortLE();
                if (class50_sub1_sub4_sub3_sub1.anInt1609 == 65535)
                    class50_sub1_sub4_sub3_sub1.anInt1609 = -1;
            }
            if ((i1 & 0x80) != 0) {
                int j1 = class50_sub1_sub2.getUnsignedPostNegativeOffsetByte();
                int j2 = class50_sub1_sub2.getUnsignedPostNegativeOffsetByte();
                class50_sub1_sub4_sub3_sub1.updateHits(j2, j1, pulseCycle);
                class50_sub1_sub4_sub3_sub1.endCycle = pulseCycle + 300;
                class50_sub1_sub4_sub3_sub1.anInt1596 = class50_sub1_sub2.getUnsignedByte();
                class50_sub1_sub4_sub3_sub1.anInt1597 = class50_sub1_sub2.getUnsignedPreNegativeOffsetByte();
            }
            if ((i1 & 4) != 0) {
                class50_sub1_sub4_sub3_sub1.graphic = class50_sub1_sub2.getUnsignedShortBE();
                int k1 = class50_sub1_sub2.getIntME1();
                class50_sub1_sub4_sub3_sub1.spotAnimationDelay = k1 >> 16;
                class50_sub1_sub4_sub3_sub1.anInt1617 = pulseCycle + (k1 & 0xffff);
                class50_sub1_sub4_sub3_sub1.currentAnimation = 0;
                class50_sub1_sub4_sub3_sub1.anInt1616 = 0;
                if (class50_sub1_sub4_sub3_sub1.anInt1617 > pulseCycle)
                    class50_sub1_sub4_sub3_sub1.currentAnimation = -1;
                if (class50_sub1_sub4_sub3_sub1.graphic == 65535)
                    class50_sub1_sub4_sub3_sub1.graphic = -1;
            }
            if ((i1 & 0x20) != 0) {
                class50_sub1_sub4_sub3_sub1.forcedChat = class50_sub1_sub2.getString();
                class50_sub1_sub4_sub3_sub1.textCycle = 100;
            }
            if ((i1 & 8) != 0) {
                class50_sub1_sub4_sub3_sub1.anInt1598 = class50_sub1_sub2.getUnsignedNegativeOffsetShortLE();
                class50_sub1_sub4_sub3_sub1.anInt1599 = class50_sub1_sub2.getUnsignedShortLE();
            }
            if ((i1 & 2) != 0) {
                int l1 = class50_sub1_sub2.getUnsignedShortBE();
                if (l1 == 65535)
                    l1 = -1;
                int k2 = class50_sub1_sub2.getUnsignedPreNegativeOffsetByte();
                if (l1 == class50_sub1_sub4_sub3_sub1.emoteAnimation && l1 != -1) {
                    int i3 = AnimationSequence.animations[l1].anInt307;
                    if (i3 == 1) {
                        class50_sub1_sub4_sub3_sub1.displayedEmoteFrames = 0;
                        class50_sub1_sub4_sub3_sub1.anInt1626 = 0;
                        class50_sub1_sub4_sub3_sub1.animationDelay = k2;
                        class50_sub1_sub4_sub3_sub1.anInt1628 = 0;
                    }
                    if (i3 == 2)
                        class50_sub1_sub4_sub3_sub1.anInt1628 = 0;
                } else if (l1 == -1
                        || class50_sub1_sub4_sub3_sub1.emoteAnimation == -1
                        || AnimationSequence.animations[l1].anInt301 >= AnimationSequence.animations[class50_sub1_sub4_sub3_sub1.emoteAnimation].anInt301) {
                    class50_sub1_sub4_sub3_sub1.emoteAnimation = l1;
                    class50_sub1_sub4_sub3_sub1.displayedEmoteFrames = 0;
                    class50_sub1_sub4_sub3_sub1.anInt1626 = 0;
                    class50_sub1_sub4_sub3_sub1.animationDelay = k2;
                    class50_sub1_sub4_sub3_sub1.anInt1628 = 0;
                    class50_sub1_sub4_sub3_sub1.anInt1613 = class50_sub1_sub4_sub3_sub1.pathLength;
                }
            }
            if ((i1 & 0x10) != 0) {
                int i2 = class50_sub1_sub2.getUnsignedPreNegativeOffsetByte();
                int l2 = class50_sub1_sub2.getUnsignedPreNegativeOffsetByte();
                class50_sub1_sub4_sub3_sub1.updateHits(l2, i2, pulseCycle);
                class50_sub1_sub4_sub3_sub1.endCycle = pulseCycle + 300;
                class50_sub1_sub4_sub3_sub1.anInt1596 = class50_sub1_sub2.getUnsignedByte();
                class50_sub1_sub4_sub3_sub1.anInt1597 = class50_sub1_sub2.getUnsignedInvertedByte();
            }
        }

    }

    private void parsePlayerBlock(int id, Player player, int mask, Buffer buffer) {
        if ((mask & 8) != 0) {
            int animation = buffer.getUnsignedShortBE();

            if (animation == 65535)
                animation = -1;

            int delay = buffer.getUnsignedPreNegativeOffsetByte();

            if (animation == player.emoteAnimation && animation != -1) {
                int mode = AnimationSequence.animations[animation].anInt307;

                if (mode == 1) {
                    player.displayedEmoteFrames = 0;
                    player.anInt1626 = 0;
                    player.animationDelay = delay;
                    player.anInt1628 = 0;
                }

                if (mode == 2)
                    player.anInt1628 = 0;
            } else if (animation == -1 || player.emoteAnimation == -1
                    || AnimationSequence.animations[animation].anInt301 >= AnimationSequence.animations[player.emoteAnimation].anInt301) {
                player.emoteAnimation = animation;
                player.displayedEmoteFrames = 0;
                player.anInt1626 = 0;
                player.animationDelay = delay;
                player.anInt1628 = 0;
                player.anInt1613 = player.pathLength;
            }
        }

        if ((mask & 0x10) != 0) {
            player.forcedChat = buffer.getString();

            if (player.forcedChat.charAt(0) == '~') {
                player.forcedChat = player.forcedChat.substring(1);
                addChatMessage(player.playerName, player.forcedChat, 2);
            } else if (player == localPlayer) {
                addChatMessage(player.playerName, player.forcedChat, 2);
            }

            player.textColour = 0;
            player.textEffect = 0;
            player.textCycle = 150;
        }

        if ((mask & 0x100) != 0) {
            player.anInt1602 = buffer.getUnsignedPostNegativeOffsetByte();
            player.anInt1604 = buffer.getUnsignedInvertedByte();
            player.anInt1603 = buffer.getUnsignedPreNegativeOffsetByte();
            player.anInt1605 = buffer.getUnsignedByte();
            player.anInt1606 = buffer.getUnsignedShortBE() + pulseCycle;
            player.anInt1607 = buffer.getUnsignedNegativeOffsetShortBE() + pulseCycle;
            player.anInt1608 = buffer.getUnsignedByte();

            player.resetPath();
        }

        if ((mask & 1) != 0) {
            player.anInt1609 = buffer.getUnsignedNegativeOffsetShortBE();

            if (player.anInt1609 == 65535)
                player.anInt1609 = -1;
        }

        if ((mask & 2) != 0) {
            player.anInt1598 = buffer.getUnsignedShortBE();
            player.anInt1599 = buffer.getUnsignedShortBE();
        }

        if ((mask & 0x200) != 0) {
            player.graphic = buffer.getUnsignedNegativeOffsetShortBE();
            int heightAndDelay = buffer.getIntME1();
            player.spotAnimationDelay = heightAndDelay >> 16;
            player.anInt1617 = pulseCycle + (heightAndDelay & 0xffff);
            player.currentAnimation = 0;
            player.anInt1616 = 0;

            if (player.anInt1617 > pulseCycle)
                player.currentAnimation = -1;

            if (player.graphic == 65535)
                player.graphic = -1;
        }

        if ((mask & 4) != 0) {
            int size = buffer.getUnsignedByte();
            byte bytes[] = new byte[size];
            Buffer appearance = new Buffer(bytes);

            buffer.getBytesReverse(bytes, 0, size);

            cachedAppearances[id] = appearance;

            player.updateAppearance(appearance);
        }

        if ((mask & 0x400) != 0) {
            int damage = buffer.getUnsignedPostNegativeOffsetByte();
            int type = buffer.getUnsignedPreNegativeOffsetByte();

            player.updateHits(type, damage, pulseCycle);

            player.endCycle = pulseCycle + 300;
            player.anInt1596 = buffer.getUnsignedInvertedByte();
            player.anInt1597 = buffer.getUnsignedByte();
        }

        if ((mask & 0x40) != 0) {
            int effectsAndColour = buffer.getUnsignedShortBE();
            int rights = buffer.getUnsignedInvertedByte();
            int length = buffer.getUnsignedPostNegativeOffsetByte();
            int currentPosition = buffer.currentPosition;

            if (player.playerName != null && player.visible) {
                long nameLong = TextUtils.nameToLong(player.playerName);
                boolean ignored = false;

                if (rights <= 1) {
                    for (int i = 0; i < ignoresCount; i++) {
                        if (ignores[i] != nameLong)
                            continue;

                        ignored = true;
                        break;
                    }

                }

                if (!ignored && !inTutorialIsland) {
                    try {
                        chatBuffer.currentPosition = 0;

                        buffer.getBytesAdded(chatBuffer.buffer, 0, length);

                        chatBuffer.currentPosition = 0;
                        String message = ChatCensor.censorString(ChatEncoder.get(length, chatBuffer));
                        player.forcedChat = message;
                        player.textColour = effectsAndColour >> 8;
                        player.textEffect = effectsAndColour & 0xff;
                        player.textCycle = 150;

                        if (rights == 2 || rights == 3)
                            addChatMessage("@cr2@" + player.playerName, message, 1);
                        else if (rights == 1)
                            addChatMessage("@cr1@" + player.playerName, message, 1);
                        else
                            addChatMessage(player.playerName, message, 2);
                    } catch (Exception exception) {
                        SignLink.reportError("cde2");
                    }
                }
            }

            buffer.currentPosition = currentPosition + length;
        }

        if ((mask & 0x80) != 0) {
            int damage = buffer.getUnsignedPreNegativeOffsetByte();
            int type = buffer.getUnsignedInvertedByte();

            player.updateHits(type, damage, pulseCycle);

            player.endCycle = pulseCycle + 300;
            player.anInt1596 = buffer.getUnsignedPreNegativeOffsetByte();
            player.anInt1597 = buffer.getUnsignedByte();
        }
    }

    private void resetTitleScreen() {
        if (aClass18_1198 != null)
            return;

        super.imageProducer = null;
        chatboxProducingGraphicsBuffer = null;
        aClass18_1157 = null;
        tabImageProducer = null;
        gameScreenImageProducer = null;
        aClass18_1108 = null;
        aClass18_1109 = null;

        flameLeftBackground = new ProducingGraphicsBuffer(128, 265, getParentComponent());
        Rasterizer.resetPixels();

        flameRightBackground = new ProducingGraphicsBuffer(128, 265, getParentComponent());
        Rasterizer.resetPixels();

        aClass18_1198 = new ProducingGraphicsBuffer(509, 171, getParentComponent());
        Rasterizer.resetPixels();

        aClass18_1199 = new ProducingGraphicsBuffer(360, 132, getParentComponent());
        Rasterizer.resetPixels();

        aClass18_1200 = new ProducingGraphicsBuffer(360, 200, getParentComponent());
        Rasterizer.resetPixels();

        aClass18_1203 = new ProducingGraphicsBuffer(202, 238, getParentComponent());
        Rasterizer.resetPixels();

        aClass18_1204 = new ProducingGraphicsBuffer(203, 238, getParentComponent());
        Rasterizer.resetPixels();

        aClass18_1205 = new ProducingGraphicsBuffer(74, 94, getParentComponent());
        Rasterizer.resetPixels();

        aClass18_1206 = new ProducingGraphicsBuffer(75, 94, getParentComponent());
        Rasterizer.resetPixels();

        if (titleArchive != null) {
            prepareTitleBackground();
            prepareTitle();
        }

        aBoolean1046 = true;
    }

    public void startup() {
        drawLoadingText(20, "Starting up");

        if (SignLink.cacheData != null) {
            for (int type = 0; type < 5; type++)
                stores[type] = new Index(type + 1, 0x927c0, SignLink.cacheData, SignLink.cacheIndex[type]);
        }

        try {
            if (Configuration.JAGGRAB_ENABLED)
                requestArchiveCrcs();

            if (USE_STATIC_DETAILS) {
                username = USERNAME;
                password = PASSWORD;
            } else {
                username = "";
                password = "";
            }
            titleArchive = requestArchive(1, "title", archiveHashes[1], 25, "title screen");
            fontSmall = new TypeFace(false, titleArchive, "p11_full");
            fontNormal = new TypeFace(false, titleArchive, "p12_full");
            fontBold = new TypeFace(false, titleArchive, "b12_full");
            fontFancy = new TypeFace(true, titleArchive, "q8_full");

            prepareTitleBackground();
            prepareTitle();

            Archive configArchive = requestArchive(2, "config", archiveHashes[2], 30, "config");
            Archive archiveInterface = requestArchive(3, "interface", archiveHashes[3], 35, "interface");
            Archive archiveMedia = requestArchive(4, "media", archiveHashes[4], 40, "2d gameGraphics");
            Archive textureArchive = requestArchive(6, "textures", archiveHashes[6], 45, "textures");
            Archive chatArchive = requestArchive(7, "wordenc", archiveHashes[7], 50, "chat system");
            Archive soundArchive = requestArchive(8, "sounds", archiveHashes[8], 55, "sound effects");
            currentSceneTileFlags = new byte[4][104][104];
            intGroundArray = new int[4][105][105];
            currentScene = new Scene(intGroundArray);

            for (int j = 0; j < 4; j++)
                currentCollisionMap[j] = new CollisionMap(104, 104);

            minimapImage = new ImageRGB(512, 512);
            Archive versionListArchive = requestArchive(5, "versionlist", archiveHashes[5], 60, "update list");

            drawLoadingText(60, "Connecting to update server");

            onDemandRequester = new OnDemandRequester();
            onDemandRequester.init(versionListArchive, this);

            Animation.method235(onDemandRequester.animCount());
            Model.init(onDemandRequester.fileCount(0), onDemandRequester);

            if (!lowMemory) {
                nextSong = 0;

//                try {
//                    nextSong = Integer.parseInt(getParameter("music"));
//                } catch (Exception ignored) {
//                }

                songChanging = true;

                onDemandRequester.request(2, nextSong);

                while (onDemandRequester.method333() > 0) {
                    method77(false);

                    try {
                        Thread.sleep(100L);
                    } catch (Exception ignored) {
                    }

                    if (onDemandRequester.requestFails > 3) {
                        method19("ondemand");
                        return;
                    }
                }
            }

            drawLoadingText(65, "Requesting animations");

            int fileRequestCount = onDemandRequester.fileCount(1);

            for (int i = 0; i < fileRequestCount; i++)
                onDemandRequester.request(1, i);

            while (onDemandRequester.method333() > 0) {
                int total = fileRequestCount - onDemandRequester.method333();

                if (total > 0)
                    drawLoadingText(65, "Loading animations - " + (total * 100) / fileRequestCount + "%");

                method77(false);

                try {
                    Thread.sleep(100L);
                } catch (Exception ignored) {
                }

                if (onDemandRequester.requestFails > 3) {
                    method19("ondemand");
                    return;
                }
            }

            drawLoadingText(70, "Requesting models");

            fileRequestCount = onDemandRequester.fileCount(0);

            for (int i = 0; i < fileRequestCount; i++) {
                int id = onDemandRequester.modelId(i);

                if ((id & 1) != 0)
                    onDemandRequester.request(0, i);
            }

            fileRequestCount = onDemandRequester.method333();

            while (onDemandRequester.method333() > 0) {
                int total = fileRequestCount - onDemandRequester.method333();

                if (total > 0)
                    drawLoadingText(70, "Loading models - " + (total * 100) / fileRequestCount + "%");

                method77(false);

                try {
                    Thread.sleep(100L);
                } catch (Exception ignored) {
                }
            }

            if (stores[0] != null) {
                drawLoadingText(75, "Requesting maps");
                onDemandRequester.request(3, onDemandRequester.regId(0, 47, 48, 0)); // these are the maps around tutorial island
                onDemandRequester.request(3, onDemandRequester.regId(0, 47, 48, 1));
                onDemandRequester.request(3, onDemandRequester.regId(0, 48, 48, 0));
                onDemandRequester.request(3, onDemandRequester.regId(0, 48, 48, 1));
                onDemandRequester.request(3, onDemandRequester.regId(0, 49, 48, 0));
                onDemandRequester.request(3, onDemandRequester.regId(0, 49, 48, 1));
                onDemandRequester.request(3, onDemandRequester.regId(0, 47, 47, 0));
                onDemandRequester.request(3, onDemandRequester.regId(0, 47, 47, 1));
                onDemandRequester.request(3, onDemandRequester.regId(0, 48, 47, 0));
                onDemandRequester.request(3, onDemandRequester.regId(0, 48, 47, 1));
                onDemandRequester.request(3, onDemandRequester.regId(0, 48, 148, 0));
                onDemandRequester.request(3, onDemandRequester.regId(0, 48, 148, 1));

                fileRequestCount = onDemandRequester.method333();

                while (onDemandRequester.method333() > 0) {
                    int total = fileRequestCount - onDemandRequester.method333();

                    if (total > 0)
                        drawLoadingText(75, "Loading maps - " + (total * 100) / fileRequestCount + "%");

                    method77(false);

                    try {
                        Thread.sleep(100L);
                    } catch (Exception ignored) {
                    }
                }
            }

            fileRequestCount = onDemandRequester.fileCount(0);

            for (int i = 0; i < fileRequestCount; i++) {
                int id = onDemandRequester.modelId(i);
                byte priority = 0;

                if ((id & 8) != 0)
                    priority = 10;
                else if ((id & 0x20) != 0)
                    priority = 9;
                else if ((id & 0x10) != 0)
                    priority = 8;
                else if ((id & 0x40) != 0)
                    priority = 7;
                else if ((id & 0x80) != 0)
                    priority = 6;
                else if ((id & 2) != 0)
                    priority = 5;
                else if ((id & 4) != 0)
                    priority = 4;
                if ((id & 1) != 0)
                    priority = 3;

                if (priority != 0)
                    onDemandRequester.setPriority(priority, 0, i);
            }

            onDemandRequester.preloadRegions(memberServer);

            if (!lowMemory) {
                fileRequestCount = onDemandRequester.fileCount(2);

                for (int i = 1; i < fileRequestCount; i++) {
                    if (onDemandRequester.midiIdEqualsOne(i))
                        onDemandRequester.setPriority((byte) 1, 2, i);
                }
            }

            fileRequestCount = onDemandRequester.fileCount(0);

            for (int i = 0; i < fileRequestCount; i++) {
                int id = onDemandRequester.modelId(i);

                if (id == 0 && onDemandRequester.anInt1350 < 200)
                    onDemandRequester.setPriority((byte) 1, 0, i);
            }

            drawLoadingText(80, "Unpacking media");

            inventoryBackgroundImage = new IndexedImage(archiveMedia, "invback", 0);
            chatboxBackgroundImage = new IndexedImage(archiveMedia, "chatback", 0);
            minimapBackgroundImage = new IndexedImage(archiveMedia, "mapback", 0);
            anIndexedImage1052 = new IndexedImage(archiveMedia, "backbase1", 0);
            anIndexedImage1053 = new IndexedImage(archiveMedia, "backbase2", 0);
            anIndexedImage1054 = new IndexedImage(archiveMedia, "backhmid1", 0);

            for (int i = 0; i < 13; i++)
                tabIcon[i] = new IndexedImage(archiveMedia, "sideicons", i);

            minimapCompass = new ImageRGB(archiveMedia, "compass", 0);
            minimapEdge = new ImageRGB(archiveMedia, "mapedge", 0);
            minimapEdge.trim();

            for (int i = 0; i < 72; i++)
                aClass50_Sub1_Sub1_Sub3Array1153[i] = new IndexedImage(archiveMedia, "mapscene", i);

            for (int i = 0; i < 70; i++)
                worldMapHintIcons[i] = new ImageRGB(archiveMedia, "mapfunction", i);

            for (int i = 0; i < 5; i++)
                aClass50_Sub1_Sub1_Sub1Array1182[i] = new ImageRGB(archiveMedia, "hitmarks", i);

            for (int i = 0; i < 6; i++)
                aClass50_Sub1_Sub1_Sub1Array1288[i] = new ImageRGB(archiveMedia, "headicons_pk", i);

            for (int i = 0; i < 9; i++)
                aClass50_Sub1_Sub1_Sub1Array1079[i] = new ImageRGB(archiveMedia, "headicons_prayer", i);

            for (int i = 0; i < 6; i++)
                aClass50_Sub1_Sub1_Sub1Array954[i] = new ImageRGB(archiveMedia, "headicons_hint", i);

            aClass50_Sub1_Sub1_Sub1_1086 = new ImageRGB(archiveMedia, "overlay_multiway", 0);
            mapFlagMarker = new ImageRGB(archiveMedia, "mapmarker", 0);
            aClass50_Sub1_Sub1_Sub1_1037 = new ImageRGB(archiveMedia, "mapmarker", 1);

            for (int i = 0; i < 8; i++)
                cursorCross[i] = new ImageRGB(archiveMedia, "cross", i);

            mapdotItem = new ImageRGB(archiveMedia, "mapdots", 0);
            mapdotActor = new ImageRGB(archiveMedia, "mapdots", 1);
            mapdotPlayer = new ImageRGB(archiveMedia, "mapdots", 2);
            mapdotFriend = new ImageRGB(archiveMedia, "mapdots", 3);
            mapdotTeammate = new ImageRGB(archiveMedia, "mapdots", 4);
            scrollbarUp = new IndexedImage(archiveMedia, "scrollbar", 0);
            scrollbarDown = new IndexedImage(archiveMedia, "scrollbar", 1);
            aClass50_Sub1_Sub1_Sub3_880 = new IndexedImage(archiveMedia, "redstone1", 0);
            aClass50_Sub1_Sub1_Sub3_881 = new IndexedImage(archiveMedia, "redstone2", 0);
            aClass50_Sub1_Sub1_Sub3_882 = new IndexedImage(archiveMedia, "redstone3", 0);
            aClass50_Sub1_Sub1_Sub3_883 = new IndexedImage(archiveMedia, "redstone1", 0);
            aClass50_Sub1_Sub1_Sub3_883.flipHorizontal();

            aClass50_Sub1_Sub1_Sub3_884 = new IndexedImage(archiveMedia, "redstone2", 0);
            aClass50_Sub1_Sub1_Sub3_884.flipHorizontal();

            aClass50_Sub1_Sub1_Sub3_983 = new IndexedImage(archiveMedia, "redstone1", 0);
            aClass50_Sub1_Sub1_Sub3_983.flipVertical();

            aClass50_Sub1_Sub1_Sub3_984 = new IndexedImage(archiveMedia, "redstone2", 0);
            aClass50_Sub1_Sub1_Sub3_984.flipVertical();

            aClass50_Sub1_Sub1_Sub3_985 = new IndexedImage(archiveMedia, "redstone3", 0);
            aClass50_Sub1_Sub1_Sub3_985.flipVertical();

            aClass50_Sub1_Sub1_Sub3_986 = new IndexedImage(archiveMedia, "redstone1", 0);
            aClass50_Sub1_Sub1_Sub3_986.flipHorizontal();
            aClass50_Sub1_Sub1_Sub3_986.flipVertical();

            aClass50_Sub1_Sub1_Sub3_987 = new IndexedImage(archiveMedia, "redstone2", 0);
            aClass50_Sub1_Sub1_Sub3_987.flipHorizontal();
            aClass50_Sub1_Sub1_Sub3_987.flipVertical();

            for (int i = 0; i < 2; i++)
                moderatorIcon[i] = new ImageRGB(archiveMedia, "mod_icons", i);

            ImageRGB image = new ImageRGB(archiveMedia, "backleft1", 0);
            aClass18_906 = new ProducingGraphicsBuffer(image.width, image.height, getParentComponent());
            image.drawInverse(0, 0);

            image = new ImageRGB(archiveMedia, "backleft2", 0);
            aClass18_907 = new ProducingGraphicsBuffer(image.width, image.height, getParentComponent());
            image.drawInverse(0, 0);

            image = new ImageRGB(archiveMedia, "backright1", 0);
            aClass18_908 = new ProducingGraphicsBuffer(image.width, image.height, getParentComponent());
            image.drawInverse(0, 0);

            image = new ImageRGB(archiveMedia, "backright2", 0);
            aClass18_909 = new ProducingGraphicsBuffer(image.width, image.height, getParentComponent());
            image.drawInverse(0, 0);

            image = new ImageRGB(archiveMedia, "backtop1", 0);
            aClass18_910 = new ProducingGraphicsBuffer(image.width, image.height, getParentComponent());
            image.drawInverse(0, 0);

            image = new ImageRGB(archiveMedia, "backvmid1", 0);
            aClass18_911 = new ProducingGraphicsBuffer(image.width, image.height, getParentComponent());
            image.drawInverse(0, 0);

            image = new ImageRGB(archiveMedia, "backvmid2", 0);
            aClass18_912 = new ProducingGraphicsBuffer(image.width, image.height, getParentComponent());
            image.drawInverse(0, 0);

            image = new ImageRGB(archiveMedia, "backvmid3", 0);
            aClass18_913 = new ProducingGraphicsBuffer(image.width, image.height, getParentComponent());
            image.drawInverse(0, 0);

            image = new ImageRGB(archiveMedia, "backhmid2", 0);
            aClass18_914 = new ProducingGraphicsBuffer(image.width, image.height, getParentComponent());
            image.drawInverse(0, 0);

            int offset = (int) (Math.random() * 41D) - 20;
            int red = (int) ((Math.random() * 21D) - 10) + offset;
            int green = (int) ((Math.random() * 21D) - 10) + offset;
            int blue = (int) ((Math.random() * 21D) - 10) + offset;

            for (int i = 0; i < 100; i++) {
                if (worldMapHintIcons[i] != null)
                    worldMapHintIcons[i].adjustRGB(red, green, blue);

                if (aClass50_Sub1_Sub1_Sub3Array1153[i] != null)
                    aClass50_Sub1_Sub1_Sub3Array1153[i].mixPalette(red, green, blue);
            }

            drawLoadingText(83, "Unpacking textures");
            Rasterizer3D.loadIndexedImages(textureArchive);
            Rasterizer3D.method501(0.80000000000000004D);
            Rasterizer3D.method496(20);
            drawLoadingText(86, "Unpacking config");
            AnimationSequence.load(configArchive);
            GameObjectDefinition.load(configArchive);
            FloorDefinition.load(configArchive);
            ItemDefinition.load(configArchive);
            ActorDefinition.load(configArchive);
            IdentityKit.load(configArchive);
            SpotAnimation.load(configArchive);
            Varp.load(configArchive);
            Varbit.load(configArchive);

            ItemDefinition.memberServer = memberServer;

            if (!lowMemory) {
                drawLoadingText(90, "Unpacking sounds");

                byte[] bytes = soundArchive.getFile("sounds.dat");
                Buffer buffer = new Buffer(bytes);

                SoundTrack.load(buffer);
            }

            drawLoadingText(95, "Unpacking interfaces");

            TypeFace[] typefaces = {fontSmall, fontNormal, fontBold, fontFancy};

            for(TypeFace typeFace: typefaces){
                typeFace.setNameIcons(moderatorIcon, (int[])null);
            }

            Widget.load(archiveInterface, typefaces, archiveMedia);
            drawLoadingText(100, "Preparing game engine");

            for (int y = 0; y < 33; y++) {
                int minWidth = 999;
                int maxWidth = 0;

                for (int x = 0; x < 34; x++) {
                    if (minimapBackgroundImage.pixels[x + y * minimapBackgroundImage.width] == 0) {
                        if (minWidth == 999)
                            minWidth = x;

                        continue;
                    }

                    if (minWidth == 999)
                        continue;

                    maxWidth = x;
                    break;
                }

                anIntArray1180[y] = minWidth;
                anIntArray1286[y] = maxWidth - minWidth;
            }

            for (int y = 5; y < 156; y++) {
                int minWidth = 999;
                int maxWidth = 0;

                for (int x = 25; x < 172; x++) {
                    if (minimapBackgroundImage.pixels[x + y * minimapBackgroundImage.width] == 0
                            && (x > 34 || y > 34)) {
                        if (minWidth == 999)
                            minWidth = x;

                        continue;
                    }

                    if (minWidth == 999)
                        continue;

                    maxWidth = x;
                    break;
                }

                anIntArray1019[y - 5] = minWidth - 25;
                anIntArray920[y - 5] = maxWidth - minWidth;
            }

            Rasterizer3D.method494(765, 503);
            anIntArray1003 = Rasterizer3D.lineOffsets;

            Rasterizer3D.method494(479, 96);
            chatboxLineOffsets = Rasterizer3D.lineOffsets;

            Rasterizer3D.method494(190, 261);
            sidebarOffsets = Rasterizer3D.lineOffsets;

            Rasterizer3D.method494(512, 334);
            viewportOffsets = Rasterizer3D.lineOffsets;

            int ai[] = new int[9];

            for (int i = 0; i < 9; i++) { //TODO: Needs refactoring
                int j9 = 128 + i * 32 + 15;
                int k9 = 600 + j9 * 3;
                int l9 = Rasterizer3D.SINE[j9];
                ai[i] = k9 * l9 >> 16;
            }

            Scene.method277(500, 800, 512, 334, ai);
            ChatCensor.load(chatArchive);

            mouseCapturer = new MouseCapturer(this);

            startRunnable(mouseCapturer, 10);

            GameObject.client = this;
            GameObjectDefinition.client = this;
            ActorDefinition.client = this;
            return;
        } catch (Exception exception) {
            SignLink.reportError("loaderror " + aString1027 + " " + anInt1322);
        }

        aBoolean1283 = true;
    }

    private void animateTexture(int i) {
        if (!lowMemory) {
            for (int k = 0; k < anIntArray1290.length; k++) {
                int l = anIntArray1290[k];
                if (Rasterizer3D.anIntArray1546[l] >= i) {
                    IndexedImage class50_sub1_sub1_sub3 = Rasterizer3D.aClass50_Sub1_Sub1_Sub3Array1540[l];
                    int i1 = class50_sub1_sub1_sub3.width * class50_sub1_sub1_sub3.height - 1;
                    int j1 = class50_sub1_sub1_sub3.width * tickDelta * 2;
                    byte abyte0[] = class50_sub1_sub1_sub3.pixels;
                    byte abyte1[] = aByteArray1245;
                    for (int k1 = 0; k1 <= i1; k1++)
                        abyte1[k1] = abyte0[k1 - j1 & i1];

                    class50_sub1_sub1_sub3.pixels = abyte1;
                    aByteArray1245 = abyte0;
                    Rasterizer3D.method499(l, 9);
                }
            }

        }
    }

    private void method66(int i, Widget class13, int j, int k, int l, int i1, int j1, int k1) {
        if (j1 != 23658)
            return;
        if (class13.type != 0 || class13.children == null || class13.hiddenUntilHovered)
            return;
        if (i1 < l || k1 < i || i1 > l + class13.width || k1 > i + class13.height)
            return;
        int l1 = class13.children.length;
        for (int i2 = 0; i2 < l1; i2++) {
            int j2 = class13.childrenX[i2] + l;
            int k2 = (class13.childrenY[i2] + i) - k;
            Widget child = Widget.forId(class13.children[i2]);
            j2 += child.xOffset;
            k2 += child.yOffset;
            if ((child.hoveredPopup >= 0 || child.disabledHoveredColor != 0) && i1 >= j2 && k1 >= k2
                    && i1 < j2 + child.width && k1 < k2 + child.height)
                if (child.hoveredPopup >= 0)
                    anInt915 = child.hoveredPopup;
                else
                    anInt915 = child.id;
            if (child.type == 8 && i1 >= j2 && k1 >= k2 && i1 < j2 + child.width
                    && k1 < k2 + child.height)
                anInt1315 = child.id;
            if (child.type == 0) {
                method66(k2, child, j, child.scrollPosition, j2, i1, 23658, k1);
                if (child.scrollLimit > child.height)
                    scrollInterface(child.scrollLimit, k2, child, (byte) 102, k1, j, i1, child.height, j2
                            + child.width);
            } else {
                if (child.actionType == 1 && i1 >= j2 && k1 >= k2 && i1 < j2 + child.width
                        && k1 < k2 + child.height) {
                    boolean flag = false;
                    if (child.contentType != 0)
                        flag = processFriendListClick(child);
                    if (!flag) {
                        menuActionTexts[menuActionRow] = child.tooltip;
                        menuActionTypes[menuActionRow] = 352;
                        secondMenuOperand[menuActionRow] = child.id;
                        menuActionRow++;
                    }
                }
                if (child.actionType == 2 && widgetSelected == 0 && i1 >= j2 && k1 >= k2 && i1 < j2 + child.width
                        && k1 < k2 + child.height) {
                    String circumfix = child.optionCircumfix;
                    if (circumfix.indexOf(" ") != -1)
                        circumfix = circumfix.substring(0, circumfix.indexOf(" "));
                    menuActionTexts[menuActionRow] = circumfix + " @gre@" + child.optionText;
                    menuActionTypes[menuActionRow] = Actions.USABLE_WIDGET;
                    secondMenuOperand[menuActionRow] = child.id;
                    menuActionRow++;
                }
                if (child.actionType == 3 && i1 >= j2 && k1 >= k2 && i1 < j2 + child.width
                        && k1 < k2 + child.height) {
                    menuActionTexts[menuActionRow] = "Close";
                    if (j == 3)
                        menuActionTypes[menuActionRow] = 55;
                    else
                        menuActionTypes[menuActionRow] = Actions.CLOSE_WIDGETS;
                    secondMenuOperand[menuActionRow] = child.id;
                    menuActionRow++;
                }
                if (child.actionType == 4 && i1 >= j2 && k1 >= k2 && i1 < j2 + child.width
                        && k1 < k2 + child.height) {
                    menuActionTexts[menuActionRow] = child.tooltip;
                    menuActionTypes[menuActionRow] = 890;
                    secondMenuOperand[menuActionRow] = child.id;
                    menuActionRow++;
                }
                if (child.actionType == 5 && i1 >= j2 && k1 >= k2 && i1 < j2 + child.width
                        && k1 < k2 + child.height) {
                    menuActionTexts[menuActionRow] = child.tooltip;
                    menuActionTypes[menuActionRow] = 518;
                    secondMenuOperand[menuActionRow] = child.id;
                    menuActionRow++;
                }
                if (child.actionType == 6 && !aBoolean1239 && i1 >= j2 && k1 >= k2 && i1 < j2 + child.width
                        && k1 < k2 + child.height) {
                    menuActionTexts[menuActionRow] = child.tooltip;
                    menuActionTypes[menuActionRow] = Actions.CLICK_TO_CONTINUE;
                    secondMenuOperand[menuActionRow] = child.id;
                    menuActionRow++;
                }
                if (child.type == 2) {
                    int l2 = 0;
                    for (int i3 = 0; i3 < child.height; i3++) {
                        for (int j3 = 0; j3 < child.width; j3++) {
                            int k3 = j2 + j3 * (32 + child.itemSpritePadsX);
                            int l3 = k2 + i3 * (32 + child.itemSpritePadsY);
                            if (l2 < 20) {
                                k3 += child.imageX[l2];
                                l3 += child.imageY[l2];
                            }
                            if (i1 >= k3 && k1 >= l3 && i1 < k3 + 32 && k1 < l3 + 32) {
                                mouseInvInterfaceIndex = l2;
                                lastActiveInvInterface = child.id;
                                if (child.items[l2] > 0) {
                                    ItemDefinition definition = ItemDefinition.lookup(child.items[l2] - 1);
                                    if (itemSelected == 1 && child.isInventory) {
                                        if (child.id != anInt1148 || l2 != anInt1147) {
                                            menuActionTexts[menuActionRow] = "Use " + selectedItemName + " with @lre@"
                                                    + definition.name;
                                            menuActionTypes[menuActionRow] = 903;
                                            selectedMenuActions[menuActionRow] = definition.id;
                                            firstMenuOperand[menuActionRow] = l2;
                                            secondMenuOperand[menuActionRow] = child.id;
                                            menuActionRow++;
                                        }
                                    } else if (widgetSelected == 1 && child.isInventory) {
                                        if ((anInt1173 & 0x10) == 16) {
                                            menuActionTexts[menuActionRow] = selectedWidgetName + " @lre@" + definition.name;
                                            menuActionTypes[menuActionRow] = 361;
                                            selectedMenuActions[menuActionRow] = definition.id;
                                            firstMenuOperand[menuActionRow] = l2;
                                            secondMenuOperand[menuActionRow] = child.id;
                                            menuActionRow++;
                                        }
                                    } else {
                                        if (child.isInventory) {
                                            for (int i4 = 4; i4 >= 3; i4--)
                                                if (definition.inventoryActions != null
                                                        && definition.inventoryActions[i4] != null) {
                                                    menuActionTexts[menuActionRow] = definition.inventoryActions[i4]
                                                            + " @lre@" + definition.name;
                                                    if (i4 == 3)
                                                        menuActionTypes[menuActionRow] = 227;
                                                    if (i4 == 4)
                                                        menuActionTypes[menuActionRow] = 891;
                                                    selectedMenuActions[menuActionRow] = definition.id;
                                                    firstMenuOperand[menuActionRow] = l2;
                                                    secondMenuOperand[menuActionRow] = child.id;
                                                    menuActionRow++;
                                                } else if (i4 == 4) {
                                                    menuActionTexts[menuActionRow] = "Drop @lre@" + definition.name;
                                                    menuActionTypes[menuActionRow] = 891;
                                                    selectedMenuActions[menuActionRow] = definition.id;
                                                    firstMenuOperand[menuActionRow] = l2;
                                                    secondMenuOperand[menuActionRow] = child.id;
                                                    menuActionRow++;
                                                }

                                        }
                                        if (child.itemUsable) {
                                            menuActionTexts[menuActionRow] = "Use @lre@" + definition.name;
                                            menuActionTypes[menuActionRow] = 52;
                                            selectedMenuActions[menuActionRow] = definition.id;
                                            firstMenuOperand[menuActionRow] = l2;
                                            secondMenuOperand[menuActionRow] = child.id;
                                            menuActionRow++;
                                        }
                                        if (child.isInventory && definition.inventoryActions != null) {
                                            for (int j4 = 2; j4 >= 0; j4--)
                                                if (definition.inventoryActions[j4] != null) {
                                                    menuActionTexts[menuActionRow] = definition.inventoryActions[j4]
                                                            + " @lre@" + definition.name;
                                                    if (j4 == 0)
                                                        menuActionTypes[menuActionRow] = 961;
                                                    if (j4 == 1)
                                                        menuActionTypes[menuActionRow] = 399;
                                                    if (j4 == 2)
                                                        menuActionTypes[menuActionRow] = 324;
                                                    selectedMenuActions[menuActionRow] = definition.id;
                                                    firstMenuOperand[menuActionRow] = l2;
                                                    secondMenuOperand[menuActionRow] = child.id;
                                                    menuActionRow++;
                                                }

                                        }
                                        if (child.options != null) {
                                            for (int k4 = 4; k4 >= 0; k4--)
                                                if (child.options[k4] != null) {
                                                    menuActionTexts[menuActionRow] = child.options[k4]
                                                            + " @lre@" + definition.name;
                                                    if (k4 == 0)
                                                        menuActionTypes[menuActionRow] = 9;
                                                    if (k4 == 1)
                                                        menuActionTypes[menuActionRow] = 225;
                                                    if (k4 == 2)
                                                        menuActionTypes[menuActionRow] = 444;
                                                    if (k4 == 3)
                                                        menuActionTypes[menuActionRow] = 564;
                                                    if (k4 == 4)
                                                        menuActionTypes[menuActionRow] = 894;
                                                    selectedMenuActions[menuActionRow] = definition.id;
                                                    firstMenuOperand[menuActionRow] = l2;
                                                    secondMenuOperand[menuActionRow] = child.id;
                                                    menuActionRow++;
                                                }

                                        }
                                        menuActionTexts[menuActionRow] = "Examine @lre@" + definition.name;
                                        menuActionTypes[menuActionRow] = Actions.EXAMINE_ITEM;
                                        selectedMenuActions[menuActionRow] = definition.id;
                                        firstMenuOperand[menuActionRow] = l2;
                                        secondMenuOperand[menuActionRow] = child.id;
                                        menuActionRow++;
                                    }
                                }
                            }
                            l2++;
                        }

                    }

                }
            }
        }

    }

    private void method67(int i) {
        for (int j = 0; j < anInt1133; j++) {
            int k = anIntArray1134[j];
            Npc class50_sub1_sub4_sub3_sub1 = npcs[k];
            if (class50_sub1_sub4_sub3_sub1 != null)
                method68(class50_sub1_sub4_sub3_sub1.npcDefinition.boundaryDimension, (byte) -97, class50_sub1_sub4_sub3_sub1);
        }

        if (i != -37214)
            outBuffer.putByte(41);
    }

    private void method68(int i, byte byte0, Actor class50_sub1_sub4_sub3) {
        if (class50_sub1_sub4_sub3.worldX < 128 || class50_sub1_sub4_sub3.worldY < 128
                || class50_sub1_sub4_sub3.worldX >= 13184 || class50_sub1_sub4_sub3.worldY >= 13184) {
            class50_sub1_sub4_sub3.emoteAnimation = -1;
            class50_sub1_sub4_sub3.graphic = -1;
            class50_sub1_sub4_sub3.anInt1606 = 0;
            class50_sub1_sub4_sub3.anInt1607 = 0;
            class50_sub1_sub4_sub3.worldX = class50_sub1_sub4_sub3.pathX[0] * 128
                    + class50_sub1_sub4_sub3.boundaryDimension * 64;
            class50_sub1_sub4_sub3.worldY = class50_sub1_sub4_sub3.pathY[0] * 128
                    + class50_sub1_sub4_sub3.boundaryDimension * 64;
            class50_sub1_sub4_sub3.resetPath();
        }
        if (class50_sub1_sub4_sub3 == localPlayer
                && (class50_sub1_sub4_sub3.worldX < 1536 || class50_sub1_sub4_sub3.worldY < 1536
                || class50_sub1_sub4_sub3.worldX >= 11776 || class50_sub1_sub4_sub3.worldY >= 11776)) {
            class50_sub1_sub4_sub3.emoteAnimation = -1;
            class50_sub1_sub4_sub3.graphic = -1;
            class50_sub1_sub4_sub3.anInt1606 = 0;
            class50_sub1_sub4_sub3.anInt1607 = 0;
            class50_sub1_sub4_sub3.worldX = class50_sub1_sub4_sub3.pathX[0] * 128
                    + class50_sub1_sub4_sub3.boundaryDimension * 64;
            class50_sub1_sub4_sub3.worldY = class50_sub1_sub4_sub3.pathY[0] * 128
                    + class50_sub1_sub4_sub3.boundaryDimension * 64;
            class50_sub1_sub4_sub3.resetPath();
        }
        if (class50_sub1_sub4_sub3.anInt1606 > pulseCycle)
            method69(class50_sub1_sub4_sub3);
        else if (class50_sub1_sub4_sub3.anInt1607 >= pulseCycle)
            method70(class50_sub1_sub4_sub3, -31135);
        else
            method71(class50_sub1_sub4_sub3, 0);
        method72((byte) 8, class50_sub1_sub4_sub3);
        method73(class50_sub1_sub4_sub3);
        if (byte0 == -97)
            ;
    }

    private void method69(Actor class50_sub1_sub4_sub3) {
        int i = class50_sub1_sub4_sub3.anInt1606 - pulseCycle;
        int j = class50_sub1_sub4_sub3.anInt1602 * 128 + class50_sub1_sub4_sub3.boundaryDimension * 64;
        int k = class50_sub1_sub4_sub3.anInt1604 * 128 + class50_sub1_sub4_sub3.boundaryDimension * 64;
        class50_sub1_sub4_sub3.worldX += (j - class50_sub1_sub4_sub3.worldX) / i;
        class50_sub1_sub4_sub3.worldY += (k - class50_sub1_sub4_sub3.worldY) / i;
        class50_sub1_sub4_sub3.anInt1623 = 0;
        if (class50_sub1_sub4_sub3.anInt1608 == 0)
            class50_sub1_sub4_sub3.nextStepOrientation = 1024;
        if (class50_sub1_sub4_sub3.anInt1608 == 1)
            class50_sub1_sub4_sub3.nextStepOrientation = 1536;
        if (class50_sub1_sub4_sub3.anInt1608 == 2)
            class50_sub1_sub4_sub3.nextStepOrientation = 0;
        if (class50_sub1_sub4_sub3.anInt1608 == 3)
            class50_sub1_sub4_sub3.nextStepOrientation = 512;
    }

    private void method70(Actor class50_sub1_sub4_sub3, int i) {
        if (class50_sub1_sub4_sub3.anInt1607 == pulseCycle
                || class50_sub1_sub4_sub3.emoteAnimation == -1
                || class50_sub1_sub4_sub3.animationDelay != 0
                || class50_sub1_sub4_sub3.anInt1626 + 1 > AnimationSequence.animations[class50_sub1_sub4_sub3.emoteAnimation]
                .getFrameLength(class50_sub1_sub4_sub3.displayedEmoteFrames)) {
            int j = class50_sub1_sub4_sub3.anInt1607 - class50_sub1_sub4_sub3.anInt1606;
            int k = pulseCycle - class50_sub1_sub4_sub3.anInt1606;
            int l = class50_sub1_sub4_sub3.anInt1602 * 128 + class50_sub1_sub4_sub3.boundaryDimension * 64;
            int i1 = class50_sub1_sub4_sub3.anInt1604 * 128 + class50_sub1_sub4_sub3.boundaryDimension * 64;
            int j1 = class50_sub1_sub4_sub3.anInt1603 * 128 + class50_sub1_sub4_sub3.boundaryDimension * 64;
            int k1 = class50_sub1_sub4_sub3.anInt1605 * 128 + class50_sub1_sub4_sub3.boundaryDimension * 64;
            class50_sub1_sub4_sub3.worldX = (l * (j - k) + j1 * k) / j;
            class50_sub1_sub4_sub3.worldY = (i1 * (j - k) + k1 * k) / j;
        }
        class50_sub1_sub4_sub3.anInt1623 = 0;
        if (class50_sub1_sub4_sub3.anInt1608 == 0)
            class50_sub1_sub4_sub3.nextStepOrientation = 1024;
        if (class50_sub1_sub4_sub3.anInt1608 == 1)
            class50_sub1_sub4_sub3.nextStepOrientation = 1536;
        if (class50_sub1_sub4_sub3.anInt1608 == 2)
            class50_sub1_sub4_sub3.nextStepOrientation = 0;
        if (class50_sub1_sub4_sub3.anInt1608 == 3)
            class50_sub1_sub4_sub3.nextStepOrientation = 512;
        class50_sub1_sub4_sub3.anInt1612 = class50_sub1_sub4_sub3.nextStepOrientation;
        if (i == -31135)
            ;
    }

    private void method71(Actor class50_sub1_sub4_sub3, int i) {
        class50_sub1_sub4_sub3.movementAnimation = class50_sub1_sub4_sub3.idleAnimation;
        if (class50_sub1_sub4_sub3.pathLength == 0) {
            class50_sub1_sub4_sub3.anInt1623 = 0;
            return;
        }
        if (class50_sub1_sub4_sub3.emoteAnimation != -1 && class50_sub1_sub4_sub3.animationDelay == 0) {
            AnimationSequence class14 = AnimationSequence.animations[class50_sub1_sub4_sub3.emoteAnimation];
            if (class50_sub1_sub4_sub3.anInt1613 > 0 && class14.anInt305 == 0) {
                class50_sub1_sub4_sub3.anInt1623++;
                return;
            }
            if (class50_sub1_sub4_sub3.anInt1613 <= 0 && class14.priority == 0) {
                class50_sub1_sub4_sub3.anInt1623++;
                return;
            }
        }
        int j = class50_sub1_sub4_sub3.worldX;
        int k = class50_sub1_sub4_sub3.worldY;
        int l = class50_sub1_sub4_sub3.pathX[class50_sub1_sub4_sub3.pathLength - 1] * 128
                + class50_sub1_sub4_sub3.boundaryDimension * 64;
        int i1 = class50_sub1_sub4_sub3.pathY[class50_sub1_sub4_sub3.pathLength - 1] * 128
                + class50_sub1_sub4_sub3.boundaryDimension * 64;
        if (l - j > 256 || l - j < -256 || i1 - k > 256 || i1 - k < -256) {
            class50_sub1_sub4_sub3.worldX = l;
            class50_sub1_sub4_sub3.worldY = i1;
            return;
        }
        if (j < l) {
            if (k < i1)
                class50_sub1_sub4_sub3.nextStepOrientation = 1280;
            else if (k > i1)
                class50_sub1_sub4_sub3.nextStepOrientation = 1792;
            else
                class50_sub1_sub4_sub3.nextStepOrientation = 1536;
        } else if (j > l) {
            if (k < i1)
                class50_sub1_sub4_sub3.nextStepOrientation = 768;
            else if (k > i1)
                class50_sub1_sub4_sub3.nextStepOrientation = 256;
            else
                class50_sub1_sub4_sub3.nextStepOrientation = 512;
        } else if (k < i1)
            class50_sub1_sub4_sub3.nextStepOrientation = 1024;
        else
            class50_sub1_sub4_sub3.nextStepOrientation = 0;
        int j1 = class50_sub1_sub4_sub3.nextStepOrientation - class50_sub1_sub4_sub3.anInt1612 & 0x7ff;
        if (j1 > 1024)
            j1 -= 2048;
        int k1 = class50_sub1_sub4_sub3.turnAroundAnimationId;
        if (i != 0)
            outBuffer.putByte(34);
        if (j1 >= -256 && j1 <= 256)
            k1 = class50_sub1_sub4_sub3.walkAnimationId;
        else if (j1 >= 256 && j1 < 768)
            k1 = class50_sub1_sub4_sub3.turnLeftAnimationId;
        else if (j1 >= -768 && j1 <= -256)
            k1 = class50_sub1_sub4_sub3.turnRightAnimationId;
        if (k1 == -1)
            k1 = class50_sub1_sub4_sub3.walkAnimationId;
        class50_sub1_sub4_sub3.movementAnimation = k1;
        int l1 = 4;
        if (class50_sub1_sub4_sub3.anInt1612 != class50_sub1_sub4_sub3.nextStepOrientation
                && class50_sub1_sub4_sub3.anInt1609 == -1 && class50_sub1_sub4_sub3.anInt1600 != 0)
            l1 = 2;
        if (class50_sub1_sub4_sub3.pathLength > 2)
            l1 = 6;
        if (class50_sub1_sub4_sub3.pathLength > 3)
            l1 = 8;
        if (class50_sub1_sub4_sub3.anInt1623 > 0 && class50_sub1_sub4_sub3.pathLength > 1) {
            l1 = 8;
            class50_sub1_sub4_sub3.anInt1623--;
        }
        if (class50_sub1_sub4_sub3.runningQueue[class50_sub1_sub4_sub3.pathLength - 1])
            l1 <<= 1;
        if (l1 >= 8 && class50_sub1_sub4_sub3.movementAnimation == class50_sub1_sub4_sub3.walkAnimationId
                && class50_sub1_sub4_sub3.runAnimationId != -1)
            class50_sub1_sub4_sub3.movementAnimation = class50_sub1_sub4_sub3.runAnimationId;
        if (j < l) {
            class50_sub1_sub4_sub3.worldX += l1;
            if (class50_sub1_sub4_sub3.worldX > l)
                class50_sub1_sub4_sub3.worldX = l;
        } else if (j > l) {
            class50_sub1_sub4_sub3.worldX -= l1;
            if (class50_sub1_sub4_sub3.worldX < l)
                class50_sub1_sub4_sub3.worldX = l;
        }
        if (k < i1) {
            class50_sub1_sub4_sub3.worldY += l1;
            if (class50_sub1_sub4_sub3.worldY > i1)
                class50_sub1_sub4_sub3.worldY = i1;
        } else if (k > i1) {
            class50_sub1_sub4_sub3.worldY -= l1;
            if (class50_sub1_sub4_sub3.worldY < i1)
                class50_sub1_sub4_sub3.worldY = i1;
        }
        if (class50_sub1_sub4_sub3.worldX == l && class50_sub1_sub4_sub3.worldY == i1) {
            class50_sub1_sub4_sub3.pathLength--;
            if (class50_sub1_sub4_sub3.anInt1613 > 0)
                class50_sub1_sub4_sub3.anInt1613--;
        }
    }

    private void method72(byte byte0, Actor class50_sub1_sub4_sub3) {
        if (byte0 != 8)
            anInt928 = incomingRandom.nextInt();
        if (class50_sub1_sub4_sub3.anInt1600 == 0)
            return;
        if (class50_sub1_sub4_sub3.anInt1609 != -1 && class50_sub1_sub4_sub3.anInt1609 < 32768) {
            Npc class50_sub1_sub4_sub3_sub1 = npcs[class50_sub1_sub4_sub3.anInt1609];
            if (class50_sub1_sub4_sub3_sub1 != null) {
                int l = class50_sub1_sub4_sub3.worldX - class50_sub1_sub4_sub3_sub1.worldX;
                int j1 = class50_sub1_sub4_sub3.worldY - class50_sub1_sub4_sub3_sub1.worldY;
                if (l != 0 || j1 != 0)
                    class50_sub1_sub4_sub3.nextStepOrientation = (int) (Math.atan2(l, j1) * 325.94900000000001D) & 0x7ff;
            }
        }
        if (class50_sub1_sub4_sub3.anInt1609 >= 32768) {
            int i = class50_sub1_sub4_sub3.anInt1609 - 32768;
            if (i == thisPlayerServerId)
                i = thisPlayerId;
            Player class50_sub1_sub4_sub3_sub2 = players[i];
            if (class50_sub1_sub4_sub3_sub2 != null) {
                int k1 = class50_sub1_sub4_sub3.worldX - class50_sub1_sub4_sub3_sub2.worldX;
                int l1 = class50_sub1_sub4_sub3.worldY - class50_sub1_sub4_sub3_sub2.worldY;
                if (k1 != 0 || l1 != 0)
                    class50_sub1_sub4_sub3.nextStepOrientation = (int) (Math.atan2(k1, l1) * 325.94900000000001D) & 0x7ff;
            }
        }
        if ((class50_sub1_sub4_sub3.anInt1598 != 0 || class50_sub1_sub4_sub3.anInt1599 != 0)
                && (class50_sub1_sub4_sub3.pathLength == 0 || class50_sub1_sub4_sub3.anInt1623 > 0)) {
            int j = class50_sub1_sub4_sub3.worldX - (class50_sub1_sub4_sub3.anInt1598 - nextTopLeftTileX - nextTopLeftTileX) * 64;
            int i1 = class50_sub1_sub4_sub3.worldY - (class50_sub1_sub4_sub3.anInt1599 - nextTopRightTileY - nextTopRightTileY) * 64;
            if (j != 0 || i1 != 0)
                class50_sub1_sub4_sub3.nextStepOrientation = (int) (Math.atan2(j, i1) * 325.94900000000001D) & 0x7ff;
            class50_sub1_sub4_sub3.anInt1598 = 0;
            class50_sub1_sub4_sub3.anInt1599 = 0;
        }
        int k = class50_sub1_sub4_sub3.nextStepOrientation - class50_sub1_sub4_sub3.anInt1612 & 0x7ff;
        if (k != 0) {
            if (k < class50_sub1_sub4_sub3.anInt1600 || k > 2048 - class50_sub1_sub4_sub3.anInt1600)
                class50_sub1_sub4_sub3.anInt1612 = class50_sub1_sub4_sub3.nextStepOrientation;
            else if (k > 1024)
                class50_sub1_sub4_sub3.anInt1612 -= class50_sub1_sub4_sub3.anInt1600;
            else
                class50_sub1_sub4_sub3.anInt1612 += class50_sub1_sub4_sub3.anInt1600;
            class50_sub1_sub4_sub3.anInt1612 &= 0x7ff;
            if (class50_sub1_sub4_sub3.movementAnimation == class50_sub1_sub4_sub3.idleAnimation
                    && class50_sub1_sub4_sub3.anInt1612 != class50_sub1_sub4_sub3.nextStepOrientation) {
                if (class50_sub1_sub4_sub3.standTurnAnimationId != -1) {
                    class50_sub1_sub4_sub3.movementAnimation = class50_sub1_sub4_sub3.standTurnAnimationId;
                    return;
                }
                class50_sub1_sub4_sub3.movementAnimation = class50_sub1_sub4_sub3.walkAnimationId;
            }
        }
    }

    private void method73(Actor actor) {
        actor.aBoolean1592 = false;
        if (actor.movementAnimation != -1) {
            AnimationSequence class14 = AnimationSequence.animations[actor.movementAnimation];
            actor.anInt1590++;
            if (actor.displayedMovementFrames < class14.frameCount
                    && actor.anInt1590 > class14.getFrameLength(actor.displayedMovementFrames)) {
                actor.anInt1590 = 1;
                actor.displayedMovementFrames++;
            }
            if (actor.displayedMovementFrames >= class14.frameCount) {
                actor.anInt1590 = 1;
                actor.displayedMovementFrames = 0;
            }
        }
        if (actor.graphic != -1 && pulseCycle >= actor.anInt1617) {
            if (actor.currentAnimation < 0)
                actor.currentAnimation = 0;
            AnimationSequence class14_1 = SpotAnimation.cache[actor.graphic].sequences;
            actor.anInt1616++;
            if (actor.currentAnimation < class14_1.frameCount
                    && actor.anInt1616 > class14_1.getFrameLength(actor.currentAnimation)) {
                actor.anInt1616 = 1;
                actor.currentAnimation++;
            }
            if (actor.currentAnimation >= class14_1.frameCount
                    && (actor.currentAnimation < 0 || actor.currentAnimation >= class14_1.frameCount))
                actor.graphic = -1;
        }
        if (actor.emoteAnimation != -1 && actor.animationDelay <= 1) {
            AnimationSequence class14_2 = AnimationSequence.animations[actor.emoteAnimation];
            if (class14_2.anInt305 == 1 && actor.anInt1613 > 0
                    && actor.anInt1606 <= pulseCycle && actor.anInt1607 < pulseCycle) {
                actor.animationDelay = 1;
                return;
            }
        }
        if (actor.emoteAnimation != -1 && actor.animationDelay == 0) {
            AnimationSequence class14_3 = AnimationSequence.animations[actor.emoteAnimation];
            actor.anInt1626++;
            if (actor.displayedEmoteFrames < class14_3.frameCount
                    && actor.anInt1626 > class14_3.getFrameLength(actor.displayedEmoteFrames)) {
                actor.anInt1626 = 1;
                actor.displayedEmoteFrames++;
            }
            if (actor.displayedEmoteFrames >= class14_3.frameCount) {
                actor.displayedEmoteFrames -= class14_3.frameStep;
                actor.anInt1628++;
                if (actor.anInt1628 >= class14_3.anInt304)
                    actor.emoteAnimation = -1;
                if (actor.displayedEmoteFrames < 0 || actor.displayedEmoteFrames >= class14_3.frameCount)
                    actor.emoteAnimation = -1;
            }
            actor.aBoolean1592 = class14_3.aBoolean300;
        }
        if (actor.animationDelay > 0)
            actor.animationDelay--;
    }

    private void method74(int i) {
        if (anInt1053 != -1 && (loadingStage == 2 || super.imageProducer != null)) {
            if (loadingStage == 2) {
                method88(tickDelta, anInt1053);
                if (anInt960 != -1)
                    method88(tickDelta, anInt960);
                tickDelta = 0;
                method147(anInt1140);
                super.imageProducer.createRasterizer();
                Rasterizer3D.lineOffsets = anIntArray1003;
                Rasterizer.resetPixels();
                aBoolean1046 = true;
                Widget class13 = Widget.forId(anInt1053);
                if (class13.width == 512 && class13.height == 334 && class13.type == 0) {
                    class13.width = 765;
                    class13.height = 503;
                }
                drawInterface(0, 0, class13, 0, 8);
                if (anInt960 != -1) {
                    Widget class13_1 = Widget.forId(anInt960);
                    if (class13_1.width == 512 && class13_1.height == 334 && class13_1.type == 0) {
                        class13_1.width = 765;
                        class13_1.height = 503;
                    }
                    drawInterface(0, 0, class13_1, 0, 8);
                }
                if (!menuOpen) {
                    processRightClick(-521);
                    drawMenuTooltip();
                } else {
                    drawMenu();
                }
            }
            super.imageProducer.drawGraphics(0, 0, super.gameGraphics);
            return;
        }
        if (aBoolean1046) {
            method122(-906);
            aBoolean1046 = false;
            aClass18_906.drawGraphics(0, 4, super.gameGraphics);
            aClass18_907.drawGraphics(0, 357, super.gameGraphics);
            aClass18_908.drawGraphics(722, 4, super.gameGraphics);
            aClass18_909.drawGraphics(743, 205, super.gameGraphics);
            aClass18_910.drawGraphics(0, 0, super.gameGraphics);
            aClass18_911.drawGraphics(516, 4, super.gameGraphics);
            aClass18_912.drawGraphics(516, 205, super.gameGraphics);
            aClass18_913.drawGraphics(496, 357, super.gameGraphics);
            aClass18_914.drawGraphics(0, 338, super.gameGraphics);
            redrawTabArea = true;
            redrawChatbox = true;
            drawTabIcons = true;
            redrawChatMode = true;
            if (loadingStage != 2) {
                gameScreenImageProducer.drawGraphics(4, 4, super.gameGraphics);
                aClass18_1157.drawGraphics(550, 4, super.gameGraphics);
            }
            anInt1237++;
            if (anInt1237 > 85) {
                anInt1237 = 0;
                outBuffer.putOpcode(168);
            }
        }
        if (loadingStage == 2)
            renderGameView();
        if (menuOpen && menuScreenArea == 1)
            redrawTabArea = true;
        if (openInvOverLayId != -1) {
            boolean flag = method88(tickDelta, openInvOverLayId);
            if (flag)
                redrawTabArea = true;
        }
        if (atInventoryInterfaceType == 2)
            redrawTabArea = true;
        if (activeInterfaceType == 2)
            redrawTabArea = true;
        if (redrawTabArea) {
            method134();
            redrawTabArea = false;
        }
        if (backDialogueId == -1 && inputType == 0) {
            chatboxInterface.scrollPosition = chatboxScrollMax - chatboxScroll - 77;
            if (super.mouseX > 448 && super.mouseX < 560 && super.mouseY > 332)
                scrollInterface(chatboxScrollMax, 0, chatboxInterface, (byte) 102, super.mouseY - 357, -1, super.mouseX - 17, 77, 463);
            int j = chatboxScrollMax - 77 - chatboxInterface.scrollPosition;
            if (j < 0)
                j = 0;
            if (j > chatboxScrollMax - 77)
                j = chatboxScrollMax - 77;
            if (chatboxScroll != j) {
                chatboxScroll = j;
                redrawChatbox = true;
            }
        }
        if (backDialogueId == -1 && inputType == 3) {
            int k = anInt862 * 14 + 7;
            chatboxInterface.scrollPosition = anInt865;
            if (super.mouseX > 448 && super.mouseX < 560 && super.mouseY > 332)
                scrollInterface(k, 0, chatboxInterface, (byte) 102, super.mouseY - 357, -1, super.mouseX - 17, 77, 463);
            int i1 = chatboxInterface.scrollPosition;
            if (i1 < 0)
                i1 = 0;
            if (i1 > k - 77)
                i1 = k - 77;
            if (anInt865 != i1) {
                anInt865 = i1;
                redrawChatbox = true;
            }
        }
        if (backDialogueId != -1) {
            boolean flag1 = method88(tickDelta, backDialogueId);
            if (flag1)
                redrawChatbox = true;
        }
        if (atInventoryInterfaceType == 3)
            redrawChatbox = true;
        if (activeInterfaceType == 3)
            redrawChatbox = true;
        if (clickToContinueString != null)
            redrawChatbox = true;
        if (menuOpen && menuScreenArea == 2)
            redrawChatbox = true;
        if (redrawChatbox) {
            renderChatbox();
            redrawChatbox = false;
        }
        if (loadingStage == 2) {
            renderMinimap();
            aClass18_1157.drawGraphics(550, 4, super.gameGraphics);
        }
        if (anInt1213 != -1)
            drawTabIcons = true;
        if (drawTabIcons) {
            if (anInt1213 != -1 && anInt1213 == currentTabId) {
                anInt1213 = -1;
                outBuffer.putOpcode(119);
                outBuffer.putByte(currentTabId);
            }
            drawTabIcons = false;
            aClass18_1110.createRasterizer();
            anIndexedImage1054.drawImage(0, 0);
            if (openInvOverLayId == -1) {
                if (tabInterfaceIDs[currentTabId] != -1) {
                    if (currentTabId == 0)
                        aClass50_Sub1_Sub1_Sub3_880.drawImage(22, 10);
                    if (currentTabId == 1)
                        aClass50_Sub1_Sub1_Sub3_881.drawImage(54, 8);
                    if (currentTabId == 2)
                        aClass50_Sub1_Sub1_Sub3_881.drawImage(82, 8);
                    if (currentTabId == 3)
                        aClass50_Sub1_Sub1_Sub3_882.drawImage(110, 8);
                    if (currentTabId == 4)
                        aClass50_Sub1_Sub1_Sub3_884.drawImage(153, 8);
                    if (currentTabId == 5)
                        aClass50_Sub1_Sub1_Sub3_884.drawImage(181, 8);
                    if (currentTabId == 6)
                        aClass50_Sub1_Sub1_Sub3_883.drawImage(209, 9);
                }
                if (tabInterfaceIDs[0] != -1 && (anInt1213 != 0 || pulseCycle % 20 < 10))
                    tabIcon[0].drawImage(29, 13);
                if (tabInterfaceIDs[1] != -1 && (anInt1213 != 1 || pulseCycle % 20 < 10))
                    tabIcon[1].drawImage(53, 11);
                if (tabInterfaceIDs[2] != -1 && (anInt1213 != 2 || pulseCycle % 20 < 10))
                    tabIcon[2].drawImage(82, 11);
                if (tabInterfaceIDs[3] != -1 && (anInt1213 != 3 || pulseCycle % 20 < 10))
                    tabIcon[3].drawImage(115, 12);
                if (tabInterfaceIDs[4] != -1 && (anInt1213 != 4 || pulseCycle % 20 < 10))
                    tabIcon[4].drawImage(153, 13);
                if (tabInterfaceIDs[5] != -1 && (anInt1213 != 5 || pulseCycle % 20 < 10))
                    tabIcon[5].drawImage(180, 11);
                if (tabInterfaceIDs[6] != -1 && (anInt1213 != 6 || pulseCycle % 20 < 10))
                    tabIcon[6].drawImage(208, 13);
            }
            aClass18_1110.drawGraphics(516, 160, super.gameGraphics);
            aClass18_1109.createRasterizer();
            anIndexedImage1053.drawImage(0, 0);
            if (openInvOverLayId == -1) {
                if (tabInterfaceIDs[currentTabId] != -1) {
                    if (currentTabId == 7)
                        aClass50_Sub1_Sub1_Sub3_983.drawImage(42, 0);
                    if (currentTabId == 8)
                        aClass50_Sub1_Sub1_Sub3_984.drawImage(74, 0);
                    if (currentTabId == 9)
                        aClass50_Sub1_Sub1_Sub3_984.drawImage(102, 0);
                    if (currentTabId == 10)
                        aClass50_Sub1_Sub1_Sub3_985.drawImage(130, 1);
                    if (currentTabId == 11)
                        aClass50_Sub1_Sub1_Sub3_987.drawImage(173, 0);
                    if (currentTabId == 12)
                        aClass50_Sub1_Sub1_Sub3_987.drawImage(201, 0);
                    if (currentTabId == 13)
                        aClass50_Sub1_Sub1_Sub3_986.drawImage(229, 0);
                }
                if (tabInterfaceIDs[8] != -1 && (anInt1213 != 8 || pulseCycle % 20 < 10))
                    tabIcon[7].drawImage(74, 2);
                if (tabInterfaceIDs[9] != -1 && (anInt1213 != 9 || pulseCycle % 20 < 10))
                    tabIcon[8].drawImage(102, 3);
                if (tabInterfaceIDs[10] != -1 && (anInt1213 != 10 || pulseCycle % 20 < 10))
                    tabIcon[9].drawImage(137, 4);
                if (tabInterfaceIDs[11] != -1 && (anInt1213 != 11 || pulseCycle % 20 < 10))
                    tabIcon[10].drawImage(174, 2);
                if (tabInterfaceIDs[12] != -1 && (anInt1213 != 12 || pulseCycle % 20 < 10))
                    tabIcon[11].drawImage(201, 2);
                if (tabInterfaceIDs[13] != -1 && (anInt1213 != 13 || pulseCycle % 20 < 10))
                    tabIcon[12].drawImage(226, 2);
            }
            aClass18_1109.drawGraphics(496, 466, super.gameGraphics);
            gameScreenImageProducer.createRasterizer();
            Rasterizer3D.lineOffsets = viewportOffsets;
        }
        if (redrawChatMode) {
            redrawChatMode = false;
            aClass18_1108.createRasterizer();
            anIndexedImage1052.drawImage(0, 0);
            fontNormal.drawStringCenter("Public chat", 55, 28, 0xffffff, true);
            if (publicChatMode == 0)
                fontNormal.drawStringCenter("On", 55, 41, 65280, true);
            if (publicChatMode == 1)
                fontNormal.drawStringCenter("Friends", 55, 41, 0xffff00, true);
            if (publicChatMode == 2)
                fontNormal.drawStringCenter("Off", 55, 41, 0xff0000, true);
            if (publicChatMode == 3)
                fontNormal.drawStringCenter("Hide", 55, 41, 65535, true);
            fontNormal.drawStringCenter("Private chat", 184, 28, 0xffffff, true);
            if (privateChatMode == 0)
                fontNormal.drawStringCenter("On", 184, 41, 65280, true);
            if (privateChatMode == 1)
                fontNormal.drawStringCenter("Friends", 184, 41, 0xffff00, true);
            if (privateChatMode == 2)
                fontNormal.drawStringCenter("Off", 184, 41, 0xff0000, true);
            fontNormal.drawStringCenter("Trade/compete", 324, 28, 0xffffff, true);
            if (tradeMode == 0)
                fontNormal.drawStringCenter("On", 324, 41, 65280, true);
            if (tradeMode == 1)
                fontNormal.drawStringCenter("Friends", 324, 41, 0xffff00, true);
            if (tradeMode == 2)
                fontNormal.drawStringCenter("Off", 324, 41, 0xff0000, true);
            fontNormal.drawStringCenter("Report abuse", 458, 33, 0xffffff, true);
            aClass18_1108.drawGraphics(0, 453, super.gameGraphics);
            gameScreenImageProducer.createRasterizer();
            Rasterizer3D.lineOffsets = viewportOffsets;
        }
        tickDelta = 0;
        if (i != 7) {
            for (int l = 1; l > 0; l++) ;
        }
    }

    private void renderSplitPrivateMessages() {
        if (anInt1223 == 0)
            return;

        TypeFace typeFace = fontNormal;
        int line = 0;

        if (systemUpdateTime != 0)
            line = 1;

        for (int i = 0; i < 100; i++) {
            if (chatMessages[i] != null) {
                int type = chatTypes[i];
                String name = chatPlayerNames[i];
                byte privilege = 0;

                if (name != null && name.startsWith("@cr1@")) {
                    name = name.substring(5);
                    privilege = 1;
                }

                if (name != null && name.startsWith("@cr2@")) {
                    name = name.substring(5);
                    privilege = 2;
                }

                if ((type == 3 || type == 7) && (type == 7 || privateChatMode == 0 || privateChatMode == 1 && method148(13292, name))) {
                    int y = 329 - line * 13;
                    int x = 4;

                    typeFace.drawString("From", x, y, 0);
                    typeFace.drawString("From", x, y - 1, 65535);

                    x += typeFace.getStringEffectWidth("From ");

                    if (privilege == 1) {
                        moderatorIcon[0].drawImage(x, y - 12);
                        x += 14;
                    }

                    if (privilege == 2) {
                        moderatorIcon[1].drawImage(x, y - 12);
                        x += 14;
                    }

                    typeFace.drawString(name + ": " + chatMessages[i], x, y, 0);
                    typeFace.drawString(name + ": " + chatMessages[i], x, y - 1, 65535);

                    if (++line >= 5)
                        return;
                }

                if (type == 5 && privateChatMode < 2) {
                    int y = 329 - line * 13;

                    typeFace.drawString(chatMessages[i], 4, y, 0);
                    typeFace.drawString(chatMessages[i], 4, y - 1, 65535);

                    if (++line >= 5)
                        return;
                }

                if (type == 6 && privateChatMode < 2) {
                    int y = 329 - line * 13;

                    typeFace.drawString("To " + name + ": " + chatMessages[i], 4, y, 0);
                    typeFace.drawString("To " + name + ": " + chatMessages[i], 4, y - 1, 65535);

                    if (++line >= 5)
                        return;
                }
            }
        }
    }

    public void init() {
//        world = Integer.parseInt(getParameter("nodeid"));
//        portOffset = Integer.parseInt(getParameter("portoff"));
//        String s = getParameter("lowmem");
//        if (s != null && s.equals("1"))
//            setLowMemory();
//        else
//            setHighMemory();
//        String s1 = getParameter("free");
//        if (s1 != null && s1.equals("1"))
//            memberServer = false;
//        else
//            memberServer = true;
        initializeApplet(765, 503);
    }

    private void renderStationaryGraphics(int i) {
        while (i >= 0)
            groundItems = null;
        for (GameAnimableObject gameAnimableObject = (GameAnimableObject) aClass6_1210.first(); gameAnimableObject != null; gameAnimableObject = (GameAnimableObject) aClass6_1210
                .next())
            if (gameAnimableObject.plane != plane || gameAnimableObject.transformCompleted)
                gameAnimableObject.remove();
            else if (pulseCycle >= gameAnimableObject.loopCycle) {
                gameAnimableObject.nextFrame(tickDelta);
                if (gameAnimableObject.transformCompleted)
                    gameAnimableObject.remove();
                else
                    currentScene.addEntity(gameAnimableObject.plane, gameAnimableObject.x, gameAnimableObject.y, gameAnimableObject.z, gameAnimableObject, -1,
                            60, false,
                            0);
            }

    }

    private void method77(boolean flag) {
        if (flag)
            opcode = -1;
        do {
            OnDemandNode class50_sub1_sub3;
            do {
                class50_sub1_sub3 = onDemandRequester.next();
                if (class50_sub1_sub3 == null)
                    return;
                if (class50_sub1_sub3.type == 0) {
                    Model.loadModelHeader(class50_sub1_sub3.buffer, class50_sub1_sub3.id);
                    if ((onDemandRequester.modelId(class50_sub1_sub3.id) & 0x62) != 0) {
                        redrawTabArea = true;
                        if (backDialogueId != -1 || dialogueId != -1)
                            redrawChatbox = true;
                    }
                }
                if (class50_sub1_sub3.type == 1 && class50_sub1_sub3.buffer != null)
                    Animation.method236(class50_sub1_sub3.buffer);
                if (class50_sub1_sub3.type == 2 && class50_sub1_sub3.id == nextSong && class50_sub1_sub3.buffer != null)
                    method24(songChanging, class50_sub1_sub3.buffer, 659);
                if (class50_sub1_sub3.type == 3 && loadingStage == 1) {
                    for (int i = 0; i < terrainData.length; i++) {
                        if (terrainDataIds[i] == class50_sub1_sub3.id) {
                            terrainData[i] = class50_sub1_sub3.buffer;
                            if (class50_sub1_sub3.buffer == null)
                                terrainDataIds[i] = -1;
                            break;
                        }
                        if (objectDataIds[i] != class50_sub1_sub3.id)
                            continue;
                        objectData[i] = class50_sub1_sub3.buffer;
                        if (class50_sub1_sub3.buffer == null)
                            objectDataIds[i] = -1;
                        break;
                    }

                }
            } while (class50_sub1_sub3.type != 93 || !onDemandRequester.method334(class50_sub1_sub3.id, false));
            Region.passiveRequestGameObjectModels(onDemandRequester, new Buffer(class50_sub1_sub3.buffer));
        } while (true);
    }

    private void login(String username, String password, boolean reconnecting) {
        SignLink.errorName = username;

        try {
            if (!reconnecting) {
                statusLineOne = "";
                statusLineTwo = "Connecting to server...";

                drawLoginScreen(true);
            }

            gameConnection = new BufferedConnection(this, openSocket(Configuration.GAME_PORT + portOffset));
            long base37name = TextUtils.nameToLong(username);
            int hash = (int) (base37name >> 16 & 31L);
            outBuffer.currentPosition = 0;

            outBuffer.putByte(14);
            outBuffer.putByte(hash);
            gameConnection.write(2, 0, outBuffer.buffer);

            for (int j = 0; j < 8; j++)
                gameConnection.read();

            int responseCode = gameConnection.read();
            int initialResponseCode = responseCode;

            if (responseCode == 0) {
                gameConnection.read(buffer.buffer, 0, 8);

                buffer.currentPosition = 0;
                serverSeed = buffer.getLongBE();
                int seed[] = new int[4];

                seed[0] = (int) (Math.random() * 99999999D);
                seed[1] = (int) (Math.random() * 99999999D);
                seed[2] = (int) (serverSeed >> 32);
                seed[3] = (int) serverSeed;

                outBuffer.currentPosition = 0;

                outBuffer.putByte(10);
                outBuffer.putIntBE(seed[0]);
                outBuffer.putIntBE(seed[1]);
                outBuffer.putIntBE(seed[2]);
                outBuffer.putIntBE(seed[3]);
                outBuffer.putIntBE(SignLink.uid);
                outBuffer.putString(username);
                outBuffer.putString(password);

                if (Configuration.RSA_ENABLED)
                    outBuffer.encrypt(Configuration.RSA_MODULUS, Configuration.RSA_PUBLIC_KEY);

                tempBuffer.currentPosition = 0;

                if (reconnecting)
                    tempBuffer.putByte(18);
                else
                    tempBuffer.putByte(16);

                tempBuffer.putByte(outBuffer.currentPosition + 36 + 1 + 1 + 2);
                tempBuffer.putByte(255);
                tempBuffer.putShortBE(SignLink.CLIENT_REVISION);
                tempBuffer.putByte(lowMemory ? 1 : 0);

                for (int i = 0; i < 9; i++)
                    tempBuffer.putIntBE(archiveHashes[i]);

                tempBuffer.putBytes(outBuffer.buffer, 0, outBuffer.currentPosition);

                outBuffer.random = new ISAACCipher(seed);

                for (int i = 0; i < 4; i++)
                    seed[i] += 50;

                incomingRandom = new ISAACCipher(seed);

                gameConnection.write(tempBuffer.currentPosition, 0, tempBuffer.buffer);

                responseCode = gameConnection.read();
            }

            if (responseCode == 1) {
                try {
                    Thread.sleep(2000L);
                } catch (Exception ignored) {
                }

                login(username, password, reconnecting);
                return;
            }

            if (responseCode == 2) {
                playerRights = gameConnection.read();
                accountFlagged = gameConnection.read() == 1;
                aLong902 = 0L;
                duplicateClickCount = 0;
                mouseCapturer.coord = 0;
                super.awtFocus = true;
                aBoolean1275 = true;
                loggedIn = true;
                outBuffer.currentPosition = 0;
                buffer.currentPosition = 0;
                opcode = -1;
                lastOpcode = -1;
                secondLastOpcode = -1;
                thirdLastOpcode = -1;
                packetSize = 0;
                timeoutCounter = 0;
                systemUpdateTime = 0;
                anInt873 = 0;
                headIconDrawType = 0;
                menuActionRow = 0;
                menuOpen = false;
                super.idleTime = 0;

                for (int j1 = 0; j1 < 100; j1++)
                    chatMessages[j1] = null;

                itemSelected = 0;
                widgetSelected = 0;
                loadingStage = 0;
                currentSound = 0;
                anInt853 = (int) (Math.random() * 100D) - 50;
                anInt1009 = (int) (Math.random() * 110D) - 55;
                cameraRandomisationA = (int) (Math.random() * 80D) - 40;
                anInt916 = (int) (Math.random() * 120D) - 60;
                anInt1233 = (int) (Math.random() * 30D) - 20;
                cameraHorizontal = (int) (Math.random() * 20D) - 10 & 0x7ff;
                minimapState = 0;
                anInt1276 = -1;
                destinationX = 0;
                destinationY = 0;
                localPlayerCount = 0;
                anInt1133 = 0;

                for (int i2 = 0; i2 < anInt968; i2++) {
                    players[i2] = null;
                    cachedAppearances[i2] = null;
                }

                for (int k2 = 0; k2 < 16384; k2++)
                    npcs[k2] = null;

                localPlayer = players[thisPlayerId] = new Player();

                aClass6_1282.getNodeCount();
                aClass6_1210.getNodeCount();

                for (int l2 = 0; l2 < 4; l2++) {
                    for (int i3 = 0; i3 < 104; i3++) {
                        for (int k3 = 0; k3 < 104; k3++)
                            groundItems[l2][i3][k3] = null;
                    }
                }

                spawnObjectList = new LinkedList();
                friendListStatus = 0;
                friendsCount = 0;

                method44(dialogueId);
                dialogueId = -1;

                method44(backDialogueId);
                backDialogueId = -1;

                method44(openInterfaceId);
                openInterfaceId = -1;

                method44(anInt1053);
                anInt1053 = -1;

                method44(anInt960);
                anInt960 = -1;

                method44(openInvOverLayId);
                openInvOverLayId = -1;

                method44(walkableWidgetId);
                walkableWidgetId = -1;

                aBoolean1239 = false;
                currentTabId = 3;
                inputType = 0;
                menuOpen = false;
                messagePromptRaised = false;
                clickToContinueString = null;
                anInt1319 = 0;
                anInt1213 = -1;
                characterEditChangeGenger = true;

                method25();

                for (int j3 = 0; j3 < 5; j3++)
                    characterEditColors[j3] = 0;

                for (int l3 = 0; l3 < 5; l3++) {
                    aStringArray1069[l3] = null;
                    aBooleanArray1070[l3] = false;
                }

                anInt1100 = 0;
                anInt1165 = 0;
                anInt1235 = 0;
                anInt1052 = 0;
                anInt1139 = 0;

                method122(-906);
                return;
            }

            if (responseCode == 3) {
                statusLineOne = "";
                statusLineTwo = "Invalid username or password.";
                return;
            }

            if (responseCode == 4) {
                statusLineOne = "Your account has been disabled.";
                statusLineTwo = "Please check your message-centre for details.";
                return;
            }

            if (responseCode == 5) {
                statusLineOne = "Your account is already logged in.";
                statusLineTwo = "Try again in 60 secs...";
                return;
            }

            if (responseCode == 6) {
                statusLineOne = "RuneScape has been updated!";
                statusLineTwo = "Please reload this page.";
                return;
            }

            if (responseCode == 7) {
                statusLineOne = "This world is full.";
                statusLineTwo = "Please use a different world.";
                return;
            }

            if (responseCode == 8) {
                statusLineOne = "Unable to connect.";
                statusLineTwo = "Login server offline.";
                return;
            }

            if (responseCode == 9) {
                statusLineOne = "Login limit exceeded.";
                statusLineTwo = "Too many connections from your address.";
                return;
            }

            if (responseCode == 10) {
                statusLineOne = "Unable to connect.";
                statusLineTwo = "Bad session id.";
                return;
            }

            if (responseCode == 12) {
                statusLineOne = "You need a members account to login to this world.";
                statusLineTwo = "Please subscribe, or use a different world.";
                return;
            }

            if (responseCode == 13) {
                statusLineOne = "Could not complete login.";
                statusLineTwo = "Please try using a different world.";
                return;
            }

            if (responseCode == 14) {
                statusLineOne = "The server is being updated.";
                statusLineTwo = "Please wait 1 minute and try again.";
                return;
            }

            if (responseCode == 15) {
                loggedIn = true;
                outBuffer.currentPosition = 0;
                buffer.currentPosition = 0;
                opcode = -1;
                lastOpcode = -1;
                secondLastOpcode = -1;
                thirdLastOpcode = -1;
                packetSize = 0;
                timeoutCounter = 0;
                systemUpdateTime = 0;
                menuActionRow = 0;
                menuOpen = false;
                aLong1229 = System.currentTimeMillis();
                return;
            }

            if (responseCode == 16) {
                statusLineOne = "Login attempts exceeded.";
                statusLineTwo = "Please wait 1 minute and try again.";
                return;
            }

            if (responseCode == 17) {
                statusLineOne = "You are standing in a members-only area.";
                statusLineTwo = "To play on this world move to a free area first";
                return;
            }

            if (responseCode == 18) {
                statusLineOne = "Account locked as we suspect it has been stolen.";
                statusLineTwo = "Press 'recover a locked account' on front page.";
                return;
            }

            if (responseCode == 20) {
                statusLineOne = "Invalid loginserver requested";
                statusLineTwo = "Please try using a different world.";
                return;
            }

            if (responseCode == 21) {
                int time = gameConnection.read();

                for (time += 3; time >= 0; time--) {
                    statusLineOne = "You have only just left another world";
                    statusLineTwo = "Your profile will be transferred in: " + time;

                    drawLoginScreen(true);

                    try {
                        Thread.sleep(1200L);
                    } catch (Exception ignored) {
                    }
                }

                login(username, password, reconnecting);
                return;
            }

            if (responseCode == 22) {
                statusLineOne = "Malformed login packet.";
                statusLineTwo = "Please try again.";
                return;
            }

            if (responseCode == 23) {
                statusLineOne = "No reply from loginserver.";
                statusLineTwo = "Please try again.";
                return;
            }

            if (responseCode == 24) {
                statusLineOne = "Error loading your profile.";
                statusLineTwo = "Please contact customer support.";
                return;
            }

            if (responseCode == 25) {
                statusLineOne = "Unexpected loginserver response.";
                statusLineTwo = "Please try using a different world.";
                return;
            }

            if (responseCode == 26) {
                statusLineOne = "This computers address has been blocked";
                statusLineTwo = "as it was used to break our rules";
                return;
            }

            if (responseCode == -1) {
                if (initialResponseCode == 0) {
                    if (anInt850 < 2) {
                        try {
                            Thread.sleep(2000L);
                        } catch (Exception ignored) {
                        }

                        anInt850++;

                        login(username, password, reconnecting);
                        return;
                    } else {
                        statusLineOne = "No response from loginserver";
                        statusLineTwo = "Please wait 1 minute and try again.";
                        return;
                    }
                } else {
                    statusLineOne = "No response from server";
                    statusLineTwo = "Please try using a different world.";
                    return;
                }
            } else {
                System.out.println("response:" + responseCode);

                statusLineOne = "Unexpected server response";
                statusLineTwo = "Please try using a different world.";
                return;
            }
        } catch (IOException ex) {
            statusLineOne = "";
        }

        statusLineTwo = "Error connecting to server.";
    }

    private boolean method80(int dstY, int j, int dstX, int l) {
        int i1 = l >> 14 & 0x7fff;
        int j1 = currentScene.method271(plane, dstX, dstY, l);
        if (j1 == -1)
            return false;
        int objectType = j1 & 0x1f;
        int l1 = j1 >> 6 & 3;
        if (objectType == 10 || objectType == 11 || objectType == 22) {
            GameObjectDefinition class47 = GameObjectDefinition.getDefinition(i1);
            int i2;
            int j2;
            if (l1 == 0 || l1 == 2) {
                i2 = class47.sizeX;
                j2 = class47.sizeY;
            } else {
                i2 = class47.sizeY;
                j2 = class47.sizeX;
            }
            int k2 = class47.anInt764;
            if (l1 != 0)
                k2 = (k2 << l1 & 0xf) + (k2 >> 4 - l1);
            walk(true, false, dstY, localPlayer.pathY[0], i2, j2, 2, 0, dstX, k2, 0,
                    localPlayer.pathX[0]);
        } else {
            walk(true, false, dstY, localPlayer.pathY[0], 0, 0, 2, objectType + 1, dstX, 0, l1,
                    localPlayer.pathX[0]);
        }
        lastClickX = super.clickX;
        lastClickY = super.clickY;
        crossType = 2;
        crossIndex = 0;
        packetSize += j;
        return true;
    }

    private void calculateFlamePositions() { //TODO: Needs more refactoring
        int c = 256;

        for (int x = 10; x < 117; x++) {
            int rand = (int) (Math.random() * 100D);

            if (rand < 50)
                anIntArray1084[x + (c - 2 << 7)] = 255;
        }

        for (int i = 0; i < 100; i++) {
            int x = (int) (Math.random() * 124D) + 2;
            int y = (int) (Math.random() * 128D) + 128;
            int pixel = x + (y << 7);
            anIntArray1084[pixel] = 192;
        }

        for (int y = 1; y < c - 1; y++) {
            for (int x = 1; x < 127; x++) {
                int pixel = x + (y << 7);
                anIntArray1085[pixel] = (anIntArray1084[pixel - 1] + anIntArray1084[pixel + 1] + anIntArray1084[pixel - 128] + anIntArray1084[pixel + 128]) / 4;
            }
        }

        anInt1238 += 128;

        if (anInt1238 > anIntArray1176.length) {
            anInt1238 -= anIntArray1176.length;
            int rand = (int) (Math.random() * 12D);

            method83(titleFlameEmblem[rand], 0);
        }

        for (int y = 1; y < c - 1; y++) {
            for (int x = 1; x < 127; x++) {
                int pixel = x + (y << 7);
                int i4 = anIntArray1085[pixel + 128] - anIntArray1176[pixel + anInt1238 & anIntArray1176.length - 1] / 5;

                if (i4 < 0)
                    i4 = 0;

                anIntArray1084[pixel] = i4;
            }
        }

        for (int i = 0; i < c - 1; i++)
            anIntArray1166[i] = anIntArray1166[i + 1];

        anIntArray1166[c - 1] = (int) (Math.sin((double) pulseCycle / 14D) * 16D + Math.sin((double) pulseCycle / 15D)
                * 14D + Math.sin((double) pulseCycle / 16D) * 12D);

        if (anInt1047 > 0)
            anInt1047 -= 4;
        if (anInt1048 > 0)
            anInt1048 -= 4;
        if (anInt1047 == 0 && anInt1048 == 0) {
            int rand = (int) (Math.random() * 2000D);

            if (rand == 0)
                anInt1047 = 1024;
            if (rand == 1)
                anInt1048 = 1024;
        }
    }

    private void method82(ActorDefinition actorDefinition, int i, int j, int k) {
        if (menuActionRow >= 400)
            return;
        if (actorDefinition.childrenIds != null)
            actorDefinition = actorDefinition.getChildDefinition();
        if (actorDefinition == null)
            return;
        if (!actorDefinition.clickable)
            return;
        String s = actorDefinition.name;
        if (actorDefinition.combatLevel != 0)
            s = s + getCombatLevelColour(localPlayer.combatLevel, actorDefinition.combatLevel) + " (level-" + actorDefinition.combatLevel + ")";
        if (itemSelected == 1) {
            menuActionTexts[menuActionRow] = "Use " + selectedItemName + " with @yel@" + s;
            menuActionTypes[menuActionRow] = 347;
            selectedMenuActions[menuActionRow] = k;
            firstMenuOperand[menuActionRow] = j;
            secondMenuOperand[menuActionRow] = i;
            menuActionRow++;
            return;
        }
        if (widgetSelected == 1) {
            if ((anInt1173 & 2) == 2) {
                menuActionTexts[menuActionRow] = selectedWidgetName + " @yel@" + s;
                menuActionTypes[menuActionRow] = 67;
                selectedMenuActions[menuActionRow] = k;
                firstMenuOperand[menuActionRow] = j;
                secondMenuOperand[menuActionRow] = i;
                menuActionRow++;
            }
        } else {
            if (actorDefinition.actions != null) {
                for (int l = 4; l >= 0; l--)
                    if (actorDefinition.actions[l] != null && !actorDefinition.actions[l].equalsIgnoreCase("attack")) {
                        menuActionTexts[menuActionRow] = actorDefinition.actions[l] + " @yel@" + s;
                        if (l == 0)
                            menuActionTypes[menuActionRow] = 318;
                        if (l == 1)
                            menuActionTypes[menuActionRow] = 921;
                        if (l == 2)
                            menuActionTypes[menuActionRow] = 118;
                        if (l == 3)
                            menuActionTypes[menuActionRow] = 553;
                        if (l == 4)
                            menuActionTypes[menuActionRow] = 432;
                        selectedMenuActions[menuActionRow] = k;
                        firstMenuOperand[menuActionRow] = j;
                        secondMenuOperand[menuActionRow] = i;
                        menuActionRow++;
                    }

            }
            if (actorDefinition.actions != null) {
                for (int i1 = 4; i1 >= 0; i1--)
                    if (actorDefinition.actions[i1] != null && actorDefinition.actions[i1].equalsIgnoreCase("attack")) {
                        char c = '\0';
                        if (actorDefinition.combatLevel > localPlayer.combatLevel)
                            c = '\u07D0';
                        menuActionTexts[menuActionRow] = actorDefinition.actions[i1] + " @yel@" + s;
                        if (i1 == 0)
                            menuActionTypes[menuActionRow] = 318 + c;
                        if (i1 == 1)
                            menuActionTypes[menuActionRow] = 921 + c;
                        if (i1 == 2)
                            menuActionTypes[menuActionRow] = 118 + c;
                        if (i1 == 3)
                            menuActionTypes[menuActionRow] = 553 + c;
                        if (i1 == 4)
                            menuActionTypes[menuActionRow] = 432 + c;
                        selectedMenuActions[menuActionRow] = k;
                        firstMenuOperand[menuActionRow] = j;
                        secondMenuOperand[menuActionRow] = i;
                        menuActionRow++;
                    }

            }
            menuActionTexts[menuActionRow] = "Examine @yel@" + s;
            menuActionTypes[menuActionRow] = 1668;
            selectedMenuActions[menuActionRow] = k;
            firstMenuOperand[menuActionRow] = j;
            secondMenuOperand[menuActionRow] = i;
            menuActionRow++;
        }
    }

    private void method83(IndexedImage class50_sub1_sub1_sub3, int i) {
        packetSize += i;
        int j = 256;
        for (int k = 0; k < anIntArray1176.length; k++)
            anIntArray1176[k] = 0;

        for (int l = 0; l < 5000; l++) {
            int i1 = (int) (Math.random() * 128D * (double) j);
            anIntArray1176[i1] = (int) (Math.random() * 256D);
        }

        for (int j1 = 0; j1 < 20; j1++) {
            for (int k1 = 1; k1 < j - 1; k1++) {
                for (int i2 = 1; i2 < 127; i2++) {
                    int k2 = i2 + (k1 << 7);
                    anIntArray1177[k2] = (anIntArray1176[k2 - 1] + anIntArray1176[k2 + 1] + anIntArray1176[k2 - 128] + anIntArray1176[k2 + 128]) / 4;
                }

            }

            int ai[] = anIntArray1176;
            anIntArray1176 = anIntArray1177;
            anIntArray1177 = ai;
        }

        if (class50_sub1_sub1_sub3 != null) {
            int l1 = 0;
            for (int j2 = 0; j2 < class50_sub1_sub1_sub3.height; j2++) {
                for (int l2 = 0; l2 < class50_sub1_sub1_sub3.width; l2++)
                    if (class50_sub1_sub1_sub3.pixels[l1++] != 0) {
                        int i3 = l2 + 16 + class50_sub1_sub1_sub3.xDrawOffset;
                        int j3 = j2 + 16 + class50_sub1_sub1_sub3.yDrawOffset;
                        int k3 = i3 + (j3 << 7);
                        anIntArray1176[k3] = 0;
                    }

            }

        }
    }

    private void renderChatbox() {
        chatboxProducingGraphicsBuffer.createRasterizer();

        Rasterizer3D.lineOffsets = chatboxLineOffsets;

        chatboxBackgroundImage.drawImage(0, 0);

        if (messagePromptRaised) {
            fontBold.drawStringLeft(chatboxInputMessage, 239, 40, 0);
            fontBold.drawStringLeft(chatMessage + "*", 239, 60, 128);
        } else if (inputType == 1) {
            fontBold.drawStringLeft("Enter amount:", 239, 40, 0);
            fontBold.drawStringLeft(inputInputMessage + "*", 239, 60, 128);
        } else if (inputType == 2) {
            fontBold.drawStringLeft("Enter name:", 239, 40, 0);
            fontBold.drawStringLeft(inputInputMessage + "*", 239, 60, 128);
        } else if (inputType == 3) {
            if (!inputInputMessage.equals(aString861)) {
                method14(inputInputMessage, 2);
                aString861 = inputInputMessage;
            }

            TypeFace typeFace = fontNormal;

            Rasterizer.setCoordinates(0, 0, 77, 463);

            for (int i = 0; i < anInt862; i++) {
                int y = (18 + i * 14) - anInt865;

                if (y > 0 && y < 110)
                    typeFace.drawStringLeft(aStringArray863[i], 239, y, 0);
            }

            Rasterizer.resetCoordinates();

            if (anInt862 > 5)
                method56(true, anInt865, 463, 77, anInt862 * 14 + 7, 0);

            if (inputInputMessage.length() == 0)
                fontBold.drawStringLeft("Enter object name", 239, 40, 255);
            else if (anInt862 == 0)
                fontBold.drawStringLeft("No matching objects found, please shorten search", 239, 40, 0);

            typeFace.drawStringLeft(inputInputMessage + "*", 239, 90, 0);
            Rasterizer.drawHorizontalLine(0, 77, 479, 0);
        } else if (clickToContinueString != null) {
            fontBold.drawStringLeft(clickToContinueString, 239, 40, 0);
            fontBold.drawStringLeft("Click to continue", 239, 60, 128);
        } else if (backDialogueId != -1) {
            drawInterface(0, 0, Widget.forId(backDialogueId), 0, 8);
        } else if (dialogueId != -1) {
            drawInterface(0, 0, Widget.forId(dialogueId), 0, 8);
        } else {
            TypeFace typeFace = fontNormal;
            int line = 0;

            Rasterizer.setCoordinates(0, 0, 77, 463);

            for (int i = 0; i < 100; i++) {
                if (chatMessages[i] != null) {
                    String name = chatPlayerNames[i];
                    int type = chatTypes[i];
                    int y = (70 - line * 14) + chatboxScroll;
                    byte privilege = 0;

                    if (name != null && name.startsWith("@cr1@")) {
                        name = name.substring(5);
                        privilege = 1;
                    }

                    if (name != null && name.startsWith("@cr2@")) {
                        name = name.substring(5);
                        privilege = 2;
                    }

                    if (type == 0) {
                        if (y > 0 && y < 110)
                            typeFace.drawString(chatMessages[i], 4, y, 0);

                        line++;
                    }

                    if ((type == 1 || type == 2) && (type == 1 || publicChatMode == 0 || publicChatMode == 1 && method148(13292, name))) {
                        if (y > 0 && y < 110) {
                            int x = 4;

                            if (privilege == 1) {
                                moderatorIcon[0].drawImage(x, y - 12);
                                x += 14;
                            }

                            if (privilege == 2) {
                                moderatorIcon[1].drawImage(x, y - 12);
                                x += 14;
                            }

                            typeFace.drawString(name + ":", x, y, 0);

                            x += typeFace.getStringEffectWidth(name) + 8;

                            typeFace.drawString(chatMessages[i], x, y, 255);
                        }

                        line++;
                    }

                    if ((type == 3 || type == 7) && anInt1223 == 0
                            && (type == 7 || privateChatMode == 0 || privateChatMode == 1 && method148(13292, name))) {
                        if (y > 0 && y < 110) {
                            int x = 4;

                            typeFace.drawString("From", x, y, 0);

                            x += typeFace.getStringEffectWidth("From ");

                            if (privilege == 1) {
                                moderatorIcon[0].drawImage(x, y - 12);
                                x += 14;
                            }

                            if (privilege == 2) {
                                moderatorIcon[1].drawImage(x, y - 12);
                                x += 14;
                            }

                            typeFace.drawString(name + ":", x, y, 0);

                            x += typeFace.getStringEffectWidth(name) + 8;

                            typeFace.drawString(chatMessages[i], x, y, 0x800000);
                        }

                        line++;
                    }

                    if (type == 4 && (tradeMode == 0 || tradeMode == 1 && method148(13292, name))) {
                        if (y > 0 && y < 110)
                            typeFace.drawString(name + " " + chatMessages[i], 4, y, 0x800080);

                        line++;
                    }

                    if (type == 5 && anInt1223 == 0 && privateChatMode < 2) {
                        if (y > 0 && y < 110)
                            typeFace.drawString(chatMessages[i], 4, y, 0x800000);

                        line++;
                    }

                    if (type == 6 && anInt1223 == 0 && privateChatMode < 2) {
                        if (y > 0 && y < 110) {
                            typeFace.drawString("To " + name + ":", 4, y, 0);
                            typeFace.drawString(chatMessages[i], 12 + typeFace.getStringEffectWidth("To " + name), y, 0x800000);
                        }

                        line++;
                    }

                    if (type == 8 && (tradeMode == 0 || tradeMode == 1 && method148(13292, name))) {
                        if (y > 0 && y < 110)
                            typeFace.drawString(name + " " + chatMessages[i], 4, y, 0x7e3200);

                        line++;
                    }
                }
            }

            Rasterizer.resetCoordinates();

            chatboxScrollMax = line * 14 + 7;

            if (chatboxScrollMax < 78)
                chatboxScrollMax = 78;

            method56(true, chatboxScrollMax - chatboxScroll - 77, 463, 77, chatboxScrollMax, 0);

            String name;

            if (localPlayer != null && localPlayer.playerName != null)
                name = localPlayer.playerName;
            else
                name = TextUtils.formatName(username);

            typeFace.drawString(name + ":", 4, 90, 0);
            typeFace.drawString(chatboxInput + "*", 6 + typeFace.getStringEffectWidth(name + ": "), 90, 255);
            Rasterizer.drawHorizontalLine(0, 77, 479, 0);
        }

        if (menuOpen && menuScreenArea == 2)
            drawMenu();

        chatboxProducingGraphicsBuffer.drawGraphics(17, 357, super.gameGraphics);
        gameScreenImageProducer.createRasterizer();

        Rasterizer3D.lineOffsets = viewportOffsets;
    }

    private void processActorOverheadText() {
        for (int i = -1; i < localPlayerCount; i++) {
            int index = i == -1 ? thisPlayerId : playerList[i];
            Player player = players[index];

            if (player != null && player.textCycle > 0) {
                player.textCycle--;

                if (player.textCycle == 0)
                    player.forcedChat = null;
            }
        }

        for (int i = 0; i < anInt1133; i++) {
            int index = anIntArray1134[i];
            Npc npc = npcs[index];

            if (npc != null && npc.textCycle > 0) {
                npc.textCycle--;

                if (npc.textCycle == 0)
                    npc.forcedChat = null;
            }
        }
    }

    private void requestArchiveCrcs() {
        int reconnectionDelay = 5;
        int attempts = 0;
        archiveHashes[8] = 0;

        while (archiveHashes[8] == 0) {
            String error = "Unknown problem";

            drawLoadingText(20, "Connecting to web server");

            try {
                DataInputStream stream = openJaggrabStream("crc" + (int) (Math.random() * 99999999D) + "-" + 377);
                Buffer jaggrab = new Buffer(new byte[40]);

                stream.readFully(jaggrab.buffer, 0, 40);
                stream.close();

                for (int i = 0; i < 9; i++)
                    archiveHashes[i] = jaggrab.getIntBE();

                int expectedCrc = jaggrab.getIntBE();
                int calculatedCrc = 1234;

                for (int i = 0; i < 9; i++)
                    calculatedCrc = (calculatedCrc << 1) + archiveHashes[i];

                if (expectedCrc != calculatedCrc) {
                    error = "Checksum problem";
                    archiveHashes[8] = 0;
                }
            } catch (EOFException _ex) {
                error = "EOF problem";
                archiveHashes[8] = 0;
            } catch (IOException _ex) {
                error = "Connection problem";
                archiveHashes[8] = 0;
            } catch (Exception _ex) {
                error = "Logic problem";
                archiveHashes[8] = 0;

                if (!SignLink.reportError)
                    return;
            }

            if (archiveHashes[8] == 0) {
                attempts++;

                for (int time = reconnectionDelay; time > 0; time--) {
                    if (attempts >= 10) {
                        drawLoadingText(10, "Game updated - please reload page");

                        time = 10;
                    } else {
                        drawLoadingText(10, error + " - Will retry in " + time + " secs.");
                    }

                    try {
                        Thread.sleep(1000L);
                    } catch (Exception ignored) {
                    }
                }

                reconnectionDelay *= 2;

                if (reconnectionDelay > 60)
                    reconnectionDelay = 60;

                useJaggrab = !useJaggrab;
            }
        }
    }

    private void renderMinimap() {
        aClass18_1157.createRasterizer();

        if (minimapState == 2) {
            byte[] mmBackgroundPixels = minimapBackgroundImage.pixels;
            int[] rasterPixels = Rasterizer.pixels;
            int pixelCount = mmBackgroundPixels.length;

            for (int i = 0; i < pixelCount; i++)
                if (mmBackgroundPixels[i] == 0)
                    rasterPixels[i] = 0;

            minimapCompass.shapeImageToPixels(0, 33, 25, 33, anIntArray1286, 0, cameraHorizontal, 256,
                    anIntArray1180, 25);
            gameScreenImageProducer.createRasterizer();

            Rasterizer3D.lineOffsets = viewportOffsets;
            return;
        }

        int angle = cameraHorizontal + anInt916 & 0x7ff;
        int centerX = 48 + localPlayer.worldX / 32;
        int centerY = 464 - localPlayer.worldY / 32;

        minimapImage.shapeImageToPixels(5, 151, centerX, 146, anIntArray920,
                25, angle, 256 + anInt1233, anIntArray1019, centerY);
        minimapCompass.shapeImageToPixels(0, 33, 25, 33, anIntArray1286,
                0, cameraHorizontal, 256, anIntArray1180, 25);

        for (int i = 0; i < minimapHintCount; i++) {
            int hintX = (minimapHintX[i] * 4 + 2) - localPlayer.worldX / 32;
            int hintY = (minimapHintY[i] * 4 + 2) - localPlayer.worldY / 32;

            drawOnMinimap(minimapHint[i], hintX, hintY);
        }

        for (int x = 0; x < 104; x++) {
            for (int y = 0; y < 104; y++) {
                LinkedList itemList = groundItems[plane][x][y];

                if (itemList != null) {
                    int itemX = (x * 4 + 2) - localPlayer.worldX / 32;
                    int itemY = (y * 4 + 2) - localPlayer.worldY / 32;

                    drawOnMinimap(mapdotItem, itemX, itemY);
                }
            }

        }

        for (int i = 0; i < anInt1133; i++) {
            Npc npc = npcs[anIntArray1134[i]];

            if (npc != null && npc.isVisible()) {
                ActorDefinition definition = npc.npcDefinition;

                if (definition.childrenIds != null)
                    definition = definition.getChildDefinition();

                if (definition != null && definition.minimapVisible && definition.clickable) {
                    int npcX = npc.worldX / 32 - localPlayer.worldX / 32;
                    int npcY = npc.worldY / 32 - localPlayer.worldY / 32;

                    drawOnMinimap(mapdotActor, npcX, npcY);
                }
            }
        }

        for (int i = 0; i < localPlayerCount; i++) {
            Player player = players[playerList[i]];

            if (player != null && player.isVisible()) {
                int playerX = player.worldX / 32 - localPlayer.worldX / 32;
                int playerY = player.worldY / 32 - localPlayer.worldY / 32;
                long name = TextUtils.nameToLong(player.playerName);
                boolean isFriend = false;
                boolean isTeammate = false;

                for (int x = 0; x < friendsCount; x++) {
                    if (name != friends[x] || friendWorlds[x] == 0)
                        continue;

                    isFriend = true;
                    break;
                }

                if (localPlayer.teamId != 0 && player.teamId != 0 && localPlayer.teamId == player.teamId)
                    isTeammate = true;

                if (isFriend)
                    drawOnMinimap(mapdotFriend, playerX, playerY);
                else if (isTeammate)
                    drawOnMinimap(mapdotTeammate, playerX, playerY);
                else
                    drawOnMinimap(mapdotPlayer, playerX, playerY);
            }
        }

        if (headIconDrawType != 0 && pulseCycle % 20 < 10) {
            if (headIconDrawType == 1 && anInt1226 >= 0 && anInt1226 < npcs.length) {
                Npc npc = npcs[anInt1226];

                if (npc != null) {
                    int npcX = npc.worldX / 32 - localPlayer.worldX / 32;
                    int npcY = npc.worldY / 32 - localPlayer.worldY / 32;

                    drawMinimap(aClass50_Sub1_Sub1_Sub1_1037, npcX, npcY);
                }
            }

            if (headIconDrawType == 2) {
                int hintX = ((anInt844 - nextTopLeftTileX) * 4 + 2) - localPlayer.worldX / 32;
                int hintY = ((anInt845 - nextTopRightTileY) * 4 + 2) - localPlayer.worldY / 32;

                drawMinimap(aClass50_Sub1_Sub1_Sub1_1037, hintX, hintY);
            }

            if (headIconDrawType == 10 && otherPlayerId >= 0 && otherPlayerId < players.length) {
                Player player = players[otherPlayerId];

                if (player != null) {
                    int playerX = player.worldX / 32 - localPlayer.worldX / 32;
                    int playerY = player.worldY / 32 - localPlayer.worldY / 32;

                    drawMinimap(aClass50_Sub1_Sub1_Sub1_1037, playerX, playerY);
                }
            }
        }

        if (destinationX != 0) {
            int flagX = (destinationX * 4 + 2) - localPlayer.worldX / 32;
            int flagY = (destinationY * 4 + 2) - localPlayer.worldY / 32;

            drawOnMinimap(mapFlagMarker, flagX, flagY);
        }

        Rasterizer.drawFilledRectangle(97, 78, 3, 3, 0xffffff);
        gameScreenImageProducer.createRasterizer();

        Rasterizer3D.lineOffsets = viewportOffsets;
    }

    public URL getCodeBase() {
        if (SignLink.applet != null)
            return SignLink.applet.getCodeBase();


        try {
            return new URL("http://" + Configuration.SERVER_ADDRESS + ":" + (Configuration.HTTP_PORT + portOffset));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean method88(int tickDelta, int interfaceId) {
        boolean flag = false;
        Widget widget = Widget.forId(interfaceId);
        for (int k = 0; k < widget.children.length; k++) {
            if (widget.children[k] == -1)
                break;
            Widget widgetChild = Widget.forId(widget.children[k]);
            if (widgetChild.type == 0)
                flag |= method88(tickDelta, widgetChild.id);
            if (widgetChild.type == 6 && (widgetChild.disabledAnimation != -1 || widgetChild.enabledAnimation != -1)) {
                boolean flag1 = componentEnabled(widgetChild);
                int enabledState;
                if (flag1)
                    enabledState = widgetChild.enabledAnimation;
                else
                    enabledState = widgetChild.disabledAnimation;
                if (enabledState != -1) {
                    AnimationSequence animationSequence = AnimationSequence.animations[enabledState];
                    for (widgetChild.animationDuration += tickDelta; widgetChild.animationDuration > animationSequence.getFrameLength(widgetChild.animationFrame); ) {
                        widgetChild.animationDuration -= animationSequence.getFrameLength(widgetChild.animationFrame);
                        widgetChild.animationFrame++;
                        if (widgetChild.animationFrame >= animationSequence.frameCount) {
                            widgetChild.animationFrame -= animationSequence.frameStep;
                            if (widgetChild.animationFrame < 0 || widgetChild.animationFrame >= animationSequence.frameCount)
                                widgetChild.animationFrame = 0;
                        }
                        flag = true;
                    }

                }
            }
            if (widgetChild.type == 6 && widgetChild.anInt218 != 0) {
                int l = widgetChild.anInt218 >> 16;
                int j1 = (widgetChild.anInt218 << 16) >> 16;
                l *= tickDelta;
                j1 *= tickDelta;
                widgetChild.rotationX = widgetChild.rotationX + l & 0x7ff;
                widgetChild.rotationY = widgetChild.rotationY + j1 & 0x7ff;
                flag = true;
            }
        }

        return flag;
    }

    private String method89(int i, int j) {
        if (j < 8 || j > 8)
            throw new NullPointerException();
        if (i < 0x3b9ac9ff)
            return String.valueOf(i);
        else
            return "*";
    }

    public void doLogic() {
        if (aBoolean1016 || aBoolean1283 || aBoolean1097)
            return;
        pulseCycle++;
        if (!loggedIn)
            method149(-724);
        else
            processGame();
        method77(false);
    }

    private void processRightClick(int i) {
        if (activeInterfaceType != 0)
            return;
        menuActionTexts[0] = "Cancel";
        menuActionTypes[0] = 1016;
        menuActionRow = 1;
        if (i >= 0)
            anInt1004 = incomingRandom.nextInt();
        if (anInt1053 != -1) {
            anInt915 = 0;
            anInt1315 = 0;
            method66(0, Widget.forId(anInt1053), 0, 0, 0, super.mouseX, 23658, super.mouseY);
            if (anInt915 != anInt1302)
                anInt1302 = anInt915;
            if (anInt1315 != anInt1129)
                anInt1129 = anInt1315;
            return;
        }
        method111(anInt1178);
        anInt915 = 0;
        anInt1315 = 0;
        if (super.mouseX > 4 && super.mouseY > 4 && super.mouseX < 516 && super.mouseY < 338)
            if (openInterfaceId != -1)
                method66(4, Widget.forId(openInterfaceId), 0, 0, 4, super.mouseX, 23658, super.mouseY);
            else
                method43((byte) 7);
        if (anInt915 != anInt1302)
            anInt1302 = anInt915;
        if (anInt1315 != anInt1129)
            anInt1129 = anInt1315;
        anInt915 = 0;
        anInt1315 = 0;
        if (super.mouseX > 553 && super.mouseY > 205 && super.mouseX < 743 && super.mouseY < 466)
            if (openInvOverLayId != -1)
                method66(205, Widget.forId(openInvOverLayId), 1, 0, 553, super.mouseX, 23658, super.mouseY);
            else if (tabInterfaceIDs[currentTabId] != -1)
                method66(205, Widget.forId(tabInterfaceIDs[currentTabId]), 1, 0, 553, super.mouseX, 23658,
                        super.mouseY);
        if (anInt915 != anInt1280) {
            redrawTabArea = true;
            anInt1280 = anInt915;
        }
        if (anInt1315 != anInt1044) {
            redrawTabArea = true;
            anInt1044 = anInt1315;
        }
        anInt915 = 0;
        anInt1315 = 0;
        if (super.mouseX > 17 && super.mouseY > 357 && super.mouseX < 496 && super.mouseY < 453)
            if (backDialogueId != -1)
                method66(357, Widget.forId(backDialogueId), 2, 0, 17, super.mouseX, 23658, super.mouseY);
            else if (dialogueId != -1)
                method66(357, Widget.forId(dialogueId), 3, 0, 17, super.mouseX, 23658, super.mouseY);
            else if (super.mouseY < 434 && super.mouseX < 426 && inputType == 0)
                method113(466, super.mouseX - 17, super.mouseY - 357);
        if ((backDialogueId != -1 || dialogueId != -1) && anInt915 != anInt1106) {
            redrawChatbox = true;
            anInt1106 = anInt915;
        }
        if ((backDialogueId != -1 || dialogueId != -1) && anInt1315 != anInt1284) {
            redrawChatbox = true;
            anInt1284 = anInt1315;
        }
        for (boolean flag = false; !flag; ) {
            flag = true;
            for (int j = 0; j < menuActionRow - 1; j++)
                if (menuActionTypes[j] < 1000 && menuActionTypes[j + 1] > 1000) {
                    String s = menuActionTexts[j];
                    menuActionTexts[j] = menuActionTexts[j + 1];
                    menuActionTexts[j + 1] = s;
                    int k = menuActionTypes[j];
                    menuActionTypes[j] = menuActionTypes[j + 1];
                    menuActionTypes[j + 1] = k;
                    k = firstMenuOperand[j];
                    firstMenuOperand[j] = firstMenuOperand[j + 1];
                    firstMenuOperand[j + 1] = k;
                    k = secondMenuOperand[j];
                    secondMenuOperand[j] = secondMenuOperand[j + 1];
                    secondMenuOperand[j + 1] = k;
                    k = selectedMenuActions[j];
                    selectedMenuActions[j] = selectedMenuActions[j + 1];
                    selectedMenuActions[j + 1] = k;
                    flag = false;
                }

        }

    }


    private void method93(int i) {
        try {
            anInt1276 = -1;
            aClass6_1210.getNodeCount();
            aClass6_1282.getNodeCount();
            Rasterizer3D.method495((byte) 71);
            resetModelCaches();
            currentScene.initToNull();
            System.gc();
            for (int plane = 0; plane < 4; plane++)
                currentCollisionMap[plane].reset();

            for (int i1 = 0; i1 < 4; i1++) {
                for (int l1 = 0; l1 < 104; l1++) {
                    for (int k2 = 0; k2 < 104; k2++)
                        currentSceneTileFlags[i1][l1][k2] = 0;

                }

            }

            Region region = new Region(currentSceneTileFlags, 104, 104, intGroundArray);
            int l2 = terrainData.length;
            outBuffer.putOpcode(40);
            if (!aBoolean1163) {
                for (int j3 = 0; j3 < l2; j3++) {
                    int j4 = (mapCoordinates[j3] >> 8) * 64 - nextTopLeftTileX;
                    int l5 = (mapCoordinates[j3] & 0xff) * 64 - nextTopRightTileY;
                    byte abyte0[] = terrainData[j3];
                    if (abyte0 != null)
                        region.method174(l5, false, (chunkY - 6) * 8, j4, abyte0, (chunkX - 6) * 8,
                                currentCollisionMap);
                }

                for (int k4 = 0; k4 < l2; k4++) {
                    int i6 = (mapCoordinates[k4] >> 8) * 64 - nextTopLeftTileX;
                    int l7 = (mapCoordinates[k4] & 0xff) * 64 - nextTopRightTileY;
                    byte abyte2[] = terrainData[k4];
                    if (abyte2 == null && chunkY < 800)
                        region.initiateVertexHeights(i6, 64, l7, 64);
                }

                outBuffer.putOpcode(40);
                for (int j6 = 0; j6 < l2; j6++) {
                    byte abyte1[] = objectData[j6];
                    if (abyte1 != null) {
                        int l8 = (mapCoordinates[j6] >> 8) * 64 - nextTopLeftTileX;
                        int k9 = (mapCoordinates[j6] & 0xff) * 64 - nextTopRightTileY;
                        region.method179(k9, currentCollisionMap, l8, -571, currentScene, abyte1);
                    }
                }

            }
            if (aBoolean1163) {
                for (int k3 = 0; k3 < 4; k3++) {
                    for (int l4 = 0; l4 < 13; l4++) {
                        for (int k6 = 0; k6 < 13; k6++) {
                            boolean flag = false;
                            int i9 = constructedMapPalette[k3][l4][k6];
                            if (i9 != -1) {
                                int l9 = i9 >> 24 & 3;
                                int j10 = i9 >> 1 & 3;
                                int l10 = i9 >> 14 & 0x3ff;
                                int j11 = i9 >> 3 & 0x7ff;
                                int l11 = (l10 / 8 << 8) + j11 / 8;
                                for (int j12 = 0; j12 < mapCoordinates.length; j12++) {
                                    if (mapCoordinates[j12] != l11 || terrainData[j12] == null)
                                        continue;
                                    region.method168(j10, (j11 & 7) * 8, false, terrainData[j12], k3, l9,
                                            l4 * 8, currentCollisionMap, k6 * 8, (l10 & 7) * 8);
                                    flag = true;
                                    break;
                                }

                            }
                            if (!flag)
                                region.method166(anInt1072, k3, k6 * 8, l4 * 8);
                        }

                    }

                }

                for (int i5 = 0; i5 < 13; i5++) {
                    for (int l6 = 0; l6 < 13; l6++) {
                        int i8 = constructedMapPalette[0][i5][l6];
                        if (i8 == -1)
                            region.initiateVertexHeights(i5 * 8, 8, l6 * 8, 8);
                    }

                }

                outBuffer.putOpcode(40);
                for (int i7 = 0; i7 < 4; i7++) {
                    for (int j8 = 0; j8 < 13; j8++) {
                        for (int j9 = 0; j9 < 13; j9++) {
                            int i10 = constructedMapPalette[i7][j8][j9];
                            if (i10 != -1) {
                                int k10 = i10 >> 24 & 3;
                                int i11 = i10 >> 1 & 3;
                                int k11 = i10 >> 14 & 0x3ff;
                                int i12 = i10 >> 3 & 0x7ff;
                                int k12 = (k11 / 8 << 8) + i12 / 8;
                                for (int l12 = 0; l12 < mapCoordinates.length; l12++) {
                                    if (mapCoordinates[l12] != k12 || objectData[l12] == null)
                                        continue;
                                    region.method172(i7, currentCollisionMap, currentScene, false,
                                            objectData[l12], j9 * 8, i11, (k11 & 7) * 8, j8 * 8,
                                            (i12 & 7) * 8, k10);
                                    break;
                                }

                            }
                        }

                    }

                }

            }
            outBuffer.putOpcode(40);
            region.createRegionScene(currentCollisionMap, currentScene);
            if (gameScreenImageProducer != null) {
                gameScreenImageProducer.createRasterizer();
                Rasterizer3D.lineOffsets = viewportOffsets;
            }
            outBuffer.putOpcode(40);
            int l3 = Region.lowestPlane;
            if (l3 > plane)
                l3 = plane;
            if (l3 < plane - 1)
                l3 = plane - 1;
            if (lowMemory)
                currentScene.setHeightLevel(Region.lowestPlane);
            else
                currentScene.setHeightLevel(0);
            for (int j5 = 0; j5 < 104; j5++) {
                for (int j7 = 0; j7 < 104; j7++)
                    processGroundItems(j5, j7);

            }

            method18((byte) 3);
        } catch (Exception exception) {
        }
        GameObjectDefinition.modelCache.removeAll();
        if (super.gameFrame != null) {
            outBuffer.putOpcode(78);
            outBuffer.putIntBE(0x3f008edd);
        }
        if (lowMemory && SignLink.cacheData != null) {
            int k = onDemandRequester.fileCount(0);
            for (int j1 = 0; j1 < k; j1++) {
                int i2 = onDemandRequester.modelId(j1);
                if ((i2 & 0x79) == 0)
                    Model.resetModel(j1);
            }

        }
        System.gc();
        Rasterizer3D.method496(20);
        onDemandRequester.immediateRequestCount();
        int l = (chunkX - 6) / 8 - 1;
        int k1 = (chunkX + 6) / 8 + 1;
        int j2 = (chunkY - 6) / 8 - 1;
        int i3 = (chunkY + 6) / 8 + 1;
        i = 94 / i;
        if (aBoolean1067) {
            l = 49;
            k1 = 50;
            j2 = 49;
            i3 = 50;
        }
        for (int i4 = l; i4 <= k1; i4++) {
            for (int k5 = j2; k5 <= i3; k5++)
                if (i4 == l || i4 == k1 || k5 == j2 || k5 == i3) {
                    int k7 = onDemandRequester.regId(0, i4, k5, 0);
                    if (k7 != -1)
                        onDemandRequester.passiveRequest(k7, 3);
                    int k8 = onDemandRequester.regId(0, i4, k5, 1);
                    if (k8 != -1)
                        onDemandRequester.passiveRequest(k8, 3);
                }

        }

    }

    private void setCameraPosition(int x, int y, int z, int pitch, int yaw) {
        int pitchDifference = 2048 - pitch & 0x7ff;
        int yawDifference = 2048 - yaw & 0x7ff;
        int xOffset = 0;
        int zOffset = 0;
        int yOffset = cameraZoom + pitch * 3;

        if (pitchDifference != 0) {
            int sine = Model.SINE[pitchDifference];
            int cosine = Model.COSINE[pitchDifference];
            int temp = zOffset * cosine - yOffset * sine >> 16;
            yOffset = zOffset * sine + yOffset * cosine >> 16;
            zOffset = temp;
        }

        if (yawDifference != 0) {
            int sine = Model.SINE[yawDifference];
            int cosine = Model.COSINE[yawDifference];
            int temp = yOffset * sine + xOffset * cosine >> 16;
            yOffset = yOffset * cosine - xOffset * sine >> 16;
            xOffset = temp;
        }

        cameraX = x - xOffset;
        cameraZ = z - zOffset;
        cameraY = y - yOffset;
        cameraVerticalRotation = pitch;
        cameraHorizontalRotation = yaw;
    }

    private boolean componentEnabled(Widget widget) {
        if (widget.conditionTypes == null) {
            return false;
        }
        for (int id = 0; id < widget.conditionTypes.length; id++) {
            int value = parseCS1(widget, id);
            int requirement = widget.conditionValues[id];
            if (widget.conditionTypes[id] == 2) {
                if (value >= requirement)
                    return false;
            } else if (widget.conditionTypes[id] == 3) {
                if (value <= requirement)
                    return false;
            } else if (widget.conditionTypes[id] == 4) {
                if (value == requirement)
                    return false;
            } else if (value != requirement)
                return false;
        }

        return true;
    }

    private void updatePlayers(int size, Buffer buffer) {
        removePlayerCount = 0;
        updatedPlayerCount = 0;

        updateLocalPlayerMovement(buffer);
        updateOtherPlayerMovement(buffer);
        addNewPlayers(size, buffer);
        parsePlayerBlocks(buffer);

        for (int i = 0; i < removePlayerCount; i++) {
            int index = removePlayers[i];

            if (players[index].pulseCycle != pulseCycle)
                players[index] = null;
        }

        if (buffer.currentPosition != size) {
            SignLink.reportError("Error packet size mismatch in getplayer coord:" + buffer.currentPosition + " psize:" + size);
            throw new RuntimeException("eek");
        }

        for (int i = 0; i < localPlayerCount; i++) {
            if (players[playerList[i]] == null) {
                SignLink.reportError(username + " null entry in pl list - coord:" + i + " size:" + localPlayerCount);
                throw new RuntimeException("eek");
            }
        }
    }

    private void removeIgnore(long l) {
        try {
            if (l == 0L)
                return;
            for (int i = 0; i < ignoresCount; i++) {
                if (ignores[i] != l)
                    continue;
                ignoresCount--;
                redrawTabArea = true;
                for (int j = i; j < ignoresCount; j++)
                    ignores[j] = ignores[j + 1];

                outBuffer.putOpcode(160);
                outBuffer.putLongBE(l);
                break;
            }

            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.reportError("45745, " + 325 + ", " + l + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

//    public String getParameter(String s) {
//        if (SignLink.applet != null)
//            return SignLink.applet.getParameter(s);
//        else
//            return super.getParameter(s);
//    }

    private void renderFlames() { //TODO: Needs more refactoring
        int c = 256;

        if (anInt1047 > 0) {
            for (int j = 0; j < 256; j++) {
                if (anInt1047 > 768)
                    anIntArray1310[j] = method106(anIntArray1311[j], anIntArray1312[j], 1024 - anInt1047, 8);
                else if (anInt1047 > 256)
                    anIntArray1310[j] = anIntArray1312[j];
                else
                    anIntArray1310[j] = method106(anIntArray1312[j], anIntArray1311[j], 256 - anInt1047, 8);
            }
        } else if (anInt1048 > 0) {
            for (int k = 0; k < 256; k++) {
                if (anInt1048 > 768)
                    anIntArray1310[k] = method106(anIntArray1311[k], anIntArray1313[k], 1024 - anInt1048, 8);
                else if (anInt1048 > 256)
                    anIntArray1310[k] = anIntArray1313[k];
                else
                    anIntArray1310[k] = method106(anIntArray1313[k], anIntArray1311[k], 256 - anInt1048, 8);
            }
        } else {
            System.arraycopy(anIntArray1311, 0, anIntArray1310, 0, 256);
        }

        System.arraycopy(anImageRGB1226.pixels, 0, flameLeftBackground.pixels, 0, 33920);

        int j1 = 0;
        int k1 = 1152;

        for (int l1 = 1; l1 < c - 1; l1++) {
            int i2 = (anIntArray1166[l1] * (c - l1)) / c;
            int k2 = 22 + i2;

            if (k2 < 0)
                k2 = 0;

            j1 += k2;

            for (int i3 = k2; i3 < 128; i3++) {
                int k3 = anIntArray1084[j1++];

                if (k3 != 0) {
                    int i4 = k3;
                    int k4 = 256 - k3;
                    k3 = anIntArray1310[k3];
                    int i5 = flameLeftBackground.pixels[k1];
                    flameLeftBackground.pixels[k1++] = ((k3 & 0xff00ff) * i4 + (i5 & 0xff00ff) * k4 & 0xff00ff00)
                            + ((k3 & 0xff00) * i4 + (i5 & 0xff00) * k4 & 0xff0000) >> 8;
                } else {
                    k1++;
                }
            }

            k1 += k2;
        }

        flameLeftBackground.drawGraphics(0, 0, super.gameGraphics);

        System.arraycopy(anImageRGB1227.pixels, 0, flameRightBackground.pixels, 0, 33920);

        j1 = 0;
        k1 = 1176;

        for (int l2 = 1; l2 < c - 1; l2++) {
            int j3 = (anIntArray1166[l2] * (c - l2)) / c;
            int l3 = 103 - j3;
            k1 += j3;

            for (int j4 = 0; j4 < l3; j4++) {
                int l4 = anIntArray1084[j1++];

                if (l4 != 0) {
                    int j5 = l4;
                    int k5 = 256 - l4;
                    l4 = anIntArray1310[l4];
                    int l5 = flameRightBackground.pixels[k1];
                    flameRightBackground.pixels[k1++] = ((l4 & 0xff00ff) * j5 + (l5 & 0xff00ff) * k5 & 0xff00ff00)
                            + ((l4 & 0xff00) * j5 + (l5 & 0xff00) * k5 & 0xff0000) >> 8;
                } else {
                    k1++;
                }
            }

            j1 += 128 - l3;
            k1 += 128 - l3 - j3;
        }

        flameRightBackground.drawGraphics(637, 0, super.gameGraphics);
    }

    private void method100(int i) {
        for (int j = -1; j < localPlayerCount; j++) {
            int k;
            if (j == -1)
                k = thisPlayerId;
            else
                k = playerList[j];
            Player class50_sub1_sub4_sub3_sub2 = players[k];
            if (class50_sub1_sub4_sub3_sub2 != null)
                method68(1, (byte) -97, class50_sub1_sub4_sub3_sub2);
        }

        if (i < anInt1222 || i > anInt1222) {
            for (int l = 1; l > 0; l++) ;
        }
    }

    private void method103(byte byte0, Widget class13) {
        if (byte0 == 2)
            byte0 = 0;
        else
            anInt1004 = -82;
        int i = class13.contentType;
        if (i >= 1 && i <= 100 || i >= 701 && i <= 800) {
            if (i == 1 && friendListStatus == 0) {
                class13.disabledText = "Loading friend list";
                class13.actionType = 0;
                return;
            }
            if (i == 1 && friendListStatus == 1) {
                class13.disabledText = "Connecting to friendserver";
                class13.actionType = 0;
                return;
            }
            if (i == 2 && friendListStatus != 2) {
                class13.disabledText = "Please wait...";
                class13.actionType = 0;
                return;
            }
            int j = friendsCount;
            if (friendListStatus != 2)
                j = 0;
            if (i > 700)
                i -= 601;
            else
                i--;
            if (i >= j) {
                class13.disabledText = "";
                class13.actionType = 0;
                return;
            } else {
                class13.disabledText = friendUsernames[i];
                class13.actionType = 1;
                return;
            }
        }
        if (i >= 101 && i <= 200 || i >= 801 && i <= 900) {
            int k = friendsCount;
            if (friendListStatus != 2)
                k = 0;
            if (i > 800)
                i -= 701;
            else
                i -= 101;
            if (i >= k) {
                class13.disabledText = "";
                class13.actionType = 0;
                return;
            }
            if (friendWorlds[i] == 0)
                class13.disabledText = "@red@Offline";
            else if (friendWorlds[i] < 200) {
                if (friendWorlds[i] == world)
                    class13.disabledText = "@gre@World" + (friendWorlds[i] - 9);
                else
                    class13.disabledText = "@yel@World" + (friendWorlds[i] - 9);
            } else if (friendWorlds[i] == world)
                class13.disabledText = "@gre@Classic" + (friendWorlds[i] - 219);
            else
                class13.disabledText = "@yel@Classic" + (friendWorlds[i] - 219);
            class13.actionType = 1;
            return;
        }
        if (i == 203) {
            int l = friendsCount;
            if (friendListStatus != 2)
                l = 0;
            class13.scrollLimit = l * 15 + 20;
            if (class13.scrollLimit <= class13.height)
                class13.scrollLimit = class13.height + 1;
            return;
        }
        if (i >= 401 && i <= 500) {
            if ((i -= 401) == 0 && friendListStatus == 0) {
                class13.disabledText = "Loading ignore list";
                class13.actionType = 0;
                return;
            }
            if (i == 1 && friendListStatus == 0) {
                class13.disabledText = "Please wait...";
                class13.actionType = 0;
                return;
            }
            int i1 = ignoresCount;
            if (friendListStatus == 0)
                i1 = 0;
            if (i >= i1) {
                class13.disabledText = "";
                class13.actionType = 0;
                return;
            } else {
                class13.disabledText = TextUtils.formatName(TextUtils.longToName(ignores[i]));
                class13.actionType = 1;
                return;
            }
        }
        if (i == 503) {
            class13.scrollLimit = ignoresCount * 15 + 20;
            if (class13.scrollLimit <= class13.height)
                class13.scrollLimit = class13.height + 1;
            return;
        }
        if (i == 327) {
            class13.rotationX = 150;
            class13.rotationY = (int) (Math.sin((double) pulseCycle / 40D) * 256D) & 0x7ff;
            if (aBoolean1277) {
                for (int j1 = 0; j1 < 7; j1++) {
                    int i2 = characterEditIdentityKits[j1];
                    if (i2 >= 0 && !IdentityKit.cache[i2].isBodyModelCached())
                        return;
                }

                aBoolean1277 = false;
                Model aclass50_sub1_sub4_sub4[] = new Model[7];
                int j2 = 0;
                for (int k2 = 0; k2 < 7; k2++) {
                    int l2 = characterEditIdentityKits[k2];
                    if (l2 >= 0)
                        aclass50_sub1_sub4_sub4[j2++] = IdentityKit.cache[l2].getBodyModel();
                }

                Model class50_sub1_sub4_sub4 = new Model(j2, aclass50_sub1_sub4_sub4);
                for (int i3 = 0; i3 < 5; i3++)
                    if (characterEditColors[i3] != 0) {
                        class50_sub1_sub4_sub4.replaceColor(playerColours[i3][0],
                                playerColours[i3][characterEditColors[i3]]);
                        if (i3 == 1)
                            class50_sub1_sub4_sub4.replaceColor(SKIN_COLOURS[0], SKIN_COLOURS[characterEditColors[i3]]);
                    }

                class50_sub1_sub4_sub4.createBones();
                class50_sub1_sub4_sub4.applyTransform(
                        AnimationSequence.animations[localPlayer.idleAnimation].getPrimaryFrame[0]);
                class50_sub1_sub4_sub4.applyLighting(64, 850, -30, -50, -30, true);
                class13.modelType = 5;
                class13.modelId = 0;
                Widget.setModel(5, class50_sub1_sub4_sub4, 0);
            }
            return;
        }
        if (i == 324) {
            if (aClass50_Sub1_Sub1_Sub1_1102 == null) {
                aClass50_Sub1_Sub1_Sub1_1102 = class13.disabledImage;
                aClass50_Sub1_Sub1_Sub1_1103 = class13.enabledImage;
            }
            if (characterEditChangeGenger) {
                class13.disabledImage = aClass50_Sub1_Sub1_Sub1_1103;
                return;
            } else {
                class13.disabledImage = aClass50_Sub1_Sub1_Sub1_1102;
                return;
            }
        }
        if (i == 325) {
            if (aClass50_Sub1_Sub1_Sub1_1102 == null) {
                aClass50_Sub1_Sub1_Sub1_1102 = class13.disabledImage;
                aClass50_Sub1_Sub1_Sub1_1103 = class13.enabledImage;
            }
            if (characterEditChangeGenger) {
                class13.disabledImage = aClass50_Sub1_Sub1_Sub1_1102;
                return;
            } else {
                class13.disabledImage = aClass50_Sub1_Sub1_Sub1_1103;
                return;
            }
        }
        if (i == 600) {
            class13.disabledText = reportedName;
            if (pulseCycle % 20 < 10) {
                class13.disabledText += "|";
                return;
            } else {
                class13.disabledText += " ";
                return;
            }
        }
        if (i == 620)
            if (playerRights >= 1) {
                if (reportMutePlayer) {
                    class13.disabledColor = 0xff0000;
                    class13.disabledText = "Moderator option: Mute player for 48 hours: <ON>";
                } else {
                    class13.disabledColor = 0xffffff;
                    class13.disabledText = "Moderator option: Mute player for 48 hours: <OFF>";
                }
            } else {
                class13.disabledText = "";
            }
        if (i == 660) {
            int k1 = anInt1170 - anInt1215;
            String s1;
            if (k1 <= 0)
                s1 = "earlier today";
            else if (k1 == 1)
                s1 = "yesterday";
            else
                s1 = k1 + " days ago";
            class13.disabledText = "You last logged in @red@" + s1 + "@bla@ from: @red@" + SignLink.dns;
        }
        if (i == 661)
            if (anInt1034 == 0)
                class13.disabledText = "\\nYou have not yet set any recovery questions.\\nIt is @lre@strongly@yel@ recommended that you do so.\\n\\nIf you don't you will be @lre@unable to recover your\\n@lre@password@yel@ if you forget it, or it is stolen.";
            else if (anInt1034 <= anInt1170) {
                class13.disabledText = "\\n\\nRecovery Questions Last Set:\\n@gre@" + getDate(anInt1034);
            } else {
                int l1 = (anInt1170 + 14) - anInt1034;
                String s2;
                if (l1 <= 0)
                    s2 = "Earlier today";
                else if (l1 == 1)
                    s2 = "Yesterday";
                else
                    s2 = l1 + " days ago";
                class13.disabledText = s2
                        + " you requested@lre@ new recovery\\n@lre@questions.@yel@ The requested change will occur\\non: @lre@"
                        + getDate(anInt1034)
                        + "\\n\\nIf you do not remember making this request\\ncancel it immediately, and change your password.";
            }
        if (i == 662) {
            String s;
            if (anInt1273 == 0)
                s = "@yel@0 unread messages";
            else if (anInt1273 == 1)
                s = "@gre@1 unread message";
            else
                s = "@gre@" + anInt1273 + " unread messages";
            class13.disabledText = "You have " + s + "\\nin your message centre.";
        }
        if (i == 663)
            if (anInt1083 <= 0 || anInt1083 > anInt1170 + 10)
                class13.disabledText = "Last password change:\\n@gre@Never changed";
            else
                class13.disabledText = "Last password change:\\n@gre@" + getDate(anInt1083);
        if (i == 665)
            if (anInt992 > 2 && !memberServer)
                class13.disabledText = "This is a non-members\\nworld. To enjoy your\\nmembers benefits we\\nrecommend you play on a\\nmembers world instead.";
            else if (anInt992 > 2)
                class13.disabledText = "\\n\\nYou have @gre@" + anInt992 + "@yel@ days of\\nmember credit remaining.";
            else if (anInt992 > 0)
                class13.disabledText = "You have @gre@"
                        + anInt992
                        + "@yel@ days of\\nmember credit remaining.\\n\\n@lre@Credit low! Renew now\\n@lre@to avoid losing members.";
            else
                class13.disabledText = "You are not a member.\\n\\nChoose to subscribe and\\nyou'll get loads of extra\\nbenefits and features.";
        if (i == 667)
            if (anInt992 > 2 && !memberServer)
                class13.disabledText = "To switch to a members-only world:\\n1) Logout and return to the world selection page.\\n2) Choose one of the members world with a gold star next to it's name.\\n\\nIf you prefer you can continue to use this world,\\nbut members only features will be unavailable here.";
            else if (anInt992 > 0)
                class13.disabledText = "To extend or cancel a subscription:\\n1) Logout and return to the frontpage of this website.\\n2)Choose the relevant option from the 'membership' section.\\n\\nNote: If you are a credit card subscriber a top-up payment will\\nautomatically be taken when 3 days credit remain.\\n(unless you cancel your subscription, which can be done at any time.)";
            else
                class13.disabledText = "To initializeApplication a subscripton:\\n1) Logout and return to the frontpage of this website.\\n2) Choose 'Start a new subscription'";
        if (i == 668) {
            if (anInt1034 > anInt1170) {
                class13.disabledText = "To cancel this request:\\n1) Logout and return to the frontpage of this website.\\n2) Choose 'Cancel recovery questions'.";
                return;
            }
            class13.disabledText = "To change your recovery questions:\\n1) Logout and return to the frontpage of this website.\\n2) Choose 'Set new recovery questions'.";
        }
    }

    private String getDate(int time) {
        if (time > anInt1170 + 10) {
            return "Unknown";
        } else {
            long date = ((long) time + 11745L) * 0x5265c00L;
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(new Date(date));

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May",
                    "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

            return day + "-" + monthNames[month] + "-" + year;
        }
    }

    private void updateVarp(int i, int j) {
        packetSize += i;
        int action = Varp.cache[j].anInt712;
        if (action == 0)
            return;
        int config = widgetSettings[j];
        if (action == 1) {
            if (config == 1)
                Rasterizer3D.method501(0.90000000000000002D);
            if (config == 2)
                Rasterizer3D.method501(0.80000000000000004D);
            if (config == 3)
                Rasterizer3D.method501(0.69999999999999996D);
            if (config == 4)
                Rasterizer3D.method501(0.59999999999999998D);
            ItemDefinition.rgbImageCache.removeAll();
            aBoolean1046 = true;
        }
        if (action == 3) {
            boolean flag = musicEnabled;
            if (config == 0) {
                adjustMidiVolume(musicEnabled, (byte) 8, 0);
                musicEnabled = true;
            }
            if (config == 1) {
                adjustMidiVolume(musicEnabled, (byte) 8, -400);
                musicEnabled = true;
            }
            if (config == 2) {
                adjustMidiVolume(musicEnabled, (byte) 8, -800);
                musicEnabled = true;
            }
            if (config == 3) {
                adjustMidiVolume(musicEnabled, (byte) 8, -1200);
                musicEnabled = true;
            }
            if (config == 4)
                musicEnabled = false;
            if (musicEnabled != flag && !lowMemory) {
                if (musicEnabled) {
                    nextSong = currentSong;
                    songChanging = true;
                    onDemandRequester.request(2, nextSong);
                } else {
                    stopMidi();
                }
                previousSong = 0;
            }
        }
        if (action == 4) {
            SoundPlayer.setVolume(config);
            if (config == 0) {
                aBoolean1301 = true;
                setWaveVolume(0);
            }
            if (config == 1) {
                aBoolean1301 = true;
                setWaveVolume(-400);
            }
            if (config == 2) {
                aBoolean1301 = true;
                setWaveVolume(-800);
            }
            if (config == 3) {
                aBoolean1301 = true;
                setWaveVolume(-1200);
            }
            if (config == 4)
                aBoolean1301 = false;
        }
        if (action == 5)
            anInt1300 = config;
        if (action == 6)
            anInt998 = config;
        if (action == 8) {
            anInt1223 = config;
            redrawChatbox = true;
        }
        if (action == 9)
            anInt955 = config;
    }

    private int method106(int i, int j, int k, int l) {
        if (l < 8 || l > 8)
            outBuffer.putByte(235);
        int i1 = 256 - k;
        return ((i & 0xff00ff) * i1 + (j & 0xff00ff) * k & 0xff00ff00)
                + ((i & 0xff00) * i1 + (j & 0xff00) * k & 0xff0000) >> 8;
    }

    private void setTutorialIslandFlag() {
        int x = (localPlayer.worldX >> 7) + nextTopLeftTileX;
        int y = (localPlayer.worldY >> 7) + nextTopRightTileY;
        inTutorialIsland = false;

        if (x >= 3053 && x <= 3156 && y >= 3056 && y <= 3136)
            inTutorialIsland = true;
        if (x >= 3072 && x <= 3118 && y >= 9492 && y <= 9535)
            inTutorialIsland = true;
        if (inTutorialIsland && x >= 3139 && x <= 3199 && y >= 3008 && y <= 3062)
            inTutorialIsland = false;
    }

    private void determineMenuSize() {
        int width = fontBold.getStringEffectWidth("Choose Option");

        for (int i = 0; i < menuActionRow; i++) {
            int rowWidth = fontBold.getStringEffectWidth(menuActionTexts[i]);

            if (rowWidth > width)
                width = rowWidth;
        }

        width += 8;
        int height = 15 * menuActionRow + 21;

        if (super.clickX > 4 && super.clickY > 4 && super.clickX < 516 && super.clickY < 338) {
            int x = super.clickX - 4 - width / 2;

            if (x + width > 512)
                x = 512 - width;
            if (x < 0)
                x = 0;

            int y = super.clickY - 4;

            if (y + height > 334)
                y = 334 - height;
            if (y < 0)
                y = 0;

            menuOpen = true;
            menuScreenArea = 0;
            menuOffsetX = x;
            menuOffsetY = y;
            anInt1307 = width;
            anInt1308 = height + 1;
        }

        if (super.clickX > 553 && super.clickY > 205 && super.clickX < 743 && super.clickY < 466) {
            int x = super.clickX - 553 - width / 2;

            if (x < 0)
                x = 0;
            else if (x + width > 190)
                x = 190 - width;

            int y = super.clickY - 205;

            if (y < 0)
                y = 0;
            else if (y + height > 261)
                y = 261 - height;

            menuOpen = true;
            menuScreenArea = 1;
            menuOffsetX = x;
            menuOffsetY = y;
            anInt1307 = width;
            anInt1308 = height + 1;
        }

        if (super.clickX > 17 && super.clickY > 357 && super.clickX < 496 && super.clickY < 453) {
            int x = super.clickX - 17 - width / 2;

            if (x < 0)
                x = 0;
            else if (x + width > 479)
                x = 479 - width;

            int y = super.clickY - 357;

            if (y < 0)
                y = 0;
            else if (y + height > 96)
                y = 96 - height;

            menuOpen = true;
            menuScreenArea = 2;
            menuOffsetX = x;
            menuOffsetY = y;
            anInt1307 = width;
            anInt1308 = height + 1;
        }
    }

    private void draw3dScreen() {
        renderSplitPrivateMessages();

        if (crossType == 1)
            cursorCross[crossIndex / 100].drawImage(lastClickX - 8 - 4, lastClickY - 8 - 4);
        if (crossType == 2)
            cursorCross[4 + crossIndex / 100].drawImage(lastClickX - 8 - 4, lastClickY - 8 - 4);

        if (walkableWidgetId != -1) {
            method88(tickDelta, walkableWidgetId);
            drawInterface(0, 0, Widget.forId(walkableWidgetId), 0, 8);
        }

        if (openInterfaceId != -1) {
            method88(tickDelta, openInterfaceId);
            drawInterface(0, 0, Widget.forId(openInterfaceId), 0, 8);
        }

        setTutorialIslandFlag();

        if (!menuOpen) {
            processRightClick(-521);
            drawMenuTooltip();
        } else if (menuScreenArea == 0) {
            drawMenu();
        }

        if (anInt1319 == 1)
            aClass50_Sub1_Sub1_Sub1_1086.drawImage(472, 296);

        if (fps) {
            int y = 20;
            int colour = 0xffff00;

            if (super.fps < 30 && lowMemory)
                colour = 0xff0000;
            if (super.fps < 20 && !lowMemory)
                colour = 0xff0000;

            fontNormal.drawStringRight("Fps:" + super.fps, 507, y, colour);

            y += 15;
            Runtime runtime = Runtime.getRuntime();
            int memoryUsed = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
            colour = 0xffff00;

            if (memoryUsed > 0x2000000 && lowMemory)
                colour = 0xff0000;
            if (memoryUsed > 0x4000000 && !lowMemory)
                colour = 0xff0000;

            fontNormal.drawStringRight("Mem:" + memoryUsed + "k", 507, y, colour);
        }

        if (systemUpdateTime != 0) {
            int seconds = systemUpdateTime / 50;
            int minutes = seconds / 60;
            seconds %= 60;

            if (seconds < 10)
                fontNormal.drawString("System update in: " + minutes + ":0" + seconds, 4, 329, 0xffff00);
            else
                fontNormal.drawString("System update in: " + minutes + ":" + seconds, 4, 329, 0xffff00);

            anInt895++;

            if (anInt895 > 112) {
                anInt895 = 0;
                outBuffer.putOpcode(197);
                outBuffer.putIntBE(0);
            }
        }
    }

    public void run() {
        if (aBoolean1314) {
            processFlamesCycle();
        } else {
            super.run();
        }
    }

    private int getFloorDrawHeight(int plane, int x, int y) {
        int groundX = x >> 7;
        int groundY = y >> 7;
        if (groundX < 0 || groundY < 0 || groundX > 103 || groundY > 103)
            return 0;
        int groundZ = plane;
        if (groundZ < 3 && (currentSceneTileFlags[1][groundX][groundY] & 2) == 2)
            groundZ++;
        int _x = x & 0x7f;
        int _y = y & 0x7f;
        int i2 = intGroundArray[groundZ][groundX][groundY] * (128 - _x) + intGroundArray[groundZ][groundX + 1][groundY] * _x >> 7;
        int j2 = intGroundArray[groundZ][groundX][groundY + 1] * (128 - _x) + intGroundArray[groundZ][groundX + 1][groundY + 1] * _x >> 7;
        return i2 * (128 - _y) + j2 * _y >> 7;
    }

//    public AppletContext getAppletContext() {
//        if (SignLink.applet != null)
//            return SignLink.applet.getAppletContext();
//        else
//            return super.getAppletContext();
//    }

    private void method111(int i) {
        i = 21 / i;
        if (anInt1223 == 0)
            return;
        int j = 0;
        if (systemUpdateTime != 0)
            j = 1;
        for (int k = 0; k < 100; k++)
            if (chatMessages[k] != null) {
                int l = chatTypes[k];
                String s = chatPlayerNames[k];
                if (s != null && s.startsWith("@cr1@")) {
                    s = s.substring(5);
                }
                if (s != null && s.startsWith("@cr2@")) {
                    s = s.substring(5);
                }
                if ((l == 3 || l == 7) && (l == 7 || privateChatMode == 0 || privateChatMode == 1 && method148(13292, s))) {
                    int i1 = 329 - j * 13;
                    if (super.mouseX > 4 && super.mouseY - 4 > i1 - 10 && super.mouseY - 4 <= i1 + 3) {
                        int j1 = fontNormal.getStringEffectWidth("From:  " + s + chatMessages[k]) + 25;
                        if (j1 > 450)
                            j1 = 450;
                        if (super.mouseX < 4 + j1) {
                            if (playerRights >= 1) {
                                menuActionTexts[menuActionRow] = "Report abuse @whi@" + s;
                                menuActionTypes[menuActionRow] = 2507;
                                menuActionRow++;
                            }
                            menuActionTexts[menuActionRow] = "Add ignore @whi@" + s;
                            menuActionTypes[menuActionRow] = 2574;
                            menuActionRow++;
                            menuActionTexts[menuActionRow] = "Add friend @whi@" + s;
                            menuActionTypes[menuActionRow] = 2762;
                            menuActionRow++;
                        }
                    }
                    if (++j >= 5)
                        return;
                }
                if ((l == 5 || l == 6) && privateChatMode < 2 && ++j >= 5)
                    return;
            }

    }

    private void method112(byte byte0, int i) {
        if (byte0 != 36)
            outBuffer.putByte(6);
        Widget class13 = Widget.forId(i);
        for (int j = 0; j < class13.children.length; j++) {
            if (class13.children[j] == -1)
                break;
            Widget class13_1 = Widget.forId(class13.children[j]);
            if (class13_1.type == 1)
                method112((byte) 36, class13_1.id);
            class13_1.animationFrame = 0;
            class13_1.animationDuration = 0;
        }

    }

    private void method113(int i, int j, int k) {
        int l = 0;
        i = 44 / i;
        for (int i1 = 0; i1 < 100; i1++) {
            if (chatMessages[i1] == null)
                continue;
            int j1 = chatTypes[i1];
            int k1 = (70 - l * 14) + chatboxScroll + 4;
            if (k1 < -20)
                break;
            String s = chatPlayerNames[i1];
            if (s != null && s.startsWith("@cr1@")) {
                s = s.substring(5);
            }
            if (s != null && s.startsWith("@cr2@")) {
                s = s.substring(5);
            }
            if (j1 == 0)
                l++;
            if ((j1 == 1 || j1 == 2) && (j1 == 1 || publicChatMode == 0 || publicChatMode == 1 && method148(13292, s))) {
                if (k > k1 - 14 && k <= k1 && !s.equals(localPlayer.playerName)) {
                    if (playerRights >= 1) {
                        menuActionTexts[menuActionRow] = "Report abuse @whi@" + s;
                        menuActionTypes[menuActionRow] = 507;
                        menuActionRow++;
                    }
                    menuActionTexts[menuActionRow] = "Add ignore @whi@" + s;
                    menuActionTypes[menuActionRow] = 574;
                    menuActionRow++;
                    menuActionTexts[menuActionRow] = "Add friend @whi@" + s;
                    menuActionTypes[menuActionRow] = 762;
                    menuActionRow++;
                }
                l++;
            }
            if ((j1 == 3 || j1 == 7) && anInt1223 == 0
                    && (j1 == 7 || privateChatMode == 0 || privateChatMode == 1 && method148(13292, s))) {
                if (k > k1 - 14 && k <= k1) {
                    if (playerRights >= 1) {
                        menuActionTexts[menuActionRow] = "Report abuse @whi@" + s;
                        menuActionTypes[menuActionRow] = 507;
                        menuActionRow++;
                    }
                    menuActionTexts[menuActionRow] = "Add ignore @whi@" + s;
                    menuActionTypes[menuActionRow] = 574;
                    menuActionRow++;
                    menuActionTexts[menuActionRow] = "Add friend @whi@" + s;
                    menuActionTypes[menuActionRow] = 762;
                    menuActionRow++;
                }
                l++;
            }
            if (j1 == 4 && (tradeMode == 0 || tradeMode == 1 && method148(13292, s))) {
                if (k > k1 - 14 && k <= k1) {
                    menuActionTexts[menuActionRow] = "Accept trade @whi@" + s;
                    menuActionTypes[menuActionRow] = 544;
                    menuActionRow++;
                }
                l++;
            }
            if ((j1 == 5 || j1 == 6) && anInt1223 == 0 && privateChatMode < 2)
                l++;
            if (j1 == 8 && (tradeMode == 0 || tradeMode == 1 && method148(13292, s))) {
                if (k > k1 - 14 && k <= k1) {
                    menuActionTexts[menuActionRow] = "Accept challenge @whi@" + s;
                    menuActionTypes[menuActionRow] = 695;
                    menuActionRow++;
                }
                l++;
            }
        }

    }

    private void updateOtherPlayerMovement(Buffer buffer) {
        int playerCount = buffer.getBits(8);

        if (playerCount < localPlayerCount) {
            for (int i = playerCount; i < localPlayerCount; i++)
                removePlayers[removePlayerCount++] = playerList[i];
        }

        if (playerCount > localPlayerCount) {
            SignLink.reportError(username + " Too many players");
            throw new RuntimeException("eek");
        }

        localPlayerCount = 0;

        for (int i = 0; i < playerCount; i++) {
            int id = playerList[i];
            Player player = players[id];
            int updated = buffer.getBits(1);

            if (updated == 0) {
                playerList[localPlayerCount++] = id;
                player.pulseCycle = pulseCycle;
            } else {
                int moveType = buffer.getBits(2);

                if (moveType == 0) {
                    playerList[localPlayerCount++] = id;
                    player.pulseCycle = pulseCycle;
                    updatedPlayers[updatedPlayerCount++] = id;
                } else if (moveType == 1) {
                    playerList[localPlayerCount++] = id;
                    player.pulseCycle = pulseCycle;
                    int direction = buffer.getBits(3);

                    player.move(direction, false);

                    int blockUpdateRequired = buffer.getBits(1);

                    if (blockUpdateRequired == 1)
                        updatedPlayers[updatedPlayerCount++] = id;
                } else if (moveType == 2) {
                    playerList[localPlayerCount++] = id;
                    player.pulseCycle = pulseCycle;
                    int direction1 = buffer.getBits(3);

                    player.move(direction1, true);

                    int direction2 = buffer.getBits(3);

                    player.move(direction2, true);

                    int updateRequired = buffer.getBits(1);

                    if (updateRequired == 1)
                        updatedPlayers[updatedPlayerCount++] = id;
                } else if (moveType == 3) {
                    removePlayers[removePlayerCount++] = id;
                }
            }
        }
    }

    private void renderViewport(int plane) {
        int pixels[] = minimapImage.pixels;
        int pixelAmount = pixels.length;
        for (int pixel = 0; pixel < pixelAmount; pixel++)
            pixels[pixel] = 0;

        for (int viewportY = 1; viewportY < 103; viewportY++) {
            int drawPoint = 24628 + (103 - viewportY) * 512 * 4;
            for (int viewportX = 1; viewportX < 103; viewportX++) {
                if ((currentSceneTileFlags[plane][viewportX][viewportY] & 0x18) == 0)
                    currentScene.renderMinimapDot(pixels, drawPoint, 512, plane, viewportX, viewportY);
                if (plane < 3 && (currentSceneTileFlags[plane + 1][viewportX][viewportY] & 8) != 0)
                    currentScene.renderMinimapDot(pixels, drawPoint, 512, plane + 1, viewportX, viewportY);
                drawPoint += 4;
            }

        }

        int primaryColour = ((238 + (int) (Math.random() * 20D)) - 10 << 16) + ((238 + (int) (Math.random() * 20D)) - 10 << 8)
                + ((238 + (int) (Math.random() * 20D)) - 10);
        int secondaryColour = (238 + (int) (Math.random() * 20D)) - 10 << 16;
        minimapImage.createRasterizer();
        for (int viewportY = 1; viewportY < 103; viewportY++) {
            for (int viewportX = 1; viewportX < 103; viewportX++) {
                if ((currentSceneTileFlags[plane][viewportX][viewportY] & 0x18) == 0)
                    method150(viewportY, plane, viewportX, secondaryColour, 563, primaryColour);
                if (plane < 3 && (currentSceneTileFlags[plane + 1][viewportX][viewportY] & 8) != 0)
                    method150(viewportY, plane + 1, viewportX, secondaryColour, 563, primaryColour);
            }

        }

        if (gameScreenImageProducer != null) {
            gameScreenImageProducer.createRasterizer();
            Rasterizer3D.lineOffsets = viewportOffsets;
        }
        anInt1082++;
        if (anInt1082 > 177) {
            anInt1082 = 0;
            outBuffer.putOpcode(173);
            outBuffer.putMediumBE(0x288b80);
        }
        minimapHintCount = 0;
        for (int viewportX = 0; viewportX < 104; viewportX++) {
            for (int viewportY = 0; viewportY < 104; viewportY++) {
                int floorHash = currentScene.getFloorDecorationHash(this.plane, viewportX, viewportY);
                if (floorHash != 0) {
                    floorHash = floorHash >> 14 & 0x7fff;
                    int icon = GameObjectDefinition.getDefinition(floorHash).icon;
                    if (icon >= 0) {
                        int drawPointX = viewportX;
                        int drawPointY = viewportY;
                        if (icon != 22 && icon != 29 && icon != 34 && icon != 36 && icon != 46 && icon != 47 && icon != 48) {
                            byte regionWidth = 104;
                            byte regionHeight = 104;
                            int flags[][] = currentCollisionMap[this.plane].adjacency;
                            for (int off = 0; off < 10; off++) {
                                int randPlane = (int) (Math.random() * 4D);
                                if (randPlane == 0 && drawPointX > 0 && drawPointX > viewportX - 3 && (flags[drawPointX - 1][drawPointY] & 0x1280108) == 0)
                                    drawPointX--;
                                if (randPlane == 1 && drawPointX < regionWidth - 1 && drawPointX < viewportX + 3 && (flags[drawPointX + 1][drawPointY] & 0x1280180) == 0)
                                    drawPointX++;
                                if (randPlane == 2 && drawPointY > 0 && drawPointY > viewportY - 3 && (flags[drawPointX][drawPointY - 1] & 0x1280102) == 0)
                                    drawPointY--;
                                if (randPlane == 3 && drawPointY < regionHeight - 1 && drawPointY < viewportY + 3 && (flags[drawPointX][drawPointY + 1] & 0x1280120) == 0)
                                    drawPointY++;
                            }

                        }
                        minimapHint[minimapHintCount] = worldMapHintIcons[icon];
                        minimapHintX[minimapHintCount] = drawPointX;
                        minimapHintY[minimapHintCount] = drawPointY;
                        minimapHintCount++;
                    }
                }
            }

        }

    }

    private boolean method116(int j, byte[] abyte0) {
        if (abyte0 == null)
            return true;
        else
            return SignLink.saveWave(abyte0, j);
    }

    private int getWorldDrawPlane() {
        if (!ROOFS_ENABLED) {
            return plane;
        }
        int i = 3;
        if (cameraVerticalRotation < 310) {
            anInt978++;
            if (anInt978 > 1457) {
                anInt978 = 0;
                outBuffer.putOpcode(244);
                outBuffer.putByte(0);
                int j = outBuffer.currentPosition;
                outBuffer.putByte(219);
                outBuffer.putShortBE(37745);
                outBuffer.putByte(61);
                outBuffer.putShortBE(43756);
                outBuffer.putShortBE((int) (Math.random() * 65536D));
                outBuffer.putByte((int) (Math.random() * 256D));
                outBuffer.putShortBE(51171);
                if ((int) (Math.random() * 2D) == 0)
                    outBuffer.putShortBE(15808);
                outBuffer.putByte(97);
                outBuffer.putByte((int) (Math.random() * 256D));
                outBuffer.putLength(outBuffer.currentPosition - j);
            }
            int k = cameraX >> 7;
            int l = cameraY >> 7;
            int i1 = localPlayer.worldX >> 7;
            int j1 = localPlayer.worldY >> 7;
            if ((currentSceneTileFlags[plane][k][l] & 4) != 0)
                i = plane;
            int k1;
            if (i1 > k)
                k1 = i1 - k;
            else
                k1 = k - i1;
            int l1;
            if (j1 > l)
                l1 = j1 - l;
            else
                l1 = l - j1;
            if (k1 > l1) {
                int i2 = (l1 * 0x10000) / k1;
                int k2 = 32768;
                while (k != i1) {
                    if (k < i1)
                        k++;
                    else if (k > i1)
                        k--;
                    if ((currentSceneTileFlags[plane][k][l] & 4) != 0)
                        i = plane;
                    k2 += i2;
                    if (k2 >= 0x10000) {
                        k2 -= 0x10000;
                        if (l < j1)
                            l++;
                        else if (l > j1)
                            l--;
                        if ((currentSceneTileFlags[plane][k][l] & 4) != 0)
                            i = plane;
                    }
                }
            } else {
                int j2 = (k1 * 0x10000) / l1;
                int l2 = 32768;
                while (l != j1) {
                    if (l < j1)
                        l++;
                    else if (l > j1)
                        l--;
                    if ((currentSceneTileFlags[plane][k][l] & 4) != 0)
                        i = plane;
                    l2 += j2;
                    if (l2 >= 0x10000) {
                        l2 -= 0x10000;
                        if (k < i1)
                            k++;
                        else if (k > i1)
                            k--;
                        if ((currentSceneTileFlags[plane][k][l] & 4) != 0)
                            i = plane;
                    }
                }
            }
        }
        if ((currentSceneTileFlags[plane][localPlayer.worldX >> 7][localPlayer.worldY >> 7] & 4) != 0)
            i = plane;
        return i;
    }

    private int getCameraPlaneCutscene() {
        if(!ROOFS_ENABLED){
            return plane;
        }
        int j = getFloorDrawHeight(plane, cameraX, cameraY);
        if (j - cameraZ < 800 && (currentSceneTileFlags[plane][cameraX >> 7][cameraY >> 7] & 4) != 0)
            return plane;
        else
            return 3;
    }

    public void startRunnable(Runnable runnable, int i) {
        if (i > 10)
            i = 10;
        if (SignLink.applet != null) {
            SignLink.startThread(runnable, i);
        } else {
            super.startRunnable(runnable, i);
        }
    }

    private void processPlayerAdditions(boolean priority) { // renderPlayers
        if (localPlayer.worldX >> 7 == destinationX
                && localPlayer.worldY >> 7 == destinationY)
            destinationX = 0;
        int playersToRender = localPlayerCount;
        if (priority)
            playersToRender = 1;
        for (int p = 0; p < playersToRender; p++) {
            Player player;
            int hash;
            if (priority) {
                player = localPlayer;
                hash = thisPlayerId << 14;
            } else {
                player = players[playerList[p]];
                hash = playerList[p] << 14;
            }
            if (player == null || !player.isVisible())
                continue;
            player.preventRotation = (lowMemory && localPlayerCount > 50 || localPlayerCount > 200)
                    && !priority
                    && player.movementAnimation == player.idleAnimation;
            int viewportX = player.worldX >> 7;
            int viewportY = player.worldY >> 7;
            if (viewportX < 0 || viewportX >= 104 || viewportY < 0 || viewportY >= 104)
                continue;
            if (player.playerModel != null
                    && pulseCycle >= player.objectAppearanceStartTick
                    && pulseCycle < player.objectAppearanceEndTick) {
                player.preventRotation = false;
                player.drawHeight2 = getFloorDrawHeight(
                        plane, player.worldX, player.worldY
                );
                currentScene.addEntity(player.anInt1768, player.anInt1769, plane, player.worldX, player.worldY, player.drawHeight2,
                        player.anInt1612, player.anInt1771, player.anInt1770, player,
                        hash);
                continue;
            }
            if ((player.worldX & 0x7f) == 64
                    && (player.worldY & 0x7f) == 64) {
                if (anIntArrayArray886[viewportX][viewportY] == renderCount)
                    continue;
                anIntArrayArray886[viewportX][viewportY] = renderCount;
            }
            player.drawHeight2 = getFloorDrawHeight(plane, player.worldX, player.worldY
            );
            currentScene.addEntity(plane, player.worldX, player.worldY, player.drawHeight2, player, hash,
                    60, player.aBoolean1592,
                    player.anInt1612);
        }
    }

    private void processMenuActions(int id) {
        if (id < 0)
            return;
        int first = firstMenuOperand[id];
        int second = secondMenuOperand[id];
        int action = menuActionTypes[id];
        int clicked = selectedMenuActions[id];
        if (action >= 2000)
            action -= 2000;
        if (inputType != 0 && action != 1016) {
            inputType = 0;
            redrawChatbox = true;
        }
        if (action == 200) {
            Player player = players[clicked];
            if (player != null) {
                walk(false, false, player.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        player.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(245);
                outBuffer.putOffsetShortLE(clicked);
            }
        }
        if (action == 227) {
            anInt1165++;
            if (anInt1165 >= 62) {
                outBuffer.putOpcode(165);
                outBuffer.putByte(206);
                anInt1165 = 0;
            }
            outBuffer.putOpcode(228);
            outBuffer.putShortLE(first);
            outBuffer.putOffsetShortBE(clicked);
            outBuffer.putShortBE(second);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == 876) {
            Player player = players[clicked];
            if (player != null) {
                walk(false, false, player.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        player.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(45);
                outBuffer.putOffsetShortBE(clicked);
            }
        }
        if (action == 921) {
            Npc npc = npcs[clicked];
            if (npc != null) {
                walk(false, false, npc.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        npc.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(67);
                outBuffer.putOffsetShortBE(clicked);
            }
        }
        if (action == 961) {
            anInt1139 += clicked;
            if (anInt1139 >= 115) {
                outBuffer.putOpcode(126);
                outBuffer.putByte(125);
                anInt1139 = 0;
            }
            outBuffer.putOpcode(203);
            outBuffer.putOffsetShortBE(second);
            outBuffer.putShortLE(first);
            outBuffer.putShortLE(clicked);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == 467 && method80(second, 0, first, clicked)) {
            outBuffer.putOpcode(152);
            outBuffer.putShortLE(clicked >> 14 & 0x7fff);
            outBuffer.putShortLE(anInt1148);
            outBuffer.putShortLE(anInt1149);
            outBuffer.putShortLE(second + nextTopRightTileY);
            outBuffer.putShortBE(anInt1147);
            outBuffer.putOffsetShortLE(first + nextTopLeftTileX);
        }
        if (action == 9) {
            outBuffer.putOpcode(3);
            outBuffer.putOffsetShortBE(clicked);
            outBuffer.putShortBE(second);
            outBuffer.putShortBE(first);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == 553) {
            Npc npc = npcs[clicked];
            if (npc != null) {
                walk(false, false, npc.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        npc.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(42);
                outBuffer.putShortLE(clicked);
            }
        }
        if (action == 677) {
            Player player = players[clicked];
            if (player != null) {
                walk(false, false, player.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        player.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(116);
                outBuffer.putShortLE(clicked);
            }
        }
        if (action == Actions.ADD_FRIEND ||
                action == Actions.ADD_IGNORE ||
                action == Actions.REMOVE_FRIEND ||
                action == Actions.REMOVE_IGNORE) {
            String s = menuActionTexts[id];
            int l1 = s.indexOf("@whi@");
            if (l1 != -1) {
                long l3 = TextUtils.nameToLong(s.substring(l1 + 5).trim());
                if (action == Actions.ADD_FRIEND)
                    addFriend(l3);
                if (action == Actions.ADD_IGNORE)
                    addIgnore(l3);
                if (action == Actions.REMOVE_FRIEND)
                    removeFriend(l3);
                if (action == Actions.REMOVE_IGNORE)
                    removeIgnore(l3);
            }
        }
        if (action == 930) {
            boolean flag = walk(false, false, second, localPlayer.pathY[0], 0, 0, 2, 0, first, 0, 0,
                    localPlayer.pathX[0]);
            if (!flag)
                flag = walk(false, false, second, localPlayer.pathY[0], 1, 1, 2, 0, first, 0, 0,
                        localPlayer.pathX[0]);
            lastClickX = super.clickX;
            lastClickY = super.clickY;
            crossType = 2;
            crossIndex = 0;
            outBuffer.putOpcode(54);
            outBuffer.putOffsetShortBE(clicked);
            outBuffer.putShortLE(second + nextTopRightTileY);
            outBuffer.putShortBE(first + nextTopLeftTileX);
        }
        if (action == 399) {
            outBuffer.putOpcode(24);
            outBuffer.putShortLE(second);
            outBuffer.putShortLE(clicked);
            outBuffer.putOffsetShortBE(first);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == 347) {
            Npc class50_sub1_sub4_sub3_sub1_2 = npcs[clicked];
            if (class50_sub1_sub4_sub3_sub1_2 != null) {
                walk(false, false, class50_sub1_sub4_sub3_sub1_2.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        class50_sub1_sub4_sub3_sub1_2.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(57);
                outBuffer.putShortBE(clicked);
                outBuffer.putShortLE(anInt1149);
                outBuffer.putOffsetShortLE(anInt1148);
                outBuffer.putShortBE(anInt1147);
            }
        }
        if (action == Actions.TOGGLE_SETTING_WIDGET) {
            outBuffer.putOpcode(79);
            outBuffer.putShortBE(second);
            Widget widget = Widget.forId(second);
            if (widget.cs1opcodes != null && widget.cs1opcodes[0][0] == 5) {
                int setting = widget.cs1opcodes[0][1];
                widgetSettings[setting] = 1 - widgetSettings[setting];
                updateVarp(0, setting);
                redrawTabArea = true;
            }
        }
        if (action == 493) {
            Player class50_sub1_sub4_sub3_sub2_3 = players[clicked];
            if (class50_sub1_sub4_sub3_sub2_3 != null) {
                walk(false, false, class50_sub1_sub4_sub3_sub2_3.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        class50_sub1_sub4_sub3_sub2_3.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(233);
                outBuffer.putOffsetShortBE(clicked);
            }
        }
        if (action == 14)
            if (!menuOpen)
                currentScene.method279(0, super.clickX - 4, super.clickY - 4);
            else
                currentScene.method279(0, first - 4, second - 4);
        if (action == 903) {
            outBuffer.putOpcode(1);
            outBuffer.putShortBE(clicked);
            outBuffer.putShortLE(anInt1147);
            outBuffer.putShortLE(anInt1149);
            outBuffer.putOffsetShortLE(anInt1148);
            outBuffer.putOffsetShortBE(first);
            outBuffer.putOffsetShortBE(second);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == 361) {
            outBuffer.putOpcode(36);
            outBuffer.putShortBE(anInt1172);
            outBuffer.putOffsetShortBE(second);
            outBuffer.putOffsetShortBE(first);
            outBuffer.putOffsetShortBE(clicked);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == 118) {
            Npc class50_sub1_sub4_sub3_sub1_3 = npcs[clicked];
            if (class50_sub1_sub4_sub3_sub1_3 != null) {
                walk(false, false, class50_sub1_sub4_sub3_sub1_3.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        class50_sub1_sub4_sub3_sub1_3.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                anInt1235 += clicked;
                if (anInt1235 >= 143) {
                    outBuffer.putOpcode(157);
                    outBuffer.putIntBE(0);
                    anInt1235 = 0;
                }
                outBuffer.putOpcode(13);
                outBuffer.putOffsetShortLE(clicked);
            }
        }
        if (action == 376 && method80(second, 0, first, clicked)) {
            outBuffer.putOpcode(210);
            outBuffer.putShortBE(anInt1172);
            outBuffer.putShortLE(clicked >> 14 & 0x7fff);
            outBuffer.putOffsetShortBE(first + nextTopLeftTileX);
            outBuffer.putShortLE(second + nextTopRightTileY);
        }
        if (action == 432) {
            Npc class50_sub1_sub4_sub3_sub1_4 = npcs[clicked];
            if (class50_sub1_sub4_sub3_sub1_4 != null) {
                walk(false, false, class50_sub1_sub4_sub3_sub1_4.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        class50_sub1_sub4_sub3_sub1_4.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(8);
                outBuffer.putShortLE(clicked);
            }
        }
        if (action == Actions.CLOSE_WIDGETS)
            closeWidgets();
        if (action == 918) {
            Player class50_sub1_sub4_sub3_sub2_4 = players[clicked];
            if (class50_sub1_sub4_sub3_sub2_4 != null) {
                walk(false, false, class50_sub1_sub4_sub3_sub2_4.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        class50_sub1_sub4_sub3_sub2_4.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(31);
                outBuffer.putShortBE(clicked);
                outBuffer.putShortLE(anInt1172);
            }
        }
        if (action == 67) {
            Npc class50_sub1_sub4_sub3_sub1_5 = npcs[clicked];
            if (class50_sub1_sub4_sub3_sub1_5 != null) {
                walk(false, false, class50_sub1_sub4_sub3_sub1_5.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        class50_sub1_sub4_sub3_sub1_5.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(104);
                outBuffer.putOffsetShortBE(anInt1172);
                outBuffer.putShortLE(clicked);
            }
        }
        if (action == 68) {
            boolean flag1 = walk(false, false, second, localPlayer.pathY[0], 0, 0, 2, 0, first, 0, 0,
                    localPlayer.pathX[0]);
            if (!flag1)
                flag1 = walk(false, false, second, localPlayer.pathY[0], 1, 1, 2, 0, first, 0, 0,
                        localPlayer.pathX[0]);
            lastClickX = super.clickX;
            lastClickY = super.clickY;
            crossType = 2;
            crossIndex = 0;
            outBuffer.putOpcode(77);
            outBuffer.putOffsetShortBE(first + nextTopLeftTileX);
            outBuffer.putShortBE(second + nextTopRightTileY);
            outBuffer.putOffsetShortLE(clicked);
        }
        if (action == 684) {
            boolean flag2 = walk(false, false, second, localPlayer.pathY[0], 0, 0, 2, 0, first, 0, 0,
                    localPlayer.pathX[0]);
            if (!flag2)
                flag2 = walk(false, false, second, localPlayer.pathY[0], 1, 1, 2, 0, first, 0, 0,
                        localPlayer.pathX[0]);
            lastClickX = super.clickX;
            lastClickY = super.clickY;
            crossType = 2;
            crossIndex = 0;
            if ((clicked & 3) == 0)
                anInt1052++;
            if (anInt1052 >= 84) {
                outBuffer.putOpcode(222);
                outBuffer.putMediumBE(0xabc842);
                anInt1052 = 0;
            }
            outBuffer.putOpcode(71);
            outBuffer.putOffsetShortLE(clicked);
            outBuffer.putOffsetShortLE(first + nextTopLeftTileX);
            outBuffer.putOffsetShortBE(second + nextTopRightTileY);
        }
        if (action == Actions.ACCEPT_TRADE || action == Actions.ACCEPT_CHALLENGE) {
            String name = menuActionTexts[id];
            int colour = name.indexOf("@whi@");
            if (colour != -1) {
                name = name.substring(colour + 5).trim();
                String username = TextUtils.formatName(TextUtils.longToName(TextUtils.nameToLong(name)));
                boolean found = false;
                for (int index = 0; index < localPlayerCount; index++) {
                    Player player = players[playerList[index]];
                    if (player == null || player.playerName == null
                            || !player.playerName.equalsIgnoreCase(username))
                        continue;
                    walk(false, false, player.pathY[0],
                            localPlayer.pathY[0], 1, 1, 2, 0,
                            player.pathX[0], 0, 0,
                            localPlayer.pathX[0]);
                    if (action == Actions.ACCEPT_TRADE) {
                        outBuffer.putOpcode(116);
                        outBuffer.putShortLE(playerList[index]);
                    }
                    if (action == Actions.ACCEPT_CHALLENGE) {
                        outBuffer.putOpcode(245);
                        outBuffer.putOffsetShortLE(playerList[index]);
                    }
                    found = true;
                    break;
                }

                if (!found)
                    addChatMessage("", "Unable to find " + username, 0);
            }
        }
        if (action == 225) {
            outBuffer.putOpcode(177); // second item action
            outBuffer.putOffsetShortBE(first);
            outBuffer.putShortLE(clicked);
            outBuffer.putShortLE(second);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == Actions.USABLE_WIDGET) {
            Widget widget = Widget.forId(second);
            widgetSelected = 1;
            anInt1172 = second;
            anInt1173 = widget.optionAttributes;
            itemSelected = 0;
            redrawTabArea = true;
            String prefix = widget.optionCircumfix;
            if (prefix.indexOf(" ") != -1)
                prefix = prefix.substring(0, prefix.indexOf(" "));
            String suffix = widget.optionCircumfix;
            if (suffix.indexOf(" ") != -1)
                suffix = suffix.substring(suffix.indexOf(" ") + 1);
            selectedWidgetName = prefix + " " + widget.optionText + " " + suffix;
            if (anInt1173 == 16) {
                redrawTabArea = true;
                currentTabId = 3;
                drawTabIcons = true;
            }
            return;
        }
        if (action == 891) {
            outBuffer.putOpcode(4);
            outBuffer.putShortLE(first);
            outBuffer.putOffsetShortLE(clicked);
            outBuffer.putOffsetShortLE(second);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == 894) {
            outBuffer.putOpcode(158); // fifth item action event
            outBuffer.putOffsetShortLE(first);
            outBuffer.putOffsetShortLE(clicked);
            outBuffer.putShortLE(second);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == 1280) {
            method80(second, 0, first, clicked);
            outBuffer.putOpcode(55);
            outBuffer.putShortLE(clicked >> 14 & 0x7fff);
            outBuffer.putShortLE(second + nextTopRightTileY);
            outBuffer.putShortBE(first + nextTopLeftTileX);
        }
        if (action == 35) {
            method80(second, 0, first, clicked);
            outBuffer.putOpcode(181);
            outBuffer.putOffsetShortBE(first + nextTopLeftTileX);
            outBuffer.putShortLE(second + nextTopRightTileY);
            outBuffer.putShortLE(clicked >> 14 & 0x7fff);
        }
        if (action == 888) {
            method80(second, 0, first, clicked);
            outBuffer.putOpcode(50);
            outBuffer.putOffsetShortBE(second + nextTopRightTileY);
            outBuffer.putShortLE(clicked >> 14 & 0x7fff);
            outBuffer.putOffsetShortLE(first + nextTopLeftTileX);
        }
        if (action == 324) {
            outBuffer.putOpcode(161);
            outBuffer.putOffsetShortLE(first);
            outBuffer.putOffsetShortLE(clicked);
            outBuffer.putShortLE(second);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == Actions.EXAMINE_ITEM) {
            ItemDefinition definition = ItemDefinition.lookup(clicked);
            Widget widget = Widget.forId(second);
            String description;
            if (widget != null && widget.itemAmounts[first] >= 0x186a0)
                description = widget.itemAmounts[first] + " x " + definition.name;
            else if (definition.description != null)
                description = new String(definition.description);
            else
                description = "It's a " + definition.name + ".";
            addChatMessage("", description, 0);
        }
        if (action == 352) {
            Widget class13_2 = Widget.forId(second);
            boolean flag7 = true;
            if (class13_2.contentType > 0)
                flag7 = handleWidgetDynamicAction(class13_2);
            if (flag7) {
                outBuffer.putOpcode(79);
                outBuffer.putShortBE(second);
            }
        }
        if (action == 1412) {
            int k1 = clicked >> 14 & 0x7fff;
            GameObjectDefinition class47 = GameObjectDefinition.getDefinition(k1);
            String s9;
            if (class47.description != null)
                s9 = new String(class47.description);
            else
                s9 = "It's a " + class47.name + ".";
            addChatMessage("", s9, 0);
        }
        if (action == 575 && !aBoolean1239) {
            outBuffer.putOpcode(226);
            outBuffer.putShortBE(second);
            aBoolean1239 = true;
        }
        if (action == 892) {
            method80(second, 0, first, clicked);
            outBuffer.putOpcode(136);
            outBuffer.putShortBE(first + nextTopLeftTileX);
            outBuffer.putShortLE(second + nextTopRightTileY);
            outBuffer.putShortBE(clicked >> 14 & 0x7fff);
        }
        if (action == 270) {
            boolean flag3 = walk(false, false, second, localPlayer.pathY[0], 0, 0, 2, 0, first, 0, 0,
                    localPlayer.pathX[0]);
            if (!flag3)
                flag3 = walk(false, false, second, localPlayer.pathY[0], 1, 1, 2, 0, first, 0, 0,
                        localPlayer.pathX[0]);
            lastClickX = super.clickX;
            lastClickY = super.clickY;
            crossType = 2;
            crossIndex = 0;
            outBuffer.putOpcode(230);
            outBuffer.putShortLE(clicked);
            outBuffer.putOffsetShortBE(first + nextTopLeftTileX);
            outBuffer.putShortBE(second + nextTopRightTileY);
        }
        if (action == 596) {
            Player class50_sub1_sub4_sub3_sub2_5 = players[clicked];
            if (class50_sub1_sub4_sub3_sub2_5 != null) {
                walk(false, false, class50_sub1_sub4_sub3_sub2_5.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        class50_sub1_sub4_sub3_sub2_5.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(143);
                outBuffer.putShortLE(anInt1149);
                outBuffer.putOffsetShortLE(anInt1147);
                outBuffer.putShortBE(anInt1148);
                outBuffer.putOffsetShortBE(clicked);
            }
        }
        if (action == 100) {
            boolean flag4 = walk(false, false, second, localPlayer.pathY[0], 0, 0, 2, 0, first, 0, 0,
                    localPlayer.pathX[0]);
            if (!flag4)
                flag4 = walk(false, false, second, localPlayer.pathY[0], 1, 1, 2, 0, first, 0, 0,
                        localPlayer.pathX[0]);
            lastClickX = super.clickX;
            lastClickY = super.clickY;
            crossType = 2;
            crossIndex = 0;
            outBuffer.putOpcode(211);
            outBuffer.putOffsetShortLE(anInt1147);
            outBuffer.putOffsetShortBE(anInt1149);
            outBuffer.putOffsetShortLE(second + nextTopRightTileY);
            outBuffer.putOffsetShortLE(first + nextTopLeftTileX);
            outBuffer.putShortLE(anInt1148);
            outBuffer.putShortLE(clicked);
        }
        if (action == 1668) {
            Npc class50_sub1_sub4_sub3_sub1_6 = npcs[clicked];
            if (class50_sub1_sub4_sub3_sub1_6 != null) {
                ActorDefinition class37 = class50_sub1_sub4_sub3_sub1_6.npcDefinition;
                if (class37.childrenIds != null)
                    class37 = class37.getChildDefinition();
                if (class37 != null) {
                    String s10;
                    if (class37.description != null)
                        s10 = new String(class37.description);
                    else
                        s10 = "It's a " + class37.name + ".";
                    addChatMessage("", s10, 0);
                }
            }
        }
        if (action == 26) {
            boolean flag5 = walk(false, false, second, localPlayer.pathY[0], 0, 0, 2, 0, first, 0, 0,
                    localPlayer.pathX[0]);
            if (!flag5)
                flag5 = walk(false, false, second, localPlayer.pathY[0], 1, 1, 2, 0, first, 0, 0,
                        localPlayer.pathX[0]);
            lastClickX = super.clickX;
            lastClickY = super.clickY;
            crossType = 2;
            crossIndex = 0;
            anInt1100++;
            if (anInt1100 >= 120) {
                outBuffer.putOpcode(95);
                outBuffer.putIntBE(0);
                anInt1100 = 0;
            }
            outBuffer.putOpcode(100);
            outBuffer.putShortBE(first + nextTopLeftTileX);
            outBuffer.putOffsetShortBE(second + nextTopRightTileY);
            outBuffer.putOffsetShortLE(clicked);
        }
        if (action == 444) {
            outBuffer.putOpcode(91); // third item action
            outBuffer.putShortLE(clicked);
            outBuffer.putOffsetShortLE(first);
            outBuffer.putShortBE(second);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == 507) {
            String string = menuActionTexts[id];
            int i_389_ = string.indexOf("@whi@");
            if (i_389_ != -1)
                if (openInterfaceId == -1) {
                    closeWidgets();
                    reportedName = string.substring(i_389_ + 5).trim();
                    reportMutePlayer = false;
                    reportAbuseInterfaceID = openInterfaceId = Widget.anInt246;
                } else {
                    addChatMessage("", "Please close the interface you have open before using 'report abuse'", 0);
                }
        }
        if (action == 389) {
            method80(second, 0, first, clicked);
            outBuffer.putOpcode(241);
            outBuffer.putShortBE(clicked >> 14 & 0x7fff);
            outBuffer.putShortBE(first + nextTopLeftTileX);
            outBuffer.putOffsetShortBE(second + nextTopRightTileY);
        }
        if (action == 564) {
            outBuffer.putOpcode(231); // fourth item action event
            outBuffer.putOffsetShortLE(second);
            outBuffer.putShortLE(first);
            outBuffer.putShortBE(clicked);
            atInventoryLoopCycle = 0;
            anInt1330 = second;
            anInt1331 = first;
            atInventoryInterfaceType = 2;
            if (Widget.forId(second).parentId == openInterfaceId)
                atInventoryInterfaceType = 1;
            if (Widget.forId(second).parentId == backDialogueId)
                atInventoryInterfaceType = 3;
        }
        if (action == 984) {
            String s3 = menuActionTexts[id];
            int l2 = s3.indexOf("@whi@");
            if (l2 != -1) {
                long l4 = TextUtils.nameToLong(s3.substring(l2 + 5).trim());
                int k3 = -1;
                for (int i4 = 0; i4 < friendsCount; i4++) {
                    if (friends[i4] != l4)
                        continue;
                    k3 = i4;
                    break;
                }

                if (k3 != -1 && friendWorlds[k3] > 0) {
                    redrawChatbox = true;
                    inputType = 0;
                    messagePromptRaised = true;
                    chatMessage = "";
                    friendsListAction = 3;
                    aLong1141 = friends[k3];
                    chatboxInputMessage = "Enter message to send to " + friendUsernames[k3];
                }
            }
        }
        if (action == Actions.RESET_SETTING_WIDGET) {
            outBuffer.putOpcode(79);
            outBuffer.putShortBE(second);
            Widget widget = Widget.forId(second);
            if (widget.cs1opcodes != null && widget.cs1opcodes[0][0] == 5) {
                int operand = widget.cs1opcodes[0][1];
                if (widgetSettings[operand] != widget.conditionValues[0]) {
                    widgetSettings[operand] = widget.conditionValues[0];
                    updateVarp(0, operand);
                    redrawTabArea = true;
                }
            }
        }
        if (action == 318) {
            Npc class50_sub1_sub4_sub3_sub1_7 = npcs[clicked];
            if (class50_sub1_sub4_sub3_sub1_7 != null) {
                walk(false, false, class50_sub1_sub4_sub3_sub1_7.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        class50_sub1_sub4_sub3_sub1_7.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(112);
                outBuffer.putShortLE(clicked);
            }
        }
        if (action == 199) {
            boolean flag6 = walk(false, false, second, localPlayer.pathY[0], 0, 0, 2, 0, first, 0, 0,
                    localPlayer.pathX[0]);
            if (!flag6)
                flag6 = walk(false, false, second, localPlayer.pathY[0], 1, 1, 2, 0, first, 0, 0,
                        localPlayer.pathX[0]);
            lastClickX = super.clickX;
            lastClickY = super.clickY;
            crossType = 2;
            crossIndex = 0;
            outBuffer.putOpcode(83);
            outBuffer.putShortLE(clicked);
            outBuffer.putShortBE(second + nextTopRightTileY);
            outBuffer.putShortLE(anInt1172);
            outBuffer.putOffsetShortLE(first + nextTopLeftTileX);
        }
        if (action == 55) {
            method44(dialogueId);
            dialogueId = -1;
            redrawChatbox = true;
        }
        if (action == 52) {
            itemSelected = 1;
            anInt1147 = first;
            anInt1148 = second;
            anInt1149 = clicked;
            selectedItemName = String.valueOf(ItemDefinition.lookup(clicked).name);
            widgetSelected = 0;
            redrawTabArea = true;
            return;
        }
        if (action == 1564) {
            ItemDefinition class16_1 = ItemDefinition.lookup(clicked);
            String s6;
            if (class16_1.description != null)
                s6 = new String(class16_1.description);
            else
                s6 = "It's a " + class16_1.name + ".";
            addChatMessage("", s6, 0);
        }
        if (action == 408) {
            Player class50_sub1_sub4_sub3_sub2_6 = players[clicked];
            if (class50_sub1_sub4_sub3_sub2_6 != null) {
                walk(false, false, class50_sub1_sub4_sub3_sub2_6.pathY[0],
                        localPlayer.pathY[0], 1, 1, 2, 0,
                        class50_sub1_sub4_sub3_sub2_6.pathX[0], 0, 0,
                        localPlayer.pathX[0]);
                lastClickX = super.clickX;
                lastClickY = super.clickY;
                crossType = 2;
                crossIndex = 0;
                outBuffer.putOpcode(194);
                outBuffer.putShortLE(clicked);
            }
        }
        itemSelected = 0;
        widgetSelected = 0;
        redrawTabArea = true;
    }

    private void method121(boolean flag) {
        anInt939 = 0;
        for (int i = -1; i < localPlayerCount + anInt1133; i++) {
            Object obj;
            if (i == -1)
                obj = localPlayer;
            else if (i < localPlayerCount)
                obj = players[playerList[i]];
            else
                obj = npcs[anIntArray1134[i - localPlayerCount]];
            if (obj == null || !((Actor) (obj)).isVisible())
                continue;
            if (obj instanceof Npc) {
                ActorDefinition class37 = ((Npc) obj).npcDefinition;
                if (class37.childrenIds != null)
                    class37 = class37.getChildDefinition();
                if (class37 == null)
                    continue;
            }
            if (i < localPlayerCount) {
                int k = 30;
                Player class50_sub1_sub4_sub3_sub2 = (Player) obj;
                if (class50_sub1_sub4_sub3_sub2.isSkulled != -1 || class50_sub1_sub4_sub3_sub2.headIcon != -1) {
                    method136(((Actor) (obj)), false, ((Actor) (obj)).modelHeight + 15);
                    if (anInt932 > -1) {
                        if (class50_sub1_sub4_sub3_sub2.isSkulled != -1) {
                            aClass50_Sub1_Sub1_Sub1Array1288[class50_sub1_sub4_sub3_sub2.isSkulled].drawImage(anInt932 - 12, anInt933
                                    - k);
                            k += 25;
                        }
                        if (class50_sub1_sub4_sub3_sub2.headIcon != -1) {
                            aClass50_Sub1_Sub1_Sub1Array1079[class50_sub1_sub4_sub3_sub2.headIcon].drawImage(anInt932 - 12, anInt933
                                    - k);
                            k += 25;
                        }
                    }
                }
                if (i >= 0 && headIconDrawType == 10 && otherPlayerId == playerList[i]) {
                    method136(((Actor) (obj)), false, ((Actor) (obj)).modelHeight + 15);
                    if (anInt932 > -1)
                        aClass50_Sub1_Sub1_Sub1Array954[1].drawImage(anInt932 - 12, anInt933 - k);
                }
            } else {
                ActorDefinition class37_1 = ((Npc) obj).npcDefinition;
                if (class37_1.headIcon >= 0 && class37_1.headIcon < aClass50_Sub1_Sub1_Sub1Array1079.length) {
                    method136(((Actor) (obj)), false, ((Actor) (obj)).modelHeight + 15);
                    if (anInt932 > -1)
                        aClass50_Sub1_Sub1_Sub1Array1079[class37_1.headIcon].drawImage(anInt932 - 12, anInt933 - 30
                        );
                }
                if (headIconDrawType == 1 && anInt1226 == anIntArray1134[i - localPlayerCount] && pulseCycle % 20 < 10) {
                    method136(((Actor) (obj)), false, ((Actor) (obj)).modelHeight + 15);
                    if (anInt932 > -1)
                        aClass50_Sub1_Sub1_Sub1Array954[0].drawImage(anInt932 - 12, anInt933 - 28);
                }
            }
            if (((Actor) (obj)).forcedChat != null
                    && (i >= localPlayerCount || publicChatMode == 0 || publicChatMode == 3 || publicChatMode == 1
                    && method148(13292, ((Player) obj).playerName))) {
                method136(((Actor) (obj)), false, ((Actor) (obj)).modelHeight);
                if (anInt932 > -1 && anInt939 < anInt940) {
                    anIntArray944[anInt939] = fontBold.getDisplayedWidth(((Actor) (obj)).forcedChat
                    ) / 2;
                    anIntArray943[anInt939] = fontBold.characterDefaultHeight;
                    anIntArray941[anInt939] = anInt932;
                    anIntArray942[anInt939] = anInt933;
                    anIntArray945[anInt939] = ((Actor) (obj)).textColour;
                    anIntArray946[anInt939] = ((Actor) (obj)).textEffect;
                    anIntArray947[anInt939] = ((Actor) (obj)).textCycle;
                    aStringArray948[anInt939++] = ((Actor) (obj)).forcedChat;
                    if (anInt998 == 0 && ((Actor) (obj)).textEffect >= 1 && ((Actor) (obj)).textEffect <= 3) {
                        anIntArray943[anInt939] += 10;
                        anIntArray942[anInt939] += 5;
                    }
                    if (anInt998 == 0 && ((Actor) (obj)).textEffect == 4)
                        anIntArray944[anInt939] = 60;
                    if (anInt998 == 0 && ((Actor) (obj)).textEffect == 5)
                        anIntArray943[anInt939] += 5;
                }
            }
            if (((Actor) (obj)).endCycle > pulseCycle) {
                method136(((Actor) (obj)), false, ((Actor) (obj)).modelHeight + 15);
                if (anInt932 > -1) {
                    int l = (((Actor) (obj)).anInt1596 * 30) / ((Actor) (obj)).anInt1597;
                    if (l > 30)
                        l = 30;
                    Rasterizer.drawFilledRectangle(anInt932 - 15, anInt933 - 3, l, 5, 65280);
                    Rasterizer.drawFilledRectangle((anInt932 - 15) + l, anInt933 - 3, 30 - l, 5, 0xff0000);
                }
            }
            for (int i1 = 0; i1 < 4; i1++)
                if (((Actor) (obj)).hitCycles[i1] > pulseCycle) {
                    method136(((Actor) (obj)), false, ((Actor) (obj)).modelHeight / 2);
                    if (anInt932 > -1) {
                        if (i1 == 1)
                            anInt933 -= 20;
                        if (i1 == 2) {
                            anInt932 -= 15;
                            anInt933 -= 10;
                        }
                        if (i1 == 3) {
                            anInt932 += 15;
                            anInt933 -= 10;
                        }
                        aClass50_Sub1_Sub1_Sub1Array1182[((Actor) (obj)).hitTypes[i1]].drawImage(
                                anInt932 - 12, anInt933 - 12);
                        fontSmall.drawStringLeft(String
                                .valueOf(((Actor) (obj)).hitDamages[i1]), anInt932, anInt933 + 4, 0);
                        fontSmall.drawStringLeft(String
                                .valueOf(((Actor) (obj)).hitDamages[i1]), anInt932 - 1, anInt933 + 3, 0xffffff);
                    }
                }

        }

        for (int j = 0; j < anInt939; j++) {
            int j1 = anIntArray941[j];
            int k1 = anIntArray942[j];
            int l1 = anIntArray944[j];
            int i2 = anIntArray943[j];
            boolean flag1 = true;
            while (flag1) {
                flag1 = false;
                for (int j2 = 0; j2 < j; j2++)
                    if (k1 + 2 > anIntArray942[j2] - anIntArray943[j2] && k1 - i2 < anIntArray942[j2] + 2
                            && j1 - l1 < anIntArray941[j2] + anIntArray944[j2]
                            && j1 + l1 > anIntArray941[j2] - anIntArray944[j2]
                            && anIntArray942[j2] - anIntArray943[j2] < k1) {
                        k1 = anIntArray942[j2] - anIntArray943[j2];
                        flag1 = true;
                    }

            }
            anInt932 = anIntArray941[j];
            anInt933 = anIntArray942[j] = k1;
            String s = aStringArray948[j];
            if (anInt998 == 0) {
                int k2 = 0xffff00;
                if (anIntArray945[j] < 6)
                    k2 = anIntArray842[anIntArray945[j]];
                if (anIntArray945[j] == 6)
                    k2 = renderCount % 20 >= 10 ? 0xffff00 : 0xff0000;
                if (anIntArray945[j] == 7)
                    k2 = renderCount % 20 >= 10 ? 65535 : 255;
                if (anIntArray945[j] == 8)
                    k2 = renderCount % 20 >= 10 ? 0x80ff80 : 45056;
                if (anIntArray945[j] == 9) {
                    int l2 = 150 - anIntArray947[j];
                    if (l2 < 50)
                        k2 = 0xff0000 + 1280 * l2;
                    else if (l2 < 100)
                        k2 = 0xffff00 - 0x50000 * (l2 - 50);
                    else if (l2 < 150)
                        k2 = 65280 + 5 * (l2 - 100);
                }
                if (anIntArray945[j] == 10) {
                    int i3 = 150 - anIntArray947[j];
                    if (i3 < 50)
                        k2 = 0xff0000 + 5 * i3;
                    else if (i3 < 100)
                        k2 = 0xff00ff - 0x50000 * (i3 - 50);
                    else if (i3 < 150)
                        k2 = (255 + 0x50000 * (i3 - 100)) - 5 * (i3 - 100);
                }
                if (anIntArray945[j] == 11) {
                    int j3 = 150 - anIntArray947[j];
                    if (j3 < 50)
                        k2 = 0xffffff - 0x50005 * j3;
                    else if (j3 < 100)
                        k2 = 65280 + 0x50005 * (j3 - 50);
                    else if (j3 < 150)
                        k2 = 0xffffff - 0x50000 * (j3 - 100);
                }
                if (anIntArray946[j] == 0) {
                    fontBold.drawStringLeft(s, anInt932, anInt933 + 1, 0);
                    fontBold.drawStringLeft(s, anInt932, anInt933, k2);
                }
                if (anIntArray946[j] == 1) {
                    fontBold.drawCenteredStringWaveY(s, anInt932, anInt933 + 1, renderCount, 0);
                    fontBold.drawCenteredStringWaveY(s, anInt932, anInt933, renderCount, k2);
                }
                if (anIntArray946[j] == 2) {
                    fontBold.drawCeneteredStringWaveXY(s, anInt932, anInt933 + 1, renderCount, 0);
                    fontBold.drawCeneteredStringWaveXY(s, anInt932, anInt933, renderCount, k2);
                }
                if (anIntArray946[j] == 3) {
                    fontBold.drawCenteredStringWaveXYMove(s, anInt932, anInt933 + 1, renderCount, 150 - anIntArray947[j], 0
                    );
                    fontBold.drawCenteredStringWaveXYMove(s, anInt932, anInt933, renderCount, 150 - anIntArray947[j], k2
                    );
                }
                if (anIntArray946[j] == 4) {
                    int k3 = fontBold.getDisplayedWidth(s);
                    int i4 = ((150 - anIntArray947[j]) * (k3 + 100)) / 150;
                    Rasterizer.setCoordinates(0, anInt932 - 50, 334, anInt932 + 50);
                    fontBold.drawString(s, (anInt932 + 50) - i4, anInt933 + 1, 0);
                    fontBold.drawString(s, (anInt932 + 50) - i4, anInt933, k2);
                    Rasterizer.resetCoordinates();
                }
                if (anIntArray946[j] == 5) {
                    int l3 = 150 - anIntArray947[j];
                    int j4 = 0;
                    if (l3 < 25)
                        j4 = l3 - 25;
                    else if (l3 > 125)
                        j4 = l3 - 125;
                    Rasterizer.setCoordinates(anInt933 - fontBold.characterDefaultHeight - 1, 0, anInt933 + 5,
                            512);
                    fontBold.drawStringLeft(s, anInt932, anInt933 + 1 + j4, 0);
                    fontBold.drawStringLeft(s, anInt932, anInt933 + j4, k2);
                    Rasterizer.resetCoordinates();
                }
            } else {
                fontBold.drawStringLeft(s, anInt932, anInt933 + 1, 0);
                fontBold.drawStringLeft(s, anInt932, anInt933, 0xffff00);
            }
        }

        if (flag)
            opcode = -1;
    }

    private void method122(int i) {
        while (i >= 0)
            aBoolean1242 = !aBoolean1242;
        if (chatboxProducingGraphicsBuffer != null) {
        } else {
            method141();
            super.imageProducer = null;
            aClass18_1198 = null;
            aClass18_1199 = null;
            aClass18_1200 = null;
            flameLeftBackground = null;
            flameRightBackground = null;
            aClass18_1203 = null;
            aClass18_1204 = null;
            aClass18_1205 = null;
            aClass18_1206 = null;
            chatboxProducingGraphicsBuffer = new ProducingGraphicsBuffer(479, 96, getParentComponent());
            aClass18_1157 = new ProducingGraphicsBuffer(172, 156, getParentComponent());
            Rasterizer.resetPixels();
            minimapBackgroundImage.drawImage(0, 0);
            tabImageProducer = new ProducingGraphicsBuffer(190, 261, getParentComponent());
            gameScreenImageProducer = new ProducingGraphicsBuffer(512, 334, getParentComponent());
            Rasterizer.resetPixels();
            aClass18_1108 = new ProducingGraphicsBuffer(496, 50, getParentComponent());
            aClass18_1109 = new ProducingGraphicsBuffer(269, 37, getParentComponent());
            aClass18_1110 = new ProducingGraphicsBuffer(249, 45, getParentComponent());
            aBoolean1046 = true;
            gameScreenImageProducer.createRasterizer();
            Rasterizer3D.lineOffsets = viewportOffsets;
        }
    }

    private void method123(int i) {
        Graphics g = getParentComponent().getGraphics();
        g.setColor(Color.black);
        i = 68 / i;
        g.fillRect(0, 0, 765, 503);
        setFrameRate(1);
        if (aBoolean1283) {
            aBoolean1243 = false;
            g.setFont(new Font("Helvetica", 1, 16));
            g.setColor(Color.yellow);
            int j = 35;
            g.drawString("Sorry, an error has occured whilst loading RuneScape", 30, j);
            j += 50;
            g.setColor(Color.white);
            g.drawString("To fix this try the following (in order):", 30, j);
            j += 50;
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", 1, 12));
            g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, j);
            j += 30;
            g.drawString("2: Try clearing your web-browsers cache from tools->internet options", 30, j);
            j += 30;
            g.drawString("3: Try using a different game-world", 30, j);
            j += 30;
            g.drawString("4: Try rebooting your computer", 30, j);
            j += 30;
            g.drawString("5: Try selecting a different version of Java from the play-game menu", 30, j);
        }
        if (aBoolean1097) {
            aBoolean1243 = false;
            g.setFont(new Font("Helvetica", 1, 20));
            g.setColor(Color.white);
            g.drawString("Error - unable to load game!", 50, 50);
            g.drawString("To play RuneScape make sure you play from", 50, 100);
            g.drawString("http://www.runescape.com", 50, 150);
        }
        if (aBoolean1016) {
            aBoolean1243 = false;
            g.setColor(Color.yellow);
            int k = 35;
            g.drawString("Error a copy of RuneScape already appears to be loaded", 30, k);
            k += 50;
            g.setColor(Color.white);
            g.drawString("To fix this try the following (in order):", 30, k);
            k += 50;
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", 1, 12));
            g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, k);
            k += 30;
            g.drawString("2: Try rebooting your computer, and reloading", 30, k);
            k += 30;
        }
    }

    private void logout() {
        try {
            if (gameConnection != null)
                gameConnection.close();
        } catch (Exception _ex) {
        }
        gameConnection = null;
        loggedIn = false;
        loginScreenState = 0;
        if (USE_STATIC_DETAILS) {
            username = USERNAME;
            password = PASSWORD;
        } else {
            username = "";
            password = "";
        }

        resetModelCaches();
        currentScene.initToNull();
        for (int plane = 0; plane < 4; plane++)
            currentCollisionMap[plane].reset();

        System.gc();

        stopMidi();
        currentSong = -1;
        nextSong = -1;
        previousSong = 0;
    }

    private void method125(String s, String s1) {
        if (gameScreenImageProducer != null) {
            gameScreenImageProducer.createRasterizer();
            Rasterizer3D.lineOffsets = viewportOffsets;
            int j = 151;
            if (s != null)
                j -= 7;
            fontNormal.drawStringLeft(s1, 257, j, 0);
            fontNormal.drawStringLeft(s1, 256, j - 1, 0xffffff);
            j += 15;
            if (s != null) {
                fontNormal.drawStringLeft(s, 257, j, 0);
                fontNormal.drawStringLeft(s, 256, j - 1, 0xffffff);
            }
            gameScreenImageProducer.drawGraphics(4, 4, super.gameGraphics);
            return;
        }
        if (super.imageProducer != null) {
            super.imageProducer.createRasterizer();
            Rasterizer3D.lineOffsets = anIntArray1003;
            int k = 251;
            char c = '\u012C';
            byte byte0 = 50;
            Rasterizer.drawFilledRectangle(383 - c / 2, k - 5 - byte0 / 2, c, byte0, 0);
            Rasterizer.drawUnfilledRectangle(383 - c / 2, k - 5 - byte0 / 2, c, byte0, 0xffffff);
            if (s != null)
                k -= 7;
            fontNormal.drawStringLeft(s1, 383, k, 0);
            fontNormal.drawStringLeft(s1, 382, k - 1, 0xffffff);
            k += 15;
            if (s != null) {
                fontNormal.drawStringLeft(s, 383, k, 0);
                fontNormal.drawStringLeft(s, 382, k - 1, 0xffffff);
            }
            super.imageProducer.drawGraphics(0, 0, super.gameGraphics);
        }
    }

    private boolean menuHasAddFriend(int i, byte byte0) {
        if (i < 0)
            return false;
        int j = menuActionTypes[i];
        if (byte0 != 97)
            throw new NullPointerException();
        if (j >= 2000)
            j -= 2000;
        return j == 762;
    }

    private void method127(boolean flag) {
        if (!flag) //never remove
            anInt1056 = incomingRandom.nextInt();
        if (headIconDrawType != 2)
            return;
        method137((anInt844 - nextTopLeftTileX << 7) + anInt847, anInt846 * 2, (anInt845 - nextTopRightTileY << 7) + anInt848, -214);
        if (anInt932 > -1 && pulseCycle % 20 < 10)
            aClass50_Sub1_Sub1_Sub1Array954[0].drawImage(anInt932 - 12, anInt933 - 28);
    }

    public void repaintGame() {
        if (aBoolean1016 || aBoolean1283 || aBoolean1097) {
            method123(281);
            return;
        }
        drawCycle++;
        if (!loggedIn)
            drawLoginScreen(false);
        else
            method74(7);
        anInt1094 = 0;
    }

    private void drawMenu() {
        int offsetX = menuOffsetX;
        int offsetY = menuOffsetY;
        int width = anInt1307;
        int height = anInt1308;
        int colour = 0x5d5447;
        Rasterizer.drawFilledRectangle(offsetX, offsetY, width, height, colour);
        Rasterizer.drawFilledRectangle(offsetX + 1, offsetY + 1, width - 2, 16, 0);
        Rasterizer.drawUnfilledRectangle(offsetX + 1, offsetY + 18, width - 2, height - 19, 0);
        fontBold.drawString("Choose Option", offsetX + 3, offsetY + 14, colour);
        int x = super.mouseX;
        int y = super.mouseY;
        if (menuScreenArea == 0) {
            x -= 4;
            y -= 4;
        }
        if (menuScreenArea == 1) {
            x -= 553;
            y -= 205;
        }
        if (menuScreenArea == 2) {
            x -= 17;
            y -= 357;
        }
        for (int action = 0; action < menuActionRow; action++) {
            int actionY = offsetY + 31 + (menuActionRow - 1 - action) * 15;
            int actionColour = 0xffffff;
            if (x > offsetX && x < offsetX + width && y > actionY - 13 && y < actionY + 3)
                actionColour = 0xffff00;
            fontBold.drawShadowedString(menuActionTexts[action], offsetX + 3, actionY, true, actionColour);
        }

    }

    private int parseCS1(Widget widget, int id) {
        if (widget.cs1opcodes == null || id >= widget.cs1opcodes.length)
            return -2;
        try {
            int opcodes[] = widget.cs1opcodes[id];
            int result = 0;
            int counter = 0;
            int type = 0;
            do {
                int opcode = opcodes[counter++];
                int value = 0;
                byte tempType = 0;
                if (opcode == 0)
                    return result;
                if (opcode == 1)
                    value = skillLevel[opcodes[counter++]];
                if (opcode == 2)
                    value = skillMaxLevel[opcodes[counter++]];
                if (opcode == 3)
                    value = skillExperience[opcodes[counter++]];
                if (opcode == 4) {
                    Widget widget1 = Widget.forId(opcodes[counter++]);
                    int itemId = opcodes[counter++];
                    if (itemId >= 0 && itemId < ItemDefinition.count && (!ItemDefinition.lookup(itemId).members || memberServer)) {
                        for (int item = 0; item < widget1.items.length; item++)
                            if (widget1.items[item] == itemId + 1)
                                value += widget1.itemAmounts[item];

                    }
                }
                if (opcode == 5)
                    value = widgetSettings[opcodes[counter++]];
                if (opcode == 6)
                    value = SKILL_EXPERIENCE[skillMaxLevel[opcodes[counter++]] - 1];
                if (opcode == 7)
                    value = (widgetSettings[opcodes[counter++]] * 100) / 46875;
                if (opcode == 8)
                    value = localPlayer.combatLevel;
                if (opcode == 9) {
                    for (int l1 = 0; l1 < SkillConstants.SKILL_COUNT; l1++)
                        if (SkillConstants.SKILL_TOGGLES[l1])
                            value += skillMaxLevel[l1];

                }
                if (opcode == 10) {
                    Widget widget1 = Widget.forId(opcodes[counter++]);
                    int itemId = opcodes[counter++] + 1;
                    if (itemId >= 0 && itemId < ItemDefinition.count && (!ItemDefinition.lookup(itemId).members || memberServer)) {
                        for (int item = 0; item < widget1.items.length; item++) {
                            if (widget1.items[item] == itemId) {
                                continue;
                            }
                            value = 999999999;
                            break;
                        }

                    }
                }
                if (opcode == 11)
                    value = runEnergy;
                if (opcode == 12)
                    value = userWeight;
                if (opcode == 13) {
                    int i2 = widgetSettings[opcodes[counter++]];
                    int i3 = opcodes[counter++];
                    value = (i2 & 1 << i3) == 0 ? 0 : 1;
                }
                if (opcode == 14) {
                    int j2 = opcodes[counter++];
                    Varbit varbit = Varbit.cache[j2];
                    int l3 = varbit.configId;
                    int i4 = varbit.leastSignificantBit;
                    int j4 = varbit.mostSignificantBit;
                    int k4 = BITFIELD_MAX_VALUE[j4 - i4];
                    value = widgetSettings[l3] >> i4 & k4;
                }
                if (opcode == 15)
                    tempType = 1;
                if (opcode == 16)
                    tempType = 2;
                if (opcode == 17)
                    tempType = 3;
                if (opcode == 18)
                    value = (localPlayer.worldX >> 7) + nextTopLeftTileX;
                if (opcode == 19)
                    value = (localPlayer.worldY >> 7) + nextTopRightTileY;
                if (opcode == 20)
                    value = opcodes[counter++];
                if (tempType == 0) {
                    if (type == 0)
                        result += value;
                    if (type == 1)
                        result -= value;
                    if (type == 2 && value != 0)
                        result /= value;
                    if (type == 3)
                        result *= value;
                    type = 0;
                } else {
                    type = tempType;
                }
            } while (true);
        } catch (Exception _ex) {
            return -1;
        }
    }

    private void drawOnMinimap(ImageRGB sprite, int x, int y) {
        if (sprite == null)
            return;
        int k = cameraHorizontal + anInt916 & 0x7ff;
        int l = x * x + y * y;
        if (l > 6400)
            return;
        int i1 = Model.SINE[k];
        int j1 = Model.COSINE[k];
        i1 = (i1 * 256) / (anInt1233 + 256);
        j1 = (j1 * 256) / (anInt1233 + 256);
        int k1 = y * i1 + x * j1 >> 16;
        int l1 = y * j1 - x * i1 >> 16;
        if (l > 2500) {
            sprite.method467(minimapBackgroundImage, 83 - l1 - sprite.maxHeight
                    / 2 - 4, -49993, ((94 + k1) - sprite.maxWidth / 2) + 4);
        } else {
            sprite.drawImage(((94 + k1) - sprite.maxWidth / 2) + 4, 83 - l1 - sprite.maxHeight / 2 - 4
            );
        }
    }

    private void drawLoginScreen(boolean flag) {
        resetTitleScreen();
        aClass18_1200.createRasterizer();
        titleboxImage.drawImage(0, 0);
        char c = '\u0168';
        char c1 = '\310';
        if (loginScreenState == 0) {
            int j = c1 / 2 + 80;
            fontSmall.drawStringCenter(onDemandRequester.message, c / 2, j, 0x75a9a9, true);
            j = c1 / 2 - 20;
            fontBold.drawStringCenter("Welcome to RuneScape", c / 2, j, 0xffff00, true);
            j += 30;
            int i1 = c / 2 - 80;
            int l1 = c1 / 2 + 20;
            titleboxButtonImage.drawImage(i1 - 73, l1 - 20);
            fontBold.drawStringCenter("New User", i1, l1 + 5, 0xffffff, true);
            i1 = c / 2 + 80;
            titleboxButtonImage.drawImage(i1 - 73, l1 - 20);
            fontBold.drawStringCenter("Existing User", i1, l1 + 5, 0xffffff, true);
        }
        if (loginScreenState == 2) {
            int k = c1 / 2 - 40;
            if (statusLineOne.length() > 0) {
                fontBold.drawStringCenter(statusLineOne, c / 2, k - 15, 0xffff00, true);
                fontBold.drawStringCenter(statusLineTwo, c / 2, k, 0xffff00, true);
                k += 30;
            } else {
                fontBold.drawStringCenter(statusLineTwo, c / 2, k - 7, 0xffff00, true);
                k += 30;
            }
            fontBold.drawShadowedString("Username: " + username
                    + ((anInt977 == 0) & (pulseCycle % 40 < 20) ? "@yel@|" : ""), c / 2 - 90, k, true, 0xffffff);
            k += 15;
            fontBold.drawShadowedString("Password: "
                    + TextUtils.censorPassword(password) + ((anInt977 == 1) & (pulseCycle % 40 < 20) ? "@yel@|" : ""), c / 2 - 88, k, true, 0xffffff
            );
            k += 15;
            if (!flag) {
                int j1 = c / 2 - 80;
                int i2 = c1 / 2 + 50;
                titleboxButtonImage.drawImage(j1 - 73, i2 - 20);
                fontBold.drawStringCenter("Login", j1, i2 + 5, 0xffffff, true);
                j1 = c / 2 + 80;
                titleboxButtonImage.drawImage(j1 - 73, i2 - 20);
                fontBold.drawStringCenter("Cancel", j1, i2 + 5, 0xffffff, true);
            }
        }
        if (loginScreenState == 3) {
            fontBold.drawStringCenter("Create a free account", c / 2, c1 / 2 - 60, 0xffff00, true
            );
            int l = c1 / 2 - 35;
            fontBold.drawStringCenter("To create a new account you need to", c / 2, l, 0xffffff, true
            );
            l += 15;
            fontBold.drawStringCenter("go back to the main RuneScape webpage", c / 2, l, 0xffffff, true
            );
            l += 15;
            fontBold.drawStringCenter("and choose the 'create account'", c / 2, l, 0xffffff, true
            );
            l += 15;
            fontBold.drawStringCenter("button near the top of that page.", c / 2, l, 0xffffff, true
            );
            l += 15;
            int k1 = c / 2;
            int j2 = c1 / 2 + 50;
            titleboxButtonImage.drawImage(k1 - 73, j2 - 20);
            fontBold.drawStringCenter("Cancel", k1, j2 + 5, 0xffffff, true);
        }
        aClass18_1200.drawGraphics(202, 171, super.gameGraphics);
        if (aBoolean1046) {
            aBoolean1046 = false;
            aClass18_1198.drawGraphics(128, 0, super.gameGraphics);
            aClass18_1199.drawGraphics(202, 371, super.gameGraphics);
            aClass18_1203.drawGraphics(0, 265, super.gameGraphics);
            aClass18_1204.drawGraphics(562, 265, super.gameGraphics);
            aClass18_1205.drawGraphics(128, 171, super.gameGraphics);
            aClass18_1206.drawGraphics(562, 171, super.gameGraphics);
        }
    }

    private void method132(Buffer class50_sub1_sub2, int i, boolean flag) {
        if (flag)
            anInt1140 = 287;
        while (class50_sub1_sub2.bitPosition + 21 < i * 8) {
            int j = class50_sub1_sub2.getBits(14);
            if (j == 16383)
                break;
            if (npcs[j] == null)
                npcs[j] = new Npc();
            Npc class50_sub1_sub4_sub3_sub1 = npcs[j];
            anIntArray1134[anInt1133++] = j;
            class50_sub1_sub4_sub3_sub1.pulseCycle = pulseCycle;
            int k = class50_sub1_sub2.getBits(1);
            if (k == 1)
                updatedPlayers[updatedPlayerCount++] = j;
            int l = class50_sub1_sub2.getBits(5);
            if (l > 15)
                l -= 32;
            int i1 = class50_sub1_sub2.getBits(5);
            if (i1 > 15)
                i1 -= 32;
            int j1 = class50_sub1_sub2.getBits(1);
            class50_sub1_sub4_sub3_sub1.npcDefinition = ActorDefinition.getDefinition(class50_sub1_sub2.getBits(13));
            class50_sub1_sub4_sub3_sub1.boundaryDimension = class50_sub1_sub4_sub3_sub1.npcDefinition.boundaryDimension;
            class50_sub1_sub4_sub3_sub1.anInt1600 = class50_sub1_sub4_sub3_sub1.npcDefinition.degreesToTurn;
            class50_sub1_sub4_sub3_sub1.walkAnimationId = class50_sub1_sub4_sub3_sub1.npcDefinition.walkAnimationId;
            class50_sub1_sub4_sub3_sub1.turnAroundAnimationId = class50_sub1_sub4_sub3_sub1.npcDefinition.turnAroundAnimationId;
            class50_sub1_sub4_sub3_sub1.turnRightAnimationId = class50_sub1_sub4_sub3_sub1.npcDefinition.turnRightAnimationId;
            class50_sub1_sub4_sub3_sub1.turnLeftAnimationId = class50_sub1_sub4_sub3_sub1.npcDefinition.turnLeftAnimationId;
            class50_sub1_sub4_sub3_sub1.idleAnimation = class50_sub1_sub4_sub3_sub1.npcDefinition.standAnimationId;
            class50_sub1_sub4_sub3_sub1.setPosition(localPlayer.pathX[0] + i1, localPlayer.pathY[0] + l,
                    j1 == 1);
        }
        class50_sub1_sub2.finishBitAccess();
    }

    public void playSong(int id) {
        if (currentSong != id) {
            nextSong = id;
            songChanging = true;
            onDemandRequester.request(2, nextSong);
            currentSong = id;
        }
    }

    private void stopMidi() {
        SignLink.music.stop();
        SignLink.fadeMidi = 0;
        SignLink.midi = "stop";
    }

    private void adjustMidiVolume(boolean updateMidi, int volume) {
        SignLink.setVolume(volume);
        if (updateMidi) {
            SignLink.midi = "voladjust";
        }
    }

    public void playSound(int id, int type, int delay, int volume) {
        sound[currentSound] = id;
        soundType[currentSound] = type;
        soundDelay[currentSound] = delay + SoundTrack.trackDelays[id];
        soundVolume[currentSound] = volume;
        currentSound++;
    }

    private void parsePlacementPacket(Buffer buf, int opcode) {
        if (opcode == 203) {
            int objectId = buf.getUnsignedShortBE();
            int j3 = buf.getUnsignedByte();
            int modelType = j3 >> 2;
            int modelOrientation = j3 & 3;
            int type = objectTypes[modelType];
            byte byte0 = buf.getInvertedByte();
            int offset = buf.getUnsignedPostNegativeOffsetByte();
            int x = placementX + (offset >> 4 & 7);
            int y = placementY + (offset & 7);
            byte byte1 = buf.getPostNegativeOffsetByte();
            int duration = buf.getUnsignedNegativeOffsetShortBE();
            int id = buf.getUnsignedShortLE();
            byte byte2 = buf.getByte();
            byte byte3 = buf.getPostNegativeOffsetByte();
            int startDelay = buf.getUnsignedShortBE();
            Player player;
            if (id == thisPlayerServerId)
                player = localPlayer;
            else
                player = players[id];
            if (player != null) {
                GameObjectDefinition object = GameObjectDefinition.getDefinition(objectId);
                int vertexHeight = this.intGroundArray[plane][x][y];
                int vertexHeightRight = this.intGroundArray[plane][x + 1][y];
                int vertexHeightTopRight = this.intGroundArray[plane][x + 1][y + 1];
                int vertexHeightTop = this.intGroundArray[plane][x][y + 1];
                Model model = object.getGameObjectModel(modelType, modelOrientation, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
                if (model != null) {
                    createObjectSpawnRequest(plane, x, 0, duration + 1, 0, -1, startDelay + 1, type, y);
                    player.objectAppearanceStartTick = startDelay + pulseCycle;
                    player.objectAppearanceEndTick = duration + pulseCycle;
                    player.playerModel = model;
                    int i23 = object.sizeX;
                    int j23 = object.sizeY;
                    if (modelOrientation == 1 || modelOrientation == 3) {
                        i23 = object.sizeY;
                        j23 = object.sizeX;
                    }
                    player.anInt1743 = x * 128 + i23 * 64;
                    player.anInt1745 = y * 128 + j23 * 64;
                    player.drawHeight = getFloorDrawHeight(plane, player.anInt1743, player.anInt1745
                    );
                    if (byte1 > byte0) {
                        byte byte4 = byte1;
                        byte1 = byte0;
                        byte0 = byte4;
                    }
                    if (byte3 > byte2) {
                        byte byte5 = byte3;
                        byte3 = byte2;
                        byte2 = byte5;
                    }
                    player.anInt1768 = x + byte1;
                    player.anInt1770 = x + byte0;
                    player.anInt1769 = y + byte3;
                    player.anInt1771 = y + byte2;
                }
            }
        }
        if (opcode == 106) { // add ground item dropped by player
            int offset = buf.getUnsignedPostNegativeOffsetByte();
            int x = placementX + (offset >> 4 & 7);
            int y = placementY + (offset & 7);
            int amount = buf.getUnsignedNegativeOffsetShortLE();
            int id = buf.getUnsignedNegativeOffsetShortBE();
            int playerId = buf.getUnsignedNegativeOffsetShortBE();
            if (x >= 0 && y >= 0 && x < 104 && y < 104 && playerId != thisPlayerServerId) {
                Item item = new Item();
                item.itemId = id;
                item.itemCount = amount;
                if (groundItems[plane][x][y] == null)
                    groundItems[plane][x][y] = new LinkedList();
                groundItems[plane][x][y].pushBack(item);
                processGroundItems(x, y);
            }
            return;
        }
        if (opcode == 142) {
            int i1 = buf.getUnsignedShortBE();
            int l3 = buf.getUnsignedPostNegativeOffsetByte();
            int k6 = l3 >> 2;
            int j9 = l3 & 3;
            int i12 = objectTypes[k6];
            int j14 = buf.getUnsignedByte();
            int x = placementX + (j14 >> 4 & 7);
            int y = placementY + (j14 & 7);
            if (x >= 0 && y >= 0 && x < 103 && y < 103) {
                int l18 = intGroundArray[plane][x][y];
                int j19 = intGroundArray[plane][x + 1][y];
                int i20 = intGroundArray[plane][x + 1][y + 1];
                int l20 = intGroundArray[plane][x][y + 1];
                if (i12 == 0) {
                    Wall wall = currentScene.method263(plane, 17734, x, y);
                    if (wall != null) {
                        int k21 = wall.uid >> 14 & 0x7fff;
                        if (k6 == 2) {
                            wall.primary = new GameObject(k21, 4 + j9, 2, j19, i20, l18, l20, i1,
                                    false);
                            wall.secondary = new GameObject(k21, j9 + 1 & 3, 2, j19, i20, l18, l20, i1,
                                    false);
                        } else {
                            wall.primary = new GameObject(k21, j9, k6, j19, i20, l18, l20, i1,
                                    false);
                        }
                    }
                }
                if (i12 == 1) {
                    WallDecoration wallDecoration = currentScene.method264(plane, y, x, false);
                    if (wallDecoration != null)
                        wallDecoration.renderable = new GameObject(wallDecoration.uid >> 14 & 0x7fff, 0, 4, j19, i20, l18, l20, i1,
                                false);
                }
                if (i12 == 2) {
                    InteractiveObject interactiveObject = currentScene.method265(x, (byte) 32, y, plane);
                    if (k6 == 11)
                        k6 = 10;
                    if (interactiveObject != null)
                        interactiveObject.renderable = new GameObject(interactiveObject.uid >> 14 & 0x7fff, j9, k6, j19, i20, l18, l20, i1,
                                false);
                }
                if (i12 == 3) {
                    FloorDecoration floorDecoration = currentScene.method266(plane, y, 0, x);
                    if (floorDecoration != null)
                        floorDecoration.renderable = new GameObject(floorDecoration.uid >> 14 & 0x7fff, j9, 22, j19, i20, l18, l20, i1,
                                false);
                }
            }
            return;
        }
        if (opcode == 107) { // add ground item (dropped by npc or "auto spawn")
            int id = buf.getUnsignedShortBE();
            int offset = buf.getUnsignedInvertedByte();
            int x = placementX + (offset >> 4 & 7);
            int y = placementY + (offset & 7);
            int amount = buf.getUnsignedNegativeOffsetShortBE();
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                Item item = new Item();
                item.itemId = id;
                item.itemCount = amount;
                if (groundItems[plane][x][y] == null)
                    groundItems[plane][x][y] = new LinkedList();
                groundItems[plane][x][y].pushBack(item);
                processGroundItems(x, y);
            }
            return;
        }
        if (opcode == 121) { // update amount of ground item
            int offset = buf.getUnsignedByte();
            int x = placementX + (offset >> 4 & 7);
            int y = placementY + (offset & 7);
            int id = buf.getUnsignedShortBE();
            int amount = buf.getUnsignedShortBE();
            int newAmount = buf.getUnsignedShortBE();
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                LinkedList list = groundItems[plane][x][y];
                if (list != null) {
                    for (Item item = (Item) list.first(); item != null; item = (Item) list.next()) {
                        if (item.itemId != (id & 0x7fff) || item.itemCount != amount)
                            continue;
                        item.itemCount = newAmount;
                        break;
                    }

                    processGroundItems(x, y);
                }
            }
            return;
        }
        if (opcode == 181) {
            int offset = buf.getUnsignedByte();
            int x = placementX + (offset >> 4 & 7);
            int y = placementY + (offset & 7);
            int i10 = x + buf.getByte();
            int l12 = y + buf.getByte();
            int l14 = buf.getShortBE();
            int k16 = buf.getUnsignedShortBE();
            int i18 = buf.getUnsignedByte() * 4;
            int i19 = buf.getUnsignedByte() * 4;
            int k19 = buf.getUnsignedShortBE();
            int j20 = buf.getUnsignedShortBE();
            int i21 = buf.getUnsignedByte();
            int j21 = buf.getUnsignedByte();
            if (x >= 0 && y >= 0 && x < 104 && y < 104 && i10 >= 0 && l12 >= 0 && i10 < 104 && l12 < 104
                    && k16 != 65535) {
                x = x * 128 + 64;
                y = y * 128 + 64;
                i10 = i10 * 128 + 64;
                l12 = l12 * 128 + 64;
                Projectile class50_sub1_sub4_sub2 = new Projectile(plane, i19, j21, y,
                        k16, j20 + pulseCycle, i21, l14, getFloorDrawHeight(plane, x, y) - i18, x, k19 + pulseCycle);
                class50_sub1_sub4_sub2.trackTarget(i10, l12, getFloorDrawHeight(plane, i10, l12) - i19, k19
                        + pulseCycle);
                aClass6_1282.pushBack(class50_sub1_sub4_sub2);
            }
            return;
        }
        if (opcode == 41) {
            int offset = buf.getUnsignedByte();
            int x = placementX + (offset >> 4 & 7);
            int y = placementY + (offset & 7);
            int soundId = buf.getUnsignedShortBE();
            int i13 = buf.getUnsignedByte();
            int i15 = i13 >> 4 & 0xf;
            int type = i13 & 7;
            if (localPlayer.pathX[0] >= x - i15
                    && localPlayer.pathX[0] <= x + i15
                    && localPlayer.pathY[0] >= y - i15
                    && localPlayer.pathY[0] <= y + i15 && aBoolean1301 && !lowMemory
                    && currentSound < 50) {
                sound[currentSound] = soundId;
                soundType[currentSound] = type;
                soundDelay[currentSound] = SoundTrack.trackDelays[soundId];
                currentSound++;
            }
        }
        if (opcode == 59) {
            int j2 = buf.getUnsignedByte();
            int i5 = placementX + (j2 >> 4 & 7);
            int l7 = placementY + (j2 & 7);
            int k10 = buf.getUnsignedShortBE();
            int j13 = buf.getUnsignedByte();
            int j15 = buf.getUnsignedShortBE();
            if (i5 >= 0 && l7 >= 0 && i5 < 104 && l7 < 104) {
                i5 = i5 * 128 + 64;
                l7 = l7 * 128 + 64;
                GameAnimableObject gameAnimableObject = new GameAnimableObject(plane, pulseCycle, j15, k10, getFloorDrawHeight(plane, i5, l7) - j13, l7, i5);
                aClass6_1210.pushBack(gameAnimableObject);
            }
            return;
        }
        if (opcode == 152) {
            int k2 = buf.getUnsignedInvertedByte();
            int j5 = k2 >> 2;
            int i8 = k2 & 3;
            int l10 = objectTypes[j5];
            int k13 = buf.getUnsignedNegativeOffsetShortLE();
            int k15 = buf.getUnsignedPostNegativeOffsetByte();
            int i17 = placementX + (k15 >> 4 & 7);
            int j18 = placementY + (k15 & 7);
            if (i17 >= 0 && j18 >= 0 && i17 < 104 && j18 < 104)
                createObjectSpawnRequest(plane, i17, i8, -1, j5, k13, 0, l10, j18);
            return;
        }
        if (opcode == 208) { // remove ground item
            int id = buf.getUnsignedNegativeOffsetShortBE();
            int offset = buf.getUnsignedPostNegativeOffsetByte();
            int x = placementX + (offset >> 4 & 7);
            int y = placementY + (offset & 7);
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                LinkedList list = groundItems[plane][x][y];
                if (list != null) {
                    for (Item item = (Item) list.first(); item != null; item = (Item) list.next()) {
                        if (item.itemId != (id & 0x7fff))
                            continue;
                        item.remove();
                        break;
                    }

                    if (list.first() == null)
                        groundItems[plane][x][y] = null;
                    processGroundItems(x, y);
                }
            }
            return;
        }
        if (opcode == 88) {
            int positionOffset = buf.getUnsignedPreNegativeOffsetByte();
            int x = placementX + (positionOffset >> 4 & 7);
            int y = placementY + (positionOffset & 7);
            int data = buf.getUnsignedPreNegativeOffsetByte();
            int objectType = data >> 2;
            int orientation = data & 3;
            int type = objectTypes[objectType];
            if (x >= 0 && y >= 0 && x < 104 && y < 104)
                createObjectSpawnRequest(plane, x, orientation, -1, objectType, -1, 0, type, y);
        }
    }

    private void method134() {
        tabImageProducer.createRasterizer();
        Rasterizer3D.lineOffsets = sidebarOffsets;
        inventoryBackgroundImage.drawImage(0, 0);
        if (openInvOverLayId != -1)
            drawInterface(0, 0, Widget.forId(openInvOverLayId), 0, 8);
        else if (tabInterfaceIDs[currentTabId] != -1)
            drawInterface(0, 0, Widget.forId(tabInterfaceIDs[currentTabId]), 0, 8);
        if (menuOpen && menuScreenArea == 1)
            drawMenu();
        tabImageProducer.drawGraphics(553, 205, super.gameGraphics);
        gameScreenImageProducer.createRasterizer();
        Rasterizer3D.lineOffsets = viewportOffsets;
    }


    private void method136(Actor class50_sub1_sub4_sub3, boolean flag, int i) {
        method137(class50_sub1_sub4_sub3.worldX, i, class50_sub1_sub4_sub3.worldY, -214);
        if (!flag)
            ;
    }

    private void method137(int i, int j, int k, int l) {
        if (i < 128 || k < 128 || i > 13056 || k > 13056) {
            anInt932 = -1;
            anInt933 = -1;
            return;
        }
        int i1 = getFloorDrawHeight(plane, i, k) - j;
        i -= cameraX;
        i1 -= cameraZ;
        k -= cameraY;
        int j1 = Model.SINE[cameraVerticalRotation];
        int k1 = Model.COSINE[cameraVerticalRotation];
        int l1 = Model.SINE[cameraHorizontalRotation];
        int i2 = Model.COSINE[cameraHorizontalRotation];
        int j2 = k * l1 + i * i2 >> 16;
        k = k * i2 - i * l1 >> 16;
        i = j2;
        j2 = i1 * k1 - k * j1 >> 16;
        k = i1 * j1 + k * k1 >> 16;
        while (l >= 0)
            opcode = -1;
        i1 = j2;
        if (k >= 50) {
            anInt932 = Rasterizer3D.centerX + (i << 9) / k;
            anInt933 = Rasterizer3D.centerY + (i1 << 9) / k;
        } else {
            anInt932 = -1;
            anInt933 = -1;
        }
    }

    private void infoDump() {
        System.out.println("============");
        System.out.println("flame-cycle:" + flameCycle);
        if (onDemandRequester != null)
            System.out.println("Od-cycle:" + onDemandRequester.cycle);
        System.out.println("loop-cycle:" + pulseCycle);
        System.out.println("draw-cycle:" + drawCycle);
        System.out.println("ptype:" + opcode);
        System.out.println("psize:" + packetSize);
        if (gameConnection != null)
            gameConnection.printDebug();
        super.dumpRequested = true;
    }

    public Component getParentComponent() {
        if (SignLink.applet != null)
            return SignLink.applet;
        if (super.gameFrame != null)
            return super.gameFrame;
        else
            return this;
    }

    public void drawLoadingText(int i, String s) {
        anInt1322 = i;
        aString1027 = s;
        resetTitleScreen();
        if (titleArchive == null) {
            super.drawLoadingText(i, s);
            return;
        }
        aClass18_1200.createRasterizer();
        char c = '\u0168';
        char c1 = '\310';
        byte byte0 = 20;
        fontBold.drawStringLeft("RuneScape is loading - please wait...", c / 2, c1 / 2 - 26 - byte0, 0xffffff
        );
        int j = c1 / 2 - 18 - byte0;
        Rasterizer.drawUnfilledRectangle(c / 2 - 152, j, 304, 34, 0x8c1111);
        Rasterizer.drawUnfilledRectangle(c / 2 - 151, j + 1, 302, 32, 0);
        Rasterizer.drawFilledRectangle(c / 2 - 150, j + 2, i * 3, 30, 0x8c1111);
        Rasterizer.drawFilledRectangle((c / 2 - 150) + i * 3, j + 2, 300 - i * 3, 30, 0);
        fontBold.drawStringLeft(s, c / 2, (c1 / 2 + 5) - byte0, 0xffffff);
        aClass18_1200.drawGraphics(202, 171, super.gameGraphics);
        if (aBoolean1046) {
            aBoolean1046 = false;
            if (!aBoolean1243) {
                flameLeftBackground.drawGraphics(0, 0, super.gameGraphics);
                flameRightBackground.drawGraphics(637, 0, super.gameGraphics);
            }
            aClass18_1198.drawGraphics(128, 0, super.gameGraphics);
            aClass18_1199.drawGraphics(202, 371, super.gameGraphics);
            aClass18_1203.drawGraphics(0, 265, super.gameGraphics);
            aClass18_1204.drawGraphics(562, 265, super.gameGraphics);
            aClass18_1205.drawGraphics(128, 171, super.gameGraphics);
            aClass18_1206.drawGraphics(562, 171, super.gameGraphics);
        }
    }

    private void prepareTitleBackground() {
        byte abyte0[] = titleArchive.getFile("title.dat");
        ImageRGB class50_sub1_sub1_sub1 = new ImageRGB(abyte0, this);
        flameLeftBackground.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(0, 0);
        flameRightBackground.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(-637, 0);
        aClass18_1198.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(-128, 0);
        aClass18_1199.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(-202, -371);
        aClass18_1200.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(-202, -171);
        aClass18_1203.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(0, -265);
        aClass18_1204.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(-562, -265);
        aClass18_1205.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(-128, -171);
        aClass18_1206.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(-562, -171);
        int ai[] = new int[class50_sub1_sub1_sub1.width];
        for (int i = 0; i < class50_sub1_sub1_sub1.height; i++) {
            for (int j = 0; j < class50_sub1_sub1_sub1.width; j++)
                ai[j] = class50_sub1_sub1_sub1.pixels[(class50_sub1_sub1_sub1.width - j - 1)
                        + class50_sub1_sub1_sub1.width * i];

            for (int l = 0; l < class50_sub1_sub1_sub1.width; l++)
                class50_sub1_sub1_sub1.pixels[l + class50_sub1_sub1_sub1.width * i] = ai[l];

        }

        flameLeftBackground.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(382, 0);
        flameRightBackground.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(-255, 0);
        aClass18_1198.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(254, 0);
        aClass18_1199.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(180, -371);
        aClass18_1200.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(180, -171);
        aClass18_1203.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(382, -265);
        aClass18_1204.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(-180, -265);
        aClass18_1205.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(254, -171);
        aClass18_1206.createRasterizer();
        class50_sub1_sub1_sub1.drawInverse(-180, -171);
        class50_sub1_sub1_sub1 = new ImageRGB(titleArchive, "logo", 0);
        aClass18_1198.createRasterizer();
        class50_sub1_sub1_sub1.drawImage(382 - class50_sub1_sub1_sub1.width / 2 - 128, 18);
        class50_sub1_sub1_sub1 = null;
        abyte0 = null;
        ai = null;
        System.gc();
    }

    private void method140(byte byte0, SpawnObjectNode spawnObjectNode) {
        int i = 0;
        int j = -1;
        int k = 0;
        int l = 0;
        if (byte0 != -61)
            outBuffer.putByte(175);
        if (spawnObjectNode.anInt1392 == 0)
            i = currentScene.method267(spawnObjectNode.anInt1391, spawnObjectNode.anInt1393, spawnObjectNode.anInt1394);
        if (spawnObjectNode.anInt1392 == 1)
            i = currentScene.method268(spawnObjectNode.anInt1393, (byte) 4, spawnObjectNode.anInt1391,
                    spawnObjectNode.anInt1394);
        if (spawnObjectNode.anInt1392 == 2)
            i = currentScene.method269(spawnObjectNode.anInt1391, spawnObjectNode.anInt1393, spawnObjectNode.anInt1394);
        if (spawnObjectNode.anInt1392 == 3)
            i = currentScene.getFloorDecorationHash(spawnObjectNode.anInt1391, spawnObjectNode.anInt1393, spawnObjectNode.anInt1394);
        if (i != 0) {
            int i1 = currentScene.method271(spawnObjectNode.anInt1391, spawnObjectNode.anInt1393, spawnObjectNode.anInt1394, i);
            j = i >> 14 & 0x7fff;
            k = i1 & 0x1f;
            l = i1 >> 6;
        }
        spawnObjectNode.anInt1387 = j;
        spawnObjectNode.anInt1389 = k;
        spawnObjectNode.anInt1388 = l;
    }

    private void method141() {
        aBoolean1243 = false;
        while (aBoolean1320) {
            aBoolean1243 = false;
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
            }
        }
        titleboxImage = null;
        titleboxButtonImage = null;
        titleFlameEmblem = null;
        anIntArray1310 = null;
        anIntArray1311 = null;
        anIntArray1312 = null;
        anIntArray1313 = null;
        anIntArray1176 = null;
        anIntArray1177 = null;
        anIntArray1084 = null;
        anIntArray1085 = null;
        anImageRGB1226 = null;
        anImageRGB1227 = null;
    }

    private void drawInterface(int i, int j, Widget class13, int k, int l) {
        if (class13.type != 0 || class13.children == null)
            return;
        if (class13.hiddenUntilHovered && anInt1302 != class13.id && anInt1280 != class13.id
                && anInt1106 != class13.id)
            return;
        int i1 = Rasterizer.topX;
        int j1 = Rasterizer.topY;
        int k1 = Rasterizer.bottomX;
        int l1 = Rasterizer.bottomY;
        Rasterizer.setCoordinates(i, j, i + class13.height, j + class13.width);
        int i2 = class13.children.length;
        if (l != 8)
            opcode = -1;
        for (int j2 = 0; j2 < i2; j2++) {
            int k2 = class13.childrenX[j2] + j;
            int l2 = (class13.childrenY[j2] + i) - k;
            Widget child = Widget.forId(class13.children[j2]);
            k2 += child.xOffset;
            l2 += child.yOffset;
            if (child.contentType > 0)
                method103((byte) 2, child);
            if (child.type == 0) {
                if (child.scrollPosition > child.scrollLimit - child.height)
                    child.scrollPosition = child.scrollLimit - child.height;
                if (child.scrollPosition < 0)
                    child.scrollPosition = 0;
                drawInterface(l2, k2, child, child.scrollPosition, 8);
                if (child.scrollLimit > child.height)
                    method56(true, child.scrollPosition, k2 + child.width, child.height, child.scrollLimit,
                            l2);
            } else if (child.type != 1)
                if (child.type == 2) {
                    int i3 = 0;
                    for (int i4 = 0; i4 < child.height; i4++) {
                        for (int j5 = 0; j5 < child.width; j5++) {
                            int i6 = k2 + j5 * (32 + child.itemSpritePadsX);
                            int l6 = l2 + i4 * (32 + child.itemSpritePadsY);
                            if (i3 < 20) {
                                i6 += child.imageX[i3];
                                l6 += child.imageY[i3];
                            }
                            if (child.items[i3] > 0) {
                                int i7 = 0;
                                int j8 = 0;
                                int l10 = child.items[i3] - 1;
                                if (i6 > Rasterizer.topX - 32 && i6 < Rasterizer.bottomX
                                        && l6 > Rasterizer.topY - 32 && l6 < Rasterizer.bottomY
                                        || activeInterfaceType != 0 && selectedInventorySlot == i3) {
                                    int k11 = 0;
                                    if (itemSelected == 1 && anInt1147 == i3 && anInt1148 == child.id)
                                        k11 = 0xffffff;
                                    ImageRGB class50_sub1_sub1_sub1_2 = ItemDefinition.sprite(
                                            l10, child.itemAmounts[i3], k11);
                                    if (class50_sub1_sub1_sub1_2 != null) {
                                        if (activeInterfaceType != 0 && selectedInventorySlot == i3 && modifiedWidgetId == child.id) {
                                            i7 = super.mouseX - anInt1114;
                                            j8 = super.mouseY - anInt1115;
                                            if (i7 < 5 && i7 > -5)
                                                i7 = 0;
                                            if (j8 < 5 && j8 > -5)
                                                j8 = 0;
                                            if (anInt1269 < 5) {
                                                i7 = 0;
                                                j8 = 0;
                                            }
                                            class50_sub1_sub1_sub1_2.drawImageAlpha(i6 + i7, l6 + j8, 128);
                                            if (l6 + j8 < Rasterizer.topY && class13.scrollPosition > 0) {
                                                int i12 = (tickDelta * (Rasterizer.topY - l6 - j8)) / 3;
                                                if (i12 > tickDelta * 10)
                                                    i12 = tickDelta * 10;
                                                if (i12 > class13.scrollPosition)
                                                    i12 = class13.scrollPosition;
                                                class13.scrollPosition -= i12;
                                                anInt1115 += i12;
                                            }
                                            if (l6 + j8 + 32 > Rasterizer.bottomY
                                                    && class13.scrollPosition < class13.scrollLimit - class13.height) {
                                                int j12 = (tickDelta * ((l6 + j8 + 32) - Rasterizer.bottomY)) / 3;
                                                if (j12 > tickDelta * 10)
                                                    j12 = tickDelta * 10;
                                                if (j12 > class13.scrollLimit - class13.height - class13.scrollPosition)
                                                    j12 = class13.scrollLimit - class13.height - class13.scrollPosition;
                                                class13.scrollPosition += j12;
                                                anInt1115 -= j12;
                                            }
                                        } else if (atInventoryInterfaceType != 0 && anInt1331 == i3 && anInt1330 == child.id)
                                            class50_sub1_sub1_sub1_2.drawImageAlpha(i6, l6, 128);
                                        else
                                            class50_sub1_sub1_sub1_2.drawImage(i6, l6);
                                        if (class50_sub1_sub1_sub1_2.maxWidth == 33 || child.itemAmounts[i3] != 1) {
                                            int k12 = child.itemAmounts[i3];
                                            fontSmall.drawString(getShortenedAmountText(k12), i6 + 1 + i7, l6 + 10 + j8, 0
                                            );
                                            fontSmall.drawString(getShortenedAmountText(k12), i6 + i7, l6 + 9 + j8, 0xffff00
                                            );
                                        }
                                    }
                                }
                            } else if (child.images != null && i3 < 20) {
                                ImageRGB class50_sub1_sub1_sub1_1 = child.images[i3];
                                if (class50_sub1_sub1_sub1_1 != null)
                                    class50_sub1_sub1_sub1_1.drawImage(i6, l6);
                            }
                            i3++;
                        }

                    }

                } else if (child.type == 3) {
                    boolean flag = false;
                    if (anInt1106 == child.id || anInt1280 == child.id
                            || anInt1302 == child.id)
                        flag = true;
                    int j3;
                    if (componentEnabled(child)) {
                        j3 = child.enabledColor;
                        if (flag && child.enabledHoveredColor != 0)
                            j3 = child.enabledHoveredColor;
                    } else {
                        j3 = child.disabledColor;
                        if (flag && child.disabledHoveredColor != 0)
                            j3 = child.disabledHoveredColor;
                    }
                    if (child.alpha == 0) {
                        if (child.filled)
                            Rasterizer.drawFilledRectangle(k2, l2, child.width, child.height, j3);
                        else
                            Rasterizer.drawUnfilledRectangle(k2, l2, child.width, child.height, j3);
                    } else if (child.filled)
                        Rasterizer.drawFilledRectangleAlhpa(k2, l2, child.width, child.height, j3,
                                256 - (child.alpha & 0xff));
                    else
                        Rasterizer.drawUnfilledRectangleAlpha(k2, l2, child.width, child.height, j3,
                                256 - (child.alpha & 0xff));
                } else if (child.type == 4) {
                    TypeFace class50_sub1_sub1_sub2 = child.typeFaces;
                    String s = child.disabledText;
                    boolean flag1 = false;
                    if (anInt1106 == child.id || anInt1280 == child.id
                            || anInt1302 == child.id)
                        flag1 = true;
                    int j4;
                    if (componentEnabled(child)) {
                        j4 = child.enabledColor;
                        if (flag1 && child.enabledHoveredColor != 0)
                            j4 = child.enabledHoveredColor;
                        if (child.enabledText.length() > 0)
                            s = child.enabledText;
                    } else {
                        j4 = child.disabledColor;
                        if (flag1 && child.disabledHoveredColor != 0)
                            j4 = child.disabledHoveredColor;
                    }
                    if (child.actionType == 6 && aBoolean1239) {
                        s = "Please wait...";
                        j4 = child.disabledColor;
                    }
                    if (Rasterizer.width == 479) {
                        if (j4 == 0xffff00)
                            j4 = 255;
                        if (j4 == 49152)
                            j4 = 0xffffff;
                    }
                    for (int j7 = l2 + class50_sub1_sub1_sub2.characterDefaultHeight; s.length() > 0; j7 += class50_sub1_sub1_sub2.characterDefaultHeight) {
                        if (s.indexOf("%") != -1) {
                            do {
                                int k8 = s.indexOf("%1");
                                if (k8 == -1)
                                    break;
                                s = s.substring(0, k8) + method89(parseCS1(child, 0), 8) + s.substring(k8 + 2);
                            } while (true);
                            do {
                                int l8 = s.indexOf("%2");
                                if (l8 == -1)
                                    break;
                                s = s.substring(0, l8) + method89(parseCS1(child, 1), 8) + s.substring(l8 + 2);
                            } while (true);
                            do {
                                int i9 = s.indexOf("%3");
                                if (i9 == -1)
                                    break;
                                s = s.substring(0, i9) + method89(parseCS1(child, 2), 8) + s.substring(i9 + 2);
                            } while (true);
                            do {
                                int j9 = s.indexOf("%4");
                                if (j9 == -1)
                                    break;
                                s = s.substring(0, j9) + method89(parseCS1(child, 3), 8) + s.substring(j9 + 2);
                            } while (true);
                            do {
                                int k9 = s.indexOf("%5");
                                if (k9 == -1)
                                    break;
                                s = s.substring(0, k9) + method89(parseCS1(child, 4), 8) + s.substring(k9 + 2);
                            } while (true);
                        }
                        int l9 = s.indexOf("\\n");
                        String s3;
                        if (l9 != -1) {
                            s3 = s.substring(0, l9);
                            s = s.substring(l9 + 2);
                        } else {
                            s3 = s;
                            s = "";
                        }
                        if (child.typeFaceCentered)
                            class50_sub1_sub1_sub2.drawStringCenter(s3, k2
                                    + child.width / 2, j7, j4, child.typeFaceShadowed);
                        else
                            class50_sub1_sub1_sub2.drawShadowedString(s3, k2, j7, child.typeFaceShadowed, j4);
                    }

                } else if (child.type == 5) {
                    ImageRGB class50_sub1_sub1_sub1;
                    if (componentEnabled(child))
                        class50_sub1_sub1_sub1 = child.enabledImage;
                    else
                        class50_sub1_sub1_sub1 = child.disabledImage;
                    switch (child.id) {
                        case 1164:
                        case 1167:
                        case 1170:
                        case 1174:
                        case 1540:
                        case 1541:
                        case 7455:
                            class50_sub1_sub1_sub1 = child.enabledImage;
                            break;
                        default:
                            break;
                    }
                    if (class50_sub1_sub1_sub1 != null)
                        class50_sub1_sub1_sub1.drawImage(k2, l2);
                } else if (child.type == 6) {
                    int k3 = Rasterizer3D.centerX;
                    int k4 = Rasterizer3D.centerY;
                    Rasterizer3D.centerX = k2 + child.width / 2;
                    Rasterizer3D.centerY = l2 + child.height / 2;
                    int k5 = Rasterizer3D.SINE[child.rotationX] * child.zoom >> 16;
                    int j6 = Rasterizer3D.COSINE[child.rotationX] * child.zoom >> 16;
                    boolean flag2 = componentEnabled(child);
                    int k7;
                    if (flag2)
                        k7 = child.enabledAnimation;
                    else
                        k7 = child.disabledAnimation;
                    Model class50_sub1_sub4_sub4;
                    if (k7 == -1) {
                        class50_sub1_sub4_sub4 = child.getAnimatedModel(-1, -1, flag2);
                    } else {
                        AnimationSequence class14 = AnimationSequence.animations[k7];
                        class50_sub1_sub4_sub4 = child.getAnimatedModel(class14.frame1Ids[child.animationFrame], class14.getPrimaryFrame[child.animationFrame],
                                flag2);
                    }
                    if (class50_sub1_sub4_sub4 != null)
                        class50_sub1_sub4_sub4.render(0, child.rotationY, 0, child.rotationX, 0, k5, j6);
                    Rasterizer3D.centerX = k3;
                    Rasterizer3D.centerY = k4;
                } else {
                    if (child.type == 7) {
                        TypeFace class50_sub1_sub1_sub2_1 = child.typeFaces;
                        int l4 = 0;
                        for (int l5 = 0; l5 < child.height; l5++) {
                            for (int k6 = 0; k6 < child.width; k6++) {
                                if (child.items[l4] > 0) {
                                    ItemDefinition class16 = ItemDefinition.lookup(child.items[l4] - 1);
                                    String s6 = String.valueOf(class16.name);
                                    if (class16.stackable || child.itemAmounts[l4] != 1)
                                        s6 = s6 + " x" + getFullAmountText(child.itemAmounts[l4]);
                                    int i10 = k2 + k6 * (115 + child.itemSpritePadsX);
                                    int i11 = l2 + l5 * (12 + child.itemSpritePadsY);
                                    if (child.typeFaceCentered)
                                        class50_sub1_sub1_sub2_1.drawStringCenter(s6, i10 + child.width / 2, i11, child.disabledColor, child.typeFaceShadowed
                                        );
                                    else
                                        class50_sub1_sub1_sub2_1.drawShadowedString(s6, i10, i11, child.typeFaceShadowed, child.disabledColor
                                        );
                                }
                                l4++;
                            }

                        }

                    }
                    if (child.type == 8
                            && (anInt1284 == child.id || anInt1044 == child.id || anInt1129 == child.id)
                            && anInt893 == 100) {
                        int l3 = 0;
                        int i5 = 0;
                        TypeFace class50_sub1_sub1_sub2_2 = fontNormal;
                        for (String s1 = child.disabledText; s1.length() > 0; ) {
                            int l7 = s1.indexOf("\\n");
                            String s4;
                            if (l7 != -1) {
                                s4 = s1.substring(0, l7);
                                s1 = s1.substring(l7 + 2);
                            } else {
                                s4 = s1;
                                s1 = "";
                            }
                            int j10 = class50_sub1_sub1_sub2_2.getStringEffectWidth(s4);
                            if (j10 > l3)
                                l3 = j10;
                            i5 += class50_sub1_sub1_sub2_2.characterDefaultHeight + 1;
                        }

                        l3 += 6;
                        i5 += 7;
                        int i8 = (k2 + child.width) - 5 - l3;
                        int k10 = l2 + child.height + 5;
                        if (i8 < k2 + 5)
                            i8 = k2 + 5;
                        if (i8 + l3 > j + class13.width)
                            i8 = (j + class13.width) - l3;
                        if (k10 + i5 > i + class13.height)
                            k10 = (i + class13.height) - i5;
                        Rasterizer.drawFilledRectangle(i8, k10, l3, i5, 0xffffa0);
                        Rasterizer.drawUnfilledRectangle(i8, k10, l3, i5, 0);
                        String s2 = child.disabledText;
                        for (int j11 = k10 + class50_sub1_sub1_sub2_2.characterDefaultHeight + 2; s2.length() > 0; j11 += class50_sub1_sub1_sub2_2.characterDefaultHeight + 1) {
                            int l11 = s2.indexOf("\\n");
                            String s5;
                            if (l11 != -1) {
                                s5 = s2.substring(0, l11);
                                s2 = s2.substring(l11 + 2);
                            } else {
                                s5 = s2;
                                s2 = "";
                            }
                            class50_sub1_sub1_sub2_2.drawShadowedString(s5, i8 + 3, j11, false, 0);
                        }

                    }
                }
        }

        Rasterizer.setCoordinates(j1, i1, l1, k1);
    }

    private void method143(byte byte0) {
        if (byte0 != -40)
            aBoolean1207 = !aBoolean1207;
        if (lowMemory && loadingStage == 2 && Region.onBuildTimePlane != plane) {
            method125(null, "Loading - please wait.");
            loadingStage = 1;
            aLong1229 = System.currentTimeMillis();
        }
        if (loadingStage == 1) {
            int i = method144(5);
            if (i != 0 && System.currentTimeMillis() - aLong1229 > 0x57e40L) {
                SignLink.reportError(username + " glcfb " + serverSeed + "," + i + "," + lowMemory + ","
                        + stores[0] + "," + onDemandRequester.method333() + "," + plane + ","
                        + chunkX + "," + chunkY);
                aLong1229 = System.currentTimeMillis();
            }
        }
        if (loadingStage == 2 && plane != anInt1276) {
            anInt1276 = plane;
            renderViewport(plane);
        }
    }

    private int method144(int i) {
        for (int j = 0; j < terrainData.length; j++) {
            if (terrainData[j] == null && terrainDataIds[j] != -1)
                return -1;
            if (objectData[j] == null && objectDataIds[j] != -1)
                return -2;
        }

        boolean flag = true;
        if (i < 5 || i > 5)
            aBoolean953 = !aBoolean953;
        for (int k = 0; k < terrainData.length; k++) {
            byte abyte0[] = objectData[k];
            if (abyte0 != null) {
                int l = (mapCoordinates[k] >> 8) * 64 - nextTopLeftTileX;
                int i1 = (mapCoordinates[k] & 0xff) * 64 - nextTopRightTileY;
                if (aBoolean1163) {
                    l = 10;
                    i1 = 10;
                }
                flag &= Region.method181(l, i1, abyte0, 24515);
            }
        }

        if (!flag)
            return -3;
        if (aBoolean1209) {
            return -4;
        } else {
            loadingStage = 2;
            Region.onBuildTimePlane = plane;
            method93(175);
            outBuffer.putOpcode(6);
            return 0;
        }
    }

    private void createObjectSpawnRequest(int plane, int x, int orientation, int duration, int objectType, int objectId, int startDelay, int type, int y) {
        SpawnObjectNode request = null;
        for (SpawnObjectNode request2 = (SpawnObjectNode) spawnObjectList.first(); request2 != null; request2 = (SpawnObjectNode) spawnObjectList
                .next()) {
            if (request2.anInt1391 != plane || request2.anInt1393 != x || request2.anInt1394 != y
                    || request2.anInt1392 != type)
                continue;
            request = request2;
            break;
        }

        if (request == null) {
            request = new SpawnObjectNode();
            request.anInt1391 = plane;
            request.anInt1392 = type;
            request.anInt1393 = x;
            request.anInt1394 = y;
            method140((byte) -61, request);
            spawnObjectList.pushBack(request);
        }
        request.anInt1384 = objectId;
        request.anInt1386 = objectType;
        request.anInt1385 = orientation;
        request.anInt1395 = startDelay;
        request.anInt1390 = duration;
    }

    private void method146(byte byte0) {
        if (byte0 != 4)
            return;
        if (minimapState != 0)
            return;
        if (super.clickType == 1) {
            int i = super.clickX - 25 - 550;
            int j = super.clickY - 5 - 4;
            if (i >= 0 && j >= 0 && i < 146 && j < 151) {
                i -= 73;
                j -= 75;
                int k = cameraHorizontal + anInt916 & 0x7ff;
                int l = Rasterizer3D.SINE[k];
                int i1 = Rasterizer3D.COSINE[k];
                l = l * (anInt1233 + 256) >> 8;
                i1 = i1 * (anInt1233 + 256) >> 8;
                int j1 = j * l + i * i1 >> 11;
                int k1 = j * i1 - i * l >> 11;
                int l1 = localPlayer.worldX + j1 >> 7;
                int i2 = localPlayer.worldY - k1 >> 7;
                boolean flag = walk(true, false, i2, localPlayer.pathY[0], 0, 0, 1, 0, l1,
                        0, 0, localPlayer.pathX[0]);
                if (flag) {
                    outBuffer.putByte(i);
                    outBuffer.putByte(j);
                    outBuffer.putShortBE(cameraHorizontal);
                    outBuffer.putByte(57);
                    outBuffer.putByte(anInt916);
                    outBuffer.putByte(anInt1233);
                    outBuffer.putByte(89);
                    outBuffer.putShortBE(localPlayer.worldX);
                    outBuffer.putShortBE(localPlayer.worldY);
                    outBuffer.putByte(anInt1126);
                    outBuffer.putByte(63);
                }
            }
        }
    }

    private void method147(int i) {
        if (super.imageProducer != null)
            return;
        method141();
        aClass18_1198 = null;
        aClass18_1199 = null;
        aClass18_1200 = null;
        if (i >= 0)
            anInt1004 = -4;
        flameLeftBackground = null;
        flameRightBackground = null;
        aClass18_1203 = null;
        aClass18_1204 = null;
        aClass18_1205 = null;
        aClass18_1206 = null;
        chatboxProducingGraphicsBuffer = null;
        aClass18_1157 = null;
        tabImageProducer = null;
        gameScreenImageProducer = null;
        aClass18_1108 = null;
        aClass18_1109 = null;
        aClass18_1110 = null;
        super.imageProducer = new ProducingGraphicsBuffer(765, 503, getParentComponent());
        aBoolean1046 = true;
    }

    private boolean method148(int i, String s) {
        if (s == null)
            return false;
        for (int j = 0; j < friendsCount; j++)
            if (s.equalsIgnoreCase(friendUsernames[j]))
                return true;

        if (i != 13292)
            aBoolean1014 = !aBoolean1014;
        return s.equalsIgnoreCase(localPlayer.playerName);
    }

    private void method149(int i) {
        while (i >= 0)
            opcode = buffer.getUnsignedByte();
        if (loginScreenState == 0) {
            int j = super.width / 2 - 80;
            int i1 = super.height / 2 + 20;
            i1 += 20;
            if (super.clickType == 1 && super.clickX >= j - 75 && super.clickX <= j + 75 && super.clickY >= i1 - 20
                    && super.clickY <= i1 + 20) {
                loginScreenState = 3;
                anInt977 = 0;
            }
            j = super.width / 2 + 80;
            if (super.clickType == 1 && super.clickX >= j - 75 && super.clickX <= j + 75 && super.clickY >= i1 - 20
                    && super.clickY <= i1 + 20) {
                statusLineOne = "";
                statusLineTwo = "Enter your username & password.";
                loginScreenState = 2;
                anInt977 = 0;
            }
        } else {
            if (loginScreenState == 2) {
                int k = super.height / 2 - 40;
                k += 30;
                k += 25;
                if (super.clickType == 1 && super.clickY >= k - 15 && super.clickY < k)
                    anInt977 = 0;
                k += 15;
                if (super.clickType == 1 && super.clickY >= k - 15 && super.clickY < k)
                    anInt977 = 1;
                k += 15;
                int j1 = super.width / 2 - 80;
                int l1 = super.height / 2 + 50;
                l1 += 20;
                if (super.clickType == 1 && super.clickX >= j1 - 75 && super.clickX <= j1 + 75
                        && super.clickY >= l1 - 20 && super.clickY <= l1 + 20) {
                    anInt850 = 0;
                    login(username, password, false);
                    if (loggedIn)
                        return;
                }
                j1 = super.width / 2 + 80;
                if (super.clickType == 1 && super.clickX >= j1 - 75 && super.clickX <= j1 + 75
                        && super.clickY >= l1 - 20 && super.clickY <= l1 + 20) {
                    loginScreenState = 0;
                    if (USE_STATIC_DETAILS) {
                        username = USERNAME;
                        password = PASSWORD;
                    } else {
                        username = "";
                        password = "";
                    }
                }
                do {
                    int i2 = readCharacter();
                    if (i2 == -1)
                        break;
                    boolean flag = false;
                    for (int j2 = 0; j2 < VALID_CHARACTERS.length(); j2++) {
                        if (i2 != VALID_CHARACTERS.charAt(j2))
                            continue;
                        flag = true;
                        break;
                    }

                    if (anInt977 == 0) {
                        if (i2 == 8 && username.length() > 0)
                            username = username.substring(0, username.length() - 1);
                        if (i2 == 9 || i2 == 10 || i2 == 13)
                            anInt977 = 1;
                        if (flag)
                            username += (char) i2;
                        if (username.length() > 12)
                            username = username.substring(0, 12);
                    } else if (anInt977 == 1) {
                        if (i2 == 8 && password.length() > 0)
                            password = password.substring(0, password.length() - 1);
                        if (i2 == 9 || i2 == 10 || i2 == 13)
                            anInt977 = 0;
                        if (flag)
                            password += (char) i2;
                        if (password.length() > 20)
                            password = password.substring(0, 20);
                    }
                } while (true);
                return;
            }
            if (loginScreenState == 3) {
                int l = super.width / 2;
                int k1 = super.height / 2 + 50;
                k1 += 20;
                if (super.clickType == 1 && super.clickX >= l - 75 && super.clickX <= l + 75
                        && super.clickY >= k1 - 20 && super.clickY <= k1 + 20)
                    loginScreenState = 0;
            }
        }
    }

    private void method150(int i, int j, int k, int l, int i1, int j1) {
        int k1 = currentScene.method267(j, k, i);
        i1 = 62 / i1;
        if (k1 != 0) {
            int l1 = currentScene.method271(j, k, i, k1);
            int k2 = l1 >> 6 & 3;
            int i3 = l1 & 0x1f;
            int k3 = j1;
            if (k1 > 0)
                k3 = l;
            int ai[] = minimapImage.pixels;
            int k4 = 24624 + k * 4 + (103 - i) * 512 * 4;
            int i5 = k1 >> 14 & 0x7fff;
            GameObjectDefinition class47_2 = GameObjectDefinition.getDefinition(i5);
            if (class47_2.anInt795 != -1) {
                IndexedImage class50_sub1_sub1_sub3_2 = aClass50_Sub1_Sub1_Sub3Array1153[class47_2.anInt795];
                if (class50_sub1_sub1_sub3_2 != null) {
                    int i6 = (class47_2.sizeX * 4 - class50_sub1_sub1_sub3_2.width) / 2;
                    int j6 = (class47_2.sizeY * 4 - class50_sub1_sub1_sub3_2.height) / 2;
                    class50_sub1_sub1_sub3_2.drawImage(48 + k * 4 + i6, 48 + (104 - i - class47_2.sizeY) * 4 + j6
                    );
                }
            } else {
                if (i3 == 0 || i3 == 2)
                    if (k2 == 0) {
                        ai[k4] = k3;
                        ai[k4 + 512] = k3;
                        ai[k4 + 1024] = k3;
                        ai[k4 + 1536] = k3;
                    } else if (k2 == 1) {
                        ai[k4] = k3;
                        ai[k4 + 1] = k3;
                        ai[k4 + 2] = k3;
                        ai[k4 + 3] = k3;
                    } else if (k2 == 2) {
                        ai[k4 + 3] = k3;
                        ai[k4 + 3 + 512] = k3;
                        ai[k4 + 3 + 1024] = k3;
                        ai[k4 + 3 + 1536] = k3;
                    } else if (k2 == 3) {
                        ai[k4 + 1536] = k3;
                        ai[k4 + 1536 + 1] = k3;
                        ai[k4 + 1536 + 2] = k3;
                        ai[k4 + 1536 + 3] = k3;
                    }
                if (i3 == 3)
                    if (k2 == 0)
                        ai[k4] = k3;
                    else if (k2 == 1)
                        ai[k4 + 3] = k3;
                    else if (k2 == 2)
                        ai[k4 + 3 + 1536] = k3;
                    else if (k2 == 3)
                        ai[k4 + 1536] = k3;
                if (i3 == 2)
                    if (k2 == 3) {
                        ai[k4] = k3;
                        ai[k4 + 512] = k3;
                        ai[k4 + 1024] = k3;
                        ai[k4 + 1536] = k3;
                    } else if (k2 == 0) {
                        ai[k4] = k3;
                        ai[k4 + 1] = k3;
                        ai[k4 + 2] = k3;
                        ai[k4 + 3] = k3;
                    } else if (k2 == 1) {
                        ai[k4 + 3] = k3;
                        ai[k4 + 3 + 512] = k3;
                        ai[k4 + 3 + 1024] = k3;
                        ai[k4 + 3 + 1536] = k3;
                    } else if (k2 == 2) {
                        ai[k4 + 1536] = k3;
                        ai[k4 + 1536 + 1] = k3;
                        ai[k4 + 1536 + 2] = k3;
                        ai[k4 + 1536 + 3] = k3;
                    }
            }
        }
        k1 = currentScene.method269(j, k, i);
        if (k1 != 0) {
            int i2 = currentScene.method271(j, k, i, k1);
            int l2 = i2 >> 6 & 3;
            int j3 = i2 & 0x1f;
            int l3 = k1 >> 14 & 0x7fff;
            GameObjectDefinition class47_1 = GameObjectDefinition.getDefinition(l3);
            if (class47_1.anInt795 != -1) {
                IndexedImage class50_sub1_sub1_sub3_1 = aClass50_Sub1_Sub1_Sub3Array1153[class47_1.anInt795];
                if (class50_sub1_sub1_sub3_1 != null) {
                    int j5 = (class47_1.sizeX * 4 - class50_sub1_sub1_sub3_1.width) / 2;
                    int k5 = (class47_1.sizeY * 4 - class50_sub1_sub1_sub3_1.height) / 2;
                    class50_sub1_sub1_sub3_1.drawImage(48 + k * 4 + j5, 48 + (104 - i - class47_1.sizeY) * 4 + k5
                    );
                }
            } else if (j3 == 9) {
                int l4 = 0xeeeeee;
                if (k1 > 0)
                    l4 = 0xee0000;
                int ai1[] = minimapImage.pixels;
                int l5 = 24624 + k * 4 + (103 - i) * 512 * 4;
                if (l2 == 0 || l2 == 2) {
                    ai1[l5 + 1536] = l4;
                    ai1[l5 + 1024 + 1] = l4;
                    ai1[l5 + 512 + 2] = l4;
                    ai1[l5 + 3] = l4;
                } else {
                    ai1[l5] = l4;
                    ai1[l5 + 512 + 1] = l4;
                    ai1[l5 + 1024 + 2] = l4;
                    ai1[l5 + 1536 + 3] = l4;
                }
            }
        }
        k1 = currentScene.getFloorDecorationHash(j, k, i);
        if (k1 != 0) {
            int j2 = k1 >> 14 & 0x7fff;
            GameObjectDefinition class47 = GameObjectDefinition.getDefinition(j2);
            if (class47.anInt795 != -1) {
                IndexedImage class50_sub1_sub1_sub3 = aClass50_Sub1_Sub1_Sub3Array1153[class47.anInt795];
                if (class50_sub1_sub1_sub3 != null) {
                    int i4 = (class47.sizeX * 4 - class50_sub1_sub1_sub3.width) / 2;
                    int j4 = (class47.sizeY * 4 - class50_sub1_sub1_sub3.height) / 2;
                    class50_sub1_sub1_sub3.drawImage(48 + k * 4 + i4, 48 + (104 - i - class47.sizeY) * 4 + j4);
                }
            }
        }
    }

    private void renderGameView() {
        this.renderCount++;
        processPlayerAdditions(true);
        renderNPCs(751, true);
        processPlayerAdditions(false);
        renderNPCs(751, false);
        renderProjectiles();
        renderStationaryGraphics(-992);
        if (!cutsceneActive) {
            int vertical = cameraVertical;
            if (secondaryCameraVertical / 256 > vertical)
                vertical = secondaryCameraVertical / 256;
            if (customCameraActive[4] && cameraAmplitude[4] + 128 > vertical)
                vertical = cameraAmplitude[4] + 128;
            int horizontal = cameraHorizontal + cameraRandomisationA & 0x7ff;
            setCameraPosition(currentCameraPositionH, currentCameraPositionV, getFloorDrawHeight(plane, localPlayer.worldX, localPlayer.worldY
            ) - 50, vertical, horizontal);
        }
        int cameraPlane;
        if (!cutsceneActive)
            cameraPlane = getWorldDrawPlane();
        else
            cameraPlane = getCameraPlaneCutscene();
        int x = cameraX;
        int y = cameraY;
        int z = cameraZ;
        int l1 = cameraVerticalRotation;
        int i2 = cameraHorizontalRotation;
        for (int i = 0; i < 5; i++)
            if (customCameraActive[i]) {
                int randomisation = (int) ((Math.random() * (double) (cameraJitter[i] * 2 + 1) - (double) cameraJitter[i]) + Math
                        .sin((double) quakeTimes[i] * ((double) cameraFrequency[i] / 100D))
                        * (double) cameraAmplitude[i]);
                if (i == 0)
                    cameraX += randomisation;
                if (i == 1)
                    cameraZ += randomisation;
                if (i == 2)
                    cameraY += randomisation;
                if (i == 3)
                    cameraHorizontalRotation = cameraHorizontalRotation + randomisation & 0x7ff;
                if (i == 4) {
                    cameraVerticalRotation += randomisation;
                    if (cameraVerticalRotation < 128)
                        cameraVerticalRotation = 128;
                    if (cameraVerticalRotation > 383)
                        cameraVerticalRotation = 383;
                }
            }

        int textureId = Rasterizer3D.textureGetCount;
        Model.aBoolean1705 = true;
        Model.resourceCount = 0;
        Model.cursorX = super.mouseX - 4;
        Model.cursorY = super.mouseY - 4;
        Rasterizer.resetPixels();
        currentScene.render(cameraX, cameraPlane, 0, cameraZ, cameraY, cameraHorizontalRotation, cameraVerticalRotation);
        currentScene.clearInteractiveObjectCache();
        method121(false);
        method127(true);
        animateTexture(textureId);
        draw3dScreen();
        gameScreenImageProducer.drawGraphics(4, 4, super.gameGraphics);
        cameraX = x;
        cameraZ = z;
        cameraY = y;
        cameraVerticalRotation = l1;
        cameraHorizontalRotation = i2;
    }

    private void method152() {
        for (int index = 0; index < currentSound; index++) {
            //if (soundDelay[index] <= 0) {
            boolean flag1 = false;
            try {
                Buffer stream = SoundTrack.data(sound[index], soundType[index]);
                new SoundPlayer(new ByteArrayInputStream(stream.buffer, 0, stream.currentPosition), soundVolume[index], soundDelay[index]);
                if (System.currentTimeMillis() + (long) (stream.currentPosition / 22) > aLong1172
                        + (long) (anInt1257 / 22)) {
                    anInt1257 = stream.currentPosition;
                    aLong1172 = System.currentTimeMillis();
                    if (method116(stream.currentPosition, stream.buffer)) {
                        anInt1272 = sound[index];
                        anInt935 = soundType[index];
                    } else {
                        flag1 = true;
                    }

                }
            } catch (Exception exception) {
                if (SignLink.reportError) {
                    outBuffer.putOpcode(80);
                    outBuffer.putShortBE(sound[index] & 0x7fff);
                } else {
                    outBuffer.putOpcode(80);
                    outBuffer.putShortBE(-1);
                }
            }
            if (!flag1 || soundDelay[index] == -5) {
                currentSound--;
                for (int j = index; j < currentSound; j++) {
                    sound[j] = sound[j + 1];
                    soundType[j] = soundType[j + 1];
                    soundDelay[j] = soundDelay[j + 1];
                    soundVolume[j] = soundVolume[j + 1];
                }

                index--;
            } else {
                soundDelay[index] = -5;
            }
			/*} else {
				soundDelay[index]--;
			}*/
        }
        if (previousSong > 0) {
            previousSong -= 20;
            if (previousSong < 0)
                previousSong = 0;
            if (previousSong == 0 && musicEnabled && !lowMemory) {
                nextSong = currentSong;
                songChanging = true;
                onDemandRequester.request(2, nextSong);
            }
        }
    }

}
