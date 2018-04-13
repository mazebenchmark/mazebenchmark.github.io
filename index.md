## Welcome to Maze Benchmark

This web page contains the supplementary material of the paper titled "Maze Benchmark for Evolutionary Algorithms". In this site you can download the dataset and the Software used. In addition, you're going to find the user guide for this material.

I want to welcome you, and I hope you found this material useful.

- [Dataset](#Dataset)
- [SCMP Folder](#SCMP-folder)
- [DCMP Folder](#DCMP-folder)

> Explore the various features to save time in your usage of this material.

## Dataset

The `dataset` folder contains the maze sets generated for testing Evolutionary Algorithms. This folder has two folders which match with the two proposed benchmark problems. These problems are the "Similarly Connected Maze Problem" (folder `SCMP`), and the "Differently Connected Maze Problem" (folder `DCMP`).

### SCMP Folder

In the `SCMP` folder there is four folders which contain the four generated instances of the SCMP problem. Each folder has a file called `starting_locations.loc`, and a folder called `mazes` where there are 10 maze files (.mz), which were generated with the connectivities shown in the following table:

**Folder** | **Connectivity**|
-------|-------------|
`SCMP1`|      0%     |
`SCMP2`|     30%     |
`SCMP3`|     60%     |
`SCMP2`|    100%     |

Additionally, in SCMP folders there is also the folder `images`, where there are images of the mazes inside folder `mazes`. Feel free to use and modify these images in your research, as you want.

### DCMP Folder

In the `DCMP` folder there is four folders which contain the four generated instances of the DCMP problem. Each folder has a file called `starting_locations.loc`, and two folders: `Test` and `Train`. The `Train` folder have the mazes thar are used in the Evolutionary Process of your Algorithm. In addition, The `Test` folder have the mazes for evaluating obtained Exploration Strategies. Finally, both folders have the folder `mazes` which contain the generated mazes, and the folder `images` which store the images of these mazes.

Finally, the `Train` folders of the DCMP folders contain the mazes listed in the following table:

**Folder**|**Connectivity**|**Amount**
----------|----------------|----------
          |        0%      |  2 files
   DCMP1  |       15%      |  2 files
          |       30%      |  2 files
----------|----------------|----------
          |       40%      |  2 files
   DCMP2  |       60%      |  2 files
          |       80%      |  2 files
----------|----------------|----------
          |       70%      |  2 files
   DCMP3  |       85%      |  2 files
          |      100%      |  2 files
----------|----------------|----------
   DCMP2  |        0%      |  3 files
          |      100%      |  3 files

And all `Test` folders contains the following mazes:

**connectivity**|**Amount**
----------------|----------
        0%      |  4 files
       25%      |  4 files
       50%      |  4 files
       75%      |  4 files
      100%      |  4 files

## Data Files

Two kinds of files are used to store both maze structures (.mz) and starting locations (.loc). How to these files are written and what these files are interpreted, are described in the next two sections.

### .mz Files

The `.mz` files contains the maze structure information: size, connectivity, and walls plot. The following table shows an example of a `.mz` file and what indicates each value:

**.mz File**|**Indicates**
-------------------------------|-----------
```10 10```|widht and height
```25.0                         ```|connectivity
```9  5  5  5  1  1  1  1  1  7```|Row 1 cells setup
```12  1  3 11 12  2  8  0  0  7```|Row 2 cells setup
```9  2 30  8  5  4  2 14 10 11```|Row 3 cells setup
```14 10  9  4  7  9  2 13  2 10```|Row 4 cells setup
```13  0  0  1  1  2  8  1  0  6```|Row 5 cells setup
```9  2 12  6  8  0  2  8  0  3```|Row 6 cells setup
```10  8  3 13  0  2 10  8  0  6```|Row 7 cells setup
```8  2  8  1  2 10 12  2  8  3```|Row 8 cells setup
```8  0  0  4  0  6 11 10 14 10```|Row 9 cells setup
```12  4  4  7 12  5  4  4  7 14```|Row 10 cells setup

The first two values indicates the weight and the height of a maze (in cells). The next value indicates the connectivity of the maze. Then, there are **width X height** values that indicate which walls surround each cell of the maze. These values encode the surrounded wall by using 5 bits. The rightmost bit indicates whether a wall surround the upper side of a cell, the second rightmost the right side, the third the lower side, the fourth the left side. Finally, the leftmost bit indicates whether the cell is a goal cell. The following table, summarizes the cell representation for each possible value:

**Value**|**Bits**|**Cell**|**Value**|**Bits**|**Cell**
---------|--------|--------|---------|--------|--------
0|00000|![](/images/cells/0.png)|16|10000|![](/images/cells/16.png)
1|00001|![](/images/cells/1.png)|17|10001|![](/images/cells/17.png)
2|00010|![](/images/cells/2.png)|18|10010|![](/images/cells/18.png)
3|00011|![](/images/cells/3.png)|19|10011|![](/images/cells/19.png)
4|00100|![](/images/cells/4.png)|20|10100|![](/images/cells/20.png)
5|00101|![](/images/cells/5.png)|21|10101|![](/images/cells/21.png)
6|00110|![](/images/cells/6.png)|22|10110|![](/images/cells/22.png)
7|00111|![](/images/cells/7.png)|23|10111|![](/images/cells/23.png)
8|01000|![](/images/cells/8.png)|24|11000|![](/images/cells/24.png)
9|01001|![](/images/cells/9.png)|25|11001|![](/images/cells/25.png)
10|01010|![](/images/cells/10.png)|26|11010|![](/images/cells/26.png)
11|01011|![](/images/cells/11.png)|27|11011|![](/images/cells/27.png)
12|01100|![](/images/cells/12.png)|28|11100|![](/images/cells/28.png)
13|01101|![](/images/cells/13.png)|29|11101|![](/images/cells/29.png)
14|01110|![](/images/cells/14.png)|30|11110|![](/images/cells/30.png)
15|01111|![](/images/cells/15.png)|31|11111|![](/images/cells/31.png)

Finally, the resulting maze of the file shown in the table is this:

![sample_maze](/images/sample_maze.png "Sample Maze")

### .loc Files

## Copyright

