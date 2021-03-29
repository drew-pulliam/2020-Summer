
public class LinearProbingHashTable<K,V>
{

    /*
    --------------------------------------------------------------------------------------------
    Drew Pulliam - DTP180003
    CS3345 Project 3
    --------------------------------------------------------------------------------------------
    */

    /**
     * Construct the hash table.
     */
    public LinearProbingHashTable( )
    {
        this( DEFAULT_TABLE_SIZE );
    }

    /**
     * Construct the hash table.
     * @param size the approximate initial size.
     */
    public LinearProbingHashTable( int size )
    {
        allocateTable( size );
        doClear( );
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

    private Entry<K,V> [ ] table; // The table of elements
    private int occupied;                 // The number of occupied cells
    private int theSize;                  // Current size

    private static class Entry<K,V>{
        public K key;
        public V val;
        public boolean isActive;

        public Entry(K key, V val, boolean isActive) {
            this.key = key;
            this.val = val;
            this.isActive = isActive;
        } 

        public String toString(){
            return "" + key + ", " + val + "\t" + (isActive ? "" : "deleted");
        }
    }

    /**
     * Method that performs quadratic probing resolution.
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    private int findPos( K key, V val )
    {
        int currentPos = getHashValue( key );
        
        while( table[ currentPos ] != null &&
                !table[ currentPos ].val.equals( val ) &&
                table[currentPos].isActive == true)
        {
            currentPos ++;
            if( currentPos >= table.length )
                currentPos -= table.length;
        }
        
        return currentPos;
    }

    /**
     * Get current size.
     * @return the size.
     */
    public int size( )
    {
        return theSize;
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return the matching item.
     */
    public boolean contains( K key, V val )
    {
        int currentPos = findPos( key, val );
        return isActive( currentPos );
    }

    /**
     * Return true if currentPos exists and is active.
     * @param currentPos the result of a call to findPos.
     * @return true if currentPos is active.
     */
    private boolean isActive( int currentPos )
    {
        return table[ currentPos ] != null && table[ currentPos ].isActive;
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty( )
    {
        doClear( );
    }

    private void doClear( )
    {
        occupied = 0;
        for( int i = 0; i < table.length; i++ )
            table[ i ] = null;
    }

    /**
     * Get length of internal table.
     * @return the size.
     */
    public int capacity( )
    {
        return table.length;
    }

    /**
     * Internal method to allocate table.
     * @param tableSize the size of the table.
     */
    private void allocateTable( int tableSize )
    {
        table = new Entry[ nextPrime( tableSize ) ];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }

    /*
    inserts entry, rehashes if half full,
    can re-use deleted entries, throws
    exception if key is null, returns
    true if inserted, false if duplicate.
    */
    public boolean insert(K key, V val) throws Exception {
        if(key == null)
            throw new Exception();
        int currentPos = findPos( key, val );
        if( isActive( currentPos ) )
            return false;

        if( table[ currentPos ] == null )
            ++occupied;
        table[ currentPos ] = new Entry<>( key, val, true );
        theSize++;
        
            // Rehash; see Section 5.5
        if( occupied > table.length / 2 )
            rehash( );
        
        return true;
    }

    /*
    returns value for key, or null if not found
    */
    public V find(K key) {
        int currentPos = getLocation(key);

        if(currentPos == -1){
            return null;
        }
        
        if(table[ currentPos ]!= null && table[ currentPos ].key.equals( key )){
            return table[ currentPos ].val;
        }

        return null;
    }

    /*
    marks the entry deleted but leaves it there,
    returns true if deleted, false if not found
    */
    public boolean delete(K key, V val) {
        int currentPos = findPos( key, val );
        if( isActive( currentPos ) )
        {
            table[ currentPos ].isActive = false;
            theSize--;
            return true;
        }
        else
            return false;
    }

    /*
    doubles the table size, hashes everything to
    the new table, omitting items marked deleted
    */
    private void rehash( ) throws Exception {
        Entry<K,V> [ ] oldTable = table;

        // Create a new double-sized, empty table
        allocateTable( 2 * oldTable.length );
        occupied = 0;
        theSize = 0;

        // Copy table over
        for( Entry<K,V> entry : oldTable )
            if( entry != null && entry.isActive )
                insert( entry.key, entry.val );
    }

    /*
    returns the hash value for the given key.
    (this is the value before probing occurs)
    */
    public int getHashValue(K key) {
        int hashVal = key.hashCode( );

        hashVal %= table.length;
        if( hashVal < 0 )
            hashVal += table.length;

        return hashVal;
    }

    /*
    returns the location for the given key,
    or -1 if not found.
    (this is the value after probing occurs)
    */
    public int getLocation(K key) {
        int currentPos = getHashValue( key );
        
        while( table[ currentPos ] != null &&
                !table[ currentPos ].key.equals( key ) &&
                table[currentPos].isActive == true)
        {
            currentPos ++;
            if( currentPos >= table.length )
                currentPos -= table.length;
        }
        if(table[ currentPos ] != null &&
        table[ currentPos ].key.equals( key )){
            return currentPos;
        }

        return -1;
    }

    /*
    returns a formatted string of the hash table,
    where k, v is the key and value at this location:
               0  k, v
               1  
               2  k, v   deleted
               ...
    */
    public String toString() {
        String result = "";
        int num = 0;
        for(Entry<K,V> e : table){
            if(e == null){
                result += num + "\n";
            }else{
                result += num + "  " + e.toString() + "\n";
            }
            num ++;
        }
        return result;
    }

    public static void main( String [ ] args ) throws Exception {

        System.out.println("\n\nCreate Hash Table using ints for keys and strings for values");
        LinearProbingHashTable<Integer, String> t = new LinearProbingHashTable<Integer, String>(11);
        
        System.out.println("insert 1,a 2,b 2,c 2,d 2,e\n");
        System.out.println("insert 1,a success = "+t.insert(1, "a"));
        System.out.println("insert 2,b success = "+t.insert(2, "b"));
        System.out.println("insert 2,c success = "+t.insert(2, "c"));
        System.out.println("insert 2,d success = "+t.insert(2, "d"));
        System.out.println("insert 2,d (2nd time) success = "+t.insert(2, "d"));
        System.out.println("insert 2,e success = "+t.insert(2, "e")+"\n");
        
        System.out.println("print table");
        System.out.println(t.toString());
        
        System.out.println("insert another to force rehash");
        System.out.println("insert 7,f success = "+t.insert(7, "f"));
        System.out.println("print table");
        System.out.println(t.toString());

        System.out.println("find key = 1: "+t.find(1));
        System.out.println("find key = 2: "+t.find(2));
        System.out.println("find key = 7: "+t.find(7));
        System.out.println("find key = 8: "+t.find(8)+"\n");
        System.out.println("get location key = 1: "+t.getLocation(1));
        System.out.println("get location key = 2: "+t.getLocation(2));
        System.out.println("get location key = 7: "+t.getLocation(7));
        System.out.println("get location key = 8: "+t.getLocation(8)+"\n");

        System.out.println("delete 2,b success = "+t.delete(2, "b")+"\n");

        System.out.println("print table");
        System.out.println(t.toString());
    }
}