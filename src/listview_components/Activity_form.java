package listview_components;

import javax.swing.*;

import resources.Activities;
import resources.Users;
import saver_loader.DataResource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class Activity_form extends JFrame {

	private JPanel contentPane;
	private JTextField descriptionField;
	private JTextField startField;
	private JTextField endField;
	private JTextField activityLabelField;

	private ArrayList<String> dependencies = new ArrayList<String>();
	private ArrayList<String> members = new ArrayList<String>();

	public Activity_form() {

		// Initialize JFrame Settings
		setTitle("ACTIVITY CREATION");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 426, 562);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);

        // Create and add Name Field
        activityLabelField = new JTextField();
        activityLabelField.setBounds(226, 120, 150, 20);
        contentPane.add(activityLabelField);
        activityLabelField.setColumns(10);

		// Create and add Description Field
		descriptionField = new JTextField();
		descriptionField.setBounds(226, 23, 150, 20);
		descriptionField.setColumns(10);
		contentPane.add(descriptionField);

		// Create and add all Labels
        JLabel lblLabel = new JLabel("Name");
        lblLabel.setBounds(64, 123, 150, 14);
        contentPane.add(lblLabel);

		JLabel lblDescription = new JLabel("Description");
        lblDescription.setBounds(64, 26, 150, 14);
		contentPane.add(lblDescription);

		JLabel lblStart = new JLabel("Start Date (DD-MM-YYYY)");
        lblStart.setBounds(64, 64, 150, 14);
		contentPane.add(lblStart);

		JLabel lblEnd = new JLabel("End Date (DD-MM-YYYY)");
        lblEnd.setBounds(64, 94, 150, 14);
		contentPane.add(lblEnd);

		JLabel lblDependencies = new JLabel("Dependencies");
        lblDependencies.setBounds(64, 193, 150, 14);
		contentPane.add(lblDependencies);

		JLabel lblResources = new JLabel("Resources");
        lblResources.setBounds(64, 293, 150, 14);
		contentPane.add(lblResources);

		// Create and add all text Fields
		startField = new JTextField();
		startField.setBounds(226, 61, 124, 20);
		contentPane.add(startField);
		startField.setColumns(10);

		// Create and add all text Fields
		endField = new JTextField();
		endField.setBounds(226, 91, 124, 20);
		contentPane.add(endField);
		endField.setColumns(10);



		// Create an array of the current Activities
		int activityCount = DataResource.selectedProject.getActivityList().size();
		Activities[] activityList = new Activities[activityCount];
		DataResource.selectedProject.getActivityList().toArray(activityList);

		// Create Selections from the list of Activities and their labels
		String[] selections = new String[activityCount];

		for (int i = 0; i < activityCount; i++)
			selections[i] = activityList[i].getLabel();

		// Create list with selections
		final JList<String> selectionList = new JList<String>(selections);

		// Create ScrollPane with list inside and add to Frame
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane_1.setBounds(226, 175, 101, 88);
		scrollPane_1.setViewportView(selectionList);

		contentPane.add(scrollPane_1);

		String[] memberNames = new String[DataResource.projectMembers.size()];

		for (int i = 0; i < DataResource.projectMembers.size(); i++) {
			memberNames[i] = DataResource.projectMembers.get(i).getName();
		}
		final JList<String> memberList = new JList<String>(memberNames);
		// Create ScrollPane with list inside and add to Frame
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane_2.setBounds(226, 285, 101, 88);
		scrollPane_2.setViewportView(memberList);

		contentPane.add(scrollPane_2);

		// Initialize and set Buttons
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(74, 400, 89, 23);
		contentPane.add(btnCancel);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(226, 400, 89, 23);
		contentPane.add(btnSave);

		// Add and define ActionListeners to the buttons

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				disposeWindow();
			}
		});

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAction();
				ActivityListPane.updateTable(DataResource.selectedProject);
				disposeWindow();
			}
		});

		// Create the listListener for dependency choices
		selectionList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting()) {// This line prevents double
												// events

					dependencies = (ArrayList<String>) selectionList.getSelectedValuesList();

				}
			}
		});

		// Create the listListener for member choices
		memberList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {// This line prevents double
												// events
					members = (ArrayList<String>) memberList.getSelectedValuesList();
				}
			}
		});

	}

	private void saveAction() {
		try {
			DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
			Date start = dateFormatter.parse(startField.getText());
			Date end = dateFormatter.parse(endField.getText());

            if (start.before(end))
            {
                // Create activity and add it to current Project
                Activities newActivity = new Activities(descriptionField.getText(), start, end,
                        activityLabelField.getText());
                DataResource.selectedProject.addActivity(newActivity);

                // Set the dependencies in the JGraphT
                for (String element : dependencies) {

                    ArrayList<Activities> activities = DataResource.selectedProject.getActivityList();

                    for (Activities activity : activities) {

                        if (activity.getLabel().equals(element))
                            DataResource.selectedProject.addArrow(activity, newActivity);
                    }
                }

                ArrayList<Users> users = DataResource.projectMembers;
                ArrayList<Users> tmp = new ArrayList<Users>();

                for (String element : members) {
                    for (Users user : users) {
                        if (user.getName().equals(element))
                            tmp.add(user);
                    }
                }
                newActivity.setMemberList(tmp);

                //***************************** SAVE NEW ACTIVITY TO DATABASE **********************
                DataResource.saveActivity(newActivity);
                //DataResource.saveToDB();
            }
            else
            {
                JOptionPane.showMessageDialog(new JFrame(),
                        "End date must be AFTER start date",
                        "Incorrect Dates",
                        JOptionPane.WARNING_MESSAGE);
            }


		} catch (Exception exception) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Please fill in all values",
                        "Values are Empty",
                        JOptionPane.WARNING_MESSAGE);
		}
	}

	private void disposeWindow() {
		this.dispose();
	}

}
