package online.hupeng.quickstransport.extra;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 扩展世界信息
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ExtraWorldSaveData extends WorldSavedData {

    private final static Logger logger = LogManager.getLogger(ExtraWorldSaveData.class);

    /**
     * 用户额外信息
     */
    private Map<UUID, PlayerExtraInfo> playerExtraInfos = new ConcurrentHashMap<>();

    public static ExtraWorldSaveData get(World world) {
        return world.getServer().overworld().getDataStorage().computeIfAbsent(() -> new ExtraWorldSaveData("QuickTransport"), "QuickTransport");
    }

    /**
     * 是否存在用户信息
     * @param uuid UUID
     */
    public boolean contains(UUID uuid) {
        return this.playerExtraInfos.containsKey(uuid);
    }

    public long getPlayerLastLoginTime(UUID uuid) {
        if (this.contains(uuid)) {
            return this.playerExtraInfos.get(uuid).getLastLogin();
        }
        return 0;
    }

    @Nullable
    public Vector3d getPlayerKeyPos(UUID uuid, String key) {
        if (this.contains(uuid)) {
            return this.playerExtraInfos.get(uuid).getKeyPos().get(key);
        }
        return null;
    }

    public void updatePlayerLastLoginTime(UUID uuid, long lastLoginTime) {
        if (!this.contains(uuid)) {
            initPlayerExtraInfo(uuid);
        }
        this.playerExtraInfos.get(uuid).setLastLogin(lastLoginTime);
        this.setDirty();
    }

    public void addPlayerKeyPos(UUID uuid, String key, Vector3d pos) {
        if (!this.contains(uuid)) {
            initPlayerExtraInfo(uuid);
        }
        this.playerExtraInfos.get(uuid).getKeyPos().put(key, pos);
        this.setDirty();
    }

    @ParametersAreNonnullByDefault
    @Override
    public void load(CompoundNBT compoundNBT) {
        logger.info("调用加载玩家外部数据方法");
        playerExtraInfos = new ConcurrentHashMap<>();
        for (String uuid : compoundNBT.getAllKeys()) {
            PlayerExtraInfo playerExtraInfo = new PlayerExtraInfo();
            CompoundNBT playerCompoundNBT = compoundNBT.getCompound(uuid);
            playerExtraInfo.setLastLogin(playerCompoundNBT.getLong("lastLogin"));
            Map<String, Vector3d> keyPos = new HashMap<>();
            CompoundNBT posCompoundNBT = playerCompoundNBT.getCompound("keyPos");
            for (String key : posCompoundNBT.getAllKeys()) {
                CompoundNBT vector3dCompoundNBT = posCompoundNBT.getCompound(key);
                Vector3d vector3d = new Vector3d(vector3dCompoundNBT.getDouble("x"), vector3dCompoundNBT.getDouble("y"), vector3dCompoundNBT.getDouble("z"));
                keyPos.put(key, vector3d);
            }
            playerExtraInfo.setKeyPos(keyPos);
            this.playerExtraInfos.put(UUID.fromString(uuid), playerExtraInfo);
        }
        this.setDirty();
    }


    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        logger.info("调用保存玩家外部数据方法");
        if (playerExtraInfos == null) {
            return compoundNBT;
        }
        for (Map.Entry<UUID, PlayerExtraInfo> entry : playerExtraInfos.entrySet()) {
            String uuid = entry.getKey().toString();
            PlayerExtraInfo playerExtraInfo = entry.getValue();
            CompoundNBT playerCompoundNBT = new CompoundNBT();
            playerCompoundNBT.putLong("lastLogin", playerExtraInfo.getLastLogin());
            CompoundNBT posCompoundNBT = new CompoundNBT();
            for (Map.Entry<String, Vector3d> playerPosEntry : playerExtraInfo.getKeyPos().entrySet()) {
                CompoundNBT vector3dCompoundNBT = new CompoundNBT();
                vector3dCompoundNBT.putDouble("x", playerPosEntry.getValue().x());
                vector3dCompoundNBT.putDouble("y", playerPosEntry.getValue().y());
                vector3dCompoundNBT.putDouble("z", playerPosEntry.getValue().z());
                posCompoundNBT.put(playerPosEntry.getKey(), vector3dCompoundNBT);
            }
            playerCompoundNBT.put("keyPos", posCompoundNBT);
            compoundNBT.put(uuid, playerCompoundNBT);
        }
        return compoundNBT;
    }

    public ExtraWorldSaveData(String id) {
        super(id);
    }

    private synchronized void initPlayerExtraInfo(UUID uuid) {
        if (!this.playerExtraInfos.containsKey(uuid)) {
            PlayerExtraInfo playerExtraInfo = new PlayerExtraInfo();
            playerExtraInfo.setLastLogin(0L);
            playerExtraInfo.setKeyPos(new HashMap<>());
            this.playerExtraInfos.put(uuid, playerExtraInfo);
        }
    }


    public static class PlayerExtraInfo {

        private long lastLogin;

        private Map<String, Vector3d> keyPos;

        public long getLastLogin() {
            return lastLogin;
        }

        public PlayerExtraInfo setLastLogin(long lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public Map<String, Vector3d> getKeyPos() {
            return keyPos;
        }

        public PlayerExtraInfo setKeyPos(Map<String, Vector3d> keyPos) {
            this.keyPos = keyPos;
            return this;
        }
    }
}
