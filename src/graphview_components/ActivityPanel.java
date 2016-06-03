package graphview_components;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class ActivityPanel extends JPanel {

	JLabel title;
	JLabel name;
	JLabel description;
	JRadioButton addButton;
	JRadioButton dependencyButton;
	ButtonGroup group;
	
	float fontScalar = Toolkit.getDefaultToolkit().getScreenSize().height/1800f;

	 public ActivityPanel() {

		 
		 
		//Set Label variables and fonts    - TODO make variables class variables for later access
		title = new JLabel("Activity Interface");
		description = new JLabel("Activity Description Here");
		name = new JLabel("Activity Title Here");
		addButton = new JRadioButton("Add Activity Mode");
		dependencyButton = new JRadioButton("Add Depedency Mode");
			
		 
		Font fs30 = title.getFont().deriveFont(fontScalar*30f);
		Font fs50 = title.getFont().deriveFont(fontScalar*50f);
		Font fs25 = title.getFont().deriveFont(fontScalar*25f);

		title.setFont(fs50);
		name.setFont(fs30);
		description.setFont(fs30);
		addButton.setFont(fs25);
		dependencyButton.setFont(fs25);
		
		//Connect the Radio Buttons
		
		group = new ButtonGroup();
		group.add(addButton);
		group.add(dependencyButton);

		addButton.setOpaque(false);
		dependencyButton.setOpaque(false);
		 //Set the Layout
		 this.setLayout(new GridBagLayout());
		 GridBagConstraints c = new GridBagConstraints();
		 
		
		 c.gridx = 0;
		 c.gridy = 0;
		 c.weightx = 1;
		 c.weighty = 1;
		 c.gridwidth = 1;
		 c.ipadx = 10;
		 c.anchor = GridBagConstraints.NORTH;
		 
		 this.add(title, c);
		 
		 c.gridx = 1;
		 c.gridy = 0;
		 c.weightx = 1;
		 c.weighty = 1;
		 c.ipady = 30;
		 c.anchor = GridBagConstraints.NORTH;
		 
		 this.add(addButton, c);
		 
		 c.gridx = 2;
		 c.gridy = 0;
		 c.weightx = 1;
		 c.weighty = 1;
		 
		 this.add(dependencyButton, c);
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

	public JRadioButton getAddButton() {
		return addButton;
	}

	public void setAddButton(JRadioButton addButton) {
		this.addButton = addButton;
	}

	public JRadioButton getDependencyButton() {
		return dependencyButton;
	}

	public void setDependencyButton(JRadioButton dependencyButton) {
		this.dependencyButton = dependencyButton;
	}
	
	
}
