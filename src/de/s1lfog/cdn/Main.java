/**
 * 
 */
package de.s1lfog.cdn;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author s1lfog
 *
 */
public class Main {
	private static final Logger log = Logger.getLogger(Main.class.getName()); 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}	
	public Main() {
		log.info("Start Application");
		StringBuffer sb = new StringBuffer();
		long numberRenamedFiles = 0;
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Music"));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(true);		
		log.info("Show FileChooser");
		fc.showOpenDialog(null);		
		File[] selected = fc.getSelectedFiles();
		for(File f : selected) {
			boolean dot = false;
			int amount = 0;
			String name = f.getName();
			for(int i = 0; i < name.length(); i++) {
				String c = String.valueOf(name.charAt(i));
				// EONumber
				if(c.equals(".")) {
					dot = true;
					break;
				}			
				// Check if char is number
				try (Scanner sc = new Scanner(c)){
					sc.nextInt();
					amount++;
				} catch (InputMismatchException e) {
					log.warning("Char is no number");
					// No Number
					break;
				}
			}			
			if(dot && amount > 0) {				
				// DO CONVERT
				String path = f.getPath();
				path = path.substring(0, path.length() - f.getName().length());
				String newName = f.getName().substring(amount + 2, f.getName().length());
				File tmp = new File(path + newName);
				log.info("Rename \"" + f.getName() + "\" <<-->> \"" + tmp.getName() + "\"");				
				f.renameTo(tmp);
				
				// For Report
				numberRenamedFiles++;				
			}			
		}
		log.info(numberRenamedFiles + " Files renamed.");
		if(numberRenamedFiles > 0) {
			sb.append(numberRenamedFiles + " Files renamed");
		} else {
			sb.append("NO Files Renamed");
		}
		// Reporting
		JOptionPane.showMessageDialog(null, sb.toString());
	}
}