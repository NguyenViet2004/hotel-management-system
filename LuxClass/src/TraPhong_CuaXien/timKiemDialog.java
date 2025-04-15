package TraPhong_CuaXien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import TraPhong_CuaXien.panel_timKiem;
public class timKiemDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			timKiemDialog dialog = new timKiemDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setUndecorated(true);
			dialog.setLocationRelativeTo(null);
			dialog.setSize(929, 629);
			dialog.setResizable(false);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public timKiemDialog() {
		setBounds(100, 100, 900, 717);
		getContentPane().setLayout(null);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setSize(929, 629);
		JPanel timKiem= new panel_timKiem();
		timKiem.setBounds(0, 0, 929, 629); 
		getContentPane().add(timKiem);  
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3); // Tạo viền đen, dày 2 pixel
        getRootPane().setBorder(border);
	    }
}
