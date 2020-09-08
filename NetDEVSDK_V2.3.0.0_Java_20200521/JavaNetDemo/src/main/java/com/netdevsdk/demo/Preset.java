package main.java.com.netdevsdk.demo;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

import main.java.com.netdevsdk.lib.NetDEVSDKLib;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_PTZ_PRESETCMD_E;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Preset extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldPersetID;
	private JTextField textFieldPersetName;
	NetDEVSDKLib netdevsdk = NetDEVSDKLib.NETDEVSDK_INSTANCE;
	Preset()
	{
		this.setSize(300,300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setTitle("Add Preset");
		this.setVisible(true);
		
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize =toolkit.getScreenSize();
        int x=(screenSize.width-this.getWidth())/2;
        int y=(screenSize.height-this.getHeight())/2;
        this.setLocation(x,y);
        getContentPane().setLayout(null);
        
        textFieldPersetID = new JTextField();
        textFieldPersetID.setBounds(120, 27, 66, 21);
        getContentPane().add(textFieldPersetID);
        textFieldPersetID.setColumns(10);
        
        JLabel lblPersetID = new JLabel("Preset ID(0~255)");
        lblPersetID.setBounds(10, 30, 100, 15);
        getContentPane().add(lblPersetID);
        
        textFieldPersetName = new JTextField();
        textFieldPersetName.setBounds(120, 74, 66, 21);
        getContentPane().add(textFieldPersetName);
        textFieldPersetName.setColumns(10);
        
        JLabel lblPersetName = new JLabel("Preset Name");
        lblPersetName.setBounds(10, 77, 100, 15);
        getContentPane().add(lblPersetName);
        
        JButton btnPersetComplete = new JButton("Complete");
        btnPersetComplete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				if(null == NetDemo.lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(NetDemo.ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				String strPresetNameString = textFieldPersetName.getText();
				boolean bRet = netdevsdk.NETDEV_PTZPreset_Other(NetDemo.lpUserID, NetDemo.ChannelID, NETDEV_PTZ_PRESETCMD_E.NETDEV_PTZ_SET_PRESET, strPresetNameString, Integer.valueOf(textFieldPersetID.getText()));
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZPreset_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                dispose();
        	}
        });
        btnPersetComplete.setBounds(57, 228, 93, 23);
        getContentPane().add(btnPersetComplete);
        
        JButton btnPersetCancel = new JButton("Cancel");
        btnPersetCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        btnPersetCancel.setBounds(165, 228, 93, 23);
        getContentPane().add(btnPersetCancel);
        
        
	}
}
