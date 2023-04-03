# JavaWordCount
Utility to count the instances of a string in files in the current folder (searches recursively)  
Currently case-insensitive  

## Usage:
1. Compile, package into jar
2. java -jar WordCount.jar [--print_files] search term goes here. don't need quotes

## TO-DO:
1. Add options for case-sensitivity  

Fun fact: This was originally created to count the number of swears in the Linux kernel source!
(Results taken from the copy I had downloaded, i.e. [this](https://github.com/kentjhall/horizon-linux/commit/0f64a3959839fb49c718da210701cb894aedc8f8))  
"fuck": 29  
“wtf”: 245  
“shit”: 170  
“hell”: 9797
