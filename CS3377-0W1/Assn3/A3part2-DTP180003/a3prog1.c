// Sample code to set a timer to interrupt after some time (to terminate the run).

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <signal.h>
#include <unistd.h>

#define MINUTE 60
static int timer_expired = 0;

static void alarm_handler(int sig)
{
	printf("The program is terminated after idle time of %d seconds\n", (timer_expired)?timer_expired:MINUTE);
	timer_expired = 0;
	exit(EXIT_SUCCESS);
}
 

int main()
{
   struct sigaction mySigAction;
   mySigAction.sa_handler = alarm_handler;
   sigaction(SIGALRM, &mySigAction, NULL); //set SIGALRM action to mySigAction
   alarm(MINUTE); //set default timer for 60 seconds

   int input=0;
   while(1)
   {
       printf("Enter command: ");
       input = scanf("timer=%d", &timer_expired);
       if(input)
            alarm(timer_expired);
       while(getchar()!='\n');
   }
   return 0;
}
