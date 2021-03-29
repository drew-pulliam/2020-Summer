/* World's simplest shell – shell0.c */
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sysexits.h>
#include <unistd.h>
void sig_int(int signo) {
	printf("\nCaught SIGINT!\n");
}
char* getinput(char *linefer, size_t linelen) {
	printf("myShell > ");
	return fgets(linefer, linelen, stdin);
}

int main(int argc, char **argv) {
	char line[1024];
	pid_t pid;
	int status;
	if (signal(SIGINT, sig_int) == SIG_ERR) {
		fprintf(stderr, "signal error: %s\n", strerror(errno));
		exit(1);
	}
	
	void redirect(char* redirection, char* file){
	int fdout;
	//checks if read, write, append for stdin/stdout
	if(!strcmp(redirection, "in=")){
		if ((fdout = open(file, O_CREAT|O_RDONLY)) < 0) {
		perror(file);	/* open failed */
		}
		dup2(fdout, 0); 
		close(fdout);
	}
	else if(!strcmp(redirection, "out=")){
		if ((fdout = open(file, O_CREAT|O_WRONLY)) < 0) {
		perror(file);	/* open failed */
		}
		dup2(fdout, 1); 
		close(fdout);
	}
	else if(!strcmp(redirection, "app=")){
		if ((fdout = open(file, O_CREAT|O_APPEND|O_WRONLY)) < 0) {
		perror(file);	/* open failed */
		}
		dup2(fdout, 1);
		close(fdout); 
	} 
}


	while (getinput(line, sizeof(line))) {
		line[strlen(line) - 1] = '\0';
		printf(" Input command is: %s \n", line);

		if (strcmp(line, "exit") == 0)  /* is it an "exit"? */
               		exit(0);
            
		if((pid=fork()) == -1) {
			fprintf(stderr, "shell: can't fork: %s\n",
				strerror(errno));
			continue;
		} else if (pid == 0) {      
			/* child process to run the command */
			execlp(line, line, (char *)0);
			fprintf(stderr, "shell: couldn't exec %s: %s\n",
                            line, strerror(errno));
			exit(EX_DATAERR);
		}
                // parent waits till the child process done.  
		if ((pid=waitpid(pid, &status, 0)) < 0)
			fprintf(stderr, "shell: waitpid error: %s\n",
					strerror(errno));
	}
	exit(EX_OK);
}
