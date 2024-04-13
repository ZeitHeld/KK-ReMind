package online.magicksaddon.magicsaddonmod.magic;

/**
 * Data loaded from Datapacks for Drive Forms
 */
public class MagicksData {

	float[] dmgMult = new float[4];
	float[] duration = new float[4];
	int[] cost = new int[4];
	int[] cd = new int[4];

	public MagicksData() {

	}

	public MagicksData(int level, int cost, int cd, float dmgMult, float magMult) {
		this.cost[level] = cost;
		this.cd[level] = cd;
		this.dmgMult[level] = dmgMult;
	}

	public int getCost(int lvl) {
		return cost[lvl];
	}

	public void setCost(int lvl, int cost) {
		this.cost[lvl] = cost;
	}

	public int getCooldown(int lvl) {
		return cd[lvl];
	}

	public void setCooldown(int lvl, int cd) {
		this.cd[lvl] = cd;
	}

	public float getDmgMult(int lvl) {
		return dmgMult[lvl];
	}

	public void setDmgMult(int lvl, float dmgMult) {
		this.dmgMult[lvl] = dmgMult;
	}
}
