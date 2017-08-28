package a8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PixelInspectorUI extends JPanel {

	private JLabel x_label;
	private JLabel y_label;
	private JLabel pixel_info;
	private JButton add_to_brush;
	private PictureView color_preview;
	private JPanel pixel_panel;
	private PictureView pixel_preview;
	
	public PixelInspectorUI() {
		x_label = new JLabel("X: ");
		y_label = new JLabel("Y: ");
		pixel_info = new JLabel("(r,g,b)");
		add_to_brush = new JButton("Add to Brush");
		
		pixel_panel = new JPanel();
		pixel_panel.setLayout(new GridLayout(3,2));
		color_preview = new PictureView(new ObservablePictureImpl(new PictureImpl(50,50)));
		pixel_preview = new PictureView(new ObservablePictureImpl(new PictureImpl(200,200)));
		setLayout(new BorderLayout());

		//setLayout(new GridLayout(4,1));
		pixel_panel.add(x_label);
		pixel_panel.add(pixel_info);
		pixel_panel.add(y_label);
		pixel_panel.add(add_to_brush);
		pixel_panel.add(color_preview);
		
		add(pixel_panel,BorderLayout.CENTER);
		add(color_preview,BorderLayout.EAST);
		add(pixel_preview,BorderLayout.SOUTH);
		
	}
	
	public void setInfo(int x, int y, Pixel p) {
		x_label.setText("X: " + x);
		y_label.setText("Y: " + y);
		pixel_info.setText(String.format("(r,g,b): (%3.2f, %3.2f, %3.2f)", p.getRed(), p.getBlue(), p.getGreen()));
		
		ObservablePicture preview_frame = color_preview.getPicture();
		preview_frame.suspendObservable();
		for (int x1=0; x1<preview_frame.getWidth(); x1++) {
			for (int y1=0; y1<preview_frame.getHeight(); y1++) {
				preview_frame.setPixel(x1, y1, p);
			}
		}
		preview_frame.resumeObservable();
		
	}
	
	public void setPixelPreview(Picture p){
		pixel_preview.setPicture(p.createObservable());
		this.validate();
	}
	
	public JButton getBrushButton(){
		return add_to_brush;
	}

}

