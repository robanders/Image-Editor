package a8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BlurToolUI extends JPanel implements ChangeListener {

	private JSlider blur_slider;
	private JSlider size_slider;
	
	public BlurToolUI() {
		setLayout(new GridLayout(0,1));
		
		JPanel color_chooser_panel = new JPanel();
		color_chooser_panel.setLayout(new BorderLayout());
		
		JPanel slider_panel = new JPanel();
		slider_panel.setLayout(new GridLayout(0,1));
		
		JPanel blur_slider_panel = new JPanel();
		JLabel blur_label = new JLabel("Blur:");
		blur_slider_panel.setLayout(new BorderLayout());
		blur_slider_panel.add(blur_label, BorderLayout.WEST);
		blur_slider = new JSlider(0,5);
		blur_slider.addChangeListener(this);
		blur_slider.setMajorTickSpacing(1);
		blur_slider.setPaintTicks(true);
		//red_slider.setPaintLabels(true);
		blur_slider_panel.add(blur_slider, BorderLayout.CENTER);

		JPanel size_slider_panel = new JPanel();
		JLabel size_label = new JLabel("Size: ");
		size_slider_panel.setLayout(new BorderLayout());
		size_slider_panel.add(size_label, BorderLayout.WEST);
		size_slider = new JSlider(0,20);
		size_slider.addChangeListener(this);
		size_slider.setMajorTickSpacing(5);
		size_slider.setPaintTicks(true);
		//red_slider.setPaintLabels(true);
		size_slider_panel.add(size_slider, BorderLayout.CENTER);

		// Assumes green label is widest and asks red and blue label
		// to be the same.
		Dimension d = size_label.getPreferredSize();
		blur_label.setPreferredSize(d);
		
		slider_panel.add(blur_slider_panel);
		slider_panel.add(size_slider_panel);

		color_chooser_panel.add(slider_panel, BorderLayout.CENTER);

		add(color_chooser_panel);
		
		stateChanged(null);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
	}
	
	public int getBlur() {
		return blur_slider.getValue();
	}
	
	public int getBlurSize(){
		return size_slider.getValue();
	}
	
}

