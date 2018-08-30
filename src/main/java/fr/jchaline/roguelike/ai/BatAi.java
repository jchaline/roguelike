package fr.jchaline.roguelike.ai;

import fr.jchaline.roguelike.board.Creature;

public class BatAi extends CreatureAi {

	public BatAi(Creature creature) {
		super(creature);
	}

	public void onUpdate() {
		wander();
		wander();
	}
}