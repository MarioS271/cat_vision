package net.marios271.cat_vision.config;

public class VisionSettings {
	public boolean auto_nv = true;

	public boolean nv_curve = false;
	public double nv_lit = 0.0;
	public double nv_dark = 1.0;
	public double nv_shape = 1.0;
	public int nv_lit_light = 12;
	public double nv_speed = 0.01;

	public double strengthFor(int light) {
		int litLight = (int) clamp(nv_lit_light, 0, 15);
		double shape = clamp(nv_shape, 0.01, 10.0);

		double darkness = litLight <= 0
			? (light <= 0 ? 1.0 : 0.0)
			: clamp((litLight - light) / (double) litLight, 0.0, 1.0);
		return clamp(nv_lit + (nv_dark - nv_lit) * Math.pow(darkness, shape), 0.0, 1.0);
	}

	public boolean sameAs(VisionSettings other) {
		return auto_nv == other.auto_nv && sameCurveAs(other);
	}

	public boolean sameCurveAs(VisionSettings other) {
		return nv_curve == other.nv_curve
			&& nv_lit == other.nv_lit
			&& nv_dark == other.nv_dark
			&& nv_lit_light == other.nv_lit_light
			&& nv_shape == other.nv_shape
			&& nv_speed == other.nv_speed;
	}

	public VisionSettings copy() {
		VisionSettings copy = new VisionSettings();
		copy.copyFrom(this);
		return copy;
	}

	public void copyFrom(VisionSettings other) {
		auto_nv = other.auto_nv;
		nv_curve = other.nv_curve;
		nv_lit = other.nv_lit;
		nv_dark = other.nv_dark;
		nv_shape = other.nv_shape;
		nv_lit_light = other.nv_lit_light;
		nv_speed = other.nv_speed;
	}

	public static double clamp(double value, double min, double max) {
		return value < min ? min : Math.min(value, max);
	}
}
