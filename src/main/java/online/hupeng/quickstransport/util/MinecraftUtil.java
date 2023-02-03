package online.hupeng.quickstransport.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3i;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * minecraft工具类
 */
@ParametersAreNonnullByDefault
public class MinecraftUtil {

    public static Vector3i vector3i(BlockPos blockPos) {
        return new Vector3i(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static Vector3d vector3d(BlockPos blockPos) {
        return new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static Vec3i vec3i(BlockPos blockPos) {
        return new Vec3i(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static String v3dToString(Vector3d pos) {
        return String.format("%.2f, %.2f, %.2f", pos.x(), pos.y(), pos.z());
    }

    public static String blockPosToString(BlockPos pos) {
        return String.format("%d, %d, %d", pos.getX(), pos.getY(), pos.getZ());
    }

    public static String vec3ToString(Vec3 vec3) {
        return String.format("%.2f, %.2f, %.2f", vec3.x(), vec3.y(), vec3.z());
    }
}
