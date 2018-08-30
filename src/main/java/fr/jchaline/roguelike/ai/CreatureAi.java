package fr.jchaline.roguelike.ai;

import fr.jchaline.roguelike.board.Creature;
import fr.jchaline.roguelike.board.Line;
import fr.jchaline.roguelike.board.Point;
import fr.jchaline.roguelike.board.Tile;

public class CreatureAi {
	protected Creature creature;

	public CreatureAi(Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}

	public void onEnter(Point p, Tile tile) {
		if (tile.isGround()) {
			creature.point = p;
		}
	}

	public void wander() {
		int mx = (int) (Math.random() * 3) - 1;
		int my = (int) (Math.random() * 3) - 1;

		Creature other = creature.creature(creature.point.move(mx, my, 0));
		if (other != null && other.glyph() == creature.glyph())
			return;
		else
			creature.moveBy(mx, my, 0);
	}

	public void onUpdate() {

	}

	public void onNotify(String format) {
	}

	public boolean canSee(Point p) {
		if (creature.point.z != p.z)
			return false;

		if ((creature.point.x - p.x) * (creature.point.x - p.x)
				+ (creature.point.y - p.y) * (creature.point.y - p.y) > creature.visionRadius()
						* creature.visionRadius())
			return false;

		for (Point pointInRangeOfView : new Line(creature.point, p)) {
			if (creature.tile(pointInRangeOfView).isGround()
					|| pointInRangeOfView.x == p.x && pointInRangeOfView.y == p.y)
				continue;

			return false;
		}

		return true;
	}
}
