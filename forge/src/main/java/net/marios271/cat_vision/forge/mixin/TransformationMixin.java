package net.marios271.cat_vision.forge.mixin;

import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import net.minecraftforge.client.extensions.IForgeTransformationMatrix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Loom 1.11 remaps Transformation method definitions (official -> named) but misses
// SRG-named call sites injected by Forge patches into BlockMath and similar classes.
// These bridges restore all missing SRG entry points.
@Mixin(value = Transformation.class, remap = false)
public abstract class TransformationMixin implements IForgeTransformationMatrix {

    // getMatrix() and getLeftRotation() are direct class methods — use @Shadow
    @Shadow public abstract Matrix4f getMatrix();
    @Shadow public abstract Quaternion getLeftRotation();

    // compose() and inverse() are IForgeTransformationMatrix default methods — call via interface
    public Transformation func_227985_a_(Transformation other) {
        return this.compose(other);
    }

    public Transformation func_227987_b_() {
        return this.inverse();
    }

    public Matrix4f func_227988_c_() {
        return this.getMatrix();
    }

    public Quaternion func_227989_d_() {
        return this.getLeftRotation();
    }
}
