package swing_components;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ActivityPanel extends JPanel {

	
	
	JLabel title;
	JLabel earliestStart;
	JLabel earliestFinish;
	JLabel latestStart;
	JLabel latestFinish;
	JTextField esField;
	JTextField efField;
	JTextField lsField;
	JTextField lfField;
	
	float fontScalar = Toolkit.getDefaultToolkit().getScreenSize().height/1800f;

	 public ActivityPanel() {

		
		 
		//Set Label variables and fonts    - TODO make variables class variables for later access
		 title = new JLabel("Activity Interface");
		 
		Font fs30 = title.getFont().deriveFont(fontScalar*30f);
		Font fs50 = title.getFont().deriveFont(fontScalar*50f);


		title.setFont(fs50);
		 
		 earliestStart = new JLabel("Earliest Start: ");
		 latestStart = new JLabel("Latest Start: ");
		 earliestFinish = new JLabel("Earliest Finish: ");
		 latestFinish = new JLabel("Latest Finish: ");
		 
		 esField = new JTextField();
		 efField = new JTextField();
		 lsField = new JTextField();
		 lfField = new JTextField();

				
		 earliestStart.setFont(fs30);
		 latestStart.setFont(fs30);
		 earliestFinish.setFont(fs30);
		 latestFinish.setFont(fs30);
		 
		 esField.setFont(fs30);
		 efField.setFont(fs30);
		 lsField.setFont(fs30);
		 lfField.setFont(fs30);
		 
		
		 //Set the Layout
		 this.setLayout(new GridBagLayout());
		 GridBagConstraints constraints = new GridBagConstraints();
		 
		
		 constraints.gridx = 0;
		 constraints.gridy = 0;
		 constraints.weightx = 1;
		 constraints.weighty = 1;
		 constraints.gridwidth = 4;
		 constraints.anchor = GridBagConstraints.NORTHWEST;
		 constraints.insets = new Insets(0,10,0,0);
		 
		
		 this.add(title, constraints);
		 
		 constraints.fill = GridBagConstraints.HORIZONTAL;
		 constraints.gridwidth = 1;
	 
		 //Add Labels
		 
		 constraints.weightx = 0.5;
		 
		 constraints.gridx = 0;
		 constraints.gridy = 1;
		 this.add(earliestStart, constraints);
		 
		 constraints.gridx = 0;
		 constraints.gridy = 2;
		 this.add(earliestFinish, constraints);
		 
		 constraints.gridx = 2;
		 constraints.gridy = 1;
		 this.add(latestStart, constraints);
		 
		 constraints.gridx = 2;
		 constraints.gridy = 2;
		 this.add(latestFinish, constraints);
		 
		 //Add TextFields
		 
		 constraints.weightx = 20;
		 constraints.insets = new Insets(0, 0, 0, 50);
		 
		 constraints.gridx = 1;
		 constraints.gridy = 1;
		 this.add(esField, constraints);
		 		 
		 constraints.gridx = 1;
		 constraints.gridy = 2;
		 this.add(efField, constraints);
		 		 
		 constraints.gridx = 3;
		 constraints.gridy = 1;
		 this.add(lsField, constraints);
		 
		 constraints.gridx = 3;
		 constraints.gridy = 2;
		 this.add(lfField, constraints);
		 
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
}
