package fr.jchaline.roguelike.controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import fr.jchaline.roguelike.asciiPanel.AsciiPanel;
import fr.jchaline.roguelike.board.Creature;
import fr.jchaline.roguelike.board.FieldOfView;
import fr.jchaline.roguelike.board.Point;
import fr.jchaline.roguelike.board.Tile;
import fr.jchaline.roguelike.board.World;
import fr.jchaline.roguelike.config.Constants;
import fr.jchaline.roguelike.service.StuffFactory;
import fr.jchaline.roguelike.service.WorldBuilder;

public class PlayScreen implements Screen {

	private World world;

	private int screenWidth;
	private int screenHeight;

	private Creature player;

	private List<String> messages;
	private FieldOfView fov;

	public PlayScreen() {
		screenWidth = Constants.SCREEN_WIDTH;
		screenHeight = Constants.SCREEN_HEIGHT;
		messages = new ArrayList<String>();
		createWorld();

		fov = new FieldOfView(world);

		StuffFactory factory = new StuffFactory(world);
		createCreatures(factory);
		
		createItems(factory);
	}

	private void createCreatures(StuffFactory creatureFactory) {
		player = creatureFactory.newPlayer(messages, 0, fov);

		for (int i = 0; i < Constants.NB_FUNGUS; i++) {
			creatureFactory.newFungus(0);
		}

		for (int i = 0; i < 100; i++) {
			creatureFactory.newBat(0);
		}
	}

	private void createItems(StuffFactory factory) {
		for (int z = 0; z < world.depth(); z++) {
			for (int i = 0; i < world.width() * world.height() / 20; i++) {
				factory.newRock(z);
			}
		}
	}

	private void createWorld() {
		world = new WorldBuilder(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, Constants.WORLD_DEPTH).makeCaves()
				.build();
	}

	private void displayMessages(AsciiPanel terminal, List<String> messages) {
		int top = screenHeight - messages.size();
		for (int i = 0; i < messages.size(); i++) {
			terminal.writeCenter(messages.get(i), top + i);
		}
		messages.clear();
	}

	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY();
		int z = getScrollZ();

		displayTiles(terminal, left, top, z);

		terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 22);
		String stats = String.format(" %3d/%3d hp. Coord(%d,%d,%d)", player.hp(), player.maxHp(), player.point.x,
				player.point.y, player.point.z);
		terminal.write(stats, 1, 23);

		displayMessages(terminal, messages);
	}

	private void scrollBy(int mx, int my, int mz) {
		player.moveBy(mx, my, mz);

		world.update();
	}

	public Screen respondToUserInput(KeyEvent key) {

		switch (key.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			return new LoseScreen();
		case KeyEvent.VK_ENTER:
			return new WinScreen();
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_H:
			scrollBy(-1, 0, 0);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_L:
			scrollBy(1, 0, 0);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_K:
			scrollBy(0, -1, 0);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_J:
			scrollBy(0, 1, 0);
			break;
		case KeyEvent.VK_Y:
			scrollBy(-1, -1, 0);
			break;
		case KeyEvent.VK_U:
			scrollBy(1, -1, 0);
			break;
		case KeyEvent.VK_B:
			scrollBy(-1, 1, 0);
			break;
		case KeyEvent.VK_N:
			scrollBy(1, 1, 0);
			break;
		case KeyEvent.VK_G:
			player.pickup();
			break;
		}

		if (Tile.STAIRS_DOWN.equals(world.tile(player.point))) {
			player.notify("descendre escalier");
			player.moveBy(0, 0, -1);
		} else if (Tile.STAIRS_UP.equals(world.tile(player.point))) {
			player.notify("monter escalier");
			player.moveBy(0, 0, 1);
		}

		if (player.hp() < 1)
			return new LoseScreen();

		return this;
	}

	public int getScrollX() {
		return Math.max(0, Math.min(player.point.x - screenWidth / 2, world.width() - screenWidth));
	}

	public int getScrollY() {
		return Math.max(0, Math.min(player.point.y - screenHeight / 2, world.height() - screenHeight));
	}

	public int getScrollZ() {
		return player.point.z;
	}

	private void displayTiles(AsciiPanel terminal, int left, int top, int z) {
		fov.update(player.point, player.visionRadius());

		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;

				Point point = new Point(wx, wy, z);

				if (player.canSee(point))
					terminal.write(world.glyph(point), x, y, world.color(point));
				else
					terminal.write(fov.tile(point).glyph(), x, y, Color.darkGray);
			}
		}
	}
}
