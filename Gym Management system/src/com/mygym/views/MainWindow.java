package com.mygym.views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow
{
	private JFrame frame;
	private JLabel dateTimeLabel;
	private ShiftUI shiftWindow;
	private WorkersView workersView;
	private SubscribersView subscribersView;
	private FinanceView financeView;
	
	public MainWindow() {
		initialize();
		clock();
	}

	private void initialize() {
		/**
		 * Initialize the contents of the frame.
		 */
		frame = new JFrame("My Gym");
		frame.setBounds(100, 100, 615, 379);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		//MyGym Label
		JLabel lblMygym = new JLabel("MyGym");
		lblMygym.setHorizontalAlignment(SwingConstants.CENTER);
		lblMygym.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMygym.setBounds(270, 10, 74, 26);
		frame.getContentPane().add(lblMygym);
		//Date and time Label
		dateTimeLabel = new JLabel("New label");
		dateTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		dateTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateTimeLabel.setBounds(409, 11, 167, 26);
		frame.getContentPane().add(dateTimeLabel);
		//Shift Button
		JButton btnShifts = new JButton("Shifts");
		btnShifts.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnShifts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(shiftWindow == null)
					shiftWindow = new ShiftUI();
				
				shiftWindow.setVisible(true);
			}
		});
		btnShifts.setBounds(23, 56, 267, 103);
		frame.getContentPane().add(btnShifts);
		//Monthly Reports Button
		JButton btnFinanceReports = new JButton("Finance Reports");
		btnFinanceReports.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnFinanceReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(financeView == null)
					financeView = new FinanceView();
				financeView.setVisible(true);
			}
		});
		btnFinanceReports.setBounds(318, 56, 258, 103);
		frame.getContentPane().add(btnFinanceReports);
		//Clients Button
		JButton btnClients = new JButton("Clients");
		btnClients.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(subscribersView == null)
					subscribersView = new SubscribersView();
				
				subscribersView.setVisible(true);
			}
		});
		btnClients.setBounds(318, 180, 258, 103);
		frame.getContentPane().add(btnClients);
		//Workers Button
		JButton btnWorkers = new JButton("Workers");
		btnWorkers.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnWorkers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(workersView == null)
					workersView = new WorkersView();
				
				workersView.setVisible(true);
			}
		});
		btnWorkers.setBounds(23, 180, 267, 103);
		frame.getContentPane().add(btnWorkers);
		//Help Button
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "For add and update - insert valid details and press the right button\nFor remove - insert id and press the right button");
			}
		});
		btnHelp.setBounds(487, 307, 89, 23);
		frame.getContentPane().add(btnHelp);
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int dialogButton = JOptionPane.YES_NO_OPTION;
				 int dialogResult = JOptionPane.showConfirmDialog (null, "Would you like to exit?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
						System.exit(0);
					}
			}		
		});
		btnExit.setBounds(10, 307, 89, 23);
		frame.getContentPane().add(btnExit);
	}
	
	//Show Date and time
	private void clock() {
		Thread clock=new Thread() {
			public void run() {
				try {
					Calendar cal;
					while(true) {
						cal=new GregorianCalendar();
						String day=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
						if(cal.get(Calendar.DAY_OF_MONTH)<10)
							day="0"+day;
						String month=String.valueOf(cal.get(Calendar.MONTH)+1);
						if(cal.get(Calendar.MONTH)<10)
							month="0"+month;
						String year=String.valueOf(cal.get(Calendar.YEAR));
						String second=String.valueOf(cal.get(Calendar.SECOND));
						if(cal.get(Calendar.SECOND)<10)
							second="0"+second;
						String minute=String.valueOf(cal.get(Calendar.MINUTE));
						if(cal.get(Calendar.MINUTE)<10)
							minute="0"+minute;
						String hour=String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
						if(cal.get(Calendar.HOUR_OF_DAY)<10)
							hour="0"+hour;
						dateTimeLabel.setText(hour+":"+minute+":"+second+"  "+day+"\\"+month+"\\"+year);
						sleep(1000);
					}
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		clock.start();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}