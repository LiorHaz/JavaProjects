package com.mygym.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.mygym.models.Shift;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ShiftUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idTextField;
	private JRadioButton rdbtnEntry,rdbtnQuit;


	public ShiftUI() {
		super("Shift Managment");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 485, 188);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		rdbtnEntry = new JRadioButton("Entry");
		rdbtnEntry.setFont(new Font("Tahoma", Font.BOLD, 18));
		rdbtnEntry.setBounds(243, 33, 127, 25);
		contentPane.add(rdbtnEntry);

		rdbtnQuit = new JRadioButton("Quit");
		rdbtnQuit.setFont(new Font("Tahoma", Font.BOLD, 18));
		rdbtnQuit.setBounds(112, 33, 127, 25);
		contentPane.add(rdbtnQuit);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnEntry);
		group.add(rdbtnQuit);

		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblId.setBounds(120, 79, 56, 16);
		contentPane.add(lblId);

		idTextField = new JTextField();
		idTextField.setBounds(171, 78, 116, 22);
		contentPane.add(idTextField);
		idTextField.setColumns(10);

		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if(idTextField.getText().equals(""))
					return;
				
				Shift s = Shift.getInstance();
				boolean flag;
				if (rdbtnEntry.isSelected()) {
					flag=s.startShift(Long.parseLong(idTextField.getText()));
					if (!flag) {
						JOptionPane.showMessageDialog(null, "Wrong ID or the worker is already at shift right now!\n");
						return;
					}
					else
						JOptionPane.showMessageDialog(null, "Have a nice shift!\n");
					
				}
				else 
					if (rdbtnQuit.isSelected()) {					
						try {
							flag = s.endShift(Long.parseLong(idTextField.getText()));
							if (flag) {
								JOptionPane.showMessageDialog(null, "Have a nice day!\n");
							}
							else {
								 JOptionPane.showMessageDialog(null, "The worker is not in a shift or not exists!\n");
							}
						} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
							e.printStackTrace();
						}
					}
			}
		});
		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEnter.setBounds(331, 77, 97, 25);
		contentPane.add(btnEnter);
	}
}