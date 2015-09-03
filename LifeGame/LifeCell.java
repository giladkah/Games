
public class LifeCell {		

	private int x;
	private int y; 
	private boolean status , isNextValueReady=false; 
	
	public LifeCell(int x, int y, boolean status) {
		this.x=x;
		this.y=y;
		this.status=status; 
		
	}
		
	public boolean getStatus() {
		return status; 	
	}
	
	public void setStatus(boolean status) {
		this.status= status; 
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}

	
	public void setNextValueReady() {
		isNextValueReady = true; 
		
	}
	
	public boolean isNextValueReady() {
		
		return isNextValueReady; 
	}

	public void update() {
		
		isNextValueReady=false; 

		// TODO Auto-generated method stub
		
	}
	
	
	
}
