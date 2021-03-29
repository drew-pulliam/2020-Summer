/*
Drew Pulliam – DTP180003
Assignment 4
CS 4337.0U2 
*/

class question7 {
  public static void main(String[] args) {
    int[] a = {4,4};
    int b = 1;
    a[b] = b = 0;
    System.out.println("a: ["+a[0]+", "+a[1]+"]");
    System.out.println("b: "+b);
  }
}