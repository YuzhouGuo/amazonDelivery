package assignment2;

public class Shelf {
	
	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength){
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}
	
	protected void clear(){
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}
	
	public String print(){
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = firstBox;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
	 * @param b
	 */
	public void addBox(Box b){
		//ADD YOUR CODE HERE
		b.next = null;
		if (firstBox == null)
		{
			firstBox = b;
			firstBox.next = null;
		}
		else
		{
			Box n = firstBox;
			while(n.next != null)
			{
				n = n.next;
			}
			b.previous = n;
			n.next = b;
			lastBox = b;
			lastBox.next = null;
		}
		availableLength = availableLength-b.length;
		//System.out.println("The available length now is: "+availableLength);
	}
	
	/**
	 * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
	 * If not, do not do anything to the Shelf and return null.
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier){
		//ADD YOUR CODE HERE
		if (firstBox == null) return null;
		
		Box temp = firstBox;
		Box target;
		
		if(temp.next == null)
		{
			if((temp.id).equals(identifier))
			{			
				firstBox = null;
				lastBox = null;
				availableLength = availableLength + temp.length;
				return temp;
			}else return null;
		}	
		
		if((temp.next != null)&&(temp.id).equals(identifier))
		{
			target = temp;
			firstBox = temp.next;
			firstBox.previous = null;
			target.previous = null;
			target.next = null;
			availableLength = availableLength + target.length;
			return target;
		}	
		
		while(temp.next != null)
		{
			if((temp.next.id).equals(identifier)) 
			{
				target = temp.next;
				temp.next = null;
				availableLength = availableLength + target.length;
				return target;
			}	
			temp = temp.next;
		}
		return null;
	}
	
}
