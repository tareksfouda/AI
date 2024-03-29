
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class state implements Comparable<state> {
	//inst variables
	boolean[] sides;
	boolean flashlight; // 0 left and 1 right
	int[] constantTime;
	static int count;
	int parentid;
	int id;
	int costtohere;
	String whomoved = "";
	
	//empty constructor
	state(){
		parentid = 0;
		flashlight = false;
		count ++;
		this.id=count;
		this.costtohere = 0;
		}
	
	// constructor with parameters
	state(boolean[] sides, boolean flashlight, int[] constantTime, int parentid, int costtohere){
		count++;
		this.sides = new boolean[sides.length];
		this.constantTime = new int[sides.length];
		for(int i=0;i<sides.length;i++){
			this.sides[i] = this.sides[i];
			this.constantTime[i] = constantTime[i];
		}
		
		this.id =count;
		this.parentid =parentid;
		this.flashlight = flashlight;
		this.costtohere=costtohere;
		
	}
	
	// tostring
	public String toString(){
		String temp = "";
		for(int k= 0;k<this.sides.length;k++){
			 temp = temp + this.sides[k] + " , "; 
			 }
		String result = temp + " flashlight = " + this.flashlight + " This state has ID = " + this.id + " , PARENT ID IS --> " + this.parentid + " and the cost to this state from root = "+ this.costtohere + " minutes , " + this.whomoved;
		return result;
	}
	
	public int compareTo(state m) {
		// TODO Auto-generated method stub
		if(this.costtohere == m.costtohere)
			return 0;
		else if(this.costtohere > m.costtohere)
			return 1;
		else
			return -1;
		
	}

	// main method
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		//code
		boolean[] rootarray = {false,false,false,false};
		boolean flashlight = false;
		int[] constTime = new int[]{1,2,5,10};
		int parentid=0;
		int costtohere=0;
		Stack<state> fringe2 = new Stack<state>();
		Queue<state> fringe = new LinkedList<state>();
		PriorityQueue<state> fringe3 = new PriorityQueue<state>();
		state root= new state(rootarray,flashlight,constTime,parentid,costtohere);
		for(int ui =0;ui<root.sides.length;ui++){
			root.sides[ui] = rootarray[ui];
		}
		fringe.add(root);
		fringe3.add(root);
		fringe2.push(root);
		//movebfs(root,fringe);
		//movedfs(root,fringe2);
		moveucs(root,fringe3);
		long endTime = System.currentTimeMillis();
		System.out.println("Took "+(endTime - startTime) + " ms");
	}
//end ofmainmethod
	
	
	
/*
 * 
 * 
 * 
 * 
 * 
 * **************88
 * 
 * 	
 */
	
	

	public static void movebfs(state check, Queue<state> fringe) {
		System.out.println(" BFS solution is :\n ");
		state[] visited= new state[1000000];
		int visitedcount = 0;
		state result =new state();
		result.costtohere=10000;
		while(!fringe.isEmpty()){
		state root = fringe.remove();
		visited[visitedcount] = root;
		boolean flag=true;
		//check if destination or not
		if(root.flashlight == true){
			for(int ui =0;ui<root.sides.length;ui++){
				if(root.sides[ui] == false){
					flag = false;
				}
			}
			if(flag){
				if(root.costtohere < result.costtohere){
					result.costtohere = root.costtohere;
					result.id = root.id;
					result.parentid=root.parentid;
					result.whomoved = root.whomoved;
					
				}
				//System.out.println("root is destination " + root.costtohere +" "+ root.id);
				//break;
			}
		}
		// end of check if destination or not
		
		
		if((root.flashlight ==  true && flag==false) || root.flashlight == false){
		// handling one crosser
		int index = 0;
		int size = root.sides.length;
		for(int i=0;i<size;i++){
			//int max=0;
			state newstate= new state(root.sides,root.flashlight,root.constantTime,root.id,0);
			//copy node
			newstate.flashlight=root.flashlight;
			for(int p=0;p<size;p++){
				newstate.sides[p]=root.sides[p];
			}
			for(int p=0;p<size;p++){
				newstate.constantTime[p]=root.constantTime[p];
			}
			//endofcopying
			int max=1;
			for(int j =i;j<size;j++){
				
            if(newstate.sides[j] == newstate.flashlight && max<2){
            	newstate.sides[j]= !root.sides[j];
            	newstate.flashlight =!root.flashlight;
            	newstate.costtohere = root.costtohere + newstate.constantTime[j];
            	index = j;
            	max++;

     	       if (newstate.flashlight == true){
     		       newstate.whomoved = newstate.constantTime[index] + " goes across in " + newstate.constantTime[index] + " minutes. ";
     		       }
     		       if (newstate.flashlight == false){
     		       newstate.whomoved = newstate.constantTime[index] + " goes back in " + newstate.constantTime[index] + " minutes. ";
     		       }
            }
			}
        	// compare with every element in visited nodes array, if exist dont put in fringe
			boolean flag2=true;
        	for(int ind=0; ind<=visitedcount;ind++){
        		
        			//System.out.println(visited[ind].sides[ind2] + "sjsj");
        			
        			if(Arrays.equals(newstate.sides, visited[ind].sides) && newstate.flashlight == visited[ind].flashlight ){
        				//fringe.add(newstate);
        				
        				flag2= false;

        			}
        		
        	}
        	if (flag2){
        		fringe.add(newstate);
    			int temp = visitedcount+1;
    			visited[temp] = newstate;
    			visitedcount++;
    			//System.out.println(newstate.toString());
        	}
        	// end ofcomparewith visited array
			//fringe.add(newstate);
	       

	       
		                         }
		
		
		
		//handling two crossers
	
		
		for(int i=0;i<size-1;i++){
			//int max=1;
			for (int j=i+1;j<size;j++){
				int max=1;
				//create and copy
				state newstate= new state(root.sides,root.flashlight,root.constantTime,root.id,0);
				//copy node
				newstate.flashlight=root.flashlight;
				for(int p=0;p<size;p++){
					newstate.sides[p]=root.sides[p];
				}
				for(int p=0;p<size;p++){
					newstate.constantTime[p]=root.constantTime[p];
				}
				//endofcopying
				//int max=1;
				if(newstate.sides[i]== newstate.flashlight && newstate.sides[j] == newstate.flashlight && max<3){
	            	newstate.sides[i]= !root.sides[i];
	            	newstate.sides[j]= !root.sides[j];
	            	newstate.flashlight =!root.flashlight;
	            	newstate.costtohere = root.costtohere + newstate.constantTime[j];
	            	index = j;
	            	max++;
	            	int firstperson = i;
	            	int secondperson = j;
	      	       if (newstate.flashlight == true){
	      		       newstate.whomoved = newstate.constantTime[firstperson] + " and " + newstate.constantTime[secondperson] +" go across in " + newstate.constantTime[index] + " minutes. ";
	      		       }
	      		       if (newstate.flashlight == false){
		      		       newstate.whomoved = newstate.constantTime[firstperson] + " and " + newstate.constantTime[secondperson] +" go back in " + newstate.constantTime[index] + " minutes. ";
	      		       }
							}
	        	// compare with every element in visited nodes array, if exist dont put in fringe
				boolean flag3=true;
	        	for(int ind=0; ind<=visitedcount;ind++){
	        		
	        			if(Arrays.equals(newstate.sides ,visited[ind].sides) && newstate.flashlight == visited[ind].flashlight){
	        				//fringe.add(newstate);
	        				flag3= false;

	        			}
	        		
	        	}
	        	if (flag3){
	        		fringe.add(newstate);
	    			int temp = visitedcount+1;
	    			visited[temp] = newstate;
	    			visitedcount++;
	    			//System.out.println(newstate.toString() + "lp");
	        	}
	        	// end ofcomparewith visited array
			//	fringe.add(newstate);
				
				
		}
			
		 }
}
		}//endof while
		//printing
		boolean end= true;
		int id = result.id;
		int parentid = result.parentid;
		String printed = " \n Total is "+ result.costtohere + " minutes.";
		printed = " \n "+ result.whomoved + printed;
		
		//System.out.println("root is destination " + result.costtohere +" "+ result.id + " parent id "+ result.parentid + " " +result.whomoved);

		while(end){
			for(int vi=0;vi<visitedcount;vi++){
				if(parentid == visited[vi].id ){
					printed = " \n " + visited[vi].whomoved + printed;
					//System.out.println("root is destination " + visited[vi].costtohere +" "+ visited[vi].id + " parent id "+ visited[vi].parentid + " " +visited[vi].whomoved);

					id = visited[vi].id;
					parentid=visited[vi].parentid;
					
					
				}
				if(id == 1){
					end=false;
				}
			}
			
		}
		System.out.println(printed + "");
		//end ofprinting
} // end of move	
	
	
	
	
//____________________________________________________________________
	
// *******************************************************************
//____________________________________________________________________	
	
	
	
	
//moveDFS	
	public static void movedfs(state check, Stack<state> fringe) {
		System.out.println(" DFS solution is :\n ");
		state[] visited= new state[1000000];
		int visitedcount = 0;
		state result =new state();
		result.costtohere=10000;
		while(!fringe.isEmpty()){
		state root = fringe.pop();
		visited[visitedcount] = root;
		boolean flag=true;
		//check if destination or not
		if(root.flashlight == true){
			for(int ui =0;ui<root.sides.length;ui++){
				if(root.sides[ui] == false){
					flag = false;
				}
			}
			if(flag){
				if(root.costtohere < result.costtohere){
					result.costtohere = root.costtohere;
					result.id = root.id;
					result.parentid=root.parentid;
					result.whomoved = root.whomoved;
					
				}
				//System.out.println("root is destination " + root.costtohere +" "+ root.id);
				//break;
			}
		}
		// end of check if destination or not
		
		
		if((root.flashlight ==  true && flag==false) || root.flashlight == false){
		// handling one crosser
		int index = 0;
		int size = root.sides.length;
		for(int i=0;i<size;i++){
			//int max=0;
			state newstate= new state(root.sides,root.flashlight,root.constantTime,root.id,0);
			//copy node
			newstate.flashlight=root.flashlight;
			for(int p=0;p<size;p++){
				newstate.sides[p]=root.sides[p];
			}
			for(int p=0;p<size;p++){
				newstate.constantTime[p]=root.constantTime[p];
			}
			//endofcopying
			int max=1;
			for(int j =i;j<size;j++){
				
            if(newstate.sides[j] == newstate.flashlight && max<2){
            	newstate.sides[j]= !root.sides[j];
            	newstate.flashlight =!root.flashlight;
            	newstate.costtohere = root.costtohere + newstate.constantTime[j];
            	index = j;
            	max++;

     	       if (newstate.flashlight == true){
     		       newstate.whomoved = newstate.constantTime[index] + " goes across in " + newstate.constantTime[index] + " minutes. ";
     		       }
     		       if (newstate.flashlight == false){
     		       newstate.whomoved = newstate.constantTime[index] + " goes back in " + newstate.constantTime[index] + " minutes. ";
     		       }
            }
			}
        	// compare with every element in visited nodes array, if exist dont put in fringe
			boolean flag2=true;
        	for(int ind=0; ind<=visitedcount;ind++){
        		
        			//System.out.println(visited[ind].sides[ind2] + "sjsj");
        			
        			if(Arrays.equals(newstate.sides, visited[ind].sides) && newstate.flashlight == visited[ind].flashlight ){
        				//fringe.add(newstate);
        				
        				flag2= false;

        			}
        		
        	}
        	if (flag2){
        		fringe.push(newstate);
    			int temp = visitedcount+1;
    			visited[temp] = newstate;
    			visitedcount++;
    			//System.out.println(newstate.toString());
        	}
        	// end ofcomparewith visited array
			//fringe.add(newstate);
	       

	       
		                         }
		
		
		
		//handling two crossers
	
		
		for(int i=0;i<size-1;i++){
			//int max=1;
			for (int j=i+1;j<size;j++){
				int max=1;
				//create and copy
				state newstate= new state(root.sides,root.flashlight,root.constantTime,root.id,0);
				//copy node
				newstate.flashlight=root.flashlight;
				for(int p=0;p<size;p++){
					newstate.sides[p]=root.sides[p];
				}
				for(int p=0;p<size;p++){
					newstate.constantTime[p]=root.constantTime[p];
				}
				//endofcopying
				//int max=1;
				if(newstate.sides[i]== newstate.flashlight && newstate.sides[j] == newstate.flashlight && max<3){
	            	newstate.sides[i]= !root.sides[i];
	            	newstate.sides[j]= !root.sides[j];
	            	newstate.flashlight =!root.flashlight;
	            	newstate.costtohere = root.costtohere + newstate.constantTime[j];
	            	index = j;
	            	max++;
	            	int firstperson = i;
	            	int secondperson = j;
	      	       if (newstate.flashlight == true){
	      		       newstate.whomoved = newstate.constantTime[firstperson] + " and " + newstate.constantTime[secondperson] +" go across in " + newstate.constantTime[index] + " minutes. ";
	      		       }
	      		       if (newstate.flashlight == false){
		      		       newstate.whomoved = newstate.constantTime[firstperson] + " and " + newstate.constantTime[secondperson] +" go back in " + newstate.constantTime[index] + " minutes. ";
	      		       }
							}
	        	// compare with every element in visited nodes array, if exist dont put in fringe
				boolean flag3=true;
	        	for(int ind=0; ind<=visitedcount;ind++){
	        		
	        			if(Arrays.equals(newstate.sides ,visited[ind].sides) && newstate.flashlight == visited[ind].flashlight){
	        				//fringe.add(newstate);
	        				flag3= false;

	        			}
	        		
	        	}
	        	if (flag3){
	        		fringe.push(newstate);
	    			int temp = visitedcount+1;
	    			visited[temp] = newstate;
	    			visitedcount++;
	    			//System.out.println(newstate.toString() + "lp");
	        	}
	        	// end ofcomparewith visited array
			//	fringe.add(newstate);
				
				
		}
			
		 }
}
		}//endof while
		//printing
		boolean end= true;
		int id = result.id;
		int parentid = result.parentid;
		String printed = " \n Total is "+ result.costtohere + " minutes.";
		printed = " \n "+ result.whomoved + printed;
		
		//System.out.println("root is destination " + result.costtohere +" "+ result.id + " parent id "+ result.parentid + " " +result.whomoved);

		while(end){
			for(int vi=0;vi<visitedcount;vi++){
				if(parentid == visited[vi].id ){
					printed = " \n " + visited[vi].whomoved + printed;
					//System.out.println("root is destination " + visited[vi].costtohere +" "+ visited[vi].id + " parent id "+ visited[vi].parentid + " " +visited[vi].whomoved);

					id = visited[vi].id;
					parentid=visited[vi].parentid;
					
					
				}
				if(id == 1){
					end=false;
				}
			}
			
		}
		System.out.println(printed + "");
		//end ofprinting
}
//endof moveDFS
	
	
	
	
	
	
/* *******88
 * 
 * 
 * 
 * 
 * 
 * 	
 */
	
	//start of UCS
	public static void moveucs(state check, PriorityQueue<state> fringe) {
		System.out.println(" BFS solution is :\n ");
		state[] visited= new state[1000000];
		int visitedcount = 0;
		state result =new state();
		result.costtohere=10000;
		while(!fringe.isEmpty()){
		state root = fringe.remove();
		visited[visitedcount] = root;
		boolean flag=true;
		//check if destination or not
		if(root.flashlight == true){
			for(int ui =0;ui<root.sides.length;ui++){
				if(root.sides[ui] == false){
					flag = false;
				}
			}
			if(flag){
				if(root.costtohere < result.costtohere){
					result.costtohere = root.costtohere;
					result.id = root.id;
					result.parentid=root.parentid;
					result.whomoved = root.whomoved;
					
				}
				//System.out.println("root is destination " + root.costtohere +" "+ root.id);
				//break;
			}
		}
		// end of check if destination or not
		
		
		if((root.flashlight ==  true && flag==false) || root.flashlight == false){
		// handling one crosser
		int index = 0;
		int size = root.sides.length;
		for(int i=0;i<size;i++){
			//int max=0;
			state newstate= new state(root.sides,root.flashlight,root.constantTime,root.id,0);
			//copy node
			newstate.flashlight=root.flashlight;
			for(int p=0;p<size;p++){
				newstate.sides[p]=root.sides[p];
			}
			for(int p=0;p<size;p++){
				newstate.constantTime[p]=root.constantTime[p];
			}
			//endofcopying
			int max=1;
			for(int j =i;j<size;j++){
				
            if(newstate.sides[j] == newstate.flashlight && max<2){
            	newstate.sides[j]= !root.sides[j];
            	newstate.flashlight =!root.flashlight;
            	newstate.costtohere = root.costtohere + newstate.constantTime[j];
            	index = j;
            	max++;

     	       if (newstate.flashlight == true){
     		       newstate.whomoved = newstate.constantTime[index] + " goes across in " + newstate.constantTime[index] + " minutes. ";
     		       }
     		       if (newstate.flashlight == false){
     		       newstate.whomoved = newstate.constantTime[index] + " goes back in " + newstate.constantTime[index] + " minutes. ";
     		       }
            }
			}
        	// compare with every element in visited nodes array, if exist dont put in fringe
			boolean flag2=true;
        	for(int ind=0; ind<=visitedcount;ind++){
        		
        			//System.out.println(visited[ind].sides[ind2] + "sjsj");
        			
        			if(Arrays.equals(newstate.sides, visited[ind].sides) && newstate.flashlight == visited[ind].flashlight ){
        				//fringe.add(newstate);
        				
        				flag2= false;

        			}
        		
        	}
        	if (flag2){
        		fringe.add(newstate);
    			int temp = visitedcount+1;
    			visited[temp] = newstate;
    			visitedcount++;
    			//System.out.println(newstate.toString());
        	}
        	// end ofcomparewith visited array
			//fringe.add(newstate);
	       

	       
		                         }
		
		
		
		//handling two crossers
	
		
		for(int i=0;i<size-1;i++){
			//int max=1;
			for (int j=i+1;j<size;j++){
				int max=1;
				//create and copy
				state newstate= new state(root.sides,root.flashlight,root.constantTime,root.id,0);
				//copy node
				newstate.flashlight=root.flashlight;
				for(int p=0;p<size;p++){
					newstate.sides[p]=root.sides[p];
				}
				for(int p=0;p<size;p++){
					newstate.constantTime[p]=root.constantTime[p];
				}
				//endofcopying
				//int max=1;
				if(newstate.sides[i]== newstate.flashlight && newstate.sides[j] == newstate.flashlight && max<3){
	            	newstate.sides[i]= !root.sides[i];
	            	newstate.sides[j]= !root.sides[j];
	            	newstate.flashlight =!root.flashlight;
	            	newstate.costtohere = root.costtohere + newstate.constantTime[j];
	            	index = j;
	            	max++;
	            	int firstperson = i;
	            	int secondperson = j;
	      	       if (newstate.flashlight == true){
	      		       newstate.whomoved = newstate.constantTime[firstperson] + " and " + newstate.constantTime[secondperson] +" go across in " + newstate.constantTime[index] + " minutes. ";
	      		       }
	      		       if (newstate.flashlight == false){
		      		       newstate.whomoved = newstate.constantTime[firstperson] + " and " + newstate.constantTime[secondperson] +" go back in " + newstate.constantTime[index] + " minutes. ";
	      		       }
							}
	        	// compare with every element in visited nodes array, if exist dont put in fringe
				boolean flag3=true;
	        	for(int ind=0; ind<=visitedcount;ind++){
	        		
	        			if(Arrays.equals(newstate.sides ,visited[ind].sides) && newstate.flashlight == visited[ind].flashlight){
	        				//fringe.add(newstate);
	        				flag3= false;

	        			}
	        		
	        	}
	        	if (flag3){
	        		fringe.add(newstate);
	    			int temp = visitedcount+1;
	    			visited[temp] = newstate;
	    			visitedcount++;
	    			//System.out.println(newstate.toString() + "lp");
	        	}
	        	// end ofcomparewith visited array
			//	fringe.add(newstate);
				
				
		}
			
		 }
}
		}//endof while
		//printing
		boolean end= true;
		int id = result.id;
		int parentid = result.parentid;
		String printed = " \n Total is "+ result.costtohere + " minutes.";
		printed = " \n "+ result.whomoved + printed;
		
		//System.out.println("root is destination " + result.costtohere +" "+ result.id + " parent id "+ result.parentid + " " +result.whomoved);

		while(end){
			for(int vi=0;vi<visitedcount;vi++){
				if(parentid == visited[vi].id ){
					printed = " \n " + visited[vi].whomoved + printed;
					//System.out.println("root is destination " + visited[vi].costtohere +" "+ visited[vi].id + " parent id "+ visited[vi].parentid + " " +visited[vi].whomoved);

					id = visited[vi].id;
					parentid=visited[vi].parentid;
					
					
				}
				if(id == 1){
					end=false;
				}
			}
			
		}
		System.out.println(printed + "");
		//end ofprinting
}
	
	//end of UCS
	
	
	
	
	
	
}