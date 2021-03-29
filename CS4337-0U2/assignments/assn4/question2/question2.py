"""
Drew Pulliam – DTP180003
Assignment 4
CS 4337.0U2 
"""
g = 100;
def sub1():
  a = 2
  print("sub1: a = ",a," g = ",g)
  def sub2():
    nonlocal a
    b = 3
    print("sub2: a = ",a," g = ",g," b = ",b)
    def sub3():
      nonlocal a,b
      c = 4
      print("sub3: a = ",a," g = ",g," b = ",b," c = ",c)
      def sub4():
        global g
        nonlocal a,b,c
        d = 5
        g = 99
        print("sub4: a = ",a," g = ",g," b = ",b," c = ",c," d = ",d)
        def sub5():
          nonlocal a,b,c,d
          e = 6
          print("sub5: a = ",a," g = ",g," b = ",b," c = ",c," d = ",d," e = ",e)
        sub5()
      sub4()
    sub3()
  sub2()

sub1()