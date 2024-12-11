package com.jilaba.design;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

public class ControlHelp {

	private JFrame jFrame;
	private JInternalFrame jInternalFrame;
	private JLabel labelToolTip;
	private JDialog jDialog;
	
	private Logger logger = Logger.getLogger(ControlHelp.class); 
	
	public ControlHelp(JFrame jFrame) {
		this.jFrame = jFrame;
	}
	public ControlHelp(JInternalFrame jInternalFrame) {
		this.jInternalFrame = jInternalFrame;
	}
	public ControlHelp(JDialog jDialog) {
		this.jDialog = jDialog;
	}
	
	public void setHelpMessage(JLabel labelToolTip){
		this.labelToolTip= labelToolTip;
		setHelpMessage();
	}
	private void setHelpMessage(){
		try{
			JComponent jComponent;
			Component component;
			Container cContainer,jFrameContainer = null;
			if(jFrame == null && jInternalFrame == null) return;
			if(jFrame != null){
				jFrameContainer = jFrame.getContentPane();
			}
			if(jInternalFrame != null){
				jFrameContainer = jInternalFrame.getContentPane();
			}
			if(jDialog != null){
				jFrameContainer = jDialog.getContentPane();
			}
			int compCount= jFrameContainer.getComponentCount();
			
			for (int i=0;i<compCount;i++){
				
				component = jFrameContainer.getComponent(i);
				if(component instanceof JPanel || component instanceof JTabbedPane){
					cContainer = (Container) component;
					setHelpMessageChild(cContainer);
				}else if(component instanceof JTextField || component instanceof JComboBox<?> || component instanceof JButton
						|| component instanceof JCheckBox || component instanceof JList<?> || component instanceof JTable 
						|| component instanceof JRadioButton || component instanceof JPasswordField){
					jComponent = (JComponent) component;
					jComponent.addFocusListener(new HelpTextListener(jComponent, labelToolTip));
				}else if(component instanceof JSpinner){
					JSpinner  jSpinner = (JSpinner) component;
					jComponent = ((JSpinner.DefaultEditor)jSpinner.getEditor()).getTextField();
					jComponent.addFocusListener(new HelpTextListener(jComponent, labelToolTip));
				}
			}
		}catch(RuntimeException e){
			logger.error(e);
		}
	}
	private void setHelpMessageChild(Container container){
		try{
			JComponent jComponent;
			Component component;
			Container cContainer;
			int compCount= container.getComponentCount();
			for (int i=0;i<compCount;i++){
				component = container.getComponent(i);
				if(component instanceof JPanel || component instanceof JTabbedPane){
					cContainer = (Container) component;
					setHelpMessageChild(cContainer);
				}else if(component instanceof JTextField || component instanceof JComboBox<?> || component instanceof JButton
						|| component instanceof JCheckBox || component instanceof JList<?> || component instanceof JTable
						|| component instanceof JRadioButton || component instanceof JPasswordField){
					jComponent = (JComponent) component;
					jComponent.addFocusListener(new HelpTextListener(jComponent, labelToolTip));
				}else if(component instanceof JSpinner){
					JSpinner  jSpinner = (JSpinner) component;
					jComponent = ((JSpinner.DefaultEditor)jSpinner.getEditor()).getTextField();
					jComponent.addFocusListener(new HelpTextListener(jComponent, labelToolTip));
				}
			}
		}catch(RuntimeException e){
			logger.error(e);
		}
	}
}

class HelpTextListener implements FocusListener{
	private JLabel labelHelpText;
	private JComponent jComponent;
	HelpTextListener(JComponent jComponent, JLabel labelHelpText){
		this.labelHelpText = labelHelpText;
		this.jComponent = jComponent;
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		if(labelHelpText != null && jComponent != null){
			if(jComponent.getToolTipText() != null && !jComponent.getToolTipText().isEmpty()){
				labelHelpText.setText(jComponent.getToolTipText());
			}else{
				labelHelpText.setText("");
			}
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		
	}
}
