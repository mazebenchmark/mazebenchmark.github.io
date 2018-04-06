## Welcome to Maze Benchmark

This web page contains the supplementary material of the paper titled "Maze Benchmark for Evolutionary Algorithms". In this site you can download the dataset and the Software used. In addition, you're going to find the user guide for this material.

I want to welcome you, and I hope you found this material useful.

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

### .mz Files

### .loc Files

## Copyright

