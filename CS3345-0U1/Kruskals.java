import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Kruskals
{

    /*
    --------------------------------------------------------------------------------------------
    Drew Pulliam - DTP180003
    CS3345 Project 5
    --------------------------------------------------------------------------------------------
    */

    public Kruskals() {
    }
   
    public static void main( String [ ] args ) throws IOException {
        Kruskals k = new Kruskals();
        Scanner graphIn = new Scanner(new File("assn9_data.csv"));
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int curIndex = 0;

        while(graphIn.hasNextLine()){
            String line = graphIn.nextLine();
            String city = line.split(",")[0];
            map.put(city, curIndex);
            curIndex ++;
        }

        ArrayList<Edge> edgeList = new ArrayList<Edge>();
        graphIn = new Scanner(new File("assn9_data.csv"));

        while(graphIn.hasNextLine()){
            String line = graphIn.nextLine();
            String[] edges = line.split(",");
            String startCity = edges[0];
            for(int i = 1; i < edges.length; i+=2){
                String endCity = edges[i];
                int distance = Integer.parseInt(edges[i+1]);
                edgeList.add(k.new Edge(startCity, endCity, distance));
            }
        }

        int edgesAccepted = 0;
        DisjSets ds = k.new DisjSets(curIndex);
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>( edgeList );
        // System.out.println(pq);
        ArrayList<Edge> acceptedEdgeList = new ArrayList<Edge>();

        while (edgesAccepted < (curIndex - 1))
        {
            Edge e = pq.poll();  // get minimum edge = (u,v)
            int uset = ds.find( map.get(e.u) ); // find set vertex u is in.
            int vset = ds.find( map.get(e.v) ); // find set vertex v is in.
            if (uset != vset)    // if not same set (not yet connected)
            {
                // accept the edge
                edgesAccepted++;
                acceptedEdgeList.add(e);
                ds.union(uset, vset); // connect them
            }
        }

        int totalDist = 0;
        for(Edge e : acceptedEdgeList){
            System.out.println(e);
            totalDist += e.len;
        }
        System.out.println("\nsum of all distances in tree = " + totalDist);
    }

    private class Edge implements Comparable<Edge>{
        String u;
        String v;
        int len;
        Edge(String u, String v, int len) {
            this.u = u;
            this.v = v;
            this.len = len;
        }

        @Override
        public int compareTo(Edge e) {
            return (this.len > e.len) ? 1 : ((this.len < e.len) ? -1 : 0);
        }

        @Override
        public String toString() {
            return u+" to "+v+", distance = "+len;
        }
    }



    // DisjSets class
    //
    // CONSTRUCTION: with int representing initial number of sets
    //
    // ******************PUBLIC OPERATIONS*********************
    // void union( root1, root2 ) --> Merge two sets
    // int find( x )              --> Return set containing x
    // ******************ERRORS********************************
    // No error checking is performed

    /**
     * Disjoint set class, using union by rank and path compression.
     * Elements in the set are numbered starting at 0.
     * @author Mark Allen Weiss
     */
    private class DisjSets
    {
        /**
         * Construct the disjoint sets object.
         * @param numElements the initial number of disjoint sets.
         */
        public DisjSets( int numElements )
        {
            s = new int [ numElements ];
            for( int i = 0; i < s.length; i++ )
                s[ i ] = -1;
        }

        /**
         * Union two disjoint sets using the height heuristic.
         * For simplicity, we assume root1 and root2 are distinct
         * and represent set names.
         * @param root1 the root of set 1.
         * @param root2 the root of set 2.
         */
        public void union( int root1, int root2 )
        {
            if( s[ root2 ] < s[ root1 ] )  // root2 is deeper
                s[ root1 ] = root2;        // Make root2 new root
            else
            {
                if( s[ root1 ] == s[ root2 ] )
                    s[ root1 ]--;          // Update height if same
                s[ root2 ] = root1;        // Make root1 new root
            }
        }

        /**
         * Perform a find with path compression.
         * Error checks omitted again for simplicity.
         * @param x the element being searched for.
         * @return the set containing x.
         */
        public int find( int x )
        {
            if( s[ x ] < 0 )
                return x;
            else
                return s[ x ] = find( s[ x ] );
        }

        private int [ ] s;
    }
}

