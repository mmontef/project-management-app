package listview_components;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import resources.Projects;

public class Project_edit extends JFrame {
	private JPanel contentPane;
	private JTextField NameField;
	private JTextField DescField;
	private JTextField BudgetField;
	private static Projects project;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Project_edit frame = new Project_edit(project);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Project_edit(Projects project) {

//		this.project = project;
		
		project.setProjectName("Fuck");
		
		System.out.println("Editing?");
		
		setTitle("PROJECT EDITING");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 266, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel NameLabel = new JLabel("Name");
		NameLabel.setForeground(Color.BLACK);
		NameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		NameLabel.setBounds(10, 11, 55, 36);
		contentPane.add(NameLabel);
		
		JLabel DescLabel = new JLabel("Description");
		DescLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		DescLabel.setBounds(10, 76, 83, 26);
		contentPane.add(DescLabel);
		
		JLabel Budget = new JLabel("Budget");
		Budget.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Budget.setBounds(10, 131, 59, 26);
		contentPane.add(Budget);
		
		NameField = new JTextField();
		NameField.setBounds(91, 21, 147, 20);
		contentPane.add(NameField);
		NameField.setColumns(10);
		
		NameField.setText(project.getProjectName());
		
		DescField = new JTextField();
		DescField.setBounds(91, 81, 147, 20);
		contentPane.add(DescField);
		DescField.setColumns(10);
		
		DescField.setText(project.getProjectName());
		
		BudgetField = new JTextField();
		BudgetField.setBounds(91, 136, 147, 20);
		contentPane.add(BudgetField);
		BudgetField.setColumns(10);
		
		BudgetField.setText(project.getProjectName());
		
		JButton btnCancel = new JButton("Cancel");
		EndingListener buttonEar = new EndingListener();
		btnCancel.addActionListener(buttonEar);
		btnCancel.setBounds(34, 183, 89, 23);
		contentPane.add(btnCancel);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(130, 183, 89, 23);
		btnSave.addActionListener(new ButtonListener());
		
		contentPane.add(btnSave);
		
		
	}
	
	private void SaveAction () {
		project.setProjectName(NameField.getText());
		System.exit(0);
	}
		
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SaveAction();
		}

	
	}
	
}
