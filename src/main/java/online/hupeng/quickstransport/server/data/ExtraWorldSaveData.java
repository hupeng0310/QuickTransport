package online.hupeng.quickstransport.server.data;

import com.mojang.logging.LogUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

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
public class ExtraWorldSaveData extends SavedData {

    private final static Logger logger = LogUtils.getLogger();

    /**
     * 用户额外信息
     */
    private Map<UUID, PlayerExtraInfo> playerExtraInfos = new ConcurrentHashMap<>();

    public static ExtraWorldSaveData get(Level serverLevel) {
        return serverLevel.getServer().overworld().getDataStorage().computeIfAbsent(compoundTag -> {
            ExtraWorldSaveData extraWorldSaveData = new ExtraWorldSaveData();
            extraWorldSaveData.load(compoundTag);
            return extraWorldSaveData;
        }, ExtraWorldSaveData::new, "QuickTransport");
    }

    /**
     * 是否存在用户信息
     *
     * @param uuid UUID
     */
    public boolean contains(UUID uuid) {
        return this.playerExtraInfos.containsKey(uuid);
    }

    @Nullable
    public Vec3 getPlayerKeyPos(UUID uuid, String key) {
        if (this.contains(uuid)) {
            return this.playerExtraInfos.get(uuid).getKeyPos().get(key);
        }
        return null;
    }

    public void putPlayerKeyPos(UUID uuid, String key, Vec3 pos) {
        if (!this.contains(uuid)) {
            initPlayerExtraInfo(uuid);
        }
        this.playerExtraInfos.get(uuid).getKeyPos().put(key, pos);
        this.setDirty();
    }

    @ParametersAreNonnullByDefault
    public void load(CompoundTag compoundTag) {
        logger.info("调用加载玩家外部数据方法");
        playerExtraInfos = new ConcurrentHashMap<>();
        for (String uuid : compoundTag.getAllKeys()) {
            PlayerExtraInfo playerExtraInfo = new PlayerExtraInfo();
            CompoundTag playerCompoundTag = compoundTag.getCompound(uuid);
            Map<String, Vec3> keyPos = new HashMap<>();
            CompoundTag posCompoundTag = playerCompoundTag.getCompound("keyPos");
            for (String key : posCompoundTag.getAllKeys()) {
                CompoundTag vector3dCompoundTag = posCompoundTag.getCompound(key);
                Vec3 vec3 = new Vec3(vector3dCompoundTag.getDouble("x"), vector3dCompoundTag.getDouble("y"), vector3dCompoundTag.getDouble("z"));
                keyPos.put(key, vec3);
            }
            playerExtraInfo.setKeyPos(keyPos);
            this.playerExtraInfos.put(UUID.fromString(uuid), playerExtraInfo);
        }
        this.setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        logger.info("调用保存玩家外部数据方法");
        if (playerExtraInfos == null) {
            return compoundTag;
        }
        for (Map.Entry<UUID, PlayerExtraInfo> entry : playerExtraInfos.entrySet()) {
            String uuid = entry.getKey().toString();
            PlayerExtraInfo playerExtraInfo = entry.getValue();
            CompoundTag playerCompoundTag = new CompoundTag();
            CompoundTag posCompoundTag = new CompoundTag();
            for (Map.Entry<String, Vec3> playerPosEntry : playerExtraInfo.getKeyPos().entrySet()) {
                CompoundTag vector3dCompoundNBT = new CompoundTag();
                vector3dCompoundNBT.putDouble("x", playerPosEntry.getValue().x());
                vector3dCompoundNBT.putDouble("y", playerPosEntry.getValue().y());
                vector3dCompoundNBT.putDouble("z", playerPosEntry.getValue().z());
                posCompoundTag.put(playerPosEntry.getKey(), vector3dCompoundNBT);
            }
            playerCompoundTag.put("keyPos", posCompoundTag);
            compoundTag.put(uuid, playerCompoundTag);
        }
        return compoundTag;
    }

    public ExtraWorldSaveData() {
        super();
    }

    private synchronized void initPlayerExtraInfo(UUID uuid) {
        if (!this.playerExtraInfos.containsKey(uuid)) {
            PlayerExtraInfo playerExtraInfo = new PlayerExtraInfo();
            playerExtraInfo.setKeyPos(new HashMap<>());
            this.playerExtraInfos.put(uuid, playerExtraInfo);
        }
    }


    public static class PlayerExtraInfo {

        private Map<String, Vec3> keyPos;

        public Map<String, Vec3> getKeyPos() {
            return keyPos;
        }

        public PlayerExtraInfo setKeyPos(Map<String, Vec3> keyPos) {
            this.keyPos = keyPos;
            return this;
        }
    }
}
