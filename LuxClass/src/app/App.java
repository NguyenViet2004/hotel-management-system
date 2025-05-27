package app;

import javax.swing.*;
import GUI.SplashScreen;
import java.awt.*;
import java.io.InputStream;

public class App {

    public static void main(String[] args) {
        // Thiết lập giao diện hệ điều hành
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Gọi hàm thiết lập font mặc định nếu có
            caiFontMacDinh("/fonts/Roboto-Regular.ttf", 14f); // bạn có thể thay đổi size hoặc font
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Khởi chạy giao diện splash screen
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.show();
            splash.connectToDatabase();
        });
    }

    /**
     * Thiết lập font mặc định cho toàn bộ giao diện Swing
     */
    private static void caiFontMacDinh(String pathFont, float size) {
        try {
            InputStream is = App.class.getResourceAsStream(pathFont);
            if (is != null) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
                // Áp dụng font cho toàn bộ thành phần UI
                UIManager.put("Label.font", font);
                UIManager.put("Button.font", font);
                UIManager.put("TextField.font", font);
                UIManager.put("TextArea.font", font);
                UIManager.put("Table.font", font);
                UIManager.put("TableHeader.font", font);
                UIManager.put("ComboBox.font", font);
                UIManager.put("Menu.font", font);
                UIManager.put("MenuItem.font", font);
            } else {
                System.err.println("Không tìm thấy font: " + pathFont);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải font: " + e.getMessage());
        }
    }
}
