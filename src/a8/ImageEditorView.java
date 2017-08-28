package a8;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImageEditorView extends JPanel implements ActionListener {

	private JFrame main_frame;
	private PictureView frame_view;
	private ImageEditorModel model;
	private ToolChooserWidget chooser_widget;
	private JPanel tool_ui_panel;
	private JPanel tool_ui;
	private JButton undo_button;
	private JButton open_button;
	private JPanel button_panel;
	
	public ImageEditorView(JFrame main_frame, ImageEditorModel model) {
		this.main_frame = main_frame;
		this.model = model;
		
		setLayout(new BorderLayout());
		
		frame_view = new PictureView(model.getCurrent());
		
		add(frame_view, BorderLayout.CENTER);
		
		tool_ui_panel = new JPanel();
		tool_ui_panel.setLayout(new BorderLayout());
		
		chooser_widget = new ToolChooserWidget();
		tool_ui_panel.add(chooser_widget, BorderLayout.NORTH);
		add(tool_ui_panel, BorderLayout.SOUTH);
		
		tool_ui = null;
		
		undo_button = new JButton("Undo");
		open_button = new JButton("Open");
		undo_button.addActionListener(this);
		open_button.addActionListener(this);
		
		button_panel = new JPanel();
		button_panel.setLayout(new GridLayout(1,2));
		button_panel.add(undo_button);
		button_panel.add(open_button);
		
		add(button_panel, BorderLayout.NORTH);
	}

	public void addToolChoiceListener(ToolChoiceListener l) {
		chooser_widget.addToolChoiceListener(l);
	}
	
	public String getCurrentToolName() {
		return chooser_widget.getCurrentToolName();
	}

	public void installToolUI(JPanel ui) {
		if (tool_ui != null) {
			tool_ui_panel.remove(tool_ui);
		}
		tool_ui = ui;
		tool_ui_panel.add(tool_ui, BorderLayout.CENTER);
		validate();
		main_frame.pack();
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		frame_view.addMouseMotionListener(l);
	}
	
	@Override
	public void removeMouseMotionListener(MouseMotionListener l) {
		frame_view.removeMouseMotionListener(l);
	}

	@Override
	public void addMouseListener(MouseListener l) {
		frame_view.addMouseListener(l);
	}
	
	public void removeMouseListener(MouseListener l) {
		frame_view.removeMouseListener(l);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("action " + e.getActionCommand());
		
		if(e.getActionCommand() == "Open"){
			String pictureURL = JOptionPane.showInputDialog(this, "Enter Picture URL", null);
			System.out.println("URL " + pictureURL);
			if(pictureURL != null){
				try{
					Picture f = PictureImpl.readFromURL(pictureURL);
					model.storePicture();
					model.setPicture(f);
					frame_view.setPicture(model.getCurrent());
					
				}
				catch(IOException error){
					//System.out.println("invalid URL");
					JOptionPane.showMessageDialog(this, "Invalid URL");
				}
				
			}
			
			
		} else if (e.getActionCommand() == "Undo") {
			model.undo();
			frame_view.setPicture(model.getCurrent());
		}
		
	}
	

}
