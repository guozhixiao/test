package main.java.com.netdevsdk.demo;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class AccessControlPermissionWindow extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTable PermissionDoorTable;
	private JTextField PermissionNameTextField;
	private JTable PermissionDoorGroupTable;
	
	public static class PERMISSION_OPERATE_WINDOW_EFFECT{
	    public static final int PERMISSION_OPERATE_WINDOW_ADDPERSON             = 1;        /* 添加门禁授权 */
	    public static final int PERMISSION_OPERATE_WINDOW_MODIFYPERSON          = 2;        /* 修改门禁授权 */
	}
	
	public AccessControlPermissionWindow(int dwOperateMode, int dwPermissionID) {
		setTitle("Access control Permission");
		this.setSize(735,404);
        
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize =toolkit.getScreenSize();
        int x=(screenSize.width-this.getWidth())/2;
        int y=(screenSize.height-this.getHeight())/2;
        this.setLocation(x,y);
        
        this.setVisible(true);
        
		getContentPane().setLayout(null);
		
		JPanel PermissionPersonInfoPanel = new JPanel();
		PermissionPersonInfoPanel.setBorder(new TitledBorder(null, "Person Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PermissionPersonInfoPanel.setBounds(360, 83, 349, 232);
		getContentPane().add(PermissionPersonInfoPanel);
		PermissionPersonInfoPanel.setLayout(null);
		
		JScrollPane PermissionPersonScrollPane = new JScrollPane();
		PermissionPersonScrollPane.setBounds(10, 29, 329, 193);
		PermissionPersonInfoPanel.add(PermissionPersonScrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Person ID", "Name"
			}
		));
		PermissionPersonScrollPane.setViewportView(table);
		
		JPanel PermissionDoorInfoPanel = new JPanel();
		PermissionDoorInfoPanel.setBorder(new TitledBorder(null, "Door Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PermissionDoorInfoPanel.setBounds(10, 83, 340, 232);
		getContentPane().add(PermissionDoorInfoPanel);
		PermissionDoorInfoPanel.setLayout(null);
		
		JTabbedPane PermissionDoorInfoTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		PermissionDoorInfoTabbedPane.setBounds(20, 22, 310, 203);
		PermissionDoorInfoPanel.add(PermissionDoorInfoTabbedPane);
		
		JPanel PermissionDoorPanel = new JPanel();
		PermissionDoorInfoTabbedPane.addTab("Door", null, PermissionDoorPanel, null);
		PermissionDoorPanel.setLayout(null);
		
		JScrollPane PermissionDoorScrollPane = new JScrollPane();
		PermissionDoorScrollPane.setBounds(10, 10, 285, 154);
		PermissionDoorPanel.add(PermissionDoorScrollPane);
		
		PermissionDoorTable = new JTable();
		PermissionDoorTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name"
			}
		));
		PermissionDoorScrollPane.setViewportView(PermissionDoorTable);
		
		JPanel PermissionDoorGroupPanel = new JPanel();
		PermissionDoorInfoTabbedPane.addTab("Door Group", null, PermissionDoorGroupPanel, null);
		PermissionDoorGroupPanel.setLayout(null);
		
		JScrollPane PermissionDoorGroupScrollPane = new JScrollPane();
		PermissionDoorGroupScrollPane.setBounds(10, 10, 285, 154);
		PermissionDoorGroupPanel.add(PermissionDoorGroupScrollPane);
		
		PermissionDoorGroupTable = new JTable();
		PermissionDoorGroupTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name"
			}
		));
		PermissionDoorGroupScrollPane.setViewportView(PermissionDoorGroupTable);
		
		JLabel lblPermissionName = new JLabel("Permission Name");
		lblPermissionName.setBounds(10, 31, 113, 15);
		getContentPane().add(lblPermissionName);
		
		PermissionNameTextField = new JTextField();
		PermissionNameTextField.setBounds(121, 28, 123, 21);
		getContentPane().add(PermissionNameTextField);
		PermissionNameTextField.setColumns(10);
		
		JLabel lblAccessPeriod = new JLabel("Access Period");
		lblAccessPeriod.setBounds(266, 31, 84, 15);
		getContentPane().add(lblAccessPeriod);
		
		JComboBox<Object> AccessPeriodComboBox = new JComboBox<Object>();
		AccessPeriodComboBox.setBounds(360, 28, 137, 21);
		getContentPane().add(AccessPeriodComboBox);
		
		JButton btnSavePermission = new JButton("Save");
		btnSavePermission.setBounds(601, 325, 93, 23);
		getContentPane().add(btnSavePermission);
		
		if(PERMISSION_OPERATE_WINDOW_EFFECT.PERMISSION_OPERATE_WINDOW_ADDPERSON == dwOperateMode)
		{
			
		}else if(PERMISSION_OPERATE_WINDOW_EFFECT.PERMISSION_OPERATE_WINDOW_MODIFYPERSON == dwOperateMode){
			
		}
	}
}
