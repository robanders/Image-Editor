package a8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PixelInspectorTool implements Tool, ActionListener {

	private PixelInspectorUI ui;
	private ImageEditorModel model;
	private Pixel selected_pixel;
	private JButton add_to_brush;
	
	public PixelInspectorTool(ImageEditorModel model) {
		this.model = model;
		ui = new PixelInspectorUI();
		add_to_brush = ui.getBrushButton();
		add_to_brush.addActionListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			ui.setInfo(e.getX(), e.getY(), model.getPixel(e.getX(), e.getY()));
			Picture pixel_preview = model.getPixelPreview(e.getX(), e.getY());
			ui.setPixelPreview(pixel_preview);
			selected_pixel = model.getPixel(e.getX(), e.getY());
		}
		catch (Exception ex) {
			// Click may have been out of bounds. Do nothing in this case.
			System.out.println("error");
		}
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
	public String getName() {
		return "Pixel Inspector";
	}

	@Override
	public JPanel getUI() {
		return ui;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		try {
			ui.setInfo(e.getX(), e.getY(), model.getPixel(e.getX(), e.getY()));
			selected_pixel = model.getPixel(e.getX(), e.getY());
		}
		catch (Exception ex) {
			// Click may have been out of bounds. Do nothing in this case.
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Add to Brush"){
			model.setPaintColor(selected_pixel);
		}
		
	}

}
