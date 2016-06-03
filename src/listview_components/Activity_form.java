package listview_components;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import resources.Activities;
import resources.Projects;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;

public class Activity_form extends JFrame {

	private JPanel contentPane;
	private JTextField DescField;
	private JTextField DuraField;
	private JTextField LabField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Activity_form frame = new Activity_form();
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
	public Activity_form() {
		setTitle("ACTIVITY CREATION");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 266, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DescField = new JTextField();
		DescField.setBounds(116, 23, 124, 20);
		contentPane.add(DescField);
		DescField.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(21, 26, 58, 14);
		contentPane.add(lblDescription);
		
		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setBounds(21, 64, 46, 14);
		contentPane.add(lblDuration);
		
		JLabel lblLabel = new JLabel("Label");
		lblLabel.setBounds(21, 103, 46, 14);
		contentPane.add(lblLabel);
		
		JLabel lblDependencies = new JLabel("Dependencies");
		lblDependencies.setBounds(21, 137, 80, 14);
		contentPane.add(lblDependencies);
		
		String[] selections = { "Boobs", "Cocks", "Your face", "Balls", "Goldorak", "dadasdsd", "dad" };
		
		JScrollPane scrollPane = new JScrollPane();
		
		DuraField = new JTextField();
		DuraField.setBounds(116, 61, 58, 20);
		contentPane.add(DuraField);
		DuraField.setColumns(10);
		
		LabField = new JTextField();
		LabField.setBounds(116, 100, 58, 20);
		contentPane.add(LabField);
		LabField.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane_1.setBounds(116, 137, 101, 88);
		contentPane.add(scrollPane_1);
		
		JList list_1 = new JList(selections);
		scrollPane_1.setViewportView(list_1);
		
		JButton btnCancel = new JButton("Cancel");
		EndingListener buttonEar = new EndingListener();
		btnCancel.addActionListener(buttonEar);
		btnCancel.setBounds(28, 252, 89, 23);
		contentPane.add(btnCancel);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(138, 252, 89, 23);
		contentPane.add(btnSave);
	}
	
	private void SaveAction () {
		Activities newActivity = new Activities(DescField.getText(), Double.parseDouble(DuraField.getText()), LabField.getText());
	}
	
	class SaveProject implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			SaveAction();
			
		}
		
	}
}
