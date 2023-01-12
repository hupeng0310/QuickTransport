package online.hupeng.quickstransport.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;

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
        return Vector3d.atLowerCornerOf(vector3i(blockPos));
    }

    public static String v3dToString(Vector3d pos) {
        return String.format("%.2f, %.2f, %.2f", pos.x(), pos.y(), pos.z());
    }

    public static String blockPosToString(BlockPos pos) {
        return String.format("%d, %d, %d", pos.getX(), pos.getY(), pos.getZ());
    }
}
