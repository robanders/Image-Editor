package a8;

import java.util.ArrayDeque;
import java.util.Deque;

public class ImageEditorModel {

	private Picture original;
	private ObservablePicture current;
	private Pixel saved_color = new ColorPixel(0.5,0.5,0.5);
	private Deque<Picture> picture_deck = new ArrayDeque<Picture>();
	
	
	public ImageEditorModel(Picture p) {
		original = p;
		current = original.copy().createObservable();
	}

	public ObservablePicture getCurrent() {
		return current;
	}

	public Pixel getPixel(int x, int y) {
		return current.getPixel(x, y);
	}

	public void paintAt(int x, int y, Pixel brushColor, int brush_size, double opacity) {
		Pixel original_pixel;
		Pixel blended_pixel;
		current.suspendObservable();;
		
		for (int xpos=x-brush_size+1; xpos <=x+brush_size-1; xpos++) {
			for (int ypos=y-brush_size+1; ypos <=y+brush_size-1; ypos++) {
				if (xpos >= 0 &&
					xpos < current.getWidth() &&
					ypos >= 0 &&
					ypos < current.getHeight()) {
					original_pixel = current.getPixel(xpos, ypos);
					blended_pixel = brushColor.blend(original_pixel, opacity/100.0 );
					current.setPixel(xpos, ypos, blended_pixel);
				}
			}
		}
		current.resumeObservable();
	}
	
	public void blurAt(int x, int y, int blur_size, int blur) {
		Pixel average_pixel;
		current.suspendObservable();;
		
		for (int xpos=x-blur_size+1; xpos <=x+blur_size-1; xpos++) {
			for (int ypos=y-blur_size+1; ypos <=y+blur_size-1; ypos++) {
				if (xpos >= 0 &&
					xpos < current.getWidth() &&
					ypos >= 0 &&
					ypos < current.getHeight()) {
					average_pixel = this.getAvgPixel(current, xpos, ypos, blur);
					current.setPixel(xpos, ypos, average_pixel);
				}
			}
		}
		current.resumeObservable();
	}
	
	public Pixel getAvgPixel(ObservablePicture p, int inX, int inY, int blur){
		
		int startX;
		int startY;
		int endX;
		int endY;
		double totalRed = 0;
		double totalGreen = 0;
		double totalBlue = 0;
		int count = 0;
		Pixel oldPixel;
		Pixel avgPixel;
		
		startX = inX - blur;
		if(startX < 0){
			startX = 0;
		}
		startY = inY - blur;
		if(startY < 0){
			startY = 0;
		}
		endX = inX + blur;
		if(endX > p.getWidth()-1){
			endX = p.getWidth()-1;
		}
		endY = inY + blur;
		if(endY > p.getHeight()-1){
			endY = p.getHeight()-1;
		}
		for(int y = startY; y <= endY; y++){
			for(int x = startX; x <= endX; x++){
				if(!(x == inX && y == inY)){
					oldPixel = p.getPixel(x, y);
					totalRed = totalRed + oldPixel.getRed();
					totalGreen = totalGreen + oldPixel.getGreen();
					totalBlue = totalBlue + oldPixel.getBlue();
					count = count + 1;
				}
			}
		}
		if(count != 0){
			avgPixel = new ColorPixel(totalRed / count, totalGreen / count, totalBlue / count);
		} else {
			avgPixel = p.getPixel(inX, inY);
		}
		return avgPixel;
	}
	
	public void setPaintColor(Pixel p){
		saved_color = p;
	}
	
	public Pixel getPaintColor(){
		return saved_color;
	}
	
	public void setPicture(Picture p){
		current = p.copy().createObservable();
	}
	
	public void storePicture(){
		picture_deck.push(current.copy());
	}
	
	public void undo(){
		if(!picture_deck.isEmpty()){
			Picture new_pic = picture_deck.pop();
			current = new_pic.copy().createObservable();
		}
	}
	
	public Picture getPixelPreview(int inX, int inY){
		int size = 20;
		int startX;
		int startY;
		int endX;
		int endY;
		Pixel oldPixel;
		Picture new_pic;
		
		startX = inX - size;
		if(startX < 0){
			startX = 0;
		}
		startY = inY - size;
		if(startY < 0){
			startY = 0;
		}
		endX = inX + size;
		if(endX > current.getWidth()-1){
			endX = current.getWidth()-1;
		}
		endY = inY + size;
		if(endY > current.getHeight()-1){
			endY = current.getHeight()-1;
		}
		new_pic = new PictureImpl(endX - startX + 1, endY - startY + 1);
		int x1 = 0;
		int y1 = 0;
		int magValue = 5;
		Picture big_pic = new PictureImpl(magValue*(endX - startX + 1), magValue*(endY - startY + 1));
		//System.out.println("big pic x " + magValue*(endX - startX + 1));
		//System.out.println("big pic y " + magValue*(endY - startY + 1));
		for(int y = startY; y <= endY; y++){
			x1 = 0;
			for(int x = startX; x <= endX; x++){
				oldPixel = current.getPixel(x, y);
				new_pic.setPixel(x1, y1, oldPixel);
				//System.out.println("x1: " + x1 + " y1: " + y1 + " x: " + x + " y: " + y);
				x1++;
			}
			y1++;
		}
		x1 = 0;
		y1 = 0;
		for(int y = 0; y < new_pic.getHeight(); y++){
			for(int j = 0; j < magValue; j++){
				x1 = 0;
				for(int x = 0; x < new_pic.getWidth(); x++){
					for(int i = 0; i < magValue; i++){
						oldPixel = new_pic.getPixel(x, y);
						big_pic.setPixel(x1, y1, oldPixel);
						x1++;
						//System.out.println("x1: " + x1 + " y1: " + y1 + " x: " + x + " y: " + y);
					}
				}
				y1++;
			}
			
		}
		return big_pic;
	}
}
