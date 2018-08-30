package fr.jchaline.roguelike.controller;

import java.awt.event.KeyEvent;

import fr.jchaline.roguelike.asciiPanel.AsciiPanel;

public interface Screen {
	public void displayOutput(AsciiPanel terminal);

	public Screen respondToUserInput(KeyEvent key);
}