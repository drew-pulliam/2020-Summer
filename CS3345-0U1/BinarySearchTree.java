import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public BinarySearchTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }

    /**
     * Find the smallest item in the tree.
     * 
     * @return smallest item or null if empty.
     * @throws Exception
     */
    public AnyType findMin() throws Exception
    {
        if( isEmpty( ) )
            throw new Exception();
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * 
     * @return the largest item of null if empty.
     * @throws Exception
     */
    public AnyType findMax() throws Exception
    {
        if( isEmpty( ) )
            throw new Exception();
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return new BinaryNode<>( x, null, null );
        
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;  // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return t;   // Item not found; do nothing
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return false;
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true;    // Match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree( BinaryNode<AnyType> t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height( BinaryNode<AnyType> t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );    
    }
    
    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType>
    {
            // Constructors
        BinaryNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
    }


      /** The tree root. */
    private BinaryNode<AnyType> root;


    /*
    --------------------------------------------------------------------------------------------
    Drew Pulliam - DTP180003
    CS3345 Project 2
    
    All new code below this line
    --------------------------------------------------------------------------------------------
    */
        // Test program
    public static void main( String [ ] args ) throws Exception
    {
        BinarySearchTree<Integer> t = new BinarySearchTree<>( );
        final int NUMS = 20;
        final int GAP  =   3;

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            t.insert( i );

        for( int i = 1; i < NUMS; i+= 2 )
            t.remove( i );

        if( NUMS < 40 )
            t.printTree( );

        System.out.println("\nsize: " + t.size());
        System.out.println("\nnumLeaves: " + t.numLeaves());
        System.out.println("\nnumLeftChildren: " + t.numLeftChildren());
        System.out.println("\nisFull: " + t.isFull());
        System.out.println("\nnodeDepth of 4: " + t.nodeDepth(4));
        System.out.println("nodeDepth of 2: " + t.nodeDepth(2));
        System.out.println("nodeDepth of 66: " + t.nodeDepth(66));
        System.out.println("\nprintByLevels: " + t.printByLevels());
    }

    /**
     * returns the number of nodes in the tree (recursive)
     * @return int number of nodes in tree
     */
    public int size(){
        return size(root);
    }
    private int size(BinaryNode<AnyType> node){
        if (node == null){
            return 0;
        }else{
            return(size(node.left) + 1 + size(node.right));
        }
    }

    /**
     * returns the number of leaves in the tree (recursive)
     * @return int number of leaves in tree
     */
    public int numLeaves(){
        return numLeaves(root);
    }
    private int numLeaves(BinaryNode<AnyType> node){
        if(node == null){
            return 0;
        }
        if(node.left == null && node.right == null){
            return 1;   // its a leaf
        }else{
            return(numLeaves(node.left) + numLeaves(node.right));   // not a leaf, check it's children
        }
    }

    /**
     * returns the number of left children in the tree (recursive)
     * @return int number of left children in tree
     */
    public int numLeftChildren(){
        return numLeftChildren(root);
    }
    private int numLeftChildren(BinaryNode<AnyType> node){
        if(node == null){
            return 0;
        }
        if(node.left != null){
            return (1 + numLeftChildren(node.left) + numLeftChildren(node.right));   // it has a left child
        }else{
            return(numLeftChildren(node.left) + numLeftChildren(node.right));   // doesn't have a left child
        }
    }

    /**
     * returns whether the tree is full
     * @return boolean is tree full
     */
    public boolean isFull(){
        return isFull(root);
    }
    private boolean isFull(BinaryNode<AnyType> node) 
    { 
        if(node == null) {
            return true;    // empty tree
        }

        if(node.left == null && node.right == null ) {
            return true;    // its a leaf
        }
           
        if((node.left!=null) && (node.right!=null)) {
            return (isFull(node.left) && isFull(node.right));   // check both child subtrees
        }
           
        // has one child (not full tree)
        return false; 
    } 

    /**
     * returns the depth of the given node value (recursive)
     * @param val node value to search for
     * @return int depth of given node value
     */
    public int nodeDepth(AnyType val){
        return nodeDepth(root, val, 0);
    }
    public int nodeDepth(BinaryNode<AnyType> node, AnyType val, int depth){
        if(node == null){
            return -1;  // not found
        }else if(node.element == val){
            return depth;
        }else if(val.compareTo( node.element ) < 0){
            // go left
            return nodeDepth(node.left,val,depth+1);
        }else{
            // go right
            return nodeDepth(node.right,val,depth+1);
        }
    }

    /**
     * print the tree level by level
     * @return string tree printed level by level
     */
    public String printByLevels(){
        String result = "";
        Queue<BinaryNode<AnyType>> q = new LinkedList<BinaryNode<AnyType>>();
		if (root == null)
			return "";
		q.add(root);
		while (!q.isEmpty()) {
            BinaryNode<AnyType> n = (BinaryNode<AnyType>) q.remove();
            result += n.element + " ";
			// System.out.print(" " + n.element);
			if (n.left != null)
				q.add(n.left);
			if (n.right != null)
				q.add(n.right);
        }
        return result;
    }
}