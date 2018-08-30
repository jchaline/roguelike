package fr.jchaline.roguelike.controller;

import java.awt.event.KeyEvent;

import fr.jchaline.roguelike.asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {

	public void displayOutput(AsciiPanel terminal) {
		terminal.write("You lost.", 1, 1);
		terminal.writeCenter("-- press [enter] to restart --", 22);
	}

	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}
}
