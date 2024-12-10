package main

import (
	"bytes"
	"fmt"
	"os"
)

func checkX(input [][]byte, i, j int) (count int) {

	if i > 0 && i < len(input)-1 && j > 0 && j < len(input[i])-1 {
		c1 := input[i-1][j-1] == 'M' && input[i+1][j+1] == 'S' || input[i-1][j-1] == 'S' && input[i+1][j+1] == 'M'
		c2 := input[i-1][j+1] == 'M' && input[i+1][j-1] == 'S' || input[i-1][j+1] == 'S' && input[i+1][j-1] == 'M'
		if c1 && c2 {
			count++
		}
	}

	return
}

func main() {
	file, err := os.ReadFile("./day4.txt")
	if err != nil {
		fmt.Printf("error reading file %w\n", err)
	}
	lines := bytes.Split(file, []byte{'\n'})
	lines = lines[0 : len(lines)-1]
	count := 0
	for i, row := range lines {
		for j, col := range row {
			if col == 'A' {
				count += checkX(lines, i, j)
			}
		}
	}
	fmt.Printf("Part1 := %d\n", count)
	// Part1 := 1824
}
