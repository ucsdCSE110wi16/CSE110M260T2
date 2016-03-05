/**
* @(#)mts_stops.java
*
*
* @author
* @version 1.00 2016/2/26
*/
import java.io.*;
import java.util.ArrayList;

public class mts_stops {

	/**
	 * Creates a new instance of <code>mts_stops</code>.
	 */
	public mts_stops() {
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		 // The name of the file to open.
		String fileName = "trips.txt";
		String fileName2="stop_times.txt";
		String outName = "route-stops-times.txt";
		// This will reference one line at a time
		String line = null;

		String r=null,t=null, s=null,a=null;
		ArrayList<Route> routes=new ArrayList<Route>();

		routes.ensureCapacity(20000);
		int index=0, last=0;
		int temp=0;
		int count=0;
		Route tempR=null;
		Trip tempT=null;

		try {

			//FILE READERS HERE

			// FileReader reads text files in the default encoding.
			FileReader fileReader =
				new FileReader(fileName);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader =
				new BufferedReader(fileReader);

			FileWriter fileWriter =
				new FileWriter(outName);
			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter =
				new BufferedWriter(fileWriter);

			// FileReader reads text files in the default encoding.
			FileReader fRead =
				new FileReader(fileName2);
			// Always wrap FileReader in BufferedReader.
			BufferedReader reader =
				new BufferedReader(fRead);


			//READING FROM FILE
		   	line=bufferedReader.readLine();
		   	System.out.println("Format original: "+line+"\nwritten as:");

		   	index=line.indexOf(",");
		   	last=line.indexOf(",",index+1);
			//route id found
		   	System.out.println(line.substring(index+1,last));

			//skip un-needed stuff
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
			//trip id found
		   	System.out.println(line.substring(index+1,last));

			while((line=bufferedReader.readLine())!=null){
				index=line.indexOf(",");
		   		last=line.indexOf(",",index+1);
				//route id found
			   	r=(line.substring(index+1,last));

				//skip un-needed stuff
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);

				//trip id found
			   	t=(line.substring(index+1,last));
			   	tempR=new Route(r,t);

			   	if ( (temp=routes.indexOf(tempR))==-1){
			   		routes.add(tempR);

			   			//System.out.println("not found. Creating new route:"+r);
			   	}else{

			   		((Route)routes.get(temp)).addTrip(t);

			   	}

			   	count++;
			   	if (count%5000==0){
			   		System.out.println(count);
			   	}
			}//end while

			//close first file
			bufferedReader.close();



			//READ FROM SECOND FILE
			count=0;
			line=reader.readLine();
		   	System.out.println("Format original: "+line+"\nwritten as:");

			index=0;
			last=line.indexOf(",",index+1);
			//trip id
			System.out.println(line.substring(index,last));

			//skip un-needed stuff
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	//time
		   	System.out.println(line.substring(index+1,last));
		   	index=last;
		   	last=line.indexOf(",",index+1);

		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	//stopID
		   	System.out.println(line.substring(index+1,last));

		   	tempR=null;
		   	while((line=reader.readLine())!=null){
		   		index=0;
				last=line.indexOf(",",index+1);
				//trip id
				t=(line.substring(index,last));

				//skip un-needed stuff
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	//arrival time
			   	a=(line.substring(index+1,last));
			   	index=last;
			   	last=line.indexOf(",",index+1);

			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	//stopID
			   	s=(line.substring(index+1,last));

			   	//t= trip id
			   	//a= arrival time
			   	//s= stop id

			   	//find route that contains trip t
			   	if(tempR==null || tempR.containsTrip(t)==false){
			   		for(Route l:routes){
			   			if(l.containsTrip(t)){
			   				tempR=l;
			   				break;
			   			}
			   		}
			   	}//end if
			   	//creates new stop in trip that belongs to route r
			   	tempT=tempR.getTrip(t);
			   	tempT.addStop(s,a);

			   	count++;
			   	if (count%100000==0){
			   		System.out.println(count);
			   	}
		   	}



			reader.close();



			// Always close files.

			bufferedWriter.close();

		}//end try
		catch(FileNotFoundException ex) {
			System.out.println(
				"Unable to open file '" +
				fileName + "'");
		}
		catch(IOException ex) {
			System.out.println(
				"Error reading/writing file '"
				+ fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}



		 /*TEST: GETS FIRST ROUTE IN LIST
		 *prints: total routes,
		 *total trips# of trips,
		 *trip ids for routes
		 */


		Route x;
		int p=0, q=0;
			//loops through every route
		for(int j=0;j<routes.size();j++){
			x=(Route)routes.get(j);
			System.out.println("Route id:"+x.getID()+"- # of trips:"+x.getTripSize());
			p+=x.getTripSize();
			//Prints every trip for route x.
			for(int i=0;i<x.getTripSize();i++){
				//accesses every trip in x
				//System.out.println( ((Trip)(x.getTrips().get(i))).getID());
				q+=((Trip)(x.getTrips().get(i))).getStops().size();
			}
		}
		System.out.println("# of routes:"+routes.size()+" - total # of trips: "+p+ " - total # of stops:"+q);




	}//end main
}//end class


class Route{
	private ArrayList<Trip> trips;

	private String routeID;

	//constructor
	public Route(String route, String trip){

		//creates new list for trips
		trips=new ArrayList<Trip>();
		routeID=route;
		trips.add(new Trip(trip));
	}

	//body methods

	// trip functionality in route
	public void addTrip(String id){
		//adds trip to a route
		trips.add(new Trip(id));
	}
	public boolean containsTrip(Trip t){
		return trips.contains(t);
	}
	public boolean containsTrip(String t){
		return trips.contains(new Trip(t));
	}
	public Trip getTrip(String t){
		if (trips.contains(new Trip(t))){
			return trips.get(trips.indexOf(new Trip(t)));
		}else{
			return null;
		}
	}
	public Trip getTrip(Trip t){
		if (trips.contains(t)){
			return trips.get(trips.indexOf(t));
		}else{
			return null;
		}
	}

	//equality checking
	public boolean equals(String route){
		if (routeID.equals(route)){
			return true;
		}else{
			return false;
		}
	}
	public boolean equals(Route r){
		return equals(r.getID());
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof Route){
			return equals((Route)o);
		}else if(o instanceof String){
			return equals((String)o);
		}else{
			return false;
		}
	}
	@Override
	public int hashCode(){
		return Integer.parseInt(routeID);
	}

	//getter methods
	public String getID(){
		return routeID;
	}

	public ArrayList getTrips(){
		return trips;
	}
	public int getTripSize(){
		return trips.size();
	}

}

class Trip{
	private String id;
	private ArrayList<Stop> stops=new ArrayList<Stop>();
	private String direction;

	public Trip(String id){
		this.id=id;
	}

	public void addStop(String s, String a){
		stops.add(new Stop(s,a));
	}

	//equals methods
	public boolean equals(String id){
		if(this.id.equals(id)){
			return true;
		}else{
			return false;
		}
	}
	public boolean equals(Trip t){
		return equals(t.getID());
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof Trip){
			return equals((Trip)o);
		}else if(o instanceof String){
			return equals((String)o);
		}else{
			return false;
		}
	}
	@Override
	public int hashCode(){
		return Integer.parseInt(id);
	}


	//getter methods
	public String getID(){
		return id;
	}
	public ArrayList getStops(){
		return stops;
	}

}

class Stop{
	private String id;
	private String time;

	public Stop(String code, String time){
		id=code;
		this.time=time;
	}

	//getter methods
	public String getID(){
		return id;
	}
	public String getTime(){
		return time;
	}
}

