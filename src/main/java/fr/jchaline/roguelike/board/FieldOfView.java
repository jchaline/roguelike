package fr.jchaline.roguelike.board;

public class FieldOfView {
	private World world;
	private int depth;

	private boolean[][] visible;

	public boolean isVisible(Point p) {
		return p.z == depth && p.x >= 0 && p.y >= 0 && p.x < visible.length && p.y < visible[0].length && visible[p.x][p.y];
	}

	private Tile[][][] tiles;

	public Tile tile(Point p) {
		return tiles[p.x][p.y][p.z];
	}

	public FieldOfView(World world) {
		this.world = world;
		this.visible = new boolean[world.width()][world.height()];
		this.tiles = new Tile[world.width()][world.height()][world.depth()];

		for (int x = 0; x < world.width(); x++) {
			for (int y = 0; y < world.height(); y++) {
				for (int z = 0; z < world.depth(); z++) {
					tiles[x][y][z] = Tile.UNKNOWN;
				}
			}
		}
	}

	public void update(Point p, int r) {
		depth = p.z;
		visible = new boolean[world.width()][world.height()];

		for (int x = -r; x < r; x++) {
			for (int y = -r; y < r; y++) {
				if (x * x + y * y > r * r)
					continue;

				if (p.x + x < 0 || p.x + x >= world.width() || p.y + y < 0 || p.y + y >= world.height())
					continue;

				for (Point p2 : new Line(p, new Point(p.x + x, p.y + y, p.z))) {
					Tile tile = world.tile(p2);
					visible[p2.x][p2.y] = true;
					tiles[p2.x][p2.y][p2.z] = tile;

					if (!tile.isGround())
						break;
				}
			}
		}
	}
}