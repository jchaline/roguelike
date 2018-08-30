package fr.jchaline.roguelike.ai;

import fr.jchaline.roguelike.board.Creature;
import fr.jchaline.roguelike.board.Point;
import fr.jchaline.roguelike.service.StuffFactory;

public class FungusAi extends CreatureAi {

	private StuffFactory factory;
	private int spreadcount;

	public FungusAi(Creature creature, StuffFactory factory) {
		super(creature);
		this.factory = factory;
	}

	public void onUpdate() {
		if (spreadcount < 5 && Math.random() < 0.02)
			spread();
	}

	private void spread() {
		int x = creature.point.x + (int) (Math.random() * 11) - 5;
		int y = creature.point.y + (int) (Math.random() * 11) - 5;

		Point point = new Point(x, y, creature.point.z);
		
		if (!creature.canEnter(point))
			return;

		Creature child = factory.newFungus(creature.point.z);
		child.point = point;
		spreadcount++;
	}
}
