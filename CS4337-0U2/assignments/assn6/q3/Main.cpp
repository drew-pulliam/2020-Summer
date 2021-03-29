/*
    4337.0U2
    Assignment 6 - Q3
    Drew Pulliam
    dtp180003
*/

#include <stdio.h>

void swap(int num1, int num2){
    int temp;
    temp = num1;
    num1 = num2;
    num2 = temp;
}

void swap(int *num1, int *num2){
    int temp;
    temp = *num1;
    *num1 = *num2;
    *num2 = temp;
}
    
int main()
{
    int num1=2,num2=5;
    swap(num1,num2);
    
    printf("\nNum1: %d",num1);
    printf("\nNum2: %d",num2);
    
    int num3=2,num4=5;
    swap(&num3,&num4);
    
    printf("\nNum3: %d",num3);
    printf("\nNum4: %d",num4);

    return 0;
}