package swing_components;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import driver.ClientLauncher;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {

	JButton addPrjButton = new JButton("new Project...");
	JButton saveButton = new JButton("Save");
	JButton deleteButton = new JButton("Delete");
	JLabel title = new JLabel();
	float fontScalar = Toolkit.getDefaultToolkit().getScreenSize().height/1800f;

	
	public ButtonPanel(){
		
		//Set text and fonts
		
		Font fs30 = addPrjButton.getFont().deriveFont(fontScalar*30f);
		Font fs40 = addPrjButton.getFont().deriveFont(fontScalar*40f);
		
		addPrjButton.setFont(fs30);
		saveButton.setFont(fs30);
		deleteButton.setFont(fs30);
		
		title.setFont(fs40);
		title.setText("Operations");


		//Add the button listener to the registered buttons
		ButtonListener internalListener = new ButtonListener();
		
		addPrjButton.addActionListener(internalListener);
		saveButton.addActionListener(internalListener);
		deleteButton.addActionListener(internalListener);
		
		//Set Layout and add Buttons
		this.setLayout(new GridLayout(13, 1, 0, 10));
	
		this.add(title);
		this.add(addPrjButton);
		this.add(saveButton);
		this.add(deleteButton);
		
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		
		BufferedImage blurBackground = null;
		
		try {
			blurBackground = ImageIO.read(new File("blur.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	
		g.drawImage(blurBackground, 0, 0, getWidth(), getHeight(), null);

		
	}
	
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource().equals(addPrjButton)){
				
				ClientLauncher.tabPane.addProjectTab();
				
					
			}
			else if(e.getSource().equals(saveButton)){
				//TODO
			}else if(e.getSource().equals(deleteButton)){
				//TODO
			}
			
		}
		
	}
	
	
}
