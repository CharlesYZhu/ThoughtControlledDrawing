package imageEditor;

import java.util.ArrayList;

public class Coordinates {
	private ArrayList<Integer> xCoordinates;
	private ArrayList<Integer> yCoordinates;
	int num_coordinates;
	
	public Coordinates(){
		num_coordinates = 0;
		xCoordinates = new ArrayList<Integer>();
		yCoordinates = new ArrayList<Integer>();
	}

	public int[] get_xCoordinates() {
		int[] x_array = new int[num_coordinates];
		for(int x = 0; x < num_coordinates; x++){
			x_array[x] = xCoordinates.get(x);
		}
		return x_array;
	}

	public int[] get_yCoordinates() {
		int[] y_array = new int[num_coordinates];
		for(int y = 0; y < num_coordinates; y++){
			y_array[y] = yCoordinates.get(y);
		}
		return y_array;
	}
	
	public int getNumCoordinates(){
		return num_coordinates;
	}
	
	public void addCoordinates(int x, int y){
		xCoordinates.add(x);
		yCoordinates.add(y);
		num_coordinates += 1;
	}
}
