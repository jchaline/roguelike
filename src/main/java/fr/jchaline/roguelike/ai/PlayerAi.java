package fr.jchaline.roguelike.ai;

import java.util.List;

import fr.jchaline.roguelike.board.Creature;
import fr.jchaline.roguelike.board.FieldOfView;
import fr.jchaline.roguelike.board.Point;
import fr.jchaline.roguelike.board.Tile;

public class PlayerAi extends CreatureAi {

	private List<String> messages;
	private FieldOfView fov;

	public PlayerAi(Creature creature, List<String> messages, FieldOfView fov) {
		super(creature);
		this.messages = messages;
		this.fov = fov;
	}

	public void onNotify(String message) {
		messages.add(message);
	}

	public boolean canSee(Point p) {
		return fov.isVisible(p);
	}

	public void onEnter(Point p, Tile tile) {
		creature.notify("niveau z : %d", p.z);
		if (tile.isGround()) {
			creature.point = p;
		} else if (tile.isDiggable()) {
			creature.dig(p);
		}
	}
}
