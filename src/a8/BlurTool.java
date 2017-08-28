package a8;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class BlurTool implements Tool {

	private BlurToolUI ui;
	private ImageEditorModel model;
	
	public BlurTool(ImageEditorModel model) {
		this.model = model;
		ui = new BlurToolUI();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		model.storePicture();
		model.blurAt(e.getX(), e.getY(), ui.getBlurSize(), ui.getBlur());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		model.blurAt(e.getX(), e.getY(), ui.getBlurSize(), ui.getBlur());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "Blur Tool";
	}

	@Override
	public JPanel getUI() {
		return ui;
	}

}
