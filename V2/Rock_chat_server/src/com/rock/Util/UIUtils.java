package com.rock.Util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class UIUtils {

	/**
	 * 得到资源图片
	 * 
	 * @param imgFile
	 * @return
	 */
	public static ImageIcon getImageIcon(String imgFile) {
		return new ImageIcon(UIUtils.class.getClassLoader().getResource(imgFile));
	}

	public static Image getIconImage(String iconFile) {
		try {
			return ImageIO.read(UIUtils.class.getClassLoader().getResourceAsStream(iconFile));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
