/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
    /**
     * Construct an empty LinkedList.
     */
    public MyLinkedList( )
    {
        doClear( );
    }
    
    private void clear( )
    {
        doClear( );
    }
    
    /**
     * Change the size of this collection to zero.
     */
    public void doClear( )
    {
        beginMarker = new Node<>( null, null, null );
        endMarker = new Node<>( null, beginMarker, null );
        beginMarker.next = endMarker;
        
        theSize = 0;
        modCount++;
    }
    
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }
    
    public boolean isEmpty( )
    {
        return size( ) == 0;
    }
    
    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add( AnyType x )
    {
        add( size( ), x );   
        return true;         
    }
    
    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add( int idx, AnyType x )
    {
        addBefore( getNode( idx, 0, size( ) ), x );
    }
    
    /**
     * Adds an item to this collection, at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */    
    private void addBefore( Node<AnyType> p, AnyType x )
    {
        Node<AnyType> newNode = new Node<>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;         
        theSize++;
        modCount++;
    }   
    
    
    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        return getNode( idx ).data;
    }
        
    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;
        
        p.data = newVal;   
        return oldVal;
    }
    
    /**
     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
     * @param idx index to search at.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    private Node<AnyType> getNode( int idx )
    {
        return getNode( idx, 0, size( ) - 1 );
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
     */    
    private Node<AnyType> getNode( int idx, int lower, int upper )
    {
        Node<AnyType> p;
        
        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
            
        if( idx < size( ) / 2 )
        {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;            
        }
        else
        {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        } 
        
        return p;
    }
    
    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        return remove( getNode( idx ) );
    }
    
    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove( Node<AnyType> p )
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;
        
        return p.data;
    }
    
    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );

        return new String( sb );
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<AnyType> iterator( )
    {
        return new LinkedListIterator( );
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyLinkedList.
     */
    private class LinkedListIterator implements java.util.Iterator<AnyType>
    {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        
        public boolean hasNext( )
        {
            return current != endMarker;
        }
        
        public AnyType next( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( ); 
                   
            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }
        
        public void remove( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );
                
            MyLinkedList.this.remove( current.prev );
            expectedModCount++;
            okToRemove = false;       
        }
    }
    
    /**
     * This is the doubly-linked list node.
     */
    private static class Node<AnyType>
    {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }
        
        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }
    
    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;

    /*
    --------------------------------------------------------------------------------------------
    Drew Pulliam - DTP180003
    CS3345 Project 1
    
    All new code below this line
    --------------------------------------------------------------------------------------------
    */

    /**
     * checks how many instances of item are in the list
     * @param item the object to search the list for
     * @return the number of times item appears in the list
     */
    public int itemCount(AnyType item){
        Node<AnyType> cur = beginMarker;
        int count = 0;
        while(cur.next != null){
            // System.out.println("cur: "+ cur.data+ " item: "+item);
            if(cur.data != null){
                if((cur.data).equals(item)){
                    count++;
                }
            }
            cur = cur.next;
        }
        return count;
    }
    
    /**
     * swaps the Nodes at positions index1 and index2, which must range from 0 to size( ) - 1.
     * @param index1 first index to swap
     * @param index2 second index to swap
     * @throws IndexOutOfBoundsException if indices are not between 0 and size( ) - 1, inclusive.
     */
    public void swap(int index1, int index2){
        Node<AnyType> node1 = getNode(index1);
        Node<AnyType> node2 = getNode(index2);
        Node<AnyType> tempNext = node1.next;
        Node<AnyType> tempPrev = node1.prev;

        if(node1.prev!=null)
            node1.prev.next = node2;
        if(node1.next!=null)
            node1.next.prev = node2;
            
        if(node2.prev!=null)
            node2.prev.next = node1;
        if(node2.next!=null)
            node2.next.prev = node1;

        node1.next = node2.next;
        node1.prev = node2.prev;
        node2.next = tempNext;
        node2.prev = tempPrev;

        modCount++;
    }

    /**
     * returns a new MyLinkedList object containing the elements from index1 to index2, inclusive
     * @param index1 starting index
     * @param index2 final index
     * @return new MyLinkedList containing the elements from index1 to index2, inclusive
     * @throws IndexOutOfBoundsException if indices are not between 0 and size( ) - 1, inclusive.
     */
    public MyLinkedList<AnyType> sublist(int index1, int index2){
        MyLinkedList<AnyType> newList = new MyLinkedList<AnyType>();
        Node<AnyType> cur = getNode(index1);
        Node<AnyType> endNode = getNode(index2);
        while(cur.equals(endNode.next) == false){
            newList.add(cur.data);
            cur = cur.next;
        }
        return newList;
    }

    /**
     * returns a new MyLinkedList object containing the elements from the given indices
     * @param indices any amount of indices to add to list
     * @return new MyLinkedList containing the elements from given indices
     * @throws IndexOutOfBoundsException if any index is not between 0 and size( ) - 1, inclusive.
     */
    public MyLinkedList<AnyType> select(int ... indices){
        MyLinkedList<AnyType> newList = new MyLinkedList<AnyType>();
        for (int i: indices){
            if(i >= 0 && i <= (size() - 1)){
                newList.add(getNode(i).data);
            }else{
                throw new IndexOutOfBoundsException( "select index: " + i + " size: "+ (size()-1));
            }
        }
        return newList;
    }

    /**
     * returns a new MyLinkedList object containing the elements from the first list in reverse order
     * @return new MyLinkedList containing reversed elements
     */
    public MyLinkedList<AnyType> reverse(){
        MyLinkedList<AnyType> newList = new MyLinkedList<AnyType>();
        Node<AnyType> cur = endMarker.prev;
        while(cur.equals(beginMarker) == false){
            newList.add(cur.data);
            cur = cur.prev;
        }
        return newList;
    }

    /**
     * removes elements starting from the starting index
     * @param index starting index
     * @param numElements number of elements after starting index to delete
     * @throws IndexOutOfBoundsException if starting and ending index is not between 0 and size( ) - 1, inclusive.
     */
    public void erase(int index, int numElements){
        if(index < 0 || (index + numElements) > (size()-1))
            throw new IndexOutOfBoundsException( "erase index: " + index + " numElements: " + numElements + " size: "+ (size()-1));
        
        Node<AnyType> start = getNode(index);
        Node<AnyType> end = getNode(index+numElements);
        start.prev.next = end.next;
        end.next.prev = start.prev;

        modCount++;
        theSize -= (numElements+1);
    }

    /**
     * inserts a new list at index position
     * @param newList new list to be inserted
     * @param index starting index
     * @throws IndexOutOfBoundsException if starting index is not between 0 and size( ) - 1, inclusive.
     */
    public void insertList(MyLinkedList<AnyType> newList, int index){
        if(index < 0 || index > (size()-1))
            throw new IndexOutOfBoundsException( "insert index: " + index + " size: "+ (size()-1));
    
        Node<AnyType> start = getNode(index);
        Node<AnyType> end = start.next;
        Node<AnyType> newStart = newList.beginMarker.next;
        Node<AnyType> newEnd = newList.endMarker.prev;
        start.next = newStart;
        end.prev = newEnd;
        newStart.prev = start;
        newEnd.next = end;

        modCount++;
        theSize += newList.size();
    }

    /**
     * shifts the list this many nodes forward or backward
     * @param shiftAmount the amount to shift the list, can be positive or negative for forward/backward shifts
     */
    public void shift(int shiftAmount){
        if(shiftAmount > 0){
            // move front nodes to tail
            Node<AnyType> start = beginMarker.next;
            Node<AnyType> end = getNode(shiftAmount - 1);
            Node<AnyType> newStart = end.next;
            Node<AnyType> oldEnd = endMarker.prev;
            start.prev = oldEnd;
            oldEnd.next = start;
            end.next = endMarker;
            endMarker.prev = end;
            newStart.prev = beginMarker;
            beginMarker.next = newStart;
            modCount++;
        }else if(shiftAmount < 0){
            // move tail nodes to front
            Node<AnyType> start = getNode(size() + shiftAmount);
            Node<AnyType> end = endMarker.prev;
            Node<AnyType> newEnd = start.prev;
            Node<AnyType> oldStart = beginMarker.next;
            start.prev = beginMarker;
            beginMarker.next = start;
            end.next = oldStart;
            oldStart.prev = end;
            newEnd.next = endMarker;
            endMarker.prev = newEnd;
            modCount++;
        }
    }
}

class TestLinkedList
{
    public static void main( String [ ] args )
    {
        MyLinkedList<Integer> lst = new MyLinkedList<>( );
        for( int i = 0; i < 10; i++ )
            lst.add( i );
        lst.add(9);

        MyLinkedList<Integer> lst2 = new MyLinkedList<>( );
        for( int i = 20; i < 25; i++ )
            lst2.add( i );

        System.out.println("\nInitial List:");
        System.out.println(lst + "\n");
        
        System.out.println("a. itemCount");
        System.out.println("number of 3's: "+lst.itemCount(3));
        System.out.println("number of 9's: "+lst.itemCount(9)+"\n");
        
        System.out.println("b. swap");
        System.out.println("swap index 3 with index 5");
        System.out.println(lst);
        lst.swap(3, 5);
        System.out.println(lst + "\n");

        System.out.println("c. sublist");
        System.out.println("return sublist from index 0 to index 7");
        System.out.println(lst);
        System.out.println(lst.sublist(0, 7) + "\n");
        
        System.out.println("d. select");
        System.out.println("return list from indices 1,3,4,7");
        System.out.println(lst);
        System.out.println(lst.select(1,3,4,7) + "\n");
        
        System.out.println("e. reverse");
        System.out.println("return reversed list");
        System.out.println(lst);
        System.out.println(lst.reverse() + "\n");
        
        System.out.println("f. erase");
        System.out.println("erase index 3 and the following 2 indices");
        System.out.println(lst);
        lst.erase(3, 2);
        System.out.println(lst + "\n");
        
        System.out.println("g. insertList");
        System.out.println("insert list: "+lst2+" at index 4");
        System.out.println(lst);
        lst.insertList(lst2, 4);
        System.out.println(lst + "\n");
        
        System.out.println("h. shift");
        System.out.println("shift list +3");
        lst.shift(3);
        System.out.println(lst);
        System.out.println("shift list -1");
        lst.shift(-1);
        System.out.println(lst + "\n");

    }
}