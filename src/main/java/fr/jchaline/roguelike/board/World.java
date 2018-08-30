package fr.jchaline.roguelike.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class World {
	private Tile[][][] tiles;
	private Item[][][] items;

	private int width;
	private int height;
	private int depth;

	private List<Creature> creatures;

	public int width() {
		return width;
	}

	public int depth() {
		return depth;
	}

	public int height() {
		return height;
	}

	public World(Tile[][][] tiles) {
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.depth = tiles[0][0].length;
		this.creatures = new ArrayList<>();
		this.items = new Item[width][height][depth];
	}

	public Tile tile(Point p) {
		if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height || p.z < 0 || p.z >= depth)
			return Tile.BOUNDS;
		else
			return tiles[p.x][p.y][p.z];
	}

	public Item item(Point p) {
		return items[p.x][p.y][p.z];
	}

	public char glyph(Point p) {
		Creature creature = creature(p);
		if (creature != null)
			return creature.glyph();

		if (item(p) != null)
			return item(p).glyph();

		return tile(p).glyph();
	}

	public Color color(Point p) {
		Creature creature = creature(p);
		if (creature != null)
			return creature.color();

		if (item(p) != null)
			return item(p).color();

		return tile(p).color();
	}

	public void dig(Point p) {
		if (tile(p).isDiggable())
			tiles[p.x][p.y][p.z] = Tile.FLOOR;
	}

	public void addAtEmptyLocation(Creature creature, int z) {
		int x;
		int y;

		Point p = null;
		do {
			x = (int) (Math.random() * width);
			y = (int) (Math.random() * height);
			p = new Point(x, y, z);
		} while (!tile(p).isGround() || creature(p) != null);

		creature.point = p;

		creatures.add(creature);
	}

	public void addAtEmptyLocation(Item item, int depth) {
		int x;
		int y;

		Point p = null;
		do {
			x = (int) (Math.random() * width);
			y = (int) (Math.random() * height);
			p = new Point(x, y, depth);
		} while (!tile(p).isGround() || item(p) != null);

		items[x][y][depth] = item;
	}

	// @TODO : a optimiser, utiliser une map<map<>>
	public Creature creature(Point p) {
		for (Creature c : creatures) {
			if (c.point().equals(p))
				return c;
		}
		return null;
	}

	public void remove(Creature other) {
		creatures.remove(other);
	}

	public void update() {
		List<Creature> toUpdate = new ArrayList<Creature>(creatures);
		for (Creature creature : toUpdate) {
			creature.update();
		}
	}

	public void remove(Point point) {
		items[point.x][point.y][point.z] = null;
	}

	public boolean addAtEmptySpace(Item item, Point startPoint) {
		if (item == null)
			return false;

		List<Point> points = new ArrayList<Point>();
		List<Point> checked = new ArrayList<Point>();

		points.add(startPoint);

		while (!points.isEmpty()) {
			Point p = points.remove(0);
			checked.add(p);

			if (!tile(p).isGround())
				continue;

			if (items[p.x][p.y][p.z] == null) {
				items[p.x][p.y][p.z] = item;
				Creature c = this.creature(p);
				if (c != null)
					c.notify("A %s lands between your feet.", item.name());
				return true;
			} else {
				List<Point> neighbors = p.neighbors8();
				neighbors.removeAll(checked);
				points.addAll(neighbors);
			}
		}
		return false;
	}
}
