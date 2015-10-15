package com.tkomiya.views.utils;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class JComponentFactory {

	public static JButton makeButton(String nameAndText, ActionListener listener) {
		JButton button = new JButton(nameAndText);
		button.setName(nameAndText);
		button.addActionListener(listener);
		return button;
	}
	
}
