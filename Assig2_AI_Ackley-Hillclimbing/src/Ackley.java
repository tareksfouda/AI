
public class Ackley {
	
	public static double function(double[] x) {
		double sum1 = 0.0;
		double sum2 = 0.0;

		for (int i = 0 ; i < x.length ; i ++) {
		        sum1 += Math.pow(x[i], 2);
		        sum2 += (Math.cos(2*Math.PI*x[i]));
		}

		return -20.0*Math.exp(-0.2*Math.sqrt(sum1 / ((double )x.length))) + 20
                - Math.exp(sum2 /((double )x.length)) + Math.exp(1.0);
		}
	
	
	
public static void main(String[] args){
	int iteration =0;
	double[] point = new double[2];
	double newvalue=0.0;
	double globalmin = 5.0;
	 point[0] = Math.random();
	 point[1] = Math.random();
	 //boolean flag = true;
	while(iteration<100){
		
	 

/*	 if(point[0] <= -5 || point[0] >= 5 || point[1] <= -5 || point[1] >=5){
		 point[0] = ((Math.random() - 0.5) * 0.1) + (point[0]);
		 point[1] = ((Math.random() - 0.5) * 0.1) + (point[1]);
	 }
	*/
	
	newvalue = function(point);
	if(newvalue < globalmin){
		globalmin = newvalue;
		System.out.println("--------------------------");
		System.out.println("F(x,y) = "+ globalmin + "\t\t\t" +"Such that X = "+point[0] + ", and Y = " + point[1] );

	}
	//System.out.println("Random number: "+Math.random());
	iteration++;
	//System.out.println(iteration);
	 point[0] = ((Math.random() - 0.5) * 0.1) + (point[0]);
	 point[1] = ((Math.random() - 0.5) * 0.1) + (point[1]);
	 

	}
	
	System.out.println("--------------------------");
	System.out.println("These were the minimas I got after running");
	System.out.print("the code for " + iteration + " times");
	
}
}
