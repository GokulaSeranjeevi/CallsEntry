package com.jilaba.control;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Jilaba JButton Component
 * @author MANOJKUMAR V
 */
public class JilabaButton extends JButton implements FocusListener, MouseListener{

	private static final long serialVersionUID = 1L;
	
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
	
	public JilabaButton(){
		super();
		this.initialize();
	}
	public JilabaButton(Action arg0){
		super(arg0);
		this.initialize();
	}
	public JilabaButton(Icon arg0){
		super(arg0);
		this.initialize();
	}
	public JilabaButton(String arg0){
		super(arg0);
		this.initialize();
	}
	public JilabaButton(String arg0,Icon arg1){
		super(arg0,arg1);
		this.initialize();
	}
	
	private void initialize() {
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.addFocusListener(this);
		this.addMouseListener(this);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
        	if(pressedBackgroundColor != null){
        		g.setColor(pressedBackgroundColor);
        	}
            
        } else if (getModel().isRollover()) {
        	if(hoverBackgroundColor != null){
        		g.setColor(hoverBackgroundColor);
        	}
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
	
	public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
    
    
    
	@Override
	public void setBackground(Color bg) {
		pressedBackgroundColor = bg;
		hoverBackgroundColor = bg;
		super.setBackground(bg);
	}
	@Override
	public void focusGained(FocusEvent e) {
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 2), BorderFactory.createLineBorder(Color.WHITE, 1)));
	}
	@Override
	public void focusLost(FocusEvent e) {
		this.setBorder(BorderFactory.createCompoundBorder());
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		/** This Method implemented By child class*/
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 2), BorderFactory.createLineBorder(Color.WHITE, 1)));
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		if(!this.isFocusOwner())
			this.setBorder(BorderFactory.createCompoundBorder());
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		/** This Method implemented By child class*/
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		/** This Method implemented By child class*/
	}
}
