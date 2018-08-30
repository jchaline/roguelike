package fr.jchaline.roguelike.service;

import java.util.List;

import fr.jchaline.roguelike.ai.BatAi;
import fr.jchaline.roguelike.ai.FungusAi;
import fr.jchaline.roguelike.ai.PlayerAi;
import fr.jchaline.roguelike.asciiPanel.AsciiPanel;
import fr.jchaline.roguelike.board.Creature;
import fr.jchaline.roguelike.board.FieldOfView;
import fr.jchaline.roguelike.board.Item;
import fr.jchaline.roguelike.board.World;

public class StuffFactory {
	private World world;
	private static int nbFungus = 0;
	private static int nbBat = 0;

	public StuffFactory(World world) {
		this.world = world;
	}

	public Item newRock(int depth) {
		Item rock = new Item(',', AsciiPanel.yellow, "rock");
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Creature newPlayer(List<String> messages, int z, FieldOfView fov) {
		Creature player = new Creature("player", world, '@', AsciiPanel.brightWhite, 100, 20, 5);
		world.addAtEmptyLocation(player, z);
		new PlayerAi(player, messages, fov);
		return player;
	}

	public Creature newFungus(int z) {
		Creature fungus = new Creature("fungus" + nbFungus++, world, 'f', AsciiPanel.green, 10, 10, 3);
		world.addAtEmptyLocation(fungus, z);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newBat(int depth) {
		Creature bat = new Creature("bat" + nbBat++, world, 'b', AsciiPanel.yellow, 15, 5, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

}
