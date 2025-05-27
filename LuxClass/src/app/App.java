package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import GUI.SplashScreen;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        try {
            // Giao diện hệ điều hành (look and feel)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Khởi chạy giao diện chính
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.show();
            splash.connectToDatabase();
        });
	}

}
