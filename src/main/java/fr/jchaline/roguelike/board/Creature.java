package fr.jchaline.roguelike.board;

import java.awt.Color;

import fr.jchaline.roguelike.ai.CreatureAi;

public class Creature {
	private static int idGenerator = 0;

	private int id;
	private String label;

	private World world;

	public Point point;

	private char glyph;

	private int maxHp;
	private int hp;
	private int attackValue;
	private int defenseValue;

	private CreatureAi ai;
	private Color color;

	private int visionRadius;

	private Inventory inventory;

	public Creature(String label, World world, char glyph, Color color, int maxHp, int attack, int defense) {
		this.id = idGenerator++;
		this.label = label;
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.visionRadius = 8;

		this.inventory = new Inventory(20);
	}

	public void pickup() {
		Item item = world.item(this.point);

		if (inventory.isFull() || item == null) {
			doAction("grab at the ground");
		} else {
			doAction("pickup a %s", item.name());
			world.remove(this.point);
			inventory.add(item);
		}
	}

	public void drop(Item item) {
		if (world.addAtEmptySpace(item, point)) {
			doAction("drop a " + item.name());
			inventory.remove(item);
		} else {
			notify("There's nowhere to drop the %s.", item.name());
		}
	}

	private void doAction(String string, Object... args) {
	}

	public Inventory inventory() {
		return inventory;
	}

	public int visionRadius() {
		return visionRadius;
	}

	public boolean canSee(Point p) {
		return ai.canSee(p);
	}

	public Tile tile(Point p) {
		return world.tile(p);
	}

	public int maxHp() {
		return maxHp;
	}

	public int hp() {
		return hp;
	}

	public int attackValue() {
		return attackValue;
	}

	public int defenseValue() {
		return defenseValue;
	}

	public void setCreatureAi(CreatureAi ai) {
		this.ai = ai;
	}

	public char glyph() {
		return glyph;
	}

	public Color color() {
		return color;
	}

	public Point point() {
		return point;
	}

	public void notify(String message, Object... params) {
		ai.onNotify(String.format(message, params));
	}

	public void dig(Point p) {
		world.dig(p);
	}

	public Creature creature(Point p) {
		return world.creature(p);
	}

	public void moveBy(int mx, int my, int mz) {
		Point p = new Point(point.x + mx, point.y + my, point.z + mz);

		if (mx == 0 && my == 0 && mz == 0)
			return;

		Creature other = world.creature(p);

		if (other == null)
			ai.onEnter(p, world.tile(p));
		else
			attack(other);
	}

	public void attack(Creature other) {
		int amount = Math.max(0, attackValue() - other.defenseValue());

		amount = (int) (Math.random() * amount) + 1;

		other.modifyHp(-amount);

		notify("You attack the '%s' for %d damage.", other.glyph, amount);
		other.notify("The '%s' attacks you for %d damage.", glyph, amount);
	}

	public void modifyHp(int amount) {
		hp += amount;

		if (hp < 1)
			world.remove(this);
		notify("'%s' Die.", this.glyph);
	}

	public void update() {
		ai.onUpdate();
	}

	public boolean canEnter(Point p) {
		return world.tile(p).isGround() && world.creature(p) == null;
	}

	public String toString() {
		return label;
	}

}
