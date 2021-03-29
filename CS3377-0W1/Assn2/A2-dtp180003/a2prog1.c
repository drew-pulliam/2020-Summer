#include "apue.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <fcntl.h>

int
main(void)
{
	time_t t;
	struct tm *tmp;
	char buf1[16];
	char buf2[64];

	time(&t);
	tmp = localtime(&t);
	
	strftime(buf2, 64, "create dir1 at %r, %a %b %d, %Y", tmp);
	printf("%s\n", buf2);
		
	size_t size;
	char *ptr = path_alloc(&size);
	if (getcwd(ptr, size) == NULL)
		err_sys("getcwd failed");


	if((mkdir("dir1",00777))==-1) {
		fprintf(stdout,"error in creating dir\n");
	}

	if(chdir("dir1") < 0)
		err_sys("chdir failed");

	if(open("file1.txt",O_CREAT | O_WRONLY) < 0)
		err_sys("open file1.txt failed");
	else{
		strftime(buf2, 64, "create file1.txt at %r, %a %b %d, %Y", tmp);
	  printf("%s\n", buf2);
  }
  
  if(open("file2.txt",O_CREAT | O_WRONLY) < 0)
		err_sys("open file2.txt failed");
	else{
		strftime(buf2, 64, "create file2.txt at %r, %a %b %d, %Y", tmp);
	  printf("%s\n", buf2);
  }
   
  if(chdir(ptr) < 0)
		err_sys("chdir failed");
     
	time(&t);
	tmp = localtime(&t);
	strftime(buf2, 64, "create dir2 at %r, %a %b %d, %Y", tmp);
	printf("%s\n",buf2);
	if((mkdir("dir2",00777))==-1) {
		fprintf(stdout,"error in creating dir\n");
	}
 
 if(chdir("dir2") < 0)
		err_sys("chdir failed");
   
   if(open("file3.txt",O_CREAT | O_WRONLY) < 0)
		err_sys("open file3.txt failed");
	else{
		strftime(buf2, 64, "create file3.txt at %r, %a %b %d, %Y", tmp);
	  printf("%s\n", buf2);
  }
   
   if(open("file4.txt",O_CREAT | O_WRONLY) < 0)
		err_sys("open file4.txt failed");
	else{
		strftime(buf2, 64, "create file4.txt at %r, %a %b %d, %Y", tmp);
	  printf("%s\n", buf2);
  }
   
   if(chdir(ptr) < 0)
		err_sys("chdir failed");
     
	time(&t);
	tmp = localtime(&t);
	strftime(buf2, 64, "create dir3 at %r, %a %b %d, %Y", tmp);
	printf("%s\n",buf2);
	if((mkdir("dir3",00777))==-1) {
		fprintf(stdout,"error in creating dir\n");
	}
  
  if(symlink("dir2/file4.txt","dir3/link5") < 0)
    err_sys("create symlink failed");
  else{
		strftime(buf2, 64, "create link5 at %r, %a %b %d, %Y", tmp);
	  printf("%s\n", buf2);
  }
	exit(0);
}
		
