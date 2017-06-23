# networkflow-solving
#The details of this networkflow problem is described in the following URL:
http://codecraft.huawei.com/home/detail

The above figure is a network topology of city G. The black cycles stand for network nodes, red cycles stand for consumption nodes, and numbers in the cycles stand for node codes. The connection line between two nodes indicates the network link. As for the (x,y) flag of each link, x refers to the total link bandwidth (Gbps) and y refers to the network rental fee per Gbps. The number between two connected links refers to the bandwidth consumption requirement (Gbps) of the consumption node.
Now, suppose that you need to deploy a video content server on the network that meets all requirements of the consumption node. The following figure describes a low-cost scheme. In this picture, green cycles refer to network nodes deployed with a video content server. Network paths to different consumption nodes are marked in different colors with the bandwidth:


This scheme may not be the one that costs the least. Therefore, participants are required to provide the program with the lowest-cost deployment scheme based on different competition use cases.
(2)	Program Input and Output
Format of the input file
Input a file separated by a space. Each line of the file ends with a linefeed ('\n' or '\r\n').

File format：
Network node count network (blank) Link count consumption (blank) Node count
(Blank line)
Deployment cost of the video content server
(Blank line)
Link's start node ID (blank) Link's end node ID (blank) Total bandwidth (blank) Network rental fee
……………. (Several lines of link information like preceding ones)
(Blank line)
Consumption node ID (blank) Adjacent network node ID (blank) Video bandwidth consumption requirement
…………….(Several lines of end user information like preceding ones.)
(End of file)
Note:
1.	The network node ID and consumption node ID are integers that greater than or equal to 0.
2.	Values in the file are positive integers at a maximum of 100000.
Sample of the input file (for details, see the preceding competition use case):
28 45 12

100

0 16 8 2
0 26 13 2
0 9 14 2
0 8 36 2
(A few lines are omitted here!) 
0 8 40
1 11 13
2 22 28
3 3 45
4 17 11
5 19 26
6 16 15
7 13 13
8 5 18
9 25 15
10 7 10
11 24 23

Format of the output file
Output a file separated by spaces. Each line of the file ends with a linefeed ('\n' or '\r\n').
File format：
If no qualified scheme exists, output a line of
NA
(End of file)
If a qualified scheme exists, output the file in the following format:
Network path count
(Blank line)
Network node ID-01 (blank) Network node ID-02 …… Network node ID-n (blank) Consumption node ID (blank) Bandwidth
……………. (Several lines of network path information like preceding ones. Each network path consists of several network nodes. The start node ID-01 of the path indicates that a video content server has been deployed on the node. The end node is a consumption node.)
(End of file)
Note:
1.	The number of network paths cannot exceed 300000.
2.	The number of nodes in a single path cannot exceed 10000.
3.	Different network paths can be output in random sequence.
4.	Values of the network node ID and consumption node ID must comply with those specified in the input file. The ID value is invalid if it cannot be found in the input file.
5.	Values in the text file must be integers ranging from 0 to 100,0000.
Output file sample (for details, see the preceding use case deployment scheme):
19

0 9 11 1 13
0 7 10 10
0 8 0 36
0 6 5 8 13
(A few lines are omitted here!)
