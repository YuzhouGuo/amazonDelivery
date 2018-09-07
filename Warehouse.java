package assignment2;

public class Warehouse{

     protected Shelf[] storage;
     protected int nbShelves;
     public Box toShip;
     public UrgentBox toShipUrgently;
     static String problem = "problem encountered while performing the operation";
     static String noProblem = "operation was successfully carried out";
 
     public Warehouse(int n, int[] heights, int[] lengths){
         this.nbShelves = n;
         this.storage = new Shelf[n];
         
         for (int i = 0; i < n; i++){
             this.storage[i]= new Shelf(heights[i], lengths[i]);
         }
         
         this.toShip = null;
         this.toShipUrgently = null;
     }
 
     public String printShipping(){
         Box b = toShip;
         String result = "not urgent : ";
         
         while(b != null){
             result += b.id + ", ";
             b = b.next;
         }
         result += "\n" + "should be already gone : ";
         b = toShipUrgently;
         
         while(b != null){
             result += b.id + ", ";
             b = b.next;
         }
         result += "\n";
         return result;
     }
 
     public String print(){
         String result = "";
         
         for (int i = 0; i < nbShelves; i++){
         result += i + "-th shelf " + storage[i].print();
         }
     return result;
     }
 
     public void clear(){
         toShip = null;
         toShipUrgently = null;
         
         for (int i = 0; i < nbShelves ; i++){
             storage[i].clear();
         }
     }
  
  /**
   * initiate the merge sort algorithm
   */
 public void sort(){
  mergeSort(0, nbShelves -1);
 }
 
 /**
  * performs the induction step of the merge sort algorithm
  */
 protected void mergeSort(int start, int end){
		if(start<end)
		{
			int mid = (start+end)/2;
			mergeSort(start, mid);
			mergeSort(mid+1, end);
			merge(start, mid, end);
		}
 }
 /**
  * performs the merge part of the merge sort algorithm
  */
 protected void merge(int start, int mid, int end){
	    int n1 = mid -start+1;
	    int n2 = end-mid;
		
		Shelf[] L = new Shelf[n1];
		Shelf[] R = new Shelf[n2];
		
		for (int i=0; i<n1; i++)
		{
			L[i] = storage[start+i];
		}
		for (int j=0; j<n2; j++)
		{
			R[j] = storage[mid+1+j];
		}
			
		int i=0;
		int j=0;		
		int k=start;
			
		while((i<n1)&&(j<n2))
		{
			if (L[i].height<=R[j].height)
			{
				storage[k] = L[i];
				i++;
			}
			else
			{
				storage[k] = R[j];
				j++;
			}
			k++;
		}
		//copy the remaining elements in two arrays
		while(i<n1)
		{
			storage[k] = L[i];
			i++;
			k++;
		}
		while(j<n2)
		{
			storage[k] = R[j];
			j++;
			k++;
		}
 }
 
 /**
  * Adds a box is the smallest possible shelf where there is room available.
  * Here we assume that there is at least one shelf (i.e. nbShelves >0)
  */
 public String addBox (Box b){
	 for (int i=0; i<nbShelves; i++)
	 {
		 if (storage[i].height>=b.height)  //since the shelves are already sorted, this is the optimal height
		 {
			 if (storage[i].availableLength>=b.length)
			 {
				 storage[i].addBox(b);
				 return noProblem;
			 }
		 }
	 }
  return problem;
 }
 
 /**
  * Adds a box to its corresponding shipping list and updates all the fields
  */
 public String addToShip (Box b){
	 try {
		if(b instanceof UrgentBox)
		 {
			if(toShipUrgently == null)
			{
				toShipUrgently = (UrgentBox)b;
				toShipUrgently.next = null;
				return noProblem;
			}
			if(toShipUrgently != null)
			{
				Box n = toShipUrgently;
				toShipUrgently = (UrgentBox)b;
				toShipUrgently.next = n;
				toShipUrgently.next.next = null;
				return noProblem;
			}
		 }
		 else
		 {
			if(toShip == null)
			{
				toShip = b;
				toShip.next = null;
				return noProblem;
			}
			if(toShip != null)
			{
				Box n = toShip;
				toShip = b;
				toShip.next = n;
				toShip.next.next = null;
				return noProblem;
			}
		 }
	} catch (Exception e) {
		return problem;
	}
	 return problem;
 }
 
 /**
  * Find a box with the identifier (if it exists)
  * Remove the box from its corresponding shelf
  * Add it to its corresponding shipping list
  */
 public String shipBox (String identifier){
	 for(int i=0; i<nbShelves; i++)
	 {
		 boolean b = (storage[i].availableLength == storage[i].totalLength);
		 if(b==false)
		 {
			 Box n = storage[i].firstBox;
			 while (n!= null)
			 {
				 if((n.id).equals(identifier))
				 {
					 
					 addToShip(n);
					 storage[i].removeBox(identifier);
					 return noProblem;
				 } 
				 n = n.next;
			 }
		 }
	  } 
  return problem;
 }
 
 /**
  * if there is a better shelf for the box, moves the box to the optimal shelf.
  * If there are none, do not do anything
  */
 public void moveOneBox (Box b, int position){
	 int h = storage[position].height;
	 for(int i=0; i<nbShelves; i++)
	 {
		 if((storage[i].height>=b.height)&&(storage[i].height<h))
		 {
			 if(storage[i].availableLength>=b.length)
			 {
				// b.next = null;
				 storage[position].removeBox(b.id);
				 storage[i].addBox(b);
				 return;
			 }
		 }
	 }
 }
 
 /**
  * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
  */
 public void reorganize (){
	 for(int i=0; i<nbShelves; i++)
	 {
		 Box n = storage[i].firstBox;
		 while(n != null)
		 {
			 moveOneBox(n, i);
			 n = n.next;
		 }
	 }
 }
}
